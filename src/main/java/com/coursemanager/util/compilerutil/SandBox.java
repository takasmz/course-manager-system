package com.coursemanager.util.compilerutil;

import java.io.*;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.*;

import com.coursemanager.model.ExamTestCase;
import com.coursemanager.util.compilerutil.dto.*;
import com.coursemanager.util.compilerutil.stream.CacheOutputStream;
import com.coursemanager.util.compilerutil.stream.ThreadInputStream;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;

import com.coursemanager.model.ExamInfo;
import com.coursemanager.util.common.Constant;

import ch.qos.logback.classic.Logger;


public class SandBox {
	// 每加载超过100个类后，就替换一个新的ClassLoader
	private static final int UPDATE_CLASSLOADER_GAP = 5;
	// 记录一共加载过的类数量
	private int loadClassCount = 0;
	private static final Logger logger = (Logger) LoggerFactory.getLogger(SandBox.class);
	private Gson gson = new Gson();
	private boolean isBusy = false;
	private String pid = null;
	private Socket communicateSocket;
	private MemoryMXBean systemMemoryBean = ManagementFactory.getMemoryMXBean();
	// 用一个线程池去处理每个判题请求
	private ExecutorService problemThreadPool = Executors
			.newSingleThreadExecutor(r -> {
				Thread thread = new Thread(r);
				thread.setName("problemThreadPool");
				thread.setUncaughtExceptionHandler((t, e) -> writeResponse(null,
						CommunicationSignal.ResponseSignal.ERROR,
						null, e.getMessage()));
				return thread;
			});
	// 用一个线程池去等待每个判题请求的结果返回
	private ExecutorService problemResultThreadPool = Executors
			.newSingleThreadExecutor(r -> {
				Thread thread = new Thread(r);
				thread.setName("problemResultThreadPool");
				thread.setUncaughtExceptionHandler((t, e) -> writeResponse(null,
						CommunicationSignal.ResponseSignal.ERROR,
						null, e.getMessage()));
				return thread;
			});

	// 用于重定向输出流，即代码输出的结果，将会输出到这个缓冲区中
	private volatile CacheOutputStream resultBuffer = new CacheOutputStream();
	private volatile ThreadInputStream systemThreadIn = new ThreadInputStream();

    private SandBox() {
        initSandbox();
    }

	/**
	 * 沙箱初始化函数
	 */
	private void initSandbox() {
		// 获取进程id，用于向外界反馈
		getPid();
		// 打开用于与外界沟通的通道
		openServerSocketWaitToConnect(8089);
		// 等外界与沙箱，通过socket沟通上之后，就会进行业务上的沟通
		service();

	}


	/**
	 * 获取进程ID
	 */
	private void getPid() {
		String name = ManagementFactory.getRuntimeMXBean().getName();
		pid = name.split("@")[0];
	}

	/**
	 * 打开连接，等待建立连接
	 * @param port 监听端口
	 */
	private void openServerSocketWaitToConnect(int port) {
		try {
			ServerSocket serverSocket = new ServerSocket(port);
			System.out.println("sandbox" + port + "wait");
			logger.debug("sandbox" + port + "wait");
			communicateSocket = serverSocket.accept();
			System.out.println("pid:" + pid);
			logger.debug("pid:" + pid);
			// 只与外部建立一个沟通的连接
			serverSocket.close();
		} catch (IOException e) {
			logger.error(e.getMessage());
			System.out.println(e.getMessage());
			throw new RuntimeException("无法打开沙箱端Socket，可能是端口被占用了");
		}
	}

	/**
	 * 检查沙箱是否正忙
	 * @param signalId 信号量
	 */
	private void checkBusy(String signalId) {
		String responseCommand;

		if (isBusy) {
			responseCommand = CommunicationSignal.ResponseSignal.YES;
		} else {
			responseCommand = CommunicationSignal.ResponseSignal.NO;
		}

		writeResponse(signalId, responseCommand,
				CommunicationSignal.RequestSignal.IS_BUSY, null);
	}

	/**
	 * 发送回复
	 * @param signalId 信号
	 * @param responseCommand 回复的命令
	 * @param requestCommand 请求的命令
	 * @param data 数据
	 */
	private void writeResponse(String signalId, String responseCommand,
							   String requestCommand, String data) {
		try {
            boolean closed = communicateSocket.isClosed();
			OutputStream outputStream = communicateSocket.getOutputStream();
			Response response = new Response();
			response.setSignalId(signalId);
			response.setResponseCommand(responseCommand);
			//a
			response.setRequestCommand(requestCommand);
			response.setData(data);
			outputStream
					.write((gson.toJson(response) + "\n").getBytes(StandardCharsets.UTF_8));
            //outputStream.flush();
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw new RuntimeException("无法对外输出数据");
		}

	}


	/**
	 * 系统服务函数
	 */
	private void service() {
		try {
			Scanner scanner = new Scanner(communicateSocket.getInputStream());
			// 必须建立了连接和流之后，才能设置这里的权限
			System.setSecurityManager(new SandboxSecurityManager());
			String data;
			while (scanner.hasNext()) {
				// 每一次交流，都是一行一行的形式交流，即本次沟通内容发送完之后，发送方会在最后，加上一个"\n"，表示发送完了这条消息
				data = scanner.nextLine();
				Request request = gson.fromJson(data, Request.class);
				dispatchRequest(request);
			}
			scanner.close();
		} catch (Exception e) {
			writeResponse(null, CommunicationSignal.ResponseSignal.ERROR, null,
					e.getMessage());
		}
	}

	/**
	 * 请求分发函数
	 * @param request 请求内容
	 */
	private void dispatchRequest(Request request) {
		if (CommunicationSignal.RequestSignal.CLOSE_SANDBOX.equals(request
				.getCommand())) {
			closeSandboxService(request.getSignalId());
		} else if (CommunicationSignal.RequestSignal.SANDBOX_STATUS
				.equals(request.getCommand())) {
			feedbackSandboxStatusService(request.getSignalId());
		} else if (CommunicationSignal.RequestSignal.REQUSET_JUDGED_PROBLEM
				.equals(request.getCommand())) {
			if (loadClassCount >= UPDATE_CLASSLOADER_GAP) {
				loadClassCount = 0;
				System.gc();
			}
			TestCaseDto testCaseDto = gson.fromJson(request.getData(),TestCaseDto.class);
			Future<ExamResultDto> future = problemThreadPool.submit(new ExamCallable(systemThreadIn,testCaseDto,resultBuffer));
			returnJudgedProblemResult(request.getSignalId(),future);
			isBusy = true;
			loadClassCount++;
		} else if (CommunicationSignal.RequestSignal.IS_BUSY.equals(request
				.getCommand())) {
			checkBusy(request.getSignalId());
		}
	}

	/**
	 * 返回题目运行结果
	 * @param signalId 信号
	 * @param result 题目运行结果
	 */
	private void returnJudgedProblemResult(final String signalId,
										   final Future<ExamResultDto> result) {
		problemResultThreadPool.execute(() -> {

			if (result != null) {
				try {
                    ExamResultDto dto = result.get();
                    String resultStr = gson.toJson(dto);
                    System.out.println(resultStr);
					writeResponse(
							signalId,
							CommunicationSignal.ResponseSignal.OK,
							CommunicationSignal.RequestSignal.REQUSET_JUDGED_PROBLEM,
                            resultStr);
					isBusy = false;

					// 通知对方，主动告诉对方，自己已经空闲了，已经准备好下一次判题
					writeResponse(null,
							CommunicationSignal.ResponseSignal.IDLE, null,
							null);
				} catch (Exception e) {
					writeResponse(null,
							CommunicationSignal.ResponseSignal.ERROR, null,
							e.getMessage());
				}
			}
		});
	}

	/**
	 * 关闭沙箱服务
	 * @param signalId 关闭信号
	 */
	private void closeSandboxService(String signalId) {
		writeResponse(signalId, CommunicationSignal.ResponseSignal.OK,
				CommunicationSignal.RequestSignal.CLOSE_SANDBOX, null);
		try {
			communicateSocket.close();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		closeSandbox();
	}

	/**
	 * 返回沙箱状态的服务
	 * @param signalId 信号
	 */
	private void feedbackSandboxStatusService(String signalId) {
		SandBoxStatus sandBoxStatus = new SandBoxStatus();
		sandBoxStatus.setPid(pid);
		sandBoxStatus.setBeginStartTime(System.currentTimeMillis());
		sandBoxStatus.setBusy(isBusy);
		// 由堆内存和非堆内存组成
		long useMemory = systemMemoryBean.getHeapMemoryUsage().getUsed()
				+ systemMemoryBean.getNonHeapMemoryUsage().getUsed();
		sandBoxStatus.setUseMemory(useMemory);
		// 由堆内存和非堆内存组成
		long maxMemory = systemMemoryBean.getHeapMemoryUsage().getMax()
				+ systemMemoryBean.getNonHeapMemoryUsage().getMax();
		sandBoxStatus.setMaxMemory(maxMemory);
		writeResponse(signalId, CommunicationSignal.ResponseSignal.OK,
				CommunicationSignal.RequestSignal.SANDBOX_STATUS,
				gson.toJson(sandBoxStatus));

	}


	/**
	 * @Author 李如豪
	 * @Description 编译前进行代码检查
	 * @Date 12:04 2019/1/24
	 * @Param code 提交的代码
	 * @return 代码是否符合规范
	 **/
	public boolean beforeRunCheckCode(String code){
		if(StringUtils.isBlank(code)){
			logger.debug("[beforeRunCheckCode] 代码为空");
			return false;
		}
		return true;
	}
	
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExamInfo problemTest = new ExamInfo();
		problemTest.setInputs("123,100,200|-123|120|120|120|120|120|120|120|120|120|120|120|120|120");
		problemTest.setAnswer("423|-123|120|120|120|120|120|120|120|120|120|120|120|120|120");
		List<ExamTestCase> testCaseList = new ArrayList<>();
		for(int i=0;i<10;i++){
			ExamTestCase examTestCase = new ExamTestCase();
			int rand1 = (int) (Math.random()*100);
			int rand2 = (int) (Math.random()*100);
			int rand3 = (int) (Math.random()*100);
			examTestCase.setInput(rand1 + "," + rand2 + "," + rand3);
			examTestCase.setOutput(rand1+rand2+rand3+"");
			testCaseList.add(examTestCase);
		}

//		String test =
//				 "public class Main{\n"
//       		+ "public int reverse(int x){\n"
//       		+ "int rev = 0;\r\n" + 
//       		"        while (x != 0) {\r\n" + 
//       		"            int pop = x % 10;\r\n" + 
//       		"            x /= 10;\r\n" + 
//       		"            if (rev > Integer.MAX_VALUE/10 || (rev == Integer.MAX_VALUE / 10 && pop > 7)) return 0;\r\n" + 
//       		"            if (rev < Integer.MIN_VALUE/10 || (rev == Integer.MIN_VALUE / 10 && pop < -8)) return 0;\r\n" + 
//       		"            rev = rev * 10 + pop;\r\n" + 
//       		"        }\r\n" + 
//       		"        return rev;\n"
//       		+ "}\n"
//       		+ "}\n";
		String test = 
				"import java.util.*;\n" +
						"public class Main{\r\n" +
				"public int solution(String x){ \r\n" +
						"String[] numbers = x.split(\",\");\n"
				+ "	int a = 0;\r\n"
				+		"List<Integer> list = new ArrayList<>();\n"
				+ "	for(int i=0;i<numbers.length;i++){\r\n"
				+ "		a += Integer.parseInt(numbers[i]);\r\n"
				+ "	}\r\n"
				+ "	return a;\r\n"
				+ "}\r\n" +
						"public static void main(String[] args){\n" +
						"Main m = new Main();\n" +
						"Scanner scanner = new Scanner(System.in);\n" +
						"String x = scanner.nextLine();\n" +
						"System.out.println(m.solution(x));\n" +
						"}\n"
				+"}";
		System.out.println(test);
		Map<String, Object> map;
		try {
            new SandBox();
//			map = (Map<String, Object>) checkThreadByRunMethod(testCaseList, test);
//			//System.setOut(new PrintStream(System.out));
//			for(Map.Entry<String, Object> entry:map.entrySet()) {
//				System.out.println(entry.getKey() + ":" + entry.getValue());
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * 关闭沙箱
	 */
	private void closeSandbox() {
		try {
			communicateSocket.close();
		} catch (IOException ignored) {
		}
		System.exit(Constant.EXIT_VALUE);
	}
}

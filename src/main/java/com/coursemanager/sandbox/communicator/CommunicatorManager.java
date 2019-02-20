package com.coursemanager.sandbox.communicator;

import com.coursemanager.sandbox.communicator.listener.SandboxIdleListener;
import com.coursemanager.sandbox.dto.CommonRequest;
import com.coursemanager.sandbox.dto.CommunicatorStatus;
import com.coursemanager.sandbox.dto.JavaSandboxStartInfo;
import com.coursemanager.sandbox.dto.JudgeProblemRequest;
import com.coursemanager.util.common.ThreadFactoryUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @Author 李如豪
 * @Description 与沙箱进行通讯的类
 * @Date 15:36 2019/2/13
 **/
public class CommunicatorManager {
	private BlockingQueue<Communicator> judgeingCommunicators = new LinkedBlockingQueue<>();
	private BlockingQueue<Communicator> noJudgeingCommunicators = new LinkedBlockingQueue<>();
	private BlockingQueue<Communicator> haveStopCommunicators = new LinkedBlockingQueue<>();
	private BlockingQueue<JudgeProblemRequest> problemRequests = new LinkedBlockingQueue<>();
	private BlockingQueue<JudgeProblemRequest> highPriorityProblemRequests = new LinkedBlockingQueue<>();
	// 注意，这个map仅仅是为了执行非请求判题的操作（比如，检查沙箱状态等）方便而设立，绝对不要从里面获取交流者，然后用于提交判题请求
	private Map<String, Communicator> allCommunicators = new ConcurrentHashMap<>();
	private ExecutorService watchingExecutor = Executors
			.newSingleThreadExecutor(ThreadFactoryUtil
					.getLogThreadFactory(CommunicatorManager.class.getName()
							+ " watchingExecutor "));
	private final static Logger logger = LoggerFactory.getLogger(CommunicatorManager.class);
	private static volatile CommunicatorManager communicatorManager = null;

    public static CommunicatorManager getInstance() {
		if (communicatorManager == null) {
			synchronized (CommunicatorManager.class) {
				if (communicatorManager == null) {
					communicatorManager = new CommunicatorManager();
				}
			}
		}

		return communicatorManager;
	}

	private CommunicatorManager() {
		init();
	}

	/**
	 * @Author 李如豪
	 * @Description 用一个线程池去不停的查看判题队列里有无判题请求，如果有就将请求发给沙箱 ，没有则阻塞
	 * @Date 16:54 2019/2/13
	 **/
	private void init() {
		// TODO 现在的代码还是挺难看的，到时候把一些东西抽取出来变成一个个方法以及变成一个Runnable这样吧
		watchingExecutor.execute(() -> {
			while (!Thread.interrupted()) {
                JudgeProblemRequest request;
				try {
					// 如果有空闲的沙箱的话，就取出一个判题请求，先取出优先级高的，这里并不会阻塞
					request = highPriorityProblemRequests.poll();
					if (request == null) {
						// 当前没有判题请求的话，则会一直阻塞在这里
						request = problemRequests.take();
					}
                } catch (InterruptedException e) {
					continue;
				}
				Communicator communicator = null;
				try {
					communicator = noJudgeingCommunicators.take();
                } catch (InterruptedException e) {
					logger.error(e.getMessage());
				}

				// 移进判题列表中
                assert communicator != null;
                judgeingCommunicators.add(communicator);
				communicator.sendRequset(request.getRequest(),
                        request.getExecutor());
				communicator.setJudgeing(true);
			}
		});
	}

	/**
	 * 返回一个交流者的身份区别凭证
	 * @param sandboxStartInfo 沙箱初始状态
	 * @return 返回沙箱ip和端口
	 */
	public String makeNewSandBox(JavaSandboxStartInfo sandboxStartInfo) {
		try {
			Process process = openNewSandBox(sandboxStartInfo);
			return connectToNewSandBox(sandboxStartInfo.getIp(),
					sandboxStartInfo.getPort(), process);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	/**
	 * @Author 李如豪
	 * @Description 使用命令行运行沙箱
	 * @Date 10:52 2019/2/14
	 * @param sandboxStartInfo 沙箱初始状态
	 * @return Process
	 **/
	private Process openNewSandBox(JavaSandboxStartInfo sandboxStartInfo)
			throws IOException {
		String command = "java -jar " + sandboxStartInfo.getJarFilePath() + " " + sandboxStartInfo.getPort();
		return Runtime.getRuntime().exec(command);
	}

	/**
	 * @Author 李如豪
	 * @Description 尝试连接沙箱
	 * @Date 10:56 2019/2/14
	 * @param ip ip地址
     * @param port 端口号
     * @param process procedd
	 * @return ip:端口号
	 **/
	private String connectToNewSandBox(String ip, int port, Process process) {
		Communicator communicator = new Communicator(ip, port, process);
		//连接服务端socket
		boolean flag = communicator.connectToSandbox();
		//连接失败
		if (!flag) {
			return null;
		}

		String url = ip + ":" + port;
		allCommunicators.put(url, communicator);
		noJudgeingCommunicators.add(communicator);

		communicator
				.addSandboxIdleListener(new CommunicatorSandboxIdleListener(
						communicator, url));
		return url;
	}

	/**
	 * @Author 李如豪
	 * @Description 停用相关id的客户端
	 * @Date 10:56 2019/2/14
	 * @param idCard 客户端id
	 **/
	public void stopSingleCommunicatorById(String idCard) {
		Communicator javaCommunicator = allCommunicators.get(idCard);
		if (javaCommunicator == null) {
			throw new RuntimeException("没有该连接");
		}

		if (haveStopCommunicators.contains(javaCommunicator)) {
			return;
		}

		// 判断当前该连接是否为空闲连接
		if (noJudgeingCommunicators.contains(javaCommunicator)) {
			// 因为集合本身是线程安全的，所以如果这里移除成功了，其它地方就不可能（不从map上拿的话）在拿到这个链接了。
			boolean remove = noJudgeingCommunicators.remove(javaCommunicator);
			if (remove) {
				// 直接加入到停止集合里
				haveStopCommunicators.add(javaCommunicator);
				javaCommunicator.setStop(true);
				return;
			}
		}
		// 如果上面都不成立，只能设置为请求停止状态，等该沙箱处理完题目后，再将其停止
		javaCommunicator.setWantStop(true);
	}

	/**
	 * @Author 李如豪
	 * @Description 关闭相关id的沙箱连接
	 * @Date 10:58 2019/2/14
	 * @param idCard 连接id
	 **/
	public void closeSandboxConnectById(String idCard) {
		Communicator javaCommunicator = allCommunicators.get(idCard);
		if (javaCommunicator == null) {
			throw new RuntimeException("没有该连接");
		}

		if (haveStopCommunicators.contains(javaCommunicator)) {
			// 直接关闭沙箱
			haveStopCommunicators.remove(javaCommunicator);
			allCommunicators.remove(idCard);
			javaCommunicator.closeWithSandboxConnect();
			return;
		}

		// 判断当前该连接是否为空闲连接
		if (noJudgeingCommunicators.contains(javaCommunicator)) {
			// 因为集合本身是线程安全的，所以如果这里移除成功了，那定时器那里，就不可能在拿到这个链接了。
			boolean remove = noJudgeingCommunicators.remove(javaCommunicator);
			if (remove) {
				allCommunicators.remove(idCard);
				javaCommunicator.closeWithSandboxConnect();
			}
		}

		// 如果上面都不成立，只能设置为请求关闭状态，等该沙箱处理完题目后，再将其停止
		javaCommunicator.setWantClose(true);
	}

	/**
	 * @Author 李如豪
	 * @Description 构造公共输入
	 * @Date 10:59 2019/2/14
	 * @param communicatorIdCard 客户端id
     * @param commonRequest 公共输入
	 **/
	public void publicCommonRequest(String communicatorIdCard,
			CommonRequest commonRequest) {
		Communicator communicator = allCommunicators.get(communicatorIdCard);

		if (communicator != null) {
			communicator.sendRequset(commonRequest.getRequest(),commonRequest.getExecutor());
		}
	}

	/**
	 * @Author 李如豪
	 * @Description 新增判题请求，添加到判题队列
	 * @Date 11:00 2019/2/14
	 * @param request 判题请求
	 **/
	public void publicJudgeProblemRequest(JudgeProblemRequest request) {
		logger.debug("[publicJudgeProblemRequest] 新增判题:%s",request.getRequest().getData());
		problemRequests.add(request);
	}

	/**
	 * @Author 李如豪
	 * @Description 获得客户端状态
	 * @Date 11:01 2019/2/14
	 * @param communicatorIdCard 客户端id
	 * @return 客户端状态对象
	 **/
	public CommunicatorStatus getCommunicatorStatus(String communicatorIdCard) {
		Communicator javaCommunicator = allCommunicators
				.get(communicatorIdCard);
		CommunicatorStatus c = new CommunicatorStatus();
		c.setJudgeing(javaCommunicator.isJudgeing());
		c.setStop(javaCommunicator.isStop());
		c.setWantClose(javaCommunicator.isWantClose());
		c.setWantStop(javaCommunicator.isWantStop());

		return c;
	}


    /**
     * 连接沙箱监听器
     */
	private class CommunicatorSandboxIdleListener implements
			SandboxIdleListener {
		private Communicator communicator;
		private String communicatorIdCard;

		/**
		 * @Author 李如豪
		 * @Description 构造器
		 * @Date 11:04 2019/2/14
		 * @param communicator 客户端对象
         * @param communicatorIdCard 客户端id
		 **/
		private CommunicatorSandboxIdleListener(Communicator communicator,
				String communicatorIdCard) {
			this.communicator = communicator;
			this.communicatorIdCard = communicatorIdCard;
		}

		/**
		 * @Author 李如豪
		 * @Description 沙箱目前空闲，之后的操作
		 * @Date 11:04 2019/2/14
		 **/
		@Override
		public void sandBoxIdelNow() {
			if (communicator == null) {
				return;
			}
			// 先移除出来
			judgeingCommunicators.remove(communicator);
			communicator.setJudgeing(false);
			// 先判断，是否被设置了，想要停止工作的标志
			if (communicator.isWantStop()) {
				haveStopCommunicators.add(communicator);
				communicator.setStop(true);
				communicator.setWantStop(false);
			} else if (communicator.isWantClose()) {
				allCommunicators.remove(communicatorIdCard);
				communicator.closeWithSandboxConnect();
			} else {
				noJudgeingCommunicators.add(communicator);
				communicator.setStop(false);
				communicator.setWantStop(false);
			}
		}
	}

}

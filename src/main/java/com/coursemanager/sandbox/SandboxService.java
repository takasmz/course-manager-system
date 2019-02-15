package com.coursemanager.sandbox;

import com.coursemanager.model.ExamTestCase;
import com.coursemanager.sandbox.commandExecutor.ResponseExecutor;
import com.coursemanager.sandbox.communicator.CommunicatorManager;
import com.coursemanager.sandbox.dto.*;
import com.coursemanager.sandbox.dto.SandBoxStatus;
import com.coursemanager.util.common.JavaCompilerUtil;
import com.coursemanager.util.common.JsonUtil;
import com.coursemanager.util.common.ThreadFactoryUtil;
import com.coursemanager.util.compilerutil.dto.*;
import com.coursemanager.util.common.Constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.*;

/**
 * @Author 李如豪
 * @Date 2019/2/13 15:09
 * @VERSION 1.0
 **/
public class SandboxService {
    private CommunicatorManager communicatorManager = null;
    private Map<String, SandboxStatusDto> sandboxStatusMap = new ConcurrentHashMap<String, SandboxStatusDto>();
    private static volatile SandboxService javaSandboxService;
    private final static Logger logger = LoggerFactory.getLogger(CommunicatorManager.class);
    private ScheduledExecutorService statusTimer = Executors
            .newScheduledThreadPool(1, ThreadFactoryUtil
                    .getLogThreadFactory(SandboxService.class.getName()
                            + " statusTimer"));
    private ExecutorService executorService = Executors
            .newCachedThreadPool(ThreadFactoryUtil
                    .getLogThreadFactory(SandboxService.class.getName()
                            + " executorService"));

    public static SandboxService getInstance() {
        if (javaSandboxService == null) {
            synchronized (SandboxService.class) {
                if (javaSandboxService == null) {
                    javaSandboxService = new SandboxService();
                }
            }
        }
        return javaSandboxService;
    }

    /**
     * @Author 李如豪
     * @Description 初始化函数，实例沙箱通讯类
     * @Date 15:34 2019/2/13
     **/
    private SandboxService() {
        communicatorManager = CommunicatorManager.getInstance();
        openStatusListen();
    }

    /**
     * @Author 李如豪
     * @Description 对沙箱状态进行监听，每5秒获取一次沙箱状态
     * @Date 15:35 2019/2/13
     **/
    private void openStatusListen() {
        // 每500毫秒，更新一次状态
        statusTimer.scheduleAtFixedRate(new Runnable() {
            private int count = 0;
            @Override
            public void run() {
                if (sandboxStatusMap.size() > 0) {
                    Iterator<Map.Entry<String, SandboxStatusDto>> iterator = sandboxStatusMap
                            .entrySet().iterator();
                    Map.Entry<String, SandboxStatusDto> entry;
                    while (iterator.hasNext()) {
                        entry = iterator.next();
                        // 相当于每5秒，通过网络向沙箱获取一次状态
                        if (count == 10) {
                            try {
                                fillingSandboxStatusData(entry.getValue(), true);
                            } catch (Exception e) {
                                // 先简单处理，只要获取错误，就当做是这个沙箱线程意外的死亡了，直接做死亡处理
                                logger.error(e.getMessage());
                                iterator.remove();
                                communicatorManager
                                        .closeSandboxConnectById(entry
                                                .getValue().getIdCard());
                            }
                        } else {
                            fillingSandboxStatusData(entry.getValue(), false);
                        }
                    }
                    count++;
                    count %= 11;
                }
                //给所有在线用户发送沙箱状态
//                notifyAllStatusObserver(sandboxStatusMap.values());
            }
        }, 0, 500, TimeUnit.MILLISECONDS);
    }

    /**
     * @Author 李如豪
     * @Description 打开沙箱
     * @Date 15:55 2019/2/13
     **/
    public void openNewJavaSandbox() {
        executorService.execute(() -> {
            String ip = "127.0.0.1";
            int port = getValidport();
            JavaSandboxStartInfo sandboxStartInfo = new JavaSandboxStartInfo();
            sandboxStartInfo.setIp(ip);
            sandboxStartInfo.setPort(port);
            try {
                sandboxStartInfo.setJarFilePath(ResourceUtils.getFile(
                        ResourceUtils.CLASSPATH_URL_PREFIX
                                + "sandbox/homework_judge_sandbox.jar")
                        .getAbsolutePath());
            } catch (FileNotFoundException e) {
                logger.error(e.getMessage());
                return;
            }

            String sandboxIdCard = communicatorManager
                    .makeNewSandBox(sandboxStartInfo);
            if (sandboxIdCard == null) {
                throw new RuntimeException("创建失败");
            }

            SandboxStatusDto sandboxStatus = new SandboxStatusDto(sandboxIdCard,
                    ip, port);
            sandboxStatusMap.put(sandboxIdCard, sandboxStatus);
            fillingSandboxStatusData(sandboxStatus, true);
        });

    }

    /**
     * @Author 李如豪
     * @Description 开始判题操作
     * @Date 11:13 2019/2/14
     * @param testCaseDto 测试用例
     * @param judgeResultListener 判题监听器
     * @param errorListener 错误监听器
     **/
    public void judgeProblem(final TestCaseDto testCaseDto, final JudgeResultListener judgeResultListener,
                             final ErrorListener errorListener) {
        executorService.execute(() -> {
            try {
                Request request = new Request();
                request.setCommand(CommunicationSignal.RequestSignal.REQUSET_JUDGED_PROBLEM);
                request.setData(JsonUtil.toJson(testCaseDto));
                JudgeProblemRequest judgeProblemRequest = new JudgeProblemRequest();
                judgeProblemRequest.setRequest(request);
                judgeProblemRequest.setExecutor(new ProblemResponseExecutor(
                        judgeResultListener));
                communicatorManager
                        .publicJudgeProblemRequest(judgeProblemRequest);
            } catch (Exception e) {
                if (errorListener != null) {
                    errorListener.onError(e);
                }
            }

        });

    }

    /**
     * @Author 李如豪
     * @Description
     * @Date 15:38 2019/2/13
     * @param sandboxStatus 沙箱状态抽象类
     * @param getFullData 是否需要向沙箱请求状态,如果为true的话，这个方法还会向沙箱发出一个请求,远程获得该沙箱的一些信息（比如，当前使用内存，pid等）
     **/
    private void fillingSandboxStatusData(SandboxStatusDto sandboxStatus,
                                          boolean getFullData) {
        if (getFullData) {
            CommonRequest commonRequest = new CommonRequest();
            commonRequest.setExecutor(new SandboxStatusResponseExecutor(
                    sandboxStatus));
            Request request = new Request();
            request.setCommand(CommunicationSignal.RequestSignal.SANDBOX_STATUS);
            commonRequest.setRequest(request);
            communicatorManager.publicCommonRequest(sandboxStatus.getIdCard(),
                    commonRequest);
        }

        CommunicatorStatus communicatorStatus = communicatorManager
                .getCommunicatorStatus(sandboxStatus.getIdCard());
        sandboxStatus.setJudgeing(communicatorStatus.isJudgeing());
        sandboxStatus.setWantClose(communicatorStatus.isWantClose());
        sandboxStatus.setWantStop(communicatorStatus.isWantStop());
        sandboxStatus.setRunning(communicatorStatus.isStop());
    }

    /**
     * @Author 李如豪
     * @Description 返回判题结果
     * @Date 17:55 2019/2/13
     * @param response 返回结果
     * @param judgeResultListener 判题监听器
     **/
    private void processJudgeResult(Response response,
                                    JudgeResultListener judgeResultListener) {
        ExamResult examResult = JsonUtil.toBean(response.getData(),
                ExamResult.class);
        examResult.setExamId(response.getExamId());
        if (judgeResultListener != null) {
            judgeResultListener.judgeResult(examResult);
        }
    }


    private void closeSandboxConnectById(String idCard) {
        communicatorManager.closeSandboxConnectById(idCard);
    }

    public interface JudgeResultListener {
        void judgeResult(ExamResult examResult);
    }

    private class ProblemResponseExecutor implements ResponseExecutor {
        private JudgeResultListener judgeResultListener;

        public ProblemResponseExecutor(
                                       JudgeResultListener judgeResultListener) {
            this.judgeResultListener = judgeResultListener;
        }

        @Override
        public void executor(Response response) {
            processJudgeResult(response,judgeResultListener);
        }
    }

    /**
     * 关闭沙箱执行器
     */
    private class CloseSandboxResponseExecutor implements ResponseExecutor {
        private String sandboxIdCard;

        public CloseSandboxResponseExecutor(String sandboxIdCard) {
            this.sandboxIdCard = sandboxIdCard;
        }

        @Override
        public void executor(Response response) {
            if (CommunicationSignal.ResponseSignal.OK.equals(response
                    .getResponseCommand())) {
                closeSandboxConnectById(sandboxIdCard);
            }
        }
    }

    private static class SandboxStatusResponseExecutor implements
            ResponseExecutor {
        private SandboxStatusDto sandboxStatus;

        public SandboxStatusResponseExecutor(SandboxStatusDto sandboxStatus) {
            this.sandboxStatus = sandboxStatus;
        }

        @Override
        public void executor(Response response) {
            SandBoxStatus status = JsonUtil.toBean(response.getData(),
                    SandBoxStatus.class);
            sandboxStatus.setBeginTime(status.getBeginStartTime());
            sandboxStatus.setPid(status.getPid());
            sandboxStatus.setUseMemory(status.getUseMemory());
            sandboxStatus = null;
        }

    }

    private static int portIndex = 10;
    private static int basePortIndex = 60000;

    private synchronized int getValidport() {
        if (portIndex >= 5535) {
            basePortIndex -= 10000;
            portIndex = 10;
        }
        portIndex++;
        return basePortIndex + portIndex;
    }

    public interface ErrorListener {
        void onError(Exception exception);
    }




    public static void main(String[] args) {
        SandboxService sandboxService =  getInstance();
        sandboxService.openNewJavaSandbox();
        TestCaseDto testCaseDto = new TestCaseDto();
        testCaseDto.setCode("import java.util.*;\n" +
                "public class Main{\n" +
                "public int solution(String x){ \n" +
                "String[] numbers = x.split(\",\");\n" +
                "\tint a = 0;\n" +
                "List<Integer> list = new ArrayList<>();\n" +
                "\tfor(int i=0;i<numbers.length;i++){\n" +
                "\t\ta += Integer.parseInt(numbers[i]);\n" +
                "\t}\n" +
                "\treturn a;\n" +
                "}\n" +
                "public static void main(String[] args){\n" +
                "Main m = new Main();\n" +
                "Scanner scanner = new Scanner(System.in);\n" +
                "String x = scanner.nextLine();\n" +
                "System.out.println(m.solution(x));\n" +
                "}\n" +
                "}");
        List<ExamTestCase> list = new ArrayList<>();
        for(int i =0;i<10;i++){
            ExamTestCase examTestCase = new ExamTestCase();
            examTestCase.setExamId(i);
            examTestCase.setId(i+1);
            examTestCase.setInput("100,200,301");
            examTestCase.setOutput("601");
            list.add(examTestCase);
        }
        testCaseDto.setTestCaseItem(list);
        sandboxService.judgeProblem(testCaseDto, null, Throwable::printStackTrace);
    }
}

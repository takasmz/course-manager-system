package com.coursemanager.sandbox.communicator.messageProcessor;

import com.coursemanager.sandbox.commandExecutor.ResponseExecutor;
import com.coursemanager.sandbox.communicator.Communicator;
import com.coursemanager.sandbox.communicator.listener.SandboxIdleListener;
import com.coursemanager.util.common.JsonUtil;
import com.coursemanager.util.compilerutil.dto.CommunicationSignal;
import com.coursemanager.util.compilerutil.dto.Request;
import com.coursemanager.util.compilerutil.dto.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

public class MessageProcessor {
	private static Map<String, ResponseExecutor> responseExecutors = new HashMap<>();
	private Thread messageBoxThread;
	private Scanner scanner;
	private OutputStream outputStream;
	private SandboxIdleListener idleListener;
	private Logger logger = LoggerFactory.getLogger(Communicator.class);

	public MessageProcessor(InputStream inputStream, OutputStream outputStream) {
		scanner = new Scanner(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
		this.outputStream = outputStream;
		openMessageListen();

	}

	/**
	 * @Author 李如豪
	 * @Description 用一个线程来监听沙箱传过来的数据
	 * @Date 17:00 2019/2/13
	 **/
	private void openMessageListen() {
		messageBoxThread = new Thread() {
			@Override
			public void run() {
				String message;
				Response response;
				while (!this.isInterrupted()) {
					if (scanner.hasNextLine()) {
						message = scanner.nextLine();
						response = JsonUtil.toBean(message, Response.class);
						if (CommunicationSignal.ResponseSignal.IDLE
								.equals(response.getResponseCommand())) {
							if (idleListener != null) {
								idleListener.sandBoxIdelNow();
							}
							// 这里理应再修改一下，利用观察者模式，做到对修改关闭，做扩展开放，可以动态添加多个监听器
						} else if (CommunicationSignal.ResponseSignal.ERROR
								.equals(response.getResponseCommand())) {
							logger.error(String.valueOf(new RuntimeException(response
									.getData())));
						} else {
							ResponseExecutor commandExecutor = responseExecutors
									.remove(response.getExamId());
							if (commandExecutor != null) {
								commandExecutor.executor(response);
							}
						}
					}
				}
			}
		};
		messageBoxThread.start();
		messageBoxThread
				.setUncaughtExceptionHandler((t, e) -> logger.error(e.getMessage()));
	}

	public void close() {
		try {
			outputStream.close();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		scanner.close();
		messageBoxThread.interrupt();
	}

    private void addExecutor(String signalId, ResponseExecutor commandExecutor) {
        responseExecutors.put(signalId, commandExecutor);
    }

    /**
     * @Author 李如豪
     * @Description 向沙箱发送请求
     * @Date 11:11 2019/2/14
     * @param request 请求内容
	 * @param executor 对应执行方法
     **/
	public void sendRequset(Request request, ResponseExecutor executor) {
		try {
			if(request.getExamId() == null){
				request.setExamId(UUID.randomUUID().toString());
			}
			String data = JsonUtil.toJson(request);
            addExecutor(request.getExamId(), executor);
			outputStream.write((data + "\n").getBytes());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public SandboxIdleListener getIdleListener() {
		return idleListener;
	}

	public void setIdleListener(SandboxIdleListener idleListener) {
		this.idleListener = idleListener;
	}

}

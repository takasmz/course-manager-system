package com.coursemanager.sandbox.communicator;

import com.coursemanager.sandbox.commandExecutor.ResponseExecutor;
import com.coursemanager.sandbox.communicator.listener.SandboxIdleListener;
import com.coursemanager.sandbox.communicator.messageProcessor.MessageProcessor;
import com.coursemanager.util.compilerutil.dto.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Communicator {
	private Socket socket;
	private Process process;
	private String ip;
	private int port;
	private MessageProcessor messageProcessor;
	private boolean isJudgeing;
	private boolean isWantStop;
	private boolean isStop;
	private boolean isWantClose;
	private Logger logger = LoggerFactory.getLogger(Communicator.class);

	public Communicator() {

	}

	public Communicator(String ip, int port, Process process) {
		this.ip = ip;
		this.port = port;
		this.process = process;
	}

	public boolean connectToSandbox() {
		if (socket == null) {
			try {
				socket = new Socket();
				socket.connect(new InetSocketAddress(ip, port));
				messageProcessor = new MessageProcessor(
						socket.getInputStream(), socket.getOutputStream());
			} catch (Exception e) {
				logger.error(e.getMessage());
				return false;
			}
		}

		return true;
	}

	public void closeWithSandboxConnect() {
		if (socket != null) {
			try {
				messageProcessor.close();
				messageProcessor = null;
				socket.close();
				socket = null;
				if (process.isAlive()) {
					process.destroyForcibly();
				}
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
	}

	public void sendRequset(Request request, ResponseExecutor executor) {
		messageProcessor.sendRequset(request, executor);
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public boolean isWantStop() {
		return isWantStop;
	}

	public void setWantStop(boolean isWantStop) {
		this.isWantStop = isWantStop;
	}

	public boolean isStop() {
		return isStop;
	}

	public void setStop(boolean isStop) {
		this.isStop = isStop;
	}

	public void addSandboxIdleListener(SandboxIdleListener idleListener) {
		if (messageProcessor != null) {
			messageProcessor.setIdleListener(idleListener);
		}
	}

	public boolean isWantClose() {
		return isWantClose;
	}

	public void setWantClose(boolean isWantClose) {
		this.isWantClose = isWantClose;
	}

	public boolean isJudgeing() {
		return isJudgeing;
	}

	public void setJudgeing(boolean isJudgeing) {
		this.isJudgeing = isJudgeing;
	}

	public Process getProcess() {
		return process;
	}

	public void setProcess(Process process) {
		this.process = process;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ip == null) ? 0 : ip.hashCode());
		result = prime * result + port;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Communicator other = (Communicator) obj;
		if (ip == null) {
			if (other.ip != null)
				return false;
		} else if (!ip.equals(other.ip))
			return false;
		if (port != other.port)
			return false;
		return true;
	}

}

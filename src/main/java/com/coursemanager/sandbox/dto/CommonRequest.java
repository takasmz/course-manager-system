package com.coursemanager.sandbox.dto;


import com.coursemanager.sandbox.commandExecutor.ResponseExecutor;
import com.coursemanager.util.compilerutil.dto.Request;

public class CommonRequest {
	private ResponseExecutor executor;
	private Request request;

	public ResponseExecutor getExecutor() {
		return executor;
	}

	public void setExecutor(ResponseExecutor executor) {
		this.executor = executor;
	}

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}
}

package com.coursemanager.util.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;



public class AjaxResponse {
	
	public static final int SUCCESS = 1;
	public static final int ERROR = 0;
	
	private int code;
	private List<ErrorInfo> errors = new ArrayList();
	private String msg = null;
	private Object data = null;
	
	public static AjaxResponse success(String msg)
	  {
	    AjaxResponse success = new AjaxResponse();
	    success.setSuccess();
	    success.setMsg(msg);
	    return success;
	  }
	 
	  public static AjaxResponse success(String msg, Object data)
	  {
	    AjaxResponse success = new AjaxResponse();
	    success.setSuccess();
	    success.setMsg(msg);
	    success.setData(data);
	    return success;
	  }
	 
	  public static AjaxResponse error(String msg)
	  {
	    AjaxResponse error = new AjaxResponse();
	    error.setError();
	    error.setMsg(msg);
	    return error;
	  }
	 
	  public static AjaxResponse error(String errorCode, String msg)
	  {
	    AjaxResponse error = new AjaxResponse();
	    error.setError();
	    error.setMsg(msg);
	    return error;
	  }
	 
	  public static AjaxResponse error(String errorCode, String msg, Object data)
	  {
	    AjaxResponse error = new AjaxResponse();
	    error.setError();
	    error.setMsg(msg);
	    error.setData(data);
	    return error;
	  }
	
	
	
	public AjaxResponse() {
		this.code = SUCCESS;
		this.msg = "success";
	};

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public AjaxResponse setErrorMsg(String msg) {
		this.code = ERROR;
		this.msg = msg;
		return this;
	}
	
	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	public void setSuccess() {
		this.code = SUCCESS;
	}
	
	public void setError() {
		this.code = ERROR;
	}
	
	public void addErrorInfo(ErrorInfo errorInfo){
	    this.errors.add(errorInfo);
	}
	
	public void addErrorInfo(List<ObjectError> validatorErrors){
	    for (int i = 0; i < validatorErrors.size(); i++) {
	    	ObjectError objectError = (ObjectError)validatorErrors.get(i);
	    	if ((objectError instanceof FieldError)) {
		        FieldError fieldError = (FieldError)objectError;
		        ErrorInfo errorInfo = new ErrorInfo();
		        errorInfo.setField(fieldError.getField());
		        errorInfo.setInfo(fieldError.getDefaultMessage());
		        addErrorInfo(errorInfo);
	    	}
	    }
	}
	
	public List<ErrorInfo> getErrors() {
	    return this.errors;
	}
	  
	public void setErrors(List<ErrorInfo> errors) {
		this.errors = errors;
	}
}
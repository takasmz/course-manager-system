package com.coursemanager.util.excel;

public class CellValueException  extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	protected String errorMsg;
	
	public String getErrorMsg() {
		return errorMsg;
	}
}

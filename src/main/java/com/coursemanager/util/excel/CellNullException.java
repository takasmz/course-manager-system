package com.coursemanager.util.excel;

public class CellNullException extends CellValueException {
	
	private static final long serialVersionUID = 1L;
	
	public CellNullException(int index, String cellName) {
		this.errorMsg = "excel第" + index + "行：" + cellName + "的值不能为空";
	}
}

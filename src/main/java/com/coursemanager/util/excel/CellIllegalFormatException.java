package com.coursemanager.util.excel;


public class CellIllegalFormatException extends CellValueException {
	
	private static final long serialVersionUID = 1L;
	
	public static final String DATE = "date";
	public static final String NUMBER = "number";
	
	public CellIllegalFormatException(int index, String cellName, String type) {
		if(DATE.equals(type)) {
			this.errorMsg = "excel第" + index + "行：" + cellName + "的日期格式错误，必须是2017/11/11格式";
		} else if(NUMBER.equals(type)) {
			this.errorMsg = "excel第" + index + "行：" + cellName + "不是数字";
		}
	}
}

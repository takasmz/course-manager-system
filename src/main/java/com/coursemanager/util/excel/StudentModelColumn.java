package com.coursemanager.util.excel;


public class StudentModelColumn extends BaseModelColumn {
	
	public static final StudentModelColumn STUDENT_ID = new StudentModelColumn(1, "学号", false);
	public static final StudentModelColumn STUDENT_NAME = new StudentModelColumn(2, "姓名", false);
	public static final StudentModelColumn MAJOR = new StudentModelColumn(3, "专业名称", false);

	
	/**
	 * @param rowIndex 行序号
	 * @param name	字段名称
	 * @param nullable	可否为空
	 */
	public StudentModelColumn(int rowIndex, String name, boolean nullable) {
		super(name, nullable);
		this.rowIndex = rowIndex;
	}
	
	private int rowIndex;

	public int getRowIndex() {
		return rowIndex;
	}
	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}
	
}

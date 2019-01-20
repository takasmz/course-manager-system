package com.coursemanager.util.excel;


public class BaseModelColumn {
	private String name;
	private boolean nullable;
	
	public BaseModelColumn(String name, boolean nullable) {
		super();
		this.name = name;
		this.nullable = nullable;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isNullable() {
		return nullable;
	}
	
	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}
	
	
}

package com.coursemanager.model;

import java.io.Serializable;

public class UserInfo implements Serializable{
	/**
	 * 唯一身份标识
	 */
	private String accessToken;
	
	private String userName;
	
	private String password;
	
	private String fullName;
	
	/**
	 * 0学生，1教师，2管理员
	 */
	private Integer type;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Integer getType() {
		return type;
	}

	/**
	 * 0学生，1教师，2管理员
	 * @param type
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
}

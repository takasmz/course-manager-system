package com.coursemanager.config;

import java.util.Base64;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.coursemanager.util.common.DESCryptography;


@Component
@ConfigurationProperties(prefix="db")
public class DbPropertiesService {
	private String jdbcUrl;
	private String driverClassName;
	private String username;
	private String password;
	
	public String getJdbcUrl() {
		return jdbcUrl;
	}
	
	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}
	
	public String getDriverClassName() {
		return driverClassName;
	}
	
	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}
	
	public String getUsername() {
		return this.username;
//		return DESCryptography.DESDecode(new String(Base64.getDecoder().decode(username)));
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return this.password;
//		return DESCryptography.DESDecode(new String(Base64.getDecoder().decode(password)));
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public static void main(String[] args) {
		DbPropertiesService s = new DbPropertiesService();
		s.username = "admin";
		s.username = DESCryptography.DESEncode(new String(Base64.getEncoder().encodeToString(s.username.getBytes())));
		System.out.println(s.getUsername());
	}
	
}

package com.coursemanager.service;

import javax.validation.Valid;

import com.coursemanager.dto.RegisterDto;
import com.coursemanager.model.StudentInfo;
import com.coursemanager.model.UserInfo;

public interface IUserInfoService {
	
	UserInfo selectByUserNameAndPassword(String username, String password, Integer loginType);

	UserInfo loginShiro(String username, String password);

	StudentInfo selectByUserName(String username);

	String insertStudent(@Valid RegisterDto registerDto);

	Object selectUserByaccessToken(String access_token);
}

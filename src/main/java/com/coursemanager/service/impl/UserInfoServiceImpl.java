package com.coursemanager.service.impl;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coursemanager.dto.RegisterDto;
import com.coursemanager.mapper.StudentInfoMapper;
import com.coursemanager.mapper.TeacherInfoMapper;
import com.coursemanager.model.StudentInfo;
import com.coursemanager.model.TeacherInfo;
import com.coursemanager.model.UserInfo;
import com.coursemanager.service.IUserInfoService;
import com.coursemanager.util.EncryptUtil;

import tk.mybatis.mapper.entity.Example;


@Service
public class UserInfoServiceImpl extends MyBatisServiceSupport implements IUserInfoService {

	private static final Logger logger = LoggerFactory.getLogger(UserInfoServiceImpl.class);
	
	@Autowired
	private StudentInfoMapper studentInfoMapper;
	
	@Autowired
	private TeacherInfoMapper teacherInfoMapper;
	
	@Override
	public UserInfo selectByUserNameAndPassword(String username, String password, Integer loginType) {
		UserInfo user = new UserInfo();
		
		if(loginType == null) {
			
		}
		//学生登陆
    	if(loginType == 0) {
    		Example example = new Example(StudentInfo.class);
    		example.createCriteria().andEqualTo("userName", username);
    		StudentInfo student = studentInfoMapper.selectOneByExample(example);
            if (student == null) {
                return null;
            }else {
            	if(EncryptUtil.verify(password,student.getPassword())) {
            		user.setUserName(student.getUserName());
                	user.setPassword(student.getPassword());
                	user.setFullName(student.getStudentName());
                	user.setAccessToken(student.getStudentId());
                	user.setType(0);
            	}else {
            		logger.debug("[selectByUserNameAndPassword] student 账号密码错误");
            		return null;
            	}
            }
    	//教师登陆
    	}else if(loginType == 1) {
    		Example example = new Example(TeacherInfo.class);
    		example.createCriteria().andEqualTo("userName", username);
    		TeacherInfo teacher = teacherInfoMapper.selectOneByExample(example);
            if (teacher == null) {
                return null;
            }else {
            	if(EncryptUtil.verify(password,teacher.getPassword())) {
            		user.setUserName(teacher.getUserName());
                	user.setPassword(teacher.getPassword());
                	user.setFullName(teacher.getTeacherName());
                	user.setAccessToken(teacher.getTeacherId());
                	user.setType(1);
            	}else {
            		logger.debug("[selectByUserNameAndPassword] teacher 账号密码错误");
            		return null;
            	}
            	
            }
    	//管理员登陆
    	}else {
    		return null;
    	}
    	return user;
	}
	
	@Override
	public UserInfo loginShiro(String username , String password) {
		UserInfo user = selectByUserNameAndPassword(username,password,0);
		if(user != null) {
			return user; 
		}else {
			return selectByUserNameAndPassword(username,password,1);
		}
	}

	@Override
	public StudentInfo selectByUserName(String username) {
		if (StringUtils.isBlank(username)) {
			logger.debug("[selectByUserName] user is null");
            return null;
        }
        Example example = new Example(StudentInfo.class);
        example.createCriteria().andEqualTo("studentNumber", username);
        List<StudentInfo> list = studentInfoMapper.selectByExample(example);
        if (list != null && list.size() > 0 && StringUtils.isNotBlank(list.get(0).getStudentNumber())) {
            return list.get(0);
        }
        return null;
	}

	@Override
	public String insertStudent(RegisterDto registerDto) {
		if(registerDto == null) {
			logger.debug("[insertStudent] registerDto is null");
			return "参数为空，添加失败";
		}
		StudentInfo student = new StudentInfo(registerDto);
		int num = studentInfoMapper.insert(student);
		if(num == 1) {
			logger.info("[insertStudent] success");
		}else {
			logger.debug("[insertStudent] error");
		}
		return student.getStudentId();
	}

	@Override
	public Object selectUserByaccessToken(String access_token) {
    	String type = access_token.substring(access_token.length()-1);
    	UserInfo user = new UserInfo();
    	if(type.equals("0")) {
			Example example = new Example(StudentInfo.class);
    		example.createCriteria().andEqualTo("studentId",access_token);
    		example.excludeProperties("password");
    		StudentInfo student = studentInfoMapper.selectOneByExample(example);
    		user.setUserName(student.getUserName());
        	user.setPassword(student.getPassword());
        	user.setFullName(student.getStudentName());
        	user.setAccessToken(student.getStudentId());
        	user.setType(0);
    		return user;
    	}else if(type.equals("1")) {
			Example example = new Example(TeacherInfo.class);
    		example.createCriteria().andEqualTo("teacherId",access_token);
    		example.excludeProperties("password");
    		TeacherInfo teacher = teacherInfoMapper.selectOneByExample(example);
    		user.setUserName(teacher.getUserName());
        	user.setPassword(teacher.getPassword());
        	user.setFullName(teacher.getTeacherName());
        	user.setAccessToken(teacher.getTeacherId());
        	user.setType(1);
    		return user;
    	}else {
    		return null;
    	}
	}
}

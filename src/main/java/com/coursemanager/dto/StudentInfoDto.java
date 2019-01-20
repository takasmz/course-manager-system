package com.coursemanager.dto;

import com.coursemanager.model.StudentInfo;

/**
 * 描述:  student_info 对应的Dto类.<br>
 *
 * @author framework generator
 * @date 2018年11月18日
 */
public class StudentInfoDto extends StudentInfo {

	public StudentInfoDto(RegisterDto registerDto) {
		super(registerDto);
	}
}
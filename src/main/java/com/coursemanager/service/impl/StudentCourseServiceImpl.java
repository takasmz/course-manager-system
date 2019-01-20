package com.coursemanager.service.impl;

import com.coursemanager.dto.CourseInfoDto;
import com.coursemanager.mapper.StudentCourseMapper;
import com.coursemanager.model.UserInfo;
import com.coursemanager.service.IStudentCourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 描述:  student_course 对应的Service接口实现类.<br>
 *
 * @author framework generator
 * @date 2018年12月19日
 */
@Service
public class StudentCourseServiceImpl extends MyBatisServiceSupport implements IStudentCourseService {
    private static final Logger logger = LoggerFactory.getLogger(StudentCourseServiceImpl.class);

    @Autowired
    private StudentCourseMapper studentCourseMapper;

}
package com.coursemanager.service.impl;

import com.coursemanager.mapper.StudentInfoMapper;
import com.coursemanager.service.IStudentInfoService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 描述:  student_info 对应的Service接口实现类.<br>
 *
 * @author framework generator
 * @date 2018年11月18日
 */
@Service
public class StudentInfoServiceImpl extends MyBatisServiceSupport implements IStudentInfoService {
    /**
     * 日志记录器
     */
    private static final Logger logger = LoggerFactory.getLogger(StudentInfoServiceImpl.class);

    @Autowired
    private StudentInfoMapper studentInfoMapper;
}
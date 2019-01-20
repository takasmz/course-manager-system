package com.coursemanager.service.impl;

import com.coursemanager.mapper.TeacherInfoMapper;
import com.coursemanager.service.ITeacherInfoService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 描述:  teacher_info 对应的Service接口实现类.<br>
 *
 * @author framework generator
 * @date 2018年11月18日
 */
@Service
public class TeacherInfoServiceImpl extends MyBatisServiceSupport implements ITeacherInfoService {
    /**
     * 日志记录器
     */
    private static final Logger logger = LoggerFactory.getLogger(TeacherInfoServiceImpl.class);

    @Autowired
    private TeacherInfoMapper teacherInfoMapper;
}
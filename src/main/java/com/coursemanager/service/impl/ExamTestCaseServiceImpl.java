package com.coursemanager.service.impl;

import com.coursemanager.mapper.ExamTestCaseMapper;
import com.coursemanager.service.IExamTestCaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 描述:  exam_test_case 对应的Service接口实现类.<br>
 *
 * @author framework generator
 * @date 2019年01月25日
 */
@Service
public class ExamTestCaseServiceImpl implements IExamTestCaseService {
    private static final Logger logger = LoggerFactory.getLogger(ExamTestCaseServiceImpl.class);

    @Autowired
    private ExamTestCaseMapper examTestCaseMapper;
}
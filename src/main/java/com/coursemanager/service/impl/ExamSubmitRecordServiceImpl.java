/*
 * Copyright© 2018 LRH, All Rights Reserved. 
 */
package com.coursemanager.service.impl;

import com.coursemanager.mapper.ExamSubmitRecordMapper;
import com.coursemanager.service.IExamSubmitRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 描述:  exam_submit_record 对应的Service接口实现类.<br>
 *
 * @author framework generator
 * @date 2018年12月15日
 */
@Service
public class ExamSubmitRecordServiceImpl extends MyBatisServiceSupport implements IExamSubmitRecordService {
    /**
     * ����������
     */
    private static final Logger logger = LoggerFactory.getLogger(ExamSubmitRecordServiceImpl.class);

    @Autowired
    private ExamSubmitRecordMapper examSubmitRecordMapper;
}
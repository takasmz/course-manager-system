/*
 * Copyright© 2018 LRH, All Rights Reserved. 
 */
package com.coursemanager.controller;

import com.coursemanager.service.IExamSubmitRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * 描述:  exam_submit_record 对应的Controller类.<br>
 *
 * @author framework generator
 * @date 2018年12月15日
 */
@Controller
public class ExamSubmitRecordController extends BaseController {
    /**
     * ����������
     */
    private static final Logger logger = LoggerFactory.getLogger(ExamSubmitRecordController.class);

    @Autowired
    private IExamSubmitRecordService examSubmitRecordService;
}
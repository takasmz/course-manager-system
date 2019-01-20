package com.coursemanager.controller;

import com.coursemanager.service.IStudentInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * 描述:  student_info 对应的Controller类.<br>
 *
 * @author framework generator
 * @date 2018年11月18日
 */
@Controller
public class StudentInfoController extends BaseController {
    /**
     * 日志记录器
     */
    private static final Logger logger = LoggerFactory.getLogger(StudentInfoController.class);

    @Autowired
    private IStudentInfoService studentInfoService;
}
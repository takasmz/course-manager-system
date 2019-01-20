package com.coursemanager.controller;

import com.coursemanager.service.IStudentCourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class StudentCourseController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(StudentCourseController.class);

    @Autowired
    private IStudentCourseService studentCourseService;
}
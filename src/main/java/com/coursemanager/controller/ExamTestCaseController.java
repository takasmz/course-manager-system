package com.coursemanager.controller;

import com.coursemanager.service.IExamTestCaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ExamTestCaseController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(ExamTestCaseController.class);

    @Autowired
    private IExamTestCaseService examTestCaseService;
}
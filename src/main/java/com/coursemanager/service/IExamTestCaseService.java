package com.coursemanager.service;

import javax.servlet.http.HttpServletRequest;

/**
 * 描述:  exam_test_case 对应的Service接口.<br>
 *
 * @author framework generator
 * @date 2019年01月31日
 */
public interface IExamTestCaseService extends BaseService {
    int addTestCase(HttpServletRequest request);

    int checkTestCaseNum(String examId);
}
package com.coursemanager.service.impl;

import com.coursemanager.mapper.ExamTestCaseMapper;
import com.coursemanager.model.ExamTestCase;
import com.coursemanager.service.IExamTestCaseService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;

/**
 * 描述:  exam_test_case 对应的Service接口实现类.<br>
 *
 * @author framework generator
 * @date 2019年01月31日
 */
@Service
public class ExamTestCaseServiceImpl implements IExamTestCaseService {
    private static final Logger logger = LoggerFactory.getLogger(ExamTestCaseServiceImpl.class);

    @Autowired
    private ExamTestCaseMapper examTestCaseMapper;

    @Override
    public int addTestCase(HttpServletRequest request) {
        String examId = request.getParameter("examId");
        if(StringUtils.isBlank(examId)){
            logger.debug("[addTestCase] id为空");
            return -1;
        }
        ExamTestCase examTestCase = new ExamTestCase();
        examTestCase.setExamId(Integer.parseInt(examId));
        String input = request.getParameter("input");
        String output = request.getParameter("output");
        if(StringUtils.isBlank(input) || StringUtils.isBlank(output)){
            logger.debug("[addTestCase] 输入和输出值为空");
            return -1;
        }
        examTestCase.setInput(input);
        examTestCase.setOutput(output);
        //判断测试用例是否合格
//        StudentExamInfoServiceImpl.JobJudgeResultListener listener = new StudentExamInfoServiceImpl.JobJudgeResultListener(studentExamInfo);
        return examTestCaseMapper.insertSelective(examTestCase);
    }

    @Override
    public int checkTestCaseNum(String examId) {
        assert examId!=null;
        Example example = new Example(ExamTestCase.class);
        example.createCriteria().andEqualTo("examId",examId);
        return examTestCaseMapper.selectCountByExample(example);
    }
}
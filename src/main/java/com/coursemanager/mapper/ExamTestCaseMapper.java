package com.coursemanager.mapper;

import com.coursemanager.model.ExamTestCase;
import com.coursemanager.util.compilerutil.dto.TestCaseDto;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ExamTestCaseMapper extends Mapper<ExamTestCase> {
    List<ExamTestCase> queryTestCaseByExamId(String examId);
}
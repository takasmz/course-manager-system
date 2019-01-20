package com.coursemanager.service;

import com.coursemanager.dto.ExamInfoDto;

/**
 * 描述:  exam_info 对应的Service类.<br>
 *
 * @author framework generator
 * @date 2018年11月18日
 */
public interface IExamInfoService extends BaseService {
    ExamInfoDto queryExamById(String examId, String courseExamId);

    Integer deleteExamById(String examId);
}
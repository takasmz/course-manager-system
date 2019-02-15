package com.coursemanager.service;

import com.coursemanager.dto.CourseExamInfoDto;
import com.coursemanager.dto.ExamInfoDto;
import com.coursemanager.model.ExamInfo;
import com.coursemanager.model.UserInfo;
import com.coursemanager.util.PageRequest;
import com.coursemanager.util.PageResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 描述:  course_exam_info 对应的Service类.<br>
 *
 * @author framework generator
 * @date 2018年11月18日
 */
public interface ICourseExamInfoService extends BaseService {
    PageResponse queryExamList(UserInfo user, HttpServletRequest request);

    List<ExamInfoDto> newHomeworkExample(HttpServletRequest request, UserInfo user);

    Integer createExamInfo(HttpServletRequest request, ExamInfo examInfo);

    Integer createNewHomework(HttpServletRequest request);

    List<CourseExamInfoDto> queryHistoryHomeworkList(PageRequest request);

    String downloadHistoryHomework(String courseExamId, String courseExamName, HttpServletResponse response);

    PageResponse<ExamInfoDto> editHomeworkList(HttpServletRequest request, UserInfo user);
}
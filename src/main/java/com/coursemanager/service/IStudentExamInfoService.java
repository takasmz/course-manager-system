package com.coursemanager.service;

import com.coursemanager.dto.StudentExamInfoDto;
import com.coursemanager.model.StudentExamInfo;
import com.coursemanager.model.UserInfo;
import com.coursemanager.util.common.AjaxResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 描述:  student_exam_info 对应的Service类.<br>
 *
 * @author framework generator
 * @date 2018年11月18日
 */
public interface IStudentExamInfoService extends BaseService {

	String checkCode(String code, UserInfo user, String examId);

    Map<String, Object> uploadFile(HttpServletRequest request,UserInfo user);

    int submitHomework(HttpServletRequest request, UserInfo user);

    Map<String,Object> getUploadedFile(HttpServletRequest request, UserInfo user);

    AjaxResponse deleteFile(String pathName, String examId ,boolean existed, UserInfo user);

    List<StudentExamInfoDto> getRecordList(UserInfo user, HttpServletRequest request);

    List<StudentExamInfoDto> getStudentRecord(UserInfo userInfo, HttpServletRequest request);

    List<StudentExamInfoDto> queryHomeworkRecordDetail(String courseExamId, String studentId);
}
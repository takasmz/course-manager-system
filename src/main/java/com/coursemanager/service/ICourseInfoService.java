package com.coursemanager.service;

import com.coursemanager.mapper.CourseInfoMapper;
import com.coursemanager.model.CourseInfo;
import com.coursemanager.model.UserInfo;
import com.coursemanager.util.common.AjaxResponse;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 描述:  course_info 对应的Service类.<br>
 *
 * @author framework generator
 * @date 2018年12月19日
 */
public interface ICourseInfoService extends BaseService{

    List<CourseInfo> getCourseList(UserInfo user);

    List<CourseInfo> queryCourseList(UserInfo user);

    String getCoursePathByCourseId(String courseId);

    AjaxResponse saveIntro(String md, String courseId, String type);
}

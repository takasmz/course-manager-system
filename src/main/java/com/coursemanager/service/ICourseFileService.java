package com.coursemanager.service;

import com.coursemanager.dto.CourseFileDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 描述:  course_file 对应的Service接口.<br>
 *
 * @author framework generator
 * @date 2019年01月15日
 */
public interface ICourseFileService extends BaseService {
    List<CourseFileDto> queryResourceList(HttpServletRequest request);
}
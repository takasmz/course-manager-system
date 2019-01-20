package com.coursemanager.mapper;

import com.coursemanager.dto.CourseInfoDto;
import com.coursemanager.model.CourseInfo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface CourseInfoMapper extends Mapper<CourseInfo> {

    List<CourseInfo> getCourseListByStudent(String studentId);

    List<CourseInfo> getCourseListByTeacher(String teacherId);

    CourseInfoDto getCourseByexamId(String examId);
}
package com.coursemanager.dto;

import com.coursemanager.model.CourseExamInfo;

/**
 * 描述:  course_exam_info 对应的Dto类.<br>
 *
 * @author framework generator
 * @date 2018年11月18日
 */
public class CourseExamInfoDto extends CourseExamInfo {

    private String courseExamName;

    private String courseName;

    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCourseExamName() {
        return courseExamName;
    }

    public void setCourseExamName(String courseExamName) {
        this.courseExamName = courseExamName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
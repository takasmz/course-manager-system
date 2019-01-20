package com.coursemanager.dto;

import com.coursemanager.model.CourseInfo;

public class CourseInfoDto extends CourseInfo {

    private Integer examNumber;

    public Integer getExamNumber() {
        return examNumber;
    }

    public void setExamNumber(Integer examNumber) {
        this.examNumber = examNumber;
    }
}
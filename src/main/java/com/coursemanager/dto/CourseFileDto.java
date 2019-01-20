package com.coursemanager.dto;

import com.coursemanager.model.CourseFile;

public class CourseFileDto extends CourseFile {
    private String icon;

    private String courseName;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
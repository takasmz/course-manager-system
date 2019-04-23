package com.coursemanager.dto;

public class StudentSubmitStatusDto {

    private Integer total;

    private Integer finished;

    private Integer submitStatus;

    private String studentId;

    private Integer courseExamId;

    private boolean detection;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getFinished() {
        return finished;
    }

    public void setFinished(Integer finished) {
        this.finished = finished;
    }

    public Integer getSubmitStatus() {
        return submitStatus;
    }

    public void setSubmitStatus(Integer submitStatus) {
        this.submitStatus = submitStatus;
    }

    public Integer getCourseExamId() {
        return courseExamId;
    }

    public void setCourseExamId(Integer courseExamId) {
        this.courseExamId = courseExamId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public boolean isDetection() {
        return detection;
    }

    public void setDetection(boolean detection) {
        this.detection = detection;
    }
}

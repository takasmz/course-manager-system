package com.coursemanager.dto;

import com.coursemanager.model.StudentExamInfo;

import java.util.List;

/**
 * 描述:  student_exam_info 对应的Dto类.<br>
 *
 * @author framework generator
 * @date 2018年11月18日
 */
public class StudentExamInfoDto extends StudentExamInfo {

    private String examTitle;

    private String submitTimeStr;

    private Integer total;

    private Integer finished;

    private Integer submitStatus;

    private String studentName;

    private Integer courseExamId;

    private List<StudentSubmitStatusDto> statusDtoList;

    private boolean detection;

    public String getExamTitle() {
        return examTitle;
    }

    public void setExamTitle(String examTitle) {
        this.examTitle = examTitle;
    }

    public String getSubmitTimeStr() {
        return submitTimeStr;
    }

    public void setSubmitTimeStr(String submitTimeStr) {
        this.submitTimeStr = submitTimeStr;
    }

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


    public List<StudentSubmitStatusDto> getStatusDtoList() {
        return statusDtoList;
    }

    public void setStatusDtoList(List<StudentSubmitStatusDto> statusDtoList) {
        this.statusDtoList = statusDtoList;
    }

    public Integer getSubmitStatus() {
        return submitStatus;
    }

    public void setSubmitStatus(Integer submitStatus) {
        this.submitStatus = submitStatus;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Integer getCourseExamId() {
        return courseExamId;
    }

    public void setCourseExamId(Integer courseExamId) {
        this.courseExamId = courseExamId;
    }

    public boolean isDetection() {
        return detection;
    }

    public void setDetection(boolean detection) {
        this.detection = detection;
    }
}
package com.coursemanager.model;

import com.coursemanager.dto.StudentExamInfoDto;

import java.util.Date;
import javax.persistence.*;

@Table(name = "student_exam_info")
public class StudentExamInfo {
    @Id
    private Integer id;

    /**
     * 学生id
     */
    @Column(name = "student_id")
    private String studentId;

    /**
     * 课程作业id
     */
    @Column(name = "exam_id")
    private String examId;

    /**
     * 提交种类(0:代码;1:文件)
     */
    @Column(name = "submit_type")
    private Integer submitType;

    /**
     * 代码内容 或 文件路径
     */
    @Column(name = "submit_content")
    private String submitContent;

    /**
     * 提交结果
     */
    private String result;

    /**
     * 错误内容
     */
    private String error;

    /**
     * 提交状态（0正常，1迟交，2未交，）
     */
    private Integer status;

    /**
     * 提交时间
     */
    @Column(name = "submit_time")
    private Date submitTime;

    /**
     * 正确的测试用例个数
     */
    private Integer totalCorrect;

    /**
     * 所有测试用例个数
     */
    private Integer totalTestcases;

    private static final long serialVersionUID = 1L;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取学生id
     *
     * @return student_id - 学生id
     */
    public String getStudentId() {
        return studentId;
    }

    /**
     * 设置学生id
     *
     * @param studentId 学生id
     */
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    /**
     * 获取课程作业id
     *
     * @return exam_id - 课程作业id
     */
    public String getExamId() {
        return examId;
    }

    /**
     * 设置课程作业id
     *
     * @param examId 课程作业id
     */
    public void setExamId(String examId) {
        this.examId = examId;
    }

    /**
     * 获取提交种类(0:代码;1:文件)
     *
     * @return submit_type - 提交种类(0:代码;1:文件)
     */
    public Integer getSubmitType() {
        return submitType;
    }

    /**
     * 设置提交种类(0:代码;1:文件)
     *
     * @param submitType 提交种类(0:代码;1:文件)
     */
    public void setSubmitType(Integer submitType) {
        this.submitType = submitType;
    }

    /**
     * 获取代码内容 或 文件路径
     *
     * @return submit_content - 代码内容 或 文件路径
     */
    public String getSubmitContent() {
        return submitContent;
    }

    /**
     * 设置代码内容 或 文件路径
     *
     * @param submitContent 代码内容 或 文件路径
     */
    public void setSubmitContent(String submitContent) {
        this.submitContent = submitContent;
    }

    /**
     * 获取提交结果
     *
     * @return result - 提交结果
     */
    public String getResult() {
        return result;
    }

    /**
     * 设置提交结果
     *
     * @param result 提交结果
     */
    public void setResult(String result) {
        this.result = result;
    }

    /**
     * 获取错误内容
     *
     * @return error - 错误内容
     */
    public String getError() {
        return error;
    }

    /**
     * 设置错误内容
     *
     * @param error 错误内容
     */
    public void setError(String error) {
        this.error = error;
    }

    /**
     * 获取提交状态（0正常，1迟交，2未交，）
     *
     * @return status - 提交状态（0正常，1迟交，2未交，）
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置提交状态（0正常，1迟交，2未交，）
     *
     * @param status 提交状态（0正常，1迟交，2未交，）
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取提交时间
     *
     * @return submit_time - 提交时间
     */
    public Date getSubmitTime() {
        return submitTime;
    }

    /**
     * 设置提交时间
     *
     * @param submitTime 提交时间
     */
    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public Integer getTotalCorrect() {
        return totalCorrect;
    }

    public void setTotalCorrect(Integer totalCorrect) {
        this.totalCorrect = totalCorrect;
    }

    public Integer getTotalTestcases() {
        return totalTestcases;
    }

    public void setTotalTestcases(Integer totalTestcases) {
        this.totalTestcases = totalTestcases;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}
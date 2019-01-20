/**
 * Copyright© 2018 LRH, All Rights Reserved. <br/>
 */
package com.coursemanager.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 * 描述:  exam_submit_record 对应的实体类.<br>
 * WARNING：不是表中字段的属性必须加@Transient注解
 * @author framework generator
 * @date 2018年12月15日
 */
@Table(name = "exam_submit_record")
public class ExamSubmitRecord implements Serializable {
    @Id
    @Column(name = "exam_record_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator="SELECT LAST_INSERT_ID()")
    private Integer examRecordId;

    /**
     * 作业id
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
     * 学生标识
     */
    @Column(name = "student_id")
    private String studentId;

    /**
     * 提交时间
     */
    @Column(name = "submit_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date submitTime;

    private static final long serialVersionUID = 1L;

    /**
     * @return exam_record_id
     */
    public Integer getExamRecordId() {
        return examRecordId;
    }

    /**
     * @param examRecordId
     */
    public void setExamRecordId(Integer examRecordId) {
        this.examRecordId = examRecordId;
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
     * 获取学生标识
     *
     * @return student_id - 学生标识
     */
    public String getStudentId() {
        return studentId;
    }

    /**
     * 设置学生标识
     *
     * @param studentId 学生标识
     */
    public void setStudentId(String studentId) {
        this.studentId = studentId;
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

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }
}
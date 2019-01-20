/**
 * Copyright© 2018 LRH, All Rights Reserved. <br/>
 */
package com.coursemanager.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import javax.persistence.*;

/**
 * 描述:  exam_info 对应的实体类.<br>
 * WARNING：不是表中字段的属性必须加@Transient注解
 * @author framework generator
 * @date 2018年12月04日
 */
@Table(name = "exam_info")
public class ExamInfo implements Serializable {
    /**
     * 问题id
     */
    @Id
    @Column(name = "exam_id")
    private String examId;

    /**
     * 问题标题
     */
    @Column(name = "exam_title")
    private String examTitle;

    /**
     * 问题正文
     */
    @Column(name = "exam_content")
    private String examContent;

    /**
     * 提交方式（0程序，1文档）
     */
    @Column(name = "submit_type")
    private Integer submitType;

    /**
     * 分值（缺省均分）
     */
    @Column(name = "number")
    private Integer number;

    /**
     * 验证方式（0人工，1程序）
     */
    @Column(name = "identify_type")
    private Integer identifyType;

    /**
     * 答案
     */
    @Column(name = "answer")
    private String answer;

    /**
     * 状态
     */
    @Column(name = "status")
    private Integer status;

    /**
     * 输入
     */
    @Column(name = "inputs")
    private String inputs;

    /**
     * 课程作业id
     */
    @Column(name = "course_exam_id")
    private Integer courseExamId;

    private static final long serialVersionUID = 1L;

    /**
     * 获取问题id
     *
     * @return exam_id - 问题id
     */
    public String getExamId() {
        return examId;
    }

    /**
     * 设置问题id
     *
     * @param examId 问题id
     */
    public void setExamId(String examId) {
        this.examId = examId;
    }

    /**
     * 获取问题标题
     *
     * @return exam_title - 问题标题
     */
    public String getExamTitle() {
        return examTitle;
    }

    /**
     * 设置问题标题
     *
     * @param examTitle 问题标题
     */
    public void setExamTitle(String examTitle) {
        this.examTitle = examTitle;
    }

    /**
     * 获取问题正文
     *
     * @return exam_content - 问题正文
     */
    public String getExamContent() {
        return examContent;
    }

    /**
     * 设置问题正文
     *
     * @param examContent 问题正文
     */
    public void setExamContent(String examContent) {
        this.examContent = examContent;
    }

    /**
     * 获取提交方式（0程序，1文档）
     *
     * @return submit_type - 提交方式（0程序，1文档）
     */
    public Integer getSubmitType() {
        return submitType;
    }

    /**
     * 设置提交方式（0程序，1文档）
     *
     * @param submitType 提交方式（0程序，1文档）
     */
    public void setSubmitType(Integer submitType) {
        this.submitType = submitType;
    }

    /**
     * 获取分值（缺省均分）
     *
     * @return number - 分值（缺省均分）
     */
    public Integer getNumber() {
        return number;
    }

    /**
     * 设置分值（缺省均分）
     *
     * @param number 分值（缺省均分）
     */
    public void setNumber(Integer number) {
        this.number = number;
    }

    /**
     * 获取验证方式（0人工，1程序）
     *
     * @return identify_type - 验证方式（0人工，1程序）
     */
    public Integer getIdentifyType() {
        return identifyType;
    }

    /**
     * 设置验证方式（0人工，1程序）
     *
     * @param identifyType 验证方式（0人工，1程序）
     */
    public void setIdentifyType(Integer identifyType) {
        this.identifyType = identifyType;
    }

    /**
     * 获取答案
     *
     * @return answer - 答案
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * 设置答案
     *
     * @param answer 答案
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     * 获取状态
     *
     * @return status - 状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态
     *
     * @param status 状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取输入
     *
     * @return inputs - 输入
     */
    public String getInputs() {
        return inputs;
    }

    /**
     * 设置输入
     *
     * @param inputs 输入
     */
    public void setInputs(String inputs) {
        this.inputs = inputs;
    }

    public Integer getCourseExamId() {
        return courseExamId;
    }

    public void setCourseExamId(Integer courseExamId) {
        this.courseExamId = courseExamId;
    }

    @JsonIgnoreProperties
    public String queryIdentifyTypeName(){
        switch (this.identifyType){
            case -1:return "见下拉框";
            case 0:return "人工验证";
            case 1:return "程序验证";
            default:return "";
        }
    }

    @JsonIgnoreProperties
    public String querySubmitTypeName(){
        switch (this.submitType){
            case -1:return "见下拉框";
            case 0:return "程序代码";
            case 1:return "文档";
            default:return "";
        }
    }
}
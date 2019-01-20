/**
 * Copyright© 2018 LRH, All Rights Reserved. <br/>
 */
package com.coursemanager.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import javax.persistence.*;

/**
 * 描述:  teacher_info 对应的实体类.<br>
 * WARNING：不是表中字段的属性必须加@Transient注解
 * @author framework generator
 * @date 2018年12月04日
 */
@Table(name = "teacher_info")
public class TeacherInfo implements Serializable {

    /**
     * 教师id
     */
    @Id
    @Column(name = "teacher_id")
    private String teacherId;

    /**
     * 账号（缺省为工号）
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 工号
     */
    @Column(name = "teacher_number")
    private String teacherNumber;

    /**
     * 密码
     */
    @Column(name = "password")
    private String password;

    /**
     * 教师姓名
     */
    @Column(name = "teacher_name")
    private String teacherName;

    /**
     * 性别
     */
    @Column(name = "sex")
    private String sex;

    /**
     * 简历url
     */
    @Column(name = "resume_url")
    private String resumeUrl;

    /**
     * 状态
     */
    @Column(name = "status")
    private Integer status;

    private static final long serialVersionUID = 1L;

    /**
     * 获取教师id
     *
     * @return teacher_id - 教师id
     */
    public String getTeacherId() {
        return teacherId;
    }

    /**
     * 设置教师id
     *
     * @param teacherId 教师id
     */
    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    /**
     * 获取账号（缺省为工号）
     *
     * @return user_name - 账号（缺省为工号）
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置账号（缺省为工号）
     *
     * @param userName 账号（缺省为工号）
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 获取工号
     *
     * @return teacher_number - 工号
     */
    public String getTeacherNumber() {
        return teacherNumber;
    }

    /**
     * 设置工号
     *
     * @param teacherNumber 工号
     */
    public void setTeacherNumber(String teacherNumber) {
        this.teacherNumber = teacherNumber;
    }

    /**
     * 获取密码
     *
     * @return password - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取教师姓名
     *
     * @return teacher_name - 教师姓名
     */
    public String getTeacherName() {
        return teacherName;
    }

    /**
     * 设置教师姓名
     *
     * @param teacherName 教师姓名
     */
    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    /**
     * 获取性别
     *
     * @return sex - 性别
     */
    public String getSex() {
        return sex;
    }

    /**
     * 设置性别
     *
     * @param sex 性别
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * 获取简历url
     *
     * @return resume_url - 简历url
     */
    public String getResumeUrl() {
        return resumeUrl;
    }

    /**
     * 设置简历url
     *
     * @param resumeUrl 简历url
     */
    public void setResumeUrl(String resumeUrl) {
        this.resumeUrl = resumeUrl;
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
}
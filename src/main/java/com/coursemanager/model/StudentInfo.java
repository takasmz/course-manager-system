/**
 * Copyright© 2018 LRH, All Rights Reserved. <br/>
 */
package com.coursemanager.model;

import com.coursemanager.dto.RegisterDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.UUID;

import javax.persistence.*;

/**
 * 描述:  student_info 对应的实体类.<br>
 * WARNING：不是表中字段的属性必须加@Transient注解
 * @author framework generator
 * @date 2018年12月04日
 */
@Table(name = "student_info")
public class StudentInfo implements Serializable {

    /**
     * 学生id
     */
    @Id
    @Column(name = "student_id")
    private String studentId;

    /**
     * 用户名（缺省为学号）
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 密码
     */
    @Column(name = "password")
    private String password;

    /**
     * 学生姓名
     */
    @Column(name = "student_name")
    private String studentName;

    /**
     * 性别（0：男；1：女）
     */
    @Column(name = "sex")
    private String sex;

    /**
     * 学号
     */
    @Column(name = "student_number")
    private String studentNumber;

    /**
     * 年级
     */
    @Column(name = "grade")
    private String grade;

    /**
     * 专业
     */
    @Column(name = "major")
    private String major;

    /**
     * 班级
     */
    @Column(name = "class_name")
    private String className;

    /**
     * 手机号码
     */
    @Column(name = "mobile")
    private String mobile;

    /**
     * 邮箱
     */
    @Column(name = "mail")
    private String mail;

    /**
     *  学校
     */
    @Column(name = "school")
    private String school;

    /**
     * 学院
     */
    @Column(name = "college")
    private String college;

    /**
     * 系
     */
    @Column(name = "department")
    private String department;

    /**
     * 状态
     */
    @Column(name = "status")
    private Integer status;

    private static final long serialVersionUID = 1L;

    public StudentInfo(RegisterDto registerDto) {
		this.college = "理学院";
		this.school = "浙江理工大学";
		this.status = 1;
		this.studentId = registerDto.getUsername();
		this.studentNumber = registerDto.getUsername();
		this.studentId = UUID.randomUUID().toString().replaceAll("-", "") + "0";
		this.password = registerDto.getPassword();
		this.mail = registerDto.getEmail();
		this.studentName = registerDto.getNickname();
	}

	public StudentInfo() {}

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
     * 获取用户名（缺省为学号）
     *
     * @return user_name - 用户名（缺省为学号）
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置用户名（缺省为学号）
     *
     * @param userName 用户名（缺省为学号）
     */
    public void setUserName(String userName) {
        this.userName = userName;
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
     * 获取学生姓名
     *
     * @return student_name - 学生姓名
     */
    public String getStudentName() {
        return studentName;
    }

    /**
     * 设置学生姓名
     *
     * @param studentName 学生姓名
     */
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    /**
     * 获取性别（0：男；1：女）
     *
     * @return sex - 性别（0：男；1：女）
     */
    public String getSex() {
        return sex;
    }

    /**
     * 设置性别（0：男；1：女）
     *
     * @param sex 性别（0：男；1：女）
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * 获取学号
     *
     * @return student_number - 学号
     */
    public String getStudentNumber() {
        return studentNumber;
    }

    /**
     * 设置学号
     *
     * @param studentNumber 学号
     */
    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    /**
     * 获取年级
     *
     * @return grade - 年级
     */
    public String getGrade() {
        return grade;
    }

    /**
     * 设置年级
     *
     * @param grade 年级
     */
    public void setGrade(String grade) {
        this.grade = grade;
    }

    /**
     * 获取专业
     *
     * @return major - 专业
     */
    public String getMajor() {
        return major;
    }

    /**
     * 设置专业
     *
     * @param major 专业
     */
    public void setMajor(String major) {
        this.major = major;
    }

    /**
     * 获取班级
     *
     * @return class_name - 班级
     */
    public String getClassName() {
        return className;
    }

    /**
     * 设置班级
     *
     * @param className 班级
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * 获取手机号码
     *
     * @return mobile - 手机号码
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 设置手机号码
     *
     * @param mobile 手机号码
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 获取邮箱
     *
     * @return mail - 邮箱
     */
    public String getMail() {
        return mail;
    }

    /**
     * 设置邮箱
     *
     * @param mail 邮箱
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * 获取 学校
     *
     * @return school -  学校
     */
    public String getSchool() {
        return school;
    }

    /**
     * 设置 学校
     *
     * @param school  学校
     */
    public void setSchool(String school) {
        this.school = school;
    }

    /**
     * 获取学院
     *
     * @return college - 学院
     */
    public String getCollege() {
        return college;
    }

    /**
     * 设置学院
     *
     * @param college 学院
     */
    public void setCollege(String college) {
        this.college = college;
    }

    /**
     * 获取系
     *
     * @return department - 系
     */
    public String getDepartment() {
        return department;
    }

    /**
     * 设置系
     *
     * @param department 系
     */
    public void setDepartment(String department) {
        this.department = department;
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
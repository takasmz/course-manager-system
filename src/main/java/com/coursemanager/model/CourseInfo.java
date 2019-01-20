package com.coursemanager.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import javax.persistence.*;
import tk.mybatis.mapper.annotation.RegisterMapper;

@Table(name = "course_info")
public class CourseInfo {
    @Id
    private Integer id;

    /**
     * 课程名称
     */
    @Column(name = "course_name")
    private String courseName;

    /**
     * 课程资源路径
     */
    @Column(name = "course_path")
    private String coursePath;

    /**
     * 教师账号
     */
    @Column(name = "teacher_account")
    private String teacherAccount;

    /**
     * 学年
     */
    private Integer year;

    /**
     * 学期
     */
    private Integer term;

    /**
     * 状态
     */
    private Integer status;

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
     * 获取课程名称
     *
     * @return course_name - 课程名称
     */
    public String getCourseName() {
        return courseName;
    }

    /**
     * 设置课程名称
     *
     * @param courseName 课程名称
     */
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    /**
     * 获取课程资源路径
     *
     * @return course_path - 课程资源路径
     */
    public String getCoursePath() {
        return coursePath;
    }

    /**
     * 设置课程资源路径
     *
     * @param coursePath 课程资源路径
     */
    public void setCoursePath(String coursePath) {
        this.coursePath = coursePath;
    }

    /**
     * 获取教师账号
     *
     * @return teacher_account - 教师账号
     */
    public String getTeacherAccount() {
        return teacherAccount;
    }

    /**
     * 设置教师账号
     *
     * @param teacherAccount 教师账号
     */
    public void setTeacherAccount(String teacherAccount) {
        this.teacherAccount = teacherAccount;
    }

    /**
     * 获取学年
     *
     * @return year - 学年
     */
    public Integer getYear() {
        return year;
    }

    /**
     * 设置学年
     *
     * @param year 学年
     */
    public void setYear(Integer year) {
        this.year = year;
    }

    /**
     * 获取学期
     *
     * @return term - 学期
     */
    public Integer getTerm() {
        return term;
    }

    /**
     * 设置学期
     *
     * @param term 学期
     */
    public void setTerm(Integer term) {
        this.term = term;
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
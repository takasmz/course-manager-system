/**
 * Copyright© 2018 LRH, All Rights Reserved. <br/>
 */
package com.coursemanager.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 * 描述:  course_exam_info 对应的实体类.<br>
 * WARNING：不是表中字段的属性必须加@Transient注解
 * @author framework generator
 * @date 2018年12月04日
 */
@Table(name = "course_exam_info")
public class CourseExamInfo implements Serializable {
    /**
     * id
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator="SELECT LAST_INSERT_ID()")
    private Integer id;

    /**
     * 教师id
     */
    @Column(name = "teacher_id")
    private String teacherId;

    /**
     * 课程id
     */
    @Column(name = "course_id")
    private String courseId;


    /**
     * 状态（0:可提交，1:已完成，2:取消，3:创建中）
     */
    @Column(name = "status")
    private Integer status;

    /**
     * 课程作业次数
     */
    @Column(name = "exam_number")
    private Integer examNumber;

    @Column(name = "show_answer_time")
    private Date showAnswerTime;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    /**
     * 过期时间
     */
    @Column(name = "expire_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date expireTime;

    private static final long serialVersionUID = 1L;

    /**
     * 获取id
     *
     * @return id - id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置id
     *
     * @param id id
     */
    public void setId(Integer id) {
        this.id = id;
    }

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
     * 获取课程id
     *
     * @return course_id - 课程id
     */
    public String getCourseId() {
        return courseId;
    }

    /**
     * 设置课程id
     *
     * @param courseId 课程id
     */
    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }


    /**
     * 获取状态（0:可提交，1:已完成，2:取消，3:创建中）
     *
     * @return status - 状态（可提交，已完成，取消等）
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态（0:可提交，1:已完成，2:取消，3:创建中）
     *
     * @param status 状态（可提交，已完成，取消等）
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取过期时间
     *
     * @return expire_time - 过期时间
     */
    public Date getExpireTime() {
        return expireTime;
    }

    /**
     * 设置过期时间
     *
     * @param expireTime 过期时间
     */
    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public Integer getExamNumber() {
        return examNumber;
    }

    public void setExamNumber(Integer examNumber) {
        this.examNumber = examNumber;
    }

    public Date getShowAnswerTime() {
        return showAnswerTime;
    }

    public void setShowAnswerTime(Date showAnswerTime) {
        this.showAnswerTime = showAnswerTime;
    }
}
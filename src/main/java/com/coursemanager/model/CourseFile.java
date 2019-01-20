package com.coursemanager.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import tk.mybatis.mapper.annotation.RegisterMapper;

@Table(name = "course_file")
public class CourseFile {
    /**
     * 课程附件id
     */
    @Id
    private Integer id;

    /**
     * 课程id
     */
    @Column(name = "course_id")
    private Integer courseId;

    /**
     * 课程路径
     */
    @Column(name = "course_path")
    private String coursePath;

    /**
     * 文件描述
     */
    private String description;

    /**
     * 文件名称
     */
    @Column(name = "file_name")
    private String fileName;

    /**
     * 文件类型(1:课件;2:音频3:习题)
     */
    @Column(name = "file_type")
    private Integer fileType;

    /**
     * 状态(1:正常;2:已删除)
     */
    private Integer status;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    private static final long serialVersionUID = 1L;

    /**
     * 获取课程附件id
     *
     * @return id - 课程附件id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置课程附件id
     *
     * @param id 课程附件id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取课程id
     *
     * @return course_id - 课程id
     */
    public Integer getCourseId() {
        return courseId;
    }

    /**
     * 设置课程id
     *
     * @param courseId 课程id
     */
    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    /**
     * 获取课程路径
     *
     * @return course_path - 课程路径
     */
    public String getCoursePath() {
        return coursePath;
    }

    /**
     * 设置课程路径
     *
     * @param coursePath 课程路径
     */
    public void setCoursePath(String coursePath) {
        this.coursePath = coursePath;
    }

    /**
     * 获取文件描述
     *
     * @return description - 文件描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置文件描述
     *
     * @param description 文件描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取文件名称
     *
     * @return file_name - 文件名称
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * 设置文件名称
     *
     * @param fileName 文件名称
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * 获取文件类型(1:课件;2:音频3:习题)
     *
     * @return file_type - 文件类型(1:课件;2:音频3:习题)
     */
    public Integer getFileType() {
        return fileType;
    }

    /**
     * 设置文件类型(1:课件;2:音频3:习题)
     *
     * @param fileType 文件类型(1:课件;2:音频3:习题)
     */
    public void setFileType(Integer fileType) {
        this.fileType = fileType;
    }

    /**
     * 获取状态(1:正常;2:已删除)
     *
     * @return status - 状态(1:正常;2:已删除)
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态(1:正常;2:已删除)
     *
     * @param status 状态(1:正常;2:已删除)
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取创建人
     *
     * @return creator - 创建人
     */
    public String getCreator() {
        return creator;
    }

    /**
     * 设置创建人
     *
     * @param creator 创建人
     */
    public void setCreator(String creator) {
        this.creator = creator;
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
}
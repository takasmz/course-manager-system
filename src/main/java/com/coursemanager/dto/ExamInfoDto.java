package com.coursemanager.dto;

import com.coursemanager.model.ExamInfo;

import java.io.Serializable;
import java.util.Date;

/**
 * 描述:  exam_info 对应的Dto类.<br>
 *
 * @author framework generator
 * @date 2018年11月18日
 */
public class ExamInfoDto extends ExamInfo implements Serializable {

    private Integer id;

    private String name;

    private String expireTime;

    private Date showAnswerTime;

    private String identifyName;

    private String submitName;

    private Integer pid;

    private Integer total;

    private Integer finished;

    private String filePath;

    private String answerPath;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public String getIdentifyName() {
        return identifyName;
    }

    public void setIdentifyName(String identifyName) {
        this.identifyName = identifyName;
    }

    public String getSubmitName() {
        return submitName;
    }

    public void setSubmitName(String submitName) {
        this.submitName = submitName;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getFinished() {
        return finished;
    }

    public void setFinished(Integer finished) {
        this.finished = finished;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getAnswerPath() {
        return answerPath;
    }

    public void setAnswerPath(String answerPath) {
        this.answerPath = answerPath;
    }

    public Date getShowAnswerTime() {
        return showAnswerTime;
    }

    public void setShowAnswerTime(Date showAnswerTime) {
        this.showAnswerTime = showAnswerTime;
    }
}
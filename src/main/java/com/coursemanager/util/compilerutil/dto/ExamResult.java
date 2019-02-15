package com.coursemanager.util.compilerutil.dto;

/**
 * @Author 李如豪
 * @Date 2019/2/13 9:33
 * @VERSION 1.0
 **/
public class ExamResult {
    private String examId;
    private long useMemory;
    private String error;
    private long useTime;
    private String status;
    private boolean normal;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public long getUseTime() {
        return useTime;
    }

    public void setUseTime(long useTime) {
        this.useTime = useTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isNormal() {
        return normal;
    }

    public void setNormal(boolean normal) {
        this.normal = normal;
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public long getUseMemory() {
        return useMemory;
    }

    public void setUseMemory(long useMemory) {
        this.useMemory = useMemory;
    }
}

package com.coursemanager.util.compilerutil.dto;

import java.lang.reflect.Method;

/**
 * @Author 李如豪
 * @Date 2019/2/1 10:33
 * @VERSION 1.0
 **/
public class ExamResultDto{
    private Method method;
    private Object instance;
    private String error;
    private long useTime;
    private String status;
    private boolean normal;

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object getInstance() {
        return instance;
    }

    public void setInstance(Object instance) {
        this.instance = instance;
    }

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
}

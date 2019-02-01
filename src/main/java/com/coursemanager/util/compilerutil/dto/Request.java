package com.coursemanager.util.compilerutil.dto;

/**
 * @Author 李如豪
 * @Date 2019/2/1 11:15
 * @VERSION 1.0
 **/
public class Request {
    private String command;
    private String data;
    private String signalId;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getSignalId() {
        return signalId;
    }

    public void setSignalId(String signalId) {
        this.signalId = signalId;
    }
}

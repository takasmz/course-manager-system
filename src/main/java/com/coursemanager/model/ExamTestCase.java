package com.coursemanager.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import javax.persistence.*;
import tk.mybatis.mapper.annotation.RegisterMapper;

@Table(name = "exam_test_case")
public class ExamTestCase {
    @Id
    private Integer id;

    /**
     * 题目id
     */
    @Column(name = "exam_id")
    private Integer examId;

    /**
     * 一次输入
     */
    private byte[] input;

    /**
     * 一次输出
     */
    private byte[] output;

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
     * 获取题目id
     *
     * @return exam_id - 题目id
     */
    public Integer getExamId() {
        return examId;
    }

    /**
     * 设置题目id
     *
     * @param examId 题目id
     */
    public void setExamId(Integer examId) {
        this.examId = examId;
    }

    /**
     * 获取一次输入
     *
     * @return input - 一次输入
     */
    public byte[] getInput() {
        return input;
    }

    /**
     * 设置一次输入
     *
     * @param input 一次输入
     */
    public void setInput(byte[] input) {
        this.input = input;
    }

    /**
     * 获取一次输出
     *
     * @return output - 一次输出
     */
    public byte[] getOutput() {
        return output;
    }

    /**
     * 设置一次输出
     *
     * @param output 一次输出
     */
    public void setOutput(byte[] output) {
        this.output = output;
    }
}
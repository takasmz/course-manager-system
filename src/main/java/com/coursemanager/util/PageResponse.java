package com.coursemanager.util;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;

public class PageResponse<T> implements Serializable {
    private static final long serialVersionUID = -5584061128249495676L;
    /**
     * 返回状态码
     */
    private int code;
    /**
     * 请求次数计数器
     */
    private int draw;
    /**
     * 总记录数
     */
    private long total;
    /**
     * 过滤后的记录数
     */
    private long recordsFiltered;
    /**
     * 返回的分页数据对象
     */
    private List<T> rows = new ArrayList<>();
    /**
     * 错误提示信息
     */
    private String error;

    /**
     * 通过分页对象构建DataTables分页结果
     *
     * @param rows
     */
    public PageResponse(List<T> rows) {
        if (rows instanceof Page) {
            Page<T> page = (Page<T>) rows;
            this.total = page.getTotal();
            this.recordsFiltered = page.getTotal();
            this.rows = rows;
            this.code = 0;
        }
    }

    /**
     * 通过分页对象构建DataTables分页结果
     *
     * @param rows  分页数据
     * @param total 总记录数
     */
    public PageResponse(List<T> rows, long total, PageRequest request) {
        this.total = total;
        this.recordsFiltered = total;
        if (rows != null) {
            this.rows = rows;
        }
//        if (request != null) {
//            this.draw = request.getDraw();
//        }
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(long recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}

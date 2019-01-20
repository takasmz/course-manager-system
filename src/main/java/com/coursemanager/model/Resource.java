/**
 * Copyright© 2018 LRH, All Rights Reserved. <br/>
 */
package com.coursemanager.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import javax.persistence.*;

/**
 * 描述:  resource 对应的实体类.<br>
 * WARNING：不是表中字段的属性必须加@Transient注解
 * @author framework generator
 * @date 2018年12月04日
 */
@Table(name = "resource")
public class Resource implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator="SELECT LAST_INSERT_ID()")
    private Integer id;

    /**
     * 菜单en
     */
    @Column(name = "name")
    private String name;

    /**
     * 菜单cn
     */
    @Column(name = "title")
    private String title;

    /**
     * url
     */
    @Column(name = "jump")
    private String jump;

    /**
     * 图标
     */
    @Column(name = "icon")
    private String icon;

    /**
     * 状态
     */
    @Column(name = "status")
    private Integer status;

    /**
     * 父菜单id
     */
    @Column(name = "parent")
    private Integer parent;

    /**
     * 是否是父菜单
     */
    @Column(name = "is_parent")
    private Integer isParent;

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
     * 获取菜单en
     *
     * @return name - 菜单en
     */
    public String getName() {
        return name;
    }

    /**
     * 设置菜单en
     *
     * @param name 菜单en
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取菜单cn
     *
     * @return title - 菜单cn
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置菜单cn
     *
     * @param title 菜单cn
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取url
     *
     * @return jump - url
     */
    public String getJump() {
        return jump;
    }

    /**
     * 设置url
     *
     * @param jump url
     */
    public void setJump(String jump) {
        this.jump = jump;
    }

    /**
     * 获取图标
     *
     * @return icon - 图标
     */
    public String getIcon() {
        return icon;
    }

    /**
     * 设置图标
     *
     * @param icon 图标
     */
    public void setIcon(String icon) {
        this.icon = icon;
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

    /**
     * 获取父菜单id
     *
     * @return parent - 父菜单id
     */
    public Integer getParent() {
        return parent;
    }

    /**
     * 设置父菜单id
     *
     * @param parent 父菜单id
     */
    public void setParent(Integer parent) {
        this.parent = parent;
    }

    /**
     * 获取是否是父菜单
     *
     * @return isParent - 是否是父菜单
     */
    public Integer getIsParent() {
        return isParent;
    }

    /**
     * 设置是否是父菜单
     *
     * @param isparent 是否是父菜单
     */
    public void setIsParent(Integer isParent) {
        this.isParent = isParent;
    }
}
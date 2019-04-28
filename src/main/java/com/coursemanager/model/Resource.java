/**
 * Copyright© 2018 LRH, All Rights Reserved. <br/>
 */
package com.coursemanager.model;

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
    @Column(name = "component")
    private String component;

    /**
     * url
     */
    @Column(name = "path")
    private String path;

    /**
     * 图标
     */
    @Column(name = "iconCls")
    private String iconCls;

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
     * @return component - 菜单cn
     */
    public String getComponent() {
        return component;
    }

    /**
     * 设置菜单cn
     *
     * @param component 菜单cn
     */
    public void setComponent(String component) {
        this.component = component;
    }

    /**
     * 获取url
     *
     * @return path - url
     */
    public String getPath() {
        return path;
    }

    /**
     * 设置url
     *
     * @param path url
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * 获取图标
     *
     * @return iconCls - 图标
     */
    public String getIconCls() {
        return iconCls;
    }

    /**
     * 设置图标
     *
     * @param iconCls 图标
     */
    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
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
     * @param isParent 是否是父菜单
     */
    public void setIsParent(Integer isParent) {
        this.isParent = isParent;
    }
}
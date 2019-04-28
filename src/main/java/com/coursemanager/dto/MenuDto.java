package com.coursemanager.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.coursemanager.model.Resource;

public class MenuDto implements Serializable{
	private String name;
	private String component;
	private String path;
	private String iconCls;
	private List<MenuDto> children;
	
	public MenuDto(Resource resource) {
		this.name = resource.getName();
		this.component = resource.getComponent();
		this.iconCls = resource.getIconCls();
		this.path = resource.getPath();
		this.children = new ArrayList<>();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getComponent() {
		return component;
	}
	public void setComponent(String component) {
		this.component = component;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public List<MenuDto> getChildren() {
		return children;
	}
//	public void setChildren(List<MenuDto> children) {
//		this.children = children;
//	}

	public void addListItem(MenuDto m) {
		this.children.add(m);
	}
	
	public void setChildren(List<Resource> childList) {
		List<MenuDto> list = new ArrayList<>();
		for(Resource res : childList) {
			MenuDto m = new MenuDto(res);
			list.add(m);
		}
		this.children = list;
	}
}

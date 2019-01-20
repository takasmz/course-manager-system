package com.coursemanager.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.coursemanager.model.Resource;

public class MenuDto implements Serializable{
	private String name;
	private String title;
	private String jump;
	private String icon;
	private List<MenuDto> list;
	
	public MenuDto(Resource resource) {
		this.name = resource.getName();
		this.title = resource.getTitle();
		this.icon = resource.getIcon();
		this.jump = resource.getJump();
		this.list = new ArrayList<>();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getJump() {
		return jump;
	}
	public void setJump(String jump) {
		this.jump = jump;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public List<MenuDto> getList() {
		return list;
	}
//	public void setList(List<MenuDto> list) {
//		this.list = list;
//	}

	public void addListItem(MenuDto m) {
		this.list.add(m);
	}
	
	public void setList(List<Resource> childList) {
		List<MenuDto> list = new ArrayList<>();
		for(Resource res : childList) {
			MenuDto m = new MenuDto(res);
			list.add(m);
		}
		this.list = list;
	}
}

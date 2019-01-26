package com.coursemanager.util;

import java.util.Map;


public class PageRequest {
	private static final long serialVersionUID = -911527837717383901L;
	private int pageNumber;
	private int limit;
	private int offset;
	private String searchText;
	private String sortName;
	private String sortOrder;
	private Map<String, Object> data;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public int getPageNumber() {
		return offset/10+1;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}
}

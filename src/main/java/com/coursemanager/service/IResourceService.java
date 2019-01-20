package com.coursemanager.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.coursemanager.dto.MenuDto;

/**
 * 描述:  resource 对应的Service类.<br>
 *
 * @author framework generator
 * @date 2018年11月28日
 */
public interface IResourceService extends BaseService {

	List<MenuDto> queryMenuJson(Integer type);
}
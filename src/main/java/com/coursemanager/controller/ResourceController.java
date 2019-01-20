package com.coursemanager.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.coursemanager.dto.MenuDto;
import com.coursemanager.model.UserInfo;
import com.coursemanager.service.IResourceService;
import com.coursemanager.util.common.AjaxResponse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 描述:  resource 对应的Controller类.<br>
 *
 * @author framework generator
 * @date 2018年11月28日
 */
@Controller
@RequestMapping("/resource")
public class ResourceController extends BaseController {
    /**
     * 日志记录器
     */
    private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);

    @Autowired
    private IResourceService resourceService;
    
    @RequestMapping("/menu")
    @ResponseBody
    public AjaxResponse menuJson(HttpServletRequest request) {
    	UserInfo user = getUser(request);
    	Integer type = user.getType();
    	List<MenuDto> list = resourceService.queryMenuJson(type);
    	return AjaxResponse.success("获取数据成功", list);
    }
}
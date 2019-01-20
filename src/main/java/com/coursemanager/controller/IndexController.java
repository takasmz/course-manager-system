package com.coursemanager.controller;

import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coursemanager.util.FileUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
public class IndexController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
	
	@RequestMapping({"/index","/login"})
	public String test() {
		return "frame";
	}
	
	@RequestMapping("/views/**")
	public void getHtml(HttpServletRequest request,HttpServletResponse response) {
		String name = request.getRequestURI().substring(7);
		String params = request.getQueryString();
		Map<String,String> map = new HashMap<>();
		if(!StringUtils.isBlank(params)){
			logger.debug("[getHtml] URL存在参数："+params);
			String[] paramArray = params.split("&");
			for(String param:paramArray){
				map.put(param.split("=")[0],param.split("=")[1]);
			}
		}
		logger.info("读取模板start:"+ name);
		String dataString = readFile("/" + "templates/" + name + ".html",map);
		response.setContentType("text/json;charset=UTF-8");
		try {
			response.getWriter().write(dataString);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	@RequestMapping("/showImg/**")
	@ResponseBody
	public void showImg(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String path = request.getRequestURI().substring(9);
		path = URLDecoder.decode(path, "utf-8");
		ServletOutputStream out = null;
		FileInputStream ips = null;
		try {
			//获取图片存放路径
			String imgPath = FileUtil.TEMP_PATH + path;
			ips = new FileInputStream(new File(imgPath));
			response.setContentType("multipart/form-data");
			out = response.getOutputStream();
			//读取文件流
			int len;
			byte[] buffer = new byte[1024 * 10];
			while ((len = ips.read(buffer)) != -1){
				out.write(buffer,0,len);
			}
			out.flush();
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			out.close();
			ips.close();
		}
	}

	/**
	 * 下载文件
	 */
	@RequestMapping("/download/**")
	@ResponseBody
	public void download(HttpServletRequest request, HttpServletResponse response){
		String url = request.getRequestURI();
		String pathName = null;
		try {
			pathName = URLDecoder.decode(url.substring(url.indexOf("download")+9), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if(StringUtils.isBlank(pathName)){
			logger.debug("[download] 参数为空");
		}
		logger.debug("[download] start");
		try {
			FileUtil.download(FileUtil.TEMP_PATH +pathName, request, response);
		}catch (Exception e){
			logger.debug("[download] 文件下载失败");
		}
	}
}

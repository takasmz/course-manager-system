package com.coursemanager.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coursemanager.model.UserInfo;
import com.coursemanager.util.common.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import java.io.*;

public class BaseController {
	private static final Logger logger = LoggerFactory.getLogger(BaseController.class);
	
	UserInfo getUser(HttpServletRequest request) {
		return (UserInfo)request.getSession().getAttribute(Constant.USER);
	}

	public static String readFile(String filePath, Map<String, String> map){
		StringBuilder dataString = new StringBuilder(1000);
		InputStreamReader read = null;
		BufferedReader buf = null;
		try {
			InputStream is = BaseController.class.getResourceAsStream(filePath);
			read = new InputStreamReader(is, StandardCharsets.UTF_8);
			buf = new BufferedReader(read);
			String line;
			while ((line = buf.readLine()) != null) {
				if(map != null &&  !map.isEmpty()){
					for(String key:map.keySet()){
						if(line.contains(key)){
							line = line.replace("<"+key+">",map.get(key));
						}
					}
				}
				dataString.append(line).append("\n");
			}
		} catch (FileNotFoundException | NullPointerException e) {
			logger.error(filePath + "不存在");
		} catch (IOException e) {
			logger.error(filePath + "模板读取失败");
		} finally {
			try {
				if (read != null) {
					read.close();
				}
				if (buf != null) {
					buf.close();
				}
			} catch (IOException e) {
				logger.error(filePath + "文件关闭失败");
			}
		}
		return dataString.toString();
	}
}

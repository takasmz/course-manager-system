package com.coursemanager.util.common;

import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtils {
	public static String getExceptionMsg(Exception e, String defaultMsg) {
		String msg = null;
		if (e instanceof SQLException) {
			msg = "服务器异常，SQL语句出错！";
		} else if (e instanceof NullPointerException) {
			msg = "服务器异常，调用了未经初始化的对象或者不存在的对象！";
		} else if (e instanceof IOException) {
			msg = "服务器异常，I/O异常！";
		}  else {
			msg = e.getMessage();
		}
		return defaultMsg + msg;
	}

	public static String camelToUnderline(String param) {
		if (param == null || "".equals(param.trim())) {
			return "";
		}
		int len = param.length();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = param.charAt(i);
			if (Character.isUpperCase(c)) {
				sb.append("_");
				sb.append(Character.toLowerCase(c));
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}
	
	/**
	 * 字符串列转换成sql in的参数形式
	 * @param params
	 * @return
	 */
	public static String listToSqlStr(List<String> params) {
		if(params == null || params.isEmpty()) {
			return null;
		} else {
			StringBuilder sb = new StringBuilder("(");
			for(String s : params) {
				sb.append("'").append(s).append("',");
			}
			sb.deleteCharAt(sb.length() - 1);
			sb.append(")");
			return sb.toString();
		}
	}

	public static String imgToBase64(String path){
		File imgFile = new File(Constant.RESOURCE_TEMP + path);
		if(imgFile.exists() && imgFile.isFile()){
			InputStream in ;
			byte[] data = null;
			//读取图片字节数组
			try
			{
				in = new FileInputStream(imgFile);
				data = new byte[in.available()];
				in.read(data);
				in.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			//对字节数组Base64编码
			BASE64Encoder encoder = new BASE64Encoder();
			//返回Base64编码过的字节数组字符串
			return encoder.encode(data);
		}
		return "";
	}
	
	public static void main(String[] args) {

	}
}

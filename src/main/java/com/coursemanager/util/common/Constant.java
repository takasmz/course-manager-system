package com.coursemanager.util.common;

import java.text.SimpleDateFormat;

public class Constant {

	
	/** 存放在session中的用户数据 */
	public static final String USER = "web_user";
	public static final String USERID = "user_id";
	public static final String CHSNAME = "chs_name";
	public static final String ROLES = "roles";
	public static final String AUTHORITIES = "authorities";

	public static final String RESOURCE_TEMP = Constant.class.getClassLoader().getResource("").getPath();

	/** 历史作业模板路径 */
	public static final String HISTORY_HOMEWORK_TEMPLATE_PATH =
			 "/static/temp/pdfTemp/historyHomework.html";
	
	
	/** 简单日期格式转换器yyyy-MM-dd */
	public static final SimpleDateFormat SimpleDateFormat = new SimpleDateFormat("yyyyMMdd");
	/** 日期格式转换器yyyy-MM-dd */
	public static final SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd");
	/** 日期格式转换器yyyy/MM/dd */
	public static final SimpleDateFormat DateFormat2 = new SimpleDateFormat("yyyy/MM/dd");
	/** 日期格式转换器yyyy年MM月dd日 */
	public static final SimpleDateFormat DateFormat3 = new SimpleDateFormat("yyyy年MM月dd日");
	/** 时间格式转换器yyyy-MM-dd HH:mm:ss */
	public static final SimpleDateFormat TimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static final int EXIT_VALUE = 74186;

	public static final String SUBMIT_RECORD_TOKEN_NAME = "token";
	public static final int SUBMIT_RECORD_GAP = 10000;
}

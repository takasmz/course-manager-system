package com.coursemanager.util.common;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.apache.commons.lang3.StringUtils;

public class RequestUtil {
	
public static final int DEFAULT_PAGE_SIZE = 20;
	
	public static final int DEFAULT_PAGE_NO = 1;
	
	/**
	 * 页号，从1开始
	 */
	private int pageNo = 1;
	/**
	 * 页大小
	 */
	private int pageSize = 20;
	
	private int first = 0;
			
	public int getFirst() {
		if ((this.pageNo < 1) || (this.pageSize < 1))
			return 0;
		return ((this.pageNo - 1) * this.pageSize);
	}
	
	public RequestUtil(HttpServletRequest request){
		super();
		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize");
		this.pageNo = Integer.parseInt(StringUtils.isBlank(pageNo)?""
				+DEFAULT_PAGE_NO:pageNo);
		this.pageSize = Integer.parseInt(StringUtils.isBlank(pageSize)?""
				+DEFAULT_PAGE_SIZE:pageSize);
	}

	/**
	 * 获取request参数并添加分页参数
	 * @param request
	 * @return
	 */
	public static Map<String, Object> getPageParams(HttpServletRequest request){
		Map<String, Object> params = getParameterMap(request);
		RequestUtil pageRequest = new RequestUtil(request);
		params.put("first", pageRequest.getFirst());
		params.put("pageNo", pageRequest.pageNo);
		params.put("pageSize", pageRequest.pageSize);
		params.put("sortName", underline((String) params.get("sortName")));
		return params;
	}

	/**
	 * 获取request参数
	 * @param request
	 * @return map
	 */
	public static Map<String, Object> getParameterMap(HttpServletRequest request) {
        Map<String, String[]> properties = request.getParameterMap();//把请求参数封装到Map<String, String[]>中
        Map<String, Object> returnMap = new HashMap<String, Object>();
        Iterator<Entry<String, String[]>> iter = properties.entrySet().iterator();
        String name = "";
        String value = "";
        while (iter.hasNext()) {
            Entry<String, String[]> entry = iter.next();
            name = entry.getKey();
            Object valueObj = entry.getValue();
            if (null == valueObj) {
                value = "";
            } else if (valueObj instanceof String[]) {
                String[] values = (String[]) valueObj;
                for (int i = 0; i < values.length; i++) { //用于请求参数中有多个相同名称
                    value = values[i] + ",";
                }
                value = value.substring(0, value.length() - 1);
            } else {
                value = valueObj.toString();//用于请求参数中请求参数名唯一
            }
            returnMap.put(name, value);
        }
        return returnMap;
    }
	
	/**
	 * 驼峰转下划线
	 * @param str
	 * @return
	 */
	public static String underline(String str) {
		StringBuffer sb = new StringBuffer(str);
		Pattern pattern = Pattern.compile("[A-Z]");
		Matcher matcher = pattern.matcher(str);
		if(matcher.find()) {
			sb = new StringBuffer();
			//将当前匹配子串替换为指定字符串，并且将替换后的子串以及其之前到上次匹配子串之后的字符串段添加到一个StringBuffer对象里。
			//正则之前的字符和被替换的字符
			matcher.appendReplacement(sb,"_"+matcher.group(0).toLowerCase());
			//把之后的也添加到StringBuffer对象里
			matcher.appendTail(sb);			
		}else {
			return sb.toString();
		}	
		return underline(sb.toString());
	}
	
	public static void main(String[] args) {
		String test = "planDate";
		System.out.println(underline(test));
	}

}

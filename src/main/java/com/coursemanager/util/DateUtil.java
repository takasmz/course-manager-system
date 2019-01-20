package com.coursemanager.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类
 * @author 
 */
public class DateUtil {

    /**
     * 缺省的日期显示格式： yyyy-MM-dd
     */
    public static final String DEFAULT_DATE_FORMAT     = "yyyy-MM-dd";
    
    /**
     * 缺省的日期显示格式： yyyy年MM月dd日
     */
    public static final String CH_DEFAULT_DATE_FORMAT = "yyyy年MM月dd日";

    /**
     * 缺省的日期显示格式： yyyy-MM-dd
     */
    public static final String DEFAULT_DATE_FORMAT1     = "yyyy/MM/dd";
    
    /**
     * 缺省的日期时间显示格式：yyyy-MM-dd HH:mm:ss
     */
    public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    
    /**
     * 时间比较
     * dateStart 时间开始
     * dateEnd 时间结束
     * min 相差分钟
     */
    public static boolean compareToDate(Date dateStart,Date dateEnd,int min){
        int i = min * 1000;
        if (dateEnd.getTime () - dateStart.getTime () > i) { return true; }
        return false;
    }
    
    /**
     * 描述: 以指定的格式来格式化日期
     * @param date
     * @param format
     * @return
     */
    public static String dateToString(java.util.Date date,String format){
        String result = "";
        if (date != null) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat (format);
                result = sdf.format (date);
            } catch (Exception ex) {
                result = "";
            }
        }
        return result;
    }

    /** 
    * 描述: 获取当前完整日期
    * @return 
    */
    public static String getCurrentSimpleDate(){
        return dateToString (new Date (), "yyyyMMdd");
    }

    /**
     * 描述: 把字符转为日期
     * @param strDate
     * @param format
     * @return
     */
    public static Date stringToDate(String strDate,String format){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat (format);
            return sdf.parse (strDate);
        } catch (Exception er) {
            return null;
        }
    }
    
    public static void main(String[] args) {
    	String date = "2018/10/31";
    	System.out.println(DateUtil.stringToDate(date.replaceAll("/", "-"),"yyyy-MM-dd"));
	}
}

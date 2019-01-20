//package com.hikvision.training.util.common;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import javax.annotation.PostConstruct;
//import javax.annotation.Resource;
//
//import org.springframework.stereotype.Component;
//
//import com.hikvision.rams.dao.CommonNativeDao;
//
//@Component
//public class SequenceUtils {
//	
//	@Resource
//	private CommonNativeDao commonNativeDao;
//	
//	private static SequenceUtils sequenceUtils;
//	
//	/**
//	 * 解决在工具类静态方法中无法直接使用spring bean
//	 */
//	@PostConstruct
//	public void init() {
//		sequenceUtils = this;
//		sequenceUtils.commonNativeDao = this.commonNativeDao;
//	}
//	
//	private static final String REQUIREMENT = "XQ";
//	private static final String EVALUTION= "PG";
//	private static final String DEVELOPMENT = "KF";
//	private static final String PROJECT = "XM";
//	private static final String TEST = "CS";
//	
//	private static final String SEQUENCE = "seq_common_no";
//	
//	private static final SimpleDateFormat DateString = new SimpleDateFormat("yyyyMMdd");
//	
//	/**
//	 * 获取下个需求单号
//	 * @return
//	 */
//	public static String getNextReqSeq() {
//		return REQUIREMENT + getDateString() + getNextNo();
//	}
//	
//	/**
//	 * 获取下个评估单号
//	 * @return
//	 */
//	public static String getNextEvaSeq() {
//		return EVALUTION + getDateString() + getNextNo();
//	}
//	
//	/**
//	 * 获取下个开发任务单号
//	 * @return
//	 */
//	public static String getNextDevSeq() {
//		return DEVELOPMENT + getDateString() + getNextNo();
//	}
//	
//	/**
//	 * 获取下个测试任务单号
//	 * @return
//	 */
//	public static String getNextTestSeq() {
//		return TEST + getDateString() + getNextNo();
//	}
//	
//	/**
//	 * 获取下个项目单号
//	 * @return
//	 */
//	public static String getNextProSeq() {
//		return PROJECT + getDateString() + getNextNo();
//	}
//	
//	private static String getNextNo() {
//		Integer seq = sequenceUtils.commonNativeDao.getNextSequence("select nextval('" + SEQUENCE + "')");
//		//转成字符串左补0到5位
//		return String.format("%05d", seq);
//	}
//	
//	private static String getDateString() {
//		return DateString.format(new Date());
//	}
//	
//	public static void main(String[] args) {
//		System.out.println(String.format("%05d", 15));
//	}
//}

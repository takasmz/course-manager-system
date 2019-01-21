package com.coursemanager.util.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coursemanager.model.StudentInfo;
import com.coursemanager.util.DateUtil;


public class ExcelUtils {
	private static Logger log = LoggerFactory.getLogger(ExcelUtils.class);
	
	private static final int TITLE_COLUMN = 1;
	private static final int DATA_COLUMN = 2;
	
	public static final String COURSE_EXPORT_MODEL = ExcelUtils.class.getClassLoader().getResource("").getPath().replaceAll("%20", " ") + "model/培训计划导出模板.xlsx";
	public static final String COURSE_IMPORT_MODEL = ExcelUtils.class.getClassLoader().getResource("").getPath().replaceAll("%20", " ") + "model/培训计划导入模板.xlsx";
	public static final String PERSON_EXPORT_MODEL = ExcelUtils.class.getClassLoader().getResource("").getPath().replaceAll("%20", " ") + "model/培训人员导出模板.xlsx";

    
    public static Workbook getCourseExportModel() {
		return getWorkbook(COURSE_EXPORT_MODEL);
	}
	
    public static Workbook getPersonExportModel() {
		return getWorkbook(PERSON_EXPORT_MODEL);
	}
	
//	/**
//	 * 生成培训计划导出excel
//	 * @param trainCourses
//	 * @return
//	 */
//	public static Workbook generateCourseExcel(List<TrainCourse> trainCourses) {
//		Workbook model = getCourseExportModel();
//		for(int i = 0 ; i < trainCourses.size() ; i ++) {
//			Row row = model.getSheetAt(0).createRow(i + 1);
//			row.createCell(0).setCellValue(trainCourses.get(i).getId());
//			row.createCell(1).setCellValue(trainCourses.get(i).getCourseName());
//			row.createCell(2).setCellValue(trainCourses.get(i).getIntroduce());
//			row.createCell(3).setCellValue(trainCourses.get(i).getTrainstyle());			
//			row.createCell(4).setCellValue(trainCourses.get(i).getCourseTeacher());			
//			row.createCell(5).setCellValue(trainCourses.get(i).getTeacherDept());
//			row.createCell(6).setCellValue(trainCourses.get(i).getPlanDate());
//			row.createCell(7).setCellValue(trainCourses.get(i).getCourseDatetime());
//			row.createCell(8).setCellValue(trainCourses.get(i).getCourseAddress());
//			row.createCell(9).setCellValue(trainCourses.get(i).getAssignobject());
//			row.createCell(10).setCellValue(trainCourses.get(i).getAdviceobject());			
//			row.createCell(11).setCellValue(trainCourses.get(i).getIsTest());
//			//row.createCell(12).setCellValue(trainCourses.get(i).getPlanDate());
//			row.createCell(12).setCellValue(trainCourses.get(i).getCoursePersonNum());
//			row.createCell(13).setCellValue(trainCourses.get(i).getRealLongtime());
//			row.createCell(14).setCellValue(trainCourses.get(i).getIsTrained());
//		}
//		return model;
//	}
//	
//	/**
//	 * 生成报名人员导出excel
//	 * @param trainCourses
//	 * @return
//	 */
//	public static Workbook generatePersonExcel(List<CourseBaoming> courseBaoming) {
//		Workbook model = getPersonExportModel();
//		for(int i = 0 ; i < courseBaoming.size() ; i ++) {
//			Row row = model.getSheetAt(0).createRow(i + 1);
//			row.createCell(0).setCellValue(courseBaoming.get(i).getId());
//			row.createCell(1).setCellValue(courseBaoming.get(i).getDisplayname());
//			row.createCell(2).setCellValue(courseBaoming.get(i).getEmail());
//		}
//		return model;
//	}
//	
//	
	/**
	 * 读取excel数据导入StudentInfo
	 * @author 
	 */
	public static List<StudentInfo> readStudentInfoExcel(File file) {
		List<StudentInfo> list = new ArrayList<> ();
		Workbook wb = getWorkbook(file);
		if (wb != null) {
			Sheet sheet = wb.getSheetAt(0);
			int rows = sheet.getPhysicalNumberOfRows();
			for (int row = 3; row < rows; row++) {
				//先判断是否是空行
				Row r = sheet.getRow(row);
				Cell StudentNumber = r.getCell(1);
				Cell StudentName = r.getCell(2);
				//如果学号和姓名为空的话，就是空行
				if ((StudentNumber == null || StudentNumber.getCellType() == CellType.BLANK)
						&& (StudentName==null || StudentName.getCellType() == CellType.BLANK)) {
					break;
				}
				StudentInfo student = new StudentInfo();
				//末尾0代表学生
				student.setStudentId(UUID.randomUUID().toString().replaceAll("-", "") + "0");
				student.setStudentNumber(getStringCellValue(sheet, StudentModelColumn.STUDENT_ID, row).toLowerCase());
				student.setUserName(getStringCellValue(sheet, StudentModelColumn.STUDENT_ID, row).toLowerCase());
				String major = getStringCellValue(sheet, StudentModelColumn.MAJOR, row).toLowerCase();
				student.setClassName(major);
				//student.setGrade(major.substring(major.indexOf("(")-2,major.indexOf("(")));
				//student.setMajor(major.substring(0,major.indexOf("(")-2));
				student.setSchool("浙江理工大学");
				student.setCollege("理学院");
				student.setMail("000");
				list.add(student);
			}
		}
		return list;
	}
	

	private static Workbook getWorkbook(File file) {
		if(!file.exists()) {
			log.error("excel文件不存在：" + file.getPath());
			return null;
		}
		Workbook wb = null;
		try {
			InputStream is = new FileInputStream(file);
			if(file.getName().endsWith(".xls")) {
				wb = new HSSFWorkbook(is);
			} else {
				wb = new XSSFWorkbook(is);
			}
		} catch (Exception e) {
			log.error("excel文件读取失败：" + file.getName(), e);
			return null;
		}
		return wb;
	}
	
	public static Workbook getWorkbook(String filePath) {
		return getWorkbook(new File(filePath));
	}
	
	private static String getStringCellValue(Sheet sheet, BaseModelColumn column, int rowIndex, int columnIndex) {
		Cell cell = sheet.getRow(rowIndex).getCell(columnIndex);
		try {
			if(!column.isNullable()) {
				if(cell == null || StringUtils.isBlank(cell.getStringCellValue())) {
					throw new CellNullException(rowIndex + 1, column.getName());
				} else {
					return cell.getStringCellValue().trim();
				}
			} else {
				return cell == null ? "" : cell.getStringCellValue().trim();
			}
		} catch (IllegalStateException e) {
			cell.setCellType(CellType.STRING);
	        String cellValue = cell.getStringCellValue();  
	        // 判断是否包含小数点，如果不含小数点，则以字符串读取，如果含小数点，则转换为Double类型的字符串  
	        if (cellValue.indexOf(".") > -1) {  
	            return String.valueOf(new Double(cellValue)).trim();  
	        } else {  
	            return cellValue.trim();  
	        }  
			
		}
	}

	private static Date getDateCellValue(Sheet sheet, StudentModelColumn column, int rowIndex) throws ParseException{
		Cell cell = sheet.getRow(rowIndex).getCell(column.getRowIndex());
		try {
			if(!column.isNullable()) {
				if(cell == null) {
					throw new CellNullException(rowIndex + 1, column.getName());
				} else {
					return cell.getDateCellValue();
				}
			} else {
				return cell == null ? null : cell.getDateCellValue();
			}
		} catch (IllegalStateException e) {
			String date = getStringCellValue(sheet, column, rowIndex);
			if (date.contains("年")&&date.contains("月")&&date.contains("日")) {
				return DateUtil.stringToDate(date.replaceAll("/", "-"),DateUtil.DEFAULT_DATE_FORMAT);
			} 
			return DateUtil.stringToDate(date.replaceAll("/", "-"),DateUtil.DEFAULT_DATE_FORMAT);
		}
	}
	
	private static Date getDateCellValue(Sheet sheet, StudentModelColumn column) throws ParseException{
		Cell cell = sheet.getRow(column.getRowIndex()).getCell(DATA_COLUMN);
		try {
			if(!column.isNullable()) {
				if(cell == null) {
					throw new CellNullException(column.getRowIndex() + 1, column.getName());
				} else {
					return cell.getDateCellValue();
				}
			} else {
				return cell == null ? null : cell.getDateCellValue();
			}
		} catch (IllegalStateException e) {
			String date = getStringCellValue(sheet, column);
			if (date.contains("年")&&date.contains("月")&&date.contains("日")) {
				return DateUtil.stringToDate(date.replaceAll("/", "-"),DateUtil.DEFAULT_DATE_FORMAT1);
			} 
			return DateUtil.stringToDate(date.replaceAll("/", "-"),DateUtil.DEFAULT_DATE_FORMAT1);
		}
	}
	
	private static String getStringCellValue(Sheet sheet, StudentModelColumn column, int rowIndex) {
		return getStringCellValue(sheet, column, rowIndex, column.getRowIndex());
	}
	
	private static String getStringCellValue(Sheet sheet, StudentModelColumn column) {
		return getStringCellValue(sheet, column, column.getRowIndex(), DATA_COLUMN);
	}
	
}

package com.coursemanager.service.impl;

import com.coursemanager.dto.CourseInfoDto;
import com.coursemanager.dto.StudentExamInfoDto;
import com.coursemanager.dto.StudentSubmitStatusDto;
import com.coursemanager.mapper.*;
import com.coursemanager.model.*;
import com.coursemanager.service.IStudentExamInfoService;

import java.io.File;
import java.util.*;
import java.util.concurrent.*;

import com.coursemanager.util.DateUtil;
import com.coursemanager.util.FileUtil;
import com.coursemanager.util.common.AjaxResponse;
import com.coursemanager.util.compilerutil.SandBox;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;

/**
 * 描述:  student_exam_info 对应的Service接口实现类.<br>
 *
 * @author framework generator
 * @date 2018年11月18日
 */
@Service
public class StudentExamInfoServiceImpl extends MyBatisServiceSupport implements IStudentExamInfoService {
    /**
     * 日志记录器
     */
    private static final Logger logger = LoggerFactory.getLogger(StudentExamInfoServiceImpl.class);

    private static  final  ScheduledExecutorService service = Executors.newScheduledThreadPool(10);

    private final StudentExamInfoMapper studentExamInfoMapper;

    private final CourseExamInfoMapper courseExamInfoMapper;

    private final ExamInfoMapper examInfoMapper;

	private final CourseInfoMapper courseInfoMapper;

    private final StudentCourseMapper studentCourseMapper;

    @Autowired
    public StudentExamInfoServiceImpl(StudentExamInfoMapper studentExamInfoMapper, CourseExamInfoMapper courseExamInfoMapper,
                                      ExamInfoMapper examInfoMapper, CourseInfoMapper courseInfoMapper, StudentCourseMapper studentCourseMapper) {
        this.studentExamInfoMapper = studentExamInfoMapper;
        this.courseExamInfoMapper = courseExamInfoMapper;
        this.examInfoMapper = examInfoMapper;
        this.courseInfoMapper = courseInfoMapper;
        this.studentCourseMapper = studentCourseMapper;
    }


    @Override
	@Transactional(timeout=6000)
	public String checkCode(String code, UserInfo user , String examId) {
		StudentExamInfo studentExamInfo = new StudentExamInfo();
		if(user != null) {
			String uuid = user.getAccessToken();
			studentExamInfo.setStudentId(uuid);
		}
        studentExamInfo.setExamId(examId);
		studentExamInfo.setSubmitTime(new Date());
        studentExamInfo.setSubmitType(0);
		CourseExamInfo courseExamInfo = new CourseExamInfo();
		Example example = new Example(ExamInfo.class);
		example.createCriteria().andEqualTo("examId",examId);
		ExamInfo examInfo = examInfoMapper.selectByExample(example).get(0);
        courseExamInfo.setId(examInfo.getCourseExamId());
        courseExamInfo = courseExamInfoMapper.selectByPrimaryKey(courseExamInfo);
		//验证
        /*try {
            //code = StringEscapeUtils.unescapeHtml4(code);
            Map results = (Map) SandBox.check(examInfo, code);
            String status = (String) results.get("status");
            studentExamInfo.setResult(status);
            if (DateUtil.compareToDate(new Date(), courseExamInfo.getExpireTime(), 0)) {
                studentExamInfo.setStatus(0);
            } else {
                studentExamInfo.setStatus(1);
            }
            studentExamInfo.setSubmitContent(code);
            if (results.containsKey("error")) {
                studentExamInfo.setError((String) results.get("error"));
            }
            studentExamInfo.setSubmitTime(new Date());
            int num = studentExamInfoMapper.insert(studentExamInfo);
            if (num == 0) {
                logger.debug("[checkCode] 插入学生作业表失败");
            }
            return status;
        } catch (InterruptedException | ExecutionException e) {
            logger.debug("Compile Error");
            return "Compile Error";
        }*/
        return "";
    }

	@Override
    @Transactional
	public Map<String,Object> uploadFile(HttpServletRequest request, UserInfo user) {
		String examId = request.getParameter("examId");
        String examName = request.getParameter("examName");
		if(StringUtils.isBlank(examId)){
			logger.debug("[uploadFile] examId is null");
			return null;
		}
		CourseInfoDto courseInfo = courseInfoMapper.getCourseByexamId(examId);
        String path = getExamFilePath(user,courseInfo,examName);
        //学生上传作业文件
		Map<String,Object> map =  FileUtil.uploadFile(request,path,"files[]",null);
        logger.info("[uploadFile]" + user.getFullName() + "上传了："+ map.get("filenames"));
        StudentExamInfo studentExamInfo = new StudentExamInfo();
        CourseExamInfo courseExamInfo = courseExamInfoMapper.selectByExamId(examId);
        if(courseExamInfo == null){
            logger.debug("[submitHomework] 未找到课程作业项");
        }
        studentExamInfo.setExamId(examId);
        studentExamInfo.setResult("insert");
        studentExamInfo.setSubmitType(1);
        if(DateUtil.compareToDate(new Date() , courseExamInfo.getExpireTime() , 0)){
            studentExamInfo.setStatus(0);
        }else{
            studentExamInfo.setStatus(1);
        }
        studentExamInfo.setSubmitContent((String) map.get("filenames"));
        studentExamInfo.setSubmitTime(new Date());
        studentExamInfo.setStudentId(user.getAccessToken());
        int num = studentExamInfoMapper.insert(studentExamInfo);
        if(num != 1){
            logger.debug("[uploadFile] 上传文件 记录表 失败");
        }
        return map;
        //创建一个定时器，如果1分钟内未保存，将删除上传的文件
//        AutoDeleteFile(user , examId , map);
		//压缩成ZIP
//		if(map!=null && !map.isEmpty()){
//
//		}
	}

	private void AutoDeleteFile(UserInfo user,String examId, Map map){
        service.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("TimerTask开始");
                Calendar calendar = Calendar.getInstance();
                Date start = calendar.getTime();
                calendar.set(Calendar.MINUTE, -1);
                Date end = calendar.getTime();
                Example example = new Example(StudentExamInfo.class);
                example.createCriteria()
                        .andEqualTo("studentId", user.getAccessToken())
                        .andEqualTo("examId", examId)
                        .andEqualTo("submitType", 1)
                        .andEqualTo("result", "insert")
                        .andBetween("submitTime", start, end);
                System.out.println("开始检索");
                List<StudentExamInfo> studentExamInfoList = studentExamInfoMapper.selectByExample(example);
                System.out.println(studentExamInfoList.size());
                if (studentExamInfoList != null && !studentExamInfoList.isEmpty()) {
                    logger.debug("[autoDeleteFile] 1分钟内未提交，开始删除上传文件");
                    String[] filenames = ((String) map.get("filenames")).split("\\|");
                    String path = (String) map.get("path");
                    try {
                        for (String filename : filenames) {
                            File file = new File(FileUtil.TEMP_PATH + path + filename);
                            if (file.exists() && file.isFile()) {
                                System.out.println("[autoDeleteFile] 开始删除");
                                if (file.delete())
                                    logger.debug("[autoDeleteFile] 自动删除成功");
                                else
                                    logger.debug("[autoDeleteFile] 自动删除失败");
                            } else
                                logger.debug("[autoDeleteFile] " + FileUtil.TEMP_PATH + path + filename + " 文件不存在");
                        }
                    } catch (Exception e) {
                        System.out.println("[autoDeleteFile] 删除失败");
                        logger.debug("[autoDeleteFile] 删除失败");
                    }
                } else {
                    System.out.println("[autoDeleteFile] 1分钟内已提交");
                    logger.debug("[autoDeleteFile] 1分钟内已提交");
                }
            }
        },5000L , TimeUnit.MILLISECONDS);
    }

	@Override
    @Transactional
	public int submitHomework(HttpServletRequest request, UserInfo user) {
		if(user == null){
			logger.debug("[submitHomework] 用户未登录");
			return -1;
		}
        MultipartHttpServletRequest params=((MultipartHttpServletRequest) request);
        String[] filenames = params.getParameterValues("filename");
//        String[] sizes = params.getParameterValues("size");
		StudentExamInfo studentExamInfo = new StudentExamInfo();
		String examId = params.getParameter("examId");
        CourseExamInfo courseExamInfo = courseExamInfoMapper.selectByExamId(examId);
        if(courseExamInfo == null){
            logger.debug("[submitHomework] 未找到课程作业项");
            return 0;
        }
		studentExamInfo.setExamId(examId);
		studentExamInfo.setResult("insert");
		studentExamInfo.setSubmitType(1);
        if(DateUtil.compareToDate(new Date() , courseExamInfo.getExpireTime() , 0)){
            studentExamInfo.setStatus(0);
        }else{
            studentExamInfo.setStatus(1);
        }
		studentExamInfo.setSubmitContent(StringUtils.join(filenames,","));
		studentExamInfo.setSubmitTime(new Date());
		studentExamInfo.setStudentId(user.getAccessToken());
        return studentExamInfoMapper.insert(studentExamInfo);
	}

    @Override
    public Map<String,Object> getUploadedFile(HttpServletRequest request, UserInfo user) {
	    String examId = request.getParameter("examId");
        String examName = request.getParameter("examName");
	    if(StringUtils.isBlank(examId)){
	        logger.debug("[getUploadedFile] 题目id为空");
	        return null;
        }
        CourseInfoDto courseInfoDto = courseInfoMapper.getCourseByexamId(examId);
        String path;
        path = getExamFilePath(user,courseInfoDto,examName);
        File file = new File(FileUtil.TEMP_PATH + path);
        Map<String,Object> result = new HashMap<>();
        List<String> nameList = new ArrayList<>();
        if(file.exists() && file.isDirectory()){
            String[] fileNames = file.list();
            if(fileNames != null && fileNames.length>0){
                nameList.addAll(Arrays.asList(fileNames));
                result.put("path",path);
                result.put("nameList",nameList);
            }else{
                logger.debug(String.format("[getUploadedFile] %s第%d次作业 文件夹中为空",user.getFullName(),courseInfoDto.getExamNumber()));
            }
        }else{
            logger.debug(String.format("[getUploadedFile] %s第%d次作业 暂无提交记录",user.getFullName(),courseInfoDto.getExamNumber()));
        }
        return result;
    }

    @Override
    public AjaxResponse deleteFile(String pathName , String examId , boolean existed , UserInfo user) {
        if(StringUtils.isBlank(pathName)){
            logger.debug("[deleteFile] 参数为空");
            return AjaxResponse.error("[deleteFile] 文件路径为空");
        }
        File file = new File(FileUtil.TEMP_PATH + pathName);
        if(existed){
            StudentExamInfo studentExamInfo = new StudentExamInfo();
            studentExamInfo.setExamId(examId);
            studentExamInfo.setResult("delete");
            studentExamInfo.setSubmitType(1);
            studentExamInfo.setStatus(0);
            studentExamInfo.setSubmitContent(pathName.substring(pathName.lastIndexOf("/")+1));
            studentExamInfo.setSubmitTime(new Date());
            studentExamInfo.setStudentId(user.getAccessToken());
            int num = studentExamInfoMapper.insert(studentExamInfo);
        }
        if (!file.exists()) {
            logger.error("[deleteFile] 您要删除的资源不存在");
            return AjaxResponse.error("该文件不存在");
        }
        if(file.delete()){
            logger.debug("[deleteFile] 删除文件成功");
            return AjaxResponse.success("[deleteFile] 删除文件成功");
        }else{
            logger.debug("[deleteFile]  文件被占用，删除失败");
            return AjaxResponse.error("[deleteFile] 文件被占用，删除失败");
        }
    }

    @Override
    public List<StudentExamInfoDto> getRecordList(UserInfo user, HttpServletRequest request) {
        PageHelper.startPage(Integer.parseInt(request.getParameter("offset"))/10+1,Integer.parseInt(request.getParameter("limit")));
        if(user == null){
            logger.debug("[getRecordList] 用户未登录");
            return null;
        }
        String courseId = request.getParameter("courseId");
        List<StudentExamInfoDto> recordList = studentExamInfoMapper.getRecordList(user.getAccessToken(),courseId);
        return recordList;
    }

    @Override
    public List<StudentExamInfoDto> getStudentRecord(UserInfo userInfo, HttpServletRequest request) {
        PageHelper.startPage(Integer.parseInt(request.getParameter("offset"))/10+1,Integer.parseInt(request.getParameter("limit")));
        if(userInfo == null){
            logger.debug("[getStudentRecord] 用户未登录");
            return null;
        }
        //查询参加该课程的学生
        String courseId = request.getParameter("courseId");
        Example example = new Example(StudentCourse.class);
        example.createCriteria().andEqualTo("courseId",courseId);
        List<StudentCourse> studentCourseList = studentCourseMapper.selectByExample(example);
        if(studentCourseList == null || studentCourseList.isEmpty()){
            logger.debug("[getStudentRecord] 该课程没有学生选择");
            return null;
        }
        Map<String,List<StudentSubmitStatusDto>> map = new HashMap<>();
        //作业各个题目的提交情况
        List<StudentSubmitStatusDto> list;
        //查询学生的提交记录
        List<StudentExamInfoDto> recordList = studentExamInfoMapper.getStudentRecordList(userInfo.getAccessToken(),courseId,studentCourseList);
        for(StudentExamInfoDto studentExamInfoDto : recordList){
            if(!map.containsKey(studentExamInfoDto.getStudentId())){
                list = new ArrayList<>();
            }else{
                list = map.get(studentExamInfoDto.getStudentId());
            }
            StudentSubmitStatusDto studentSubmitStatusDto = new StudentSubmitStatusDto();
            studentSubmitStatusDto.setFinished(studentExamInfoDto.getFinished());
            studentSubmitStatusDto.setTotal(studentExamInfoDto.getTotal());
            studentSubmitStatusDto.setSubmitStatus(studentExamInfoDto.getSubmitStatus());
            studentSubmitStatusDto.setCourseExamId(studentExamInfoDto.getCourseExamId());
            studentSubmitStatusDto.setStudentId(studentExamInfoDto.getStudentId());
            list.add(studentSubmitStatusDto);
            map.put(studentExamInfoDto.getStudentName(),list);
        }
        Page<StudentExamInfoDto> result = new Page<>();
        for(Map.Entry<String,List<StudentSubmitStatusDto>> entry : map.entrySet()){
            StudentExamInfoDto s = new StudentExamInfoDto();
            s.setStudentName(entry.getKey());
            s.setStatusDtoList(entry.getValue());
            result.add(s);
        }
        result.setTotal(result.size());
        return result;
    }

    @Override
    public List<StudentExamInfoDto> queryHomeworkRecordDetail(String courseExamId, String studentId) {
        if(StringUtils.isBlank(courseExamId) || StringUtils.isBlank(studentId)){
            logger.debug("[queryHomeworkRecordDetail] 参数为空");
            return null;
        }
        List<StudentExamInfoDto> studentExamInfoDtoList = studentExamInfoMapper.queryHomeworkRecordDetail(courseExamId,studentId);
        return studentExamInfoDtoList;
    }
}
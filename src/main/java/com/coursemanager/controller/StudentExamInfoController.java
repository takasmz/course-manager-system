package com.coursemanager.controller;

import com.coursemanager.dto.StudentExamInfoDto;
import com.coursemanager.model.UserInfo;
import com.coursemanager.service.IStudentExamInfoService;
import com.coursemanager.util.PageResponse;
import com.coursemanager.util.common.AjaxResponse;
import com.coursemanager.util.common.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 描述:  student_exam_info 对应的Controller类.<br>
 *
 * @author framework generator
 * @date 2018年11月18日
 */
@Controller
@RequestMapping({"student/homework","teacher/homework"})
public class StudentExamInfoController extends BaseController {
    /**
     * 日志记录器
     */
    private static final Logger logger = LoggerFactory.getLogger(StudentExamInfoController.class);

    private final IStudentExamInfoService studentExamInfoService;

    @Autowired
    public StudentExamInfoController(IStudentExamInfoService studentExamInfoService) {
        this.studentExamInfoService = studentExamInfoService;
    }

    /**
     * 学生提交代码，在线编译检查
     * @param code 提交的代码
     * @param examId 题目id
     * @param session session
     * @return 提交结果
     */
    @RequestMapping("/checkCode")
    @ResponseBody
    public AjaxResponse checkCode(String code, String examId , HttpSession session) {
    	logger.debug("[checkCode] start");
    	UserInfo user = (UserInfo) session.getAttribute(Constant.USER);
    	if(user==null) {
    		logger.debug("[checkCode] user id null");
    		return AjaxResponse.error("请先登录");
    	}
        Long nextSubmitTime = (Long) session.getAttribute(Constant.SUBMIT_RECORD_TOKEN_NAME);
        System.out.println(nextSubmitTime);
        // 如果为空，就表明是第一次提交
        if (nextSubmitTime != null) {
            if (nextSubmitTime > System.currentTimeMillis()) {
                return AjaxResponse.error("请" + TimeUnit.MILLISECONDS.toSeconds(nextSubmitTime - System.currentTimeMillis()) + "秒后再提交代码");
            }
        }
    	String status = studentExamInfoService.checkCode(code,user,examId);
        // 10秒后才能允许再一次提交代码
        session.setAttribute(Constant.SUBMIT_RECORD_TOKEN_NAME, System.currentTimeMillis() + Constant.SUBMIT_RECORD_GAP);
        System.out.println(System.currentTimeMillis() + Constant.SUBMIT_RECORD_GAP);
    	return AjaxResponse.success("提交成功",status);
    }

    /**
     * 上传附件（目前是学生）
     * @return 上传结果
     */
    @RequestMapping("/uploadFile")
    @ResponseBody
    public AjaxResponse uploadFile(HttpServletRequest request){
        UserInfo user = getUser(request);
        if(user==null) {
            logger.debug("[uploadFile] user id null");
            return AjaxResponse.error("您尚未登录,请先登录");
        }
        logger.debug("[uploadFile] start");
        Map<String,Object> result = studentExamInfoService.uploadFile(request,user);
        try{
            //Map<String,Object> result = FileUtil.uploadFile(request);
            if(result!=null){
                return AjaxResponse.success("附件上传成功",result);
            }else{
                return AjaxResponse.error("非表单操作");
            }
        }catch(Exception e){
            logger.debug("上传文件出现错误");
            return AjaxResponse.error("上传文件出现错误");
        }
    }


    /**
     * 获得已上传文件
     */
    @RequestMapping("/getUploadedFile")
    @ResponseBody
    public AjaxResponse getUploadedFile(HttpServletRequest request){
        UserInfo user = getUser(request);
        if(user==null) {
            logger.debug("[getUploadedFile] user id null");
            return AjaxResponse.error("您尚未登录,请先登录");
        }
        logger.debug("[getUploadedFile] start");
        Map<String,Object> map = studentExamInfoService.getUploadedFile(request,user);
        return AjaxResponse.success("获取文件列表成功",map);
    }

    /**
     * 学生 删除已提交文件
     * @param pathName 文件名称
     * @param examId 作业id
     * @param existed 文件是否存在
     */
    @RequestMapping("/deleteFile/**")
    @ResponseBody
    public AjaxResponse deleteFile(String pathName , String examId , boolean existed, HttpServletRequest request){
        try {
            logger.info("[deleteFile] start");
            UserInfo user = getUser(request);
            return studentExamInfoService.deleteFile(pathName, examId , existed, user);
        } catch (Exception e) {
            return AjaxResponse.error("[deleteFile] 删除文件失败");
        }
    }

    /**
     * 学生 提交作业
     */
    @RequestMapping("/submitHomework")
    @ResponseBody
    public AjaxResponse saveFile(HttpServletRequest request){
        UserInfo user = getUser(request);
        int status = studentExamInfoService.submitHomework(request,user);
        if(status == 0){
            return AjaxResponse.error("提交失败");
        }else if(status == -1){
            return  AjaxResponse.error("用户未登录");
        }else {
            return AjaxResponse.success("提交成功");
        }
    }

    /**
     * 学生 获取自己作业提交记录
     * @return 分页列表
     */
    @RequestMapping("/getRecordList")
    @ResponseBody
    public PageResponse<StudentExamInfoDto> getRecordList(HttpServletRequest request){
        logger.debug("[getRecordList] start");
        UserInfo user = getUser(request);
        List<StudentExamInfoDto> recordList = studentExamInfoService.getRecordList(user,request);
        return new PageResponse<>(recordList);
    }

    /**
     * 教师 获取课程所有学生作业记录
     * @return 分页列表
     */
    @RequestMapping("/getStudentRecord")
    @ResponseBody
    public PageResponse<StudentExamInfoDto> getStudentRecord(HttpServletRequest request){
        logger.debug("[getStudentRecord] start");
        UserInfo userInfo = getUser(request);
        List<StudentExamInfoDto> recordList = studentExamInfoService.getStudentRecord(userInfo,request);
        return new PageResponse<>(recordList);
    }

    /**
     * 教师 获取学生某次课程作业具体信息
     * @param courseExamId 课程作业id
     * @param studentId 学生id
     */
    @RequestMapping("/queryHomeworkRecordDetail")
    @ResponseBody
    public AjaxResponse queryHomeworkRecordDetail(String courseExamId , String studentId){
        logger.debug("[queryHomeworkRecordDetail] start");
        List<StudentExamInfoDto> studentExamInfoDtoList = studentExamInfoService.queryHomeworkRecordDetail(courseExamId,studentId);
        return AjaxResponse.success("获取学生具体提交内容成功",studentExamInfoDtoList);
    }

}
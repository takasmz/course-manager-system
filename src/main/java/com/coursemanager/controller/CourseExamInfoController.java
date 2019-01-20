package com.coursemanager.controller;

import com.alibaba.fastjson.JSONObject;
import com.coursemanager.dto.CourseExamInfoDto;
import com.coursemanager.dto.ExamInfoDto;
import com.coursemanager.model.CourseExamInfo;
import com.coursemanager.model.ExamInfo;
import com.coursemanager.model.UserInfo;
import com.coursemanager.service.ICourseExamInfoService;
import com.coursemanager.service.ICourseInfoService;
import com.coursemanager.util.FileUtil;
import com.coursemanager.util.PageRequest;
import com.coursemanager.util.PageResponse;
import com.coursemanager.util.common.AjaxResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 描述:  course_exam_info 对应的Controller类.<br>
 *
 * @author framework generator
 * @date 2018年11月18日
 */
@Controller
@RequestMapping("course/courseExam")
public class CourseExamInfoController extends BaseController {
    /**
     * 日志记录器
     */
    private static final Logger logger = LoggerFactory.getLogger(CourseExamInfoController.class);

    private final ICourseExamInfoService courseExamInfoService;

    private final ICourseInfoService courseInfoService;

    @Autowired
    public CourseExamInfoController(ICourseExamInfoService courseExamInfoService, ICourseInfoService courseInfoService) {
        this.courseExamInfoService = courseExamInfoService;
        this.courseInfoService = courseInfoService;
    }

    /**
     * @Author 李如豪
     * @Description 获取学生题目列表，并显示有无提交
     * @Date 15:50 2019/1/16
     * @Param request
     * @return 题目列表
     **/
    @RequestMapping("/queryExamList")
    @ResponseBody
    public PageResponse queryExamList(HttpServletRequest request){
        logger.debug("[queryExamList] start");
        UserInfo user = getUser(request);
        if(user == null){
            logger.debug("[queryExamList] user is null");
            return null;
        }
        return courseExamInfoService.queryExamList(user,request);
    }

    /**
     * @Author 李如豪
     * @Description 编辑器上传图片
     * @Date 15:52 2019/1/16
     * @Param type:上传种类(teacherHomework:老师上传作业，courseIntro：课程介绍编辑)
     * @return {msg:返回语句,success:上传结果,path:上传路径}
     **/
    @RequestMapping("/uploadImg")
    @ResponseBody
    public JSONObject uploadImg(String type, HttpServletRequest request){
        logger.debug("[uploadImg] start");
        JSONObject obj = new JSONObject();
        try{
            UserInfo user = getUser(request);
            String courseExamId = request.getParameter("courseExamId");
            String courseId = request.getParameter("courseId");
            String couserPath = courseInfoService.getCoursePathByCourseId(courseId);
            if(user == null){
                logger.debug("[uploadImg] 用户未登录");
                obj.put("msg","用户未登录");
                obj.put("success",false);
                obj.put("file_path",null);
                return obj;
            }
            String pathName,url="";
            Map<String,Object> result = new HashMap<>();
            if(type.equals("teacherHomework")){
                pathName = "teacher/homework/"+courseExamId;
                result = FileUtil.uploadFile(request,pathName,"files[]",null);
                url = ((String)result.get("filenames")).replace("|","");
                url = "/showImg/"+ pathName + "/" + url;
                obj.put("success",true);
                obj.put("msg","上传成功");
            }else if(type.equals("courseIntro")){
                pathName = "static/contents/"+couserPath;
                result = FileUtil.uploadFile(request,pathName,"editormd-image-file",null);
                url = ((String)result.get("filenames")).replace("|","");
                url = "/showImg/" + pathName + "/" + url;
                obj.put("success",1);
                obj.put("message","上传成功");
            }
            obj.put("file_path",result.get("filenames"));
            obj.put("url",url);
            return obj;
        }catch(Exception e){
            logger.debug("[uploadImg] 上传失败");
            obj.put("msg","上传失败");
            obj.put("success",false);
            obj.put("file_path",null);
            return obj;
        }
    }

    /**
     * @Author 李如豪
     * @Description 新建课程时预览新建的题目列表
     * @Date 15:55 2019/1/16
     * @Param request:HTTP请求
     * @return 题目列表
     **/
    @RequestMapping("/newHomeworkExample")
    @ResponseBody
    public PageResponse<ExamInfoDto> newHomeworkExample(HttpServletRequest request){
        logger.debug("[newHomeworkExample] start");
        UserInfo user = getUser(request);
        if(user == null){
            logger.debug("[uploadImg] 用户未登录");
            return null;
        }
        List<ExamInfoDto> list = courseExamInfoService.newHomeworkExample(request,user);
        return new PageResponse<>(list);
    }

    /**
     * @Author 李如豪
     * @Description 教师创建题目完成后，新建作业，该作业下的题目状态变成正常
     * @Date 15:56 2019/1/16
     * @Param request:HTTP请求,examInfo：问题对象
     * @return 保存结果
     **/
    @RequestMapping("/createExamInfo")
    @ResponseBody
    public AjaxResponse createExamInfo(HttpServletRequest request,ExamInfo examInfo){
        logger.debug("[createExamInfo] start");
        Integer status = courseExamInfoService.createExamInfo(request,examInfo);
        if(status == 1){
            return  AjaxResponse.success("保存成功");
        }else{
            return AjaxResponse.error("保存失败");
        }
    }

    /**
     * @Author 李如豪
     * @Description 教师新建题目，其状态为创建中
     * @Date 15:58 2019/1/16
     * @Param request:HTTP请求
     * @return 保存结果
     **/
    @RequestMapping("/createNewHomework")
    @ResponseBody
    public AjaxResponse createNewHomework(HttpServletRequest request){
        logger.debug("[createNewHomework] start");
        Integer status = courseExamInfoService.createNewHomework(request);
        if(status == 1){
            return  AjaxResponse.success("保存成功");
        }else{
            return AjaxResponse.error("保存失败");
        }
    }

    /**
     * @Author 李如豪
     * @Description 获取历史作业，将作业内容转成Word供学生下载
     * @Date 16:00 2019/1/16
     * @Param
     * @return 分页列表
     **/
    @RequestMapping("/queryHistoryHomeworkList")
    @ResponseBody
    public PageResponse queryHistoryHomeworkList(PageRequest request){
        logger.debug("[queryHistoryHomeworkList] start");
        List<CourseExamInfoDto> list = courseExamInfoService.queryHistoryHomeworkList(request);
        return new PageResponse<>(list);
    }

    @RequestMapping("/downloadHistoryHomework")
    @ResponseBody
    public void downloadHistoryHomework(String courseExamId, String courseExamName, HttpServletRequest request ,HttpServletResponse response){
        logger.debug("[downloadHistoryHomework] start");
        String fileName = courseExamName + ".pdf";
        try {
            FileUtil.SetDownloadResponseHeader(fileName,request,response);
            courseExamInfoService.downloadHistoryHomework(courseExamId, courseExamName ,response);
        } catch (Exception e) {
            logger.debug("[downloadHistoryHomework] 下载PDF文件失败");
            e.printStackTrace();
        }

    }
}
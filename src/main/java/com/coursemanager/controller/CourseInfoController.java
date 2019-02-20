package com.coursemanager.controller;

import com.coursemanager.model.CourseInfo;
import com.coursemanager.model.UserInfo;
import com.coursemanager.service.ICourseInfoService;
import com.coursemanager.util.common.AjaxResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/course")
public class CourseInfoController extends BaseController{
    private static final Logger logger = LoggerFactory.getLogger(CourseInfoController.class);

    private final ICourseInfoService courseInfoService;

    @Autowired
    public CourseInfoController(ICourseInfoService courseInfoService) {
        this.courseInfoService = courseInfoService;
    }

    /**
     * @Author 李如豪
     * @Description 学生获取课程列表
     * @Date 17:09 2019/2/20
     **/
    @RequestMapping("/getCourseList")
    @ResponseBody
    public AjaxResponse getCourseList(HttpServletRequest request){
        logger.debug("[getCourseList} start");
        UserInfo user = getUser(request);
        List<CourseInfo> list = courseInfoService.getCourseList(user);
        return AjaxResponse.success("课程列表获取成功",list);
    }

    /**
     * @Author 李如豪
     * @Description 教师获取课程列表
     * @Date 17:09 2019/2/20
     **/
    @RequestMapping("teacher/queryCourseList")
    @ResponseBody
    public AjaxResponse queryCourseList(HttpServletRequest request){
        logger.debug("[queryCourseList] start");
        UserInfo user = getUser(request);
        List<CourseInfo> courseInfoList = courseInfoService.queryCourseList(user);
        return AjaxResponse.success("success",courseInfoList);
    }

    /**
     * @Author 李如豪
     * @Description 获取课程介绍文件路径
     * @Date 17:10 2019/2/20
     * @param courseId 课程id
     * @param type 文件名
     **/
    @RequestMapping("/getCourseIntro")
    @ResponseBody
    public AjaxResponse getCourseIntro(String courseId, String type){
        logger.debug("[getCourseIntro] start");
        String coursePath = courseInfoService.getCoursePathByCourseId(courseId);
        String filePath = "/static/contents/"+coursePath+"/"+type+".md";
        String dataString = readFile(filePath,null);
        //dataString = dataString.replace("editormd-logo-180x180.png","/contents/"+coursePath+"/editormd-logo-180x180.png");
        Map<String,Object> map = new HashMap<>();
        map.put("data",dataString);
        map.put("path","/contents/"+coursePath+"/");
        return AjaxResponse.success("success",map);
    }

    /**
     * @Author 李如豪
     * @Description 保存课程介绍
     * @Date 17:11 2019/2/20
     * @param md 文件路径
     * @param courseId 课程id
     * @param type 文件名称
     **/
    @RequestMapping("saveIntro")
    @ResponseBody
    public AjaxResponse saveIntro(String md,String courseId,String type){
        logger.debug("[saveIntro] start");
        return courseInfoService.saveIntro(md,courseId,type);
    }

    /**
     * @Author 李如豪
     * @Description 获取课程教学大纲pdf路径
     * @Date 17:12 2019/2/20
     * @param courseId 课程id
     * @return 大纲pdf路径
     **/
    @RequestMapping("/getSyllabus")
    @ResponseBody
    public String getSyllabus(String courseId){
        logger.debug("[getSyllabus] start");
        String coursePath = courseInfoService.getCoursePathByCourseId(courseId);
        return "/contents/" + coursePath + "/outline.pdf";
    }

    /**
     * @Author 李如豪
     * @Description 老师新建课程
     * @Date 17:13 2019/2/20
     **/
    @RequestMapping("/teacher/createNewCourse")
    @ResponseBody
    public AjaxResponse createNewCourse(HttpServletRequest request){
        logger.debug("[createNewCourse] start");
        UserInfo userInfo = getUser(request);
        int num = courseInfoService.createNewCourse(request,userInfo);
        if(num == -1){
            return AjaxResponse.error("系统错误");
        }else if(num == 0){
            return AjaxResponse.error("课程保存失败");
        }else{
            return AjaxResponse.success("课程保存成功");
        }
    }

}
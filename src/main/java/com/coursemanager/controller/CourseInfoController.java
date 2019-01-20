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
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/course")
public class CourseInfoController extends BaseController{
    private static final Logger logger = LoggerFactory.getLogger(CourseInfoController.class);

    @Autowired
    private ICourseInfoService courseInfoService;

    @RequestMapping("/getCourseList")
    @ResponseBody
    public AjaxResponse getCourseList(HttpServletRequest request){
        logger.debug("[getCourseList} start");
        UserInfo user = getUser(request);
        List<CourseInfo> list = courseInfoService.getCourseList(user);
        return AjaxResponse.success("课程列表获取成功",list);
    }

    @RequestMapping("teacher/queryCourseList")
    @ResponseBody
    public AjaxResponse queryCourseList(HttpServletRequest request){
        logger.debug("[queryCourseList] start");
        UserInfo user = getUser(request);
        List<CourseInfo> courseInfoList = courseInfoService.queryCourseList(user);
        return AjaxResponse.success("success",courseInfoList);
    }

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

    @RequestMapping("saveIntro")
    @ResponseBody
    public AjaxResponse saveIntro(String md,String courseId,String type){
        logger.debug("[saveIntro] start");
        return courseInfoService.saveIntro(md,courseId,type);
    }

    @RequestMapping("/getSyllabus")
    @ResponseBody
    public String getSyllabus(String courseId){
        logger.debug("[getSyllabus] start");
        String coursePath = courseInfoService.getCoursePathByCourseId(courseId);
        return "/contents/" + coursePath + "/outline.pdf";
    }
}
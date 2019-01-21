package com.coursemanager.controller;

import com.coursemanager.dto.CourseFileDto;
import com.coursemanager.model.CourseFile;
import com.coursemanager.service.ICourseFileService;
import com.coursemanager.util.PageResponse;
import com.coursemanager.util.common.AjaxResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.util.List;

@Controller
@RequestMapping("course/courseResource")
public class CourseFileController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(CourseFileController.class);

    @Autowired
    private ICourseFileService courseFileService;

    @RequestMapping("/queryResourceList")
    @ResponseBody
    public PageResponse<CourseFileDto> queryResourceList(HttpServletRequest request){
        logger.debug("[queryResourceList] start");
        List<CourseFileDto> list = courseFileService.queryResourceList(request);
        return new PageResponse<>(list);
    }
}
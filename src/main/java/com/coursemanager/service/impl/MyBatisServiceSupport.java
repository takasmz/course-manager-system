package com.coursemanager.service.impl;

import com.coursemanager.cache.DataCache;
import com.coursemanager.dto.CourseInfoDto;
import com.coursemanager.model.CourseExamInfo;
import com.coursemanager.model.UserInfo;
import com.coursemanager.service.BaseService;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyBatisServiceSupport implements BaseService{

    String getExamFilePath(UserInfo user, CourseInfoDto courseInfoDto, String examName) {
        String coursePath = courseInfoDto.getCoursePath();
        String fileName = user.getUserName() + user.getFullName() + "第" + courseInfoDto.getExamNumber() + "次作业";
        String lastPath = coursePath + courseInfoDto.getYear() + "" +courseInfoDto.getTerm() + "/" + user.getUserName() + user.getFullName();
        return "student/homework/" + lastPath + "/" + fileName + "/" + examName;
    }

    static  String getNumUUId(){
        String numStr ;
        String trandStr = String.valueOf((Math.random() * 9 + 1) * 1000000);
        String dataStr = new SimpleDateFormat("yyyyMMdd").format(new Date());
        numStr = trandStr.substring(0, 6);
        numStr = numStr + dataStr.substring(2,4) ;
        return numStr ;
    }

    static String getCourseExamName(CourseExamInfo courseExamInfo){
        return DataCache.getCourseName(Integer.parseInt(courseExamInfo.getCourseId())) +
                "第" +courseExamInfo.getExamNumber()+"次作业";
    }

    boolean checkPageRequest(HttpServletRequest request){
        return StringUtils.isBlank(request.getParameter("offset")) ||
                StringUtils.isBlank(request.getParameter("limit"));
    }
}

package com.coursemanager.service.impl;

import com.coursemanager.dto.CourseInfoDto;
import com.coursemanager.model.UserInfo;
import com.coursemanager.service.BaseService;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyBatisServiceSupport implements BaseService{

    public String getExamFilePath(UserInfo user, CourseInfoDto courseInfoDto,String examName) {
        String coursePath = courseInfoDto.getCoursePath();
        String fileName = user.getUserName() + user.getFullName() + "第" + courseInfoDto.getExamNumber() + "次作业";
        String lastPath = coursePath + courseInfoDto.getYear() + "" +courseInfoDto.getTerm() + "/" + user.getUserName() + user.getFullName();
        String path = "student/homework/" + lastPath + "/" + fileName + "/" + examName;
        return path;
    }

    public static  String getNumUUId(){
        String numStr = "" ;
        String trandStr = String.valueOf((Math.random() * 9 + 1) * 1000000);
        String dataStr = new SimpleDateFormat("yyyyMMdd").format(new Date());
        numStr = trandStr.toString().substring(0, 6);
        numStr = numStr + dataStr.substring(2,4) ;
        return numStr ;
    }
}

package com.coursemanager.service.impl;

import com.coursemanager.dto.CourseInfoDto;
import com.coursemanager.mapper.CourseInfoMapper;
import com.coursemanager.model.CourseInfo;
import com.coursemanager.model.StudentCourse;
import com.coursemanager.model.UserInfo;
import com.coursemanager.service.ICourseInfoService;
import com.coursemanager.util.FileUtil;
import com.coursemanager.util.common.AjaxResponse;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.weaver.loadtime.Aj;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.List;

@Service
public class CourseInfoServiceImpl implements ICourseInfoService {
    private static final Logger logger = LoggerFactory.getLogger(CourseInfoServiceImpl.class);

    @Autowired
    private CourseInfoMapper courseInfoMapper;

    @Override
    public List<CourseInfo> getCourseList(UserInfo user) {
        List<CourseInfo> list;
        if(user.getType() == 0){
            list = courseInfoMapper.getCourseListByStudent(user.getAccessToken());
        }else{
            list = courseInfoMapper.getCourseListByTeacher(user.getAccessToken());
        }
        return list;
    }

    @Override
    public List<CourseInfo> queryCourseList(UserInfo user) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        Example example = new Example(CourseInfo.class);
        example.createCriteria().andEqualTo("teacherAccount",user.getAccessToken()).andGreaterThanOrEqualTo("year",year);
        return courseInfoMapper.selectByExample(example);
    }

    @Override
    public String getCoursePathByCourseId(String courseId) {
        if (StringUtils.isBlank(courseId)){
            logger.debug("[getCoursePathByCourseId] 课程id为空");
            return null;
        }
        CourseInfo courseInfo = new CourseInfo();
        courseInfo.setId(Integer.parseInt(courseId));
        courseInfo = courseInfoMapper.selectByPrimaryKey(courseInfo);
        return courseInfo.getCoursePath();
    }

    @Override
    public AjaxResponse saveIntro(String md, String courseId, String type) {
        if(StringUtils.isBlank(md) || StringUtils.isBlank(courseId)){
            logger.debug("[saveIntro] 参数为空");
            return AjaxResponse.error("参数为空");
        }
        String coursePath = getCoursePathByCourseId(courseId);
        File file = new File(FileUtil.TEMP_PATH+"static/contents/"+coursePath +"/"+type+".md");
        PrintWriter out = null;
        if(file.exists() && file.isFile()){
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                out = new PrintWriter(file);
                out.write(md);
            }catch (FileNotFoundException e) {
                e.printStackTrace();
                logger.debug("[saveIntro] "+coursePath+"文件不存在");
                return AjaxResponse.error("[saveIntro] "+coursePath+"文件不存在");
            }finally {
                out.flush();
                out.close();
            }
        }else{
            logger.debug("[saveIntro] "+coursePath+"文件不存在");
            return AjaxResponse.error("[saveIntro] "+coursePath+"文件不存在");
        }
        return AjaxResponse.success("上传文件成功");
    }

}
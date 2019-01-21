package com.coursemanager.service.impl;

import com.coursemanager.mapper.CourseInfoMapper;
import com.coursemanager.mapper.StudentInfoMapper;
import com.coursemanager.model.CourseInfo;
import com.coursemanager.model.StudentInfo;
import com.coursemanager.model.UserInfo;
import com.coursemanager.service.ICourseInfoService;
import com.coursemanager.util.FileUtil;
import com.coursemanager.util.common.AjaxResponse;
import com.coursemanager.util.common.Constant;
import com.coursemanager.util.excel.ExcelUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

@Service
public class CourseInfoServiceImpl implements ICourseInfoService {
    private static final Logger logger = LoggerFactory.getLogger(CourseInfoServiceImpl.class);

    @Autowired
    private CourseInfoMapper courseInfoMapper;

    @Autowired
    private StudentInfoMapper studentInfoMapper;

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
        int fullYear = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year,term;
        if(month<=7){
            year = fullYear-1;
            term = 2;
        }else{
            year = fullYear;
            term = 1;
        }
        Example example = new Example(CourseInfo.class);
        example.createCriteria()
                .andEqualTo("teacherAccount",user.getAccessToken())
                .andEqualTo("year",year)
                .andEqualTo("term",term);
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

    @Override
    @Transactional
    public int createNewCourse(HttpServletRequest request, UserInfo userInfo) {
        Map<String,Object> map =
                FileUtil.uploadFile(request,"temp","studentInfoFile",null);
        if(map.get("filenames") == null){
            logger.debug("[createNewCourse] 录入文件为空");
            //return -1;
        }else{
            File file = new File(Constant.RESOURCE_TEMP+"\\temp\\"+((String) map.get("filenames")).split("\\|")[0]);
            logger.debug("[createNewCourse] 开始将学生信息导入数据库");
            List<StudentInfo> list = ExcelUtils.readStudentInfoExcel(file);
            for (StudentInfo studentInfo : list){
                studentInfoMapper.insert(studentInfo);
            }
        }
        String courseName = request.getParameter("courseName");
        Calendar calendar = Calendar.getInstance();
        int fullYear = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year,term;
        if(month<=7){
            year = fullYear-1;
            term = 2;
        }else{
            year = fullYear;
            term = 1;
        }
        CourseInfo courseInfo = new CourseInfo();
        courseInfo.setCourseName(courseName);
        courseInfo.setStatus(1);
        courseInfo.setTerm(term);
        courseInfo.setYear(year);
        courseInfo.setTeacherAccount(userInfo.getAccessToken());
        return courseInfoMapper.insert(courseInfo);
    }

}
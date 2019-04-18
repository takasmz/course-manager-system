package com.coursemanager.cache;

import com.coursemanager.mapper.CourseInfoMapper;
import com.coursemanager.mapper.StudentInfoMapper;
import com.coursemanager.mapper.TeacherInfoMapper;
import com.coursemanager.model.CourseInfo;
import com.coursemanager.model.StudentInfo;
import com.coursemanager.model.TeacherInfo;
import com.coursemanager.model.UserInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author 李如豪
 * @Date 2019/2/19 15:51
 * @VERSION 1.0
 **/
@Component
public class DataCache {
    private static Map<String,Map<String,Long>> userAccessRecordMap ;
    private static Map<String,String> userNameMap;
    private static Map<Integer,String> courseMap;

    static {
        userAccessRecordMap = new ConcurrentHashMap<>(256);
        userNameMap = new ConcurrentHashMap<>(256);
        courseMap = new ConcurrentHashMap<>(256);
    }

    @Autowired
    private StudentInfoMapper studentInfoMapper;

    @Autowired
    private TeacherInfoMapper teacherInfoMapper;

    @Autowired
    private CourseInfoMapper courseInfoMapper;

    @PostConstruct
    public void init(){
        List<StudentInfo> studentInfos = studentInfoMapper.selectAll();
        List<TeacherInfo> teacherInfos = teacherInfoMapper.selectAll();
        List<CourseInfo> courseInfos = courseInfoMapper.selectAll();
        for(StudentInfo studentInfo:studentInfos){
            addUserName(studentInfo.getStudentId(),studentInfo.getStudentName());
        }
        for(TeacherInfo teacherInfo:teacherInfos){
            addUserName(teacherInfo.getTeacherId(),teacherInfo.getTeacherName());
        }
        for (CourseInfo courseInfo:courseInfos){
            addCourseName(courseInfo.getId(),courseInfo.getCourseName());
        }
    }

    /**
     * 缓存新增用户访问方法的时间
     * @param userId 用户id
     * @param userName 用户名
     */
    public static void addUserName(String userId,String userName) {
        synchronized (userNameMap) {
            userNameMap.put(userId,userName);
        }
    }

    public static void addCourseName(Integer courseId, String courseName){
        synchronized (courseMap){
            courseMap.put(courseId,courseName);
        }
    }

    /**
     * 缓存新增用户访问方法的时间
     * @param userId 用户id
     * @param method 方法名
     */
    public static void addUserAccessRecord(String userId,String method) {
        synchronized (userAccessRecordMap) {
            Long currentTime = System.currentTimeMillis();
            if (userAccessRecordMap.isEmpty() || !userAccessRecordMap.containsKey(userId)) {
                Map<String,Long> map = new ConcurrentHashMap();
                map.put(method,currentTime);
                userAccessRecordMap.put(userId,map);
            }else {
                Map<String,Long> map = userAccessRecordMap.get(userId);
                map.put(method,currentTime);
            }
        }
    }

    /**
     * 缓存删除用户访问方法的时间
     * @param userId 用户id
     */
    public static void deleteUserAccessRecord(String userId) {
        synchronized (userAccessRecordMap) {
            userAccessRecordMap.remove(userId);
        }
    }

    /**
     * 根据userId和ajax请求获取上一次请求时间
     * @param userId 用户id
     * @param method 请求的ajax方法
     * @return
     */
    public static Long getLastTime(String userId,String method) {
        if(!userAccessRecordMap.isEmpty()){
            Map<String,Long> map = userAccessRecordMap.get(userId);
            if(!map.isEmpty()){
                Long time = map.get(method);
                return time == null ? 0L : time;
            }
        }
        return 0L;
    }


    /**
     *@Description 根据userId获取用户姓名
     *@param userId: 用户id
     *@return 用户姓名
     *@Author 李如豪
     *@Date 2019/3/9
     **/
    public static String getUserName(String userId){
        if(StringUtils.isNotBlank(userId)){
            String userName = userNameMap.get(userId);
            if(StringUtils.isNotBlank(userName)){
                return userName;
            }
        }
        return "";
    }

    /**
     *@Description 根据courseId获取课程名
     *@param courseId: 课程id
     *@return 课程名称
     *@Author 李如豪
     *@Date 2019/3/9
     **/
    public static String getCourseName(Integer courseId){
        if(courseId != null){
            String courseName = courseMap.get(courseId);
            if(StringUtils.isNotBlank(courseName)){
                return courseName;
            }
        }
        return "";
    }
}

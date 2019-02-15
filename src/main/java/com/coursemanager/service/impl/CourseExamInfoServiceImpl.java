package com.coursemanager.service.impl;

import com.coursemanager.controller.BaseController;
import com.coursemanager.dto.CourseExamInfoDto;
import com.coursemanager.dto.ExamInfoDto;
import com.coursemanager.mapper.CourseExamInfoMapper;
import com.coursemanager.mapper.ExamInfoMapper;
import com.coursemanager.mapper.StudentExamInfoMapper;
import com.coursemanager.model.CourseExamInfo;
import com.coursemanager.model.ExamInfo;
import com.coursemanager.model.UserInfo;
import com.coursemanager.service.ICourseExamInfoService;

import com.coursemanager.util.DateUtil;
import com.coursemanager.util.FileUtil;
import com.coursemanager.util.PageRequest;
import com.coursemanager.util.PageResponse;
import com.coursemanager.util.common.*;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 描述:  course_exam_info 对应的Service接口实现类.<br>
 *
 * @author framework generator
 * @date 2018年11月18日
 */
@Service
public class CourseExamInfoServiceImpl extends MyBatisServiceSupport implements ICourseExamInfoService {
    /**
     * 日志记录器
     */
    private static final Logger logger = LoggerFactory.getLogger(CourseExamInfoServiceImpl.class);

    private final CourseExamInfoMapper courseExamInfoMapper;

    private final ExamInfoMapper examInfoMapper;

    private final StudentExamInfoMapper studentExamInfoMapper;

    @Autowired
    public CourseExamInfoServiceImpl(CourseExamInfoMapper courseExamInfoMapper, ExamInfoMapper examInfoMapper, StudentExamInfoMapper studentExamInfoMapper) {
        this.courseExamInfoMapper = courseExamInfoMapper;
        this.examInfoMapper = examInfoMapper;
        this.studentExamInfoMapper = studentExamInfoMapper;
    }

    @Override
    public PageResponse queryExamList(UserInfo user , HttpServletRequest request) {
        PageHelper.startPage(Integer.parseInt(request.getParameter("offset"))/10+1,Integer.parseInt(request.getParameter("limit")));
        String userId = user.getAccessToken();
        Map<String,Object> map = RequestUtil.getParameterMap(request);
        map.put("studentId",userId);
        if(map.get("courseId").equals("-1")){
            map.remove("courseId");
        }
        List<ExamInfoDto> courseExamInfos = examInfoMapper.queryCourseExamList(map);
        File file;String path = FileUtil.TEMP_PATH + "teacher/homework/";
        for(ExamInfoDto exam : courseExamInfos){
            file = new File(path + exam.getId() + "/file");
            if(file.exists() && file.isDirectory() && file.listFiles() !=null && Objects.requireNonNull(file.listFiles()).length>0)
                exam.setFilePath(StringUtils.join(file.list(),"|"));
            file = new File(path + exam.getId() + "/answer");
            if(file.exists() && file.isDirectory() && file.listFiles() !=null && Objects.requireNonNull(file.listFiles()).length>0)
                exam.setAnswerPath(StringUtils.join(file.list(),"|"));
        }
        if(courseExamInfos.isEmpty()){
            logger.debug("[queryExamList] 该课程暂无作业");
            return new PageResponse<>(courseExamInfos);
        }else {
            map.put("courseExamList", courseExamInfos);
            List<ExamInfoDto> examList = examInfoMapper.queryExamList(map);
            for (ExamInfoDto exam : examList) {
                exam.setIdentifyName(exam.queryIdentifyTypeName());
                exam.setSubmitName(exam.querySubmitTypeName());
                exam.setAnswerPath(null);
                exam.setFilePath(null);
            }
            PageResponse<ExamInfoDto> pageResponse = new PageResponse<>(courseExamInfos);
            courseExamInfos.addAll(examList);
            pageResponse.setRows(courseExamInfos);
            return pageResponse;
        }
    }

    @Override
    @Transactional
    public List<ExamInfoDto> newHomeworkExample(HttpServletRequest request, UserInfo user) {
        String courseExamId = request.getParameter("courseExamId");
        String courseId = request.getParameter("courseId");
        Page<ExamInfoDto> list;
        if(StringUtils.isBlank(courseExamId)){
            logger.debug("[newHomeworkExample] 该课程作业暂无创建题目");
            Example example = new Example(CourseExamInfo.class);
            example.createCriteria().andEqualTo("teacherId",user.getAccessToken()).andEqualTo("status",3);
            example.orderBy("createTime");
            CourseExamInfo courseExamInfo = courseExamInfoMapper.selectOneByExample(example);
            list = new Page<>();
            ExamInfoDto dto = new ExamInfoDto();
            if(courseExamInfo == null){
                logger.debug("[newHomeworkExample] 该课程作业暂未创建，现在开始创建该课程作业");
                courseExamInfo = new CourseExamInfo();
                courseExamInfo.setCreateTime(new Date());
                courseExamInfo.setStatus(3);
                courseExamInfo.setTeacherId(user.getAccessToken());
                courseExamInfo.setCourseId(courseId);
                courseExamInfoMapper.insert(courseExamInfo);
            }
            dto.setCourseExamId(courseExamInfo.getId());
            list.add(dto);
            return list;
        }else{
            Map<String,Object> map = new HashMap<>();
            map.put("courseExamId",courseExamId);
            map.put("courseId",courseId);
            list = examInfoMapper.getCourseExamList(map);
            for (ExamInfoDto exam : list) {
                exam.setIdentifyName(exam.queryIdentifyTypeName());
                exam.setSubmitName(exam.querySubmitTypeName());
            }
            list.setTotal(list.size());
            return list;
        }
    }

    @Override
    public Integer createExamInfo(HttpServletRequest request,ExamInfo examInfo){
        Map<String,Object> map = RequestUtil.getParameterMap(request);
        String courseExamId = request.getParameter("courseExamId");

        String inputsAndAnswers = request.getParameter("inputs");
        if(!inputsAndAnswers.contains("input") || !inputsAndAnswers.contains("output")){
            logger.debug("[createExamInfo] 测试用例格式错误");
            return -1;
        }
        String inputs  = inputsAndAnswers.substring(inputsAndAnswers.indexOf("input:")+6,inputsAndAnswers.indexOf("output:")-1);
        String outputs = inputsAndAnswers.substring(inputsAndAnswers.indexOf("output:")+7);
        examInfo.setAnswer(outputs);
        examInfo.setInputs(inputs);
        examInfo.setStatus(3);
        examInfo.setCourseExamId(Integer.parseInt(courseExamId));
        int num;
        if(StringUtils.isBlank(examInfo.getExamId())){
            examInfo.setExamId(getNumUUId());
            num = examInfoMapper.insert(examInfo);
        }else{
            num = examInfoMapper.updateByPrimaryKey(examInfo);
        }
        String filePath = "teacher/homework/" + courseExamId + "/file/"+examInfo.getExamId();
        if(num == 1 ){
            FileUtil.uploadFile(request,filePath,"file","作业附件");
        }else{
            logger.debug("[createExamInfo] 上传文件失败，未找到表单文件或数据库操作失败");
        }
        return num;
    }

    @Override
    @Transactional
    public Integer createNewHomework(HttpServletRequest request) {
        String courseId = request.getParameter("courseId");
        String expireTime = request.getParameter("expireTime");
        String courseExamId = request.getParameter("courseExamId");
        String filePath = "teacher/homework/" + courseExamId + "/answer";
        Date showAnswerTime = DateUtil.stringToDate(request.getParameter("showAnswerTime"),DateUtil.DEFAULT_DATE_FORMAT);
        if(StringUtils.isBlank(courseId) || StringUtils.isBlank(expireTime) || StringUtils.isBlank(courseExamId)){
            logger.debug("[createNewHomework] 参数存在空值");
            return -1;
        }
        Example e = new Example(ExamInfo.class);
        e.createCriteria().andEqualTo("status",3).andEqualTo("courseExamId",courseExamId);
        List<ExamInfo> examInfoList = examInfoMapper.selectByExample(e);
        for (ExamInfo examInfo : examInfoList){
            examInfo.setStatus(1);
            examInfoMapper.updateByPrimaryKey(examInfo);
        }
        CourseExamInfo courseExamInfo = new CourseExamInfo();
        courseExamInfo.setId(Integer.parseInt(courseExamId));
        courseExamInfo = courseExamInfoMapper.selectByPrimaryKey(courseExamInfo);
        courseExamInfo.setStatus(0);
        Example example = new Example(CourseExamInfo.class);
        example.createCriteria().andEqualTo("courseId",courseId);
        example.and(example.createCriteria().orEqualTo("status",0).orEqualTo("status",1));
        courseExamInfo.setExamNumber(courseExamInfoMapper.selectCountByExample(example)+1);
        courseExamInfo.setCourseId(courseId);
        courseExamInfo.setShowAnswerTime(showAnswerTime);
        Date date = DateUtil.stringToDate(expireTime,DateUtil.DEFAULT_DATETIME_FORMAT);
        courseExamInfo.setExpireTime(date);
        int num = courseExamInfoMapper.updateByPrimaryKey(courseExamInfo);
        if(num == 1)
            FileUtil.uploadFile(request,filePath,"answer","答案");
        return num;
    }

    @Override
    public List<CourseExamInfoDto> queryHistoryHomeworkList(PageRequest request) {
        PageHelper.startPage(request.getPageNumber(),request.getLimit());
        Map<String,Object> params = request.getData();
        List<CourseExamInfoDto> list = courseExamInfoMapper.queryHistoryHomeworkList(params);
        return list;
    }

    /**
     * @Author 李如豪
     * @Description 正则匹配html中img的src,并替换成Base64
     * @Date 14:02 2019/1/17
     * @Param 一段html
     * @return 储存src的有序集合
     **/
    public static String getImgStrToBase64(String htmlStr) {
        String img = "";
        Pattern p_image;
        Matcher m_image;
        String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
        p_image = Pattern.compile
                (regEx_img, Pattern.CASE_INSENSITIVE);
        m_image = p_image.matcher(htmlStr);
        String result = htmlStr;
        while (m_image.find()) {
            // 得到<img />数据
            img = m_image.group();
            // 匹配<img>中的src数据
            Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
            while (m.find()) {
                String src = m.group(1);
                //String replace = CommonUtils.imgToBase64(src.replace("/showImg/","" )).replaceAll("\r\n","");\
                //result = result.replaceAll(m.group(1),"data:image/png;base64,"+replace);
                String replaceStr = src.substring(src.lastIndexOf("/")+1);
                result = result.replaceAll(m.group(1),replaceStr);
            }
        }
        return result;
    }

    @Override
    public String downloadHistoryHomework(String courseExamId, String courseExamName, HttpServletResponse response) {
        if(StringUtils.isBlank(courseExamId)){
            logger.debug("[downloadHistoryHomework] 课程作业id为空");
            return null;
        }
        CourseExamInfo courseExamInfo = new CourseExamInfo();
        courseExamInfo.setId(Integer.parseInt(courseExamId));
        courseExamInfo = courseExamInfoMapper.selectByPrimaryKey(courseExamInfo);
        String content = BaseController.readFile(Constant.HISTORY_HOMEWORK_TEMPLATE_PATH,null);
        Example example = new Example(ExamInfo.class);
        example.createCriteria().andEqualTo("courseExamId",courseExamId);
        List<ExamInfo> examInfoList = examInfoMapper.selectByExample(example);
        StringBuilder str = new StringBuilder();
        String resource = Constant.RESOURCE_TEMP + "teacher/homework/"+courseExamId +"/";
        int i = 1;
        for(ExamInfo examInfo : examInfoList){
            str.append(i++ + "、").append(examInfo.getExamTitle()).append("\r\n\t\t");
            if(examInfo.getExamContent().contains("<img")){
                examInfo.setExamContent(getImgStrToBase64(examInfo.getExamContent()));
            }
            str.append(examInfo.getExamContent().replaceAll("<br>",""))
                    .append("\r\n\r\n\r\n");
        }
        content = content.replace("<content>",str.toString()).replace("<title>",courseExamName);
        PDFUtil pdfUtil = new PDFUtil();
        pdfUtil.createPdf(content,resource.replaceFirst("/",""),response);
        return null;
    }

    @Override
    public PageResponse<ExamInfoDto> editHomeworkList(HttpServletRequest request, UserInfo user) {
        PageHelper.startPage(Integer.parseInt(request.getParameter("offset"))/10+1,Integer.parseInt(request.getParameter("limit")));
        String userId = user.getAccessToken();
        List<ExamInfoDto> courseExamInfos = examInfoMapper.queryCourseExamListByTeacher(userId);
        File file;String path = FileUtil.TEMP_PATH + "teacher/homework/";
        for(ExamInfoDto exam : courseExamInfos){
            file = new File(path + exam.getId() + "/file");
            if(file.exists() && file.isDirectory() && file.listFiles() !=null && Objects.requireNonNull(file.listFiles()).length>0)
                exam.setFilePath(StringUtils.join(file.list(),"|"));
            file = new File(path + exam.getId() + "/answer");
            if(file.exists() && file.isDirectory() && file.listFiles() !=null && Objects.requireNonNull(file.listFiles()).length>0)
                exam.setAnswerPath(StringUtils.join(file.list(),"|"));
        }
        if(courseExamInfos.isEmpty()){
            logger.debug("[queryExamList] 该课程暂无作业");
            return new PageResponse<>(courseExamInfos);
        }else {
            Map<String,Object> map = new HashMap<>();
            map.put("courseExamList", courseExamInfos);
            List<ExamInfoDto> examList = examInfoMapper.queryExamList(map);
            for (ExamInfoDto exam : examList) {
                exam.setIdentifyName(exam.queryIdentifyTypeName());
                exam.setSubmitName(exam.querySubmitTypeName());
                exam.setAnswerPath(null);
                exam.setFilePath(null);
            }
            PageResponse<ExamInfoDto> pageResponse = new PageResponse<>(courseExamInfos);
            courseExamInfos.addAll(examList);
            pageResponse.setRows(courseExamInfos);
            return pageResponse;
        }
    }
}
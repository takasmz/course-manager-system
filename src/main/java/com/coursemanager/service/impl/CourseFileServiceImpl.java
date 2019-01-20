package com.coursemanager.service.impl;

import com.coursemanager.dto.CourseFileDto;
import com.coursemanager.mapper.CourseFileMapper;
import com.coursemanager.model.CourseFile;
import com.coursemanager.service.ICourseFileService;
import com.coursemanager.util.PageRequest;
import com.coursemanager.util.common.RequestUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 描述:  course_file 对应的Service接口实现类.<br>
 *
 * @author framework generator
 * @date 2019年01月15日
 */
@Service
public class CourseFileServiceImpl implements ICourseFileService {
    private static final Logger logger = LoggerFactory.getLogger(CourseFileServiceImpl.class);

    @Autowired
    private CourseFileMapper courseFileMapper;

    @Override
    public List<CourseFileDto> queryResourceList(HttpServletRequest request) {
        String type = request.getParameter("type");
        String courseId = request.getParameter("courseId");
        Map<String,Object> params = RequestUtil.getParameterMap(request);
        if(StringUtils.isBlank(type) || StringUtils.isBlank(courseId)){
            logger.debug("[queryResourceList] 参数为空");
            return null;
        }
        if(courseId.equals("-1")){
            courseId = "";
        }
        params.put("courseId",courseId);
        Page<CourseFileDto> page = new Page<>();
        Integer num = courseFileMapper.selectFileCount(params);
        List<CourseFileDto> list = courseFileMapper.queryResourceList(params);
        for(CourseFileDto courseFileDto : list){
            String fileExtName = courseFileDto.getFileName().
                    substring(courseFileDto.getFileName().lastIndexOf(".")+1);
            switch (fileExtName){
                case ("ppt"):
                case ("pptx"):{
                    courseFileDto.setIcon("PPT1");
                    break;
                }
                case ("doc"):
                case ("docx"):{
                    courseFileDto.setIcon("WORD");
                    break;
                }
                case ("xlsx"):
                case ("xls"):{
                    courseFileDto.setIcon("Microsoft-Excel");
                    break;
                }
                case ("zip"):
                case ("rar"):
                case ("rar5"):{
                    courseFileDto.setIcon("yasuobao1");
                    break;
                }
                case ("mp4"):
                case ("mvn"):
                case ("avi"):
                case ("mp3"):{
                    courseFileDto.setIcon("yinpin");
                    break;
                }
                case ("pdf"):{
                    courseFileDto.setIcon("PDF");
                    break;
                }
                default:{
                    courseFileDto.setIcon("wenjian");
                    break;
                }
            }
            page.add(courseFileDto);
        }
        page.setTotal(num);
        return page;
    }
}
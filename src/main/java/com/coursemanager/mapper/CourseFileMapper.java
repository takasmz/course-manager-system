package com.coursemanager.mapper;

import com.coursemanager.dto.CourseFileDto;
import com.coursemanager.model.CourseFile;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface CourseFileMapper extends Mapper<CourseFile> {
    List<CourseFileDto> queryResourceList(Map<String,Object> params);

    Integer selectFileCount(Map<String, Object> params);
}
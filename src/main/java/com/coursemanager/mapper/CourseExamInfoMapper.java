/**
 * @author framework generator
 * @date 2018年11月18日
 * @version 2.0
 */
package com.coursemanager.mapper;

import com.coursemanager.dto.CourseExamInfoDto;
import com.coursemanager.model.CourseExamInfo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 描述:  course_exam_info 对应的Mapper类.<br>
 *
 * @author framework generator
 * @date 2018年11月18日
 */
public interface CourseExamInfoMapper extends Mapper<CourseExamInfo> {

    CourseExamInfo selectByExamId(String examId);

    List<CourseExamInfoDto> queryHistoryHomeworkList(Map<String, Object> params);
}
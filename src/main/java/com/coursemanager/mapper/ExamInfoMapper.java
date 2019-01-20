/**
 * @author framework generator
 * @date 2018年11月18日
 * @version 2.0
 */
package com.coursemanager.mapper;

import com.coursemanager.dto.ExamInfoDto;
import com.coursemanager.model.ExamInfo;
import com.github.pagehelper.Page;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 描述:  exam_info 对应的Mapper类.<br>
 *
 * @author framework generator
 * @date 2018年11月18日
 */
public interface ExamInfoMapper extends Mapper<ExamInfo> {

    List<ExamInfoDto> queryCourseExamList(Map<String, Object> map);
    List<ExamInfoDto> queryExamList(Map<String, Object> map);

    Page<ExamInfoDto> getCourseExamList(Map<String, Object> map);

    ExamInfoDto queryExamById(String examId);
}
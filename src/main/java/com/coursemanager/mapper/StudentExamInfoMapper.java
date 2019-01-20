/**
 * @author framework generator
 * @date 2018年11月18日
 * @version 2.0
 */
package com.coursemanager.mapper;

import com.coursemanager.dto.StudentExamInfoDto;
import com.coursemanager.model.CourseInfo;
import com.coursemanager.model.StudentCourse;
import com.coursemanager.model.StudentExamInfo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 描述:  student_exam_info 对应的Mapper类.<br>
 *
 * @author framework generator
 * @date 2018年11月18日
 */
public interface StudentExamInfoMapper extends Mapper<StudentExamInfo> {
    CourseInfo getCourseNameByexamId(String examId);

    List<StudentExamInfoDto> getRecordList(@Param("studentId") String studentId, @Param("courseId") String courseId);

    List<StudentExamInfoDto> getStudentRecordList(@Param("studentId") String studentId, @Param("courseId") String courseId, @Param("list") List<StudentCourse> studentCourseList);

    List<StudentExamInfoDto> queryHomeworkRecordDetail(@Param("courseExamId")String courseExamId,@Param("studentId") String studentId);
}
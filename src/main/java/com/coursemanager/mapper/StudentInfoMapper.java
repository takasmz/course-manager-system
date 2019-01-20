/**
 * @author framework generator
 * @date 2018年11月18日
 * @version 2.0
 */
package com.coursemanager.mapper;

import com.coursemanager.model.StudentInfo;
import tk.mybatis.mapper.common.Mapper;

/**
 * 描述:  student_info 对应的Mapper类.<br>
 *
 * @author framework generator
 * @date 2018年11月18日
 */
public interface StudentInfoMapper extends Mapper<StudentInfo> {

	Object selectUserByaccessToken(String access_token);
}
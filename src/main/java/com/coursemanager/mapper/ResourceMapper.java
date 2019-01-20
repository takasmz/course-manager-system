/**
 * @author framework generator
 * @date 2018年11月28日
 * @version 2.0
 */
package com.coursemanager.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.coursemanager.model.Resource;
import tk.mybatis.mapper.common.Mapper;

/**
 * 描述:  resource 对应的Mapper类.<br>
 *
 * @author framework generator
 * @date 2018年11月28日
 */
public interface ResourceMapper extends Mapper<Resource> {

	List<Resource> selectResource(@Param("type") Integer type);
}
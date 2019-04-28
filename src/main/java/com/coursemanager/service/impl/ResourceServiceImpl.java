package com.coursemanager.service.impl;

import com.coursemanager.dto.MenuDto;
import com.coursemanager.mapper.ResourceMapper;
import com.coursemanager.model.Resource;
import com.coursemanager.service.IResourceService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 描述:  resource 对应的Service接口实现类.<br>
 *
 * @author framework generator
 * @date 2018年11月28日
 */
@Service
public class ResourceServiceImpl extends MyBatisServiceSupport implements IResourceService {
    /**
     * 日志记录器
     */
    private static final Logger logger = LoggerFactory.getLogger(ResourceServiceImpl.class);

    @Autowired
    private ResourceMapper resourceMapper;

	@Override
	public List<MenuDto> queryMenuJson(Integer type) {
		List<Resource> list = resourceMapper.selectResource(type);
		List<MenuDto> result;
		Map<Integer,MenuDto> map = new HashMap<>();
		for(Resource res:list) {
			MenuDto menu = new MenuDto(res);
			if(res.getIsParent() == 1) {
				if(res.getParent() == 0) {
//					result.add(res.getId(), menu);
					map.put(res.getId(), menu);
					List<Resource> childList = list.stream()
							.filter(t -> t.getParent().equals(res.getId()) && t.getIsParent() == 0)
							.collect(Collectors.toList());
					menu.setChildren(childList);
				}else {
					List<Resource> childList = list.stream().filter(t -> t.getParent().equals(res.getId())).collect(Collectors.toList());
					menu.setChildren(childList);
					map.get(res.getParent()).addListItem(menu);
				}
			}
		}
		result = new ArrayList<>(map.values());
		return result;
	}
}
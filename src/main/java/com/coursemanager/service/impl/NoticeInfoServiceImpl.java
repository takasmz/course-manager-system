package com.coursemanager.service.impl;

import com.coursemanager.mapper.NoticeInfoMapper;
import com.coursemanager.model.NoticeInfo;
import com.coursemanager.model.UserInfo;
import com.coursemanager.service.INoticeInfoService;
import com.coursemanager.util.PageResponse;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 描述:  notice_info 对应的Service接口实现类.<br>
 *
 * @author framework generator
 * @date 2019年03月09日
 */
@Service
public class NoticeInfoServiceImpl extends MyBatisServiceSupport implements INoticeInfoService {
    /**
     * 日志记录器
     */
    private static final Logger logger = LoggerFactory.getLogger(NoticeInfoServiceImpl.class);

    @Autowired
    private NoticeInfoMapper noticeInfoMapper;

    @Override
    public PageResponse<NoticeInfo> queryNoticeList(UserInfo user, HttpServletRequest request) {
        if(checkPageRequest(request)){
            logger.debug("[]");
        }
        PageHelper.startPage(Integer.parseInt(request.getParameter("offset"))/10+1,
                Integer.parseInt(request.getParameter("limit")));
        Example example = new Example(NoticeInfo.class);
        example.createCriteria().andEqualTo("useStatus",1).andEqualTo("userId",user.getAccessToken());
        List<NoticeInfo> noticeInfos =noticeInfoMapper.selectByExample(example);
        return new PageResponse<>(noticeInfos);
    }
}
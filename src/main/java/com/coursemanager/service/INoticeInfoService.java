package com.coursemanager.service;

import com.coursemanager.model.NoticeInfo;
import com.coursemanager.model.UserInfo;
import com.coursemanager.util.PageResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * 描述:  notice_info 对应的Service类.<br>
 *
 * @author framework generator
 * @date 2019年03月09日
 */
public interface INoticeInfoService extends BaseService {
    PageResponse<NoticeInfo> queryNoticeList(UserInfo user, HttpServletRequest request);
}
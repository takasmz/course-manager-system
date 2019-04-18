package com.coursemanager.controller;

import com.coursemanager.model.NoticeInfo;
import com.coursemanager.model.UserInfo;
import com.coursemanager.service.INoticeInfoService;
import com.coursemanager.util.PageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 描述:  notice_info 对应的Controller类.<br>
 *
 * @author framework generator
 * @date 2019年03月09日
 */
@Controller
@RequestMapping("/notice")
public class NoticeInfoController extends BaseController {
    /**
     * 日志记录器
     */
    private static final Logger logger = LoggerFactory.getLogger(NoticeInfoController.class);

    @Autowired
    private INoticeInfoService noticeInfoService;

    @RequestMapping("/queryList")
    @ResponseBody
    public PageResponse<NoticeInfo> queryNoticeList(HttpServletRequest request){
        UserInfo user = getUser(request);
        return noticeInfoService.queryNoticeList(user,request);
    }
}
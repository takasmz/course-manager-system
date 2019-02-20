package com.coursemanager.filter;

import com.alibaba.fastjson.JSONObject;
import com.coursemanager.cache.DataCache;
import com.coursemanager.util.common.AjaxResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.servlet.AdviceFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coursemanager.util.common.Constant;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Author 李如豪
 * @Date 2019/2/19 15:39
 * @VERSION 1.0
 **/


public class repeatFilter extends AdviceFilter {
    Logger logger = LoggerFactory.getLogger(repeatFilter.class);

    /**
     * @Author 李如豪
     * @Description 拦截前处理，防止一个请求在短时间内重复提交
     * @Date 15:56 2019/1/18
     * @Param HTTP 请求 和 回复
     **/
    @Override
    protected boolean preHandle(ServletRequest req, ServletResponse resp) throws Exception {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)resp;
        HttpSession session = request.getSession();
        String requestType = request.getHeader("X-Requested-With");
        if("XMLHttpRequest".equals(requestType)){
            String token = (String) session.getAttribute(Constant.USERID);
            String requestUrl = request.getRequestURI();
            long time = DataCache.getLastTime(token,requestUrl);
            DataCache.addUserAccessRecord(token,requestUrl);
            if(time == 0){
                return true;
            }else{
                if(System.currentTimeMillis() - time < 10000){
                    return true;
                }else{
                    AjaxResponse ajaxResponse = AjaxResponse.error("您操作的太快了，请稍后再试");
                    response.setCharacterEncoding("UTF-8");
                    response.setContentType("application/json");
                    response.getWriter().write(JSONObject.toJSONString(ajaxResponse));
                    return false;
                }
            }
        }else{
            //如果请求类型不是ajax，那么此时requestType为null
            logger.info("非AJAX请求!");
        }
        return true;
    }

    /**
     * 后处理，类似于AOP中的后置返回增强
     * 在拦截器链执行完成后执行
     * 一般用于记录时间。
     */
    @Override
    protected void postHandle(ServletRequest request, ServletResponse response)
            throws Exception {
        super.postHandle(request, response);
    }

    /**
     * 最终处理，一定会执行的，一般用于释放资源。
     * 先留着
     */
    @Override
    public void afterCompletion(ServletRequest request, ServletResponse response, Exception exception)
            throws Exception {
        super.afterCompletion(request, response, exception);
    }
}

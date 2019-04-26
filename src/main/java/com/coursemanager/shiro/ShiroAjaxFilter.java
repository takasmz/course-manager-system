package com.coursemanager.shiro;

import com.alibaba.fastjson.JSONObject;
import com.coursemanager.cache.DataCache;
import com.coursemanager.util.common.AjaxResponse;
import com.coursemanager.util.common.Constant;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.servlet.AdviceFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author 李如豪
 * @Date 2019/1/18 15:54
 * @VERSION 1.0
 **/
@Component
public class ShiroAjaxFilter extends AdviceFilter {
    private Logger logger = LoggerFactory.getLogger(ShiroAjaxFilter.class);

    /**
     * @Author 李如豪
     * @Description 拦截前处理，判断是否是ajax请求，并阻止短时间多次请求
     * @Date 15:56 2019/1/18
     * @Param HTTP 请求 和 回复
     **/
    @Override
    protected boolean preHandle(ServletRequest req, ServletResponse resp) throws Exception {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)resp;
        //获取请求头中的信息，ajax请求最大的特点就是请求头中多了 X-Requested-With 参数
        String requestType = request.getHeader("X-Requested-With");
        HttpSession session = request.getSession();
        String url = request.getRequestURI();
        List<String> filterUrl = new ArrayList<>();
        filterUrl.add("/templates/.*");
        filterUrl.add("/static/config.js");
        filterUrl.add("/js/.*");
        filterUrl.add("/css/.*");
        filterUrl.add("/user/.*");
        filterUrl.add("/index");
        filterUrl.add("/captcha");
        filterUrl.add("/views/.*");
        if(filterUrl.stream().anyMatch(url::matches)){
            return true;
        }else{
            if("XMLHttpRequest".equals(requestType)){
                logger.info("{{}}:本次请求是AJAX请求!",url);
                //现在的用途就是判断当前用户是否被认证
                //先获取到由Web容器管理的subject对象
                Subject subject = SecurityUtils.getSubject();
                //判断是否已经认证
                boolean isAuthc = subject.isAuthenticated();
                if(!isAuthc){
                    logger.info("当前账户使用Shiro认证失败!");
                    //如果当前账户没有被认证,本次请求被驳回，ajax进入error的function中进行重定向到登录界面。
                    return false;
                }
                String token = (String) session.getAttribute(Constant.USERID);
                String requestUrl = request.getRequestURI();
                long time = DataCache.getLastTime(token,requestUrl);
                DataCache.addUserAccessRecord(token,requestUrl);
                if(time == 0){
                    return true;
                }else{
                    if(System.currentTimeMillis() - time > 100){
                        return true;
                    }else{
                        logger.info("{{}}:本次请求操作太快!",url);
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

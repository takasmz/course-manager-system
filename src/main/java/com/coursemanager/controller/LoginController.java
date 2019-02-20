package com.coursemanager.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.coursemanager.cache.DataCache;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.coursemanager.dto.LoginDto;
import com.coursemanager.dto.RegisterDto;
import com.coursemanager.model.StudentInfo;
import com.coursemanager.model.TeacherInfo;
import com.coursemanager.model.UserInfo;
import com.coursemanager.service.IUserInfoService;
import com.coursemanager.util.EncryptUtil;
import com.coursemanager.util.MailUtil;
import com.coursemanager.util.common.AjaxResponse;
import com.coursemanager.util.common.Constant;
import com.coursemanager.util.common.DESCryptography;

@Controller
@RequestMapping("/user")
public class LoginController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private IUserInfoService userInfoService;

	/**
	 * @Author 李如豪
	 * @Description 学生注册账号
	 * @Date 11:21 2019/1/22
	 * @Param session , RegisterDto 注册实体 ,result 错误信息
	 * @return 注册结果
	 **/
    @RequestMapping("/register")
    @ResponseBody
    public AjaxResponse register(HttpSession session, @Valid RegisterDto RegisterDto,BindingResult result){
    	logger.debug("用户注册");
    	if (result.hasErrors()) {
            AjaxResponse error = AjaxResponse.error("参数验证错误");
            error.addErrorInfo(result.getAllErrors());
            return error;
        }
    	//验证码校验
        String checkCode = session.getAttribute(DefaultCaptchaController.getSessionKey()) + "";
        if (!RegisterDto.getVercode().equalsIgnoreCase(checkCode)) {
            return AjaxResponse.error("验证码输入错误");
        }
        StudentInfo User= userInfoService.selectByUserName(RegisterDto.getUsername());
        if(User == null){
            return AjaxResponse.error("注册失败，该学号无法注册");
        }
        String uid = userInfoService.insertStudent(RegisterDto);
        //new Thread(new MailUtil(RegisterDto.getEmail(), uid)).start();;
        session.setAttribute(Constant.USER, User);
        return AjaxResponse.success("注册成功",User);
    }

	
    /**
     * 检查用户名是否存在数据库中
     * @param username 用户名
     * @return 结果
     */
    @RequestMapping(value = "/regNameCheck", method = RequestMethod.POST)
    @ResponseBody
	public boolean regNameCheck(String username){
		return userInfoService.selectByUserName(username) != null;
	}
    
    /**
     * 验证码校验
     * @param vercode
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/vercodeCheck", method = RequestMethod.POST)
    @ResponseBody
	public boolean vercodeCheck(String vercode , HttpSession session) throws Exception{
    	//验证码校验
        String checkCode = session.getAttribute(DefaultCaptchaController.getSessionKey()) + "";
        if (!vercode.equalsIgnoreCase(checkCode)) 
            return false;
        return true;
	}
    
    @RequestMapping("/login")
    @ResponseBody
    public AjaxResponse login(HttpServletRequest request, HttpServletResponse response, @Valid LoginDto loginDto, BindingResult result) {
    	logger.debug("系统用户登录");
        if (result.hasErrors()) {
            AjaxResponse error = AjaxResponse.error("参数验证错误");
            error.addErrorInfo(result.getAllErrors());
            return error;
        }
        //验证码校验
        HttpSession session = request.getSession();
        String checkCode = session.getAttribute(DefaultCaptchaController.getSessionKey()) + "";
        if (!loginDto.getVercode().equalsIgnoreCase(checkCode)) {
            return AjaxResponse.error("验证码输入错误");
        }
        session.removeAttribute(DefaultCaptchaController.getSessionKey());
        UserInfo User ;
        String loginType = request.getParameter("loginType");
        if(StringUtils.isBlank(loginType)) {
        	logger.debug("[login] loginType is null");
        	return AjaxResponse.error("未选择登陆方式");
        }
        try {
            User= userInfoService.selectByUserNameAndPassword(loginDto.getUsername(),loginDto.getPassword(),Integer.parseInt(loginType));
            if(User==null){
                return AjaxResponse.error("登录失败,系统中没有该用户信息,请检查用户名或密码是否正确!");
            }
//            if("0".equals(User.getStatus())){
//                return AjaxResponse.error("登录失败,该用户尚未激活!");
//            }
            UsernamePasswordToken loginToken = new UsernamePasswordToken(loginDto.getUsername(), loginDto.getPassword());
            Subject subject = SecurityUtils.getSubject();	
            if (!subject.isAuthenticated()){
            	loginToken.setRememberMe(true);
            	subject.login(loginToken);
            }
            session.setAttribute(Constant.USER, User);
            session.setAttribute(Constant.USERID, User.getAccessToken());
//            //将登陆用户SysUser放入Session
//            UserProfile userProfile = (UserProfile)subject.getPrincipal();
//            SysUserDto sysUser = sysUserService.selectForCredit(userProfile.getUserId());
//            session.setAttribute(Constants.CREDIT_PLATFORM_SESSION_SYS_USER, sysUser);
//            session.setAttribute("headMenu", null);
        } catch (Exception e) {	
            e.printStackTrace();
            return AjaxResponse.error("登录失败，用户名或密码错误!");
        }
        return AjaxResponse.success("登录成功",User);
    }
    
    @RequestMapping("/logout")
    @ResponseBody
    public AjaxResponse logout(HttpServletRequest request) {
        DataCache.deleteUserAccessRecord(getUser(request).getAccessToken());
    	Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        AjaxResponse re = new AjaxResponse();
        re.setCode(1001);
        re.setMsg("注销成功");
    	return re;
    }
    
    
    
    
    /**
     * 跳转找回密码
     * @return
     */
    @RequestMapping({"/forgetView","/forgetView/type=resetpass"})
    public String forget() {
    	return "forget";
    }
    
    /**
     * 重置密码
     */
    @RequestMapping("/resetpass")
    @ResponseBody
    public void resetpass() {
    	
    }
    
    
    
    /**
     * 跳转注册
     * @return
     */
    @RequestMapping("/regView")
    public String regView() {
    	return "reg";
    }
    
    
    /**
     * 发送邮箱验证码
     */
    @RequestMapping("/sms")
    @ResponseBody
    public void sms() {
    	
    }
    
    /**
     * 
     */
    @RequestMapping("/getUser")
    @ResponseBody
    public AjaxResponse getUser(String access_token) {
    	if(StringUtils.isBlank(access_token)) {
    		logger.debug("[getUser] access_token is null");
    	}
    	Object user = userInfoService.selectUserByaccessToken(access_token);
    	if(user != null) {
    		return AjaxResponse.success("获取用户成功",user);
    	}else {
    		return AjaxResponse.error("获取用户失败");
    	}
    }
}

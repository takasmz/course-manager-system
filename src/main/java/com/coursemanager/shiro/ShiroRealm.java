package com.coursemanager.shiro;
 
import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.coursemanager.model.UserInfo;
import com.coursemanager.service.IUserInfoService;
 
/**
 * 登录Realm
 *
 */
public class ShiroRealm extends AuthorizingRealm {
	final static Logger log = LoggerFactory.getLogger(ShiroRealm.class);
	
	@Autowired
	private IUserInfoService userInfoService;

	private boolean permissionsLookupEnabled = true;
	

	
	/**
	 * 登录认证回调函数,登录时调用.
	 * @param authcToken 登录页面参数，用户名和密码等
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		String username = token.getUsername();
		token.setRememberMe(true);
		if (username == null){
			throw new AccountException("账号不能为空");
		}
		//如果用户中心没有用户，则请求校讯通登录接口并查询用户中心对应的用户
		String password = String.valueOf(token.getPassword());
		UserInfo user = userInfoService.loginShiro(username, password);
		if (user != null){
			return new SimpleAuthenticationInfo(user,password,getName());
		}else {
			return null;
		}
		
	}
 
	/**
	 * 登录认证通过后的权限查询函数, 由于目前用户中心前台页面不需要权限控制，所以没写，以后如果需要可扩展
	 * 
	 * @see org.apache.shiro.authz.AuthorizationInfo
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		if (principals == null) {
            throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
        }

		UserInfo user = (UserInfo) getAvailablePrincipal(principals);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Set<String> roleSet = new HashSet<String>();
        String roleName = "";
        if(user.getType()==0) {
        	roleName = "student";
        }else if(user.getType()==1) {
        	roleName = "teacher";
        }else {
        	roleName = "admin";
        }
        roleSet.add(roleName);
        info.setRoles(roleSet);
		System.out.println(roleName);
        log.info("当前登录角色为:"+ roleName);
        return info;
 	}
}

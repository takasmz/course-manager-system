package com.coursemanager.shiro;

import java.util.LinkedHashMap;
import java.util.Map;

import com.coursemanager.exception.LoginException;
import com.coursemanager.shiro.ShiroAjaxFilter;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;

import com.coursemanager.shiro.ShiroRealm;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.HandlerExceptionResolver;
import redis.clients.jedis.JedisPool;

import javax.servlet.Filter;

@Configuration
public class ShiroConfig {

    @Value("${spring.redis.shiro.host}")
    private String host;
    @Value("${spring.redis.shiro.port}")
    private int port;
    @Value("${spring.redis.shiro.timeout}")
    private int timeout;
    @Value("${spring.redis.shiro.password}")
    private String password;

    @Bean
    public FilterRegistrationBean delegatingFilterProxy(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        DelegatingFilterProxy proxy = new DelegatingFilterProxy();
        proxy.setTargetFilterLifecycle(true);
        proxy.setTargetBeanName("shiroFilter");
        filterRegistrationBean.setFilter(proxy);
        return filterRegistrationBean;
    }


    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //自定义拦截器
        Map<String, Filter> filtersMap = shiroFilterFactoryBean.getFilters();
//        filtersMap.put("repeatFilter",new repeatFilter());
        filtersMap.put("roleOrFilter", new ShiroAjaxFilter());

        // setLoginUrl 如果不设置值，默认会自动寻找Web工程根目录下的"/login.jsp"页面 或 "/login" 映射
        shiroFilterFactoryBean.setLoginUrl("/user/unauth");
        // 设置无权限时跳转的 url;
        shiroFilterFactoryBean.setUnauthorizedUrl("/404");

        // 设置拦截器
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/templates/**", "anon");
        filterChainDefinitionMap.put("/static/config.js", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/user/**", "anon");
        filterChainDefinitionMap.put("/index", "anon");
        //验证码接口
        filterChainDefinitionMap.put("/captcha", "anon");
        //开放登陆接口
        filterChainDefinitionMap.put("/views/login", "anon");
        filterChainDefinitionMap.put("/views/forget", "anon");
        //开放注册接口
        filterChainDefinitionMap.put("/views/reg", "anon");
        //开放404接口
        filterChainDefinitionMap.put("/views/404", "anon");
        //老师，需要角色权限 “teacher”
        filterChainDefinitionMap.put("/teacher/**", "roles[teacher]");
        //学生，需要角色权限 “student”
        filterChainDefinitionMap.put("/student/**", "roles[student]");
        //管理员，需要角色权限 “admin”
        filterChainDefinitionMap.put("/admin/**", "roles[admin]");
        //开放登陆接口
        
        //其余接口一律拦截
        //主要这行代码必须放在所有权限设置的最后，不然会导致所有 url 都被拦截
        filterChainDefinitionMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilters(filtersMap);
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        System.out.println("Shiro拦截器工厂类注入成功");
        return shiroFilterFactoryBean;
    }
    
    /**  
     * cookie对象;  
     * rememberMeCookie()方法是设置Cookie的生成模版，比如cookie的name，cookie的有效时间等等。  
     * @return  
    */  
   @Bean  
   public SimpleCookie rememberMeCookie(){  
         //System.out.println("ShiroConfiguration.rememberMeCookie()");  
         //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe  
         SimpleCookie simpleCookie = new SimpleCookie("rememberMe");  
         //<!-- 记住我cookie生效时间30天 ,单位秒;-->  
         simpleCookie.setMaxAge(259200);  
         return simpleCookie;  
   }  
     
   /**  
     * cookie管理对象;  
     * rememberMeManager()方法是生成rememberMe管理器，而且要将这个rememberMe管理器设置到securityManager中  
     * @return  
    */  
   @Bean  
   public CookieRememberMeManager rememberMeManager(){  
         //System.out.println("ShiroConfiguration.rememberMeManager()");  
         CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();  
         cookieRememberMeManager.setCookie(rememberMeCookie());  
         //rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)  
         cookieRememberMeManager.setCipherKey(Base64.decode("2AvVhdsgUs0FSA3SDFAdag=="));  
         return cookieRememberMeManager;  
   }  
     
   @Bean(name = "securityManager")  
   public DefaultWebSecurityManager defaultWebSecurityManager(ShiroRealm realm){  
         DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();  
         //设置realm  
         securityManager.setRealm(realm);  
         //用户授权/认证信息Cache, 采用EhCache缓存  
//         securityManager.setCacheManager(getEhCacheManager());  
         //注入记住我管理器  
         securityManager.setRememberMeManager(rememberMeManager());  
         return securityManager;  
   }

    /**
     * 注入 securityManager
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置realm.
        securityManager.setRealm(ShiroRealm());
        return securityManager;
    }

    /**
     * 自定义身份认证 realm;
     * <p>
     * 必须写这个类，并加上 @Bean 注解，目的是注入 CustomRealm，
     * 否则会影响 CustomRealm类 中其他类的依赖注入
     */
    @Bean
    public ShiroRealm ShiroRealm() {
        return new ShiroRealm();
    }


    //自定义sessionManager
    @Bean
    public SessionManager sessionManager() {
        MySessionManager mySessionManager = new MySessionManager();
        mySessionManager.setSessionDAO(redisSessionDAO());
        return mySessionManager;
    }

    /**
     * 配置shiro redisManager
     * <p>
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setJedisPool(new JedisPool(host,port));
        redisManager.setTimeout(timeout);
        redisManager.setPassword(password);
        return redisManager;
    }

    /**
     * cacheManager 缓存 redis实现
     * <p>
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    @Bean
    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }

    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis
     * <p>
     * 使用的是shiro-redis开源插件
     */
    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }


    /**
     * 注册全局异常处理
     * @return
     */
    @Bean(name = "exceptionHandler")
    public HandlerExceptionResolver handlerExceptionResolver() {
        return new LoginException();
    }


}


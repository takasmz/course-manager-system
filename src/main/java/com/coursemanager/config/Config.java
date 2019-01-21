package com.coursemanager.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.servlet.MultipartConfigElement;

@Configuration
public class Config implements WebMvcConfigurer {
	
	@Autowired
	private DbPropertiesService dbPropertiesService;

	//配置mybatis的分页插件pageHelper
//	@Primary
//	@Bean(name = "mysqlSessionFactory")
//	public SqlSessionFactory mysqlSessionFactory() throws Exception {
//		final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
//		sessionFactory.setDataSource(hikariDataSource());
//		sessionFactory.setMapperLocations(
//				new PathMatchingResourcePatternResolver()
//						.getResources("classpath:mappers/*Mapper.xml"));
//
//		//分页插件
//		Properties properties = new Properties();
//		properties.setProperty("helperDialect", "mysql");
//		properties.setProperty("offsetAsPageNum", "true");
//		properties.setProperty("rowBoundsWithCount", "true");
//		properties.setProperty("reasonable", "true");
//		Interceptor interceptor = new PageInterceptor();
//		interceptor.setProperties(properties);
//		sessionFactory.setPlugins(new Interceptor[] {interceptor});
//
//		return sessionFactory.getObject();
//	}

	/**
	 * 文件上传临时路径
	 */
	@Bean
	MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setLocation("d://coursemanager/temp");
		return factory.createMultipartConfig();
	}

	
	/**
	 * 数据库连接池
	 * @return
	 */
	@Bean
	public HikariDataSource hikariDataSource() {
		HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setJdbcUrl(dbPropertiesService.getJdbcUrl());
		hikariConfig.setDriverClassName(dbPropertiesService.getDriverClassName());
		hikariConfig.setUsername(dbPropertiesService.getUsername());
		hikariConfig.setPassword(dbPropertiesService.getPassword());
		hikariConfig.setMaximumPoolSize(25);
		hikariConfig.setMinimumIdle(15);
		hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
		hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
		hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
		
		HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
		return hikariDataSource;
	}
	
}

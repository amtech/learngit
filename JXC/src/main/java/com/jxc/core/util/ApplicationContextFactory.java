package com.jxc.core.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 获取Bean的工厂类
 * @FileName: ApplicationContextFactory.java
 * @Author: WuF
 * @Version: 1.0
 * @Date: 2012-8-29上午8:46:37
 * @Description: 
 * @Others: 
 * @FuntionList:
 * @History:
 * @Category:
 */
public class ApplicationContextFactory {
	private static ApplicationContext atx;
	
	static{
		atx = new ClassPathXmlApplicationContext("config/spring/*.xml");
	}
		
	public static Object getBean(String beanID){
		return atx.getBean(beanID);
	}
}

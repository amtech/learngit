package com.jxc.core.authority;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.jxc.core.log.LogHelper;

/**
 * 拦截器,用于处理程序权限
 * @FileName: AuthorityInterceptor.java
 * @Author: WuF
 * @Version: 1.0
 * @Date: 2012-3-21下午4:45:48
 * @Description: 
 * @Others: 
 * @FuntionList:
 * @History:
 * @Category:
 */
public class AuthorityInterceptor implements HandlerInterceptor {
	private static Logger log = LogHelper.getCommonLogger();

	private static final String[] DIRECT_URI = { "/login2.htm", "/login.jsp",
			"/index.jsp", "/index.htm", "/index_login.htm", "/login.htm",
			"/register.jsp", "/register.htm" };

	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}

	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
	}

	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object arg2) throws Exception {
		return true;
	}

	public boolean isOpenURI(String path, HttpServletRequest request) {
		log.debug("[C02]Request Path : "+path);
		boolean rf = false;
		for (String url : DIRECT_URI) {
			if ((path != null) && (path.contains(url))) {
				rf = true;
				break;
			}
		}
		return rf;
	}
}

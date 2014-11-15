package com.jxc.core.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.jxc.core.log.LogHelper;
import com.jxc.core.log.Logable;
import com.jxc.core.log.MessageAlertable;
import com.jxc.core.util.Tracer;

/**
 * @FileName: CommonExceptionHandler.java
 * @Author: WuF
 * @Version: 1.0
 * @Date: 2012-3-21下午5:19:30
 * @Description: 公共异常类
 * @Others:
 * @FuntionList:
 * @History:
 * @Category:
 */
public class CommonExceptionHandler implements HandlerExceptionResolver {
	private Logger log = LogHelper.getCommonLogger();
	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object arg2, Exception exception) {
		Tracer.trace("异常" + exception.getMessage());
		exception.printStackTrace();
		this.log.error("异常" + exception.getMessage());
		ModelAndView failModelAndView = new ModelAndView("fail");
		if ((exception instanceof MessageAlertable))
			failModelAndView.addObject("FAIL_KEY", exception.getMessage());
		else {
			failModelAndView.addObject("FAIL_KEY", "系统错误");
		}

		if ((exception instanceof Logable)) {
			this.log.error(exception.getMessage(), exception);
		}
		return failModelAndView;
	}
}

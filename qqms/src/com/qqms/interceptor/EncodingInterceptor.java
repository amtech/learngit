package com.qqms.interceptor;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;


public class EncodingInterceptor extends AbstractInterceptor {
	private static final long	serialVersionUID	= -1491545455517544577L;

	public String intercept(ActionInvocation actionInvocation) throws Exception {
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		ServletActionContext.getRequest().setCharacterEncoding("utf-8");
		return actionInvocation.invoke();
	}

}

package com.jxc.core.exception;

import org.apache.log4j.Logger;

import com.jxc.core.log.LogHelper;
import com.jxc.core.log.Logable;
import com.jxc.core.log.MessageAlertable;

/**
 * @FileName: ServiceException.java
 * @Author: WuF
 * @Version: 1.0
 * @Date: 2012-3-21下午5:21:58
 * @Description: Service公共异常类
 * @Others: 
 * @FuntionList:
 * @History:
 * @Category:
 */
public class ServiceException extends Exception implements MessageAlertable,Logable {
	private static final long serialVersionUID = 1L;
	private Logger log = LogHelper.getCommonLogger();

	public ServiceException(String msg, Throwable e) {
		super(msg, e);
		this.log.error("Service异常:" + msg + e.getMessage());
	}
}

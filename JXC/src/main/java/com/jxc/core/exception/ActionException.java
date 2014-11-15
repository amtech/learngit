package com.jxc.core.exception;

import org.apache.log4j.Logger;

import com.jxc.core.log.LogHelper;
import com.jxc.core.log.Logable;
import com.jxc.core.log.MessageAlertable;

/**
 * Controller异常类
 * @FileName: ActionException.java
 * @Author: WuF
 * @Version: 1.0
 * @Date: 2012-3-21下午4:45:08
 * @Description: 
 * @Others: 
 * @FuntionList:
 * @History:
 * @Category:
 */
public class ActionException extends Exception implements MessageAlertable,Logable {
	private static final long serialVersionUID = 1L;
	private Logger log = LogHelper.getCommonLogger();

	public ActionException(String msg, Throwable e) {
		super(msg, e);
		this.log.error("Controller异常：" + msg + e.getMessage());
	}

	public ActionException(String msg) {
		super(msg);
		this.log.error("Controller异常：" + msg);
	}
}

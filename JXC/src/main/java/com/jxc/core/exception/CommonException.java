package com.jxc.core.exception;

import org.apache.log4j.Logger;

import com.jxc.core.log.LogHelper;

/**
 * @FileName: CommonException.java
 * @Author: WuF
 * @Version: 1.0
 * @Date: 2012-3-21下午5:19:30
 * @Description: 公共异常类
 * @Others:
 * @FuntionList:
 * @History:
 * @Category:
 */
public class CommonException extends Exception {

	
	private static final long serialVersionUID = 1L;
	private Logger log = LogHelper.getCommonLogger();

	public CommonException(String msg, Throwable e) {
		super(msg, e);
		e.printStackTrace();
		this.log.error("[TASK Exception]：\n" + msg + "\n" + e.getMessage());
	}

	public CommonException(String msg) {
		super(msg);
		this.log.error("[TASK Exception]：" + msg);
	}
}

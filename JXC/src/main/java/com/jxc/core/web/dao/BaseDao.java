package com.jxc.core.web.dao;

import org.mybatis.spring.support.SqlSessionDaoSupport;

/**
 * @FileName: BaseDao.java
 * @Author: WuF
 * @Version: 1.0
 * @Date: 2013-2-25上午8:33:37
 * @Description: DAO的基类
 * @Others: 
 * @FuntionList:
 * @History:
 * @Category:
 */
public class BaseDao extends SqlSessionDaoSupport {
	
	public void traceSql(String sql) {
		System.out.println("JDBC:" + sql);
	}
}

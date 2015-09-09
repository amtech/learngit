package com.sun.base;

import org.mybatis.spring.support.SqlSessionDaoSupport;
/**
 * @FileName: BaseDao.java
 * @Author: Sunyiban
 * @Version: 1.0
 * @Date: 2015-09-01 10:54am
 * @Description: DAO的基类
 * @Others: 
 * @FuntionList:
 * @History:
 * @Category:
 */
public class BaseDao extends SqlSessionDaoSupport{
	public void traceSql(String sql) {
		System.out.println("JDBC:" + sql);
	}
}

package com.jxc.web.user.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jxc.core.web.service.BaseService;
import com.jxc.web.user.dao.UserDao;
import com.jxc.web.user.model.CounterEntity;
import com.jxc.web.user.model.UserEntity;
/**
  @File:           ModuleService.java
  @Author:         WuF
  @Version:        v 1.0
  @Date:           2014年8月5日下午2:05:30
  @Description:	模块管理service
 */
@Transactional
@Service
public class UserService extends BaseService {

	@Autowired
	private UserDao userDao;
	
	/**
	 * 保存用户方法
	 * @param users
	 */
	public void saveUser(UserEntity users){
		userDao.saveUserforRegist(users);
	}
	/**
	 * 保存用户柜台关系
	 * @param users
	 */
	public void saveUserCounter(UserEntity users){
		userDao.insertUserCounter(users);
	}
	/**
	 * 查询用户名是否存在
	 * @param username
	 * @return
	 */
	public int queryIsUserNameExist(String username){
		return userDao.queryIsUserNameExist(username);
	}
	public UserDao getUserDao() {
		return this.userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	/**
	 * 查询全部用户
	 * @return
	 */
	public List<UserEntity> queryUser(){
		return userDao.queryUser();
	}
	/**
	 * 登陆成功后获取用户ID，用户名，用户级别，柜台ID
	 * @param username
	 * @return
	 */
	public List<UserEntity> saveUserInfo(String username){
		return userDao.saveUserInfo(username);
	}

	public List<CounterEntity> queryCountry(String id) {
		// TODO Auto-generated method stub
		return userDao.queryCountry(id);
	}
	
	public void updateUser(String username,String newpassword){
		userDao.updateUser(username,newpassword);
	}
	public List<Map> discountday(){
		return userDao.discountday();
	}
	public void changediscountday(Map map){
		userDao.changediscountday(map);
	}
}

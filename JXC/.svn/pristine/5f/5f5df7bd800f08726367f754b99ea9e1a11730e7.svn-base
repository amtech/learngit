package com.jxc.web.user.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.jxc.core.web.dao.BaseDao;
import com.jxc.web.user.dao.UserDao;
import com.jxc.web.user.model.CounterEntity;
import com.jxc.web.user.model.UserEntity;

@Repository
public class UserDaoImpl extends BaseDao implements UserDao {
	
	@Override
	public List<UserEntity> queryUserforLogin(HashMap map) {
		
		return this.getSqlSession().selectList("queryUserforLogin",map);
	}
	//注册，保存用户信息方法
	@Override
	public void saveUserforRegist(UserEntity users) {
		
		this.getSqlSession().insert("saveUserforRegist",users);
	}
	@Override
	public List<UserEntity> queryUser() {
		return this.getSqlSession().selectList("queryUser");
	}
	
	//根据用户名获取用户信息
		@Override
		public List<UserEntity> saveUserInfo(String username) {
			List<UserEntity> list = new ArrayList<UserEntity>();
			list = this.getSqlSession().selectList("saveUserInfo", username);
			return list;
		}
		@Override
		public List<CounterEntity> queryCountry(String id) {
			return this.getSqlSession().selectOne("queryCountry",id);
		}
		@Override
		public void updateUser(String username, String newpassword) {
			this.getSqlSession().update("updateUser",new Object[]{username,newpassword});
		}
		@Override
		public List<Map> discountday() {
			return this.getSqlSession().selectOne("discountday");
		}
		@Override
		public void changediscountday(Map map) {
			this.getSqlSession().update("changediscountday",map);
		}
		@Override
		public void insertUserCounter(UserEntity users) {
			this.getSqlSession().insert("insertUserCounter",users);
		}
		@Override
		public int queryIsUserNameExist(String username) {
			return this.getSqlSession().selectOne("queryIsUserNameExist",username);
		}

}

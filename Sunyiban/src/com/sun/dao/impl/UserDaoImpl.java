package com.sun.dao.impl;

import com.sun.base.BaseDao;
import com.sun.dao.IUserDao;
import com.sun.entity.UserEntity;

public class UserDaoImpl extends BaseDao implements IUserDao {

	@Override
	public int deleteByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(UserEntity record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertSelective(UserEntity record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public UserEntity selectByPrimaryKey(String id) {
		return this.getSqlSession().selectOne("selectByPrimaryKey",id);
	}

	@Override
	public int updateByPrimaryKeySelective(UserEntity record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKey(UserEntity record) {
		// TODO Auto-generated method stub
		return 0;
	}

}

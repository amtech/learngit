package com.sun.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sun.dao.IUserDao;
import com.sun.entity.UserEntity;
import com.sun.service.IUserService;

@Service("userServiceImpl")
public class UserServiceImpl implements IUserService {

	@Autowired  
    private IUserDao userDao;  
	
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
		return this.userDao.selectByPrimaryKey(id);
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

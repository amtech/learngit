package com.sun.service;

import com.sun.entity.UserEntity;

public interface IUserService {
	int deleteByPrimaryKey(String id);

    int insert(UserEntity record);

    int insertSelective(UserEntity record);

    UserEntity selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UserEntity record);

    int updateByPrimaryKey(UserEntity record);
}

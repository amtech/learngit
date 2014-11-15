package com.jxc.web.login.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jxc.core.exception.ServiceException;
import com.jxc.core.web.service.BaseService;
import com.jxc.web.user.dao.UserDao;
import com.jxc.web.user.model.UserEntity;

/**
 * 用来处理登录的业务类
 * @FileName: LoginService.java
 * @Author: WuF
 * @Version: 1.0
 * @Date: 2012-11-12下午7:41:26
 * @Description: 
 * @Others: 
 * @FuntionList:
 * @History:
 * @Category:
 */
@Transactional
@Service
public class LoginService extends BaseService implements Serializable {
	private static final long serialVersionUID = 7359676925243736344L;
	@Autowired
	private UserDao userDao;
	
	public boolean validLogin(String username,String password) throws ServiceException{
		boolean rf = true;
		try {
			HashMap map=new HashMap();
			map.put("username", username);
			map.put("password", password);
			List<UserEntity> list = userDao.queryUserforLogin(map);
			if (list.size()==0) {
				rf = false ;
			} 
		} catch (Exception e) {
			rf = false;
			throw new ServiceException("验证登录失败", e);
		}
		return rf;
	}

}

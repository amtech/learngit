package com.oasys.serviceImpl;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oasys.dao.PublicDao;
import com.oasys.model.Role;
import com.oasys.service.RoleService;
import com.oasys.util.Collections;
import com.oasys.viewModel.ComboBoxModel;
@Service("roleService")
@SuppressWarnings("unchecked")
public class RoleServiceImpl implements RoleService
{

	@Autowired
	private PublicDao<Role> roleDao;
	
	
	@Override
	public List<ComboBoxModel> findRoleList() {
		String hql="from Role";
		 List<Role> roleList = roleDao.find(hql);
		 List<ComboBoxModel> list=new ArrayList<ComboBoxModel>();
		 for (Role role : roleList) {
			//key编码
			 String code=role.getRoleCode();
			 //value
			 String text=role.getName();
			
			ComboBoxModel boxModel=new ComboBoxModel(code, text);
			 list.add(boxModel);
		}
		 return list;
	}

	@Override
	public Role findRoleByCode(String roleCode) {
		String hql = "select r from Role r where r.roleCode='"
				+ roleCode + "'";
		List<Role> list = roleDao.find(hql);
		if (Collections.listIsNotEmpty(list)) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public Role findRoleByRoleId(Integer roleId) {
		// TODO Auto-generated method stub
		return roleDao.get(Role.class, roleId);
	}



	
	
	
	
}

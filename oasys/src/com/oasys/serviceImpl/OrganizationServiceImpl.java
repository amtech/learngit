package com.oasys.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oasys.dao.PublicDao;
import com.oasys.model.Organization;
import com.oasys.model.Users;
import com.oasys.service.OrganizationService;
import com.oasys.service.UserService;
import com.oasys.util.Constants;
import com.oasys.util.HqlUtil;
import com.oasys.viewModel.TreeModel;

/**
 * 
 * 组织机构管理ServiceImpl
 * 
 * @author 刘洪虎 2015/05/07.
 * 
 * @version V1.00.
 * 
 *          更新履历： V1.00 2015/05/07 刘洪虎 创建.
 */
@Service("organizationService")
@SuppressWarnings("unchecked")
public class OrganizationServiceImpl implements OrganizationService {

	/** 自动注入实体共同dao */
	@SuppressWarnings("rawtypes")
	@Autowired
	private PublicDao publicDao;
	
	@Autowired
	private UserService userService;

	public List<TreeModel> findOrganizationList() {
		String hql = "from Organization o where o.status='A'";
		List<Organization> tempList = publicDao.find(hql);
		List<TreeModel> list = new ArrayList<TreeModel>();
		for (Organization o : tempList) {
			TreeModel treeModel = new TreeModel();
			treeModel.setId(o.getOrganizationId() + HqlUtil.NULL_STRING);
			treeModel.setPid(o.getPid() == null ? null : o.getPid().toString());
			treeModel.setName(o.getFullName());
			treeModel.setState(Constants.TREE_STATUS_OPEN);
			treeModel.setIconCls(o.getIconCls());
			list.add(treeModel);
		}
		return list;
	}

	public List<Organization> findOrganizationList(Integer id) {
		String hql = "from Organization o where o.status='A' ";
		if (null == id || "".equals(id)) {
			hql += " and o.pid is null";
		} else {
			hql += " and o.pid=" + id;
		}
		return publicDao.find(hql);
	}
	
	public List<Organization> findOrganizationByIdList(){
		String hql = "from Organization o where o.status='A'";
		List<Organization> tempList = publicDao.find(hql);
		return tempList;
	}
	
	//根据部门ID加载部门名称
	public String getOrgNameByID(Integer id){
		String hql="from Organization where organizationId = "+id;
		List<Organization> orgList = publicDao.find(hql);
		String fullName = "";
		if(null != orgList && orgList.size() > 0){
			fullName = orgList.get(0).getFullName();
		}
		return fullName;
	}
	

	public boolean persistenceOrganization(Organization o) {
		Integer userId = Constants.getCurrendUser().getUserId();
		if (null == o.getOrganizationId() || "".equals(o.getOrganizationId())) {
			o.setCreated(new Date());
			o.setLastmod(new Date());
			o.setCreater(userId);
			o.setModifyer(userId);
			o.setStatus(Constants.PERSISTENCE_STATUS);
			publicDao.save(o);
		} else {
			o.setLastmod(new Date());
			o.setModifyer(userId);
			publicDao.update(o);
		}
		return true;
	}

	@SuppressWarnings("rawtypes")
	public boolean delOrganization(Integer id) {
		String hql = "from Organization o where o.status='A' and o.pid=" + id;
		List<Organization> list = publicDao.find(hql);
		if (list.size() != 0) {
			return false;
		} else {
			String hql2 = "from Users t where t.organizeId=" + id;
			List list2 = publicDao.find(hql2);
			if (list2.size() != 0) {
				return false;
			} else {
				Organization o = (Organization) publicDao.get(
						Organization.class, id);
				o.setStatus(Constants.PERSISTENCE_DELETE_STATUS);
				o.setLastmod(new Date());
				o.setModifyer(Constants.getCurrendUser().getUserId());
				publicDao.deleteToUpdate(o);
			}
			return true;
		}
	}

	@Override
	public String getRegionTypeById(Integer id) {
		// TODO Auto-generated method stub
		String hql = "select t.regionType from Organization t where t.status='A' and t.id="+id;
		String regionType=null;
		List<Object> orgList = publicDao.find(hql);
		if(orgList!=null&&orgList.size()>0){
			regionType = orgList.get(0).toString();
		}
		return regionType;
	}

	//根据部门id加载部门信息
	@Override
	public Organization findOrganizationByOrganizationId(Integer id) {
		return (Organization)publicDao.get(Organization.class, id);
	}
	
	//根据userID加载部门信息
	@Override
	public Organization findOrganizationByUserId(Integer id) {
		Users user = userService.getUserByID(id);
		return findOrganizationByOrganizationId(user.getOrganizeId());
	}

	//根据userID查询部门类型 分部or总部
	@Override
	public String findOrgDeptLevelByUserID(Integer userID) {
		return findOrganizationByUserId(userID).getDeptLevel();
	}
	
	@Override
	public List<Object[]> getIdByName(String name) {
		return publicDao.findBySQL("SELECT * FROM qqms.t_organization WHERE FULL_NAME = '"+name+"'");
	}
	
}

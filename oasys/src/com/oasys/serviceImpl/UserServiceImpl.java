package com.oasys.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.jni.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oasys.dao.PublicDao;
import com.oasys.model.Organization;
import com.oasys.model.Role;
import com.oasys.model.UserRole;
import com.oasys.model.Users;
import com.oasys.service.RoleService;
import com.oasys.service.UserService;
import com.oasys.shiro.ShiroUser;
import com.oasys.util.Collections;
import com.oasys.util.Constants;
import com.oasys.util.HqlUtil;
import com.oasys.util.PageUtil;
import com.oasys.viewModel.ComboBoxModel;
import com.oasys.viewModel.Json;
import com.oasys.viewModel.UserRoleModel;
import com.oasys.viewModel.UsersModel;

@Service("userService")
@SuppressWarnings("rawtypes")
public class UserServiceImpl implements UserService {
	private PublicDao<Users> publicDao;
	private PublicDao publicDaoSQL;
	@Autowired
	private RoleService roleService;
	@Autowired
	private PublicDao<UsersModel> publicDaoUsersModel;
	@Autowired
	private PublicDao<Organization> publicDaoOrganization;
	@Autowired
	public void setPublicDao(PublicDao<Users> publicDao) {
		this.publicDao = publicDao;
	}

	@Autowired
	public void setPublicDaoSQL(PublicDao publicDaoSQL) {
		this.publicDaoSQL = publicDaoSQL;
	}

	public boolean persistenceUsers(Map<String, List<Users>> map) {
		this.addUsers(map.get("addList"));
		this.updUsers(map.get("updList"));
		this.delUsers(map.get("delList"));
		return true;
	}

	/*
	 * 查询列表
	 */
	public List<Users> findAllUserList(Map<String, Object> map,
			PageUtil pageUtil) {
		String hql = "from Users u where u.status='A' ";
		hql += HqlUtil.getSearchConditionsHQL("u", map);
		hql += HqlUtil.getGradeSearchConditionsHQL("u", pageUtil);
		List<Users> list = publicDao.find(hql, map, pageUtil.getPage(),
				pageUtil.getRows());
		for (Users users : list) {
			users.setUserRoles(null);
		}
		return list;
	}

	/**
	 * 查询总条数
	 */
	public Long getCount(Map<String, Object> map, PageUtil pageUtil) {
		String hql = "select count(*) from Users  u where u.status='A' ";
		hql += HqlUtil.getSearchConditionsHQL("u", map);
		hql += HqlUtil.getGradeSearchConditionsHQL("u", pageUtil);
		return publicDao.count(hql, map);
	}

	private boolean addUsers(List<Users> addList) {
		if (addList != null && addList.size() != 0) {
			ShiroUser user = Constants.getCurrendUser();
			for (Users users : addList) {
				users.setCreated(new Date());
				users.setLastmod(new Date());
				users.setLastVisits(new Date());
				users.setCreater(user.getUserId());
				users.setModifyer(user.getUserId());
				users.setStatus(Constants.PERSISTENCE_STATUS);
				publicDao.save(users);
			}
		}
		return true;
	}

	private boolean updUsers(List<Users> updList) {
		if (updList != null && updList.size() != 0) {
			ShiroUser user = Constants.getCurrendUser();
			for (Users users : updList) {
				users.setLastmod(new Date());
				users.setModifyer(user.getUserId());
				publicDao.update(users);
			}
		}
		return true;
	}

	private boolean delUsers(List<Users> delList) {
		ShiroUser user = Constants.getCurrendUser();
		if (delList != null && delList.size() != 0) {
			for (Users users : delList) {
				users.setLastmod(new Date());
				users.setStatus(Constants.PERSISTENCE_DELETE_STATUS);
				users.setModifyer(user.getUserId());
				publicDao.update(users);
			}
		}
		return true;
	}

	/**
	 * 新增和修改
	 */
	public Json persistenceUsers(Users u) {
		Integer userId = Constants.getCurrendUser().getUserId();// 获取当前用户的ID
		String msg = "";
		if (null == u.getUserId() || "".equals(u.getUserId())) {
			u.setPassword(u.getPassword());
			u.setCreated(new Date());
			u.setLastmod(new Date());
			u.setCreater(userId);
			u.setModifyer(userId);
			u.setStatus(Constants.PERSISTENCE_STATUS);
			publicDao.save(u);
			msg = "数据添加成功!";
		} else {
			u.setPassword(u.getPassword());
			u.setLastmod(new Date());
			u.setModifyer(userId);
			publicDao.update(u);
			msg = "数据修改成功!";
		}
		return new Json("提示", msg, true);
	}

	/**
	 * 批量删除多个用户
	 */
	public boolean delUsers(String ids) {
		if (StringUtils.isNotBlank(ids)) {
			String[] idsArray = ids.split(",");
			for (int i = 0; i < idsArray.length; i++) {
				int id = Integer.parseInt(idsArray[i]);
				Users users = publicDao.get(Users.class, id);
				users.setStatus(Constants.PERSISTENCE_DELETE_STATUS);
				users.setLastmod(new Date());
				users.setModifyer(Constants.getCurrendUser().getUserId());
				publicDao.deleteToUpdate(users);
			}
			return true;
		}
		return false;
	}

	public List<UserRoleModel> findUsersRolesList(Integer userId) {
		String sql = "SELECT ur.USER_ID,ur.ROLE_ID FROM\n"
				+ "QQMS.T_USER_AND_ROLE AS ur where ur.STATUS ='A' and ur.USER_ID="
				+ userId;
		List list = publicDaoSQL.findBySQL(sql);
		List<UserRoleModel> listm = getUserRoleModelList(userId, list);
		return listm;
	}

	private List<UserRoleModel> getUserRoleModelList(Integer userId, List list) {
		List<UserRoleModel> listm = new ArrayList<UserRoleModel>();
		for (Object object : list) {
			Object[] obj = (Object[]) object;
			UserRoleModel userRoleModel = new UserRoleModel();
			userRoleModel.setUserId(userId);
			userRoleModel.setRoleId(obj[1] == null ? null : Integer
					.valueOf(obj[1].toString()));
			listm.add(userRoleModel);
		}
		return listm;
	}

	@SuppressWarnings("unchecked")
	public boolean saveUserRoles(Integer userId, String isCheckedIds) {
		Users user = publicDao.get(Users.class, userId);
		Set<UserRole> set = user.getUserRoles();
		Map<Integer, UserRole> map = new HashMap<Integer, UserRole>();
		for (UserRole userRole : set) {
			map.put(userRole.getRole().getRoleId(), userRole);
			userRole.setLastmod(new Date());
			userRole.setStatus(Constants.PERSISTENCE_DELETE_STATUS);
			publicDaoSQL.deleteToUpdate(userRole);
		}
		if (!"".equals(isCheckedIds) && isCheckedIds.length() != 0) {
			String[] ids = isCheckedIds.split(",");
			ShiroUser currUser = Constants.getCurrendUser();
			for (String id : ids) {
				Integer tempId = Integer.valueOf(id);
				Role role = (Role) publicDaoSQL.get(Role.class,
						Integer.valueOf(id));
				UserRole userRole = null;
				if (map.containsKey(tempId)) {
					userRole = map.get(tempId);
					userRole.setStatus(Constants.PERSISTENCE_STATUS);
					userRole.setCreater(currUser.getUserId());
					userRole.setModifyer(currUser.getUserId());
					publicDaoSQL.update(userRole);
				} else {
					userRole = new UserRole();
					userRole.setCreated(new Date());
					userRole.setLastmod(new Date());
					userRole.setRole(role);
					userRole.setUsers(user);
					userRole.setCreater(currUser.getUserId());
					userRole.setModifyer(currUser.getUserId());
					userRole.setStatus(Constants.PERSISTENCE_STATUS);
					publicDaoSQL.save(userRole);
				}
			}
		}
		return true;
	}
	
	/**
	 * 
	 * @time:2015年9月8日 下午2:15:32
	 * @Title:findCountByAccount
	 * @Description:TODO 查询数据库里是否有重名账户（这里描述这个方法的作用）
	 * @param account
	 * @throws:
	 */
	public boolean isExistsAccount(Users u){
		boolean flag = false;
		String hql = "from Users u where u.status='A' and u.account = '"+u.getAccount()+"'";
		Long count = publicDao.count("select count(*)" +hql);
		if(null==u.getUserId() ||"".equals(u.getUserId())){
			//新增操作
			if(count>0){
				flag=true;
			}
		}else{
			//修改操作
			hql+=" and u.userId <>'"+u.getUserId()+"'";
			Long count1 = publicDao.count("select count(*)"+hql);
			if(count1>=1){
				flag=true;
			}
		}
		return flag;
	}
	
	@Override
	public Users getUserByID(Integer id){
		List<Users> list = publicDaoSQL.find("from Users where id ="+id);
		try {
			return list.get(0);
		} catch (Exception e) {
			// TODO: handle exception
			return new Users();
		}
	}

	@Override
	public Users findUserById(Integer userid) {
		String hql = " FROM Users u WHERE u.userId = '" + userid + "'";
		List<Users> list = publicDao.find(hql);
		if (list.size() > 0)
			return list.get(0);
		return null;
	}

	@Override
	public List<UsersModel> findUserList(String q) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("o.FULL_NAME AS '所在机构',");
		sql.append("u.ACCOUNT AS '用户名',");
		sql.append("u.USER_ID AS '用户id',");
		sql.append("u.ORGANIZATION_ID AS '部门id' ");
		sql.append("FROM QQMS.t_users u LEFT JOIN QQMS.t_organization o ON u.ORGANIZATION_ID = o.ORGANIZATION_ID WHERE u.`STATUS` = 'A'");
		if(StringUtils.isNotBlank(q)){
			sql.append(" AND u.ACCOUNT LIKE '%"+q+"%'");
		}
		List list = publicDao.findBySQL(sql.toString());
		List<UsersModel> modelList = new ArrayList<UsersModel>();
		if(Collections.listIsNotEmpty(list)){
			UsersModel model = new UsersModel();
			for (int i = 0; i < list.size(); i++) {
				UsersModel cmodel = (UsersModel) model.clone();
				Object[] obj = (Object[]) list.get(i);
				cmodel.setFullName(obj[0] == null?"":String.valueOf(obj[0]));
				cmodel.setAccount(obj[1] == null?"":String.valueOf(obj[1]));
				cmodel.setUserId(obj[2] == null?0:(Integer)obj[2]);
				cmodel.setOrganizeId(obj[3]==null?0:(Integer)obj[3]);
				modelList.add(cmodel);
			}
		}
		return modelList;
	}
	
	@Override
	public UsersModel findUserOrgById(Integer userId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("o.FULL_NAME AS '所在机构',");
		sql.append("u.NAME AS '用户名',");
		sql.append("u.USER_ID AS '用户id',");
		sql.append("o.ORGANIZATION_ID AS '机构ID'");
		sql.append("FROM QQMS.t_users u LEFT JOIN QQMS.t_organization o ON u.ORGANIZATION_ID = o.ORGANIZATION_ID WHERE u.`STATUS` = 'A' ");
		sql.append(" AND u.USER_ID="+userId);
		List list = publicDao.findBySQL(sql.toString());
		UsersModel model = new UsersModel();
		UsersModel cmodel = (UsersModel) model.clone();
		if(Collections.listIsNotEmpty(list)){
			Object[] obj = (Object[]) list.get(0);
			cmodel.setFullName(obj[0] == null?"":String.valueOf(obj[0]));
			cmodel.setName(obj[1] == null?"":String.valueOf(obj[1]));
			cmodel.setUserId(obj[2] == null?0:(Integer)obj[2]);
			cmodel.setOrganizeId(obj[3] == null?0:(Integer)obj[3]);
				
		}
		return cmodel;
	}


	//加载用户名及机构
		@Override
		public List<UsersModel> getUserInfo() {
			List<Object[]> list=publicDaoUsersModel.findBySQL("SELECT u.`NAME`,o.FULL_NAME,u.user_id FROM qqms.t_users u LEFT JOIN qqms.t_organization o ON u.ORGANIZATION_ID = o.ORGANIZATION_ID");
			List<UsersModel> list2 = new ArrayList<UsersModel>();
			for (Object[] item : list) {
				UsersModel usersModel = new UsersModel();
				usersModel.setName(item[0]+"");
				usersModel.setFullName(item[1]+"");
				usersModel.setUserId(Integer.parseInt(item[2]+""));
				list2.add(usersModel);
			}
			return list2;
		}
	
	

	@Override
	public List<Role> findRoleListByUserId(Integer userId) {
		String sql = "SELECT ur.USER_ID,ur.ROLE_ID FROM\n"
				+ "QQMS.T_USER_AND_ROLE AS ur where ur.STATUS ='A' and ur.USER_ID="
				+ userId;
		List list = publicDaoSQL.findBySQL(sql);
		List<Role> roleList = new ArrayList<Role>();
		if(Collections.listIsNotEmpty(list)){
			for (int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[])list.get(i);
				Role role = roleService.findRoleByRoleId((Integer)obj[1]);
				if(role!=null){
					roleList.add(role);
				}
			}
		}
		return roleList;
	}

	@Override
	public List<ComboBoxModel> findOrgUserList(String organizeId) {
		List<ComboBoxModel> list=new ArrayList<ComboBoxModel>();
		String hql="from Users where 1=1  ";
		if(organizeId!=null && StringUtils.isNotBlank(organizeId)){
			hql+="and organizeId="+organizeId;
		}
		List<Users> userList = publicDao.find(hql);
		for (Users users : userList) {
			//key编码,申请人id
			 String code=String.valueOf(users.getUserId());
			 //value，申请人名字
			 String text=users.getName();
			
			ComboBoxModel boxModel=new ComboBoxModel(code, text);
			 list.add(boxModel);
			
		}
		return list;
	}

	@Override
	public Organization findOrgByuserId(Integer userId) {
		Users user = publicDao.get(Users.class, userId);
		if (user!=null) {
			Integer organizeId = user.getOrganizeId();
			return publicDaoOrganization.get(Organization.class, organizeId);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UsersModel> loadUserInfo(Integer id) {
		StringBuffer stringBuffer = new StringBuffer("SELECT o.FULL_NAME AS '部门名称',r.NAME AS '职位',u.tel AS '办公电话',u.mobile AS '移动电话',u.email AS '邮箱' ");
		stringBuffer.append(" FROM qqms.t_users u ");
		stringBuffer.append(" LEFT JOIN qqms.t_organization o ON o.ORGANIZATION_ID = u.ORGANIZATION_ID");
		stringBuffer.append(" LEFT JOIN qqms.t_user_and_role ur ON u.USER_ID = ur.user_id");
		stringBuffer.append(" LEFT JOIN qqms.t_role r ON r.ROLE_ID = ur.role_id");
		stringBuffer.append(" WHERE ");
		stringBuffer.append(" o. STATUS = 'A'");
		if(id!=null){
			stringBuffer.append(" AND u.user_id="+id);
		}
		List<Object[]> list=publicDaoUsersModel.findBySQL(stringBuffer.toString());
		List<UsersModel> list2 = new ArrayList<UsersModel>();
		for (Object[] item : list) {
			UsersModel usersModel = new UsersModel();
			usersModel.setFullName(item[0]==null?"":item[0]+"");
			usersModel.setName(item[1]==null?"":item[1]+"");
			usersModel.setTel(item[2]==null?"":item[2]+"");
			usersModel.setMobile(item[3]==null?"":item[3]+"");
			usersModel.setEmail(item[4]==null?"":item[4]+"");
			list2.add(usersModel);
		}
		return list2;
	}
}

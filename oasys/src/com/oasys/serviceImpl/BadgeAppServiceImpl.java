package com.oasys.serviceImpl;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oasys.dao.PublicDao;
import com.oasys.model.BadgeApp;
import com.oasys.model.BadgeAppAttach;
import com.oasys.model.Organization;
import com.oasys.model.Role;
import com.oasys.model.StatusNameRef;
import com.oasys.model.Users;
import com.oasys.service.BadgeAppService;
import com.oasys.service.OrganizationService;
import com.oasys.service.StatusNameRefService;
import com.oasys.service.UserService;
import com.oasys.util.Collections;
import com.oasys.util.Constants;
import com.oasys.util.PageUtil;
import com.oasys.util.UniqueIdUtil;
import com.oasys.viewModel.BadgeAppModel;
@Service("badgeAppService")
@SuppressWarnings("unchecked")
public class BadgeAppServiceImpl    implements BadgeAppService
{
	@Autowired
	private PublicDao<BadgeApp> publicDao;
	
	@Autowired
	private PublicDao<Users> publicUserDao;
	@Autowired
	private PublicDao<Organization> publicOrganDao;
	@Autowired
	private UserService userService;
	//申请人
	@Autowired
	private PublicDao<BadgeAppAttach> publicAttDao;
	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private StatusNameRefService statusNameRefService;
	
	
	
	@Override
	public BadgeApp findAddBadgeAppNo(String appNo) {
		if(StringUtils.isNotBlank(appNo)){
			BadgeApp badgeApp = this.findBadgeAppNo(appNo);
			//登记人姓名
			badgeApp.setRegName(userService.findUserById(badgeApp.getRegistrantNo()).getName());
			return badgeApp;
		}
		BadgeApp badgeApp=new BadgeApp();
		//申请编号
		badgeApp.setAppNo(UniqueIdUtil.generate("GP"));
		//登记人id
		Integer userId=Constants.getCurrendUser().getUserId();
		badgeApp.setRegistrantNo(userId);
		//登记人姓名
		badgeApp.setRegName(userService.findUserById(userId).getName());
		//登记日期
		badgeApp.setRegDatetime(new Date());
		//流程状态
		badgeApp.setProcStatus("1");
		publicDao.save(badgeApp);
		return badgeApp;
	}

	@Override
	public BadgeApp findBadgeAppNo(String appNo) {
		String hql="from BadgeApp where appNo='"+appNo+"'";
		List<BadgeApp> list = publicDao.find(hql);
		if(list !=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public boolean saveBadgeAttach(BadgeAppAttach badgeAppAtt) {
		try {
			
			String userIds=badgeAppAtt.getName();
			String[] userList = userIds.split(",");
			for (String userId : userList) {
				BadgeAppAttach badgeAppAttach=new BadgeAppAttach();
				//部门编号
				badgeAppAttach.setDeptNo(badgeAppAtt.getDeptNo());
				//申请编号
				badgeAppAttach.setAppNo(badgeAppAtt.getAppNo());
				//备注
				badgeAppAttach.setRemark(badgeAppAtt.getRemark());
				//设置申请人id
				badgeAppAttach.setApplicantNo(Integer.parseInt(userId));
				//获取英文名
				Users users = userService.findUserById(Integer.parseInt(userId));
				badgeAppAttach.setNamePinyin(users.getEname());
				//申请人职位信息
				Role role = userService.findRoleListByUserId(Integer.parseInt(userId)).get(0);
				badgeAppAttach.setPosition(role.getRoleCode());
				
				publicAttDao.save(badgeAppAttach);
			}
			return true;
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public List<BadgeAppAttach> findBadgeAttList(String appNo,String deptNo,PageUtil pageUtil) {
		try {
			if(StringUtils.isNotBlank(appNo)){
				StringBuffer sql = new StringBuffer();
				sql.append("SELECT ");
				sql.append("baa.PNR_ID '申请附加表id',");
				sql.append("baa.APP_NO '申请编号',");
				sql.append("baa.APPLICANT_NO '申请人id',");
				sql.append("baa.NAME_PINYIN '申请人拼音',");
				sql.append("baa.POSITION '职位编码',");
				sql.append("baa.DEPT_NO '部门id',");
				sql.append("baa.REMARK '备注'  ");
				sql.append(" FROM t_oa_ad_badge_app_attach baa WHERE 1=1 ");
				if(StringUtils.isNotBlank(appNo)){
					sql.append(" AND baa.APP_NO = '"+appNo+"' ");
				}
				if(StringUtils.isNotBlank(deptNo)){
					sql.append(" AND baa.DEPT_NO = "+deptNo);
				}
				sql.append("  ORDER BY baa.APP_NO ");
				List<Object> list = publicAttDao.findBySql(sql.toString(), pageUtil);
				List<BadgeAppAttach> attList=new ArrayList<BadgeAppAttach>();
				if(Collections.listIsNotEmpty(list)){
					BadgeAppAttach appAttach=new BadgeAppAttach();
					for (int i = 0; i < list.size(); i++) {
						BadgeAppAttach cmodel=(BadgeAppAttach) appAttach.clone();
						Object[] obj = (Object[]) list.get(i);
						cmodel.setPnrId(obj[0] == null?0:(Integer)obj[0]);
						cmodel.setAppNo(obj[1]==null?"":String.valueOf(obj[1]));
						cmodel.setApplicantNo(obj[2]==null?0:(Integer)obj[2]);
						cmodel.setNamePinyin(obj[3]==null?"":String.valueOf(obj[3]));
						cmodel.setPosition(obj[4]==null?"":String.valueOf(obj[4]));
						cmodel.setDeptNo(obj[5]==null?null:(Integer)obj[5]);
						cmodel.setRemark(obj[6]==null?"":String.valueOf(obj[6]));
						attList.add(cmodel);
					}
				}
				return attList;
			}else{
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Long findbadgeAtttotal(String appNo,String deptNo) {
		try {
			String sql="SELECT COUNT(*) FROM t_oa_ad_badge_app_attach  baa where baa.APP_NO='"+appNo+"'  ";
			if(StringUtils.isNotBlank(deptNo)){
				sql+=" and baa.DEPT_NO='"+deptNo+"' ";
			}
			sql+=" ORDER BY baa.APP_NO ";
			Long totalCount = publicAttDao.findTotalCount(sql);
			return totalCount;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	@Override
	public boolean deleteBadgeAttList(String ids) {
		try {
			publicDao.executeHql("delete from BadgeAppAttach where id in ("+ids+")");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<String> findBadgeAppList(PageUtil pageUtil,BadgeApp badgeApp) {
		try {
			String sql="SELECT APP_NO FROM t_oa_ad_badge_app WHERE 1=1  ";
			//判断是否是超级管理员
			if(Constants.getCurrendUser().getUserId()!=1){
				sql+=" AND REGISTRANT_NO = "+Constants.getCurrendUser().getUserId();
			}
			
			if(StringUtils.isNotBlank(badgeApp.getAppNo())){
				sql+=" AND APP_NO='"+badgeApp.getAppNo()+"' ";
			}else{
				if(StringUtils.isNotBlank(badgeApp.getAppDateS())){
					sql += " and APP_DATE >='" + badgeApp.getAppDateS()+"' ";  //申请开始日期
				}
				if(StringUtils.isNotBlank(badgeApp.getAppDateE())){
					sql += " and APP_DATE <='" + badgeApp.getAppDateE()+"' ";  //申请结束日期
				}
				if(StringUtils.isNotBlank(badgeApp.getProcStatus())){
					sql+=" and PROC_STATUS='"+badgeApp.getProcStatus()+"' ";
				}
				
			}
			
			sql+=" ORDER BY PNR_ID DESC ";
			List<Object> bList=publicDao.findBySql(sql.toString(), pageUtil);
			List<String> sList=new ArrayList<String>();
			if(Collections.listIsNotEmpty(bList)){
				String appNo=null;
				for (int i = 0; i < bList.size(); i++) {
					String sappNo= (String) bList.get(i);
					if(sappNo!=null){
						appNo=String.valueOf(sappNo);
					}else{
						appNo="";
					}
					sList.add(appNo);
				}
			}
			return sList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<BadgeAppModel> getBadgeApp(List<String> appNoList) {
		List<BadgeAppModel> modelList=new ArrayList<BadgeAppModel>();
			for(String appNo: appNoList){
				try {
					StringBuffer sql = new StringBuffer();
					SimpleDateFormat djr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					sql.append("SELECT ");
					sql.append("ba.PNR_ID '申请id',");
					sql.append("ba.APP_NO '申请编号',");
					sql.append("rg.FULL_NAME '部门',");
					sql.append("u.`NAME` '姓名',");
					sql.append("u.ENAME '英文名',");
					sql.append("r.`NAME` '职位',");
					sql.append("baa.REMARK '备注',");
					sql.append("ba.APP_STATUS '申请状态',");
					sql.append("ba.PROC_STATUS '流程状态',");
					sql.append("ba.REG_DATETIME '登记日期',");
					sql.append("ba.APP_DATE '申请日期',");
					sql.append("baa.APPLICANT_NO '申请人id', ");
					sql.append("ba.REGISTRANT_NO '登记人id' ");
					sql.append("FROM t_oa_ad_badge_app ba ");
					sql.append("LEFT JOIN t_oa_ad_badge_app_attach baa ON ba.APP_NO = baa.APP_NO ");
					sql.append("LEFT JOIN qqms.t_users u ON baa.APPLICANT_NO = u.USER_ID ");
					sql.append("LEFT JOIN qqms.t_organization  rg ON baa.DEPT_NO = rg.ORGANIZATION_ID ");
					sql.append("LEFT JOIN qqms.t_role r ON baa.POSITION = r.ROLE_CODE ");
					sql.append("WHERE ba.APP_NO='"+appNo+"' ");
					sql.append(" ORDER BY baa.DEPT_NO ASC ");
					
					List<Object> badgeList =publicDao.findBySQL(sql.toString());
					//List<Object> badgeList = publicDao.findBySql(sql.toString(),pageUtil);
					
					if(Collections.listIsNotEmpty(badgeList)){
						BadgeAppModel badgeAppModel=new BadgeAppModel();
						for (int i = 0; i < badgeList.size(); i++) {
							BadgeAppModel cmodel=(BadgeAppModel) badgeAppModel.clone();
							Object[] obj = (Object[]) badgeList.get(i);
							cmodel.setPnrId(obj[0] == null?0:(Integer)obj[0]);
							cmodel.setAppNo(obj[1]==null?"":String.valueOf(obj[1]));
							cmodel.setFullName(obj[2]==null?"":String.valueOf(obj[2]));
							cmodel.setName(obj[3]==null?"":String.valueOf(obj[3]));
							cmodel.setNamePinyin(obj[4]==null?"":String.valueOf(obj[4]));
							cmodel.setPositionName(obj[5]==null?"":String.valueOf(obj[5]));
							cmodel.setRemark(obj[6]==null?"":String.valueOf(obj[6]));
							cmodel.setAppStatus(obj[7]==null?"":String.valueOf(obj[7]));
							cmodel.setProcStatus(obj[8]==null?"":String.valueOf(obj[8]));
							cmodel.setRegDatetime(obj[9]==null?null:djr.parse(String.valueOf(obj[9])));
							cmodel.setAppDate(obj[10]==null?null:String.valueOf(obj[10]));
							cmodel.setApplicantNo(obj[11] == null?0:(Integer)obj[11]);
							cmodel.setRegistrantNo(obj[12] == null?0:(Integer)obj[12]);
							modelList.add(cmodel);
						}
					}
			} catch (ParseException e) {
				e.printStackTrace();
				return null;
			}
			
			
		}
		return modelList;
	}

	@Override
	public Long findtotal(BadgeApp badgeApp) {
		try {
			String sql="SELECT COUNT(*) FROM t_oa_ad_badge_app WHERE 1=1 ";
			if(Constants.getCurrendUser().getUserId()!=1){
				sql+=" AND REGISTRANT_NO = "+Constants.getCurrendUser().getUserId();
			}
			
			if(StringUtils.isNotBlank(badgeApp.getAppNo())){
				sql+=" AND APP_NO='"+badgeApp.getAppNo()+"' ";
			}else{
				if(StringUtils.isNotBlank(badgeApp.getAppDateS())){
					sql += " and APP_DATE >='" + badgeApp.getAppDateS()+"' ";  //申请开始日期
				}
				if(StringUtils.isNotBlank(badgeApp.getAppDateE())){
					sql += " and APP_DATE <='" + badgeApp.getAppDateE()+"' ";  //申请结束日期
				}
				if(StringUtils.isNotBlank(badgeApp.getProcStatus())){
					sql+=" and PROC_STATUS='"+badgeApp.getProcStatus()+"' ";
				}
				
			}
			Long totalCount = publicDao.findTotalCount(sql);
			return totalCount;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0L;
	}

	@Override
	public boolean deleteBadgeApp(String appNo) {
		try {
			publicDao.executeHql("delete from BadgeApp where appNo = '" + appNo+"'");
			publicAttDao.executeHql("delete from BadgeAppAttach where appNo = '" + appNo+"'");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void upBadgeProcStatus(Integer id, String c) {
		BadgeApp badgeApp = publicDao.get(BadgeApp.class, id);
		badgeApp.setProcStatus(c);
		publicDao.saveOrUpdate(badgeApp);
	}

	@Override
	public void upBadgeAppStatus(Integer id, String appStatus) {
		BadgeApp badgeApp = publicDao.get(BadgeApp.class, id);
		badgeApp.setAppStatus(appStatus);
		publicDao.saveOrUpdate(badgeApp);
	}

	@Override
	public BadgeApp findBadgeByPnrId(Integer pnrId) {
		return publicDao.get(BadgeApp.class, pnrId);
	}

	@Override
	public boolean deletebadgeNotDate(String pnrIds) {
		try {
			String hql="delete from BadgeApp where id in ("+pnrIds+")";
			publicDao.executeHql(hql);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}

	
	
	
	
	
	
	
}

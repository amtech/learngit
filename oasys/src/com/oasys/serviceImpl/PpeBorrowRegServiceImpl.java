package com.oasys.serviceImpl;


import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oasys.dao.PublicDao;
import com.oasys.model.PpeBorrowReg;
import com.oasys.service.OrganizationService;
import com.oasys.service.PpeBorrowRegService;
import com.oasys.service.UserService;
import com.oasys.util.Constants;
import com.oasys.util.PageUtil;

@Service("ppeBorrowRegService")
@SuppressWarnings("unchecked")
public class PpeBorrowRegServiceImpl  extends WorkFlowBaseServiceImpl  implements PpeBorrowRegService
{

	
	
	@Autowired
	private PublicDao<PpeBorrowReg> ppeBorrowRegDao;
	
	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private UserService userService;
	
	@Override
	public List<PpeBorrowReg> findPpeBorrowRegList(PpeBorrowReg ppeBorrowReg,PageUtil pageUtil) {
		try {
			String hql="from PpeBorrowReg where 1=1 ";
			//判断是否是超级管理员
			Integer userId=Constants.getCurrendUser().getUserId();
			
			if(userId!=1){
				hql+=" and registrantNo="+userId;
			}		
			
			if(StringUtils.isNotBlank(ppeBorrowReg.getPpeNo())){
				hql+=" and ppeNo='"+ppeBorrowReg.getPpeNo()+"' ";
			}
			if(StringUtils.isNotBlank(ppeBorrowReg.getAppDateS())){
				hql+=" and regDatetime >='" + ppeBorrowReg.getAppDateS()+"' ";  //申请开始日期
			}
			if(StringUtils.isNotBlank(ppeBorrowReg.getAppDateE())){
				hql+=" and regDatetime <='" + ppeBorrowReg.getAppDateE()+"' ";  //申请结束日期
			}
			
			hql+=" order by pbrId desc";
			
			List<PpeBorrowReg> ppeBorrowList = ppeBorrowRegDao.find(hql, pageUtil);
			for (PpeBorrowReg ppeBorrow : ppeBorrowList) {
				//登记人姓名
				ppeBorrow.setRegistrantName(userService.findUserOrgById(ppeBorrow.getRegistrantNo()).getName());
				//借用人姓名
				ppeBorrow.setBorrowerName(userService.findUserOrgById(ppeBorrow.getBorrower()).getName());
				//借用人部门名称
				ppeBorrow.setFullName(organizationService.getOrgNameByID(ppeBorrow.getDeptNo()));
				//归还人姓名
				ppeBorrow.setReverterName(userService.findUserOrgById(ppeBorrow.getReverter()).getName());
				//借用部门
				ppeBorrow.setReverterDeptNo(userService.findUserOrgById(ppeBorrow.getReverter()).getOrganizeId());
			}
			return ppeBorrowList;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Long getTatol(PpeBorrowReg ppeBorrowReg) {
		try {
			Integer userId=Constants.getCurrendUser().getUserId();
			String sql="SELECT COUNT(*) FROM t_oa_ad_ppe_borrow_reg WHERE 1=1 ";
			if(userId!=1){
				sql+=" AND REGISTRANT_NO="+userId;
			}
			if(StringUtils.isNotBlank(ppeBorrowReg.getPpeNo())){
				sql+=" AND PPE_NO='"+ppeBorrowReg.getPpeNo()+"' ";
			}
			if(StringUtils.isNotBlank(ppeBorrowReg.getAppDateS())){
				sql+=" AND REG_DATETIME >='" + ppeBorrowReg.getAppDateS()+"' ";  //申请开始日期
			}
			if(StringUtils.isNotBlank(ppeBorrowReg.getAppDateE())){
				sql+=" AND REG_DATETIME <='" + ppeBorrowReg.getAppDateE()+"' ";  //申请结束日期
			}
			Long count = ppeBorrowRegDao.findTotalCount(sql);
			return count;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0L;
	}
	
	
	
	@Override
	public boolean saveOrUpdPpeBorrowReg(PpeBorrowReg ppeBorrowReg) {
		try {
			if(ppeBorrowReg.getPbrId()==null){
				ppeBorrowReg.setRegDatetime(new Date());
				ppeBorrowRegDao.save(ppeBorrowReg);
			}else if(ppeBorrowReg.getReverter()==null){
				ppeBorrowRegDao.saveOrUpdate(ppeBorrowReg);
			}else{
				ppeBorrowReg.setRevDatetime(new Date());
				ppeBorrowRegDao.saveOrUpdate(ppeBorrowReg);
			}
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deletePpeBorrowRegList(String ids) {
		try {
			ppeBorrowRegDao.executeHql("delete from PpeBorrowReg where id in ("+ids+")");
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	
	
	

	
	
	
	
	
	
	
	
}

package com.oasys.serviceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oasys.dao.PublicDao;
import com.oasys.model.CardAppAttach;
import com.oasys.service.CardAppAttachService;
import com.oasys.viewModel.CardAppAttachModel;

@Service("cardAppAttachService")
public class CardAppAttachServiceImpl implements CardAppAttachService{
	@Autowired
	private PublicDao<CardAppAttach> publicDao;
	
	//添加附件
	@Override
	public boolean addCardAppAttach(CardAppAttach cardAppAttach) {
		boolean flag=false;
		try {
			publicDao.saveOrUpdate(cardAppAttach);
			flag=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	//获取添加附件
	@Override
	public List<CardAppAttachModel> getList(String caID) {
		StringBuffer stringBuffer = new StringBuffer("SELECT a.APP_QTY '申请数量',a.PERSONAL_TEL '个人电话',a.OFFICE_TEL '办公电话',a.EMAIL '邮箱',a.BRANCH_ADDR '分公司地址',a.COM_URL '公司网址',a.UNIT '单位',a.REMARK '备注信息',u.name as '姓名',o.FULL_NAME as '部门名称',a.POSITION '职位',a.CA_ID,a.APP_NO,u.USER_ID,card.REGISTRANT_NO '登记人编号'");
		stringBuffer.append(" FROM t_oa_ad_card_app_attach a ");
		stringBuffer.append(" LEFT JOIN t_oa_ad_card_app card ON a.APP_NO = card.APP_NO");
		stringBuffer.append(" LEFT JOIN qqms.t_users u ON u.USER_ID = a.APPLICANT_NO ");
		stringBuffer.append(" LEFT JOIN qqms.t_organization o ON o.ORGANIZATION_ID = a.DEPT_NO");
		stringBuffer.append(" WHERE o.STATUS = 'A' ");
		if(StringUtils.isNotBlank(caID) && !"null".equals(caID)){
			String appNo=publicDao.findBySQL("SELECT APP_NO FROM t_oa_ad_card_app WHERE CA_ID="+caID).get(0)+"";
			stringBuffer.append(" AND a.APP_NO = '"+appNo+"'");
		}else{
			stringBuffer.append(" AND a.APP_NO IS NULL");
		}
		List<Object[]> list=publicDao.findBySQL(stringBuffer.toString());
		List<CardAppAttachModel> list2 = new ArrayList<CardAppAttachModel>();
		for (int i = 0; i < list.size(); i++) {
			Object[] objects = list.get(i);
			CardAppAttachModel cardAppAttachModel = new CardAppAttachModel();
			cardAppAttachModel.setAppQty(Integer.parseInt((objects[0]==null||"".equals(objects[0])?"0":objects[0])+""));//申请数量
			cardAppAttachModel.setPersonalTel(objects[1]==null?"":objects[1]+"");//个人电话
			cardAppAttachModel.setOfficeTel(objects[2]+"");//办公电话
			cardAppAttachModel.setEmail(objects[3]+"");//邮箱
			cardAppAttachModel.setBranchAddr(objects[4]+"");//公司地址
			cardAppAttachModel.setComUrl(objects[5]+"");//公司网址
			cardAppAttachModel.setUnit(objects[6]+"");//单位
			cardAppAttachModel.setRemark(objects[7]+"");//备注信息
			cardAppAttachModel.setApplicantNo(objects[8]+"");//申请人
			cardAppAttachModel.setDeptNo(objects[9]+"");//部门名称
			cardAppAttachModel.setPosition(objects[10]+"");//职位
			cardAppAttachModel.setCaId(Integer.parseInt(objects[11]+""));
			cardAppAttachModel.setAppNo(objects[12]+"");
			cardAppAttachModel.setUserId(Integer.parseInt(objects[13]+""));
			cardAppAttachModel.setRegistrantNO(objects[14]==null?null:Integer.parseInt(objects[14]+""));
			list2.add(cardAppAttachModel);
		}
		return list2;
	}
	//删除附件
	@Override
	public boolean delCardAttach(Integer cardAttachId) {
		Integer count=publicDao.executeHql("delete CardAppAttach where caId="+cardAttachId);
		if(count>0){
			return true;
		}else {
			return false;
		}
	}
	//修改附件
	@Override
	public boolean modifyCardAttachCaId(String appNo) {
		Integer count=publicDao.executeHql("update CardAppAttach set appNo = '"+appNo+"' where appNo=null");
		if(count>0){
			return true;
		}else {
			return false;
		}
	}
	//统计附件
	@Override
	public BigDecimal getCountByCardApp(String appNo) {
		return (BigDecimal) publicDao.findBySQL("SELECT SUM(APP_QTY) FROM t_oa_ad_card_app_attach WHERE APP_NO is "+appNo+"").get(0);
	}

}

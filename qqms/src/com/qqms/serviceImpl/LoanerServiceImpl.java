/**   
 * @Title: qqms 
 * @Package com.qqms.serviceImpl 
 * @Description: TODO 贷款客户service(用一句话描述该文件做什么) 
 * @Author xujianwei  
 * @Date 2015年6月23日 下午1:06:00 
 * @Version V1.0   
 */
package com.qqms.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qqms.dao.PublicDao;
import com.qqms.model.Loaner;
import com.qqms.model.VO.AccountJournalVO;
import com.qqms.model.VO.LoanOrderVO;
import com.qqms.model.VO.LoanerInfoHisVO;
import com.qqms.service.LoanerService;
import com.qqms.util.PageUtil;

/**
 * @ClassName: LoanerServiceImpl
 * @Description: TODO
 * @Author xujianwei
 * @Version 1.0
 * @Date 2015年6月23日 下午1:06:00
 *
 */
@Service("loanerService")
public class LoanerServiceImpl implements LoanerService {

	
	@Autowired
	private PublicDao publicDao;
	/** (非 Javadoc) 
	 * <p>Title: findLoanerList</p> 
	 * <p>Description: </p> 
	 * @return 
	 * @see com.qqms.service.LoanerService#findLoanerList() 
	 */
	@Override
	public Object[] findLoanerList(String name,String idNo,String genderType,PageUtil pageUtil) {
		// TODO Auto-generated method stub
		String hql="from Loaner t where 1=1";
		if(StringUtils.isNotBlank(name)){
			hql+=" and t.name like '%"+name+"%'";
		}
		if(StringUtils.isNotBlank(idNo)){
			hql+=" and t.idNo like '%"+idNo+"%'";
		}
		if(StringUtils.isNotBlank(genderType)){
			hql+=" and t.genderType = '"+genderType+"'";
		}
		List<Loaner> loanerList = (List<Loaner>)publicDao.find(hql,pageUtil);
		Long count = publicDao.count("select count(*) "+hql);
		Object[] obj = new Object[2];
		obj[0]=loanerList;
		obj[1]=count;
		return obj;
	}
	/**
	 * (非 Javadoc) 
	 * <p>Title: findLoanerDetailById</p> 
	 * <p>Description: </p> 
	 * @param loanerId
	 * @return 
	 * @see com.qqms.service.LoanerService#findLoanerDetailById(java.lang.String)
	 */
	@Override
	public String findLoanerDetailById(String loanerId) {
		// TODO Auto-generated method stub
		//查询出该贷款人最近一次的订单id号
		String sql="select t1.loan_order_id from t_bp_loan_order t1 "
				+ "where 1=1 and t1.LOANER_ID ='"+loanerId+"'"
				+ "order by t1.CREATE_DATE desc";
		List<Object> objList = publicDao.findBySQL(sql);
		String orderId = objList.size()>0?objList.get(0).toString():null;
		return orderId;
	}
	/**
	 * (非 Javadoc) 
	 * <p>Title: findLoanerOrderListById</p> 
	 * <p>Description: </p> 
	 * @param loanerId
	 * @return 
	 * @see com.qqms.service.LoanerService#findLoanerOrderListById(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object[] findLoanerOrderListById(String loanerId,PageUtil pageUtil) {
		// TODO Auto-generated method stub
		String sql="select"
				+" t1.LOANER_ID,t1.name as loanerName,t1.ID_NO,t1.LOAN_ORDER_ID,t4.DICT_NAME,t1.LOAN_AMOUNT,t1.LOAN_MIN,t1.APPLY_DATE,t3.`NAME` as creator,t2.STATUS_NAME "
				+" from t_bp_loan_order t1 "
				+" left join t_bp_order_status t2 on t1.STATUS_ID = t2.STATUS_ID "
				+" left join t_users t3 on t1.CREATOR = t3.USER_ID"
				+" left join "
			    +" (select dict_code,dict_name from t_sys_dict where `STATUS`='A' AND parent_id in "
				+" (select CODE_ID from t_sys_dict where DICT_CODE='loan_type'))t4 on t4.dict_code = t1.loan_type"	
				+" where t1.PROCESS_STATUS <> 0 and t1.LOANER_ID = "+loanerId
				+" order by t1.CREATE_DATE desc";
		List<Object> objList = publicDao.findBySql(sql, pageUtil);
		Long count=publicDao.findTotalCount(sql);
		List<LoanOrderVO> loanOrderVOList=new ArrayList<LoanOrderVO>();
		Object[] obj=new Object[2];
		try {
			LoanOrderVO vo=new LoanOrderVO();
			for(int i = 0; i < objList.size(); i++){
				Object[] obs = (Object[]) objList.get(i);
				LoanOrderVO vo_c=(LoanOrderVO) vo.clone();
				vo_c.setLoanerId(obs[0]==null?"":String.valueOf(obs[0]));
				vo_c.setLoanerName(obs[1]==null?"":String.valueOf(obs[1]));
				vo_c.setIdNo(obs[2]==null?"":String.valueOf(obs[2]));
				vo_c.setLoanOrderId(obs[3]==null?"":String.valueOf(obs[3]));
				vo_c.setLoanType(obs[4]==null?"":String.valueOf(obs[4]));
				vo_c.setLoanAmount(obs[5]==null?"":numberWithDelimiter(String.valueOf(obs[5])));
				vo_c.setLoanMin(obs[6]==null?"":numberWithDelimiter(String.valueOf(obs[6])));
				vo_c.setCreateDate(obs[7]==null?"":String.valueOf(obs[7]));
				vo_c.setCreator(obs[8]==null?"":String.valueOf(obs[8]));
				vo_c.setStatusName(obs[9]==null?"":String.valueOf(obs[9]));
				loanOrderVOList.add(vo_c);
			}
			obj[0]=loanOrderVOList;
			obj[1]=count;
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	}
	/**
	 * (非 Javadoc) 
	 * <p>Title: findLoanerDetailByOrderId</p> 
	 * <p>Description: </p> 
	 * @param loanOrderId
	 * @return 
	 * @see com.qqms.service.LoanerService#findLoanerDetailByOrderId(java.lang.String)
	 */
	@Override
	public Object[] findLoanerDetailByOrderId(String loanOrderId) {
		// TODO Auto-generated method stub
		//个人基本信息
		String baseSql="select t1.`NAME` as loanerName,t3.dict_name as GENDER_TYPE,t1.ID_NO,t1.AGE,concat(t8.AREA_NAME,t9.AREA_NAME,t10.AREA_NAME,t6.ADDR_DETAILS) as HUKOU_ADDR, "
				+" concat(t11.AREA_NAME,t12.AREA_NAME,t13.AREA_NAME,t7.ADDR_DETAILS) as CUR_ADDR, "
				+" t1.MOBILE_TEL,t1.FIXED_TEL,t2.FAMILY_TEL,t1.QQ_NO,t1.EMAIL,t4.dict_name as MARRIAGE_TYPE,t5.dict_name as HAS_CHILD,t1.ANNUAL_SALARY,t1.INCOME_SRC,t14.dict_name as MORTGAGE_STATUS "  
				+" from t_bp_loan_order t1 "  
				+" left join t_bp_loaner t2 on t1.LOANER_ID=t2.LOANER_ID "  
				+" left join " 		 
				+" (select dict_code,dict_name from t_sys_dict where `STATUS`='A' AND parent_id in "  
				+" (select CODE_ID from t_sys_dict where DICT_CODE='gender_type'))t3 on t3.dict_code = t1.GENDER_TYPE " 
				+" left join " 		 
				+" (select dict_code,dict_name from t_sys_dict where `STATUS`='A' AND parent_id in "  
				+" (select CODE_ID from t_sys_dict where DICT_CODE='marriage_type'))t4 on t4.dict_code = t1.MARRIAGE_TYPE " 
				+" left join " 		 
				+" (select dict_code,dict_name from t_sys_dict where `STATUS`='A' AND parent_id in "  
				+" (select CODE_ID from t_sys_dict where DICT_CODE='has_child'))t5 on t5.dict_code = t1.HAS_CHILD " 
				+" left join " 
				+" t_bp_address t6 on t1.HUKOU_ADDR = t6.ADDR_ID "
				+" left join t_bp_address t7 on t1.CUR_ADDR = t7.ADDR_ID "
				+" left join t_area t8 on t6.PROVINCE_ID = t8.AREA_ID "
				+" left join t_area t9 on t6.CITY_ID = t9.AREA_ID "
				+" left join t_area t10 on t6.AREA_ID = t10.AREA_ID "
				+" left join t_area t11 on t7.PROVINCE_ID = t11.AREA_ID "
				+" left join t_area t12 on t7.CITY_ID = t12.AREA_ID "
				+" left join t_area t13 on t7.AREA_ID = t13.AREA_ID "
				+" left join "		 
				+" (select dict_code,dict_name from t_sys_dict where `STATUS`='A' AND parent_id in " 
				+" (select CODE_ID from t_sys_dict where DICT_CODE='mortgage_status'))t14 on t14.dict_code = t1.MORTGAGE_STATUS "
				+" where 1=1 and t1.LOAN_ORDER_ID='"+loanOrderId+"' ";
		Object[] baseObjs=(Object[]) publicDao.uniqueResult(baseSql);
		Object baseObj = null;
		if(baseObjs!=null){
			baseObjs[13] =baseObjs[13]==null?"":numberWithDelimiter(String.valueOf(baseObjs[13]));
			baseObj=baseObjs;
		}
		//单位信息
		String companySql="select t3.name,t3.TELE,CONCAT(t5.AREA_NAME,t6.AREA_NAME,t7.AREA_NAME,t4.ADDR_DETAILS) as ADDRESS,t3.REG_CAPITAL,t3.FOUNDED_TIME,t3.EMP_AMOUNT,t3.AREA_SIZE,t3.BUSINESS_SCOPE " 
				+" from t_bp_loan_order t1 " 
				+" left join t_bp_loanorder_and_company t2 on t2.LOAN_ORDER_ID = t1.LOAN_ORDER_ID " 
				+" right join t_bp_company t3 on t2.COM_ID= t3.COM_ID " 
				+" left join " 
				+" t_bp_address t4 on t3.ADDRESS = t4.ADDR_ID "						
				+" left join t_area t5 on t4.PROVINCE_ID = t5.AREA_ID "
				+" left join t_area t6 on t4.CITY_ID = t6.AREA_ID "
				+" left join t_area t7 on t4.AREA_ID = t7.AREA_ID "
				+" where 1=1 and t1.LOAN_ORDER_ID='"+loanOrderId+"'";
		List<Object> objList=publicDao.findBySQL(companySql);
		List<Object> companyList=new ArrayList<Object>();
		for(int i=0;i<objList.size();i++){
			Object[] companyObjs=(Object[]) objList.get(i);
			companyObjs[3] =companyObjs[3]==null?"":numberWithDelimiter(String.valueOf(companyObjs[3]));
			companyList.add(companyObjs);
		}
		//个人资产信息
		String assetsSql="select t3.ACCOUNT_NAME,t3.BANK_NAME,t3.ACCOUNT_NUM,t4.dict_name as ACT_MEDIUM,t5.dict_name as ACT_NATURE "
				+" from t_bp_loan_order t1 "
				+" left join t_bp_loanorder_and_accountinfo t2 on t2.LOAN_ORDER_ID=t1.LOAN_ORDER_ID "
				+" right join t_bp_account_info t3 on t3.ACCOUNT_ID = t2.ACCOUNT_ID "
				+" left join "  
				+" (select dict_code,dict_name from t_sys_dict where `STATUS`='A' AND parent_id in "  
				+" (select CODE_ID from t_sys_dict where DICT_CODE='account_medium'))t4 on t4.dict_code = t3.ACT_MEDIUM "  
				+" left join "  
				+" (select dict_code,dict_name from t_sys_dict where `STATUS`='A' AND parent_id in "  
				+" (select CODE_ID from t_sys_dict where DICT_CODE='bankAccount_type'))t5 on t5.dict_code = t3.ACT_NATURE "  
				
				+" where 1=1 and t1.LOAN_ORDER_ID='"+loanOrderId+"'";
		List<Object> assetsList = publicDao.findBySQL(assetsSql);
		//配偶信息
		String spouseSql="select t3.RELATIONSHIP,t3.CH_NAME,t4.dict_name as GENDER_TYPE,t3.ID_NO,t3.age,CONCAT(t7.AREA_NAME,t8.AREA_NAME,t9.AREA_NAME,t5.ADDR_DETAILS)as CURR_ADDR,t3.TEL,t3.FIXED_TEL,t3.WORKPLACE,t3.COMP_TEL,t3.JOB_TITLE,CONCAT(t10.AREA_NAME,t11.AREA_NAME,t12.AREA_NAME,t6.ADDR_DETAILS) as COMP_ADDR,t3.ANNUAL_SALARY,t3.YEARS_OF_WORK "  
				+" from t_bp_loan_order t1 "  
				+" left join t_bp_loanorder_and_contacts t2 on t2.LOAN_ORDER_ID = t1.LOAN_ORDER_ID "  
				+" right join t_bp_contacts t3 on t2.CONTACT_ID = t3.CONTACT_ID "  
				+" left join "  
				+" (select dict_code,dict_name from t_sys_dict where `STATUS`='A' AND parent_id in "  
				+" (select CODE_ID from t_sys_dict where DICT_CODE='gender_type'))t4 on t4.dict_code = t1.GENDER_TYPE "  
				+" left join  t_bp_address t5 on t3.CURR_ADDR = t5.ADDR_ID "  
				+" left join t_bp_address t6 on t3.COMP_ADDR = t6.ADDR_ID "  
				+" left join t_area t7 on t5.PROVINCE_ID = t7.AREA_ID "  
				+" left join t_area t8 on t5.CITY_ID = t8.AREA_ID "  
				+" left join t_area t9 on t5.AREA_ID = t9.AREA_ID "  
				+" left join t_area t10 on t6.PROVINCE_ID = t10.AREA_ID "  
				+" left join t_area t11 on t6.CITY_ID = t11.AREA_ID "  
				+" left join t_area t12 on t6.AREA_ID = t12.AREA_ID " 
				+" where 1=1 and t3.RELATIONSHIP = 'spouse' and t1.LOAN_ORDER_ID='"+loanOrderId+"'";
		List<Object> spouseList = publicDao.findBySQL(spouseSql);
		Object[] spouseObjs=(Object[]) (spouseList.size()>0?spouseList.get(0):null);
		Object spouseObj=null;
		if(spouseObjs!=null){
			spouseObjs[12] = spouseObjs[12]==null?"":numberWithDelimiter(String.valueOf(spouseObjs[12]));
			spouseObj=spouseObjs;
		}
		//紧急联系人列表
		String emccontactSql="select t3.CH_NAME,t4.dict_name as RELATIONSHIP,t3.WORKPLACE,CONCAT(t7.AREA_NAME,t8.AREA_NAME,t9.AREA_NAME,t5.ADDR_DETAILS) AS COMP_ADDR,CONCAT(t10.AREA_NAME,t11.AREA_NAME,t12.AREA_NAME,t6.ADDR_DETAILS) AS CURR_ADDR,t3.TEL " 
				+" from t_bp_loan_order t1 " 
				+" left join t_bp_loanorder_and_contacts t2 on t2.LOAN_ORDER_ID = t1.LOAN_ORDER_ID " 
				+" right join t_bp_contacts t3 on t2.CONTACT_ID = t3.CONTACT_ID " 
				+" left join " 	 
				+" (select dict_code,dict_name from t_sys_dict where `STATUS`='A' AND parent_id in " 
				+" (select CODE_ID from t_sys_dict where DICT_CODE='relationship_type'))t4 on t4.dict_code = t3.RELATIONSHIP " 
				+" left join " 
				+" t_bp_address t5 on t3.COMP_ADDR = t5.ADDR_ID "
				+" left join t_bp_address t6 on t3.CURR_ADDR = t6.ADDR_ID "
				+" left join t_area t7 on t5.PROVINCE_ID = t7.AREA_ID "
				+" left join t_area t8 on t5.CITY_ID = t8.AREA_ID "
				+" left join t_area t9 on t5.AREA_ID = t9.AREA_ID "
				+" left join t_area t10 on t6.PROVINCE_ID = t10.AREA_ID "
				+" left join t_area t11 on t6.CITY_ID = t11.AREA_ID "
				+" left join t_area t12 on t6.AREA_ID = t12.AREA_ID "
				+" where 1=1 and t1.LOAN_ORDER_ID='"+loanOrderId+"'";
		List<Object> emccontactList=(List<Object>)publicDao.findBySQL(emccontactSql);
		
		Object[] obj = new Object[5];
		obj[0] = baseObj;
		obj[1] = companyList;
		obj[2] = assetsList;
		obj[3] = spouseObj;
		obj[4] = emccontactList;
		return obj;
	}
	/**
	 * (非 Javadoc) 
	 * <p>Title: findLoanOrderDetailById</p> 
	 * <p>Description: </p> 
	 * @param loanOrderId
	 * @return 
	 * @see com.qqms.service.LoanerService#findLoanOrderDetailById(java.lang.String)
	 */
	@Override
	public LoanOrderVO findLoanOrderDetailById(String loanOrderId){
		String sql="select t1.LOAN_ORDER_ID,t1.APPLY_DATE,t1.`NAME` as loanerName,t4.dict_name as LOAN_TYPE,t1.LOAN_AMOUNT,t1.LOAN_MIN, "
				+ " t1.LOAN_PERIOD,t2.STATUS_NAME,t5.dict_name as REPAY_METHOD,t3.`NAME` as userName,t1.PURPOSE "
				+ " from t_bp_loan_order t1 "
				+ " left join t_bp_order_status t2 on  t1.STATUS_ID = t2.STATUS_ID "
				+ " left join t_users t3 on t1.CREATOR = t3.USER_ID "
				+ " left join " 
				+ " (select dict_code,dict_name from t_sys_dict where `STATUS`='A' AND parent_id in "
				+ " (select CODE_ID from t_sys_dict where DICT_CODE='loan_type'))t4 on t4.dict_code = t1.loan_type "
				+ " left join " 
				+ " (select dict_code,dict_name from t_sys_dict where `STATUS`='A' AND parent_id in "
				+ " (select CODE_ID from t_sys_dict where DICT_CODE='repay_method'))t5 on t5.dict_code = t1.REPAY_METHOD "
				+ " where t1.PROCESS_STATUS <> 0 and t1.LOAN_ORDER_ID='"+loanOrderId+"'";
		Object[] obs = (Object[])publicDao.findBySQL(sql).get(0);
		LoanOrderVO vo = new LoanOrderVO();
		vo.setLoanOrderId(obs[0]==null?"":String.valueOf(obs[0]));
		vo.setCreateDate(obs[1]==null?"":String.valueOf(obs[1]));
		vo.setLoanerName(obs[2]==null?"":String.valueOf(obs[2]));
		vo.setLoanType(obs[3]==null?"":String.valueOf(obs[3]));
		vo.setLoanAmount(obs[4]==null?"":numberWithDelimiter(String.valueOf(obs[4])));
		vo.setLoanMin(obs[5]==null?"":numberWithDelimiter(String.valueOf(obs[5])));
		vo.setLoanPeriod(obs[6]==null?"":String.valueOf(obs[6]));
		vo.setStatusName(obs[7]==null?"":String.valueOf(obs[7]));
		vo.setRepayMethod(obs[8]==null?"":String.valueOf(obs[8]));
		vo.setCreator(obs[9]==null?"":String.valueOf(obs[9]));
		vo.setPurpose(obs[10]==null?"":String.valueOf(obs[10]));
		return vo;
	}
	
	/**
	 * (非 Javadoc) 
	 * <p>Title: findCreditAuditDetailByOrderId</p> 
	 * <p>Description: </p> 
	 * @param loanOrderId
	 * @return 
	 * @throws CloneNotSupportedException 
	 * @see com.qqms.service.LoanerService#findCreditAuditDetailByOrderId(java.lang.String)
	 */
	@Override
	public Object[] findCreditAuditDetailByOrderId(String loanOrderId) throws CloneNotSupportedException{
		//个人基本信息
		String baseSql="select t1.`NAME` as LOANER_NAME,t1.ID_NO,t5.dict_name as LAW_QUERY_STATUS,t6.dict_name as LOAN_TYPE,t1.LOAN_AMOUNT,t1.PURPOSE,t2.LOAN_CITY,t4.`NAME` as COMP_NAME,t7.dict_name as COM_CREDIT_STATUS "
					  +" from t_bp_loan_order t1 "
					  +" left join t_bp_credit_audit_report t2 on t2.LOAN_ORDER_ID = t1.LOAN_ORDER_ID "
					  +" left join t_bp_loanorder_and_company t3 on t3.LOAN_ORDER_ID = t1.LOAN_ORDER_ID "
					  +" left join t_bp_company t4 on t3.COM_ID=t4.COM_ID "
					  +" left join "
					  +" (select dict_code,dict_name from t_sys_dict where `STATUS`='A' AND parent_id in " 
					  +" (select CODE_ID from t_sys_dict where DICT_CODE='law_query_status'))t5 on t5.dict_code = t2.LAW_QUERY_STATUS "
					  +" left join "
					  +" (select dict_code,dict_name from t_sys_dict where `STATUS`='A' AND parent_id in " 
					  +" (select CODE_ID from t_sys_dict where DICT_CODE='loan_type'))t6 on t6.dict_code = t1.LOAN_TYPE "
					  +" left join "
					  +" (select dict_code,dict_name from t_sys_dict where `STATUS`='A' AND parent_id in " 
					  +" (select CODE_ID from t_sys_dict where DICT_CODE='com_credit_status'))t7 on t7.dict_code = t2.COM_CREDIT_STATUS "
					  +" where t1.PROCESS_STATUS <> 0 and t1.LOAN_ORDER_ID='"+loanOrderId+"'";  
		Object[] baseObjs = (Object[])publicDao.uniqueResult(baseSql);
		Object baseObj = null;
		if(baseObjs!=null){
			baseObjs[4]=baseObjs[4]!=null?numberWithDelimiter(String.valueOf(baseObjs[4])):"";
			baseObj=baseObjs;
		}
		//征信情况--贷款
		String loanSql="select t3.TOTAL_LOAN_COUNT,t3.OUTSTANDING_COUNT,t3.OUTSTANDING_SUM,t3.OUTSTANDING_BALANCE,t3.YEAR_OVERDUE,t3.CUMULATIVE_OVERDUE,t3.OVERDUE_RATE,t3.MONTH_REPAY,t3.LAST_LOAN_SPACE,t3.LOAN_DETAIL "
				     +" from t_bp_loan_order t1 "
					 +" left join t_bp_credit_audit_report t2 on t2.LOAN_ORDER_ID=t1.LOAN_ORDER_ID "
					 +" left join t_bp_loans_details t3 on t2.EXIST_LOAN_ID=t3.EXIST_LOAN_ID "
					 +" where t1.PROCESS_STATUS <> 0 and t1.LOAN_ORDER_ID='"+loanOrderId+"'";
		Object[] loanObjs = (Object[])publicDao.uniqueResult(loanSql);
		Object loanObj = null;
		if(loanObjs!=null){
			loanObjs[2]=loanObjs[2]!=null?numberWithDelimiter(String.valueOf(loanObjs[2])):"";//未结清贷款总额
			loanObjs[3]=loanObjs[3]!=null?numberWithDelimiter(String.valueOf(loanObjs[3])):"";//未结清贷款余额
			loanObjs[6]=loanObjs[6]!=null?numberWithDelimiter(String.valueOf(loanObjs[6])):"";//逾期率
			loanObjs[7]=loanObjs[7]!=null?numberWithDelimiter(String.valueOf(loanObjs[7])):"";//月还款额度
			loanObj=loanObjs;
		}
		//征信情况--信用卡
		String creditCardSql="select t3.CARD_COUNT,t3.CARD_IN_USE,t3.CREDIT_TOTAL_AMOUNT,t3.CREDIT_LIMIT,t3.YEAR_OVERDUE,t3.VALIDATE_YEAR,t3.USE_RATE, "
				            +" t3.OVERDUE_CARD_COUNT,t3.OVERDUE_RATIO,t3.MAX_LIMIT,t3.MONTH_REPAY,t3.CUMULATIVE_OVERDUE,t3.MAX_QISHU,t3.OVERDUE_RATE,t3.DETAIL "
							+" from t_bp_loan_order t1 "
							+" left join t_bp_credit_audit_report t2 on t2.LOAN_ORDER_ID=t1.LOAN_ORDER_ID "
							+" left join t_bp_credit_cards_details t3 on t2.CARD_INFO_ID=t3.CARD_INFO_ID "
							+" where t1.PROCESS_STATUS <> 0 and t1.LOAN_ORDER_ID='"+loanOrderId+"'";
		Object[] creditCardObjs=(Object[])publicDao.uniqueResult(creditCardSql);
		Object creditCardObj=null;
		if(creditCardObjs!=null){
			creditCardObjs[2]=creditCardObjs[2]!=null?numberWithDelimiter(String.valueOf(creditCardObjs[2])):"";//授信总额
			creditCardObjs[3]=creditCardObjs[3]!=null?numberWithDelimiter(String.valueOf(creditCardObjs[3])):"";//使用额度
			creditCardObjs[9]=creditCardObjs[9]!=null?numberWithDelimiter(String.valueOf(creditCardObjs[9])):"";//最高额度
			creditCardObjs[10]=creditCardObjs[10]!=null?numberWithDelimiter(String.valueOf(creditCardObjs[10])):"";//月还额度
			creditCardObj=creditCardObjs;
		}
		//征信情况--征信查询
		String creditSql="select t4.dict_name as QUERY_INTERVAL,t3.SELF_QUERY,t3.LOAN_APPROVAL,t3.CREDIT_CARD_APPROVAL,t3.INTERNET_QUERY,t3.QUERY_NOTES "
						+" from t_bp_loan_order t1 "
						+" left join t_bp_credit_audit_report t2 on t2.LOAN_ORDER_ID=t1.LOAN_ORDER_ID "
						+" left join t_bp_credit_investigation t3 on t3.CAR_ID=t2.CAR_ID "
						+" left join "
						+" (select dict_code,dict_name from t_sys_dict where `STATUS`='A' AND parent_id in " 
						+" (select CODE_ID from t_sys_dict where DICT_CODE='query_interval'))t4 on t4.dict_code = t3.QUERY_INTERVAL "
						+" where t1.PROCESS_STATUS <> 0 and t1.LOAN_ORDER_ID='"+loanOrderId+"' " 
						+" order by  t3.QUERY_INTERVAL";
		List<Object> creditList = publicDao.findBySQL(creditSql);
		//财务情况
		String accountSql="select t3.BANKACCOUNT_NO,t4.dict_name as BANKACCOUNT_TYPE,t3.MON_NAME_01,t3.INCOME01,t3.MON_NAME_02,t3.INCOME02,t3.MON_NAME_03,t3.INCOME03, " 
						+" t3.MON_NAME_04,t3.INCOME04,t3.MON_NAME_05,t3.INCOME05,t3.MON_NAME_06,t3.INCOME06,t3.AVERAGE_INCOME,t3.ANALYSIS " 
						+" from t_bp_loan_order t1 "  
						+" left join t_bp_credit_audit_report t2 on t2.LOAN_ORDER_ID=t1.LOAN_ORDER_ID "  
						+" left join t_bp_accounts_journal t3 on t3.CAR_ID = t2.CAR_ID " 
						+" left join "
						+" (select dict_code,dict_name from t_sys_dict where `STATUS`='A' AND parent_id in " 
						+" (select CODE_ID from t_sys_dict where DICT_CODE='bankAccount_type'))t4 on t4.dict_code = t3.BANKACCOUNT_TYPE "
						+" where t1.PROCESS_STATUS <> 0 and t1.LOAN_ORDER_ID='"+loanOrderId+"' ";
		List<Object> objList = publicDao.findBySQL(accountSql);
		List<AccountJournalVO> accountList =new ArrayList<AccountJournalVO>();
		AccountJournalVO ajvo=new AccountJournalVO();
			for(int i = 0; i < objList.size(); i++){
				Object[] obs = (Object[])objList.get(i);
				AccountJournalVO ajvo_c=(AccountJournalVO) ajvo.clone();
				ajvo_c.setAccountNo(obs[0]==null?"":String.valueOf(obs[0]));//账号后四位
				ajvo_c.setAccountType(obs[1]==null?"":String.valueOf(obs[1]));//账号类型
				ajvo_c.setMonth01(obs[2]==null?"":String.valueOf(obs[2]));//第一个月份
				ajvo_c.setIncome01(obs[3]==null?"":numberWithDelimiter(String.valueOf(obs[3])));//入账金额
				ajvo_c.setMonth02(obs[4]==null?"":String.valueOf(obs[4]));//第二个月份
				ajvo_c.setIncome02(obs[5]==null?"":numberWithDelimiter(String.valueOf(obs[5])));//入账金额
				ajvo_c.setMonth03(obs[6]==null?"":String.valueOf(obs[6]));//第三个月份
				ajvo_c.setIncome03(obs[7]==null?"":numberWithDelimiter(String.valueOf(obs[7])));//入账金额
				ajvo_c.setMonth04(obs[8]==null?"":String.valueOf(obs[8]));//第四个月份
				ajvo_c.setIncome04(obs[9]==null?"":numberWithDelimiter(String.valueOf(obs[9])));//入账金额
				ajvo_c.setMonth05(obs[10]==null?"":String.valueOf(obs[10]));//第五个月份
				ajvo_c.setIncome05(obs[11]==null?"":numberWithDelimiter(String.valueOf(obs[11])));//入账金额
				ajvo_c.setMonth06(obs[12]==null?"":String.valueOf(obs[12]));//第六个月份
				ajvo_c.setIncome06(obs[13]==null?"":numberWithDelimiter(String.valueOf(obs[13])));//入账金额
				ajvo_c.setAvemonIncome(obs[14]==null?"":numberWithDelimiter(String.valueOf(obs[14])));//月均
				ajvo_c.setAnalysis(obs[15]==null?"":String.valueOf(obs[15]));//流水分析
				accountList.add(ajvo_c);
			}
		
		//资产信息
		String assetsSql="select t3.ASSET_ID,t3.REAL_ESTATE,t3.VEHICLE,t3.THE_SAME_INDUSTRY "
				        +" from t_bp_loan_order t1 "
						+" left join t_bp_credit_audit_report t2 on t2.LOAN_ORDER_ID=t1.LOAN_ORDER_ID "
						+" left join t_bp_assets t3 on t2.ASSET_ID=t3.ASSET_ID"
						+" where t1.PROCESS_STATUS <> 0 and t1.LOAN_ORDER_ID='"+loanOrderId+"' ";
		Object[] assetsObjs=(Object[])publicDao.uniqueResult(assetsSql);
		Object assetsObj=null;
		if(assetsObjs!=null){
			assetsObj=assetsObjs;
		}
		
		
		//初审资质分析
		String firstAuditSql="select t3.DESCRIPTION,t4.dict_name as IS_LOCAL_RES,t5.dict_name as MARRIAGE_TYPE,t3.INDUSTRY_TYPE,t3.COM_OPER_DURATION,t6.dict_name as COM_OPER_STATUS, "
							+" t7.dict_name as CREDIT_STATUS,t8.dict_name as PHONE_CHECK_STATUS,t9.dict_name as VISIT_STATUS,t10.dict_name as QULIFICATION_STATUS,t3.NOTE,t3.FIRS_PERS_SUGG,t11.`NAME` as FIRS_PERSONNEL,t3.FIRS_DATE,t3.FIRS_DEP_SUGG "
							+" from t_bp_loan_order t1 "
							+" left join t_bp_credit_audit_report t2 on t2.LOAN_ORDER_ID=t1.LOAN_ORDER_ID "
							+" left join t_bp_first_audit_report t3 on t2.FIRS_ID=t3.FIRS_ID "
							+" left join " 
							+" (select dict_code,dict_name from t_sys_dict where `STATUS`='A' AND parent_id in "  
							+" (select CODE_ID from t_sys_dict where DICT_CODE='is_local_resident'))t4 on t4.dict_code = t3.IS_LOCAL_RES "
							+" left join " 
							+" (select dict_code,dict_name from t_sys_dict where `STATUS`='A' AND parent_id in "  
							+" (select CODE_ID from t_sys_dict where DICT_CODE='marriage_type'))t5 on t5.dict_code = t3.MARRIAGE_TYPE "
							+" left join " 
							+" (select dict_code,dict_name from t_sys_dict where `STATUS`='A' AND parent_id in "  
							+" (select CODE_ID from t_sys_dict where DICT_CODE='com_oper_status'))t6 on t6.dict_code = t3.COM_OPER_STATUS "
							+" left join " 
							+" (select dict_code,dict_name from t_sys_dict where `STATUS`='A' AND parent_id in "  
							+" (select CODE_ID from t_sys_dict where DICT_CODE='credit_status'))t7 on t7.dict_code = t3.CREDIT_STATUS "
							+" left join " 
							+" (select dict_code,dict_name from t_sys_dict where `STATUS`='A' AND parent_id in "  
							+" (select CODE_ID from t_sys_dict where DICT_CODE='phone_check_status'))t8 on t8.dict_code = t3.PHONE_CHECK_STATUS "
							+" left join " 
							+" (select dict_code,dict_name from t_sys_dict where `STATUS`='A' AND parent_id in "  
							+" (select CODE_ID from t_sys_dict where DICT_CODE='visit_status'))t9 on t9.dict_code = t3.VISIT_STATUS "
							+" left join " 
							+" (select dict_code,dict_name from t_sys_dict where `STATUS`='A' AND parent_id in "  
							+" (select CODE_ID from t_sys_dict where DICT_CODE='QULIFICATION_status'))t10 on t10.dict_code = t3.QULIFICATION_STATUS "
							+" left join t_users t11 on t3.FIRS_PERSONNEL = t11.USER_ID "
							+" where t1.PROCESS_STATUS <> 0 and t1.LOAN_ORDER_ID='"+loanOrderId+"' ";
		Object[] firstAuditObjs=(Object[])publicDao.uniqueResult(firstAuditSql);
		Object firstAuditObj = null ;
		if(firstAuditObjs!=null){
			firstAuditObj=firstAuditObjs;
		}
		//终审资质分析
		String finalAuditSql="select t3.DESCRIPTION,t4.`NAME` as FINA_PERSONNEL,t3.FINA_DATE, "
							+" t3.CONTRACT_LOAN_AMOUNT,t3.ACTUAL_LOAN_AMOUNT,t5.dict_name as LOAN_PERIOD_TYPE,t6.dict_name as MONTH_SERVICE_FEE_RATE,t7.dict_name as LOAN_INTEREST_RATE,t3.VISIT_FEE,t3.MONTH_REPAY "
							+" from t_bp_loan_order t1 "
							+" left join t_bp_credit_audit_report t2 on t2.LOAN_ORDER_ID=t1.LOAN_ORDER_ID "
							+" left join t_bp_final_audit_report t3 on t2.FINA_ID=t3.FINA_ID "
							+" left join t_users t4 on t3.FINA_PERSONNEL = t4.USER_ID "
							+" left join " 
							+" (select dict_code,dict_name from t_sys_dict where `STATUS`='A' AND parent_id in "  
							+" (select CODE_ID from t_sys_dict where DICT_CODE='loan_period_type'))t5 on t5.dict_code = t3.LOAN_PERIOD_TYPE "
							+" left join " 
							+" (select dict_code,dict_name from t_sys_dict where `STATUS`='A' AND parent_id in "  
							+" (select CODE_ID from t_sys_dict where DICT_CODE='month_service_fee_rate'))t6 on t6.dict_code = t3.MONTH_SERVICE_FEE_RATE "
							+" left join " 
							+" (select dict_code,dict_name from t_sys_dict where `STATUS`='A' AND parent_id in "  
							+" (select CODE_ID from t_sys_dict where DICT_CODE='loan_interest_rate'))t7 on t7.dict_code = t3.LOAN_INTEREST_RATE "
							+" where t1.PROCESS_STATUS <> 0 and t1.LOAN_ORDER_ID='"+loanOrderId+"' ";
		Object[] finalAuditObjs=(Object[])publicDao.uniqueResult(finalAuditSql);
		Object finalAuditObj = null;
		if(finalAuditObjs!=null){
			finalAuditObjs[3]=finalAuditObjs[3]!=null?numberWithDelimiter(String.valueOf(finalAuditObjs[3])):"";//合同金额
			finalAuditObjs[4]=finalAuditObjs[4]!=null?numberWithDelimiter(String.valueOf(finalAuditObjs[4])):"";//实放金额
			finalAuditObjs[8]=finalAuditObjs[8]!=null?numberWithDelimiter(String.valueOf(finalAuditObjs[8])):"";;//信访费用
			finalAuditObj=finalAuditObjs;
		}
		
		Object[] obj = new Object[8];
		obj[0] = baseObj;
		obj[1] = loanObj;
		obj[2] = creditCardObj;
		obj[3] = creditList;
		obj[4] = accountList;
		obj[5] = assetsObj;
		obj[6] = firstAuditObj;
		obj[7] = finalAuditObj;
		return obj;
	}
	/**
	 * (非 Javadoc) 
	 * <p>Title: findLoanerHisListByLoanerId</p> 
	 * <p>Description: </p> 
	 * @param loanerId
	 * @return 
	 * @see com.qqms.service.LoanerService#findLoanerHisListByLoanerId(java.lang.String)
	 */
	@Override
	public Object[] findLoanerHisListByLoanerId(String loanerId,PageUtil pageUtil) {
		// TODO Auto-generated method stub
		
		String sql="select t1.`NAME` as loanerName,t2.dict_name as GENDER_TYPE,t1.ID_NO,t1.AGE,t1.HUKOU_ADDR,t1.CUR_ADDR,t1.MOBILE_TEL,t1.FIXED_TEL,t3.dict_name as MARRIAGE_TYPE,t4.dict_name as HAS_CHILD, "
					+" t1.EMAIL,t1.QQ_NO,t1.ANNUAL_SALARY,t1.INCOME_SRC,t5.dict_name as MORTGAGE_STATUS,t1.HOUSE_INSTALL_PAY,t1.RENT,t1.CHANGE_CONTENTS,t1.CHANGE_TIEM,t1.CHANGE_PEOPLE "
					 +" from t_bp_loaner_info_his t1 "
					+" left join " 
					+" (select dict_code,dict_name from t_sys_dict where `STATUS`='A' AND parent_id in "  
					+" (select CODE_ID from t_sys_dict where DICT_CODE='gender_type'))t2 on t2.dict_code = t1.GENDER_TYPE " 
					+" left join " 
					+" (select dict_code,dict_name from t_sys_dict where `STATUS`='A' AND parent_id in "  
					+" (select CODE_ID from t_sys_dict where DICT_CODE='marriage_type'))t3 on t3.dict_code = t1.MARRIAGE_TYPE "
					+" left join " 
					+" (select dict_code,dict_name from t_sys_dict where `STATUS`='A' AND parent_id in "  
					+" (select CODE_ID from t_sys_dict where DICT_CODE='has_child'))t4 on t4.dict_code = t1.HAS_CHILD "
					+" left join " 
					+" (select dict_code,dict_name from t_sys_dict where `STATUS`='A' AND parent_id in "  
					+" (select CODE_ID from t_sys_dict where DICT_CODE='mortgage_status'))t5 on t5.dict_code = t1.MORTGAGE_STATUS "
				+" where t1.LOANER_ID="+loanerId;
		List<Object> objList = publicDao.findBySql(sql, pageUtil);
		Long count=publicDao.findTotalCount(sql);
		List<LoanerInfoHisVO> loanerInfoHisVOList=new ArrayList<LoanerInfoHisVO>();
		Object[] obj=new Object[2];
		try {
			LoanerInfoHisVO vo = new LoanerInfoHisVO();
			for(int i = 0; i < objList.size(); i++){
				LoanerInfoHisVO vo_c=(LoanerInfoHisVO) vo.clone();
				Object[] obs = (Object[]) objList.get(i);
				vo_c.setLoanerName(obs[0]==null?"":String.valueOf(obs[0]));
				vo_c.setGenderType(obs[1]==null?"":String.valueOf(obs[1]));
				vo_c.setIdNo(obs[2]==null?"":String.valueOf(obs[2]));
				vo_c.setAge(obs[3]==null?"":String.valueOf(obs[3]));
				vo_c.setHukouAddr(obs[4]==null?"":String.valueOf(obs[4]));
				vo_c.setCurAddr(obs[5]==null?"":String.valueOf(obs[5]));
				vo_c.setMobileTel(obs[6]==null?"":String.valueOf(obs[6]));
				vo_c.setFixedTel(obs[7]==null?"":String.valueOf(obs[7]));
				vo_c.setMarriageType(obs[8]==null?"":String.valueOf(obs[8]));
				vo_c.setHasChild(obs[9]==null?"":String.valueOf(obs[9]));
				vo_c.setEmail(obs[10]==null?"":String.valueOf(obs[10]));
				vo_c.setQqNo(obs[11]==null?"":String.valueOf(obs[11]));
				vo_c.setAnnualSalary(obs[12]==null?"":String.valueOf(obs[12]));
				vo_c.setIncomeSrc(obs[13]==null?"":String.valueOf(obs[13]));
				vo_c.setMortgageStatus(obs[14]==null?"":String.valueOf(obs[14]));
				vo_c.setHouseInstallPay(obs[15]==null?"":String.valueOf(obs[15]));
				vo_c.setRent(obs[16]==null?"":String.valueOf(obs[16]));
				vo_c.setChangeContents(obs[17]==null?"":String.valueOf(obs[17]));
				vo_c.setChangeTime(obs[18]==null?"":String.valueOf(obs[18]));
				vo_c.setChangePeople(obs[19]==null?"":String.valueOf(obs[19]));
			}
			obj[0]=loanerInfoHisVOList;
			obj[1]=count;
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	}
	/**
	 * 
	 * @author: xujianwei
	 * @time:2015年6月24日 下午8:50:40
	 * @Title:numberWithDelimiter
	 * @Description:TODO 给数字金额加千位符“,”（这里描述这个方法的作用）
	 * @param num
	 * @return
	 * @throws:
	 */
	public static String numberWithDelimiter(String str) {
		boolean neg = false;  
	    if (str.startsWith("-")){  //处理负数  
	        str = str.substring(1);  
	        neg = true;  
	    }  
	    String tail = null;  
	    if (str.indexOf('.') != -1){ //处理小数点  
	        tail = str.substring(str.indexOf('.'));  
	        str = str.substring(0, str.indexOf('.'));  
	    }  
	    StringBuilder sb = new StringBuilder(str);  
	    sb.reverse();  
	    for (int i = 3; i < sb.length(); i += 4){  
	        sb.insert(i, ',');  
	    }  
	    sb.reverse();  
	    if (neg){  
	        sb.insert(0, '-');  
	    }  
	    if (tail != null){  
	        sb.append(tail);  
	    }  
	    return sb.toString();  
	} 
	public static void main(String[] args) {
		System.out.println(numberWithDelimiter("50000.00000"));
	}
	
}

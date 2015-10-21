package com.oasys.serviceImpl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oasys.dao.PublicDao;
import com.oasys.model.MonthlyWorkPlan;
import com.oasys.model.Users;
import com.oasys.model.VO.MonthlyWorkPlanModel;
import com.oasys.service.MonthlyWorkPlanService;
import com.oasys.service.UserService;
import com.oasys.util.Collections;
import com.oasys.util.Constants;
import com.oasys.util.PageUtil;
/**
 * 管理中心月度工作计划表serviceImpl
 * @ClassName: MonthlyWorkPlanServiceImpl 
 * @Description: TODO
 * @author PANCHUANHE
 * @date 2015年10月12日 上午9:21:59
 */
@Service(value="monthlyWorkPlanService")
public class MonthlyWorkPlanServiceImpl extends WorkFlowBaseServiceImpl implements MonthlyWorkPlanService {

	@Autowired
	private PublicDao<MonthlyWorkPlan> publicDao;
	@Autowired
	private UserService userService;
	
	
	@Override
	public boolean saveMonthlyWorkPlan(MonthlyWorkPlan monthlyWorkPlan) {
		try {
			Users user = userService.findUserById(Constants.getCurrendUser().getUserId());
			monthlyWorkPlan.setPreparer(user.getUserId());
			monthlyWorkPlan.setAppDept(user.getOrganizeId());
			monthlyWorkPlan.setFillingDate(new Date());
			publicDao.saveOrUpdate(monthlyWorkPlan);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<MonthlyWorkPlanModel> findMonthlyWorkPlanList(PageUtil pageUtil,MonthlyWorkPlanModel monthlyWorkPlanModel) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Integer userId = Constants.getCurrendUser().getUserId();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT ");
			sql.append("m.MWP_ID AS '主表主键',");
			sql.append("m.PREPARER AS '填表人',");
			sql.append("m.POSITION AS '填表人职位',");
			sql.append("m.APP_DEPT AS '所在部门',");
			sql.append("m.FILLING_DATE AS '填表时间',");
			sql.append("m.UFW_RESON AS '为完成原因',");
			sql.append("m.REMARK '主表备注信息',");
			
			sql.append("ma.MWPA_ID AS '字表主键',");
			sql.append("ma.CONT_SUMUP AS '本月工作内容总结',");
			sql.append("ma.COMPLETED1 AS 'COMPLETED1',");
			sql.append("ma.COMPLETED2 AS 'COMPLETED2 ',");
			sql.append("ma.COMPLETED3 AS 'COMPLETED3',");
			sql.append("ma.COMPLETED4 AS 'COMPLETED4',");
			sql.append("ma.CONT_PLAN AS '下月工作内容计划',");
			sql.append("ma.PLAN_COMP1 AS 'PLAN_COMP1',");
			sql.append("ma.PLAN_COMP2 AS 'PLAN_COMP2',");
			sql.append("ma.PLAN_COMP3 AS 'PLAN_COMP3',");
			sql.append("ma.PLAN_COMP4 AS 'PLAN_COMP4',");
			sql.append("ma.REMARK AS '字表备注信息',");
			sql.append("u.ACCOUNT AS '填表人名称',");
			sql.append("o.FULL_NAME AS '所在部门名称' ");
			sql.append("FROM t_oa_ad_monthly_work_plan m ");
			sql.append("LEFT JOIN t_oa_ad_monthly_work_plan_attach ma ON m.MWP_ID = ma.MWP_ID ");
			sql.append("LEFT JOIN qqms.t_users u ON m.PREPARER = u.USER_ID ");
			sql.append("LEFT JOIN qqms.t_organization o ON m.APP_DEPT = o.ORGANIZATION_ID ");
			sql.append("WHERE m.PREPARER = '"+userId+"' ");
			if (StringUtils.isNotBlank(monthlyWorkPlanModel.getAppDateMini())) {
				sql.append("AND m.FILLING_DATE >= '"+monthlyWorkPlanModel.getAppDateMini()+"' ");
			}
			if (StringUtils.isNotBlank(monthlyWorkPlanModel.getAppDateMax())) {
				sql.append("AND m.FILLING_DATE <= '"+monthlyWorkPlanModel.getAppDateMax()+"' ");
			}
			if (StringUtils.isNotBlank(monthlyWorkPlanModel.getRemark())) {
				sql.append("AND m.REMARK like '%"+monthlyWorkPlanModel.getRemark()+"%' ");
			}
			sql.append("ORDER BY m.MWP_ID DESC");
			List<Object> list = publicDao.findBySql(sql.toString(), pageUtil);
			List<MonthlyWorkPlanModel> monthlyWorkPlanModelList = new ArrayList<MonthlyWorkPlanModel>();
			if (Collections.listIsNotEmpty(list)) {
				MonthlyWorkPlanModel model = new MonthlyWorkPlanModel();
				for (int i = 0; i < list.size(); i++) {
					Object[] obj = (Object[])list.get(i);
					MonthlyWorkPlanModel cmodel = (MonthlyWorkPlanModel)model.clone();
					cmodel.setMwpId(obj[0]==null?0:(Integer)obj[0]);
					cmodel.setPreparer(obj[1]==null?0:(Integer)obj[1]);
					cmodel.setPosition(obj[2]==null?"":String.valueOf(obj[0]));
					cmodel.setAppDept(obj[3]==null?0:(Integer)obj[3]);
					cmodel.setFillingDate(obj[4]==null?null:sdf.parse(String.valueOf(obj[4])));
					cmodel.setUfwReson(obj[5]==null?"":String.valueOf(obj[5]));
					cmodel.setRemark(obj[6]==null?"":String.valueOf(obj[6]));
					
					cmodel.setMwpaId(obj[7]==null?0:(Integer)obj[7]);
					cmodel.setContSumup(obj[8]==null?"":String.valueOf(obj[8]));
					cmodel.setCompleted1(obj[9]==null?null:new BigDecimal(String.valueOf(obj[9])));
					cmodel.setCompleted2(obj[10]==null?null:new BigDecimal(String.valueOf(obj[10])));
					cmodel.setCompleted3(obj[11]==null?null:new BigDecimal(String.valueOf(obj[11])));
					cmodel.setCompleted4(obj[12]==null?null:new BigDecimal(String.valueOf(obj[12])));
					cmodel.setContPlan(obj[13]==null?"":String.valueOf(obj[13]));
					cmodel.setPlanComp1(obj[14]==null?null:new BigDecimal(String.valueOf(obj[14])));
					cmodel.setPlanComp2(obj[15]==null?null:new BigDecimal(String.valueOf(obj[15])));
					cmodel.setPlanComp3(obj[16]==null?null:new BigDecimal(String.valueOf(obj[16])));
					cmodel.setPlanComp4(obj[17]==null?null:new BigDecimal(String.valueOf(obj[17])));
					cmodel.setSonRemark(obj[18]==null?"":String.valueOf(obj[18]));
					cmodel.setPreparerName(obj[19]==null?"":String.valueOf(obj[19]));
					cmodel.setAppDeptName(obj[20]==null?"":String.valueOf(obj[20]));
					monthlyWorkPlanModelList.add(cmodel);
				}
			}
			return monthlyWorkPlanModelList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Long countFindMonthlyWorkPlanList() {
		Integer userId = Constants.getCurrendUser().getUserId();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT COUNT(*) FROM t_oa_ad_monthly_work_plan m ");
		sql.append("LEFT JOIN t_oa_ad_monthly_work_plan_attach ma ON m.MWP_ID = ma.MWP_ID ");
		sql.append("LEFT JOIN qqms.t_users u ON m.PREPARER = u.USER_ID ");
		sql.append("LEFT JOIN qqms.t_organization o ON m.APP_DEPT = o.ORGANIZATION_ID ");
		sql.append("WHERE m.PREPARER = '"+userId+"' ");
		return publicDao.findTotalCount(sql.toString());
	}

	@Override
	public boolean delMonthlyWorkPlanbyMwpId(String ids) {
		try {
			//删除主数据
			StringBuffer sql = new StringBuffer("DELETE FROM t_oa_ad_monthly_work_plan WHERE MWP_ID in ("+ids+")");
			publicDao.executeBySql(sql.toString());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}

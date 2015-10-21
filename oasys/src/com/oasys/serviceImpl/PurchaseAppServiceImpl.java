package com.oasys.serviceImpl;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oasys.dao.PublicDao;
import com.oasys.model.AuditProcHis;
import com.oasys.model.Organization;
import com.oasys.model.PurchaseApp;
import com.oasys.model.StatusNameRef;
import com.oasys.model.Users;
import com.oasys.service.AuditProcHisService;
import com.oasys.service.OrganizationService;
import com.oasys.service.PurchaseAppAttachService;
import com.oasys.service.PurchaseAppService;
import com.oasys.service.StatusNameRefService;
import com.oasys.service.UserService;
import com.oasys.service.WorkFlowTaskService;
import com.oasys.util.Collections;
import com.oasys.util.Constants;
import com.oasys.util.PageUtil;
import com.oasys.util.UniqueIdUtil;
import com.oasys.viewModel.PurchaseAppAttachModel;
import com.oasys.viewModel.PurchaseAppModel;
import com.oasys.viewModel.WorkFlowTasksModel;
/**
 * @ClassName: PurchaseAppServiceImpl 
 * @Description: TODO 物料申请ServiceImpl
 * @author PANCHUANHE
 * @date 2015年9月16日 下午1:46:49
 */
@Service(value="purchaseAppService")
public class PurchaseAppServiceImpl extends WorkFlowBaseServiceImpl implements PurchaseAppService {

	@Autowired
	private PublicDao<PurchaseApp> publicDao;
	@Autowired
	private UserService userService;
	@Autowired
	private AuditProcHisService auditProcHisService;
	@Autowired
	private StatusNameRefService statusNameRefService;
	@Autowired
	private PurchaseAppAttachService purchaseAppAttachService;
	@Autowired
	private WorkFlowTaskService workFlowTaskService;
	@Autowired
	private OrganizationService organizationService;
	
	@Override
	public boolean savePurchaseApp(PurchaseApp purchaseApp) {
		try {
			if(purchaseApp.getPaId()==null){
				//新增
				String appNum = UniqueIdUtil.generate("CG");
				Users user = userService.findUserById(Constants.getCurrendUser().getUserId());
				purchaseApp.setAppDept(user.getOrganizeId());//申请单位
				purchaseApp.setAppStatus("1");//申请状态，初始状态
		    	purchaseApp.setApplicantNo(Constants.getCurrendUser().getUserId());//申请人
		    	purchaseApp.setAppNo(appNum);//申请编号
		    	purchaseApp.setProcStatus("1");
		    	purchaseApp.setAppDate(new Date());
		   // 	purchaseApp.setAppType("1");
				publicDao.save(purchaseApp);
			}else{
				//更新
				publicDao.update(purchaseApp);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void updatePurchaseAppTotalAmt(String appNo, BigDecimal count) {
		try {
			StringBuffer hql = new StringBuffer();
			hql.append("update PurchaseApp p set p.totalAmt = '"+count+"' where p.appNo = '"+appNo+"'");
			publicDao.executeHql(hql.toString(), null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<PurchaseAppModel> findPurchaseAppList(PageUtil pageUtil,PurchaseAppModel purchaseAppModel) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Integer userId = Constants.getCurrendUser().getUserId();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT ");
			sql.append("u.ACCOUNT as '申请人姓名',");
			sql.append("o.FULL_NAME as '申请机构名称',");
			sql.append("p.PA_ID as 'ID',");
			sql.append("p.APP_NO as '申请编号',");
			sql.append("IF(p.APP_TYPE='',1,p.APP_TYPE) as '申请类型',");
			sql.append("p.APP_TYPE_OTHER as '其他申请类型',");
			sql.append("p.APPLICANT_NO as '申请人id',");
			sql.append("p.APP_DEPT as '申请部门id',");
			sql.append("p.APP_DATE as '申请时间',");
			sql.append("p.APP_STATUS as '申请状态',");
			sql.append("p.TOTAL_AMT as '申请总额',");
			sql.append("p.PLAN_REC_DATE as '计划到货时间',");
			sql.append("IFNULL(p.PROC_STATUS,1) as '流程状态',");
			sql.append("p.REMARK as '备注信息',");
			sql.append("s.DICT_NAME as '申请类型名称',");
			
			sql.append("pa.ARTICLE_NAME AS '物品名称',");
			sql.append("pa.MODEL AS '型号规格',");
			sql.append("pa.PRICE AS '单价',");
			sql.append("pa.QTY AS '数量',");
			sql.append("pa.TOTAL_AMT AS '合计金额',");
			sql.append("pa.PURPOSE AS '用途',");
			sql.append("pa.`USER` AS '用户id',");
			sql.append("pa.DEPOSITARY AS '保管人id',");
			sql.append("pa.REMARK AS '备注',");
			sql.append("uu.ACCOUNT AS '使用人姓名',");
			sql.append("uuu.ACCOUNT AS '保管人姓名' ");
			sql.append("FROM t_oa_ad_purchase_app p ");
			sql.append("LEFT JOIN t_oa_ad_purchase_app_attach pa ON p.APP_NO = pa.APP_NO ");
			sql.append("LEFT JOIN qqms.t_users u ON p.APPLICANT_NO = u.USER_ID ");
			sql.append("LEFT JOIN qqms.t_users uu ON uu.USER_ID = pa.`USER` ");
			sql.append("LEFT JOIN qqms.t_users uuu ON uuu.USER_ID = pa.DEPOSITARY ");
			sql.append("LEFT JOIN qqms.t_organization o ON p.APP_DEPT = o.ORGANIZATION_ID ");
			sql.append("LEFT JOIN (SELECT DICT_CODE,DICT_NAME FROM qqms.t_sys_dict WHERE PARENT_ID = (SELECT CODE_ID FROM qqms.t_sys_dict WHERE DICT_CODE = 'app_type')) s ON s.DICT_CODE = p.APP_TYPE ");
			sql.append("WHERE p.APPLICANT_NO = "+userId);
			if (StringUtils.isNotBlank(purchaseAppModel.getAppType())) {
				sql.append(" And p.APP_TYPE = '"+purchaseAppModel.getAppType()+"'");
			}
			if(StringUtils.isNotBlank(purchaseAppModel.getAppNo())){
				sql.append(" AND p.APP_NO like '%"+purchaseAppModel.getAppNo()+"%'");
			}
			if(purchaseAppModel.getTotalAmtMini()!=null){
				sql.append(" AND p.TOTAL_AMT >= "+purchaseAppModel.getTotalAmtMini());
			}
			if(purchaseAppModel.getTotalAmtMax()!=null){
				sql.append(" AND p.TOTAL_AMT <= "+purchaseAppModel.getTotalAmtMax());
			}
			if(StringUtils.isNotBlank(purchaseAppModel.getAppDateMini())){
				sql.append(" AND p.APP_DATE >= '"+purchaseAppModel.getAppDateMini()+"'");
			}
			if(StringUtils.isNotBlank(purchaseAppModel.getAppDateMax())){
				sql.append(" AND p.APP_DATE <= '"+purchaseAppModel.getAppDateMax()+"'");
			}
			sql.append(" ORDER BY p.PA_ID DESC");
			List<Object> list = publicDao.findBySql(sql.toString(), pageUtil);
			List<PurchaseAppModel> modelList = new ArrayList<PurchaseAppModel>();
			if(Collections.listIsNotEmpty(list)){
				PurchaseAppModel model = new PurchaseAppModel();
				for (int i = 0; i < list.size(); i++) {
					PurchaseAppModel cmodel = (PurchaseAppModel) model.clone();
					Object[] obj = (Object[]) list.get(i);
					cmodel.setAccount(obj[0] == null?"":String.valueOf(obj[0]));
					cmodel.setFullName(obj[1] == null?"":String.valueOf(obj[1]));
					cmodel.setPaId(obj[2] == null?0:(Integer)obj[2]);
					cmodel.setAppNo(obj[3] == null?"":String.valueOf(obj[3]));
					cmodel.setAppType(obj[4] == null?"":String.valueOf(obj[4]));
					cmodel.setAppTypeOther(obj[5] == null?"":String.valueOf(obj[5]));
					cmodel.setApplicantNo(obj[6] == null?0:(Integer)obj[6]);
					cmodel.setAppDept(obj[7] == null?0:(Integer)obj[7]);
					cmodel.setAppDate(obj[8] == null?null:sdf.parse(String.valueOf(obj[8])));
					cmodel.setAppStatus(obj[9] == null?"":String.valueOf(obj[9]));
					cmodel.setTotalAmt(obj[10] == null?new BigDecimal(0):new BigDecimal(String.valueOf(obj[10])));
					cmodel.setPlanRecDate(obj[11] == null?null:sdf.parse(String.valueOf(obj[11])));
					cmodel.setProcStatus(obj[12] == null?"":String.valueOf(obj[12]));
					cmodel.setRemark(obj[13] == null?"":String.valueOf(obj[13]));
					cmodel.setAppTypeName(obj[14] == null?"":String.valueOf(obj[14]));
					
					cmodel.setArticleName(obj[15] == null?"":String.valueOf(obj[15]));
					cmodel.setModel(obj[16] == null?"":String.valueOf(obj[16]));
					cmodel.setPrice(obj[17] == null?new BigDecimal(0):new BigDecimal(String.valueOf(obj[17])));
					cmodel.setQty(obj[18] == null?0:(Integer)obj[18]);
					cmodel.setZtotalAmt(obj[19] == null?new BigDecimal(0):new BigDecimal(String.valueOf(obj[19])));
					cmodel.setPurpose(obj[20] == null?"":String.valueOf(obj[20]));
					cmodel.setUser(obj[21] == null?0:(Integer)obj[21]);
					cmodel.setDepositary(obj[22] == null?0:(Integer)obj[22]);
					cmodel.setZremark(obj[23] == null?"":String.valueOf(obj[23]));
					cmodel.setUserName(obj[24] == null?"":String.valueOf(obj[24]));
					cmodel.setDepositaryName(obj[25] == null?"":String.valueOf(obj[25]));
					modelList.add(cmodel);
				}
			}
			return modelList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Long countPurchaseAppList() {
		try {
			Integer userId = Constants.getCurrendUser().getUserId();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT COUNT(*) ");
			sql.append("FROM t_oa_ad_purchase_app p ");
			sql.append("LEFT JOIN t_oa_ad_purchase_app_attach pa ON p.APP_NO = pa.APP_NO ");
			sql.append("LEFT JOIN qqms.t_users u ON p.APPLICANT_NO = u.USER_ID ");
			sql.append("LEFT JOIN qqms.t_users uu ON uu.USER_ID = pa.`USER` ");
			sql.append("LEFT JOIN qqms.t_users uuu ON uuu.USER_ID = pa.DEPOSITARY ");
			sql.append("LEFT JOIN qqms.t_organization o ON p.APP_DEPT = o.ORGANIZATION_ID ");
			sql.append("LEFT JOIN (SELECT DICT_CODE,DICT_NAME FROM qqms.t_sys_dict WHERE PARENT_ID = (SELECT CODE_ID FROM qqms.t_sys_dict WHERE DICT_CODE = 'app_type')) s ON s.DICT_CODE = p.APP_TYPE ");
			sql.append("WHERE p.APPLICANT_NO = "+userId);
			return publicDao.findTotalCount(sql.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean delPurchaseAppByPaId(Integer paId) {
		try {
			PurchaseApp purchaseApp = new PurchaseApp();
			purchaseApp.setPaId(paId);
			publicDao.delete(purchaseApp);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public PurchaseApp findPurchaseAppByAppNo(String appNo) {
		StringBuffer hql = new StringBuffer();
		hql.append("from PurchaseApp p where p.appNo = '"+appNo+"'");
		List<PurchaseApp> list = publicDao.find(hql.toString());
		if(Collections.listIsNotEmpty(list)){
			return list.get(0);
		}
		return null;
	}

	@Override
	public boolean startWorkflow(PurchaseApp purchaseApp) {
		//查询该条主数据是否有子数据
		List<PurchaseAppAttachModel> findPurchaseAppAttachList = purchaseAppAttachService.findPurchaseAppAttachList(purchaseApp.getAppNo());
		if (Collections.listIsNotEmpty(findPurchaseAppAttachList)) {
			//开启流程
			String proDefKey = "";
			boolean bl = findZongBuOrFenBu(purchaseApp);
			if(bl){
				//总部
				proDefKey = Constants.PURCHASEAPP_HO;
			}else{
				proDefKey = Constants.PURCHASEAPP_BO;
			}
			String businessKey = proDefKey + "." + purchaseApp.getPaId();
			Map<String, Object> variables = new HashMap<String, Object>();
			String userId = String.valueOf(Constants.getCurrendUser().getUserId());
			variables.put(Constants.CURRENT_USER_KEY,userId);
			ProcessInstance pi = this.runtimeService.startProcessInstanceByKey(proDefKey,businessKey,variables);
			//更新流程大状态
			PurchaseApp purchaseApp2 = publicDao.get(PurchaseApp.class, purchaseApp.getPaId());
			purchaseApp2.setProcStatus("2");//审批中
			publicDao.saveOrUpdate(purchaseApp2);
			return pi != null ? true : false;
		}else{
			return false;
		}
	}

	@Override
	public boolean findZongBuOrFenBu(PurchaseApp purchaseApp) {
		PurchaseApp purchaseApp2 = publicDao.get(PurchaseApp.class,purchaseApp.getPaId());
		if (purchaseApp2!=null) {
			Integer appDeptId = purchaseApp2.getAppDept();
			Organization organization = organizationService.findOrganizationByOrganizationId(appDeptId);
			if(organization!=null){
				//0是总部，1是分部
				if("0".equals(organization.getDeptLevel())){
					return true;
				}
			}
		}
		return false;
	}
	@Override
	public void updatePurchaseAppStatus(Integer paId, String state) {
		//跟新订单状态
		PurchaseApp purchaseApp = publicDao.get(PurchaseApp.class, paId);
		purchaseApp.setAppStatus(state);
		publicDao.saveOrUpdate(purchaseApp);
	}

	@Override
	public List<PurchaseAppModel> findAllPurchaseAppTaskList(Integer page,Integer rows) {
		//登录角色所要办理的的任务集合
		List<WorkFlowTasksModel> taskModelList = workFlowTaskService.findAcceptTaskByGroup(new WorkFlowTasksModel());
		//返回的结果集
		List<PurchaseAppModel> purchaseAppModelList = new ArrayList<PurchaseAppModel>();
		String ids = "";
		//流程id
		String processKeyId = "";
		if (Collections.listIsNotEmpty(taskModelList)) {
			for (WorkFlowTasksModel workFlowTasksModel : taskModelList) {
				ids += workFlowTasksModel.getBusinessKey()+",";
			}
			//id字符串
			ids = ids.substring(0, ids.length()-1);
			//根据id字符串查出的集合
			List<PurchaseAppModel> pamList = findPurchaseAppByTask(ids,new PageUtil(page, rows));
			for (WorkFlowTasksModel wl : taskModelList) {
				for (PurchaseAppModel purchaseAppModel : pamList) {
					//根据部门id获取部门信息
					Organization organization = organizationService.findOrganizationByOrganizationId(purchaseAppModel.getAppDept());
					//0是总部，1是分部
					if (organization!=null && "0".equals(organization.getDeptLevel())) {
						processKeyId = Constants.PURCHASEAPP_HO;
					}else{
						processKeyId = Constants.PURCHASEAPP_BO;
					}
					if(wl.getProcessKey().equals(processKeyId) && Integer.valueOf(wl.getBusinessKey()) == purchaseAppModel.getPaId()){
						purchaseAppModel.setTaskModel(wl);
						purchaseAppModel.setTaskId(wl.getTaskID());
						purchaseAppModelList.add(purchaseAppModel);
					}
				}
			}
			return purchaseAppModelList;
		}
		return null;
	}
	
	@Override
	public Long countAllPurchaseAppTaskList() {
		try {
			//登录角色所要办理的的任务集合
			List<WorkFlowTasksModel> taskModelList = workFlowTaskService.findAcceptTaskByGroup(new WorkFlowTasksModel());
			if (Collections.listIsNotEmpty(taskModelList)) {
				return Long.valueOf(taskModelList.size());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0L;
	}
	
	/**
	 * @throws ParseException 
	 * 根据id字符串查询实体对象集合
	 * @Title: findPurchaseAppByTask 
	 * @Description: TODO
	 * @param @param task
	 * @param @return
	 * @author PANCHUANHE
	 * @return PurchaseApp
	 * @date 2015年9月23日 上午9:28:03
	 * @throws
	 */
	public List<PurchaseAppModel> findPurchaseAppByTask(String ids,PageUtil pageUtil){
		try {
			List<PurchaseAppModel> modelList = new ArrayList<PurchaseAppModel>();
			if (StringUtils.isNotBlank(ids)) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				StringBuffer sql = new StringBuffer();
				sql.append("SELECT ");
				sql.append("u.ACCOUNT as '申请人姓名',");
				sql.append("o.FULL_NAME as '申请机构名称',");
				sql.append("p.PA_ID as 'ID',");
				sql.append("p.APP_NO as '申请编号',");
				sql.append("IF(p.APP_TYPE='',1,p.APP_TYPE) as '申请类型',");
				sql.append("p.APP_TYPE_OTHER as '其他申请类型',");
				sql.append("p.APPLICANT_NO as '申请人id',");
				sql.append("p.APP_DEPT as '申请部门id',");
				sql.append("p.APP_DATE as '申请时间',");
				sql.append("p.APP_STATUS as '申请状态',");
				sql.append("p.TOTAL_AMT as '申请总额',");
				sql.append("p.PLAN_REC_DATE as '计划到货时间',");
				sql.append("IFNULL(p.PROC_STATUS,1) as '流程状态',");
				sql.append("p.REMARK as '备注信息',");
				sql.append("s.DICT_NAME as '申请类型名称',");
				
				sql.append("pa.ARTICLE_NAME AS '物品名称',");
				sql.append("pa.MODEL AS '型号规格',");
				sql.append("pa.PRICE AS '单价',");
				sql.append("pa.QTY AS '数量',");
				sql.append("pa.TOTAL_AMT AS '合计金额',");
				sql.append("pa.PURPOSE AS '用途',");
				sql.append("pa.`USER` AS '用户id',");
				sql.append("pa.DEPOSITARY AS '保管人id',");
				sql.append("pa.REMARK AS '备注',");
				sql.append("uu.ACCOUNT AS '使用人姓名',");
				sql.append("uuu.ACCOUNT AS '保管人姓名' ");
				sql.append("FROM t_oa_ad_purchase_app p ");
				sql.append("LEFT JOIN t_oa_ad_purchase_app_attach pa ON p.APP_NO = pa.APP_NO ");
				sql.append("LEFT JOIN qqms.t_users u ON p.APPLICANT_NO = u.USER_ID ");
				sql.append("LEFT JOIN qqms.t_users uu ON uu.USER_ID = pa.`USER` ");
				sql.append("LEFT JOIN qqms.t_users uuu ON uuu.USER_ID = pa.DEPOSITARY ");
				sql.append("LEFT JOIN qqms.t_organization o ON p.APP_DEPT = o.ORGANIZATION_ID ");
				sql.append("LEFT JOIN (SELECT DICT_CODE,DICT_NAME FROM qqms.t_sys_dict WHERE PARENT_ID = (SELECT CODE_ID FROM qqms.t_sys_dict WHERE DICT_CODE = 'app_type')) s ON s.DICT_CODE = p.APP_TYPE ");
				sql.append("WHERE p.PA_ID in ("+ids+") ORDER BY p.PA_ID DESC");
				List<Object> list = publicDao.findBySql(sql.toString(), pageUtil);
				if(Collections.listIsNotEmpty(list)){
					PurchaseAppModel model = new PurchaseAppModel();
					for (int i = 0; i < list.size(); i++) {
						PurchaseAppModel cmodel = (PurchaseAppModel) model.clone();
						Object[] obj = (Object[]) list.get(i);
						cmodel.setAccount(obj[0] == null?"":String.valueOf(obj[0]));
						cmodel.setFullName(obj[1] == null?"":String.valueOf(obj[1]));
						cmodel.setPaId(obj[2] == null?0:(Integer)obj[2]);
						cmodel.setAppNo(obj[3] == null?"":String.valueOf(obj[3]));
						cmodel.setAppType(obj[4] == null?"":String.valueOf(obj[4]));
						cmodel.setAppTypeOther(obj[5] == null?"":String.valueOf(obj[5]));
						cmodel.setApplicantNo(obj[6] == null?0:(Integer)obj[6]);
						cmodel.setAppDept(obj[7] == null?0:(Integer)obj[7]);
						cmodel.setAppDate(obj[8] == null?null:sdf.parse(String.valueOf(obj[8])));
						cmodel.setAppStatus(obj[9] == null?"":String.valueOf(obj[9]));
						cmodel.setTotalAmt(obj[10] == null?new BigDecimal(0):new BigDecimal(String.valueOf(obj[10])));
						cmodel.setPlanRecDate(obj[11] == null?null:sdf.parse(String.valueOf(obj[11])));
						cmodel.setProcStatus(obj[12] == null?"":String.valueOf(obj[12]));
						cmodel.setRemark(obj[13] == null?"":String.valueOf(obj[13]));
						cmodel.setAppTypeName(obj[14] == null?"":String.valueOf(obj[14]));
						
						cmodel.setArticleName(obj[15] == null?"":String.valueOf(obj[15]));
						cmodel.setModel(obj[16] == null?"":String.valueOf(obj[16]));
						cmodel.setPrice(obj[17] == null?new BigDecimal(0):new BigDecimal(String.valueOf(obj[17])));
						cmodel.setQty(obj[18] == null?0:(Integer)obj[18]);
						cmodel.setZtotalAmt(obj[19] == null?new BigDecimal(0):new BigDecimal(String.valueOf(obj[19])));
						cmodel.setPurpose(obj[20] == null?"":String.valueOf(obj[20]));
						cmodel.setUser(obj[21] == null?0:(Integer)obj[21]);
						cmodel.setDepositary(obj[22] == null?0:(Integer)obj[22]);
						cmodel.setZremark(obj[23] == null?"":String.valueOf(obj[23]));
						cmodel.setUserName(obj[24] == null?"":String.valueOf(obj[24]));
						cmodel.setDepositaryName(obj[25] == null?"":String.valueOf(obj[25]));
						modelList.add(cmodel);
					}
				}
			}
		    return modelList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean saveHoldWorkTask(String taskId) {
		try {
			//获取用户的id
			Integer userId = Constants.getCurrendUser().getUserId();
			//签收任务
			this.taskService.claim(taskId, String.valueOf(userId));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	@Override
	public Long countMyWorkTask() {
		try {
			//流程key
			String proDefKey = Constants.PURCHASEAPP_BO;
			String userId = String.valueOf(Constants.getCurrendUser().getUserId());
			return this.taskService.createTaskQuery().processDefinitionKey(proDefKey).taskCandidateUser(userId).count();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0L;
	}

	@Override
	public boolean saveSubmitTaskBo(PurchaseAppModel purchaseAppModel,String result,AuditProcHis auditProcHis) {
		try {
			
			Task task = this.taskService.createTaskQuery().taskId(purchaseAppModel.getTaskId()).singleResult();// 获取当前执行任务
			Integer userId = Constants.getCurrendUser().getUserId();//获取登录人的id
			updatePurchaseAppStatus(purchaseAppModel.getPaId(),Constants.PURCHASEAPP_BO+"_"+result);// 修改订单的状态
			
			/***保存审批流程履历表 start****/
			String roleCode = (String) taskService.getVariableLocal(task.getId(), "role");
			StatusNameRef statusNameRef = statusNameRefService.getstatusNameRefByStatusCode(Constants.PURCHASEAPP_BO+"_"+result);
			auditProcHis.setAppNo(purchaseAppModel.getAppNo());
			auditProcHis.setAppStatus(statusNameRef.getRefId());
			auditProcHis.setHandleDate(new Date());
			auditProcHis.setHandler(userId);
			auditProcHis.setHandlerRole(roleCode);
			auditProcHisService.saveAuditProcHis(auditProcHis);
			/***保存审批流程履历表 start****/
			
			Map<String, Object> variables = new HashMap<String, Object>();
			//如果是总裁助理通过，那么得判断他是否是借款部门的人，如果是，则需要借款总监审查
			if("zongcaizhulitongguo".equals(result)){
				Organization org = userService.findOrgByuserId(userId);
				if (org!=null) {
					String myid = org.getMyid();
					if ("JK".equals(myid)) {
						result = "jiekuanzongjianchayue";
					}
				}
			}
			variables.put("result", result);
			taskService.complete(task.getId(), variables);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean saveSubmitTaskHo(PurchaseAppModel purchaseAppModel,
			String result, AuditProcHis auditProcHis) {
		try {
			Task task = this.taskService.createTaskQuery().taskId(purchaseAppModel.getTaskId()).singleResult();// 获取当前执行任务
			Integer userId = Constants.getCurrendUser().getUserId();//获取登录人的id
			updatePurchaseAppStatus(purchaseAppModel.getPaId(),Constants.PURCHASEAPP_HO+"_"+result);// 修改订单的状态
			
			/***保存审批流程履历表 start****/
			String roleCode = (String) taskService.getVariableLocal(task.getId(), "role");
			StatusNameRef statusNameRef = statusNameRefService.getstatusNameRefByStatusCode(Constants.PURCHASEAPP_HO+"_"+result);
			auditProcHis.setAppNo(purchaseAppModel.getAppNo());
			auditProcHis.setAppStatus(statusNameRef.getRefId());
			auditProcHis.setHandleDate(new Date());
			auditProcHis.setHandler(userId);
			auditProcHis.setHandlerRole(roleCode);
			auditProcHisService.saveAuditProcHis(auditProcHis);
			/***保存审批流程履历表 start****/
			
			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put("result", result);
			taskService.complete(task.getId(), variables);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	@Override
	public void getDiagramResourceByPaId(HttpServletResponse response,Integer paId){
		// 图片的文件的流
		InputStream in = null;
		try {
			String proDefKey = "";
			String imgName = "";
			PurchaseApp purchaseApp = new PurchaseApp();
			purchaseApp.setPaId(paId);
			boolean bl = findZongBuOrFenBu(purchaseApp);
			if(bl){
				proDefKey = Constants.PURCHASEAPP_HO;//总部
				imgName = "OA_AD_purchase_HO";
			}else{
				proDefKey = Constants.PURCHASEAPP_BO;//分部
				imgName = "OA_AD_purchase_BO";
			}
			String businessKey = proDefKey + "." + paId;
			// 获取当前执行的流程实例
			ProcessInstance pi = this.runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(businessKey).singleResult();
			if(pi!=null){
				// 获取流程定义的实体对象（对应.bpmn文件中的数据）
				ProcessDefinitionEntity pd = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(pi.getProcessDefinitionId());
				// 获取当前执行任务流程
				List<Task> tasks = this.taskService.createTaskQuery().processInstanceBusinessKey(businessKey).list();
				// 获取图片的流程图片名称
				String resourceName = imgName + ".png";
				// 获取图片的文件的流
				in = this.repositoryService.getResourceAsStream(pd.getDeploymentId(),resourceName);
				// 获取图片对象
				BufferedImage bimg;
				bimg = ImageIO.read(in);
				// 得到Graphics2D 对象
				Graphics2D g2d = (Graphics2D) bimg.getGraphics();
				// 设置颜色和画笔粗细
				g2d.setColor(Color.RED);
				g2d.setStroke(new BasicStroke(3));
				// 遍历执行的任务,画图
				for (Task task : tasks) {
					// 根据任务的获取当前执行对象的id,根据执行对象的id获取执行对象的信息
					Execution execution = runtimeService.createExecutionQuery().executionId(task.getExecutionId()).singleResult();
					// 根据当前的执行对象的id获取正在执行的活动信息
					ActivityImpl activityImpl = pd.findActivity(execution.getActivityId());
					// 绘制矩形
					Rectangle2D rectangle = new Rectangle2D.Float(
							activityImpl.getX(), activityImpl.getY(),
							activityImpl.getWidth(), activityImpl.getHeight());
					g2d.draw(rectangle);
				}
				// 写入response输出流中
				ImageIO.write(bimg, "png", response.getOutputStream());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

	@Override
	public void updatePurchaseAppProcessStatus(String paId, String status) {
		PurchaseApp purchaseApp = publicDao.get(PurchaseApp.class, Integer.valueOf(paId));
		if (purchaseApp!=null) {
			purchaseApp.setProcStatus(status);
		}
		//跟新订单状态
		publicDao.saveOrUpdate(purchaseApp);
	}
}

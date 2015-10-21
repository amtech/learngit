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
import com.oasys.model.PPEScrapApp;
import com.oasys.model.PpeScrapAppAttach;
import com.oasys.model.PpeTurnoverApp;
import com.oasys.model.PpeTurnoverAttach;
import com.oasys.model.PurchaseApp;
import com.oasys.model.Role;
import com.oasys.model.StatusNameRef;
import com.oasys.model.Users;
import com.oasys.service.AuditProcHisService;
import com.oasys.service.OrganizationService;
import com.oasys.service.PpeTurnoverAppAttachService;
import com.oasys.service.PpeTurnoverAppService;
import com.oasys.service.StatusNameRefService;
import com.oasys.service.UserService;
import com.oasys.service.WorkFlowTaskService;
import com.oasys.util.Collections;
import com.oasys.util.Constants;
import com.oasys.util.PageUtil;
import com.oasys.util.UniqueIdUtil;
import com.oasys.viewModel.PpeTurnoverModel;
import com.oasys.viewModel.PurchaseAppAttachModel;
import com.oasys.viewModel.PurchaseAppModel;
import com.oasys.viewModel.WorkFlowTasksModel;

/**
 *author:yuanzhongqiu
 *Time:2015年9月18日  下午5:24:55
 * @param <E>
 */
@Service(value="ppeTurnoverAppService")
public class PpeTurnoverAppServiceImpl extends WorkFlowBaseServiceImpl implements PpeTurnoverAppService {
	
	private static final String PpeTurnoverApp = null;
	@Autowired
	private PublicDao<PpeTurnoverApp> publicDao;
	@Autowired
	private UserService userService;
	@Autowired
	private AuditProcHisService auditProcHisService;
	@Autowired
	private StatusNameRefService statusNameRefService;
	@Autowired
	private WorkFlowTaskService workFlowTaskService;
	@Autowired
	private PpeTurnoverAppAttachService ppeTurnoverAppAttachService;
	@Autowired
	private OrganizationService organizationService;
	@Override
	public List<PpeTurnoverModel> findAllPpeTurnover(PageUtil pageUtil,PpeTurnoverModel ppeTurnoverModel) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Integer userId = Constants.getCurrendUser().getUserId();
			StringBuffer sql = new StringBuffer();
			sql.append("select ");
			sql.append("t1.APP_NO,");
			sql.append("t2.PPE_NO,");
			sql.append("t2.PPE_NAME,");
			sql.append("t2.QTY,");
			sql.append("t2.UNIT,");
			sql.append("t1.PPE_TOTAL_AMT,");
			sql.append("t1.APP_STATUS,");
			sql.append("o.FULL_NAME as '申请机构名称',");
//			sql.append("t1.APP_DATE,");
			sql.append("u.ACCOUNT  as '申请人姓名', ");
			sql.append("oo.FULL_NAME as '接收机构名称',");
			sql.append("uu.ACCOUNT as '接收人姓名', ");
			sql.append("t1.APP_DATE, ");
			sql.append("t1.PTA_ID ");
			sql.append("from t_oa_ad_ppe_turnover_app t1 ");
			sql.append("left join qqms.t_users u ON t1.APPLICANT_NO = u.USER_ID ");
			sql.append("left join qqms.t_users uu ON t1.TAKEOVER_USER = uu.USER_ID ");
			sql.append("left join qqms.t_organization o ON t1.APP_DEPT = o.ORGANIZATION_ID ");
			sql.append("left join qqms.t_organization oo ON t1.TAKEOVER_DEPT = o.ORGANIZATION_ID ");
			sql.append("left join t_oa_ad_ppe_turnover_attach t2 ON t1.APP_NO=t2.APP_NO ");
			sql.append("where 1=1 and t1.APPLICANT_NO="+userId);
			if(StringUtils.isNotBlank(ppeTurnoverModel.getAppDateBefore())){
				sql.append(" AND t1.APP_DATE >= '"+ppeTurnoverModel.getAppDateBefore()+"'");
			}
			if(StringUtils.isNotBlank(ppeTurnoverModel.getAppDateAfter())){
				sql.append(" AND t1.APP_DATE <= '"+ppeTurnoverModel.getAppDateAfter()+"'");
			}
			List<Object> list = publicDao.findBySql(sql.toString(), pageUtil);
			System.out.println(list);
			List<PpeTurnoverModel> modelList = new ArrayList<PpeTurnoverModel>();
			if (Collections.listIsNotEmpty(list)) {
				PpeTurnoverModel model = new PpeTurnoverModel();
				for (int i=0;i<list.size();i++) {
				 PpeTurnoverModel cloneModel=(PpeTurnoverModel)model.clone();
				 Object[] obj = (Object[]) list.get(i);
				 cloneModel.setAppNo(obj[0]==null?"":String.valueOf(obj[0]));
				 cloneModel.setPpeNo(obj[1] == null?"":String.valueOf(obj[1]));
				 cloneModel.setPpeName(obj[2] == null?"":String.valueOf(obj[2]));
				 cloneModel.setQty(obj[3] == null?0:(Integer)obj[3]);
				 cloneModel.setUnit(obj[4] == null?"":String.valueOf(obj[4]));
				 cloneModel.setPpeTotalAmt(obj[5] == null?new BigDecimal(0):new BigDecimal(String.valueOf(obj[5])));
				 cloneModel.setAppStatus(obj[6] == null?"":String.valueOf(obj[6]));
				 cloneModel.setAppDeptName(obj[7] == null?"":String.valueOf(obj[7]));
				 cloneModel.setName(obj[8] == null?"":String.valueOf(obj[8]));
				 cloneModel.setTakeoverDeptName(obj[9] == null?"":String.valueOf(obj[9]));
				 cloneModel.setTakeoverUserName(obj[10] == null?"":String.valueOf(obj[10]));
				 cloneModel.setAppDate(obj[11] == null?format.parse(""):format.parse(String.valueOf(obj[11])));
				 cloneModel.setPtaId(obj[12] == null?0:(Integer)obj[12]);
				 modelList.add(cloneModel);
				}
			}
			return modelList;
		} catch (Exception e) {
			e.printStackTrace();
		}
	return null;
	}
	@Override
	public boolean savePpeTurnoverApp(PpeTurnoverApp ppeTurnoverApp) {
		try {
			if(ppeTurnoverApp.getPtaId()==null){
				//新增
				String appNo=UniqueIdUtil.generate("YJ");
				Users user = userService.findUserById(Constants.getCurrendUser().getUserId());
				ppeTurnoverApp.setAppNo(appNo);
				ppeTurnoverApp.setAppDept(user.getOrganizeId());//申请单位
				ppeTurnoverApp.setAppStatus("1");//申请状态，初始状态
				ppeTurnoverApp.setApplicantNo(Constants.getCurrendUser().getUserId());//申请人
				ppeTurnoverApp.setAppDate(new Date());
				ppeTurnoverApp.setProcStatus("1");
				publicDao.save(ppeTurnoverApp);
			}else{
				//更新
				publicDao.update(ppeTurnoverApp);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	@Override
	public Long findAllPpeTurnoverCount() {
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT COUNT(*) ");
			sql.append("FROM t_oa_ad_ppe_turnover_app t1 ");
			sql.append("LEFT JOIN t_oa_ad_ppe_turnover_attach t2 ON t1.APP_NO=t2.APP_NO ");
			sql.append("WHERE t1.APPLICANT_NO="+Constants.getCurrendUser().getUserId());
			Long totalCount = publicDao.findTotalCount(sql.toString());
			return totalCount;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public boolean delPpeTurnoverByPaId(String ids) {
		boolean flag = false;
		if (StringUtils.isNotBlank(ids)) {
			String[] idsArray = ids.split(",");
			for (int i = 0; i < idsArray.length; i++) {
				String id = String.valueOf((idsArray[i]));
//				PpeTurnoverApp ppeTurnoverApp = publicDao.get(PpeTurnoverApp.class, Integer.parseInt(id));
				PpeTurnoverApp ppeTurnoverApp = new PpeTurnoverApp();
				ppeTurnoverApp.setPtaId(Integer.parseInt(id));
//				ppeTurnoverApp.setInvestorStatus("I");
				publicDao.delete(ppeTurnoverApp);
			}
			flag = true;
		}
		return flag;
	}
	@Override
	public List<PpeTurnoverApp> findAllPpeTurnover2(PageUtil pageUtil,
			PpeTurnoverApp ppeTurnoverApp) {
		StringBuffer hql = new StringBuffer();
		hql.append("from PpeTurnoverApp p1 ");
		hql.append("WHERE p1.applicantNo = "+Constants.getCurrendUser().getUserId());
		List<PpeTurnoverApp> list = publicDao.find(hql.toString(),pageUtil);
		return list;
	}
	@Override
	public boolean startWorkflow(PpeTurnoverApp ppeTurnoverApp) {
		List<PpeTurnoverAttach> ppeTurnoverAttachList = ppeTurnoverAppAttachService.findListByAppNo(ppeTurnoverApp.getAppNo());
		if (Collections.listIsNotEmpty(ppeTurnoverAttachList)) {
			String proDefKey="";
			boolean bl = findZongBuOrFenBu(ppeTurnoverApp);
			if(bl){
				//总部
				proDefKey = Constants.PPETURNOVERAPP_HO;
			}else{
				proDefKey = Constants.PPETURNOVERAPP_BO;
			}
			//开启流程
			String businessKey = proDefKey + "." + ppeTurnoverApp.getPtaId();
			Map<String, Object> variables = new HashMap<String, Object>();
			String userId = String.valueOf(Constants.getCurrendUser().getUserId());
			variables.put(Constants.CURRENT_USER_KEY,userId);
			variables.put("inputUser", ppeTurnoverApp.getTakeoverUser().toString());
			ProcessInstance pi = this.runtimeService.startProcessInstanceByKey(proDefKey,businessKey,variables);
			//更新流程大状态
			PpeTurnoverApp ppeTurnoverApp2 = publicDao.get(PpeTurnoverApp.class, ppeTurnoverApp.getPtaId());
			ppeTurnoverApp2.setProcStatus("2");//审批中
			publicDao.saveOrUpdate(ppeTurnoverApp2);
			return pi != null ? true : false;
		} else {
			return false;
		}
		
	}
	@Override
	public boolean findZongBuOrFenBu(PpeTurnoverApp ppeTurnoverApp) {
		PpeTurnoverApp ppeTurnoverApp2 = publicDao.get(PpeTurnoverApp.class,ppeTurnoverApp.getPtaId());
		if (ppeTurnoverApp2!=null) {
			Integer appDeptId = ppeTurnoverApp2.getAppDept();
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
	public List<PpeTurnoverAttach> findAllPpeTurnoverAppTaskList(Integer page,
			Integer rows) {
		//登录角色所要办理的的任务集合
				List<WorkFlowTasksModel> taskModelList = workFlowTaskService.findAcceptTaskByGroup(new WorkFlowTasksModel());
				List<PpeTurnoverApp> ppeTurnoverAppList = new ArrayList<PpeTurnoverApp>();//返回的结果集
				List<PpeTurnoverAttach> attchList = new ArrayList<PpeTurnoverAttach>();
				String ids = "";
				if (Collections.listIsNotEmpty(taskModelList)) {
					for (WorkFlowTasksModel workFlowTasksModel : taskModelList) {
						ids += workFlowTasksModel.getBusinessKey()+",";
					}
					//id字符串
					ids = ids.substring(0, ids.length()-1);
					//根据id字符串查出的集合
					List<PpeTurnoverApp> pamList = findPpeTurnoverAppByTask(ids,new PageUtil(page, rows));
					for (WorkFlowTasksModel wl : taskModelList) {
						for (PpeTurnoverApp ppeTurnoverApp : pamList) {
							/*if(Integer.valueOf(wl.getBusinessKey()) == ppeTurnoverApp.getPtaId()){*/
							if (wl.getProcessKey().equals(ppeTurnoverApp.getAppStatus().split("_")[0]) && Integer.valueOf(wl.getBusinessKey()) == ppeTurnoverApp.getPtaId()) {
								ppeTurnoverApp.setTaskModel(wl);
								ppeTurnoverApp.setTaskID(wl.getTaskID());
								ppeTurnoverApp.setAssistant(wl.getAssistant());
								ppeTurnoverApp.setFormKey(wl.getFormKey());
								ppeTurnoverAppList.add(ppeTurnoverApp);
							}
						}
					}
//					return ppeTurnoverAppList;
					for (PpeTurnoverApp ppeTurnoverApp : ppeTurnoverAppList) {
						ppeTurnoverApp.setAppDeptName(organizationService.getOrgNameByID(ppeTurnoverApp.getAppDept()));
						ppeTurnoverApp.setTakeoverDeptName(organizationService.getOrgNameByID(ppeTurnoverApp.getTakeoverDept()));
						ppeTurnoverApp.setName(userService.getUserByID(ppeTurnoverApp.getApplicantNo()).getName());
						ppeTurnoverApp.setTakeoverUserName(userService.getUserByID(ppeTurnoverApp.getTakeoverUser()).getName());
						List<PpeTurnoverAttach> tmpList = ppeTurnoverAppAttachService.findPpeAttachList(ppeTurnoverApp.getAppNo());
						if(tmpList.size()>0){
							for(PpeTurnoverAttach ppeAtt : tmpList){
								ppeAtt.setPpeTurnoverApp(ppeTurnoverApp);;
							}
							attchList.addAll(tmpList);
						}else{
							PpeTurnoverAttach ppeTurnoverAttach = new PpeTurnoverAttach();
							ppeTurnoverAttach.setAppNo(ppeTurnoverApp.getAppNo());
							ppeTurnoverAttach.setPpeTurnoverApp(ppeTurnoverApp);
							attchList.add(ppeTurnoverAttach);
						}
					}
				}
				return attchList;
	}
	@Override
	public Long countAllPpeTurnoverAppTaskList() {
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
	
	public List<PpeTurnoverApp> findPpeTurnoverAppByTask(String ids,PageUtil pageUtil){
		if (StringUtils.isNotBlank(ids)) {
			StringBuffer hql = new StringBuffer();
			hql.append("from PpeTurnoverApp p where p.ptaId in ("+ids+") order by ptaId desc");
			List<PpeTurnoverApp> list = publicDao.find(hql.toString(),pageUtil);
			
			return list;
		}
		
		return null;
	}
	@Override
	public boolean saveHoldWorkTask(String taskId) {
		try {
			//获取用户的id
			Integer userId = Constants.getCurrendUser().getUserId();
			//办理任务
			this.taskService.claim(taskId, String.valueOf(userId));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

/*	@Override
	public Long countMyWorkTask() {
		try {
			//流程key
			String proDefKey = Constants.PPETURNOVERAPP_HO;
			String userId = String.valueOf(Constants.getCurrendUser().getUserId());
			return this.taskService.createTaskQuery().processDefinitionKey(proDefKey).taskCandidateUser(userId).count();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0L;
	}*/
	@Override
	public boolean saveSubmitTask(PpeTurnoverApp ppeTurnoverApp, String result,
			AuditProcHis auditProcHis) {
	try {
		Task task = this.taskService.createTaskQuery().taskId(ppeTurnoverApp.getTaskID()).singleResult();
		Integer userId = Constants.getCurrendUser().getUserId();
		updatePurchaseAppStatus(ppeTurnoverApp.getPtaId(),result);// 修改订单的状态
		
		String proDefKey="";
		boolean bl = findZongBuOrFenBu(ppeTurnoverApp);
		if(bl){
			//总部
			proDefKey = Constants.PPETURNOVERAPP_HO;
		}else{
			proDefKey = Constants.PPETURNOVERAPP_BO;
		}
		
		/***保存审批流程履历表 start****/
		String roleCode = (String) taskService.getVariableLocal(task.getId(), "role");
		StatusNameRef statusNameRef = statusNameRefService.getstatusNameRefByStatusCode(proDefKey+"_"+result);
		auditProcHis.setAppNo(ppeTurnoverApp.getAppNo());
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
	public void updatePurchaseAppStatus(Integer ptaId, String state) {
		//跟新订单状态
		
		PpeTurnoverApp ppeTurnoverApp = publicDao.get(PpeTurnoverApp.class, ptaId);
		String proDefKey="";
		boolean bl = findZongBuOrFenBu(ppeTurnoverApp);
		if (bl) {
			proDefKey=Constants.PPETURNOVERAPP_HO;
		} else {
			proDefKey=Constants.PPETURNOVERAPP_BO;
		}
		ppeTurnoverApp.setAppStatus(proDefKey+"_"+state);
		publicDao.saveOrUpdate(ppeTurnoverApp);
		
	}
	@Override
	public PpeTurnoverApp findPpeTurnoverAppByID(String ptaId) {
		// TODO Auto-generated method stub
		StringBuffer hql= new StringBuffer();
		hql.append("from PpeTurnoverApp where ptaId="+Integer.parseInt(ptaId));
		PpeTurnoverApp ppeTurnoverApp = publicDao.find(hql.toString()).get(0);
		return ppeTurnoverApp;
	}
	@Override
	public List<PpeTurnoverAttach> findPpeList(PageUtil pageUtil,
			PpeTurnoverApp ppeTurnover) {
		String hql = getFindHQL(ppeTurnover);
		List<PpeTurnoverApp> list = publicDao.find(hql, pageUtil);
		List<PpeTurnoverAttach> attachList = new ArrayList<PpeTurnoverAttach>();
		for (PpeTurnoverApp ppeTurnoverApp : list) {
			ppeTurnoverApp.setAppDeptName(organizationService.getOrgNameByID(ppeTurnoverApp.getAppDept()));
			ppeTurnoverApp.setTakeoverDeptName(organizationService.getOrgNameByID(ppeTurnoverApp.getTakeoverDept()));
			ppeTurnoverApp.setName(userService.getUserByID(ppeTurnoverApp.getApplicantNo()).getName());
			ppeTurnoverApp.setTakeoverUserName(userService.getUserByID(ppeTurnoverApp.getTakeoverUser()).getName());
			List<PpeTurnoverAttach> tmpList = ppeTurnoverAppAttachService.findPpeAttachList(ppeTurnoverApp.getAppNo());//设置查询条件 查询固定资产列表信息
			//填充List对象
			if(tmpList.size()>0){
				for(PpeTurnoverAttach ppeAtt : tmpList){
					ppeAtt.setPpeTurnoverApp(ppeTurnoverApp);;
				}
				attachList.addAll(tmpList);
			}else{
				PpeTurnoverAttach ppeTurnoverAttach = new PpeTurnoverAttach();
				ppeTurnoverAttach.setAppNo(ppeTurnoverApp.getAppNo());
				ppeTurnoverAttach.setPpeTurnoverApp(ppeTurnoverApp);
				attachList.add(ppeTurnoverAttach);
			}
		}
		return attachList;
	}
	
	private String getFindHQL(PpeTurnoverApp ppeTurnoverApp){
		String hql = "from PpeTurnoverApp where 1=1 and applicantNo = "+Constants.getCurrendUser().getUserId();
		
		if(StringUtils.isNotBlank(ppeTurnoverApp.getAppDateBefore())){
			hql += " and appDate >='" + ppeTurnoverApp.getAppDateBefore()+"'";  //申请开始日期
		}
		if(StringUtils.isNotBlank(ppeTurnoverApp.getAppDateAfter())){
			hql += " and appDate <='" + ppeTurnoverApp.getAppDateAfter()+"'";  //申请结束日期
		}
		if(null != ppeTurnoverApp.getAppDept()){
			hql += " and appDept ='"+ppeTurnoverApp.getAppDept()+"'";
		}
		if(StringUtils.isNotBlank(ppeTurnoverApp.getProcStatus())){
			hql+=" and procStatus='"+ppeTurnoverApp.getProcStatus()+"'";
		}
		hql += " order by appDate desc";
		return hql;
	}
	
	@Override
	public Long findPpeListCount(PpeTurnoverApp ppeTurnoverApp) {
		String hql = "select count(*) "+ getFindHQL(ppeTurnoverApp);
		return publicDao.count(hql);
	}
	@Override
	public PpeTurnoverApp findPpeTurnoverAppByAppNo(String appNo) {
		StringBuffer hql= new StringBuffer();
		hql.append("from PpeTurnoverApp where appNo='"+appNo+"'");
		PpeTurnoverApp ppeTurnoverApp = publicDao.find(hql.toString()).get(0);
		return ppeTurnoverApp;
	}
	@Override
	public void updatePpeTurnoverappProceStatus(Integer id, String state) {
		// TODO Auto-generated method stub
		PpeTurnoverApp ppeTurnoverApp2 = publicDao.get(PpeTurnoverApp.class, id);
		ppeTurnoverApp2.setProcStatus(state);
		publicDao.saveOrUpdate(ppeTurnoverApp2);
	}
	@Override
	public void getDiagramResourceByPaId(
			HttpServletResponse httpServletResponse, Integer ptaId) {
		// 图片的文件的流
				InputStream in = null;
				try {
					String proDefKey = "";
					String imgName = "";
					PpeTurnoverApp ppeTurnoverApp= new PpeTurnoverApp();
					ppeTurnoverApp.setPtaId(ptaId);
					boolean bl = findZongBuOrFenBu(ppeTurnoverApp);
					if(bl){
						proDefKey = Constants.PPETURNOVERAPP_HO;//总部
						imgName = "OA_AD_PpeTurnoverApp_HO";
					}else{
						proDefKey = Constants.PPETURNOVERAPP_BO;//分部
						imgName = "OA_AD_PpeTurnoverApp_BO";
					}
					String businessKey = proDefKey + "." + ptaId;
					// 获取当前执行的流程实例
					ProcessInstance pi = this.runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(businessKey).singleResult();
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
					ImageIO.write(bimg, "png", httpServletResponse.getOutputStream());
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
	public StatusNameRef findAppStatusByID(String appStatus) {
		// TODO Auto-generated method stub
		return statusNameRefService.getStatusNameRefByStatusID(appStatus);
	}
	@Override
	public void delPpeTurnover(String appNo) {
		// TODO Auto-generated method stub
		publicDao.executeHql("delete from PpeTurnoverApp where appNo = '" + appNo+"'");
		publicDao.executeHql("delete from PpeTurnoverAttach where appNo = '" + appNo+"'");
	}
}



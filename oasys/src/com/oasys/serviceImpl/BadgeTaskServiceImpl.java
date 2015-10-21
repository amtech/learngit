package com.oasys.serviceImpl;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.ActivitiTaskAlreadyClaimedException;
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
import com.oasys.model.BadgeApp;
import com.oasys.model.StatusNameRef;
import com.oasys.service.AuditProcHisService;
import com.oasys.service.BadgeAppService;
import com.oasys.service.BadgeTaskService;
import com.oasys.service.StatusNameRefService;
import com.oasys.service.WorkFlowTaskService;
import com.oasys.util.Collections;
import com.oasys.util.Constants;
import com.oasys.util.PageUtil;
import com.oasys.viewModel.BadgeAppModel;
import com.oasys.viewModel.WorkFlowTasksModel;
@Service("badgeTaskService")
@SuppressWarnings("unchecked")
public class BadgeTaskServiceImpl  extends WorkFlowBaseServiceImpl  implements BadgeTaskService
{

	
	//申请状态
	@Autowired
	private StatusNameRefService  statusNameRefService;

	@Autowired
	private AuditProcHisService auditProcHisService;
	
	@Autowired
	private BadgeAppService badgeAppService;
	
	@Autowired
	private PublicDao<BadgeApp> publicDao;
	
	@Autowired
	private WorkFlowTaskService workFlowTaskService;
	
	
	@Override
	public boolean badgeAppStartProcessInstance(Integer pnrId) {
		// TODO Auto-generated method stub
		// 获取启动实例的key
		String proDefKey = Constants.BADGEAPP;
		// 定义businessKey
		String businessKey = proDefKey + "." + pnrId;
		// 指定流程变量
		Map<String, Object> variables = new HashMap<String, Object>();
		String userId = String.valueOf(Constants.getCurrendUser().getUserId());
		variables.put(Constants.CURRENT_USER_KEY, userId);
		// 启动流程实例
		ProcessInstance pi = this.runtimeService.startProcessInstanceByKey(
				proDefKey, businessKey, variables);
		if(pi!=null){
			//申请时间
			BadgeApp badgeApp = publicDao.get(BadgeApp.class, pnrId);
			badgeApp.setAppDate(new Date());
			publicDao.saveOrUpdate(badgeApp);
			return true;
		}
		return  false;
	}

	@Override
	public List<BadgeAppModel> findNotTaskClimList(BadgeApp badgeApp,PageUtil pageUtil) {
		WorkFlowTasksModel taskModel = new WorkFlowTasksModel();
		taskModel.setProcessKey(Constants.BADGEAPP);
		List<WorkFlowTasksModel> workList = workFlowTaskService.findAcceptTaskByGroup(taskModel);
		if(null == workList || workList.size() == 0)return new ArrayList<BadgeAppModel>();
		//获得申请的编号列表
		String sql="SELECT APP_NO FROM t_oa_ad_badge_app WHERE 1=1  AND  PNR_ID in   ("+getTaskPPEids(workList)+") ";
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
		//根据编号集合获得工牌申请的详情
		List<BadgeAppModel> badgeAppList = badgeAppService.getBadgeApp(sList);
		//将工作流信息字段更新到PPEScrapApp对象中
		for (WorkFlowTasksModel wl : workList) {
			for (BadgeAppModel badgeAppModel : badgeAppList) {
				
				if( Integer.valueOf(wl.getBusinessKey()).intValue() == badgeAppModel.getPnrId().intValue() ){
					badgeAppModel.setTaskModel(wl);
					badgeAppModel.setTaskId(wl.getTaskID());
					badgeAppModel.setAssistant(wl.getAssistant());
					badgeAppModel.setFormKey(wl.getFormKey());
				}
			}
		}
		
		return badgeAppList;
	}

	@Override
	public Long findTotal(BadgeApp badgeApp) {
		try {
			WorkFlowTasksModel taskModel = new WorkFlowTasksModel();
			taskModel.setProcessKey(Constants.BADGEAPP);
			List<WorkFlowTasksModel> workList = workFlowTaskService.findAcceptTaskByGroup(taskModel);
			if(null == workList || workList.size() == 0)return 0L;
			
			String sql="SELECT count(*) FROM t_oa_ad_badge_app WHERE 1=1  and PNR_ID  in  ("+getTaskPPEids(workList)+") ";
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
			Long totalCount = publicDao.findTotalCount(sql);
			
			return totalCount;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0L;
	}

	//获取task中工牌申请表的id集合
	private String getTaskPPEids(List<WorkFlowTasksModel> workList){
		String ids = "";
		for (WorkFlowTasksModel workFlowTasksModel : workList) {
			ids+=workFlowTasksModel.getBusinessKey()+",";
		}
		if(ids.length()>0){
			ids = ids.substring(0, ids.length()-1);
		}
		return ids;
	}
	
	@Override
	public void getTaskUserClaim(String taskId) throws ActivitiTaskAlreadyClaimedException{
		//领取人id
		Integer userId=Constants.getCurrendUser().getUserId();
		this.taskService.claim(taskId, String.valueOf(userId));
	}

	@Override
	public boolean addBadgeTaskMgr(BadgeAppModel badgeAppModel) {
		try {
			String appstatus=badgeAppModel.getResult();	
			Task task = this.taskService.createTaskQuery().taskId(badgeAppModel.getTaskId()).singleResult();// 获取当前执行任务
			
			//更改工牌申请主表信息
			BadgeApp badgeApp = publicDao.get(BadgeApp.class, badgeAppModel.getPnrId());
			badgeApp.setAppStatus(appstatus);
			//当申请流程为撤销时，更改流程状态
			if( StringUtils.equals(appstatus,"RevokeApply")){
				badgeApp.setProcStatus("5");
			}
			publicDao.saveOrUpdate(badgeApp);

			//执行任务
			Map<String, Object> variables = new HashMap<String, Object>();
			if(StringUtils.isNotBlank(appstatus)){
				variables.put("result", appstatus);// 设置流程变量
			}
			taskService.complete(badgeAppModel.getTaskId(), variables);//向task发送消息 跳转下一流程
			
			//填写审批意见表
			StatusNameRef statusNameRef = statusNameRefService.getstatusNameRefByStatusCode(Constants.BADGEAPP+"_"+appstatus);
			AuditProcHis auditProcHis=new AuditProcHis();
			auditProcHis.setAppNo(badgeAppModel.getAppNo());
			auditProcHis.setAppStatus(statusNameRef.getRefId());
			auditProcHis.setHandleDate(new Date());
			auditProcHis.setHandler(Constants.getCurrendUser().getUserId());
			auditProcHis.setHandleResult(badgeAppModel.getIsSuccess());
			auditProcHis.setHandlerRole(Constants.getTaskRoleCodeMap().get(task.getTaskDefinitionKey()));
			auditProcHis.setRemark(badgeAppModel.getIdeaRemark());
			auditProcHisService.saveAuditProcHis(auditProcHis);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void getDiagramResourceByPaId(HttpServletResponse response,
			Integer pnrId) {
		// 图片的文件的流
				InputStream in = null;
				try {
					String proDefKey = Constants.BADGEAPP;
					String imgName ="OA_AD_BadgeApp";
					
					String businessKey = proDefKey + "." + pnrId;
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
					ImageIO.write(bimg, "png", response.getOutputStream());
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

	
	
	
	
	
	
	
	
}

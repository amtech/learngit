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
import com.oasys.model.ConsumablesApp;
import com.oasys.model.ConsumablesAppAttach;
import com.oasys.model.Organization;
import com.oasys.model.StatusNameRef;
import com.oasys.service.AuditProcHisService;
import com.oasys.service.ConsumablesAppAttachService;
import com.oasys.service.ConsumablesAppService;
import com.oasys.service.OrganizationService;
import com.oasys.service.StatusNameRefService;
import com.oasys.service.UserService;
import com.oasys.service.WorkFlowTaskService;
import com.oasys.util.Constants;
import com.oasys.util.PageUtil;
import com.oasys.viewModel.WorkFlowTasksModel;

@Service(value="consumablesAppService")
public class ConsumablesAppServiceImpl extends WorkFlowBaseServiceImpl implements ConsumablesAppService {

	@Autowired
	public PublicDao<ConsumablesApp> publicDao;
	@Autowired
	private OrganizationService orgService;
	@Autowired
	private UserService userService;
	@Autowired
	private WorkFlowTaskService workFlowTaskService;
	@Autowired
	private ConsumablesAppAttachService consumablesAppAttachService;
	@Autowired
	private StatusNameRefService statusNameService;
	@Autowired
	private AuditProcHisService auditProcHisService;
	/**
	 * 
	 * @time:2015年9月30日 上午9:52:38
	 * @Title:getHql
	 * @Description:TODO（这里描述这个方法的作用）
	 * @return 查询hql语句
	 * @throws:
	 */
	public static String getHql(){
		String hql="from ConsumablesApp t where 1=1";
		return hql;
	}
	@Override
	public List<ConsumablesApp> findConsumablesList(
			ConsumablesApp consumablesApp) {
		// TODO Auto-generated method stub
		return publicDao.find(getHql());
	}

	@Override
	public Long countConsumablesList(ConsumablesApp consumablesApp) {
		return publicDao.count("select count(*) "+getHql());
	}
	
	@Override
	public boolean addOrUpdateConsumablesEntity(ConsumablesApp consumablesApp) {
		// TODO Auto-generated method stub
		publicDao.saveOrUpdate(consumablesApp);
		return true;
	}

	@Override
	public void delConsumablesAppScrap(Integer id) {
		// TODO Auto-generated method stub
		publicDao.delete(getConsumablesAppByID(id));
	}

	@Override
	public ConsumablesApp getConsumablesAppByID(Integer id) {
		// TODO Auto-generated method stub
		return publicDao.get(ConsumablesApp.class, id);
	}

	@Override
	public boolean consumablesAppStartProcessInstance(ConsumablesApp consumablesApp) {
		// TODO Auto-generated method stub
		// 获取启动实例的key
				String proDefKey = "";
				Organization org = orgService.findOrganizationByOrganizationId(consumablesApp.getAppDept());
				//0为总部 1为分部
				if(null != org.getDeptLevel() && "0".equals(org.getDeptLevel())){
					proDefKey = Constants.ConsumablesApp_HO;
				}else{
					proDefKey = Constants.ConsumablesApp_BO;
				}
				// 定义businessKey
				String businessKey =  proDefKey+"."+consumablesApp.getCaId();
				// 指定流程变量
				Map<String, Object> variables = new HashMap<String, Object>();
				String userId = String.valueOf(Constants.getCurrendUser().getUserId());
				
				variables.put(Constants.CURRENT_USER_KEY, userId);
				// 启动流程实例
				ProcessInstance pi = this.runtimeService.startProcessInstanceByKey(proDefKey, businessKey, variables);
				return pi != null ? true : false;
	}

	@Override
	public void updProcessStatus(Integer id, String status) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updStatus(Integer id, String status) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<ConsumablesApp> findConsumablesListTask(
			ConsumablesApp consumablesApp) {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public List<ConsumablesAppAttach> findConsumablesAppAttachList(
			PageUtil pageUtil, ConsumablesApp consumablesApp) {
		// TODO Auto-generated method stub
		String hql = getFindHQL(consumablesApp);
		List<ConsumablesApp> list = publicDao.find(hql, pageUtil);
		List<ConsumablesAppAttach> attachList = new ArrayList<ConsumablesAppAttach>();
		for (ConsumablesApp consumables : list) {
			Organization org = orgService.findOrganizationByOrganizationId(consumables.getAppDept());
			//判断为总部还是分部
			if(null != org.getDeptLevel() && org.getDeptLevel().equals("0")){
				consumables.setResourcesName(Constants.ConsumablesApp_HO_RES);
				consumables.setTaskID(workFlowTaskService.getTaskByBusinessKey(consumables.getCaId().toString(), Constants.ConsumablesApp_HO)); //将taskID设置到实体对象中
			}else{
				consumables.setResourcesName(Constants.ConsumablesApp_BO_RES);
				consumables.setTaskID(workFlowTaskService.getTaskByBusinessKey(consumables.getCaId().toString(), Constants.ConsumablesApp_BO)); //将taskID设置到实体对象中
			}
			
			consumables.setAppDeptName(orgService.getOrgNameByID(consumables.getAppDept()));
			consumables.setAccount(userService.getUserByID(consumables.getApplicantNo()).getName());//翻译用户名
			
			List<ConsumablesAppAttach> tmpList = consumablesAppAttachService.findConsumablesAppAttachList(consumables.getAppNo());//设置查询条件 查询固定资产列表信息
			//填充List对象
			if(tmpList.size()>0){
				for(ConsumablesAppAttach consumablesAppAttach : tmpList){
					consumablesAppAttach.setConsumablesApp(consumables);
				}
				attachList.addAll(tmpList);
			}else{
				ConsumablesAppAttach consumablesAppAttachBak = new ConsumablesAppAttach();
				consumablesAppAttachBak.setAppNo(consumables.getAppNo());
				consumablesAppAttachBak.setConsumablesApp(consumables);;
				attachList.add(consumablesAppAttachBak);
			}
		}
		return attachList;
	}
	
	//获取固定资产报废HQL通用语句方法
		private String getFindHQL(ConsumablesApp consumablesApp){
			String hql = "from ConsumablesApp where 1=1 and applicantNo = "+Constants.getCurrendUser().getUserId();
			
			if(StringUtils.isNotBlank(consumablesApp.getAppDateBefore())){
				hql += " and appDate >='" + consumablesApp.getAppDateBefore()+"'";  //申请开始日期
			}
			if(StringUtils.isNotBlank(consumablesApp.getAppDateAfter())){
				hql += " and appDate <='" + consumablesApp.getAppDateAfter()+"'";  //申请结束日期
			}
			if(null != consumablesApp.getAppDept()){
				hql += " and appDept ='"+consumablesApp.getAppDept()+"'";
			}
			if(StringUtils.isNotBlank(consumablesApp.getProcStatus())){
				hql+=" and procStatus='"+consumablesApp.getProcStatus()+"'";
			}
			hql += " order by appDate desc";
			return hql;
		}
	@Override
	public Long findConsumablesAppCount(ConsumablesApp consumablesApp) {
		String hql = "select count(*) "+ getFindHQL(consumablesApp);
		return publicDao.count(hql);
	}
	@Override
	public void delConsumablesApp(String appNo) {
		// TODO Auto-generated method stub
		publicDao.executeHql("delete from ConsumablesApp where appNo = '" + appNo+"'");
		publicDao.executeHql("delete from ConsumablesAppAttach where appNo = '" + appNo+"'");
	}
	@Override
	public void updateConsumablesAppProceStatus(Integer id, String state) {
		// TODO Auto-generated method stub
		ConsumablesApp consumablesApp = publicDao.get(ConsumablesApp.class, id);
		consumablesApp.setProcStatus(state);
		publicDao.saveOrUpdate(consumablesApp);
	}
	@Override
	public void updateConsumablesAppStatus(Integer caId, String state) {
		// TODO Auto-generated method stub
		ConsumablesApp consumablesApp = publicDao.get(ConsumablesApp.class, caId);
		String proDefKey="";
		boolean bl = findZongBuOrFenBu(consumablesApp);
		if (bl) {
			proDefKey=Constants.ConsumablesApp_HO;
		} else {
			proDefKey=Constants.ConsumablesApp_BO;
		}
		consumablesApp.setAppStatus(proDefKey+"_"+state);
		publicDao.saveOrUpdate(consumablesApp);
	}
	@Override
	public boolean findZongBuOrFenBu(ConsumablesApp consumablesApp) {
		ConsumablesApp consumablesApp2 = publicDao.get(ConsumablesApp.class,consumablesApp.getCaId());
		if (consumablesApp2!=null) {
			Integer appDeptId = consumablesApp2.getAppDept();
			Organization organization = orgService.findOrganizationByOrganizationId(appDeptId);
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
	public void getDiagramResourceByPaId(
			HttpServletResponse httpServletResponse, Integer caId) {
		// TODO Auto-generated method stub

		// 图片的文件的流
				InputStream in = null;
				try {
					String proDefKey = "";
					String imgName = "";
					ConsumablesApp consumablesApp = new ConsumablesApp();
					consumablesApp.setCaId(caId);
					boolean bl = findZongBuOrFenBu(consumablesApp);
					if(bl){
						proDefKey = Constants.ConsumablesApp_HO;//总部
						imgName = "OA_AD_ConsumablesApp_HO";
					}else{
						proDefKey = Constants.ConsumablesApp_BO;//分部
						imgName = "OA_AD_ConsumablesApp_BO";
					}
					String businessKey = proDefKey + "." + caId;
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
	public List<ConsumablesAppAttach> findConsumablesListTask(
			PageUtil pageUtil, ConsumablesApp consumablesApp) {
		// TODO Auto-generated method stub
		List<WorkFlowTasksModel> workList = workFlowTaskService.findAcceptTaskByGroup(new WorkFlowTasksModel());
		if(null == workList || workList.size() == 0)return new ArrayList<ConsumablesAppAttach>();
		String hql ="from ConsumablesApp where caId in ("+getTaskPPEids(workList)+") ";
		if(StringUtils.isNotBlank(consumablesApp.getAppDateBefore())){
			hql += " and appDate >='" + consumablesApp.getAppDateBefore()+"'";  //申请开始日期
		}
		if(StringUtils.isNotBlank(consumablesApp.getAppDateAfter())){
			hql += " and appDate <='" + consumablesApp.getAppDateAfter()+"'";  //申请结束日期
		}
		if(StringUtils.isNotBlank(consumablesApp.getProcStatus())){
			hql+=" and procStatus='"+consumablesApp.getProcStatus()+"'";
		}
		hql += " order by appDate desc";
		List<ConsumablesApp> consumablesAppList = publicDao.find(hql, pageUtil);
		List<ConsumablesAppAttach> attachList = new ArrayList<ConsumablesAppAttach>();
		//将工作流信息字段更新到PPEScrapApp对象中
		for (WorkFlowTasksModel wl : workList) {
			for (ConsumablesApp consumables : consumablesAppList) {
				if(wl.getProcessKey().equals(consumables.getAppStatus().split("_")[0]) && Integer.valueOf(wl.getBusinessKey()) == consumables.getCaId()){
					consumables.setTaskModel(wl);
					consumables.setTaskID(wl.getTaskID());
					consumables.setAssistant(wl.getAssistant());
					consumables.setFormKey(wl.getFormKey());
					consumables.setAppDeptName(orgService.getOrgNameByID(consumables.getAppDept()));
					break;
				}
			}
		}
		//循环处理固定资产实体与附加表实体对象 多对一关系
		for (ConsumablesApp consumables : consumablesAppList) {
			consumables.setAppDeptName(orgService.getOrgNameByID(consumables.getAppDept()));//翻译部门名称
			consumables.setAccount(userService.getUserByID(consumables.getApplicantNo()).getName());//翻译用户名
			List<ConsumablesAppAttach> tmpList = consumablesAppAttachService.findConsumablesAppAttachList(consumables.getAppNo());//设置查询条件 查询固定资产列表信息
			//填充List对象 如果有附加表信息 则将信息查询出后 循环将固定资产信息set到对应的属性中
			if(tmpList.size()>0){
				for(ConsumablesAppAttach appAttach : tmpList){
					appAttach.setConsumablesApp(consumables);
				}
				attachList.addAll(tmpList);
			}else{
				//如果为增加附加表信息 则将固定资产附加表重新创建后赋值到对象中
				ConsumablesAppAttach appAttachBak = new ConsumablesAppAttach();
				appAttachBak.setAppNo(consumables.getAppNo());
				appAttachBak.setConsumablesApp(consumables);
				attachList.add(appAttachBak);
			}
		}
		return attachList;
	}
	
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
	public Long findConsumablesListTaskCount(ConsumablesApp consumablesApp) {
		// TODO Auto-generated method stub
		List<WorkFlowTasksModel> workList = workFlowTaskService.findAcceptTaskByGroup(new WorkFlowTasksModel());
		if(null == workList || workList.size() == 0)return (long) 0;
		Long counts = publicDao.count("select count(*) from ConsumablesApp where caId in ("+getTaskPPEids(workList)+")");
		return counts;
	}
	@Override
	public void saveSubmitTask(ConsumablesApp consumablesApp) {
		// TODO Auto-generated method stub
		Task task = this.taskService.createTaskQuery().taskId(consumablesApp.getTaskID()).singleResult();// 获取当前执行任务
		ConsumablesApp consumablesApp2 = getConsumablesAppByID(consumablesApp.getCaId());
		Organization org = orgService.findOrganizationByOrganizationId(consumablesApp2.getAppDept());
		StatusNameRef statusNameRef = new StatusNameRef();
		//判断总部或分部
		if(null != org.getDeptLevel() && "0".equals(org.getDeptLevel())){
			statusNameRef = statusNameService.getstatusNameRefByStatusCode(Constants.ConsumablesApp_HO+"_"+consumablesApp.getResult());
		}else{
			statusNameRef = statusNameService.getstatusNameRefByStatusCode(Constants.ConsumablesApp_BO+"_"+consumablesApp.getResult());
		}
		if(StringUtils.isNotBlank(consumablesApp.getTaskID())){
			String appStatusCode = consumablesApp.getResult();
			updateConsumablesAppStatus(consumablesApp.getCaId(),appStatusCode);//更新流程状态
			Map<String, Object> variables = new HashMap<String, Object>();
			if(StringUtils.isNotBlank(appStatusCode)){
				variables.put("result", appStatusCode);// 设置流程变量
			}
			taskService.complete(consumablesApp.getTaskID(), variables);//向task发送消息 跳转下一流程
			/***保存审批流程履历表 start****/
			AuditProcHis auditProcHis = new AuditProcHis();
			auditProcHis.setAppNo(consumablesApp.getAppNo());
			auditProcHis.setAppStatus(statusNameRef.getRefId());
			auditProcHis.setHandleDate(new Date());
			auditProcHis.setHandler(Constants.getCurrendUser().getUserId());
			auditProcHis.setHandleResult(consumablesApp.getIsSuccess());
			auditProcHis.setHandlerRole(Constants.getTaskRoleCodeMap().get(task.getTaskDefinitionKey()));
			auditProcHis.setRemark(consumablesApp.getTaskModel().getRemark());
			auditProcHisService.saveAuditProcHis(auditProcHis);
			/***保存审批流程履历表 end ****/
		}
		
	}
	@Override
	public StatusNameRef findAppStatusByID(String appStatus) {
		// TODO Auto-generated method stub
		return statusNameService.getStatusNameRefByStatusID(appStatus);
	}
	@Override
	public ConsumablesApp getConsumablesAppByAppNo(String appNo) {
		// TODO Auto-generated method stub
		String hql="from ConsumablesApp where appNo='"+appNo+"'";
		return publicDao.find(hql).get(0);
	}
}

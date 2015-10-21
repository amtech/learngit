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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.oasys.dao.PublicDao;
import com.oasys.model.AuditProcHis;
import com.oasys.model.CardApp;
import com.oasys.model.PurchaseApp;
import com.oasys.model.Role;
import com.oasys.model.StatusNameRef;
import com.oasys.service.AuditProcHisService;
import com.oasys.service.CardApplyService;
import com.oasys.service.StatusNameRefService;
import com.oasys.service.WorkFlowTaskService;
import com.oasys.util.Collections;
import com.oasys.util.Constants;
import com.oasys.util.DateUtils;
import com.oasys.util.PageUtil;
import com.oasys.viewModel.CardAppVO;
import com.oasys.viewModel.PurchaseAppModel;
import com.oasys.viewModel.WorkFlowTasksModel;
/**
 * 
 * @author Guo
 *
 */
@Service("cardApplyService")
public class CardApplyServiceImpl extends WorkFlowBaseServiceImpl implements CardApplyService {
	@Autowired
	private PublicDao<CardApp> publicDao;
	@Autowired
	private PublicDao<CardAppVO> publicDaoVO;
	@Autowired
	private WorkFlowTaskService workFlowTaskService;
	@Autowired
	private StatusNameRefService statusNameRefService;
	@Autowired
	private AuditProcHisService auditProcHisService;
	
	/**
	 * 展示数据
	 */
	@Override
	public List<CardAppVO> getList(Map<String, Object> map,PageUtil pageUtil) {
		StringBuffer stringBuffer = new StringBuffer("SELECT u.name as '姓名',o.FULL_NAME as '部门名称',r.NAME as '职位',c.personal_tel as '个人电话',c.office_tel as '办公电话', ");
		stringBuffer.append(" c.email as '邮箱',c.BRANCH_ADDR as '分公司地址',c.APP_QTY as '申请数量',c.COM_URL as '公司网址',card.REMARK as '备注',card.APP_NO as '申请编号',card.PROC_STATUS AS '流程状态',card.REG_DATETIME AS '登记日期',card.APP_DATE AS '申请日期',card.APP_QTY AS '总数',card.CA_ID '编号',card.REGISTRANT_NO '登记人编号'");
		stringBuffer.append(" FROM t_oa_ad_card_app card LEFT JOIN t_oa_ad_card_app_attach c ON card.APP_NO=c.APP_NO");
		stringBuffer.append(" LEFT JOIN qqms.t_users u ON u.USER_ID = c.APPLICANT_NO ");
		stringBuffer.append(" LEFT JOIN qqms.t_organization o ON o.ORGANIZATION_ID = c.DEPT_NO");
		stringBuffer.append(" LEFT JOIN qqms.t_role r ON r.ROLE_ID=POSITION");
		stringBuffer.append(" WHERE o.STATUS = 'A'");
		if(StringUtils.hasText(map.get("status")+"") && map.get("status")!=null){
			stringBuffer.append(" and card.PROC_STATUS="+map.get("status"));
		}
		if(StringUtils.hasText(map.get("dateBegin")+"") && map.get("dateBegin")!=null){
			stringBuffer.append(" and card.REG_DATETIME>='"+map.get("dateBegin")+"'");
		}
		if(StringUtils.hasText(map.get("dateEnd")+"") && map.get("dateEnd")!=null){
			stringBuffer.append(" and card.REG_DATETIME<='"+map.get("dateEnd")+"'");
		}
		if(StringUtils.hasText(map.get("daetApplyBegin")+"") && map.get("daetApplyBegin")!=null){
			stringBuffer.append(" and card.APP_DATE>='"+map.get("daetApplyBegin")+"'");
		}
		if(StringUtils.hasText(map.get("daetApplyEnd")+"") && map.get("daetApplyEnd")!=null){
			stringBuffer.append(" and card.APP_DATE<='"+map.get("daetApplyEnd")+"'");
		}
		List<Object> list=publicDaoVO.findBySql(stringBuffer.toString(),pageUtil);
		List<CardAppVO> list2 = new ArrayList<CardAppVO>();
		for (int i = 0; i < list.size(); i++) {
			Object[] item=(Object[]) list.get(i);
			CardAppVO cardAppVO = new CardAppVO();
			cardAppVO.setUserName(item[0]+"");
			cardAppVO.setDeptName(item[1]==null?"":item[1]+"");
			cardAppVO.setPositionName(item[2]==null?"":item[2]+"");
			cardAppVO.setPersonalTel(item[3]==null?"":item[3]+"");
			cardAppVO.setOfficeTel(item[4]+"");
			cardAppVO.setEmail(item[5]+"");
			cardAppVO.setBranchAddr(item[6]+"");
			cardAppVO.setAppQty(Integer.parseInt(item[7]+""));
			cardAppVO.setComUrl(item[8]+"");
			cardAppVO.setRemark(item[9]+"");
			cardAppVO.setAppNO(item[10]+"");
			cardAppVO.setProcStatusInfo(Integer.parseInt(item[11]+"")==1?"初始状态":Integer.parseInt(item[11]+"")==2?"审批中":Integer.parseInt(item[11]+"")==3?"已完成":Integer.parseInt(item[11]+"")==4?"已失效":"已撤销");
			cardAppVO.setRegDeteTime(DateUtils.parse(item[12]==null?"0000-00-00 00:00:00":item[12]+""));
			cardAppVO.setAppDate(DateUtils.parse(item[13]==null?"0000-00-00 00:00:00":item[13]+""));
			cardAppVO.setSumAppQty(Long.parseLong(item[14]+""));
			cardAppVO.setCaID(Integer.parseInt(item[15]+""));
			cardAppVO.setRegistrantNO(Integer.parseInt(item[16]+""));
			list2.add(cardAppVO);
		}
		return list2;
	}
	

	/**
	 * 删除数据
	 */
	@Override
	public boolean delCard(Integer id) {
		int count=publicDao.executeHql("delete CardApp where caID="+id+"");
		if(count>=1){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * 添加申请
	 */
	@Override
	public boolean addCard(CardApp cardApp) {
		boolean flag=false;
		try {
			publicDao.saveOrUpdate(cardApp);
			flag=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 开启流程
	 */
	@Override
	public boolean submitCardApply(Integer id) {
		String proDefKey=null;
		if(getStatus(id)){//总部流程
			// 获取启动实例的key
			proDefKey = Constants.CALLINGCARD_HO;
		}else {
			proDefKey = Constants.CALLINGCARD_BO;
		}
		// 定义businessKey
		String businessKey = proDefKey + "." + id;
		System.out.println(businessKey+"-------");
		// 指定流程变量
		Map<String, Object> variables = new HashMap<String, Object>();
		String userId = String.valueOf(Constants.getCurrendUser().getUserId());
		variables.put(Constants.CURRENT_USER_KEY, userId);
		// 启动流程实例
		ProcessInstance pi = this.runtimeService.startProcessInstanceByKey(proDefKey, businessKey, variables);
		return pi != null ? true : false;
	}

	/**
	 * 获取总部分部
	 * @Title: getStatus 
	 * @Description: 获取总部分部
	 * @param @param id
	 * @param @return
	 * @author Guo
	 * @return boolean
	 * @date 2015年10月13日 上午10:49:45
	 * @throws
	 */
	private boolean getStatus(Integer id){
		int status=Integer.parseInt(publicDao.findBySQL("SELECT DEPT_LEVEL FROM qqms.t_organization WHERE ORGANIZATION_ID = (SELECT ORGANIZATION_ID FROM qqms.t_users WHERE USER_ID = (SELECT REGISTRANT_NO FROM t_oa_ad_card_app WHERE CA_ID = "+id+"))").get(0)+"");
		if(status==0){
			return true;//总部
		}else{
			return false;//分部
		}
	}

	/**
	 * 更新流程状态
	 */
	@Override
	public void updateStatus(Integer id, String status) {
		publicDao.executeHql("update CardApp set procStatus="+status+" where caID="+id+"");
	}

	/**
	 * 更新申请状态
	 */
	@Override
	public void updateApplyStatus(Integer id, String status) {
//		taskService.setVariable("1005", "RESULT", "rea");
		System.out.println("------"+CardApp.class.getSimpleName());
		publicDao.executeHql("update CardApp set appStatus='"+CardApp.class.getSimpleName()+"_"+status+"' where caID="+id+"");
	}
	
	/**
	 * 获取待办任务
	 */
	@Override
	public List<CardAppVO> getTaskByGroup(int firstResult,int maxResults) {
		//登录角色所要办理的的任务集合
				List<WorkFlowTasksModel> taskModelList = workFlowTaskService.findAcceptTaskByGroup(new WorkFlowTasksModel());
				List<CardAppVO> purchaseAppModelList = new ArrayList<CardAppVO>();//返回的结果集
				String ids = "";
				if (Collections.listIsNotEmpty(taskModelList)) {
					for (WorkFlowTasksModel workFlowTasksModel : taskModelList) {
						ids += workFlowTasksModel.getBusinessKey()+",";
					}
					//id字符串
					ids = ids.substring(0, ids.length()-1);
//					//根据id字符串查出的集合
					List<CardAppVO> pamList = getList(new HashMap<String, Object>(), new PageUtil(firstResult, maxResults));
					for (WorkFlowTasksModel wl : taskModelList) {
						for (CardAppVO purchaseAppModel : pamList) {
							if(Integer.valueOf(wl.getBusinessKey()) == purchaseAppModel.getCaID()){
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
	/**
	 * 获取待办任务信息
	 * @Title: findPurchaseAppByTask 
	 * @Description: 获取待办任务信息
	 * @param @param task
	 * @param @return
	 * @author Guo
	 * @return CardAppVO
	 * @date 2015年10月13日 上午10:52:01
	 * @throws
	 */
	public CardAppVO findPurchaseAppByTask(Task task){
		//根据task,获取流程实例
		ProcessInstance processInstance = this.runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
		String businessKey = processInstance.getBusinessKey();
		if (org.apache.commons.lang3.StringUtils.isNotBlank(businessKey)) {
			String paId = businessKey.split("\\.")[1];
			System.err.println(paId+"----------------------------");
			StringBuffer stringBuffer = new StringBuffer("SELECT u.name as '姓名',o.FULL_NAME as '部门名称',r.NAME as '职位',c.personal_tel as '个人电话',c.office_tel as '办公电话', ");
			stringBuffer.append(" c.email as '邮箱',c.BRANCH_ADDR as '分公司地址',c.APP_QTY as '申请数量',c.COM_URL as '公司网址',card.REMARK as '备注',card.APP_NO as '申请编号',card.PROC_STATUS AS '流程状态',card.REG_DATETIME AS '登记日期',card.APP_DATE AS '申请日期',card.APP_QTY AS '总数',card.CA_ID '编号'");
			stringBuffer.append(" FROM t_oa_ad_card_app card LEFT JOIN t_oa_ad_card_app_attach c ON card.APP_NO=c.APP_NO");
			stringBuffer.append(" LEFT JOIN qqms.t_users u ON u.USER_ID = c.APPLICANT_NO ");
			stringBuffer.append(" LEFT JOIN qqms.t_organization o ON o.ORGANIZATION_ID = c.DEPT_NO");
			stringBuffer.append(" LEFT JOIN qqms.t_role r ON r.ROLE_ID=POSITION");
			stringBuffer.append(" WHERE o.STATUS = 'A'");
			List list = publicDao.findBySQL(stringBuffer.toString());
			CardAppVO model = new CardAppVO();
			if (Collections.listIsNotEmpty(list)) {
				Object[] obj = (Object[])list.get(0);
				model.setAppNO(obj[1]+"");
				model.setAppStatus(obj[2]+"");
				model.setAppQty(Integer.parseInt(obj[3]+""));
				model.setTaskId(task.getId());
				return model;
			}
		}
		return null;
	}

	/**
	 * 签收任务
	 */
	@Override
	public boolean signTask(String taskId) {
		boolean flag = false;
		try {
			taskService.claim(taskId, Constants.getCurrendUser().getUserId()+"");
			flag=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 办理任务
	 */
	@Override
	public boolean handleTask(String taskId,String result,CardAppVO cardAppVO, AuditProcHis auditProcHis,String paId) {
		boolean flag=false;
		try {
			Task task = this.taskService.createTaskQuery().taskId(taskId).singleResult();// 获取当前执行任务
			Integer userId = Constants.getCurrendUser().getUserId();//获取登录人的id
			updateApplyStatus(Integer.parseInt(paId),result);// 修改订单的状态
			/***保存审批流程履历表 start****/
			String roleCode = (String) taskService.getVariableLocal(task.getId(), "role");
			StatusNameRef statusNameRef=null;
			if(getStatus(Integer.parseInt(paId))){//总部流程
				statusNameRef = statusNameRefService.getstatusNameRefByStatusCode(Constants.CALLINGCARD_HO+"_"+result);
			}else {
				statusNameRef = statusNameRefService.getstatusNameRefByStatusCode(Constants.CALLINGCARD_BO+"_"+result);
			}
			auditProcHis.setAppStatus(statusNameRef.getRefId());
			auditProcHis.setHandleDate(new Date());
			auditProcHis.setHandler(userId);
			auditProcHis.setHandlerRole(roleCode);
			auditProcHisService.saveAuditProcHis(auditProcHis);
			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put("result", result);
			taskService.complete(task.getId(), variables);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 获取一条申请的总数
	 */
	@Override
	public Long getCount(Map<String, Object> map) {
		StringBuffer stringBuffer = new StringBuffer("SELECT COUNT(1) ");
		stringBuffer.append(" FROM t_oa_ad_card_app card LEFT JOIN t_oa_ad_card_app_attach c ON card.APP_NO=c.APP_NO");
		stringBuffer.append(" LEFT JOIN qqms.t_users u ON u.USER_ID = c.APPLICANT_NO ");
		stringBuffer.append(" LEFT JOIN qqms.t_organization o ON o.ORGANIZATION_ID = c.DEPT_NO");
		stringBuffer.append(" LEFT JOIN qqms.t_role r ON r.ROLE_ID=POSITION");
		stringBuffer.append(" WHERE o.STATUS = 'A'");
		if(StringUtils.hasText(map.get("status")+"") && map.get("status")!=null){
			stringBuffer.append(" and card.PROC_STATUS="+map.get("status"));
		}
		if(StringUtils.hasText(map.get("dateBegin")+"") && map.get("dateBegin")!=null){
			stringBuffer.append(" and card.REG_DATETIME>='"+map.get("dateBegin")+"'");
		}
		if(StringUtils.hasText(map.get("dateEnd")+"") && map.get("dateEnd")!=null){
			stringBuffer.append(" and card.REG_DATETIME<='"+map.get("dateEnd")+"'");
		}
		if(StringUtils.hasText(map.get("daetApplyBegin")+"") && map.get("daetApplyBegin")!=null){
			stringBuffer.append(" and card.APP_DATE>='"+map.get("daetApplyBegin")+"'");
		}
		if(StringUtils.hasText(map.get("daetApplyEnd")+"") && map.get("daetApplyEnd")!=null){
			stringBuffer.append(" and card.APP_DATE<='"+map.get("daetApplyEnd")+"'");
		}
		return publicDao.findTotalCount(stringBuffer.toString());
	}

	/**
	 * 根据部门名称获取Id
	 */
	@Override
	public Map<String, Object> getDeptNo(String name) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("deptNo",publicDao.findBySQL("SELECT ORGANIZATION_ID FROM qqms.t_organization where full_name = '"+name+"'").get(0));
		return map;
	}

	/**
	 * 获取办理任务
	 */
	@Override
	public List<CardAppVO> getAcceptTask() {
		// 获取用户的id
		Integer userId = Constants.getCurrendUser().getUserId();
		// 查到该用户的所有角色list
		List<Role> roleList = userService.findRoleListByUserId(userId);
		// 角色roleCode集合
		List<String> roleIdList = new ArrayList<String>();
		if (Collections.listIsNotEmpty(roleList)) {
			for (int i = 0; i < roleList.size(); i++) {
				Role role = roleList.get(i);
				roleIdList.add(String.valueOf(role.getRoleCode()));
			}
		}
		// 流程key
		String proDefKey = Constants.CALLINGCARD_HO;
		// 查询任务List集合
		List<Task> tasks =taskService.createTaskQuery().taskAssignee(userId+"").list();
		// 返回的结果集
		List<CardAppVO> purchaseAppList = new ArrayList<CardAppVO>();
		if (Collections.listIsNotEmpty(tasks)) {
			for (int i = 0; i < tasks.size(); i++) {
				Task task = tasks.get(i);
				CardAppVO CardAppTask = findPurchaseAppByTask(task);
				if (CardAppTask != null) {
					CardAppVO CardApp = (CardAppVO) CardAppTask.clone();
					CardApp.setTaskId(task.getId());
					purchaseAppList.add(CardApp);
				}
			}
		}
		return purchaseAppList;
	}

	/**
	 * 删除未保存的名片附件
	 */
	@Override
	public boolean removeCardAccessory() {
		boolean flag=false;
		publicDao.executeHql("delete CardAppAttach c where c.appNo is null");
		flag=true;
		return flag;
	}


	@Override
	public void getDiagramResourceByCaId(HttpServletResponse response,Integer caId) {
		// 图片的文件的流
		InputStream in = null;
		try {
			String proDefKey = "";
			String imgName = "";
			if(getStatus(caId)){//总部流程
				proDefKey = Constants.CALLINGCARD_HO;//总部
				imgName = "OA_AD_CallingCard_HO";
			}else{
				proDefKey = Constants.CALLINGCARD_BO;//分部
				imgName = "OA_AD_callingCard_BO";
			}
			String businessKey = proDefKey + "." + caId;
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
						e.printStackTrace();
					}
			}	
	}
}

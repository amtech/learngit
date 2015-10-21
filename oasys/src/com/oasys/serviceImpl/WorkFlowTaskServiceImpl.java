package com.oasys.serviceImpl;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.activiti.engine.ActivitiTaskAlreadyClaimedException;
import org.activiti.engine.impl.persistence.entity.IdentityLinkEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.NativeTaskQuery;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oasys.model.Organization;
import com.oasys.model.UserRole;
import com.oasys.model.Users;
import com.oasys.service.OrganizationService;
import com.oasys.service.WorkFlowTaskService;
import com.oasys.util.Constants;
import com.oasys.viewModel.ProcessNameModel;
import com.oasys.viewModel.WorkFlowTasksModel;


/**
 * @Title WorkFlowTaskServiceImpl
 * @Package com.oasys.serviceImpl;
 * @author lida
 * @Date 2015/9/23
 * @remark 任务管理Service实现类
 * 
 * */
@Service("workFlowTaskService")
public class WorkFlowTaskServiceImpl extends WorkFlowBaseServiceImpl implements WorkFlowTaskService {

	@Autowired
	private OrganizationService orgService;
	
	//构建任务查询Query对象
	@Override
	public List<WorkFlowTasksModel> findAcceptTaskByGroup(
			WorkFlowTasksModel taskModelQuery) {
		return getTaskModelList(getNativeQuery(taskModelQuery));
	}
	
	
	//获取NativeQuery对象方法
	private NativeTaskQuery getNativeQuery(WorkFlowTasksModel taskModelQuery){
		String sql = "SELECT DISTINCT TASK.* FROM (SELECT T.* FROM "+managementService.getTableName(TaskEntity.class)+
				" T,"+managementService.getTableName(IdentityLinkEntity.class)+" I WHERE T.ID_ = I.TASK_ID_ "
				+ "AND (I.GROUP_ID_ in (#{groupID}) OR T.ASSIGNEE_=#{assignee})"
				+"UNION ALL SELECT T.* FROM "+managementService.getTableName(TaskEntity.class)+" T WHERE T.ASSIGNEE_=#{assignee}) TASK";
		if(StringUtils.isNotBlank(taskModelQuery.getProcessKey())){
			sql += " WHERE TASK.PROC_DEF_ID_ LIKE '"+taskModelQuery.getProcessKey()+":%'";
		}
		sql +=" ORDER BY TASK.CREATE_TIME_ ";
		NativeTaskQuery nativeQuery = taskService.createNativeTaskQuery().sql(sql)
						.parameter("assignee", Constants.getCurrendUser().getUserId().toString())
						.parameter("groupID", getRoleIdList());
		return nativeQuery;
	}
	
	//签收任务
	@Override
	public void signForTask(String taskID) throws ActivitiTaskAlreadyClaimedException{
			taskService.claim(taskID, Constants.getCurrendUser().getUserId().toString());
	}
	
	
	//待办任务类型下拉框数据查询
	@Override
	public List<ProcessNameModel> processNameList(){
		//获取taskList
		List<Task> taskList = getTaskList(getNativeQuery(new WorkFlowTasksModel()));
		List<ProcessNameModel> procNameList = new ArrayList<ProcessNameModel>();
		ProcessNameModel procNameModel = new ProcessNameModel();
		String keyStr = "";
		//对processNameList进行排重
		for (Task task : taskList) {
			ProcessInstance pi = runtimeService.createProcessInstanceQuery()
					.processInstanceId(task.getProcessInstanceId()).singleResult();
			procNameModel = new ProcessNameModel();
			procNameModel.setProcessKey(pi.getProcessDefinitionKey());
			procNameModel.setProcessName(pi.getProcessDefinitionName());
			procNameModel.setListURL(Constants.getTaskLocationMap().get(procNameModel.getProcessKey()));
			if(keyStr.indexOf(pi.getProcessDefinitionKey()) == -1){
				procNameList.add(procNameModel);
			}
			keyStr += pi.getProcessDefinitionKey() + ",";
		}
        return procNameList;
	}

	private String getRoleIdList(){
		//获取当前登陆用户
		Users users = userService.getUserByID(Constants.getCurrendUser().getUserId());
		String roleStr = "";
		//重新填充角色List
		Set<UserRole> userRoleSet = users.getUserRoles();
		for (UserRole userRole : userRoleSet) {
			roleStr += userRole.getRole().getRoleCode()+",";
		}
		if(roleStr.length() > 0){
			roleStr = roleStr.substring(0, roleStr.length()-1);
		}
		return roleStr;
	}
	
	//获取流程任务方法
	private List<Task> getTaskList(NativeTaskQuery nq){
		List<Task> taskList = nq.list();
		return taskList;
	}
	
	//获取流程任务实例List方法
	private List<WorkFlowTasksModel> getTaskModelList(NativeTaskQuery nq){
		List<Task> taskList = getTaskList(nq);
		List<WorkFlowTasksModel> taskModelList = new ArrayList<WorkFlowTasksModel>();
		WorkFlowTasksModel taskModel;
		Integer userID = null;
		for (Task task : taskList) {
			try {
				taskModel = new WorkFlowTasksModel();
				taskModel.setTaskID(task.getId());
				ProcessInstance pi = runtimeService.createProcessInstanceQuery()
						.processInstanceId(task.getProcessInstanceId()).singleResult();
				taskModel.setProcessKey(pi.getProcessDefinitionKey());//获取ProcessDefinitionKey用于区分流程    (yuanzhongqiu)
				taskModel.setProcessName(pi.getProcessDefinitionName());//流程名称
				taskModel.setCreateDate(task.getCreateTime());//任务提交时间
				taskModel.setBusinessKey(pi.getBusinessKey().split("\\.")[1]);//对应的业务表ID
				taskModel.setAssistant(task.getAssignee());
				Execution ex = this.runtimeService.createExecutionQuery().processInstanceBusinessKey(pi.getBusinessKey()).singleResult();
				Object variable = this.runtimeService.getVariable(ex.getId(),Constants.CURRENT_USER_KEY);
				userID = Integer.valueOf((String) variable);
				Users user = userService.getUserByID(userID);//获取申请任务人信息
				taskModel.setUserName(user.getName());//申请任务用户名称
				taskModel.setFormKey(task.getFormKey());//受理任务jsp地址
				taskModelList.add(taskModel);
			} catch (Exception e) {
				System.out.println(e.toString());
			}

		}
		
		return taskModelList;
	}

	//根据业务ID返回taskID方法
	@Override
	public String getTaskByBusinessKey(String businessKey, String definitionKey) {
		List<Task> taskList = taskService.createTaskQuery().list();
		ProcessInstance pi = null;
		String busKeyID = "";
		String busKey = "";
		for (Task task : taskList) {
			pi = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
			busKey = pi.getBusinessKey().split("\\.")[0];
			busKeyID = pi.getBusinessKey().split("\\.")[1];
			if(busKeyID.equals(businessKey) && busKey.equals(definitionKey)){
				return task.getId();
			}
		}
		return "";
	}
	
	
	//根据UserID判断返回角色类型(通用处理部门负责人转角色处理方法)
	@Override
	public String getRoleCodeByUserID(String userID){
		Organization org = orgService.findOrganizationByUserId(Integer.valueOf(userID));
		String roleCode = "";
		//判断部门类型 0为总部 1为分部
		switch (org.getDeptLevel()) {
			case "0":
				roleCode = Constants.OA_ROLE_CODE_DEPARTMENTHEADS;
				break;
			case "1":
				//判断如果为财富端 调用营业部经理角色
				if(Constants.ORG_MYID_CF.equals(org.getMyid())){
					roleCode =  Constants.AD_ROLE_CODE_BUSINESSMGR;
				//判断如果为借款端 调用城市经理角色	
				}else if(Constants.ORG_MYID_JK.equals(org.getMyid())){
					roleCode =  Constants.AD_ROLE_CODE_CITYMANAGER;
				}else{
					roleCode =  Constants.OA_ROLE_CODE_DEPARTMENTHEADS;
				}
				break;
			default:
				break;
		}
		return roleCode;
	}
	
}

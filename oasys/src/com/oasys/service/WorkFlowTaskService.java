package com.oasys.service;

import java.util.List;

import com.oasys.viewModel.ProcessNameModel;
import com.oasys.viewModel.WorkFlowTasksModel;
/**
 * @Title WorkFlowTaskService
 * @Package com.oasys.service 
 * @author lida
 * @Date 2015/9/23
 * @remark 任务管理Service
 * 
 * */
public interface WorkFlowTaskService {
	
	/**
	 * 查询任务列表
	 * */
	public List<WorkFlowTasksModel> findAcceptTaskByGroup(WorkFlowTasksModel taskModel);
	
	/**
	 * 根据TaskID受理申请任务 指派当前登陆用户(签收任务用户)
	 * */
	public void signForTask(String taskID);
	
	/**
	 * 待办任务类型下拉框数据查询
	 * */
	public List<ProcessNameModel> processNameList();
	
	/**
	 * 根据业务ID返回taskID方法
	 * */
	public String getTaskByBusinessKey(String businessKey,String definitionKey);

	/** 根据UserID判断返回角色类型(通用处理部门负责人转角色处理方法) */
	public String getRoleCodeByUserID(String userID);
	
}

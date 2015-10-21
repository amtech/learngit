package com.oasys.viewModel;

import java.util.Date;
/**
 * 待办任务绑定前台页面实体对象类 
 * */
public class WorkFlowTasksModel {
	
	private String taskID;//任务节点ID
	private String processInstanceId;//流程实例编号
	private String userName;//申请人
	private Date createDate;//申请时间
	private String processName;//申请类型名称
	private String processKey;//流程类型状态标识
	private String businessKey;//对应业务表的APP_NO字段
	private String assistant;//指定的候选人
	private String remark;//审批意见
	private String formKey;//跳转对应的受理页面JSP配置(在流程图的formKey中设置)
	
	

	public WorkFlowTasksModel() {
		super();
	}
	public WorkFlowTasksModel(String taskID, String processInstanceId,
			String userName, Date createDate, String processName,
			String processKey, String businessKey, String assistant,
			String remark, String formKey) {
		super();
		this.taskID = taskID;
		this.processInstanceId = processInstanceId;
		this.userName = userName;
		this.createDate = createDate;
		this.processName = processName;
		this.processKey = processKey;
		this.businessKey = businessKey;
		this.assistant = assistant;
		this.remark = remark;
		this.formKey = formKey;
	}
	public String getFormKey() {
		return formKey;
	}
	public void setFormKey(String formKey) {
		this.formKey = formKey;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getBusinessKey() {
		return businessKey;
	}
	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}
	public String getProcessKey() {
		return processKey;
	}
	public void setProcessKey(String processKey) {
		this.processKey = processKey;
	}
	
	public String getTaskID() {
		return taskID;
	}
	public void setTaskID(String taskID) {
		this.taskID = taskID;
	}
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	
	public String getAssistant() {
		return assistant;
	}
	public void setAssistant(String assistant) {
		this.assistant = assistant;
	}
	
}

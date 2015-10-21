package com.oasys.listener.CallingCard;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.impl.el.FixedValue;

import com.oasys.listener.BaseTaskListener;
import com.oasys.util.Constants;

@SuppressWarnings("serial")
public class CallingCardHoApprovelListener extends BaseTaskListener {
	private FixedValue status;
	public FixedValue getStatus() {
		return status;
	}

	public void setStatus(FixedValue status) {
		this.status = status;
	}

	@Override
	public void notify(DelegateTask task) {
		Object curUserId=null;
		if("ShenQingTiaoZheng".equals(status.getValue(null))){
			curUserId = task.getVariable(Constants.CURRENT_USER_KEY);
			if(null != curUserId){
				task.setAssignee(curUserId.toString());
			}
		}
//		else{
//			task.addCandidateGroup(Constants.getTaskRoleCodeMap().get(status.getValue(null)));
//			task.setVariableLocal("role",Constants.getTaskRoleCodeMap().get(status.getValue(null)));
//		}
		//判断流程是否为部门负责人节点	
		else if(null != status.getValue(null) && "DepartmentHeads".equals(status)){
			//将流程变量中申请人ID传入workFlowTaskService中 返回对应的业务角色code
			task.addCandidateGroup(workFlowTaskService.getRoleCodeByUserID(curUserId+""));
		}else{
			task.addCandidateGroup(Constants.getTaskRoleCodeMap().get(status.getValue(null)));
			task.setVariableLocal("role",Constants.getTaskRoleCodeMap().get(status.getValue(null)));
		}
//		task.addCandidateGroup(getRoleCode());
	}

	@Override
	public String getRoleCode() {
		String code = Constants.getTaskRoleCodeMap().get(status.getValue(null));
		return code;
	}

}

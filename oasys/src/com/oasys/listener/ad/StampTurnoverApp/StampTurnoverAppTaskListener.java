package com.oasys.listener.ad.StampTurnoverApp;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

import com.oasys.listener.BaseTaskListener;
import com.oasys.util.Constants;

public class StampTurnoverAppTaskListener extends BaseTaskListener implements TaskListener{
	
	private static final long serialVersionUID = -344720324571393495L;

	@Override
	public void notify(DelegateTask task) {
		Object curUserId = task.getVariable(Constants.CURRENT_USER_KEY);
		String curUserIdStr = "";
		if(curUserId != null){
			curUserIdStr = curUserId.toString();
		}
		//申请调整时 将任务指定回提交申请人
		if(null != getRoleCode() && "ApplyForAdjustment".equals(getRoleCode())){
			if(null != curUserIdStr)
				task.setAssignee(curUserIdStr.toString());
		}else if(null != getRoleCode() && "DepartmentHeads".equals(getRoleCode())){
			//将流程变量中申请人ID传入workFlowTaskService中 返回对应的业务角色code
			task.addCandidateGroup(workFlowTaskService.getRoleCodeByUserID(curUserIdStr));
		}else{
			String roleCode = Constants.getTaskRoleCodeMap().get(getRoleCode());
			task.addCandidateGroup(roleCode);
		}
	}

	@Override
	public String getRoleCode() {
		return getRoleCodeKey().getValue(null).toString();
	}
}

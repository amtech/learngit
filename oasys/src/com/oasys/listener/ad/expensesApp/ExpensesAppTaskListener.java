package com.oasys.listener.ad.expensesApp;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.impl.el.FixedValue;

import com.oasys.listener.BaseTaskListener;
import com.oasys.util.Constants;
/**
 * userTask监听
 * @ClassName: ExpensesAppTaskListener 
 * @Description: TODO
 * @author PANCHUANHE
 * @date 2015年10月10日 下午4:54:37
 */
public class ExpensesAppTaskListener extends BaseTaskListener{

	
	private static final long serialVersionUID = 1L;
	
	private FixedValue roleCodeKey;
	
	public FixedValue getRoleCodeKey() {
		return roleCodeKey;
	}

	public void setRoleCodeKey(FixedValue roleCodeKey) {
		this.roleCodeKey = roleCodeKey;
	}

	@Override
	public void notify(DelegateTask task) {
		Object curUserId = task.getVariable(Constants.CURRENT_USER_KEY);
		if("ShenQingTiaoZheng".equals(roleCodeKey.getValue(null))){
			if(null != curUserId){
				task.setAssignee(curUserId.toString());
			}
		}else if(null != getRoleCode() && "BuMenFuZeRen".equals(getRoleCode())){
			task.addCandidateGroup(workFlowTaskService.getRoleCodeByUserID(curUserId.toString()));
			task.setVariableLocal("role",workFlowTaskService.getRoleCodeByUserID(curUserId.toString()));
		}else{
			task.addCandidateGroup(Constants.getTaskRoleCodeMap().get(roleCodeKey.getValue(null)));
			task.setVariableLocal("role",Constants.getTaskRoleCodeMap().get(roleCodeKey.getValue(null)));
		}
	}

	@Override
	public String getRoleCode() {
		return getRoleCodeKey().getValue(null).toString();
	}

}

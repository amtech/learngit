package com.oasys.listener.ad.badgeApp;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.el.FixedValue;

import com.oasys.listener.BaseTaskListener;
import com.oasys.util.Constants;


/**
 * @Creater chenfl
 * @File_Name FinancingDeptAssistantTaskListener.java
 * @Version v1.0
 * @Creation_Date 2015年5月25日 下午1:23:14
 * @Modifier
 * @Modified_Date
 * @Description 财富业务流程部门助理节点任务监听器
 */
@SuppressWarnings("serial")
public class BadgeAppAssistantTaskListener extends BaseTaskListener implements TaskListener {
	
	private static final long serialVersionUID = -344720324571393495L;
	
	private FixedValue roleCodeKey;

	public FixedValue getRoleCodeKey() {
		return roleCodeKey;
	}
	         
	public void setRoleCodeKey(FixedValue roleCodeKey) {
		this.roleCodeKey = roleCodeKey;
	}

	@Override
	public void notify(DelegateTask task) {
		//申请调整时 将任务指定回提交申请人
		if(null != roleCodeKey && "XingZhengQianTai".equals(roleCodeKey.getValue(null))){
			Object curUserId = task.getVariable(Constants.CURRENT_USER_KEY);
			if(null != curUserId)
				task.setAssignee(curUserId.toString());
		}else{
			//分配任务角色
			String roleCode = Constants.getTaskRoleCodeMap().get(roleCodeKey.getValue(null));
			task.addCandidateGroup(roleCode);
		}
		
	}

	@Override
	public String getRoleCode() {
		// TODO Auto-generated method stub
		return null;
	}

	

	
}

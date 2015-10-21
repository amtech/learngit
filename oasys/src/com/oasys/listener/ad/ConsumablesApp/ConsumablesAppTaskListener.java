package com.oasys.listener.ad.ConsumablesApp;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.impl.el.FixedValue;

import com.oasys.listener.BaseTaskListener;
import com.oasys.util.Constants;
/**
 * 
 * @Title: ConsumablesAppTaskListener.java 
 * @Package com.oasys.listener.ad.ConsumablesApp 
 * @Description: 各任务节点监听器
 * @author yuanzhongqiu  
 * @date 2015年10月12日 下午5:29:43 
 * @version V1.0
 */
public class ConsumablesAppTaskListener extends BaseTaskListener{
	
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
		Object curUserId = task.getVariable(Constants.CURRENT_USER_KEY);
		if (roleCodeKey!=null && "Adjustment".equals(getRoleCode())) {
			if(curUserId!=null){
				task.setAssignee(curUserId.toString());
			}
		} else  if(null != getRoleCode() && "DepartmentHeads".equals(getRoleCode())){
			//将流程变量中申请人ID传入workFlowTaskService中 返回对应的业务角色code
			task.addCandidateGroup(workFlowTaskService.getRoleCodeByUserID(curUserId.toString()));
		}
		else{

			String roleCode = Constants.getTaskRoleCodeMap().get(getRoleCode());
			System.out.println(roleCode);
			task.addCandidateGroup(roleCode);
			task.setVariableLocal("role",roleCode);
		}
	}

	@Override
	public String getRoleCode() {
		// TODO Auto-generated method stub
		return getRoleCodeKey().getValue(null).toString();
	}
}

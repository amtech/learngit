package com.oasys.listener;

import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.el.FixedValue;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.oasys.service.RoleService;
import com.oasys.service.UserService;
import com.oasys.service.WorkFlowTaskService;

public abstract class BaseTaskListener implements TaskListener {

	private static final long serialVersionUID = 1L;

	public abstract String getRoleCode();

	private FixedValue roleCodeKey;

	public FixedValue getRoleCodeKey() {
		return roleCodeKey;
	}

	public void setRoleCodeKey(FixedValue roleCodeKey) {
		this.roleCodeKey = roleCodeKey;
	}
	
	// 上下文
	protected WebApplicationContext webContext;
	// 用户的UserService
	protected UserService userService;
	// 角色的service
	protected RoleService roleService;
	//流程的service
	protected WorkFlowTaskService workFlowTaskService;
	{
		webContext = ContextLoader.getCurrentWebApplicationContext();
		userService = (UserService) webContext.getBean("userService");
		roleService = (RoleService) webContext.getBean("roleService");
		workFlowTaskService = (WorkFlowTaskService) webContext.getBean("workFlowTaskService");
	}
	
}

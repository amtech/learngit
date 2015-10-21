package com.oasys.listener.ad.expensesApp;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.oasys.service.ExpensesAppService;
import com.oasys.util.Constants;
/**
 * 更新订单状态监听
 * @ClassName: PurchaseAppBoUpdateStateListener 
 * @Description: TODO
 * @author PANCHUANHE
 * @date 2015年9月22日 下午7:37:38
 */
public class ExpensesAppHoUpdateStateListener implements ExecutionListener{

	private static final long serialVersionUID = 1L;

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		WebApplicationContext wx = ContextLoader.getCurrentWebApplicationContext();
		ExpensesAppService expensesAppServiceImpl = (ExpensesAppService)wx.getBean("expensesAppService");
		ExecutionEntity executionEntity = (ExecutionEntity)execution;
		if(executionEntity.getTransition()!=null){
			String paId = "";
			if(StringUtils.isNotBlank(execution.getProcessBusinessKey())){
				paId = execution.getProcessBusinessKey().split("\\.")[1];
			}
			expensesAppServiceImpl.updateExpensesAppStatus(Integer.valueOf(paId), Constants.EXPENSESAPP_HO +"_"+ executionEntity.getTransition().getId());
		}
	}

}

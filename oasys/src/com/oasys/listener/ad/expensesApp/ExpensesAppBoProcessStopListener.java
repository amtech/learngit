package com.oasys.listener.ad.expensesApp;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.oasys.service.ExpensesAppService;
import com.oasys.service.PurchaseAppService;
/**
 * 流程结束监听，更改订单流程状态
 * @ClassName: PurchaseAppBoProcessStopListener 
 * @Description: TODO
 * @author PANCHUANHE
 * @date 2015年10月10日 上午10:25:00
 */
@SuppressWarnings("serial")
public class ExpensesAppBoProcessStopListener implements ExecutionListener {

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		// 得到上下文环境
		WebApplicationContext ctx = ContextLoader
				.getCurrentWebApplicationContext();
		// 使用上下文环境中的getBean方法得到bean实例
		ExpensesAppService expensesAppService = (ExpensesAppService) ctx
				.getBean("expensesAppService");
		// 根据流程实例获取流程的BusinessKey,并获取当前的业务订单的id
		String id = "";
		if (StringUtils.isNotBlank(execution.getProcessBusinessKey())) {
			// 截取字符串，取BusinessKey小数点的第2个值,第二个值为业务订单的id
			id = execution.getProcessBusinessKey().split("\\.")[1];
		}
		// 修改订单的状态
		expensesAppService.updateExpensesAppProcessStatus(Integer.valueOf(id), "3");
		
	}

}

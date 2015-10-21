package com.oasys.listener.ad.StampTurnoverApp;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.oasys.model.StampTurnoverApp;
import com.oasys.model.StatusNameRef;
import com.oasys.service.StampTurnoverAppService;
import com.oasys.util.Constants;

/**
 * @Creater chenfl
 * @File_Name LoanProcessStopTaskListener.java
 * @Version v1.0
 * @Creation_Date 2015年5月25日 下午1:23:14
 * @Modifier
 * @Modified_Date
 * @Description 流程关闭的监听器
 */
@SuppressWarnings("serial")
public class StampTurnoverAppStopExecutionListener implements ExecutionListener {

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		// 得到上下文环境
		WebApplicationContext ctx = ContextLoader
				.getCurrentWebApplicationContext();
		// 使用上下文环境中的getBean方法得到bean实例
		StampTurnoverAppService stampTurnoverAppService = (StampTurnoverAppService) ctx
				.getBean("stampTurnoverAppService");
		// 根据流程实例获取流程的BusinessKey,并获取当前的业务订单的id
		Integer id = null;
		if (StringUtils.isNotBlank(execution.getProcessBusinessKey())) {
			// 截取字符串，取BusinessKey小数点的第2个值,第二个值为业务订单的id
			id = Integer.valueOf(execution.getProcessBusinessKey().split("\\.")[1]);
		}
		StampTurnoverApp stampTurnoverApp = stampTurnoverAppService.getStampTurnoverByID(id);
		StatusNameRef statusNameRef = stampTurnoverAppService.findAppStatusByID(stampTurnoverApp.getAppStatus());
		// 修改订单的状态 判断状态是否为申请撤销 如果的话 将流程状态更新为已撤销
		String statusKey=Constants.STAMPTURNOVERAPP+"_"+Constants.STAMPTURNOVERAPP_APPLY_KEY;
		if(statusNameRef.getAppStatusCode().equals(statusKey)){
			stampTurnoverAppService.updStampTurnoverProcessStatus(id, "5");
		}else{
			stampTurnoverAppService.updStampTurnoverProcessStatus(id, "3");
		}
	}

}

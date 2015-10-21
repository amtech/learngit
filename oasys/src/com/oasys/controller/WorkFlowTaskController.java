package com.oasys.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oasys.service.WorkFlowTaskService;
import com.oasys.viewModel.ProcessNameModel;


/**
 * 
 * @author lida
 * @time 2015/9/22
 * @remark 任务管理Controller(待办任务、受理任务)
 * 
 * */
@Controller
@RequestMapping("/workFlowTaskAction")
public class WorkFlowTaskController extends BaseController{
	
	/** 注入service. */
	@Autowired
	private WorkFlowTaskService workflowService;
	
	
	/**
	 * 查询下拉列表框
	 * */
	@ResponseBody
	@RequestMapping(value="/getProcNameList",method=RequestMethod.POST)
	public void getProcNameList(HttpServletRequest request,HttpServletResponse httpServletResponse){
		List<ProcessNameModel> procList = workflowService.processNameList();
		if(null == procList || procList.size() == 0){
			procList = new ArrayList<ProcessNameModel>();
			procList.add(0, new ProcessNameModel("", "无待办任务",""));
		}else{
			procList.add(0, new ProcessNameModel("", "请选择待办任务种类：",""));
		}
		OutputJson(httpServletResponse,procList);
	}
}

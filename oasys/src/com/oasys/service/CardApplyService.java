package com.oasys.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.oasys.model.AuditProcHis;
import com.oasys.model.CardApp;
import com.oasys.util.PageUtil;
import com.oasys.viewModel.CardAppVO;

public interface CardApplyService {
	//展示数据
	List<CardAppVO> getList(Map<String, Object> map,PageUtil pageUtil);
	//删除数据
	boolean delCard(Integer id);
	//添加申请
	boolean addCard(CardApp cardApp);
	//提交申请
	boolean submitCardApply(Integer id);
	//修改流程状态
	void updateStatus(Integer id,String status);
	//修改申请状态
	void updateApplyStatus(Integer id,String status);
	//获取任务列表
	List<CardAppVO> getTaskByGroup(int firstResult,int maxResults);
	//签收任务
	boolean signTask(String taskId);
	//受理任务
	boolean handleTask(String taskId,String result,CardAppVO cardAppVO, AuditProcHis auditProcHis,String paId);
	//获取总条数
	Long getCount(Map<String, Object> map);
	//获取部门Id及职位Id
	Map<String, Object> getDeptNo(String name);
	//查看受理任务
	List<CardAppVO> getAcceptTask();
	//删除名片附件
	boolean removeCardAccessory();
	//查看流程图
	void getDiagramResourceByCaId(HttpServletResponse response,Integer caId);
}

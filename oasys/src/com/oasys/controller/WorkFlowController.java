package com.oasys.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.oasys.model.ActReDeployment;
import com.oasys.model.ActReProcDef;
import com.oasys.service.AuditProcHisService;
import com.oasys.service.WorkFlowService;
import com.oasys.service.WorkFlowTaskService;
import com.oasys.viewModel.DataModel;
import com.oasys.viewModel.GridModel;
import com.oasys.viewModel.WorkFlowBean;



/**
 *author:masa
 *Time:2015年9月15日  上午9:12:44
 */
@Controller
@RequestMapping("/workflowAction")
public class WorkFlowController extends BaseController{

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 注入service. */
	@Autowired
	private WorkFlowService workflowService;
	
	@Autowired
	private WorkFlowTaskService workflowTaskService;
	
	@Autowired
	private AuditProcHisService auditProcHisService;
	

	/**
	 * 获取全部的部署的流程
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getAllProcessDeployment",method=RequestMethod.POST)
	public String getAllProcessDeployment(HttpServletResponse httpServletResponse,WorkFlowBean workFlowBean) {
		Long total = workflowService
				.findAllProcessDeploymentCount(workFlowBean);
		List<ActReDeployment> rows = workflowService
				.findAllProcessDeployment(workFlowBean);
		OutputJson(httpServletResponse,new GridModel(rows, total));
		return null;
	}

	/**
	 * 删除部署的流程
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/delProcessDeployment",method=RequestMethod.POST)
	public String delProcessDeployment(HttpServletResponse httpServletResponse,WorkFlowBean workFlowBean) {
		workflowService.deleteProcessDeployment(workFlowBean.getDeploymentId());
		OutputJson(httpServletResponse,new DataModel("提示", "删除成功!", true));
		return null;
	}
	
	/**
	 * 发布流程
	 * 
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/deployProcess",method=RequestMethod.POST)
	public String deployProcess(@RequestParam(value="file",required=false) MultipartFile file,HttpServletResponse httpServletResponse,HttpServletRequest request) {
//		System.out.println(request.getParameter("fileName"));
		
		try {
			// 判断文件类型是不是zip格式
		  /*String realPath = request.getSession().getServletContext().getRealPath("/diagrams");
			File dest = new File(realPath+File.separator+originalFilename);
			file.transferTo(dest);*/
			String originalFilename = file.getOriginalFilename();
			String extension = FilenameUtils.getExtension(originalFilename);
			if ("zip".equals(extension)) {
				
				this.workflowService.saveDeployProcess(file.getBytes(), request.getParameter("fileName"));
				OutputJson(httpServletResponse,new DataModel("提示", "流程发布成功!!", true), "text/html");
			}else{
			OutputJson(httpServletResponse,new DataModel("提示", "文件类型不匹配,请你上传zip/bar格式!!", false),
					"text/html");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 查询全部的流程定义实例
	 * 
	 * @return 流程定义列表
	 */
	@ResponseBody
	@RequestMapping(value="/getAllProcessDefinition",method=RequestMethod.POST)
	public String getAllProcessDefinition(HttpServletResponse httpServletResponse,WorkFlowBean workFlowBean) {
		List<ActReProcDef> list = this.workflowService
				.findAllProcessDefination(workFlowBean);
		Long total = this.workflowService
				.findAllProcessDefinationCount(workFlowBean);
		OutputJson(httpServletResponse,new GridModel(list, total));
		return null;
	}

	@ResponseBody
	@RequestMapping(value="/getTaskIDByBusID",method=RequestMethod.POST)
	public String getTaskIDByBusID(HttpServletResponse httpServletResponse,String busID,String definitionKey) {
		String taskID = workflowTaskService.getTaskByBusinessKey(busID,definitionKey);
		OutputJson(httpServletResponse,taskID);
		return null;
	}
	
	
	/**
	 * 获取流程定义的png图片
	 * 
	 * @return
	 */
	@RequestMapping(value="/getDiagramResource",method=RequestMethod.GET)
	public String getDiagramResource(HttpServletResponse httpServletResponse,WorkFlowBean workFlowBean) {
		InputStream in = this.workflowService
				.findDefinationResourceAsStream(workFlowBean);
		// 从response对象获取输出流
		try {
			OutputStream out =httpServletResponse.getOutputStream();
			// 将输入流中的数据读取出来，写到输出流中
			for (int b = -1; (b = in.read()) != -1;) {
				out.write(b);
			}
			out.close();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 查看流程图片
	 * @Title: showProcessImg 
	 * @Description: TODO
	 * @param @param httpServletResponse
	 * @param @param taskId
	 * @param @return
	 * @author PANCHUANHE
	 * @return String
	 * @date 2015年9月23日 下午5:24:29
	 * @throws
	 */
	@RequestMapping(value="/showProcessImg",method=RequestMethod.GET)
	public String showProcessImg(HttpServletResponse httpServletResponse,String imgName,String taskId){
		try {
			workflowService.getDiagramResourceByTaskId(httpServletResponse, imgName, taskId);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 根据formKey获取jspName
	 * @Title: findTaskFormKeyByTaskId 
	 * @Description: TODO
	 * @param @param httpServletResponse
	 * @param @param taskId
	 * @param @return
	 * @author PANCHUANHE
	 * @return String
	 * @date 2015年9月23日 下午7:27:37
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value="/findTaskFormKeyByTaskId",method=RequestMethod.POST)
	public String findTaskFormKeyByTaskId(HttpServletResponse httpServletResponse,String taskId){
		OutputJson(httpServletResponse, workflowService.findTaskFormKeyByTaskId(taskId));
		return null;
	}
	
	/**
	 * 查看历史审批意见
	 * @Title: findWorkFlowHis 
	 * @Description: TODO
	 * @param @param httpServletResponse
	 * @param @param appNo 唯一标识
	 * @param @return
	 * @author LiDa
	 * @return String
	 * @date 2015/10/9
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value="/findWorkFlowHis",method=RequestMethod.POST)
	public String findWorkFlowHis(HttpServletRequest httpRequest, HttpServletResponse httpServletResponse, @RequestParam(value="appNo",required = true) String appNo){
		OutputJson(httpServletResponse,auditProcHisService.findAllAuditProcHisList(appNo));
		return null;
	}
	
}



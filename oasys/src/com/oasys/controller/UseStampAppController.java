package com.oasys.controller;


import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.ActivitiTaskAlreadyClaimedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oasys.model.BadgeApp;
import com.oasys.model.BadgeAppAttach;
import com.oasys.model.UseStampApp;
import com.oasys.model.Users;
import com.oasys.service.BadgeAppService;
import com.oasys.service.OrganizationService;
import com.oasys.service.RoleService;
import com.oasys.service.UseStampAppService;
import com.oasys.service.UserService;
import com.oasys.service.WorkFlowService;
import com.oasys.util.Constants;
import com.oasys.util.PageUtil;
import com.oasys.viewModel.BadgeAppModel;
import com.oasys.viewModel.ComboBoxModel;
import com.oasys.viewModel.DataModel;
import com.oasys.viewModel.GridModel;
import com.oasys.viewModel.UsersModel;
/**
 * 
 * @ClassName: LoginController
 * @Description: TODO
 * @Author wangxincheng
 * @Version 1.0
 * @Date 2015年8月17日 下午2:24:06
 *工牌申请表
 */

@Controller
@RequestMapping("/UseStampApp")
public class UseStampAppController extends BaseController{
	
	@Autowired
	private UseStampAppService useStampAppService;
	@Autowired
	private UserService userService;
	@Autowired
	private WorkFlowService workFlowService;
	
	
	//查询用章申请列表
	@ResponseBody
	@RequestMapping(value="/findUseStampAppAttList",method=RequestMethod.POST)
	public String findUseStampAppAttList(HttpServletResponse httpServletResponse,Integer page,Integer rows,@ModelAttribute("useStampApp") UseStampApp useStampApp,  BindingResult bindingResult){
		PageUtil pageUtil = new PageUtil(page,rows);
		List<UseStampApp> useStampAppAttList = useStampAppService.findUseStampAppAttList(useStampApp, pageUtil);
		OutputJson2(httpServletResponse, new GridModel(useStampAppAttList,useStampAppService.getTotal(useStampApp)));
		return null;
	}
	
	//用章申请增加页面，查询申请人和部门名字
	@ResponseBody
	@RequestMapping(value="/findFullAppName",method=RequestMethod.POST)
	public String findFullAppName(HttpServletResponse httpServletResponse){
		UsersModel usersModel = useStampAppService.findFullAppName();
		OutputJson(httpServletResponse, usersModel);
		return null;
	}
	

	//保存或更新申请
	@ResponseBody
	@RequestMapping(value="/saveUseStamp",method=RequestMethod.POST)
	public String saveUseStamp(HttpServletResponse httpServletResponse,@ModelAttribute("useStampApp") UseStampApp useStampApp){
		boolean flag = useStampAppService.saveUseStamp(useStampApp);
		OutputJson(httpServletResponse,getMessage(flag));
		return null;
	}
	
	//删除申请
	@ResponseBody
	@RequestMapping(value="/deleteUseStamp",method=RequestMethod.POST)
	public String deleteUseStamp(HttpServletResponse httpServletResponse,Integer usaId){
		boolean flag = useStampAppService.deleteUseStamp(usaId);
		OutputJson(httpServletResponse,getMessage(flag));
		return null;
	}
	
	//--------------------------------------    用章申请任务办理            --------------------
	
	
	//提交申请
	@ResponseBody
	@RequestMapping(value="/addUseStampAppTask",method=RequestMethod.POST)
	public String addUseStampAppTask(HttpServletResponse httpServletResponse,Integer usaId){
		boolean flag = useStampAppService.addUseStampAppTask(usaId);
		OutputJson(httpServletResponse,getMessage(flag));
		return null;
	}
	
	//申请人列表查看流程图
	@ResponseBody
	@RequestMapping(value="/showBadgeProcess",method=RequestMethod.GET)
	public String showBadgeProcess(HttpServletResponse httpServletResponse,Integer usaId){
		useStampAppService.getDiagramResourceByPaId(httpServletResponse, usaId);
		return null;
	}
	
	//查询用章任务申请列表
	@ResponseBody
	@RequestMapping(value="/findUseStampAppClimTask",method=RequestMethod.POST)
	public String findUseStampAppClimTask(HttpServletResponse httpServletResponse,Integer page,Integer rows,@ModelAttribute("useStampApp") UseStampApp useStampApp,  BindingResult bindingResult){
		PageUtil pageUtil = new PageUtil(page,rows);
		List<UseStampApp> useStampAppAttList = useStampAppService.findUseStampAppClimTask(useStampApp, pageUtil);
		OutputJson2(httpServletResponse, new GridModel(useStampAppAttList,useStampAppService.getTaskTotal(useStampApp)));
		return null;
	}
	
	//任务办理查看流程图
	@ResponseBody
	@RequestMapping(value="/showProcessImg",method=RequestMethod.GET)
	public String showProcessImg(HttpServletResponse httpServletResponse,String taskId){
		String imgName="OA_PD_UseStampApp";
		try {
			workFlowService.getDiagramResourceByTaskId(httpServletResponse, imgName, taskId);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//个人领取任务
	@ResponseBody
	@RequestMapping(value="/taskUserClaim",method=RequestMethod.POST)
	public String taskUserClaim(HttpServletResponse httpServletResponse,String taskId){
		try {
			useStampAppService.getTaskUserClaim(taskId);
			OutputJson(httpServletResponse,new DataModel("提示", "任务签收成功。", true));
		} catch (ActivitiTaskAlreadyClaimedException e) {
			OutputJson(httpServletResponse,new DataModel("提示", "此任务已被他人签收！！", false));
		}
		return null;
	}
	
	//个人办理任务
	@ResponseBody
	@RequestMapping(value="/saveTaskMgrResult",method=RequestMethod.POST)
	public String saveTaskMgrResult(HttpServletResponse httpServletResponse,@ModelAttribute("useStampApp") UseStampApp useStampApp){
		boolean flag = useStampAppService.setUseStampTask(useStampApp);
		OutputJson(httpServletResponse, getMessage(flag));
		return null;
	}
	
	
}



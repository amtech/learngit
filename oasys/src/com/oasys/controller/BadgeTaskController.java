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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oasys.model.BadgeApp;
import com.oasys.service.BadgeTaskService;
import com.oasys.service.OrganizationService;
import com.oasys.service.StatusNameRefService;
import com.oasys.service.UserService;
import com.oasys.service.WorkFlowService;
import com.oasys.util.PageUtil;
import com.oasys.viewModel.BadgeAppModel;
import com.oasys.viewModel.DataModel;
import com.oasys.viewModel.GridModel;
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
@RequestMapping("/BadgeTask")
public class BadgeTaskController extends BaseController{
	
	@Autowired
	private BadgeTaskService badgeTaskService;
	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private UserService userService;
	//申请状态
	@Autowired
	private StatusNameRefService  statusNameRefService;
	/** 注入service. */
	@Autowired
	private WorkFlowService workflowService;
	
	
	
	//提交申请
	@ResponseBody
	@RequestMapping(value="/startBadgeApp",method=RequestMethod.POST)
	public String startBadgeApp(HttpServletResponse httpServletResponse,@RequestParam(value="pnrId",required=true) Integer pnrId){
		boolean isSuccess = true;
		try {
			isSuccess = badgeTaskService.badgeAppStartProcessInstance(pnrId);
		} catch (Exception e) {
			// TODO: handle exception
			isSuccess = false;
			System.out.println(e.toString());
		}
		OutputJson(httpServletResponse,getMessage(isSuccess));
		return null;
	}
	
	/**
	 * 查看未办理任务
	 * @Title: startBadgeApp 
	 * @Description: TODO
	 * @param @param httpServletResponse
	 * @param @param pnrId
	 * @param @return
	 * @author WANGXINCHENG
	 * @return String
	 * @date 2015年10月8日 下午3:08:04
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value="/findNotTaskClaimList",method=RequestMethod.POST)
	public String findNotTaskClaimList(HttpServletResponse httpServletResponse,Integer page,Integer rows,@ModelAttribute("badgeApp") BadgeApp badgeApp, BindingResult bindingResult){
		//分页后展示数据
		PageUtil pageUtil = new PageUtil(page,rows);
		//工牌申请详情
		List<BadgeAppModel> taskClimList = badgeTaskService.findNotTaskClimList(badgeApp, pageUtil);
		//工牌申请数量
		Long total = badgeTaskService.findTotal(badgeApp);
		OutputJson(httpServletResponse, new GridModel(taskClimList, total));
		
		return null;
	}
	
	/**
	 * 个人领取任务
	 * @Title: taskUserClaim 
	 * @Description: TODO
	 * @param @param httpServletResponse
	 * @param @param taskId
	 * @param @return
	 * @author WANGXINCHENG
	 * @return String
	 * @date 2015年9月24日 上午10:48:17
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value="/taskUserClaim",method=RequestMethod.POST)
	public String taskUserClaim(HttpServletResponse httpServletResponse,String taskId){
		try {
			badgeTaskService.getTaskUserClaim(taskId);
			OutputJson(httpServletResponse,new DataModel("提示", "任务签收成功。", true));
		} catch (ActivitiTaskAlreadyClaimedException e) {
			OutputJson(httpServletResponse,new DataModel("提示", "此任务已被他人签收！！", false));
		}
		return null;
	}
	
	/**
	 * 查看申请流程
	 * @Title: showProcessImg 
	 * @Description: TODO
	 * @param @param httpServletResponse
	 * @param @param imgName
	 * @param @param taskId
	 * @param @return
	 * @author WANGXINCHENG
	 * @return String
	 * @date 2015年9月24日 上午10:49:09
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value="/showProcessImg",method=RequestMethod.GET)
	public String showProcessImg(HttpServletResponse httpServletResponse,String taskId){
		String imgName="OA_AD_BadgeApp";
		try {
			workflowService.getDiagramResourceByTaskId(httpServletResponse, imgName, taskId);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 任务办理结果
	 * @Title: saveTaskMgrResult 
	 * @Description: TODO
	 * @param @param httpServletResponse
	 * @param @param badgeAppModel
	 * @param @return
	 * @author WANGXINCHENG
	 * @return String
	 * @date 2015年10月9日 下午1:39:20
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value="/saveTaskMgrResult",method=RequestMethod.POST)
	public String saveTaskMgrResult(HttpServletResponse httpServletResponse,@ModelAttribute("badgeAppModel") BadgeAppModel badgeAppModel){
		boolean flag = badgeTaskService.addBadgeTaskMgr(badgeAppModel);
		OutputJson(httpServletResponse, getMessage(flag));
		return null;
	}
	
	/**
	 * 查看申请流程
	 * @Title: showProcessImg 
	 * @Description: TODO
	 * @param @param httpServletResponse
	 * @param @param imgName
	 * @param @param taskId
	 * @param @return
	 * @author WANGXINCHENG
	 * @return String
	 * @date 2015年9月24日 上午10:49:09
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value="/showBadgeProcess",method=RequestMethod.GET)
	public String showBadgeProcess(HttpServletResponse httpServletResponse,Integer pnrId){
		badgeTaskService.getDiagramResourceByPaId(httpServletResponse, pnrId);
		return null;
	}
	
	
	
	
}



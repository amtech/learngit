package com.oasys.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.servlet.ModelAndView;

import com.oasys.model.StampTurnoverApp;
import com.oasys.model.Users;
import com.oasys.service.OrganizationService;
import com.oasys.service.StampTurnoverAppService;
import com.oasys.service.UserService;
import com.oasys.service.WorkFlowTaskService;
import com.oasys.util.Constants;
import com.oasys.util.DateUtils;
import com.oasys.util.PageUtil;
import com.oasys.util.UniqueIdUtil;
import com.oasys.viewModel.GridModel;
import com.oasys.viewModel.Json;

/**   
 * @Title: StampTurnoverAppController
 * @Package com.oasys.controller
 * @Description: 印章移交申请Controller
 * @author lida  
 * @date 2015/10/9
 * @version V1.0   
 */
@Controller
@RequestMapping("/stampTurnoverAppController")
public class StampTurnoverAppController extends BaseController{
	/** 注入service. */
	@Autowired
	private StampTurnoverAppService stampTurnoverAppService;
	
	@Autowired
	private OrganizationService orgService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private WorkFlowTaskService workTaskService;
	
	
	/** 
	 * @Title: findAllStampTurnover
	 * @Description: 查询印章移交申请列表
	 * @param page 分页参数
	 * @param rows 分页参数
	 * @param StampTurnover 页面绑定实体对象参数
	 * @author lida
	 * @return String
	 * @date 2015/10/9
	 */
	@ResponseBody
	@RequestMapping(value="/findAllStampTurnover",method=RequestMethod.POST)
	public String findAllStampTurnover(HttpServletRequest httpRequest, HttpServletResponse httpServletResponse, Integer page,Integer rows,@ModelAttribute("StampTurnover") StampTurnoverApp stampTurnover,  BindingResult bindingResult){
    	GridModel gridModel = new GridModel();
		gridModel.setRows(stampTurnoverAppService.findStampList(new PageUtil(page,rows),stampTurnover));
		gridModel.setTotal(stampTurnoverAppService.findStampListCount(stampTurnover));
		OutputJson2(httpServletResponse, gridModel);
		return null;
	}
	
	/** 
	 * @Title: addStampTurnoverApp
	 * @Description: 新增印章移交申请
	 * @param StampTurnover 页面绑定实体对象参数
	 * @author lida
	 * @return String
	 * @date 2015/10/10
	 */
	@ResponseBody
	@RequestMapping(value="/addOrUpdateStampTurnoverApp",method=RequestMethod.POST)
	public String addStampTurnoverApp(HttpServletRequest httpRequest,HttpServletResponse httpServletResponse, @ModelAttribute("stampTurnover") StampTurnoverApp stampTurnover,  BindingResult bindingResult) {
		//新增获取当前登录用户中的信息
		Users user = userService.getUserByID(Constants.getCurrendUser().getUserId());
		List<String> dataList = new ArrayList<String>();
		try {
			if(null == stampTurnover.getStaId()){
				stampTurnover.setAppNo(UniqueIdUtil.generate("YZ"));
				stampTurnover.setAppDept(user.getOrganizeId());//部门编号
				stampTurnover.setApplicantNo(user.getUserId());//申请人
				stampTurnover.setAppDate(DateUtils.getNowTime(DateUtils.DATE_FULL_STR));//申请时间
				stampTurnover.setProcStatus("1");//新增默认初始状态
				stampTurnoverAppService.saveOrUpdatePpeEntity(stampTurnover);
			}else{
				stampTurnoverAppService.saveOrUpdatePpeEntity(stampTurnover);
			}
			dataList.add(stampTurnover.getStaId().toString());
			dataList.add(stampTurnover.getAppNo());
			OutputJson(httpServletResponse,new Json("提示","保存成功",true,dataList));
		} catch (Exception e) {
			OutputJson(httpServletResponse,new Json("提示","保存失败",false,dataList));
		}
		return null;
	}
	
	/** 
	 * @Title: delStampTurnover
	 * @Description:  删除印章移交申请
	 * @param appNo 申请编号
	 * @author lida 
	 * @return String
	 * @date 2015/10/11
	 */
	@ResponseBody
	@RequestMapping(value="/delStampTurnover",method=RequestMethod.POST)
	public String delStampTurnover(HttpServletRequest httpRequest, HttpServletResponse httpServletResponse, @RequestParam(value="appNo",required = true) String appNo){
		boolean isSuccess = true;
		try {
			stampTurnoverAppService.delStampTurnover(appNo);
		} catch (Exception e) {
			// TODO: handle exception
			isSuccess = false;
		}
		OutputJson(httpServletResponse,getMessage(isSuccess));
		return null;
	}
	
	/** 
	 * @Title: toAddStampTurnoverApp
	 * @Description:  跳转到印章移交申请新增页面
	 * @param staId ID标识
	 * @author lida 
	 * @return String
	 * @date 2015/10/12
	 */
    @RequestMapping(value="/toAddStampTurnoverApp")
	public ModelAndView toAddStampTurnoverApp(HttpServletRequest httpServletRequest,@RequestParam(value="staId",required = true) String staId){
    	StampTurnoverApp stampTurnoverApp = new StampTurnoverApp();
    	//判断是否为新增
    	if(null == staId || "".equals(staId)){
    		stampTurnoverApp.setUserName(Constants.getCurrendUser().getAccount());
    		stampTurnoverApp.setOrgName(orgService.getOrgNameByID(userService.getUserByID(Constants.getCurrendUser().getUserId()).getOrganizeId()));
    		stampTurnoverApp.setAppDate(DateUtils.getNowTime());
    		stampTurnoverApp.setProcStatus("1");//设置默认流程状态为1
    	}else{
    	//修改
    		stampTurnoverApp = stampTurnoverAppService.getStampTurnoverByID(Integer.valueOf(staId));
    		stampTurnoverApp.setUserName(userService.getUserByID(stampTurnoverApp.getApplicantNo()).getName());//翻译用户名
    		stampTurnoverApp.setOrgName(orgService.getOrgNameByID(userService.getUserByID(stampTurnoverApp.getApplicantNo()).getOrganizeId()));//翻译部门名称
    		
    		stampTurnoverApp.setReceiverOrgName(orgService.getOrgNameByID(stampTurnoverApp.getReceiverDept()));//接收部门
			stampTurnoverApp.setReceiverUserName(userService.getUserByID(stampTurnoverApp.getReceiverNo()).getName());//接收人
			
			stampTurnoverApp.setSuperviserOrgName(orgService.getOrgNameByID(stampTurnoverApp.getSuperviserDept()));//监交部门
			stampTurnoverApp.setSuperviserUserName(userService.getUserByID(stampTurnoverApp.getSuperviserNo()).getName());//监交人
    		
    	}
    	ModelAndView mv = new ModelAndView("ad/stampTurnover/stampTurnoverAdd");
    	mv.addObject("stampTurnover", stampTurnoverApp);
		return mv;
	}
    
	
	/**---------------------------------------流程相关---------------------------------------*/
	
	/** 
	 * @Title: startProcessStampTurnover
	 * @Description:  提交开启印章移交申请流程
	 * @param staId ID标识
	 * @author lida 
	 * @return String
	 * @date 2015/10/12
	 */
	@ResponseBody
	@RequestMapping(value="/startProcessStampTurnover",method=RequestMethod.POST)
	public String startProcessStampTurnover(HttpServletRequest httpRequest, HttpServletResponse httpServletResponse, @RequestParam(value="staId",required = true) Integer staId){
		boolean isSuccess = true;
		try {
			isSuccess = stampTurnoverAppService.StampTurnoverStartProcessInstance(staId);
		} catch (Exception e) {
			// TODO: handle exception
			isSuccess = false;
		}
		OutputJson(httpServletResponse,getMessage(isSuccess));
		return null;
	}

	
	/** 
	 * @Title: findStampTurnoverTask
	 * @Description:  查询印章移交申请任务列表
	 * @param StampTurnover 页面绑定实体对象
	 * @author lida 
	 * @return String
	 * @date 2015/10/11
	 */
	@ResponseBody
	@RequestMapping(value="/findStampTurnoverTask",method=RequestMethod.POST)
	public String findStampTurnoverTask(HttpServletRequest httpRequest, HttpServletResponse httpServletResponse,  Integer page,Integer rows,@ModelAttribute("stampTurnover") StampTurnoverApp stampTurnover,  BindingResult bindingResult){
    	GridModel gridModel = new GridModel();
		gridModel.setRows(stampTurnoverAppService.findStampListTask(new PageUtil(page,rows),stampTurnover));//获取申请任务列表
		gridModel.setTotal(stampTurnoverAppService.findStampListTaskCount(stampTurnover));//获取总记录数
		OutputJson2(httpServletResponse,gridModel);
		return null;
	}
	
	/** 
	 * @Title: signForTask
	 * @Description:  签收任务
	 * @param taskID 任务流程ID
	 * @author lida 
	 * @return String
	 * @date 2015/10/12
	 */
	@ResponseBody
	@RequestMapping(value="/signForTask",method=RequestMethod.POST)
	public String signForTask(HttpServletRequest httpRequest, HttpServletResponse httpServletResponse, @RequestParam(value="taskID",required = true) String taskID){
		boolean isSuccess = true;
		try{
			workTaskService.signForTask(taskID);//执行任务签收
		}catch (ActivitiTaskAlreadyClaimedException e) {
			isSuccess = false;
		}
		OutputJson(httpServletResponse,getMessage(isSuccess));
		return null;
	}

	
	/** 
	 * @Title: saveTaskStampTurnoverApp
	 * @Description:  受理申请
	 * @param StampTurnover 页面绑定实体对象
	 * @author lida 
	 * @return String
	 * @date 2015/10/12
	 */
	@ResponseBody
	@RequestMapping(value="/saveTaskStampTurnoverApp",method=RequestMethod.POST)
	public String saveTaskStampTurnoverApp(HttpServletRequest httpRequest,HttpServletResponse httpServletResponse, @ModelAttribute("stampTurnover") StampTurnoverApp stampTurnover,  BindingResult bindingResult) {
		boolean saveSuccess = true;
		try {
			stampTurnoverAppService.saveSubmitTask(stampTurnover);	//执行受理申请
		} catch (Exception e) {
			e.printStackTrace();
			saveSuccess = false;
		}
		OutputJson(httpServletResponse, getMessage(saveSuccess));
		return null;
	}
	
}

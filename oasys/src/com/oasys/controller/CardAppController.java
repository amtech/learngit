package com.oasys.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import com.oasys.model.AuditProcHis;
import com.oasys.model.CardApp;
import com.oasys.model.CardAppAttach;
import com.oasys.service.AuditPorcService;
import com.oasys.service.CardAppAttachService;
import com.oasys.service.CardApplyService;
import com.oasys.service.UserService;
import com.oasys.shiro.ShiroUser;
import com.oasys.util.Constants;
import com.oasys.util.DateUtils;
import com.oasys.util.PageUtil;
import com.oasys.util.UniqueIdUtil;
import com.oasys.viewModel.CardAppAttachModel;
import com.oasys.viewModel.CardAppVO;
import com.oasys.viewModel.GridModel;
import com.oasys.viewModel.Json;
import com.oasys.viewModel.UsersModel;
import com.sun.org.apache.bcel.internal.generic.LSTORE;

import freemarker.template.utility.StringUtil;

/**
 * 
 * @author Guo
 *
 */
@Controller 
public class CardAppController extends BaseController{
	private static Logger logger = Logger.getLogger(CardAppController.class);
	@Autowired
	private CardApplyService cardApplyService;
	@Autowired
	private AuditPorcService auditPorcService;
	@Autowired
	private UserService userService;
	@Autowired
	private CardAppAttachService cardAppAttachService;

	/**
	 * 展示名片
	 * @Title: index 
	 * @Description: 展示名片
	 * @param @param response
	 * @param @param request
	 * @param @return
	 * @author Guo
	 * @return String
	 * @date 2015年9月30日 上午10:59:50
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value="/callingCard/index")
	public String index(HttpServletResponse response,HttpServletRequest request,Integer page,Integer rows){
		Map<String, Object> map = new HashMap<String, Object>();
		GridModel gridModel = new GridModel();
		PageUtil pageUtil = new PageUtil(page,rows);
		map.put("status", request.getParameter("procStatus"));
		map.put("dateBegin", request.getParameter("appDateBefore"));
		map.put("dateEnd", request.getParameter("appDateAfter"));
		map.put("daetApplyBegin", request.getParameter("appApplyDateBefore"));
		map.put("daetApplyEnd", request.getParameter("appApplyDateAfter"));
		gridModel.setRows(cardApplyService.getList(map,pageUtil));
		gridModel.setTotal(cardApplyService.getCount(map));
		OutputJson(response,gridModel);
		return null;
	}
	
	/**
	 * 展示名片附件
	 * @Title: indexCardAppAttach 
	 * @Description: 展示名片附件
	 * @param @param response
	 * @param @param request
	 * @param @return
	 * @author Guo
	 * @return String
	 * @date 2015年9月30日 上午11:00:08
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value="/callingCard/indexCardAppAttach")
	public String indexCardAppAttach(HttpServletResponse response,HttpServletRequest request,String caID){
		List<CardAppAttachModel> list = cardAppAttachService.getList(caID);
		OutputJson(response,list);
		return null;
	}
	
	/**
	 * 删除名片
	 * @Title: removeCard 
	 * @Description: 删除名片
	 * @param @param id
	 * @param @param map
	 * @param @param response
	 * @param @return
	 * @author Guo
	 * @return String
	 * @date 2015年9月29日 下午2:07:40
	 * @throws
	 */
	@RequestMapping(value="/callingCard/removeCard")
	public String removeCard(@RequestParam("id")String id,ModelMap map,HttpServletResponse response){
		boolean flag=cardApplyService.delCard(Integer.parseInt(id+""));
		OutputJson(response,getMessage(flag));
		return null;
	}
	
	/**
	 * 删除名片附件为未保存的
	 * @Title: removeCardAccess 
	 * @Description: 删除名片附件
	 * @param @param id
	 * @param @param response
	 * @param @return
	 * @author Guo
	 * @return String
	 * @date 2015年10月13日 上午10:45:06
	 * @throws
	 */
	@RequestMapping(value="/callingCard/removeCardAccess")
	public String removeCardAccess(@RequestParam("id")String id,HttpServletResponse response){
		boolean flag=cardApplyService.removeCardAccessory();
		OutputJson(response,getMessage(flag));
		return null;
	}
	

	/**
	 * 删除名片附件
	 * @Title: removeCardAttach 
	 * @Description: 删除名片附件
	 * @param @param id
	 * @param @param response
	 * @param @return
	 * @author Guo
	 * @return String
	 * @date 2015年9月29日 下午2:07:29
	 * @throws
	 */
	@RequestMapping(value="/callingCard/removeCardAttach")
	public String removeCardAttach(@RequestParam("id")String id,HttpServletResponse response){
		boolean flag=cardAppAttachService.delCardAttach(Integer.parseInt(id));
		OutputJson(response,getMessage(flag));
		return null;
	}
	
	/**
	 * 添加名片附件申请
	 * @Title: addCard 
	 * @Description: 添加名片附件申请
	 * @param @param request
	 * @param @param response
	 * @param @return
	 * @author Guo
	 * @return String
	 * @date 2015年9月30日 上午11:00:20
	 * @throws
	 */
	@RequestMapping(value="/callingCard/addCard",method=RequestMethod.POST )
	public String addCard(HttpServletRequest request,HttpServletResponse response){
		//名片附件表
		CardAppAttach e = new CardAppAttach();
		if(org.apache.commons.lang3.StringUtils.isNotBlank(request.getParameter("caId"))){
			e.setCaId(Integer.parseInt(request.getParameter("caId")));
		}
		if(request.getParameter("appNo")==null || "".equals(request.getParameter("appNo"))){
			e.setAppNo(null);//申请编号
		}else{
			e.setAppNo(request.getParameter("appNo"));//申请编号
		}
		e.setApplicantNo(Integer.parseInt(request.getParameter("applicantNo")));//申请人
		e.setDeptNo(Integer.parseInt(cardApplyService.getDeptNo(request.getParameter("deptNo")).get("deptNo")+""));//部门
		e.setPosition(request.getParameter("position"));//申请人职位
		if(org.apache.commons.lang3.StringUtils.isNotBlank(request.getParameter("personalTel"))){
			e.setPersonalTel(request.getParameter("personalTel"));//申请人电话*
		}
		if(org.apache.commons.lang3.StringUtils.isNotBlank(request.getParameter("officeTel"))){
			e.setOfficeTel(request.getParameter("officeTel"));//申请人办公电话*
		}
		e.setEmail(request.getParameter("email"));//申请人邮箱
		e.setBranchAddr(request.getParameter("branchAddr"));//分公司地址
		e.setComUrl(request.getParameter("comUrl"));//网址
		e.setAppQty(Integer.parseInt(request.getParameter("appQty")));//申请数量
		e.setUnit("盒");//申请单位
		e.setRemark(request.getParameter("remark"));//备注
		boolean flag=cardAppAttachService.addCardAppAttach(e);
		OutputJson(response,getMessage(flag));
		return null;
	}
	
	/**
	 * 添加名片
	 * @Title: addCardTable 
	 * @Description: 添加名片
	 * @param @param request
	 * @param @param response
	 * @param @return
	 * @author Guo
	 * @return String
	 * @date 2015年9月30日 上午11:00:41
	 * @throws
	 */
	@RequestMapping(value="/callingCard/addCardTable")
	public String addCardTable(HttpServletRequest request,HttpServletResponse response){
		ShiroUser shiroUser = Constants.getCurrendUser();
		CardApp  cardApp = new CardApp();
		cardApp.setAppNo(UniqueIdUtil.generate("MP"));//唯一编号
		cardApp.setRegistrantNO(shiroUser.getUserId());//登记人Id
		cardApp.setRegDeteTime(DateUtils.parse(DateUtils.getNowTime()));//登记日期
		cardApp.setAppDate(null);//申请日期
		cardApp.setAppStatus(null);//申请状态
		cardApp.setAppQty(cardAppAttachService.getCountByCardApp(null).intValue());//申请数量
		cardApp.setProcStatus(1);//流程状态
		cardApp.setUnit("盒");//单位
		cardApp.setRemark(cardAppAttachService.getCountByCardApp(null)+"");//备注
		boolean flag=cardApplyService.addCard(cardApp);
		if(flag){
			cardAppAttachService.modifyCardAttachCaId(cardApp.getAppNo());
		}
		OutputJson(response,getMessage(flag));
		return null;
	}
	
	/**
	 * 查看审批意见
	 * @Title: getAuditPorc 
	 * @Description: 查看审批意见
	 * @param @param appNo
	 * @param @param response
	 * @param @return
	 * @author Guo
	 * @return String
	 * @date 2015年9月30日 上午11:00:51
	 * @throws
	 */
	@RequestMapping(value="/callingCard/getAuditPorc")
	public String getAuditPorc(@RequestParam("appNo")String appNo,HttpServletResponse response){
		System.out.println("--------------"+appNo);
		List<AuditProcHis> list = auditPorcService.getAuditPorcList(appNo);
		OutputJson(response, list);
		return null;
	}
	
	/**
	 * 加载姓名
	 * @Title: getUserInfo 
	 * @Description: 加载姓名
	 * @param @param q
	 * @param @param response
	 * @param @return
	 * @author Guo
	 * @return String
	 * @date 2015年9月30日 上午11:00:58
	 * @throws
	 */
	@RequestMapping(value="/callingCard/getUserInfo")
	public String getUserInfo(@RequestParam("q")String q,HttpServletResponse response){
		System.out.println(q+"-----------");
		if(StringUtils.hasText(q)){
			System.out.println(q.substring(1));
		}
		GridModel gridModel = new GridModel();
		if(StringUtils.hasText(q)){
			gridModel.setRows(userService.getUserInfo());
		}else{
			gridModel.setRows(userService.getUserInfo());
		}
		OutputJson(response, gridModel);
		return null;
	}
	
	/**
	 * 根据姓名加载信息
	 * @Title: loadUserInfo 
	 * @Description: 根据姓名加载信息
	 * @param @param q
	 * @param @param response
	 * @param @return
	 * @author Guo
	 * @return String
	 * @date 2015年9月30日 上午11:01:14
	 * @throws
	 */
	@RequestMapping(value="/callingCard/loadUserInfo")
	public String loadUserInfo(@RequestParam("q")String q,HttpServletResponse response){
		System.out.println(q+"-----------");
		List<UsersModel> list=null;
		try {
			list = userService.loadUserInfo(Integer.parseInt(q));
			OutputJson(response,list.get(0));
		} catch (NumberFormatException e) {
			System.err.println(e.toString());
		}
		return null;
	}
	
	
	/**
	 * 提交申请
	 * @Title: saveCardApply 
	 * @Description: 提交申请
	 * @param @param q
	 * @param @param response
	 * @param @return
	 * @author Guo
	 * @return String
	 * @date 2015年9月30日 上午11:02:13
	 * @throws
	 */
	@RequestMapping(value="/callingCard/saveCardApply")
	public String saveCardApply(@RequestParam("id")String q,HttpServletResponse response){
		boolean flag=cardApplyService.submitCardApply(Integer.parseInt(q));
		OutputJson(response, getMessage(flag));
		return null;
	}
	
	/**
	 * 查看待办任务
	 * @Title: findAllPurchaseAppTaskList 
	 * @Description: 查看待办任务
	 * @param @param httpServletResponse
	 * @param @param page
	 * @param @param rows
	 * @param @return
	 * @author Guo
	 * @return String
	 * @date 2015年9月24日 上午9:35:24
	 * @throws
	 */
	 @ResponseBody
	 @RequestMapping(value="/callingCard/QueryCardTask",method=RequestMethod.POST)
	 public String findAllPurchaseAppTaskList(HttpServletResponse httpServletResponse,Integer page,Integer rows){
	    	Integer firstResult = (page-1)*rows;
	        Integer maxResults = rows;
	        GridModel gridModel = new GridModel();
	        gridModel.setRows(cardApplyService.getTaskByGroup(firstResult,maxResults));
	        OutputJson2(httpServletResponse, gridModel);
	    	return null;
	  }
	 
	 /**
	  * 签收任务
	  * @Title: signTask 
	  * @Description: 签收任务
	  * @param @param taskId
	  * @param @param httpServletResponse
	  * @param @return
	  * @author Guo
	  * @return String
	  * @date 2015年10月9日 下午2:24:31
	  * @throws
	  */
	 @ResponseBody
	 @RequestMapping(value="/callingCard/SignTask",method=RequestMethod.POST)
	 public String signTask(@RequestParam("taskId")String taskId,HttpServletResponse httpServletResponse){
		 	boolean flag=cardApplyService.signTask(taskId);
		 	if(flag){
		 		 OutputJson2(httpServletResponse,new Json("提示","提交申请成功!",flag));
		 	}else {
		 		 OutputJson2(httpServletResponse,new Json("提示","提交申请失败!",flag));
			}
	    	return null;
	  }
	 
	 
	 /**
	  * 办理任务
	  * @Title: handleTast 
	  * @Description: 办理任务
	  * @param @param status
	  * @param @param taskId
	  * @param @param isSuccess
	  * @param @param httpServletResponse
	  * @param @return
	  * @author Guo
	  * @return String
	  * @date 2015年10月9日 下午2:24:43
	  * @throws
	  */
	 @ResponseBody
	 @RequestMapping(value="/callingCard/handleTask",method=RequestMethod.POST)
	 public String handleTast(String taskId,String handleResult,CardAppVO cardAppVO,AuditProcHis auditProcHis,String result,String paId,HttpServletResponse httpServletResponse){
		 if("bumenfuzerentongguo".equals(result)){//部门负责人通过申请
			 result="DepartmentPersonSuccess";
		 }else if("bumenfuzerenbohui".equals(result)){//部门负责人驳回申请
			 result="DepartmentPersonApplyReject";
		 }else if("shenqingchongti".equals(result)){//申请重提
			 result="DepartmentPersonAlignApply";
		 }else if("shenqingchexiao".equals(result)){//申请撤销
			 result="DepartmentPersonTransferApply";
		 }else if("shenqingchongtibo".equals(result)){//申请重提bo
			 result="DepartmentPersonAlignApplybo";
		 }else if("shenqingchexiaobo".equals(result)){//申请撤销bo
			 result="DepartmentPersonTransferApplybo";
		 }else if("xingzhengjinglitongguo".equals(result)){//行政经理审批通过
			 result="AdministrationManagerSuccess";
		 }else if("xingzhengjinglibohui".equals(result)){//行政经理审批驳回
			 result="AdministrationManagerRejectApply";
		 }else if("xingzhengjinglitongguobo".equals(result)){//行政经理审批通过bo
			 result="AdministrationManagerSuccessbo";
		 }else if("xingzhengjinglibohuibo".equals(result)){//行政经理审批驳回bo
			 result="AdministrationManagerRejectApplybo";
		 }else if("qiyexuanchuanguanlizhongxintongguo".equals(result)){//企业宣传管理中心通过
			 result="PublicityCenterSuccess";
		 }else if("xingzhengzhuanyuantongguo".equals(result)){//行政专员通过
			 result="AdminPersonSuccess";
		 }
		 boolean flag=cardApplyService.handleTask(taskId,result,cardAppVO,auditProcHis,paId);
	     OutputJson2(httpServletResponse, flag);
	     return null;
	  }
	 
	 /**
	  * 获取办理任务
	  * @Title: getAcceptTask 
	  * @Description: 获取办理任务
	  * @param @param httpServletResponse
	  * @param @return
	  * @author Guo
	  * @return String
	  * @date 2015年10月9日 下午2:24:59
	  * @throws
	  */
	 @ResponseBody
	 @RequestMapping(value="/callingCard/getAcceptTask",method=RequestMethod.POST)
	 public String getAcceptTask(HttpServletResponse httpServletResponse){
		 	List<CardAppVO> list = cardApplyService.getAcceptTask();
	        OutputJson2(httpServletResponse, list);
	    	return null;
	  }
	 
	 /**
	  * 查看流程图
	  * @Title: showProcessImg 
	  * @Description: 查看流程图
	  * @param @param httpServletResponse
	  * @param @param paId
	  * @param @return
	  * @author Guo
	  * @return String
	  * @date 2015年10月14日 下午6:50:10
	  * @throws
	  */
	 @RequestMapping(value="/callingCard/showProcessImg",method=RequestMethod.GET)
	    public String showProcessImg(HttpServletResponse response,Integer caId){
		 	cardApplyService.getDiagramResourceByCaId(response, caId);
	    	return null;
	    }
}

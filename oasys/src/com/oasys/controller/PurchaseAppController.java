package com.oasys.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.oasys.model.AuditProcHis;
import com.oasys.model.PurchaseApp;
import com.oasys.service.PurchaseAppAttachService;
import com.oasys.service.PurchaseAppService;
import com.oasys.util.Constants;
import com.oasys.util.PageUtil;
import com.oasys.viewModel.GridModel;
import com.oasys.viewModel.Json;
import com.oasys.viewModel.PurchaseAppModel;

/**
 * @ClassName: PurchaseAppController 
 * @Description: TODO 物料申请Controller
 * @author PANCHUANHE
 * @date 2015年9月15日 下午4:31:29
 */
@Controller
@RequestMapping("/purchaseAppController")
public class PurchaseAppController extends BaseController{
	
	@Autowired
	private PurchaseAppService purchaseAppService;
	@Autowired
	private PurchaseAppAttachService purchaseAppAttachService;
	/**
	 * @Title: toAddWindow 
	 * @Description: TODO 跳转到新增页面
	 * @param @return
	 * @author PANCHUANHE
	 * @return ModelAndView
	 * @date 2015年9月15日 下午4:59:06
	 * @throws
	 */
    @RequestMapping(value="/toAddWindow")
	public ModelAndView toAddWindow(HttpServletRequest httpServletRequest){
    	String account =  Constants.getCurrendUser().getAccount();
    	httpServletRequest.setAttribute("account", account);
    	ModelAndView mv = new ModelAndView("ad/purchaseApp/purchaseAppForm");
		return mv;
	}
    /**
	 * @Title: toAddWindow 
	 * @Description: TODO 跳转到新增页面
	 * @param @return
	 * @author PANCHUANHE
	 * @return ModelAndView
	 * @date 2015年9月15日 下午4:59:06
	 * @throws
	 */
    @RequestMapping(value="/toAddWindow2")
	public ModelAndView toAddWindow2(HttpServletRequest httpServletRequest){
    	String account =  Constants.getCurrendUser().getAccount();
    	httpServletRequest.setAttribute("account", account);
    	ModelAndView mv = new ModelAndView("ad/purchaseApp/purchaseApp2Form");
		return mv;
	}
    /**
     * @Title: savePurchaseApp 
     * @Description: TODO 新增采购申请表
     * @param @return
     * @author PANCHUANHE
     * @return String
     * @date 2015年9月16日 下午1:41:08
     * @throws
     */
    @ResponseBody
    @RequestMapping(value="/savePurchaseApp",method=RequestMethod.POST)
    public String savePurchaseApp(HttpServletResponse httpServletResponse,PurchaseApp purchaseApp){
    	boolean bl = purchaseAppService.savePurchaseApp(purchaseApp);
    	if(bl){
			OutputJson2(httpServletResponse,new Json("提示","保存成功!",bl,purchaseApp));
		}else{
			OutputJson2(httpServletResponse,new Json("提示","出错了,保存失败!",bl));
		}
    	return null;
    }
    /**
     * @Title: searchPurchaseAppList 
     * @Description: TODO 查询列表
     * @param @param httpServletResponse
     * @param @param page
     * @param @param rows
     * @param @return
     * @author PANCHUANHE
     * @return String
     * @date 2015年9月18日 下午2:10:14
     * @throws
     */
    @ResponseBody
    @RequestMapping(value="/searchPurchaseAppList",method=RequestMethod.POST)
    public String searchPurchaseAppList(HttpServletResponse httpServletResponse,Integer page,Integer rows,PurchaseAppModel purchaseAppModel){
    	PageUtil pageUtil = new PageUtil(page,rows);
    	GridModel gridModel = new GridModel();
    	gridModel.setRows(purchaseAppService.findPurchaseAppList(pageUtil,purchaseAppModel));
    	gridModel.setTotal(purchaseAppService.countPurchaseAppList());
    	OutputJson2(httpServletResponse, gridModel);
    	return null;
    }
    /**
     * @Title: delPurchaseApp 
     * @Description: TODO 删除
     * @param @return
     * @author PANCHUANHE
     * @return String
     * @date 2015年9月18日 下午2:09:19
     * @throws
     */
    @ResponseBody
    @RequestMapping(value="/delPurchaseApp",method=RequestMethod.POST)
    public String delPurchaseApp(HttpServletResponse httpServletResponse,PurchaseApp purchaseApp){
    	boolean fl = purchaseAppAttachService.delPurchaseAppAttachByAppNo(purchaseApp.getAppNo());
    	boolean bl = purchaseAppService.delPurchaseAppByPaId(purchaseApp.getPaId());
    	if(bl && fl){
    		OutputJson(httpServletResponse,new Json("提示","删除成功!",bl));
    	}else{
    		OutputJson(httpServletResponse,new Json("提示","出错了,删除失败!",bl));
    	}
    	return null;
    }
    /**
     * 根据id查询
     * @Title: findPurchaseAppByPaId 
     * @Description: TODO
     * @param @return
     * @author PANCHUANHE
     * @return String
     * @date 2015年9月21日 下午1:24:44
     * @throws
     */
    @ResponseBody
    @RequestMapping(value="/findPurchaseAppByPaId",method=RequestMethod.POST)
    public String findPurchaseAppByAppNo(HttpServletResponse httpServletResponse,String appNo) {
    	PurchaseApp purchaseApp = new PurchaseApp();
    	purchaseApp = purchaseAppService.findPurchaseAppByAppNo(appNo);
    	OutputJson2(httpServletResponse, purchaseApp);
		return null;
	}
    /**
     * 开启流程
     * @Title: startWorkflow 
     * @Description: TODO
     * @param @param purchaseApp
     * @param @return
     * @author PANCHUANHE
     * @return String
     * @date 2015年9月22日 下午5:32:10
     * @throws
     */
    @ResponseBody
    @RequestMapping(value="/startWorkflow",method=RequestMethod.POST)
    public String startWorkflow(HttpServletResponse httpServletResponse,PurchaseApp purchaseApp){
    	boolean bl = purchaseAppService.startWorkflow(purchaseApp);
    	if(bl){
    		OutputJson(httpServletResponse,new Json("提示","提交申请成功!",bl));
    	}else{
    		OutputJson(httpServletResponse,new Json("提示","提交失败,请完善物料采购申请信息!",bl));
    	}
    	return null;
    }
    /**
     * 查询所有待办任务
     * @Title: findAllPurchaseAppTaskList 
     * @Description: TODO
     * @param @param httpServletRequest
     * @param @return
     * @author PANCHUANHE
     * @return ModelAndView
     * @date 2015年9月22日 下午8:37:14
     * @throws
     */
    @ResponseBody
    @RequestMapping(value="/findAllPurchaseAppTaskList",method=RequestMethod.POST)
    public String findAllPurchaseAppTaskList(HttpServletResponse httpServletResponse,Integer page,Integer rows){
        GridModel gridModel = new GridModel();
        gridModel.setRows(purchaseAppService.findAllPurchaseAppTaskList(page,rows));
        gridModel.setTotal(purchaseAppService.countAllPurchaseAppTaskList());
        OutputJson2(httpServletResponse, gridModel);
    	return null;
    }
    /**
     * 签收任务
     * @Title: holdWorkTask 
     * @Description: TODO
     * @param @param httpServletResponse
     * @param @param taskId
     * @param @return
     * @author PANCHUANHE
     * @return String
     * @date 2015年9月23日 下午3:12:20
     * @throws
     */
    @ResponseBody
    @RequestMapping(value="/holdWorkTask",method=RequestMethod.POST)
    public String holdWorkTask(HttpServletResponse httpServletResponse,String taskId){
    	boolean bl = purchaseAppService.saveHoldWorkTask(taskId);
    	if(bl){
    		OutputJson(httpServletResponse, new Json("提示","签收任务成功!",bl));
    	}else{
    		OutputJson(httpServletResponse, new Json("提示","出错了,签收任务失败!",bl));
    	}
    	return null;
    }
    /**
     * （分部）办理任务(通过或是驳回)
     * @Title: submitTask 
     * @Description: TODO
     * @param @param httpServletResponse
     * @param @param purchaseAppModel
     * @param @return
     * @author PANCHUANHE
     * @return String
     * @date 2015年9月24日 上午11:31:08
     * @throws
     */
    @ResponseBody
    @RequestMapping(value="/submitTask",method=RequestMethod.POST)
    public String submitTask(HttpServletResponse httpServletResponse,PurchaseAppModel purchaseAppModel,String result,AuditProcHis auditProcHis){
    	boolean bl = purchaseAppService.saveSubmitTaskBo(purchaseAppModel, result, auditProcHis);
    	if(bl){
    		OutputJson(httpServletResponse, new Json("提示","办理任务成功!",bl));
    	}else{
    		OutputJson(httpServletResponse, new Json("提示","出错了,办理任务失败!",bl));
    	}
    	return null;
    }
    /**
     * （总部）办理任务(通过或是驳回)
     * @Title: submitTask 
     * @Description: TODO
     * @param @param httpServletResponse
     * @param @param purchaseAppModel
     * @param @return
     * @author PANCHUANHE
     * @return String
     * @date 2015年9月24日 上午11:31:08
     * @throws
     */
    @ResponseBody
    @RequestMapping(value="/submitTaskHo",method=RequestMethod.POST)
    public String submitTaskHo(HttpServletResponse httpServletResponse,PurchaseAppModel purchaseAppModel,String result,AuditProcHis auditProcHis){
    	boolean bl = purchaseAppService.saveSubmitTaskHo(purchaseAppModel, result, auditProcHis);
    	if(bl){
    		OutputJson(httpServletResponse, new Json("提示","办理任务成功!",bl));
    	}else{
    		OutputJson(httpServletResponse, new Json("提示","出错了,办理任务失败!",bl));
    	}
    	return null;
    }
    /**
     * 查看流程图
     * @Title: showProcessImg 
     * @Description: TODO
     * @param @param httpServletResponse
     * @param @param paId
     * @param @return
     * @author PANCHUANHE
     * @return String
     * @date 2015年10月9日 下午3:35:52
     * @throws
     */
    @RequestMapping(value="/showProcessImg",method=RequestMethod.GET)
    public String showProcessImg(HttpServletResponse httpServletResponse,Integer paId){
    	purchaseAppService.getDiagramResourceByPaId(httpServletResponse,paId);
    	return null;
    }
}

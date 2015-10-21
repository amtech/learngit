package com.oasys.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oasys.service.AuditProcHisService;
import com.oasys.viewModel.GridModel;

@Controller
@RequestMapping("/auditProcHisController")
public class AuditProcHisController extends BaseController{
	
    @Autowired
	private AuditProcHisService auditProcHisService;
    
    /**
     * 查看历史审批意见列表
     * @Title: findAllAuditProcHisList 
     * @Description: TODO
     * @param @param httpServletResponse
     * @param @param appNo
     * @param @return
     * @author PANCHUANHE
     * @return String
     * @date 2015年10月9日 下午4:28:39
     * @throws
     */
    @ResponseBody
    @RequestMapping(value="/findAllAuditProcHisList",method=RequestMethod.POST)
    public String findAllAuditProcHisList(HttpServletResponse httpServletResponse,String appNo){
    	GridModel gridModel = new GridModel();
    	gridModel.setRows(auditProcHisService.findAllAuditProcHisList(appNo));
    	OutputJson(httpServletResponse, gridModel);
		return null;
	}
}

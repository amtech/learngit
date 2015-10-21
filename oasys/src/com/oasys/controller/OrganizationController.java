package com.oasys.controller;


import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oasys.service.OrganizationService;
import com.oasys.viewModel.TreeModel;
/**
 * 
 * @ClassName: LoginController
 * @Description: TODO
 * @Author xujianwei
 * @Version 1.0
 * @Date 2015年8月17日 下午2:24:06
 * 部门
 */
@Controller
@RequestMapping("/Organization")
public class OrganizationController extends BaseController{
	
	@Autowired
	private OrganizationService organization;
	
	
	//部门列表
	@ResponseBody
	@RequestMapping(value="/organizationList",method=RequestMethod.POST)
	public String findMenuList(HttpServletResponse httpServletResponse){
		
		List<TreeModel> organizationList = organization.findOrganizationList();
		OutputJson(httpServletResponse,organizationList);
		return null;
	}
	
}



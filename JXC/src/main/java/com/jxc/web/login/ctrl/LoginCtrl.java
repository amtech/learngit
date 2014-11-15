package com.jxc.web.login.ctrl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.druid.util.StringUtils;
import com.jxc.core.exception.ActionException;
import com.jxc.core.util.Tracer;
import com.jxc.core.util.encrypt.MD5;
import com.jxc.web.login.service.LoginService;
import com.jxc.web.user.model.CounterEntity;
import com.jxc.web.user.model.UserEntity;
import com.jxc.web.user.service.UserService;

/**
 * @FileName: LoginCtrl.java
 * @Author: WuF
 * @Version: 1.0
 * @Date: 2013-2-25上午8:56:01
 * @Description: 登录用action
 * @Others:
 * @FuntionList:
 * @History:
 * @Category:
 */
@Controller
public class LoginCtrl implements Serializable {

	private static final long serialVersionUID = 7455084105185303545L;

	@Autowired
	private LoginService loginService;

	@Autowired
	private UserService userService;

	@RequestMapping("/regist")
	public String regist() {
		return "regist";
	}

	@RequestMapping(value = "/regist", method = RequestMethod.POST)
	public String regists(UserEntity users) {
		System.out.println(users);
		users.setId(UUID.randomUUID().toString());
		userService.saveUser(users);
		return "";
	}

	@RequestMapping("/")
	public String Welcome(HttpServletResponse response) throws ActionException {
		return "welcome";
	}

	@RequestMapping("/login")
	public String validLogin(Model model, HttpServletResponse response,
			String username, HttpServletRequest request, String password)
			throws ActionException {
		try {
			MD5 md5 = new MD5();
			boolean loginSituation = false;
			if (!StringUtils.isEmpty(username)
					&& !StringUtils.isEmpty(password)) {
				loginSituation = loginService.validLogin(username,
						md5.getMD5ofStr(password));
			}

			if (!loginSituation) {
				model.addAttribute("message", "1");
				return "welcome";
			}
			Tracer.trace(username);
		} catch (Exception e) {
			throw new ActionException("登录失败", e);
		}
		List<UserEntity> list = userService.saveUserInfo(username);
		HttpSession session = request.getSession();
		String id = "";
		for (UserEntity ue : list) {
			model.addAttribute("gradetype", ue.getGradetype());
			model.addAttribute("id", ue.getId());
			session.setAttribute("id", ue.getId());
			session.setAttribute("IP", getCPUKey());
			session.setAttribute("realname", ue.getName());
			session.setAttribute("username", username);
			System.out.println(ue.getGradetype());
			session.setAttribute("gradetype", ue.getGradetype());
			id = ue.getId();
		}
		List<CounterEntity> counters = userService.queryCountry(id);
		if(counters.size()>0)
		session.setAttribute("counters", counters.get(0));
		model.addAttribute("userinfo", list);
		model.addAttribute("message", "");

		ServletContext application = request.getServletContext();
		Map<String, Object> map = (Map) application.getAttribute("userMap");
		if (null == map) {
			map = new HashMap<String, Object>();
		}

//		if (map.containsKey(list.get(0).getUsername())) {
//			HttpSession sessionTemp = (HttpSession) map.get(list.get(0)
//					.getUsername());
//			// sessionTemp.setAttribute("id", session.getAttribute("id"));
//
//			System.out.println(sessionTemp.getAttribute("IP"));
//			System.out.println(session.getAttribute("IP"));
//
//			if (!sessionTemp.getAttribute("IP").equals(
//					session.getAttribute("IP"))) {
//				map.remove(list.get(0).getUsername());
//				map.put(list.get(0).getUsername(), session);
//				application.setAttribute("userMap", map);
//				sessionTemp.invalidate();
//			}
//		} else {
//			map.put(list.get(0).getUsername(), session);
//			application.setAttribute("userMap", map);
//		}

		return "view";
	}

	public static String getCPUKey() {
		long start = System.currentTimeMillis();
		Process process;
		String serial = "";
		try {
			process = Runtime.getRuntime().exec(
					new String[] { "wmic", "cpu", "get", "ProcessorId" });

			process.getOutputStream().close();
			Scanner sc = new Scanner(process.getInputStream());
			String property = sc.next();
			serial = sc.next();
			System.out.println(property + ": " + serial);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return serial;
	}


	@RequestMapping("/index")
	public String index() {
		return "hello";
	}

	public LoginService getLoginService() {
		return loginService;
	}

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}

}

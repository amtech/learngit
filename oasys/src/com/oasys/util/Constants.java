package com.oasys.util;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.oasys.shiro.ShiroUser;

/**
 * 
 * 系统常用的常量
 * 
 * @author 刘洪虎 2015/05/07.
 * 
 * @version V1.00.
 * 
 *          更新履历： V1.00 2015/05/07 刘洪虎 创建.
 */
public class Constants {

	/** session中的用户的信息. */
	public static final String LOGIN_SESSION_DATANAME = "users";

	/** 登录用户的地址. */
	public static final String LOGIN_URL = "login";

	/** 成功页面. */
	public static final String LOGIN_SUCCESS_URL = "index";

	/** 退出登录的地址. */
	public static final String LOGIN_LOGIN_OUT_URL = "loginout";

	/** 登录信息提示的消息的key. */
	public static final String LOGIN_MSG = "loginMsg";

	/** 登录时用户名为空的时候,提示信息. */
	public static final String USERNAME_IS_NULL = "用户名为空!";

	/** 用户已经登录的提示信息. */
	public static final String LOGIN_IS_EXIST = "该用户已登录!";

	/** 异常回话的提示消息. */
	public static final String UNKNOWN_SESSION_EXCEPTION = "异常会话!";

	/** 账号错的提示消息. */
	public static final String UNKNOWN_ACCOUNT_EXCEPTION = "账号错误!";

	/** 登录时候,密码错误. */
	public static final String INCORRECT_CREDENTIALS_EXCEPTION = "密码错误!";

	/** 登录时候,账号被锁定. */
	public static final String LOCKED_ACCOUNT_EXCEPTION = "账号已被锁定，请与系统管理员联系!";

	/** 登录的时候,验证码输入错误的提示信息. */
	public static final String INCORRECT_CAPTCHA_EXCEPTION = "验证码错误!";

	/** 　没有授权的提示信息． */
	public static final String AUTHENTICATION_EXCEPTION = "您没有授权!";

	/** 出现未知情况的时候,提示的信息. */
	public static final String UNKNOWN_EXCEPTION = "出现未知异常,请与系统管理员联系!";

	/** 增加树节点的状态标示. */
	public static final String TREE_GRID_ADD_STATUS = "add";

	/** 更新数据成功的标示. */
	public static final String POST_DATA_SUCCESS = "数据更新成功!";

	/** 提交数据失败的标示. */
	public static final String POST_DATA_FAIL = "提交失败了!";

	/** 模糊查询的拼接符. */
	public static final String GET_SQL_LIKE = "%";

	/** 是不是菜单类型的权限 */
	public static final String IS_FUNCTION = "F";

	/** 显示数据的标示. */
	public static final String PERSISTENCE_STATUS = "A";

	/** 逻辑删除的标示. */
	public static final String PERSISTENCE_DELETE_STATUS = "I";

	/** 系统admin. */
	public static final String SYSTEM_ADMINISTRATOR = "admin";

	/** 返回response的头部的信息文本. */
	public static final String TEXT_TYPE_PLAIN = "text/plain";

	/** 返回response的头部的信息页面. */
	public static final String TEXT_TYPE_HTML = "text/html";

	/** 是不是操作类型的权限(增删改). */
	public static final String FUNCTION_TYPE_O = "O";

	/** 展开状态. */
	public static final String TREE_STATUS_OPEN = "open";

	/** 折叠状态. */
	public static final String TREE_STATUS_CLOSED = "closed";

	/** 是否含有子菜单的提示信息. */
	public static final String IS_EXT_SUBMENU = " 或可能包含菜单!";

	/** 当用户的key. */
	public static final String SHIRO_USER = "shiroUser";

	/** sql文件的后缀名称. */
	public static final String FILE_SUFFIX_SQL = ".sql";

	/** 压缩包的后缀. */
	public static final String FILE_SUFFIX_ZIP = ".zip";

	
	/** 流程启动者流程变量key */
	public static final String CURRENT_USER_KEY = "curUserId";
	
	/** 数据源切换常量 */
	public static final String DATASOURCE_OA="dataSourceOA";
	public static final String DATASOURCE_QQMS="dataSourceQQms";
	
	/** 工牌申请常量 */
	public static final String BADGEAPP="BadgeApp";
	/**工牌申请撤销表示*/
	public static final String BADGEAPP_RE_KEY ="RevokeApply";
	
	/**用章申请*/
	public static  final String USESTAMPAPP="UseStampApp";
	/**用章谁去哪个撤销提示*/
	public static final String USESTAMPAPP_RE_KEY="ShenQingCheXiao";
	
	/** 固定资产报废申请单-总部 常量 */
	public static final String PPESCRAPAPP_HO="PPEScrapAppHo";
	public static final String PPESCRAPAPP_HO_RES="OA_AD_PPEScrapAPP_HO";
	/** 固定资产报废申请单 撤销申请标识KEY */
	public static final String PPESCRAPAPP_APPLY_KEY="ApplyForAdjustmentSubmit";
	/** 固定资产报废申请单-分部 常量 */
	public static final String PPESCRAPAPP_BO="PPEScrapAppBo";
	public static final String PPESCRAPAPP_BO_RES="OA_AD_PPEScrapAPP_BO";
	
	/** 印章移交申请单 常量 */
	public static final String STAMPTURNOVERAPP="StampTurnoverApp";
	public static final String STAMPTURNOVERAPP_RES="OA_AD_StampTurnoverApp";
	/** 印章移交废申请单 撤销申请标识KEY */
	public static final String STAMPTURNOVERAPP_APPLY_KEY="ApplyForAdjustmentSubmit";
	
	/** 财务端 组织机构标识 */
	public static final String ORG_MYID_CF = "CF";
	/** 借款端 组织机构标识 */
	public static final String ORG_MYID_JK = "JK";
	
	/** 跳转办理任务的Map存储常量 */
	public static final Map<String, String> getTaskLocationMap() {
		Map<String,String> TASK_LOCATION_MAP = new HashMap<String, String>();
		TASK_LOCATION_MAP.put(PPESCRAPAPP_HO, "jsp/ad/ppeScrap/ppeScrapHoTask.jsp");//总部固定资产报废申请
		TASK_LOCATION_MAP.put(PPESCRAPAPP_BO, "jsp/ad/ppeScrap/ppeScrapBoTask.jsp");//分部固定资产报废申请
		TASK_LOCATION_MAP.put(STAMPTURNOVERAPP, "jsp/ad/stampTurnover/stampTurnoverTask.jsp");//分部固定资产报废申请
		TASK_LOCATION_MAP.put(PURCHASEAPP_BO, "jsp/ad/purchaseApp/waitWorkTaskMain.jsp");//分部物料采购
		TASK_LOCATION_MAP.put(PURCHASEAPP_HO, "jsp/ad/purchaseApp/waitWorkTaskMain.jsp");//总部物料采购
		TASK_LOCATION_MAP.put(PPETURNOVERAPP_HO, "jsp/ad/ppeTurnover/taskUnClaimMain.jsp");//总部固定资产移交申请
		TASK_LOCATION_MAP.put(PPETURNOVERAPP_BO, "jsp/ad/ppeTurnover/taskUnClaimMain.jsp");//分部固定资产移交申请
		TASK_LOCATION_MAP.put(BADGEAPP, "jsp/ad/badgeApply/badgeTaskClaimMain.jsp");//工牌申请
		TASK_LOCATION_MAP.put(CALLINGCARD_HO, "jsp/ad/callingCard/callingCardTaskMain.jsp");//总部名片申请
		TASK_LOCATION_MAP.put(CALLINGCARD_BO, "jsp/ad/callingCard/callingCardTaskMain.jsp");//分部名片申请
		TASK_LOCATION_MAP.put(EXPENSESAPP_BO, "jsp/ad/expensesApp/expensesAppTaskMain.jsp");//分部费用申请
		TASK_LOCATION_MAP.put(EXPENSESAPP_HO, "jsp/ad/expensesApp/expensesAppTaskMain.jsp");//总部费用申请
		TASK_LOCATION_MAP.put(USESTAMPAPP,"jsp/pd/UseStampApp/useStampTaskClaimMain.jsp");//用章申请
		TASK_LOCATION_MAP.put(ConsumablesApp_BO, "jsp/ad/consumablesApp/task/consumablesAppTask.jsp");//分部低值易耗品申请
		TASK_LOCATION_MAP.put(ConsumablesApp_HO, "jsp/ad/consumablesApp/task/consumablesAppTask.jsp");//总部低值易耗品申请

		return TASK_LOCATION_MAP;
	}

	
	public static final Map<String, String> getTaskRoleCodeMap() {
		Map<String,String> TSAK_ROLE_CODE_MAP = new HashMap<String, String>();
		/** <<<<<<<<<<<<<<<<总部、分部固定资产报废申请TASK对应角色存储常量 */
		TSAK_ROLE_CODE_MAP.put("DepartmentHeads", AD_ROLE_CODE_DIVISIONDIRECTOR);//部门负责人
		TSAK_ROLE_CODE_MAP.put("ChiefCommissioner", AD_ROLE_CODE_SPECIALIST);//行政专员
		TSAK_ROLE_CODE_MAP.put("CorporateFinance", AD_ROLE_CODE_HEADFINANCIAL);//财务主管
		TSAK_ROLE_CODE_MAP.put("ExecutiveManager", AD_ROLE_CODE_MGR);//行政经理
		TSAK_ROLE_CODE_MAP.put("ChiefExecutiveOfficer", AD_ROLE_CODE_CHIEF);//行政总监
		TSAK_ROLE_CODE_MAP.put("GeneralManager", OA_ROLE_CODE_CEO);//总经理
		TSAK_ROLE_CODE_MAP.put("FinanceManager", AD_ROLE_CODE_FINANCIALMGR);//财务经理
		TSAK_ROLE_CODE_MAP.put("BusinessManager", AD_ROLE_CODE_YWJINGLI);//业务部经理
		TSAK_ROLE_CODE_MAP.put("ChiefAssistantButt", AD_ROLE_CODE_ASSISTANTDOCKING);//行政对接助理
		TSAK_ROLE_CODE_MAP.put("PresidentAssistant", AD_ROLE_CODE_ASSISTANT_PRESIDENT);//总裁助理
		TSAK_ROLE_CODE_MAP.put("ChiefDirector", AD_ROLE_CODE_CHIEF);//行政总监
		TSAK_ROLE_CODE_MAP.put("FinancialDirector", AD_ROLE_CODE_FINANCIALDIRECTOR);//财务总监
		/** 总部、分部固定资产报废申请TASK对应角色存储常量 <<<<<<<<<<<<<<<< */
		/** <<<<<<印章移交申请 TASK对应角色存储变量 */
		TSAK_ROLE_CODE_MAP.put("Recipient", AD_ROLE_CODE_RECIPIENT);//接收人
		/** >>>>>>印章移交申请 TASK对应角色存储变量 */
		
		
		TSAK_ROLE_CODE_MAP.put("YingYeBuJingLi",AD_ROLE_CODE_BUSINESSMGR);//营业部经理
		TSAK_ROLE_CODE_MAP.put("XingZhengDuiJieZhuLi",AD_ROLE_CODE_ASSISTANTDOCKING);//行政对接助理
		TSAK_ROLE_CODE_MAP.put("ZongCaiZhuLi",AD_ROLE_CODE_ASSISTANT_PRESIDENT);//总裁助理
		TSAK_ROLE_CODE_MAP.put("XingZhengJingLi",AD_ROLE_CODE_MGR);//行政经理
		TSAK_ROLE_CODE_MAP.put("XingZhengZongJian",AD_ROLE_CODE_CHIEF);//行政总监
		TSAK_ROLE_CODE_MAP.put("ZongJingLi",OA_ROLE_CODE_CEO);//总经理
		TSAK_ROLE_CODE_MAP.put("CaiWuJingLi",AD_ROLE_CODE_FINANCIALMGR);//财务经理
		TSAK_ROLE_CODE_MAP.put("JieKuanZongJian",AD_ROLE_CODE_BORROWDIRECTOR);//借款总监
		TSAK_ROLE_CODE_MAP.put("XingZhengQianTai",AD_ROLE_CODE_RECEPTIONIST);//行政前台

		TSAK_ROLE_CODE_MAP.put("JIESHOUREN", AD_ROLE_CODE_RECIPIENT);//接收人
		TSAK_ROLE_CODE_MAP.put("CaiWuZongJian", AD_ROLE_CODE_FINANCIALDIRECTOR);//财务总监
		TSAK_ROLE_CODE_MAP.put("XingZhengZhuanYuan", AD_ROLE_CODE_SPECIALIST);//行政专员
		TSAK_ROLE_CODE_MAP.put("BuMenFuZeRen", AD_ROLE_CODE_DIVISIONDIRECTOR);//部门负责人
		TSAK_ROLE_CODE_MAP.put("QiXuanZhuanYuan", AD_ROLE_CODE_QXCOMMISSIONER);//企宣专员
		TSAK_ROLE_CODE_MAP.put("HuiJi", AD_ROLE_CODE_ACCOUNTANT);//会计
		TSAK_ROLE_CODE_MAP.put("ChuNa", AD_ROLE_CODE_CASHIER);//出纳
		TSAK_ROLE_CODE_MAP.put("XingZhengPiaoJuHeShi", AD_ROLE_CODE_ADINSTRUVERIFY);//行政票据审核
		TSAK_ROLE_CODE_MAP.put("ChengShiJingLi", AD_ROLE_CODE_CITYMANAGER);//城市经理
		TSAK_ROLE_CODE_MAP.put("BuMenZhuGuan", OA_ROLE_CODE_DEPARTMENTHEADS);//部门主管
		return TSAK_ROLE_CODE_MAP;
	}
	
	
	/**=====OA有关角色-行政======*/
	//总经理
	public static final String OA_ROLE_CODE_CEO = "ZongJingLi";
	//部门主管
	public static final String OA_ROLE_CODE_DEPARTMENTHEADS = "BuMenZhuGuan";
	//行政前台
	public static final String AD_ROLE_CODE_RECEPTIONIST = "XingZhengQianTai";
	//营业部经理
	public static final String AD_ROLE_CODE_BUSINESSMGR = "YingYeBuJingLi";
	//城市经理
	public static final String AD_ROLE_CODE_CITYMANAGER = "ChengShiJingLi";
	//行政对接助理
	public static final String AD_ROLE_CODE_ASSISTANTDOCKING = "XingZhengDuiJieZhuLi";
	//行政经理
	public static final String AD_ROLE_CODE_MGR = "XingZhengJingLi";
	//行政专员
	public static final String AD_ROLE_CODE_SPECIALIST = "XingZhengZhuanYuan";
	//总裁助理
	public static final String AD_ROLE_CODE_ASSISTANT_PRESIDENT = "ZongCaiZhuLi";
	//行政总监
	public static final String AD_ROLE_CODE_CHIEF = "XingZhengZongJian";
	//借款总监
	public static final String AD_ROLE_CODE_BORROWDIRECTOR = "JieKuanZongJian";
	//部门负责人
	public static final String AD_ROLE_CODE_DIVISIONDIRECTOR = "BuMenFuZeRen";
	//企宣专员
	public static final String AD_ROLE_CODE_QXCOMMISSIONER = "QiXuanZhuanYuan";
	//财务经理
	public static final String AD_ROLE_CODE_FINANCIALMGR = "CaiWuJingLi";
	//财务主管
	public static final String AD_ROLE_CODE_HEADFINANCIAL = "CaiWuZhuGuan";
	//接收人
	public static final String AD_ROLE_CODE_RECIPIENT="JIESHOUREN";
	//业务部经理
	public static final String AD_ROLE_CODE_YWJINGLI="YeWuBuJingLi";
	//出纳
	public static final String AD_ROLE_CODE_CASHIER="ChuNa";
	//行政票据核实
	public static final String AD_ROLE_CODE_ADINSTRUVERIFY="XingZhengPiaoJuHeShi";
	//财务总监
	public static final String AD_ROLE_CODE_FINANCIALDIRECTOR="CaiWuZongJian";
	//会计
	public static final String AD_ROLE_CODE_ACCOUNTANT="HuiJi";
	
	
	/** 物料采购申请-分部  常量 */ 
	public static final String PURCHASEAPP_BO="purchaseAppBO";
	
	/** 物料采购申请-分部  常量 */ 
	public static final String PURCHASEAPP_HO="purchaseAppHO";
	
	/** 名片申请-总部-常量*/
	public static final String CALLINGCARD_HO="CallingCard";
	/** 名片申请-分部-常量*/
	public static final String CALLINGCARD_BO="CallingCardBO";
	
	/** 固定资产移交申请-总部-常量*/
	public static final String PPETURNOVERAPP_HO="PpeTurnoverAppHO";
	
	/** 固定资产移交申请-分部-常量*/
	public static final String PPETURNOVERAPP_BO="PpeTurnoverAppBO";
	
	/** 低值易耗品申请-分部-常量*/
	public static final String ConsumablesApp_BO="ConsumablesAppBO";
	public static final String ConsumablesApp_BO_RES="OA_AD_ConsumablesApp_BO";
	
	/** 低值易耗品申请-总部-常量*/
	public static final String ConsumablesApp_HO="ConsumablesAppHO";
	public static final String ConsumablesApp_HO_RES="OA_AD_ConsumablesApp_HO";
	
	/***费用申请-分部***/
	public static final String EXPENSESAPP_BO = "expensesAppBO";
	/***费用申请-总部***/
	public static final String EXPENSESAPP_HO = "expensesAppHO";
	
	/**
	 * 生成UUID主键
	 * 
	 * @return 主键
	 */
	public static String getPrimaryKeyByUUID() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 获取当前登录的用户的信息
	 * 
	 * @return 当前登录的用户的信息
	 */
	public static ShiroUser getCurrendUser() {
		Subject subject = SecurityUtils.getSubject();
		return (ShiroUser) subject.getSession().getAttribute(SHIRO_USER);
	}

	/**
	 * 私有的构造方法,工具类不支持实例化操作
	 */
	private Constants() {

	}
}

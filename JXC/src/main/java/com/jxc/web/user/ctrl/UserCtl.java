package com.jxc.web.user.ctrl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import com.jxc.core.exception.ServiceException;
import com.jxc.core.util.JfreeChart;
import com.jxc.core.util.encrypt.MD5;
import com.jxc.web.login.service.LoginService;
import com.jxc.web.user.model.CounterEntity;
import com.jxc.web.user.model.UserEntity;
import com.jxc.web.user.service.CounterService;
import com.jxc.web.user.service.OrderService;
import com.jxc.web.user.service.UserService;
/**
  @File:           UserCtl.java
  @Author:         WuF
  @Version:        v 1.0
  @Date:           2014年7月24日下午8:57:43
  @Description:	菜单Controller
 */
@Controller
@RequestMapping("user")
public class UserCtl {
	@Autowired
	private UserService userService;
	@Autowired
	private LoginService loginService;
	@Autowired
	private CounterService counterservice;
	@Autowired
	private OrderService orderservice;
	private JfreeChart jfc = new JfreeChart();
	/*@RequestMapping("/regist")
	public String regist(){
		return "regist";
	}
	
	@RequestMapping(value="/regist",method=RequestMethod.POST)
	public String regists(UserEntity users){
		System.out.println(users);
		userService.saveUser(users);
		return "login";
	}*/
/*	public static void main(String[] args) {
		System.out.println(path);
		File file =new File(path);
		System.out.println(file.exists());
	}*/
	
	/**
	 * 列表初始页
	 * @return
	 */
	@RequestMapping("list")
	public String queryUser(){
		return "user/user_list";
	}
	
	/**
	 * 获取列表数据方法
	 * @return
	 */
	@RequestMapping("getList")
	public @ResponseBody String getUser(){
		List<UserEntity> list = new ArrayList<UserEntity>();
		list = userService.queryUser();
		String user = JSON.toJSONString(list);
		return user;
	}
	@RequestMapping(value="changepassword",method=RequestMethod.POST)
	public @ResponseBody String changepassword(String username,String password,String newpassword){
		MD5 md5 = new MD5();
		try {
			boolean result = loginService.validLogin(username,
					md5.getMD5ofStr(password));
			if(result){
				userService.updateUser(username,md5.getMD5ofStr(newpassword));
				return "1";
			}else{
				return "0";
			}
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "0";
	}
	
	@RequestMapping(value="queryIsUserNameExist",method=RequestMethod.POST)
	public @ResponseBody String queryIsUserNameExist(String username){
		int i = userService.queryIsUserNameExist(username);
		if(i>0){
			return "0";
		}else{
			return "1";
		}
	}
	
	
	@RequestMapping(value="add")
	public String userAdd(Model model){
		List<CounterEntity> listeounter = counterservice.getList();
		
		model.addAttribute("counterlist",listeounter);
		return "user/user_add";
	}
	
	@RequestMapping(value="add",method=RequestMethod.POST)
	public @ResponseBody String userAddPost(UserEntity user,String[] counterid){
		//System.out.println(Arrays.toString(counterid));
		if(user.getBirth()==""){
			user.setBirth(null);
		}
		if(user.getEntrydate()==""){
			user.setEntrydate(null);
		}
		MD5 md5 = new MD5();
		user.setPassword(md5.getMD5ofStr(user.getPassword()));
		if("0001".equals(user.getGradetype())){
			Map map = new HashMap();
			map.put("countercode", user.getCounterid());
			List list = counterservice.getHeadId(map);
			String headid = list.get(0).toString();
			user.setHeadid(headid);
		}
		user.setId(UUID.randomUUID().toString().replace("-", ""));
		user.setCounterid(counterid[0]);
		userService.saveUser(user);
		for(int i = 0 ; i < counterid.length ; i++){
			user.setCounterid(counterid[i]);
			userService.saveUserCounter(user);
		}
		return "1";
	}
	
	@RequestMapping(value="discountday")
	public String userdiscountday(Model model){
		List<Map> list = userService.discountday();
		//System.out.println(list);
		model.addAttribute("list",list);
		return "user/user_discountday";
	}
	
	@RequestMapping(value="discountday",method=RequestMethod.POST)
	public @ResponseBody String updateDiscountday(String[] desc,String[] value){
		/*System.out.println(Arrays.toString(desc));
		System.out.println(Arrays.toString(value));*/
		Map map = new HashMap();
		//map.put("discountday", discountday);
		for(int i = 0;i<desc.length;i++){
			map.put("discountday", desc[i]);
			map.put("value", value[i]);
			userService.changediscountday(map);
			map.clear();
		}
		return "1";
	}
	
	@RequestMapping(value="managephoto")
	public String gotoManagephoto(){
		return "user/user_managephoto";
	}
	
/*	@RequestMapping(value="managephoto",method=RequestMethod.POST)
	public String gotoManagephoto(String begindate,String enddate,String countercode) throws ParseException{
		List<Map> countrylist = orderservice.getCountryVlueAndText();
		List<Integer> ordernums = new ArrayList<Integer>();
		List inandout = new ArrayList();
		DefaultPieDataset dataset = new DefaultPieDataset();//饼图数据
		DefaultCategoryDataset linechart = new DefaultCategoryDataset();//折线图数据
		DefaultCategoryDataset columnchart = new DefaultCategoryDataset();//柱状图数据
		Map map = new HashMap();
		map.put("begindate", begindate);
		map.put("enddate", enddate);
		String[] countrycode = null;
		//System.out.println(countrylist);
		for(Map cl:countrylist){
			String itemvalue = (String) cl.get("ITEM_VALUE");
			String itemtext = (String) cl.get("ITEM_TEXT");
			map.put("d_country", itemvalue);
			int count = orderservice.queryCountIntimeBc(map);
			//System.out.println(itemtext+":"+count);
			dataset.setValue(itemtext, count);//创建3D饼图：订单量
		}
		//记录每天多少条记录的线性图
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date bdate = sdf.parse(begindate);
		Date edate = sdf.parse(enddate);
		map.clear();
		map.put("modify_date", begindate);
		int orderdaynumsbegin = orderservice.queryDayOrderNums(map);//开始日期
		Map listsbegin  =  orderservice.queryDayInOut(map);//开始日期
		BigDecimal yushou = new BigDecimal(0);
		BigDecimal youhui = new BigDecimal(0);
		if(null!=listsbegin){
			yushou = (BigDecimal) listsbegin.get("yushou");
			//BigDecimal tuizu = (BigDecimal) listsbegin.get("tuizu");
			youhui = (BigDecimal) listsbegin.get("youhui");
		}
		columnchart.addValue(yushou, "收入", begindate);
		columnchart.addValue(youhui, "支出", begindate);
		//BigDecimal zhichu = tuizu.add(youhui);
		ordernums.add(orderdaynumsbegin);
		inandout.add(listsbegin);
		linechart.addValue(orderdaynumsbegin, "", begindate);
		map.clear();
		while(bdate.before(edate)){
			//System.out.println(sdf.format(bdate));//1-5
			calendar.setTime(bdate);
			calendar.set(Calendar.DATE, calendar.get(Calendar.DATE)+1);
			bdate = calendar.getTime();
			map.put("modify_date", sdf.format(bdate).replace("/", "-"));
			int orderdaynums = orderservice.queryDayOrderNums(map);
			Map lists  =  orderservice.queryDayInOut(map);//2-6号
			if(lists!=null){
				yushou = (BigDecimal) lists.get("yushou");
				//tuizu = (BigDecimal) lists.get("tuizu");
				youhui = (BigDecimal) lists.get("youhui");
				//zhichu = tuizu.add(youhui);
			}
			ordernums.add(orderdaynums);
			inandout.add(lists);
			System.out.println("linechart--------------"+orderdaynums+":"+sdf.format(bdate));
			System.out.println("columnchart--------------"+yushou+":"+sdf.format(bdate));
			linechart.addValue(orderdaynums, "", sdf.format(bdate));
			columnchart.addValue(yushou, "收入", sdf.format(bdate));
			columnchart.addValue(youhui, "支出", sdf.format(bdate));
			//System.out.println(bdate+":"+orderdaynums+":"+lists);
		}
		map.clear();
		map.put("modify_date", enddate);
		int orderdaynumsend = orderservice.queryDayOrderNums(map);//开始日期
		List<Map> listsend  =  orderservice.queryDayInOut(map);//开始日期
		ordernums.add(orderdaynumsend);
		inandout.add(listsend);
 		System.out.println(ordernums);
 		System.out.println(inandout);
		
		jfc.createpieChart3D(dataset);
		jfc.createLineChart(linechart);
		jfc.createColumnChart3D(columnchart);
		return "user/user_managephoto";
	}*/
	
	//饼图数据
	@RequestMapping(value="managephoto",method=RequestMethod.POST)
	public @ResponseBody String gotoManagephoto(String begindate,String enddate,String countercode) throws ParseException{
		List<Map> countrylist = orderservice.getCountryVlueAndText();
		List<Integer> ordernums = new ArrayList<Integer>();
		List inandout = new ArrayList();
		List<Map> piechartdata = new ArrayList<Map>();//饼图数据
		
		List<Map> linechart = new ArrayList<Map>();//折线图数据
		Map linechardata = new HashMap();//折线图DATA
		
		List<Map> columnchart = new ArrayList<Map>();//柱状图数据
		Map columnchartdata = new HashMap();//柱状图data
		
		Map map = new HashMap();//存放根据国家，时间查询订单量的条件
		map.put("begindate", begindate);
		map.put("enddate", enddate);
		map.put("countercode", countercode);
		String[] countrycode = null;
		//循环遍历各个国家，查询订单量
		for(Map cl:countrylist){
			String itemvalue = (String) cl.get("ITEM_VALUE");
			String itemtext = (String) cl.get("ITEM_TEXT");
			map.put("d_country", itemvalue);
			int count = orderservice.queryCountIntimeBc(map);//查询到的订单量
			Map piemap = new HashMap();//存放页面显示图标需要的参数 data
			piemap.put("label",itemtext);
			piemap.put("value", count);
			piechartdata.add(piemap);//将需要的数据放入list data
		}
		Calendar calendar = Calendar.getInstance();//获取日期函数  用来计算每一天
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date bdate = sdf.parse(begindate);
		Date edate = sdf.parse(enddate);
		map.clear();//清空Map 存放 线性表需要的信息
		map.put("modify_date", begindate);
		int orderdaynumsbegin = orderservice.queryDayOrderNums(map);//查询起始日的订单量
		
		linechardata.put("label",begindate);
		linechardata.put("value",orderdaynumsbegin);
		linechart.add(linechardata);//存放第一天 折线图的 data
		
		Map listsbegin  =  orderservice.queryDayInOut(map);//查询起始日的收入支出
		BigDecimal shouru = new BigDecimal(0);//定义收入金额 根据预收
		BigDecimal zhichu = new BigDecimal(0);//定义支出金额 根据退还金额
		if(null!=listsbegin){
			shouru = (BigDecimal) listsbegin.get("yushou");
			zhichu = (BigDecimal) listsbegin.get("tuihuan");
		}
		ordernums.add(orderdaynumsbegin);
		inandout.add(listsbegin);
		map.clear();
		//循环日期  每次加一天
		while(bdate.before(edate)){
			calendar.setTime(bdate);
			calendar.set(Calendar.DATE, calendar.get(Calendar.DATE)+1);
			bdate = calendar.getTime();
			map.put("modify_date", sdf.format(bdate).replace("/", "-"));
			int orderdaynums = orderservice.queryDayOrderNums(map);//查询订单量
			
			linechardata.put("label",sdf.format(bdate));
			linechardata.put("value",orderdaynums);
			linechart.add(linechardata);//存放每一天折线图的 data
			
			Map lists  =  orderservice.queryDayInOut(map);//2-6号
			if(lists!=null){
				shouru = (BigDecimal) lists.get("yushou");
				zhichu = (BigDecimal) lists.get("tuihuan");
			}
			ordernums.add(orderdaynums);
			inandout.add(lists);
		}
		Map infomations = new HashMap();//存放饼图fasionchart 需要的数据
		Map infomap = new HashMap(); //存放饼图fasionchart 的信息内容

		infomap.put("caption","订单数量表");
		infomap.put("subCaption","按国家");
		infomap.put("startingAngle", "120");
		infomap.put("showLabels", "0");
		infomap.put("showLegend", "1" );
		infomap.put("enableMultiSlicing", "0");
		infomap.put("slicingDistance", "15");
		infomap.put("showPercentValues", "1");
		infomap.put("showPercentInTooltip", "0");
		infomap.put("plotTooltext", "Age group : $label<br>Total visit : $datavalue");
		infomap.put("theme", "fint");
		infomap.put("bgcolor","#F8F8FF");
		
		infomations.put("chart", infomap);
		infomations.put("data", piechartdata);
		
		Map lineinfomations = new HashMap();//存放折线图需要的数据
		lineinfomations.put("chart", infomap);
		lineinfomations.put("data", linechart);
		
		List infolist = new ArrayList();//存放不同表的数据
		infolist.add(infomations);
		infolist.add(lineinfomations);
		/*System.out.println(infolist);
		System.out.println(JSON.toJSONString(infolist));
		System.out.println(JSONUtils.toJSONString(infolist));JSONUtils.toJSONString(infolist)*/
		//System.out.println(JSON.toJSONString(JSON.toJSONString(infolist),SerializerFeature.DisableCircularReferenceDetect));
		return JSON.toJSONString(JSON.toJSONString(infomations));
	}
	
	/**
	 * 各国订单数量饼图页面
	 * @return
	 */
	@RequestMapping(value="numbersOfOrdersPie")
	public String gotonumbersOfOrdersPie(Model model){
		List<CounterEntity> listeounter = counterservice.getList();
		
		model.addAttribute("counterlist",listeounter);
		return "user/user_numbersOfOrdersPie";
	}
	
	/**
	 * 每天的订单数量页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="everyDayOrderNums")
	public String gotoeveryDayOrderNums(Model model){
		List<CounterEntity> listeounter = counterservice.getList();
		
		model.addAttribute("counterlist",listeounter);
		return "user/user_everyDayOrderNums";
	}
	/**
	 * 订单量折线图页面数据返回
	 * @param begindate
	 * @param enddate
	 * @param countercode
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value="everyDayOrderNums",method=RequestMethod.POST)
	public @ResponseBody String gotoeveryDayOrderNums(String begindate,String enddate,String countercode) throws ParseException{
		List<Map> countrylist = orderservice.getCountryVlueAndText();
		List<Map> linechart = new ArrayList<Map>();//折线图数据
		Map linechardata = new HashMap();//折线图DATA
		Calendar calendar = Calendar.getInstance();//获取日期函数  用来计算每一天
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date bdate = sdf.parse(begindate);
		Date edate = sdf.parse(enddate);
		Map map = new HashMap();
		map.put("modify_date", begindate);
		map.put("countercode", countercode);
		int orderdaynumsbegin = orderservice.queryDayOrderNums(map);//查询起始日的订单量
		
		linechardata.put("label",begindate);
		linechardata.put("value",orderdaynumsbegin);
		linechart.add(linechardata);//存放第一天 折线图的 data
		//循环日期  每次加一天
		while(bdate.before(edate)){
			calendar.setTime(bdate);
			calendar.set(Calendar.DATE, calendar.get(Calendar.DATE)+1);
			bdate = calendar.getTime();
			map.put("modify_date", sdf.format(bdate).replace("/", "-"));
			int orderdaynums = orderservice.queryDayOrderNums(map);//查询订单量
			linechardata = new HashMap();
			linechardata.put("label",sdf.format(bdate));
			linechardata.put("value",orderdaynums);
			linechart.add(linechardata);//存放每一天折线图的 data
			//System.out.println(linechardata);
		}
		Map infomations = new HashMap();//存放折线图的datasource
		Map infomap = new HashMap(); //存放折线图fasionchart的配置信息
		
		infomap.put("caption", "订单数量表");
		infomap.put("subCaption", "按天");
		infomap.put("xAxisName", "日期");
		infomap.put("yAxisName", "数量");
		infomap.put("lineThickness", "2");
		infomap.put("paletteColors", "#0075c2");
		infomap.put("baseFontColor", "#333333");
		infomap.put("baseFont", "Helvetica Neue,Arial");
		infomap.put("captionFontSize", "14");
		infomap.put("subcaptionFontSize", "14");
		infomap.put("subcaptionFontBold", "0");
		infomap.put("showBorder", "0");
		infomap.put("borderAlpha", "0");
		infomap.put("bgcolor","#F8F8FF");
		infomap.put("bgAlpha", "100");
		infomap.put("showShadow", "0");
		infomap.put("showXAxisLine", "1");
		infomap.put("showXAxisLine", "1");
		infomap.put("xAxisLineColor", "#999999");
		infomap.put("showAlternateHGridColor", "0");
		infomap.put("canvasBgColor", "#F8F8FF");
		infomap.put("showBorder", "0");
		infomap.put("divlineColor", "#F8F8FF");
		//infomap.put("yAxisName","Revenues (In USD)");
		infomap.put("theme","fint");
		
		infomations.put("chart", infomap);
		infomations.put("data", linechart);
		//System.out.println(linechart);
		return JSONUtils.toJSONString(infomations);
	}
	
	/**
	 * 每日收入支出柱状图页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="dayInAndOut")
	public String dayInAndOut(Model model){
		List<CounterEntity> listeounter = counterservice.getList();
		
		model.addAttribute("counterlist",listeounter);
		return "user/user_dayInAndOut";
	}
	/**
	 * 日收支图表数据返回
	 * @param model
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="dayInAndOut",method=RequestMethod.POST)
	public @ResponseBody String dayInAndOut(String begindate,String enddate,String countercode) throws ParseException{
		List<Map> countrylist = orderservice.getCountryVlueAndText();
		List<Map> columnchart = new ArrayList<Map>();//柱状图dataset数据  其中包含两条list
		
		List<Map> columnshouru = new ArrayList<Map>();//装收入的LIST
		List<Map> columnzhichu = new ArrayList<Map>();//装支出的LIST
		List<Map> columncategory = new ArrayList<Map>();//存放categoryLIST
		
		Map columndatashouru = new HashMap();//柱状图data 单条 收入
		Map columndatazhichu = new HashMap();//柱状图data 单条 支出
		Map category = new HashMap();//存放单条category
		
		Map map = new HashMap();
		Calendar calendar = Calendar.getInstance();//获取日期函数  用来计算每一天
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date bdate = sdf.parse(begindate);
		Date edate = sdf.parse(enddate);
		map.clear();//清空Map 存放 线性表需要的信息
		map.put("modify_date", begindate);
		map.put("countercode", countercode);
		
		Map listsbegin  =  orderservice.queryDayInOut(map);//查询起始日的收入支出
		BigDecimal shouru = new BigDecimal(0);//定义收入金额 根据预收
		BigDecimal zhichu = new BigDecimal(0);//定义支出金额 根据退还金额
		if(null!=listsbegin){
			shouru = (BigDecimal) listsbegin.get("yushou");
			zhichu = (BigDecimal) listsbegin.get("tuihuan");
		}
		columndatashouru.put("value", shouru);
		columndatazhichu.put("value", zhichu);
		category.put("label", begindate);
		columnshouru.add(columndatashouru);
		columnzhichu.add(columndatazhichu);
		columncategory.add(category);
		//循环日期  每次加一天
		while(bdate.before(edate)){
			map = new HashMap();
			calendar.setTime(bdate);
			calendar.set(Calendar.DATE, calendar.get(Calendar.DATE)+1);
			bdate = calendar.getTime();
			map.put("modify_date", sdf.format(bdate).replace("/", "-"));
			map.put("countercode", countercode);
			Map lists  =  orderservice.queryDayInOut(map);//2-6号
			columndatashouru = new HashMap();
			columndatazhichu = new HashMap();
			if(lists!=null){
				shouru = (BigDecimal) lists.get("yushou");
				zhichu = (BigDecimal) lists.get("tuihuan");
				columndatashouru.put("value", shouru);
				columndatazhichu.put("value", zhichu);
			}
			columnshouru.add(columndatashouru);
			columnzhichu.add(columndatazhichu);
			category = new HashMap();
			category.put("label", sdf.format(bdate));
			columncategory.add(category);//fasion需要的categories数据 ****************
		}
		
		Map catagorymap  = new HashMap();
		catagorymap.put("category", columncategory);
		List catagorylist = new ArrayList();
		catagorylist.add(catagorymap);
		
		Map shourumap = new HashMap();//fasion需要的map
		Map zhichumap = new HashMap();//fasion需要的map
		shourumap.put("seriesname","收入" );
		shourumap.put("data", columnshouru);
		zhichumap.put("seriesname", "支出");
		zhichumap.put("data", columnzhichu);
		
		List datasets = new ArrayList();//fasion需要的dataset **********
		datasets.add(shourumap);
		datasets.add(zhichumap);
		
		Map infomations = new HashMap();//存放fasionchart 需要的数据
		Map infomap = new HashMap(); //存放fasionchart 的chart内容
		
		infomap.put("caption","收支表");
		infomap.put("subCaption","按天");
		infomap.put("xAxisname","日期");
		infomap.put("yAxisName","金额");
		infomap.put("bgcolor","#F8F8FF");
		infomap.put("canvasBgColor", "#F8F8FF");
		infomap.put("showBorder", "0");
		infomap.put("divlineColor", "#F8F8FF");
		infomap.put("bgAlpha", "100");
		//infomap.put("yAxisName","Revenues (In USD)");
		infomap.put("theme","fint");
		
		
		infomations.put("chart", infomap);
		infomations.put("dataset", datasets);
		infomations.put("categories", catagorylist);
		
		return JSONUtils.toJSONString(infomations);
	}
	
	@RequestMapping(value="dayInOutProportion")
	public String dayInOutProportion(Model model){
		List<CounterEntity> listeounter = counterservice.getList();
		
		model.addAttribute("counterlist",listeounter);
		return "user/user_dayInOutProportion";
	}
	
	@RequestMapping(value="dayInOutProportions",method=RequestMethod.POST)
	public @ResponseBody String dayInOutProportion(String begindate,String enddate,String countercode) throws ParseException{
		List<Map> countrylist = orderservice.getCountryVlueAndText();
		Map map = new HashMap();
		Calendar calendar = Calendar.getInstance();//获取日期函数  用来计算每一天
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date bdate = sdf.parse(begindate);
		Date edate = sdf.parse(enddate);
		map.clear();//清空Map 存放 线性表需要的信息
		map.put("modify_date", begindate);
		map.put("countercode", countercode);
		
		Map listsbegin  =  orderservice.queryDayInOut(map);//查询起始日的收入支出
		BigDecimal shouru = new BigDecimal(0);//定义收入金额 根据预收
		BigDecimal zhichu = new BigDecimal(0);//定义支出金额 根据退还金额
		BigDecimal shouzhiproportion = new BigDecimal(0);//支出/收入
		
		if(null!=listsbegin){
			shouru = (BigDecimal) listsbegin.get("yushou");
			zhichu = (BigDecimal) listsbegin.get("tuihuan");
			if(zhichu.equals(0)){
				
			}else{
				shouzhiproportion = zhichu.divide(shouru,2,BigDecimal.ROUND_HALF_DOWN).multiply(new BigDecimal(100));
			}
		}
		
		//XML类型catagory
		String categories = "<categories><category label='"+begindate+"' />";
		StringBuffer categorie = new StringBuffer(categories);
		
		StringBuffer shourubuffer = new StringBuffer("<dataset seriesname='收入'><set value='"+shouru+"'/>");
		StringBuffer zhichubuffer = new StringBuffer("<dataset seriesname='支出' renderas='area' showvalues='0'><set value='"+zhichu+"'/>");
		StringBuffer proportion = new StringBuffer("<dataset seriesname='支出/收入 %' parentyaxis='S' renderas='line' showvalues='0'><set value='"+shouzhiproportion+"' />");
		
		//循环日期  每次加一天
		while(bdate.before(edate)){
			calendar.setTime(bdate);
			calendar.set(Calendar.DATE, calendar.get(Calendar.DATE)+1);
			bdate = calendar.getTime();
			map.put("modify_date", sdf.format(bdate).replace("/", "-"));
			map.put("countercode", countercode);
			Map lists  =  orderservice.queryDayInOut(map);//2-6号
			if(lists!=null){
				shouru = (BigDecimal) lists.get("yushou");
				zhichu = (BigDecimal) lists.get("tuihuan");
				if(zhichu.equals(0)){
					
				}else{
					shouzhiproportion = zhichu.divide(shouru,2,BigDecimal.ROUND_HALF_DOWN).multiply(new BigDecimal(100));
				}
			}
			
			shourubuffer.append("<set value='"+shouru+"'/>");
			zhichubuffer.append("<set value='"+zhichu+"'/>");
			proportion.append("<set value='"+shouzhiproportion+"'/>");
			
			categories = "<category label='"+sdf.format(bdate)+"' />";
			categorie.append(categories);
		}
		categorie.append("</categories>");
		shourubuffer.append("</dataset>");
		zhichubuffer.append("</dataset>");
		proportion.append("</dataset>");
		
		//-------------------------------XML类型数据--------------------------------------
			StringBuffer chart = new StringBuffer("<chart caption='收支比例表' subcaption='按天' xaxisname='日期' pyaxisname='金额' syaxisname='比率 %' numberprefix='' snumbersuffix='%' syaxismaxvalue='50' palettecolors='#0075c2,#1aaf5d,#f2c500' basefontcolor='#333333' basefont='Helvetica Neue,Arial' captionfontsize='14' subcaptionfontsize='14' subcaptionfontbold='0' showborder='0' bgcolor='#F8F8FF' bgAlpha='100' showshadow='0' canvasbgcolor='#F8F8FF' canvasborderalpha='0' divlinealpha='100' divlinecolor='#F8F8FF' divlinethickness='1' divlinedashed='1' divlinedashlen='1' divlinegaplen='1' useplotgradientcolor='0' showplotborder='0' showxaxisline='1' xaxislinethickness='1' xaxislinecolor='#999999' showalternatehgridcolor='0' showalternatevgridcolor='0' legendbgalpha='0' legendborderalpha='0' legendshadow='0' legenditemfontsize='10' legenditemfontcolor='#666666' >");
			chart.append(categorie);
			chart.append(shourubuffer);
			chart.append(zhichubuffer);
			chart.append(proportion);
			chart.append("</chart>");
		
		//-------------------------------XML类型数据--------------------------------------
		return chart.toString();
	}
	
	@RequestMapping(value="daymanageinfo")
	public String daymanageinfo(Model model){
		Map map = new HashMap();//存放查询参数
		List<Map> countrylist = orderservice.getCountryVlueAndText();//存放国家名及编码
		List<Map> lists = new ArrayList<Map>();//存放查询数据
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date());
		BigDecimal totalin = new BigDecimal(0);
		BigDecimal totalout = new BigDecimal(0);
		
		for(Map cl:countrylist){
			Map mapinfo = new HashMap();//存放查询结果有效数据
			String itemvalue = (String) cl.get("ITEM_VALUE");//国家编码
			String itemtext = (String) cl.get("ITEM_TEXT");//国家名
			map.put("d_country", itemvalue);
			map.put("modify_time", date);
			map.put("countercode", "");
			int count = orderservice.queryOneDayOrderCount(map);
			Map moneys = orderservice.queryDayInOutByCountry(map);//该国当天的收入支出
			mapinfo.put("country", itemtext);
			mapinfo.put("nums", count);//将查询结果放入MAP 国家：订单数
			if(moneys!=null){
				BigDecimal yushou =  (BigDecimal) moneys.get("yushou");
				BigDecimal tuizu = (BigDecimal) moneys.get("tuizu");
				//BigDecimal youhui = (BigDecimal) moneys.get("youhui");
				//BigDecimal zhichu = tuizu.add(youhui);
				mapinfo.put("totalincome", yushou);
				mapinfo.put("totalpay", tuizu);
				totalin = yushou.add(totalin);
				totalout = tuizu.add(totalout);
			}else{
				mapinfo.put("totalincome", 0);
				mapinfo.put("totalpay", 0);	
			}
			lists.add(mapinfo);//将MAP放入LIST
			//System.out.println(mapinfo);
			//System.out.println(lists);
		}
		//System.out.println(lists);
		//System.out.println(lists.get(0).get("country"));
		if(null==lists.get(0).get("country") || "".equals(lists.get(0).get("country"))){
			lists = null;
		}
		if(totalin==null){
			totalin = new BigDecimal(0);
		}
		if(totalout==null){
			totalout = new BigDecimal(0);
		}
		model.addAttribute("lists",lists);
		model.addAttribute("totalin",totalin);
		model.addAttribute("totalout",totalout);
		return "user/user_daymanageinfo";
	}
	
	@RequestMapping(value="daymanageinfo",method=RequestMethod.POST)
	public @ResponseBody String dayInfo(String time){
		Map map = new HashMap();//存放查询参数
		List<Map> countrylist = orderservice.getCountryVlueAndText();//存放国家名及编码
		List<Map> lists = new ArrayList<Map>();//存放查询数据
		time = time.replace("/", "-");
		for(Map cl:countrylist){
			Map mapinfo = new HashMap();//存放查询结果有效数据
			String itemvalue = (String) cl.get("ITEM_VALUE");//国家编码
			String itemtext = (String) cl.get("ITEM_TEXT");//国家名
			map.put("d_country", itemvalue);
			map.put("modify_time", time);
			map.put("countercode", "");
			int count = orderservice.queryOneDayOrderCount(map);
			Map moneys = orderservice.queryDayInOutByCountry(map);//该国当天的收入支出
			mapinfo.put("country", itemtext);
			mapinfo.put("nums", count);//将查询结果放入MAP 国家：订单数
			if(moneys!=null){
				BigDecimal yushou =  (BigDecimal) moneys.get("yushou");
				BigDecimal tuizu = (BigDecimal) moneys.get("tuizu");
				//BigDecimal youhui = (BigDecimal) moneys.get("youhui");
				//BigDecimal zhichu = tuizu.add(youhui);
				mapinfo.put("totalincome", yushou);
				mapinfo.put("totalpay", tuizu);
				}else{
					mapinfo.put("totalincome", 0);
					mapinfo.put("totalpay", 0);	
				}
			lists.add(mapinfo);//将MAP放入LIST
		}
		//System.out.println(lists);
		//System.out.println(JSON.toJSONString(lists));
		return JSON.toJSONString(lists);
	}
	
	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}

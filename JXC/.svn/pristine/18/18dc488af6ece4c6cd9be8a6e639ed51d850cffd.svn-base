package com.jxc.web.user.ctrl;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFDataFormatter;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.jxc.web.user.model.CounterEntity;
import com.jxc.web.user.model.EquipmentEntity;
import com.jxc.web.user.model.InfoEntity;
import com.jxc.web.user.model.InfologEntity;
import com.jxc.web.user.service.CounterService;
import com.jxc.web.user.service.EquipmentService;
import com.jxc.web.user.service.InfoService;
import com.jxc.web.user.service.OrderService;

@Controller
@RequestMapping("info")
public class InfoCtl {
	@Autowired
	private InfoService infoservice;
	@Autowired
	private OrderService orderService;
	@Autowired
	private EquipmentService equipmentService;
	@Autowired
	private CounterService counterService;
	/**
	 * 默认列表页
	 * @return
	 */
	@RequestMapping(value="list")
	public String list(Model model){
		Map map = new HashMap();
		map.put("country", "");
		List<Map> country = orderService.getCountryVlueAndText();
		List operatorlist = infoservice.queryOperaterVAT(map);
	
		model.addAttribute("operatorlist",operatorlist);
		model.addAttribute("country",country);
		return "info/info_list";
	}
	/**
	 * 获取列表数据
	 * @param model
	 * @return
	 */
	@RequestMapping(value="getlist")
	public @ResponseBody String queryOrder(Model model,String page,String rows){
		//当前页  
        int intPage = Integer.parseInt((page == null || page == "0") ? "1":page);  
        //每页显示条数  
        int number = Integer.parseInt((rows == null || rows == "0") ? "10":rows);  
        //每页的开始记录  第一页为1  第二页为number +1  
        if(intPage<=0)
        	intPage = 1;
        int start = (intPage-1)*number;  
        int total = infoservice.infocount();
        Map map = new HashMap();
        map.put("start", start);
        map.put("number", number);
		List<InfoEntity> lists = new ArrayList<InfoEntity>();
		lists = infoservice.getList(map);
		for(InfoEntity info:lists){
			info.setCountry(orderService.getCountry(info.getCountry()));
			if("0001".equals(info.getIs_valid())){
				info.setIs_valid("有效");
			}else{
				info.setIs_valid("无效");
			}
			if(info.getDay_begin()!=null){
				info.setDay_begin(subDate(info.getDay_begin()));
			}
			if(info.getDay_end()!=null){
				info.setDay_end(info.getDay_end().substring(0,info.getDay_end().length()-11));
			}
			if(info.getModify_time()!=null){
				info.setModify_time(subDate(info.getModify_time()));
			}
			map.clear();
			map.put("itemvalue", info.getOperators());
			map.put("country", "");
			List list = infoservice.queryOperaterVAT(map);
			if(list.size()>0){
			Map maps = (Map) list.get(0);
			//System.out.println(maps.get("ITEM_TEXT"));
			info.setOperators((String) maps.get("ITEM_TEXT"));}
		}
		map.clear();
		map.put("rows", lists);
		map.put("total",total);
		//String ss = JSON.toJSONString(lists);
		return JSON.toJSONString(map);
	}
	
	@RequestMapping(value="add")
	public String add(Model model){
		List<Map> country = orderService.getCountryVlueAndText();
		model.addAttribute("country",country);
		return "info/info_add";
	}
	@RequestMapping(value="addinfo",method=RequestMethod.POST)
	public @ResponseBody String addP(InfoEntity info,String euipmentno){
		Map map = new HashMap();
		map.put("no", euipmentno);
		map.put("simid", info.getId());
		infoservice.addInfo(info);
		infoservice.updateEqptAfterInfo(map);
		return "1";
	}
	@RequestMapping(value="detail")
	public String gotoDetail(Model model,String id){
		InfoEntity info = infoservice.queryInfoById(id);
		info.setCountry(orderService.getCountry(info.getCountry()));
		if("0001".equals(info.getIs_valid())){
			info.setIs_valid("有效");
		}else{
			info.setIs_valid("无效");
		}
		if(info.getDay_begin()!=null){
			info.setDay_begin(subDate(info.getDay_begin()));
		}
		if(info.getDay_end()!=null){
			info.setDay_end(info.getDay_end().substring(0,info.getDay_end().length()-11));
		}
		if(info.getModify_time()!=null){
			info.setModify_time(subDate(info.getModify_time()));
		}
		Map map  = new HashMap();
		map.put("itemvalue", info.getOperators());
		map.put("country", "");
		List list = infoservice.queryOperaterVAT(map);
		if(list.size()>0){
		Map maps = (Map) list.get(0);
		//System.out.println(maps.get("ITEM_TEXT"));
		info.setOperators((String) maps.get("ITEM_TEXT"));}
		model.addAttribute("info",info);
		return "info/info_detail";
	}
	@RequestMapping(value="detaillist")
	public String detaillist(Model model,String id){
		model.addAttribute("id",id);
		return "info/info_detaillist";
	}
	
	@RequestMapping(value="getSimChildList",method=RequestMethod.POST)
	public @ResponseBody String getSimChildList(String id,String page,String rows){
		//当前页  
        int intPage = Integer.parseInt((page == null || page == "0") ? "1":page);  
        //每页显示条数  
        int number = Integer.parseInt((rows == null || rows == "0") ? "10":rows);  
        //每页的开始记录  第一页为1  第二页为number +1  
        if(intPage<=0)
        	intPage = 1;
        int start = (intPage-1)*number;  
        int total = infoservice.querySimChildInfoCount(id);
		Map map = new HashMap();
		map.put("cardid", id);
		map.put("start", start);
		map.put("number", number);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
		List<InfologEntity> list = infoservice.querySimChildInfo(map);
		//System.out.println(list);
		//System.out.println(JSON.toJSONString(list));
		for(InfologEntity ife:list){
			if(ife.getOperation_time()!=null){
				ife.setOperation_time(subDate(ife.getOperation_time()));
			}
		}
		map.clear();
		map.put("rows", list);
		map.put("total", total);
		//System.out.println(map);
		return JSON.toJSONString(map);
	}
	
	@RequestMapping(value="queryInfoWc",method=RequestMethod.POST)
	public @ResponseBody String queryInfoWc(InfoEntity ie,String page,String rows){
		//当前页  
        int intPage = Integer.parseInt((page == null || page == "0") ? "1":page);  
        //每页显示条数  
        int number = Integer.parseInt((rows == null || rows == "0") ? "10":rows);  
        //每页的开始记录  第一页为1  第二页为number +1  
        if(intPage<=0)
        	intPage = 1;
        int start = (intPage-1)*number;  
		Map map = new HashMap();
		map.put("ie", ie);
		map.put("start", start);
		map.put("number", number);
		//System.out.println("=================="+map);
        int total = infoservice.queryInfoWithConditionCounts(map);
		List<InfoEntity> list = infoservice.queryInfoWithCondition(map);
		for(InfoEntity iey:list){
			iey.setCountry(orderService.getCountry(iey.getCountry()));
			if("0001".equals(iey.getIs_valid())){
				iey.setIs_valid("有效");
			}else{
				iey.setIs_valid("无效");
			}
			if(iey.getDay_begin()!=null){
				iey.setDay_begin(subDate(iey.getDay_begin()));
			}
			if(iey.getDay_end()!=null){
				iey.setDay_end(iey.getDay_end().substring(0,iey.getDay_end().length()-11));
			}
			if(iey.getModify_time()!=null){
				iey.setModify_time(subDate(iey.getModify_time()));
			}
			Map maps  = new HashMap();
			map.put("itemvalue", iey.getOperators());
			map.put("country", "");
			List lists = infoservice.queryOperaterVAT(map);
			if(lists.size()>0){
			Map mapss = (Map) lists.get(0);
			//System.out.println(maps.get("ITEM_TEXT"));
			iey.setOperators((String) mapss.get("ITEM_TEXT"));}
		}
		map.clear();
		map.put("total", total);
		map.put("rows", list);
		//System.out.println(map);
		return JSON.toJSONString(map);
	}
	
	@RequestMapping(value="queryUnbindEqpt")
	public String queryUnBindPage(Model model){
		 List<CounterEntity> listeounter = counterService.getList();
		 model.addAttribute("counterlist",listeounter);
		
		return "info/unbindeqpt";
	}
	
	@RequestMapping(value="queryUnbindEqpt",method=RequestMethod.POST)
	public @ResponseBody String queryUnbindEqpt(String page,String rows,Model model){
		//当前页  
        int intPage = Integer.parseInt((page == null || page == "0") ? "1":page);  
        //每页显示条数  
        int number = Integer.parseInt((rows == null || rows == "0") ? "10":rows);  
        //每页的开始记录  第一页为1  第二页为number +1  
        if(intPage<=0)
        	intPage = 1;
        int start = (intPage-1)*number;  
		Map map = new HashMap();
		EquipmentEntity equipment = new EquipmentEntity();
		equipment.setSim_id("");//#{country}equipment_state}equipment_type}scrap_date},'%').stock_date},'%')rent_enddate},'%')rent_expectdate},'%')rent_begindate},'%')counterid}is_valid
		equipment.setNo("");equipment.setCounterid("");equipment.setEquipment_state("0001");equipment.setEquipment_type("0001");equipment.setScrap_date("");equipment.setStock_date("");equipment.setRent_begindate("");equipment.setRent_enddate("");equipment.setRent_expectdate("");equipment.setIs_valid("");
		map.put("start", start);
		map.put("number", number);
		map.put("equipment", equipment);
		map.put("country", "");
		List<EquipmentEntity> list = equipmentService.queryEqptByConditions(map);
		int total = equipmentService.queryEqptByConditionsNums(map);
		for(EquipmentEntity ee:list){
			ee.setIs_valid(isValid(ee.getIs_valid()));
			if(ee.getRent_begindate()!=null)
			ee.setRent_begindate(subDate(ee.getRent_begindate()));
			if(ee.getRent_enddate()!=null)
			ee.setRent_enddate(subDate(ee.getRent_enddate()));
			if(ee.getRent_expectdate()!=null)
			ee.setRent_expectdate(subDate(ee.getRent_expectdate()));
			if(ee.getScrap_date()!=null)
			ee.setScrap_date(subDate(ee.getScrap_date()));
			if(ee.getStock_date()!=null)
			ee.setStock_date(subDate(ee.getStock_date()));
			if(ee.getCreate_time()!=null)
			ee.setCreate_time(subDate(ee.getCreate_time()));
			if(ee.getModify_time()!=null)
			ee.setModify_time(subDate(ee.getModify_time()));
			ee.setCounterid(equipmentService.queryCounterName(ee.getCounterid()));
			ee.setD_country(orderService.queryDcountry(ee.getNo()));
			if("0001".equals(ee.getEquipment_type())){
				ee.setEquipment_type("内部设备");
				ee.setDeposit(equipmentService.queryDepositByEtype("内部设备"));
			}else{
				ee.setDeposit(equipmentService.queryDepositByEtype("外部设备"));
				ee.setEquipment_type("外部设备");
			}
			if("0001".equals(ee.getEquipment_state())){
				ee.setEquipment_state("可用");
			}else{
				ee.setEquipment_state("已租");
			}
			if(ee.getSim_id()!=null){
				ee.setSim_id("已绑定");
			}else{
				ee.setSim_id("未绑定");
			}
			
		} 
		map.clear();
		map.put("total", total);
		map.put("rows", list);
		//System.out.println(map);
		return JSON.toJSONString(map);
	}
	
	/**
	 * 根据设备号，或者柜台号，或者国家查询
	 * @param no 设备号
	 * @param counterid 柜台号
	 * @param countrys 国家
	 * @return
	 */
	@RequestMapping(value="query")
	public @ResponseBody String queryEquipmentByNum(String no,String countercode,String countrys,String page,String rows,String type,String equipment_state){
		List<EquipmentEntity> list = new ArrayList<EquipmentEntity>();
		//当前页  
        int intPage = Integer.parseInt((page == null || page == "0") ? "1":page);  
        //每页显示条数  
        int number = Integer.parseInt((rows == null || rows == "0") ? "10":rows);  
        //每页的开始记录  第一页为1  第二页为number +1   
        if(intPage<=0)
        	intPage = 1;
        int start = (intPage-1)*number;  
        int total = 0;
        if(StringUtils.isEmpty(no) && StringUtils.isEmpty(countercode) && StringUtils.isEmpty(countrys) && "1".equals(type)){
        	list = equipmentService.queryEquipmentByPage(start, number,countercode);
        }else{
        	Map map = new HashMap();
        	map.put("no", no.trim());
        	map.put("countercode", countercode);
        	map.put("countrys", countrys);
        	map.put("equipment_state", equipment_state);
        	map.put("start", start);
        	map.put("number", number);
			list = equipmentService.queryEquipmentByNum(map);
			total = equipmentService.queryEquipmentByNumTotal(map);
		}
		if(list.size()>0){
			for(EquipmentEntity ee:list){
				ee.setIs_valid(isValid(ee.getIs_valid()));
				ee.setCounterid(equipmentService.queryCounterName(ee.getCounterid()));
				ee.setD_country(orderService.queryDcountry(ee.getNo()));
				if(ee.getEquipment_type().equals("0001"))
					ee.setDeposit(equipmentService.queryDepositByEtype("内部设备"));
			} 
		}
		Map maps = new HashMap();
		maps.put("total", total);
		maps.put("rows", list);
		return JSON.toJSONString(maps);
	}
	
	@RequestMapping(value="queryOperatorByC")
	public @ResponseBody String queryOperatorByC(String country){
		Map map = new HashMap();
		map.put("country", country);
		List list = infoservice.queryOperaterVAT(map);
		String dayrent = infoservice.queryInfoDayRent(map);
		map.clear();
		map.put("list", list);
		map.put("dayrent", dayrent);
		return JSON.toJSONString(map);
	}
	
	@RequestMapping(value="checkIsBind",method=RequestMethod.POST)
	public @ResponseBody String checkIsBind(String id){
		Map map = new HashMap();
		map.put("id", id);
		int i = infoservice.checkIsBind(map);
		return i>0?"1":"0";
	}
	
	@RequestMapping(value="searchOfUnbindEqpt",method=RequestMethod.POST)
	public @ResponseBody String searchOfUnbindEqpt(EquipmentEntity ee ,String page,String rows){
		//当前页  
        int intPage = Integer.parseInt((page == null || page == "0") ? "1":page);  
        //每页显示条数  
        int number = Integer.parseInt((rows == null || rows == "0") ? "10":rows);  
        //每页的开始记录  第一页为1  第二页为number +1   
        if(intPage<=0)
        	intPage = 1;
        int start = (intPage-1)*number;  
        Map map = new HashMap();
        map.put("equipmentno", ee.getNo().trim());map.put("counterid", ee.getCounterid());map.put("simid", ee.getSim_id());
        map.put("start", start);map.put("number", number);
        List<EquipmentEntity> list = infoservice.searchOfUnbindEqpt(map);
        int total = infoservice.searchOfUnbindEqptCounts(map);
        for(EquipmentEntity eqpt:list){
        	if(ee.getModify_time()!=null)
    			ee.setModify_time(subDate(ee.getModify_time()));
        	eqpt.setCounterid(equipmentService.queryCounterName(eqpt.getCounterid()));
			if("0001".equals(eqpt.getEquipment_type())){
				eqpt.setEquipment_type("内部设备");
				eqpt.setDeposit(equipmentService.queryDepositByEtype("内部设备"));
			}else{
				eqpt.setDeposit(equipmentService.queryDepositByEtype("外部设备"));
				eqpt.setEquipment_type("外部设备");
			}
			if("0001".equals(eqpt.getEquipment_state())){
				eqpt.setEquipment_state("可用");
			}else{
				eqpt.setEquipment_state("已租");
			}
			if(eqpt.getSim_id()!=null){
				eqpt.setSim_id("已绑定");
			}else{
				eqpt.setSim_id("未绑定");
			}
        }
        map.clear();
        map.put("total", total);
        map.put("rows", list);
        return JSON.toJSONString(map);
	}
	
	@RequestMapping(value="checkIsExist",method=RequestMethod.POST)
	public @ResponseBody String checkIsExist(String id){
		Map map = new HashMap();
		map.put("id", id);
		int i = infoservice.checkIsExist(map);
		return i>0?"1":"0";
	}
	
	@RequestMapping(value="queryInfoDayRent",method=RequestMethod.POST)
	public @ResponseBody String queryInfoDayRent(String country){
		Map map = new HashMap();
		map.put("country", country);
		String dayrent = infoservice.queryInfoDayRent(map);
		return dayrent;
	}
	
	@RequestMapping(value="infoedit")
	public String gotoInfoedit(Model model,String id){
		InfoEntity info = infoservice.queryInfoById(id);
		info.setCountry(orderService.getCountry(info.getCountry()));
		model.addAttribute("is_valid",info.getIs_valid());
		if("0001".equals(info.getIs_valid())){
			info.setIs_valid("有效");
		}else{
			info.setIs_valid("无效");
		}
		if(info.getDay_begin()!=null){
			info.setDay_begin(subDate(info.getDay_begin()));
		}
		if(info.getDay_end()!=null){
			info.setDay_end(subDate(info.getDay_end()));
		}
		if(info.getModify_time()!=null){
			info.setModify_time(subDate(info.getModify_time()));
		}
		Map map  = new HashMap();
		map.put("itemvalue", info.getOperators());
		map.put("country", "");
		List list = infoservice.queryOperaterVAT(map);
		if(list.size()>0){
		Map maps = (Map) list.get(0);
		//System.out.println(maps.get("ITEM_TEXT"));
		info.setOperators((String) maps.get("ITEM_TEXT"));}
		model.addAttribute("info",info);
		return "info/info_edit";
	}
	
	@RequestMapping(value="editOfInfo",method=RequestMethod.POST)
	public String editOfInfo(Model model,InfoEntity ies){
		System.out.println(ies);
		Map map = new HashMap();
		map.put("card_id", ies.getId());
		map.put("operation_user", ies.getModify_user());
		map.put("operation_time", ies.getModify_time());
		map.put("day_end", ies.getDay_end());
		map.put("is_valid", ies.getIs_valid());
		map.put("remark", ies.getRemark());
		map.put("id", ies.getId());
		infoservice.editOfInfo(map);
		infoservice.recordEditInfo(map);
		model.addAttribute("status","1");
		return "info/overthrough";
	}
	
	@RequestMapping(value="xufei",method=RequestMethod.POST)
	public String xufei(Model model,HttpServletRequest request,String card_id,String ex_updatedate,String updatedate,String remark){
		/*System.out.println("================execute here========================");
		System.out.println(card_id);
		System.out.println(ex_updatedate);
		System.out.println(updatedate);*/
		try{
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("realname");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String date = sdf.format(new Date());
		String[] card  = card_id.split(",");
		String[] ex_update = ex_updatedate.split(",");
		String[] upda = updatedate.split(",");
		String[] rema = remark.split(",");
		List<InfologEntity> list = new ArrayList<InfologEntity>();
		for(int i = 0 ; i < card.length;i++){
			InfologEntity ife = new InfologEntity();
			ife.setId(UUID.randomUUID().toString().replace("-", ""));
			ife.setCard_id(card[i]);
			ife.setEx_updatedate(ex_update[i]);
			ife.setOperation_content("续费");
			ife.setUpdatedate(upda[0]);
			ife.setRemark(rema[0]);
			ife.setOperation_user(username);
			ife.setOperation_time(date);
			System.out.println(ife);
			list.add(ife);
		}
			infoservice.xufeioperation(list);
			model.addAttribute("status","2");
		}catch(Exception e){
			model.addAttribute("status","-1");
		}
		return "info/overthrough";
	}
	
	@RequestMapping(value="uploadfileForInfo",method=RequestMethod.POST)
	public String uploadfileForInfo(Model model,@RequestParam("resourcefile") CommonsMultipartFile file,HttpServletRequest request,String country,String operators,String day_rent,String day_end,String create_user,String create_time) throws IOException{
		List<InfoEntity> infolist = new ArrayList<InfoEntity>();
        if(file.getContentType().equalsIgnoreCase("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")){
            List<String> infonolist = readExcelTS(file.getInputStream());
            int j = infonolist.size();
            int i = 0 ;
            while(i<j){
            	InfoEntity ie = new InfoEntity();
            	ie.setOperators(operators);
            	ie.setId(infonolist.get(i));
            	ie.setCountry(country);
            	ie.setDay_rent(day_rent);
            	ie.setDay_end(day_end);
            	ie.setCreate_user(create_user);
            	ie.setCreate_time(create_time);
            	infolist.add(ie);
            	i++;
            }
            if(!queryIsDouble(infonolist)){
            	model.addAttribute("status","3");
				return "info/overthrough";
            }
            //先将数据添加到临时表，然后测试表中的数据
            infoservice.batchInportTemporatorySim(infonolist);
            List<String> tempinfono = infoservice.queryTemporatorySim();
            for(String id : tempinfono){
            	Map map = new HashMap();
            	map.put("id", id);
            	int l = infoservice.queryIsDoubleByTemp(map);
            	if(l>0){
            		model.addAttribute("status","3");
					return "info/overthrough";
            	}
            }
            //如果无误，将数据添加到正式表
            infoservice.batchInportSim(infolist);
            model.addAttribute("status","0");
        }else{
        	try{
        	List<String> simnos = new ArrayList<String>();//保存所有的设备号
            HSSFWorkbook wb = new HSSFWorkbook(file.getInputStream());
            HSSFDataFormatter hs = new HSSFDataFormatter();
            HSSFSheet sheet = wb.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.rowIterator();
            rowIterator.next();//第一行为标题,过滤掉
            while (rowIterator.hasNext()) {//遍历查询每一行
                Row row = rowIterator.next();//遍历查询每一列
                if(row.getCell(0) != null){
                	//System.out.println(row.getCell(0).getStringCellValue());
                	String simno = row.getCell(0).getStringCellValue();
                	simnos.add(simno);
                	InfoEntity ientity = new InfoEntity();
                	ientity.setId(simno);
                	ientity.setOperators(operators);
                	ientity.setCountry(country);
                	ientity.setDay_rent(day_rent);
                	ientity.setDay_end(day_end);
                	ientity.setCreate_user(create_user);
                	ientity.setCreate_time(create_time);
                	infolist.add(ientity);
                }
               }
            System.out.println("======================="+simnos);
            HttpSession session = request.getSession();
            session.setAttribute("infolist",infolist);
            session.setAttribute("simno", simnos);
            model.addAttribute("simnotype",1);
            model.addAttribute("simnos",simnos);
            model.addAttribute("simnosnum",simnos.size());
            //return "info/info_list";
            return "info/overthrough";
			/*if(!queryIsDouble(simnos)){
				model.addAttribute("status","3");
				return "info/overthrough";
			}*/
           /* //先将数据添加到临时表，然后测试表中的数据
            infoservice.batchInportTemporatorySim(simnos);
            System.out.println("执行完插入临时表");
            List<String> infotempno = infoservice.queryTemporatorySim();
            System.out.println("========================="+infotempno);
			for(String id : infotempno){
            	Map map = new HashMap();
            	map.put("id", id);
            	int l = infoservice.queryIsDoubleByTemp(map);
            	if(l>0){
            		model.addAttribute("status","3");
					return "info/overthrough";
            	}
            }
            //如果无误，将数据添加到正式表
			System.out.println("-=====--------------"+infolist);
            infoservice.batchInportSim(infolist);*/
            //model.addAttribute("status","0");
            //System.out.println("====================执行到这了！！！！！！");
        //return "schedule/curriculum_list";
        }catch(Exception e){
        	model.addAttribute("status","-1");
        }
       }
        return "info/overthrough";
	}
	
	@RequestMapping(value="kaika",method=RequestMethod.POST)
	public String kaika(Model model,String[] simno,HttpServletRequest request){
		List<String> simnos = new ArrayList<String>();
		HttpSession session = request.getSession();
		List<InfoEntity> infolist = (List<InfoEntity>) session.getAttribute("infolist");
		simnos = Arrays.asList(simno);
		if(!queryIsDouble(simnos)){
			model.addAttribute("status","3");
			return "info/overthrough";
		}
		 infoservice.batchInportTemporatorySim(simnos);
         System.out.println("执行完插入临时表");
         List<String> infotempno = infoservice.queryTemporatorySim();
         System.out.println("========================="+infotempno);
			for(String id : infotempno){
         	Map map = new HashMap();
         	map.put("id", id);
         	int l = infoservice.queryIsDoubleByTemp(map);
         	if(l>0){
         		model.addAttribute("status","3");
					return "info/overthrough";
         	}
         }
         //如果无误，将数据添加到正式表
		 //System.out.println("-=====--------------"+infolist);
         infoservice.batchInportSim(infolist);
         model.addAttribute("dayend",infolist.get(0).getDay_end());
         session.setAttribute("infolist", null);
         model.addAttribute("status","0");
         return "info/overthrough";
	}
	
	@RequestMapping(value="simopen",method=RequestMethod.POST)
	public String simopen(Model model,HttpServletRequest request,InfoEntity info,String[] ids){
		//System.out.println(info);
		if(ids.length>0){
			List<String> simlist = Arrays.asList(ids);
			/*System.out.println(info);
			System.out.println(simlist);*/
			for(String id:simlist){
				InfoEntity infoentity = new InfoEntity();
				infoentity.setId(id);
				infoentity.setDay_begin(info.getDay_begin());;
				infoentity.setDay_end(info.getDay_end());
				infoentity.setModify_user(info.getModify_user());
				infoentity.setModify_time(info.getModify_time());
				infoentity.setRemark(info.getRemark());
				infoservice.simcardopoen(infoentity);
			}
			model.addAttribute("status","4");
			return "info/overthrough";
		}
		HttpSession session = request.getSession();
		List<String> list = (List<String>) session.getAttribute("simno");
		for(String id:list){
			InfoEntity infoentity = new InfoEntity();
			infoentity.setId(id);
			infoentity.setDay_begin(info.getDay_begin());;
			infoentity.setDay_end(info.getDay_end());
			infoentity.setModify_user(info.getModify_user());
			infoentity.setModify_time(info.getModify_time());
			infoentity.setRemark(info.getRemark());
			infoservice.simcardopoen(infoentity);
		}
		model.addAttribute("status","4");
		session.setAttribute("simno", null);
		return "info/overthrough";
	}
	
	/**
	 * 读取EXCEL 2007 表单方法
	 */
	public List<String> readExcelTS(InputStream file){
        List<String> equipmentnolist = new ArrayList<String>();//存放所有设备号
        try{
        XSSFWorkbook xwb = new XSSFWorkbook(file);
        //读取第一章表格的内容
        XSSFSheet sheet = xwb.getSheetAt(0);
        Object value = null;
        XSSFRow row = null;
        XSSFCell cell = null;
        for(int k = 1; k<=sheet.getLastRowNum();k++){//第一行为标题，默认从第二行开始读取
            row = sheet.getRow(k);
            equipmentnolist.add(row.getCell(0).getStringCellValue());
        }
        return equipmentnolist;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
	}
	
	@RequestMapping(value="downloadModel")
	public void downloadModel(HttpServletResponse response){
		InputStream in = this.getClass().getResourceAsStream("/sim.xls");
        try {
            response.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode("SIMcard.xls", "UTF-8") + "\"");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }  
        response.setContentType("application/octet-stream;charset=UTF-8"); 
        try {
            IOUtils.copy(in, response.getOutputStream());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}
	
	//判断设备是否有效0001有效，0002无效
		public String isValid(String isValid){
			if("0001".equals(isValid)){
				return "有效";
			}else{
				return "无效";
			}
		}
	//截取二期最后一个.0
	public  static String subDate(String dates){
		return dates.substring(0,dates.length()-2);
	}
	
	//判断一个LIST中的值是否有重复
		public boolean queryIsDouble(List<String> list){
			String temp = "";
			 for (int i = 0; i < list.size() - 1; i++)
		        {
		            temp = list.get(i);
		            for (int j = i + 1; j < list.size(); j++)
		            {
		                if (temp.equals(list.get(j)))
		                {
		                    return false;
		                }
		            }
		        }
			 return true;
		}
}

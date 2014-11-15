package com.jxc.web.user.dao;

import java.util.List;
import java.util.Map;

import com.jxc.web.user.model.OrderEntity;

public interface OrderDao {
		//添加订单
		public void addOrder(OrderEntity order);
		//查询所有订单
		public List<OrderEntity> queryOrder(Map map);
		//根据设备号或者手机号查询3g订购卡
		//public List<OrderEntity> queryOrderByNum(String equipment_no,String rent_p_tel);
		public List<OrderEntity> queryOrderByNum(Map map);
		//public OrderEntity queryOrderByNum(String sqls);
		/*//根据订单中的设备号修改设备信息：设备返还日期，设备租金，备注
		public abstract void updateEquipment(String equipment_no,String rent_enddate,String cost_sum,String remark);*/
		//根据当前页和每页的条数查询order
		public List<OrderEntity> queryOrderByPage(Map map);
		//查询总共条数的方法
		public int queryOrderRows(Map map);
		//根据设备号查询目的地
		public String queryDcountry(String equipment_no);
		//更新订单
		public void updateOrder(OrderEntity oe);
		//获取支付方式及其对应编码
		public List<String> getPayment();
		//根据设备地区编码查询地区
		public String getCountry(String itemvalue);
		//根据地区名查询地区编码
		public String getCountryCode(String itemtext);
		//根据支付方式代码查询支付方式
		public String getPaymentText(String paymentvalue);
		//查询国籍及其对应的代码
		public List<String> queryNationality();
		//根据国籍代码查询国籍名称
		public String queryNationText(String nationvalue);
		//根据 手机号，设备号，租用日期，返还日期，国籍，目的地，租用人姓名，订单号查询订单
		public List<OrderEntity> queryOrderWithConditons(Map map);
		//查询地区及其代码
		public List<Map> getCountryVlueAndText();
		//根据订单号查询订单
		public OrderEntity getDetailByOrderNum(Map map);
		//根据 手机号，设备号，租用日期，返还日期，国籍，目的地，租用人姓名，订单号查询到订单的总条数
		public int queryOrderWithConditonsTotal(Map map);
		//根据舱位等级查询优惠天数
		public String queryDiscountdayByCLevel(Map map);
		//根据舱位等级查询舱位名
		public String queryPositionDesc(Map map);
		//查询最近跟新的一条订单
		public OrderEntity queryLastOrder();
		//查询起始优惠天数
		public String querysaleday();
		//按国家，日期查询该时间段内各国的订单量
		public int queryCountIntimeBc(Map map);
		//查询指定时间内每天的订单量
		public int queryDayOrderNums(Map map);
		//查询指定时间内每天的收入支出
		public Map queryDayInOut(Map map);
		//设备退还后更新SIM卡中最后租用日期
		public void updateSimLastRentDate(Map map);
		//查询某天某个国家的总收入支出
		public Map queryDayInOutByCountry(Map map);
		//
		public int queryOneDayOrderCount(Map map);
}

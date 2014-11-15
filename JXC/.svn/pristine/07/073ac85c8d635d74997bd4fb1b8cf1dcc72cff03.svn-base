package com.jxc.web.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jxc.web.user.dao.OrderDao;
import com.jxc.web.user.model.OrderEntity;

@Service
public class OrderService {
	@Autowired
	private OrderDao orderDao;
	
	/**
	 * 添加3g订购卡
	 * @param orders
	 */
	public void addOrder(OrderEntity orders){
		orderDao.addOrder(orders);
	}
	/**
	 * 查询全部3g订购卡
	 * @return
	 */
	public List<OrderEntity> queryOrder(Map map){
		return orderDao.queryOrder(map);
	}
	/**
	 * 根据设备号或者手机号查询3g订购卡
	 * @param equipment_no
	 * @param rent_p_tel
	 * @return
	 */
	/*public List<OrderEntity>  queryOrderByNum(String equipment_no,String rent_p_tel){
		return orderDao.queryOrderByNum(equipment_no, rent_p_tel);
	}*/
	
	public List<OrderEntity> queryOrderByNum(Map map){
		return orderDao.queryOrderByNum(map);
	}
	/*public OrderEntity queryOrderByNum(String sqls){
		return orderDao.queryOrderByNum(sqls);
	}*/
	/**
	 * 分页查询方法
	 * @param start 当前页第一条条数
	 * @param number 每页的条数
	 * @return
	 */
	public List<OrderEntity> queryOrderByPage(Map map){
		return orderDao.queryOrderByPage(map);
	}
	/*public void updateEquipment(String equipment_no, String rent_enddate,
			String cost_sum, String remark){
			orderDao.updateEquipment(equipment_no, rent_enddate, cost_sum, remark);
	}*/
	/**
	 * 查询总条数
	 * @return
	 */
	public int queryOrderRows(Map map){
		return orderDao.queryOrderRows(map);
	}
	/**
	 * 根据设备号查询目的地
	 * @param equipment_no
	 * @return
	 */
	public String queryDcountry(String equipment_no){
		return orderDao.queryDcountry(equipment_no);
	}
	/**
	 * 更新订单
	 * @param oe
	 */
	public void updateOrder(OrderEntity oe){
		orderDao.updateOrder(oe);
		Map map = new HashMap();
		map.put("rent_enddate", oe.getRent_enddate());
		map.put("no", oe.getEquipment_no());
		orderDao.updateSimLastRentDate(map);
	}
	/**
	 * 查询支付编码及支付方式
	 * @return
	 */
	public List<String> getPayment(){
		return orderDao.getPayment();
	}
	/**
	 * 根据地区名称查询地区编码
	 * @param itemvalue
	 * @return
	 */
	public String getCountry(String itemvalue){
		return orderDao.getCountry(itemvalue);
	}
	/**
	 * 根据地区名称查询地区编码
	 * @param itemtext
	 * @return
	 */
	public String getCountryCode(String itemtext){
		return orderDao.getCountryCode(itemtext);
	}
	/**
	 * 根据支付代码查询支付方式
	 * @param paymentvalue
	 * @return
	 */
	public String getPaymentText(String paymentvalue){
		return orderDao.getPaymentText(paymentvalue);
	}
	/**
	 * 查询所有国籍名称及其代码
	 * @return
	 */
	public List<String> queryNationality(){
		return orderDao.queryNationality();
	}
	/**
	 * 根据国籍代码查询国籍名称
	 * @param nationvalue
	 * @return
	 */
	public String queryNationText(String nationvalue){
		return orderDao.queryNationText(nationvalue);
	}
	/**
	 * 根据条件查询订单
	 * @param oe
	 * @return
	 */
	public List<OrderEntity> queryOrderWithConditons(Map map){
		return orderDao.queryOrderWithConditons(map);
	}
	/**
	 * 查询地区名称及其代码
	 * @return
	 */
	public List<Map> getCountryVlueAndText(){
		return orderDao.getCountryVlueAndText();
	}
	/**
	 * 根据订单号查询订单
	 * @param map
	 * @return
	 */
	public OrderEntity getDetailByOrderNum(Map map){
		return orderDao.getDetailByOrderNum(map);
	}
	/**
	 * 根据条件查询到订单的总条数
	 * @param map
	 * @return
	 */
	public int queryOrderWithConditonsTotal(Map map){
		return orderDao.queryOrderWithConditonsTotal(map);
	}
	/**
	 * 根据舱位等级查询优惠天数
	 * @param map
	 * @return
	 */
	public String queryDiscountdayByCLevel(Map map){
		return orderDao.queryDiscountdayByCLevel(map);
	}
	/**
	 * 根据舱位等级查询舱位名
	 * @param map
	 * @return
	 */
	public String queryPositionDesc(Map map){
		return orderDao.queryPositionDesc(map);
	}
	/**
	 * 查询最近更新的一条订单
	 * @return
	 */
	public OrderEntity queryLastOrder(){
		return orderDao.queryLastOrder();
	}
	/**
	 * 查询起始优惠天数
	 * @return
	 */
	public String querysaleday(){
		return orderDao.querysaleday();
	}
	/**
	 * 查询某国该时间段内的订单量
	 * @param map
	 * @return
	 */
	public int queryCountIntimeBc(Map map){
		return orderDao.queryCountIntimeBc(map);
	}
	/**
	 * 查询每天的订单量
	 * @param map
	 * @return
	 */
	public int queryDayOrderNums(Map map){
		return orderDao.queryDayOrderNums(map);
	}
	/**
	 * 查询每天的收入支出
	 * @param map
	 * @return
	 */
	public Map queryDayInOut(Map map){
		return orderDao.queryDayInOut(map);
	}
	/**
	 * 查询某天某国的总收入支出
	 * @param map
	 * @return
	 */
	public Map queryDayInOutByCountry(Map map){
		return orderDao.queryDayInOutByCountry(map);
	}
	/**
	 * 查询某天某国的订单量
	 * @param map 时间，国家
	 * @return
	 */
	public int queryOneDayOrderCount(Map map){
		return orderDao.queryOneDayOrderCount(map);
	}
}

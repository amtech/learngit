package com.jxc.web.user.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单表
 * @author Sun
 *
 */
public class OrderEntity implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	
	private String id; //订单ID  ：时间到毫秒
	private String useid; //业务员ID
	private String order_num; //订单编号
	private String rent_p_name; //租用人姓名
	private String rent_p_idnumber; //租用人身份证号
	private String rent_p_passportno; //租用人护照号
	private String rent_p_nationality; //租用人国籍
	private String rent_p_tel; //租用人电话号
	private String rent_begindate; //租用日期
	private String rent_expectdate; //预计返还日期
	private String rent_enddate; //返还日期
	private String equipment_no; //设备ID
	private BigDecimal cost_rent_3g; //3G卡租赁费用
	private BigDecimal cost_rent_equipment; //设备租赁费用
	private BigDecimal upfront_sum; //预收金额
	private Date upfront_date; //预收日期
	private BigDecimal cost_discount; //优惠费用
	private BigDecimal cost_return_discount; //退还押金费用
	private BigDecimal cost_sum; //实际结算金额
	private String is_valid; // 是否有效.0001有效,0002无效
	private String create_user; //创建人
	private String create_time; //创建时间
	private String modify_user; //修改人
	private String modify_time; //修改时间
	private String remark; //备注
	private int sort; //排序
	private String s_country;//出发地国家
	private String d_country;//目的地国家
	private String payment;//支付方式
	private String rent_day;//日租金
	private String order_state;//订单状态0001进行中，0002已完成
	private String counterid;//柜台ID
	private String position_level;//舱位等级
	
	public String getPosition_level() {
		return position_level;
	}


	public void setPosition_level(String position_level) {
		this.position_level = position_level;
	}


	public String getCounterid() {
		return counterid;
	}


	public void setCounterid(String counterid) {
		this.counterid = counterid;
	}


	public String getOrder_state() {
		return order_state;
	}


	public void setOrder_state(String order_state) {
		this.order_state = order_state;
	}


	public String getRent_day() {
		return rent_day;
	}


	public void setRent_day(String rent_day) {
		this.rent_day = rent_day;
	}


	public String getPayment() {
		return payment;
	}


	public void setPayment(String payment) {
		this.payment = payment;
	}


	public String getS_country() {
		return s_country;
	}


	public void setS_country(String s_country) {
		this.s_country = s_country;
	}


	public String getD_country() {
		return d_country;
	}


	public void setD_country(String d_country) {
		this.d_country = d_country;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	

	public String getUseid() {
		return useid;
	}


	public void setUseid(String useid) {
		this.useid = useid;
	}


	public String getOrder_num() {
		return order_num;
	}


	public void setOrder_num(String order_num) {
		this.order_num = order_num;
	}


	public String getRent_p_name() {
		return rent_p_name;
	}


	public void setRent_p_name(String rent_p_name) {
		this.rent_p_name = rent_p_name;
	}


	public String getRent_p_idnumber() {
		return rent_p_idnumber;
	}


	public void setRent_p_idnumber(String rent_p_idnumber) {
		this.rent_p_idnumber = rent_p_idnumber;
	}


	public String getRent_p_passportno() {
		return rent_p_passportno;
	}


	public void setRent_p_passportno(String rent_p_passportno) {
		this.rent_p_passportno = rent_p_passportno;
	}


	public String getRent_p_nationality() {
		return rent_p_nationality;
	}


	public void setRent_p_nationality(String rent_p_nationality) {
		this.rent_p_nationality = rent_p_nationality;
	}


	public String getRent_p_tel() {
		return rent_p_tel;
	}


	public void setRent_p_tel(String rent_p_tel) {
		this.rent_p_tel = rent_p_tel;
	}


	public String getRent_begindate() {
		return rent_begindate;
	}


	public void setRent_begindate(String rent_begindate) {
		this.rent_begindate = rent_begindate;
	}


	public String getRent_expectdate() {
		return rent_expectdate;
	}


	public void setRent_expectdate(String rent_expectdate) {
		this.rent_expectdate = rent_expectdate;
	}


	public String getRent_enddate() {
		return rent_enddate;
	}


	public void setRent_enddate(String rent_enddate) {
		this.rent_enddate = rent_enddate;
	}


	public String getEquipment_no() {
		return equipment_no;
	}


	public void setEquipment_no(String equipment_no) {
		this.equipment_no = equipment_no;
	}


	public BigDecimal getCost_rent_3g() {
		return cost_rent_3g;
	}


	public void setCost_rent_3g(BigDecimal cost_rent_3g) {
		this.cost_rent_3g = cost_rent_3g;
	}


	public BigDecimal getCost_rent_equipment() {
		return cost_rent_equipment;
	}


	public void setCost_rent_equipment(BigDecimal cost_rent_equipment) {
		this.cost_rent_equipment = cost_rent_equipment;
	}


	public BigDecimal getUpfront_sum() {
		return upfront_sum;
	}


	public void setUpfront_sum(BigDecimal upfront_sum) {
		this.upfront_sum = upfront_sum;
	}


	public Date getUpfront_date() {
		return upfront_date;
	}


	public void setUpfront_date(Date upfront_date) {
		this.upfront_date = upfront_date;
	}


	public BigDecimal getCost_discount() {
		return cost_discount;
	}


	public void setCost_discount(BigDecimal cost_discount) {
		this.cost_discount = cost_discount;
	}


	public BigDecimal getCost_return_discount() {
		return cost_return_discount;
	}


	public void setCost_return_discount(BigDecimal cost_return_discount) {
		this.cost_return_discount = cost_return_discount;
	}


	public BigDecimal getCost_sum() {
		return cost_sum;
	}


	public void setCost_sum(BigDecimal cost_sum) {
		this.cost_sum = cost_sum;
	}


	public String getIs_valid() {
		return is_valid;
	}


	public void setIs_valid(String is_valid) {
		this.is_valid = is_valid;
	}


	public String getCreate_user() {
		return create_user;
	}


	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}


	public String getCreate_time() {
		return create_time;
	}


	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}


	public String getModify_user() {
		return modify_user;
	}


	public void setModify_user(String modify_user) {
		this.modify_user = modify_user;
	}

	public String getModify_time() {
		return modify_time;
	}


	public void setModify_time(String modify_time) {
		this.modify_time = modify_time;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	public int getSort() {
		return sort;
	}


	public void setSort(int sort) {
		this.sort = sort;
	}


	@Override
	public String toString() {
		return "OrderEntity [id=" + id + ", useid=" + useid + ", order_num="
				+ order_num + ", rent_p_name=" + rent_p_name
				+ ", rent_p_idnumber=" + rent_p_idnumber
				+ ", rent_p_passportno=" + rent_p_passportno
				+ ", rent_p_nationality=" + rent_p_nationality
				+ ", rent_p_tel=" + rent_p_tel + ", rent_begindate="
				+ rent_begindate + ", rent_expectdate=" + rent_expectdate
				+ ", rent_enddate=" + rent_enddate + ", equipment_no="
				+ equipment_no + ", cost_rent_3g=" + cost_rent_3g
				+ ", cost_rent_equipment=" + cost_rent_equipment
				+ ", upfront_sum=" + upfront_sum + ", upfront_date="
				+ upfront_date + ", cost_discount=" + cost_discount
				+ ", cost_return_discount=" + cost_return_discount
				+ ", cost_sum=" + cost_sum + ", is_valid=" + is_valid
				+ ", create_user=" + create_user + ", create_time="
				+ create_time + ", modify_user=" + modify_user
				+ ", modify_time=" + modify_time + ", remark=" + remark
				+ ", sort=" + sort + ", s_country=" + s_country
				+ ", d_country=" + d_country + ", payment=" + payment
				+ ", rent_day=" + rent_day + ", order_state=" + order_state
				+ ", counterid=" + counterid + ", position_level="
				+ position_level + "]";
	}
	
	
	
} 

package com.jxc.web.user.model;

import java.math.BigDecimal;

/**
 * 设备管理表
 * @author Sun
 *
 */
public class EquipmentEntity {
	private String id;
	private String no; //设备编号
	private String sim_id; //3g卡id
	private String counterid; //柜台编码
	private String rent_begindate; //租用日期
	private String rent_expectdate; //预计返还日期
	private String rent_enddate; //返还日期
	private String stock_date; //进货日期
	private String scrap_date; //报废日期
	private String is_valid; //是否有效，0001有效，0002无效
	private int sort; //排序
	private String create_user; //创建人
	private String create_time; //创建时间
	private String modify_user; //修改人
	private String modify_time; //修改时间
	private String remark; //备注
	private String day_rent;//日租金
	private String deposit;//押金
	private BigDecimal total_rent;//设备租金
	private String equipment_type;//设备类型，0001自营设备，0002外部设备
	private String equipment_state;//状态：0001可用，0002已租，0003预订
	private String d_country;//设备目的地
	
	
	public String getD_country() {
		return d_country;
	}
	public void setD_country(String d_country) {
		this.d_country = d_country;
	}
	public String getEquipment_type() {
		return equipment_type;
	}
	public void setEquipment_type(String equipment_type) {
		this.equipment_type = equipment_type;
	}
	public String getEquipment_state() {
		return equipment_state;
	}
	public void setEquipment_state(String equipment_state) {
		this.equipment_state = equipment_state;
	}
	public String getDeposit() {
		return deposit;
	}
	public void setDeposit(String deposit) {
		this.deposit = deposit;
	}
	
	public BigDecimal getTotal_rent() {
		return total_rent;
	}
	public void setTotal_rent(BigDecimal total_rent) {
		this.total_rent = total_rent;
	}

	public String getDay_rent() {
		return day_rent;
	}
	public void setDay_rent(String day_rent) {
		this.day_rent = day_rent;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getSim_id() {
		return sim_id;
	}
	public void setSim_id(String sim_id) {
		this.sim_id = sim_id;
	}
	public String getCounterid() {
		return counterid;
	}
	public void setCounterid(String counterid) {
		this.counterid = counterid;
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
	public String getStock_date() {
		return stock_date;
	}
	public void setStock_date(String stock_date) {
		this.stock_date = stock_date;
	}
	public String getScrap_date() {
		return scrap_date;
	}
	public void setScrap_date(String scrap_date) {
		this.scrap_date = scrap_date;
	}
	public String getIs_valid() {
		return is_valid;
	}
	public void setIs_valid(String is_valid) {
		this.is_valid = is_valid;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
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
	@Override
	public String toString() {
		return "EquipmentEntity [id=" + id + ", no=" + no + ", sim_id="
				+ sim_id + ", counterid=" + counterid + ", rent_begindate="
				+ rent_begindate + ", rent_expectdate=" + rent_expectdate
				+ ", rent_enddate=" + rent_enddate + ", stock_date="
				+ stock_date + ", scrap_date=" + scrap_date + ", is_valid="
				+ is_valid + ", sort=" + sort + ", create_user=" + create_user
				+ ", create_time=" + create_time + ", modify_user="
				+ modify_user + ", modify_time=" + modify_time + ", remark="
				+ remark + "]";
	}
	
	
}

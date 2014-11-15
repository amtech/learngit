package com.jxc.web.user.model;

/**
 * 国际漫游卡基础信息表
 * @author Sun
 *
 */
public class InfoEntity {
	private String id;//3g卡编号
	private String operators; //运营商
	private String country; //国家
	private String is_valid; //是否有效，0001有效，0002无效
	private String create_user; //创建人
	private String create_time;  //创建时间
	private String modify_user; //修改人
	private String modify_time; //修改时间
	private String remark; //备注
	private int soft; //排序
	private String day_rent;//日租金
	private String day_begin;//开卡时间
	private String day_end;//卡到期时间
	private String last_rentdate;//最后出租日期
	
	
	public String getLast_rentdate() {
		return last_rentdate;
	}
	public void setLast_rentdate(String last_rentdate) {
		this.last_rentdate = last_rentdate;
	}
	public InfoEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOperators() {
		return operators;
	}
	public void setOperators(String operators) {
		this.operators = operators;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
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
	public int getSoft() {
		return soft;
	}
	public void setSoft(int soft) {
		this.soft = soft;
	}
	public String getDay_rent() {
		return day_rent;
	}
	public void setDay_rent(String day_rent) {
		this.day_rent = day_rent;
	}
	public String getDay_begin() {
		return day_begin;
	}
	public void setDay_begin(String day_begin) {
		this.day_begin = day_begin;
	}
	public String getDay_end() {
		return day_end;
	}
	public void setDay_end(String day_end) {
		this.day_end = day_end;
	}
	@Override
	public String toString() {
		return "InfoEntity [id=" + id + ", operators=" + operators
				+ ", country=" + country + ", is_valid=" + is_valid
				+ ", create_user=" + create_user + ", create_time="
				+ create_time + ", modify_user=" + modify_user
				+ ", modify_time=" + modify_time + ", remark=" + remark
				+ ", soft=" + soft + ", day_rent=" + day_rent + ", day_begin="
				+ day_begin + ", day_end=" + day_end + "]";
	}
	
	
	
}

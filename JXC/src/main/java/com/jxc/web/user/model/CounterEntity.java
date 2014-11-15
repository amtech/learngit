package com.jxc.web.user.model;

/**
 * 柜台表
 * @author Sun
 *
 */
public class CounterEntity {
	
	private String id; 
	private String name; //柜台名称
	private String counter_code;//柜台编码
	public String getCounter_code() {
		return counter_code;
	}
	public void setCounter_code(String counter_code) {
		this.counter_code = counter_code;
	}
	private String headid; //主管ID
	private String head_tel; //负责人电话
	private String rent_begindate; //租用日期
	private String rent_enddate; //租用到期日期
	private String is_valid; //是否有效，0001有效，0002无效
	private int sort; //排序
	private String create_user; //创建人
	private String create_time; //创建时间
	private String modify_user; //修改人
	private String modify_time; //修改时间
	private String remark; //备注
	private String country;//所在地
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHeadid() {
		return headid;
	}
	public void setHeadid(String headid) {
		this.headid = headid;
	}
	public String getHead_tel() {
		return head_tel;
	}
	public void setHead_tel(String head_tel) {
		this.head_tel = head_tel;
	}
	public String getRent_begindate() {
		return rent_begindate;
	}
	public void setRent_begindate(String rent_begindate) {
		this.rent_begindate = rent_begindate;
	}
	public String getRent_enddate() {
		return rent_enddate;
	}
	public void setRent_enddate(String rent_enddate) {
		this.rent_enddate = rent_enddate;
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
		return "CounterEntity [id=" + id + ", name=" + name + ", headid="
				+ headid + ", head_tel=" + head_tel + ", rent_begindate="
				+ rent_begindate + ", rent_enddate=" + rent_enddate
				+ ", is_valid=" + is_valid + ", sort=" + sort
				+ ", create_user=" + create_user + ", create_time="
				+ create_time + ", modify_user=" + modify_user
				+ ", modify_time=" + modify_time + ", remark=" + remark + "]";
	}
	
	
	
	
}

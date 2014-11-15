package com.jxc.web.user.model;
/**
 * SIM卡子表
 * @author Sun
 *
 */
public class InfologEntity {
	private String id;
	private String card_id;//SIM卡号
	private String operation_content;//操作内容
	private String ex_updatedate;//续费钱到期日期
	private String updatedate;//续费后到期日期
	private String operation_user;//操作员
	private String operation_time;//操作时间
	private String remark;//备注
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCard_id() {
		return card_id;
	}
	public void setCard_id(String card_id) {
		this.card_id = card_id;
	}
	public String getOperation_content() {
		return operation_content;
	}
	public void setOperation_content(String operation_content) {
		this.operation_content = operation_content;
	}
	public String getEx_updatedate() {
		return ex_updatedate;
	}
	public void setEx_updatedate(String ex_updatedate) {
		this.ex_updatedate = ex_updatedate;
	}
	public String getUpdatedate() {
		return updatedate;
	}
	public void setUpdatedate(String updatedate) {
		this.updatedate = updatedate;
	}
	public String getOperation_user() {
		return operation_user;
	}
	public void setOperation_user(String operation_user) {
		this.operation_user = operation_user;
	}
	public String getOperation_time() {
		return operation_time;
	}
	public void setOperation_time(String operation_time) {
		this.operation_time = operation_time;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "InfologEntity [id=" + id + ", card_id=" + card_id
				+ ", operation_content=" + operation_content
				+ ", ex_updatedate=" + ex_updatedate + ", updatedate="
				+ updatedate + ", operation_user=" + operation_user
				+ ", operation_time=" + operation_time + ", remark=" + remark
				+ "]";
	}
	
	
}

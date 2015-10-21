package com.oasys.viewModel;

import java.math.BigDecimal;

public class PurchaseAppAttachModel implements java.io.Serializable,Cloneable{
	
	private static final long serialVersionUID = 1L;
	/**
	 * 主键
	 */
	private Integer psaId;
	/**
	 * 申请编号
	 */
	private String appNo;
	/**
	 * 物品名称
	 */
	private String articleName;
	/**
	 * 型号规格
	 */
	private String model;
	/**
	 * 单价
	 */
	private BigDecimal price;
	/**
	 * 数量
	 */
	private Integer qty;
	/**
	 * 合计价格
	 */
	private BigDecimal totalAmt;
	/**
	 * 用途
	 */
	private String purpose;
	/**
	 * 使用人
	 */
	private Integer user;
	/**
	 * 保管人
	 */
	private Integer depositary;
	/**
	 * 备注信息
	 */
	private String remark;
	/**
	 * 使用人姓名
	 */
	private String userName;
	/**
	 * 保管人姓名
	 */
	private String depositaryName;

	public PurchaseAppAttachModel() {
	}

	public PurchaseAppAttachModel(Integer psaId) {
		this.psaId = psaId;
	}

	public PurchaseAppAttachModel(Integer psaId, String appNo, String articleName,
			String model, BigDecimal price, Integer qty, BigDecimal totalAmt,
			String purpose, Integer user, Integer depositary, String remark) {
		this.psaId = psaId;
		this.appNo = appNo;
		this.articleName = articleName;
		this.model = model;
		this.price = price;
		this.qty = qty;
		this.totalAmt = totalAmt;
		this.purpose = purpose;
		this.user = user;
		this.depositary = depositary;
		this.remark = remark;
	}

	public Integer getPsaId() {
		return psaId;
	}

	public void setPsaId(Integer psaId) {
		this.psaId = psaId;
	}

	public String getAppNo() {
		return appNo;
	}

	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}

	public String getArticleName() {
		return articleName;
	}

	public void setArticleName(String articleName) {
		this.articleName = articleName;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public BigDecimal getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(BigDecimal totalAmt) {
		this.totalAmt = totalAmt;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public Integer getUser() {
		return user;
	}

	public void setUser(Integer user) {
		this.user = user;
	}

	public Integer getDepositary() {
		return depositary;
	}

	public void setDepositary(Integer depositary) {
		this.depositary = depositary;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDepositaryName() {
		return depositaryName;
	}

	public void setDepositaryName(String depositaryName) {
		this.depositaryName = depositaryName;
	}
	
	@Override
	public Object clone(){
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}
}

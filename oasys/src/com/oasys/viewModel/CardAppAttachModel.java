package com.oasys.viewModel;

import java.io.Serializable;

public class CardAppAttachModel implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer caId;//id
	private String appNo;//申请编号
	private String applicantNo;//申请人姓名
	private String position;//申请人职位
	private String deptNo;//所属部门
	private String personalTel;
	private String officeTel;
	private String email;
	private String branchAddr;
	private String comUrl;
	private Integer appQty;
	private String unit;
	private String remark;
	private Integer userId;
	private Integer registrantNO;
	
	
	public CardAppAttachModel() {
	}

	public CardAppAttachModel(Integer caId, String appNo, String applicantNo,
			String position, String deptNo, String personalTel,
			String officeTel, String email, String branchAddr, String comUrl,
			Integer appQty, String unit, String remark,Integer registrantNo) {
		this.caId = caId;
		this.appNo = appNo;
		this.applicantNo = applicantNo;
		this.position = position;
		this.deptNo = deptNo;
		this.personalTel = personalTel;
		this.officeTel = officeTel;
		this.email = email;
		this.branchAddr = branchAddr;
		this.comUrl = comUrl;
		this.appQty = appQty;
		this.unit = unit;
		this.remark = remark;
		this.registrantNO = registrantNo;
	}

	public Integer getCaId() {
		return caId;
	}

	public void setCaId(Integer caId) {
		this.caId = caId;
	}

	public String getAppNo() {
		return appNo;
	}

	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}

	public String getApplicantNo() {
		return applicantNo;
	}

	public void setApplicantNo(String applicantNo) {
		this.applicantNo = applicantNo;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getDeptNo() {
		return deptNo;
	}

	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}

	public String getPersonalTel() {
		return personalTel;
	}

	public void setPersonalTel(String personalTel) {
		this.personalTel = personalTel;
	}

	public String getOfficeTel() {
		return officeTel;
	}

	public void setOfficeTel(String officeTel) {
		this.officeTel = officeTel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBranchAddr() {
		return branchAddr;
	}

	public void setBranchAddr(String branchAddr) {
		this.branchAddr = branchAddr;
	}

	public String getComUrl() {
		return comUrl;
	}

	public void setComUrl(String comUrl) {
		this.comUrl = comUrl;
	}

	public Integer getAppQty() {
		return appQty;
	}

	public void setAppQty(Integer appQty) {
		this.appQty = appQty;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getRegistrantNO() {
		return registrantNO;
	}

	public void setRegistrantNO(Integer registrantNO) {
		this.registrantNO = registrantNO;
	}
}

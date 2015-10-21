package com.qqms.model;

// Generated 2015-6-16 10:13:19 by Hibernate Tools 4.3.1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 初审资质分析实体类
 * @author PANCHUANHE
 * @date 2016/6/16
 */
@Entity
@Table(name = "t_bp_first_audit_report")
public class FirstAuditReport implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 初审资质分析ID
	 */
	private String firsId;
	/**
	 * 贷款订单ID号
	 */
	private String loanOrderId;
	/**
	 * 初审分析描述
	 */
	private String description;
	/**
	 * 是否本地
	 */
	private String isLocalRes;
	/**
	 * 婚姻状况
	 */
	private String marriageType;
	/**
	 * 行业类型
	 */
	private String industryType;
	/**
	 * 经营年限
	 */
	private Integer comOperDuration;
	/**
	 * 经营状况
	 */
	private String comOperStatus;
	/**
	 * 信用情况
	 */
	private String creditStatus;
	/**
	 * 电核情况
	 */
	private String phoneCheckStatus;
	/**
	 * 信访情况
	 */
	private String visitStatus;
	/**
	 * 资质总评
	 */
	private String qulificationStatus;
	/**
	 * 备注
	 */
	private String note;
	/**
	 * 初审人员意见
	 */
	private String firsPersSugg;
	/**
	 * 初审人
	 */
	private String firsPersonnel;
	/**
	 * 初审日期
	 */
	private Date firsDate;
	/**
	 * 初审部门意见
	 */
	private String firsDepSugg;
	
	private Set<CreditAuditReport> creditAuditReports = new HashSet<CreditAuditReport>(0);

	public FirstAuditReport() {
	}

	public FirstAuditReport(String firsId) {
		this.firsId = firsId;
	}

	public FirstAuditReport(String firsId, String loanOrderId,
			String description, String isLocalRes, String marriageType,
			String industryType, Integer comOperDuration,
			String comOperStatus, String creditStatus,
			String phoneCheckStatus, String visitStatus,
			String qulificationStatus, String note, String firsPersSugg,
			String firsPersonnel, Date firsDate, String firsDepSugg,
			Set<CreditAuditReport> creditAuditReports) {
		this.firsId = firsId;
		this.loanOrderId = loanOrderId;
		this.description = description;
		this.isLocalRes = isLocalRes;
		this.marriageType = marriageType;
		this.industryType = industryType;
		this.comOperDuration = comOperDuration;
		this.comOperStatus = comOperStatus;
		this.creditStatus = creditStatus;
		this.phoneCheckStatus = phoneCheckStatus;
		this.visitStatus = visitStatus;
		this.qulificationStatus = qulificationStatus;
		this.note = note;
		this.firsPersSugg = firsPersSugg;
		this.firsPersonnel = firsPersonnel;
		this.firsDate = firsDate;
		this.firsDepSugg = firsDepSugg;
		this.creditAuditReports = creditAuditReports;
	}

	@Id
	@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@GeneratedValue(generator = "systemUUID")
	@Column(name = "FIRS_ID", insertable = true, updatable = true, nullable = false, length = 40, unique = true)
public String getFirsId() {
		return this.firsId;
	}

	public void setFirsId(String firsId) {
		this.firsId = firsId;
	}

	@Column(name = "LOAN_ORDER_ID", length = 40)
	public String getLoanOrderId() {
		return this.loanOrderId;
	}

	public void setLoanOrderId(String loanOrderId) {
		this.loanOrderId = loanOrderId;
	}

	@Column(name = "DESCRIPTION", length = 1024)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "IS_LOCAL_RES", length = 1)
	public String getIsLocalRes() {
		return this.isLocalRes;
	}

	public void setIsLocalRes(String isLocalRes) {
		this.isLocalRes = isLocalRes;
	}

	@Column(name = "MARRIAGE_TYPE", length = 1)
	public String getMarriageType() {
		return this.marriageType;
	}

	public void setMarriageType(String marriageType) {
		this.marriageType = marriageType;
	}

	@Column(name = "INDUSTRY_TYPE", length = 50)
	public String getIndustryType() {
		return this.industryType;
	}

	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}

	@Column(name = "COM_OPER_DURATION")
	public Integer getComOperDuration() {
		return this.comOperDuration;
	}

	public void setComOperDuration(Integer comOperDuration) {
		this.comOperDuration = comOperDuration;
	}

	@Column(name = "COM_OPER_STATUS", length = 1)
	public String getComOperStatus() {
		return this.comOperStatus;
	}

	public void setComOperStatus(String comOperStatus) {
		this.comOperStatus = comOperStatus;
	}

	@Column(name = "CREDIT_STATUS", length = 1)
	public String getCreditStatus() {
		return this.creditStatus;
	}

	public void setCreditStatus(String creditStatus) {
		this.creditStatus = creditStatus;
	}

	@Column(name = "PHONE_CHECK_STATUS", length = 1)
	public String getPhoneCheckStatus() {
		return this.phoneCheckStatus;
	}

	public void setPhoneCheckStatus(String phoneCheckStatus) {
		this.phoneCheckStatus = phoneCheckStatus;
	}

	@Column(name = "VISIT_STATUS", length = 1)
	public String getVisitStatus() {
		return this.visitStatus;
	}

	public void setVisitStatus(String visitStatus) {
		this.visitStatus = visitStatus;
	}

	@Column(name = "QULIFICATION_STATUS", length = 1)
	public String getQulificationStatus() {
		return this.qulificationStatus;
	}

	public void setQulificationStatus(String qulificationStatus) {
		this.qulificationStatus = qulificationStatus;
	}

	@Column(name = "NOTE", length = 300)
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Column(name = "FIRS_PERS_SUGG", length = 10)
	public String getFirsPersSugg() {
		return this.firsPersSugg;
	}

	public void setFirsPersSugg(String firsPersSugg) {
		this.firsPersSugg = firsPersSugg;
	}

	@Column(name = "FIRS_PERSONNEL", length = 40)
	public String getFirsPersonnel() {
		return this.firsPersonnel;
	}

	public void setFirsPersonnel(String firsPersonnel) {
		this.firsPersonnel = firsPersonnel;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FIRS_DATE", length = 19)
	public Date getFirsDate() {
		return this.firsDate;
	}

	public void setFirsDate(Date firsDate) {
		this.firsDate = firsDate;
	}

	@Column(name = "FIRS_DEP_SUGG", length = 512)
	public String getFirsDepSugg() {
		return this.firsDepSugg;
	}

	public void setFirsDepSugg(String firsDepSugg) {
		this.firsDepSugg = firsDepSugg;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "firstAuditReport")
	@JSONField(serialize = false)
	public Set<CreditAuditReport> getCreditAuditReports() {
		return this.creditAuditReports;
	}

	public void setCreditAuditReports(Set<CreditAuditReport> creditAuditReports) {
		this.creditAuditReports = creditAuditReports;
	}

}

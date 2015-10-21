package com.qqms.model;

// Generated 2015-6-16 10:13:19 by Hibernate Tools 4.3.1

import java.math.BigDecimal;
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
 * 终审资质分析 实体
 */
@Entity
@Table(name = "t_bp_final_audit_report")
public class FinalAuditReport implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String finaId;
	private String loanOrderId;
	private String description;
	private String finaPersonnel;
	private String finaPersSugg;
	private Date finaDate;
	private BigDecimal contractLoanAmount;
	private BigDecimal actualLoanAmount;
	private String loanPeriodType;
	private String monthServiceFeeRate;
	private String loanInterestRate;
	private String visitFee;
	private BigDecimal monthRepay;
	private Set<CreditAuditReport> creditAuditReports = new HashSet<CreditAuditReport>(
			0);

	public FinalAuditReport() {
	}

	public FinalAuditReport(String finaId) {
		this.finaId = finaId;
	}

	public FinalAuditReport(String finaId, String loanOrderId,
			String description, String finaPersonnel, String finaPersSugg,
			Date finaDate, BigDecimal contractLoanAmount,
			BigDecimal actualLoanAmount, String loanPeriodType,
			String monthServiceFeeRate, String loanInterestRate,
			String visitFee, BigDecimal monthRepay,
			Set<CreditAuditReport> creditAuditReports) {
		this.finaId = finaId;
		this.loanOrderId = loanOrderId;
		this.description = description;
		this.finaPersonnel = finaPersonnel;
		this.finaPersSugg = finaPersSugg;
		this.finaDate = finaDate;
		this.contractLoanAmount = contractLoanAmount;
		this.actualLoanAmount = actualLoanAmount;
		this.loanPeriodType = loanPeriodType;
		this.monthServiceFeeRate = monthServiceFeeRate;
		this.loanInterestRate = loanInterestRate;
		this.visitFee = visitFee;
		this.monthRepay = monthRepay;
		this.creditAuditReports = creditAuditReports;
	}

	@Id
	@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@GeneratedValue(generator = "systemUUID")
	@Column(name = "FINA_ID", insertable = true, updatable = true, nullable = false, length = 40, unique = true)
	public String getFinaId() {
		return this.finaId;
	}

	public void setFinaId(String finaId) {
		this.finaId = finaId;
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

	@Column(name = "FINA_PERSONNEL", length = 40)
	public String getFinaPersonnel() {
		return this.finaPersonnel;
	}

	public void setFinaPersonnel(String finaPersonnel) {
		this.finaPersonnel = finaPersonnel;
	}

	@Column(name = "FINA_PERS_SUGG", length = 1024)
	public String getFinaPersSugg() {
		return this.finaPersSugg;
	}

	public void setFinaPersSugg(String finaPersSugg) {
		this.finaPersSugg = finaPersSugg;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FINA_DATE", length = 19)
	public Date getFinaDate() {
		return this.finaDate;
	}

	public void setFinaDate(Date finaDate) {
		this.finaDate = finaDate;
	}

	@Column(name = "CONTRACT_LOAN_AMOUNT", precision = 20, scale = 5)
	public BigDecimal getContractLoanAmount() {
		return this.contractLoanAmount;
	}

	public void setContractLoanAmount(BigDecimal contractLoanAmount) {
		this.contractLoanAmount = contractLoanAmount;
	}

	@Column(name = "ACTUAL_LOAN_AMOUNT", precision = 20, scale = 5)
	public BigDecimal getActualLoanAmount() {
		return this.actualLoanAmount;
	}

	public void setActualLoanAmount(BigDecimal actualLoanAmount) {
		this.actualLoanAmount = actualLoanAmount;
	}

	@Column(name = "LOAN_PERIOD_TYPE", length = 1)
	public String getLoanPeriodType() {
		return this.loanPeriodType;
	}

	public void setLoanPeriodType(String loanPeriodType) {
		this.loanPeriodType = loanPeriodType;
	}

	@Column(name = "MONTH_SERVICE_FEE_RATE", length = 1)
	public String getMonthServiceFeeRate() {
		return this.monthServiceFeeRate;
	}

	public void setMonthServiceFeeRate(String monthServiceFeeRate) {
		this.monthServiceFeeRate = monthServiceFeeRate;
	}

	@Column(name = "LOAN_INTEREST_RATE", length = 20)
	public String getLoanInterestRate() {
		return this.loanInterestRate;
	}

	public void setLoanInterestRate(String loanInterestRate) {
		this.loanInterestRate = loanInterestRate;
	}

	@Column(name = "VISIT_FEE", length = 1)
	public String getVisitFee() {
		return this.visitFee;
	}

	public void setVisitFee(String visitFee) {
		this.visitFee = visitFee;
	}

	@Column(name = "MONTH_REPAY", precision = 20, scale = 5)
	public BigDecimal getMonthRepay() {
		return this.monthRepay;
	}

	public void setMonthRepay(BigDecimal monthRepay) {
		this.monthRepay = monthRepay;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "finalAuditReport")
	@JSONField(serialize = false)
	public Set<CreditAuditReport> getCreditAuditReports() {
		return this.creditAuditReports;
	}

	public void setCreditAuditReports(Set<CreditAuditReport> creditAuditReports) {
		this.creditAuditReports = creditAuditReports;
	}

}

package com.qqms.model;

// Generated 2015-6-16 10:13:19 by Hibernate Tools 4.3.1

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 信审报告实体
 */
@Entity
@Table(name = "t_bp_credit_audit_report")
public class CreditAuditReport implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String carId;
	private Assets Assets;
	private CreditCardsDetails creditCardsDetails;
	private Set<CreditInvestigation> creditInvestigations = new HashSet<CreditInvestigation>(
			0);
	private FinalAuditReport finalAuditReport;
	private FirstAuditReport firstAuditReport;
	private LoanOrder loanOrder;
	private LoansDetails loansDetails;
	private String lawQueryStatus;
	private String comCreditStatus;
	private Set<AccountsJournal> accountsJournals = new HashSet<AccountsJournal>(
			0);

	public CreditAuditReport() {
	}

	public CreditAuditReport(String carId) {
		this.carId = carId;
	}

	public CreditAuditReport(String carId,Assets assets,
			CreditCardsDetails creditCardsDetails,
			Set<CreditInvestigation> creditInvestigations,
			FinalAuditReport finalAuditReport,
			FirstAuditReport firstAuditReport, LoanOrder loanOrder,
			LoansDetails loansDetails, String lawQueryStatus,
			String comCreditStatus, Set<AccountsJournal> accountsJournals) {
		super();
		this.carId = carId;
		Assets = assets;
		this.creditCardsDetails = creditCardsDetails;
		this.creditInvestigations = creditInvestigations;
		this.finalAuditReport = finalAuditReport;
		this.firstAuditReport = firstAuditReport;
		this.loanOrder = loanOrder;
		this.loansDetails = loansDetails;
		this.lawQueryStatus = lawQueryStatus;
		this.comCreditStatus = comCreditStatus;
		this.accountsJournals = accountsJournals;
	}

	@Id
	@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@GeneratedValue(generator = "systemUUID")
	@Column(name = "CAR_ID", insertable = true, updatable = true, nullable = false, length = 40, unique = true)
	public String getCarId() {
		return this.carId;
	}

	public void setCarId(String carId) {
		this.carId = carId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ASSET_ID")
	public Assets getAssets() {
		return this.Assets;
	}

	public void setAssets(Assets assets) {
		this.Assets = assets;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CARD_INFO_ID")
	public CreditCardsDetails getCreditCardsDetails() {
		return this.creditCardsDetails;
	}

	public void setCreditCardsDetails(CreditCardsDetails creditCardsDetails) {
		this.creditCardsDetails = creditCardsDetails;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FINA_ID")
	public FinalAuditReport getFinalAuditReport() {
		return this.finalAuditReport;
	}

	public void setFinalAuditReport(FinalAuditReport finalAuditReport) {
		this.finalAuditReport = finalAuditReport;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FIRS_ID")
	public FirstAuditReport getFirstAuditReport() {
		return this.firstAuditReport;
	}

	public void setFirstAuditReport(FirstAuditReport firstAuditReport) {
		this.firstAuditReport = firstAuditReport;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LOAN_ORDER_ID")
	public LoanOrder getLoanOrder() {
		return this.loanOrder;
	}

	public void setLoanOrder(LoanOrder loanOrder) {
		this.loanOrder = loanOrder;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EXIST_LOAN_ID")
	public LoansDetails getLoansDetails() {
		return this.loansDetails;
	}

	public void setLoansDetails(LoansDetails loansDetails) {
		this.loansDetails = loansDetails;
	}

	@Column(name = "LAW_QUERY_STATUS", length = 1)
	public String getLawQueryStatus() {
		return this.lawQueryStatus;
	}

	public void setLawQueryStatus(String lawQueryStatus) {
		this.lawQueryStatus = lawQueryStatus;
	}

	@Column(name = "COM_CREDIT_STATUS", length = 1)
	public String getComCreditStatus() {
		return this.comCreditStatus;
	}

	public void setComCreditStatus(String comCreditStatus) {
		this.comCreditStatus = comCreditStatus;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "creditAuditReport")
	@JSONField(serialize = false)
	public Set<AccountsJournal> getAccountsJournals() {
		return this.accountsJournals;
	}

	public void setAccountsJournals(Set<AccountsJournal> accountsJournals) {
		this.accountsJournals = accountsJournals;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "creditAuditReport")
	@JSONField(serialize = false)
	public Set<CreditInvestigation> getCreditInvestigations() {
		return creditInvestigations;
	}

	public void setCreditInvestigations(
			Set<CreditInvestigation> creditInvestigations) {
		this.creditInvestigations = creditInvestigations;
	}

}

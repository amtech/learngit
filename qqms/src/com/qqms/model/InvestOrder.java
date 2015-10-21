package com.qqms.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * 投资订单
 * 
 * @author liuhh
 *
 */
@Entity
@Table(name = "t_bp_invest_order")
public class InvestOrder implements java.io.Serializable,Cloneable{

	private static final long serialVersionUID = 1L;
	/**
	 * 主键ID
	 */
	private String investOrderId;
	/**
	 * 投资人ID
	 */
	private String investorId;
	/**
	 * 订单状态
	 */
	private OrderStatus orderStatus;
	/**
	 * 订单描述
	 */
	private String orderDesc;
	/**
	 * 合同编号
	 */
	private String contractNo;
	/**
	 * 合同签署地
	 */
	private String signSite;
	/**
	 * 合同签署时间
	 */
	private Date signDate;
	/**
	 * 身份证号码
	 */
	private String idCrad;
	/**
	 * 移动电话
	 */
	private String mobTel;
	/**
	 * 理财经理
	 */
	private String financingMgr;
	/**
	 * 理财经理电话
	 */
	private String fmPhone;
	/**
	 * 理财经理签署日期
	 */
	private Date fmSignDate;
	/**
	 * 部门主管
	 */
	private String deptMgr;
	/**
	 * 部门主管签署日期
	 */
	private Date dmSignDate;
	/**
	 * 创建人
	 */
	private String creator;
	/**
	 * 创建日期
	 */
	private Date createDate;
	/**
	 * 开户行名称
	 */
	private String bankName;
	/**
	 * 开户行账号
	 */
	private String actNo;
	/**
	 * 账户名称
	 */
	private String actName;
	/**
	 * 账户介质
	 */
	private String actMedium;
	/**
	 * 账户性质
	 */
	private String actNature;
	/**
	 * 流程状态
	 */
	private String processStatus;
	/**
	 * 投资人姓名
	 */
	private String investorName;
	/**
	 * 部门id
	 */
	private Integer organizationId;
	/**
	 * 任务id
	 */
	private String taskId;
	
	public InvestOrder() {
	}

	public InvestOrder(String investOrderId, String contractNo, String idCrad,
			String mobTel, String bankName, String actNo, String actName) {
		this.investOrderId = investOrderId;
		this.contractNo = contractNo;
		this.idCrad = idCrad;
		this.mobTel = mobTel;
		this.bankName = bankName;
		this.actNo = actNo;
		this.actName = actName;
	}

	
	public InvestOrder(String investOrderId, String investorId,
			OrderStatus orderStatus, String orderDesc, String contractNo,
			String signSite, Date signDate, String idCrad, String mobTel,
			String financingMgr, String fmPhone, Date fmSignDate,
			String deptMgr, Date dmSignDate, String creator, Date createDate,
			String bankName, String actNo, String actName, String actMedium,
			String actNature) {
		super();
		this.investOrderId = investOrderId;
		this.investorId = investorId;
		this.orderStatus = orderStatus;
		this.orderDesc = orderDesc;
		this.contractNo = contractNo;
		this.signSite = signSite;
		this.signDate = signDate;
		this.idCrad = idCrad;
		this.mobTel = mobTel;
		this.financingMgr = financingMgr;
		this.fmPhone = fmPhone;
		this.fmSignDate = fmSignDate;
		this.deptMgr = deptMgr;
		this.dmSignDate = dmSignDate;
		this.creator = creator;
		this.createDate = createDate;
		this.bankName = bankName;
		this.actNo = actNo;
		this.actName = actName;
		this.actMedium = actMedium;
		this.actNature = actNature;
	}

	@Id
	@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@GeneratedValue(generator = "systemUUID")
	@Column(name = "INVEST_ORDER_ID", insertable = true, updatable = true, nullable = false, length = 40, unique = true)
	public String getInvestOrderId() {
		return this.investOrderId;
	}

	public void setInvestOrderId(String investOrderId) {
		this.investOrderId = investOrderId;
	}

	@Column(name = "INVESTOR_ID", length = 40)
	public String getInvestorId() {
		return investorId;
	}

	public void setInvestorId(String investorId) {
		this.investorId = investorId;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ORDER_STATUS")
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	@Column(name = "ORDER_DESC", length = 200)
	public String getOrderDesc() {
		return this.orderDesc;
	}

	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
	}

	@Column(name = "CONTRACT_NO", nullable = false, length = 32)
	public String getContractNo() {
		return this.contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	@Column(name = "SIGN_SITE", length = 128)
	public String getSignSite() {
		return this.signSite;
	}

	public void setSignSite(String signSite) {
		this.signSite = signSite;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "SIGN_DATE", length = 10)
	public Date getSignDate() {
		return this.signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

	@Column(name = "ID_CARD", nullable = false, length = 20)
	public String getIdCrad() {
		return this.idCrad;
	}

	public void setIdCrad(String idCrad) {
		this.idCrad = idCrad;
	}

	@Column(name = "MOB_TEL", nullable = false, length = 15)
	public String getMobTel() {
		return this.mobTel;
	}

	public void setMobTel(String mobTel) {
		this.mobTel = mobTel;
	}

	@Column(name = "FINANCING_MGR", length = 40)
	public String getFinancingMgr() {
		return this.financingMgr;
	}

	public void setFinancingMgr(String financingMgr) {
		this.financingMgr = financingMgr;
	}

	@Column(name = "FM_PHONE", length = 20)
	public String getFmPhone() {
		return this.fmPhone;
	}

	public void setFmPhone(String fmPhone) {
		this.fmPhone = fmPhone;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "FM_SIGN_DATE", length = 10)
	public Date getFmSignDate() {
		return this.fmSignDate;
	}

	public void setFmSignDate(Date fmSignDate) {
		this.fmSignDate = fmSignDate;
	}

	@Column(name = "DEPT_MGR", length = 40)
	public String getDeptMgr() {
		return this.deptMgr;
	}

	public void setDeptMgr(String deptMgr) {
		this.deptMgr = deptMgr;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DM_SIGN_DATE", length = 10)
	public Date getDmSignDate() {
		return this.dmSignDate;
	}

	public void setDmSignDate(Date dmSignDate) {
		this.dmSignDate = dmSignDate;
	}

	@Column(name = "CREATOR", length = 40)
	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_DATE", length = 19)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "BANK_NAME", nullable = false, length = 80)
	public String getBankName() {
		return this.bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	@Column(name = "ACT_NO", nullable = false, length = 32)
	public String getActNo() {
		return this.actNo;
	}

	public void setActNo(String actNo) {
		this.actNo = actNo;
	}

	@Column(name = "ACT_NAME", nullable = false, length = 32)
	public String getActName() {
		return this.actName;
	}

	public void setActName(String actName) {
		this.actName = actName;
	}

	@Column(name = "ACT_MEDIUM", length = 1)
	public String getActMedium() {
		return this.actMedium;
	}

	public void setActMedium(String actMedium) {
		this.actMedium = actMedium;
	}

	@Column(name = "ACT_NATURE", length = 1)
	public String getActNature() {
		return this.actNature;
	}

	public void setActNature(String actNature) {
		this.actNature = actNature;
	}
	@Column(name = "PROCESS_STATUS", length = 1)
	public String getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}
	@Column(name = "INVESTOR_NAME", length = 20)
	public String getInvestorName() {
		return investorName;
	}

	public void setInvestorName(String investorName) {
		this.investorName = investorName;
	}
	@Column(name = "ORGANIZATION_ID")
	public Integer getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Integer organizationId) {
		this.organizationId = organizationId;
	}
    @Transient
	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	@Override
	public Object clone(){
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String toString() {
		return "InvestOrder [investOrderId=" + investOrderId + ", investorId="
				+ investorId + ", orderStatus=" + orderStatus + ", orderDesc="
				+ orderDesc + ", contractNo=" + contractNo + ", signSite="
				+ signSite + ", signDate=" + signDate + ", idCrad=" + idCrad
				+ ", mobTel=" + mobTel + ", financingMgr=" + financingMgr
				+ ", fmPhone=" + fmPhone + ", fmSignDate=" + fmSignDate
				+ ", deptMgr=" + deptMgr + ", dmSignDate=" + dmSignDate
				+ ", creator=" + creator + ", createDate=" + createDate
				+ ", bankName=" + bankName + ", actNo=" + actNo + ", actName="
				+ actName + ", actMedium=" + actMedium + ", actNature="
				+ actNature + ", processStatus=" + processStatus
				+ ", investorName=" + investorName + ", organizationId="
				+ organizationId + ", taskId=" + taskId + "]";
	}
	
	
}

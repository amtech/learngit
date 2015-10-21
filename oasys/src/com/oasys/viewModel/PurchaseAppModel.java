package com.oasys.viewModel;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName: PurchaseAppVo 
 * @Description: TODO 物料申请vo
 * @author PANCHUANHE
 * @date 2015年9月17日 下午3:26:50
 */
public class PurchaseAppModel implements java.io.Serializable,Cloneable{

	private static final long serialVersionUID = 1L;
	/**
	 * 主键
	 */
	private int paId;
	/**
	 * 申请编号
	 */
	private String appNo;
	/**
	 * 申请类型
	 */
	private String appType;
	/**
	 * 其他申请类型
	 */
	private String appTypeOther;
	/**
	 * 申请人
	 */
	private Integer applicantNo;
	/**
	 * 申请部门
	 */
	private Integer appDept;
	/**
	 * 申请日期
	 */
	private Date appDate;
	/**
	 * 申请状态
	 */
	private String appStatus;
	/**
	 * 合计金额
	 */
	private BigDecimal totalAmt;
	/**
	 * 计划到货时间
	 */
	private Date planRecDate;
	/**
	 * 备注信息
	 */
	private String remark;
	/**
	 * 流程状态
	 */
	private String procStatus;
	
	
	/**
	 * 申请人姓名
	 */
	private String account;
	/**
	 * 申请机构名称
	 */
	private String fullName;
	/**
	 * 申请类型名称
	 */
	private String appTypeName;
	
	/**
	 * 最小合计金额
	 */
	private BigDecimal totalAmtMini;
	/**
	 * 最大合计金额
	 */
	private BigDecimal totalAmtMax;
	/**
	 * 最小申请日期
	 */
	private String appDateMini;
	/**
	 * 最大申请日期
	 */
	private String appDateMax;
	
	/********附加表字段 start*******/
	/**
	 * 主键
	 */
	private Integer psaId;
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
	private BigDecimal ztotalAmt;
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
	private String zremark;
	/**
	 * 使用人姓名
	 */
	private String userName;
	/**
	 * 保管人姓名
	 */
	private String depositaryName;
	/**
	 * 任务id
	 */
	private String taskId;
	/**
	 * task对象
	 */
	private WorkFlowTasksModel taskModel;
	
	public PurchaseAppModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PurchaseAppModel(int paId, String appNo, String appType,
			String appTypeOther, Integer applicantNo, Integer appDept,
			Date appDate, String appStatus, BigDecimal totalAmt,
			Date planRecDate, String remark, String procStatus,
			String account) {
		super();
		this.paId = paId;
		this.appNo = appNo;
		this.appType = appType;
		this.appTypeOther = appTypeOther;
		this.applicantNo = applicantNo;
		this.appDept = appDept;
		this.appDate = appDate;
		this.appStatus = appStatus;
		this.totalAmt = totalAmt;
		this.planRecDate = planRecDate;
		this.remark = remark;
		this.procStatus = procStatus;
		this.account = account;
	}
	public int getPaId() {
		return paId;
	}
	public void setPaId(int paId) {
		this.paId = paId;
	}
	public String getAppNo() {
		return appNo;
	}
	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}
	public String getAppType() {
		return appType;
	}
	public void setAppType(String appType) {
		this.appType = appType;
	}
	public String getAppTypeOther() {
		return appTypeOther;
	}
	public void setAppTypeOther(String appTypeOther) {
		this.appTypeOther = appTypeOther;
	}
	public Integer getApplicantNo() {
		return applicantNo;
	}
	public void setApplicantNo(Integer applicantNo) {
		this.applicantNo = applicantNo;
	}
	public Integer getAppDept() {
		return appDept;
	}
	public void setAppDept(Integer appDept) {
		this.appDept = appDept;
	}
	public Date getAppDate() {
		return appDate;
	}
	public void setAppDate(Date appDate) {
		this.appDate = appDate;
	}
	public String getAppStatus() {
		return appStatus;
	}
	public void setAppStatus(String appStatus) {
		this.appStatus = appStatus;
	}
	public BigDecimal getTotalAmt() {
		return totalAmt;
	}
	public void setTotalAmt(BigDecimal totalAmt) {
		this.totalAmt = totalAmt;
	}
	public Date getPlanRecDate() {
		return planRecDate;
	}
	public void setPlanRecDate(Date planRecDate) {
		this.planRecDate = planRecDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getProcStatus() {
		return procStatus;
	}
	public void setProcStatus(String procStatus) {
		this.procStatus = procStatus;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
    public String getAppTypeName() {
		return appTypeName;
	}
	public void setAppTypeName(String appTypeName) {
		this.appTypeName = appTypeName;
	}
	public BigDecimal getTotalAmtMini() {
		return totalAmtMini;
	}
	public void setTotalAmtMini(BigDecimal totalAmtMini) {
		this.totalAmtMini = totalAmtMini;
	}
	public BigDecimal getTotalAmtMax() {
		return totalAmtMax;
	}
	public void setTotalAmtMax(BigDecimal totalAmtMax) {
		this.totalAmtMax = totalAmtMax;
	}
	public String getAppDateMini() {
		return appDateMini;
	}
	public void setAppDateMini(String appDateMini) {
		this.appDateMini = appDateMini;
	}
	public String getAppDateMax() {
		return appDateMax;
	}
	public void setAppDateMax(String appDateMax) {
		this.appDateMax = appDateMax;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public Integer getPsaId() {
		return psaId;
	}
	public void setPsaId(Integer psaId) {
		this.psaId = psaId;
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
	public BigDecimal getZtotalAmt() {
		return ztotalAmt;
	}
	public void setZtotalAmt(BigDecimal ztotalAmt) {
		this.ztotalAmt = ztotalAmt;
	}
	public String getZremark() {
		return zremark;
	}
	public void setZremark(String zremark) {
		this.zremark = zremark;
	}
	public WorkFlowTasksModel getTaskModel() {
		return taskModel;
	}
	public void setTaskModel(WorkFlowTasksModel taskModel) {
		this.taskModel = taskModel;
	}
	@Override
    public Object clone(){
    	try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
    	return null;
    }
}

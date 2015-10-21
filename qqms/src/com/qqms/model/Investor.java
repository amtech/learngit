package com.qqms.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * 投资人
 * 
 * @author liuhh
 *
 */
@Entity
@Table(name = "t_bp_investor")
public class Investor implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 投资人ID
	 */
	private String investorId;
	/**
	 * 投资人类型
	 */
	private String investorType;
	/**
	 * 投资人状态
	 */
	private String investorStatus;
	/**
	 * 中文姓名
	 */
	private String chName;
	/**
	 * 英文姓名
	 */
	private String enName;
	/**
	 * 性别
	 */
	private String genderType;
	/**
	 * 出生日期
	 */
	private Date birthday;
	/**
	 * 国籍
	 */
	private String nationality;
	/**
	 * 婚姻状况
	 */
	private String maritalStatus;
	/**
	 * 家庭情况
	 */
	private String familyStatus;
	/**
	 * 家庭收入情况
	 */
	private String famIncSta;
	/**
	 * 证件类型
	 */
	private String idType;
	/**
	 * 证件号码
	 */
	private String idNo;
	/**
	 * 签发日期
	 */
	private Date idIssueDate;
	/**
	 * 失效日期
	 */
	private Date idExpireDate;
	/**
	 * 发证机构所在地
	 */
	private String idLocation;
	/**
	 * 学历
	 */
	private String degreeType;
	/**
	 * 职业类型
	 */
	private String jobType;
	/**
	 * 所属行业
	 */
	private String industry;
	/**
	 * 单位名称
	 */
	private String companyName;
	/**
	 * 工作年限
	 */
	private Integer yearsOfWork;
	/**
	 * 单位规模
	 */
	private String compScale;
	/**
	 * 职务
	 */
	private String jobTitle;
	/**
	 * 移动电话
	 */
	private String mobileTel;
	/**
	 * 固定电话
	 */
	private String fixedTel;
	/**
	 * 电子邮箱
	 */
	private String email;
	/**
	 * 通讯地址
	 */
	private String commAddr;
	/**
	 * 邮政编码
	 */
	private String zip;
	/**
	 * 订单状态
	 */
	private Integer state = 0;
	/**
	 * 省
	 */
	private Integer provinceId;
	/**
	 * 市
	 */
	private Integer cityId;
	/**
	 * 县
	 */
	private Integer areaId;
	/**
	 * 地址详细
	 */
	private String addressDetails;
	
	public Investor() {
	}

	public Investor(String investorId) {
		this.investorId = investorId;
	}

	public Investor(String investorId, String investorType,
			String investorStatus, String chName, String enName,
			String genderType, Date birthday, String nationality,
			String maritalStatus, String familyStatus, String famIncSta,
			String idType, String idNo, Date idIssueDate, Date idExpireDate,
			String idLocation, String degreeType, String jobType,
			String industry, String companyName, Integer yearsOfWork,
			String compScale, String jobTitle, String mobileTel,
			String fixedTel, String email, String commAddr, String zip) {
		this.investorId = investorId;
		this.investorType = investorType;
		this.investorStatus = investorStatus;
		this.chName = chName;
		this.enName = enName;
		this.genderType = genderType;
		this.birthday = birthday;
		this.nationality = nationality;
		this.maritalStatus = maritalStatus;
		this.familyStatus = familyStatus;
		this.famIncSta = famIncSta;
		this.idType = idType;
		this.idNo = idNo;
		this.idIssueDate = idIssueDate;
		this.idExpireDate = idExpireDate;
		this.idLocation = idLocation;
		this.degreeType = degreeType;
		this.jobType = jobType;
		this.industry = industry;
		this.companyName = companyName;
		this.yearsOfWork = yearsOfWork;
		this.compScale = compScale;
		this.jobTitle = jobTitle;
		this.mobileTel = mobileTel;
		this.fixedTel = fixedTel;
		this.email = email;
		this.commAddr = commAddr;
		this.zip = zip;
	}

	@Id
	@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@GeneratedValue(generator = "systemUUID")
	@Column(name = "INVESTOR_ID", insertable = true, updatable = true, nullable = false, length = 40, unique = true)
	public String getInvestorId() {
		return this.investorId;
	}

	public void setInvestorId(String investorId) {
		this.investorId = investorId;
	}

	@Column(name = "INVESTOR_TYPE", length = 1)
	public String getInvestorType() {
		return this.investorType;
	}

	public void setInvestorType(String investorType) {
		this.investorType = investorType;
	}

	@Column(name = "INVESTOR_STATUS", length = 1)
	public String getInvestorStatus() {
		return this.investorStatus;
	}

	public void setInvestorStatus(String investorStatus) {
		this.investorStatus = investorStatus;
	}

	@Column(name = "CH_NAME", length = 50)
	public String getChName() {
		return this.chName;
	}

	public void setChName(String chName) {
		this.chName = chName;
	}

	@Column(name = "EN_NAME", length = 50)
	public String getEnName() {
		return this.enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	@Column(name = "GENDER_TYPE", length = 1)
	public String getGenderType() {
		return this.genderType;
	}

	public void setGenderType(String genderType) {
		this.genderType = genderType;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "BIRTHDAY", length = 10)
	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@Column(name = "NATIONALITY", length = 50)
	public String getNationality() {
		return this.nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	@Column(name = "MARITAL_STATUS", length = 1)
	public String getMaritalStatus() {
		return this.maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	@Column(name = "FAMILY_STATUS", length = 1)
	public String getFamilyStatus() {
		return this.familyStatus;
	}

	public void setFamilyStatus(String familyStatus) {
		this.familyStatus = familyStatus;
	}

	@Column(name = "FAM_INC_STA", length = 1)
	public String getFamIncSta() {
		return this.famIncSta;
	}

	public void setFamIncSta(String famIncSta) {
		this.famIncSta = famIncSta;
	}

	@Column(name = "ID_TYPE", length = 32)
	public String getIdType() {
		return this.idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	@Column(name = "ID_NO", length = 32)
	public String getIdNo() {
		return this.idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "ID_ISSUE_DATE", length = 10)
	public Date getIdIssueDate() {
		return this.idIssueDate;
	}

	public void setIdIssueDate(Date idIssueDate) {
		this.idIssueDate = idIssueDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "ID_EXPIRE_DATE", length = 10)
	public Date getIdExpireDate() {
		return this.idExpireDate;
	}

	public void setIdExpireDate(Date idExpireDate) {
		this.idExpireDate = idExpireDate;
	}

	@Column(name = "ID_LOCATION")
	public String getIdLocation() {
		return this.idLocation;
	}

	public void setIdLocation(String idLocation) {
		this.idLocation = idLocation;
	}

	@Column(name = "DEGREE_TYPE", length = 32)
	public String getDegreeType() {
		return this.degreeType;
	}

	public void setDegreeType(String degreeType) {
		this.degreeType = degreeType;
	}

	@Column(name = "JOB_TYPE", length = 1)
	public String getJobType() {
		return this.jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	@Column(name = "INDUSTRY", length = 2)
	public String getIndustry() {
		return this.industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	@Column(name = "COMPANY_NAME")
	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@Column(name = "YEARS_OF_WORK")
	public Integer getYearsOfWork() {
		return this.yearsOfWork;
	}

	public void setYearsOfWork(Integer yearsOfWork) {
		this.yearsOfWork = yearsOfWork;
	}

	@Column(name = "COMP_SCALE", length = 1)
	public String getCompScale() {
		return this.compScale;
	}

	public void setCompScale(String compScale) {
		this.compScale = compScale;
	}

	@Column(name = "JOB_TITLE", length = 200)
	public String getJobTitle() {
		return this.jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	@Column(name = "MOBILE_TEL", length = 20)
	public String getMobileTel() {
		return this.mobileTel;
	}

	public void setMobileTel(String mobileTel) {
		this.mobileTel = mobileTel;
	}

	@Column(name = "FIXED_TEL", length = 20)
	public String getFixedTel() {
		return this.fixedTel;
	}

	public void setFixedTel(String fixedTel) {
		this.fixedTel = fixedTel;
	}

	@Column(name = "EMAIL", length = 200)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "COMM_ADDR", length = 40)
	public String getCommAddr() {
		return this.commAddr;
	}

	public void setCommAddr(String commAddr) {
		this.commAddr = commAddr;
	}

	@Column(name = "ZIP", length = 20)
	public String getZip() {
		return this.zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	@Transient
	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
	@Transient
	public Integer getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}
	@Transient
	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	@Transient
	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
	@Transient
	public String getAddressDetails() {
		return addressDetails;
	}

	public void setAddressDetails(String addressDetails) {
		this.addressDetails = addressDetails;
	}
	
}

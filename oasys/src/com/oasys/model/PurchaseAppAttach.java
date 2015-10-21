package com.oasys.model;

// Generated 2015-9-15 14:17:02 by Hibernate Tools 4.0.0

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @ClassName: PurchaseAppAttach 
 * @Description: TODO 物料申请附件表
 * @author PANCHUANHE
 * @date 2015年9月15日 下午2:55:08
 */
@Entity
@Table(name = "t_oa_ad_purchase_app_attach", uniqueConstraints = @UniqueConstraint(columnNames = "APP_NO"))
public class PurchaseAppAttach implements java.io.Serializable,Cloneable {
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

	public PurchaseAppAttach() {
	}

	public PurchaseAppAttach(Integer psaId) {
		this.psaId = psaId;
	}

	public PurchaseAppAttach(Integer psaId, String appNo, String articleName,
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

	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "PSA_ID", unique = true, nullable = false)
	public Integer getPsaId() {
		return this.psaId;
	}

	public void setPsaId(Integer psaId) {
		this.psaId = psaId;
	}

	@Column(name = "APP_NO", length = 25)
	public String getAppNo() {
		return this.appNo;
	}

	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}

	@Column(name = "ARTICLE_NAME", length = 50)
	public String getArticleName() {
		return this.articleName;
	}

	public void setArticleName(String articleName) {
		this.articleName = articleName;
	}

	@Column(name = "MODEL", length = 50)
	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	@Column(name = "PRICE", precision = 10)
	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Column(name = "QTY")
	public Integer getQty() {
		return this.qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	@Column(name = "TOTAL_AMT", precision = 10)
	public BigDecimal getTotalAmt() {
		return this.totalAmt;
	}

	public void setTotalAmt(BigDecimal totalAmt) {
		this.totalAmt = totalAmt;
	}

	@Column(name = "PURPOSE", length = 200)
	public String getPurpose() {
		return this.purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	@Column(name = "USER")
	public Integer getUser() {
		return this.user;
	}

	public void setUser(Integer user) {
		this.user = user;
	}

	@Column(name = "DEPOSITARY")
	public Integer getDepositary() {
		return this.depositary;
	}

	public void setDepositary(Integer depositary) {
		this.depositary = depositary;
	}

	@Column(name = "REMARK", length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
    
	@Override
	protected Object clone(){
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}
}

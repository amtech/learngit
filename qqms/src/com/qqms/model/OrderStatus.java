package com.qqms.model;



import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 订单状态实体
 * @author PANCHUANHE
 * @date 2015/6/16
 */
@Entity
@Table(name = "t_bp_order_status")
public class OrderStatus implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 订单状态ID
	 */
	private String statusId;
	/**
	 * 订单状态码
	 */
	private String statusCode;
	/**
	 * 订单状态名称
	 */
	private String statusName;
	/**
	 * 订单状态描述
	 */
	private String statusDesc;
	
	private Set<LoanOrder> loanOrders = new HashSet<LoanOrder>(0);

	public OrderStatus() {
	}

	public OrderStatus(String statusId) {
		this.statusId = statusId;
	}

	public OrderStatus(String statusId, String statusCode, String statusName,
			String statusDesc, Set<LoanOrder> loanOrders) {
		this.statusId = statusId;
		this.statusCode = statusCode;
		this.statusName = statusName;
		this.statusDesc = statusDesc;
		this.loanOrders = loanOrders;
	}

	@Id
	@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@GeneratedValue(generator = "systemUUID")
	@Column(name = "STATUS_ID", insertable = true, updatable = true, nullable = false, length = 40, unique = true)
	public String getStatusId() {
		return this.statusId;
	}

	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}

	@Column(name = "STATUS_CODE", length = 100)
	public String getStatusCode() {
		return this.statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	@Column(name = "STATUS_NAME", length = 100)
	public String getStatusName() {
		return this.statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	@Column(name = "STATUS_DESC")
	public String getStatusDesc() {
		return this.statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "orderStatus")
	@JSONField(serialize = false)
	public Set<LoanOrder> getLoanOrders() {
		return this.loanOrders;
	}

	public void setLoanOrders(Set<LoanOrder> loanOrders) {
		this.loanOrders = loanOrders;
	}

}

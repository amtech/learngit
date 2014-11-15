package com.jxc.core.web.entity;
/**
 * 模型类的基类,提供所有表中共有的属性
 * @FileName: BaseEntity.java
 * @Author: WuF
 * @Version: 1.0
 * @Date: 2012-10-23下午3:20:32
 * @Description: 
 * @Others: 
 * @FuntionList:
 * @History:
 * @Category:
 */
public class BaseEntity {
	
	protected String id;
	protected String createTime;
	protected String createdBy;
	protected String modifyTime;
	protected String modifiedBy;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
}

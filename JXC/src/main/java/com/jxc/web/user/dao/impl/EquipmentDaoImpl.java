package com.jxc.web.user.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jxc.core.web.dao.BaseDao;
import com.jxc.web.user.dao.EquipmentDao;
import com.jxc.web.user.model.EquipmentEntity;

public class EquipmentDaoImpl extends BaseDao implements EquipmentDao {

	@Override
	public List<EquipmentEntity> getList(Map map) {
		return this.getSqlSession().selectList("getList",map);
	}

	@Override
	public void updateEquipment(String equipment_no, String rent_enddate,
			BigDecimal cost_sum, String remark) {
		this.getSqlSession().update("updateEquipmentOut", new Object[]{equipment_no,rent_enddate,cost_sum,remark});
	}

	@Override
	public EquipmentEntity queryDayRent(String deptno) {
		return this.getSqlSession().selectOne("queryDayRent", deptno);
	}

	@Override
	public void updateEquipmentAfterAdd(String equipment_no,
			String rent_begindate, String rent_expectdate, String rent_enddate,
			String modify_user, String modify_time, String remark,
			BigDecimal total_rent) {
			this.getSqlSession().update("updateEquipmentAfterAdd", new Object[]{rent_begindate,rent_expectdate,rent_enddate,modify_user,modify_time,remark,total_rent});
		
	}
	//这里需要修改
	@Override
	public List<EquipmentEntity> queryIsValid(String equipment_no) {
		return this.getSqlSession().selectList("queryIsValid",equipment_no);
	}

	@Override
	public List<EquipmentEntity> queryEquipmentByNum(Map map) {
		return this.getSqlSession().selectList("queryEquipmentByNum",map);
	}

	@Override
	public List<EquipmentEntity> getAll() {
		return  this.getSqlSession().selectList("getAll");
	}

	@Override
	public int queryEquipmentRows(String counterid) {
		return this.getSqlSession().selectOne("queryEquipmentRows",counterid);
	}

	@Override
	public List<EquipmentEntity> queryEquipmentByPage(int start, int number,String counterid) {
		return this.getSqlSession().selectList("queryEquipmentByPage",new Object[]{start,number,counterid});
	}

	@Override
	public int queryEquipmentInvalidRows() {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectOne("queryEquipmentInvalidRows");
	}

	@Override
	public void addequipment(EquipmentEntity equip) {
		this.getSqlSession().insert("addequipment",equip);
	}

	@Override
	public String queryCounterName(String no) {
		return this.getSqlSession().selectOne("queryCounterName",no);
	}

	@Override
	public List<EquipmentEntity> getNowList(int start, int number,
			String counterid) {
		return this.getSqlSession().selectList("getNowList",new Object[]{start,number,counterid});
	}

	@Override
	public void addOutEquipment(String no, String counterid, String is_valid, String create_user,String create_time, String modify_user,String modify_time,String day_rent,String deposit,String rent_begindate,String rent_expectdate,String total_rent) {
		this.getSqlSession().insert("addOutEquipment",new Object[]{no,counterid,is_valid,create_user,create_time,modify_user,modify_time,day_rent,deposit,rent_begindate,rent_expectdate,total_rent});
	}

	@Override
	public String queryDepositByEtype(String equipment_type) {
		return this.getSqlSession().selectOne("queryDepositByEtype",equipment_type);
	}

	@Override
	public int isExist(String equipment_no) {
		return this.getSqlSession().selectOne("isExist",equipment_no);
	}

	@Override
	public Map<String,String> queryOutEquipDayRent(String countryvalue) {
		return this.getSqlSession().selectOne("queryOutEquipDayRent",countryvalue);
	}

	@Override
	public void updateEquipmentOut(String equipment_no, String rent_enddate,
			BigDecimal cost_sum, String remark) {
		this.getSqlSession().update("updateEquipmentOut",new Object[]{equipment_no,rent_enddate,cost_sum,remark});
	}

	@Override
	public int queryEquipmentByNumTotal(Map map) {
		return this.getSqlSession().selectOne("queryEquipmentByNumTotal",map);
	}

	@Override
	public EquipmentEntity queryEquipmentDetail(Map map) {
		return this.getSqlSession().selectOne("queryEquipmentDetail",map);
	}

	@Override
	public String querySimIsOuttime(String equipmentno) {
		return this.getSqlSession().selectOne("querySimIsOuttime",equipmentno);
	}

	@Override
	public void editequipment(EquipmentEntity equipment) {
		this.getSqlSession().update("editequipment",equipment);
	}

	@Override
	public List<EquipmentEntity> queryEqptByConditions(Map map) {
		return this.getSqlSession().selectList("queryEqptByConditions",map);
	}

	@Override
	public int queryEqptByConditionsNums(Map map) {
		return this.getSqlSession().selectOne("queryEqptByConditionsNums",map);
	}

	@Override
	public void batchInsertEqupment(List<EquipmentEntity> list) {
		this.getSqlSession().insert("batchInsertEqupment",list);
	}

	@Override
	public void updateBinding(Map map) {
		this.getSqlSession().update("updateBinding",map);
	}

	@Override
	public void batchInsertTemporatoryEqupment(List<EquipmentEntity> list) {
		this.getSqlSession().insert("batchInsertTemporatoryEqupment",list);
	}

	@Override
	public List<String> queryEqptnoTemporatory() {
		return this.getSqlSession().selectList("queryEqptnoTemporatory");
	}

	@Override
	public int queryIfDoubleData(Map map) {
		return this.getSqlSession().selectOne("queryIfDoubleData",map);
	}

	@Override
	public void clearTemporatoryEqptno() {
		this.getSqlSession().update("clearTemporatoryEqptno");
	}

	/*@Override
	public void createTemTable() {
		this.getSqlSession().update("createTemTable");
	}
*/
	/*@Override
	public void editequipmentrefresh(EquipmentEntity equipment) {
		this.getSqlSession().update("editequipmentrefresh",equipment)
	}*/
	
}

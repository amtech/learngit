package com.jxc.web.user.dao.impl;

import java.util.List;
import java.util.Map;

import com.jxc.core.web.dao.BaseDao;
import com.jxc.web.user.dao.InfoDao;
import com.jxc.web.user.model.EquipmentEntity;
import com.jxc.web.user.model.InfoEntity;
import com.jxc.web.user.model.InfologEntity;

public class InfoDaoImpl extends BaseDao implements InfoDao {

	@Override
	public void addInfo(InfoEntity info) {
		 this.getSqlSession().insert("addInfo",info);
	}

	@Override
	public List<InfoEntity> selectInfo(Map map) {
		return this.getSqlSession().selectList("selectInfo");
	}

	@Override
	public Map<String,String> queryD_country(String equipment_Sno) {
		return this.getSqlSession().selectOne("queryD_country",equipment_Sno);
	}

	@Override
	public InfoEntity queryInfoById(String id) {
		return this.getSqlSession().selectOne("queryInfoById",id);
	}

	@Override
	public void recordSimInfo(Map map) {
		this.getSqlSession().selectOne("recordSimInfo",map);
	}

	@Override
	public List<InfologEntity> querySimChildInfo(Map map) {
		return this.getSqlSession().selectList("querySimChildInfo",map);
	}

	@Override
	public int infocount() {
		return this.getSqlSession().selectOne("infocount");
	}

	@Override
	public int querySimChildInfoCount(String cardid) {
		return this.getSqlSession().selectOne("querySimChildInfoCount",cardid);
	}

	@Override
	public List<InfoEntity> queryInfoWithCondition(Map map) {
		return this.getSqlSession().selectList("queryInfoWithCondition",map);
	}

	@Override
	public int queryInfoWithConditionCounts(Map map) {
		return this.getSqlSession().selectOne("queryInfoWithConditionCounts",map);
	}

	@Override
	public void recordInsert(Map map) {
		this.getSqlSession().insert("recordInsert",map);
	}

	@Override
	public List queryOperaterVAT(Map map) {
		return this.getSqlSession().selectList("queryOperaterVAT", map);
	}

	@Override
	public int checkIsBind(Map map) {
		return this.getSqlSession().selectOne("checkIsBind",map);
	}

	@Override
	public List<EquipmentEntity> searchOfUnbindEqpt(Map map) {
		return this.getSqlSession().selectList("searchOfUnbindEqpt",map);
	}

	@Override
	public int searchOfUnbindEqptCounts(Map map) {
		return this.getSqlSession().selectOne("searchOfUnbindEqptCounts",map);
	}

	@Override
	public void updateEqptAfterInfo(Map map) {
		this.getSqlSession().update("updateEqptAfterInfo",map);
	}

	@Override
	public int checkIsExist(Map map) {
		return this.getSqlSession().selectOne("checkIsExist",map);
	}

	@Override
	public String queryInfoDayRent(Map map) {
		return this.getSqlSession().selectOne("queryInfoDayRent",map);
	}

	@Override
	public void editOfInfo(Map map) {
		this.getSqlSession().update("editOfInfo",map);
	}

	@Override
	public void recordEditInfo(Map map) {
		this.getSqlSession().insert("recordEditInfo",map);
	}

	@Override
	public void xufeioperation(InfologEntity list) {
		this.getSqlSession().update("xufeioperation",list);
	}

	@Override
	public void xufeioperationChild(List<InfologEntity> list) {
		this.getSqlSession().insert("xufeioperationChild",list);
	}

	@Override
	public void batchInportSim(List<InfoEntity> list) {
		this.getSqlSession().insert("batchInportSim",list);
	}

	@Override
	public void batchInportTemporatorySim(List<String> list) {
		this.getSqlSession().insert("batchInportTemporatorySim",list);
	}

	@Override
	public List<String> queryTemporatorySim() {
		return this.getSqlSession().selectList("queryTemporatorySim");
	}

	@Override
	public int queryIsDoubleByTemp(Map map) {
		return this.getSqlSession().selectOne("queryIsDoubleByTemp",map);
	}

	@Override
	public void deleteTemporatorySim() {
		this.getSqlSession().update("deleteTemporatorySim");
		
	}

	@Override
	public void insertDaoruAfterBatchin(Map map) {
		this.getSqlSession().insert("insertDaoruAfterBatchin",map);
	}

	@Override
	public void simcardopoen(InfoEntity info) {
		this.getSqlSession().update("simcardopoen",info);
	}

	@Override
	public void insertAftersimcardopoen(InfologEntity ife) {
		this.getSqlSession().insert("insertAftersimcardopoen",ife);
	}

	/*@Override
	public void insertDaoruAfterBatchin(List<InfologEntity> lists) {
		this.getSqlSession().insert("insertDaoruAfterBatchin",lists);
	}*/

}

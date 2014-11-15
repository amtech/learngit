package com.jxc.web.user.dao;

import java.util.List;
import java.util.Map;

import com.jxc.web.user.model.EquipmentEntity;
import com.jxc.web.user.model.InfoEntity;
import com.jxc.web.user.model.InfologEntity;

public interface InfoDao {
	//添加方法
	public void addInfo(InfoEntity info);
	//添加完成后子表记录
	public void recordInsert(Map map);
	//查询总记录
	public List<InfoEntity> selectInfo(Map map);
	//查询总条数
	public int infocount();
	//根据设备号查询目的地
	public Map<String,String> queryD_country(String equipment_Sno);
	//根据SIMID查询
	public InfoEntity queryInfoById(String id);
	//对SIM卡操作后向其子表插入数据
	public void recordSimInfo(Map map);
	//根据sim卡号查询其操作记录
	public List<InfologEntity> querySimChildInfo(Map map);
	//根据SIM卡号查询到操作记录的总条数
	public int querySimChildInfoCount(String cardid);
	//根据条件查询SIM卡
	public List<InfoEntity> queryInfoWithCondition(Map map);
	//根据条件查询SIM卡的总条数
	public int queryInfoWithConditionCounts(Map map);
	//根据条件查询运营商代码和运营商
	public List queryOperaterVAT(Map map);
	//根据卡号查询此设备是否已经绑定
	public int checkIsBind(Map map);
	//根据条件查询设备
	public List<EquipmentEntity> searchOfUnbindEqpt(Map map);
	//根据条件查询到设备的条数
	public int searchOfUnbindEqptCounts(Map map);
	//添加SIM卡后更新设备表
	public void updateEqptAfterInfo(Map map);
	//根据SIM卡查询其是否存在
	public int checkIsExist(Map map);
	//根据国家获取SIM卡日租金
	public String queryInfoDayRent(Map map);
	//编辑数据卡信息
	public void editOfInfo(Map map);
	//编辑完成后子表记录
	public void recordEditInfo(Map map);
	//续费操作
	public void xufeioperation(InfologEntity list);
	//续费后记录
	public void xufeioperationChild(List<InfologEntity> list);
	//批量添加SIM卡
	public void batchInportSim(List<InfoEntity> list);
	//将数据添加到临时表
	public void batchInportTemporatorySim(List<String> list);
	//查询临时表中的所有卡号
	public List<String> queryTemporatorySim();
	//根据卡号查询是否有重复数据
	public int queryIsDoubleByTemp(Map map);
	//删除临时表中的数据
	public void deleteTemporatorySim();
	//导入完成后更新子表
//	public void insertDaoruAfterBatchin(List<InfologEntity> lists);
	public void insertDaoruAfterBatchin(Map ie);
	//批量开卡
	public void simcardopoen(InfoEntity info);
	//批量开卡后插入子表
	public void insertAftersimcardopoen(InfologEntity ife);
}

package com.jxc.web.user.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jxc.web.user.dao.InfoDao;
import com.jxc.web.user.model.EquipmentEntity;
import com.jxc.web.user.model.InfoEntity;
import com.jxc.web.user.model.InfologEntity;

@Service
public class InfoService {
	@Autowired
	private InfoDao infodao;
	/**
	 * 添加方法
	 * @param info
	 */
	public void addInfo(InfoEntity info){
		 Map map = new HashMap();
		 map.put("card_id", info.getId());
		 map.put("operation_user", info.getCreate_user());
		 map.put("operation_time", info.getCreate_time());
		 map.put("remark", info.getRemark());
		 infodao.addInfo(info);
		 infodao.recordInsert(map);
	}
	/**
	 * 获取列表方法
	 * @return
	 */
	public List<InfoEntity> getList(Map map){
		return infodao.selectInfo(map);
	}
	/**
	 * 查询总记录数
	 * @return
	 */
	public int infocount(){
		return infodao.infocount();
	}
	/**
	 * 根据设备号查询目的地
	 * @param equipment_no
	 * @return
	 */
	public Map<String,String> queryD_country(String equipment_no) {
		return infodao.queryD_country(equipment_no);
	}
	/**
	 * 根据SIMID查询SIM卡
	 * @param id
	 * @return
	 */
	public InfoEntity queryInfoById(String id) {
		return infodao.queryInfoById(id);
	}
	/**
	 * 对SIM卡操作后向其子表插入数据
	 * @param map
	 */
	public void recordSimInfo(Map map){
		infodao.recordSimInfo(map);
	}
	/**
	 * 根据SIM卡号查询其操作记录
	 * @param cardid
	 * @return
	 */
	public List<InfologEntity> querySimChildInfo(Map map){
		return infodao.querySimChildInfo(map);
	}
	/**
	 * 根据SIM卡号查询到操作记录的总条数
	 * @param cardid
	 * @return
	 */
	public int querySimChildInfoCount(String cardid){
		return infodao.querySimChildInfoCount(cardid);
	}
	/**
	 * 根据条件查询SIM卡
	 * @param map
	 * @return
	 */
	public List<InfoEntity> queryInfoWithCondition(Map map){
		return infodao.queryInfoWithCondition(map);
	}
	/**
	 * 根据条件查询到SIM卡的总条数
	 * @param map
	 * @return
	 */
	public int queryInfoWithConditionCounts(Map map){
		return infodao.queryInfoWithConditionCounts(map);
	}
	/**
	 * 根据条件查询运营商及其代码
	 * @param map
	 * @return
	 */
	public List queryOperaterVAT(Map map){
		return infodao.queryOperaterVAT(map);
	}
	/**
	 * 根据SIM卡查询其是否已经绑定
	 * @param map
	 * @return
	 */
	public int checkIsBind(Map map ){
		return infodao.checkIsBind(map);
	}
	/**
	 * 根据条件查询设备
	 * @param map
	 * @return
	 */
	public List<EquipmentEntity> searchOfUnbindEqpt(Map map){
		return infodao.searchOfUnbindEqpt(map);
	}
	/**
	 * 根据条件查询到设备的总条数
	 * @param map
	 * @return
	 */
	public int searchOfUnbindEqptCounts(Map map){
		return infodao.searchOfUnbindEqptCounts(map);
	}
	/**
	 * 添加SIM卡后更新设备表
	 * @param map
	 */
	public void updateEqptAfterInfo(Map map){
		infodao.updateEqptAfterInfo(map);
	}
	/**
	 * 根据SIM卡查询其是否存在
	 * @param map
	 * @return
	 */
	public int checkIsExist(Map map){
		return infodao.checkIsExist(map);
	}
	/**
	 * 根据国家查询SIM卡日租金
	 * @param map
	 * @return
	 */
	public String queryInfoDayRent(Map map){
		return infodao.queryInfoDayRent(map);
	}
	/**
	 * 编辑数据卡信息
	 * @param map
	 */
	public void editOfInfo(Map map){
		infodao.editOfInfo(map);
	}
	/**
	 * 将编辑信息记录到子表
	 * @param map
	 */
	public void recordEditInfo(Map map){
		infodao.recordEditInfo(map);
	}
	public void xufeioperation(List<InfologEntity> list){
		for(InfologEntity ief : list){
			infodao.xufeioperation(ief);
		}
		infodao.xufeioperationChild(list);
	}
	public void batchInportSim(List<InfoEntity> list){
		infodao.batchInportSim(list);
		//List<InfologEntity> lists = new ArrayList<InfologEntity>();
		for(InfoEntity ie:list){
//			InfologEntity ife = new InfologEntity();
			Map map = new HashMap();
//			ife.setCard_id(ie.getId());
//			ife.setOperation_user(ie.getCreate_user());
//			ife.setOperation_time(ie.getCreate_time());
//			lists.add(ife);
			map.put("card_id", ie.getId());
			map.put("operation_user", ie.getCreate_user());
			map.put("operation_time", ie.getCreate_time());
			infodao.insertDaoruAfterBatchin(map);
		}
		//System.out.println(lists);
		//infodao.insertDaoruAfterBatchin(lists);
	}
	public void batchInportTemporatorySim(List<String> list){
		infodao.deleteTemporatorySim();
		infodao.batchInportTemporatorySim(list);
	}
	public List<String> queryTemporatorySim(){
		return infodao.queryTemporatorySim();
	}
	public int queryIsDoubleByTemp(Map map){
		return infodao.queryIsDoubleByTemp(map);
	}
	
	public void simcardopoen(InfoEntity info){
		 infodao.simcardopoen(info);
		 InfologEntity ife = new InfologEntity();
		 ife.setCard_id(info.getId());
		 ife.setOperation_time(info.getModify_time());
		 ife.setOperation_user(info.getModify_user());
		 ife.setRemark(info.getRemark());
		 infodao.insertAftersimcardopoen(ife);
	}
}

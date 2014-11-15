package com.jxc.web.user.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jxc.web.user.dao.CounterDao;
import com.jxc.web.user.model.CounterEntity;

@Service
public class CounterService {
	@Autowired
	private CounterDao counterdao;
	/**
	 * 添加管理员
	 * @param counter
	 */
	public void addCounter(CounterEntity counter){
		counterdao.add(counter);
	}
	/**
	 * 获取管理员信息
	 * @return
	 */
	public List<CounterEntity> getList(){
		return counterdao.getList();
	}
	
	public List getHeadId(Map map){
		return counterdao.getHeadId(map);
	}
}

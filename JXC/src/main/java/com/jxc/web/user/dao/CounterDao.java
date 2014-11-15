package com.jxc.web.user.dao;

import java.util.List;
import java.util.Map;

import com.jxc.web.user.model.CounterEntity;

public interface CounterDao {
		//添加方法
		public void add(CounterEntity counter);
		//查询方法
		public List<CounterEntity> getList();
		//根据柜台号查询此柜台的主管
		public List getHeadId(Map map);
}	

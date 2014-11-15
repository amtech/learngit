package com.jxc.web.user.dao.impl;

import java.util.List;
import java.util.Map;

import com.jxc.core.web.dao.BaseDao;
import com.jxc.web.user.dao.CounterDao;
import com.jxc.web.user.model.CounterEntity;

public class CounterDaoImpl extends BaseDao implements CounterDao {

	@Override
	public void add(CounterEntity counter) {
		this.getSqlSession().insert("addCounter", counter);

	}

	@Override
	public List<CounterEntity> getList() {
		return this.getSqlSession().selectList("selectCounter");
	}

	@Override
	public List getHeadId(Map map) {
		return this.getSqlSession().selectList("getHeadId",map);
	}

}

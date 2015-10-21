package com.oasys.service;

import java.util.List;

import com.oasys.model.SystemCode;
import com.oasys.viewModel.TreeModel;

public interface SystemCodeService
{

	List<SystemCode> findSystemCodeList(Integer id);

	List<TreeModel> findSystemCodeList();

	boolean persistenceSystemCodeDig(SystemCode systemCode,String permissionName,Integer codePid);

	boolean delSystemCode(Integer codeId );

	List<SystemCode> findSystemCodeByType(String codeMyid );
	
	public boolean isExistsCode(SystemCode systemCode);

}

package com.oasys.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Service;

import com.oasys.service.BackupScheduleService;
import com.oasys.service.DbBackUpService;
import com.oasys.util.DBBackUpUtil;
@Service("backupScheduleService")
public class BackupScheduleServiceImpl implements BackupScheduleService
{
	/* (非 Javadoc) 
	* <p>Title: execute</p> 
	* <p>Description:备份调度执行方法 </p> 
	* @param jobCtx
	* @throws JobExecutionException 
	* @see org.quartz.Job#execute(org.quartz.JobExecutionContext) 
	*/
	public void execute(JobExecutionContext jobCtx ) throws JobExecutionException
	{
		String fineName = DBBackUpUtil.dbBackUp();
		String sqlName = DBBackUpUtil.getMkdirsPath()+ fineName;
		System.out.println(jobCtx.getTrigger().getName()   + " triggered. time is:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));  
		SpringWiredBean sdf = SpringWiredBean.getInstance();
		DbBackUpService sdsdf = (DbBackUpService)sdf.getBeanById("dbBackUpService");
		sdsdf.addLog(sqlName,fineName,true);
	}
}

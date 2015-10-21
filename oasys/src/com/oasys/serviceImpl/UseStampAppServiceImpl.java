package com.oasys.serviceImpl;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.ActivitiTaskAlreadyClaimedException;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oasys.dao.PublicDao;
import com.oasys.model.AuditProcHis;
import com.oasys.model.StatusNameRef;
import com.oasys.model.UseStampApp;
import com.oasys.service.AuditProcHisService;
import com.oasys.service.OrganizationService;
import com.oasys.service.StatusNameRefService;
import com.oasys.service.UseStampAppService;
import com.oasys.service.UserService;
import com.oasys.service.WorkFlowTaskService;
import com.oasys.util.Collections;
import com.oasys.util.Constants;
import com.oasys.util.PageUtil;
import com.oasys.util.UniqueIdUtil;
import com.oasys.viewModel.UsersModel;
import com.oasys.viewModel.WorkFlowTasksModel;

@Service("useStampAppService")
@SuppressWarnings("unchecked")
public class UseStampAppServiceImpl  extends WorkFlowBaseServiceImpl  implements UseStampAppService
{

	
	@Autowired
	private WorkFlowTaskService workFlowTaskService;
	
	@Autowired
	private PublicDao<UseStampApp> useStampAppDao;
	
	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private UserService userService;
	@Autowired
	private  StatusNameRefService  statusNameRefService;
	@Autowired
	private AuditProcHisService auditProcHisService;
	
	@Override
	public List<UseStampApp> findUseStampAppAttList(UseStampApp useStampApp,PageUtil pageUtil) {
		
		try {
			Integer userId = Constants.getCurrendUser().getUserId();
			SimpleDateFormat djr = new SimpleDateFormat("yyyy-MM-dd");
			//用章申请列表sql语句
			StringBuffer sql = getUseStampSql();
			if(userId!=1){
				sql.append(" AND us.APPLICANT_NO = "+userId);
			}
			
			if(StringUtils.isNotBlank(useStampApp.getAppNo())){
				sql.append(" AND us.APP_NO='"+useStampApp.getAppNo()+"' ");
			}else{
				if(StringUtils.isNotBlank(useStampApp.getAppDateS())){
					sql.append(" AND us.APP_DATE >='" + useStampApp.getAppDateS()+"' ") ;  //申请开始日期
				}
				if(StringUtils.isNotBlank(useStampApp.getAppDateE())){
					sql.append(" AND us.APP_DATE <='" + useStampApp.getAppDateE()+"' ") ;  //申请结束日期
				}
				if(StringUtils.isNotBlank(useStampApp.getProcStatus())){
					sql.append(" AND us.PROC_STATUS='"+useStampApp.getProcStatus()+"' ");
				}
			}
			sql.append(" ORDER BY us.USA_ID DESC ");
			//执行sql语句
			List<Object> objList = useStampAppDao.findBySql(sql.toString(), pageUtil);
			List<UseStampApp> useStampList=new ArrayList<UseStampApp>();
			if(Collections.listIsNotEmpty(objList)){
				UseStampApp useStamp=new UseStampApp();
				for (int i = 0; i < objList.size(); i++) {
					UseStampApp cmodel=(UseStampApp) useStamp.clone();
					Object[] obj = (Object[]) objList.get(i);
					
					cmodel.setUsaId(obj[0] == null?0:(Integer)obj[0]);
					cmodel.setAppNo(obj[1]==null?"":String.valueOf(obj[1]));
					cmodel.setFullName(obj[2]==null?"":String.valueOf(obj[2]));
					cmodel.setApplicantNo(obj[3]==null?0:(Integer)obj[3]);
					cmodel.setApplicantName(obj[4]==null?"":String.valueOf(obj[4]));
					cmodel.setStampType(obj[5]==null?"":String.valueOf(obj[5]));
					
					//判断印章类型如果是其他类型，显示自定义印章类型
					if(String.valueOf(obj[5]).equals("E")){
						cmodel.setDictName(obj[6]==null?"":String.valueOf(obj[6]));//自定义印章类型
					}else{
						cmodel.setDictName(obj[7]==null?"":String.valueOf(obj[7]));//规定的印章
					};
					cmodel.setStampTypeOther(obj[6]==null?"":String.valueOf(obj[6]));
					
					cmodel.setUsBeginDate(obj[8]==null?"":String.valueOf(obj[8]));
					cmodel.setUsEndDate(obj[9]==null?"":String.valueOf(obj[9]));
					cmodel.setUsReason(obj[10]==null?"":String.valueOf(obj[10]));
					cmodel.setRemark(obj[11]==null?"":String.valueOf(obj[11]));
					cmodel.setAppDate(obj[12]==null?null:djr.parse(String.valueOf(obj[12])));
					cmodel.setAppStatus(obj[13]==null?"":String.valueOf(obj[13]));
					cmodel.setAppStateName(obj[14]==null?"":String.valueOf(obj[14]));
					cmodel.setProcStatus(obj[15]==null?"":String.valueOf(obj[15]));
					cmodel.setUsQuantity(obj[16]==null?0:(Integer)obj[16]);
					cmodel.setDeptNo(obj[17]==null?0:(Integer)obj[17]);
					
					useStampList.add(cmodel);
				}
			}
			return useStampList;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	

	@Override
	public Long getTotal(UseStampApp useStampApp) {
		try {
			StringBuffer sql =new StringBuffer();
			sql.append("SELECT COUNT(*) FROM t_oa_pd_use_stamp_app us WHERE 1=1 ");
			Integer userId = Constants.getCurrendUser().getUserId();
			if(userId!=1){
				sql.append(" AND us.APPLICANT_NO = "+userId);
			}
			
			if(StringUtils.isNotBlank(useStampApp.getAppNo())){
				sql.append(" AND us.APP_NO='"+useStampApp.getAppNo()+"' ");
			}else{
				if(StringUtils.isNotBlank(useStampApp.getAppDateS())){
					sql.append(" AND us.APP_DATE >='" + useStampApp.getAppDateS()+"' ") ;  //申请开始日期
				}
				if(StringUtils.isNotBlank(useStampApp.getAppDateE())){
					sql.append(" AND us.APP_DATE <='" + useStampApp.getAppDateE()+"' ") ;  //申请结束日期
				}
				if(StringUtils.isNotBlank(useStampApp.getProcStatus())){
					sql.append(" AND us.PROC_STATUS='"+useStampApp.getProcStatus()+"' ");
				}
			}
			sql.append(" ORDER BY us.USA_ID DESC ");
			Long count = useStampAppDao.findTotalCount(sql.toString());
			return count;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0L;
	}

	@Override
	public UsersModel findFullAppName() {
		Integer userId=Constants.getCurrendUser().getUserId();
		UsersModel usersModel = userService.findUserOrgById(userId);	
		return usersModel;
	}
	
	@Override
	public boolean saveUseStamp(UseStampApp useStampApp) {
		try {
			
			if(useStampApp.getUsaId()==null){
				//添加申请人id及部门id
				UsersModel usersModel = this.findFullAppName();
				useStampApp.setApplicantNo(usersModel.getUserId());
				useStampApp.setDeptNo(usersModel.getOrganizeId());
				useStampApp.setDeptNo(usersModel.getOrganizeId());
				//添加申请编号
				String appNo=UniqueIdUtil.generate("YZ");
				useStampApp.setAppNo(appNo);
				//添加流程申请
				useStampApp.setProcStatus("1");
				useStampAppDao.save(useStampApp);
				
			}else{
				useStampAppDao.saveOrUpdate(useStampApp);
			}
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deleteUseStamp(Integer usaId) {
		try {
			useStampAppDao.executeHql("delete from UseStampApp where id in ("+usaId+")");
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public UseStampApp finduseStampAppByUsaId(Integer usaId) {
		UseStampApp stampApp = useStampAppDao.get(UseStampApp.class, usaId);
		return stampApp;
	}

	@Override
	public void upBadgeProcStatus(Integer usaId, String procStatus) {
		UseStampApp useStampApp = useStampAppDao.get(UseStampApp.class, usaId);
		useStampApp.setProcStatus(procStatus);
		useStampAppDao.saveOrUpdate(useStampApp);
	}

	@Override
	public void upBadgeAppStatus(Integer usaID, String appStatus) {
		UseStampApp useStampApp = useStampAppDao.get(UseStampApp.class, usaID);
		useStampApp.setAppStatus(appStatus);
		useStampAppDao.saveOrUpdate(useStampApp);
	}

	
	
	//-------------------------------    用章申请任务办理              ---------------------
	
	
	
	@Override
	public boolean addUseStampAppTask(Integer usaId) {
		// TODO Auto-generated method stub
		// 获取启动实例的key
		String proDefKey = Constants.USESTAMPAPP;
		// 定义businessKey
		String businessKey = proDefKey + "." + usaId;
		// 指定流程变量
		Map<String, Object> variables = new HashMap<String, Object>();
		String userId = String.valueOf(Constants.getCurrendUser().getUserId());
		variables.put(Constants.CURRENT_USER_KEY, userId);
		// 启动流程实例
		ProcessInstance pi = this.runtimeService.startProcessInstanceByKey(
				proDefKey, businessKey, variables);
		if(pi!=null){
			//申请时间
			UseStampApp useStampApp = useStampAppDao.get(UseStampApp.class, usaId);
			useStampApp.setAppDate(new Date());
			useStampAppDao.saveOrUpdate(useStampApp);
			return true;
		}
		return false;
	}

	@Override
	public void getDiagramResourceByPaId(HttpServletResponse response,
			Integer usaId) {
		// 图片的文件的流
				InputStream in = null;
				try {
					String proDefKey = Constants.USESTAMPAPP;
					String imgName ="OA_PD_UseStampApp";
					
					String businessKey = proDefKey + "." + usaId;
					// 获取当前执行的流程实例
					ProcessInstance pi = this.runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(businessKey).singleResult();
					// 获取流程定义的实体对象（对应.bpmn文件中的数据）
					ProcessDefinitionEntity pd = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(pi.getProcessDefinitionId());
					// 获取当前执行任务流程
					List<Task> tasks = this.taskService.createTaskQuery().processInstanceBusinessKey(businessKey).list();
					// 获取图片的流程图片名称
					String resourceName = imgName + ".png";
					// 获取图片的文件的流
					in = this.repositoryService.getResourceAsStream(pd.getDeploymentId(),resourceName);
					// 获取图片对象
					BufferedImage bimg;
					bimg = ImageIO.read(in);
					// 得到Graphics2D 对象
					Graphics2D g2d = (Graphics2D) bimg.getGraphics();
					// 设置颜色和画笔粗细
					g2d.setColor(Color.RED);
					g2d.setStroke(new BasicStroke(3));
					// 遍历执行的任务,画图
					for (Task task : tasks) {
						// 根据任务的获取当前执行对象的id,根据执行对象的id获取执行对象的信息
						Execution execution = runtimeService.createExecutionQuery().executionId(task.getExecutionId()).singleResult();
						// 根据当前的执行对象的id获取正在执行的活动信息
						ActivityImpl activityImpl = pd.findActivity(execution.getActivityId());
						// 绘制矩形
						Rectangle2D rectangle = new Rectangle2D.Float(
								activityImpl.getX(), activityImpl.getY(),
								activityImpl.getWidth(), activityImpl.getHeight());
						g2d.draw(rectangle);
					}
					// 写入response输出流中
					ImageIO.write(bimg, "png", response.getOutputStream());
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (in != null)
						try {
							in.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}
	
	}

	@Override
	public List<UseStampApp> findUseStampAppClimTask(UseStampApp useStampApp,
			PageUtil pageUtil) {
		try {
			SimpleDateFormat djr = new SimpleDateFormat("yyyy-MM-dd");
			WorkFlowTasksModel taskModel = new WorkFlowTasksModel();
			taskModel.setProcessKey(Constants.USESTAMPAPP);
			List<WorkFlowTasksModel> workList = workFlowTaskService.findAcceptTaskByGroup(taskModel);
			if(null == workList || workList.size() == 0)return new ArrayList<UseStampApp>();
			StringBuffer sql = this.getUseStampSql();
			sql.append(" AND  us.USA_ID in   ("+getTaskPPEids(workList)+") ");
			if(StringUtils.isNotBlank(useStampApp.getAppNo())){
				sql.append(" AND us.APP_NO='"+useStampApp.getAppNo()+"' ");
			}else{
				if(StringUtils.isNotBlank(useStampApp.getAppDateS())){
					sql.append(" AND us.APP_DATE >='" + useStampApp.getAppDateS()+"' ") ;  //申请开始日期
				}
				if(StringUtils.isNotBlank(useStampApp.getAppDateE())){
					sql.append(" AND us.APP_DATE <='" + useStampApp.getAppDateE()+"' ") ;  //申请结束日期
				}
				if(StringUtils.isNotBlank(useStampApp.getProcStatus())){
					sql.append(" AND us.PROC_STATUS='"+useStampApp.getProcStatus()+"' ");
				}
			}
			sql.append(" ORDER BY us.USA_ID DESC ");
			//执行sql语句
			List<Object> objList = useStampAppDao.findBySql(sql.toString(), pageUtil);
			List<UseStampApp> useStampList=new ArrayList<UseStampApp>();
			if(Collections.listIsNotEmpty(objList)){
				UseStampApp useStamp=new UseStampApp();
				for (int i = 0; i < objList.size(); i++) {
					UseStampApp cmodel=(UseStampApp) useStamp.clone();
					Object[] obj = (Object[]) objList.get(i);
					
					cmodel.setUsaId(obj[0] == null?0:(Integer)obj[0]);
					cmodel.setAppNo(obj[1]==null?"":String.valueOf(obj[1]));
					cmodel.setFullName(obj[2]==null?"":String.valueOf(obj[2]));
					cmodel.setApplicantNo(obj[3]==null?0:(Integer)obj[3]);
					cmodel.setApplicantName(obj[4]==null?"":String.valueOf(obj[4]));
					cmodel.setStampType(obj[5]==null?"":String.valueOf(obj[5]));
					
					//判断印章类型如果是其他类型，显示自定义印章类型
					if(String.valueOf(obj[5]).equals("E")){
						cmodel.setDictName(obj[6]==null?"":String.valueOf(obj[6]));//自定义印章类型
					}else{
						cmodel.setDictName(obj[7]==null?"":String.valueOf(obj[7]));//规定的印章
					};
					cmodel.setStampTypeOther(obj[6]==null?"":String.valueOf(obj[6]));
					
					cmodel.setUsBeginDate(obj[8]==null?"":String.valueOf(obj[8]));
					cmodel.setUsEndDate(obj[9]==null?"":String.valueOf(obj[9]));
					cmodel.setUsReason(obj[10]==null?"":String.valueOf(obj[10]));
					cmodel.setRemark(obj[11]==null?"":String.valueOf(obj[11]));
					cmodel.setAppDate(obj[12]==null?null:djr.parse(String.valueOf(obj[12])));
					cmodel.setAppStatus(obj[13]==null?"":String.valueOf(obj[13]));
					cmodel.setAppStateName(obj[14]==null?"":String.valueOf(obj[14]));
					cmodel.setProcStatus(obj[15]==null?"":String.valueOf(obj[15]));
					cmodel.setUsQuantity(obj[16]==null?0:(Integer)obj[16]);
					cmodel.setDeptNo(obj[17]==null?0:(Integer)obj[17]);
					
					useStampList.add(cmodel);
				}
			}
			//添加任务相关数据
			for (WorkFlowTasksModel wl : workList) {
				for (UseStampApp stampApp : useStampList) {
					if( Integer.valueOf(wl.getBusinessKey()).intValue() == stampApp.getUsaId().intValue() ){
						stampApp.setTaskModel(wl);
						stampApp.setTaskId(wl.getTaskID());
						stampApp.setAssistant(wl.getAssistant());
						stampApp.setFormKey(wl.getFormKey());
					}
				}
			}
			return useStampList;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Long getTaskTotal(UseStampApp useStampApp) {
		try {
			WorkFlowTasksModel taskModel = new WorkFlowTasksModel();
			taskModel.setProcessKey(Constants.USESTAMPAPP);
			List<WorkFlowTasksModel> workList = workFlowTaskService.findAcceptTaskByGroup(taskModel);
			if(null == workList || workList.size() == 0)return 0L;
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT COUNT(*) FROM t_oa_pd_use_stamp_app us WHERE 1=1 ");
			sql.append(" AND  us.USA_ID in   ("+getTaskPPEids(workList)+") ");
			if(StringUtils.isNotBlank(useStampApp.getAppNo())){
				sql.append(" AND us.APP_NO='"+useStampApp.getAppNo()+"' ");
			}else{
				if(StringUtils.isNotBlank(useStampApp.getAppDateS())){
					sql.append(" AND us.APP_DATE >='" + useStampApp.getAppDateS()+"' ") ;  //申请开始日期
				}
				if(StringUtils.isNotBlank(useStampApp.getAppDateE())){
					sql.append(" AND us.APP_DATE <='" + useStampApp.getAppDateE()+"' ") ;  //申请结束日期
				}
				if(StringUtils.isNotBlank(useStampApp.getProcStatus())){
					sql.append(" AND us.PROC_STATUS='"+useStampApp.getProcStatus()+"' ");
				}
			}
			Long count = useStampAppDao.findTotalCount(sql.toString());
			return count;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0L;
	}
	
	
	
	//用章申请列表sql语句
		public StringBuffer getUseStampSql(){
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT ");
			sql.append("us.USA_ID '申请id',");
			sql.append("us.APP_NO '申请编号', ");
			sql.append("rr.FULL_NAME '部门', ");
			sql.append("us.APPLICANT_NO '申请人id', ");
			sql.append(" u.`NAME` '申请人名字',");
			sql.append("us.STAMP_TYPE '印章类型', ");
			sql.append("us.STAMP_TYPE_OTHER '其他印章', ");
			sql.append("s.DICT_NAME '印章名字', ");
			sql.append("us.US_BEGIN_DATE '用章开始日期', ");
			sql.append("us.US_END_DATE '用章结束日期', ");
			sql.append("us.US_REASON '用章事由', ");
			sql.append("us.REMARK '备注', ");
			sql.append("us.APP_DATE '申请日期', ");
			sql.append("us.APP_STATUS '申请状态', ");
			sql.append(" sr.APP_STATUS_NAME '申请状态名称', ");
			sql.append(" us.PROC_STATUS '流程状态', ");
			sql.append("us.US_QUANTITY '用章数量', ");
			sql.append("us.DEPT_NO '部门id' ");
			sql.append(" FROM t_oa_pd_use_stamp_app us ");
			sql.append(" LEFT JOIN qqms.t_users u ON us.APPLICANT_NO = u.USER_ID ");
			sql.append(" LEFT JOIN qqms.t_organization rr ON u.ORGANIZATION_ID = rr.ORGANIZATION_ID ");
			sql.append(" LEFT JOIN t_oa_app_status_name_ref sr ON us.APP_STATUS=sr.APP_STATUS_CODE ");
			sql.append(" LEFT JOIN (SELECT DICT_CODE,DICT_NAME FROM qqms.t_sys_dict WHERE PARENT_ID = ( SELECT CODE_ID FROM qqms.t_sys_dict WHERE DICT_CODE = 'stamp_type')) s ON s.DICT_CODE = us.STAMP_TYPE ");
			sql.append(" WHERE 1=1 ");
			return sql;
		}
	
		//获取task中工牌申请表的id集合
		private String getTaskPPEids(List<WorkFlowTasksModel> workList){
			String ids = "";
			for (WorkFlowTasksModel workFlowTasksModel : workList) {
				ids+=workFlowTasksModel.getBusinessKey()+",";
			}
			if(ids.length()>0){
				ids = ids.substring(0, ids.length()-1);
			}
			return ids;
		}



		@Override
		public void getTaskUserClaim(String taskId)
				throws ActivitiTaskAlreadyClaimedException {
			//领取人id
			Integer userId=Constants.getCurrendUser().getUserId();
			this.taskService.claim(taskId, String.valueOf(userId));
			
		}



		@Override
		public boolean setUseStampTask(UseStampApp useStampApp) {
			
			try {
				String appstatus=useStampApp.getResult();	
				Task task = this.taskService.createTaskQuery().taskId(useStampApp.getTaskId()).singleResult();// 获取当前执行任务
				
				//更改工牌申请主表信息
				UseStampApp stampApp = useStampAppDao.get(UseStampApp.class, useStampApp.getUsaId());
				stampApp.setAppStatus(appstatus);
				//当申请流程为撤销时，更改流程状态
				if( StringUtils.equals(appstatus,"RevokeApply")){
					stampApp.setProcStatus("5");
				}
				useStampAppDao.saveOrUpdate(stampApp);

				//执行任务
				Map<String, Object> variables = new HashMap<String, Object>();
				if(StringUtils.isNotBlank(appstatus)){
					variables.put("result", appstatus);// 设置流程变量
				}
				taskService.complete(useStampApp.getTaskId(), variables);//向task发送消息 跳转下一流程
				
				//填写审批意见表
				StatusNameRef statusNameRef = statusNameRefService.getstatusNameRefByStatusCode(Constants.USESTAMPAPP+"_"+appstatus);
				AuditProcHis auditProcHis=new AuditProcHis();
				auditProcHis.setAppNo(useStampApp.getAppNo());
				auditProcHis.setAppStatus(statusNameRef.getRefId());
				auditProcHis.setHandleDate(new Date());
				auditProcHis.setHandler(Constants.getCurrendUser().getUserId());
				auditProcHis.setHandleResult(useStampApp.getIsSuccess());
				//获取当前审批人角色
				String roleCode = workFlowTaskService.getRoleCodeByUserID(String.valueOf(Constants.getCurrendUser().getUserId()));
				auditProcHis.setHandlerRole(roleCode);
				
				auditProcHis.setRemark(useStampApp.getIdeaRemark());
				auditProcHisService.saveAuditProcHis(auditProcHis);
				return true;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return false;
		}


		



	
	
	
	
}

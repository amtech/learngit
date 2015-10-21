package com.oasys.service;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.oasys.model.AuditProcHis;
import com.oasys.model.PurchaseApp;
import com.oasys.util.PageUtil;
import com.oasys.viewModel.PurchaseAppModel;

/**
 * @ClassName: PurchaseAppService 
 * @Description: TODO 物料申请service
 * @author PANCHUANHE
 * @date 2015年9月16日 下午1:43:58
 */
public interface PurchaseAppService {

	/**
	 * @Title: savePurchaseApp 
	 * @Description: TODO
	 * @param @param purchaseApp 新增
	 * @param @return
	 * @author PANCHUANHE
	 * @return boolean
	 * @date 2015年9月16日 下午1:45:55
	 * @throws
	 */
	boolean savePurchaseApp(PurchaseApp purchaseApp);
	/**
	 * @Title: updatePurchaseAppTotalAmt 
	 * @Description: TODO 更新总金额
	 * @param @param count
	 * @author PANCHUANHE
	 * @return void
	 * @date 2015年9月17日 下午3:00:12
	 * @throws
	 */
	void updatePurchaseAppTotalAmt(String appNo,BigDecimal count);
	/**
	 * @Title: findPurchaseAppList 
	 * @Description: TODO 查询列表
	 * @param @return
	 * @author PANCHUANHE
	 * @return List<PurchaseApp>
	 * @date 2015年9月17日 下午3:33:26
	 * @throws
	 */
	List<PurchaseAppModel> findPurchaseAppList(PageUtil pageUtil,PurchaseAppModel purchaseAppModel);
	/**
	 * @Title: countPurchaseAppList 
	 * @Description: TODO 分页需要的总条数
	 * @param @return
	 * @author PANCHUANHE
	 * @return Long
	 * @date 2015年9月17日 下午5:08:34
	 * @throws
	 */
	Long countPurchaseAppList();
	/**
	 * @Title: delPurchaseAppByPaId 
	 * @Description: TODO 根据id删除
	 * @param @param paId
	 * @param @return
	 * @author PANCHUANHE
	 * @return boolean
	 * @date 2015年9月18日 下午2:05:58
	 * @throws
	 */
	boolean delPurchaseAppByPaId(Integer paId);
	/**
	 * 根据appNo查询单个实体
	 * @Title: findPurchaseAppByPaId 
	 * @Description: TODO
	 * @param @param appNo
	 * @param @return
	 * @author PANCHUANHE
	 * @return PurchaseApp
	 * @date 2015年9月18日 下午4:35:45
	 * @throws
	 */
	PurchaseApp findPurchaseAppByAppNo(String appNo);
	/**
	 * 开启流程
	 * @Title: startWorkflow 
	 * @Description: TODO
	 * @param @param purchaseApp
	 * @param @return
	 * @author PANCHUANHE
	 * @return boolean
	 * @date 2015年9月22日 下午5:17:32
	 * @throws
	 */
	boolean startWorkflow(PurchaseApp purchaseApp);
	/**
	 * 跟新订单状态
	 * @Title: updatePurchaseAppStatus 
	 * @Description: TODO
	 * @param @param paId
	 * @param @param state
	 * @author PANCHUANHE
	 * @return void
	 * @date 2015年9月22日 下午6:33:15
	 * @throws
	 */
	void updatePurchaseAppStatus(Integer paId,String state);
	/**
	 * 查询所有待办任务
	 * @Title: findAllPurchaseAppTaskList 
	 * @Description: TODO
	 * @param @return
	 * @author PANCHUANHE
	 * @return List<PurchaseApp>
	 * @date 2015年9月22日 下午8:38:52
	 * @throws
	 */
	List<PurchaseAppModel> findAllPurchaseAppTaskList(Integer firstResult,Integer maxResults);
	/**
	 * 查询所有待办任务count
	 * @Title: countAllPurchaseAppTaskList 
	 * @Description: TODO
	 * @param @return
	 * @author PANCHUANHE
	 * @return Long
	 * @date 2015年9月23日 下午1:44:20
	 * @throws
	 */
	Long countAllPurchaseAppTaskList();
	/**
	 * 签收任务
	 * @Title: saveHoldWorkTask 
	 * @Description: TODO
	 * @param @param taskId
	 * @param @return
	 * @author PANCHUANHE
	 * @return boolean
	 * @date 2015年9月23日 下午3:29:33
	 * @throws
	 */
	boolean saveHoldWorkTask(String taskId);
	/**
	 * 受理任务列表总条数
	 * @Title: countMyWorkTask 
	 * @Description: TODO
	 * @param @return
	 * @author PANCHUANHE
	 * @return Long
	 * @date 2015年9月23日 下午6:36:28
	 * @throws
	 */
	Long countMyWorkTask();
	/**
	 * （分部）办理任务(通过/驳回)
	 * @Title: saveSubmitTask 
	 * @Description: TODO
	 * @param @param purchaseAppModel
	 * @param @param result
	 * @param @param comment
	 * @param @return
	 * @author PANCHUANHE
	 * @return boolean
	 * @date 2015年9月24日 上午11:36:39
	 * @throws
	 */
	boolean saveSubmitTaskBo(PurchaseAppModel purchaseAppModel,String result,AuditProcHis auditProcHis);
	/**
	 * （总部）办理任务(通过/驳回)
	 * @Title: saveSubmitTaskHo 
	 * @Description: TODO
	 * @param @param purchaseAppModel
	 * @param @param result
	 * @param @param auditProcHis
	 * @param @return
	 * @author PANCHUANHE
	 * @return boolean
	 * @date 2015年10月9日 上午10:13:07
	 * @throws
	 */
	boolean saveSubmitTaskHo(PurchaseAppModel purchaseAppModel,String result,AuditProcHis auditProcHis);
	/**
	 * 查询申请人属于总部还是分部
	 * @Title: findZongBuOrFenBu 
	 * @Description: TODO
	 * @param @param purchaseApp
	 * @param @return
	 * @author PANCHUANHE
	 * @return boolean
	 * @date 2015年9月29日 下午3:03:27
	 * @throws
	 */
	boolean findZongBuOrFenBu(PurchaseApp purchaseApp);
	/**
	 * 查看流程图
	 * @Title: getDiagramResourceByPaId 
	 * @Description: TODO
	 * @param @param response
	 * @param @param paId
	 * @author PANCHUANHE
	 * @return void
	 * @date 2015年10月9日 下午3:39:59
	 * @throws
	 */
	void getDiagramResourceByPaId(HttpServletResponse response,Integer paId);
	/**
	 * 根据id变更流程状态
	 * @Title: updatePurchaseAppProcessStatus 
	 * @Description: TODO
	 * @param @param paId
	 * @param @param status
	 * @author PANCHUANHE
	 * @return void
	 * @date 2015年10月10日 上午10:31:47
	 * @throws
	 */
	void updatePurchaseAppProcessStatus(String paId,String status);
}

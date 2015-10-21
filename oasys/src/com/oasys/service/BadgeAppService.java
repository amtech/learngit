package com.oasys.service;


import java.util.Date;
import java.util.List;

import org.quartz.Job;

import com.oasys.model.BadgeApp;
import com.oasys.model.BadgeAppAttach;
import com.oasys.model.StatusNameRef;
import com.oasys.util.PageUtil;
import com.oasys.viewModel.BadgeAppModel;
/**
 * 工牌申请
 * @author Administrator
 *
 */
public interface BadgeAppService 
{	
	/**
	 * 添加按钮，返回工牌一个申请
	 * @Title: findbadgeAppNo 
	 * @Description: TODO
	 * @param @return
	 * @author WANGXINCHENG
	 * @return BadgeApp
	 * @date 2015年9月25日 下午3:23:25
	 * @throws
	 */
	BadgeApp findAddBadgeAppNo(String appNo);
	/**
	 * 根据申请编号查询申请
	 * @Title: findBadgeAppNo 
	 * @Description: TODO
	 * @param @param appNo
	 * @param @return
	 * @author WANGXINCHENG
	 * @return BadgeApp
	 * @date 2015年9月25日 下午4:36:58
	 * @throws
	 */
	BadgeApp findBadgeAppNo(String appNo);
	/**
	 * 保存申请用户
	 * @Title: saveBadgeAttach 
	 * @Description: TODO
	 * @param @param badgeAppAttach
	 * @param @return
	 * @author WANGXINCHENG
	 * @return boolean
	 * @date 2015年9月25日 下午5:36:13
	 * @throws
	 */
	boolean saveBadgeAttach(BadgeAppAttach badgeAppAttach);
	/**
	 * 查询申请下的申请人
	 * @Title: findBadgeAttList 
	 * @Description: TODO
	 * @param @param appNo
	 * @param @return
	 * @author WANGXINCHENG
	 * @return List<BadgeAppAttach>
	 * @date 2015年9月25日 下午5:53:23
	 * @throws
	 */
	List<BadgeAppAttach> findBadgeAttList(String appNo,String deptNo,PageUtil pageUtil);
	/**
	 * 申请下的申请人人数
	 * @Title: findbadgeAtttotal 
	 * @Description: TODO
	 * @param @return
	 * @author WANGXINCHENG
	 * @return Long
	 * @date 2015年9月30日 下午1:27:42
	 * @throws
	 */
	Long findbadgeAtttotal(String appNo,String deptNo);
	/**
	 * 根据id删除工牌申请人
	 * @Title: deleteBadgeAttList 
	 * @Description: TODO
	 * @param @param ids
	 * @param @return
	 * @author WANGXINCHENG
	 * @return boolean
	 * @date 2015年9月28日 上午10:58:22
	 * @throws
	 */
	boolean deleteBadgeAttList(String ids);
	/**
	 * 工牌申请列表-工牌编号
	 * @Title: findBadgeAppList 
	 * @Description: TODO
	 * @param @param pageUtil
	 * @param @return
	 * @author WANGXINCHENG
	 * @return List<BadgeApp>
	 * @date 2015年9月29日 上午10:46:47
	 * @throws
	 */
	List<String> findBadgeAppList(PageUtil pageUtil,BadgeApp badgeApp);
	/**
	 * 根据工牌申请编号查询工牌申请详情信息
	 * @Title: findBadgeAppList 
	 * @Description: TODO
	 * @param @return
	 * @author WANGXINCHENG
	 * @return List<BadgeApp>
	 * @date 2015年9月28日 下午3:59:38
	 * @throws
	 */
	List<BadgeAppModel> getBadgeApp(List<String> appNoList);
	/**
	 * 查询工牌申请总条数
	 * @Title: total 
	 * @Description: TODO
	 * @param @return
	 * @author WANGXINCHENG
	 * @return int
	 * @date 2015年9月30日 下午12:54:02
	 * @throws
	 */
	Long findtotal(BadgeApp badgeApp);
	/**
	 * 根据申请编号删除申请所有信息
	 * @Title: deleteBadgeApp 
	 * @Description: TODO
	 * @param @param appNo
	 * @param @return
	 * @author WANGXINCHENG
	 * @return boolean
	 * @date 2015年10月8日 上午10:03:50
	 * @throws
	 */
	boolean deleteBadgeApp(String appNo);
	/**
	 * 修改工牌申请流程状态
	 * @Title: upBadgeProcStatus 
	 * @Description: TODO
	 * @param @param id
	 * @param @param c
	 * @author WANGXINCHENG
	 * @return void
	 * @date 2015年10月8日 上午11:14:18
	 * @throws
	 */
	void upBadgeProcStatus(Integer id, String c);
	/**
	 * 更改工牌申请状态
	 * @Title: upBadgeAppStatus 
	 * @Description: TODO
	 * @param @param id
	 * @param @param appStatus
	 * @author WANGXINCHENG
	 * @return void
	 * @date 2015年10月9日 上午10:20:19
	 * @throws
	 */
	void upBadgeAppStatus(Integer id,String appStatus);
	/**
	 * 根据工牌申请id获得申请对象
	 * @Title: findBadgeByPnrId 
	 * @Description: TODO
	 * @param @param pnriId
	 * @param @return
	 * @author WANGXINCHENG
	 * @return BadgeApp
	 * @date 2015年10月10日 上午11:41:48
	 * @throws
	 */
	BadgeApp findBadgeByPnrId(Integer pnrId);
	
	/**
	 * 根据申请编号删除空数据
	 * @Title: deletebadgeNotDate 
	 * @Description: TODO
	 * @param @param appNos
	 * @param @return
	 * @author WANGXINCHENG
	 * @return boolean
	 * @date 2015年10月16日 上午10:42:43
	 * @throws
	 */
	boolean deletebadgeNotDate(String pnrIds);
	
}

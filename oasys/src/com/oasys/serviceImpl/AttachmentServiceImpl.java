package com.oasys.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oasys.dao.PublicDao;
import com.oasys.model.Attachment;
import com.oasys.model.VO.AttachmentModel;
import com.oasys.service.AttachmentService;
import com.oasys.util.Collections;
import com.oasys.util.SaveUploadFileUtil;

@Service(value="attachmentService")
public class AttachmentServiceImpl implements AttachmentService {

	@Autowired
	private PublicDao<Attachment> attachmentDao;
	
	@Override
	public boolean persistenceAttachment(Attachment attachment) {
		if (attachment.getAttId()==null ||"".equals(attachment.getAttId())) {
			attachmentDao.save(attachment);
		} else {
			attachmentDao.update(attachment);
		}
		return true;
	}
	
	@Override
	public Attachment findSameByNT(Attachment attachment) {
		String hql = "SELECT ta FROM Attachment ta   WHERE ta.appRegNo = '"+attachment.getAppRegNo()+"' AND ta.creator = '"+attachment.getCreator()+"'  AND ta.attName = '"+attachment.getAttName()+"' ";
		List<Attachment> attlist = attachmentDao.find(hql);
		if (attlist.size() == 0) {
			return null;
		} else {
			return attlist.get(0);
		}
	}



	@Override
	public List<AttachmentModel> findAttachmentList(String userId, String appNo,String detailId) {
		// 定义返回值
				List<AttachmentModel> attachmentModels = new ArrayList<AttachmentModel>();
				StringBuffer sql = new StringBuffer();
				sql.append(" SELECT tba.ATT_DESC,tba.ATT_ID,tba.ATT_NAME ");
				sql.append(" ,tba.CREATE_DATETIME,tba.CREATOR,tu.NAME AS CREATOR_NAME, tba.ATT_URL, tba.FILE_TYPE ");
				sql.append(" FROM t_oa_attachment tba ");
				sql.append(" LEFT JOIN qqms.t_users tu ON tba.CREATOR = tu.USER_ID ");
				sql.append(" WHERE tba.CREATOR = '" + userId + "' AND tba.APP_REG_NO = '" + appNo + "'");
				if(StringUtils.isNotBlank(detailId)){
					sql.append(" AND tba.DETAIL_ID = " +detailId);
				}
				List<Object> list = attachmentDao.findBySQL(sql.toString());
				if (Collections.listIsNotEmpty(list)) {
					for (Object object : list) {
						Object[] obj = (Object[]) object;
						AttachmentModel model = new AttachmentModel();
						model.setAttDesc((String) obj[0]);
						model.setAttId(String.valueOf(obj[1]));
						model.setAttName((String) obj[2]);
						model.setCreateDate((Date) obj[3]);
						model.setCreator((String) obj[4]);
						model.setCreatorName((String) obj[5]);
						model.setAttURL(SaveUploadFileUtil.getFileSystemURL()+(String) obj[6]);
						model.setFileType((String) obj[7]);
						attachmentModels.add(model);
					}
				}
				return attachmentModels;
	}

	@Override
	public List<AttachmentModel> findAttachmentByULA(String userId,
			String appNo, String isDetail,String detailId) {
		// 定义返回值
				List<AttachmentModel> list = new ArrayList<AttachmentModel>();
				String sql = "SELECT ta.ATT_ID,ta.ATT_NAME,ta.ATT_URL,ta.FILE_TYPE  FROM t_oa_attachment ta WHERE ta.APP_REG_NO = '" + appNo + "' ";
				//如果不是查看详情，则只显示当前登陆人所上传附件
				if(!"1".equals(isDetail)){
					sql+="  AND ta.CREATOR = '" + userId + "'";
				}
				if(StringUtils.isNotBlank(detailId)){
					sql+=" AND ta.DETAIL_ID = " +detailId;
				}
				List<Object> result = attachmentDao.findBySQL(sql);
				if (Collections.listIsNotEmpty(result)) {
					for (Object object : result) {
						Object[] obj = (Object[]) object;
						AttachmentModel model = new AttachmentModel();
						model.setAttId(String.valueOf(obj[0]));
						model.setAttName(String.valueOf(obj[1]));
						model.setAttURL(SaveUploadFileUtil.getFileSystemURL()+String.valueOf(obj[2]));
						model.setFileType(String.valueOf(obj[3]));
						list.add(model);
					}
				}
				
				return list;
	}

	@Override
	public Attachment findAttachmentById(String id) {
		// TODO Auto-generated method stub
		return attachmentDao.get(Attachment.class, Integer.parseInt(id));
	}

	@Override
	public boolean delAttachment(String id) {
		String hql = "DELETE FROM Attachment t WHERE t.attId = '" + id + "'";
		Integer i = attachmentDao.executeHql(hql);
		return i > 0 ? true : false;
	}

}

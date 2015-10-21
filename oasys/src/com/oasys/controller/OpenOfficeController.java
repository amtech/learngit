package com.oasys.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oasys.model.Attachment;
import com.oasys.service.AttachmentService;
import com.oasys.util.DocConverter;
import com.oasys.util.SaveUploadFileUtil;
import com.oasys.viewModel.DataModel;
import com.sun.jersey.api.client.UniformInterfaceException;
/**
 * 
 * @ClassName: LoginController
 * @Description: TODO
 * @Author xujianwei
 * @Version 1.0
 * @Date 2015年8月17日 下午2:24:06
 *
 */
@Controller
@RequestMapping("/openOfficeController")
public class OpenOfficeController extends BaseController{
	private static Logger logger = Logger.getLogger(OpenOfficeController.class);
	
	/** 自动注入service. */
	@Autowired
	private AttachmentService attachmentService;
	
	/**
	 * 在线预览文档
	 */
	@ResponseBody
	@RequestMapping(value="/openOffice",method=RequestMethod.POST)
	public void openOffice(HttpServletRequest request,HttpServletResponse response,String attachmentId) {
		Attachment attachment = attachmentService
				.findAttachmentById(attachmentId);
		// 附件不存在的时候给前台提示信息
		if (null == attachment) {
			OutputJson(response,new DataModel("提示", "附件已经不存在！", false));
			return;
		}
		// 如果是图片则直接写到服务器上作为临时文件

		if (isImage(attachment.getFileType())) {
			//saveTempImage(attachment);
			// 返回信息
			OutputJson(response,new DataModel("提示", "转化成功！", true, getData("0",
					SaveUploadFileUtil.getFileSystemURL()+attachment.getAttURL())));
		}

		// 可以在线预览的文件
		else if (isOfficeFile(attachment.getFileType())) {
			saveDocToSwf(request,response,attachment);
		} else {
			OutputJson(response,new DataModel("提示", "此文档不支持在线预览！", true,
					getData("2", "")));
		}
	}
	
	/**
	 * 将office文档转化为swf并降临时文件保存到服务器
	 * 
	 * @param attachment
	 *            附件
	 * @throws IOException
	 */
	private void saveDocToSwf(HttpServletRequest request,HttpServletResponse response,Attachment attachment) {
		OutputStream os = null;
		try { // 获取存储的路径
			String contextPath = request.getSession().getServletContext()
					.getRealPath("/") + "upload";
//			String contextPath =SaveUploadFileUtil.getFileSystemURL()+"resources/images"; 

			// 获取源文件的名称，不带路径
			String fileName = attachment.getAttURL().split("/")[2];

			// 带路径的源文件的名称
			String fileString = contextPath.replaceAll("\\\\", "/") + "/"
					+ fileName;

			// 创建转化器
			DocConverter dc = new DocConverter(fileString);

			// 获取转化后的swf文件的相对路径
			String swfPath = "/" + fileName.split("\\.")[0] + ".swf";
			// 获取转化后的
			try {
				SaveUploadFileUtil
						.getFileFormFileSystemByPath("resources/images"
								+ swfPath);
			} catch (UniformInterfaceException e) {
				// 保存源文件到服务器
				os = new FileOutputStream(new File(fileString));
				os.write(FileUtils.readFileToByteArray(SaveUploadFileUtil
						.getFileFormFileSystemByPath(attachment.getAttURL())));
				os.close();
				// 转化
				dc.conver();
				// 将转化后的文件保存到服务器

				if (dc.getswfPath().lastIndexOf("/") != -1) {
					swfPath = dc.getswfPath().substring(
							dc.getswfPath().lastIndexOf("/"));
				} else {
					swfPath = dc.getswfPath();
				}

				File file = new File(contextPath + swfPath);
				// 获取文件将文件写入到指定服务器
				if (file.exists()) {
					SaveUploadFileUtil.saveSwf2FileSystem(swfPath, file);
					file.delete();
				}
			}

			// 返回信息
			OutputJson(response,new DataModel("提示", "转化成功！", true, getData("1",
					SaveUploadFileUtil.getFileSystemURL() + "resources/images"
							+ swfPath)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 判断是否是图片
	 * 
	 * @param fileType
	 *            文件类型
	 * @return 是图片类型返回true否则返回false
	 */

	private boolean isImage(String fileType) {
		Pattern p = Pattern
				.compile("(JPEG|jpeg|JPG|jpg|GIF|gif|BMP|bmp|PNG|png){1}");
		Matcher m = p.matcher(fileType);
		return m.matches();
	}

	/**
	 * 判断是不是在线预览的支持的文件格式
	 * 
	 * @param fileType
	 *            文件类型
	 * @return 是在线预览的文件格式返回true否则返回false
	 */
	private boolean isOfficeFile(String fileType) {
		Pattern p = Pattern
				.compile("(xlsx|docx|xls|doc|pdf|txt|ppt|pptx|wps){1}");
		Matcher m = p.matcher(fileType);
		return m.matches();
	}

	/**
	 * 组织返回值的信息
	 * 
	 * @param fileType
	 *            0：图片，1：在线预览文档，2：其他 文件类型
	 * @param filePath
	 *            文件地址
	 * @return 返回组织好的信息
	 */
	private Map<String, Object> getData(String fileType, String filePath) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("fileType", fileType);
		data.put("filePath", filePath);
		return data;
	}
}



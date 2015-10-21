package com.oasys.test;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.oasys.controller.BaseController;
import com.oasys.viewModel.DataModel;

/**
 *author:yuanzhongqiu
 *Time:2015年9月17日  上午9:39:28
 */
@Controller
@RequestMapping("/testController")
public class TestController extends BaseController{
	@ResponseBody
    @RequestMapping(value="/uploadFile",method=RequestMethod.POST)  
    public String uploadFile(HttpServletResponse response,HttpServletRequest request,@RequestParam(value="file", required=false) MultipartFile file){  
        byte[] bytes;
		try {
			bytes = file.getBytes();
			System.out.println(file.getOriginalFilename());  
			String uploadDir = request.getSession().getServletContext().getRealPath("upload");  
			File dirPath = new File(uploadDir);  
			if (!dirPath.exists()) {  
				dirPath.mkdirs();  
			}  
			String sep = System.getProperty("file.separator");  
			File uploadedFile = new File(uploadDir + sep  
					+ file.getOriginalFilename());  
			FileCopyUtils.copy(bytes, uploadedFile);  
			System.out.println(uploadedFile);
			OutputJson(response,new DataModel("提示", "文件上传成功!!", true), "text/html");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
          return null;
    }  
}



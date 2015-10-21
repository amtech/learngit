package com.oasys.util;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;

/**
 * 
 * 往远程服务器系统保存上传文件.
 * 
 */
public class SaveUploadFileUtil {

	/** 文件的名称. */
	private static final String PROPERTIES_NAME = "filesys.properties";

	/** Properties工具类. */
	private static Properties pro = new Properties();

	/**
	 * 无参数的构造方法,限定工具类,不能进行创建实例
	 */
	private SaveUploadFileUtil() {
	}

	/** 加载配置文件. */
	static {
		try {
			pro.load(SaveUploadFileUtil.class.getClassLoader()
					.getResourceAsStream(PROPERTIES_NAME));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/** 文件系统URL KEY. */
	private static final String KEY_FILE_SYSTEM_URL = pro
			.getProperty("file_system_url");

	/** 文件系统端口 KEY. */
	private static final String KEY_FILE_SYSTEM_PORT = pro
			.getProperty("file_system_port");

	/** 文件系统APP名称 KEY. */
	private static final String KEY_FILE_SYSTEM_NAME = pro
			.getProperty("file_system_name");

	/** 请求路径中的分割线。 */
	private static final String KEY_FORWARD = "/";

	/**
	 * 文件服务器的访问地址
	 */
	public static String getFileSystemURL() {
		StringBuffer url = new StringBuffer();
		url.append(KEY_FILE_SYSTEM_URL);
		url.append(":");
		url.append(KEY_FILE_SYSTEM_PORT);
		url.append(KEY_FORWARD);
		url.append(KEY_FILE_SYSTEM_NAME);
		url.append(KEY_FORWARD);
		return url.toString();
	}

	/**
	 * 将文件按保存到文件服务器并返回文件相对路径名称
	 */
	public static String saveFile2FileSystem(String fileName, File file) {
		String ext = FilenameUtils.getExtension(fileName);
		String fileNewName = getFileNewName();
		String path = "resources/images/" + fileNewName + "." + ext;
		String url = getFileSystemURL() + path;
		try {
			getJerseyClient(url).put(String.class,
					FileUtils.readFileToByteArray(file));
		} catch (UniformInterfaceException | IOException e) {
			e.printStackTrace();
		}
		return path;
	}

	/**
	 * 将文件按保存到文件服务器并返回文件相对路径名称
	 */
	public static String saveFile2FileSystem(String fileName, byte[] bytes) {
		String ext = FilenameUtils.getExtension(fileName);
		String fileNewName = getFileNewName();
		String path = "resources/images/" + fileNewName + "." + ext;
		String url = getFileSystemURL() + path;
		getJerseyClient(url).put(String.class, bytes);
		return path;
	}
	
	/**
	 * 
	 * @author:xujianwei
	 * @time:2015年10月14日 上午10:45:47
	 * @Title:saveFile2FileSystem4App
	 * @Description:TODO（这里描述这个方法的作用） 对于申请业务：将文件按保存到文件服务器并返回文件相对路径名称(新的保存附件的方法)
	 * @param appNo
	 * @param fileName
	 * @param bytes
	 * @return
	 * @throws:
	 */
	public static String saveFile2FileSystem4App(String appNo, String fileName, byte[] bytes) {
		String ext = FilenameUtils.getExtension(fileName);
		String fileNewName = getFileNewName();
		//定义发起RestFul风格请求的逻辑URL路径。
		String path = "resources/oaAttachment/" + appNo + "/" + fileNewName + "." + ext;
		String url = getFileSystemURL() + path;
		try {
			getJerseyClient(url).put(String.class,bytes);
			//返回图片实际存储的物理路径（注意区别：图片的逻辑URL路径比图片的实际物理路径多了一个“resources”字符串）。
			path = path.replace("resources/", "");			
		} catch (UniformInterfaceException e) {
			e.printStackTrace();
		}
		return path;
	}	

	/**
	 * 将文件按保存到文件服务器并返回文件相对路径名称
	 */
	public static String saveSwf2FileSystem(String fileName, File file) {
		String path = "resources/images/" + fileName;
		String url = getFileSystemURL() + path;
		try {
			getJerseyClient(url).put(String.class,
					FileUtils.readFileToByteArray(file));
		} catch (UniformInterfaceException | IOException e) {
			e.printStackTrace();
		}
		return path;
	}

	/**
	 * 从服务器上获取文件
	 * 
	 * @param path
	 *            文件的路径
	 */
	public static File getFileFormFileSystemByPath(String path) {
		return getJerseyClient(getFileSystemURL() + path).get(File.class);
	}

	/**
	 * 根据文件的路径获取文件字节信息
	 * 
	 * @param path
	 *            文件的路径
	 */
	public static byte[] getBytesFormFileSystemByPath(String path) {
		return getJerseyClient(getFileSystemURL() + path).get(byte[].class);
	}

	/**
	 * 获取jersey的客户端
	 */
	private static WebResource getJerseyClient(String url) {
		return new Client().resource(url);
	}

	/**
	 * 删除服务器上的图片文件
	 */
	public static void deleteFlieSystemFileByPath(String path) {
		getJerseyClient(getFileSystemURL() + path).delete();
	}

	/**
	 * 获取文件新名称，防止服务器文件名重复
	 */
	private static String getFileNewName() {
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String fileName = df.format(new Date());
		Random random = new Random(System.currentTimeMillis());
		for (int j = 0; j < 5; j++) {
			fileName += random.nextInt(10);
		}
		return fileName;
	}

	public static void main(String[] args) throws IOException {
		/*
		 * File s = getJerseyClient( getFileSystemURL() +
		 * "resources/images//2015091709124560081131111.jpg") .get(File.class);
		 * FileUtils.writeByteArrayToFile(new File("d://d.jpg"),
		 * FileUtils.readFileToByteArray(s));
		 */
		System.out.println("fdasfdsafdsaff");

		File file = new File(getFileSystemURL()
				+ "resources/images/2015091709124560081131111.jpg");
		System.out.println("fdasfdsafdsaff");
		if (file.exists()) {
			System.out.println("文件存在");
		} else {
			System.out.println("文件不存在");
		}
	}
}

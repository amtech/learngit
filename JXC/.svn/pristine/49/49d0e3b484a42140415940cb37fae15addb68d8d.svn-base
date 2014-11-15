package com.jxc.core.util.property;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.jxc.core.log.LogHelper;

/**
 * 
 * @FileName: PropertyHelper.java
 * @Author: WuF
 * @Version: 1.0
 * @Date: 2012-3-21下午5:19:59
 * @Description: 读取资源文件类
 * @Others:
 * @FuntionList:
 * @History:
 * @Category:
 */
@SuppressWarnings("rawtypes")
public class PropertyHelper {
	private static PropertyHelper config;
	private static Logger log = LogHelper.getCommonLogger();
	private static Map<String, String> proMap = new HashMap<String, String>();

	static {
		Properties properties = new Properties();
		try {
			String URL = PropertyHelper.class.getResource("/") + "config";
			URL = URL.substring(6);

			File f = new File(URL);
			File[] files = f.listFiles(new FilenameFilter() {
				public boolean accept(File dir, String name) {
					name = name.toLowerCase();

					return name.endsWith(".properties");
				}
			});
			for (int i = 0; i < files.length; i++) {
				try {
					properties.load(new FileInputStream(files[i]));
				} catch (IOException e) {
					log.error(e.getMessage(), e);
					throw e;
				}

			}

			InputStream in = new BufferedInputStream(new FileInputStream(URL));
			properties.load(in);
			Enumeration en = properties.propertyNames();
			while (en.hasMoreElements()) {
				String key = (String) en.nextElement();
				String Property = properties.getProperty(key);
				proMap.put(key, Property);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static PropertyHelper getInstance() {
		if (config == null) {
			return new PropertyHelper();
		}
		return config;
	}

	public String getProperties(String key) {
		return proMap.containsKey(key) ? (String) proMap.get(key) : "";
	}
}

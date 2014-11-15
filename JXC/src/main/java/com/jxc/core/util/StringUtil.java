package com.jxc.core.util;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.jxc.core.log.LogHelper;


/**
 * @FileName: StringUtil.java
 * @Author: WuF
 * @Version: 1.0
 * @Date: 2012-3-21下午6:04:05
 * @Description: 字符串工具类
 * @Others: 
 * @FuntionList:
 * @History:
 * @Category:
 */
public class StringUtil {

	private static Logger log = LogHelper.getCommonLogger();
	
	/**
	 Function: getBoolean 
	 Description: 将字符串转换为布尔型		 		
	 @param str
	 @return
	 */
	public static boolean getBoolean(String str){
		return StringUtils.isNotBlank(str)?Boolean.valueOf(str):false;
	}

	/**
	 * 获取字符串,为空时返回"",否则返回字符串trim值
	 * @param str
	 * @return
	 */
	public static String getString(String str) {
		return (str == null) || (str.length() < 1) ? "" : str.trim();
	}

	/**
	 * 获取字符串,为空时返回convertStr,否则返回字符串trim值
	 * @param str
	 * @param convertStr
	 * @return
	 */
	public static String getString(String str, String convertStr) {
		return (str == null) || (str.length() < 1) ? convertStr.trim() : str.trim();
	}
	
	/**
	 * 获取字符串,为空时返回"",否则返回字符串原值
	 * @param str
	 * @return
	 */
	public static String getStringOra(String str) {
		return (str == null) || (str.length() < 1) ? "" : str;
	}

	/**
	 * 获取字符串,为空时返回convertStr,否则返回字符串原值
	 * @param str
	 * @param convertStr
	 * @return
	 */
	public static String getStringOra(String str, String convertStr) {
		return (str == null) || (str.length() < 1) ? convertStr.trim() : str;
	}
	

	public static boolean isEmpty(String str) {
		return (str == null) || (str.length() < 1);
	}

	public static boolean isNotBlank(String str) {
		return (str != null) && (str.trim().length() > 0);
	}

	public static boolean isBlank(String str) {
		return (str == null) || (str.trim().length() < 1);
	}

	public static String getExpressionVal(String sourceStr, String startChar,
			String endChar) {
		String ss = sourceStr;
		int startIndex = ss.indexOf(startChar) + startChar.length();
		int endIndex = ss.indexOf(endChar, startIndex);
		try {
			ss = ss.substring(startIndex, endIndex);
		} catch (Exception e) {
			log.error("字符串截取异常:" + e.getMessage());
			e.printStackTrace();
			ss = "";
		}
		return ss;
	}

	public static String change(String src) {
		if (src != null) {
			StringBuffer sb = new StringBuffer(src);
			sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
			return sb.toString();
		}
		return null;
	}
}

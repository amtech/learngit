package com.jxc.core.util;

import java.io.File;
import java.io.FileReader;
import java.io.StringReader;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * @FileName: DomHelper.java
 * @Author: WuF
 * @Version: 1.0
 * @Date: 2013-2-25上午8:49:38
 * @Description:操作XML的工具类 
 * @Others: 
 * @FuntionList:
 * @History:
 * @Category:
 */
public class DomHelper {
	
	Document dom =  null;
	
	public DomHelper() {
	}
	
	public DomHelper(String path,String XMLStr){
		try {
			SAXReader sxReader = new SAXReader();
			dom = sxReader.read(null == XMLStr?new FileReader(new File(path)):new StringReader(XMLStr));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//获得节点list
	public List<Element> selectNodes(String path){
		return dom.selectNodes(path);
	}
	
	//获得文本
	public String getText(String key){
		return dom.getRootElement().elementText(key) ;
	}
	
	//获得属性
	public String getAttribute(String el,String key){
		return dom.getRootElement().element(el).attributeValue(key);
	}
	
	//获得跟节点
	public Element getRoot(){
		return dom.getRootElement();
	}
	
	
}

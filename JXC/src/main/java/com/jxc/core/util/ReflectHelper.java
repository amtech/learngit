 package com.jxc.core.util;
 
 import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.jxc.core.log.LogHelper;

/**
 * @FileName: ReflectHelper.java
 * @Author: WuF
 * @Version: 1.0
 * @Date: 2012-3-21下午6:03:44
 * @Description: 反射帮助类
 * @Others: 
 * @FuntionList:
 * @History:
 * @Category:
 */
 public class ReflectHelper
 {
   private static Logger log = LogHelper.getCommonLogger();
 
   @SuppressWarnings("rawtypes")
public static String getAttribute(Object obj)
   {
     StringBuilder sb = new StringBuilder("{");
     StringBuilder tsb = new StringBuilder("children:[");
     String returnVal = "";
 
     Field[] field = obj.getClass().getDeclaredFields();
     for (int i = 0; i < field.length; i++)
     {
       field[i].setAccessible(true);
 
       String fieldName = field[i].getName();
       try
       {
         if (("class java.lang.String".equalsIgnoreCase(field[i].getGenericType().toString())) || ("class java.lang.Boolean".equalsIgnoreCase(field[i].getGenericType().toString())))
         {
           Method method = obj.getClass().getMethod("get" + StringUtil.change(fieldName), new Class[0]);
 
           Object objValue = method.invoke(obj, new Object[0]);
 
           objValue = (objValue == null) || ("null".equalsIgnoreCase(objValue.toString())) ? "" : objValue;
           if (!"".equalsIgnoreCase(objValue.toString())) {
             sb.append(field[i].getName() + ":'" + objValue + "', ");
           }
         }
         if (field[i].getGenericType().toString().contains("java.util.List")) {
           List list = new ArrayList();
 
           Method method = obj.getClass().getMethod("get" + StringUtil.change(fieldName), new Class[0]);
 
           list = (List)method.invoke(obj, new Object[0]);
 
           if (list != null) {
             for (Iterator localIterator = list.iterator(); localIterator.hasNext(); ) { Object object = localIterator.next();
               if (object != null)
                 tsb.append(getAttribute(object) + ",");
             }
           }
         }
       }
       catch (Exception e)
       {
         log.error("反射获取错误:" + e.getMessage());
         e.printStackTrace();
       }
     }
     tsb.deleteCharAt(tsb.length() - 1);
     tsb.append("]");
     sb.append(tsb);
     sb.append("}");
     returnVal = sb.toString().equals("{}") ? null : sb.toString();
     return returnVal;
   }
 }


 


package com.jxc.core.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

/**
  Copyright (C), 1987-., Mord Tech. Co., Ltd. 
  @File:			JSONHelper.java
  @Author:			Leon
  @Version:		v 1.0
  @Date:			2013-8-19 上午12:05:37
  @Description:	FastJSON帮助类
  @History:		
*/
public class JSONHelper {
    /**
      @Function:        toGridString
      @Description:     返回GridPanel需要的JSON格式
      @param list
      @return
     */
    public static <T> String toGridString(List<T> list){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("total", list.size());
        map.put("data", list);
        return JSON.toJSONString(map);
    }

}

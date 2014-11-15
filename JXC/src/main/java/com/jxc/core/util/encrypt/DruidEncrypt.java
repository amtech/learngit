package com.jxc.core.util.encrypt;

import com.alibaba.druid.filter.config.ConfigTools;

/**
  Copyright (C), 1987-., Mord Tech. Co., Ltd. 
  @File:			DruidEncrypt.java
  @Author:			Leon
  @Version:		v 1.0
  @Date:			2013-8-19 上午9:37:05
  @Description:	Druid连接池数据库密码加密/解密帮助类
  @History:
 */
public class DruidEncrypt {
    /**
      @Function:       encryptPWD
      @Description:    加密数据库密码
      @param pwd
      @return
     */
    public static String encryptPWD(String pwd){
        try {
           pwd = ConfigTools.encrypt(pwd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pwd;
    }
}

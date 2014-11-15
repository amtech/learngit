package com.jxc.core.util.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ModelProp
{

	/**
	 * 中文名
	 * 
	 * @return
	 */
	String symbol() default "";

	/**
	 * 描述
	 * 
	 * @return
	 */
	String desc() default "";

	/**
	 * 对应Dao注册在spring中的id
	 * 
	 * @return
	 */
	String daoId() default "";

	/**
	 * 对应Dao实现类
	 * 
	 * @return
	 */
	String daoClass() default "";
}

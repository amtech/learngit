package com.jxc.core.util.json;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.CharacterIterator;
import java.text.DateFormat;
import java.text.StringCharacterIterator;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Stack;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.util.HtmlUtils;

/**
 * <p>
 * 将对象序列化为JSON. 如果发现循环引用，输出为null
 * </p>
 */
@SuppressWarnings({ "unchecked", "unused" })
public class JSONWriter
{
	private static final Log log = LogFactory.getLog(JSONWriter.class);

	/** 默认枚举类型作为名值对序列化 */
	public static final boolean ENUM_AS_BEAN_DEFAULT = false;

	static char[] hex = "0123456789ABCDEF".toCharArray();
	private StringBuilder buf = new StringBuilder();
	private Stack stack = new Stack();
	private Object root;
	private boolean buildExpr = true;
	private String exprStack = "";
	private JSONConfig config = new JSONConfig();

	public JSONConfig getConfig()
	{
		return config;
	}

	public void setConfig(JSONConfig config)
	{
		this.config = config;
	}

	/**
	 * @param object
	 *            要进行序列化的对象
	 * @return JSON 对象序列化后的JSON字符串
	 * @throws JSONException
	 */
	public String write(Object object) throws JSONException
	{
		this.buf.setLength(0);
		this.root = object;
		this.exprStack = "";
	
		this.buildExpr = ((this.config.getExcludeProperties() != null) && !this.config
				.getExcludeProperties().isEmpty())
				|| ((this.config.getIncludeProperties() != null) && !this.config
						.getIncludeProperties().isEmpty());
		this.value(object, null);

		return this.buf.toString();
	}

	/**
	 * 检测循环调用
	 */
	private void value(Object object, Method method) throws JSONException
	{

		if (object == null)
		{
			this.add("null");

			return;
		}
		// 判断是否包含了相同的对象
		if (this.stack.contains(object))
		{
			Class clazz = object.getClass();

			// 循环调用
			if (clazz.isPrimitive() || clazz.equals(String.class))
			{
				this.process(object, method);
			} else
			{
				if (log.isDebugEnabled())
				{
					log.debug("Cyclic reference detected on " + object);
				}

				this.add("null");
			}

			return;
		}

		this.process(object, method);
	}

	/**
	 * 将对象序列化为JSON字符串
	 */
	private void process(Object object, Method method) throws JSONException
	{
		this.stack.push(object);

		if (object instanceof Class)
		{
			this.string(object);
		} else if (object instanceof Boolean)
		{
			this.bool(((Boolean) object).booleanValue());
		} else if (object instanceof Number)
		{
			this.add(object);
		} else if (object instanceof String)
		{
			this.string(object);
		} else if (object instanceof Character)
		{
			this.string(object);
		} else if (object instanceof Map)
		{
			this.map((Map) object, method);
		} else if (object.getClass().isArray())
		{
			this.array(object, method);
		} else if (object instanceof Iterable)
		{
			this.array(((Iterable) object).iterator(), method);
		} else if (object instanceof Date)
		{
			this.date((Date) object, method);
		} else if (object instanceof Calendar)
		{
			this.date(((Calendar) object).getTime(), method);
		} else if (object instanceof Locale)
		{
			this.string(object);
		} else if (object instanceof Enum)
		{
			this.enumeration((Enum) object);
		} else
		{
			this.bean(object);
		}

		this.stack.pop();
	}

	/**
	 * 反射bean并序列化bean的属性
	 */
	@SuppressWarnings("static-access")
	private void bean(Object object) throws JSONException
	{
		this.add("{");

		BeanInfo info;
		boolean treeGridFormat = false;

		boolean leafFormat = false;
		boolean leaf = true;

		boolean parentHasData = false;
		StringBuilder parentBuff = null;
		Class clazz = object.getClass();
		String classType = null;
		boolean hasData = false;

		try
		{

			if (clazz.getName().indexOf("_$$_") > -1)
				classType = clazz.getName().substring(0,
						clazz.getName().indexOf("_$$_"));
			else
				classType = clazz.getName();

			// 是否读取所有父类的属性 (object == this.root) &&
			// this.config.isIgnoreHierarchy()
			info = ((object == this.root) && this.config.isIgnoreHierarchy()) ? Introspector
					.getBeanInfo(clazz, clazz.getSuperclass()) : Introspector
					.getBeanInfo(clazz);

			// 获取所有私有属性
			PropertyDescriptor[] props = info.getPropertyDescriptors();

			for (int i = 0; i < props.length; ++i)
			{
				PropertyDescriptor prop = props[i];
				String name = prop.getName();
				Method accessor = prop.getReadMethod();
				Class clz = prop.getPropertyType();
				Method baseAccessor = null;

				// 处理CGLIB
				if (clazz.getName().indexOf("$$EnhancerByCGLIB$$") > -1)
				{
					try
					{
						baseAccessor = Class.forName(
								clazz.getName().substring(0,
										clazz.getName().indexOf("$$")))
								.getMethod(accessor.getName(),
										accessor.getParameterTypes());
					} catch (Exception ex)
					{
						log.debug(ex.getMessage(), ex);
					}
				} else
					baseAccessor = accessor;

				if (baseAccessor != null)
				{

					// 忽略 "class"和其他
					if (this.shouldExcludeProperty(clazz, prop))
					{
						continue;
					}
					String expr = null;
					expr = this.expandExpr(name);

					if (this.buildExpr)
					{

						// 忽略非基本类型
						if (this.ignoreImprimitive(clz, expr))
						{
							continue;
						}

						// 忽略exclude指定的属性
						if (this.shouldExcludeProperty(expr))
						{
							continue;
						}

						// 处理 treeGrid - json 形式
						if (this.isParentProperty(expr))
						{
							treeGridFormat = true;
						}

						prop.getPropertyType().toString();
						expr = this.setExprStack(expr);

					} else
					{

						if (this.ignoreImprimitive(clz, expr))
						{
							continue;
						}

						// 处理 treeGrid - json 形式
						if (this.isParentProperty(expr))
						{
							treeGridFormat = true;
						}

						expr = this.setExprStack(expr);
					}

					// 关闭所有集合类型的序列化
					if (this.config.isClosCollection())
					{

						String clsT = clz.getName();
						boolean contue = false;
						if (clsT.indexOf("java.util.Set") != -1)
							contue = true;
						if (clsT.indexOf("java.util.List") != -1)
							contue = true;
						if (clsT.indexOf("java.util.Map") != -1)
							contue = true;
						if (contue)
						{
							this.setExprStack(expr);
							continue;
						}
					}

					Object value = accessor.invoke(object, new Object[0]);

					// 关闭所有集合类型的序列化
					if (this.config.isClosCollection()
							&& value instanceof Iterable)
					{
						this.setExprStack(expr);
						continue;
					}

					// 添加 中文字段显示
					if (config.isFieldSymbol())
					{
						String symbol = this.getModelPropSymbol(
								accessor.getAnnotations(), 2);
						if (config.isOverrideSymbol())
						{
							name = symbol;
						} else
						{
							boolean success = this.add(name + config.SYMBOLEND,
									symbol, null, hasData);
							hasData = hasData || success;
						}
					}

					// 处理 treeGrid - json形式
					if (treeGridFormat)
					{
						parentBuff = this.getValue(value,
								config.getParentModel()[1]);
						leafFormat = true;
						treeGridFormat = false;

						this.setExprStack(expr);
						continue;
					}

					if (this.config.isIterableAsSize()
							&& value instanceof Iterable)
					{
						value = ((Collection) value).size();
						if ((Integer) value > 0)
							leaf = false;
					}

					boolean propertyPrinted = this.add(name, value, accessor,
							hasData);

					hasData = hasData || propertyPrinted;
					parentHasData = hasData;

					if (this.buildExpr)
					{
						this.setExprStack(expr);
					} else
					{
						this.setExprStack(expr);
					}
				}
			}

			// special-case handling for an Enumeration - include the name() as
			// a property */
			if (object instanceof Enum)
			{
				Object value = ((Enum) object).name();
				this.add("_name", value, object.getClass().getMethod("name"),
						hasData);
			}
		} catch (Exception e)
		{
			throw new JSONException(e);
		}

		// 是否 显示类的中文名
		if (config.isModelSymbol())
		{
			String symbol = this.getModelPropSymbol(clazz.getAnnotations(), 1);
			this.add("class" + config.SYMBOLEND, symbol, null, true);
		}

		this.add("classType", classType, null, hasData);

		if (leafFormat)
		{
			if (parentHasData)
				this.add(",");
			this.add(parentBuff.toString());

			this.add("leaf", leaf, null, true);
		}

		this.add("}");
	}

	/**
	 * Instrospect an Enum and serialize it as a name/value pair or as a bean
	 * including all its own properties
	 */
	private void enumeration(Enum enumeration) throws JSONException
	{
		if (this.config.isEnumAsBean())
		{
			this.bean(enumeration);
		} else
		{
			this.string(enumeration.name());
		}
	}

	/**
	 * 忽略"class"属性
	 */
	private boolean shouldExcludeProperty(Class clazz, PropertyDescriptor prop)
			throws SecurityException, NoSuchFieldException
	{
		String name = prop.getName();

		if (name.equals("class") || name.equals("declaringClass"))
		{
			return true;
		}

		return false;
	}

	private String expandExpr(int i)
	{
		return this.exprStack + "[" + i + "]";
	}

	// 拼接表达式
	private String expandExpr(String property)
	{
		if (this.exprStack.length() == 0)
			return property;
		return this.exprStack + "." + property;
	}

	private String setExprStack(String expr)
	{
		String s = this.exprStack;
		this.exprStack = expr;
		return s;
	}

	// 屏蔽将要排除的属性
	private boolean shouldExcludeProperty(String expr)
	{

		List<String> excludes = this.config.getExcludeProperties();
		if (excludes != null)
		{
			String express = expr.substring(
					expr.indexOf(".") < 0 ? 0 : expr.indexOf(".") + 1,
					expr.length());
			for (String exclude : excludes)
			{
				// exclude.split("0");
				if (express.equals(exclude))
				{
					if (log.isDebugEnabled())
						log.debug("Ignoring property because of exclude rule: "
								+ expr);
					return true;
				}
			}
		}

		List<String> includes = this.config.getIncludeProperties();
		if (includes != null)
		{

			if (expr.indexOf("]") >= 0)
			{
				// 如果是集合的首层 则忽略
				if (expr.indexOf(".") == -1)
				{
					return false;
				}
				expr = expr.substring(expr.indexOf(".") + 1, expr.length());
			}
			;

			for (String include : includes)
			{

				if (expr.length() >= include.length())
				{// 全部匹配
					if (expr.equals(include))
						return false;
				} else // 匹配前几项
				if (expr.equals(include.substring(0, expr.length())))
				{
					return false;
				}
			}

			if (log.isDebugEnabled())
				log.debug("Ignoring property because of include rule:  " + expr);
			return true;
		}

		return false;
	}

	/**
	 * 序列化名值对
	 */
	private boolean add(String name, Object value, Method method,
			boolean hasData) throws JSONException
	{
		if (!this.config.isExcludeNullProperties() || value != null)
		{
			if (hasData)
			{
				this.add(',');
			}
			this.add('"');
			this.add(name);
			this.add("\":");
			this.value(value, method);
			return true;
		}

		return false;
	}

	/**
	 * 序列化数组Map
	 */
	private void map(Map map, Method method) throws JSONException
	{
		this.add("{");

		Iterator it = map.entrySet().iterator();
		boolean hasData = false;
		while (it.hasNext())
		{
			Map.Entry entry = (Map.Entry) it.next();
			Object key = entry.getKey();
			String expr = null;
			if (this.buildExpr)
			{
				if (key == null)
				{
					log.error("Cannot build expression for null key in "
							+ this.exprStack);
					continue;
				} else
				{
					// 排除 key 或接纳 key
					expr = this.expandExpr(key.toString());
					if (this.shouldExcludeProperty(expr))
					{
						continue;
					}
					expr = this.setExprStack(expr);
				}
			}
			if (hasData)
			{
				this.add(',');
			}
			hasData = true;
			this.value(key, method);
			this.add(":");
			this.value(entry.getValue(), method);
			if (this.buildExpr)
			{
				this.setExprStack(expr);
			}
		}

		this.add("}");
	}

	/**
	 * 序列化日期
	 */
	private void date(Date date, Method method)
	{

		DateFormat formatter = this.config.getFormatter();

		this.string(formatter.format(date));
	}

	/**
	 * 序列化数组
	 */
	private void array(Iterator it, Method method) throws JSONException
	{
		this.add("[");

		boolean hasData = false;
		for (int i = 0; it.hasNext(); i++)
		{
			String expr = null;
			if (this.buildExpr)
			{
				expr = this.expandExpr(i);
				if (this.shouldExcludeProperty(expr))
				{
					it.next();
					continue;
				}
				expr = this.setExprStack(expr);
			}
			if (hasData)
			{
				this.add(',');
			}
			hasData = true;
			this.value(it.next(), method);
			if (this.buildExpr)
			{
				this.setExprStack(expr);
			}
		}

		this.add("]");
	}

	/**
	 * 序列化数组
	 */
	private void array(Object object, Method method) throws JSONException
	{
		this.add("[");

		int length = Array.getLength(object);

		boolean hasData = false;
		for (int i = 0; i < length; ++i)
		{
			String expr = null;
			if (this.buildExpr)
			{
				expr = this.expandExpr(i);
				if (this.shouldExcludeProperty(expr))
				{
					continue;
				}
				expr = this.setExprStack(expr);
			}
			if (hasData)
			{
				this.add(',');
			}
			hasData = true;
			this.value(Array.get(object, i), method);
			if (this.buildExpr)
			{
				this.setExprStack(expr);
			}
		}

		this.add("]");
	}

	/**
	 * 序列化布尔值
	 */
	private void bool(boolean b)
	{
		this.add(b ? "true" : "false");
	}

	/**
	 * escape characters
	 */
	private void string(Object obj)
	{
		this.add('"');

		CharacterIterator it = new StringCharacterIterator(obj.toString());
		HtmlUtils.htmlEscape("d");
		for (char c = it.first(); c != CharacterIterator.DONE; c = it.next())
		{
			if (c == '"')
			{
				this.add("\\\"");
			} else if (c == '\\')
			{
				this.add("\\\\");
			} else if (c == '/')
			{
				this.add("\\/");
			} else if (c == '\b')
			{
				this.add("\\b");
			} else if (c == '\f')
			{
				this.add("\\f");
			} else if (c == '\n')
			{
				this.add("\\n");
			} else if (c == '\r')
			{
				this.add("\\r");
			} else if (c == '\t')
			{
				this.add("\\t");
			} else if (Character.isISOControl(c))
			{
				this.unicode(c);
			} else
			{
				if (this.config.isEncodeHtml())
				{
					if (c == '<')
					{
						this.add("&lt;");
					} else if (c == '>')
					{
						this.add("&gt;");
					} else
						this.add(c);
				} else
					this.add(c);
			}
		}

		this.add('"');
	}

	/**
	 * 序列化object
	 */
	private void add(Object obj)
	{
		this.buf.append(obj);
	}

	/**
	 * 序列化字符
	 */
	private void add(char c)
	{
		this.buf.append(c);
	}

	/**
	 * 中文处理 Represent as unicode
	 * 
	 * @param c
	 *            character to be encoded
	 */
	private void unicode(char c)
	{
		this.add("\\u");

		int n = c;

		for (int i = 0; i < 4; ++i)
		{
			// 取高4位 并移到低4位 转int型
			int digit = (n & 0xf000) >> 12;
			// 通过转后的int做为下标 读取相应十六进制字符
			this.add(hex[digit]);
			// 将高4位移除 低4位补零
			n <<= 4;
		}
	}

	// 忽略非基本类型
	private boolean ignoreImprimitive(Class clazz, String expr)
	{

		int bln = this.config.getDepth();
		if (expr.indexOf("]") >= 0)
		{
			bln++;
		}
		;

		if (expr.split("\\.").length == bln)
		{

			if ((!clazz.isPrimitive() && !clazz.equals(String.class)))
			{
				return true;
			}
		}

		// 忽略子类的子类
		if (expr.split("\\.").length > bln)
		{
			return true;
		}

		return false;
	}

	/**
	 * 判断是否指定了 treeGrid-json 形式
	 * 
	 * @return
	 */
	private boolean isParentProperty(String expr)
	{

		String[] model = config.getParentModel();

		if (model != null && model.length == 2)
		{

			String express = expr.substring(
					expr.indexOf(".") < 0 ? 0 : expr.lastIndexOf(".") + 1,
					expr.length());
			if (model[0].equals(express))
				return true;
		}

		return false;
	}

	/**
	 * 获取 自连接的类中 parent的相关值(用于treeGrid形式) 该方法只处理基本类型
	 * 
	 * 所对应的columnName的值
	 * 
	 * @param columnName
	 * @return
	 * @throws IntrospectionException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	private StringBuilder getValue(Object object, String idName)
			throws IntrospectionException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException
	{

		StringBuilder buff = new StringBuilder();
		if (object == null)
		{
			buff.append("\"parent\"").append(":").append("\"\"");
			return buff;
		}
		List<String> include = config.getIncludeParentProp();

		BeanInfo info;
		Class clazz = object.getClass();
		if (clazz.equals(String.class)) {//addBy 王绪乐
			buff.append("\"parent\"").append(":").append("\"")
					.append(object).append("\"");
			return buff;
		}
		
		info = Introspector.getBeanInfo(clazz);

		// 获取所有私有属性
		PropertyDescriptor[] props = info.getPropertyDescriptors();
		Object value = null;
		boolean hasData = false;

		String column = "";

		for (int i = 0; i < props.length; ++i)
		{
			PropertyDescriptor prop = props[i];
			String name = prop.getName();
			Method accessor = prop.getReadMethod();
			Class clz = prop.getPropertyType();
			Method baseAccessor = null;

			if ((!clz.isPrimitive() && !clz.equals(String.class)))
			{

				continue;
			}

			if (name.equals(idName))
			{

				if (hasData)
				{
					buff.append(",");
				}
				value = accessor.invoke(object, new Object[0]);
				buff.append("\"parent\"").append(":").append("\"")
						.append(value).append("\"");
				hasData = true;
				continue;
			}

			if (include == null)
				continue;
			for (int j = 0; j < include.size(); j++)
			{

				column = include.get(j);
				if (!name.equals(column))
					continue;

				if (hasData)
				{
					buff.append(",");
				}
				value = accessor.invoke(object, new Object[0]);
				buff.append("\"").append(this.expandParentName(name))
						.append("\"");
				buff.append(":").append("\"").append(value).append("\"");
				hasData = true;
			}
		}

		return buff;
	}

	/**
	 * 扩展为 父类字段 全部以parent作为前缀拼接 遵守驼峰命名法
	 * 
	 * @param name
	 * @return
	 */
	private String expandParentName(String name)
	{

		String first = name.substring(0, 1).toUpperCase();
		String expName = "parent" + first + name.substring(1, name.length());

		return expName;
	}

	/**
	 * 获得 对应注解的中文名
	 * 
	 * @param annot
	 * @param mode
	 *            1:显示中文类名 2：显示中文字段类
	 * @return
	 * @throws JSONException
	 */
	@SuppressWarnings("static-access")
	public String getModelPropSymbol(Annotation[] annot, int mode)
			throws JSONException
	{

		boolean bool = true;
		int index = -1;
		String result = null;

		String annotType = "";
		switch (mode)
		{
		case 1:
			annotType = config.MODELPROP;
			break;
		case 2:
			annotType = config.FIELDPROP;
			break;
		}

		for (int j = 0; j < annot.length; j++)
		{
			String annotName = annot[j].annotationType().getName();
			if (annotType.equals(annotName))
			{
				String annotInfo = annot[j].toString();
				if ((index = annotInfo.indexOf(config.SYMBOL)) != -1)
				{
					int start = index + config.SYMBOL.length();
					int end = annotInfo.length();
					String value = annotInfo.substring(start, end);
					start = value.indexOf("=") + 1;
					end = value.indexOf(",") == -1 ? value.indexOf(")") : value
							.indexOf(",");
					value = value.substring(start, end);
					result = value;
					bool = false;
				}

			}
		}

		return result;
	}

}


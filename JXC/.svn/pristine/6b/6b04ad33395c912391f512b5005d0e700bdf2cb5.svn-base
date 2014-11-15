package com.jxc.core.util.json;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.text.CharacterIterator;
import java.text.DateFormat;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sun.org.apache.bcel.internal.generic.GOTO;

@SuppressWarnings({ "unused", "unchecked" })
public class JSONTreeWriter {

	private static final Log log = LogFactory.getLog(JSONWriter.class);

	/** 默认枚举类型作为名值对序列化 */
	public static final boolean ENUM_AS_BEAN_DEFAULT = false;
	private String compartSign;

	static char[] hex = "0123456789ABCDEF".toCharArray();
	private StringBuilder buf = new StringBuilder();
	private Stack stack = new Stack();
	private Object root;
	private boolean buildExpr = true;
	private String exprStack = "";

	private String idField;
	private String textField;

	private JSONTreeConfig config;

	// key:clazzName value:urlName
	private Map<String, String> clazzMappingUrlKey = new HashMap();
	// key:urlName value：url
	private Map<String, String> urlAction = null;

	public JSONTreeWriter() {

		this.config = new JSONTreeConfig();
	}

	public JSONTreeWriter(JSONTreeConfig config) {

		this.config = config;
	}

	/**
	 * @param object
	 *            要进行序列化的对象
	 * @return JSON 对象序列化后的JSON字符串
	 * @throws JSONException
	 */
	public String write(Object object) throws JSONException {
		this.buf.setLength(0);
		this.root = object;
		this.exprStack = "";
		this.idField = this.config.getIdField();
		// this.textField = null ; this.config.getTextField();
		this.urlAction = this.config.getUrlAction();

		// 样式分隔符
		// 表达式 判断
		this.buildExpr = true;
		// 程序入口
		this.value(object, null);

		return this.buf.toString();
	}

	/**
	 * 检测循环调用
	 */
	private void value(Object object, Method method) throws JSONException {

		if (object == null) {
			this.add("null");

			return;
		}

		if (this.idField == null || this.config.getTextField().isEmpty()) {
			if (log.isDebugEnabled()) {
				log.debug("Cannot null property to id or text " + object);
			}

			return;
		}

		// 判断是否包含了相同的对象 (对象子对象相同)
		if (this.stack.contains(object)) {
			Class clazz = object.getClass();

			// 循环调用
			if (clazz.isPrimitive() || clazz.equals(String.class)) {
				this.process(object, method);
			} else {
				// if (log.isDebugEnabled()) {
				// log.debug("Cyclic reference detected on " + object);
				// }
				this.add("null");
			}

			return;
		}

		this.process(object, method);
	}

	/**
	 * 将对象序列化为JSON字符串
	 */
	private void process(Object object, Method method) throws JSONException {
		this.stack.push(object);

		if (object instanceof Class) {
			this.string(object);
		} else if (object instanceof Boolean) {
			this.bool(((Boolean) object).booleanValue());
		} else if (object instanceof Number) {
			this.add(object);
		} else if (object instanceof String) {
			this.string(object);
		} else if (object instanceof Character) {
			this.string(object);
		} else if (object instanceof Map) {
			this.map((Map) object, method);
		} else if (object.getClass().isArray()) {
			this.array(object, method);
		} else if (object instanceof Iterable) {
			this.array(((Iterable) object).iterator(), method);
		} else if (object instanceof Date) {
			this.date((Date) object, method);
		} else if (object instanceof Calendar) {
			this.date(((Calendar) object).getTime(), method);
		} else if (object instanceof Locale) {
			this.string(object);
		} else if (object instanceof Enum) {
			this.enumeration((Enum) object);
		} else {
			this.bean(object);
		}

		this.stack.pop();
	}

	/**
	 * 反射bean并序列化bean的属性
	 */
	@SuppressWarnings("static-access")
	private void bean(Object object) throws JSONException {
		this.add("{");

		BeanInfo info;
		boolean leaf = true;// 叶子
		int leafage = -1;// -1：对象无 leaf属性 0：有此属性 值为false 1:有此属性 值为 true

		int size = 0;
		String nae = "text";

		String val = null;// this.config.getTextField();

		List childList = new ArrayList();

		String exprStackTemp = "";// 临时表达式子
		String classType = null;

		Class clazz = object.getClass();
		try {

			classType = clazz.getName();

			// 是否读取所有父类的属性 (object == this.root) &&
			// this.config.isIgnoreHierarchy()
			info = ((object == this.root) && this.config.isIgnoreHierarchy()) ? Introspector
					.getBeanInfo(clazz, clazz.getSuperclass()) : Introspector
					.getBeanInfo(clazz);

			// 获取所有私有属性
			PropertyDescriptor[] props = info.getPropertyDescriptors();
			boolean ctinue = false;
			boolean hasData = false;
			for (int i = 0; i < props.length; ++i) {
				PropertyDescriptor prop = props[i];
				String name = prop.getName();
				Method accessor = prop.getReadMethod();
				Class clz = prop.getPropertyType();
				Method baseAccessor = null;

				// 处理CGLIB
				if (clazz.getName().indexOf("$$EnhancerByCGLIB$$") > -1) {
					try {
						baseAccessor = Class.forName(
								clazz.getName().substring(0,
										clazz.getName().indexOf("$$")))
								.getMethod(accessor.getName(),
										accessor.getParameterTypes());
					} catch (Exception ex) {
						log.debug(ex.getMessage(), ex);
					}
				} else
					baseAccessor = accessor;

				if (baseAccessor != null) {
					// 忽略 "class"和其他
					if (this.shouldExcludeProperty(clazz, prop)) {
						continue;
					}

					String expr = null;
					if (this.buildExpr) {
						expr = this.expandExpr(name);

						// 忽略非基本类型 以及 深度控制
						if (this.ignoreImprimitive(clz, expr)) {
							continue;
						}

						// 过滤所有不需要的属性
						if (this.shouldExcludeProperty(expr))
							if (this.shouldExcludeChildren(expr))
								continue;

						expr = this.setExprStack(expr);
					}

					Object value = accessor.invoke(object, new Object[0]);

					// 判断是否 类中有名为leaf的属性 有责以它为基准
					if ("leaf".equals(name)) {
						if (value instanceof Boolean) {
							leafage = ((Boolean) value) == false ? 0 : 1;
						}
					}

					// 添加 中文字段显示
					if (config.isFieldSymbol()) {
						String symbol = this.getModelPropSymbol(
								accessor.getAnnotations(), 2);
						if (config.isOverrideSymbol()) {
							name = symbol;
						} else {
							boolean success = this.add(name + config.SYMBOLEND,
									symbol, null, hasData);
							hasData = hasData || success;
						}
					}

					// if( this.isStyle(name) ){
					// //填充树text样式
					// val = this.fillStyle(val,name,""+value);
					// } else

					if ("text".equals(setName(name))) {// 处理 树text
						nae = name;
						val = (String) value;
						if (this.buildExpr) {
							this.setExprStack(expr);
						}
						continue;
					}

					// 判断是否是集合 即子集节点
					if (value instanceof Iterable) {
						size += ((Collection) value).size();
						if (this.config.isAutoLeaf())
							leaf = size > 0 ? false : true;
						else
							leaf = this.config.isLeafageTree();
						name = "children";

						if (((Collection) value).size() > 0) {
							this.clazzMappingUrlKey.put(((Collection) value)
									.iterator().next().getClass().getName(),
									this.exprStack);
						}
						// 合并集合
						this.incorporateCollection(childList,
								(Collection) value);

						// 保存当前递归 表达式子
						exprStackTemp = this.exprStack;
						if (this.buildExpr) {
							this.setExprStack(expr);
						}
						continue;
					}

					boolean propertyPrinted = this.add(name, value, accessor,
							hasData);

					hasData = hasData || propertyPrinted;
					if (this.buildExpr) {
						this.setExprStack(expr);
					}
				}
			}
			// special-case handling for an Enumeration - include the name() as
			// a property */
			if (object instanceof Enum) {
				Object value = ((Enum) object).name();
				this.add("_name", value, object.getClass().getMethod("name"),
						hasData);
			}

		} catch (Exception e) {
			throw new JSONException(e);
		}

		// 是否显示 Iterable size
		if (!leaf && this.config.isIterableAsSize()) {
			val = val + "[" + size + "]";
		}

		// 设置URL
		if (this.urlAction != null) {
			this.add("url", buildUrl(this.exprStack), null, true);
		}

		// 设置DATAURL
		if (this.config.getDataUrlAction() != null) {
			this.add("dataUrl", buildDataUrl(this.exprStack), null, true);
		}

		this.add("classType", classType, null, true);
		this.add(nae, val, null, true); // 拼接 tree-text

		if (leafage != -1)
			leaf = leafage == 0 ? false : true;
		this.add("leaf", leaf, null, true);

		// 是否 显示类的中文名
		if (config.isModelSymbol()) {
			String symbol = this.getModelPropSymbol(clazz.getAnnotations(), 1);
			this.add("class" + config.SYMBOLEND, symbol, null, true);
		}

		// 拼接 当前节点的 子节点
		if (!childList.isEmpty()) {

			exprStackTemp = this.setExprStack(exprStackTemp);
			this.add("children", childList, null, true);
			this.setExprStack(exprStackTemp);
		}

		this.add("}");
	}

	/**
	 * 序列化名值对
	 */
	private boolean add(String name, Object value, Method method,
			boolean hasData) throws JSONException {
		if (!this.config.isExcludeNullProperties() || value != null) {
			if (hasData) {
				this.add(',');
			}
			this.add('"');
			this.add(setName(name));
			this.add("\":");
			// 异步时 不处理'子'
			if (!this.config.isSynchronize() && value instanceof Iterable) {
				if (this.exprStack.indexOf(".") != -1) { // 异步时 只展开一级
					value = null;
				}
			}
			this.value(value, method);

			return true;
		}

		return false;
	}

	/**
	 * 序列化数组Map
	 */
	private void map(Map map, Method method) throws JSONException {
		this.add("{");

		Iterator it = map.entrySet().iterator();
		boolean hasData = false;
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			Object key = entry.getKey();
			String expr = null;
			if (this.buildExpr) {
				if (key == null) {
					log.error("Cannot build expression for null key in "
							+ this.exprStack);
					continue;
				} else {
					// 排除 key 或接纳 key
					expr = this.expandExpr(key.toString());
					if (this.shouldExcludeProperty(expr)) {
						continue;
					}
					expr = this.setExprStack(expr);
				}
			}
			if (hasData) {
				this.add(',');
			}
			hasData = true;
			this.value(key, method);
			this.add(":");
			this.value(entry.getValue(), method);
			if (this.buildExpr) {
				this.setExprStack(expr);
			}
		}

		this.add("}");
	}

	/**
	 * 序列化日期
	 */
	private void date(Date date, Method method) {

		DateFormat formatter = this.config.getFormatter();

		this.string(formatter.format(date));
	}

	/**
	 * 序列化数组
	 */
	private void array(Iterator it, Method method) throws JSONException {

		boolean bool = true;
		Object obj = null;
		// 如果 被序列化对象是一个集合 并且递归进行在第一层中 则json格式不需要 [ ] 括号
		if (this.exprStack.equals("") && this.root instanceof Iterable) {
			bool = false;
		}
		if (bool)
			this.add("[");

		boolean hasData = false;
		for (int i = 0; it.hasNext(); i++) {
			String expr = null;
			obj = it.next();

			if (obj instanceof Iterable) {
				if (((Collection) obj).isEmpty())
					continue;
			}

			// 当一个集合包含一个以上的类型时候 则修复 堆栈表达式子 this.exprStack
			if (this.clazzMappingUrlKey.size() > 1) {
				if (bool) { // 在第一层时 不需要修复 表达式
					if (this.clazzMappingUrlKey.containsKey(obj.getClass()
							.getName()))
						this.exprStack = this.clazzMappingUrlKey.get(obj
								.getClass().getName());
				}
			}

			if (this.buildExpr) {
				expr = this.exprStack;
				if (this.shouldExcludeChildren(expr)) {
					continue;
				}
				expr = this.setExprStack(expr);
			}
			if (hasData) {
				this.add(',');
			}
			hasData = true;
			this.value(obj, method);

			if (this.buildExpr) {
				this.setExprStack(expr);
			}
		}

		if (bool)
			this.add("]");
	}

	/**
	 * 序列化数组
	 */
	private void array(Object object, Method method) throws JSONException {
		this.add("[");

		int length = Array.getLength(object);

		boolean hasData = false;
		for (int i = 0; i < length; ++i) {
			String expr = null;

			if (this.buildExpr) {
				// expr = this.expandExpr(i);
				expr = this.exprStack;
				if (this.shouldExcludeProperty(expr)) {
					continue;
				}
				expr = this.setExprStack(expr);
			}

			if (hasData) {
				this.add(',');
			}
			hasData = true;
			this.value(Array.get(object, i), method);
			if (this.buildExpr) {
				this.setExprStack(expr);
			}
		}

		this.add("]");
	}

	/**
	 * 序列化布尔值
	 */
	private void bool(boolean b) {
		this.add(b ? "true" : "false");
	}

	/**
	 * escape characters
	 */
	private void string(Object obj) {
		this.add('"');

		CharacterIterator it = new StringCharacterIterator(obj.toString());

		for (char c = it.first(); c != CharacterIterator.DONE; c = it.next()) {
			if (c == '"') {
				this.add("\\\"");
			} else if (c == '\\') {
				this.add("\\\\");
			} else if (c == '/') {
				this.add("\\/");
			} else if (c == '\b') {
				this.add("\\b");
			} else if (c == '\f') {
				this.add("\\f");
			} else if (c == '\n') {
				this.add("\\n");
			} else if (c == '\r') {
				this.add("\\r");
			} else if (c == '\t') {
				this.add("\\t");
			} else if (Character.isISOControl(c)) {
				this.unicode(c);
			} else {
				this.add(c);
			}
		}

		this.add('"');
	}

	/**
	 * 中文处理 Represent as unicode
	 * 
	 * @param c
	 *            character to be encoded
	 */
	private void unicode(char c) {
		this.add("\\u");

		int n = c;

		for (int i = 0; i < 4; ++i) {
			// 取高4位 并移到低4位 转int型
			int digit = (n & 0xf000) >> 12;
			// 通过转后的int做为下标 读取相应十六进制字符
			this.add(hex[digit]);
			// 将高4位移除 低4位补零
			n <<= 4;
		}
	}

	/**
	 * Instrospect an Enum and serialize it as a name/value pair or as a bean
	 * including all its own properties
	 */
	private void enumeration(Enum enumeration) throws JSONException {
		if (this.config.isEnumAsBean()) {
			this.bean(enumeration);
		} else {
			this.string(enumeration.name());
		}
	}

	/**
	 * 需泪花object
	 */
	private void add(Object obj) {
		this.buf.append(obj);
	}

	/**
	 * 序列化字符
	 */
	private void add(char c) {
		this.buf.append(c);
	}

	/**
	 * 忽略非基本类型 以及深度控制
	 * 
	 * 
	 * @param clazz
	 *            如果不是基本类型 则返回true 既然被忽略
	 * @param expr
	 *            堆栈表达式子 作用与递归深度控制的判断
	 * @return
	 */
	private boolean ignoreImprimitive(Class clazz, String expr) {
		int bln = this.config.getDepth();
		if (expr.indexOf("]") >= 0) {
			bln++; // 深度
		}
		;

		if (expr.split("\\.").length == bln) {

			if ((!clazz.isPrimitive() && !clazz.equals(String.class))) {
				return true;
			}
		}
		// 忽略子类的子类
		if (expr.split("\\.").length > bln) {
			return true;
		}

		return false;
	}

	/**
	 * 忽略"class"属性
	 * 
	 * @param clazz
	 * @param prop
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 */
	private boolean shouldExcludeProperty(Class clazz, PropertyDescriptor prop)
			throws SecurityException, NoSuchFieldException {
		String name = prop.getName();

		if (name.equals("class") || name.equals("declaringClass")) {
			return true;
		}

		return false;
	}

	private String expandExpr(int i) {
		return this.exprStack + "[" + i + "]";
	}

	/**
	 * 获得 当前堆栈表达式
	 * 
	 * @param property
	 * @return
	 */
	private String expandExpr(String property) {
		if (this.exprStack.length() == 0)
			return property;
		return this.exprStack + "." + property;
	}

	/**
	 * 交换字符串 配合递归特性 可以构成动态"面包线"既 堆栈表达式子
	 * 
	 * @param expr
	 * @return
	 */
	private String setExprStack(String expr) {
		String s = this.exprStack;
		this.exprStack = expr;
		return s;
	}

	/**
	 * 检查是否包含 需要展开的 子
	 * 
	 * @param exprStack
	 *            完整路径表达式
	 * @return --false 不忽略 --true 忽略
	 */
	private boolean shouldExcludeChildren(String expr) {
		List<String> children = this.config.getIncludeChildren();
		String express = null;
		int index = -1;
		if (children != null && !"".equals(expr)) {
			for (String childExpr : children) {

				if ((index = childExpr.lastIndexOf(".*")) > 0) {
					express = expr.substring(expr.lastIndexOf(".") < 0 ? 0
							: expr.lastIndexOf(".") + 1, expr.length());
					if (express.equals(childExpr.substring(0, index))) {
						return false;
					}
				}

				if (expr.equals(childExpr))
					return false;
			}

			return true;
		}

		// 如果 getIncludeProperties 中只包含 id text 2个属性 则显示所有基本类型
		// if(this.config.getIncludeProperties().size() == 2 ){
		// return false ;
		// }

		return false;
	}

	/**
	 * 过滤掉不需要显示的字段 如果集合中不包含则为视为忽略
	 * 
	 * @param expr
	 * @return --false 不忽略 --true 忽略
	 */
	private boolean shouldExcludeProperty(String expr) {

		List<String> includes = this.config.getIncludeProperties();
		String express = expr.substring(
				expr.lastIndexOf(".") < 0 ? 0 : expr.lastIndexOf(".") + 1,
				expr.length());

		// 必选属性
		if (express.equals(this.idField) || this.isTextScope(express))
			return false;

		// 基本属性
		if (includes != null && !"".equals(express)) {
			// (忽略表达式expr层次的比对)
			for (String include : includes) {
				if (express.equals(include)) {
					return false;
				}
			}

			// if (log.isDebugEnabled())
			// log.debug("Ignoring property because of include rule:  " + expr);
			return true;
		}

		return true;
	}

	/**
	 * 根据JSONTreeConfig中的配置url项目 通过 堆栈表达式 获取URL
	 * 
	 * 当expr == "" 时 则表示当前的递归在首层 则需要 “url”作为 key取url
	 * 
	 * @param expr
	 * @return
	 */
	private String buildUrl(String expr) {
		String url = this.urlAction.get(expr);

		// 当递归是首层时 既expr=="" 规定 使用 url作为key值
		if ("".equals(expr)) {
			url = this.urlAction.get("url");
		}
		// 无星(*)号时 有数据则取数据
		if (url != null)
			return url;

		// 判断是否有带星(*)号的处理
		if (this.urlAction.get("isSign").equals("true")) {
			String express = expr.substring(expr.lastIndexOf(".") < 0 ? 0
					: expr.lastIndexOf(".") + 1, expr.length());
			url = this.urlAction.get(express + ".*");
			return url;
		}
		return null;
	}

	/**
	 * 根据JSONTreeConfig中的配置dataUrl项目 通过 堆栈表达式 获取URL
	 * 
	 * 当expr == "" 时 则表示当前的递归在首层 则需要 "url"作为 key取url
	 * 
	 * @param expr
	 * @return
	 */
	private String buildDataUrl(String expr) {
		Map<String, String> dataUrlAction = config.getDataUrlAction();
		String url = dataUrlAction.get(expr);

		// 当递归是首层时 既expr=="" 规定 使用 url作为key值
		if ("".equals(expr)) {
			url = dataUrlAction.get("dataUrl");
		}
		// 无星(*)号时 有数据则取数据
		if (url != null)
			return url;

		// 判断是否有带星(*)号的处理
		if (dataUrlAction.get("isSign").equals("true")) {
			String express = expr.substring(expr.lastIndexOf(".") < 0 ? 0
					: expr.lastIndexOf(".") + 1, expr.length());
			url = dataUrlAction.get(express + ".*");
			return url;
		}
		return null;
	}

	/**
	 * 判断是否为必选属性 作为id 或 text字符串返回
	 * 
	 * @param name
	 * @return
	 */
	private String setName(String name) {

		if (name.equals(this.idField))
			return "id";
		if (this.isTextScope(name))
			return "text";

		return name;
	}

	/**
	 * 判断 该属性是否被设置为 前台EXT树text组件属性
	 * 
	 * @param text
	 * @return
	 */
	private boolean isTextScope(String text) {

		List<String> list = this.config.getTextField();
		if (list.isEmpty())
			return false;
		for (int i = 0; i < list.size(); i++) {
			if (text.equals(list.get(i)))
				return true;
		}
		return false;
	}

	/**
	 * 填充样式 样式数组style[]
	 * 
	 * @param style
	 * @param name
	 * @param value
	 * @return
	 */
	private String fillStyle(String style, String name, String value) {

		if (value == null)
			value = "[null]";
		return style.replaceAll("\\$" + name + "\\$", value);
	}

	/**
	 * 合并集合 将两个集合 合并成几个list集合
	 * 
	 * @param list
	 * @param colle
	 */
	private void incorporateCollection(List list, Collection colle) {

		Iterator it = ((Iterable) colle).iterator();
		while (it.hasNext()) {
			list.add(it.next());
		}
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
			throws JSONException {

		boolean bool = true;
		int index = -1;
		String result = null;

		String annotType = "";
		switch (mode) {
		case 1:
			annotType = config.MODELPROP;
			break;
		case 2:
			annotType = config.FIELDPROP;
			break;
		}

		for (int j = 0; j < annot.length; j++) {
			String annotName = annot[j].annotationType().getName();
			if (annotType.equals(annotName)) {
				String annotInfo = annot[j].toString();
				if ((index = annotInfo.indexOf(config.SYMBOL)) != -1) {
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
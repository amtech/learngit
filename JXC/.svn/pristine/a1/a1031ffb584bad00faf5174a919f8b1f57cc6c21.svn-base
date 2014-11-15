package com.jxc.core.util.json;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import com.jxc.core.util.annotations.FieldProp;
import com.jxc.core.util.annotations.ModelProp;

public class JSONConfig {

	public static final boolean ENUM_AS_BEAN_DEFAULT = false;
	final static String RFC3339_FORMAT = "yyyy-MM-dd HH:mm:ss";

	private List<String> includeProperties; // 可选参数 当不为空时 程序不再花销时间解析
	private List<String> excludeProperties; // 可选参数 去除不显示的类属性
	private boolean ignoreHierarchy = false; // 是否忽视类层次
	private boolean enumAsBean = ENUM_AS_BEAN_DEFAULT;
	private boolean excludeNullProperties = false;// 是否排除为空的字段
	private DateFormat formatter = new SimpleDateFormat(RFC3339_FORMAT);// 日期格式
																		// 默认为

	private String[] parentModel; // 指定 treeGrid 形式时 需要指定父类ID
	private List<String> includeParentProp; // 指定父类的其他属性 该属性所部以 parentxxxx的形式拼接

	private boolean iterableAsSize = false; // 是否打印 size 打印则将关闭 集合类型的展开
	private boolean closCollection = false; // 关闭所有集合类型的序列化

	private boolean encodeHtml = false;// HTML字符转义

	public boolean isEncodeHtml() {
		return encodeHtml;
	}

	public void setEncodeHtml(boolean encodeHtml) {
		this.encodeHtml = encodeHtml;
	}

	// 中文处理
	private boolean modelSymbol = false; // true:开启 显示类的中文名 json格式:
											// [类名简写+Symbol：中文]
	private boolean fieldSymbol = false; // true:开启 显示类的字段中名 json格式:
											// [字段名+Symbol：中文]
	private boolean overrideSymbol = false;// 将字段 用中文名覆盖 pojo字段值不变
	public static final String MODELPROP = ModelProp.class.getName();
	public static final String FIELDPROP = FieldProp.class.getName();
	public static final String SYMBOL = "symbol";
	public static final String SYMBOLEND = "Symbol";

	private int depth = 2;

	public JSONConfig() {

	}

	public List<String> getIncludeProperties() {
		return includeProperties;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	/**
	 * 获得输出JSON需要包含的属性的规则正则表达式
	 * 
	 * @return 编译后的正则表达式列表
	 */
	public void setIncludeProperties(String commaDelim) {
		this.includeProperties = JSONUtil.asList(commaDelim);

	}

	/**
	 * 获得输出JSON需要排除的属性的规则正则表达式
	 * 
	 * @return 编译后的正则表达式列表
	 */
	public List<String> getExcludeProperties() {
		return excludeProperties;
	}

	/**
	 * 设置逗号分隔的正则表达式列表，用于过滤不需要输出为JSON的属性.
	 * 
	 * @param commaDelim
	 *            逗号分隔的正则表达式列表
	 */
	public void setExcludeProperties(String commaDelim) {
		this.excludeProperties = JSONUtil.asList(commaDelim);
	}

	public List<String> getIncludeParentProp() {
		return includeParentProp;
	}

	public void setIncludeParentProp(String commaDelim) {
		this.includeParentProp = JSONUtil.asList(commaDelim);
	}

	public boolean isIgnoreHierarchy() {
		return ignoreHierarchy;
	}

	/**
	 * 忽略父类
	 * 
	 * @param ignoreHierarchy
	 */
	public void setIgnoreHierarchy(boolean ignoreHierarchy) {
		this.ignoreHierarchy = ignoreHierarchy;
	}

	public boolean isEnumAsBean() {
		return enumAsBean;
	}

	/**
	 * 控制枚举类型的序列化型式 : 如果为true,则序列化为名值对 如果为false,则序列化为Bean
	 * 
	 * @param enumAsBean
	 */
	public void setEnumAsBean(boolean enumAsBean) {
		this.enumAsBean = enumAsBean;
	}

	public boolean isExcludeNullProperties() {
		return excludeNullProperties;
	}

	/**
	 * 过滤序列化null值属性
	 * 
	 * @param excludeNullProperties
	 */
	public void setExcludeNullProperties(boolean excludeNullProperties) {
		this.excludeNullProperties = excludeNullProperties;
	}

	public DateFormat getFormatter() {
		return formatter;
	}

	/**
	 * 设置日期的输出格式
	 * 
	 * @param formatter
	 */
	public void setFormatter(String formatter) {
		if (formatter != null && !formatter.equals("")) {
			this.formatter = new SimpleDateFormat(formatter);
		}
	}

	public boolean isIterableAsSize() {
		return iterableAsSize;
	}

	/**
	 * 集合对象输出其包含的对象数量
	 * 
	 * @param iterableAsSize
	 */
	public void setIterableAsSize(boolean iterableAsSize) {
		this.iterableAsSize = iterableAsSize;
	}

	public String[] getParentModel() {
		return parentModel;
	}

	/**
	 * 设置序列化为TreeGridjson 时候 所必须的指明的 父类字段名 以及父类的ID名
	 * 
	 * @param parentName
	 * @param idName
	 */
	public void setParentModel(String parentName, String idName) {
		this.parentModel = new String[2];
		this.parentModel[0] = parentName;
		this.parentModel[1] = idName;
		
		this.setIterableAsSize(true);
	}

	public boolean isModelSymbol() {
		return modelSymbol;
	}

	/**
	 * 开启 中文类名 序列化后的json key值定义为固定的 [class+Symbol]
	 * 
	 * @param modelSymbol
	 */
	public void setModelSymbol(boolean modelSymbol) {
		this.modelSymbol = modelSymbol;
	}

	public boolean isFieldSymbol() {
		return fieldSymbol;
	}

	/**
	 * 开启 中文字段 序列化后的json key值定义为相对应的 [字段名+Symbol]
	 * 
	 * @return
	 */
	public void setFieldSymbol(boolean fieldSymbol) {
		this.fieldSymbol = fieldSymbol;
	}

	public boolean isOverrideSymbol() {
		return overrideSymbol;
	}

	/**
	 * 将中文名 代替原 pojo字段名
	 * 
	 * @param overrideSymbol
	 */
	public void setOverrideSymbol(boolean overrideSymbol) {
		this.overrideSymbol = overrideSymbol;
	}

	public boolean isClosCollection() {
		return closCollection;
	}

	/**
	 * 关闭所有集合类型的序列化
	 * 
	 * @param closCollection
	 */
	public void setClosCollection(boolean closCollection) {
		this.closCollection = closCollection;
	}

}

package com.jxc.core.util.json;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.jxc.core.util.annotations.FieldProp;
import com.jxc.core.util.annotations.ModelProp;

/**
 * *序列化json配置类 其中（显示到EXT树中每个节点）默认包含其属性： （leaf,classType,id,text ） *根据配置的不同
 * 将会多出以下属性 （url,children） *其他属性 需要使用 setIncludeProperties 方法设置
 * 
 * @author sony
 * 
 */
public class JSONTreeConfig {
	public static final boolean ENUM_AS_BEAN_DEFAULT = false;
	final static String RFC3339_FORMAT = "yyyy-MM-dd HH:mm:ss";

	// 可选参数(1.该参数忽略层次 2.其中默认包含了树的 id 与 text
	// 3.不配置则默认显示所有属性
	// 4.一但配置 则严格按照指定的的属性 显示)
	private List<String> includeProperties;

	// 指定要展开哪个子
	// 例子: 1.父子同类（children.*）
	// 2.父子不同类（children1.children2.childrenN）
	private List<String> includeChildren;
	// 指定节点的 url请求 (自身请求)
	// 例子： 1.父子同类（children.*:urlValue）
	// 2.父子不同类(children1:urlval1, children1.children2:urlval2)
	private Map<String, String> urlAction;

	private Map<String, String> dataUrlAction;

	// 当树中包含多个 不同类的 树节点时 需要为没个类 指明text树组件属性将包含在这里
	// 此集合 程序自动处理 无需使用者关心
	private List<String> includeField;

	private boolean ignoreHierarchy = false;// 是否忽视类层次 -- 默认为不忽略 即把父类属性也包含在内
	private boolean enumAsBean = ENUM_AS_BEAN_DEFAULT;
	private boolean excludeNullProperties = false;// 是否排除为空的字段
	private DateFormat formatter = new SimpleDateFormat(RFC3339_FORMAT);// 日期格式
																		// 默认为

	private boolean iterableAsSize = false; // 是否打印 childrens - Size
	private boolean synchronize = true;// true-同步树 false-异步树(延迟加载)

	private String idField; // 必选参数
	private List<String> textField; // 必选参数 多字段拼接请使用,符号-'$' 将字段括起来
									// 例：$colmn-1$拼接$colmn-2$

	private int depth = 100;// 深度控制(默认为100 实际上没有100级的树 )

	private boolean autoLeaf = true; // 是否开启 自动识别 叶子 与 非叶子
	private boolean leafageTree = true;// true -- 整棵树都是叶子 false -- 整棵树都是非叶子

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

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public JSONTreeConfig() {

	}

	public JSONTreeConfig(String id, String name) {
		this.setIdField(id);
		this.setTextField(name);
	}

	public List<String> getIncludeProperties() {
		return includeProperties;
	}

	/**
	 * 获得输出JSON需要包含的属性的规则正则表达式
	 * 
	 * @return 编译后的正则表达式列表
	 */
	public void setIncludeProperties(String commaDelim) {

		this.includeProperties = JSONUtil.asList(commaDelim);
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

	public boolean isSynchronize() {
		return synchronize;
	}

	public void setSynchronize(boolean synchronize) {
		this.synchronize = synchronize;
	}

	public String getIdField() {
		return idField;
	}

	public void setIdField(String idField) {

		this.idField = idField;
	}

	public List<String> getTextField() {
		return textField;
	}

	public void setTextField(String textField) {

		this.textField = JSONUtil.asList(textField);
	}

	public List<String> getIncludeChildren() {
		return includeChildren;
	}

	/**
	 * 指定 所要展开的子对应的集合 1.父子为同一对象的树结构 : 只需指定一个需要展开的 '子'集合
	 * 
	 * 例： this.setIncludeChildren("HashSetChildren"); ps: HashSetChildren 是一个类的
	 * 集合属性名
	 */
	public void setIncludeChildren(String includeChildren) {
		this.includeChildren = JSONUtil.asList(includeChildren);
	}

	public Map<String, String> getUrlAction() {
		return urlAction;
	}

	/**
	 * Ext.node.atrributes.url 指定其属性
	 * 
	 * 树根节点 请使用 url:XXXX 指定， 其子 childName.*:XXXX 指定 指定 节点的请求数据路径 多个属性间使用","号分开
	 */
	public void setUrlAction(String urlAction) {
		this.urlAction = JSONUtil.asMap(urlAction);
	}

	public boolean isLeafageTree() {
		return leafageTree;
	}

	/**
	 * @param leafageTree
	 *            -true 整棵树都是叶子 -false 整棵树都是非叶子
	 */
	public void setLeafageTree(boolean leafageTree) {
		this.leafageTree = leafageTree;
	}

	public boolean isAutoLeaf() {
		return autoLeaf;
	}

	/**
	 * 自动设置 树的 非/是 叶子节点 当为true 时候 setLeafageTree方法失效
	 * 
	 * 默认为 true -- 自动
	 * 
	 * @param autoLeaf
	 */
	public void setAutoLeaf(boolean autoLeaf) {
		this.autoLeaf = autoLeaf;
	}

	public List<String> getIncludeField() {
		return includeField;
	}

	public Map<String, String> getDataUrlAction() {
		return dataUrlAction;
	}

	/**
	 * Ext.node.atrributes.dataUrl 指定其属性
	 * 
	 * 树根节点 请使用 dataUrl:XXXX 指定， 其子 childName.*:XXXX 指定 指定 节点的请求数据路径
	 * 多个属性间使用","号分开
	 * 
	 * @param dataUrlAction
	 */
	public void setDataUrlAction(String dataUrlAction) {

		this.dataUrlAction = JSONUtil.asMap(dataUrlAction);
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

	// main 测试方法
	public static void main(String[] args) {
		JSONTreeConfig c = new JSONTreeConfig();
		String s = "$col1$";

		c.setIdField("id");
		c.setTextField(s);
		c.setIncludeProperties("888,999");
		Iterator<String> ite = c.includeProperties.iterator();
		while (ite.hasNext()) {
			System.out.println(ite.next());
		}
		System.out.println(c.getTextField());
		System.out.println("-------测试 url-------");
		c.setUrlAction("chi.*:filter!method.html,chi.mef:filter!method.html,chi.mef.user:kofe!method2.html");

		Map<String, String> map = c.getUrlAction();
		Iterator i = map.entrySet().iterator();
		while (i.hasNext()) {
			Map.Entry entry = (Map.Entry) i.next();
			Object key = entry.getKey();
			System.out
					.println(key.toString() + " : " + map.get(key.toString()));
		}
	}
}
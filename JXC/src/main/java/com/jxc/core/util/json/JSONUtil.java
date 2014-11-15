package com.jxc.core.util.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class JSONUtil {

	/**
	 * 将Object 序列化为 符合GRID的JSON 格式
	 * 
	 * @return json-string
	 * @throws JSONException
	 */
	public static String serializeForGrid(Object object, String rootName,
			int total, JSONConfig config) throws JSONException {
		config.setEncodeHtml(true);
		StringBuilder buff = new StringBuilder();
		buff.append("{success:true,");
		buff.append("total:" + total);
		buff.append(",");
		buff.append(rootName + ":");
		buff.append(serialize(object, config));
		buff.append("}");
		return buff.toString();
	}

	/**
	 * serializeForGrid 将Object 序列化为 符合GRID的JSON 格式
	 * 
	 * @author Sixday 2010-8-10
	 * 
	 * @param object
	 * @param rootName
	 * @param total
	 * @param config
	 * @param isEncodeHtml
	 * @return
	 * @throws JSONException
	 */

	public static String serializeForGrid(Object object, String rootName,
			int total, JSONConfig config, boolean isEncodeHtml)
			throws JSONException {
		config.setEncodeHtml(isEncodeHtml);
		StringBuilder buff = new StringBuilder();
		buff.append("{success:true,");
		buff.append("total:" + total);
		buff.append(",");
		buff.append(rootName + ":");
		buff.append(serialize(object, config));
		buff.append("}");
		return buff.toString();
	}

	/**
	 * 将object 序列化为 符合FORM的JSON 格式
	 * 
	 * @param object
	 * @param rootName
	 * @param config
	 * @return
	 * @throws JSONException
	 */
	public static String serializeForForm(Object object, String rootName,
			JSONConfig config) throws JSONException {
		StringBuilder buff = new StringBuilder();
		buff.append("{success:true,");
		buff.append("root:[");
		buff.append(serialize(object, config));
		buff.append("]}");
		return buff.toString();
	}

	/**
	 * 将结果集合 序列化为 符合 TreeGrid 格式的JSON
	 * 
	 * @param object
	 *            结果集合
	 * @param config
	 *            -JSONTreeConfig
	 * @return
	 * @throws JSONException
	 */
	public static String serializeForTreeGrid(Object object, String rootName,
			int total, JSONConfig config) throws JSONException {

		StringBuilder buff = new StringBuilder();
		buff.append("{success:true,");
		buff.append("total:" + total);
		buff.append(",");
		buff.append(rootName + ":");
		buff.append(serialize(object, config));
		buff.append("}");
		return buff.toString();
	}

	/**
	 * 将 list集合中的POJO类 或单个树根 序列化为 符合tree的json格式
	 * 
	 * @param object
	 * @param config
	 * @return
	 * @throws JSONException
	 */
	public static String serializeForTree(Object object, JSONTreeConfig config)
			throws JSONException {

		String result = null;
		JSONTreeWriter writer = new JSONTreeWriter(config);
		if (object instanceof Iterable) {

			result = "[" + writer.write(object) + "]";
		} else {
			result = "[" + writer.write(object) + "]";
		}
		return result;
	}

	/**
	 * 将 list集合中的POJO类 或单个树根 序列化为 符合tree的json格式 这里指定的 rootName 主要用于
	 * Ext.template.组件的使用 没有用到rootName就不要指定root
	 * 
	 * @param object
	 * @param config
	 * @return
	 * @throws JSONException
	 */
	public static String serializeForTree(Object object, String rootName,
			JSONTreeConfig config) throws JSONException {

		String result = null;
		JSONTreeWriter writer = new JSONTreeWriter(config);
		result = "{" + rootName + ":[" + writer.write(object) + "]}";
		return result;
	}

	/**
	 * 将 多个list集合中的POJO类 或多个树根 整合到一棵树中 并序列化为 符合tree的json格式 每个 被序列化对象数组object 应与
	 * 配置类数组 一一对应
	 * 
	 * @param object
	 *            []
	 * @param config
	 *            []
	 * @return
	 * @throws JSONException
	 */
	public static String serializeForTree(Object[] object,
			JSONTreeConfig[] config) throws JSONException {

		Object obj = null;
		JSONTreeConfig conf = null;
		JSONTreeWriter writer = null;
		String result = "";
		for (int i = 0; i < object.length; i++) {
			if (i > 0) {
				result += ",";
			}
			obj = object[i];
			conf = config[i];
			writer = new JSONTreeWriter(conf);
			result += writer.write(obj);
		}
		return "[" + result + "]";
	}

	/**
	 * 将Object序列化为JSON.
	 * 
	 * @param object
	 *            要进行序列化的对象
	 * @return JSON 字符串
	 * @throws JSONException
	 */
	public static String serialize(Object object) throws JSONException {
		JSONWriter writer = new JSONWriter();
		return writer.write(object);
	}

	/**
	 * 依据参数将Object序列化为JSON,
	 * 
	 * @param object
	 *            要进行序列化的对象
	 * @param config
	 *            序列化参数
	 * @return JSON 字符串
	 * @throws JSONException
	 */
	public static String serialize(Object object, JSONConfig config)
			throws JSONException {
		JSONWriter writer = new JSONWriter();
		writer.setConfig(config);
		return writer.write(object);
	}

	/**
	 * 使用给定的writer将Object序列化为JSON
	 * 
	 * @param writer
	 *            Writer对象，用于序列化的工具类
	 * @param object
	 *            要进行序列化的对象
	 * @throws IOException
	 * @throws JSONException
	 */
	public static void serialize(Writer writer, Object object)
			throws IOException, JSONException {
		writer.write(serialize(object));
	}

	/**
	 * 使用给定的writer依据参数将Object序列化为JSON.
	 * 
	 * @param writer
	 *            Writer对象，用于序列化的工具类
	 * @param object
	 *            要进行序列化的对象
	 * @param config
	 *            序列化参数
	 * @throws IOException
	 * @throws JSONException
	 */
	public static void serialize(Writer writer, Object object, JSONConfig config)
			throws IOException, JSONException {
		writer.write(serialize(object, config));
	}

	/**
	 * JSON反序列化，将JSON字符串反序列化为一个Object对象
	 * 
	 * @param obj
	 *            要反序列化的JSON字符串
	 * @return desrialized 反序列化后得到的对象
	 * @throws JSONException
	 */
	public static Object deserialize(String obj) throws JSONException {
		JSONReader reader = new JSONReader();
		return reader.read(obj);
	}

	/**
	 * JSON反序列化，使用给定的Reader将JSON字符串反序列化为一个Object对象
	 * 
	 * @param reader
	 *            Reader对象，用于反序列化的工具类
	 * @return deserialized 反序列化后得到的对象
	 * @throws JSONException
	 */
	public static Object deserialize(Reader reader) throws JSONException {
		// read content
		BufferedReader bufferReader = new BufferedReader(reader);
		String line = null;
		StringBuilder buffer = new StringBuilder();

		try {
			while ((line = bufferReader.readLine()) != null) {
				buffer.append(line);
			}
		} catch (IOException e) {
			throw new JSONException(e);
		}

		return deserialize(buffer.toString());
	}

	/**
	 * 将字符串 转化为MAP形式
	 * 
	 * @param commaDelim
	 * @return
	 */
	public static Map<String, String> asMap(String commaDelim) {
		if ((commaDelim == null) || (commaDelim.trim().length() == 0))
			return null;
		Map<String, String> map = new HashMap<String, String>();
		map.put("isSign", "false");
		if (commaDelim.indexOf(".*") >= 0) {
			map.put("isSign", "true");
		}
		String[] split = commaDelim.split(",");
		String key = null;
		String value = null;
		for (int i = 0; i < split.length; i++) {
			String trimmed = split[i].trim();
			if (trimmed.length() > 0) {
				key = trimmed.substring(0, trimmed.indexOf(":"));
				if (key.trim().length() == 0)
					continue;
				value = trimmed.substring(trimmed.indexOf(":") + 1,
						trimmed.length());
				map.put(key.trim(), value);
			}
		}

		return map;
	}

	/**
	 * 将以 ","符号 连接的字符串 拆分
	 * 
	 * @param commaDelim
	 * @return
	 */
	public static List<String> asList(String commaDelim) {
		if ((commaDelim == null) || (commaDelim.trim().length() == 0))
			return null;
		List<String> list = new ArrayList<String>();
		String[] split = commaDelim.split(",");
		for (int i = 0; i < split.length; i++) {
			String trimmed = split[i].trim();
			if (trimmed.length() > 0) {
				list.add(trimmed);
			}
		}
		return list;
	}

	/**
	 * 将字符串中 被 "$"符号包围的 属性 拆分出来
	 * 
	 * @param style
	 *            传进来的 带 $符号的字符串
	 * @return List 拆分后的结果集合
	 */
	@SuppressWarnings({ "unchecked" })
	public static List<String> styleAsList(String style) {

		if (style == null || style.trim().length() == 0)
			return null;
		String tmpStr = style + "sign";
		List list = new ArrayList<String>();
		if (tmpStr.split("\\$").length >= 3) {
			String[] arr = style.split("\\$");
			for (int i = 1; i < arr.length; i = i + 2) {
				String column = arr[i].trim();
				if (column.length() > 0) {
					list.add(column);
				}
			}
		} else {
			list.add(style);
		}

		return list;
	}

	/**
	 * 合并集合
	 * 
	 * @param coll
	 *            本集合
	 * @param aimColl
	 *            目标合
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Collection mergeCollection(Collection coll, Collection aimColl) {

		if (coll == null || aimColl == null)
			return null;
		coll.addAll(aimColl);

		return coll;
	}

	public static interface ClassVisitor {

		/**
		 * 遇到新的接口/类时被调用
		 * 
		 * @param clazz
		 *            遇到的新的接口/类
		 * @return 如果为true递归继续，如果false递归立即停止
		 * */
		@SuppressWarnings("unchecked")
		boolean visit(Class clazz);
	}

	/**
	 * 通过指定的对象，访问所有被实现的接口，接口的超类和接口
	 * 
	 * Visitation按照下面的顺序执行: 类 类的接口 接口的超类（接口） 类的超类 超类的接口 超类的接口的超类（接口） 超类的超类 等等
	 * 
	 * 除了对象自身，类/接口的访问紧次一次
	 * 
	 * @param clazz
	 *            开始向上递归的类
	 * @param visitor
	 *            遇到类/接口时要调用的访问器
	 * @return true表示所有的类/接口都被访问则返回true，false表示提前退出
	 */
	public static boolean visitInterfaces(Class clazz, ClassVisitor visitor) {
		List<Class> classesVisited = new LinkedList<Class>();
		return visitUniqueInterfaces(clazz, visitor, classesVisited);
	}

	/**
	 * 如果还没有被访问，访问类的所有接口（接口的超类和接口）的递归方法
	 * 
	 * 如果没有被访问则小哦能够使访问自己
	 * 
	 * @param thisClass
	 *            当前访问的类
	 * @param classesVisited
	 *            已访问的类
	 * @param visitor
	 *            遇到类/接口时要调用的访问器
	 * @return 如果为true递归继续，false则退出
	 */
	@SuppressWarnings("unchecked")
	private static boolean visitUniqueInterfaces(Class thisClass,
			ClassVisitor visitor, List<Class> classesVisited) {
		boolean okayToContinue = true;

		if (!classesVisited.contains(thisClass)) {
			classesVisited.add(thisClass);
			okayToContinue = visitor.visit(thisClass);

			if (okayToContinue) {
				Class[] interfaces = thisClass.getInterfaces();
				int index = 0;
				while ((index < interfaces.length) && (okayToContinue)) {
					okayToContinue = visitUniqueInterfaces(interfaces[index++],
							visitor, classesVisited);
				}

				if (okayToContinue) {
					Class superClass = thisClass.getSuperclass();
					if ((superClass != null)
							&& (!Object.class.equals(superClass))) {
						okayToContinue = visitUniqueInterfaces(superClass,
								visitor, classesVisited);
					}
				}
			}
		}
		return okayToContinue;
	}

	public static void main(String[] args) {

	}

}

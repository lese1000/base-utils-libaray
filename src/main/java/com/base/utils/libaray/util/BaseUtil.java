package com.base.utils.libaray.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

//import org.apache.commons.lang.StringUtils;

public class BaseUtil
{
	/**
	 * 生成树状路径
	 * @param lenstr
	 * @param index
	 * @return
	 */
	public static String createTreePath(String pathLen,int addVal){
		java.text.DecimalFormat df = new java.text.DecimalFormat(pathLen);
		return df.format(pathLen);
	}
	
	/**
	 * 数字排重(只支持数字)
	 * @param str 
	 * @return
	 */
	public static String noRepeat(String str){  
        char[] chars = new char[255];  
        char[] input = str.toCharArray();  
  
        int temp;  
        for(int i = 0;i< input.length;i++){  
            temp = input[i];  
            if(chars[temp] == 0)  
                chars[temp] = 1;  
        }  
  
        StringBuilder sb = new StringBuilder();  
        for (int i = 0; i < chars.length; i++) {  
            if(chars[i] == 1)  
                sb.append((char)i);  
        }  
        return sb.toString();  
    }  
	
	/**
	 * 判断是否null 则返回空对象
	 * @param o
	 * @return
	 */
	public static Object returnNullObject(Object o){
		return isNullOrEmpty(o)?new HashMap<String,Object>():o;
	}
	
	/**
	 * 判断是否null 则返回空对象MapList
	 * @param o
	 * @return
	 */
	@SuppressWarnings("unused")
	public static Object returnNullListObject(List<Map<String,Object>> o){
		for (int i=0;i<o.size();i++){
			return isNullOrEmpty(o.get(0))?new ArrayList<HashMap<String,Object>>():o;
		}
		return o;
	}
	
	/**
	 * 去除数组中重复的记录   cpx
	 * @param a
	 * @return
	 */
	public static String[] array_unique(String[] a) {  
		// array_unique  
		List<String> list = new LinkedList<String>();  
		for(int i = 0; i < a.length; i++) {  
		    if(!list.contains(a[i])) {  
		        list.add(a[i]);  
		    }  
		}  
		return (String[])list.toArray(new String[list.size()]);  
	}  
	
	/**
	 * 检测字符串是否不为空(null,"","null")
	 * @param s
	 * @return 不为空则返回true，否则返回false
	 */
	public static boolean notEmpty(String s){
		return s!=null && !"".equals(s) && !"null".equals(s);
	}
	
	/**
	 * 检测字符串是否为空(null,"","null")
	 * @param s
	 * @return 为空则返回true，不否则返回false
	 */
	public static boolean isEmpty(String s){
		return s==null || "".equals(s) || "null".equals(s);
	}
	
	/**
	 * 判断对象或对象数组中每一个对象是否为空: 对象为null，字符序列长度为0，集合类、Map为empty
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isNullOrEmpty(Object obj) {
		if (obj == null)
			return true;

		if (obj instanceof CharSequence)
			return ((CharSequence) obj).length() == 0;

		if (obj instanceof Collection)
			return ((Collection) obj).isEmpty();

		if (obj instanceof Map)
			return ((Map) obj).isEmpty();

		if (obj instanceof Object[]) {
			Object[] object = (Object[]) obj;
			if (object.length == 0) {
				return true;
			}
			boolean empty = true;
			for (int i = 0; i < object.length; i++) {
				if (!isNullOrEmpty(object[i])) {
					empty = false;
					break;
				}
			}
			return empty;
		}
		return false;
	}
	
	/**
    *
    * @Title: substring 
    * @Description: TODO(截取后缀) 
    * @param @param String
    * @return String    返回类型 
    * @throws
     */
    public static String substring(String path){
    	//path = StringUtils.substring(path,StringUtils.lastIndexOf(path,".") + 1);
    	return path;
    }
	
  public static String toString(String value)
	{
		if (value == null)
		{
			return "";
		}
		String str = value.replaceAll("\r\n", "<br>");
		str = str.replaceAll("<br> ", "<br>&nbsp;");
		str = str.replaceAll("  ", "&nbsp;&nbsp;");
		str = str.replaceAll("\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
		return str;
	}

	public static String toString(int value)
	{
		DecimalFormat numFormat = new DecimalFormat("#");
		return numFormat.format(value);
	}

	public static String toString(long value)
	{
		DecimalFormat numFormat = new DecimalFormat("#");
		return numFormat.format(value);
	}

	public static String toString(float value)
	{
		DecimalFormat numFormat = new DecimalFormat("#.#####");
		return numFormat.format(value);
	}

	public static String toString(double value)
	{
		DecimalFormat numFormat = new DecimalFormat("#.#####");
		return numFormat.format(value);
	}

	public static String toString(double value, int n)
	{
		String str = "";
		for (int i = 0; i < n; i++)
		{
			str = str + "0";
		}
		DecimalFormat numFormat = new DecimalFormat("0." + str);
		return numFormat.format(value);
	}

	public static String toString(Date date)
	{
		if (date == null)
		{
			return "";
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(date);
	}

	public static String toTimeString(Date date)
	{
		if (date == null)
		{
			return "";
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(date);
	}

	public static String toLocaleString(Date date)
	{
		if (date == null)
		{
			return "";
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
		return dateFormat.format(date);
	}

	public static boolean toBoolean(String value)
	{
		if (value == null)
		{
			return false;
		}
		return value.equals("true");
	}

	public static boolean toBoolean(int value)
	{
		return value == 1;
	}

	public static int toInt(String parameter)
	{
		try
		{
			return Integer.parseInt(parameter);
		} catch (Exception ex)
		{
		}
		return 0;
	}

	public static long toLong(String parameter)
	{
		try
		{
			return Long.parseLong(parameter);
		} catch (Exception ex)
		{
		}
		return 0L;
	}

	public static float toFloat(String parameter)
	{
		try
		{
			return Float.parseFloat(parameter);
		} catch (Exception ex)
		{
		}
		return 0.0F;
	}

	public static double toDouble(String parameter)
	{
		try
		{
			return Double.parseDouble(parameter);
		} catch (Exception ex)
		{
		}
		return 0.0D;
	}

	public static String replace(String source, String find, String replacewith)
	{
		if ((source == null) || (find == null))
		{
			return source;
		}
		int index = source.indexOf(find);
		if (index == -1)
		{
			return source;
		}
		int nOldLength = find.length();
		if (nOldLength == 0)
		{
			return source;
		}
		int indexStart = index + nOldLength;
		StringBuffer strDest = new StringBuffer(0);
		strDest.append(source.substring(0, index) + replacewith);
		while ((index = source.indexOf(find, indexStart)) != -1)
		{
			strDest.append(source.substring(indexStart, index) + replacewith);
			indexStart = index + nOldLength;
		}
		strDest.append(source.substring(indexStart));
		return strDest.toString();
	}

	public static String toString(String[] values)
	{
		String value = "";
		if (values != null)
		{
			for (int i = 0; i < values.length; i++)
			{
				if (i == 0)
					value = value + values[i];
				else
					value = value + ";" + values[i];
			}
		}
		return value;
	}
}
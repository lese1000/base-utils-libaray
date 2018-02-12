package com.base.utils.libaray.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

/**
 * Copyright: Copyright (c) 2017
 * Version: 1.0.0
 * Author: ...
 * Date: 2017/5/10 0010
 * Description:字符转化帮助类
 */
public class DataUtil {

    public static String getAsString(Object obj){
        if (obj == null) {
            return null;
        }
        return obj.toString();
    }

    public static String byteToString(byte[] by){
        if (by.length < 1) {
            return null;
        }
        return new String(by);
    }

    public static short getAsShort(Object obj) {
        if (obj == null){
            return 0;
        }
        if (obj instanceof Number) {
            return ((Number)obj).shortValue();
        }
        return ((Short)transfer(obj, Short.class)).shortValue();
    }

    public static int getAsInt(Object obj) {
        if (obj == null){
            return 0;
        }
        if (obj instanceof Number) {
            return ((Number)obj).intValue();
        }
        return ((Integer)transfer(obj, Integer.class)).intValue();
    }

    public static long getAsLong(Object obj) {
        if (obj == null){
            return 0L;
        }
        if (obj instanceof Number) {
            return ((Number)obj).longValue();
        }
        return ((Long)transfer(obj, Long.class)).longValue();
    }
    
    public static double getAsDouble(Object obj) {
    	String str = getAsString(obj);
    	String cha = " ";
		char ch = DataUtil.getAsChar(cha);
    	str = remove(str,ch);//去除特殊空格
    	obj = (Object)str;
        if (obj == null){
            return 0.0D;
        }
        if (obj instanceof Number) {
            return ((Number)obj).doubleValue();
        }
        return ((Double)transfer(obj, Double.class)).doubleValue();
    }

    public static float getAsFloat(Object obj) {
        if (obj == null){
            return 0.0F;
        }
        if (obj instanceof Number) {
            return ((Number)obj).floatValue();
        }
        return ((Float)transfer(obj, Float.class)).floatValue();
    }

    public static byte getAsByte(Object obj) {
        if (obj == null){
            return 0;
        }
        if (obj instanceof Number) {
            return ((Number)obj).byteValue();
        }
        return ((Byte)transfer(obj, Byte.class)).byteValue();
    }

    public static boolean getAsBoolean(Object obj) {
        if (obj == null){
            return false;
        }
        if (obj instanceof Boolean) {
            return ((Boolean)obj).booleanValue();
        }
        return ((Boolean)transfer(obj, Boolean.class)).booleanValue();
    }

    public static char getAsChar(Object obj) {
        if (obj == null){
            return '\0';
        }
        if (obj instanceof Character){
            return ((Character)obj).charValue();
        }
        if ((obj instanceof String) && (((String)obj).length() == 1)) {
            return ((String)obj).charAt(0);
        }
        return ((Character)transfer(obj, Character.class)).charValue();
    }

    /*
    * Description:对象转日
    * */
    public static java.sql.Date getAsDate(Object obj) {
        if (obj == null){
            return null;
        }
        if (obj instanceof java.sql.Date){
            return ((java.sql.Date)obj);
        }
        if (obj instanceof Timestamp){
            return new java.sql.Date(((Timestamp)obj).getTime());
        }
        String msg ="Cannot transform "+obj.toString()+" to type "+obj.getClass().getName();
        throw new RuntimeException(msg);
    }

    /*
    * Description:对象转时间
    * */
    public static Time getAsTime(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Time) {
            return ((Time) obj);
        }
        if (obj instanceof Timestamp){
            return new Time(((Timestamp)obj).getTime());
        }
        String msg ="Cannot transform "+obj.toString()+" to type "+obj.getClass().getName();
        throw new RuntimeException(msg);
    }

    /*
    * Description:对象转日期
    * */
    public static Timestamp getAsDateTime(Object obj){
        if (obj == null) {
            return null;
        }
        if (obj instanceof Timestamp) {
            return ((Timestamp) obj);
        }
        if (obj instanceof java.sql.Date){
            return new Timestamp(((java.sql.Date)obj).getTime());
        }
        String msg ="Cannot transform "+obj.toString()+" to type "+obj.getClass().getName();
        throw new RuntimeException(msg);
    }

    /*
    * Description:按类转化对象
    * */
    public static Object transfer(Object value, Class type){
        SimpleDateFormat a;
        String msg;
        try {
            if (value == null){ return null;}
            if ((value instanceof String) && (value.toString().trim().equals(""))) {
                if (String.class.equals(type)) {
                    return value;
                }
                return null;
            }

            if ((type.equals(Short.class)) || (type.equals(Short.TYPE))) {
                if (value instanceof Short) {
                    return value;
                }
                return new Short(new BigDecimal(value.toString()).shortValue());
            }
            if ((type.equals(Integer.class)) || (type.equals(Integer.TYPE))) {
                if (value instanceof Integer) {
                    return value;
                }
                return new Integer(new BigDecimal(value.toString()).intValue());
            }
            if ((type.equals(Character.class)) || (type.equals(Character.TYPE))) {
                if (value instanceof Character) {
                    return value;
                }
                return new Character(value.toString().charAt(0));
            }
            if ((type.equals(Long.class)) || (type.equals(Long.TYPE))) {
                if (value instanceof Long) {
                    return value;
                }
                return new Long(new BigDecimal(value.toString()).longValue());
            }
            if (type.equals(String.class)) {
                if (value instanceof String) {
                    return value;
                }
                return value.toString();
            }
            if (type.equals(java.sql.Date.class)) {
                if (value instanceof java.sql.Date) {
                    return value;
                }
                if (value instanceof java.util.Date) {
                    return new java.sql.Date(((java.util.Date) value).getTime());
                }
                a = new SimpleDateFormat("yyyy-MM-dd");
                return new java.sql.Date(a.parse(value.toString()).getTime());
            }
            if (type.equals(Time.class)) {
                if (value instanceof Time){
                    return value;
                }
                if (value instanceof java.util.Date){
                    return new Time(((java.util.Date)value).getTime());
                }
                a = new SimpleDateFormat("HH:mm:ss");
                return new Time(a.parse(value.toString()).getTime());
            }
            if (type.equals(Timestamp.class)) {
                if (value instanceof Timestamp) {
                    return value;
                }
                if (value instanceof java.util.Date) {
                    return new Timestamp(((java.util.Date) value).getTime());
                }
                a = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String tmpstr = value.toString();
                if (tmpstr.trim().length() <= 10) {
                    tmpstr = tmpstr + " 00:00:00";
                }
                return new Timestamp(a.parse(tmpstr).getTime());
            }
            if ((type.equals(Double.class)) || (type.equals(Double.TYPE))) {
            	String str = getAsString(value);
            	String cha = " ";
            	if(str.contains(cha)){
            		char ch = DataUtil.getAsChar(cha);
                	str = remove(str,ch);//去除特殊空格
                	value = (Object)str;
            	}
                if (value instanceof Double) {
                    return value;
                }
                return new Double(new BigDecimal(value.toString()).doubleValue()); }
            if ((type.equals(Float.class)) || (type.equals(Float.TYPE))) {
                if (value instanceof Float) {
                    return value;
                }
                return new Float(new BigDecimal(value.toString()).floatValue()); }
            if ((type.equals(Byte.class)) || (type.equals(Byte.TYPE))) {
                if (value instanceof Byte) {
                    return value;
                }
                return new Byte(new BigDecimal(value.toString()).byteValue()); }
            if ((type.equals(Boolean.class)) || (type.equals(Boolean.TYPE))) {
                if (value instanceof Boolean) {
                    return value;
                }
                if (value instanceof Number) {
                    if (((Number)value).doubleValue() > 0.0D) {
                        return new Boolean(true);
                    }
                    return new Boolean(false);
                }
                if (value instanceof String) {
                    if ((((String)value).equalsIgnoreCase("true")) || (((String)value).equalsIgnoreCase("y"))) {
                        return new Boolean(true);
                    }
                    return new Boolean(false);
                }
            }
        }catch (Exception e){
            msg ="Cannot transform "+value.toString()+" to type "+type;
            throw new RuntimeException(msg, e);
        }
        return value;
    }

	public static String remove(String resource,char ch){
		if(null == resource){
			return "";
		}
	    StringBuffer buffer=new StringBuffer();
	    int position=0;
	    char currentChar;
	    while(position<resource.length()){
	        currentChar=resource.charAt(position++);
	        if(currentChar!=ch) buffer.append(currentChar);
	    }
	    return buffer.toString();
	}
	
    /*
    * Description:对象转化大数据
    * */
    public static BigDecimal getAsBigDecimal(Object obj) {
        if (obj == null){
            return new BigDecimal("0");
        }
        return new BigDecimal(obj.toString());
    }
   
   	public static final String REGEX_NUM = "^[0-9]+\\.{0,1}[0-9]{0,}$";
	public static boolean isNumber(String str) {
	    return Pattern.matches(REGEX_NUM, str);
	}
	
    public static BigDecimal getStrToBigDecimal(String str) {
        if (str == null){
            return new BigDecimal("0");
        }
        str = str.replace(",", "");
        if (!isNumber(str)){
            return new BigDecimal("0");
        }
        return new BigDecimal(str);
    }


    /**
     * DES算法密钥
     */
    private static final byte[] DES_KEY = { 5, 45, -110, 82, -32, -85, -127, -65 };//取值范围是-128~127

    /**
     * JavaBean转换成xml
     * 默认编码UTF-8
     * @param obj
     * @param
     * @return
     */
    public static String convertToXml(Object obj) {
        return convertToXml(obj, "UTF-8");
    }

    /**
     * JavaBean转换成xml
     * @param obj
     * @param encoding
     * @return
     */
    public static String convertToXml(Object obj, String encoding) {
        String result = null;
        try {
            JAXBContext context = JAXBContext.newInstance(obj.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);

            StringWriter writer = new StringWriter();
            marshaller.marshal(obj, writer);
            result = writer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * xml转换成JavaBean
     * @param xml
     * @param c
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T converyToJavaBean(String xml, Class<T> c) {
        T t = null;
        try {
            JAXBContext context = JAXBContext.newInstance(c);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            t = (T) unmarshaller.unmarshal(new StringReader(xml));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return t;
    }

    public static boolean isBlankNull(String str){
        return (org.apache.commons.lang.StringUtils.isBlank(str) || str.equalsIgnoreCase("NULL"));
    }
    
    public static boolean isNotBlankNull(String str){
        return !isBlankNull(str);
    }
    
    //解决中文乱码
    public static String normalCode(String str){
    	try{
        	str = new String(str.getBytes("ISO-8859-1"),"UTF-8");
    	}catch (Exception e) {
			e.printStackTrace();
		}
        return str;
    }
    
    public static String makelinefeed(String s,int l) {
		if(s==null || "".equals(s.trim())){
			return "";
		}
        StringBuffer buffer = new StringBuffer();
        s=s.replaceAll("\r\n", " ").replaceAll("\t", " ").replaceAll("\n", " ");
        int len = s.length();
        int num=len%l;
        if(num==0){
        	num=len/l;
        }else{
        	num=len/l+1;
        }
        for(int i=0;i<num;i++){
        	if(i!=0){
        		buffer.append("\n");
        	}
        	if(i!=num-1){
        		buffer.append(s.substring(i*l, (i+1)*l));
        	}else{
        		buffer.append(s.substring(i*l, len));
        	}
        	
        }
        return buffer.toString();
    }

}

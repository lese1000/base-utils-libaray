package com.base.utils.libaray.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimeZone;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;

/**
 * 
 * @author lese_tech
 *
 */
public class JacksonUtils {

	private static final ObjectMapper objectMapper;
	static {
		objectMapper = new ObjectMapper();
		// 去掉默认的时间戳格式
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		// 设置为中国上海时区
		objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		objectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
		// 空值不序列化
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		// 反序列化时，属性不存在的兼容处理
		objectMapper.getDeserializationConfig().withoutFeatures(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		// 序列化时，日期的统一格式
		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		// 单引号处理
		objectMapper.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		// objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
	}

	
	/**
	 * json String  Upper case  
	 * 1111111 json string convert to javaBean  
	 */
	public static <T> T upperCaseToPojo(String json, Class<T> clazz) {
		try {
			objectMapper.setPropertyNamingStrategy(new PropertyNamingStrategy() {
				private static final long serialVersionUID = 1L;

				// 反序列化时调用
				@Override
				public String nameForSetterMethod(MapperConfig<?> config, AnnotatedMethod method, String defaultName) {
					return method.getName().substring(3);
				}

				// 序列化时调用
				@Override
				public String nameForGetterMethod(MapperConfig<?> config, AnnotatedMethod method, String defaultName) {
					return method.getName().substring(3);
				}
			});
			return objectMapper.readValue(json, clazz);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 1111111 json string convert to javaBean
	 */
	public static <T> T toPojo(String json, Class<T> clazz) {
		try {
			return objectMapper.readValue(json, clazz);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 22222222 javaBean,list,array convert to json string
	 */
	public static <T> String toJson(T entity) {
		try {
			return objectMapper.writeValueAsString(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 222222222 javaBean,list,array convert to json string
	 */
	public static String toJson2(Object obj) {
		try {
			return objectMapper.writeValueAsString(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
    
    /**333333333
     * javaBean,list,array convert to json string
     */
    public static String toJson3(Object obj) {
    	try {
    		 objectMapper.setDateFormat(null);  
    		return objectMapper.writeValueAsString(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return null;
    }
    
    /**444444444
     * javaBean,list,array convert to json string
     */
    public static <T> String toJson4(T entity) {  
        try {  
        	 objectMapper.setDateFormat(null);  
            return objectMapper.writeValueAsString(entity);  
        } catch (Exception e) {  
            e.printStackTrace();
        } 
        return null;  
    } 

	/**
	 * json string convert to map
	 */
	public static <T> Map<String, Object> toMap(String jsonStr) {
		try {
			return objectMapper.readValue(jsonStr, Map.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * json string convert to map with javaBean
	 */
	public static <T> Map<String, T> toMap(String jsonStr, Class<T> clazz) {
		try {
			Map<String, Map<String, Object>> map = objectMapper.readValue(jsonStr, new TypeReference<Map<String, T>>() {
			});
			Map<String, T> result = new HashMap<String, T>();
			for (Entry<String, Map<String, Object>> entry : map.entrySet()) {
				result.put(entry.getKey(), mapToPojo(entry.getValue(), clazz));
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * json array string convert to list with javaBean
	 */
	public static <T> List<T> toList(String jsonArrayStr, Class<T> clazz) {
		try {
			List<Map<String, Object>> list = objectMapper.readValue(jsonArrayStr, new TypeReference<List<T>>() {
			});
			List<T> result = new ArrayList<T>();
			for (Map<String, Object> map : list) {
				result.add(mapToPojo(map, clazz));
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * map convert to javaBean
	 */
	public static <T> T mapToPojo(Map map, Class<T> clazz) {
		return objectMapper.convertValue(map, clazz);
	}

	public static <T> T toCollection(String json, TypeReference<T> typeReference) {
		try {
			return objectMapper.readValue(json, typeReference);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取泛型的Collection Type
	 * 
	 * @param collectionClass
	 *            泛型的Collection
	 * @param elementClasses
	 *            元素类
	 * @return JavaType Java类型
	 * @since 1.0
	 */
	public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
		return objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
	}

	
	
}
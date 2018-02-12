package com.base.utils.libaray.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.QName;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

/**
 * 
 * @author lese
 *将xml转换成Map,能够应对不用结构的xml,而不是只针对固定格式的xml.
 *转换规则:
 *1.主要是Map与List的互相嵌套
 *2.同名称的节点会被装进List
 */
public class XmlUtils { 
	
	public static Map<String,Object> xml2Map(String str){
		Document doc =null;
		try {
			doc = DocumentHelper.parseText(str);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return xml2Map(doc);
	}
  @SuppressWarnings("unchecked")  
    public static Map<String, Object> xml2Map(Document doc){ 
        Map<String, Object> map = new HashMap<String, Object>(); 
        if(doc == null) 
            return map; 
        Element root = doc.getRootElement(); 
        return buildXml2Map(root); 
    } 
     @SuppressWarnings("unchecked")
    public static Map buildXml2Map(Element e){ 
        Map map = new HashMap(); 
        List list = e.elements(); 
        addAttr(map, e.attributes());
        if(list.size() > 0){ 
            for (int i = 0;i < list.size(); i++) { 
                Element iter = (Element) list.get(i); 
                List mapList = new ArrayList(); 
                 
                if(iter.elements().size() > 0){ 
                    Map m = buildXml2Map(iter); 
                    if(map.get(iter.getName()) != null){ 
                        Object obj = map.get(iter.getName()); 
                        if(!obj.getClass().getName().equals("java.util.ArrayList")){ 
                            mapList = new ArrayList();
//                            Map<String,Object> nameMap = new HashMap<String,Object>();
//                            nameMap.put(iter.attribute(QName.get("name")).getValue(), obj);
                            mapList.add(obj); 
                            addAttr(m, iter.attributes());
                            mapList.add(m); 
                        } 
                        if(obj.getClass().getName().equals("java.util.ArrayList")){ 
                            mapList = (List) obj; 
                            mapList.add(m); 
                        } 
                        map.put(iter.getName(), mapList); 
                    }else 
                        map.put(iter.getName(), m); 
                } 
                else{ 
                    if(map.get(iter.getName()) != null){ 
                        Object obj = map.get(iter.getName()); 
                        if(!obj.getClass().getName().equals("java.util.ArrayList")){
                        	iter.attribute(QName.get("name")).getValue();
                            mapList = new ArrayList(); 
                            mapList.add(obj); 
                            mapList.add(iter.getText()); 
                        } 
                        if(obj.getClass().getName().equals("java.util.ArrayList")){ 
                            mapList = (List) obj; 
                            mapList.add(iter.getText()); 
                        } 
                        map.put(iter.getName(), mapList); 
                    }else 
                        map.put(iter.getName(), iter.getText()); 
                } 
            } 
        }else {
        	 map.put(e.getName(), e.getText()); 
        }
        return map; 
    } 
     
     @SuppressWarnings("unchecked")
     public static Map buildXml2Map_QName(Element e){ 
         Map map = new HashMap(); 
         List list = e.elements(); 
         addAttr(map, e.attributes());
         if(list.size() > 0){ 
             for (int i = 0;i < list.size(); i++) { 
                 Element iter = (Element) list.get(i); 
                 List mapList = new ArrayList(); 
                  
                 if(iter.elements().size() > 0){ 
                     Map m = buildXml2Map(iter); 
                     if(map.get(iter.getName()) != null){ 
                         Object obj = map.get(iter.getName()); 
                         if(!obj.getClass().getName().equals("java.util.ArrayList")){ 
                             mapList = new ArrayList(); 
                             mapList.add(obj); 
                             addAttr(m, iter.attributes());
                             mapList.add(m); 
                         } 
                         if(obj.getClass().getName().equals("java.util.ArrayList")){ 
                             mapList = (List) obj; 
                             mapList.add(m); 
                         } 
                         map.put(iter.getName(), mapList); 
                     }else 
                         map.put(iter.getName(), m); 
                 } 
                 else{ 
                     if(map.get(iter.getName()) != null){ 
                         Object obj = map.get(iter.getName()); 
                         if(!obj.getClass().getName().equals("java.util.ArrayList")){
//                         	iter.attribute(QName.get("name")).getValue();
                             mapList = new ArrayList(); 
                             mapList.add(obj); 
                             mapList.add(iter.getText()); 
                         } 
                         if(obj.getClass().getName().equals("java.util.ArrayList")){ 
                             mapList = (List) obj; 
                             mapList.add(iter.getText()); 
                         } 
                         map.put(iter.getName(), mapList); 
                     }else 
                         map.put(iter.getName(), iter.getText()); 
                 } 
             } 
         }else {
         	 map.put(e.getName(), e.getText()); 
         }
         return map; 
     } 
     
     /**
      * 添加属性值
      * @param map
      * @param arr
      */
     private static void addAttr(Map<String, Object> map,List arr){
    	 if(arr!=null && arr.size()>0){
    		 for(int i=0;i<arr.size();i++){
    	     		Attribute a=(Attribute) arr.get(i);
    	     		map.put(a.getName(), a.getText()); 
    	     	}
    	 }
     }
     
     /**
      * 生成的xml中除了String类型，其他会添加Type属性。
      * @param map
      * @param rootElement 指定xml根元素
      * @return
      */
     public static String map2XmlAddAttrType(Map<String, Object> map, String rootElement) {
         Document doc = DocumentHelper.createDocument();
         Element body = DocumentHelper.createElement(rootElement);
         doc.add(body);
         buildMap2XmlAddAttrType(body, map);
         return doc.asXML();
     }
     /**
      * 生成的xml不添加元素属性
      * @param map
      * @param rootElement
      * @return
      */
     public static String map2XmlWithoutAttr(Map<String, Object> map, String rootElement) {
         Document doc = DocumentHelper.createDocument();
         Element body = DocumentHelper.createElement(rootElement);
         doc.add(body);
         buildMap2XmlWithoutAttr(body, map);
         return doc.asXML();
     }
      
     /**
      * 未完待续
      * @param body
      * @param map
      * 根据数据类型，在元素上增加type属性。String,map,list类型不添加
      * eg:<Address type="java.util.Map">
      * <AddressLine3></AddressLine3>
	  *	<AddressLine2></AddressLine2>
      * </Address>
      */
     private static void buildMap2XmlAddAttrType(Element body, Map<String, Object> map) {
         if (map != null) {
             Iterator<String> it = map.keySet().iterator();
             while (it.hasNext()) {
                 String key = (String) it.next();
                 if (null!=key&&!key.equals("")) {
                     Object obj = map.get(key);
                    
                     if (obj != null) {
                         if (obj instanceof java.lang.String) {
                        	 Element element = DocumentHelper.createElement(key);
                             element.setText((String) obj);
                             body.add(element);
                         } else {
                             if (obj instanceof java.lang.Character || obj instanceof java.lang.Boolean || obj instanceof java.lang.Number
                                     || obj instanceof java.math.BigInteger || obj instanceof java.math.BigDecimal) {
                            	 Element element = DocumentHelper.createElement(key);
                                 Attribute attr = DocumentHelper.createAttribute(element, "type", obj.getClass().getCanonicalName());
                                 element.add(attr);
                                 element.setText(String.valueOf(obj));
                                 body.add(element);
                             } else if (obj instanceof java.util.Map) {
                            	 Element element = DocumentHelper.createElement(key);
                                 buildMap2XmlAddAttrType(element, (Map<String, Object>) obj);
                                 body.add(element);
                             } else if (obj instanceof java.util.List){
                            	 for(Object item:(List)obj){
                            		 Element itemElement = DocumentHelper.createElement(key);
                            		 if( item instanceof java.util.Map){
                            			 buildMap2XmlAddAttrType(itemElement,(Map)item);
                            		 }else{
                            			 if (item instanceof java.lang.Character || item instanceof java.lang.Boolean || item instanceof java.lang.Number
                                                 || item instanceof java.math.BigInteger || item instanceof java.math.BigDecimal) {
                                             Attribute attr = DocumentHelper.createAttribute(itemElement, "type", item.getClass().getCanonicalName());
                                             itemElement.add(attr);
                                             itemElement.setText(String.valueOf(item));
                                         }
                            		 }
                            		 body.add(itemElement);
                            	 }
                             }
                         }
                     }
                     
                 }
             }
         }
     }
     
     /**
      * 生成的xml不添加元素属性type
      * @param body
      * @param map
      */
     public static void buildMap2XmlWithoutAttr(Element body, Map<String, Object> map){
    	 if (map != null) {
             Iterator<String> it = map.keySet().iterator();
             while (it.hasNext()) {
                 String key = (String) it.next();
                 Object item = map.get(key);
                 if (item instanceof java.util.Map) {
                	 Element itemElement = DocumentHelper.createElement(key);
                	 buildMap2XmlWithoutAttr(itemElement,(Map)item);
                	 body.add(itemElement); 
                 }else if(item instanceof java.util.List){
                	 for(Object obj:(List)item){
                		 Element itemElement = DocumentHelper.createElement(key);
                		 if( obj instanceof java.util.Map){
                        	 buildMap2XmlWithoutAttr(itemElement,(Map)obj);
                		 }else{
                			 itemElement.setText(String.valueOf(obj));
                		 }
                		 body.add(itemElement);
                	 }
                 }else{
                	 Element itemElement = DocumentHelper.createElement(key);
                	 itemElement.setText(String.valueOf(item));
                	 body.add(itemElement); 
                 }
                 
             }
    	 }
     }
     
     // smz  add start
     /** 
      * xml转map 不带属性 
      * @param xmlStr 
      * @param needRootKey 是否需要在返回的map里加根节点键 
      * @return 
      * @throws DocumentException 
      */  
     public static Map xml2map(String xmlStr, boolean needRootKey) throws DocumentException {  
         Document doc = DocumentHelper.parseText(xmlStr);  
         Element root = doc.getRootElement();  
         Map<String, Object> map = (Map<String, Object>) xml2map(root);  
         if(root.elements().size()==0 && root.attributes().size()==0){  
             return map;  
         }  
         if(needRootKey){  
             //在返回的map里加根节点键（如果需要）  
             Map<String, Object> rootMap = new HashMap<String, Object>();  
             rootMap.put(root.getName(), map);  
             return rootMap;  
         }  
         return map;  
     }  
   
     /** 
      * xml转map 带属性 
      * @param xmlStr 
      * @param needRootKey 是否需要在返回的map里加根节点键 
      * @return 
      * @throws DocumentException 
      */  
     public static Map xml2mapWithAttr(String xmlStr, boolean needRootKey) throws DocumentException {  
         Document doc = DocumentHelper.parseText(xmlStr);  
         Element root = doc.getRootElement();  
         Map<String, Object> map = (Map<String, Object>) xml2mapWithAttr(root);  
         if(root.elements().size()==0 && root.attributes().size()==0){  
             return map; //根节点只有一个文本内容  
         }  
         if(needRootKey){  
             //在返回的map里加根节点键（如果需要）  
             Map<String, Object> rootMap = new HashMap<String, Object>();  
             rootMap.put(root.getName(), map);  
             return rootMap;  
         }  
         return map;  
     }  
   
     /** 
      * xml转map 不带属性 
      * @param e 
      * @return 
      */  
     private static Map xml2map(Element e) {  
         Map map = new LinkedHashMap();  
         List list = e.elements();  
         if (list.size() > 0) {  
             for (int i = 0; i < list.size(); i++) {  
                 Element iter = (Element) list.get(i);  
                 List mapList = new ArrayList();  
   
                 if (iter.elements().size() > 0) {  
                     Map m = xml2map(iter);  
                     if (map.get(iter.getName()) != null) {  
                         Object obj = map.get(iter.getName());  
                         if (!(obj instanceof List)) {  
                             mapList = new ArrayList();  
                             mapList.add(obj);  
                             mapList.add(m);  
                         }  
                         if (obj instanceof List) {  
                             mapList = (List) obj;  
                             mapList.add(m);  
                         }  
                         map.put(iter.getName(), mapList);  
                     } else  
                         map.put(iter.getName(), m);  
                 } else {  
                     if (map.get(iter.getName()) != null) {  
                         Object obj = map.get(iter.getName());  
                         if (!(obj instanceof List)) {  
                             mapList = new ArrayList();  
                             mapList.add(obj);  
                             mapList.add(iter.getText());  
                         }  
                         if (obj instanceof List) {  
                             mapList = (List) obj;  
                             mapList.add(iter.getText());  
                         }  
                         map.put(iter.getName(), mapList);  
                     } else  
                         map.put(iter.getName(), iter.getText());  
                 }  
             }  
         } else  
             map.put(e.getName(), e.getText());  
         return map;  
     }  
   
     /** 
      * xml转map 带属性 
      * @param e 
      * @return 
      */  
     private static Map xml2mapWithAttr(Element element) {  
         Map<String, Object> map = new LinkedHashMap<String, Object>();  
   
         List<Element> list = element.elements();  
         List<Attribute> listAttr0 = element.attributes(); // 当前节点的所有属性的list  
         for (Attribute attr : listAttr0) {  
//             map.put("@" + attr.getName(), attr.getValue());  
         	  map.put(attr.getName(), attr.getValue());  
         }  
         if (list.size() > 0) {  
   
             for (int i = 0; i < list.size(); i++) {  
                 Element iter = list.get(i);  
                 List mapList = new ArrayList();  
   
                 if (iter.elements().size() > 0) {  
                     Map m = xml2mapWithAttr(iter);  
                     if (map.get(iter.getName()) != null) {  
                         Object obj = map.get(iter.getName());  
                         if (!(obj instanceof List)) {  
                             mapList = new ArrayList();  
                             mapList.add(obj);  
                             mapList.add(m);  
                         }  
                         if (obj instanceof List) {  
                             mapList = (List) obj;  
                             mapList.add(m);  
                         }  
                         map.put(iter.getName(), mapList);  
                     } else  
                         map.put(iter.getName(), m);  
                 } else {  
   
                     List<Attribute> listAttr = iter.attributes(); // 当前节点的所有属性的list  
                     Map<String, Object> attrMap = null;  
                     boolean hasAttributes = false;  
                     if (listAttr.size() > 0) {  
                         hasAttributes = true;  
                         attrMap = new LinkedHashMap<String, Object>();  
                         for (Attribute attr : listAttr) {  
//                             attrMap.put("@" + attr.getName(), attr.getValue()); 
                         	 attrMap.put(attr.getName(), attr.getValue());  
                         }  
                     }  
   
                     if (map.get(iter.getName()) != null) {  
                         Object obj = map.get(iter.getName());  
                         if (!(obj instanceof List)) {  
                             mapList = new ArrayList();  
                             mapList.add(obj);  
                             // mapList.add(iter.getText());  
                             if (hasAttributes) {  
//                                 attrMap.put("#text", iter.getText());  
                             	attrMap.put("text", iter.getText());  
                                 mapList.add(attrMap);  
                             } else {  
                                 mapList.add(iter.getText());  
                             }  
                         }  
                         if (obj instanceof List) {  
                             mapList = (List) obj;  
                             // mapList.add(iter.getText());  
                             if (hasAttributes) { 
//                                 attrMap.put("#text", iter.getText());  
                             	attrMap.put("text", iter.getText());   
                                 mapList.add(attrMap);  
                             } else {  
                                 mapList.add(iter.getText());  
                             }  
                         }  
                         map.put(iter.getName(), mapList);  
                     } else {  
                         // map.put(iter.getName(), iter.getText());  
                         if (hasAttributes) {  
//                           attrMap.put("#text", iter.getText());  
 	                      	attrMap.put("text", iter.getText());  
 	                      	map.put(iter.getName(), attrMap);  
                         } else {  
                             map.put(iter.getName(), iter.getText());  
                         }  
                     }  
                 }  
             }  
         } else {  
             // 根节点的  
             if (listAttr0.size() > 0) {  
//                 map.put("#text", element.getText()); 
             	map.put("text", element.getText());  
             } else {  
                 map.put(element.getName(), element.getText());  
             }  
         }  
         return map;  
     }  
       
     /** 
      * map转xml map中没有根节点的键 
      * @param map 
      * @param rootName 
      * @throws DocumentException 
      * @throws IOException 
      */  
     public static Document map2xml(Map<String, Object> map, String rootName) throws DocumentException, IOException  {  
         Document doc = DocumentHelper.createDocument();  
         Element root = DocumentHelper.createElement(rootName);  
         doc.add(root);  
         map2xml(map, root);  
         //System.out.println(doc.asXML());  
         //System.out.println(formatXml(doc));  
         return doc;  
     }  
       
     /** 
      * map转xml map中含有根节点的键 
      * @param map 
      * @throws DocumentException 
      * @throws IOException 
      */  
     public static Document map2xml(Map<String, Object> map) throws DocumentException, IOException  {  
         Iterator<Map.Entry<String, Object>> entries = map.entrySet().iterator();  
         if(entries.hasNext()){ //获取第一个键创建根节点  
             Map.Entry<String, Object> entry = entries.next();  
             Document doc = DocumentHelper.createDocument();  
             Element root = DocumentHelper.createElement(entry.getKey());  
             doc.add(root);  
             map2xml((Map)entry.getValue(), root);  
             //System.out.println(doc.asXML());  
             //System.out.println(formatXml(doc));  
             return doc;  
         }  
         return null;  
     }  
       
     /** 
      * map转xml 
      * @param map 
      * @param body xml元素 
      * @return 
      */  
     private static Element map2xml(Map<String, Object> map, Element body) {  
         Iterator<Map.Entry<String, Object>> entries = map.entrySet().iterator();  
         while (entries.hasNext()) {  
             Map.Entry<String, Object> entry = entries.next();  
             String key = entry.getKey();  
             Object value = entry.getValue();  
             if(key.startsWith("@")){    //属性  
                 body.addAttribute(key.substring(1, key.length()), value.toString());  
             } else if(key.equals("#text")){ //有属性时的文本  
                 body.setText(value.toString());  
             } else {  
                 if(value instanceof java.util.List ){  
                     List list = (List)value;  
                     Object obj;  
                     for(int i=0; i<list.size(); i++){  
                         obj = list.get(i);  
                         //list里是map或String，不会存在list里直接是list的，  
                         if(obj instanceof java.util.Map){  
                             Element subElement = body.addElement(key);  
                             map2xml((Map)list.get(i), subElement);  
                         } else {  
                             body.addElement(key).setText((String)list.get(i));  
                         }  
                     }  
                 } else if(value instanceof java.util.Map ){  
                     Element subElement = body.addElement(key);  
                     map2xml((Map)value, subElement);  
                 } else {  
                     body.addElement(key).setText(value.toString());  
                 }  
             }  
             //System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  
         }  
         return body;  
     }  
       
     /** 
      * 格式化输出xml 
      * @param xmlStr 
      * @return 
      * @throws DocumentException 
      * @throws IOException 
      */  
     public static String formatXml(String xmlStr) throws DocumentException, IOException  {  
         Document document = DocumentHelper.parseText(xmlStr);  
         return formatXml(document);  
     }  
       
     /** 
      * 格式化输出xml 
      * @param document 
      * @return 
      * @throws DocumentException 
      * @throws IOException 
      */  
     public static String formatXml(Document document) throws DocumentException, IOException  {  
         // 格式化输出格式  
         OutputFormat format = OutputFormat.createPrettyPrint();  
         //format.setEncoding("UTF-8");  
         StringWriter writer = new StringWriter();  
         // 格式化输出流  
         XMLWriter xmlWriter = new XMLWriter(writer, format);  
         // 将document写入到输出流  
         xmlWriter.write(document);  
         xmlWriter.close();  
         return writer.toString();  
     }  
     
     /**
 	 * 将对象直接转换成String类型的 XML输出
 	 * 
 	 * @param obj
 	 * @return
 	 */
 	public static String convertToXml(Object obj) {
 		// 创建输出流
 		StringWriter sw = new StringWriter();
 		try {
 			// 利用jdk中自带的转换类实现
 			JAXBContext context = JAXBContext.newInstance(obj.getClass());

 			Marshaller marshaller = context.createMarshaller();
 			// 格式化xml输出的格式
 			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
 					Boolean.TRUE);
 			// 将对象转换成输出流形式的xml
 			marshaller.marshal(obj, sw);
 		} catch (JAXBException e) {
 			e.printStackTrace();
 		}
 		return sw.toString();
 	}

 	/**
 	 * 将对象根据路径转换成xml文件
 	 * 
 	 * @param obj
 	 * @param path
 	 * @return
 	 */
 	public static void convertToXml(Object obj, String path) {
 		try {
 			// 利用jdk中自带的转换类实现
 			JAXBContext context = JAXBContext.newInstance(obj.getClass());

 			Marshaller marshaller = context.createMarshaller();
 			// 格式化xml输出的格式
 			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
 					Boolean.TRUE);
 			// 将对象转换成输出流形式的xml
 			// 创建输出流
 			FileWriter fw = null;
 			try {
 				fw = new FileWriter(path);
 			} catch (IOException e) {
 				e.printStackTrace();
 			}
 			marshaller.marshal(obj, fw);
 		} catch (JAXBException e) {
 			e.printStackTrace();
 		}
 	}

 	@SuppressWarnings("unchecked")
 	/**
 	 * 将String类型的xml转换成对象
 	 */
 	public static Object convertXmlStrToObject(Class clazz, String xmlStr) {
 		Object xmlObject = null;
 		try {
 			JAXBContext context = JAXBContext.newInstance(clazz);
 			// 进行将Xml转成对象的核心接口
 			Unmarshaller unmarshaller = context.createUnmarshaller();
 			StringReader sr = new StringReader(xmlStr);
 			xmlObject = unmarshaller.unmarshal(sr);
 		} catch (JAXBException e) {
 			e.printStackTrace();
 		}
 		return xmlObject;
 	}

 	@SuppressWarnings("unchecked")
 	/**
 	 * 将file类型的xml转换成对象
 	 */
 	public static Object convertXmlFileToObject(Class clazz, String xmlPath) {
 		Object xmlObject = null;
 		try {
 			JAXBContext context = JAXBContext.newInstance(clazz);
 			Unmarshaller unmarshaller = context.createUnmarshaller();
 			FileReader fr = null;
 			try {
 				fr = new FileReader(xmlPath);
 			} catch (FileNotFoundException e) {
 				e.printStackTrace();
 			}
 			xmlObject = unmarshaller.unmarshal(fr);
 		} catch (JAXBException e) {
 			e.printStackTrace();
 		}
 		return xmlObject;
 	}
 	
     
     //   smz add end
} 
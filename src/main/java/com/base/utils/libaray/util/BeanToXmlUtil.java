package com.base.utils.libaray.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class BeanToXmlUtil<T> {

	private T t;

	public T getT() {
		return t;
	}

	public BeanToXmlUtil() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BeanToXmlUtil(T t) {
		super();
		this.t = t;
	}

	public String toXml() {
		try {
			JAXBContext context = JAXBContext.newInstance(t.getClass()); // 获取上下文对象
			Marshaller marshaller = context.createMarshaller(); // 根据上下文获取marshaller对象

			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8"); // 设置编码字符集
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true); // 格式化XML输出，有分行和缩进
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			marshaller.marshal(t, baos);
			String xmlObj = new String(baos.toByteArray()); // 生成XML字符串
			return xmlObj.trim();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 将XML字符串转换为指定类型的pojo
	 * 
	 * @param clazz
	 * @param xmlStr
	 * @return
	 */
	public <T extends Object> T xmlStrToObject(String xmlStr) throws Exception {
		Object xmlObject = null;
		Reader reader = null;
		try {
			JAXBContext context = JAXBContext.newInstance(t.getClass());
			// 进行将Xml转成对象的核心接口
			Unmarshaller unmarshaller = context.createUnmarshaller();
			reader = new StringReader(xmlStr);
			xmlObject = unmarshaller.unmarshal(reader);
		} finally {
			if (null != reader) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return (T) xmlObject;
	}
}

package com.base.utils.libaray.util;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestUtils {
	private static final Logger logger = LoggerFactory.getLogger(RequestUtils.class);
	private static volatile HttpServletRequest requestLocal= null;
	

	public static HttpServletRequest getRequest() {
		 return (HttpServletRequest)requestLocal;  
	}

	public static void setRequest(HttpServletRequest request) {
		  requestLocal = request; 
	}
}
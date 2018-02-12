package com.base.utils.libaray.util;

public class GetParamsToJsonUtil {

	/**
	 * get 请求参数转json数据格式
	 * @param param
	 * @return
	 */
	public static String transformJson(String param) {
		String[] params = param.split("&");
		StringBuilder sb = new StringBuilder("{");
		for (int i = 0; i < params.length; i++) {
			String str = params[i];
			String[] keyValue = str.split("=");
			sb.append("\"" + keyValue[0] + "\":").append("\"" + keyValue[1] + "\"");
			if (i != (params.length - 1)) {
				sb.append(",");
			}
		}
		sb.append("}");
		return sb.toString();
	}

}

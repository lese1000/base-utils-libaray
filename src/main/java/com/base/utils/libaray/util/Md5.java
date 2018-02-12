package com.base.utils.libaray.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5 {
	
	public static String getMd5String(String text) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(text.getBytes());
			byte b[] = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			return buf.toString();// 32位的加密
//			return buf.toString().substring(8, 24);// 16位的加密
		} catch (NoSuchAlgorithmException e) {		
			e.printStackTrace();
		}
		return null;
	}
	
//	public static void main(String[] args) {
//		System.out.println(Md5.getMd5String("user123654"));
//	}
}

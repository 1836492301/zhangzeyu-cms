package com.zhangzeyu.cms.util;



import org.apache.commons.codec.digest.DigestUtils;
public class Md5Util {
	private static String salt="1a2b3c4d";
	
	public static String md5Encoding(String password) {
		
		return  DigestUtils.md5Hex(password+salt);
	}

	
	
	
	
	
	
	public static void main(String[] args) {
		
		
		System.out.println(Md5Util.md5Encoding("123456"));
		System.out.println(DigestUtils.md5Hex("123456"));
		
	}
}

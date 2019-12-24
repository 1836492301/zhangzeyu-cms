package com.zhangzeyu.cms.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.propertyeditors.URLEditor;

public class CookieUtil {

	public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
		String encode;
		try {
			encode = URLEncoder.encode(value,"UTF-8");	
			Cookie cookie = new Cookie(name, encode);
			cookie.setPath("/");
			if (maxAge > 0) {
				cookie.setMaxAge(maxAge);
			}
			response.addCookie(cookie);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static Cookie getCookieByName(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(name))
					return cookie;
			}
		}
		return null;
	}
	
}

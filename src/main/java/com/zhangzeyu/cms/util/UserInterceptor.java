package com.zhangzeyu.cms.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.zhangzeyu.cms.dao.UserMapper;
import com.zhangzeyu.cms.domain.User;

public class UserInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		HttpSession session = request.getSession(false);
		if (null != session) {
			Object object = session.getAttribute("user");
			if (null != object)
				return true;
		}

		if(rememberAutoLogin(request,request.getSession()))
			return true;
			
		request.setAttribute("error", "权限不符合,请重新登录");
		request.getRequestDispatcher("/WEB-INF/view/passport/login.jsp").forward(request, response);

		return false;

	}

	@Resource
	private UserMapper userMapper;

	private boolean rememberAutoLogin(HttpServletRequest request,HttpSession session) throws UnsupportedEncodingException {
		Cookie cokUsername = CookieUtil.getCookieByName(request, "username");		
		Cookie cokPassword = CookieUtil.getCookieByName(request, "password");
		if (null != cokUsername && null != cokPassword && null != cokUsername.getValue()		//获取cookie 判断是否为空
				&& null != cokPassword.getValue()) {
			String username =URLDecoder.decode(cokUsername.getValue(),"UTF-8");			//获取cookie中的username
			String password = URLDecoder.decode(cokPassword.getValue(),"UTF-8");		//获取cookie中的password
			User user = userMapper.selectByName(username);	
			if (null != user && username.equals(user.getUsername()) && password.equals(user.getPassword())) {
				session.setAttribute("user", user);
				
				return true;
			}
		}
		return false;

	}
}

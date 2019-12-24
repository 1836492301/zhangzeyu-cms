package com.zhangzeyu.cms.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
public class AdminInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		HttpSession session = request.getSession(false);		//
	   if(null!=session) {
		   Object object = session.getAttribute("admin");
		   if(null!=object)
			  return true;
	   }
	   
	   request.setAttribute("error", "权限不符合,请重新登录");
	   request.getRequestDispatcher("/WEB-INF/view/passport/login.jsp").forward(request, response);	
	   
	   return false;
		
	}	
}

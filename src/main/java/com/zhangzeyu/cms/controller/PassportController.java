package com.zhangzeyu.cms.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.zhangzeyu.cms.domain.User;
import com.zhangzeyu.cms.service.UserService;
import com.zhangzeyu.cms.util.CMSException;
import com.zhangzeyu.cms.util.CookieUtil;
import com.bobo.common.utils.StringUtil;

@RequestMapping("passport")
@Controller
public class PassportController {

	@Resource
	private UserService userService;

	@GetMapping("login")
	public String login() {

		return "passport/login";

	}
	

	/**
	 * 	登录
	 * @param model
	 * @param user
	 * @param session
	 * @param response
	 * @return
	 */
	@PostMapping("login")
	public String login(Model model,User user,HttpSession session,HttpServletResponse response) {
		
		try {
			User u = userService.login(user);
			if(StringUtil.hasText(user.getIsRemember())) {
				CookieUtil.addCookie(response,"username", u.getUsername(), 60 * 60 * 24 * 30);//存一个月
				CookieUtil.addCookie(response,"password", u.getPassword(), 60 * 60 * 24 * 30);//存一个月
			}
			
			if("0".equals(u.getRole())){			//普通用户,进入个人中心
				session.setAttribute("user", u);
				return "redirect:/my";
			}else {
				session.setAttribute("admin", u);
				return "redirect:/admin";//管理员				
			}
			
		} catch (CMSException e) {
			e.printStackTrace();
			model.addAttribute("error", e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", "系统异常,请于管理员联系");
		}
	
		
		return "passport/login";
	}

	@GetMapping("reg")
	public String reg() {

		return "passport/reg";

	}

	@PostMapping("reg")
	public String reg(Model model, User user, RedirectAttributes redirectAttributes) {
		try {
			int i = userService.insertSelective(user);
			if (i > 0) {
				redirectAttributes.addFlashAttribute("username", user.getUsername());
				return "redirect:/passport/login";
			}
		} catch (CMSException e) {
			e.printStackTrace();
			model.addAttribute("error", e.getMessage());

		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", "系统错误.请联系管理员");
		}
		model.addAttribute("user", user);
		return "passport/reg";

	}
	
	
	@GetMapping("logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/passport/login";
		
	}
	

}

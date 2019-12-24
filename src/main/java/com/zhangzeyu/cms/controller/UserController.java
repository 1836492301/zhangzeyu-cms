package com.zhangzeyu.cms.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhangzeyu.cms.domain.User;
import com.zhangzeyu.cms.service.UserService;
import com.github.pagehelper.PageInfo;
@RequestMapping("user")
@Controller
public class UserController {
	@Resource
	private UserService userService;
	
	@GetMapping("selects")
	public String selects(Model model,String username,
			@RequestParam(defaultValue = "1")Integer page,
			@RequestParam(defaultValue = "3")Integer pageSize) {
		PageInfo<User> info = userService.selects(username, page, pageSize);
		model.addAttribute("info", info);
		model.addAttribute("username", username);
		
		return "admin/user/users";
	}
	@ResponseBody
	@PostMapping("update")
	public boolean update(User user) {
		return userService.updateByPrimaryKeySelective(user)>0;
	}

}

package com.zhangzeyu.cms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("article")
@Controller
public class ArticleController {


	@GetMapping("selects")
	public String selects() {
		return "admin/article/articles";
		
	}
	
	
	

}

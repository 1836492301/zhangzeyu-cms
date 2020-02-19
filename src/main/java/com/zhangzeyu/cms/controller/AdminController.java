package com.zhangzeyu.cms.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhangzeyu.cms.domain.Article;
import com.zhangzeyu.cms.domain.ArticleWithBLOBs;
import com.zhangzeyu.cms.domain.Complain;
import com.zhangzeyu.cms.domain.User;
import com.zhangzeyu.cms.service.ArticleService;
import com.zhangzeyu.cms.service.ComplainService;
import com.zhangzeyu.cms.service.UserService;
import com.zhangzeyu.cms.vo.ComplainVO;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;


@RequestMapping("admin")
@Controller
public class AdminController {
	@Resource
	private UserService userService;
	@Resource
	private ArticleService articleService;
	@SuppressWarnings("rawtypes")
	@Autowired
	private KafkaTemplate<String, String> kafka;
	@Resource
	private ComplainService complainService;
	
	@RequestMapping(value = { "/", "index", "" })
	public String index() {
		return "admin/index";
	}
	
	
	@GetMapping("article/selects")
	public String articles(Model model, Article article, @RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "3") Integer pageSize) {
		//默认文章审核状态为 待审
		if(article.getStatus()==null) {
			article.setStatus(0);
		}
		
		
		PageInfo<Article> info = articleService.selects(article, page, pageSize);
		model.addAttribute("info", info);
		model.addAttribute("article", article);
		return "admin/article/articles";
		
	}


	@GetMapping("user/selects")
	public String selects(Model model, String username, @RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "3") Integer pageSize) {
		PageInfo<User> info = userService.selects(username, page, pageSize);
		model.addAttribute("info", info);
		model.addAttribute("username", username);

		return "admin/user/users";
	}
	
	
	@GetMapping("article/select")
	public String select(Model model ,Integer id) {
		ArticleWithBLOBs a = articleService.selectByPrimaryKey(id);
		
		model.addAttribute("a", a);
		return "admin/article/article";
	}
	
	
	@ResponseBody
	@PostMapping("article/update")
	public boolean update(ArticleWithBLOBs article) {
		ArticleWithBLOBs aaa = articleService.selectByPrimaryKey(article.getId());
		aaa.setTitle("add"+aaa.getTitle());
		String artic = JSON.toJSONString(aaa);
		kafka.send("article", artic);
		return articleService.updateByPrimaryKeySelective(article)> 0;
	}
	
	@ResponseBody
	@PostMapping("user/update")
	public boolean update(User user) {
		return userService.updateByPrimaryKeySelective(user) > 0;
	}
	
	
	//查询投诉
	@GetMapping("article/complains")
	public String complain(Model model ,ComplainVO complainVO , 
			@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "3") Integer pageSize) {

		PageInfo<Complain> info = complainService.selects(complainVO, page, pageSize);
		model.addAttribute("info", info);
		model.addAttribute("complainVO", complainVO);
		
		return "admin/article/complains";
	}

}

package com.zhangzeyu.cms.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.zhangzeyu.cms.domain.Article;
import com.zhangzeyu.cms.domain.ArticleWithBLOBs;
import com.zhangzeyu.cms.domain.Category;
import com.zhangzeyu.cms.domain.Channel;
import com.zhangzeyu.cms.domain.Comment;
import com.zhangzeyu.cms.domain.User;
import com.zhangzeyu.cms.service.ArticleService;
import com.zhangzeyu.cms.service.ChannelService;
import com.zhangzeyu.cms.service.CommentService;
import com.github.pagehelper.PageInfo;

@RequestMapping("my")
@Controller
public class MyController {
	
	@Resource
	private ChannelService channelService;
	
	
	@Resource
	private ArticleService articleService;
	
	@Resource
	private CommentService commentService;
	@RequestMapping(value = {"","/","index"})
	public String index() {
		
		return "my/index";
		
	}
	@RequestMapping("article/comments")
	public String comments(Model model,HttpSession session) {
		User user = (User) session.getAttribute("user");
		Comment comment = new Comment();
		comment.setUserId(user.getId());
		PageInfo<Comment> info = commentService.selects(comment, 1, 100);
		model.addAttribute("info", info);
		return "my/article/comments";
		
	}
	@GetMapping("article/publish")
	public String publish() {
		
		return "my/article/publish";
	}
	@ResponseBody
	@GetMapping("channel/selects")
	public List<Channel> selectChannels(){
		return channelService.selects();
	}
	@ResponseBody
	@GetMapping("category/selectsByChannelId")
	public List<Category> selectsByChannelId(Integer channelId){
		return channelService.selectsByChannelId(channelId);
	}
	
	@ResponseBody
	@PostMapping("article/update")
	public boolean update(ArticleWithBLOBs article) {
		return articleService.updateByPrimaryKeySelective(article)>0;
	}
	@GetMapping("article/article")
	public String article(Integer id,Model model) {
		
		ArticleWithBLOBs a = articleService.selectByPrimaryKey(id);
		model.addAttribute("a", a);
		return "my/article/article";
	}
	@GetMapping("article/articles")
	public String articles(Model model, HttpServletRequest request,Article article, @RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "3") Integer pageSize) {
		HttpSession session = request.getSession();
		User u = (User) session.getAttribute("user");
		article.setUserId(u.getId());//只查询当前用户的文章
		
		PageInfo<Article> info = articleService.selects(article, page, pageSize);
		model.addAttribute("info", info);
		
		return "my/article/articles";
		
	}
	@ResponseBody
	@PostMapping("article/publish")
	public boolean publish( MultipartFile file, ArticleWithBLOBs article,HttpServletRequest request) throws IllegalStateException, IOException {
		String path="d:/pic/";//文件存放路径
		if(!file.isEmpty()) {
			String filename = file.getOriginalFilename();
			String newFileName = UUID.randomUUID()+filename.substring(filename.lastIndexOf("."));
			file.transferTo(new File(path,newFileName));
			article.setPicture(newFileName);
		}
		
		article.setCreated(new Date());
		article.setStatus(0);
		article.setHits(0);
		article.setDeleted(0);
		article.setUpdated(new Date());
		HttpSession session = request.getSession();
		User u = (User) session.getAttribute("user");
		article.setUserId(u.getId());
		article.setHot(0);
		return articleService.insertSelective(article)>0;
		
	}
	
}

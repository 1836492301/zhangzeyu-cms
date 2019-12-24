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
import com.zhangzeyu.cms.domain.Complain;
import com.zhangzeyu.cms.domain.Slide;
import com.zhangzeyu.cms.domain.User;
import com.zhangzeyu.cms.service.ArticleService;
import com.zhangzeyu.cms.service.ChannelService;
import com.zhangzeyu.cms.service.CommentService;
import com.zhangzeyu.cms.service.ComplainService;
import com.zhangzeyu.cms.service.SlideService;
import com.zhangzeyu.cms.util.CMSException;
import com.github.pagehelper.PageInfo;

@Controller
public class IndexController {
	@Resource
	private ChannelService channelService;
	@Resource
	private ArticleService articleService;
	
	@Resource
	private SlideService slideService;
	
	@Resource
	private CommentService commentService;
	
	@Resource
	private ComplainService complainService;
	
	@RequestMapping(value = {"","/","index"})
	public String index(Model model,Article article,@RequestParam(defaultValue = "1") Integer page,@RequestParam(defaultValue = "10") Integer pageSize) {
		article.setStatus(1);
		model.addAttribute("article", article);
				List<Channel> channels = channelService.selects();
				model.addAttribute("channels", channels);
				
				
		if(null==article.getChannelId()) {
		
			List<Slide> slides = slideService.selects();
			model.addAttribute("slides", slides);
			
			Article a2 = new Article();
			a2.setHot(1);
			a2.setStatus(1);
			PageInfo<Article> info = articleService.selects(a2, page, pageSize);	
			model.addAttribute("info", info);
		}
		
		if(null!=article.getChannelId()) {
			List<Category> categorys = channelService.selectsByChannelId(article.getChannelId());
			
			PageInfo<Article> info = articleService.selects(article, page, pageSize);
			model.addAttribute("info", info);
			model.addAttribute("categorys", categorys);
			
			
			if(null!=article.getCategoryId()) {
				PageInfo<Article> info2 = articleService.selects(article, page, pageSize);
				model.addAttribute("info", info2);
			}
		}
		Article last = new Article();
		last.setStatus(1);
		
		PageInfo<Article> lastInfo = articleService.selects(last, 1, 5);
		model.addAttribute("lastInfo", lastInfo);
		
		
	
		return "index/index";
		
	}
	
	
	
	@GetMapping("article")
	public String article(Integer id,Model model) {
		ArticleWithBLOBs article = articleService.selectByPrimaryKey(id);
		model.addAttribute("article", article);
		
		Comment comment = new Comment();
		comment.setArticleId(article.getId());
		PageInfo<Comment> info = commentService.selects(comment, 1, 100);
		model.addAttribute("info", info);
		return "/index/article";
		
	}

	@ResponseBody
	@PostMapping("addComment")
	public boolean addComment(Comment comment,HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if(null==user)
		 return false;
		comment.setUserId(user.getId());
		comment.setCreated(new Date());
		return commentService.insert(comment)>0;
		
	}
	@GetMapping("complain")
	public String complain(Model model ,Article article,HttpSession session) {
		User user = (User) session.getAttribute("user");
		if(null!=user) {
			article.setUser(user);
			model.addAttribute("article", article);
			return "index/complain";
		}
		
		return "redirect:/passport/login";
		
	}
	@ResponseBody
	@PostMapping("complain")
	public boolean complain(Model model,MultipartFile  file, Complain complain) {
		if(null!=file &&!file.isEmpty()) {
			String path="d:/pic/";
			String filename = file.getOriginalFilename();
		   String newFileName =UUID.randomUUID()+filename.substring(filename.lastIndexOf("."));
			File f = new File(path,newFileName);
			try {
				file.transferTo(f);
				complain.setPicurl(newFileName);
				
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
		}
		try {
			 complainService.insert(complain);
				return true;
		} catch (CMSException e) {
			e.printStackTrace();
			
			model.addAttribute("error", e.getMessage());
		}
		catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", "系统错误，联系管理员");
		}
		return false;
	
	    
	}

	
}

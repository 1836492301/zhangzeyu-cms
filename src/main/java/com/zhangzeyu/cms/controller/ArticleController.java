package com.zhangzeyu.cms.controller;

import java.util.List;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.zhangzeyu.cms.dao.ArticleRes;
import com.zhangzeyu.cms.domain.Article;
import com.zhangzeyu.cms.domain.ArticleWithBLOBs;
import com.zhangzeyu.cms.util.HLUtils;

@RequestMapping("article")
@Controller
public class ArticleController {
	@Autowired
	private ArticleRes res;
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;

	@GetMapping("selects")
	public String selects() {
		return "admin/article/articles";
		
	}
	
	@RequestMapping("seach")
	public String seach(String title,Model m,@RequestParam(defaultValue = "1")int page,@RequestParam(defaultValue = "10")int pageSize) {
//		List<Article> list = res.findByTitle(title);
//		PageInfo<Article> info = new PageInfo<>(list);
		PageInfo<Article> info = (PageInfo<Article>) HLUtils.findByHighLight(elasticsearchTemplate, Article.class, page, pageSize, new String[] {"title"}, "id", title);
		m.addAttribute("info", info);
		m.addAttribute("title", title);
		return "index/index";
	}
	

}

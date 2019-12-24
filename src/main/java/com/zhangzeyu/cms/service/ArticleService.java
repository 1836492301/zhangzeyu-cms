package com.zhangzeyu.cms.service;

import com.zhangzeyu.cms.domain.Article;
import com.zhangzeyu.cms.domain.ArticleWithBLOBs;
import com.github.pagehelper.PageInfo;

public interface ArticleService {
	int updateByPrimaryKeySelective(ArticleWithBLOBs record);
	
	ArticleWithBLOBs  selectByPrimaryKey(Integer id);
	PageInfo<Article> selects(Article article,Integer page,Integer pageSize);
	 int insertSelective(ArticleWithBLOBs record);

}

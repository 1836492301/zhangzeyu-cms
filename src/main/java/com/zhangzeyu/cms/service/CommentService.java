package com.zhangzeyu.cms.service;

import com.zhangzeyu.cms.domain.Comment;
import com.github.pagehelper.PageInfo;

public interface CommentService {
	int insert(Comment comment);
	PageInfo<Comment> selects(Comment comment,Integer page,Integer pageSize);
	
}

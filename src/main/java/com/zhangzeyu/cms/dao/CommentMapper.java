package com.zhangzeyu.cms.dao;

import java.util.List;

import com.zhangzeyu.cms.domain.Comment;

public interface CommentMapper {
	
	int insert(Comment comment);
	List<Comment> selects(Comment comment);

}

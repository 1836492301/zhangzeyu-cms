package com.zhangzeyu.cms.service;



import com.zhangzeyu.cms.domain.User;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
@Service
public interface UserService {

	User login(User user);
	
	
	  int insertSelective(User record);
	
	PageInfo<User> selects(String name,Integer page,Integer pageSize);
	 int updateByPrimaryKeySelective(User record);

}

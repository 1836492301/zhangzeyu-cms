package com.zhangzeyu.cms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zhangzeyu.cms.domain.User;

public interface UserMapper {
	List<User> selects(@Param("username")String username);
	User selectByName(@Param("username")String name);
	
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}
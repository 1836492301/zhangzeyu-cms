package com.zhangzeyu.cms.service;

import java.util.List;

import com.zhangzeyu.cms.domain.Category;
import com.zhangzeyu.cms.domain.Channel;

public interface ChannelService {
	List<Channel> selects();
	
	List<Category> selectsByChannelId(Integer channelId);
	
}

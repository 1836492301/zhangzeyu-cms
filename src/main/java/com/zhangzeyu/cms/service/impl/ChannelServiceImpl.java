package com.zhangzeyu.cms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zhangzeyu.cms.dao.CategoryMapper;
import com.zhangzeyu.cms.dao.ChannelMapper;
import com.zhangzeyu.cms.domain.Category;
import com.zhangzeyu.cms.domain.Channel;
import com.zhangzeyu.cms.service.ChannelService;
@Service
public class ChannelServiceImpl implements ChannelService {
	@Resource
	private ChannelMapper channerMapper;
	
	@Resource
	private CategoryMapper categoryMapper;

	@Override
	public List<Channel> selects() {
		return channerMapper.selects();
	}

	@Override
	public List<Category> selectsByChannelId(Integer channelId) {
		// TODO Auto-generated method stub
		return categoryMapper.selectsByChannelId(channelId);
	}

}

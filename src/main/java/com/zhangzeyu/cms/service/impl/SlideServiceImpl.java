package com.zhangzeyu.cms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zhangzeyu.cms.dao.SlideMapper;
import com.zhangzeyu.cms.domain.Slide;
import com.zhangzeyu.cms.service.SlideService;
@Service
public class SlideServiceImpl implements SlideService {
	@Resource
	private SlideMapper slideMapper;

	@Override
	public List<Slide> selects() {
		// TODO Auto-generated method stub
		return slideMapper.selects();
	}

}

package com.zhangzeyu.cms.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zhangzeyu.cms.dao.ArticleMapper;
import com.zhangzeyu.cms.dao.ComplainMapper;
import com.zhangzeyu.cms.domain.Complain;
import com.zhangzeyu.cms.util.CMSException;
import com.zhangzeyu.cms.vo.ComplainVO;
import com.bobo.common.utils.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
@Service
public class ComplainServiceImpl implements ComplainService {
	@Resource
	private ComplainMapper complainMapper;
	@Resource
	private ArticleMapper articleMapper;

	@Override
	public boolean insert(Complain complain) {
		try {
			boolean b = StringUtil.isHttpUrl(complain.getUrl());
			if(!b) {
				throw new CMSException("url 不合法");
			}
			
			complainMapper.insert(complain);
			articleMapper.updateComplainnum(complain.getArticleId());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("举报失败");
			
		}
	
	}

	@Override
	public PageInfo<Complain> selects(ComplainVO complainVO, Integer page, Integer pageSize) {
		PageHelper.startPage(page, pageSize);
		List<Complain> list = complainMapper.selects(complainVO);
		return new PageInfo<Complain>(list);
	}

}

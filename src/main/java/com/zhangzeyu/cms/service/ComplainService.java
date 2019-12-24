package com.zhangzeyu.cms.service;


import com.zhangzeyu.cms.domain.Complain;
import com.zhangzeyu.cms.vo.ComplainVO;
import com.github.pagehelper.PageInfo;

public interface ComplainService {
	boolean insert(Complain complain);
	
		PageInfo<Complain> selects(ComplainVO complainVO,Integer page,Integer pageSize);
}

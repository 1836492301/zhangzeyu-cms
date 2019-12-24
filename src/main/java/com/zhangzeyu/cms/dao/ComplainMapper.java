package com.zhangzeyu.cms.dao;

import java.util.List;

import com.zhangzeyu.cms.domain.Complain;
import com.zhangzeyu.cms.vo.ComplainVO;

public interface ComplainMapper {
	
	int insert(Complain complain);
	
	List<Complain> selects(ComplainVO complainVO);

}

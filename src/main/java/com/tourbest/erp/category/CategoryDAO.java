package com.tourbest.erp.category;

import java.util.List;

import com.tourbest.erp.account.AccountVO;

public interface CategoryDAO {

	public void insertCate(String cateNm);
	
	public List<CategoryVO> selectCate();
	
	public void updateCateYn(CategoryVO vo);
	
}

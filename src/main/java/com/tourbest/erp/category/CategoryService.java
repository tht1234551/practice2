package com.tourbest.erp.category;

import java.util.ArrayList;
import java.util.List;

public interface CategoryService {
	
	public List<CategoryVO> selectCate();
	
	public void updateCateYn(CategoryVO vo);

	public void insertCate(ArrayList<String> cateNmList);

}

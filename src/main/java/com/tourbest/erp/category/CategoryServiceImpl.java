package com.tourbest.erp.category;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Resource
	CategoryDAO categoryDAO;
	
	
//	@Override
//	public void insertCate(cateNmList, cateCdList) {
//		
//		for(int i=0; i<cateList.size(); i++) {
//			System.out.println(cateList.get(i));
//			
//			
//			
//			//categoryDAO.insertCate(cateNm);		
//		}
//	
//	}

	@Override
	public void insertCate(ArrayList<String> cateNmList) {
		
		for(int i=0; i<cateNmList.size(); i++) {			
			categoryDAO.insertCate(cateNmList.get(i));
		}
	}	

/*
	@Override
	public List<CategoryVO> selectCate() {
		List<CategoryVO> list = new ArrayList<>();
		list = categoryDAO.selectCate();		
		return list;
	}
*/
	@Override
	public List<CategoryVO> selectCate() {
		return categoryDAO.selectCate();
	}
	
	@Override
	public void updateCateYn(CategoryVO vo) {
		categoryDAO.updateCateYn(vo);
	}
}

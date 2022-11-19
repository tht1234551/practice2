package com.tourbest.erp.category;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tourbest.erp.account.AccountVO;

@Repository
public class CategoryDaoImpl implements CategoryDAO {
	
	@Autowired
	private SqlSession sqlSession;
	private static final String NAMESPACE = "com.tourbest.erp.category.CategoryDAO.";

	@Override
	public void insertCate(String cateNm) {		
		sqlSession.insert(NAMESPACE + "insertCate", cateNm);		
	}

	@Override
	public List<CategoryVO> selectCate() {
		List<CategoryVO> list = new ArrayList<>();
		list = sqlSession.selectList(NAMESPACE+"selectCate");
		return list;
	}

	@Override
	public void updateCateYn(CategoryVO vo) {		
		sqlSession.update(NAMESPACE+"updateCateYn", vo);
	}

}

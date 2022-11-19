package com.tourbest.erp.budget;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BudgetDaoImpl implements BudgetDAO{
	
	@Autowired
	private SqlSession sqlSession;
	private static final String NAMESPACE = "com.tourbest.erp.budget.BudgetDAO.";

	@Override
	public void budgetInsert(BudgetVO vo) {
		
		System.out.println("DAO : "+vo);
		
		sqlSession.insert(NAMESPACE+"budgetInsert", vo);
				
	}

}

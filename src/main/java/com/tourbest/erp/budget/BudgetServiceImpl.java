package com.tourbest.erp.budget;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service
public class BudgetServiceImpl implements BudgetService{
	
	@Resource
	BudgetDAO budgetDao;

	@Override
	public void budgetInsert(BudgetVO vo) {
		
		for (BudgetVO budVo : vo.getBudgetVoList()) {
		 	budVo.setBudMonth(vo.getBudMonth());
		 	budVo.setBudYear(vo.getBudYear());
			budgetDao.budgetInsert(budVo);			
		}				
	}
}

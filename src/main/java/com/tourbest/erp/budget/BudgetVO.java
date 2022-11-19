package com.tourbest.erp.budget;

import java.util.List;

import lombok.Data;

@Data
public class BudgetVO {

	String budCd;
	int cateCd;
	int budPrice;
	String budMonth;
	String budRemarks;
	String budYear;
	
	List<BudgetVO> budgetVoList;
}

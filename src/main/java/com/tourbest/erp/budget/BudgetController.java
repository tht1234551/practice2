package com.tourbest.erp.budget;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.tourbest.erp.category.CategoryService;
import com.tourbest.erp.category.CategoryVO;

@Controller
public class BudgetController {
	
	@Resource
	CategoryService categoryService;
	
	@Resource
	BudgetService budgetService;
	
	@RequestMapping("budgetList")
	public ModelAndView budgetList() throws Exception{
		ModelAndView mav = new ModelAndView("error.jsp");
		
		mav.addObject("cateList", categoryService.selectCate());
		mav.setViewName("account/budgetInsert");
		return mav;
	}
	
	@RequestMapping("budgetInsert")
	public String budgetInsert(BudgetVO vo) throws Exception{
		
		budgetService.budgetInsert(vo);
				
		return "account/budgetInsert";
	}

}

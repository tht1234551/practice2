package com.tourbest.erp.category;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CategoryController {
	
	@Resource
	CategoryService categoryService;
	
	@RequestMapping("categoryList")
	public ModelAndView categoryList() throws Exception{
		
		ModelAndView mav = new ModelAndView("error.jsp");
		
		mav.addObject("list", categoryService.selectCate());
		mav.setViewName("account/category");
		return mav;
	}
	
	@RequestMapping("categoryAdd")
	public ModelAndView categoryAdd(CategoryVO categoryVO, @RequestParam(value="cateNm", required=false) ArrayList<String> cateNmList) 
		                            throws Exception {
		ModelAndView mav = new ModelAndView("error.jsp");
		categoryService.insertCate(cateNmList);
		mav.setViewName("redirect:categoryList"); // controller로 전달
//		mav.setViewName("forward:categoryList"); // controller에 form파라미터도 같이 전달  ex) categoryVO,categoryVO를 categoryList로 같이 감
		return mav;
	}
	
	@RequestMapping("updateCateYn")
	@ResponseBody
	public Map<String, Object> updateCateYn(CategoryVO vo)throws Exception {
		Map<String, Object> map = new HashMap<>();
		categoryService.updateCateYn(vo);
		
		return map;
	}
	
}

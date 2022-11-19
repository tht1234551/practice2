package com.tourbest.erp.mediation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tourbest.erp.util.FileUtil;

@Controller
@RequestMapping("/medi")
public class MediController {
	
	
	@Autowired
	MediService mediService;
	
	@RequestMapping("/main")
	public String main(Model model) {
		
		return "medi.main";
	}

	@RequestMapping("/join")
	public String join(Model model) {
		
		return "medi.join";
	}
	
	@RequestMapping("/goodsDeal")
	public String goodsDeal(Model model,String goodsIdx) {
		
		GoodsVo goodsVo = mediService.selectGoodsDetail(goodsIdx);
		
		model.addAttribute("goodsVo",goodsVo);
		
		return "medi.dealRegi";
	}
	
	@RequestMapping("/goods")
	public String goods(Model model,HttpSession httpSession) {
		
		List<GoodsVo> goodsList = mediService.selecGoods(httpSession);
		CompVo compVo = mediService.selectComp(httpSession);
		
		model.addAttribute("compVo",compVo);
		model.addAttribute("goodsList",goodsList);
		
		return "medi.goodsMain";
	}

	@RequestMapping("/goodsRegi")
	public String goodsRegi(Model model,HttpSession httpSession) {
		
		CompVo compVo = mediService.selectComp(httpSession);
		
		model.addAttribute("compVo",compVo);
		
		return "medi.goodsRegi";
	}
	
	@RequestMapping("/goodsDetail")
	public String goodsDetail(Model model,HttpSession httpSession,String goodsIdx) {
		
		CompVo compVo = mediService.selectComp(httpSession);
		GoodsVo goodsVo = mediService.selectGoodsDetail(compVo,goodsIdx);
		
		model.addAttribute("compVo",compVo);
		model.addAttribute("goodsVo",goodsVo);
		
		return "medi.goodsRegi";
	}

	@RequestMapping("/comp")
	public String comp(Model model, HttpSession httpSession) {
		
		CompVo compVo = mediService.selectComp(httpSession);
		
		model.addAttribute("compVo",compVo);
		
		return "medi.compMain";
	}
	
	@RequestMapping("/logout")
	public String logout(Model model,HttpSession httpSession) {
		
		httpSession.invalidate();
		
		return "redirect:/medi/main";
	}

	@RequestMapping(value = "/joinAction" , method = RequestMethod.POST )
	@ResponseBody
	public Map<String, Object> joinAction(Model model,UserVo userVo) {
			
		Map<String, Object> map = new HashMap<>();
		int resultCnt = mediService.insert(userVo);
		
		map.put("success", resultCnt > 0?"Y":"N");
		
		return map;
	}

	@RequestMapping(value = "/loginAction" , method = RequestMethod.POST )
	@ResponseBody
	public Map<String, Object> loginAction(Model model,UserVo userVo,HttpSession HttpSession) {
		
		Map<String, Object> map = new HashMap<>();
		
		UserVo retsultVo = mediService.selectUser(userVo);
		
		if(retsultVo != null) {
			HttpSession.setAttribute("userVo", retsultVo);
		}
		
		map.put("success", retsultVo != null? retsultVo:"N");
		
		return map;
	}
	
	@RequestMapping(value = "/goodsRegiAction" , method = RequestMethod.POST )
	@ResponseBody
	public Map<String, Object> goodsRegiAction(Model model,GoodsVo goodsVo,HttpServletRequest request)  throws Exception{
			
		Map<String, Object> map = new HashMap<>();
		FileUtil fileUtil = new FileUtil();
		
		fileUtil.imageUploadFile(request, "goodsImg1", "test", "/uploadImg", 1000*1024, 100);
		
		int resultCnt = mediService.insertGoods(goodsVo);
		
		map.put("success", resultCnt > 0?"Y":"N");
		
		return map;
	}
	
	@RequestMapping(value = "/goodsModiAction" , method = RequestMethod.POST )
	@ResponseBody
	public Map<String, Object> goodsModiAction(Model model,GoodsVo goodsVo) {
		
		Map<String, Object> map = new HashMap<>();
		int resultCnt = mediService.updateGoods(goodsVo);
		
		map.put("success", resultCnt > 0?"Y":"N");
		
		return map;
	}
	
	@RequestMapping(value = "/compRegiAction" , method = RequestMethod.POST )
	@ResponseBody
	public Map<String, Object> compRegiAction(Model model,CompVo compVo,HttpSession httpSession) {
		
		Map<String, Object> map = new HashMap<>();
		int resultCnt = mediService.insertComp(httpSession,compVo);
		
		map.put("success", resultCnt > 0?"Y":"N");
		
		return map;
	}
	
	@RequestMapping(value = "/compModiAction" , method = RequestMethod.POST )
	@ResponseBody
	public Map<String, Object> compModiAction(Model model,CompVo compVo,HttpSession httpSession) {
		
		Map<String, Object> map = new HashMap<>();
		int resultCnt = mediService.updateComp(httpSession,compVo);
		
		map.put("success", resultCnt > 0?"Y":"N");
		
		return map;
	}
	
	@RequestMapping(value = "/dealRegiAction" , method = RequestMethod.POST )
	@ResponseBody
	public Map<String, Object> dealRegiAction(Model model,DealGoodVo dealGoodVo,DealVo dealVo,HttpSession HttpSession) {
			
		Map<String, Object> map = new HashMap<>();
		
		mediService.insertDeal(HttpSession,dealVo);
		
		int resultCnt = mediService.insertDealGood(dealVo,dealGoodVo);
		
		map.put("success", resultCnt > 0?"Y":"N");
		
		return map;
	}
	
	@RequestMapping("/dealList")
	public String dealList(Model model, HttpSession httpSession) {
		
		List<DealVo> dealList = mediService.selectDeal(httpSession);
		
		model.addAttribute("dealList",dealList);
		
		return "medi.dealList";
	}

}

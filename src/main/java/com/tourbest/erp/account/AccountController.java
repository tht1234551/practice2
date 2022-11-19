package com.tourbest.erp.account;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AccountController {

	@RequestMapping("accountMain")
	public String accountMain() throws Exception{
		return "account/accountMain";
	}
	
//	@RequestMapping("list")
//	public String list() throws Exception{
//		return "board.list";
//	}
//	@RequestMapping("write")
//	public String write() throws Exception{
//		return "board.write";
//	}
	
	@RequestMapping("accountInsert")
	public String accountInsert() throws Exception{
		return "account/accountInsert";
	}
	
}

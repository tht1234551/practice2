package com.tourbest.erp.display;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class displayController {

	@RequestMapping("displayInsert")
	public String displayInsert() throws Exception{
		return "display/displayInsert";
	}
}

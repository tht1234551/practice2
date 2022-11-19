package com.tourbest.erp.rest;

import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("http://localhost:8081")
@RestController
public class TestRest {

	@RequestMapping("/testRest")
	public String main(String msg) throws Exception{
		
		System.out.println(msg);
		
		JSONObject jsonObject = new JSONObject();
		
		jsonObject.put("test","value");
		
		return jsonObject.toString();
	}
}

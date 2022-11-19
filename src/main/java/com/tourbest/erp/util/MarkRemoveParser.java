package com.tourbest.erp.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MarkRemoveParser {
	
	private static final List<Map<String, String>> rules = new ArrayList<Map<String, String>>();
	
	static {
		HashMap<String, String> COLOR_RED = new HashMap<String, String>();
		COLOR_RED.put("rule", "\\#*(?:)(\\#r(.*?)r\\#)");
		COLOR_RED.put("markdown", "$2");
		
		HashMap<String, String> COLOR_BLUE = new HashMap<String, String>();
		COLOR_BLUE.put("rule", "\\#*(?:)(\\#b(.*?)b\\#)");
		COLOR_BLUE.put("markdown", "$2");
		
		HashMap<String, String> COLOR_RED_BOLD = new HashMap<String, String>();
		COLOR_RED_BOLD.put("rule", "\\#*(?:)(\\#rs(.*?)rs\\#)");
		COLOR_RED_BOLD.put("markdown", "$2");
		
		HashMap<String, String> COLOR_BLUE_BOLD = new HashMap<String, String>();
		COLOR_BLUE_BOLD.put("rule", "\\#*(?:)(\\#bs(.*?)bs\\#)");
		COLOR_BLUE_BOLD.put("markdown", "$2");
		
		HashMap<String, String> BOLD = new HashMap<String, String>();
		BOLD.put("rule", "\\#*(?:)(\\#s(.*?)s\\#)");
		BOLD.put("markdown", "$2");
		
		rules.add(COLOR_RED);
		rules.add(COLOR_BLUE);
		rules.add(COLOR_RED_BOLD);
		rules.add(COLOR_BLUE_BOLD);
		rules.add(BOLD);
	}
		
	public static String render(String src) {
		String output = "";
		
		if (!StringUtil.isEmpty(src)) {
			output = src;
			for(Map<String, String> rule : rules) {
				Pattern p = Pattern.compile((String) rule.get("rule"), Pattern.DOTALL);
				Matcher m = p.matcher(output);
				
				if(m.find()) {
					output = m.replaceAll((String) rule.get("markdown"));
				}
			}
		}
		
		return output;
	}
	
}

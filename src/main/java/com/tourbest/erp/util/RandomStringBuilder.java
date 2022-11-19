package com.tourbest.erp.util;

import java.util.Random;

public class RandomStringBuilder {
    
	private static final char[] chars;
	
	static {
		StringBuilder buffer = new StringBuilder();
		for(char ch = '0'; ch <= '9' ; ch++)
			buffer.append(ch);
		for(char ch = 'a'; ch <= 'z' ; ch++)
			buffer.append(ch);
		for(char ch = 'A'; ch <= 'Z' ; ch++)
			buffer.append(ch);
		
		chars = buffer.toString().toCharArray();
	}
	
	public static String randomStringBuild(int length){
		if(length < 1)
			throw new IllegalArgumentException("length < 1: "+ length);
		
		StringBuilder randomString = new StringBuilder();
		Random random = new Random();
		
		for(int i=0; i<length; i++)
			randomString.append(chars[random.nextInt(chars.length)]);
		
		return randomString.toString();
	}
}
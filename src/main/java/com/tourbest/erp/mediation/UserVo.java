package com.tourbest.erp.mediation;

import lombok.Data;

@Data
public class UserVo extends CompVo{
	
	private String userIdx;
	private String userId;
	private String userPw;
	private String userName;
	private String userPhone1;
	private String userPhone2;
	private String userPost;
	private String userAddr;
	private String userAuth;
}

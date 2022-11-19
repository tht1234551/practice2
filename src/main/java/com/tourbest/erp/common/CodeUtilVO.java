package com.tourbest.erp.common;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

import lombok.Data;

/**
 *  클래스
 * @author 
 * @since 
 * @see
 *
 */
@Data
public class CodeUtilVO   implements Serializable {
    /**
	 *  serialVersion UID
	 */
	private static final long serialVersionUID = -2020648489890016404L;

	
	private String commCd;     /* 공통코드 */
	private String commTp;     /* 공통코드그룹ID */
	private String commCdNm;     /* 공통코드명 */
	private String commCdHi;     /* 상위공통코드 */
	private int commCdLvl;     /* 코드레벨 */
	private int seqNo;     /* 정렬순서 */
	private String remark;     /* 비고 */

	private String attribute1;     /* 코드속성1 */
	private String attribute2;     /* 코드속성2 */
	private String attribute3;     /* 코드속성3 */
	private String attribute4;     /* 코드속성4 */
	private String attribute5;     /* 코드속성5 */
	private String attribute6;     /* 코드속성6 */
	private String attribute7;     /* 코드속성7 */
	private String attribute8;     /* 코드속성8 */
	private String attribute9;     /* 코드속성9 */
	private String attribute10;     /* 코드속성10 */
	
	/* 공통 사용 */                                                                                                                                                                                                                             
	private String useYn;	// 사용여부
	
	/* 회원사 검색 옵션 */
	private String searchOpt;   // 검색 옵션    authNm : 권한명
	private String keyword;  	// 검색어
	
    
 
}

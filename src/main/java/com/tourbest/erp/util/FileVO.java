package com.tourbest.erp.util;


import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

import lombok.Data;

/**
 *
 *
 */
@Data
public class FileVO implements Serializable {

    /**
	 *  serialVersion UID
	 */
	private static final long serialVersionUID = -287950405903719128L;
	/** 파일명 */
    private String fileName = "";
    /** ContextType */
    private String contentType = "";
    /** 하위 디렉토리 지정 */
    private String serverSubPath = "";
    /** 물리적 파일명 */
    private String physicalName = "";
    /** 파일 사이즈 */
    private long size = 0L;
	private String note;     /* 비고 */
	private String fileOriginalNm;     /* 오리지날 파일명  */
	private String filePath;     /* 파일패스 */
	private String fileExtsn; /* 파일 확장자 DB 별도 */
	private int fileGrpId;     /* 파일_GRP_ID int형 */
	private int seq;     /* 순서 */
	private String fileNm;     /* 파일_명 */
	private String fileUrl;     /* 파일_URL */
	private String fileSize;     /* 파일_크기 int형 */
	private String delYn;     /* 삭제_여부 */
	private long regId;     /* REG_ID */
	private String regIp;     /* REG_IP */
	private String regDt;     /* REG_DT */
	private long updId;     /* UPD_ID */
	private String updIp;     /* UPD_IP */
	private String updDt;     /* UPD_DT */

	private String title;
	private String contents;
	
	private int imgWidth;   // 이미지의 경우 가로 사이즈
	private int imgHeight;  // 이미지의 경우 세로 사이즈
	

    /**
     * toString 메소드를 대치한다.
     */
    public String toString() {
	return ToStringBuilder.reflectionToString(this);
    }

}

package com.tourbest.erp.mediation;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class GoodsVo {

	private String goodsIdx;
	private String compIdx;
	private String goodsName;
	private String goodsPrice;
	private String goodsComment;
	private String goodsInven;
	private String goodsDis;
	private String goodsImg;
	private MultipartFile goodsImg1;
	private int rowNum;
	
}

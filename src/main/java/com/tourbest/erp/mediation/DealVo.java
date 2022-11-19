package com.tourbest.erp.mediation;

import lombok.Data;

@Data
public class DealVo {
	
	private String dealIdx;
	private String dealStatus;
	private String compIdx;
	private String userIdx;
	private DealGoodVo dealGoodVo;
	private GoodsVo goodsVo;
	private int rowNum;
}

package com.tourbest.erp.mediation;

import java.util.List;

import javax.servlet.http.HttpSession;

public interface MediService {

	public int insert(UserVo userVo);

	public UserVo selectUser(UserVo userVo);

	public CompVo selectComp(HttpSession httpSession);

	public List<GoodsVo> selecGoods(HttpSession httpSession);

	public int insertGoods(GoodsVo goodsVo);

	public GoodsVo selectGoodsDetail(CompVo compVo, String goodsIdx);

	public int updateGoods(GoodsVo goodsVo);

	public int insertComp(HttpSession httpSession, CompVo compVo);

	public int updateComp(HttpSession httpSession, CompVo compVo);

	public GoodsVo selectGoodsDetail(String goodsIdx);

	public void insertDeal(HttpSession httpSession, DealVo dealVo);

	public int insertDealGood(DealVo dealVo, DealGoodVo dealGoodVo);

	public List<DealVo> selectDeal(HttpSession httpSession);

}

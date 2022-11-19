package com.tourbest.erp.mediation;

import java.util.List;

public interface MediDAO {

	public int insert(UserVo userVo);

	public UserVo selectUser(UserVo userVo);

	public CompVo selectComp(UserVo userVo);

	public List<GoodsVo> selecGoods(CompVo compVo);

	public int insertGoods(GoodsVo goodsVo);

	public GoodsVo selectGoodsDetail(GoodsVo goodsVo);

	public int updateGoods(GoodsVo goodsVo);

	public int insertComp(CompVo compVo);

	public int updateComp(CompVo compVo);

	public void insertDeal(DealVo dealVo);

	public int insertDealGood(DealGoodVo dealGoodVo);

	public List<DealVo> selectDeal(UserVo userVo);

}

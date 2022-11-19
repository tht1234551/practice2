package com.tourbest.erp.mediation;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MediDaoImpl implements MediDAO{
	
	@Autowired
	private SqlSession sqlSession;
	private static final String NAMESPACE = "com.tourbest.erp.mediaction.MediDAO.";

	
	@Override
	public int insert(UserVo userVo) {
		return sqlSession.insert(NAMESPACE+"insert",userVo);
	}
	
	@Override
	public UserVo selectUser(UserVo userVo) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(NAMESPACE+"selectUser",userVo);
	}
	
	@Override
	public CompVo selectComp(UserVo userVo) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(NAMESPACE+"selectComp",userVo);
	}
	
	@Override
	public int insertComp(CompVo compVo) {
		// TODO Auto-generated method stub
		return sqlSession.insert(NAMESPACE+"insertComp",compVo);
	}
	
	@Override
	public int updateComp(CompVo compVo) {
		// TODO Auto-generated method stub
		return sqlSession.update(NAMESPACE+"updateComp",compVo);
	}
	
	@Override
	public List<GoodsVo> selecGoods(CompVo compVo) {
		// TODO Auto-generated method stub
		return sqlSession.selectList(NAMESPACE+"selectGoods",compVo);
	}
	
	@Override
	public int insertGoods(GoodsVo goodsVo) {
		// TODO Auto-generated method stub
		return sqlSession.insert(NAMESPACE+"insertGoods",goodsVo);
	}
	
	@Override
	public GoodsVo selectGoodsDetail(GoodsVo goodsVo) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(NAMESPACE+"selectGoodsDetail",goodsVo);
	}
	
	@Override
	public int updateGoods(GoodsVo goodsVo) {
		// TODO Auto-generated method stub
		return sqlSession.update(NAMESPACE+"updateGoods",goodsVo);
	}
	
	@Override
	public void insertDeal(DealVo dealVo) {
		// TODO Auto-generated method stub
		sqlSession.insert(NAMESPACE+"insertDeal",dealVo);
	}
	
	@Override
	public int insertDealGood(DealGoodVo dealGoodVo) {
		// TODO Auto-generated method stub
		return sqlSession.insert(NAMESPACE+"insertDealGood",dealGoodVo);
	}
	
	@Override
	public List<DealVo> selectDeal(UserVo userVo) {
		// TODO Auto-generated method stub
		return sqlSession.selectList(NAMESPACE+"selectDeal",userVo);
	}
}

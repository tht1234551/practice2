package com.tourbest.erp.mediation;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MediServiceImpl implements MediService{
	
	@Autowired
	MediDAO mediDao;
	
	@Override
	public int insert(UserVo userVo) {
		// TODO Auto-generated method stub
		return mediDao.insert(userVo);
	}	
	
	@Override
	public UserVo selectUser(UserVo userVo) {
		// TODO Auto-generated method stub
		return mediDao.selectUser(userVo);
	}
	
	@Override
	public CompVo selectComp(HttpSession httpSession) {
		// TODO Auto-generated method stub
		
		UserVo userVo = (UserVo) httpSession.getAttribute("userVo");
		
		return mediDao.selectComp(userVo);
	}
	
	@Override
	public int insertComp(HttpSession httpSession,CompVo compVo) {
		
		UserVo userVo = (UserVo) httpSession.getAttribute("userVo");
		
		compVo.setUserIdx(userVo.getUserIdx());
		
		return mediDao.insertComp(compVo);
	}
	
	@Override
	public int updateComp(HttpSession httpSession,CompVo compVo) {
		
		UserVo userVo = (UserVo) httpSession.getAttribute("userVo");
		
		compVo.setUserIdx(userVo.getUserIdx());
		
		return mediDao.updateComp(compVo);
	}
	
	@Override
	public List<GoodsVo> selecGoods(HttpSession httpSession) {
		// TODO Auto-generated method stub
		
		UserVo userVo = (UserVo) httpSession.getAttribute("userVo");
		
		CompVo compVo =  mediDao.selectComp(userVo);
		
		return mediDao.selecGoods(compVo);
	}
	
	@Override
	public int insertGoods(GoodsVo goodsVo) {
		// TODO Auto-generated method stub
		return mediDao.insertGoods(goodsVo);
	}
	
	@Override
	public GoodsVo selectGoodsDetail(CompVo compVo, String goodsIdx) {
		// TODO Auto-generated method stub
		
		GoodsVo goodsVo = new GoodsVo();
		
		goodsVo.setCompIdx(compVo.getCompIdx());
		goodsVo.setGoodsIdx(goodsIdx);
		
		return mediDao.selectGoodsDetail(goodsVo);
	}
	
	@Override
	public GoodsVo selectGoodsDetail(String goodsIdx) {
		// TODO Auto-generated method stub
		GoodsVo goodsVo = new GoodsVo();
		
		goodsVo.setGoodsIdx(goodsIdx);
		
		return mediDao.selectGoodsDetail(goodsVo);
	}
	
	@Override
	public int updateGoods(GoodsVo goodsVo) {
		// TODO Auto-generated method stub
		return mediDao.updateGoods(goodsVo);
	}
	
	@Override
	public void insertDeal(HttpSession httpSession,DealVo dealVo) {
		
		UserVo userVo = (UserVo) httpSession.getAttribute("userVo");
		dealVo.setUserIdx(userVo.getUserIdx());
		mediDao.insertDeal(dealVo);
	}
	
	@Override
	public int insertDealGood(DealVo dealVo, DealGoodVo dealGoodVo) {
		// TODO Auto-generated method stub
		dealGoodVo.setDealIdx(dealVo.getDealIdx());
		
		return mediDao.insertDealGood(dealGoodVo);
	}
	
	@Override
	public List<DealVo> selectDeal(HttpSession httpSession) {
		// TODO Auto-generated method stub
		UserVo userVo = (UserVo) httpSession.getAttribute("userVo");
		
		CompVo compVo =  mediDao.selectComp(userVo);
		
		if(compVo != null) {
			userVo.setCompIdx(compVo.getCompIdx());
		}
		
		return mediDao.selectDeal(userVo);
	}
	
}

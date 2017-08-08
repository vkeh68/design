package yjc.wdb.scts.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import yjc.wdb.scts.bean.GoodsVO;
import yjc.wdb.scts.bean.PageVO;
import yjc.wdb.scts.dao.GoodsDAO;
import yjc.wdb.scts.service.GoodsService;

@Service
public class GoodsServiceImpl implements GoodsService {

	@Inject
	private GoodsDAO dao;
	
	@Override
	public void insertGoods(GoodsVO vo) throws Exception {
		// TODO Auto-generated method stub
		dao.insertGoods(vo);
	}

	@Override
	public List<GoodsVO> selectGoodsList() throws Exception {
		// TODO Auto-generated method stub
		return dao.selectGoodsList();
	}

	@Override
	public List<GoodsVO> searchGoodsList(String goodsName) throws Exception {
		// TODO Auto-generated method stub
		return dao.searchGoodsList(goodsName);
	}

	@Override
	public GoodsVO selectGoodsOne(int goods_code) throws Exception {
		// TODO Auto-generated method stub
		return dao.selectGoodsOne(goods_code);
	}

	@Override
	public void deleteStock(int goods_code) throws Exception {
		// TODO Auto-generated method stub
		dao.deleteStock(goods_code);
	}

	@Override
	public List<GoodsVO> selectPageList(PageVO cri) throws Exception {
		// TODO Auto-generated method stub
		return dao.selectPageList(cri);
	}

	@Override
	public int countSearch(PageVO cri) throws Exception {
		return dao.countSearch(cri);
	}

	@Override
	public List<GoodsVO> selectAdNotGoodsList(int bhf_code) throws Exception {
		
		return dao.selectAdNotGoodsList(bhf_code);
	}

}

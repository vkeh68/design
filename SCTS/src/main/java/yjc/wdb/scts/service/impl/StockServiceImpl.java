package yjc.wdb.scts.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import yjc.wdb.scts.bean.GoodsVO;
import yjc.wdb.scts.bean.PageVO;
import yjc.wdb.scts.bean.StockVO;
import yjc.wdb.scts.dao.StockDAO;
import yjc.wdb.scts.service.StockService;

@Service
public class StockServiceImpl implements StockService {
	
	@Inject
	private StockDAO dao;
	
	@Override
	public List<StockVO> selectStockList(PageVO cri) throws Exception {
		return dao.selectStockList(cri);
	}

	@Override
	public List<HashMap> deleteStockList(String user_id, int goods_code) throws Exception {
		return dao.deleteStockList(user_id, goods_code);
	}
	
	@Override
	public List<StockVO> searchStockList(PageVO cri) throws Exception {
		return dao.searchStockList(cri);
	}

	@Override
	public int countSearch(PageVO cri) throws Exception {
		return dao.countSearch(cri);
	}

	@Override
	public List<GoodsVO> selectgoodsList() throws Exception {
		return dao.selectgoodsList();
	}

	@Override
	public void insertStockList(Map map)throws Exception {
		dao.insertStockList(map);
	}

	@Override
	public List<String> selectEnterprise() throws Exception {
		return dao.selectEnterprise();
	}

}

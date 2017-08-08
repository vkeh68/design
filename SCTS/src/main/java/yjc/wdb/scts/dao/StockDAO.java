package yjc.wdb.scts.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import yjc.wdb.scts.bean.GoodsVO;
import yjc.wdb.scts.bean.PageVO;
import yjc.wdb.scts.bean.StockVO;

public interface StockDAO {
	public List<StockVO> selectStockList(PageVO cri) throws Exception;
	public List<HashMap> deleteStockList(String user_id, int goods_code) throws Exception;
	public List<StockVO> searchStockList(PageVO cri) throws Exception;
	public List<GoodsVO> selectgoodsList() throws Exception;
	public List<String> selectEnterprise()throws Exception;
	public void insertStockList(Map map)throws Exception;
	public int countSearch(PageVO cri) throws Exception;
}

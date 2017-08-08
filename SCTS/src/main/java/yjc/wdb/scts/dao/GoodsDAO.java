package yjc.wdb.scts.dao;

import java.util.List;

import yjc.wdb.scts.bean.GoodsVO;
import yjc.wdb.scts.bean.PageVO;

public interface GoodsDAO {
	public void insertGoods(GoodsVO vo) throws Exception;
	public List<GoodsVO> selectGoodsList() throws Exception;
	public List<GoodsVO> selectPageList(PageVO cri) throws Exception;
	public int countSearch(PageVO cri) throws Exception;
	public List<GoodsVO> searchGoodsList(String goodsName) throws Exception;
	public GoodsVO selectGoodsOne(int goods_code) throws Exception;
	public void deleteStock(int goods_code) throws Exception;
	public List<GoodsVO> selectAdNotGoodsList(int bhf_code) throws Exception;
}
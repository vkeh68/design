package yjc.wdb.scts.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import yjc.wdb.scts.bean.Purchase_goodsVO;

public interface Purchase_goodsDAO {
	public void insertPurchase_goods(HashMap<String, String> vo) throws Exception;
	public void insertPurchase_goodsList(Map<String, Object> map) throws Exception;
}

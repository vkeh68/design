package yjc.wdb.scts.dao.impl;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import yjc.wdb.scts.bean.Purchase_goodsVO;
import yjc.wdb.scts.dao.Purchase_goodsDAO;

@Repository
public class Purchase_goodsDAOImpl implements Purchase_goodsDAO {
	
	private static final String NAMESPACE = "yjc.wdb.mapper.Purchase_goodsMapper";

	@Inject
	private SqlSession sqlSession;

	@Override
	public void insertPurchase_goods(HashMap<String, String> vo) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.insert(NAMESPACE + ".insertPurchase_goods", vo);
	}

	@Override
	public void insertPurchase_goodsList(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.insert(NAMESPACE + ".insertPurchase_goodsList", map);
	}

}

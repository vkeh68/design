package yjc.wdb.scts.dao.impl;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import yjc.wdb.scts.dao.Coupon_holdDAO;

@Repository
public class Coupon_holdDAOImpl implements Coupon_holdDAO {

	
	private static final String NAMESPACE = "yjc.wdb.mapper.Coupon_holdMapper";
	
	@Inject
	private SqlSession sql;
	
	@Override
	public List<HashMap<String, String>> selectCouponHoldGoods(HashMap<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return sql.selectList(NAMESPACE + ".selectCouponHoldGoods", map);
	}

}

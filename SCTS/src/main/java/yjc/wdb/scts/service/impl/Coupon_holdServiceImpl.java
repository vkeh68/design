package yjc.wdb.scts.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import yjc.wdb.scts.dao.Coupon_holdDAO;
import yjc.wdb.scts.service.Coupon_holdService;

@Service
public class Coupon_holdServiceImpl implements Coupon_holdService {

	@Inject
	private Coupon_holdDAO dao;
	
	@Override
	public List<HashMap<String, String>> selectCouponHoldGoods(HashMap<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return dao.selectCouponHoldGoods(map);
	}

}

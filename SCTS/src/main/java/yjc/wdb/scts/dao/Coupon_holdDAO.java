package yjc.wdb.scts.dao;

import java.util.HashMap;
import java.util.List;

import yjc.wdb.scts.bean.Coupon_holdVO;

public interface Coupon_holdDAO {
	public List<HashMap<String, String>> selectCouponHoldGoods(HashMap<String, Object> map) throws Exception;
	

}

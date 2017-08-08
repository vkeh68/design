package yjc.wdb.scts.service;

import java.util.HashMap;
import java.util.List;

public interface Coupon_holdService {
	public List<HashMap<String, String>> selectCouponHoldGoods(HashMap<String, Object> map) throws Exception;
}

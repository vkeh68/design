package yjc.wdb.scts.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Repository;

import yjc.wdb.scts.bean.BillVO;
import yjc.wdb.scts.bean.CouponVO;
import yjc.wdb.scts.bean.Coupon_holdVO;
import yjc.wdb.scts.bean.GoodsVO;
import yjc.wdb.scts.bean.UserVO;
import yjc.wdb.scts.dao.AndroidDAO;


@Repository
public class AndroidDAOImpl implements AndroidDAO{
	
	private static final String NAMESPACE = "yjc.wdb.mapper.AndroidMapper";
	
	@Inject
	private SqlSession sql;
	
	@Override
	public List<BillVO> billList(String user_id, int day) throws Exception {
		
		Map map = new HashMap();
		map.put("user_id", user_id);
		map.put("day", day);
		
		return sql.selectList(NAMESPACE + ".billList", map);
	}

	@Override
	public List<HashMap> billOne(int bill_code) throws Exception {
		
		return sql.selectList(NAMESPACE+".billOne", bill_code);
	}

	@Override
	public List<HashMap> settleInfo(String user_id, int bill_code) throws Exception {
		
		Map map = new HashMap();
		map.put("user_id", user_id);
		map.put("bill_code", bill_code);
		
		return sql.selectList(NAMESPACE+".settleInfo", map);
	}

	@Override
	public List<HashMap> recommandProduct(String user_id) throws Exception {
		
		return sql.selectList(NAMESPACE+".recommandProduct", user_id);
	}
	
	
	@Override
	public List<CouponVO> couponBasket(String user_id) throws Exception {
		
		return sql.selectList(NAMESPACE+".couponList", user_id);
	}

	@Override
	public void delCouponBasket(Coupon_holdVO coupon_holdVO) throws Exception {
		
		sql.delete(NAMESPACE+".delCouponBasket", coupon_holdVO);
		
	}
	
	@Override
	public CouponVO selectSendAndroidCoupon(Map map) throws Exception {
	
		return sql.selectOne(NAMESPACE+".selectSendAndroidCoupon", map);
	}

	@Override
	public List<GoodsVO> productSearch(String productName, int bhf_code) throws Exception {
		Map map = new HashMap();
		map.put("productName", productName);
		map.put("bhf_code", bhf_code);
		
		return sql.selectList(NAMESPACE+".productSearch", map);
	}

	@Override
	public int scanCoupon_hold(Map map) throws Exception {
		// TODO Auto-generated method stub
		return sql.selectOne(NAMESPACE + ".scanCoupon_hold", map);
	}

	@Override
	public List<HashMap<String, Integer>> TileCoupon_code(Map map) throws Exception {
		// TODO Auto-generated method stub
		return sql.selectList(NAMESPACE + ".TileCoupon_code", map);
	}
	
	public List<HashMap> eventList() throws Exception {
		// TODO Auto-generated method stub
		return sql.selectList(NAMESPACE+".eventList");
	}

	@Override
	public List<HashMap> eventOne(int bbsctt) throws Exception {
		// TODO Auto-generated method stub
		return sql.selectList(NAMESPACE+".eventOne", bbsctt);
		
		
	}


	@Override
	public void insertCoupon_hold(String user_id, int coupon_code) throws Exception {
		Map map = new HashMap();
		map.put("user_id", user_id);
		map.put("coupon_code", coupon_code);
		sql.insert(NAMESPACE+".insertCoupon_hold", map);
	}


	@Override
	public void updateToken(UserVO user) throws Exception {
		sql.update(NAMESPACE+".updateToken", user);
	}

	@Override
	public int androidLoginUser(UserVO user) throws Exception {
	
		return sql.selectOne(NAMESPACE+".androidLoginUser", user);
	}
	
	
	@Override
	public int checkUser(String id) throws Exception {
		
		return sql.selectOne(NAMESPACE+".checkUser", id);
	}
	
	
	// Æ÷ÀÎÆ®
	@Override
	public int point(String user_id) throws Exception {
		
		return sql.selectOne(NAMESPACE+".point", user_id);
	}

	@Override
	public String userToken(String user_id) throws Exception {
		
		return sql.selectOne(NAMESPACE+".userToken", user_id);
	}

	@Override
	public List<HashMap> fcmCoupon(String user_id) throws Exception {
		
		return sql.selectList(NAMESPACE+".fcmCoupon", user_id);
	}

	@Override
	public int confirmCoupon(String user_id, int coupon_code) throws Exception {
		Map map = new HashMap();
		map.put("user_id", user_id);
		map.put("coupon_code", coupon_code);
		return sql.selectOne(NAMESPACE+".confirmCoupon", map);
	}

	@Override
	public List<HashMap> basketInfo(String user_id, int bhf_code) throws Exception {
		Map map = new HashMap();
		map.put("user_id", user_id);
		map.put("bhf_code", bhf_code);
		return sql.selectList(NAMESPACE+".basketInfo", map);
	}

	@Override
	public void updateBasket_qy(JSONObject obj) throws Exception {
		
		sql.update(NAMESPACE+".updateBasket_qy", obj);
		
	}

	@Override
	public void insertBasket(JSONObject json) throws Exception {
		
		sql.insert(NAMESPACE+".insertBasket", json);
		
	}

	@Override
	public List<HashMap> oneBasketInfo(JSONObject json) throws Exception {
		
		return sql.selectList(NAMESPACE+".oneBasketInfo", json);
	}

	@Override
	public int knowBasket_qy(JSONObject json) throws Exception {
		
		return sql.selectOne(NAMESPACE+".knowBasket_qy", json);
	}

	@Override
	public void delBasket(int bhf_code, int goods_code, String user_id) throws Exception {
	
		Map map = new HashMap();
		map.put("user_id", user_id);
		map.put("bhf_code", bhf_code);
		map.put("goods_code", goods_code);
		
		sql.delete(NAMESPACE+".delBasket", map);
		
	}

	@Override
	public String userDeliveryAddr(String user_id) throws Exception {
		
		return sql.selectOne(NAMESPACE+".userDeliveryAddr", user_id);
	}

	@Override
	public List<HashMap> usableCoupon(String user_id) throws Exception {
		
		return sql.selectList(NAMESPACE+".usableCoupon", user_id);
	}

	@Override
	public void insertBill(BillVO bill) throws Exception {
		
		sql.insert(NAMESPACE+".insertBill", bill);
		
	}

	@Override
	public void insertPurchase_goods(Map<String, Object> map) throws Exception {
	
		sql.insert(NAMESPACE+".insertPurchase_goods", map);
		
	}

	@Override
	public void insertDelivery(String delivery_addr) throws Exception {
		
		sql.insert(NAMESPACE+".insertDelivery", delivery_addr);

		
	}

	@Override
	public void insertSettlement_information(int setle_mth_code, int stprc) throws Exception {
		Map map = new HashMap();
		map.put("setle_mth_code", setle_mth_code);
		map.put("stprc", stprc);
		
		sql.insert(NAMESPACE+".insertSettlement_information", map);
	}

	@Override
	public void updatePurchase_goods() throws Exception {
		
		sql.update(NAMESPACE+".updatePurchase_goods");
		
	}

	@Override
	public void updateCoupon_hold(String user_id, int coupon_code) throws Exception {
		
		Map map = new HashMap();
		map.put("user_id", user_id);
		map.put("coupon_code", coupon_code);
		
		sql.update(NAMESPACE+".updateCoupon_hold", map);
		
	}

	@Override
	public HashMap<String, String> getZone(HashMap<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		return sql.selectOne(NAMESPACE + ".getZone", map);
	}

	@Override
	public HashMap goodsOne(int goods_code, int bhf_code) throws Exception {
		
		Map map = new HashMap();
		map.put("goods_code", goods_code);
		map.put("bhf_code", bhf_code);
		
		return sql.selectOne(NAMESPACE+".goodsOne", map);
	}

	@Override
	public List<HashMap> tileList(int bhf_code) throws Exception {
		return sql.selectList(NAMESPACE+".tileList", bhf_code);
	}

	@Override
	public List<HashMap> drawingList(int bhf_code) throws Exception {
	
		return sql.selectList(NAMESPACE+".drawingList", bhf_code);
	}


}

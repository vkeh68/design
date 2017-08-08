package yjc.wdb.scts.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;

import yjc.wdb.scts.bean.BillVO;
import yjc.wdb.scts.bean.CouponVO;
import yjc.wdb.scts.bean.Coupon_holdVO;
import yjc.wdb.scts.bean.GoodsVO;
import yjc.wdb.scts.bean.UserVO;

public interface AndroidService {
	
	public int androidLoginUser(UserVO user) throws Exception;
	public List<BillVO> billList(String user_id, int day) throws Exception;
	public List<HashMap> billOne(int bill_code) throws Exception;
	public List<HashMap> settleInfo(String user_id, int bill_code) throws Exception;
	public List<HashMap> recommandProduct(String user_id) throws Exception;
	public List<CouponVO> couponBasket(String user_id) throws Exception;
	public void delCouponBasket(Coupon_holdVO coupon_holdVO) throws Exception;
	public CouponVO selectSendAndroidCoupon(Map<String, String> vo) throws Exception;
	public List<GoodsVO> productSearch(String productName, int bhf_code) throws Exception;

	public int checkUser(String id) throws Exception;
	public int point(String user_id) throws Exception;
	
	public List<HashMap> eventList() throws Exception;
	public List<HashMap> eventOne(int bbsctt) throws Exception;
	
	public void updateToken(UserVO user) throws Exception;


	public void insertCoupon_hold(String user_id, int coupon_code) throws Exception;
	
	public String userToken(String user_id) throws Exception;
	public List<HashMap> fcmCoupon(String user_id) throws Exception;

	public void periodicCoupon(String user_id, int coupon_code) throws Exception;

	public List<HashMap> basketInfo(String user_id, int bhf_code) throws Exception;
	
	public void updateBasket_qy(JSONObject obj) throws Exception;
	
	public List<HashMap> basket(JSONObject obj) throws Exception;
	
	public void delBasket(int bhf_code, int goods_code, String user_id) throws Exception;
	

	public String userDeliveryAddr(String user_id) throws Exception;
	
	public List<HashMap> usableCoupon(String user_id) throws Exception;
	
	public void delivery(JSONObject json) throws Exception;
	

	public HashMap<String, String> getZone(HashMap<String, String> map) throws Exception;
	
	public JSONObject goodsOne(int goods_code, int bhf_code) throws Exception;
}

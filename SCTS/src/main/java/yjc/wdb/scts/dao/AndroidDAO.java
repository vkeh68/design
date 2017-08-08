package yjc.wdb.scts.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;

import yjc.wdb.scts.bean.BillVO;
import yjc.wdb.scts.bean.CouponVO;
import yjc.wdb.scts.bean.Coupon_holdVO;
import yjc.wdb.scts.bean.GoodsVO;
import yjc.wdb.scts.bean.UserVO;

public interface AndroidDAO {
	
	public int androidLoginUser(UserVO user) throws Exception;
	public List<BillVO> billList(String user_id, int day) throws Exception;
	public List<HashMap> billOne(int bill_code) throws Exception;
	public List<HashMap> settleInfo(String user_id, int bill_code) throws Exception;
	public List<HashMap> recommandProduct(String user_id) throws Exception;
	public List<CouponVO> couponBasket(String user_id) throws Exception;
	public void delCouponBasket(Coupon_holdVO coupon_holdVO) throws Exception;
	public CouponVO selectSendAndroidCoupon(Map map) throws Exception;
	public List<GoodsVO> productSearch(String productName, int bhf_code) throws Exception;
	
	
	public int checkUser(String id) throws Exception;
	public int point(String user_id) throws Exception;
	
	

	public List<HashMap> eventList() throws Exception;
	public List<HashMap> eventOne(int bbsctt) throws Exception;

	public void insertCoupon_hold(String user_id, int coupon_code) throws Exception;
	

	public void updateToken(UserVO user) throws Exception;



	public int scanCoupon_hold(Map map) throws Exception;
	public List<HashMap<String, Integer>> TileCoupon_code(Map map) throws Exception;
	
	
	public String userToken(String user_id) throws Exception;
	public List<HashMap> fcmCoupon(String user_id) throws Exception;
	
	public int confirmCoupon(String user_id, int coupon_code) throws Exception;
	
	public List<HashMap> basketInfo(String user_id, int bhf_code) throws Exception;
	
	public void updateBasket_qy(JSONObject obj) throws Exception;
	
	public void insertBasket(JSONObject json) throws Exception;
	public List<HashMap> oneBasketInfo(JSONObject json) throws Exception;
	
	public int knowBasket_qy(JSONObject json) throws Exception;
	
	public void delBasket(int bhf_code, int goods_code, String user_id) throws Exception;
	
	public String userDeliveryAddr(String user_id) throws Exception;
	
	public List<HashMap> usableCoupon(String user_id) throws Exception;
	
	public void insertBill(BillVO bill) throws Exception;
	public void insertPurchase_goods(Map<String, Object> map) throws Exception;
	public void insertDelivery(String delivery_addr) throws Exception;
	public void insertSettlement_information(int setle_mth_code, int stprc) throws Exception;
	
	public void updatePurchase_goods() throws Exception;
	public void updateCoupon_hold(String user_id, int coupon_code) throws Exception;
	
	public HashMap<String, String> getZone(HashMap<String, String> map) throws Exception;
	
	
	public HashMap goodsOne(int goods_code, int bhf_code) throws Exception;
	
	public List<HashMap> tileList(int bhf_code) throws Exception;
	
	
	public List<HashMap> drawingList(int bhf_code) throws Exception;
}

package yjc.wdb.scts.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import yjc.wdb.scts.bean.BillVO;
import yjc.wdb.scts.bean.CouponVO;
import yjc.wdb.scts.bean.Coupon_holdVO;
import yjc.wdb.scts.bean.GoodsVO;
import yjc.wdb.scts.bean.UserVO;
import yjc.wdb.scts.dao.AndroidDAO;
import yjc.wdb.scts.service.AndroidService;

@Service
public class AndroidServiceImpl implements AndroidService{
	
	@Inject
	private AndroidDAO dao;
	
	@Override
	public List<BillVO> billList(String user_id, int day) throws Exception {
		
		return dao.billList(user_id, day);
	}

	@Override
	public List<HashMap> billOne(int bill_code) throws Exception {
		
		return dao.billOne(bill_code);
	}

	@Override
	public List<HashMap> settleInfo(String user_id, int bill_code) throws Exception {
		
		return dao.settleInfo(user_id, bill_code);
	}

	@Override
	public List<HashMap> recommandProduct(String user_id) throws Exception {
		
		return dao.recommandProduct(user_id);
	}
	
	
	@Override
	public List<CouponVO> couponBasket(String user_id) throws Exception {
		
		return dao.couponBasket(user_id);
	}

	@Override
	public void delCouponBasket(Coupon_holdVO coupon_holdVO) throws Exception {
		
		dao.delCouponBasket(coupon_holdVO);
		
	}
	
	@Override
	@Transactional
	public CouponVO selectSendAndroidCoupon(Map<String, String> vo) throws Exception {
		// 고객이 감지한 타일에서 받을 수 있는 쿠폰들을 검색
		List<HashMap<String, Integer>> coupon_code_Array = dao.TileCoupon_code(vo);
		
		// 해당 쿠폰들을 저장
		Map<String, Object> map = new HashMap<String, Object>();

		if(coupon_code_Array.isEmpty())
			return null;
		
		map.put("user_id", vo.get("user_id"));
		map.put("coupon_code_Array", coupon_code_Array);
		
		// 저장된 쿠폰들 중 받은 것이 있는지 검색
		int coupon = dao.scanCoupon_hold(map);
		
		System.out.println("여기서 어떻게 되나 coupon 값 = " + coupon);
		// 받은게 있을 경우 쿠폰을 주지 않음
		if(coupon > 0)
			return null;
		
		// 받은 게 없으면 쿠폰을 검색해서 랜덤으로 한개 줌
		return dao.selectSendAndroidCoupon(map);
	}

	@Override
	public List<GoodsVO> productSearch(String productName, int bhf_code) throws Exception {
		
		return dao.productSearch(productName, bhf_code);
	}

	@Override
	public List<HashMap> eventList() throws Exception {
		// TODO Auto-generated method stub
		return dao.eventList();
	}

	@Override
	public List<HashMap> eventOne(int bbsctt) throws Exception {
		// TODO Auto-generated method stub
		return dao.eventOne(bbsctt);
	}

	@Override
	public void insertCoupon_hold(String user_id, int coupon_code) throws Exception {
		dao.insertCoupon_hold(user_id, coupon_code);
		
	}

	@Override
	public void updateToken(UserVO user) throws Exception {
		
		dao.updateToken(user);
				
	}

	@Override
	public int androidLoginUser(UserVO user) throws Exception {
		
		return dao.androidLoginUser(user);
	}
	
	
	@Override
	public int checkUser(String id) throws Exception {
		
		return dao.checkUser(id);
	}

	@Override
	public int point(String user_id) throws Exception {
		
		return dao.point(user_id);
	}

	@Override
	public String userToken(String user_id) throws Exception {
		
		return dao.userToken(user_id);
	}

	@Override
	public List<HashMap> fcmCoupon(String user_id) throws Exception {
		
		return dao.fcmCoupon(user_id);
	}

	@Override
	public void periodicCoupon(String user_id, int coupon_code) throws Exception {
		
		int count = dao.confirmCoupon(user_id, coupon_code);
		
		if(count == 0){
			dao.insertCoupon_hold(user_id, coupon_code);
		}
		
	}

	@Override
	public List<HashMap> basketInfo(String user_id, int bhf_code) throws Exception {
	
		return dao.basketInfo(user_id, bhf_code);
	}

	public void updateBasket_qy(JSONObject obj) throws Exception {
		
		dao.updateBasket_qy(obj);
		
	}

	@Transactional
	@Override
	public List<HashMap> basket(JSONObject obj) throws Exception {
		
		String user_id = obj.get("user_id").toString();
		int bhf_code = Integer.parseInt(obj.get("bhf_code").toString());
		
		List<HashMap> list = dao.basketInfo(user_id, bhf_code);
		
		int count = 0;
		for(int i = 0; i < list.size(); i++){
			if((list.get(i).get("goods_code").toString()).equals(obj.get("goods_code").toString())){
				
				System.out.println("존재합니다!!!!"+list.get(i).get("goods_code").toString());
				System.out.println(obj.get("goods_code").toString());
				
				count++;
				
				
			}
		}
		
		if(count <= 0){
			dao.insertBasket(obj);
		}else{
			int basket_qy = dao.knowBasket_qy(obj) +1;
			
			obj.put("basket_qy", basket_qy);
	
			dao.updateBasket_qy(obj);
		}
		
		
		return dao.oneBasketInfo(obj);
	}

	@Override
	public void delBasket(int bhf_code, int goods_code, String user_id) throws Exception {
		dao.delBasket(bhf_code, goods_code, user_id);
	}

	@Override
	public String userDeliveryAddr(String user_id) throws Exception {
		
		return dao.userDeliveryAddr(user_id);
	}

	@Override
	public List<HashMap> usableCoupon(String user_id) throws Exception {
		return dao.usableCoupon(user_id);
	}

	@Transactional
	@Override
	public void delivery(JSONObject json) throws Exception {
		
		String user_id = json.get("user_id").toString();
	
		int bhf_code = Integer.parseInt(json.get("bhf_code").toString());
		
		int bill_totamt = Integer.parseInt(json.get("bill_totamt").toString());
		int setle_mth_code = Integer.parseInt(json.get("setle_mth_code").toString());
		
		Map map = (Map) new JSONParser().parse(json.toJSONString());
		
		List<HashMap<String, String>> goodsList = (List) map.get("goodsList");
				
		Map<String, Object> goodsMap = new HashMap<String, Object>();
		goodsMap.put("goodsList", goodsList);
		
		BillVO bill = new BillVO();
		
		bill.setUser_id(user_id);
		bill.setBhf_code(bhf_code);
		bill.setBill_totamt(bill_totamt);
		
		dao.insertBill(bill);
		dao.insertPurchase_goods(goodsMap);
		dao.insertDelivery(json.get("delivery_addr").toString());
		dao.insertSettlement_information(setle_mth_code, bill_totamt);

		for(int i = 0; i < goodsList.size(); i++){
			
			int goods_code = Integer.parseInt(goodsList.get(i).get("goods_code").toString());
			int coupon_code = Integer.parseInt(goodsList.get(i).get("coupon_code").toString());
			dao.delBasket(bhf_code, goods_code, user_id);
			if(coupon_code != 0)
			{
				dao.updateCoupon_hold(user_id, coupon_code);
			}

			
		}
		
		dao.updatePurchase_goods();
		
	}

	@Override
	public HashMap<String, String> getZone(HashMap<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		return dao.getZone(map);
	}

	@Transactional
	@Override
	public JSONObject goodsOne(int goods_code, int bhf_code) throws Exception {
		HashMap map = dao.goodsOne(goods_code, bhf_code);
		List<HashMap> list = dao.tileList(bhf_code);
		List<HashMap> drawList = dao.drawingList(bhf_code);
		
		
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("goods_code", map.get("goods_code"));
		jsonObj.put("goods_nm", map.get("goods_nm"));
		jsonObj.put("goods_pc", map.get("goods_pc"));
		jsonObj.put("tile_code", map.get("tile_code"));
		jsonObj.put("tile_nm", map.get("tile_nm"));
		jsonObj.put("tile_crdnt_x", map.get("tile_crdnt_x"));
		jsonObj.put("tile_crdnt_y", map.get("tile_crdnt_y"));
		jsonObj.put("drw_code", map.get("drw_code"));
		jsonObj.put("drw_flpth", map.get("drw_flpth"));
		jsonObj.put("floorinfo_floor", map.get("floorinfo_floor"));
		
		JSONObject tileList;
		JSONArray jArray = new JSONArray();
		
		
		for(int i = 0; i < list.size(); i++){
			tileList = new JSONObject();
			tileList.put("drw_code", list.get(i).get("drw_code"));
			tileList.put("tile_code", list.get(i).get("tile_code"));
			tileList.put("tile_nm", list.get(i).get("tile_nm"));
			tileList.put("tile_crdnt_x", list.get(i).get("tile_crdnt_x"));
			tileList.put("tile_crdnt_y", list.get(i).get("tile_crdnt_y"));
			jArray.add(tileList);
		}
		
		
		JSONObject drawingList;
		JSONArray drawingArray = new JSONArray();
		
		
		for(int i = 0; i < drawList.size(); i++){
			drawingList = new JSONObject();
			drawingList.put("drw_code", drawList.get(i).get("drw_code"));
			drawingList.put("drw_flpth", drawList.get(i).get("drw_flpth"));
			drawingList.put("floorinfo_floor", drawList.get(i).get("floorinfo_floor"));
			drawingList.put("size_x", drawList.get(i).get("size_x"));
			drawingList.put("size_y", drawList.get(i).get("size_y"));
			
			
			drawingArray.add(drawingList);
		}
		
		JSONObject json = new JSONObject();
		json.put("goodsOne", jsonObj);
		json.put("tileList", jArray);
		json.put("drawingList", drawingArray);
		
		
		return json;
	}
	


}

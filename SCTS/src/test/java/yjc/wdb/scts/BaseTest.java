package yjc.wdb.scts;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import yjc.wdb.scts.bean.CouponVO;
import yjc.wdb.scts.bean.Purchase_goodsVO;
import yjc.wdb.scts.service.AndroidService;
import yjc.wdb.scts.service.Branch_officeService;
import yjc.wdb.scts.service.CouponService;
import yjc.wdb.scts.service.CourseService;
import yjc.wdb.scts.service.Purchase_goodsService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(
		//locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"} )
		locations = {"file:src/main/webapp/WEB-INF/spring/**/*-context.xml"} )
public class BaseTest {
	
	private static final Logger logger =
			LoggerFactory.getLogger(BaseTest.class);
	
	@Inject
	private WebApplicationContext wac;
	
	private MockMvc mockMvc;
	
	@Inject
	CouponService couponService;
	
	@Inject
	Purchase_goodsService PService;
	
	@Inject
	AndroidService Aservice;
	
	@Inject
	Branch_officeService bService;
	
	@Test
	public void asdasd() throws Exception {
		
		HashMap<String, String> vo = new HashMap<String, String>();

		vo.put("beacon_mjr", "87");
		vo.put("beacon_mnr", "61220");

		CouponVO coupon = Aservice.selectSendAndroidCoupon(vo);
		Map<String, String> map = Aservice.getZone(vo);
		
		JSONObject resultData = new JSONObject();
		
		String str;

		str = new Gson().toJson(coupon);

		resultData.put("coupon", (JSONObject) new JSONParser().parse(str));
		resultData.put("tile", (JSONObject) new JSONParser().parse(new Gson().toJson(map)));
		resultData.put("status", "SUCCESS");
		resultData.put("command", "fullcoupon");
		

		System.out.println(((JSONObject)resultData.get("tile")).get("TILE_CRDNT_X"));
		System.out.println();
		

		JsonObject json = new Gson().fromJson(resultData.toString(), JsonObject.class);
		
		System.out.println(json.get("tile"));
		System.out.println(((JsonObject)json.get("coupon")).get("coupon_dscnt"));
	}

	public void ServerDBTest() {
		
		try {

			HashMap<String, String> vo = new HashMap<String, String>();
			
			vo.put("beacon_mjr", "87" );
			vo.put("beacon_mnr", "59683" );
			vo.put("user_id", "asd" );
			
			
			CouponVO cvo = Aservice.selectSendAndroidCoupon(vo);
			
			System.out.println(cvo.toString());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
	public void mapTest() {
		String str = "{\"user_id\":\"\",\"goodsList\":[{\"goods_code\":\"3\",\"coupon_code\":\"4\",\"purchsgoods_qy\":\"1\"},{\"goods_code\":\"4\",\"purchsgoods_qy\":\"1\"},{\"goods_code\":\"5\",\"purchsgoods_qy\":\"1\"}],\"setle_mth_nm\":\"card\",\"stprc\":265000}";
		
		try {
			JSONObject json = (JSONObject) new JSONParser().parse(str);
			System.out.println(json.toString());

			
			List<HashMap<String, String>> purchase_goodsList = null;	
			
			JSONParser parser = new JSONParser();
			
			Map map = (Map) parser.parse(json.toJSONString());
			
			System.out.println(map.get("goodsList").toString());
			List<HashMap<String, String>> list = (List) map.get("goodsList");
			
			String user_id = map.get("user_id").toString();
			System.out.println(map.get("user_id"));
			System.out.println(user_id);
			
			System.out.println(list.get(0).get("coupon_code"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void dbinput() {
		//Purchase_goodsVO vo = new Purchase_goodsVO();
		/*
		vo.setBill_code(5);
		vo.setGoods_code(4);
		vo.setPurchsgoods_qy(10);
		*/
		
		HashMap<String, String> vo = new HashMap<String, String>();

		vo.put("bill_code", "5");
		vo.put("goods_code", "4");
		vo.put("purchsgoods_qy", "10");
		
		try {
			PService.insertPurchase_goods(vo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void couponSend() {

		try {

			CouponVO coupon = couponService.selectSendAndroidCoupon();
			
			JSONObject resultData = new JSONObject();
			
			if(coupon == null) {
				resultData.put("status", "SUCCESS");
				resultData.put("command", "emptycoupon");
				System.out.println(resultData.toJSONString());
				System.out.println(resultData.toString());
				return;
			}
			String str;
			
			str = new Gson().toJson(coupon);
			resultData = (JSONObject) new JSONParser().parse(str);
			
			resultData.put("status", "SUCCESS");
			resultData.put("command", "fullcoupon");
	
			System.out.println(resultData.toJSONString());
			System.out.println(resultData.toString());
			
			logger.debug(resultData.toString());
			
			return;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void asd() {
		try {
			
			String str = "{\"beacon_mjr\":13716,\"beacon_mnr\":14756,\"tile_code\":134}";
			JSONObject json = (JSONObject) new JSONParser().parse(str);
			System.out.println(json);
			HashMap<String, String> vo = new HashMap<String, String>();
			
			System.out.println(json.get("tile_code").toString());
	
			vo.put("tile_code", json.get("tile_code").toString());
			vo.put("beacon_mjr", json.get("beacon_mjr").toString());
			vo.put("beacon_mnr", json.get("beacon_mnr").toString());
			System.out.println("tile_code = " + vo.get("tile_code") + 
					"beacon_mjr = " + vo.get("beacon_mjr") + 
					"beacon_mnr = " + vo.get("beacon_mnr") );
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Inject
	CourseService courseService;
	//CourseDAO dao;
	
	
	/*
	@Test
	public void asdasd(){
		
		String str;
		try {
			JSONObject json = new JSONObject();
			json.put("status", "ERROR");
			
			System.out.println(json.toString());

			str = new Gson().toJson(couponService.selectTest());
			json = (JSONObject) new JSONParser().parse(str);
			
			json.put("status", "success");

			System.out.println(json.toString());
			
			
			str = new Gson().toJson(couponService.selectTest());
			JSONObject coupon = (JSONObject) new JSONParser().parse(str);
			
			coupon.put("status", "success");
			
			System.out.println(coupon);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
}

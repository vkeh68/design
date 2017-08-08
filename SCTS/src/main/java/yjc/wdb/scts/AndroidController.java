package yjc.wdb.scts;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import yjc.wdb.scts.bean.UserVO;
import yjc.wdb.scts.bean.BillVO;
import yjc.wdb.scts.bean.CouponVO;
import yjc.wdb.scts.bean.Coupon_holdVO;
import yjc.wdb.scts.bean.GoodsVO;
import yjc.wdb.scts.service.AndroidService;
import yjc.wdb.scts.service.Branch_officeService;
import yjc.wdb.scts.service.CourseService;
import yjc.wdb.scts.service.UserService;

@Controller
@RequestMapping("/android")
public class AndroidController {

	@Inject
	private UserService userService;

	@Inject
	private CourseService courseService;
	
	@Inject
	private Branch_officeService branchService;


	@Inject
	private AndroidService androidService;

	private String androidUser_id;


	private static final Logger logger = LoggerFactory.getLogger(AndroidController.class);


	// 메세징
	public final static String AUTH_KEY_FCM = "AAAAC8CC5Gc:APA91bGjNXJsXudD_Cmimb9WyhrnSsARuNb9rwI_dPLbxqMFn5mYg0V027JWRzmTjQpn_9BiWWKPlv-5LyH5KysYukKPl3Gmz60NAC7fN3tnEAMr_HQzd3jFSBuSOVf2D8qqa-cO13rm";
	public final static String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";

	// 안드로이드에서 전송되는 로그인 정보를 저장
	@RequestMapping(value="/androidLogin", produces = "text/plain; charset=UTF-8")
	@ResponseBody
	public String androidLogin(HttpServletRequest request) throws Exception{
		// 에러 방지하기 위해 추가함
		// request 객체 안에 넘어오는 파라미터가 원하는 것이 있으면 계속 진행되지만 없을 경우 error 라는 문자열을 리턴함
		String str = request.getParameter("UserVO");
		if(str == null) {
			return "ERROR";
		}



		JSONObject userJson = (JSONObject) new JSONParser().parse( str );

		UserVO user = new UserVO();
		try {

			user.setUser_id( userJson.get("user_id").toString() );
			user.setUser_password(userJson.get("user_pw").toString());
			user.setToken(userJson.get("token").toString());

			System.out.println(user.getUser_id() + " " + user.getToken());

			androidService.updateToken(user);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		if(androidService.androidLoginUser(user) == 1){
			androidUser_id = user.getUser_id();
			return "SUCCESS";
		}
		else
			return "ERROR";

	}


	// 안드로이드에서 비콘정보 전송
	/*
	 * 처음 비콘을 감지햇을때 해당하는 감지된 비콘을 바로 서버측으로 전송해서 
	 * 감지된 비콘을 저장하여 실시간에 사용할 수 있도록 하는 역할
	 */
	@RequestMapping(value="/firstCourse", produces = "text/plain; charset=UTF-8")
	@ResponseBody
	public String firstCourse(HttpServletRequest request) throws Exception{
		// 에러 방지하기 위해 추가함
		// request 객체 안에 넘어오는 파라미터가 원하는 것이 있으면 계속 진행되지만 없을 경우 error 라는 문자열을 리턴함
		String str = request.getParameter("CourseVO");

		JSONObject resultData = new JSONObject();

		if(str == null) {
			resultData.put("status", "ERROR");
			resultData.put("errorCode", "CourseEmpty");
			return resultData.toString();
		}

		// 넘어온 문자열을 json 객체로 변환
		JSONObject courseJson = (JSONObject) new JSONParser().parse( str );

		//PositionVO position = new PositionVO();
		HashMap<String, String> vo = new HashMap<String, String>();

		try {
			// 문자열 형태의 날짜시간 값을 timestamp값으로 변환
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.S");
			java.util.Date parsedDate = dateFormat.parse( (String) courseJson.get("cours_pasng_time") );
			Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());

			vo.put("beacon_mjr", courseJson.get("beacon_mjr").toString() );
			vo.put("beacon_mnr", courseJson.get("beacon_mnr").toString() );
			vo.put("user_id", courseJson.get("user_id").toString() );
			vo.put("cours_pasng_time", timestamp.toString() );
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.debug("깨짐");
			resultData.put("status", "ERROR");
			return resultData.toString();
		}
		
		int bhf_code = 0;
		
		// 디비에 저장
		try {
			courseService.insertCourse(vo);
			bhf_code = branchService.selectBranchCode(vo);
		}
		catch (Exception e) {
			e.printStackTrace();
			resultData.put("status", "ERROR");
			resultData.put("errorCode", "BeaconNotTileSetting");
			return resultData.toString();
		}
		
		resultData.put("bhf_code", bhf_code);
		
		// 안드로이드로 쿠폰 정보를 보내기 위해서 사용
		CouponVO coupon = androidService.selectSendAndroidCoupon(vo);
		Map<String, String> tilemap = androidService.getZone(vo);

		str = new Gson().toJson(tilemap);
		resultData.put("tile", (JSONObject) new JSONParser().parse(str));
		
		if(coupon == null) {
			resultData.put("status", "SUCCESS");
			resultData.put("command", "emptycoupon");
			return resultData.toString();
		}

		str = new Gson().toJson(coupon);
		resultData.put("coupon", (JSONObject) new JSONParser().parse(str));

		resultData.put("status", "SUCCESS");
		resultData.put("command", "fullcoupon");
		
		
		logger.debug(resultData.toString());

		return resultData.toString();
	}

	/*
	 * 두번째 비콘 전송, 이 명령이 실행 될때는
	 * 안드로이드 측에서의 현재 가장 가까운 비콘의 변경이 일어나서 기존에 감지하고 있던 비콘의 머문 시간을 저장 시킬 때 이 명령이 실행 됨
	 */
	@RequestMapping(value="/secondCourse", produces = "text/plain; charset=UTF-8")
	@ResponseBody
	public String secondCourse(HttpServletRequest request) throws Exception{
		// 에러 방지하기 위해 추가함
		// request 객체 안에 넘어오는 파라미터가 원하는 것이 있으면 계속 진행되지만 없을 경우 error 라는 문자열을 리턴함
		String str = request.getParameter("CourseVO");

		JSONObject resultData = new JSONObject();

		if(str == null) {
			resultData.put("status", "ERROR");
			return resultData.toString();
		}

		// 넘어온 문자열을 json 객체로 변환
		JSONObject courseJson = (JSONObject) new JSONParser().parse( str );

		//PositionVO position = new PositionVO();
		HashMap<String, String> vo = new HashMap<String, String>();

		try {
			// 문자열 형태의 날짜시간 값을 timestamp값으로 변환
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.S");
			java.util.Date parsedDate = dateFormat.parse( (String) courseJson.get("cours_pasng_time") );
			Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());

			vo.put("beacon_mjr", courseJson.get("beacon_mjr").toString() );
			vo.put("beacon_mnr", courseJson.get("beacon_mnr").toString() );
			vo.put("user_id", courseJson.get("user_id").toString() );
			vo.put("cours_stay_time", courseJson.get("cours_stay_time").toString() );
			vo.put("cours_pasng_time", timestamp.toString() );
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.debug("깨짐");
			resultData.put("status", "ERROR");
			return resultData.toString();
		}

		logger.debug(str);
		logger.debug(vo.toString());

		// 디비에 머문시간 저장
		//positionService.insertPosition(position);
		courseService.updateStayTime(vo);


		resultData.put("status", "SUCCESS");
		resultData.put("command", "emptycoupon");

		logger.debug(resultData.toString());

		return resultData.toString();
	}

	// 회원가입
	@RequestMapping(value="checkUser", method=RequestMethod.POST, produces = "text/plain; charset=UTF-8")
	public @ResponseBody String checkUser(String user_id, HttpServletRequest request) throws Exception{
		System.out.print(request.getParameter("user_id"));
		int checkUser = androidService.checkUser(user_id);
		return ""+checkUser;
	}
	@RequestMapping(value="signUp", method=RequestMethod.POST)
	public @ResponseBody String signUp(HttpServletRequest request) throws Exception{

		System.out.println(request.getParameter("json"));
		String str = request.getParameter("json");
		JSONObject joinJSON = (JSONObject) new JSONParser().parse(str);

		UserVO user = new UserVO();
		user.setUser_id(joinJSON.get("user_id").toString());
		user.setUser_password((String) joinJSON.get("user_pw").toString());
		//user.setAge(Integer.parseInt(joinJSON.get("age").toString()));
		user.setUser_sexdstn(joinJSON.get("gender").toString());



		userService.insertUser(user);



		int checkUser = androidService.checkUser(user.getUser_id());
		return ""+checkUser;
	}

	// 이벤트 보기
	@RequestMapping(value="eventList", method=RequestMethod.GET)
	public @ResponseBody String eventList(HttpServletRequest request) throws Exception{

		String callback = request.getParameter("callback");
		List<HashMap> list = androidService.eventList();
		JSONObject eventJson;
		JSONObject event;
		JSONArray eventArray = new JSONArray();
		for(int i = 0; i < list.size(); i++){
			eventJson = new JSONObject();
			eventJson.put("bbsctt_code", list.get(i).get("bbsctt_code"));
			eventJson.put("event_begin_de", list.get(i).get("event_begin_de").toString());
			eventJson.put("event_end_de", list.get(i).get("event_end_de").toString());
			eventJson.put("bbsctt_sj", list.get(i).get("bbsctt_sj"));

			eventArray.add(eventJson);

		}

		event = new JSONObject();
		event.put("data", eventArray);

		String eventStr = event.toString();

		return callback+"("+eventStr+")";
	}

	// 이벤트 상세보기
	@RequestMapping(value="eventOne", method=RequestMethod.GET)
	public @ResponseBody String eventOne(int bbsctt_code, HttpServletRequest request) throws Exception{


		String callback = request.getParameter("callback");
		List<HashMap> list = androidService.eventOne(bbsctt_code);

		JSONObject eventJson = new JSONObject();
		eventJson.put("bbsctt_code", list.get(0).get("bbsctt_code"));
		eventJson.put("event_begin_de", list.get(0).get("event_begin_de").toString());
		eventJson.put("event_end_de", list.get(0).get("event_end_de").toString());
		eventJson.put("bbsctt_sj", list.get(0).get("bbsctt_sj"));
		eventJson.put("bbsctt_cn", list.get(0).get("bbsctt_cn"));

		return callback + "(" + eventJson + ")";

	}



	// 쿠폰 바구니에 담긴 쿠폰 보기
	@RequestMapping(value="couponList", method=RequestMethod.GET, produces = "text/plain; charset=UTF-8")
	public @ResponseBody String couponList(String user_id, HttpServletRequest request) throws Exception{

		String callback = request.getParameter("callback");
		List<CouponVO> list = androidService.couponBasket(user_id);
		JSONObject couponJson;
		JSONObject coupon;
		JSONArray couponArray = new JSONArray();
		for(int i = 0; i < list.size(); i++){
			couponJson = new JSONObject();
			couponJson.put("coupon_code", list.get(i).getCoupon_code());
			couponJson.put("coupon_nm", list.get(i).getCoupon_nm());
			couponJson.put("coupon_cntnts", list.get(i).getCoupon_cntnts());
			couponJson.put("coupon_begin_de", list.get(i).getCoupon_begin_de().toString());
			couponJson.put("coupon_end_de", list.get(i).getCoupon_end_de().toString());

			couponArray.add(couponJson);

		}
		coupon = new JSONObject();
		coupon.put("data", couponArray);

		return callback+"("+coupon+")";
	}

	// 쿠폰 바구니에서 쿠폰 삭제
	@RequestMapping(value="delCouponBasket", method=RequestMethod.GET, produces = "text/plain; charset=UTF-8")
	public @ResponseBody String delCouponBasket(Coupon_holdVO coupon_holdVO, HttpServletRequest request) throws Exception{

		String callback = request.getParameter("callback");
		androidService.delCouponBasket(coupon_holdVO);


		JSONObject coupon;

		coupon = new JSONObject();
		coupon.put("result", "success");



		return callback+"("+coupon+")";
	}

	// 계산서 정보
	@RequestMapping(value="billList", method=RequestMethod.GET, produces = "text/plain; charset=UTF-8")
	public @ResponseBody String billList(String user_id, int day, HttpServletRequest request) throws Exception{

		String callback = request.getParameter("callback");

		List<BillVO> list = androidService.billList(user_id,day);


		JSONObject billJson;

		JSONArray billArray = new JSONArray();
		for(int i=0; i < list.size(); i++){
			billJson = new JSONObject();
			billJson.put("bill_code", list.get(i).getBill_code());	
			billJson.put("bill_issu_de", list.get(i).getBill_issu_de().toString());		
			billJson.put("bill_totamt", list.get(i).getBill_totamt());

			billArray.add(billJson);
		}

		JSONObject billList = new JSONObject();
		billList.put("data", billArray);

		System.out.println(billList.toString());


		return callback+"("+billList+")";
	}

	// 계산서 상세정보
	@RequestMapping(value="billOne", method=RequestMethod.GET, produces = "text/plain; charset=UTF-8")
	public @ResponseBody String billOne(int bill_code, HttpServletRequest request) throws Exception{

		String callback = request.getParameter("callback");

		List<HashMap> list = androidService.billOne(bill_code); 


		JSONObject billJson;

		JSONArray billArray = new JSONArray();
		for(int i=0; i < list.size(); i++){
			billJson = new JSONObject();	
			billJson.put("goods_nm", list.get(i).get("goods_nm"));	
			billJson.put("purchgoods_qy", list.get(i).get("purchgoods_qy"));	
			billJson.put("COUPON_DSCNT", list.get(i).get("COUPON_DSCNT"));
			billJson.put("price", list.get(i).get("price"));	

			billArray.add(billJson);
		}

		JSONObject billList = new JSONObject();
		billList.put("data", billArray);

		System.out.println(billList.toString());


		return callback+"("+billList+")";
	}

	// 계산서를 결제하다 정보
	@RequestMapping(value="settleInfo", method=RequestMethod.GET, produces = "text/plain; charset=UTF-8")
	public @ResponseBody String settleInfo(String user_id, int bill_code,
			HttpServletRequest request) 
					throws Exception{

		String callback = request.getParameter("callback");

		List<HashMap> list = androidService.settleInfo(user_id, bill_code);

		JSONObject settleJson;

		JSONArray settleArray = new JSONArray();
		for(int i=0; i < list.size(); i++){
			settleJson = new JSONObject();	
			settleJson.put("bill_code", list.get(i).get("bill_code"));	
			settleJson.put("setle_mth_code", list.get(i).get("setle_mth_code"));
			settleJson.put("setle_mth_nm", list.get(i).get("setle_mth_nm"));	
			settleJson.put("stprc", list.get(i).get("stprc"));

			settleArray.add(settleJson);
		}

		JSONObject settleList = new JSONObject();
		settleList.put("data", settleArray);

		System.out.println(settleList.toString());


		return callback+"("+settleList+")";


	}




	// 포인트
	@RequestMapping(value="point", method=RequestMethod.POST, produces = "text/plain; charset=UTF-8")
	public @ResponseBody String point(HttpServletRequest request) throws Exception{

		System.out.println(request.getParameter("user_id"));
		int point = androidService.point(request.getParameter("user_id"));
		System.out.println(point);
		return ""+point;
	}

	// 물품검색
	@RequestMapping(value="productSearch", method=RequestMethod.POST, produces = "text/plain; charset=UTF-8")
	public @ResponseBody String productSearch(HttpServletRequest request) throws Exception{


		request.setCharacterEncoding("UTF-8");
		String json = request.getParameter("json");
		JSONObject jsonObj = (JSONObject) new JSONParser().parse(json);
		String productName = jsonObj.get("productName").toString();
		
		int bhf_code = Integer.parseInt(jsonObj.get("bhf_code").toString()); 
		System.out.println(productName);

		List<GoodsVO> list = androidService.productSearch(productName, bhf_code);

		JSONObject productJSON;

		JSONArray productArray = new JSONArray();
		JSONObject productSearchList = new JSONObject();

		if(list == null){

			productArray = null;
			productSearchList.put("data", productArray);
			return productSearchList.toString();

		}



		for(int i=0; i < list.size(); i++){
			productJSON = new JSONObject();	
			productJSON.put("goods_code", list.get(i).getGoods_code());
			productJSON.put("goods_nm", list.get(i).getGoods_nm());
			productJSON.put("goods_pc", list.get(i).getGoods_pc());
			productJSON.put("goods_flpth", list.get(i).getGoods_flpth());

			productArray.add(productJSON);
		}

		productSearchList.put("data", productArray);

		System.out.println(productSearchList.toString());


		return productSearchList.toString();
	}



	@RequestMapping(value="insertCoupon_hold", method=RequestMethod.GET)
	public @ResponseBody String insertCoupon_hold(String user_id, int coupon_code, HttpServletRequest request) throws Exception{

		System.out.println(user_id + " "+coupon_code);


		String callback = request.getParameter("callback");


		androidService.insertCoupon_hold(user_id, coupon_code);

		JSONObject coupon;

		coupon = new JSONObject();
		coupon.put("result", "success");


		return callback+"("+coupon+")";


	}


	@RequestMapping(value="fcmCoupon", method=RequestMethod.GET)
	public @ResponseBody String fcmCoupon() throws Exception{

		String token = androidService.userToken(androidUser_id);
		List<HashMap> list = androidService.fcmCoupon(androidUser_id);


		JSONObject couponJson;
		JSONObject coupon;
		JSONArray couponArray = new JSONArray();
		for(int i = 0; i < list.size(); i++){
			couponJson = new JSONObject();
			couponJson.put("coupon_code", list.get(i).get("coupon_code").toString());
			couponJson.put("coupon_nm", list.get(i).get("coupon_nm").toString());
			couponJson.put("coupon_cntnts", list.get(i).get("coupon_cntnts").toString());
			couponJson.put("coupon_begin_de", list.get(i).get("coupon_begin_de").toString());
			couponJson.put("coupon_end_de", list.get(i).get("coupon_end_de").toString());

			couponArray.add(couponJson);

		}

		coupon = new JSONObject();
		coupon.put("coupon", couponArray.toString());


		String authKey = AUTH_KEY_FCM;
		String FCMurl = API_URL_FCM;

		URL url = new URL(FCMurl);

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setUseCaches(false);
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Authorization", "key=" + authKey);
		conn.setRequestProperty("Content-Type", "application/json");

		JSONObject json = new JSONObject();
		JSONObject info = new JSONObject();

		info.put("body", "쿠폰이 도착했습니다."); // Notification body

		json.put("notification", info);
		json.put("to", token.trim()); // deviceID
		json.put("data", coupon);

		System.out.println(json.toString());

		//혹시나 한글 깨짐이 발생하면
		try(OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream(), "UTF-8")){

			wr.write(json.toString());
			wr.flush();
		}catch(Exception e){
		}

		if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
			throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));

		String output;
		System.out.println("Output from Server .... \n");
		while ((output = br.readLine()) != null) {
			System.out.println(output);
		}

		conn.disconnect();

		return "success";

	}



	@RequestMapping(value="periodicCoupon", method=RequestMethod.GET)
	public void periodicCoupon(String user_id, int coupon_code) throws Exception{

		androidService.periodicCoupon(user_id, coupon_code);


	}



	// 장바구니 보기
	@RequestMapping(value="basketInfo", method=RequestMethod.GET, produces = "text/plain; charset=UTF-8")
	public @ResponseBody String basketInfo(String user_id, int bhf_code, HttpServletRequest request) throws Exception{

		String callback = request.getParameter("callback");
		List<HashMap> list = androidService.basketInfo(user_id, bhf_code);
		JSONObject basketJson;
		JSONObject basket;
		JSONArray basketArray = new JSONArray();
		for(int i = 0; i < list.size(); i++){
			basketJson = new JSONObject();
			basketJson.put("goods_code", list.get(i).get("goods_code").toString());
			basketJson.put("goods_nm", list.get(i).get("goods_nm").toString());
			basketJson.put("goods_pc", list.get(i).get("goods_pc").toString());
			basketJson.put("basket_qy", list.get(i).get("basket_qy").toString());

			basketArray.add(basketJson);

		}

		basket = new JSONObject();
		basket.put("data", basketArray);

		String basketStr = basket.toString();

		return callback+"("+basketStr+")";
	}



	@RequestMapping(value="updateBasket_qy", method=RequestMethod.GET,  produces = "text/plain; charset=UTF-8")
	public @ResponseBody String updateBasket_qy(int bhf_code, int goods_code, String user_id, int basket_qy, HttpServletRequest request) throws Exception{

		String callback = request.getParameter("callback");
		JSONObject obj = new JSONObject();
		obj.put("bhf_code", bhf_code);
		obj.put("goods_code", goods_code);
		obj.put("user_id", user_id);
		obj.put("basket_qy", basket_qy);

		System.out.println(obj.toJSONString());
		androidService.updateBasket_qy(obj);
		JSONObject json = new JSONObject();
		json.put("result", "success");


		return callback+"("+json.toString()+")";
	}



	// nfc를 이용하여 상품정보 가져옴
	@RequestMapping(value="oneBasketInfo", method=RequestMethod.POST, produces = "text/plain; charset=UTF-8")
	public @ResponseBody String oneBasketInfo(HttpServletRequest request) throws Exception{

		String str = request.getParameter("basket");

		JSONObject json = (JSONObject) new JSONParser().parse(str);

		List<HashMap> list = androidService.basket(json);
		JSONObject basketJson;
		JSONObject basket;
		JSONArray basketArray = new JSONArray();
		for(int i = 0; i < list.size(); i++){
			basketJson = new JSONObject();
			basketJson.put("goods_code", list.get(i).get("goods_code").toString());
			basketJson.put("goods_nm", list.get(i).get("goods_nm").toString());
			basketJson.put("goods_pc", list.get(i).get("goods_pc").toString());
			basketJson.put("basket_qy", list.get(i).get("basket_qy").toString());

			basketArray.add(basketJson);

		}

		basket = new JSONObject();
		basket.put("data", basketArray);

		String basketStr = basket.toString();

		return basketStr;
	}


	@RequestMapping(value="delBasket", method=RequestMethod.GET,  produces = "text/plain; charset=UTF-8")
	public @ResponseBody String delBasket(int bhf_code, int goods_code, String user_id, HttpServletRequest request) throws Exception{

		String callback = request.getParameter("callback");


		androidService.delBasket(bhf_code, goods_code, user_id);
		JSONObject json = new JSONObject();
		json.put("result", "success");


		return callback+"("+json.toString()+")";
	}


	@RequestMapping(value="userDeliveryAddr", method=RequestMethod.GET,  produces = "text/plain; charset=UTF-8")
	public @ResponseBody String userDeliveryAddr(String user_id, HttpServletRequest request) throws Exception{

		String callback = request.getParameter("callback");


		String addr = androidService.userDeliveryAddr(user_id);
		JSONObject json = new JSONObject();
		json.put("user_addr", addr);



		return callback+"("+json.toString()+")";
	}


	@RequestMapping(value="usableCoupon", method=RequestMethod.GET, produces = "text/plain; charset=UTF-8")
	public @ResponseBody String usableCoupon(String user_id, HttpServletRequest request) throws Exception{


		String callback = request.getParameter("callback");



		List<HashMap> list = androidService.usableCoupon(user_id);
		JSONObject basketJson;
		JSONObject basket;
		JSONArray basketArray = new JSONArray();
		for(int i = 0; i < list.size(); i++){
			basketJson = new JSONObject();
			basketJson.put("goods_code", list.get(i).get("goods_code").toString());
			basketJson.put("coupon_code", list.get(i).get("coupon_code").toString());
			basketJson.put("coupon_nm", list.get(i).get("coupon_nm").toString());
			basketJson.put("coupon_dscnt", list.get(i).get("coupon_dscnt").toString());
			basketJson.put("coupon_end_de", list.get(i).get("coupon_end_de").toString());

			basketArray.add(basketJson);

		}

		basket = new JSONObject();
		basket.put("data", basketArray);

		String basketStr = basket.toString();

		return callback+"(" + basketStr + ")";
	}


	@RequestMapping(value="delivery", method=RequestMethod.GET)
	public @ResponseBody String delivery(HttpServletRequest request) throws Exception{

		String user_id = request.getParameter("user_id");
        int setle_mth_code = Integer.parseInt(request.getParameter("setle_mth_code"));
        int bhf_code =  Integer.parseInt(request.getParameter("bhf_code"));
        int bill_totamt = Integer.parseInt(request.getParameter("bill_totamt"));
        String delivery_addr = request.getParameter("delivery_addr");
        String goodsList = request.getParameter("goodsList");

        System.out.println(goodsList);
        
        JSONArray jArray = (JSONArray) new JSONParser().parse(goodsList);
        System.out.println(jArray.toString());
        
        JSONObject json = new JSONObject();
        json.put("user_id", user_id);
        json.put("setle_mth_code", setle_mth_code);
        json.put("bhf_code", bhf_code);
        json.put("bill_totamt", bill_totamt);
        json.put("delivery_addr", delivery_addr);
        json.put("goodsList", jArray);
        
        
        
        androidService.delivery(json);
        
        System.out.println(json.toString());

		JSONObject result = new JSONObject();
		result.put("result", "success");

		String callback = request.getParameter("callback");


		return callback+"("+result.toString()+")";
	}

	@RequestMapping(value="goodsOne", method=RequestMethod.GET,  produces = "text/plain; charset=UTF-8")
	public @ResponseBody String delBasket(int bhf_code, int goods_code, HttpServletRequest request) throws Exception{

		String callback = request.getParameter("callback");


		JSONObject json = androidService.goodsOne(goods_code, bhf_code);
		
		System.out.println(json.toString());

		return callback+"("+json.toString()+")";
	}

}

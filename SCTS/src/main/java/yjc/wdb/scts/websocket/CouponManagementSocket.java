package yjc.wdb.scts.websocket;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import yjc.wdb.scts.bean.CouponVO;
import yjc.wdb.scts.service.BBSService;
import yjc.wdb.scts.service.CouponService;

public class CouponManagementSocket extends TextWebSocketHandler{

	private static Logger logger = LoggerFactory.getLogger(MainSocket.class);
	
	private List<WebSocketSession> sessionList = new ArrayList<WebSocketSession>();
	private List<CouponVO> couponList;

	@Inject
	private CouponService couponService;
	
	// Ŭ���̾�Ʈ �������Ŀ� ����Ǵ� �޼ҵ�
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		
		super.afterConnectionEstablished(session);
		
		
		sessionList.add(session);
		logger.info("{}�� {} �����", session.getUri(), session.getId());
	}

	// Ŭ���̾�Ʈ�� �� ���ϼ����� �޼����� �������� �� ����Ǵ� �޼ҵ�
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		
		super.handleTextMessage(session, message);
		
		logger.info("{} ����", message.getPayload());
		
		JSONParser jsonp = new JSONParser();
		JSONObject obj = null;
		obj = (JSONObject) jsonp.parse(message.getPayload());
		
		int goods_code = Integer.parseInt(obj.get("goods_code").toString());
		int detailctgry_code = Integer.parseInt(obj.get("detailctgry_code").toString());
		int coupon_co = Integer.parseInt(obj.get("coupon_co").toString());
		String coupon_nm = obj.get("coupon_nm").toString();
		String coupon_cntnts = obj.get("coupon_cntnts").toString();
		String coupon_dscnt = obj.get("coupon_dscnt").toString();
		String yPersent = obj.get("yPersent").toString();
		Date coupon_begin_de = Date.valueOf(obj.get("coupon_begin_de").toString());
		Date coupon_end_de = Date.valueOf(obj.get("coupon_end_de").toString());
		String select = obj.get("select").toString();
		JSONArray branch = (JSONArray) new JSONParser().parse(obj.get("branch_office").toString());
		int[] branch_office = new int[branch.size()];
		
		CouponVO couponVO = new CouponVO();
		couponVO.setCoupon_begin_de(coupon_begin_de);;
		couponVO.setCoupon_cntnts(coupon_cntnts);;
		couponVO.setCoupon_co(coupon_co);
		couponVO.setCoupon_dscnt(coupon_dscnt);
		couponVO.setCoupon_end_de(coupon_end_de);;
		couponVO.setCoupon_nm(coupon_nm);
		couponVO.setDetailctgry_code(detailctgry_code);;
		couponVO.setGoods_code(goods_code);
		couponVO.setyPersent(yPersent);
		
		for(int i = 0; i < branch.size(); i++){
			branch_office[i] = Integer.parseInt(branch.get(i).toString());
			System.out.println(branch_office[i]);
		}
		
		couponList = couponService.insertAdCoupon(couponVO, branch_office, select);
		System.out.println(couponList.toString());
		
		JSONObject json = null;
		JSONArray jArray = new JSONArray();
		
		for(int i = 0; i < couponList.size(); i++){
			json = new JSONObject();
			json.put("coupon_code", couponList.get(i).getCoupon_code());
			json.put("coupon_nm", couponList.get(i).getCoupon_nm());
			json.put("coupon_cntnts", couponList.get(i).getCoupon_cntnts());
			json.put("coupon_dscnt", couponList.get(i).getCoupon_dscnt());
			json.put("coupon_begin_de", couponList.get(i).getCoupon_begin_de().toString());
			json.put("coupon_end_de", couponList.get(i).getCoupon_end_de().toString());
			json.put("bhf_code", couponList.get(i).getBhf_code());
			jArray.add(json);
		}
		
		JSONObject coupon = new JSONObject();
		coupon.put("message", "���翡�� ������ �߱޵Ǿ����ϴ�");
		coupon.put("coupon", jArray);
		
		System.out.println(coupon.toJSONString());
		
		for(WebSocketSession sess : sessionList){
			sess.sendMessage(new TextMessage(coupon.toString()));
		}
	
	}
	
	// Ŭ���̾�Ʈ�� ������ ������ �� ����Ǵ� �޼ҵ�
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		
		super.afterConnectionClosed(session, status);
		System.out.println(status);
		sessionList.remove(session);
		logger.info("{} ���� ����", session.getId());
	}
	
	

}

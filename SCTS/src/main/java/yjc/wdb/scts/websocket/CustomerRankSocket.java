package yjc.wdb.scts.websocket;

import java.util.ArrayList;
import java.util.HashMap;
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

import yjc.wdb.scts.dao.BillDAO;

public class CustomerRankSocket extends TextWebSocketHandler{

	private static Logger logger = LoggerFactory.getLogger(MainSocket.class);
	
	private List<WebSocketSession> sessionList = new ArrayList<WebSocketSession>();
	private List<HashMap> ageSales;
	private List<HashMap> ageSalesInfo;
	private JSONParser parser;

	@Inject
	private BillDAO billDAO;
	
	// 클라이언트 연결이후에 실행되는 메소드
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		
		super.afterConnectionEstablished(session);
		
		
		sessionList.add(session);
		logger.info("{}에 {} 연결됨", session.getUri(), session.getId());
	}

	// 클라이언트가 웹 소켓서버로 메세지를 전송했을 때 실행되는 메소드
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		
		super.handleTextMessage(session, message);
		
		logger.info("{} 보냄", message.getPayload());
		

		parser = new JSONParser();

		JSONObject obj =null;
		obj = (JSONObject) parser.parse(message.getPayload());
		
		String date = obj.get("date").toString();
		int standard = Integer.parseInt(obj.get("standard").toString());
		int age = Integer.parseInt(obj.get("age").toString());
		String gender = obj.get("gender").toString();
		int bhf_code = Integer.parseInt(obj.get("bhf_code").toString());
		
		if(gender.equals("null")){
			gender = null;
		}
		

		ageSales = billDAO.ageSales(date, age, standard, gender, bhf_code);

		
		JSONArray jArray = new JSONArray();
		
		for(int i = 0; i < ageSales.size(); i++){
			JSONObject json = new JSONObject();
			json.put("goods_nm", ageSales.get(i).get("goods_nm"));
			json.put("goods_netIncome", ageSales.get(i).get("goods_netIncome"));
			json.put("totalPrice", ageSales.get(i).get("totalPrice"));
			
			jArray.add(json);
		}

		
		
		ageSalesInfo = billDAO.ageSalesInfo(date, age, standard, gender, bhf_code);
		
		System.out.println(ageSalesInfo.toString());
		JSONArray yearSalesInfoArray = new JSONArray();
		for(int i = 0; i < ageSalesInfo.size(); i++){
			JSONObject json = new JSONObject();
			json.put("goods_nm", ageSalesInfo.get(i).get("goods_nm"));
			json.put("goods_netIncome", ageSalesInfo.get(i).get("goods_netIncome"));
			json.put("totalPrice", ageSalesInfo.get(i).get("totalPrice"));
			json.put("totalPurchsgoods_qy", ageSalesInfo.get(i).get("totalPurchsgoods_qy"));
			json.put("totalCouponCount", ageSalesInfo.get(i).get("totalCouponCount"));
			yearSalesInfoArray.add(json);
		}
		

		int todaySales = billDAO.todaySales(bhf_code);
		int monthTotalSales = billDAO.monthTotalSales(bhf_code);
		
		JSONObject result = new JSONObject();
		result.put("ageSales", jArray);
		result.put("ageSalesInfo", yearSalesInfoArray);
		result.put("todaySales", todaySales);
		result.put("monthTotalSales", monthTotalSales);
		
		for(WebSocketSession sess : sessionList){
			sess.sendMessage(new TextMessage(result.toString()));
		}
		
	}
	
	// 클라이언트가 연결을 끊었을 때 실행되는 메소드
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		
		super.afterConnectionClosed(session, status);
		System.out.println(status);
		sessionList.remove(session);
		logger.info("{} 연결 끊김", session.getId());
	}
	
	

}
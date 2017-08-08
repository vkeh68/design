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
import yjc.wdb.scts.dao.CourseDAO;

public class YearSalesSocket extends TextWebSocketHandler{

	private static Logger logger = LoggerFactory.getLogger(MainSocket.class);
	
	private List<WebSocketSession> sessionList = new ArrayList<WebSocketSession>();
	private List<HashMap> list;
	private List<HashMap> yearSalesInfo;
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


		int bhf_code = Integer.parseInt(obj.get("bhf_code").toString());
		
		list = billDAO.yearSales(Integer.parseInt(obj.get("year2").toString()), bhf_code);
		
		//System.out.println(list.toString());
		
		JSONArray jArray = new JSONArray();
		
		for(int i = 0; i < list.size(); i++){
			JSONObject json = new JSONObject();
			json.put("year", list.get(i).get("year").toString());
			json.put("totalPrice", list.get(i).get("totalPrice"));
			
			jArray.add(json);
		}

		yearSalesInfo = billDAO.settleSalesInfo(Integer.parseInt(obj.get("year1").toString()), 
				Integer.parseInt(obj.get("year2").toString()), bhf_code);
		JSONArray yearSalesInfoArray = new JSONArray();
		for(int i = 0; i < yearSalesInfo.size(); i++){
			JSONObject json = new JSONObject();
			json.put("year", yearSalesInfo.get(i).get("year").toString());
			json.put("totalPrice", yearSalesInfo.get(i).get("totalPrice"));
			json.put("setle_mth_nm", yearSalesInfo.get(i).get("setle_mth_nm").toString());
			yearSalesInfoArray.add(json);
		}
		
		System.out.println(yearSalesInfo.toString());
		
		int todaySales = billDAO.todaySales(bhf_code);

		
		int monthTotalSales = billDAO.monthTotalSales(bhf_code);
		
		JSONObject result = new JSONObject();
		result.put("yearSales", jArray);
		result.put("yearSalesInfo", yearSalesInfoArray);
		result.put("todaySales", todaySales);
		result.put("monthTotalSales", monthTotalSales);
		
		System.out.println(result.toString());
		
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

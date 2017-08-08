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

public class SearchYearSalesSocket extends TextWebSocketHandler{

	private static Logger logger = LoggerFactory.getLogger(MainSocket.class);
	
	private List<WebSocketSession> sessionList = new ArrayList<WebSocketSession>();
	private List<HashMap> searchYear;
	private List<HashMap> yearSalesInfo;
	private JSONParser parser;
	
	@Inject
	private BillDAO billDAO;

	
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
		
		parser = new JSONParser();
		
		JSONObject obj =null;
		obj = (JSONObject) parser.parse(message.getPayload());
		
		int year1 = Integer.parseInt(obj.get("year1").toString());
		int year2 = Integer.parseInt(obj.get("year2").toString());
		int bhf_code = Integer.parseInt(obj.get("bhf_code").toString());
		
		searchYear = billDAO.searchYear(year1, year2, bhf_code);
		

		
		JSONArray jArray = new JSONArray();
		
		for(int i = 0; i < searchYear.size(); i++){
			JSONObject json = new JSONObject();
			json.put("year", searchYear.get(i).get("year").toString());
			json.put("totalPrice", searchYear.get(i).get("totalPrice"));
			
			jArray.add(json);
		}

		yearSalesInfo = billDAO.settleSalesInfo(year1, year2, bhf_code);
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
	
	// Ŭ���̾�Ʈ�� ������ ������ �� ����Ǵ� �޼ҵ�
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		
		super.afterConnectionClosed(session, status);
		System.out.println(status);
		sessionList.remove(session);
		logger.info("{} ���� ����", session.getId());
	}
	
	

}

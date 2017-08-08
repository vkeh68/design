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

public class DaySalesSocket extends TextWebSocketHandler{

	private static Logger logger = LoggerFactory.getLogger(MainSocket.class);
	
	private List<WebSocketSession> sessionList = new ArrayList<WebSocketSession>();
	private List<HashMap> daySales;
	private List<HashMap> daySalesInfo;

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
		
		
		JSONParser parser = new JSONParser();
		
		JSONObject obj =null;
		obj = (JSONObject) parser.parse(message.getPayload());

		int bhf_code = Integer.parseInt(obj.get("bhf_code").toString());
		daySales = billDAO.daySales(bhf_code);
		
		//System.out.println(daySales.toString());
		
		JSONArray jArray = new JSONArray();
		
		for(int i = 0; i < daySales.size(); i++){
			JSONObject json = new JSONObject();
			json.put("bill_issu_de", daySales.get(i).get("bill_issu_de").toString());
			json.put("totalPrice", daySales.get(i).get("totalPrice"));
			
			jArray.add(json);
		}

		
		
		daySalesInfo = billDAO.daySalesSettleInfo(bhf_code);
		
		System.out.println(daySalesInfo.toString());
		JSONArray yearSalesInfoArray = new JSONArray();
		for(int i = 0; i < daySalesInfo.size(); i++){
			JSONObject json = new JSONObject();
			json.put("day", daySalesInfo.get(i).get("year").toString());
			json.put("totalPrice", daySalesInfo.get(i).get("totalPrice"));
			json.put("setle_mth_nm", daySalesInfo.get(i).get("setle_mth_nm").toString());
			yearSalesInfoArray.add(json);
		}
		

		int todaySales = billDAO.todaySales(bhf_code);
		int monthTotalSales = billDAO.monthTotalSales(bhf_code);
		
		
		JSONObject result = new JSONObject();
		result.put("daySales", jArray);
		result.put("daySalesInfo", yearSalesInfoArray);
		result.put("todaySales", todaySales);
		result.put("monthTotalSales", monthTotalSales);
		
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

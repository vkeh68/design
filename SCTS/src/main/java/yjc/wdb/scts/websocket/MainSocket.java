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

public class MainSocket extends TextWebSocketHandler{

	private static Logger logger = LoggerFactory.getLogger(MainSocket.class);

	private List<WebSocketSession> sessionList = new ArrayList<WebSocketSession>();
	private List<HashMap> list;

	@Inject
	private BillDAO billDAO;

	@Inject
	private CourseDAO courseDAO;

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

		JSONParser parser = new JSONParser();

		JSONObject obj =null;
		obj = (JSONObject) parser.parse(message.getPayload());

		int bhf_code = Integer.parseInt(obj.get("bhf_code").toString());
		list = billDAO.daySales(bhf_code);
		
	
		System.out.println(list.toString());
		JSONArray jArray = new JSONArray();
		for(int i = 0; i < list.size(); i++){
			JSONObject json = new JSONObject();
			json.put("bill_issu_de", list.get(i).get("bill_issu_de").toString());
			json.put("totalPrice", list.get(i).get("totalPrice").toString());

			jArray.add(json);
		}

		JSONObject result = new JSONObject();
		result.put("result", jArray);



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

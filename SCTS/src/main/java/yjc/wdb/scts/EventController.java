package yjc.wdb.scts;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import yjc.wdb.scts.bean.BBScttVO;
import yjc.wdb.scts.bean.BBSctt_WritingVO;
import yjc.wdb.scts.bean.Branch_officeVO;
import yjc.wdb.scts.bean.EventVO;
import yjc.wdb.scts.service.BBSService;

@Controller
public class EventController {
	
	@Inject
	private BBSService bbsService;
	
	// 캘린더 위 일정 뿌리기
		@RequestMapping(value="viewCalendar", method=RequestMethod.GET,
				produces = "text/plain; charset=UTF-8")
		public @ResponseBody String viewCalendar(int bhf_code) throws Exception{
			
			
			List<HashMap> list = bbsService.viewCalendar(bhf_code);
			
			JSONObject viewCalJson;
			JSONArray viewCalArray = new JSONArray();
			
			for(int i=0; i < list.size(); i++){
				
				viewCalJson = new JSONObject();
				
				viewCalJson.put("bbsctt_code", list.get(i).get("bbsctt_code"));
				viewCalJson.put("title", list.get(i).get("bbsctt_sj"));
				viewCalJson.put("bbsctt_cn", list.get(i).get("bbsctt_cn"));
				viewCalJson.put("start", list.get(i).get("event_begin_de").toString());
				viewCalJson.put("end", list.get(i).get("event_end_de").toString());
				
				viewCalArray.add(viewCalJson);
				
			}
			 
			JSONObject json = new JSONObject();
			json.put("result", viewCalArray);
		 
			
			return json.toString();
		}
		

		
		// 이벤트 등록
		@RequestMapping(value="insertEvent", method=RequestMethod.POST)
		//public ResponseEntity<String> insertEvent(EventVO eventVO, BBScttVO bbscttVO, BBSctt_WritingVO bbsctt_writingVO){
		public ResponseEntity<String> insertEvent(@RequestBody JSONObject json){
		
			ResponseEntity<String> entity = null;
		
			
			try {
				bbsService.insertEvent(json);
				
				
				entity = new ResponseEntity<String>("success", HttpStatus.OK);
				
			} catch (Exception e) {
				
				entity =  new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
				e.printStackTrace();
			}
			
			return entity;
			
			
		}
		

		// 이벤트 수정
		@RequestMapping(value="updateEvent", method=RequestMethod.GET)
		public ResponseEntity<String> updateEvent(EventVO eventVO, BBScttVO bbscttVO){
				
			ResponseEntity<String> entity = null;
					
			try {
					bbsService.updateEvent(eventVO, bbscttVO);						
						
					entity = new ResponseEntity<String>("success", HttpStatus.OK);
						
				} catch (Exception e) {
						
						entity =  new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
						e.printStackTrace();
				}
	 				
				return entity;
								
		}
		
		@RequestMapping(value="updateDropEvent", method=RequestMethod.GET)
		public ResponseEntity<String> updateDropEvent(EventVO eventVO){
				
			ResponseEntity<String> entity = null;
					
			try {
					bbsService.updateDropEvent(eventVO);					
						
					entity = new ResponseEntity<String>("success", HttpStatus.OK);
						
				} catch (Exception e) {
						
						entity =  new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
						e.printStackTrace();
				}
					
				return entity;
								
		}
		// 이벤트 삭제
		@RequestMapping(value="deleteEvent", method=RequestMethod.GET)
		public ResponseEntity<String> deleteEvent(int bbsctt_code){
				
			ResponseEntity<String> entity = null;
					
			try {
					bbsService.deleteEvent(bbsctt_code);				
						
					entity = new ResponseEntity<String>("success", HttpStatus.OK);
						
				} catch (Exception e) {
						
						entity =  new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
						e.printStackTrace();
				}
					
				return entity;
								
		}
		
		// list event
		
		@RequestMapping(value="listEvent", method=RequestMethod.GET,
				produces = "text/plain; charset=UTF-8")
		public @ResponseBody String listEvent(String date1, String date2) throws Exception{
			
			List<HashMap> list = bbsService.listEvent(date1, date2);
			
			JSONObject viewCalJson;
			JSONArray viewCalArray = new JSONArray();
			
			for(int i=0; i < list.size(); i++){
				
				viewCalJson = new JSONObject();
				
				viewCalJson.put("bbsctt_code", list.get(i).get("bbsctt_code"));
				viewCalJson.put("title", list.get(i).get("bbsctt_sj"));
				viewCalJson.put("bbsctt_cn", list.get(i).get("bbsctt_cn"));
				viewCalJson.put("start", list.get(i).get("event_begin_de").toString());
				viewCalJson.put("end", list.get(i).get("event_end_de").toString());
				
				viewCalArray.add(viewCalJson);
				
			}
			 
			JSONObject json = new JSONObject();
			json.put("result", viewCalArray);
			
			return json.toString();
		}
		
		
		@RequestMapping(value="eventOne", method=RequestMethod.GET
				,produces = "text/plain; charset=UTF-8")
		public @ResponseBody String eventOne(int code) throws Exception{
			
			List<HashMap> list = bbsService.eventOne(code);
			
			JSONObject viewCalJson;
			JSONArray viewCalArray = new JSONArray();
			
			for(int i=0; i < list.size(); i++){
				
				viewCalJson = new JSONObject();
				
				viewCalJson.put("bbsctt_code", list.get(i).get("bbsctt_code"));
				viewCalJson.put("title", list.get(i).get("bbsctt_sj"));
				viewCalJson.put("bbsctt_cn", list.get(i).get("bbsctt_cn"));
				viewCalJson.put("start", list.get(i).get("event_begin_de").toString());
				viewCalJson.put("end", list.get(i).get("event_end_de").toString());
				viewCalJson.put("bhf_code", list.get(i).get("bhf_code"));
				
				viewCalArray.add(viewCalJson);
				
			}
			 
			JSONObject json = new JSONObject();
			json.put("result", viewCalArray);
			
			return json.toString();
		}
		
		
		@RequestMapping(value="notification", method=RequestMethod.GET
				,produces = "text/plain; charset=UTF-8")
		public @ResponseBody String notification(int reciever) throws Exception{
			
			List<HashMap> list = bbsService.notification(reciever);
			int notiCnt = bbsService.notiCnt(reciever);
			
			JSONObject notiJson;
			JSONArray notiArray = new JSONArray();
			
			for(int i=0; i < list.size(); i++){
				
				notiJson = new JSONObject();
				
				notiJson.put("sender", list.get(i).get("sender"));
				notiJson.put("reciever", list.get(i).get("reciever"));
				notiJson.put("bbsctt_code", list.get(i).get("bbsctt_code"));
				notiJson.put("dateCha", list.get(i).get("dateCha"));
				notiJson.put("ntcn_code", list.get(i).get("ntcn_code"));
				notiJson.put("bbsctt_sj", list.get(i).get("bbsctt_sj"));
				
				notiArray.add(notiJson);
				
			}
			 
			JSONObject json = new JSONObject();
			json.put("result", notiArray);
			json.put("notiCnt", notiCnt);
			
			return json.toString();
		}
		
		
		
		@RequestMapping(value="notiEventDetail", method=RequestMethod.GET
				,produces = "text/plain; charset=UTF-8")
		public @ResponseBody String notiEventDetail(int nctn_code, int bbsctt_code, int reciever) throws Exception{
			
			List<HashMap> list = bbsService.notiEventDetail(nctn_code, bbsctt_code);
			
			JSONObject notiJson;
			JSONArray notiArray = new JSONArray();
			
			int notiCnt = bbsService.notiCnt(reciever);
			
			for(int i=0; i < list.size(); i++){
				
				notiJson = new JSONObject();
				
				notiJson.put("bbsctt_code", list.get(i).get("bbsctt_code").toString());
				notiJson.put("bbsctt_sj", list.get(i).get("bbsctt_sj"));
				notiJson.put("bbsctt_cn", list.get(i).get("bbsctt_cn"));
				notiJson.put("event_begin_de", list.get(i).get("event_begin_de").toString());
				notiJson.put("event_end_de", list.get(i).get("event_end_de").toString());
				
				notiArray.add(notiJson);
				
			}
			 
			JSONObject json = new JSONObject();
			json.put("result", notiArray);
			json.put("notiCnt", notiCnt);
			
			
			return json.toString();
		}
		
		
		@RequestMapping(value="allBranch_office", method=RequestMethod.GET
				,produces = "text/plain; charset=UTF-8")
		public @ResponseBody String allBranch_office() throws Exception{
			
			List<Branch_officeVO> list = bbsService.allBranch_office();
			
			JSONObject notiJson;
			JSONArray notiArray = new JSONArray();
			
			for(int i=0; i < list.size(); i++){
				
				notiJson = new JSONObject();
				
				notiJson.put("bhf_code", list.get(i).getBhf_code());
				notiArray.add(notiJson);
				
			}
			 
			JSONObject json = new JSONObject();
			json.put("result", notiArray);
		
			return json.toString();
		}

}

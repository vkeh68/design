package yjc.wdb.scts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import yjc.wdb.scts.bean.PageMaker;
import yjc.wdb.scts.bean.PageVO;
import yjc.wdb.scts.bean.ReplyVO;
import yjc.wdb.scts.service.ReplyService;

@RestController
public class ReplyController {
		
	@Inject
	private ReplyService replyService;
	
	/* 등록해보자. */
	@RequestMapping(value="replies", method=RequestMethod.POST)
	public ResponseEntity<String>register(@RequestBody ReplyVO vo){
		
		System.out.println("replies");
		ResponseEntity<String> entity = null;
		 /*오류 메세지 전송하고 400을 결과로 전송 */
		try {
			replyService.createReply(vo);
			entity = new ResponseEntity<String>("SUCCESS",HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	/* 리스트를 띄아보자 */
	@RequestMapping(value="all/{bno}", method=RequestMethod.GET)
	public ResponseEntity<List<ReplyVO>>list(
		/* URI 경로에서 원하는 데이터를 추출하는 용도 */
		@PathVariable("bno") Integer bno){
		System.out.println("all리스트");
		ResponseEntity<List<ReplyVO>> entity = null;
		
		/* 마찬가지로 예외 처리*/
		try {
			entity = new ResponseEntity<List<ReplyVO>>(
					replyService.listReply(bno), HttpStatus.OK); /* 메소드의 처리가 성공하면 Http~OK 헤더를 전송하고, 데이터를 같이 전송 */
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<List<ReplyVO>>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	/* 수정을 해보자 */
	@RequestMapping(value="update/{rno}",method = { RequestMethod.PUT, RequestMethod.PATCH})
	public ResponseEntity<String>update(
			@PathVariable("rno")Integer rno,
			@RequestBody ReplyVO vo){
		
		ResponseEntity<String> entity = null;
		try {
		vo.setRno(rno);
		replyService.updateReply(vo);
		entity = new ResponseEntity<String> ("SUCESS",HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(
					e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	/* 삭제를 해보자 */
	@RequestMapping(value="/delete/{rno}", method=RequestMethod.DELETE)
	public ResponseEntity<String> delete(
			@PathVariable("rno") Integer rno){
		
		System.out.println("딜리트다. ");
		ResponseEntity<String>entity = null;
		try {
			replyService.deleteReply(rno);
			entity = new ResponseEntity<String> ("SUCCESS", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(
					e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	/* 페이징 처리를 위한 클래스 두 개의 @PathVariable로 처리. */
	@RequestMapping(value="/cri/{bno}/{page}", method=RequestMethod.GET)
	public ResponseEntity<Map<String,Object>> listReply(
			@PathVariable("bno") Integer bno,
			@PathVariable("page")Integer page){
			System.out.println("page리스트 " + "bno는 : " + bno + "page는 : " + page);
		ResponseEntity<Map<String,Object>> entity = null;
		
		try {
			PageVO cri = new PageVO();
			cri.setPage(page);

			PageMaker pageMaker = new PageMaker();
			pageMaker.setCri(cri);

			Map<String, Object> map = new HashMap<String, Object>();

			List<ReplyVO> list = replyService.criReply(bno, cri);
			
			map.put("list", list);
			
			int replyCount = replyService.countReply(bno);
			pageMaker.setTotalCount(replyCount);
			
			map.put("pageMaker", pageMaker);
			
			entity = new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<Map<String, Object>>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	

}

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
	
	/* ����غ���. */
	@RequestMapping(value="replies", method=RequestMethod.POST)
	public ResponseEntity<String>register(@RequestBody ReplyVO vo){
		
		System.out.println("replies");
		ResponseEntity<String> entity = null;
		 /*���� �޼��� �����ϰ� 400�� ����� ���� */
		try {
			replyService.createReply(vo);
			entity = new ResponseEntity<String>("SUCCESS",HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	/* ����Ʈ�� ��ƺ��� */
	@RequestMapping(value="all/{bno}", method=RequestMethod.GET)
	public ResponseEntity<List<ReplyVO>>list(
		/* URI ��ο��� ���ϴ� �����͸� �����ϴ� �뵵 */
		@PathVariable("bno") Integer bno){
		System.out.println("all����Ʈ");
		ResponseEntity<List<ReplyVO>> entity = null;
		
		/* ���������� ���� ó��*/
		try {
			entity = new ResponseEntity<List<ReplyVO>>(
					replyService.listReply(bno), HttpStatus.OK); /* �޼ҵ��� ó���� �����ϸ� Http~OK ����� �����ϰ�, �����͸� ���� ���� */
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<List<ReplyVO>>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	/* ������ �غ��� */
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
	
	/* ������ �غ��� */
	@RequestMapping(value="/delete/{rno}", method=RequestMethod.DELETE)
	public ResponseEntity<String> delete(
			@PathVariable("rno") Integer rno){
		
		System.out.println("����Ʈ��. ");
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
	
	/* ����¡ ó���� ���� Ŭ���� �� ���� @PathVariable�� ó��. */
	@RequestMapping(value="/cri/{bno}/{page}", method=RequestMethod.GET)
	public ResponseEntity<Map<String,Object>> listReply(
			@PathVariable("bno") Integer bno,
			@PathVariable("page")Integer page){
			System.out.println("page����Ʈ " + "bno�� : " + bno + "page�� : " + page);
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

package yjc.wdb.scts.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Repository;

import yjc.wdb.scts.bean.BBScttVO;
import yjc.wdb.scts.bean.BBSctt_WritingVO;
import yjc.wdb.scts.bean.Branch_officeVO;
import yjc.wdb.scts.bean.EventVO;
import yjc.wdb.scts.dao.BBSDAO;

@Repository
public class BBSDAOImpl implements BBSDAO {
	
	private static final String NAMESPACE = "yjc.wdb.mapper.BBSMapper";

	@Inject
	private SqlSession sql;
	
	@Override
	public List<HashMap> viewCalendar(int bhf_code) throws Exception {
		
		return sql.selectList(NAMESPACE+".viewCalendar", bhf_code);
	}

	@Override
	public void insertBBSctt(JSONObject json) throws Exception {
		
		sql.insert(NAMESPACE+".bbscttInsert", json);
	}

	@Override
	public void insertEvent(JSONObject json) throws Exception {
		sql.insert(NAMESPACE+".eventInsert", json);
	}

	@Override
	public List<HashMap> eventOne(int code) throws Exception {
		
		return sql.selectList(NAMESPACE+".eventOne", code);
	}

	@Override
	public void updateEvent(EventVO eventVO) throws Exception {
		
		sql.selectList(NAMESPACE+".updateEvent", eventVO);
		
	}

	@Override
	public void updateBbsctt(BBScttVO bbscttVO) throws Exception {
		
		sql.selectList(NAMESPACE+".updateBbsctt", bbscttVO);
	}

	@Override
	public void deleteEvent(int bbsctt_code) throws Exception {
		sql.selectList(NAMESPACE+".deleteEvent", bbsctt_code);
	}

	@Override
	public void deleteBbsctt(int bbsctt_code) throws Exception {
		sql.selectList(NAMESPACE+".deleteBbsctt", bbsctt_code);
	}

	@Override
	public List<HashMap> listEvent(String date1, String date2) throws Exception {
		Map map = new HashMap();
		map.put("date1", date1);
		map.put("date2", date2);
		
		return sql.selectList(NAMESPACE+".listEvent", map);
	}

	@Override
	public void insertBbsctt_writing(JSONObject json) throws Exception {
		
		sql.insert(NAMESPACE+".bbsctt_writingInsert", json);
		
	}

	@Override
	public void deleteBbscttWriting(int bbsctt_code) throws Exception {
		sql.delete(NAMESPACE+".deleteBbscttWriting", bbsctt_code);
	}

	@Override
	public List<HashMap> eventNotification(int sender) throws Exception {
		// TODO Auto-generated method stub
		return sql.selectList(NAMESPACE+".eventNotification", sender);
	}

	@Override
	public void insertNoti(JSONObject json) throws Exception {
		
		sql.insert(NAMESPACE+".insertNoti", json);
	}

	@Override
	public List<HashMap> notification(int reciever) throws Exception {
		
		return sql.selectList(NAMESPACE+".notification", reciever);
	}

	@Override
	public int notiCnt(int reciever) throws Exception {
		
		return sql.selectOne(NAMESPACE+ ".notiCnt", reciever);
	}

	@Override
	public void deleteNoti(int bbsctt_code) throws Exception {
		sql.delete(NAMESPACE+".deleteNoti", bbsctt_code);
	}

	@Override
	public void updateNoti(int nctn_code) throws Exception {
		sql.update(NAMESPACE+".updateNoti", nctn_code);
	}

	@Override
	public List<Branch_officeVO> allBranch_office() throws Exception {
		
		return sql.selectList(NAMESPACE+".allBranch_office");
	}
	
	

}

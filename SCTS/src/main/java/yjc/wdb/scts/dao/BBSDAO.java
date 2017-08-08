package yjc.wdb.scts.dao;

import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONObject;

import yjc.wdb.scts.bean.BBScttVO;
import yjc.wdb.scts.bean.BBSctt_WritingVO;
import yjc.wdb.scts.bean.Branch_officeVO;
import yjc.wdb.scts.bean.EventVO;

public interface BBSDAO {
	
	
	public List<HashMap> viewCalendar(int bhf_code) throws Exception;
	public void insertBBSctt(JSONObject json) throws Exception;
	public void insertEvent(JSONObject json) throws Exception;
	public List<HashMap> eventOne(int code) throws Exception;
	public void updateEvent(EventVO eventVO) throws Exception;
	public void updateBbsctt(BBScttVO bbscttVO) throws Exception;
	public void deleteEvent(int bbsctt_code) throws Exception;
	public void deleteBbsctt(int bbsctt_code) throws Exception;
	public void deleteBbscttWriting(int bbsctt_code) throws Exception;
	public List<HashMap> listEvent(String date1, String date2) throws Exception;
	public void insertBbsctt_writing(JSONObject json) throws Exception;
	public List<HashMap> eventNotification(int sender) throws Exception;  
	public List<HashMap> notification(int reciever) throws Exception;
	public void insertNoti(JSONObject json) throws Exception;
	public int notiCnt(int reciever) throws Exception;
	public void deleteNoti(int bbsctt_code) throws Exception;
	public void updateNoti(int nctn_code) throws Exception;
	public List<Branch_officeVO> allBranch_office() throws Exception;
	
}

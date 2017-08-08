package yjc.wdb.scts.service;

import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONObject;

import yjc.wdb.scts.bean.BBScttVO;
import yjc.wdb.scts.bean.BBSctt_WritingVO;
import yjc.wdb.scts.bean.Branch_officeVO;
import yjc.wdb.scts.bean.EventVO;

public interface BBSService {
	
	public List<HashMap> viewCalendar(int bhf_code) throws Exception;
	public void insertEvent(JSONObject json) throws Exception;
	public List<HashMap> eventOne(int code) throws Exception;
	public void updateEvent(EventVO eventVO, BBScttVO bbscttVO) throws Exception;
	public void updateDropEvent(EventVO eventVO) throws Exception;
	public void deleteEvent(int bbsctt_code) throws Exception;
	public List<HashMap> listEvent(String date1, String date2) throws Exception;
	public JSONObject eventNotification(JSONObject json) throws Exception;
	public List<HashMap> notification(int reciever) throws Exception;
	public int notiCnt(int reciever) throws Exception;
	public void deleteNoti(int bbsctt_code) throws Exception;
	public void updateNoti(int nctn_code) throws Exception;
	public List<HashMap> notiEventDetail(int nctn_code, int code) throws Exception;
	public List<Branch_officeVO> allBranch_office() throws Exception;

}

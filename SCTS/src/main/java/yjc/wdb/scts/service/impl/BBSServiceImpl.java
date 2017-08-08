package yjc.wdb.scts.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import yjc.wdb.scts.bean.BBScttVO;
import yjc.wdb.scts.bean.BBSctt_WritingVO;
import yjc.wdb.scts.bean.Branch_officeVO;
import yjc.wdb.scts.bean.EventVO;
import yjc.wdb.scts.dao.BBSDAO;
import yjc.wdb.scts.service.BBSService;

@Service
public class BBSServiceImpl implements BBSService {

	@Inject
	private BBSDAO dao;

	@Override
	public List<HashMap> viewCalendar(int bhf_code) throws Exception {

		return dao.viewCalendar(bhf_code);
	}

	@Transactional
	@Override
	public void insertEvent(JSONObject json) throws Exception {

		dao.insertBBSctt(json);
		dao.insertEvent(json);
		dao.insertBbsctt_writing(json);
	}

	@Override
	public List<HashMap> eventOne(int code) throws Exception {
		// TODO Auto-generated method stub
		return dao.eventOne(code);
	}


	@Transactional
	@Override
	public void updateEvent(EventVO eventVO, BBScttVO bbscttVO) throws Exception {
		dao.updateEvent(eventVO);
		dao.updateBbsctt(bbscttVO);
	}

	@Transactional
	@Override
	public void deleteEvent(int bbsctt_code) throws Exception {


		dao.deleteEvent(bbsctt_code);
		dao.deleteBbsctt(bbsctt_code);
		dao.deleteBbscttWriting(bbsctt_code);
		dao.deleteNoti(bbsctt_code);


	}

	@Override
	public List<HashMap> listEvent(String date1, String date2) throws Exception {
		return dao.listEvent(date1, date2);
	}

	@Override
	public void updateDropEvent(EventVO eventVO) throws Exception {
		dao.updateEvent(eventVO);
	}


	@Transactional
	@Override
	public JSONObject eventNotification(JSONObject json) throws Exception {

		int sender = Integer.parseInt(json.get("sender").toString());
		int reciever = Integer.parseInt(json.get("reciever").toString());

		List<HashMap> list = dao.eventNotification(sender);
		List<HashMap> list2 = dao.notification(reciever);

		if(list2 != null){

			int count = 0;

			for(int i = 0; i < list.size(); i++){

				count = 0;

				for(int j = 0; j < list2.size(); j++){


					if(reciever == Integer.parseInt(list2.get(j).get("reciever").toString())){
						if(list.get(i).get("bbsctt_code").toString().equals(list2.get(j).get("bbsctt_code").toString())){


							System.out.println(list.get(i).get("bbsctt_code").toString()+" 같아요");
							count++;

						}
					}


				}

				if(count <= 0){

					JSONObject obj = new JSONObject();
					obj.put("sender", sender);
					obj.put("reciever", reciever);
					obj.put("bbsctt_code", list.get(i).get("bbsctt_code"));
					System.out.println(list.get(i).get("bbsctt_code").toString()+ " 같지않음");
					dao.insertNoti(obj);

				}


			}
		}

		List<HashMap> list3 = dao.notification(reciever);


		JSONArray jArray = new JSONArray();

		for(int i = 0; i < list3.size(); i++){
			JSONObject json2 = new JSONObject();
			json2.put("sender", list3.get(i).get("sender"));
			json2.put("reciever", list3.get(i).get("reciever"));
			json2.put("bbsctt_code", list3.get(i).get("bbsctt_code"));
			json2.put("dateCha", list3.get(i).get("dateCha"));
			json2.put("ntcn_code", list3.get(i).get("ntcn_code"));
			json2.put("bbsctt_sj", list3.get(i).get("bbsctt_sj"));
			jArray.add(json2);
		}

		int notiCnt = dao.notiCnt(reciever);

		JSONObject result = new JSONObject();
		result.put("eventNotification", jArray);
		result.put("notiCnt", notiCnt);


		return result; 
	}

	@Override
	public List<HashMap> notification(int reciever) throws Exception {
		return dao.notification(reciever);
	}

	@Override
	public int notiCnt(int reciever) throws Exception {

		return dao.notiCnt(reciever);
	}

	@Override
	public void deleteNoti(int bbsctt_code) throws Exception {
		dao.deleteNoti(bbsctt_code);
	}

	@Override
	public void updateNoti(int nctn_code) throws Exception {
		dao.updateNoti(nctn_code);
	}

	@Transactional
	@Override
	public List<HashMap> notiEventDetail(int nctn_code, int code) throws Exception {
		dao.updateNoti(nctn_code);
		return dao.eventOne(code);
	}

	@Override
	public List<Branch_officeVO> allBranch_office() throws Exception {
		return dao.allBranch_office();
	}





}

package yjc.wdb.scts.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import yjc.wdb.scts.bean.TileVO;
import yjc.wdb.scts.dao.CourseDAO;
import yjc.wdb.scts.service.CourseService;

@Service
public class CourseServiceImpl implements CourseService {

	@Inject
	private CourseDAO dao;

	@Override
	public int selectTodayVisitCnt(int bhf_code) throws Exception {
		// TODO Auto-generated method stub
		return dao.selectTodayVisitCnt(bhf_code);
	}

	@Override
	public void insertCourse(HashMap<String, String> vo) throws Exception {
		// TODO Auto-generated method stub
		dao.insertCourse(vo);
	}

	@Override
	public HashMap<String, String> tileProbability(TileVO vo) throws Exception {
		// TODO Auto-generated method stub
		return dao.tileProbability(vo);
	}

	@Override
	public List<HashMap<String, String>> tileUserinfo(TileVO vo) throws Exception {
		// TODO Auto-generated method stub
		return dao.tileUserinfo(vo);
	}

	@Override
	public void updateStayTime(HashMap<String, String> vo) throws Exception {
		// TODO Auto-generated method stub
		dao.updateStayTime(vo);
	}

	@Override
	public int monthAvgVisitor(int bhf_code) throws Exception {
		
		return dao.monthAvgVisitor(bhf_code);
	}

	@Override
	public List<HashMap> tileGender(int day) throws Exception {
		// TODO Auto-generated method stub
		return dao.tileGender(day);
	}

	@Override
	public List<HashMap> tileAge(int day) throws Exception {
		// TODO Auto-generated method stub
		return dao.tileAge(day);
	}

	@Override
	public List<HashMap> oneTileAvgTime(int day, int drw_code, int tile_crdnt_x, int tile_crdnt_y) throws Exception {
		// TODO Auto-generated method stub
		return dao.oneTileAvgTime(day, drw_code, tile_crdnt_x, tile_crdnt_y);
	}

	@Override
	public List<HashMap> oneTileGender(int day, int drw_code, int tile_crdnt_x, int tile_crdnt_y) throws Exception {
	
		return dao.oneTileGender(day, drw_code, tile_crdnt_x, tile_crdnt_y);
	}

	@Override
	public List<HashMap> oneTileAge(int day, int drw_code, int tile_crdnt_x, int tile_crdnt_y) throws Exception {
		// TODO Auto-generated method stub
		return dao.oneTileAge(day, drw_code, tile_crdnt_x, tile_crdnt_y);
	}

	@Override
	public List<HashMap> tileTotal(int day, int drw_code, int tile_crdnt_x, int tile_crdnt_y) throws Exception {
		// TODO Auto-generated method stub
		return dao.tileTotal(day, drw_code, tile_crdnt_x, tile_crdnt_y);
	}

	@Override
	public List<HashMap<String, String>> testTileColor(int bhf_code) throws Exception {
		// TODO Auto-generated method stub
		return dao.testTileColor(bhf_code);
	}

	@Override
	public List<HashMap> categoryTypeMap(int bhf_code) throws Exception {
		// TODO Auto-generated method stub
		return dao.categoryTypeMap(bhf_code);
	}

	@Override
	public List<HashMap> zoneTypeMap(int bhf_code) throws Exception {
		// TODO Auto-generated method stub
		return dao.zoneTypeMap(bhf_code);
	}

}

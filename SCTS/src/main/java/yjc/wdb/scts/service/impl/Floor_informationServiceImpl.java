package yjc.wdb.scts.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import yjc.wdb.scts.bean.Floor_informationVO;
import yjc.wdb.scts.bean.TileVO;
import yjc.wdb.scts.dao.Floor_informationDAO;
import yjc.wdb.scts.dao.TileDAO;
import yjc.wdb.scts.service.Floor_informationService;

@Service
public class Floor_informationServiceImpl implements Floor_informationService {
	
	@Inject
	private Floor_informationDAO dao;
	
	@Inject
	private TileDAO tiledao;

	@Override
	@Transactional
	public void register_shop(String drw_flpth, Floor_informationVO vo) throws Exception {
		// TODO Auto-generated method stub
		dao.insertDrawing(drw_flpth);
		
		int drw_code = dao.selectLast_insert_id();
		
		dao.insertFloor_information(vo);
		
		List<TileVO> tileList = new ArrayList<TileVO>();
		
		int floor = dao.selectCountStory(vo.getBhf_code());
		
		//String firstName = (char) ('A' + floor);
		char first = (char) ('A' + (floor-1)) ;
		
		// 타일 자동으로 생성해서 값 넣기
		for(int i=0; i<vo.getSize_x(); i ++) {
			for(int j=0; j<vo.getSize_y(); j++) {
				TileVO tile = new TileVO();
				tile.setTile_nm(first + "_" + i + "_" + j);
				tile.setDrw_code(drw_code);
				tile.setTile_crdnt_x(i);
				tile.setTile_crdnt_y(j);
		
				// 타일 한개씩 넣을때 쓰던 거
				//tiledao.insertTile(tile);
				
				tileList.add(tile);
			}
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tileList", tileList);
		tiledao.insertDrawingTile(map);
		
	}

	@Override
	public List<HashMap<String, String>> selectDrawingList(int bhf_code) throws Exception {
		// TODO Auto-generated method stub
		return dao.selectDrawingList(bhf_code);
	}

	@Override
	public int selectCountStory(int bhf_code) throws Exception {
		// TODO Auto-generated method stub
		return dao.selectCountStory(bhf_code);
	}

	@Override
	public HashMap<String, String> selectDrawingOne(int bhf_code, int floor) throws Exception {
		// TODO Auto-generated method stub
		return dao.selectDrawingOne(bhf_code, floor);
	}

	@Override
	public List<HashMap<String, String>> selectTileCategoryList(int drw_code) throws Exception {
		// TODO Auto-generated method stub
		return dao.selectTileCategoryList(drw_code);
	}

}

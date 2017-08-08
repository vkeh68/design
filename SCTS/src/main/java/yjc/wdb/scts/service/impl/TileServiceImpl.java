package yjc.wdb.scts.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import yjc.wdb.scts.bean.TileVO;
import yjc.wdb.scts.dao.BeaconDAO;
import yjc.wdb.scts.dao.TileDAO;
import yjc.wdb.scts.service.TileService;

@Service
public class TileServiceImpl implements TileService {

	@Inject
	private TileDAO tiledao;
	
	@Inject
	private BeaconDAO beacondao;
	
	@Override
	public List<HashMap<String, String>> selectTileList() throws Exception {
		// TODO Auto-generated method stub
		return tiledao.selectTileList();
	}

	@Override
	public void insertTile(TileVO vo) throws Exception {
		// TODO Auto-generated method stub
		tiledao.insertTile(vo);
	}

	@Override
	public HashMap<String, String> selectTile_LocationOne(TileVO vo) throws Exception {
		// TODO Auto-generated method stub
		return tiledao.selectTile_LocationOne(vo);
	}

	@Override
	@Transactional
	public void updateTileBeaconSet(HashMap<String, String> vo) throws Exception {
		// TODO Auto-generated method stub
		tiledao.updateTileBeaconSet(vo);
		
		vo.remove("tile_code");
		vo.put("beacon_sttus", "USE");
		
		beacondao.updateBeaconSttus(vo);
	}

	@Override
	public List<HashMap<String, String>> selectTileListUp(Map map) throws Exception {
		// TODO Auto-generated method stub
		return tiledao.selectTileListUp(map);
	}

}

package yjc.wdb.scts.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import yjc.wdb.scts.bean.TileVO;

public interface TileService {
	public List<HashMap<String, String>> selectTileList() throws Exception; // 이전에 쓰던거
	public List<HashMap<String, String>> selectTileListUp(Map map) throws Exception; // 업글 버전
	public void insertTile(TileVO vo) throws Exception;
	public HashMap<String, String> selectTile_LocationOne(TileVO vo) throws Exception;
	public void updateTileBeaconSet(HashMap<String, String> vo) throws Exception;

}

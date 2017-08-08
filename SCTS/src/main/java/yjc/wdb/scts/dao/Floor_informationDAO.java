package yjc.wdb.scts.dao;

import java.util.HashMap;
import java.util.List;

import yjc.wdb.scts.bean.Floor_informationVO;

public interface Floor_informationDAO {
	public void insertDrawing(String drw_flpth) throws Exception;
	public void insertFloor_information(Floor_informationVO vo) throws Exception;
	public List<HashMap<String, String>> selectDrawingList(int bhf_code) throws Exception;
	public int selectCountStory(int bhf_code) throws Exception;
	public HashMap<String, String> selectDrawingOne(int bhf_code, int floor) throws Exception;
	public int selectLast_insert_id() throws Exception;
	public List<HashMap<String, String>> selectTileCategoryList(int drw_code) throws Exception;
}

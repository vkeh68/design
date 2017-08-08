package yjc.wdb.scts.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Repository;

import yjc.wdb.scts.bean.TileVO;
import yjc.wdb.scts.dao.CourseDAO;

@Repository
public class CourseDAOImpl implements CourseDAO {
	
	private static final String NAMESPACE = "yjc.wdb.mapper.CourseMapper";

	@Inject
	private SqlSession sqlSession;
	
	@Override
	public int selectTodayVisitCnt(int bhf_code) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(NAMESPACE + ".selectTodayVisitCnt", bhf_code);
	}

	@Override
	public void insertCourse(HashMap<String, String> vo) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.insert(NAMESPACE + ".insertCourse", vo);
	}

	@Override
	public HashMap<String, String> tileProbability(TileVO vo) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(NAMESPACE + ".tileProbability", vo);
	}

	@Override
	public List<HashMap<String, String>> tileUserinfo(TileVO vo) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(NAMESPACE + ".tileUserinfo", vo);
	}

	@Override
	public void updateStayTime(HashMap<String, String> vo) throws Exception {
		
		sqlSession.update(NAMESPACE + ".updateStayTime", vo);
	}

	@Override
	public int monthAvgVisitor(int bhf_code) throws Exception {
		
		return sqlSession.selectOne(NAMESPACE+".monthAvgVisitor", bhf_code);
	}

	@Override
	public List<HashMap> tileGender(int day) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(NAMESPACE+".tileGender", day);
	}

	@Override
	public List<HashMap> tileAge(int day) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(NAMESPACE+".tileAge", day);
	}

	@Override
	public List<HashMap> oneTileAvgTime(int day, int drw_code, int tile_crdnt_x, int tile_crdnt_y) throws Exception {
		
		Map map = new HashMap();
		map.put("day", day);
		map.put("drw_code", drw_code);
		map.put("tile_crdnt_x", tile_crdnt_x);
		map.put("tile_crdnt_y", tile_crdnt_y);
		
		return sqlSession.selectList(NAMESPACE+".oneTileAvgTime", map);
	}

	@Override
	public List<HashMap> oneTileGender(int day, int drw_code, int tile_crdnt_x, int tile_crdnt_y) throws Exception {
		
		Map map = new HashMap();
		map.put("day", day);
		map.put("drw_code", drw_code);
		map.put("tile_crdnt_x", tile_crdnt_x);
		map.put("tile_crdnt_y", tile_crdnt_y);
		
		return sqlSession.selectList(NAMESPACE+".oneTileGender", map);
	}

	@Override
	public List<HashMap> oneTileAge(int day, int drw_code, int tile_crdnt_x, int tile_crdnt_y) throws Exception {
		
		Map map = new HashMap();
		map.put("day", day);
		map.put("drw_code", drw_code);
		map.put("tile_crdnt_x", tile_crdnt_x);
		map.put("tile_crdnt_y", tile_crdnt_y);
		
		return sqlSession.selectList(NAMESPACE+".oneTileAge", map);
	}

	@Override
	public List<HashMap> tileTotal(int day, int drw_code, int tile_crdnt_x, int tile_crdnt_y) throws Exception {
		Map map = new HashMap();
		map.put("day", day);
		map.put("drw_code", drw_code);
		map.put("tile_crdnt_x", tile_crdnt_x);
		map.put("tile_crdnt_y", tile_crdnt_y);
		
		return sqlSession.selectList(NAMESPACE+".tileTotal", map);
	}

	@Override
	public int tileTodayVisitCnt(int drw_code, int tile_crdnt_x, int tile_crdnt_y) throws Exception {
		Map map = new HashMap();
	
		map.put("drw_code", drw_code);
		map.put("tile_crdnt_x", tile_crdnt_x);
		map.put("tile_crdnt_y", tile_crdnt_y);
		return sqlSession.selectOne(NAMESPACE+".tileTodayVisitCnt", map);
	}

	@Override
	public List<HashMap<String, String>> testTileColor(int bhf_code) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(NAMESPACE + ".testTileColor", bhf_code);
	}

	@Override
	public List<HashMap> categoryTypeMap(int bhf_code) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(NAMESPACE + ".categoryTypeMap", bhf_code);
	}

	@Override
	public List<HashMap> zoneTypeMap(int bhf_code) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(NAMESPACE + ".zoneTypeMap", bhf_code);
	}
	
	

}

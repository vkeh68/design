package yjc.wdb.scts.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import yjc.wdb.scts.bean.TileVO;
import yjc.wdb.scts.dao.TileDAO;

@Repository
public class TileDAOImpl implements TileDAO {
	
	private static final String NAMESPACE = "yjc.wdb.mapper.TileMapper";

	@Inject
	private SqlSession sqlSession;
	
	@Override
	public List<HashMap<String, String>> selectTileList() throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(NAMESPACE + ".selectTileList");
	}

	@Override
	public List<HashMap<String, String>> selectTileListUp(Map map) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(NAMESPACE + ".selectTileListUp", map);
	}
	
	@Override
	public void insertTile(TileVO vo) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.insert(NAMESPACE + ".insertTile", vo);
	}

	@Override
	public HashMap<String, String> selectTile_LocationOne(TileVO vo) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(NAMESPACE + ".selectTile_LocationOne", vo);
	}

	@Override
	public void insertDrawingTile(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.insert(NAMESPACE + ".insertDrawingTile", map);
	}

	@Override
	public void updateTileBeaconSet(HashMap<String, String> vo) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.update(NAMESPACE + ".updateTileBeaconSet", vo);
	}

	
}

package yjc.wdb.scts.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import yjc.wdb.scts.dao.Settlement_methodDAO;

@Repository
public class Settlement_methodDAOImpl implements Settlement_methodDAO {
	
	private static final String NAMESPACE = "yjc.wdb.mapper.Settlement_methodMapper";

	@Inject
	private SqlSession sqlSession;

	@Override
	public void insertSettlement_method(String setle_mth_nm) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.insert(NAMESPACE + ".insertSettlement_method", setle_mth_nm);
	}

	@Override
	public void insertSettlement_infomation(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.insert(NAMESPACE + ".insertSettlement_infomation", map);
	}

	@Override
	public List<HashMap> getSettlement() throws Exception {
		 
		return sqlSession.selectList(NAMESPACE+".getSettlement");
	}

}

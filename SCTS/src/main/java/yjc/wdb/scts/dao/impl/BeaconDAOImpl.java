package yjc.wdb.scts.dao.impl;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import yjc.wdb.scts.bean.BeaconVO;
import yjc.wdb.scts.dao.BeaconDAO;

@Repository
public class BeaconDAOImpl implements BeaconDAO {
	
	private static final String NAMESPACE = "yjc.wdb.mapper.BeaconMapper";
	
	@Inject
	private SqlSession sql;
	
	@Override
	public void updateBeaconSttus(HashMap<String, String> vo) throws Exception {
		// TODO Auto-generated method stub
		sql.update(NAMESPACE + ".updateBeaconSttus", vo);
	}

	@Override
	public List<BeaconVO> selectSetBeaconList(int bhf_code) throws Exception {
		// TODO Auto-generated method stub
		return sql.selectList(NAMESPACE + ".selectSetBeaconList", bhf_code);
	}

	@Override
	public List<BeaconVO> selectAllBeaconList(int bhf_code) throws Exception {
		// TODO Auto-generated method stub
		return sql.selectList(NAMESPACE + ".selectAllBeaconList", bhf_code);
	}

	@Override
	public int insertBeacon(BeaconVO vo) throws Exception {
		// TODO Auto-generated method stub
		return sql.insert(NAMESPACE+ ".insertBeacon", vo);
	}

}

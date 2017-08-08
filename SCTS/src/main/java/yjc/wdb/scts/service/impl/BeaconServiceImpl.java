package yjc.wdb.scts.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import yjc.wdb.scts.bean.BeaconVO;
import yjc.wdb.scts.dao.BeaconDAO;
import yjc.wdb.scts.service.BeaconService;

@Service
public class BeaconServiceImpl implements BeaconService {
	
	@Inject
	private BeaconDAO dao;
	

	@Override
	public List<BeaconVO> selectSetBeaconList(int bhf_code) throws Exception {
		// TODO Auto-generated method stub
		return dao.selectSetBeaconList(bhf_code);
	}

	@Override
	public List<BeaconVO> selectAllBeaconList(int bhf_code) throws Exception {
		// TODO Auto-generated method stub
		return dao.selectAllBeaconList(bhf_code);
	}

	@Override
	public int insertBeacon(BeaconVO vo) throws Exception {
		// TODO Auto-generated method stub
		return dao.insertBeacon(vo);
	}

}

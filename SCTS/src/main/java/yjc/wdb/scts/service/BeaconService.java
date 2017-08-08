package yjc.wdb.scts.service;

import java.util.List;

import yjc.wdb.scts.bean.BeaconVO;

public interface BeaconService {
	public List<BeaconVO> selectSetBeaconList(int bhf_code) throws Exception;
	public List<BeaconVO> selectAllBeaconList(int bhf_code) throws Exception;
	public int insertBeacon(BeaconVO vo)throws Exception;
}

package yjc.wdb.scts.dao;

import java.util.HashMap;
import java.util.List;

import yjc.wdb.scts.bean.BeaconVO;

public interface BeaconDAO {
	public void updateBeaconSttus(HashMap<String, String> vo) throws Exception;
	public List<BeaconVO> selectSetBeaconList(int bhf_code) throws Exception;
	public List<BeaconVO> selectAllBeaconList(int bhf_code) throws Exception;
	public int insertBeacon(BeaconVO vo)throws Exception;
}

package yjc.wdb.scts.dao;

import java.util.HashMap;
import java.util.List;

import yjc.wdb.scts.bean.Branch_officeVO;
import yjc.wdb.scts.bean.CouponVO;

public interface Branch_officeDAO {
	public List<Branch_officeVO> searchBranchList(String bhf_nm) throws Exception;
	public List<HashMap<String, String>> selectBranchNameList() throws Exception;
	public List<Branch_officeVO> selectBranchOffice() throws Exception;
	public List<Branch_officeVO> selectGrade() throws Exception;
	public Branch_officeVO selectTotalSale() throws Exception;
	public int selectBranchCode(HashMap<String, String> vo) throws Exception;
}

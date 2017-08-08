package yjc.wdb.scts.service;

import java.util.HashMap;
import java.util.List;

import yjc.wdb.scts.bean.Branch_officeVO;

public interface Branch_officeService {
	public List<HashMap<String, String>> selectBranchNameList() throws Exception;
	public List<Branch_officeVO> searchBranchList(String bhf_nm) throws Exception;
	public List<Branch_officeVO> selectBranchOffice() throws Exception;
	public List<Branch_officeVO> selectGrade() throws Exception;
	public Branch_officeVO selectTotalSale() throws Exception;
	public int selectBranchCode(HashMap<String, String> vo) throws Exception;
}

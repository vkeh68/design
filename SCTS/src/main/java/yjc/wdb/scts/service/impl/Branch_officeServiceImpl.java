package yjc.wdb.scts.service.impl;


import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import yjc.wdb.scts.bean.Branch_officeVO;
import yjc.wdb.scts.dao.Branch_officeDAO;
import yjc.wdb.scts.service.Branch_officeService;

@Service
public class Branch_officeServiceImpl implements Branch_officeService {
	
	@Inject
	private Branch_officeDAO dao;

	@Override
	public List<HashMap<String, String>> selectBranchNameList() throws Exception {
		// TODO Auto-generated method stub
		return dao.selectBranchNameList();
	}

	@Override
	public List<Branch_officeVO> searchBranchList(String bhf_nm) throws Exception {
		// TODO Auto-generated method stub
		return dao.searchBranchList(bhf_nm);
	}

	@Override
	public List<Branch_officeVO> selectBranchOffice() throws Exception {
		// TODO Auto-generated method stub
		return dao.selectBranchOffice();
	}

	@Override
	public List<Branch_officeVO> selectGrade() throws Exception {
		// TODO Auto-generated method stub
		return dao.selectGrade();
	}

	@Override
	public Branch_officeVO selectTotalSale() throws Exception {
		// TODO Auto-generated method stub
		return dao.selectTotalSale();
	}

	@Override
	public int selectBranchCode(HashMap<String, String> vo) throws Exception {
		// TODO Auto-generated method stub
		return dao.selectBranchCode(vo);
	}

/*	@Override
	public List<Branch_officeVO> monthlyTotalSale() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}*/
}

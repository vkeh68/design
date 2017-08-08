package yjc.wdb.scts.dao.impl;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import yjc.wdb.scts.bean.Branch_officeVO;
import yjc.wdb.scts.dao.Branch_officeDAO;

@Repository
public class Branch_officeDAOImpl implements Branch_officeDAO {
	
	private static final String NAMESPACE = "yjc.wdb.mapper.Branch_officeMapper";
	
	@Inject
	private SqlSession sql;

	@Override
	public List<HashMap<String, String>> selectBranchNameList() throws Exception {
		// TODO Auto-generated method stub
		return sql.selectList(NAMESPACE + ".selectBranchNameList");
	}

	@Override
	public List<Branch_officeVO> searchBranchList(String bhf_nm) throws Exception {
		// TODO Auto-generated method stub
		return sql.selectList(NAMESPACE+".likeBranchInfoList", bhf_nm);
	}

	@Override
	public List<Branch_officeVO> selectBranchOffice() throws Exception {
		// TODO Auto-generated method stub
		return sql.selectList(NAMESPACE + ".selectBranchOffice");
	}

	@Override
	public List<Branch_officeVO> selectGrade() throws Exception {
		// TODO Auto-generated method stub
		return sql.selectList(NAMESPACE+ ".gradeBranchList");
	}

	@Override
	public Branch_officeVO selectTotalSale() throws Exception {
		// TODO Auto-generated method stub
		return sql.selectOne(NAMESPACE+".branchTotalsale");
	}

	@Override
	public int selectBranchCode(HashMap<String, String> vo) throws Exception {
		// TODO Auto-generated method stub
		return sql.selectOne(NAMESPACE + ".selectBranchCode", vo);
	}
}

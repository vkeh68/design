package yjc.wdb.scts.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import yjc.wdb.scts.bean.PageVO;
import yjc.wdb.scts.bean.ReplyVO;
import yjc.wdb.scts.dao.ReplyDAO;

@Repository
public class ReplyDAOImpl implements ReplyDAO {
	private static String namespace = "yjc.wdb.mapper.ReplyMapper";
	
	@Inject
	private SqlSession sql;
	
	
	@Override
	public List<ReplyVO> listReply(Integer bno) throws Exception {
		return sql.selectList(namespace+".listReply", bno);
	}

	@Override
	public void createReply(ReplyVO vo) throws Exception {
		sql.insert(namespace+".insertReply", vo);
	}

	@Override
	public void updateReply(ReplyVO vo) throws Exception {
		sql.update(namespace+".updateReply", vo);
	}

	@Override
	public void deleteReply(Integer rno) throws Exception {
		sql.delete(namespace+".deleteReply", rno);
	}

	@Override
	public List<ReplyVO> criReply(Integer bno, PageVO cri) throws Exception {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		
		paramMap.put("bno", bno);
		paramMap.put("cri", cri);
		
		return sql.selectList(namespace+".criReply", paramMap);
	}

	@Override
	public int countReply(Integer bno) throws Exception {
		// TODO Auto-generated method stub
		return sql.selectOne(namespace+".countReply",bno);
	}

}


package yjc.wdb.scts.dao.impl;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import yjc.wdb.scts.bean.PageVO;
import yjc.wdb.scts.bean.HelpVO;
import yjc.wdb.scts.dao.HelpDAO;

@Repository
public class HelpDAOImpl implements HelpDAO {

	@Inject
	private SqlSession sql;

	private static String namespace = "yjc.wdb.mapper.HelpMapper";
	
	@Override
	public void createHelp(HelpVO vo) throws Exception {
		sql.insert(namespace +".insertHelp", vo);
	}

	@Override
	public void updateHelp(HelpVO vo) throws Exception {
		sql.update(namespace +".updateHelp", vo);
	}

	@Override
	public void deleteHelp(Integer bbsctt_code) throws Exception {
		sql.delete(namespace +".deleteHelp", bbsctt_code);
	}

	@Override
	public HelpVO readHelp(Integer bbsctt_code) throws Exception {
		return sql.selectOne(namespace+".readHelp", bbsctt_code);
	}

	@Override
	public List<HelpVO> listSearch(PageVO cri) throws Exception {
		return sql.selectList(namespace + ".listSearch", cri);
	}

	@Override
	public int countSearch(PageVO cri) throws Exception {
		return sql.selectOne(namespace + ".countSearch", cri);
	}

	@Override
	public void createHelp2(HelpVO vo) throws Exception {
		sql.insert(namespace+".insertHelp2",vo);
	}

	@Override
	public void updateHelp2(HelpVO vo) throws Exception {
		sql.update(namespace+".updateHelp2",vo);
	}

	@Override
	public int maxHelp() throws Exception {
		return sql.selectOne(namespace+".maxCode");
	}

}

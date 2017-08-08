package yjc.wdb.scts.dao.impl;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import yjc.wdb.scts.bean.GoodsVO;
import yjc.wdb.scts.bean.PageVO;
import yjc.wdb.scts.dao.GoodsDAO;

@Repository
public class GoodsDAOImpl implements GoodsDAO {
	
	private static final String NAMESPACE = "yjc.wdb.mapper.GoodsMapper";

	@Inject
	private SqlSession sqlSession;

	@Override
	public void insertGoods(GoodsVO vo) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.insert(NAMESPACE + ".insertGoods", vo);
	}

	@Override
	public List<GoodsVO> selectGoodsList() throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(NAMESPACE + ".selectGoodsList");
	}

	@Override
	public List<GoodsVO> searchGoodsList(String goodsName) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(NAMESPACE+".likeGoodsList", goodsName);
	}

	@Override
	public GoodsVO selectGoodsOne(int goods_code) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(NAMESPACE + ".selectGoodsOne", goods_code);
	}

	@Override
	public void deleteStock(int goods_code) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.delete(NAMESPACE + ".deleteStock", goods_code);
	}

	@Override
	public List<GoodsVO> selectPageList(PageVO cri) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(NAMESPACE+".selectPageGoods", cri);
	}

	@Override
	public int countSearch(PageVO cri) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(NAMESPACE+".countSearch", cri);
	}

	@Override
	public List<GoodsVO> selectAdNotGoodsList(int bhf_code) throws Exception {
		
		return sqlSession.selectList(NAMESPACE+".selectAdNotGoodsList", bhf_code);
	}

}

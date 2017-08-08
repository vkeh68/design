package yjc.wdb.scts.dao.impl;

import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import yjc.wdb.scts.bean.UserVO;
import yjc.wdb.scts.dao.UserDAO;

@Repository
public class UserDAOImpl implements UserDAO {

private static String namespace ="yjc.wdb.mapper.UserMapper";
	
	@Inject
	private SqlSession sqlSession;
	
	// 로그인
	@Override
	public int loginUser(UserVO user) throws Exception {
	
		return sqlSession.selectOne(namespace+".loginUser", user);
	}

	// 회원가입
	@Override
	public void insertUser(UserVO user) throws Exception {
		sqlSession.insert(namespace+".insertUser",user);
	}

	@Override
	public int checkUser(String id) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(namespace+".checkUser", id);
	}
	

	@Override
	public Map<String, String> knowUserBranch(String user_id) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(namespace+".knowUserBranch", user_id);
	}

}

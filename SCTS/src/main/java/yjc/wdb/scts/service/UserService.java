package yjc.wdb.scts.service;

import java.util.Map;

import yjc.wdb.scts.bean.UserVO;

public interface UserService {
	
	public int loginUser(UserVO user) throws Exception;
	public void insertUser(UserVO user) throws Exception;
	public int checkUser(String id) throws Exception;
	public Map<String, String> knowUserBranch(String user_id) throws Exception;
}

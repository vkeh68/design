package yjc.wdb.scts.service;

import java.util.List;

import yjc.wdb.scts.bean.PageVO;
import yjc.wdb.scts.bean.ReplyVO;

public interface ReplyService {
	public List<ReplyVO>listReply(Integer bno) throws Exception;
	public void createReply(ReplyVO vo)throws Exception;
	public void updateReply(ReplyVO vo)throws Exception;
	public void deleteReply(Integer rno)throws Exception;
	public List<ReplyVO>criReply(Integer bno, PageVO cri)throws Exception;
	public int countReply(Integer bno)throws Exception;
}

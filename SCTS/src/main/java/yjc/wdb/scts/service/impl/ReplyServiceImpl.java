package yjc.wdb.scts.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import yjc.wdb.scts.bean.PageVO;
import yjc.wdb.scts.bean.ReplyVO;
import yjc.wdb.scts.dao.ReplyDAO;
import yjc.wdb.scts.service.ReplyService;

@Service
public class ReplyServiceImpl implements ReplyService {
	@Inject
	private ReplyDAO dao;
	
	@Override
	public List<ReplyVO> listReply(Integer bno) throws Exception {
		return dao.listReply(bno);
	}

	@Override
	public void createReply(ReplyVO vo) throws Exception {
		dao.createReply(vo);
	}

	@Override
	public void updateReply(ReplyVO vo) throws Exception {
		dao.updateReply(vo);
	}

	@Override
	public void deleteReply(Integer rno) throws Exception {
		dao.deleteReply(rno);
	}

	@Override
	public List<ReplyVO> criReply(Integer bno, PageVO cri) throws Exception {
		return dao.criReply(bno, cri);
	}

	@Override
	public int countReply(Integer bno) throws Exception {
		return dao.countReply(bno);
	}

}

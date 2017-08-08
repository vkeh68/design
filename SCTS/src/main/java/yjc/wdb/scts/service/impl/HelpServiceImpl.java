package yjc.wdb.scts.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import yjc.wdb.scts.bean.PageVO;
import yjc.wdb.scts.bean.HelpVO;
import yjc.wdb.scts.dao.HelpDAO;
import yjc.wdb.scts.service.HelpService;

@Service
public class HelpServiceImpl implements HelpService {
	
	@Inject
	private HelpDAO dao;
	
	@Override
	public void createHelp(HelpVO vo) throws Exception {
		dao.createHelp(vo);
	}

	@Override
	public void updateHelp(HelpVO vo) throws Exception {
		dao.updateHelp(vo);
	}

	@Override
	public void deleteHelp(Integer bbsctt_code) throws Exception {
		dao.deleteHelp(bbsctt_code);
	}

	@Override
	public HelpVO readHelp(Integer bbsctt_code) throws Exception {
		return dao.readHelp(bbsctt_code);
	}

	@Override
	public List<HelpVO> listSearch(PageVO cri) throws Exception {
		return dao.listSearch(cri);
	}

	@Override
	public int countSearch(PageVO cri) throws Exception {
		return dao.countSearch(cri);
	}

	@Override
	public void createHelp2(HelpVO vo) throws Exception {
		dao.createHelp2(vo);
	}

	@Override
	public void updateHelp2(HelpVO vo) throws Exception {
		dao.updateHelp2(vo);
	}
	@Override
	public int maxHelp() throws Exception {
		return dao.maxHelp();
	}


}

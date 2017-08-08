package yjc.wdb.scts.service.impl;

import java.util.HashMap;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import yjc.wdb.scts.bean.Purchase_goodsVO;
import yjc.wdb.scts.dao.Purchase_goodsDAO;
import yjc.wdb.scts.service.Purchase_goodsService;

@Service
public class Purchase_goodsServiceImpl implements Purchase_goodsService {

	@Inject
	private Purchase_goodsDAO dao;

	@Override
	public void insertPurchase_goods(HashMap<String, String> vo) throws Exception {
		// TODO Auto-generated method stub
		dao.insertPurchase_goods(vo);
	}

}

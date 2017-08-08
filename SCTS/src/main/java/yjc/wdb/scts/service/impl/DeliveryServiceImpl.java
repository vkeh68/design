package yjc.wdb.scts.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import yjc.wdb.scts.dao.DeliveryDAO;
import yjc.wdb.scts.service.DeliveryService;

@Service
public class DeliveryServiceImpl implements DeliveryService{
	
	@Inject
	private DeliveryDAO dao;

	@Override
	public List<HashMap> deliveryList(int bhf_code) throws Exception {
		return dao.deliveryList(bhf_code);
	}

	@Override
	public List<HashMap> delivery_detail(int bill_code) throws Exception {
		
		return dao.delivery_detail(bill_code);
	}

	@Override
	public void updateDelivery(int bill_code) throws Exception {
		
		dao.updateDelivery(bill_code);
		
	}

	@Override
	public List<HashMap> deliveryNoti(int bhf_code) throws Exception {
		return dao.deliveryNoti(bhf_code);
	}

}

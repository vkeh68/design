package yjc.wdb.scts.dao.impl;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import yjc.wdb.scts.dao.DeliveryDAO;

@Repository
public class DeliveryDAOImpl implements DeliveryDAO{
	
	@Inject
	private SqlSession sql;
	
	private static final String NAMESPACE ="yjc.wdb.mapper.DeliveryMapper";

	@Override
	public List<HashMap> deliveryList(int bhf_code) throws Exception {
		
		return sql.selectList(NAMESPACE+".deliveryList", bhf_code);
	}

	@Override
	public List<HashMap> delivery_detail(int bill_code) throws Exception {
		
		return sql.selectList(NAMESPACE+".delivery_detail", bill_code);
	}

	@Override
	public void updateDelivery(int bill_code) throws Exception {
		
		sql.update(NAMESPACE+".updateDelivery", bill_code);
		
	}

	@Override
	public List<HashMap> deliveryNoti(int bhf_code) throws Exception {

		return sql.selectList(NAMESPACE+".deliveryNoti", bhf_code);
	}
	
	

}

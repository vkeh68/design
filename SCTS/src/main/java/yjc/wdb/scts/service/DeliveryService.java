package yjc.wdb.scts.service;

import java.util.HashMap;
import java.util.List;

public interface DeliveryService {
	
	public List<HashMap> deliveryList(int bhf_code) throws Exception;
	public List<HashMap> delivery_detail(int bill_code) throws Exception;
	public void updateDelivery(int bill_code) throws Exception;
	public List<HashMap> deliveryNoti(int bhf_code) throws Exception;

}

package yjc.wdb.scts.dao;

import java.util.HashMap;
import java.util.List;

public interface DeliveryDAO {
	
	public List<HashMap> deliveryList(int bhf_code) throws Exception;
	public List<HashMap> delivery_detail(int bill_code) throws Exception;
	public void updateDelivery(int bill_code) throws Exception;
	public List<HashMap> deliveryNoti(int bhf_code) throws Exception;

}

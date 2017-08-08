package yjc.wdb.scts.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* 결제 수단과 
 * 결제 정보를 함께 처리하는 dao
 */
public interface Settlement_methodDAO {
	public void insertSettlement_method(String setle_mth_nm) throws Exception;
	public void insertSettlement_infomation(Map<String, String> map) throws Exception;
	public List<HashMap> getSettlement() throws Exception;
}

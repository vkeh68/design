package yjc.wdb.scts.dao;

import java.util.List;
import java.util.Map;

public interface CategoryDAO {
	public List<Map> selectDetail_categoryList(Map map) throws Exception;
	public List<Map> selectLarge_categoryList() throws Exception;
	public List<Map> selectCategoryLocation(Map map) throws Exception;
	
	/* �ΰ� ��� Ʈ����� ó����  service ���� insertDetail_category_location�� Ʈ��������� ���� */
	public void deleteForRegister(Map map) throws Exception;
	public void deleteForRegister_position(Map map) throws Exception;
	public void insertDetail_category_location(Map map) throws Exception;
}

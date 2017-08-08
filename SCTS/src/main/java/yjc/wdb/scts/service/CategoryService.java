package yjc.wdb.scts.service;

import java.util.List;
import java.util.Map;

public interface CategoryService {

	public List<Map> selectDetail_categoryList(Map map) throws Exception;
	public List<Map> selectLarge_categoryList() throws Exception;
	public List<Map> selectCategoryLocation(Map map) throws Exception;
	
	public void insertDetail_category_location(Map map) throws Exception;
}

package yjc.wdb.scts.service.impl;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import yjc.wdb.scts.dao.CategoryDAO;
import yjc.wdb.scts.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Inject
	private CategoryDAO dao;

	@Override
	public List<Map> selectDetail_categoryList(Map map) throws Exception {
		// TODO Auto-generated method stub
		return dao.selectDetail_categoryList(map);
	}

	@Override
	public List<Map> selectLarge_categoryList() throws Exception {
		// TODO Auto-generated method stub
		return dao.selectLarge_categoryList();
	}

	@Override
	public List<Map> selectCategoryLocation(Map map) throws Exception {
		// TODO Auto-generated method stub
		return dao.selectCategoryLocation(map);
	}

	@Override
	@Transactional
	public void insertDetail_category_location(Map map) throws Exception {
		// TODO Auto-generated method stub
		dao.deleteForRegister(map);
		
		if(map.get("tileList") != null) {
			dao.deleteForRegister_position(map);
			dao.insertDetail_category_location(map);
		}
	}

}

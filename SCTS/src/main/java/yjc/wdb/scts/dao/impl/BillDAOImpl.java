package yjc.wdb.scts.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import yjc.wdb.scts.bean.BillVO;
import yjc.wdb.scts.dao.BillDAO;

@Repository
public class BillDAOImpl implements BillDAO {

	private static final String NAMESPACE = "yjc.wdb.mapper.BillMapper";

	@Inject
	private SqlSession sql;


	@Override
	public List<HashMap> yearSales(int year, int bhf_code) throws Exception {
		
		Map map = new HashMap();
		map.put("year", year);
		map.put("bhf_code", bhf_code);
		
		return sql.selectList(NAMESPACE+".yearSales", map);
	}

	@Override
	public List<HashMap> daySales(int bhf_code) throws Exception {
		
		return sql.selectList(NAMESPACE+".daySales", bhf_code);
	}

	@Override
	public List<HashMap> searchYear(int year1, int year2,  int bhf_code) throws Exception {
		Map map = new HashMap();
		map.put("year1", year1);
		map.put("year2", year2);
		map.put("bhf_code", bhf_code);

		return sql.selectList(NAMESPACE+".searchYear", map);
	}

	@Override
	public List<HashMap> settleSalesInfo(int year1, int year2, int bhf_code) throws Exception {
		Map map = new HashMap();
		map.put("year1", year1);
		map.put("year2", year2);
		map.put("bhf_code", bhf_code);

		return sql.selectList(NAMESPACE+".settleSalesInfo", map);
	}

	@Override
	public List<HashMap> daySalesSettleInfo(int bhf_code) throws Exception {
		// TODO Auto-generated method stub
		return sql.selectList(NAMESPACE+".daySalesSettleInfo", bhf_code);
	}

	@Override
	public List<HashMap> searchDaySales(String date1, String date2, int bhf_code) throws Exception {

		Map map = new HashMap();
		map.put("date1", date1);
		map.put("date2", date2);
		map.put("bhf_code", bhf_code);

		return sql.selectList(NAMESPACE+".searchDaySales", map);
	}

	@Override
	public List<HashMap> daySettle(String date1, String date2, int setle_mth_code, int bhf_code) {

		Map map = new HashMap();
		map.put("date1", date1);
		map.put("date2", date2);
		map.put("setle_mth_code", setle_mth_code);
		map.put("bhf_code", bhf_code);

		return sql.selectList(NAMESPACE+".daySettle", map);
	}

	@Override
	public List<HashMap> monthSales(String month1, String month2, int bhf_code) throws Exception {
		Map map = new HashMap();
		map.put("month1", month1);
		map.put("month2", month2);
		map.put("bhf_code", bhf_code);


		return sql.selectList(NAMESPACE+".monthSales", map);
	}

	@Override
	public List<HashMap> monthSalesSettleInfo(String month1, String month2, int setle_mth_code, int bhf_code) throws Exception {
		Map map = new HashMap();
		map.put("month1", month1);
		map.put("month2", month2);
		map.put("setle_mth_code", setle_mth_code);
		map.put("bhf_code", bhf_code);

		return sql.selectList(NAMESPACE+".monthSalesSettleInfo", map);
	}

	@Override
	public List<HashMap> productRank(String date, int standard, int bhf_code) throws Exception {

		Map map = new HashMap();
		map.put("date", date);
		map.put("standard", standard);
		map.put("bhf_code", bhf_code);

		return sql.selectList(NAMESPACE+".productRank", map);
	}

	@Override
	public List<HashMap> productRankInfo(String date ,int standard, int bhf_code) throws Exception {
		Map map = new HashMap();
		map.put("date", date);
		map.put("standard", standard);
		map.put("bhf_code", bhf_code);

		return sql.selectList(NAMESPACE+".productRankInfo", map);
	}

	@Override
	public List<HashMap> yearToMonth(int year, int bhf_code) throws Exception {
		Map map = new HashMap();
		map.put("year", year);
		map.put("bhf_code", bhf_code);
		return sql.selectList(NAMESPACE+".yearToMonth", map);
	}

	@Override
	public List<HashMap> genderSales(String date, String gender, int bhf_code) throws Exception {
		Map map = new HashMap();
		map.put("date", date);
		map.put("gender", gender);
		map.put("bhf_code", bhf_code);

		return sql.selectList(NAMESPACE+".genderSales", map);
	}

	@Override
	public List<HashMap> ageSales(String date, int age, int standard, String gender, int bhf_code) throws Exception {
		Map map = new HashMap();
		map.put("date", date);
		map.put("age", age);
		map.put("standard", standard);
		map.put("gender", gender);
		map.put("bhf_code", bhf_code);

		return sql.selectList(NAMESPACE+".ageSales", map);
	}

	@Override
	public List<HashMap> ageSalesInfo(String date, int age, int standard, String gender, int bhf_code) throws Exception {

		Map map = new HashMap();
		map.put("date", date);
		map.put("age", age);
		map.put("standard", standard);
		map.put("gender", gender);
		map.put("bhf_code", bhf_code);

		return sql.selectList(NAMESPACE+".ageSalesInfo", map);
	}

	@Override
	public int todaySales(int bhf_code) throws Exception {
		return sql.selectOne(NAMESPACE+".todaySales", bhf_code);
	}

	@Override
	public int monthTotalSales(int bhf_code) throws Exception {

		return sql.selectOne(NAMESPACE+".monthTotalSales", bhf_code);
	}


	@Override
	public void insertBill(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		sql.insert(NAMESPACE + ".insertBill", map);
	}

	@Override
	public void updateTotamt() throws Exception {
		// TODO Auto-generated method stub
		sql.update(NAMESPACE + ".totalPrice");
	}

	@Override
	public List<BillVO> monthlyTotalSale() throws Exception {
		// TODO Auto-generated method stub
		return sql.selectList(NAMESPACE + ".monthlyTotalSale");
	}

	@Override
	public List<BillVO> branchTotalSale(int bhf_code) throws Exception {
		// TODO Auto-generated method stub
		return sql.selectList(NAMESPACE + ".branchTotalSale", bhf_code);
	}

}

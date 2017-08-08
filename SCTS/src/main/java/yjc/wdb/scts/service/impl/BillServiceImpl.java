package yjc.wdb.scts.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import yjc.wdb.scts.bean.BillVO;
import yjc.wdb.scts.dao.BillDAO;
import yjc.wdb.scts.dao.Purchase_goodsDAO;
import yjc.wdb.scts.dao.Settlement_methodDAO;
import yjc.wdb.scts.service.BillService;

@Service
public class BillServiceImpl implements BillService {

	@Inject
	private BillDAO dao;

	@Inject
	private Purchase_goodsDAO p_dao;

	@Inject
	private Settlement_methodDAO s_dao;




	@Override
	public List<HashMap> yearSales(int year, int bhf_code) throws Exception {
		// TODO Auto-generated method stub
		return dao.yearSales(year, bhf_code);
	}

	@Override
	public List<HashMap> daySales(int bhf_code) throws Exception {
		// TODO Auto-generated method stub
		return dao.daySales(bhf_code);
	}

	@Override
	public List<HashMap> searchYear(int year1, int year2, int bhf_code) throws Exception {
		// TODO Auto-generated method stub
		return dao.searchYear(year1, year2, bhf_code);
	}

	@Override
	public List<HashMap> settleSalesInfo(int year1, int year2, int bhf_code) throws Exception {

		return dao.settleSalesInfo(year1, year2, bhf_code);
	}

	@Override
	public List<HashMap> daySalesSettleInfo(int bhf_code) throws Exception {

		return dao.daySalesSettleInfo(bhf_code);
	}

	@Override
	public List<HashMap> searchDaySales(String date1, String date2, int bhf_code) throws Exception {
		// TODO Auto-generated method stub
		return dao.searchDaySales(date1, date2, bhf_code);
	}

	@Override
	public List<HashMap> daySettle(String date1, String date2, int setle_mth_code, int bhf_code) {
		// TODO Auto-generated method stub
		return dao.daySettle(date1, date2, setle_mth_code, bhf_code);
	}

	@Override
	public List<HashMap> monthSales(String month1, String month2, int bhf_code) throws Exception {
		// TODO Auto-generated method stub
		return dao.monthSales(month1, month2, bhf_code);
	}

	@Override
	public List<HashMap> monthSalesSettleInfo(String month1, String month2, int setle_mth_code, int bhf_code) throws Exception {
		// TODO Auto-generated method stub
		return dao.monthSalesSettleInfo(month1, month2, setle_mth_code, bhf_code);
	}

	@Override
	public List<HashMap> productRank(String date, int standard, int bhf_code) throws Exception {

		return dao.productRank(date, standard, bhf_code);
	}

	@Override
	public List<HashMap> productRankInfo(String date, int standard, int bhf_code) throws Exception {

		return dao.productRankInfo(date, standard, bhf_code);
	}

	@Override
	public List<HashMap> yearToMonth(int year, int bhf_code) throws Exception {

		return dao.yearToMonth(year, bhf_code);
	}

	@Override
	public List<HashMap> genderSales(String date, String gender, int bhf_code) throws Exception {

		return dao.genderSales(date, gender, bhf_code);
	}

	@Override
	public List<HashMap> ageSales(String date, int age, int standard, String gender, int bhf_code) throws Exception {

		return dao.ageSales(date, age, standard, gender, bhf_code);
	}

	@Override
	public List<HashMap> ageSalesInfo(String date, int age, int standard, String gender, int bhf_code) throws Exception {
		// TODO Auto-generated method stub
		return dao.ageSalesInfo(date, age, standard, gender, bhf_code);
	}

	@Override
	public int todaySales(int bhf_code) throws Exception {
		// TODO Auto-generated method stub
		return dao.todaySales(bhf_code);
	}

	@Override
	public int monthTotalSales(int bhf_code) throws Exception {

		return dao.monthTotalSales(bhf_code);
	}

	@Override
	@Transactional
	public void payment(Map<String, String> billMap, int stprc[], int setle_mth_code[], List<HashMap<String, String>> goodsList)
			throws Exception {
		// TODO Auto-generated method stub

		// 새로운 계산서 생성
		dao.insertBill(billMap);

		/*******************************************/
		// 구매 물품 리스트를 맵에다가 삽입
		Map<String, Object> goodsMap = new HashMap<String, Object>();
		goodsMap.put("goodsList", goodsList);

		// 구매몰품 목록을 삽입
		p_dao.insertPurchase_goodsList(goodsMap);

		// 총 가격 업데이트
		dao.updateTotamt();


		/*******************************************/
		// 결제 정보 관련 맵 생성
		for(int i = 0; i < stprc.length; i++){
			
			Map<String, String> payment_map = new HashMap<String, String>();
			payment_map.put("setle_mth_code", setle_mth_code[i]+"");
			payment_map.put("stprc", "" + stprc[i]+"");

			// 결제 정보 삽입
			s_dao.insertSettlement_infomation(payment_map);
			
		}
		
	}

	@Override
	public List<BillVO> monthlyTotalSale() throws Exception {
		// TODO Auto-generated method stub
		return dao.monthlyTotalSale();
	}

	@Override
	public List<BillVO> branchTotalSale(int bhf_code) throws Exception {
		// TODO Auto-generated method stub
		return dao.branchTotalSale(bhf_code);
	}

	@Override
	public List<HashMap> getSettlement() throws Exception {
		
		return s_dao.getSettlement();
	}

}

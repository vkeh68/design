package yjc.wdb.scts;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import yjc.wdb.scts.service.BillService;

@Controller
public class SalesController {
	
	@Inject
	private BillService billService;
	
	@RequestMapping(value="yearSales", method=RequestMethod.GET, produces = "text/plain; charset=UTF-8")
	public @ResponseBody String yearSales(int year, int bhf_code) throws Exception{

		List<HashMap> list = billService.yearSales(year, bhf_code);
		

		JSONObject salesJson;
		JSONArray salesArray = new JSONArray();
		JSONArray ytmArray = new JSONArray();
		
		
		for(int i = 0; i < list.size(); i++){
			salesJson = new JSONObject();
			salesJson.put("year", list.get(i).get("year"));
			salesJson.put("totalPrice", list.get(i).get("totalPrice"));
			salesArray.add(salesJson);

		}
		

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("yearSales", salesArray);
	

		return jsonObject.toString();
	}
	
	@RequestMapping(value="yearToMonth", method=RequestMethod.GET, produces = "text/plain; charset=UTF-8")
	public @ResponseBody String yearToMonth(int year, HttpSession session) throws Exception{


		int bhf_code = (int) session.getAttribute("bhf_code");
		List<HashMap> yearToMonth = billService.yearToMonth(year, bhf_code);

		JSONObject salesJson;
	
		JSONArray ytmArray = new JSONArray();
	
		
		for(int i = 0; i < yearToMonth.size(); i++){
			salesJson = new JSONObject();
			salesJson.put("bill_issu_de", yearToMonth.get(i).get("bill_issu_de"));
			salesJson.put("totalPrice", yearToMonth.get(i).get("totalPrice"));
			ytmArray.add(salesJson);

		}
	

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("yearToMonth", ytmArray);

		return jsonObject.toString();
	}
	
	
	@RequestMapping(value="searchYear", method=RequestMethod.GET, produces = "text/plain; charset=UTF-8")
	public @ResponseBody String searchYear(int year1, int year2, int bhf_code) throws Exception{


		List<HashMap> list = billService.searchYear(year1, year2, bhf_code);
		
		JSONObject salesJson;
		JSONArray salesArray = new JSONArray();
		
		for(int i = 0; i < list.size(); i++){
			salesJson = new JSONObject();
			salesJson.put("year", list.get(i).get("year"));
			salesJson.put("totalPrice", list.get(i).get("totalPrice"));
			salesArray.add(salesJson);

		}
		

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("yearSales", salesArray);

		return jsonObject.toString();
	}
	
	
	@RequestMapping(value="settleSalesInfo", method=RequestMethod.GET, produces = "text/plain; charset=UTF-8")
	public @ResponseBody String settleSalesInfo(int year1, int year2, int bhf_code) throws Exception{

		List<HashMap> list = billService.settleSalesInfo(year1, year2, bhf_code);

		JSONObject salesJson;
		JSONArray salesArray = new JSONArray();
		
		for(int i = 0; i < list.size(); i++){
			salesJson = new JSONObject();
			salesJson.put("year", list.get(i).get("year"));
			salesJson.put("setle_mth_nm", list.get(i).get("setle_mth_nm"));
			salesJson.put("totalPrice", list.get(i).get("totalPrice"));
			salesArray.add(salesJson);

		}
		

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", salesArray);

		return jsonObject.toString();
	}
	

	@RequestMapping(value="daySales", method=RequestMethod.GET, produces = "text/plain; charset=UTF-8")
	public @ResponseBody String daySales(int bhf_code) throws Exception{
		List<HashMap> list = billService.daySales(bhf_code);

		JSONObject salesJson;
		JSONArray salesArray = new JSONArray();
		
		for(int i = 0; i < list.size(); i++){
			salesJson = new JSONObject();
			salesJson.put("bill_issu_de", list.get(i).get("bill_issu_de").toString());
			salesJson.put("totalPrice", list.get(i).get("totalPrice"));
			salesArray.add(salesJson);
		}
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", salesArray);

		return jsonObject.toString();
	}
	

	
	@RequestMapping(value="daySettle", method=RequestMethod.GET, produces = "text/plain; charset=UTF-8")
	public @ResponseBody String daySettle(String date1, String date2, int setle_mth_code, int bhf_code) throws Exception{


		List<HashMap> list = billService.daySettle(date1, date2, setle_mth_code, bhf_code);
		
		JSONObject salesJson;
		JSONArray salesArray = new JSONArray();
		
		for(int i = 0; i < list.size(); i++){
			salesJson = new JSONObject();
			salesJson.put("year", list.get(i).get("year").toString());
			salesJson.put("setle_mth_nm", list.get(i).get("setle_mth_nm"));
			salesJson.put("totalPrice", list.get(i).get("totalPrice"));
			salesArray.add(salesJson);

		}
		

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", salesArray);

		return jsonObject.toString();
	}
	
	@RequestMapping(value="searchDaySales", method=RequestMethod.GET, produces = "text/plain; charset=UTF-8")
	public @ResponseBody String searchDaySales(String date1, String date2, int bhf_code) throws Exception{

		List<HashMap> list = billService.searchDaySales(date1, date2, bhf_code);

		JSONObject salesJson;
		JSONArray salesArray = new JSONArray();
		
		for(int i = 0; i < list.size(); i++){
			salesJson = new JSONObject();
			salesJson.put("bill_issu_de", list.get(i).get("bill_issu_de").toString());
			salesJson.put("totalPrice", list.get(i).get("totalPrice"));
			salesArray.add(salesJson);
		}
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", salesArray);

		return jsonObject.toString();
	}
	

	
	@RequestMapping(value="daySalesSettleInfo", method=RequestMethod.GET, produces = "text/plain; charset=UTF-8")
	public @ResponseBody String daySalesSettleInfo(int bhf_code) throws Exception{

		List<HashMap> list = billService.daySalesSettleInfo(bhf_code);
		
		JSONObject salesJson;
		JSONArray salesArray = new JSONArray();
		
		for(int i = 0; i < list.size(); i++){
			salesJson = new JSONObject();
			salesJson.put("year", list.get(i).get("year").toString());
			salesJson.put("setle_mth_nm", list.get(i).get("setle_mth_nm"));
			salesJson.put("totalPrice", list.get(i).get("totalPrice"));
			salesArray.add(salesJson);

		}
		

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", salesArray);

		return jsonObject.toString();
	}
	
	@RequestMapping(value="monthSales", method=RequestMethod.GET, produces = "text/plain; charset=UTF-8")
	public @ResponseBody String monthSales(String month1, String month2, int bhf_code) throws Exception{


		List<HashMap> list = billService.monthSales(month1, month2, bhf_code);

		JSONObject salesJson;
		JSONArray salesArray = new JSONArray();
		
		for(int i = 0; i < list.size(); i++){
			salesJson = new JSONObject();
			salesJson.put("bill_issu_de", list.get(i).get("bill_issu_de").toString());
			salesJson.put("totalPrice", list.get(i).get("totalPrice"));
			salesArray.add(salesJson);
		}
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", salesArray);

		return jsonObject.toString();
	}
	

	
	@RequestMapping(value="monthSalesSettleInfo", method=RequestMethod.GET, produces = "text/plain; charset=UTF-8")
	public @ResponseBody String monthSalesSettleInfo(String month1, String month2, int setle_mth_code, int bhf_code) throws Exception{


		List<HashMap> list = billService.monthSalesSettleInfo(month1, month2, setle_mth_code, bhf_code);
		
		JSONObject salesJson;
		JSONArray salesArray = new JSONArray();
		
		for(int i = 0; i < list.size(); i++){
			salesJson = new JSONObject();
			salesJson.put("year", list.get(i).get("year").toString());
			salesJson.put("setle_mth_nm", list.get(i).get("setle_mth_nm"));
			salesJson.put("totalPrice", list.get(i).get("totalPrice"));
			salesArray.add(salesJson);

		}
		

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", salesArray);

		return jsonObject.toString();
		
	}
	
	@RequestMapping(value="productRank", method=RequestMethod.GET, produces = "text/plain; charset=UTF-8")
	public @ResponseBody String productRank(String date, int standard, int bhf_code) throws Exception{


		List<HashMap> list = billService.productRank(date, standard, bhf_code);
		
		JSONObject salesJson;
		JSONArray salesArray = new JSONArray();
		
		for(int i = 0; i < list.size(); i++){
			salesJson = new JSONObject();
			salesJson.put("goods_nm", list.get(i).get("goods_nm").toString());
			salesJson.put("totalPurchsgoods_qy", list.get(i).get("totalPurchsgoods_qy"));
			salesJson.put("totalPrice", list.get(i).get("totalPrice"));
			salesJson.put("goods_netIncome", list.get(i).get("goods_netIncome"));
			salesArray.add(salesJson);

		}
	
		

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", salesArray);

		return jsonObject.toString();
		
	}
	
	
	@RequestMapping(value="productRankInfo", method=RequestMethod.GET, produces = "text/plain; charset=UTF-8")
	public @ResponseBody String productRankInfo(String date, int standard, int bhf_code) throws Exception{

		List<HashMap> list = billService.productRankInfo(date, standard, bhf_code);
		
		JSONObject salesJson;
		JSONArray salesArray = new JSONArray();
		
		for(int i = 0; i < list.size(); i++){
			salesJson = new JSONObject();
			salesJson.put("goods_nm", list.get(i).get("goods_nm").toString());
			salesJson.put("totalPurchsgoods_qy", list.get(i).get("totalPurchsgoods_qy"));
			salesJson.put("totalPrice", list.get(i).get("totalPrice"));
			salesJson.put("goods_netIncome", list.get(i).get("goods_netIncome"));
			salesJson.put("totalCouponCount", list.get(i).get("totalCouponCount"));
			salesArray.add(salesJson);

		}
	
		

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", salesArray);

		return jsonObject.toString();
		
	}
	
	@RequestMapping(value="genderSales", method=RequestMethod.GET, produces = "text/plain; charset=UTF-8")
	public @ResponseBody String genderSales(String date, int bhf_code) throws Exception{

		List<HashMap> list = billService.genderSales(date, "m", bhf_code);
		List<HashMap> list2 = billService.genderSales(date, "w", bhf_code);

		JSONObject salesJson;
		JSONArray salesArray = new JSONArray();
		JSONArray ytmArray = new JSONArray();
		
		System.out.println(list.toString());
		System.out.println(list2.toString());
		
		
		for(int i = 0; i < list.size(); i++){
			salesJson = new JSONObject();
			salesJson.put("goods_nm", list.get(i).get("goods_nm"));
			salesJson.put("totalPrice", list.get(i).get("totalPrice"));
			
			
				
				/*if(list.get(i).get("goods_nm") != list2.get(i).get("goods_nm")){
					HashMap map = new HashMap();
					map.put("goods_nm", list.get(i).get("goods_nm"));
					map.put("totalPrice", 0);
					
					
					list2.add(map);
					
					System.out.println(list.toString());
					System.out.println(list2.toString());
					
					
				}*/

				salesArray.add(salesJson);

		}
		
		for(int i = 0; i < list2.size(); i++){
			salesJson = new JSONObject();
			salesJson.put("goods_nm", list2.get(i).get("goods_nm"));
			salesJson.put("totalPrice", list2.get(i).get("totalPrice"));
			ytmArray.add(salesJson);

		}
		

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("m", salesArray);
		jsonObject.put("w", ytmArray);
	

		return jsonObject.toString();
	}
	
	
	@RequestMapping(value="ageSales", method=RequestMethod.GET, produces = "text/plain; charset=UTF-8")
	public @ResponseBody String ageSales(HttpServletRequest request, String date, int age, 
			int standard, String gender, int bhf_code) throws Exception{

		List<HashMap> list = billService.ageSales(date, age, standard, gender, bhf_code);
	
		JSONObject salesJson;
		JSONArray salesArray = new JSONArray();
		
		for(int i = 0; i < list.size(); i++){
			salesJson = new JSONObject();
			salesJson.put("goods_nm", list.get(i).get("goods_nm").toString());
			salesJson.put("totalPrice", list.get(i).get("totalPrice"));
			salesJson.put("goods_netIncome", list.get(i).get("goods_netIncome"));
		
			salesArray.add(salesJson);

		}

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", salesArray);

		return jsonObject.toString();
		
	}
	
	@RequestMapping(value="ageSalesInfo", method=RequestMethod.GET, produces = "text/plain; charset=UTF-8")
	public @ResponseBody String ageSalesInfo(String date, int age, 
			int standard, String gender, int bhf_code) throws Exception{


		
		List<HashMap> list = billService.ageSalesInfo(date, age, standard, gender, bhf_code);
	
		JSONObject salesJson;
		JSONArray salesArray = new JSONArray();
		
		for(int i = 0; i < list.size(); i++){
			salesJson = new JSONObject();
			salesJson.put("goods_nm", list.get(i).get("goods_nm").toString());
			salesJson.put("totalPrice", list.get(i).get("totalPrice"));
			salesJson.put("totalPurchsgoods_qy", list.get(i).get("totalPurchsgoods_qy"));
			salesJson.put("totalCouponCount", list.get(i).get("totalCouponCount"));
			salesJson.put("goods_netIncome", list.get(i).get("goods_netIncome"));
		
			salesArray.add(salesJson);

		}

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", salesArray);

		return jsonObject.toString();
		
	}

}

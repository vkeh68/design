package yjc.wdb.scts;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import yjc.wdb.scts.bean.Branch_officeVO;
import yjc.wdb.scts.bean.CouponVO;
import yjc.wdb.scts.bean.GoodsVO;
import yjc.wdb.scts.service.CouponService;
import yjc.wdb.scts.service.GoodsService;

@Controller
public class CouponController {
	private static final Logger logger = LoggerFactory.getLogger(CouponController.class);
	@Inject
	private CouponService couponService;
	
	@Inject
	private GoodsService goodsService;
	
	/********************************** 捻迄 包府 何盒***************************************/
	/********************************** 捻迄 包府 何盒***************************************/

	@RequestMapping(value = "insertCoupon", method = RequestMethod.GET, produces = "text/plain; charset=UTF-8")
	public @ResponseBody String insertCoupon(CouponVO couponVO, Model model, HttpSession session, String select) throws Exception {
		String ContentPage = "coupon_Management";
		
		int bhf_code = (int) session.getAttribute("bhf_code");
		couponVO.setBhf_code(bhf_code);
		couponService.insertCoupon(couponVO, select);
	
		model.addAttribute("main_content", ContentPage);
		return "success";
	}
	
	@RequestMapping(value = "deleteCoupon", method = RequestMethod.POST)
	public String remove(int coupon_code, HttpSession session) throws Exception {

		int bhf_code = (int)session.getAttribute("bhf_code");
		couponService.deleteCoupon(coupon_code, bhf_code);

		return "redirect:coupon_Management";
	}

	@RequestMapping(value = "coupon_Management", method = RequestMethod.GET)
	public String coupon(HttpServletRequest request, HttpSession session, Model model, String status) throws Exception {
		String ContentPage = "coupon_Management";
		model.addAttribute("main_content", ContentPage);

		int bhf = (int)session.getAttribute("bhf_code");
		
		if(status != null){
			model.addAttribute("status", status);
		}
		
		List<CouponVO> Couponlist = couponService.selectCouponList(bhf);
		model.addAttribute("list", Couponlist);
		
		return "mainPage";
	}
	
	@RequestMapping(value="coupon_modify", method=RequestMethod.POST)
	public String couponModify(HttpServletRequest request, HttpSession session, Model model, int coupon_code) throws Exception{
		
		String ContentPage = "coupon_Modify";
		model.addAttribute("main_content", ContentPage);
		
		List<GoodsVO> GoodsList  = null;
		List<Branch_officeVO> branchList = null;
		int bhf_code = (int) session.getAttribute("bhf_code");
		
		if(bhf_code == 1){
			
			GoodsList = goodsService.selectGoodsList();
			branchList = couponService.selectAllbranchOffice();
			model.addAttribute("branchList", branchList);
			
		}else{
			GoodsList = goodsService.selectAdNotGoodsList(bhf_code);
		}
		
		CouponVO coupon = couponService.selectCouponOne(coupon_code, bhf_code);
		model.addAttribute("coupon",coupon);
		

		model.addAttribute("GoodsList", GoodsList);
		
		return "mainPage";
		
	}
	
	@RequestMapping(value="modifyCoupon", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<String> modify(HttpSession session, CouponVO couponVO, String select){
	
		ResponseEntity<String> entity = null;
		
		int bhf_code = (int) session.getAttribute("bhf_code");
		try {
			couponService.updateCoupon(couponVO, bhf_code, select);
			entity = new ResponseEntity<String>("success", HttpStatus.OK);
					
		} catch (Exception e) {
			entity = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
			e.printStackTrace();
		}

		return entity;
		
	}
	
	@RequestMapping(value="modifyAdCoupon", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<String> modifyAd(HttpSession session, CouponVO couponVO, String select, String branch){
		
		ResponseEntity<String> entity = null;
		
		JSONArray jsonArray;
		try {
			JSONObject json = (JSONObject) new JSONParser().parse(branch);
			jsonArray = (JSONArray) new JSONParser().parse(json.get("branch").toString());
			int[] branch_office = new int[jsonArray.size()];
			
			for(int i = 0; i < jsonArray.size(); i++){
				branch_office[i] = Integer.parseInt(jsonArray.get(i).toString());
				System.out.println(couponVO.getCoupon_code());
				System.out.println(branch_office[i]);
			}
			
			System.out.println(branch_office.toString());
			
			int bhf_code = (int) session.getAttribute("bhf_code");
		
			couponService.updateAdCoupon(couponVO, bhf_code, select, branch_office);
			entity = new ResponseEntity<String>("success", HttpStatus.OK);
			
		} catch (Exception e) {
			entity = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
			e.printStackTrace();
		}
		

		return entity;
		
	}
	
	@RequestMapping(value = "reg_coupon", method = RequestMethod.GET)
	public String search(Model model, HttpSession session) throws Exception {
		String ContentPage = "coupon_Register";
		model.addAttribute("main_content", ContentPage);
		
		List<GoodsVO> GoodsList  = null;
		List<Branch_officeVO> branchList = null;
		int bhf_code = (int) session.getAttribute("bhf_code");
		
		if(bhf_code == 1){
			
			GoodsList = goodsService.selectGoodsList();
			branchList = couponService.selectAllbranchOffice();
			model.addAttribute("branchList", branchList);
			
		}else{
			GoodsList = goodsService.selectAdNotGoodsList(bhf_code);
		}
		
		
		model.addAttribute("GoodsList", GoodsList);
		
		return "mainPage";
	}
	
	@RequestMapping(value="search", method=RequestMethod.GET,produces = "text/plain; charset=UTF-8")
	public @ResponseBody String searcing(String goodsName) throws Exception{
		
		List<GoodsVO> GoodsSearch = goodsService.searchGoodsList(goodsName);
		
		JSONObject goodJson;
		JSONArray goodsArray= new JSONArray();
		
		
		
		for(int i=0; i < GoodsSearch.size(); i++){
			
			goodJson = new JSONObject();
			
			goodJson.put("goods_code", GoodsSearch.get(i).getGoods_code());
			goodJson.put("goods_nm", GoodsSearch.get(i).getGoods_nm());
			goodJson.put("goods_pc", GoodsSearch.get(i).getGoods_pc());
			
			goodsArray.add(goodJson);

		}
		 
		JSONObject json = new JSONObject();
		json.put("result", goodsArray);
		
		logger.info("goods: " + json.toString());
		return json.toString();
	}
	
	
	@RequestMapping(value="searchingBranchOffice", method=RequestMethod.GET,produces = "text/plain; charset=UTF-8")
	public @ResponseBody String searchingBranchOffice(String bhf_nm) throws Exception{
		
		List<Branch_officeVO> branchSearch = couponService.searchingBranchOffice(bhf_nm);
		
		JSONObject branchJson;
		JSONArray branchArray= new JSONArray();
		
		
		
		for(int i=0; i < branchSearch.size(); i++){
			
			branchJson = new JSONObject();
			
			branchJson.put("bhf_code", branchSearch.get(i).getBhf_code());
			branchJson.put("bhf_nm", branchSearch.get(i).getBhf_nm());
			branchJson.put("bhf_adres", branchSearch.get(i).getBhf_adres());
			
			branchArray.add(branchJson);

		}
		 
		JSONObject json = new JSONObject();
		json.put("result", branchArray);

		return json.toString();
	}
	
	
	@RequestMapping(value="selectAllCategory", method=RequestMethod.GET,produces = "text/plain; charset=UTF-8")
	public @ResponseBody String selectAllCategory() throws Exception{
		
		List<HashMap> category = couponService.selectAllCategory();
		
		JSONObject categoryJson;
		JSONArray categoryArray= new JSONArray();
		
		for(int i=0; i < category.size(); i++){
			
			categoryJson = new JSONObject();
			
			categoryJson.put("detailctgry_code", category.get(i).get("detailctgry_code"));
			categoryJson.put("detailctgry_nm", category.get(i).get("detailctgry_nm"));
		
			categoryArray.add(categoryJson);

		}
		 
		JSONObject json = new JSONObject();
		json.put("result", categoryArray);

		return json.toString();
	}
}

package yjc.wdb.scts;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import yjc.wdb.scts.bean.BillVO;
import yjc.wdb.scts.bean.Branch_officeVO;
import yjc.wdb.scts.service.BillService;
import yjc.wdb.scts.service.Branch_officeService;
import yjc.wdb.scts.service.CourseService;

@Controller
public class HeadOfficeController {
	
	@Inject
	private Branch_officeService branch_officeService;
	
	@Inject
	private BillService billService;
	
	@Inject
	private CourseService courseService;
	
	private static final Logger logger = LoggerFactory.getLogger(HeadOfficeController.class);
	
	@RequestMapping(value="headOfficeMain", method=RequestMethod.GET)
	public String headOfficeMain(HttpServletRequest request, HttpSession session, Model model) throws Exception {
		
		String ContentPage = "headOffice";
		model.addAttribute("main_content", ContentPage);
		
		List<Branch_officeVO> branchList = branch_officeService.selectBranchOffice();
		model.addAttribute("branchList", branchList);
		
		return "mainPage";
	}
	
	@RequestMapping(value="list", method=RequestMethod.GET)
	public String list(HttpServletRequest request, HttpSession session, Model model) throws Exception {
		
		String ContentPage = "list";
		model.addAttribute("main_content", ContentPage);
		
		return "mainPage";
	}
	
	@RequestMapping(value="bhfSearching", method=RequestMethod.GET,produces = "text/plain; charset=UTF-8")
	public @ResponseBody String searching(String bhf_nm) throws Exception{
		
		System.out.println(bhf_nm);
		
		List<Branch_officeVO> branchSearch = branch_officeService.searchBranchList(bhf_nm);
		JSONObject branchJson;
		JSONArray branchArray= new JSONArray();
		
		for(int i=0; i < branchSearch.size(); i++){
			
			branchJson = new JSONObject();
			
			branchJson.put("bhf_code", branchSearch.get(i).getBhf_code());
			branchJson.put("bhf_nm", branchSearch.get(i).getBhf_nm());
			branchJson.put("bhf_telno", branchSearch.get(i).getBhf_telno());
			
			branchArray.add(branchJson);

		}
		 
		JSONObject json = new JSONObject();
		json.put("result", branchArray);
		
		logger.info("branch: " + json.toString());
		return json.toString();
	}
	
	@RequestMapping(value="branchGrade", method=RequestMethod.GET,produces = "text/plain; charset=UTF-8")
	public @ResponseBody String grade() throws Exception{

		List<Branch_officeVO> branchGrade = branch_officeService.selectGrade();
		JSONObject gradeJson;
		JSONArray gradeArray= new JSONArray();
		
		for(int i=0; i < branchGrade.size(); i++){
			
			gradeJson = new JSONObject();
			
			gradeJson.put("bhf_code", branchGrade.get(i).getBhf_code());
			gradeJson.put("bhf_nm", branchGrade.get(i).getBhf_nm());
			gradeJson.put("totalPrice", branchGrade.get(i).getTotalPrice());
			
			gradeArray.add(gradeJson);

		}
		 
		JSONObject json = new JSONObject();
		json.put("result", gradeArray);
		
		logger.info("grade: " + json.toString());
		return json.toString();
	}
	
	@RequestMapping(value="monthlyTotalSale", method=RequestMethod.GET, produces = "text/plain; charset=UTF-8")
	public @ResponseBody String month() throws Exception{

		List<BillVO> MonthlySale = billService.monthlyTotalSale();
		JSONObject monthlyJson;
		JSONArray monthlyArray= new JSONArray();
		
		for(int i=0; i < MonthlySale.size(); i++){
			
			monthlyJson = new JSONObject();
			
			monthlyJson.put("date", MonthlySale.get(i).getDate().toString());
			monthlyJson.put("monthlyTotalSale", MonthlySale.get(i).getTotalSale());
			
			monthlyArray.add(monthlyJson);

		}
		 
		JSONObject json = new JSONObject();
		json.put("result", monthlyArray);
		
		logger.info("monthlyTotalSale: " + json.toString());
		return json.toString();
	}
	
	@RequestMapping(value="branchTotalSale", method=RequestMethod.GET, produces = "text/plain; charset=UTF-8")
	public @ResponseBody String branch(int bhf_code) throws Exception{

		List<BillVO> branchSale = billService.branchTotalSale(bhf_code);
		JSONObject branchSaleJson;
		JSONArray branchSaleArray= new JSONArray();
		
		for(int i=0; i < branchSale.size(); i++){
			
			branchSaleJson = new JSONObject();
			
			branchSaleJson.put("date", branchSale.get(i).getDate().toString());
			branchSaleJson.put("monthlyTotalSale", branchSale.get(i).getTotalSale());
			
			branchSaleArray.add(branchSaleJson);

		}
		 
		JSONObject json = new JSONObject();
		json.put("result", branchSaleArray);
		
		logger.info("branchTotalSale: " + json.toString());
		return json.toString();
	}
	
	@RequestMapping(value="branchGender", method=RequestMethod.GET)
	@ResponseBody
	public String branchGender(int day) throws Exception{
		List<HashMap> list = courseService.tileGender(day);

		JSONArray branchGenderArray = new JSONArray();
		for(int i = 0; i < list.size(); i++){

			JSONObject branchGenderObj = new JSONObject();

			branchGenderObj.put("probability", list.get(i).get("probability"));
			branchGenderObj.put("user_sexdstn", list.get(i).get("user_sexdstn"));

			branchGenderArray.add(branchGenderObj);			
		}

		JSONObject jsonObj = new JSONObject();
		jsonObj.put("branchGender", branchGenderArray);

		return jsonObj.toJSONString();
	}
	
}

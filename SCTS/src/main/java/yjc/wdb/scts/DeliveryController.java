package yjc.wdb.scts;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import yjc.wdb.scts.service.DeliveryService;

@Controller
public class DeliveryController {

	@Inject
	private DeliveryService deliveryService;


	@RequestMapping(value="delivery_Management", method=RequestMethod.GET)
	public String delivery_Management(HttpServletRequest request, HttpSession session, Model model) 
			throws Exception{
		String ContentPage = "delivery_Management";

		model.addAttribute("main_content", ContentPage);

		return "mainPage";
	}



	@RequestMapping(value="deliveryList", method=RequestMethod.GET)
	public @ResponseBody String deliveryList(int bhf_code) throws Exception{
	
		List<HashMap> list = deliveryService.deliveryList(bhf_code);
		JSONArray deliveryArray = new JSONArray();
		JSONObject deliveryJson;
		
		for(int i = 0; i < list.size(); i++){
			deliveryJson = new JSONObject();
			deliveryJson.put("bill_code", list.get(i).get("bill_code").toString());
			deliveryJson.put("user_id", list.get(i).get("user_id").toString());
			deliveryJson.put("bill_issu_de", list.get(i).get("bill_issu_de").toString());
			deliveryJson.put("bill_totamt", list.get(i).get("bill_totamt").toString());
			deliveryArray.add(deliveryJson);
		}
		
		JSONObject json = new JSONObject();
		json.put("delivery", deliveryArray);
		
		System.out.println(json.toString());

		return json.toString();
	}
	
	
	
	@RequestMapping(value="delivery_detail", method=RequestMethod.POST)
	public String delivery_detail(int bill_code, Model model) throws Exception{
		
		String ContentPage = "delivery_Detail";
	
		List<HashMap> list = deliveryService.delivery_detail(bill_code);
		JSONArray deliveryArray = new JSONArray();
		JSONObject deliveryJson;
		
		for(int i = 0; i < list.size(); i++){
			deliveryJson = new JSONObject();
			
			deliveryJson.put("bill_code", list.get(i).get("bill_code").toString());
			deliveryJson.put("user_id", list.get(i).get("user_id").toString());
			deliveryJson.put("goods_nm", list.get(i).get("goods_nm").toString());
			deliveryJson.put("bill_totamt", list.get(i).get("bill_totamt").toString());
			deliveryJson.put("delivery_addr", list.get(i).get("delivery_addr").toString());
			deliveryJson.put("goods_pc", list.get(i).get("goods_pc").toString());
			deliveryJson.put("purchsgoods_qy", list.get(i).get("purchsgoods_qy").toString());
			
			
			
			deliveryArray.add(deliveryJson);
		}
		
	
		model.addAttribute("delivery", deliveryArray);
		
		


		model.addAttribute("main_content", ContentPage);

		return "mainPage";
	}
	
	
	@RequestMapping(value="updateDelivery", method=RequestMethod.POST)
	public String updateDelivery(int bill_code, Model model) throws Exception{
		
		String ContentPage = "delivery_Management";
	
		deliveryService.updateDelivery(bill_code);
		

		model.addAttribute("main_content", ContentPage);

		return "mainPage";
	}
	
	
	@RequestMapping(value="deliveryNoti", method=RequestMethod.GET)
	public @ResponseBody String deliveryNoti(int bhf_code, Model model) throws Exception{
		
	
		List<HashMap> list = deliveryService.deliveryNoti(bhf_code);
		JSONArray deliveryArray = new JSONArray();
		JSONObject deliveryJson;
		
		for(int i = 0; i < list.size(); i++){
			deliveryJson = new JSONObject();
			
			deliveryJson.put("bill_code", list.get(i).get("bill_code").toString());
			deliveryJson.put("user_id", list.get(i).get("user_id").toString());
			
			deliveryArray.add(deliveryJson);
		}
		
		JSONObject json = new JSONObject();
		json.put("delivery", deliveryArray);
		
		model.addAttribute("delivery", list.size());
		

		
	
		return json.toString();
	}




}

package yjc.wdb.scts;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import yjc.wdb.scts.bean.GoodsVO;
import yjc.wdb.scts.bean.PageMaker;
import yjc.wdb.scts.bean.PageVO;
import yjc.wdb.scts.bean.StockVO;
import yjc.wdb.scts.service.GoodsService;
import yjc.wdb.scts.service.StockService;

@Controller
public class StockController {
	@Inject
	private StockService stockService;
	@Inject
	private GoodsService goodsService;
	
	/* 검색에서 중요한건 값이 변경 되는 경우의 수 check의 유지가 중요 */
	@RequestMapping(value="stock_Management", method=RequestMethod.GET)
	public String stockManagement(@ModelAttribute("cri") PageVO cri, Model model, HttpSession session) throws Exception{
		String ContentPage = "stock_Management";

		int bhf = (int)session.getAttribute("bhf_code");
		cri.setBhf_code(bhf);
		List<StockVO> StockList = stockService.selectStockList(cri);
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(stockService.countSearch(cri));
		
		model.addAttribute("msg", cri.isMsg());
		model.addAttribute("pageMaker", pageMaker);
		model.addAttribute("main_content", ContentPage);
		model.addAttribute("list", StockList);
		 
		return "mainPage";
	}
	
	@RequestMapping(value="searchStock", method=RequestMethod.GET)
	public String searchStock(@ModelAttribute("cri") PageVO cri, Model model, HttpSession session) throws Exception{
		String ContentPage = "stock_Management";

		int bhf = (int)session.getAttribute("bhf_code");
		cri.setBhf_code(bhf);
		
		List<StockVO> StockList = stockService.selectStockList(cri);
		
		System.out.println(cri.getCheck() + " " + cri.getSearchType());
		cri.setMsg(false);
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(stockService.countSearch(cri));
		model.addAttribute("msg", cri.isMsg());
		model.addAttribute("pageMaker", pageMaker);
		model.addAttribute("main_content", ContentPage);
		model.addAttribute("list", StockList);
		 
		return "mainPage";
	}
	
	@RequestMapping(value="insertStock", method=RequestMethod.GET)
	public String insertGetStock(Model model)throws Exception{
		String ContentPage = "stock_Register";
		
		List<GoodsVO> GoodsList = goodsService.selectGoodsList();
		List<String> EnterList = stockService.selectEnterprise();
		
		model.addAttribute("goodsList", GoodsList);
		model.addAttribute("enterList", EnterList);
		model.addAttribute("main_content", ContentPage);
		return "mainPage";
	}
	
	@RequestMapping(value="insertStock", method=RequestMethod.POST)
	public String insertPostStock(Model model, StockVO vo, HttpSession session, String user_id)throws Exception{
		
		int bhf = (int)session.getAttribute("bhf_code");
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		String json = gson.toJson(vo);
		HashMap<String, Object> map = new ObjectMapper().readValue(json, HashMap.class);
		map.put("bhf_code", bhf);
		map.put("user_id", user_id);
		
		stockService.insertStockList(map);
		
		return "redirect:stock_Management";
	}
	
	@RequestMapping(value="deleteStock", method=RequestMethod.GET)
	public String deleteStock(String user_id, int goods_code, @ModelAttribute("cri") PageVO cri , RedirectAttributes rttr, Model model)throws Exception{
		
		cri.setMsg(false);
		
		stockService.deleteStockList(user_id, goods_code);
		
		/* 삭제후 돌아가기 위해 필요 */
		rttr.addAttribute("page", cri.getPage());
		rttr.addAttribute("perPageNum", cri.getPerPageNum());
		rttr.addAttribute("searchType", cri.getSearchType());
		rttr.addAttribute("keyword", cri.getKeyword());
		rttr.addAttribute("startAmount", cri.getStartAmount());
		rttr.addAttribute("endAmount", cri.getEndAmount());
		rttr.addAttribute("check", cri.getCheck());
		
		return "redirect:stock_Management";
	}

}

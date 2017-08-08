package yjc.wdb.scts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import yjc.wdb.scts.bean.BeaconVO;
import yjc.wdb.scts.bean.TileVO;
import yjc.wdb.scts.dao.CategoryDAO;
import yjc.wdb.scts.service.BeaconService;
import yjc.wdb.scts.service.Branch_officeService;
import yjc.wdb.scts.service.CategoryService;
import yjc.wdb.scts.service.CourseService;
import yjc.wdb.scts.service.TileService;

@Controller
public class AjaxController {

	private static final Logger logger = LoggerFactory.getLogger(AjaxController.class);

	@Inject
	TileService tileService;

	@Inject
	BeaconService beaconService;

	@Inject
	Branch_officeService branchService;

	@Inject
	CourseService courseService;
	
	@Inject
	CategoryService categoryService;

	/* shop_Register.js
	 * 매장등록 페이지에서 도면위의 타일을 클릭햇을때 발생하는 아작스 통신
	 * 해당 타일의 정보를 디비에서 가져와서 보내준다
	 */
	@RequestMapping(value="shopTileClick", method=RequestMethod.POST)
	@ResponseBody
	public String shopTileClick(@RequestParam("drw_code") int drw_code,
			@RequestParam("X_index") int X_index, @RequestParam("Y_index") int Y_index) throws Exception {

		logger.info("X = " + X_index + "  Y = " + Y_index);

		TileVO clickTile = new TileVO();
		clickTile.setDrw_code(drw_code);
		clickTile.setTile_crdnt_x(X_index);
		clickTile.setTile_crdnt_y(Y_index);

		HashMap<String, String> tile = tileService.selectTile_LocationOne(clickTile);

		String str = new Gson().toJson(tile);

		System.out.println(str);

		return str;
	}

	/* shop_Register.js
	 * 매장등록 페이지에서 도면위의 타일을 클릭햇을때 발생하는 아작스 통신
	 * 세부 카테고리 가져옴
	 */
	@RequestMapping(value="shopDetailCategory", method=RequestMethod.POST, produces = "text/plain; charset=UTF-8")
	@ResponseBody
	public String shopDetailCategory(@RequestParam("lclasctgry_code") int lclasctgry_code) throws Exception {
		
		Map map = new HashMap();
		
		map.put("lclasctgry_code", lclasctgry_code);

		List<Map> detailCategoryList = categoryService.selectDetail_categoryList(map);

		String str = new Gson().toJson(detailCategoryList);

		System.out.println(str);

		return str;
	}

	/* shop_Register.js
	 * 매장등록 페이지에서 카테고리 설정모드 클릭시
	 * 대분류 카테고리 가져옴
	 */
	@RequestMapping(value="shopLargeCategory", method=RequestMethod.POST, produces = "text/plain; charset=UTF-8")
	@ResponseBody
	public String shopLargeCategory() throws Exception {

		List<Map> largeCategoryList = categoryService.selectLarge_categoryList();

		String str = new Gson().toJson(largeCategoryList);

		System.out.println(str);

		return str;
	}


	/* shop_Register.js
	 * 매장등록 페이지에서 카테고리 설정모드 클릭시
	 * 대분류 카테고리 가져옴
	 */
	@RequestMapping(value="categorySetAllZone", method=RequestMethod.POST, produces = "text/plain; charset=UTF-8")
	@ResponseBody
	public String categorySetAllZone(@RequestParam("detailctgry_code") int detailctgry_code, @RequestParam("drw_code") int drw_code) throws Exception {

		Map map = new HashMap();

		map.put("detailctgry_code", detailctgry_code);
		map.put("drw_code", drw_code);
		
		List<Map> largeCategoryList = categoryService.selectCategoryLocation(map);

		String str = new Gson().toJson(largeCategoryList);

		System.out.println(str);

		return str;
	}
	

	/* shop_Register.js
	 * 매장등록 페이지에서 카테고리 설정모드 클릭시
	 * 대분류 카테고리 가져옴
	 */
	@RequestMapping(value="setTileCategory", method=RequestMethod.POST)
			/*, produces="application/json; charset=utf-8",
			headers = "content-type=application/x-www-form-urlencoded")*/
	@ResponseBody
	public String setTileCategory(@RequestBody JSONObject jObject) throws Exception {

		try {
			
		Map map = new ObjectMapper().readValue(jObject.toString(), HashMap.class);
		
		System.out.println(jObject);
		
		System.out.println(map.get("tileList"));
		
		categoryService.insertDetail_category_location(map);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		JSONObject result = new JSONObject();
		
		result.put("status", "success");

		return result.toString();
	}

	/* shop_Register.js
	 * 매장등록 페이지에서 타일클릭 후 타일에 비콘이 등록되어 있지 않을 때
	 * 비콘등록 버튼을 누를 시 디비에서 사용가능한 비콘의 정보들을 불러와서 아작스로 전송 
	 */
	@RequestMapping(value="getBeaconList", method=RequestMethod.POST)
	@ResponseBody
	public String getBeacon(HttpSession session) throws Exception {

		int bhf_code = (int) session.getAttribute("bhf_code");
		List<BeaconVO> beaconList = beaconService.selectSetBeaconList(bhf_code);

		String str = new Gson().toJson(beaconList);

		System.out.println(str);

		return str;
	}


	/* dashBoard.js
	 * 대쉬보드에서 도면의 타일을 선택할 경우 발생하는 아작스 통신
	 * 현재 이 메서드는 아직 제대로 된 데이터를 긁어오는 것이 아님
	 */
	@RequestMapping(value="dashBoardTile", method=RequestMethod.POST)
	@ResponseBody
	public String dashBoardTile(@RequestParam("drw_code") int drw_code,
			@RequestParam("X_index") int X_index, @RequestParam("Y_index") int Y_index) throws Exception {

		logger.info("X = " + X_index + "  Y = " + Y_index);

		TileVO clickTile = new TileVO();
		clickTile.setDrw_code(drw_code);
		clickTile.setTile_crdnt_x(X_index);
		clickTile.setTile_crdnt_y(Y_index);

		HashMap<String, String> tile = tileService.selectTile_LocationOne(clickTile);

		String str = new Gson().toJson(tile);

		System.out.println(str);

		return str;
	}

	/* shop_Register.js
	 * 타일에 비콘정보를 셋팅하는 아작스
	 */
	@RequestMapping(value="setTileBeacon", method=RequestMethod.POST)
	@ResponseBody
	public String setTileBeacon(@RequestBody JSONObject json) throws Exception {

		System.out.println( json.get("tile_code") + "  타일  " + json.get("beacon_mjr") + "  비콘 데이터  " + json.get("beacon_mnr") );

		HashMap<String, String> vo = new HashMap<String, String>();


		vo.put("tile_code", json.get("tile_code").toString());
		vo.put("beacon_mjr", json.get("beacon_mjr").toString());
		vo.put("beacon_mnr", json.get("beacon_mnr").toString());

		tileService.updateTileBeaconSet(vo);

		// 현재 이부분에 실제 비콘정보가 타일로 들어가야함.
		/*
		 *  현재 shop_Register.js 에 이부분과 연동되는 아작스가 있지만
		 *  제대로 코딩이 되지 않음 양쪽 부분 다 손봐야함
		 */
		return "success";
	}
	
	
	/* shop_Register.js
	 * 층 변동시 타일 정보 리로드
	 */
	@RequestMapping(value="tileReload", method=RequestMethod.POST)
	@ResponseBody
	public String tileReload(@RequestParam("floor") int floor, HttpSession session) throws Exception {

		Map map = new HashMap();
		
		int bhf_code = (int) session.getAttribute("bhf_code");
		floor += 1;
		
		map.put("bhf_code", bhf_code);
		map.put("floor", floor);
		
		List<HashMap<String, String>> tileList = tileService.selectTileListUp(map);

		String str = new Gson().toJson(tileList);

		return str;
		
	}

	/* shop_Register.js
	 * 매장 등록 버튼 클릭시 아작스로 존재하는 매장과, 층정보를 db에서 검색하여 넘겨주는 역할
	 */

	@RequestMapping(value="shop_RegisterForm", method=RequestMethod.GET, produces = "text/plain; charset=UTF-8")
	@ResponseBody
	public String shop_RegisterForm() throws Exception{


		// 지점 정보들 불러오기
		List<HashMap<String, String>> branchList = branchService.selectBranchNameList();

		String str = new Gson().toJson(branchList);

		System.out.println(str);

		return str;
	}


	/* dashBoard.js
	 * 매장등록 페이지에서 도면위의 타일을 클릭햇을때 발생하는 아작스 통신
	 * 해당 타일에서 발생한 고객들의 정보에 따른 데이터들을 여러 분류로 나눠서
	 * 대쉬보드에서 표시할 수 있도록 보내줌
	 */
	@RequestMapping(value="getTileData", method=RequestMethod.POST)
	@ResponseBody
	public String getTileData(int drw_code, int X_index, int Y_index) throws Exception {

		logger.info("X = " + X_index + "  Y = " + Y_index);

		TileVO vo = new TileVO();
		vo.setDrw_code(drw_code);
		vo.setTile_crdnt_x(X_index);
		vo.setTile_crdnt_y(Y_index);

		// HashMap<String, String> tile = tileService.selectTile_LocationOne(Map_XY);

		// 필요한 데이터들 디비에서 끌어와서 저장
		HashMap<String, String> pro = courseService.tileProbability(vo);
		List<HashMap<String, String>> user = courseService.tileUserinfo(vo);

		// 가져온 데이터 전체 넣기 위한 jsonObject
		JSONObject json = new JSONObject();

		// db에서 가져온 데이터들 json으로 변환
		JsonArray jsonUser = (JsonArray) new JSONParser().parse(new Gson().toJson(user));
		JSONObject jsonPro = (JSONObject) new JSONParser().parse(new Gson().toJson(pro));

		// jsonObject에 모든 데이터들을 저장
		json.put("jsonUser", jsonUser);
		json.put("jsonPro", jsonPro);
		json.put("status", "success");

		return json.toString();
	}

	// 타일 하나의 고객 평균 머문 시간
	@RequestMapping(value="oneTileAvgTime", method=RequestMethod.GET)
	@ResponseBody
	public JSONObject oneTileAvgTime(int day, int drw_code, int tile_crdnt_x, int tile_crdnt_y) throws Exception{
		List<HashMap> list = courseService.oneTileAvgTime(day, drw_code, tile_crdnt_x, tile_crdnt_y);

		JSONObject jsonObj = new JSONObject();
		jsonObj.put("avgTime", list.get(0).get("avgTime"));
		jsonObj.put("tile_nm", list.get(0).get("tile_nm"));
		
		return jsonObj;
	}

	@RequestMapping(value="tileGender", method=RequestMethod.GET)
	@ResponseBody
	public String tileGender(int day) throws Exception{
		List<HashMap> list = courseService.tileGender(day);

		JSONArray tileGenderArray = new JSONArray();
		for(int i = 0; i < list.size(); i++){

			JSONObject tileGenderObj = new JSONObject();

			tileGenderObj.put("probability", list.get(i).get("probability"));
			tileGenderObj.put("user_sexdstn", list.get(i).get("user_sexdstn"));

			tileGenderArray.add(tileGenderObj);			
		}

		JSONObject jsonObj = new JSONObject();
		jsonObj.put("tileGender", tileGenderArray);

		return jsonObj.toJSONString();
	}
	
	
	
	

	@RequestMapping(value="tileAge", method=RequestMethod.GET)
	@ResponseBody
	public String tileAge(int day) throws Exception{
		List<HashMap> list = courseService.tileAge(day);

		JSONArray tileAgeArray = new JSONArray();
		for(int i = 0; i < list.size(); i++){

			JSONObject tileAgeObj = new JSONObject();

			tileAgeObj.put("probability", list.get(i).get("probability"));
			tileAgeObj.put("agegroup", list.get(i).get("agegroup").toString());

			tileAgeArray.add(tileAgeObj);			
		}

		JSONObject jsonObj = new JSONObject();
		jsonObj.put("tileAge", tileAgeArray);

		return jsonObj.toJSONString();
	}
	
	@RequestMapping(value="oneTileGender", method=RequestMethod.GET)
	@ResponseBody
	public JSONObject oneTileGender(int day, int drw_code, int tile_crdnt_x, int tile_crdnt_y) throws Exception{
		
		List<HashMap> list = courseService.oneTileGender(day, drw_code, tile_crdnt_x, tile_crdnt_y);

		System.out.println("list : " + list.toString());
		JSONArray tileGenderArray = new JSONArray();
		for(int i = 0; i < list.size(); i++){

			JSONObject tileGenderObj = new JSONObject();

			tileGenderObj.put("probability", list.get(i).get("probability"));
			tileGenderObj.put("user_sexdstn", list.get(i).get("user_sexdstn"));

			tileGenderArray.add(tileGenderObj);			
		}

		JSONObject jsonTileObj = new JSONObject();
		jsonTileObj.put("oneTileGender", tileGenderArray);

		return jsonTileObj;
	}
	
	
	@RequestMapping(value="oneTileAge", method=RequestMethod.GET)
	@ResponseBody
	public String oneTileAge(int day, int drw_code, int tile_crdnt_x, int tile_crdnt_y) throws Exception{
		List<HashMap> list = courseService.oneTileAge(day, drw_code, tile_crdnt_x, tile_crdnt_y);

		JSONArray tileAgeArray = new JSONArray();
		for(int i = 0; i < list.size(); i++){

			JSONObject tileAgeObj = new JSONObject();

			tileAgeObj.put("probability", list.get(i).get("probability"));
			tileAgeObj.put("agegroup", list.get(i).get("agegroup").toString());

			tileAgeArray.add(tileAgeObj);			
		}

		JSONObject jsonObj = new JSONObject();
		jsonObj.put("tileAge", tileAgeArray);

		return jsonObj.toJSONString();
	}
	
	
	@RequestMapping(value="tileTotal", method=RequestMethod.GET)
	@ResponseBody
	public String tileTotal(int day, int drw_code, int tile_crdnt_x, int tile_crdnt_y) throws Exception{
		List<HashMap> list = courseService.tileTotal(day, drw_code, tile_crdnt_x, tile_crdnt_y);

		JSONArray tileTotalArray = new JSONArray();
		for(int i = 0; i < list.size(); i++){

			JSONObject tileTotalObj = new JSONObject();

			tileTotalObj.put("probability", list.get(i).get("probability"));
			tileTotalObj.put("cours_pasng_time", list.get(i).get("cours_pasng_time").toString());
			tileTotalObj.put("tile_visit", list.get(i).get("tile_visit"));

			tileTotalArray.add(tileTotalObj);			
		}

		JSONObject jsonObj = new JSONObject();
		jsonObj.put("tileTotal", tileTotalArray);

		return jsonObj.toJSONString();
	}
}

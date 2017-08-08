package yjc.wdb.scts;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;

import yjc.wdb.scts.bean.Floor_informationVO;
import yjc.wdb.scts.service.CourseService;
import yjc.wdb.scts.service.Floor_informationService;
import yjc.wdb.scts.util.MediaUtils;
import yjc.wdb.scts.util.UploadFileUtils;


@Controller
public class FileUploadController {
	private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);
	
	//@Autowired
	//@Inject
	@Resource(name = "uploadPath")
	private String uploadPath;
	
	@Resource(name = "drawingPath")
	private String drawingPath;
	
	@Inject
	Floor_informationService floor_informationService;
	
	@Inject
	CourseService courseService;

	
	@RequestMapping(value="shop_RegisterForm", method=RequestMethod.POST)
	public String shop_RegisterFormPost(HttpSession session, Model model,
			@RequestParam("file") MultipartFile file, @RequestParam("bhf_code") int bhf_code, 
			@RequestParam("floorinfo_floor") String floorinfo_floor,
			@RequestParam("size_x") int size_x,
			@RequestParam("size_y") int size_y) throws Exception {
		// 메인 콘텐츠에서 어떤 페이지를 보여 줄 것인지 저장할 변수.
		String ContentPage = "dashBoard";

		// 실제 뷰 페이지로 메인 콘텐츠 페이지 정보를 넘겨준다.
		model.addAttribute("main_content", ContentPage);
		
		System.out.println(file.getOriginalFilename());
		
		String savedName = UploadFileUtils.uploadDrawingFile(file.getOriginalFilename(), drawingPath, file.getBytes());
		
		Floor_informationVO vo = new Floor_informationVO();
		
		vo.setBhf_code(bhf_code);
		vo.setFloorinfo_floor(floorinfo_floor);
		vo.setSize_x(size_x);
		vo.setSize_y(size_y);

		floor_informationService.register_shop(savedName, vo);

		return "redirect:mainPage";
	}

	//@RequestMapping(value="uploadAjax", method=RequestMethod.POST, produces = "text/plain; charset=UTF-8")
	@RequestMapping(value="getDrawingFileName", produces = "text/plain; charset=UTF-8")
	@ResponseBody
	public String getDrawingFileName(int floor, HttpSession session) throws Exception {

		floor += 1;
		int bhf_code = (int) session.getAttribute("bhf_code");
		
		HashMap map = floor_informationService.selectDrawingOne(bhf_code, floor);
		
		int drw_code = Integer.parseInt(map.get("drw_code").toString());
		List<HashMap<String, String>> tileInfoList = floor_informationService.selectTileCategoryList(drw_code);
		List<HashMap<String, String>> testTileColor = courseService.testTileColor(bhf_code);
		
		//Map<String, Object> mainMap = new HashMap<String, Object>();
		// 타일 색깔 정보들
		map.put("tileInfoList", tileInfoList);
		map.put("testTileColor", testTileColor);
		//mainMap.put("tileInfoList", tileInfoList);
		//mainMap.put("drw_code", drw_code);
		
		String str = new Gson().toJson(map);
		
		return str;
	}


	@RequestMapping(value="loadCategory", produces = "text/plain; charset=UTF-8")
	@ResponseBody
	public String loadCategory(int floor, HttpSession session) throws Exception {

		floor += 1;
		int bhf_code = (int) session.getAttribute("bhf_code");

		HashMap map = floor_informationService.selectDrawingOne(bhf_code, floor);

		int drw_code = Integer.parseInt(map.get("drw_code").toString());
		List<HashMap> categoryList = courseService.categoryTypeMap(drw_code);

		map.put("categoryList", categoryList);
		
		String str = new Gson().toJson(map);
		
		return str;
	}

	@RequestMapping(value="loadZone", produces = "text/plain; charset=UTF-8")
	@ResponseBody
	public String loadZone(int floor, HttpSession session) throws Exception {
		int bhf_code = (int) session.getAttribute("bhf_code");

		floor += 1;
		HashMap map = floor_informationService.selectDrawingOne(bhf_code, floor);

		int drw_code = Integer.parseInt(map.get("drw_code").toString());
		List<HashMap> zoneList = courseService.zoneTypeMap(drw_code);

		map.put("zoneList", zoneList);
		
		String str = new Gson().toJson(map);
		
		return str;
	}
	
	
	@RequestMapping("displayDrawing")
	@ResponseBody
	public ResponseEntity<byte[]> displayFile(String fileName) throws Exception {
		ResponseEntity<byte[]> entity = null;
		
		String ext = fileName.substring(fileName.lastIndexOf(".")+1);
		MediaType mediaType = MediaUtils.getMediaType(ext);
		
		InputStream in = null;
		
		logger.info("File Name: " + fileName);
		
		HttpHeaders headers = new HttpHeaders();
		
		try {
			in = new FileInputStream(drawingPath + fileName);
			if(mediaType != null) {
				headers.setContentType(mediaType);
			}
			else {
				fileName = fileName.substring(fileName.indexOf("_")+1);
				headers.setContentType(mediaType.APPLICATION_OCTET_STREAM);
				String fN = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
				headers.add("Content-Dissponstion", "attachment; filename=\""+ fN + "\"");
			}

			byte[] data = IOUtils.toByteArray(in);
			entity = new ResponseEntity<byte[]>(data,headers,HttpStatus.CREATED);
		} catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		} finally {
			if(in != null) in.close();
		}
		
		return entity;
	}
	
	/*
	@RequestMapping(value="upload", method=RequestMethod.POST)
	public String upload(MultipartFile file, Model model) throws Exception{
		logger.debug("uploadFormPost Requested debug");
		logger.info("originalName:" + file.getOriginalFilename() );
		logger.info("size:" + file.getSize() );
		logger.info("contentType:" + file.getContentType() );
		logger.info("server FileName:" + file.getName() );
		
		String savedName = UploadFileUtils.uploadFile(file.getOriginalFilename(), uploadPath, file.getBytes());
		
		model.addAttribute("savedName", savedName);
		
		return "uploadResult";
	}

	@RequestMapping(value="uploadAjax", method=RequestMethod.GET)
	public void uploadAjax() {
		
	}

	@RequestMapping(value="uploadAjax", method=RequestMethod.POST, produces = "text/plain; charset=UTF-8")
	@ResponseBody
	public ResponseEntity<String> uploadAjax(MultipartFile file) throws Exception {
		logger.info("originalName:" + file.getOriginalFilename());
		
		String savedName = UploadFileUtils.uploadFile(file.getOriginalFilename(), uploadPath, file.getBytes());
		
		
		return new ResponseEntity<String>(savedName, HttpStatus.CREATED);
	}
	
	@ResponseBody
	@RequestMapping("displayFile")
	public ResponseEntity<byte[]> displayFile(String fileName) throws Exception {
		ResponseEntity<byte[]> entity = null;
		
		String ext = fileName.substring(fileName.lastIndexOf(".")+1);
		MediaType mediaType = MediaUtils.getMediaType(ext);
		
		InputStream in = null;
		
		logger.info("File Name: " + fileName);
		
		HttpHeaders headers = new HttpHeaders();
		
		try {
			in = new FileInputStream(uploadPath + fileName);
			if(mediaType != null) {
				headers.setContentType(mediaType);
			}
			else {
				fileName = fileName.substring(fileName.indexOf("_")+1);
				headers.setContentType(mediaType.APPLICATION_OCTET_STREAM);
				String fN = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
				headers.add("Content-Dissponstion", "attachment; filename=\""+ fN + "\"");
			}

			byte[] data = IOUtils.toByteArray(in);
			entity = new ResponseEntity<byte[]>(data,headers,HttpStatus.CREATED);
		} catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		} finally {
			if(in != null) in.close();
		}
		
		return entity;
	}
	
	@ResponseBody
	@RequestMapping(value="deleteFile", method=RequestMethod.DELETE)
//	public ResponseEntity<String> deleteFile(HttpServletRequest request, @RequestBody String fileName) throws Exception{
	public ResponseEntity<String> deleteFile(HttpServletRequest request, @RequestBody HashMap<String, String> list) throws Exception{
		String fileName = list.get("fileName");
		
		
		System.out.println("삭제시 파일 이름" + fileName.toString());
		
		String ext = fileName.substring(fileName.lastIndexOf(".")+1);
		
		MediaType mType = MediaUtils.getMediaType(ext);
		if(mType != null) {
			String folderPath = fileName.substring(0, 12);
			String orgName = fileName.substring(12+"thumbNail_".length());
			File orgImgFile = new File(uploadPath + folderPath + orgName);
			System.out.println("이미지 삭제시 절대경로" + orgImgFile.getAbsolutePath());
			orgImgFile.delete();
		}
		File orgFile = new File(uploadPath+fileName);
		System.out.println("삭제시 절대경로" + orgFile.getAbsolutePath());
		orgFile.delete();
		

		ResponseEntity<String> entity = new ResponseEntity<String>("deleted", HttpStatus.OK);
		return entity;
	}
	*/
}

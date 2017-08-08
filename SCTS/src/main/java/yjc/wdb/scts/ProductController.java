package yjc.wdb.scts;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;

import yjc.wdb.scts.bean.GoodsVO;
import yjc.wdb.scts.bean.PageMaker;
import yjc.wdb.scts.bean.PageVO;
import yjc.wdb.scts.service.GoodsService;
import yjc.wdb.scts.util.MediaUtils;
import yjc.wdb.scts.util.UploadFileUtils;

@Controller
public class ProductController {

	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

	@Inject
	private GoodsService goodsService;

	@Resource(name = "uploadPath")
	private String uploadPath;

	   // 상품 리스트
	   @RequestMapping(value = "product_List", method = RequestMethod.GET)
	   public String product_List(@ModelAttribute("cri") PageVO cri, HttpServletRequest request, HttpSession session, Model model) throws Exception {
	   
	      String ContentPage = "product_List";
	      model.addAttribute("main_content", ContentPage);

	      List<GoodsVO> GoodsList = goodsService.selectPageList(cri);
	      PageMaker pageMaker = new PageMaker();
	      pageMaker.setCri(cri);
	      pageMaker.setTotalCount(goodsService.countSearch(cri));
	      model.addAttribute("GoodsList", GoodsList);

	      return "mainPage";
	   }

	// 상품 등록 폼 호출
	@RequestMapping(value = "product_Register", method = RequestMethod.GET)
	public String product_Register(HttpServletRequest request, HttpSession session, Model model) {
		String ContentPage = "product_Register";

		model.addAttribute("main_content", ContentPage);

		return "mainPage";
	}

	// 상품등록 처리
	@RequestMapping(value = "product_Register", method = RequestMethod.POST)
	public String product_RegisterPost(GoodsVO vo) throws Exception {

		System.out.println("GoodsVO 정보 : " + vo.getDetailctgry_code() + "  상품명 : " + vo.getGoods_nm());
		goodsService.insertGoods(vo);

		return "redirect:product_List";
	}

	@RequestMapping(value = "upload", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public ResponseEntity<String> upload(MultipartFile file) throws Exception {
		logger.info("originalName:" + file.getOriginalFilename());
		logger.info("size:" + file.getSize());
		logger.info("contentType:" + file.getContentType());
		String savedName = UploadFileUtils.uploadFile(file.getOriginalFilename(), uploadPath, file.getBytes());

		return new ResponseEntity<String>(savedName, HttpStatus.CREATED);
	}
	
	@RequestMapping(value="getProductImg", produces = "text/plain; charset=UTF-8")
	@ResponseBody
	public String getProductImg(int product_id) throws Exception {
		
		
		return uploadPath;
		
	}

	// 상품 정보
	@RequestMapping(value = "product_Info", method = RequestMethod.GET)
	public String product_Info(HttpServletRequest request, HttpSession session, Model model,
			@RequestParam int product_id) throws Exception {
		String ContentPage = "product_Info";

		model.addAttribute("main_content", ContentPage);

		GoodsVO goods = goodsService.selectGoodsOne(product_id);

		model.addAttribute("goods", goods);

		return "mainPage";
	}
	
	@ResponseBody
	@RequestMapping("displayFile")
	public ResponseEntity<byte[]> displayFile(String fileName) throws Exception{
			
		ResponseEntity<byte[]> entity = null;
		
		String ext = fileName.substring(fileName.lastIndexOf(".")+1);
		
		MediaType mediaType = MediaUtils.getMediaType(ext);
		InputStream in = null;
		
		logger.info("File Name : "+fileName);
		
		HttpHeaders headers = new HttpHeaders();
		
		try{
			in = new FileInputStream(uploadPath+fileName);
			if(mediaType != null){
				headers.setContentType(mediaType);
			}else{
				fileName = fileName.substring(fileName.indexOf("_")+1);
				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
				String fN = new String(fileName.getBytes("UTF-8"),"ISO-8859-1");
				headers.add("Content-Dispostion","attachment; filename = \""+fN+"\"");
			}
			
			byte[] data = IOUtils.toByteArray(in);
			entity = new ResponseEntity<byte[]>(data, headers, HttpStatus.CREATED);
			
		}catch(Exception e){	
				e.printStackTrace();
				entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
			
		}finally{
			in.close();	
		}
		return entity;
		
	}
}

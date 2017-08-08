package yjc.wdb.scts;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class StyleController {
	
	@RequestMapping(value="/example", method=RequestMethod.GET)
	public String login() {
		return "frontPage";
	}
	
	@RequestMapping(value="/sideBar", method=RequestMethod.GET)
	public String sideBar() {
		return "sideBar";
	}
	
}

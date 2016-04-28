package com.estsoft.mysite.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
	
	/**
	 *  Logger 생성
	 */
	private static final Log LOG = LogFactory.getLog( MainController.class );

	
	@RequestMapping("/main")
	public String index(){
		LOG.debug("index called");
		return "main/index";
	}
	
	
}

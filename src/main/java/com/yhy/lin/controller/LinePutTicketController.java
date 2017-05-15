package com.yhy.lin.controller;

import javax.servlet.http.HttpServletRequest;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 线路放票管理
 * @author linj
 *
 */
@Controller
@RequestMapping("/linePutTicketController")
public class LinePutTicketController extends BaseController {
	@Autowired
	private SystemService systemService;
	
	
	@RequestMapping(params="linePutTicketList")
	public ModelAndView linePutTicketList(HttpServletRequest request){
		return new ModelAndView("/yhy/putTicket/linePutTicketList");
	}
	
}

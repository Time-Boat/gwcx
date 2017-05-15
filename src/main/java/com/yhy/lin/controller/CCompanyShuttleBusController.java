package com.yhy.lin.controller;

import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 公司班车信息查询
 * @author linj
 *
 */
@Controller
@RequestMapping("/cCompanyShuttleBusController")
public class CCompanyShuttleBusController extends BaseController{
	@Autowired
	private SystemService systemService;
	
	@RequestMapping(params="cCompanyShuttleBusInfoList")
	public ModelAndView cCompanyShuttleBusInfoList(HttpServletRequest request){
		return new ModelAndView("/yhy/cCompany/cCompanyShuttleBusInfoList");
	}
}

package com.yhy.lin.controller;

import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 包车管理
 * @author linj
 *
 */
@Controller
@RequestMapping("/cCompanyCharteredController")
public class CCompanyCharteredController extends BaseController{
	@Autowired
	private SystemService systemService;
	
	@RequestMapping(params="cCompanyCharteredList")
	public ModelAndView cCompanyShuttleBusInfoList(HttpServletRequest request){
		return new ModelAndView("/yhy/cCompany/cCompanyCharteredList");
	}
}

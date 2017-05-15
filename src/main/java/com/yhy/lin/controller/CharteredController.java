package com.yhy.lin.controller;

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
@RequestMapping("/charteredController")
public class CharteredController extends BaseController{
	@Autowired
	private SystemService systemService;
	
	/*
	 *	包车列表页面
	 */
	@RequestMapping(params="charteredList")
	public ModelAndView charteredList(){
		return new ModelAndView("/yhy/car/charteredList");
	}
	
	/*
	 *	包车订单管理页面
	 */
	@RequestMapping(params="charterOrderList")
	public ModelAndView charterOrderList(){
		return new ModelAndView("/yhy/car/charterOrderList");
	}
	
	
}

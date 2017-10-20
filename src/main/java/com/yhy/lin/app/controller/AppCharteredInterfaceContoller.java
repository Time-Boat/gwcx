package com.yhy.lin.app.controller;

import org.apache.log4j.Logger;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yhy.lin.app.service.AppCharteredInterfaceService;

/**
* Description : app包车接口
* @author Timer
* @date 2017年10月19日 下午4:08:28
*/
@Controller
@RequestMapping(value = "/charteredApp")
public class AppCharteredInterfaceContoller  extends AppBaseController {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(AppInterfaceController.class);

	@Autowired
	private AppCharteredInterfaceService appCharteredService;
	
	@Autowired
	private SystemService systemService;
	
	
	
	
}

package com.yhy.lin.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.tag.core.easyui.TagUtil;

import com.yhy.lin.app.entity.CarCustomerEntity;
import com.yhy.lin.entity.BusStopInfoEntity;
import com.yhy.lin.service.CarCustomerServiceI;

import net.sf.json.JSONObject;

/**   
 * @Title: Controller
 * @Description: 乘客表
 * @author zhangdaihao
 * @date 2017-06-15 10:13:25
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/carCustomerController")
public class CarCustomerController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(CarCustomerController.class);

	@Autowired
	private CarCustomerServiceI carCustomerService;

	/**
	 * 乘客表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("yhy/customer/carCustomerList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */
	@RequestMapping(params = "datagrid")
	public void datagrid(CarCustomerEntity carCustomer,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(CarCustomerEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, carCustomer, request.getParameterMap());
		//String customerId = request.getParameter("customerId");
		///String commonAddrs = request.getParameter("commonAddrs");
		//JSONObject jObject = carCustomerService.getDatagrid(carCustomer, dataGrid,customerId,commonAddrs);
		//responseDatagrid(response, jObject);
		this.carCustomerService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 常用地址查看页面
	 */
	@RequestMapping(params = "check")
	public ModelAndView lineAddBusStop(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("id", request.getParameter("id"));
		return new ModelAndView("yhy/customer/checkAddr");
	}
	
	/**
	 * 根据线路id查询对应的站点信息  
	 */
	@RequestMapping(params = "addrDatagrid")
	public void roleUserDatagrid(CarCustomerEntity carCustomer,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        String id = request.getParameter("id");
        
        JSONObject jObject = carCustomerService.getDatagrid(id,dataGrid);
        responseDatagrid(response, jObject);
	}
}

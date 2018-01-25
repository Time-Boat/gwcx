package com.yhy.lin.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;

import com.yhy.lin.entity.CarNumberEntity;
import com.yhy.lin.service.CarNumberServiceI;

/**   
 * @Title: Controller
 * @Description: 上车人数表
 * @author zhangdaihao
 * @date 2018-01-23 16:36:36
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/carNumberController")
public class CarNumberController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(CarNumberController.class);

	@Autowired
	private CarNumberServiceI carNumberService;
	@Autowired
	private SystemService systemService;

	/**
	 * 上车人数表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "carNumberList")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("yhy/car/carNumberList");
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
	public void datagrid(CarNumberEntity carNumber,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(CarNumberEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, carNumber, request.getParameterMap());
		this.carNumberService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除上车人数表
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(CarNumberEntity carNumber, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		carNumber = systemService.getEntity(CarNumberEntity.class, carNumber.getId());
		message = "上车人数表删除成功";
		carNumberService.delete(carNumber);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加上车人数表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(CarNumberEntity carNumber, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(carNumber.getId())) {
			message = "上车人数表更新成功";
			CarNumberEntity t = carNumberService.get(CarNumberEntity.class, carNumber.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(carNumber, t);
				carNumberService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = "上车人数表更新失败";
			}
		} else {
			message = "上车人数表添加成功";
			carNumberService.save(carNumber);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 上车人数表列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(CarNumberEntity carNumber, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(carNumber.getId())) {
			carNumber = carNumberService.getEntity(CarNumberEntity.class, carNumber.getId());
			req.setAttribute("carNumberPage", carNumber);
		}
		return new ModelAndView("yhy/car/carNumber");
	}
	
}

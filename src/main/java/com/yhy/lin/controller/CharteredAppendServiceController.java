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
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;

import com.yhy.lin.app.util.AppUtil;
import com.yhy.lin.entity.CharteredAppendServiceEntity;
import com.yhy.lin.service.CharteredAppendServiceServiceI;
import net.sf.json.JSONObject;

import javax.validation.Validator;

/**   
 * @Title: Controller
 * @Description: 包车服务
 * @author zhangdaihao
 * @date 2017-10-20 10:55:22
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/charteredAppendServiceController")
public class CharteredAppendServiceController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(CharteredAppendServiceController.class);

	@Autowired
	private CharteredAppendServiceServiceI charteredAppendServiceService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * 包车服务列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "charteredAppendServiceList")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("yhy/charteredAppendService/charteredAppendServiceList");
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
	public void datagrid(CharteredAppendServiceEntity charteredAppendService,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		String cityID = request.getParameter("cityID");// 城市
		JSONObject jObject = charteredAppendServiceService.getDatagrid(dataGrid,charteredAppendService);
		responseDatagrid(response, jObject);
	}

	/**
	 * 删除包车服务
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(CharteredAppendServiceEntity charteredAppendService, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		charteredAppendService = systemService.getEntity(CharteredAppendServiceEntity.class, charteredAppendService.getId());
		message = "包车服务删除成功";
		charteredAppendServiceService.delete(charteredAppendService);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加包车服务
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(CharteredAppendServiceEntity charteredAppendService, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		String userId = ResourceUtil.getSessionUserName().getId();
		if (StringUtil.isNotEmpty(charteredAppendService.getId())) {
			message = "包车服务更新成功";
			CharteredAppendServiceEntity t = charteredAppendServiceService.get(CharteredAppendServiceEntity.class, charteredAppendService.getId());
			try {
				charteredAppendService.setMidifyTime(AppUtil.getDate());
				MyBeanUtils.copyBeanNotNull2Bean(charteredAppendService, t);
				charteredAppendServiceService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = "包车服务更新失败";
			}
		} else {
			charteredAppendService.setCreateUserId(userId);
			charteredAppendService.setCreateTime(AppUtil.getDate());
			message = "包车服务添加成功";
			charteredAppendServiceService.save(charteredAppendService);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 包车服务列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(CharteredAppendServiceEntity charteredAppendService, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(charteredAppendService.getId())) {
			charteredAppendService = charteredAppendServiceService.getEntity(CharteredAppendServiceEntity.class, charteredAppendService.getId());
			req.setAttribute("charteredAppendServicePage", charteredAppendService);
		}
		return new ModelAndView("yhy/charteredAppendService/charteredAppendService");
	}
	
}

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

import com.yhy.lin.entity.CharteredPackageEntity;
import com.yhy.lin.service.CharteredPackageServiceI;

/**   
 * @Title: Controller
 * @Description: 包车套餐设置
 * @author zhangdaihao
 * @date 2017-10-17 17:05:06
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/charteredPackageController")
public class CharteredPackageController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(CharteredPackageController.class);

	@Autowired
	private CharteredPackageServiceI charteredPackageService;
	@Autowired
	private SystemService systemService;

	/**
	 * 包车套餐设置列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("yhy/charteredPackage/charteredPackageList");
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
	public void datagrid(CharteredPackageEntity charteredPackage,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(CharteredPackageEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, charteredPackage, request.getParameterMap());
		this.charteredPackageService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除包车套餐设置
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(CharteredPackageEntity charteredPackage, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		charteredPackage = systemService.getEntity(CharteredPackageEntity.class, charteredPackage.getId());
		message = "包车套餐设置删除成功";
		charteredPackageService.delete(charteredPackage);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加包车套餐设置
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(CharteredPackageEntity charteredPackage, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(charteredPackage.getId())) {
			message = "包车套餐设置更新成功";
			CharteredPackageEntity t = charteredPackageService.get(CharteredPackageEntity.class, charteredPackage.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(charteredPackage, t);
				charteredPackageService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = "包车套餐设置更新失败";
			}
		} else {
			message = "包车套餐设置添加成功";
			charteredPackageService.save(charteredPackage);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 包车套餐设置列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(CharteredPackageEntity charteredPackage, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(charteredPackage.getId())) {
			charteredPackage = charteredPackageService.getEntity(CharteredPackageEntity.class, charteredPackage.getId());
			req.setAttribute("charteredPackagePage", charteredPackage);
		}
		return new ModelAndView("yhy/charteredPackage/charteredPackage");
	}
	
}

package com.yhy.lin.controller;

import java.util.List;

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
import org.jeecgframework.core.util.ResourceUtil;

import javax.validation.Validator;

import com.yhy.lin.entity.CitiesEntity;
import com.yhy.lin.entity.OpenCityEntity;
import com.yhy.lin.entity.ProvincesEntity;
import com.yhy.lin.service.OpenCityServiceI;

import net.sf.json.JSONObject;

/**   
 * @Title: Controller
 * @Description: 业务开通城市
 * @author zhangdaihao
 * @date 2017-05-17 17:37:52
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/openCityController")
public class OpenCityController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(OpenCityController.class);

	@Autowired
	private OpenCityServiceI openCityService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;

	/**
	 * 业务开通城市列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		
		return new ModelAndView("yhy/openCity/openCityList");
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
	public void datagrid(OpenCityEntity openCity,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
//		CriteriaQuery cq = new CriteriaQuery(OpenCityEntity.class, dataGrid);
//		//查询条件组装器
//		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, openCity, request.getParameterMap());
//		this.openCityService.getDataGridReturn(cq, true);
//		TagUtil.datagrid(response, dataGrid);
		
		JSONObject jObject = openCityService.getDatagrid(dataGrid);
		responseDatagrid(response, jObject);
	}

	/**
	 * 删除业务开通城市
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(OpenCityEntity openCity, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		openCity = systemService.getEntity(OpenCityEntity.class, openCity.getId());
		message = "业务开通城市删除成功";
		openCityService.delete(openCity);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}

	/**
	 * 添加业务开通城市
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(OpenCityEntity openCity, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		openCity.setCreatePeople(ResourceUtil.getSessionUserName().getUserName());
		if (StringUtil.isNotEmpty(openCity.getId())) {
			message = "业务开通城市更新成功";
			OpenCityEntity t = openCityService.get(OpenCityEntity.class, openCity.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(openCity, t);
				openCityService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = "业务开通城市更新失败";
			}
		} else {
			message = "业务开通城市添加成功";
			openCityService.save(openCity);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 业务开通城市列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(OpenCityEntity openCity, HttpServletRequest request) {
		List<ProvincesEntity> pList = systemService.getList(ProvincesEntity.class);
		request.setAttribute("pList", pList);
		if (StringUtil.isNotEmpty(openCity.getId())) {
			openCity = openCityService.getEntity(OpenCityEntity.class, openCity.getId());
			request.setAttribute("openCityPage", openCity);
			
			String provinceId = openCity.getProvinceId();
			List<CitiesEntity> cities = systemService.findByProperty(CitiesEntity.class, "provinceId", provinceId);
			request.setAttribute("cities", cities);
		}
		return new ModelAndView("yhy/openCity/openCity");
	}
	
	/**
	 * 业务开通城市列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "getCitys")
	@ResponseBody
	public String getCitys(HttpServletRequest request) {
		
		String provinceId = request.getParameter("provinceId");
		List<CitiesEntity> cList = systemService.findByProperty(CitiesEntity.class, "provinceId", provinceId);
		
		StringBuffer sbf = new StringBuffer("[");
		for(CitiesEntity c : cList){
			sbf.append("{'city':'"+c.getCity()+"',");
			sbf.append("'cityId':'"+c.getCityId()+"'},");
		}
		if(cList.size()>0)
			sbf.deleteCharAt(sbf.length()-1);
		
		sbf.append("]");
		
		return sbf.toString();
	}
	
}

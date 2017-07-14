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

import com.yhy.lin.entity.AreaLineEntity;
import com.yhy.lin.service.AreaLineServiceI;

import net.sf.json.JSONObject;

/**   
 * @Title: Controller
 * @Description: 包车区域线路
 * @author zhangdaihao
 * @date 2017-07-14 09:23:48
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/areaLineController")
public class AreaLineController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(AreaLineController.class);

	@Autowired
	private AreaLineServiceI areaLineService;
	@Autowired
	private SystemService systemService;


	/**
	 * 包车区域线路列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("yhy/areaLine/areaLineList");
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
	public void datagrid(AreaLineEntity areaLine,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
//		CriteriaQuery cq = new CriteriaQuery(AreaLineEntity.class, dataGrid);
//		//查询条件组装器
//		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, areaLine, request.getParameterMap());
//		this.areaLineService.getDataGridReturn(cq, true);
//		TagUtil.datagrid(response, dataGrid);
		
		JSONObject jObject  = areaLineService.getDatagrid(dataGrid);
        responseDatagrid(response, jObject);
	}

	/**
	 * 删除包车区域线路
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(AreaLineEntity areaLine, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		areaLine = systemService.getEntity(AreaLineEntity.class, areaLine.getId());
		message = "包车区域线路删除成功";
		areaLineService.delete(areaLine);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加包车区域线路
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(AreaLineEntity areaLine, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(areaLine.getId())) {
			message = "包车区域线路更新成功";
			AreaLineEntity t = areaLineService.get(AreaLineEntity.class, areaLine.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(areaLine, t);
				areaLineService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = "包车区域线路更新失败";
			}
		} else {
			message = "包车区域线路添加成功";
			areaLineService.save(areaLine);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 包车区域线路列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(AreaLineEntity areaLine, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(areaLine.getId())) {
			areaLine = areaLineService.getEntity(AreaLineEntity.class, areaLine.getId());
			req.setAttribute("areaLinePage", areaLine);
		}
		return new ModelAndView("yhy/areaLine/areaLine");
	}
	
}

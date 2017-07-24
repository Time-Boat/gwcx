package com.yhy.lin.controller;

import java.util.List;
import java.util.Map;

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
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;

import com.yhy.lin.app.util.AppUtil;
import com.yhy.lin.entity.AreaLineEntity;
import com.yhy.lin.entity.AreaStationEntity;
import com.yhy.lin.entity.AreaStationLineEntity;
import com.yhy.lin.entity.BusStopInfoEntity;
import com.yhy.lin.entity.LineInfoEntity;
import com.yhy.lin.entity.OpenCityEntity;
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
		
		areaLine.setDepartId(ResourceUtil.getSessionUserName().getCurrentDepart().getId());
		areaLine.setCreatePeople(ResourceUtil.getSessionUserName().getUserName());
		areaLine.setCreateTime(AppUtil.getDate());
		
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
		
		List<OpenCityEntity> cities = systemService.findByProperty(OpenCityEntity.class, "status", "0");
		req.setAttribute("cities", cities);
		
		List<BusStopInfoEntity> stations = systemService.findHql("from BusStopInfoEntity where station_type != ?", 0);
		req.setAttribute("stations", stations);
		
		if (StringUtil.isNotEmpty(areaLine.getId())) {
			areaLine = areaLineService.getEntity(AreaLineEntity.class, areaLine.getId());
			req.setAttribute("areaLinePage", areaLine);
		}
		return new ModelAndView("yhy/areaLine/areaLine");
	}
	

//----------------------------------------站点-----------------------------------------------
	
	
	/**
	 * 获取站点
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "getStation")
	@ResponseBody
	public JSONObject getStation(AreaLineEntity areaLine, HttpServletRequest request) {
		
		//站点类型    0：机场站点     1：火车站点
		String type = request.getParameter("type");
		List<Map<String,Object>> list = systemService.findForJdbc("select id,name from busstopinfo where station_type=?", "0".equals(type) ? "2" : "1");
		JSONObject obj = new JSONObject();
		obj.put("data", list);
		return obj;
	}
	
	/**
	 * 线路挂接页面
	 */
	@RequestMapping(params = "areaAddStation")
	public ModelAndView areaAddStation(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("areaLineId", request.getParameter("areaLineId"));
		return new ModelAndView("yhy/areaLine/areaLineAddStationList");
	}
	
	/**
	 * 获取站点信息
	 */
	@RequestMapping(params = "areaStationDatagrid")
	public void areaStationDatagrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		String areaLineId = request.getParameter("areaLineId");
		JSONObject jObject  = areaLineService.getAreaStationDatagrid(dataGrid, areaLineId);
        responseDatagrid(response, jObject);
	}
	
	/**
	 * 获取站点信息
	 */	
	@RequestMapping(params = "addOrUpdateStation")
	public ModelAndView addOrUpdateStation(AreaStationEntity as, HttpServletRequest request, HttpServletResponse response) {
		
		String areaLineId = request.getParameter("areaLineId");
		request.setAttribute("areaLineId", areaLineId);
		
		AreaLineEntity areaLine = areaLineService.get(AreaLineEntity.class, areaLineId);
		
		systemService.findForJdbc("select * from area_line al join citys ", areaLineId);
		
		request.setAttribute("lineCity", areaLine.getCityId());
		//as.getId() 是关联表的id
		if(StringUtil.isNotEmpty(as.getId())){
			
			AreaStationLineEntity asl = systemService.getEntity(AreaStationLineEntity.class, as.getId());
			
			if(asl != null){
				request.setAttribute("asLine", asl);
				
				as = systemService.getEntity(AreaStationEntity.class, asl.getAreaStationId());
				request.setAttribute("aStation", as);
			}
		}
		
//		// 获取部门信息
//		List<TSDepart> departList = systemService.getList(TSDepart.class);
//		request.setAttribute("departList", departList);
//		if (StringUtil.isNotEmpty(lineInfo.getId())) {
//			lineInfo = lineInfoService.getEntity(LineInfoEntity.class, lineInfo.getId());
//			request.setAttribute("lineInfo", lineInfo);
//		}
//		//获取已开通的城市列表
//		List<OpenCityEntity> cities = systemService.findByProperty(OpenCityEntity.class, "status", "0");
//		request.setAttribute("cities", cities);
//		List<TSDepart> list = systemService.findByProperty(TSDepart.class, "orgType", "4");
//		if (list.size() > 0) {
//			request.setAttribute("list", list);
//		}
		
		return new ModelAndView("yhy/areaLine/areaLineAddStation");
	}
	
	/**
	 * 保存站点信息
	 * 
	 * @return
	 */
	@RequestMapping(params = "saveAreaStation")
	@ResponseBody
	public AjaxJson saveAreaStation(AreaStationEntity as, AreaStationLineEntity asl, HttpServletRequest request) {
		
		String message = null;
		AjaxJson j = new AjaxJson();
		as.setCreatePeople(ResourceUtil.getSessionUserName().getUserName());
		as.setCreateTime(AppUtil.getDate());
		//站点id
		String stationId = request.getParameter("stationId");
		//关联表id
		String slId = request.getParameter("slId");
		
		if (StringUtil.isNotEmpty(stationId)) {
			message = "包车区域线路更新成功";
			AreaStationEntity t = areaLineService.get(AreaStationEntity.class, stationId);
			AreaStationLineEntity t1 = areaLineService.get(AreaStationLineEntity.class, slId);
			try {
				MyBeanUtils.copyBeanNotNull2Bean(as, t);
				MyBeanUtils.copyBeanNotNull2Bean(asl, t1);
				areaLineService.saveOrUpdate(t);
				areaLineService.saveOrUpdate(t1);
			} catch (Exception e) {
				e.printStackTrace();
				message = "包车区域线路更新失败";
			}
		}else{
			message = "站点添加成功";
			systemService.saveOrUpdate(as);
			asl.setAreaStationId(as.getId());
			systemService.saveOrUpdate(asl);
		}
		
		systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		j.setMsg(message);
		return j;
	}
	
	
	/**
	 * 删除站点信息
	 * 
	 * @return
	 */
	@RequestMapping(params = "delAreaStation")
	@ResponseBody
	public AjaxJson delAreaStation(HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		
		String slId = request.getParameter("id");
		
		String stationId = request.getParameter("stationId");
		
		AreaStationEntity station = areaLineService.get(AreaStationEntity.class, stationId);
		AreaStationLineEntity stationLine = systemService.getEntity(AreaStationLineEntity.class, slId);
		message = "站点删除成功";
		areaLineService.delete(station);
		areaLineService.delete(stationLine);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}
	
}

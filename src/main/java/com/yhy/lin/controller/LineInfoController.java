package com.yhy.lin.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.druid.pool.vendor.SybaseExceptionSorter;
import com.google.gson.Gson;
import com.yhy.lin.entity.BusStopInfoEntity;
import com.yhy.lin.entity.CitiesEntity;
import com.yhy.lin.entity.LineInfoEntity;
import com.yhy.lin.entity.Line_busStopEntity;
import com.yhy.lin.entity.OpenCityEntity;
import com.yhy.lin.service.BusStopInfoServiceI;
import com.yhy.lin.service.LineInfoServiceI;

import net.sf.json.JSONObject;

/**
 * 线路管理
 * 
 * @author linj
 *
 */

@Controller
@RequestMapping("lineInfoController")
public class LineInfoController extends BaseController {
	private static final Logger logger = Logger.getLogger(LineInfoController.class);
	@Autowired
	private SystemService systemService;

	@Autowired
	private BusStopInfoServiceI busStopInfoService;

	@Autowired
	private LineInfoServiceI lineInfoService;

	/**
	 * 线路管理跳转页面
	 */
	@RequestMapping(params = "lineList")
	public ModelAndView lineList(HttpServletRequest request) {
		return new ModelAndView("yhy/lines/lineList");
	}

	/**
	 * 线路查询跳转页面
	 */
	@RequestMapping(params = "lineSearchList")
	public ModelAndView lineSearchList(HttpServletRequest request) {
		return new ModelAndView("yhy/lines/lineSearchList");
	}

	@RequestMapping(params = "datagrid")
	public void datagrid(LineInfoEntity lineInfos, HttpServletRequest request, HttpServletResponse response,
			DataGrid dataGrid) {
		String linetype = request.getParameter("linetype");// 线路类型
		String beginTime = request.getParameter("createTime_begin");
		String endTime = request.getParameter("createTime_end");
		String lstartTime_begin = request.getParameter("lstartTime_begin");
		String lstartTime_end = request.getParameter("lstartTime_end");
		String lendTime_begin = request.getParameter("lendTime_begin");
		String lendTime_end = request.getParameter("lendTime_end");
		String search = request.getParameter("search");// 用于区分线路查询页面，线路查询页面是给企业管理员使用
		JSONObject jObject = null;
		if (StringUtil.isNotEmpty(search)) {
			jObject = lineInfoService.getDatagrid(lineInfos, beginTime, endTime, dataGrid, lstartTime_begin,
					lstartTime_end, lendTime_begin, lendTime_end, " < '2' ");
		} else {
			jObject = lineInfoService.getDatagrid3(lineInfos, beginTime, endTime, dataGrid, lstartTime_begin,
					lstartTime_end, lendTime_begin, lendTime_end, " < '2' ");
		}
		responseDatagrid(response, jObject);
	}

	/**
	 * 删除线路
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(LineInfoEntity lineInfos, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		String del = request.getParameter("deleteFlag");
		try {
			/*
			 * 通过deleteFlag来区别逻辑删除和物理删除 1：逻辑删除 2 ：物理删除
			 */
			lineInfos = systemService.getEntity(LineInfoEntity.class, lineInfos.getId());
			if ("1".equals(del)) {
				lineInfos.setDeleteFlag((short) 1);
				message = "线路下架成功(逻辑删除)";
				lineInfoService.saveOrUpdate(lineInfos);
			} else {
				message = "删除成功";
				lineInfoService.delete(lineInfos);
			}
		} catch (Exception e) {
		}
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		j.setMsg(message);
		return j;
	}

	/**
	 * 删除线路和站点的数据
	 * 
	 * @return
	 */
	@RequestMapping(params = "delLine_BusStop")
	@ResponseBody
	public AjaxJson delLine_BusStop(Line_busStopEntity line_busStop, HttpServletRequest request) {
		String message = null;
		String id = request.getParameter("id");
		String zdId = request.getParameter("zdId");
		AjaxJson j = new AjaxJson();
		try {
			systemService.deleteEntityById(Line_busStopEntity.class, id);
			//将站点状态改为0
			systemService.updateBySqlString("update busstopinfo set status='0' where id = '"+zdId+"'");
			message = "删除成功";
		} catch (Exception e) {
		}
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		j.setMsg(message);
		return j;
	}

	/**
	 * 批量逻辑删除选中的所有验票员
	 * 
	 * @return
	 * @author linj
	 * @date 2017-04-17
	 */
	@RequestMapping(params = "doDeleteALLSelect")
	@ResponseBody
	public AjaxJson doDeleteALLSelect(LineInfoEntity lineInfos, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		try {
			String ids = request.getParameter("ids");
			String[] entitys = ids.split(",");
			List<LineInfoEntity> list = new ArrayList<LineInfoEntity>();
			for (int i = 0; i < entitys.length; i++) {
				lineInfos = systemService.getEntity(LineInfoEntity.class, entitys[i]);
				lineInfos.setDeleteFlag((short) 1);
				list.add(lineInfos);
			}
			message = "线路下架成功(逻辑删除)";
			// 批量逻辑删除
			lineInfoService.updateAllEntitie(list);
		} catch (Exception e) {
		}
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		j.setMsg(message);
		return j;
	}

	/**
	 * 去线路添加页面
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(LineInfoEntity lineInfo, HttpServletRequest req) {
		// 获取部门信息
		List<TSDepart> departList = systemService.getList(TSDepart.class);
		req.setAttribute("departList", departList);
		if (StringUtil.isNotEmpty(lineInfo.getId())) {
			lineInfo = lineInfoService.getEntity(LineInfoEntity.class, lineInfo.getId());
			req.setAttribute("lineInfo", lineInfo);
		}
		//获取已开通的城市列表
		List<OpenCityEntity> cities = systemService.findByProperty(OpenCityEntity.class, "status", "0");
		req.setAttribute("cities", cities);
		List<TSDepart> list = systemService.findByProperty(TSDepart.class, "orgType", "4");
		if (list.size() > 0) {
			req.setAttribute("list", list);
		}
		return new ModelAndView("yhy/lines/lineAdd");
	}

	/**
	 * 线路保存
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(LineInfoEntity lineInfo, HttpServletRequest request, HttpServletResponse respone) {
		String message = null;
		AjaxJson j = new AjaxJson();
		lineInfo.setDeleteFlag((short) 0);
		lineInfo.setDepartId(ResourceUtil.getSessionUserName().getCurrentDepart().getId());
		lineInfo.setCreatePeople(ResourceUtil.getSessionUserName().getUserName());
		String settledCompanyId = request.getParameter("settledCompany");// 合作公司的id
		String cityId = request.getParameter("city");
		if(StringUtil.isNotEmpty(cityId)){
			List<CitiesEntity> listCity = systemService.findByProperty(CitiesEntity.class, "cityId", cityId);
			if (StringUtil.isNotEmpty(listCity)) {
				lineInfo.setCityId(cityId);
				lineInfo.setCityName(listCity.get(0).getCity());
			}
		}
		if (StringUtil.isNotEmpty(settledCompanyId)) {
			TSDepart t = systemService.getEntity(TSDepart.class, settledCompanyId);
			if (StringUtil.isNotEmpty(t)) {
				lineInfo.setSettledCompanyId(settledCompanyId);
				lineInfo.setSettledCompanyName(t.getDepartname());
			}
		}
		if (StringUtil.isNotEmpty(lineInfo.getId())) {
			try {
				LineInfoEntity l = lineInfoService.getEntity(LineInfoEntity.class, lineInfo.getId());
				MyBeanUtils.copyBeanNotNull2Bean(lineInfo, l);
				lineInfoService.saveOrUpdate(l);
				message = "线路修改成功！";
				// -----数据修改日志[类SVN]------------
				Gson gson = new Gson();
				systemService.addDataLog("lineInfo", l.getId(), gson.toJson(l));
				// -----数据修改日志[类SVN]------------
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				message = "线路添加失败！";
				j.setSuccess(false);
				logger.error(ExceptionUtil.getExceptionMessage(e));
				// e.printStackTrace();
			}
		} else {
			try {
				lineInfoService.save(lineInfo);
				// lineInfoService.findByQueryString("sdsdd");
				message = "线路添加成功！";
				// -----数据修改日志[类SVN]------------
				Gson gson = new Gson();
				systemService.addDataLog("lineInfo", lineInfo.getId(), gson.toJson(lineInfo));
				// -----数据修改日志[类SVN]------------
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				message = "线路添加失败！";
				j.setSuccess(false);
				logger.error(ExceptionUtil.getExceptionMessage(e));
				// e.printStackTrace();
			}
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 线路挂接页面
	 */
	@RequestMapping(params = "addBusStop")
	public ModelAndView lineAddBusStop(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("lineInfoId", request.getParameter("lineInfoId"));
		request.setAttribute("lineType", request.getParameter("lineType"));
		return new ModelAndView("yhy/lines/lineAddBusStopList");
	}

	/**
	 * 线路挂接页面查看，给企业用户
	 */
	@RequestMapping(params = "addBusStop2")
	public ModelAndView lineAddBusStop2(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("lineInfoId", request.getParameter("lineInfoId"));
		return new ModelAndView("yhy/lines/lineAddBusStopList2");
	}

	/**
	 * 根据线路id查询对应的站点信息  
	 */
	@RequestMapping(params = "busStopByLineDatagrid")
	public void roleUserDatagrid(BusStopInfoEntity busStopInfo,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        String lineInfoId = request.getParameter("lineInfoId");
        
        JSONObject jObject = busStopInfoService.getDatagrid3(lineInfoId,dataGrid);
        responseDatagrid(response, jObject);
	}

	/**
	 * 去站点信息页面
	 */
	@RequestMapping(params = "busStopInfoList")
	public ModelAndView stopList(HttpServletRequest request) {
		String lineInfoId = request.getParameter("lineInfoId");
		request.setAttribute("lineInfoId", lineInfoId);
		String lineType = request.getParameter("lineType");
		request.setAttribute("lineType", lineType);
		return new ModelAndView("yhy/lines/busStopInfoList");
	}

	/**
	 * 根据线路id 查询出这条线路未挂接的站点信息
	 */
	@RequestMapping(params = "nullTobusStopInfoList")
	public void nullTobusStopInfoList(BusStopInfoEntity busStopInfo, HttpServletRequest request,
			HttpServletResponse response, DataGrid dataGrid) {
		String lineInfoId = request.getParameter("lineInfoId");
		if(StringUtil.isNotEmpty(lineInfoId)){
			LineInfoEntity lineInfo = systemService.getEntity(LineInfoEntity.class, lineInfoId);
			String lineType = request.getParameter("lineType");
			JSONObject jObject = busStopInfoService.getDatagrid3a(busStopInfo, lineInfoId, dataGrid, lineType, lineInfo);
			responseDatagrid(response, jObject);
		}
		
	}

	/**
	 * 跳转到站点顺序编号页面
	 */
	@RequestMapping(params = "updateBusStopOrder")
	public ModelAndView updateBusStopOrder(Line_busStopEntity lineBusStop, HttpServletRequest request) {
		request.setAttribute("id", request.getParameter("line_busstopId"));
		request.setAttribute("name", request.getParameter("name"));
		request.setAttribute("siteOrder", request.getParameter("siteOrder"));
		request.setAttribute("arrivalTime", request.getParameter("arrivalTime"));
		return new ModelAndView("yhy/lines/lineBusStopUpdate");
	}

	/**
	 * 跳转到站点顺序编号页面
	 */
	@RequestMapping(params = "saveBusStopOrder")
	@ResponseBody
	public AjaxJson saveBusStopOrder(HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		String id = request.getParameter("id");
		String siteOrder = request.getParameter("siteOrder");
		String arrivalTime = request.getParameter("arrivalTime");
		if (StringUtil.isNotEmpty(id) && StringUtil.isNotEmpty(siteOrder)) {
			systemService.updateBySqlString("update line_busstop set siteOrder='" + siteOrder + "', arrivalTime = '"
					+ arrivalTime + "'  where id='" + id + "'");
			message = "站点序号更新成功";
		}
		j.setMsg(message);
		return j;
	}

}

package com.yhy.lin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.yhy.lin.entity.BusStopInfoEntity;
import com.yhy.lin.entity.CitiesEntity;
import com.yhy.lin.entity.LineInfoEntity;
import com.yhy.lin.entity.Line_busStopEntity;
import com.yhy.lin.entity.OpenCityEntity;
import com.yhy.lin.entity.StartOrEndEntity;
import com.yhy.lin.service.BusStopInfoServiceI;
import com.yhy.lin.service.LineInfoServiceI;
import com.yhy.lin.service.StartOrEndServiceI;

import net.sf.json.JSONArray;
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

	@Autowired
	private StartOrEndServiceI startOrEndService;

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
		String cityID = request.getParameter("cityID");// 城市
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
			jObject = lineInfoService.getDatagrid3(lineInfos, cityID, beginTime, endTime, dataGrid, lstartTime_begin,
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

			if (StringUtil.isNotEmpty(zdId)) {
				BusStopInfoEntity bus = this.systemService.getEntity(BusStopInfoEntity.class, zdId);
				Line_busStopEntity lines = systemService.getEntity(Line_busStopEntity.class, id);
				if ("1".equals(bus.getStationType())) {
					message = "火车站点不可删除";
				} else if ("2".equals(bus.getStationType())) {
					message = "机场站点不可删除";
				} else if (lines.getSiteOrder() == 99) {
					message = "终点站点不可删除";
				} else {
					LineInfoEntity lin = systemService.getEntity(LineInfoEntity.class, lines.getLineId());
					if (StringUtil.isNotEmpty(lines)) {
						List<Line_busStopEntity> list = this.systemService.findByQueryString(
								"from Line_busStopEntity where siteOrder!='99' and siteOrder>" + lines.getSiteOrder());
						if (list.size() > 0) {
							for (int i = 0; i < list.size(); i++) {
								Line_busStopEntity buss = list.get(i);
								int order = buss.getSiteOrder();
								if (order > 1) {
									buss.setSiteOrder(order - 1);
								}
							}

						}
					}

					// 删除起点和终点关联表
					if ("2".equals(lin.getType()) || "4".equals(lin.getType())) {
						List<StartOrEndEntity> list = systemService.findHql(
								"from StartOrEndEntity where startid=? and endid=? and linetype=?",
								lin.getStartLocation(), zdId, lin.getType());
						systemService.delete(list.get(0));
					}
					if ("3".equals(lin.getType()) || "5".equals(lin.getType())) {
						List<StartOrEndEntity> list = systemService.findHql(
								"from StartOrEndEntity where startid=? and endid=? and linetype=?", zdId,
								lin.getEndLocation(), lin.getType());
						systemService.delete(list.get(0));
					}

					systemService.deleteEntityById(Line_busStopEntity.class, id);

					// 将站点状态改为0
					systemService.updateBySqlString("update busstopinfo set status=REPLACE(status,'" + lin.getType()
							+ "','') where id='" + zdId + "'");

					message = "删除成功";
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
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
			e.printStackTrace();
		}
		// systemService.addLog(message, Globals.Log_Type_DEL,
		// Globals.Log_Leavel_INFO);
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
		// 获取已开通的城市列表
		List<OpenCityEntity> cities = systemService.findByProperty(OpenCityEntity.class, "status", "0");
		req.setAttribute("cities", cities);
		List<TSDepart> list = systemService.findByProperty(TSDepart.class, "orgType", "4");
		if (list.size() > 0) {
			req.setAttribute("list", list);
		}
		return new ModelAndView("yhy/lines/lineAdd");
	}

	// 获取最大的线路编码
	private synchronized String getMaxLocalCode(String cityid) {
		String code = "";
		String sql = "select a.lineNumber from lineinfo a where a.cityId='" + cityid + "'  ORDER BY a.createTime desc";
		List<Object> bList = systemService.findListbySql(sql.toString());
		if (bList.size() > 0) {
			String pum = (String) bList.get(0);
			// String pum = (String) ob[0];
			if (StringUtil.isNotEmpty(pum)) {
				code = pum.substring(6);
			}
		} else {
			code = "10000";
		}
		return code;
	}

	/**
	 * 接送机线路保存
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(LineInfoEntity lineInfo, HttpServletRequest request, HttpServletResponse respone) {
		String message = null;
		AjaxJson j = new AjaxJson();
		lineInfo.setDeleteFlag((short) 0);
		TSUser user = ResourceUtil.getSessionUserName();

		lineInfo.setDepartId(user.getCurrentDepart().getId());
		lineInfo.setCreatePeople(user.getUserName());
		lineInfo.setCreateUserId(user.getId());
		String cityId = request.getParameter("city");
		if (StringUtil.isNotEmpty(cityId)) {
			List<CitiesEntity> listCity = systemService.findByProperty(CitiesEntity.class, "cityId", cityId);
			if (StringUtil.isNotEmpty(listCity)) {

				lineInfo.setCityId(cityId);
				lineInfo.setCityName(listCity.get(0).getCity());
			}
		}
		if (StringUtil.isNotEmpty(lineInfo.getId())) {
			try {
				LineInfoEntity l = lineInfoService.getEntity(LineInfoEntity.class, lineInfo.getId());

				/*
				 * //修改起点和终点数据 List<StartOrEndEntity> list=
				 * systemService.findHql(
				 * "from StartOrEndEntity where startid=? and endid=? and linetype=? "
				 * , l.getStartLocation(),l.getEndLocation(),l.getType());
				 * StartOrEndEntity addrs = list.get(0); if
				 * (StringUtil.isNotEmpty(addrs)) {
				 * addrs.setStartid(lineInfo.getStartLocation());
				 * addrs.setEndid(lineInfo.getEndLocation());
				 * addrs.setLinetype(lineInfo.getType()); }
				 */

				MyBeanUtils.copyBeanNotNull2Bean(lineInfo, l);
				// startOrEndService.saveOrUpdate(addrs);
				lineInfoService.saveOrUpdate(l);
				message = "线路修改成功！";
				// -----数据修改日志[类SVN]------------
				Gson gson = new Gson();
				systemService.addDataLog("lineInfo", l.getId(), gson.toJson(l));
				// -----数据修改日志[类SVN]------------
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				message = "线路修改失败！";
				j.setSuccess(false);
				logger.error(ExceptionUtil.getExceptionMessage(e));
				// e.printStackTrace();
			}
		} else {
			try {

				String code = getMaxLocalCode(cityId);
				if (code != null) {
					int pum = Integer.parseInt(code) + 1;
					String linecode = cityId + pum;
					lineInfo.setLineNumber(linecode);// 设置线路编号
				}

				StartOrEndEntity st = new StartOrEndEntity();
				if (StringUtil.isNotEmpty(lineInfo.getStartLocation())) {
					st.setStartid(lineInfo.getStartLocation());
				}
				if (StringUtil.isNotEmpty(lineInfo.getEndLocation())) {
					st.setEndid(lineInfo.getEndLocation());
				}
				if (StringUtil.isNotEmpty(lineInfo.getType())) {
					st.setLinetype(lineInfo.getType());
				}

				startOrEndService.save(st);
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
				e.printStackTrace();
			}
		}
		// 线路挂接
		saveUserToOrg(request, lineInfo);
		j.setMsg(message);
		return j;
	}

	/**
	 * 业务车(班车)线路保存
	 */
	@RequestMapping(params = "serviceLineSave")
	@ResponseBody
	public AjaxJson serviceLineSave(LineInfoEntity lineInfo, HttpServletRequest request, HttpServletResponse respone) {
		String message = null;
		AjaxJson j = new AjaxJson();
		lineInfo.setDeleteFlag((short) 0);
		TSUser user = ResourceUtil.getSessionUserName();

		lineInfo.setDepartId(user.getCurrentDepart().getId());
		lineInfo.setCreatePeople(user.getUserName());
		lineInfo.setCreateUserId(user.getId());
		String settledCompanyId = request.getParameter("settledCompany");// 合作公司的id
		String cityId = request.getParameter("city");
		if (StringUtil.isNotEmpty(cityId)) {
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
				// startOrEndService.saveOrUpdate(addrs);
				lineInfoService.saveOrUpdate(l);
				message = "线路修改成功！";
				// -----数据修改日志[类SVN]------------
				Gson gson = new Gson();
				systemService.addDataLog("lineInfo", l.getId(), gson.toJson(l));
				// -----数据修改日志[类SVN]------------
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				message = "线路修改失败！";
				j.setSuccess(false);
				logger.error(ExceptionUtil.getExceptionMessage(e));
				// e.printStackTrace();
			}
		} else {
			try {

				String code = getMaxLocalCode(cityId);
				if (code != null) {
					int pum = Integer.parseInt(code) + 1;
					String linecode = cityId + pum;
					lineInfo.setLineNumber(linecode);// 设置线路编号
				}

				StartOrEndEntity st = new StartOrEndEntity();
				if (StringUtil.isNotEmpty(lineInfo.getStartLocation())) {
					st.setStartid(lineInfo.getStartLocation());
				}
				if (StringUtil.isNotEmpty(lineInfo.getEndLocation())) {
					st.setEndid(lineInfo.getEndLocation());
				}
				if (StringUtil.isNotEmpty(lineInfo.getType())) {
					st.setLinetype(lineInfo.getType());
				}

				startOrEndService.save(st);
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
				e.printStackTrace();
			}
		}
		// 线路挂接
		saveUserToOrg(request, lineInfo);
		j.setMsg(message);
		return j;
	}

	/**
	 * 线路挂接
	 * 
	 * @param request
	 * @param lineInfo
	 * @return
	 */
	public AjaxJson saveUserToOrg(HttpServletRequest request, LineInfoEntity lineInfo) {
		String message = null;
		AjaxJson j = new AjaxJson();
		Line_busStopEntity lin_busStop = new Line_busStopEntity();
		Line_busStopEntity lin_busStop1 = new Line_busStopEntity();
		List<Line_busStopEntity> list = new ArrayList<Line_busStopEntity>();
		String startlocaid = request.getParameter("startLocation");
		String endlocaid = request.getParameter("endLocation");

		StringBuffer str = new StringBuffer();

		if (StringUtil.isNotEmpty(startlocaid)) {
			str.append("select b.name from busstopinfo b,line_busstop a where b.id='");
			str.append(startlocaid);
			str.append("' and a.busStopsId=b.id and a.lineId='");
			str.append(lineInfo.getId() + "'");

			List<Object> bList = systemService.findListbySql(str.toString());
			if (bList.size() > 0) {
				message = "站点存在！";
			} else {
				lin_busStop.setLineId(lineInfo.getId());
				lin_busStop.setBusStopsId(startlocaid);
				lin_busStop.setSiteOrder(0);
				list.add(lin_busStop);
			}

		}

		if (StringUtil.isNotEmpty(endlocaid)) {
			String sql = "select b.name from busstopinfo b,line_busstop a where b.id='" + endlocaid
					+ "' and a.busStopsId=b.id and a.lineId='" + lineInfo.getId() + "'";

			List<Object> bList = systemService.findListbySql(sql);
			if (bList.size() > 0) {
				message = "站点存在！";
			} else {
				lin_busStop1.setLineId(lineInfo.getId());
				lin_busStop1.setBusStopsId(endlocaid);
				lin_busStop1.setSiteOrder(99);
				list.add(lin_busStop1);
			}
		}
		try {
			if (list.size() > 0) {
				systemService.saveAllEntitie(list);// 挂载站点
			}

			message = "站点挂接成功";
		} catch (Exception e) {
			message = "站点挂接失败";
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
	public void roleUserDatagrid(BusStopInfoEntity busStopInfo, HttpServletRequest request,
			HttpServletResponse response, DataGrid dataGrid) {
		String lineInfoId = request.getParameter("lineInfoId");

		JSONObject jObject = busStopInfoService.getDatagrid3(lineInfoId, dataGrid);
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
		String lineType = request.getParameter("lineType");
		if (StringUtil.isNotEmpty(lineInfoId)) {
			LineInfoEntity lineInfo = systemService.getEntity(LineInfoEntity.class, lineInfoId);

			JSONObject jObject = busStopInfoService.getDatagrid3a(busStopInfo, dataGrid, lineInfo);
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
	 * 上移
	 */
	@RequestMapping(params = "moveup")
	@ResponseBody
	public AjaxJson moveup(HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		String id = request.getParameter("id");
		String busid = request.getParameter("line_busstopId");
		String siteOrder = request.getParameter("siteOrder");
		if (StringUtil.isNotEmpty(siteOrder)) {
			int site = Integer.parseInt(siteOrder);
			if (site != 0 && site != 1 && site != 99) {
				int order = site - 1;
				Line_busStopEntity ln = this.systemService.getEntity(Line_busStopEntity.class, busid);
				List<Line_busStopEntity> list = this.systemService
						.findHql("from Line_busStopEntity where siteOrder=? and lineId=?", order, ln.getLineId());
				Line_busStopEntity line = list.get(0);
				String sql = "update line_busstop set siteOrder=" + order + "  where id='" + id + "'";
				System.out.println(sql);
				systemService.updateBySqlString(
						"update line_busstop set siteOrder=" + order + "  where id='" + busid + "'");
				systemService.updateBySqlString(
						"update line_busstop set siteOrder='" + site + "'  where id='" + line.getId() + "'");
				message = "站点上移成功！";
			} else if (site == 0) {
				message = "起点站不能上移";
			} else if (site == 1) {
				message = "起点站不可更改";
			} else if (site == 99) {
				message = "终点站不能上移";
			}
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 下移
	 */
	@RequestMapping(params = "movedown")
	@ResponseBody
	public AjaxJson movedown(HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		String id = request.getParameter("id");
		String busid = request.getParameter("line_busstopId");
		String siteOrder = request.getParameter("siteOrder");
		int siteOreder = 0;
		String sql = "select b.siteOrder from  line_busstop b where b.siteOrder!='99' ORDER BY b.siteOrder DESC";
		List<Object> bList = systemService.findListbySql(sql.toString());
		if (bList.size() > 0) {
			siteOreder = (int) bList.get(0);
		}

		if (StringUtil.isNotEmpty(siteOrder)) {
			int site = Integer.parseInt(siteOrder);
			if (site != 0 && site != siteOreder && site != 99) {
				//需要交换对象的排序值
				int order = site + 1;
				Line_busStopEntity ln = systemService.getEntity(Line_busStopEntity.class, busid);
				List<Line_busStopEntity> list = systemService.findHql("from Line_busStopEntity where siteOrder=? and lineId=?", order, ln.getLineId());
				Line_busStopEntity lb = list.get(0);
				systemService.updateBySqlString(
						"update line_busstop set siteOrder='" + order + "' where id='" + busid + "'");
				systemService.updateBySqlString(
						"update line_busstop set siteOrder='" + site + "' where id='" + lb.getId() + "'");
				message = "站点下移成功！";
			} else if (site == 0) {
				message = "起点站不能下移";
			} else if (site == siteOreder) {
				message = "终点站不可更改";
			} else if (site == 99) {
				message = "终点站不能下移";
			}
		}
		j.setMsg(message);
		return j;
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
			String sql = "update line_busstop set siteOrder='" + siteOrder + "', arrivalTime = '" + arrivalTime + "'";
			systemService.updateBySqlString(sql + " where id='" + id + "'");
			message = "站点序号更新成功";
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 根据起点站添加终点站
	 */
	@RequestMapping(params = "getEndlocation")
	@ResponseBody
	public JSONArray getEndlocation(BusStopInfoEntity busStopInfo, HttpServletRequest request,
			HttpServletResponse response) {
		String city = request.getParameter("city");
		// 线路类型 0：班车 1：包车 2：接机 3：送机 4：接火车 5：送火车 6：接送机 7：接送火车
		String type = request.getParameter("type");
		String startLocation = request.getParameter("startLocation");
		JSONObject jsonObj = new JSONObject();
		JSONArray jsonArray = new JSONArray();

		StringBuffer st = new StringBuffer();
		st.append("select DISTINCT b.id,b.name from busstopinfo b  where 1=1 ");
		if (StringUtil.isNotEmpty(city)) {
			st.append(" and b.cityId='" + city + "'");
		}
		if (StringUtil.isNotEmpty(type) || "0".equals(type)) {
			if ("2".equals(type) || "4".equals(type)) {
				st.append(" and b.station_type = '0'");
			}
			if ("3".equals(type)) {
				st.append(" and b.station_type = '2'");
			}
			if ("5".equals(type)) {
				st.append(" and b.station_type = '1'");
			}
			if (StringUtil.isNotEmpty(startLocation)) {
				st.append(" and b.id not in(select se.endId from start_end se where 1=1 and se.startId='"
						+ startLocation + "' and se.lineType like '%" + type + "%')");
			}
		}

		System.out.println(st.toString());
		List<Object> bList = systemService.findListbySql(st.toString());

		if (bList.size() > 0) {
			for (int i = 0; i < bList.size(); i++) {
				Object[] ob = (Object[]) bList.get(i);
				String id = (String) ob[0];
				String name = (String) ob[1];
				jsonObj.put("stopid", id);
				jsonObj.put("name", name);
				jsonArray.add(jsonObj);
			}

		}
		return jsonArray;
	}

	/**
	 * 根据城市和类型添加起点
	 */
	@RequestMapping(params = "getStartLocation")
	@ResponseBody
	public JSONArray getStartLocation(BusStopInfoEntity busStopInfo, HttpServletRequest request,
			HttpServletResponse response) {
		String city = request.getParameter("city");
		// 线路类型 0：班车 1：包车 2：接机 3：送机 4：接火车 5：送火车 6：接送机 7：接送火车
		String type = request.getParameter("type");
		JSONObject jsonObj = new JSONObject();
		JSONArray jsonArray = new JSONArray();

		StringBuffer st = new StringBuffer();
		st.append("select DISTINCT b.id,b.name from busstopinfo b where  1=1 ");

		if (StringUtil.isNotEmpty(city)) {
			st.append(" and b.cityId='" + city + "'");
		}
		if (StringUtil.isNotEmpty(type) || "0".equals(type)) {
			if ("2".equals(type)) {
				st.append("and b.station_type = '2'");
			}
			if ("4".equals(type)) {
				st.append("and b.station_type = '1'");
			}
			if ("3".equals(type) || "5".equals(type)) {
				st.append("and b.station_type = '0'");
			}
		}

		List<Object> bList = systemService.findListbySql(st.toString());

		if (bList.size() > 0) {
			for (int i = 0; i < bList.size(); i++) {
				Object[] ob = (Object[]) bList.get(i);
				String id = (String) ob[0];
				String name = (String) ob[1];
				jsonObj.put("stopid", id);
				jsonObj.put("name", name);
				jsonArray.add(jsonObj);
			}
		}

		return jsonArray;
	}

}

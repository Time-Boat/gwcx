package com.yhy.lin.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSType;
import org.jeecgframework.web.system.pojo.base.TSTypegroup;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yhy.lin.app.util.AppUtil;
import com.yhy.lin.entity.BusStopInfoEntity;
import com.yhy.lin.entity.CarTSTypeLineEntity;
import com.yhy.lin.entity.LineBusstopHistoryEntity;
import com.yhy.lin.entity.LineInfoEntity;
import com.yhy.lin.entity.LineInfoView;
import com.yhy.lin.entity.Line_busStopEntity;
import com.yhy.lin.entity.LineinfoHistoryEntity;
import com.yhy.lin.entity.OpenCityEntity;
import com.yhy.lin.entity.StartOrEndEntity;
import com.yhy.lin.service.CarTSTypeLineServiceI;
import com.yhy.lin.service.LineInfoServiceI;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 接送机线路管理模块
 * 
 * @author HSAEE
 *
 */
@Controller
@RequestMapping("lineInfoSpecializedController")
public class LineInfoSpecializedController extends BaseController {
	@Autowired
	private SystemService systemService;

	@Autowired
	private LineInfoServiceI lineInfoService;

	@Autowired
	private CarTSTypeLineServiceI carTSTypeLineServiceI;

	/**
	 * 接送机线路管理跳转页面
	 */
	@RequestMapping(params = "lineList")
	public ModelAndView lineList(HttpServletRequest request) {
		request.setAttribute("cityList", getOpencity());

		/*TSUser user = ResourceUtil.getSessionUserName();
		boolean hasPermission = checkRole(user, AppGlobals.PLATFORM_LINE_AUDIT);
		if (hasPermission) {
			String oc = user.getOrgCompany();
			if(StringUtil.isNotEmpty(oc)){
				String json = getCompany(oc);
				request.setAttribute("companyList", json);
			}
		}*/
		return new ModelAndView("yhy/linesSpecial/lineList");
	}

	/**
	 * 接送机线路管理跳转页面
	 */
	@RequestMapping(params = "applyEditList")
	public ModelAndView applyEditList(HttpServletRequest request) {
		request.setAttribute("cityList", getOpencity());

		return new ModelAndView("yhy/linesSpecial/applyEditList");
	}
	/**
	 * 获得子公司
	 * 
	 * @return
	 *//*
	public String getCompany(String oc) {

		String[] ocArr = oc.split(",");

		StringBuffer sbf = new StringBuffer();

		for (int i = 0; i < ocArr.length; i++) {
			sbf.append("'" + ocArr[i] + "',");
		}

		String sql = "select departname,org_code from t_s_depart where org_code in("
				+ sbf.toString().substring(0, sbf.length() - 1) + ") ";
		List<Object> list = this.systemService.findListbySql(sql);
		StringBuffer json = new StringBuffer("{'data':[");
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Object[] ob = (Object[]) list.get(i);
				String dName = ob[0] + "";
				String dCode = ob[1] + "";
				json.append("{");
				json.append("'dName':'" + dName + "',");
				json.append("'dCode':'" + dCode + "'");
				json.append("},");
			}
		}
		json.delete(json.length() - 1, json.length());
		json.append("]}");
		return json.toString();
	}*/

	@RequestMapping(params = "datagrid")
	public void datagrid(LineInfoEntity lineInfos, HttpServletRequest request, HttpServletResponse response,
			DataGrid dataGrid) {
		String departname = request.getParameter("departname");
		String username = request.getParameter("username");
		String cityid = request.getParameter("cityID");
		String linetype = request.getParameter("linetype");// 线路类型
		String beginTime = request.getParameter("createTime_begin");// 线路创建开始查询时间
		String endTime = request.getParameter("createTime_end");// 线路创建结束查询时间
		String lstartTime_begin = request.getParameter("lstartTime_begin");
		String lstartTime_end = request.getParameter("lstartTime_end");
		String lendTime_begin = request.getParameter("lendTime_begin");
		String lendTime_end = request.getParameter("lendTime_end");

		//		String company = request.getParameter("company");

		// 因为调用的方法一样，所以在外层来处理... 忘记是啥意思了。。。
		linetype = " >='" + linetype + "' ";
		JSONObject jObject = lineInfoService.getDatagrid3(lineInfos, cityid, beginTime, endTime, dataGrid,
				lstartTime_begin, lstartTime_end, lendTime_begin, lendTime_end, linetype, username, departname,
				null);
		responseDatagrid(response, jObject);
	}

	/**
	 * 显示所有激活的用户
	 */
	@RequestMapping(params = "userdatagrid")
	public void userdatagrid(LineInfoEntity lineInfos, HttpServletRequest request, HttpServletResponse response,
			DataGrid dataGrid) {

		JSONObject jObject = lineInfoService.getDatagrid4(lineInfos, dataGrid);
		responseDatagrid(response, jObject);
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
		String id = req.getParameter("id");

		// 修改线路
		if (StringUtil.isNotEmpty(id)) {
			lineInfo = lineInfoService.getEntity(LineInfoEntity.class, id);
			req.setAttribute("lineInfo", lineInfo);
			req.setAttribute("startList", getStartLocation(lineInfo.getCityId(), lineInfo.getType()));
			req.setAttribute("endList",
					getEndLocation(lineInfo.getCityId(), lineInfo.getType(), lineInfo.getStartLocation()));
		}
		List<OpenCityEntity> cities = systemService.findByProperty(OpenCityEntity.class, "status", "0");
		req.setAttribute("cities", cities);
		return new ModelAndView("yhy/linesSpecial/lineAdd");
	}

	/**
	 * 查看线路详情
	 * 
	 * @return
	 */
	@RequestMapping(params = "linedetail")
	public ModelAndView linedetail(HttpServletRequest request) {
		String id = request.getParameter("id");
		String history = request.getParameter("history");
		if (StringUtil.isNotEmpty(id)) {
			
			if("1".equals(history)){
				LineInfoView view = lineInfoService.getDetailHistory(id);
				if (view != null) {
					request.setAttribute("View", view);
					if (StringUtil.isNotEmpty(view.getApplicationUserid())) {
						TSUser applicationUser = this.systemService.getEntity(TSUser.class, view.getApplicationUserid());
						request.setAttribute("applicationUser", applicationUser);
					}
					if (StringUtil.isNotEmpty(view.getFirstApplicationUser())) {
						TSUser firstUser = this.systemService.getEntity(TSUser.class, view.getFirstApplicationUser());
						request.setAttribute("firstUser", firstUser);
					}
					if (StringUtil.isNotEmpty(view.getLastApplicationUser())) {
						TSUser lastUser = this.systemService.getEntity(TSUser.class, view.getLastApplicationUser());
						request.setAttribute("lastUser", lastUser);
					}
				}
				
			}else{
				LineInfoView view = lineInfoService.getDetail(id);
				if (view != null) {
					request.setAttribute("View", view);
					if (StringUtil.isNotEmpty(view.getApplicationUserid())) {
						TSUser applicationUser = this.systemService.getEntity(TSUser.class, view.getApplicationUserid());
						request.setAttribute("applicationUser", applicationUser);
					}
					if (StringUtil.isNotEmpty(view.getFirstApplicationUser())) {
						TSUser firstUser = this.systemService.getEntity(TSUser.class, view.getFirstApplicationUser());
						request.setAttribute("firstUser", firstUser);
					}
					if (StringUtil.isNotEmpty(view.getLastApplicationUser())) {
						TSUser lastUser = this.systemService.getEntity(TSUser.class, view.getLastApplicationUser());
						request.setAttribute("lastUser", lastUser);
					}
				}
			}
			
		}

		return new ModelAndView("yhy/linesSpecial/lineDetial");
	}

	/**
	 * 分配用户
	 * 
	 * @return
	 */
	@RequestMapping(params = "lineAllot")
	public ModelAndView lineAllot(LineInfoEntity lineInfo, HttpServletRequest request) {
		String ids = request.getParameter("ids");
		request.setAttribute("ids", ids);
		return new ModelAndView("yhy/linesSpecial/lineAllot");
	}

	/**
	 * 申请上架、申请下架
	 */
	@RequestMapping(params = "allot")
	@ResponseBody
	public AjaxJson allot(HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		String lineid = request.getParameter("lineid");
		String id = request.getParameter("ids");
		String username = request.getParameter("username");
		if (StringUtil.isNotEmpty(lineid)) {
			String lin[] = lineid.split(",");
			for (int i = 0; i < lin.length; i++) {
				String linId = lin[i];
				LineInfoEntity lineinfo = this.systemService.getEntity(LineInfoEntity.class, linId);
				lineinfo.setCreateUserId(id);
				lineinfo.setCreatePeople(username);
				try {
					message = "分配成功！";
					this.systemService.saveOrUpdate(lineinfo);
				} catch (Exception e) {
					message = "系统异常！";
					// TODO: handle exception
				}
			}
		}

		j.setMsg(message);
		return j;
	}

	/**
	 * 获取起点站点
	 * 
	 * @param cityid
	 * @param type
	 * @return
	 */
	public List<BusStopInfoEntity> getStartLocation(String cityid, String type) {

		StringBuffer st = new StringBuffer();

		if (StringUtil.isNotEmpty(type)) {
			if ("2".equals(type) || "4".equals(type)) {
				st.append(
						"select DISTINCT b.id,b.name from busstopinfo b LEFT JOIN line_busstop a on b.id=a.busStopsId where  1=1 ");
				if (StringUtil.isNotEmpty(cityid)) {
					st.append(" and b.cityId='" + cityid + "'");
				}
				if ("2".equals(type)) {
					st.append("and b.station_type = '2'");
				}
				if ("4".equals(type)) {
					st.append("and b.station_type = '1'");
				}
			}

			if ("3".equals(type) || "5".equals(type)) {
				st.append(
						"select DISTINCT b.id,b.name from busstopinfo b LEFT JOIN line_busstop a on b.id=a.busStopsId where 1=1");
				if (StringUtil.isNotEmpty(cityid)) {
					st.append(" and b.cityId='" + cityid + "'");
				}
				st.append("and b.station_type = '0'");
			}
		} else {
			st.append(
					"select DISTINCT b.id,b.name from busstopinfo b LEFT JOIN line_busstop a on b.id=a.busStopsId where  1=1");
		}

		List<Object> list = this.systemService.findListbySql(st.toString());
		List<BusStopInfoEntity> buslist = new ArrayList<BusStopInfoEntity>();
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Object[] ob = (Object[]) list.get(i);
				String id = ob[0] + "";
				String name = ob[1] + "";
				BusStopInfoEntity bus = new BusStopInfoEntity();
				bus.setId(id);
				bus.setName(name);
				buslist.add(bus);
			}
		}
		return buslist;
	}

	/**
	 * 获取终点站点
	 * 
	 * @param cityid
	 * @param type
	 * @param busid
	 * @return
	 */
	public List<BusStopInfoEntity> getEndLocation(String cityid, String type, String busid) {

		StringBuffer st = new StringBuffer();

		if (StringUtil.isNotEmpty(type)) {
			if ("2".equals(type) || "4".equals(type)) {
				st.append("select DISTINCT b.id,b.name from busstopinfo b  where 1=1 ");

				if (StringUtil.isNotEmpty(cityid)) {
					st.append(" and b.cityId='" + cityid + "'");
				}
				st.append("and b.station_type = '0'");

				if (StringUtil.isNotEmpty(busid)) {
					st.append(
							" and b.id not in (select g.id from line_busstop f LEFT JOIN busstopinfo g on g.id=f.busStopsId where f.siteId='");
					st.append(busid + "'");
					st.append(" and g.status like '%" + type + "%')");
				}

			}

			if ("3".equals(type) || "5".equals(type)) {
				st.append("select DISTINCT b.id,b.name from busstopinfo b  where 1=1 ");
				if (StringUtil.isNotEmpty(cityid)) {
					st.append(" and b.cityId='" + cityid + "'");
				}
				if ("3".equals(type)) {
					st.append("and b.station_type = '2'");
				}
				if ("5".equals(type)) {
					st.append("and b.station_type = '1'");
				}

				if (StringUtil.isNotEmpty(busid)) {
					st.append(
							" and b.id not in (select lb.siteId from line_busstop lb LEFT JOIN busstopinfo a on a.id=lb.busStopsId where lb.siteId is not null");
					st.append(" and lb.busStopsId='" + busid + "'");
					st.append(" and a.status like '%" + type + "%')");
				}
			}
		}
		List<Object> list = this.systemService.findListbySql(st.toString());
		List<BusStopInfoEntity> buslist = new ArrayList<BusStopInfoEntity>();
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Object[] ob = (Object[]) list.get(i);
				String id = ob[0] + "";
				String name = ob[1] + "";
				BusStopInfoEntity bus = new BusStopInfoEntity();
				bus.setId(id);
				bus.setName(name);
				buslist.add(bus);
			}
		}
		return buslist;
	}

	/**
	 * 去线路添加页面
	 * 
	 * @return
	 */
	@RequestMapping(params = "lineMap")
	public ModelAndView lineMap(LineInfoEntity lineInfo, HttpServletRequest req) {
		// 获取部门信息

		String lineId = req.getParameter("id");

		// List<Line_busStopEntity> lbs = systemService.findListbySql(
		// "select b.* from line_busstop lb join busstopinfo b on b.id =
		// lb.busStopsId "
		// + "where lineId = '2c9a500d5cd3ee4a015cd3f80c130010'");
		// + "where lineId = '" + lineId + "'");

		List<Map<String, Object>> lbs = systemService.findForJdbc(
				"select b.name,b.x,b.y,b.stopLocation,lb.siteOrder from line_busstop lb join busstopinfo b on b.id = lb.busStopsId "
						+ "where lineId = '" + lineId + "' order by siteOrder ");

		String json = JSONArray.fromObject(lbs).toString().replaceAll("\"", "'");

		req.setAttribute("stations", json);

		return new ModelAndView("yhy/linesSpecial/lineMap");
	}

	/**
	 * 去线路添加页面
	 * 
	 * @return
	 */
	@RequestMapping(params = "historyLineMap")
	public ModelAndView historyLineMap(LineInfoEntity lineInfo, HttpServletRequest req) {
		// 获取部门信息

		String lineId = req.getParameter("id");

		// List<Line_busStopEntity> lbs = systemService.findListbySql(
		// "select b.* from line_busstop lb join busstopinfo b on b.id =
		// lb.busStopsId "
		// + "where lineId = '2c9a500d5cd3ee4a015cd3f80c130010'");
		// + "where lineId = '" + lineId + "'");

		List<Map<String, Object>> lbs = systemService.findForJdbc(
				"select b.name,b.x,b.y,b.stopLocation,lb.siteOrder from line_busstop_history lb join busstopinfo b on b.id = lb.busStopsId "
						+ "where lineId = '" + lineId + "' order by siteOrder ");

		String json = JSONArray.fromObject(lbs).toString().replaceAll("\"", "'");

		req.setAttribute("stations", json);

		return new ModelAndView("yhy/linesSpecial/lineMap");
	}
	
	/**
	 * 申请上架、申请下架
	 */
	@RequestMapping(params = "applyShelves")
	@ResponseBody
	public AjaxJson applyShelves(HttpServletRequest request) {
		String message = null;
		boolean success = false;
		AjaxJson j = new AjaxJson();
		String id = request.getParameter("id");
		String lineStatus= request.getParameter("status");
		String isDealerLine = request.getParameter("isDealerLine");
		if("1".equals(lineStatus)){
			if("1".equals(isDealerLine)){
				if(StringUtil.isNotEmpty(id)){
					List<CarTSTypeLineEntity> carlist = this.systemService.findByProperty(CarTSTypeLineEntity.class, "lineId", id);
					if(carlist.size()>0){
						success=true;
					}else{
						message="渠道商区间价格未填写，请填写区间价格再申请上架！";
						success=false;
						j.setMsg(message);
						j.setSuccess(success);
						return j;
					}
				}
			}
			if(StringUtil.isNotEmpty(id)){
				LineInfoEntity lineinfo = this.systemService.getEntity(LineInfoEntity.class, id);
					if(StringUtil.isNotEmpty(lineinfo)){
						j=checkEnabled(lineinfo);
						if(j.isSuccess()==false){
							return j;
						}
					
					}
			}
			
		}else if("0".equals(lineStatus)){
			if(StringUtil.isNotEmpty(id)){
				LineInfoEntity lineinfo = this.systemService.getEntity(LineInfoEntity.class, id);
					if(StringUtil.isNotEmpty(lineinfo)){
						
						List<Object> calist = this.systemService.findListbySql("select a.version from lineinfo_history a where a.lineNumber='"+lineinfo.getLineNumber()+"' ORDER BY a.version desc");
						if(calist.size()>0){
							Object ca = calist.get(0);
							if(!StringUtil.isNotEmpty(ca)){
								ca="0";
							}
							List<LineinfoHistoryEntity> historylist = systemService.findHql(
									"from LineinfoHistoryEntity where lineNumber=? and version=? ",lineinfo.getLineNumber(),ca);
						//List<LineinfoHistoryEntity> historylist = this.systemService.findByProperty(LineinfoHistoryEntity.class, "lineNumber", lineinfo.getLineNumber());
						if(historylist.size()>0){
							LineinfoHistoryEntity history = historylist.get(0);
							if("1".equals(history.getApplicationEditStatus()) || "2".equals(history.getApplicationEditStatus())){
								message="正在申请修改，请勿申请下架";
								success=false;
								j.setMsg(message);
								j.setSuccess(success);
								return j;
							}
					}
				}
			}
		}
		}

		LineInfoEntity  line = this.systemService.getEntity(LineInfoEntity.class, id);
		if(StringUtil.isNotEmpty(line)){
			line.setApplicationStatus("1");//待审核
			if("0".equals(line.getStatus())){
				line.setApplyContent("1");//申请内容
			}else{
				line.setApplyContent("0");//申请内容
			}

			line.setApplicationTime(AppUtil.getDate());
			line.setApplicationUserid(ResourceUtil.getSessionUserName().getId());
		}
		try {
			message = "申请成功！";
			success=true;
			this.systemService.saveOrUpdate(line);
		} catch (Exception e) {
			// TODO: handle exception
			message = "服务器异常！";
		}
		j.setSuccess(success);
		j.setMsg(message);
		return j;
	}

	/**
	 * 获取拒绝原因
	 */
	@RequestMapping(params = "getReason")
	@ResponseBody
	public AjaxJson getReason(HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		String id = request.getParameter("id");
		LineInfoEntity line = this.systemService.getEntity(LineInfoEntity.class, id);

		if (StringUtil.isNotEmpty(line)) {
			if (StringUtil.isNotEmpty(line.getReviewReason())) {
				message = line.getReviewReason();// 复审拒绝原因
			} else {
				message = line.getTrialReason();// 初审拒绝原因
			}
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 申请同意
	 */
	@RequestMapping(params = "agree")
	@ResponseBody
	public AjaxJson agree(HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		String id = request.getParameter("id");
		LineInfoEntity  line = this.systemService.getEntity(LineInfoEntity.class, id);

		if(StringUtil.isNotEmpty(line)){
			if("1".equals(line.getApplicationStatus())){
				if("1".equals(line.getStatus()) || "2".equals(line.getStatus())){
					j=checkEnabled(line);
					if(j.isSuccess()==false){
						return j;
					}
				}
				
				line.setApplicationStatus("2");//初审
				line.setFirstApplicationUser(ResourceUtil.getSessionUserName().getId());
				line.setFirstApplicationTime(AppUtil.getDate());
			}else if("2".equals(line.getApplicationStatus())){
				
				if("0".equals(line.getStatus())){
					line.setApplicationStatus("4");//复审
					line.setStatus("2");//已下架
					if(StringUtil.isNotEmpty(line.getId())){
						List<CarTSTypeLineEntity> carlist = this.systemService.findByProperty(CarTSTypeLineEntity.class, "lineId", line.getId());
						for (int i = 0; i < carlist.size(); i++) {
							CarTSTypeLineEntity ca = carlist.get(i);
							ca.setApplyStatus("0");
							carTSTypeLineServiceI.saveOrUpdate(ca);
						}
						
						//申请下架之后，释放关联表
						List<LineinfoHistoryEntity> historylist = this.systemService.findByProperty(LineinfoHistoryEntity.class, "lineNumber", line.getLineNumber());
						if(historylist.size()>0){
							for (int i = 0; i < historylist.size(); i++) {
								LineinfoHistoryEntity history = historylist.get(i);
								if(StringUtil.isNotEmpty(history)){
									history.setStatus("1");//已下架
									this.systemService.saveOrUpdate(history);
									editStationStatus(history.getId());//线路下架释放起点和终点关联数据-->线路历史表表数据
								}
								
							}
						}
						
						editStationStatus(line.getId());//线路下架释放起点和终点关联数据-->线路表数据
						
						//线路下架修改线路历史记录线路状态
						
					}

				}else if("1".equals(line.getStatus()) || "2".equals(line.getStatus())){
					
					j=checkEnabled(line);
					if(j.isSuccess()==false){
						return j;
					}
					line.setApplicationStatus("3");//复审
					line.setStatus("0");//已上架
					
					saveLineHistory(request,line);//保存历史记录和相关车辆类型区间价格
					enabledStartEndState(line);//启用起点和终点关联表状态
					
				}
				
				line.setLastApplicationTime(AppUtil.getDate());
				line.setLastApplicationUser(ResourceUtil.getSessionUserName().getId());
			}
		}
		try {
			message = "申请成功！";
			this.systemService.saveOrUpdate(line);
		} catch (Exception e) {
			// TODO: handle exception
			message = "服务器异常！";
		}

		j.setMsg(message);
		return j;
	}
	
	//验证是否存在站点已挂接
	public AjaxJson checkEnabled(LineInfoEntity  line){
		boolean success=false;
		String message = null;
		AjaxJson j = new AjaxJson();
		//判断站点是否被启用
		if(StringUtil.isNotEmpty(line.getId())){
			List<StartOrEndEntity> startlist = this.systemService.findByProperty(StartOrEndEntity.class, "lineId", line.getId());
			if(startlist.size()>0){
				for (int i = 0; i < startlist.size(); i++) {
					StartOrEndEntity see = startlist.get(i);
					if(StringUtil.isNotEmpty(see)){
						List<StartOrEndEntity> list = this.systemService.findHql("from StartOrEndEntity where startid=? and endid=? and stationStatus=? and linetype=?", see.getStartid(),see.getEndid(),"1",line.getType());
						if(list.size()>0){
							for (int k = 0; k < list.size(); k++) {
								StartOrEndEntity sre = list.get(k);
								if("1".equals(sre.getStationStatus())){
									BusStopInfoEntity bus = new BusStopInfoEntity();
									if ("2".equals(line.getType()) || "4".equals(line.getType())) {
										if(StringUtil.isNotEmpty(sre.getEndid()) ){
											 bus = this.systemService.getEntity(BusStopInfoEntity.class, sre.getEndid());
										}
									}else if("3".equals(line.getType()) || "5".equals(line.getType())){
										if(StringUtil.isNotEmpty(sre.getStartid())){
											 bus = this.systemService.getEntity(BusStopInfoEntity.class, sre.getStartid());
										}
									}
									if(!"1".equals(bus.getStationType()) && !"2".equals(bus.getStationType())){
										message = bus.getName()+"已被启用，不能重复添加！";
										success = false;
										j.setSuccess(success);
										j.setMsg(message);
										return j;
									}
								}
							}
							
						}
					}
				}
			}
		}
		j.setMsg(message);
		return j;
	}
	
	
	public void editStationStatus(String id){
		if (StringUtil.isNotEmpty(id)) {
			List<StartOrEndEntity> strlist = this.systemService.findByProperty(StartOrEndEntity.class, "lineId", id);
			if(strlist.size()>0){
				for (int i = 0; i < strlist.size(); i++) {
					StartOrEndEntity start = strlist.get(i);
					start.setStationStatus("0");
					this.systemService.saveOrUpdate(start);
				}
			}
		}
	}
	
	public void enabledStartEndState(LineInfoEntity line){
		if (StringUtil.isNotEmpty(line)) {
			List<Line_busStopEntity> buslist = this.systemService.findByProperty(Line_busStopEntity.class, "lineId", line.getId());
			if(buslist.size()>0){
				for (int i = 0; i < buslist.size(); i++) {
					Line_busStopEntity bus = buslist.get(i);
					if ("2".equals(line.getType()) || "4".equals(line.getType())) {
						if(!line.getStartLocation().equals(bus.getBusStopsId())){
							List<StartOrEndEntity> startlist = this.systemService.findHql("from StartOrEndEntity where startid=? and lineId=?", line.getStartLocation(),line.getId());
							if(startlist.size()>0){
								for (int j = 0; j < startlist.size(); j++) {
									StartOrEndEntity start = startlist.get(j);
									start.setStationStatus("1");
									this.systemService.saveOrUpdate(start);
								}
							}		
						}
					}else if("3".equals(line.getType()) || "5".equals(line.getType())){
						if(!line.getEndLocation().equals(bus.getBusStopsId())){
							List<StartOrEndEntity> startlist = this.systemService.findHql("from StartOrEndEntity where lineId=? and endid=?",line.getId(),line.getEndLocation());
							if(startlist.size()>0){
								for (int j = 0; j < startlist.size(); j++) {
									StartOrEndEntity start = startlist.get(j);
									start.setStationStatus("1");
									this.systemService.saveOrUpdate(start);
								}
							}		
						}
					}
				}
			}	
		}
	}
	
	public void saveLineHistory(HttpServletRequest request,LineInfoEntity line){
		
		LineinfoHistoryEntity history = new LineinfoHistoryEntity();
		try {
			
			List<Object> calist = this.systemService.findListbySql("SELECT a.version from lineinfo_history a where a.lineNumber = '"+line.getLineNumber()+"' ORDER BY a.version desc");
			Object ca = null;
			if(calist.size()>0){
				ca = calist.get(0);
			}
			if(!StringUtil.isNotEmpty(ca)){
				ca="0";
			}
			if(StringUtil.isNotEmpty(line)){
				MyBeanUtils.copyBeanNotNull2Bean(line, history);
				history.setVersion(Integer.parseInt(ca.toString())+1+"");
				history.setStatus("0");
				history.setApplicationEditStatus("0");
			}
			
			List<CarTSTypeLineEntity> carlist = this.systemService.findByProperty(CarTSTypeLineEntity.class, "lineId", line.getId());
			for (int i = 0; i < carlist.size(); i++) {
				CarTSTypeLineEntity caline = carlist.get(i);
				caline.setApplyStatus("1");
				carTSTypeLineServiceI.saveOrUpdate(caline);
			}
			this.systemService.save(history);
			saveStartAndEndHistory(history, line);
			
			List<CarTSTypeLineEntity> catlist = this.systemService.findByProperty(CarTSTypeLineEntity.class, "lineId", line.getId());
			if(catlist.size()>0){
				for (int i = 0; i < catlist.size(); i++) {
					CarTSTypeLineEntity carTSTypeLine = new CarTSTypeLineEntity();
					carTSTypeLine.setCarTypeId(catlist.get(i).getCarTypeId());
					carTSTypeLine.setApplyStatus(catlist.get(i).getApplyStatus());
					carTSTypeLine.setCarTypePrice(catlist.get(i).getCarTypePrice());
					carTSTypeLine.setLineId(history.getId());
					carTSTypeLine.setVersion(catlist.get(i).getVersion());
					this.systemService.save(carTSTypeLine);
				}
			}
			saveUserToOrg(request, history,line);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//起点和终点关联表保存历史记录
	public void saveStartAndEndHistory(LineinfoHistoryEntity history,LineInfoEntity line){
		
		if (StringUtil.isNotEmpty(line.getId())) {
			List<StartOrEndEntity> startlist  = this.systemService.findByProperty(StartOrEndEntity.class, "lineId", line.getId());
			if(startlist.size()>0){
				for (int i = 0; i < startlist.size(); i++) {
					StartOrEndEntity se = startlist.get(i);
					StartOrEndEntity st = new StartOrEndEntity();
					st.setEndid(se.getEndid());
					if (StringUtil.isNotEmpty(history.getId())) {
						st.setLineId(history.getId());
					}
					st.setLinetype(se.getLinetype());
					st.setStartid(se.getStartid());
					st.setStationStatus("1");
					this.systemService.save(st);
				}
			}
		}
		
	}
	
	/**
	 * 线路挂接
	 * 
	 * @param request
	 * @param lineInfo
	 * @return
	 */
	public AjaxJson saveUserToOrg(HttpServletRequest request, LineinfoHistoryEntity lineInfo,LineInfoEntity  line) {
		String message = null;
		AjaxJson j = new AjaxJson();
		List<LineBusstopHistoryEntity> list = new ArrayList<LineBusstopHistoryEntity>();

		if (StringUtil.isNotEmpty(line)) {
			List<Line_busStopEntity> lines = this.systemService.findByProperty(Line_busStopEntity.class, "lineId", line.getId());
			if(lines.size()>0){
				for (int i = 0; i < lines.size(); i++) {
					LineBusstopHistoryEntity lin_busStop = new LineBusstopHistoryEntity();
					lin_busStop.setLineId(lineInfo.getId());
					lin_busStop.setBusStopsId(lines.get(i).getBusStopsId());
					
					if (StringUtil.isNotEmpty(lines.get(i).getSiteOrder())) {
						lin_busStop.setSiteOrder(lines.get(i).getSiteOrder());
					}
					if (StringUtil.isNotEmpty(lineInfo.getVersion())) {
						lin_busStop.setVersion(lineInfo.getVersion());
					}
					list.add(lin_busStop);
				}
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
	 * 申请拒绝
	 */
	@RequestMapping(params = "refuse")
	@ResponseBody
	public AjaxJson refuse(HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		String id = request.getParameter("id");
		String rejectReason = request.getParameter("rejectReason");
		LineInfoEntity line = this.systemService.getEntity(LineInfoEntity.class, id);

		if (StringUtil.isNotEmpty(line)) {

			if ("1".equals(line.getApplicationStatus())) {
				line.setTrialReason(rejectReason);
			}
			if ("2".equals(line.getApplicationStatus())) {
				line.setReviewReason(rejectReason);
			}
			if ("1".equals(line.getApplicationStatus())) {
				line.setApplicationStatus("5");// 初审拒绝
				line.setFirstApplicationUser(ResourceUtil.getSessionUserName().getId());
				line.setFirstApplicationTime(AppUtil.getDate());
			} else if ("2".equals(line.getApplicationStatus())) {
				line.setApplicationStatus("6");// 复审拒绝
				line.setLastApplicationUser(ResourceUtil.getSessionUserName().getId());
				line.setLastApplicationTime(AppUtil.getDate());
			}
			
		}

		try {
			message = "拒绝成功！";
			this.systemService.saveOrUpdate(line);
		} catch (Exception e) {
			// TODO: handle exception
			message = "服务器异常！";
		}

		j.setMsg(message);
		return j;
	}

	/**
	 * 强制下架
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "coerceShelves")
	@ResponseBody
	public AjaxJson coerceShelves(HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		String id = request.getParameter("id");
		if(StringUtil.isNotEmpty(id)){
			String ids[]= id.split(",");
			for (int i = 0; i < ids.length; i++) {
				LineInfoEntity  line = this.systemService.getEntity(LineInfoEntity.class, ids[i]);
				if(StringUtil.isNotEmpty(line)){
					line.setStatus("1");
					line.setApplyContent("1");//申请内容
					line.setApplicationStatus("0");
					line.setLastApplicationTime(AppUtil.getDate());
					line.setLastApplicationUser(ResourceUtil.getSessionUserName().getId());
				}
				try {
					message = "强制下架成功！";
					this.systemService.saveOrUpdate(line);
				} catch (Exception e) {
					// TODO: handle exception
					message = "服务器异常！";
				}
			}

		}else{
			message="没有选中要强制下架的线路！";
		}

		j.setMsg(message);
		return j;
	}
	/**
	 * 去线路添加页面
	 * 
	 * @return
	 */
	@RequestMapping(params = "addCarRegion")
	public ModelAndView addCarRegion(LineInfoEntity lineInfo, HttpServletRequest req) {

		String lineId = req.getParameter("id");
		String history = req.getParameter("history");//历史记录标识，用于历史记录

		List<TSTypegroup> list = systemService.findByProperty(TSTypegroup.class, "typegroupcode", "car_type");
		for (int i = 0; i < list.size(); i++) {
			String typeId = list.get(i).getId();
			List<TSType> typelist = this.systemService.findByProperty(TSType.class, "TSTypegroup.id",typeId);
			req.setAttribute("typelist", typelist);
		}

		if(StringUtil.isNotEmpty(lineId)){
			req.setAttribute("lineId", lineId);
				
				List<Object> calist = this.systemService.findListbySql("select c.version from car_t_s_type_line c where c.line_id='"+lineId+"' ORDER BY c.version desc");
				if(calist.size()>0){
					Object ca = calist.get(0);
					if(!StringUtil.isNotEmpty(ca)){
						ca="0";
					}
					List<CarTSTypeLineEntity> carlist = systemService.findHql(
							"from CarTSTypeLineEntity where lineId=? and version=? ",lineId,ca);
					if(carlist.size()>0){
						req.setAttribute("carlist", carlist);//获取最高版本的已通过审核的车辆类型区间价格
					}
					
				}
			
		}

		return new ModelAndView("yhy/linesSpecial/addCarRegion");
	}

	/**
	 * 保存价格
	 */
	@RequestMapping(params = "savePrice")
	@ResponseBody
	public AjaxJson savePrice(HttpServletRequest request, HttpServletResponse respone) {

		String message = null;
		AjaxJson j = new AjaxJson();

		String price[] = request.getParameterValues("price");
		String lineId = request.getParameter("lineId");
		String typeid[] = request.getParameterValues("typeid");

		if(StringUtil.isNotEmpty(lineId)){
			List<CarTSTypeLineEntity> carlist = this.systemService.findByProperty(CarTSTypeLineEntity.class, "lineId", lineId);

			if(carlist.size()>0){
				
				List<Object> calist = this.systemService.findListbySql("select c.version from car_t_s_type_line c where c.line_id='"+lineId+"' ORDER BY c.version desc");
				Object ca = null;
				if(calist.size()>0){
					ca = calist.get(0);
				}
				if(!StringUtil.isNotEmpty(ca)){
					ca="0";
				}
				
				List<CarTSTypeLineEntity> cartslist = systemService.findHql(
						"from CarTSTypeLineEntity where lineId=? and version=? ",lineId,ca);
				if(cartslist.size()>0){
					for (int i = 0; i < cartslist.size(); i++) {
						CarTSTypeLineEntity cartype =cartslist.get(i);
						cartype.setCarTypePrice(new BigDecimal(price[i]));
						int version = Integer.parseInt(cartype.getVersion())+1;
						cartype.setVersion(version+"");
						message="修改价格成功！";
						carTSTypeLineServiceI.saveOrUpdate(cartype);
					}
				}
				
				
			}else{
				for (int i = 0; i < price.length; i++) {
					CarTSTypeLineEntity cartype = new CarTSTypeLineEntity();
					cartype.setLineId(lineId);
					cartype.setCarTypeId(typeid[i]);
					cartype.setCarTypePrice(new BigDecimal(price[i]));
					cartype.setApplyStatus("0");
					cartype.setVersion("1");
					try {
						message="保存价格成功！";
						carTSTypeLineServiceI.save(cartype);
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}
		}

		j.setMsg(message);
		return j;
	}

	/**
	 * 验证渠道商是否已添加车辆类型区间价格
	 */
	@RequestMapping(params = "checkCarRegion")
	@ResponseBody
	public AjaxJson checkCarRegion(HttpServletRequest request) {
		boolean success = false;
		AjaxJson j = new AjaxJson();

		String lineId = request.getParameter("id");
		if(StringUtil.isNotEmpty(lineId)){
			List<CarTSTypeLineEntity> carlist = this.systemService.findByProperty(CarTSTypeLineEntity.class, "lineId", lineId);
			if(carlist.size()>0){
				success=true;
			}
		}

		j.setSuccess(success);
		return j;
	}

	/**
	 * 修改区间价格
	 * 
	 * @return
	 */
	@RequestMapping(params = "applyEdit")
	public ModelAndView applyEdit(LineInfoEntity lineInfo, HttpServletRequest req) {

		String lineId = req.getParameter("id");

		List<TSTypegroup> list = systemService.findByProperty(TSTypegroup.class, "typegroupcode", "car_type");
		for (int i = 0; i < list.size(); i++) {
			String typeId = list.get(i).getId();
			List<TSType> typelist = this.systemService.findByProperty(TSType.class, "TSTypegroup.id",typeId);
			req.setAttribute("typelist", typelist);
		}

		if(StringUtil.isNotEmpty(lineId)){
			req.setAttribute("lineId", lineId);
			List<CarTSTypeLineEntity> carlist = this.systemService.findByProperty(CarTSTypeLineEntity.class, "lineId", lineId);
			if(carlist.size()>0){
				req.setAttribute("carlist", carlist);
			}
		}

		return new ModelAndView("yhy/linesSpecial/applyEdit");
	}

	/**
	 * 保存申请修改的价格
	 */
	@RequestMapping(params = "saveEditPrice")
	@ResponseBody
	public AjaxJson saveEditPrice(HttpServletRequest request, HttpServletResponse respone) {

		String message = null;
		AjaxJson j = new AjaxJson();

		String price[] = request.getParameterValues("price");
		String lineId = request.getParameter("lineId");
		String typeid[] = request.getParameterValues("typeid");

		if(StringUtil.isNotEmpty(lineId)){

			LineinfoHistoryEntity lnfo = this.systemService.getEntity(LineinfoHistoryEntity.class, lineId);
			lnfo.setApplicationEditStatus("1");
			lnfo.setApplyContent("2");
			lnfo.setApplicationEditTime(AppUtil.getDate());
			lnfo.setApplicationEditUserId(ResourceUtil.getSessionUserName().getId());

			try {
				List<Object> calist = this.systemService.findListbySql("select c.version from car_t_s_type_line c where c.line_id='"+lineId+"' ORDER BY c.version desc");
				Object ca = null;
				if(calist.size()>0){
					 ca = calist.get(0);
				}
				
				if(!StringUtil.isNotEmpty(ca)){
					ca="0";
				}
				for (int i = 0; i < price.length; i++) {
					CarTSTypeLineEntity cartype = new CarTSTypeLineEntity();

					cartype.setLineId(lineId);
					cartype.setCarTypeId(typeid[i]);
					cartype.setCarTypePrice(new BigDecimal(price[i]));
					cartype.setApplyStatus("0");
					cartype.setVersion(Integer.parseInt(ca.toString())+1+"");

					message="保存价格成功！";

					carTSTypeLineServiceI.save(cartype);

				}
				this.lineInfoService.saveOrUpdate(lnfo);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		j.setMsg(message);
		return j;
	}
	
	/**
	 * 申请同意
	 */
	@RequestMapping(params = "agreeEdit")
	@ResponseBody
	public AjaxJson agreeEdit(HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		String id = request.getParameter("id");
		LineinfoHistoryEntity  line = this.systemService.getEntity(LineinfoHistoryEntity.class, id);
		try {
		if(StringUtil.isNotEmpty(line)){
			if("1".equals(line.getApplicationEditStatus())){
				line.setApplicationEditStatus("2");//初审
				line.setFirstApplicationEditTime(AppUtil.getDate());
				line.setFirstApplicationEditUserId(ResourceUtil.getSessionUserName().getId());
				line.setFirstApplicationUser(ResourceUtil.getSessionUserName().getId());
			}else if("2".equals(line.getApplicationEditStatus())){
				line.setApplicationEditStatus("3");
				line.setLastApplicationEditTime(AppUtil.getDate());
				line.setLastApplicationEditUserId(ResourceUtil.getSessionUserName().getId());
				
				List<Object> calist = this.systemService.findListbySql("select c.version from car_t_s_type_line c where c.line_id='"+line.getId()+"' ORDER BY c.version desc");
				Object ca = null;
				if(calist.size()>0){
					ca = calist.get(0);
				}
				if(!StringUtil.isNotEmpty(ca)){
					ca="0";
				}
				List<CarTSTypeLineEntity> carlist = systemService.findHql(
						"from CarTSTypeLineEntity where lineId=? and version=? ",line.getId(),ca);
				if(carlist.size()>0){
					for (int i = 0; i < carlist.size(); i++) {
						CarTSTypeLineEntity cats = carlist.get(i);
						cats.setApplyStatus("1");
						this.systemService.saveOrUpdate(cats);
					}
				}
			}
		}
			message = "申请成功！";
			this.systemService.saveOrUpdate(line);
		} catch (Exception e) {
			// TODO: handle exception
			message = "服务器异常！";
		}

		j.setMsg(message);
		return j;
	}
	
	/**
	 * 申请拒绝
	 */
	@RequestMapping(params = "refuseEdit")
	@ResponseBody
	public AjaxJson refuseEdit(HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		String id = request.getParameter("id");
		String rejectReason = request.getParameter("rejectReason");
		LineinfoHistoryEntity line = this.systemService.getEntity(LineinfoHistoryEntity.class, id);

		if (StringUtil.isNotEmpty(line)) {

			if ("1".equals(line.getApplicationEditStatus())) {
				line.setTrialEditReason(rejectReason);
				line.setApplicationEditStatus("4"); //初审拒绝
				line.setFirstApplicationEditTime(AppUtil.getDate());
				line.setFirstApplicationEditUserId(ResourceUtil.getSessionUserName().getId());
			}
			if ("2".equals(line.getApplicationEditStatus())) {
				line.setReviewEditReason(rejectReason);
				line.setApplicationEditStatus("5");// 复审拒绝
				line.setLastApplicationEditTime(AppUtil.getDate());
				line.setLastApplicationEditUserId(ResourceUtil.getSessionUserName().getId());
			}
		}

		try {
			message = "拒绝成功！";
			this.systemService.saveOrUpdate(line);
		} catch (Exception e) {
			// TODO: handle exception
			message = "服务器异常！";
		}

		j.setMsg(message);
		return j;
	}
	
	
	/**
	 * 获取拒绝原因
	 */
	@RequestMapping(params = "getEditReason")
	@ResponseBody
	public AjaxJson getEditReason(HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		String id = request.getParameter("id");
		LineinfoHistoryEntity line = this.systemService.getEntity(LineinfoHistoryEntity.class, id);

		if (StringUtil.isNotEmpty(line)) {
			if (StringUtil.isNotEmpty(line.getReviewEditReason())) {
				message = line.getReviewEditReason();// 复审拒绝原因
			} else {
				message = line.getTrialEditReason();// 初审拒绝原因
			}
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 获取拒绝原因
	 */
	@RequestMapping(params = "delLine")
	@ResponseBody
	public AjaxJson delLine(HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		String id = request.getParameter("id");
		if (StringUtil.isNotEmpty(id)) {
			LineInfoEntity lineinfo = this.systemService.getEntity(LineInfoEntity.class, id);
			try {
				//删除起点和终点关联表
				List<StartOrEndEntity> startlist = this.systemService.findByProperty(StartOrEndEntity.class, "lineId", id);
				if(startlist.size()>0){
					for (int i = 0; i < startlist.size(); i++) {
						StartOrEndEntity start = startlist.get(i);
						this.systemService.delete(start);
					}
				}
				
				message = "删除成功！";
				this.systemService.delete(lineinfo);
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				message="服务异常！";
			}
		}
		j.setMsg(message);
		
		return j;
	}
}

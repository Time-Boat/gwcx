package com.yhy.lin.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
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

import com.yhy.lin.app.util.AppGlobals;
import com.yhy.lin.app.util.AppUtil;
import com.yhy.lin.entity.BusStopInfoEntity;
import com.yhy.lin.entity.LineInfoEntity;
import com.yhy.lin.entity.LineInfoView;
import com.yhy.lin.entity.OpenCityEntity;
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
		if (StringUtil.isNotEmpty(id)) {
			LineInfoView view = lineInfoService.getDetail(id);
			if (view != null) {
				request.setAttribute("View", view);
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
	 * 申请上架、申请下架
	 */
	@RequestMapping(params = "applyShelves")
	@ResponseBody
	public AjaxJson applyShelves(HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		String id = request.getParameter("id");
		//String lineStatus= request.getParameter("id");
		
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
				line.setApplicationStatus("2");//初审
				line.setFirstApplicationUser(ResourceUtil.getSessionUserName().getId());
				line.setFirstApplicationTime(AppUtil.getDate());
			}else if("2".equals(line.getApplicationStatus())){
				if("0".equals(line.getStatus())){
					line.setApplicationStatus("4");//复审
					line.setStatus("1");//已上架
				}else if("1".equals(line.getStatus())){
					line.setApplicationStatus("3");//复审
					line.setStatus("0");//已下架
				}
				line.setLastApplicationTime(AppUtil.getDate());
				line.setLastApplicationUser(ResourceUtil.getSessionUserName().getId());
			}
			//line.setApplicationUserid(ResourceUtil.getSessionUserName().getId());
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
				line.setFirstApplicationTime(AppUtil.getDate());
			} else if ("2".equals(line.getApplicationStatus())) {
				line.setApplicationStatus("6");// 复审拒绝
				line.setLastApplicationTime(AppUtil.getDate());
			}
			
			line.setApplicationTime(AppUtil.getDate());
			line.setApplicationUserid(ResourceUtil.getSessionUserName().getId());
		}

		try {
			message = "拒绝成功！";
			this.systemService.saveOrUpdate(line);
		} catch (Exception e) {
			// TODO: handle exception
			message = "服务器异常！";
		}

		j.setMsg(message);
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
					line.setApplicationTime(AppUtil.getDate());
					line.setApplicationUserid(ResourceUtil.getSessionUserName().getId());
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
}

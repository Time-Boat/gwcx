package com.yhy.lin.controller;
import java.util.ArrayList;
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
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;

import com.google.gson.Gson;
import com.yhy.lin.app.util.AppGlobals;
import com.yhy.lin.app.util.AppUtil;
import com.yhy.lin.app.util.SystemMessage;
import com.yhy.lin.entity.BusStopInfoEntity;
import com.yhy.lin.entity.CarTSTypeLineEntity;
import com.yhy.lin.entity.CitiesEntity;
import com.yhy.lin.entity.LineBusstopHistoryEntity;
import com.yhy.lin.entity.LineInfoEntity;
import com.yhy.lin.entity.Line_busStopEntity;
import com.yhy.lin.entity.LineinfoHistoryEntity;
import com.yhy.lin.entity.OpenCityEntity;
import com.yhy.lin.entity.StartOrEndEntity;
import com.yhy.lin.service.BusStopInfoServiceI;
import com.yhy.lin.service.LineinfoHistoryServiceI;
import com.yhy.lin.service.StartOrEndServiceI;

import net.sf.json.JSONObject;

import javax.validation.Validator;


/**   
 * @Title: Controller
 * @Description: 线路修改
 * @author zhangdaihao
 * @date 2017-10-30 15:59:01
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/lineinfoHistoryController")
public class LineinfoHistoryController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(LineinfoHistoryController.class);

	@Autowired
	private LineinfoHistoryServiceI lineinfoHistoryService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private BusStopInfoServiceI busStopInfoService;
	
	@Autowired
	private StartOrEndServiceI startOrEndService;
	@Autowired
	private Validator validator;

	/**
	 * 线路历史记录表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "lineinfoHistoryList")
	public ModelAndView list(HttpServletRequest request) {
		request.setAttribute("cityList", getOpencity());
		return new ModelAndView("yhy/linesSpecial/lineinfoHistoryList");
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
	public void datagrid(LineinfoHistoryEntity lineinfoHistory,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
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
		JSONObject jObject = lineinfoHistoryService.getDatagrid(lineinfoHistory, cityid, beginTime, endTime, dataGrid,
				lstartTime_begin, lstartTime_end, lendTime_begin, lendTime_end, linetype, username, departname,
				null);
		responseDatagrid(response, jObject);
	}

	/**
	 * 删除线路历史记录表
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(LineinfoHistoryEntity lineinfoHistory, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		lineinfoHistory = systemService.getEntity(LineinfoHistoryEntity.class, lineinfoHistory.getId());
		message = "线路历史记录表删除成功";
		lineinfoHistoryService.delete(lineinfoHistory);
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
	public ModelAndView addorupdate(LineinfoHistoryEntity lineInfo, HttpServletRequest req) {
		// 获取部门信息
		List<TSDepart> departList = systemService.getList(TSDepart.class);
		req.setAttribute("departList", departList);
		String id = req.getParameter("id");

		// 修改线路
		if (StringUtil.isNotEmpty(id)) {
			lineInfo = lineinfoHistoryService.getEntity(LineinfoHistoryEntity.class, id);
			req.setAttribute("lineInfo", lineInfo);
			req.setAttribute("startList", getStartLocation(lineInfo.getCityId(), lineInfo.getType()));
			req.setAttribute("endList",
					getEndLocation(lineInfo.getCityId(), lineInfo.getType(), lineInfo.getStartLocation()));
		}
		List<OpenCityEntity> cities = systemService.findByProperty(OpenCityEntity.class, "status", "0");
		req.setAttribute("cities", cities);
		return new ModelAndView("yhy/linesSpecial/lineinfoHistory");
	}
	
	/**
	 * 接送机线路保存
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(LineinfoHistoryEntity lineInfo, HttpServletRequest request, HttpServletResponse respone) {
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
				LineinfoHistoryEntity l = lineinfoHistoryService.getEntity(LineinfoHistoryEntity.class, lineInfo.getId());

				MyBeanUtils.copyBeanNotNull2Bean(lineInfo, l);
				// startOrEndService.saveOrUpdate(addrs);
				lineinfoHistoryService.saveOrUpdate(l);
				message = "线路修改成功！";
				// -----数据修改日志[类SVN]------------
				Gson gson = new Gson();
				//systemService.addDataLog("lineInfo", l.getId(), gson.toJson(l));
				// -----数据修改日志[类SVN]------------    报错，不知道什么鬼，注释掉
				//systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				message = "线路修改失败！";
				j.setSuccess(false);
				logger.error(ExceptionUtil.getExceptionMessage(e));
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
						"select DISTINCT b.id,b.name from busstopinfo b LEFT JOIN line_busstop_history a on b.id=a.busStopsId where  1=1 ");
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
						"select DISTINCT b.id,b.name from busstopinfo b LEFT JOIN line_busstop_history a on b.id=a.busStopsId where 1=1");
				if (StringUtil.isNotEmpty(cityid)) {
					st.append(" and b.cityId='" + cityid + "'");
				}
				st.append("and b.station_type = '0'");
			}
		} else {
			st.append(
					"select DISTINCT b.id,b.name from busstopinfo b LEFT JOIN line_busstop_history a on b.id=a.busStopsId where  1=1");
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
							" and b.id not in (select g.id from line_busstop_history f LEFT JOIN busstopinfo g on g.id=f.busStopsId where f.siteId='");
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
							" and b.id not in (select lb.siteId from line_busstop_history lb LEFT JOIN busstopinfo a on a.id=lb.busStopsId where lb.siteId is not null");
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
	 * 线路挂接页面
	 */
	@RequestMapping(params = "addBusStop")
	public ModelAndView lineAddBusStop(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("lineInfoId", request.getParameter("lineInfoId"));
		request.setAttribute("lineType", request.getParameter("lineType"));
		return new ModelAndView("yhy/lines/lineHistoryAddBusStopList");
	}
	
	/**
	 * 根据线路id查询对应的站点信息
	 */
	@RequestMapping(params = "busStopByLineDatagrid")
	public void roleUserDatagrid(BusStopInfoEntity busStopInfo, HttpServletRequest request,
			HttpServletResponse response, DataGrid dataGrid) {
		String lineInfoId = request.getParameter("lineInfoId");

		JSONObject jObject = busStopInfoService.getDatagrid4(lineInfoId, dataGrid);
		responseDatagrid(response, jObject);
	}
	
	/**
	 * 申请修改
	 */
	@RequestMapping(params = "applyEdit")
	@ResponseBody
	public AjaxJson applyEdit(HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		boolean success = false;
		String lineId = request.getParameter("id");
		String isDealerLine = request.getParameter("isDealerLine");
		try {
			
		if(StringUtil.isNotEmpty(lineId)){
			
			if(isDealerLine.contains("1")){
				if(StringUtil.isNotEmpty(lineId)){
					List<CarTSTypeLineEntity> carlist = this.systemService.findByProperty(CarTSTypeLineEntity.class, "lineId", lineId);
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

			LineinfoHistoryEntity info = this.systemService.getEntity(LineinfoHistoryEntity.class, lineId);
			
			List<LineInfoEntity> lineinfolist = this.systemService.findByProperty(LineInfoEntity.class, "lineNumber", info.getLineNumber());
			if(lineinfolist.size()>0){
				for (int i = 0; i < lineinfolist.size(); i++) {
					LineInfoEntity lineinfo = lineinfolist.get(i);
					if("1".equals(lineinfo.getApplicationStatus()) || "2".equals(lineinfo.getApplicationStatus())){
						message = "线路正在审核，请勿修改！";
						j.setMsg(message);
						return j;
					}
				}
			}
			
			info.setApplicationEditStatus("1");
			info.setApplyContent("2");
			info.setApplicationEditTime(AppUtil.getDate());
			info.setApplicationEditUserId(ResourceUtil.getSessionUserName().getId());
			systemService.saveOrUpdate(info);
			
			//根据创建线路的运营专员向上找一级，找到运营经理的id
			List<String> userList = this.systemService.findListbySql(
					" select u.id from t_s_base_user u left join t_s_user_org o on o.user_id = u.id left join t_s_depart t on t.id = o.org_id "
					+ " where t.org_code = SUBSTRING((select t.org_code from t_s_base_user u left join t_s_user_org o on o.user_id = u.id "
					+ " left join t_s_depart t on o.org_id = t.id where u.id = '" + info.getCreateUserId() + "') ,1,9)");
			
			String[] users = new String[userList.size()];
			
			SystemMessage.getInstance().saveMessage(
					systemService, "线路修改待审核", "您有一条修改线路待审核，请尽快处理。", new String[]{AppGlobals.OPERATION_MANAGER}
					, new String[]{"1","2"}, users);
		}
			success=true;
			message = "申请成功！";
		} catch (Exception e) {
			// TODO: handle exception
			message = "服务器异常！";
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
			//初审
			if("1".equals(line.getApplicationEditStatus())){
				
				j=checkEnabled(line);
				if(j.isSuccess()==false){
					return j;
				}
				line.setApplicationEditStatus("2");//初审同意
				line.setFirstApplicationEditTime(AppUtil.getDate());
				line.setFirstApplicationEditUserId(ResourceUtil.getSessionUserName().getId());
				line.setFirstApplicationUser(ResourceUtil.getSessionUserName().getId());

				//根据创建线路的运营专员，找到其所属的公司，然后根据所属公司找到管理这家公司的平台审核员
				List<String> list = systemService.findListbySql("select tsu.id from t_s_role r left join t_s_role_user ru on r.id = ru.roleid "
						+ " left join t_s_user tsu on tsu.id = ru.userid where r.rolecode in ('" + AppGlobals.PLATFORM_LINE_AUDIT + "') and tsu.org_company like "
						+ " CONCAT('%',(select SUBSTRING(t.org_code,1,6) from lineinfo l left join t_s_user_org o on o.user_id = l.createUserId "
						+ " left join t_s_depart t on o.org_id = t.id where l.id = '" + line.getId() + "'),'%')");
				
				String[] users = new String[list.size()];
				
				SystemMessage.getInstance().saveMessage(
						systemService, "线路修改待审核", "您有一条修改线路待审核，请尽快处理。", new String[]{AppGlobals.PLATFORM_LINE_AUDIT}
						, new String[]{"1","2"}, users);
			//复审
			}else if("2".equals(line.getApplicationEditStatus())){
				
				
				j=checkEnabled(line);
				if(j.isSuccess()==false){
					return j;
				}
				line.setApplicationEditStatus("3");
				line.setLastApplicationEditTime(AppUtil.getDate());
				line.setLastApplicationEditUserId(ResourceUtil.getSessionUserName().getId());
				
				
				LineinfoHistoryEntity history = new LineinfoHistoryEntity();
				MyBeanUtils.copyBeanNotNull2Bean(line, history);
				List<Object> calist = this.systemService.findListbySql("SELECT a.version from lineinfo_history a where a.lineNumber = '"+line.getLineNumber()+"' ORDER BY a.version desc");
				Object ca = null;
				if(calist.size()>0){
					ca = calist.get(0);
				}
				if(!StringUtil.isNotEmpty(ca)){
					ca="0";
				}
				history.setVersion(Integer.parseInt(ca.toString())+1+"");
				this.systemService.save(history);
				saveCarTSTypeLine(history);
				
				saveCarTSTypeLine(line);  //修改车辆类型区间价格状态
				//修改线路赋值
				saveLineinfo(line);
				saveLineUserToOrg(line);
				
				SystemMessage.getInstance().saveMessage(
						systemService, "线路已修改", line.getName() + "的线路修改申请已通过审核，请及时查看。", 
						new String[]{AppGlobals.OPERATION_MANAGER, AppGlobals.OPERATION_SPECIALIST}, 
						new String[]{"1","2"}, new String[]{line.getCreateUserId(),line.getFirstApplicationUser()});
			}
		}
			message = "申请成功！";
			this.systemService.saveOrUpdate(line);
			
		} catch (Exception e) {
			// TODO: handle exception
			message = "服务器异常！";
			e.printStackTrace();
		}

		j.setMsg(message);
		return j;
	}
	
	
	//验证是否存在站点已挂接
		public AjaxJson checkEnabled(LineinfoHistoryEntity  line){
			boolean success=false;
			String message = null;
			AjaxJson j = new AjaxJson();
			//判断站点是否被启用
			if(StringUtil.isNotEmpty(line.getId())){
				List<StartOrEndEntity> startlist = this.systemService.findByProperty(StartOrEndEntity.class, "lineId", line.getId());
				List<LineInfoEntity> infolist = this.systemService.findByProperty(LineInfoEntity.class, "lineNumber", line.getLineNumber());
				if(infolist.size()>0){
					LineInfoEntity lineinfo = infolist.get(0);
					if(startlist.size()>0){
						for (int i = 0; i < startlist.size(); i++) {
							StartOrEndEntity see = startlist.get(i);
							if(StringUtil.isNotEmpty(see)){
								List<StartOrEndEntity> list = this.systemService.findHql("from StartOrEndEntity where startid=? and endid=? and stationStatus=? and linetype=?", see.getStartid(),see.getEndid(),"1",line.getType());
								if(list.size()>0){
									for (int k = 0; k < list.size(); k++) {
										StartOrEndEntity sre = list.get(k);
										if(!line.getId().equals(sre.getLineId()) && !lineinfo.getId().equals(sre.getLineId())){
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
				}
				
				
			}
			j.setMsg(message);
			return j;
	}
	
	public void saveCarTSTypeLine(LineinfoHistoryEntity  line){
		List<Object> calist = this.systemService.findListbySql("select c.version from car_t_s_type_line c where c.line_id='"+line.getId()+"' ORDER BY c.version desc");
		Object ca = null;
		if(calist.size()>0){
			ca = calist.get(0);
		}
		LineInfoEntity lineInfo = new LineInfoEntity();
		if(StringUtil.isNotEmpty(line.getLineNumber())){
			List<LineInfoEntity> lineInfolist = this.systemService.findByProperty(LineInfoEntity.class, "lineNumber", line.getLineNumber());
			if(lineInfolist.size()>0){
				 lineInfo = lineInfolist.get(0);
			}
		}
		
		if(!StringUtil.isNotEmpty(ca)){
			ca="0";
		}
		List<CarTSTypeLineEntity> carlist = systemService.findHql(
				"from CarTSTypeLineEntity where lineId=? and version=? ",line.getId(),ca);
		if(carlist.size()>0){
			for (int i = 0; i < carlist.size(); i++) {
				CarTSTypeLineEntity cats = carlist.get(i);
				CarTSTypeLineEntity carTSTypeLine = new CarTSTypeLineEntity();
				try {
					//MyBeanUtils.copyBeanNotNull2Bean(cats, carTSTypeLine);
					carTSTypeLine.setCarTypeId(cats.getCarTypeId());
					carTSTypeLine.setCarTypePrice(cats.getCarTypePrice());
					if(StringUtil.isNotEmpty(lineInfo)){
						carTSTypeLine.setLineId(lineInfo.getId());
					}
					carTSTypeLine.setApplyStatus("1");
					int cas =Integer.parseInt(ca.toString())+1;
					carTSTypeLine.setVersion(cas+"");
					this.systemService.save(carTSTypeLine);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
	}
	
	/**
	 * 修改线路赋值
	 * @param line
	 */
	public void saveLineinfo(LineinfoHistoryEntity line){
		if(StringUtil.isNotEmpty(line)){
			List<LineInfoEntity> lineInfolist = this.systemService.findByProperty(LineInfoEntity.class, "lineNumber", line.getLineNumber());
			if(lineInfolist.size()>0){
				LineInfoEntity lineInfo = lineInfolist.get(0);
				if(StringUtil.isNotEmpty(line.getDispath())){
					lineInfo.setDispath(line.getDispath());
				}
				if(StringUtil.isNotEmpty(line.getLineTimes())){
					lineInfo.setLineTimes(line.getLineTimes());
				}
				if(StringUtil.isNotEmpty(line.getPrice())){
					lineInfo.setPrice(line.getPrice());
				}
				if(StringUtil.isNotEmpty(line.getIsDealerLine())){
					lineInfo.setIsDealerLine(line.getIsDealerLine());
				}
				if(StringUtil.isNotEmpty(line.getRemark())){
					lineInfo.setRemark(line.getRemark());
				}
				if(StringUtil.isNotEmpty(line.getVersion())){
					lineInfo.setVersion(line.getVersion());
				}
				try {
					this.systemService.saveOrUpdate(lineInfo);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
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
	public void saveLineUserToOrg(LineinfoHistoryEntity line) {
		String message = null;
		AjaxJson j = new AjaxJson();
		if(StringUtil.isNotEmpty(line)){
			List<LineInfoEntity> lineInfolist = this.systemService.findByProperty(LineInfoEntity.class, "lineNumber", line.getLineNumber());
			LineInfoEntity lineInfo = lineInfolist.get(0);
			if(lineInfolist.size()>0){
				
				if(StringUtil.isNotEmpty(lineInfo.getId())){
					List<Line_busStopEntity> linebustoplist = this.systemService.findByProperty(Line_busStopEntity.class, "lineId", lineInfo.getId());
					if(linebustoplist.size()>0){
						for (int i = 0; i < linebustoplist.size(); i++) {
							Line_busStopEntity linebustop= linebustoplist.get(i);
							
							if ("2".equals(lineInfo.getType()) || "4".equals(lineInfo.getType())) {
								List<StartOrEndEntity> list = systemService.findHql(  //删除起点和终点的关联
										"from StartOrEndEntity where startid=? and lineId=? and linetype=?",
										lineInfo.getStartLocation(), lineInfo.getId(), lineInfo.getType());
								if(list.size()>0){
									for (int k = 0; k < list.size(); k++) {
										startOrEndService.delete(list.get(k));
									}
								}
								
							}
							if ("3".equals(lineInfo.getType()) || "5".equals(lineInfo.getType())) {
								List<StartOrEndEntity> list = systemService.findHql(
										"from StartOrEndEntity where lineId=? and endid=? and linetype=?",lineInfo.getId(),
										lineInfo.getEndLocation(), lineInfo.getType());
								if(list.size()>0){
									for (int k = 0; k < list.size(); k++) {
										startOrEndService.delete(list.get(k));
									}
								}
							}
							systemService.deleteEntityById(Line_busStopEntity.class, linebustop.getId());//删除原先线路关联站点挂接
						}
					}
				}
			}
			
			List<LineBusstopHistoryEntity> linebuslist = this.systemService.findByProperty(LineBusstopHistoryEntity.class, "lineId", line.getId());
			if(linebuslist.size()>0){
				for (int i = 0; i < linebuslist.size(); i++) {
					LineBusstopHistoryEntity history = linebuslist.get(i);
					Line_busStopEntity linebustop = new Line_busStopEntity();
					if(StringUtil.isNotEmpty(history.getArrivalTime())){
						linebustop.setArrivalTime(history.getArrivalTime());
					}
					linebustop.setBusStopsId(history.getBusStopsId());
					linebustop.setLineId(lineInfo.getId());
					if(StringUtil.isNotEmpty(history.getSiteId())){
						linebustop.setSiteId(history.getSiteId());
					}
					linebustop.setVersion(lineInfo.getVersion());
					linebustop.setSiteOrder(history.getSiteOrder());
					systemService.save(linebustop);
					
					StartOrEndEntity st = new StartOrEndEntity();
		        	if("2".equals(line.getType()) || "4".equals(line.getType())){
		        		
						if (StringUtil.isNotEmpty(line.getStartLocation())) {
							st.setStartid(line.getStartLocation());
							System.out.println(line.getStartLocation());
						}
						if (StringUtil.isNotEmpty(history.getBusStopsId())) {
							st.setEndid(history.getBusStopsId());
						}
						st.setLineId(lineInfo.getId());
						st.setStationStatus("1");
						st.setLinetype(line.getType());
						
		        	}else if("3".equals(line.getType()) || "5".equals(line.getType())){
		        		
		        		if (StringUtil.isNotEmpty(history.getBusStopsId())) {
		        			st.setStartid(history.getBusStopsId());
						}
						if (StringUtil.isNotEmpty(line.getEndLocation())) {
							st.setEndid(line.getEndLocation());
						}
						st.setLineId(lineInfo.getId());
						st.setStationStatus("1");
						st.setLinetype(line.getType());
		 		}
		        	this.systemService.save(st);
				}
			}
		}
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
				LineBusstopHistoryEntity lines = systemService.getEntity(LineBusstopHistoryEntity.class, id);
				if ("1".equals(bus.getStationType())) {
					message = "火车站点不可删除";
				} else if ("2".equals(bus.getStationType())) {
					message = "机场站点不可删除";
				} else if (lines.getSiteOrder() == 99) {
					message = "终点站点不可删除";
				} else {
					LineinfoHistoryEntity lin = systemService.getEntity(LineinfoHistoryEntity.class, lines.getLineId());
					if (StringUtil.isNotEmpty(lines)) {
						List<LineBusstopHistoryEntity> list = this.systemService.findByQueryString(
								"from LineBusstopHistoryEntity where siteOrder!='99' and lineId='" + lin.getId() + "' and siteOrder>" + lines.getSiteOrder());
						if (list.size() > 0) {
							for (int i = 0; i < list.size(); i++) {
								LineBusstopHistoryEntity buss = list.get(i);
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
								"from StartOrEndEntity where startid=? and endid=? and lineId =? and linetype=?",
								lin.getStartLocation(), zdId,lin.getId(), lin.getType());
						systemService.delete(list.get(0));
					}
					if ("3".equals(lin.getType()) || "5".equals(lin.getType())) {
						List<StartOrEndEntity> list = systemService.findHql(
								"from StartOrEndEntity where startid=? and endid=? and lineId =? and linetype=?", zdId,
								lin.getEndLocation(), lin.getId(),lin.getType());
						systemService.delete(list.get(0));
					}

					systemService.deleteEntityById(LineBusstopHistoryEntity.class, id);

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
}

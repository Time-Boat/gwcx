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

import com.alibaba.druid.pool.vendor.SybaseExceptionSorter;
import com.google.gson.Gson;
import com.yhy.lin.entity.BusStopInfoEntity;
import com.yhy.lin.entity.CitiesEntity;
import com.yhy.lin.entity.LineInfoEntity;
import com.yhy.lin.entity.Line_busStopEntity;
import com.yhy.lin.entity.OpenCityEntity;
import com.yhy.lin.service.BusStopInfoServiceI;
import com.yhy.lin.service.LineInfoServiceI;

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
		String cityID  = request.getParameter("cityID");// 城市
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
			jObject = lineInfoService.getDatagrid3(lineInfos,cityID, beginTime, endTime, dataGrid, lstartTime_begin,
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
			Line_busStopEntity lines = systemService.getEntity(Line_busStopEntity.class, id);
			LineInfoEntity lin = systemService.getEntity(LineInfoEntity.class, lines.getLineId());
			if (StringUtil.isNotEmpty(lines.getSiteId())) {
				lines.setSiteId("");
			}
			
			systemService.deleteEntityById(Line_busStopEntity.class, id);
			
			//将站点状态改为0
			systemService.updateBySqlString("update busstopinfo set status=REPLACE(status,'"+lin.getType()+"','') where id='"+zdId+"'");
			
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
		TSUser user = ResourceUtil.getSessionUserName();
		
		lineInfo.setDepartId(user.getCurrentDepart().getId());
		lineInfo.setCreatePeople(user.getUserName());
		lineInfo.setCreateUserId(user.getId());
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
				message = "线路修改失败！";
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
		//线路挂接
		saveUserToOrg(request, lineInfo);
		j.setMsg(message);
		return j;
	}

	/**
	 * 线路挂接
	 * @param request
	 * @param lineInfo
	 * @return
	 */
	public AjaxJson saveUserToOrg(HttpServletRequest request,LineInfoEntity lineInfo){
		String message = null;
        AjaxJson j = new AjaxJson();
		Line_busStopEntity lin_busStop = new Line_busStopEntity();
		Line_busStopEntity lin_busStop1 = new Line_busStopEntity();
		 List<Line_busStopEntity> list = new ArrayList<Line_busStopEntity>();
		 String startlocaid = request.getParameter("startLocation");
		 String endlocaid = request.getParameter("endLocation");
		 
		 if("2".equals(lineInfo.getType()) || "4".equals(lineInfo.getType())){
			 lin_busStop.setSiteId(startlocaid);
			 lin_busStop1.setSiteId(startlocaid);
		 }else if("3".equals(lineInfo.getType()) || "5".equals(lineInfo.getType())){
			 lin_busStop.setSiteId(endlocaid);
			 lin_busStop1.setSiteId(endlocaid);
			 
		 }
			
			StringBuffer str = new StringBuffer();
			StringBuffer str1 = new StringBuffer();
			StringBuffer str2 = new StringBuffer();
			
			if(StringUtil.isNotEmpty(startlocaid)){
				str2.append("select b.name from busstopinfo b,line_busstop a where b.id='");
				str2.append(startlocaid);
				str2.append("' and a.busStopsId=b.id and a.lineId='");
				str2.append(lineInfo.getId()+"'");
				
				List<Object> bList =systemService.findListbySql(str2.toString());
				if(bList.size()>0){
					message="站点存在！";
				}else{
					lin_busStop.setLineId(lineInfo.getId());
					lin_busStop.setBusStopsId(startlocaid);
					lin_busStop.setSiteOrder(0);
					list.add(lin_busStop);
					if(StringUtil.isNotEmpty(lineInfo.getType())){
						str.append("update busstopinfo set status=CONCAT(status,'");
						str.append(lineInfo.getType());
						str.append("') where ");
						str.append("id = '"+startlocaid+"' and ");
						str.append(" station_type = '0' ");
						systemService.updateBySqlString(str.toString());
					}
					
				}
				
			}
			
			if(StringUtil.isNotEmpty(endlocaid)){
				String sql="select b.name from busstopinfo b,line_busstop a where b.id='"+endlocaid+"' and a.busStopsId=b.id and a.lineId='"+lineInfo.getId()+"'";
				
				List<Object> bList =systemService.findListbySql(sql);
				if(bList.size()>0){
					message="站点存在！";
				}else{
					lin_busStop1.setLineId(lineInfo.getId());
					lin_busStop1.setBusStopsId(endlocaid);
					lin_busStop1.setSiteOrder(0);
					list.add(lin_busStop1);
					str1.append("update busstopinfo set status=CONCAT(status,'");
					str1.append(lineInfo.getType());
					str1.append("') where ");
					str1.append("id = '"+endlocaid+"' and ");
					str1.append(" station_type = '0' ");
					systemService.updateBySqlString(str1.toString());
				}
			}
			try{
			if(list.size()>0){
				systemService.saveAllEntitie(list);//挂载站点
			}
			
			message="站点挂接成功";
        }catch (Exception e) {
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
	
	
	/**
	 * 根据城市添加城市站点
	 */
	@RequestMapping(params = "getProvinceJson")
	@ResponseBody
	public JSONArray getLineJson(BusStopInfoEntity busStopInfo,HttpServletRequest request, HttpServletResponse response) {
        String city = request.getParameter("city");
        String type = request.getParameter("type");
        String starts = request.getParameter("starts");
        String ends = request.getParameter("ends");
        String ids = request.getParameter("ids");
        
        JSONObject jsonObj = new JSONObject(); 
        JSONArray jsonArray = new JSONArray();
        StringBuffer st = new StringBuffer();
        
        String sql = "select * from line_busstop a";
        List<Object> sqlList =systemService.findListbySql(sql);
        //判断是否有挂机数据
        if(sqlList.size()>0){
        	st.append("select DISTINCT b.id,b.name,b.station_type from busstopinfo b,line_busstop a where ");
            
            st.append(" b.cityId='");
        	st.append(city+"'");
        	
        	if(type.equals("2") || type.equals("3")){
    			st.append(" and b.station_type != 1");
    		}
    		if(type.equals("4") || type.equals("5")){
    			st.append(" and b.station_type != 2");
    		}
    		if(type.equals("0")){
    			st.append(" and b.station_type = '0'");
    		}
    		
    		if(StringUtil.isNotEmpty(type)){
    			st.append(" and (b.status not like'%");
    			st.append(type);
    			st.append("%'");
    		}else{
    			st.append(" and (b.status ='0'");
    		}
    		
    		st.append("or (a.lineId= '");
    		st.append(ids+"'");
    		st.append(" and a.busStopsId=b.id))");
        }else{
        	st.append("select DISTINCT b.id,b.name,b.station_type from busstopinfo b where ");
            
            st.append(" b.cityId='");
        	st.append(city+"'");
        	
        	if(type.equals("2") || type.equals("3")){
    			st.append(" and b.station_type != 1");
    		}
    		if(type.equals("4") || type.equals("5")){
    			st.append(" and b.station_type != 2");
    		}
    		if(type.equals("0")){
    			st.append(" and b.station_type = '0'");
    		}
    		
    		if(StringUtil.isNotEmpty(type)){
    			st.append(" and b.status not like'%");
    			st.append(type);
    			st.append("%'");
    		}else{
    			st.append(" and b.status ='0'");
    		}
    		
        }
        
        List<Object> bList =systemService.findListbySql(st.toString());
        String startname = "";
        String endname = "";
        if(StringUtil.isNotEmpty(starts)){
        	List<BusStopInfoEntity> sList = busStopInfoService.findByProperty(BusStopInfoEntity.class,"id",starts);
        	if(sList.size()>0){
        		for (int i = 0; i < sList.size(); i++) {
    				 startname = sList.get(i).getName();
    			}
        	}
        	
        }
        
        if(StringUtil.isNotEmpty(ends)){
        	List<BusStopInfoEntity> eList = busStopInfoService.findByProperty(BusStopInfoEntity.class,"id",ends);
        	if(eList.size()>0){
        		for (int i = 0; i < eList.size(); i++) {
    				 endname = eList.get(i).getName();
    			}
        	}
        }
        
        if(bList.size()>0){
        	for (int i = 0; i < bList.size(); i++) {
        		Object [] ob = (Object[]) bList.get(i);
				String id = (String) ob[0];
				String name = (String) ob[1];
				String statype = (String) ob[2];
				jsonObj.put("stopid", id);
				jsonObj.put("name", name);
				jsonObj.put("statype", statype);
				jsonObj.put("startname", startname);
				jsonObj.put("endname", endname);
            	jsonArray.add(jsonObj);
			}
        	
        }
       
		return jsonArray;
	}
	
	/**
	 * 根据城市添加城市站点
	 */
	@RequestMapping(params = "getEndlocation")
	@ResponseBody
	public JSONArray getEndlocation(BusStopInfoEntity busStopInfo,HttpServletRequest request, HttpServletResponse response) {
		String city = request.getParameter("city");
        String type = request.getParameter("type");
        String starts = request.getParameter("starts");
        String startLocation = request.getParameter("startLocation");
        JSONObject jsonObj = new JSONObject(); 
        JSONArray jsonArray = new JSONArray();
        
        StringBuffer st = new StringBuffer();
        
        if(StringUtil.isNotEmpty(type)){
        	if("2".equals(type) || "4".equals(type)){
        		st.append("select DISTINCT b.id,b.name from busstopinfo b  where 1=1 ");
        		
        		if(StringUtil.isNotEmpty(city)){
                	st.append(" and b.cityId='"+city+"'");
                }
        		st.append("and b.station_type = '0'");
        		
        		if(StringUtil.isNotEmpty(startLocation)){
        			st.append(" and b.id not in (select g.id from line_busstop f LEFT JOIN busstopinfo g on g.id=f.busStopsId where f.siteId='");
        			st.append(startLocation+"'");
        		}
        		st.append(" and g.status like '%"+type+"%')");
        		
        	}
        	
        	if("3".equals(type) || "5".equals(type)){
        		st.append("select DISTINCT b.id,b.name from busstopinfo b  where 1=1 ");
                if(StringUtil.isNotEmpty(city)){
                	st.append(" and b.cityId='"+city+"'");
                }
                if("3".equals(type)){
                	st.append("and b.station_type = '2'");
                }
                if("5".equals(type)){
                	st.append("and b.station_type = '1'");
                }
                
                if(StringUtil.isNotEmpty(startLocation)){
                	st.append(" and b.id not in (select lb.siteId from line_busstop lb LEFT JOIN busstopinfo a on a.id=lb.busStopsId where lb.siteId is not null");
                	st.append(" and lb.busStopsId='"+startLocation+"'");
                	st.append(" and a.status like '%"+type+"%')");
        		}
        	}
        }
        
        System.out.println(st.toString());
        List<Object> bList =systemService.findListbySql(st.toString());
        
        if(bList.size()>0){
        	for (int i = 0; i < bList.size(); i++) {
        		Object [] ob = (Object[]) bList.get(i);
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
	 * 根据城市添加城市站点
	 */
	@RequestMapping(params = "getStartLocation")
	@ResponseBody
	public JSONArray getStartLocation(BusStopInfoEntity busStopInfo,HttpServletRequest request, HttpServletResponse response) {
        String city = request.getParameter("city");
        String type = request.getParameter("type");
        String endLocation = request.getParameter("ends");
        
        JSONObject jsonObj = new JSONObject(); 
        JSONArray jsonArray = new JSONArray();
        
        StringBuffer st = new StringBuffer();
        
        if(StringUtil.isNotEmpty(type)){
        	if("2".equals(type) || "4".equals(type)){
        		st.append("select DISTINCT b.id,b.name from busstopinfo b LEFT JOIN line_busstop a on b.id=a.busStopsId where  1=1 ");
                if(StringUtil.isNotEmpty(city)){
                	st.append(" and b.cityId='"+city+"'");
                }
                if("2".equals(type)){
                	st.append("and b.station_type = '2'");
                }
                if("4".equals(type)){
                	st.append("and b.station_type = '1'");
                }
        		
        		if(StringUtil.isNotEmpty(endLocation)){
        			Line_busStopEntity lb = this.systemService.getEntity(Line_busStopEntity.class, endLocation);
        			st.append(" and a.siteId!='");
    				st.append(lb.getSiteId()+"'");
        		}
        	}
        	
        	if("3".equals(type) || "5".equals(type)){
        		st.append("select DISTINCT b.id,b.name from busstopinfo b LEFT JOIN line_busstop a on b.id=a.busStopsId where 1=1");
                if(StringUtil.isNotEmpty(city)){
                	st.append(" and b.cityId='"+city+"'");
                }
                st.append("and b.station_type = '0'");
                
        		if(StringUtil.isNotEmpty(endLocation)){
        			st.append(" and a.siteId!='");
    				st.append(endLocation+"'");
        		}
        		
        	}
        }
        
        List<Object> bList =systemService.findListbySql(st.toString());
        
        if(bList.size()>0){
        	for (int i = 0; i < bList.size(); i++) {
        		Object [] ob = (Object[]) bList.get(i);
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

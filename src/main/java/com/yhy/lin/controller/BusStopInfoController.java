package com.yhy.lin.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import org.jeecgframework.core.util.UUIDGenerator;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.yhy.lin.entity.BusStopInfoEntity;
import com.yhy.lin.entity.CitiesEntity;
import com.yhy.lin.entity.LineBusstopHistoryEntity;
import com.yhy.lin.entity.LineInfoEntity;
import com.yhy.lin.entity.Line_busStopEntity;
import com.yhy.lin.entity.LineinfoHistoryEntity;
import com.yhy.lin.entity.OpenCityEntity;
import com.yhy.lin.entity.StartOrEndEntity;
import com.yhy.lin.app.util.AppGlobals;
import com.yhy.lin.comparators.SortBySeq;
import com.yhy.lin.entity.AreaStationDTO;
import com.yhy.lin.entity.AreaStationMiddleEntity;
import com.yhy.lin.service.BusStopInfoServiceI;
import com.yhy.lin.service.StartOrEndServiceI;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 站点信息管理
 * @author linj
 *   
 */
@Controller
@RequestMapping("/busStopInfoController")
public class BusStopInfoController extends BaseController {
	private static final Logger logger = Logger.getLogger(BusStopInfoController.class);
	
	@Autowired
	private SystemService systemService;
	
	@Autowired
	private BusStopInfoServiceI busStopInfoService;
	
	@Autowired
	private StartOrEndServiceI startOrEndService;
	
	//班车站点
	@RequestMapping(params="busStopInfoList")
	public ModelAndView stopList(HttpServletRequest request){
		return new ModelAndView("yhy/busStop/busStopInfoList");
	}
	
	//接送机
	@RequestMapping(params="busStopInfoList2")
	public ModelAndView stopList2(HttpServletRequest request){
		request.setAttribute("cityList",getOpencity());
		
		//权限判断，只有运营专员和运营经理能看到安排司机车辆按钮
		if(checkRole(AppGlobals.OPERATION_SPECIALIST) || checkRole(AppGlobals.OPERATION_MANAGER) || checkRole(AppGlobals.SUBSIDIARY_ADMIN)){
			request.setAttribute("permission", "0");
		}else{
			request.setAttribute("permission", "1");
		}
		return new ModelAndView("yhy/busStop/busStopInfoList2");
	}
	
	/**
	 * 获得开通城市
	 * @return
	 */
	public String getOpencity(){
		String sql = "select op.city_id,op.city_name from open_city op where op.status='0' ";
		List<Object> list = this.systemService.findListbySql(sql);
		StringBuffer json = new StringBuffer("{'data':[");
		if(list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				Object[] ob = (Object[]) list.get(i);
				String id = ob[0]+"";
				String cityName = ob[1]+"";
					json.append("{");
					json.append("'cityID':'" +id + "',");
					json.append("'cityName':'"+ cityName + "'");
					json.append("},");
				}
			}
		json.delete(json.length()-1, json.length());
		json.append("]}");
		return json.toString();
	}
	
	@RequestMapping(params="datagrid")
	public void datagrid(BusStopInfoEntity busStopInfo,HttpServletRequest request,HttpServletResponse response,DataGrid dataGrid){
		String cityID = request.getParameter("cityID");
		String createTime_begin = request.getParameter("createTime_begin");
		String createTime_end = request.getParameter("createTime_end");
		JSONObject jObject = busStopInfoService.getDatagrid(busStopInfo, dataGrid,cityID,createTime_begin,createTime_end);
		responseDatagrid(response, jObject);
	}
	
	/**
	 * 删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(BusStopInfoEntity busStopInfo, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		String del = request.getParameter("deleteFlag");
		try{
			/*
			 * 通过deleteFlag来区别逻辑删除和物理删除
			 * 1：逻辑删除     2 ：物理删除
			 */
			busStopInfo = systemService.getEntity(BusStopInfoEntity.class, busStopInfo.getId());
			if("1".equals(del)){
				busStopInfo.setDeleteFlag((short)1);
				message = "站点下架成功(逻辑删除)";
				busStopInfoService.saveOrUpdate(busStopInfo);
			}else{
				busStopInfoService.delete(busStopInfo);
			}
			message = "下架成功";
		}catch (Exception e) {
		}
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量逻辑删除选中的站点信息
	 * 
	 * @return
	 * @author linj
	 * @date 2017-04-17 
	 */
	@RequestMapping(params = "doDeleteALLSelect")
	@ResponseBody
	public AjaxJson doDeleteALLSelect(BusStopInfoEntity busStopInfos, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		try{
			String ids = request.getParameter("ids");
			String[] entitys = ids.split(",");
		    List<BusStopInfoEntity> list = new ArrayList<BusStopInfoEntity>();
			for(int i=0;i<entitys.length;i++){
				busStopInfos = systemService.getEntity(BusStopInfoEntity.class, entitys[i]);
				busStopInfos.setDeleteFlag((short)1);
	            list.add(busStopInfos);			
			}
			message = "站点下架成功(逻辑删除)";
			//批量逻辑删除
			busStopInfoService.updateAllEntitie(list);
		}catch (Exception e) {
		}
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 去站点添加页面
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(BusStopInfoEntity busStopInfo, HttpServletRequest req) {
		//获取部门信息
		List<TSDepart> departList = systemService.getList(TSDepart.class);
		req.setAttribute("departList", departList);		
		
		
		if (StringUtil.isNotEmpty(busStopInfo.getId())) {
			
			busStopInfo = busStopInfoService.getEntity(BusStopInfoEntity.class, busStopInfo.getId());
			req.setAttribute("busStopInfo", busStopInfo);
			
			if("3".equals(busStopInfo.getStationType())){
				List<Map<String,Object>> list = busStopInfoService.findForJdbc(
						" select area_x,area_y,xy_seq from area_station_middle asm join busstopinfo b on b.id = asm.station_id "
						+ " where station_id = ? and b.deleteFlag = 0 order by station_id,xy_seq asc ", busStopInfo.getId());
				
				StringBuffer sbfX = new StringBuffer();
				StringBuffer sbfY = new StringBuffer();
				
				//查出来的结果是无序的，拍个序，不然会有问题
				Collections.sort(list, new SortBySeq());
				
				for(Map<String,Object> map : list){
					sbfX.append(map.get("area_x"));
					sbfX.append("&");
					sbfY.append(map.get("area_y"));
					sbfY.append("&");
				}
				
				String json = "";
				if(sbfX.length() > 0){
					json = sbfX.deleteCharAt(sbfX.length()-1).toString() + "," + sbfY.deleteCharAt(sbfY.length()-1).toString();
				}
				
				req.setAttribute("areaStations", json);
			}
			
		}
		
		List<OpenCityEntity> cities = systemService.findByProperty(OpenCityEntity.class, "status", "0");
		req.setAttribute("cities", cities);
		return new ModelAndView("yhy/busStop/busStopInfoAdd");
	}

	/**
	 * 获取其他当前区域站点
	 */
	@RequestMapping(params="getOtherAreaStation")
	@ResponseBody
	public AjaxJson getOtherAreaStation(BusStopInfoEntity busStopInfo,HttpServletRequest request,HttpServletResponse respone){
		String message = null;
		AjaxJson j = new AjaxJson();
		
		String bId = request.getParameter("bId");
		String cityId = request.getParameter("cityId");
		
		if(!StringUtil.isNotEmpty(bId)){
			bId = "";
		}
		try{
			//查出其他区域站点，显示在地图上，不能让绘制的区域和这些已经存在的区域重叠
			List<Map<String,Object>> aList = busStopInfoService.findForJdbc(
					"select b.name,area_x,area_y,xy_seq from area_station_middle asm join busstopinfo b on b.id = asm.station_id"
							+ " where station_id != ? and b.deleteflag = 0 and b.cityId = ? order by station_id,xy_seq", bId, cityId);
			
			List<AreaStationDTO> asdList = new ArrayList<>();
			
			Map<String,String> tempMap = new HashMap<>();
			
			//同一个对象
			String sName = "";
			String tempName = "";
			
			//这个算法头都大了，后面肯定会忘记的....     看有没有时间来波优化了
			AreaStationDTO as = null;
			String[][] path = null;
			
			//思维是越发的精进了（优化后）
			int sum = 0;
			
			//最后一个对象少个点，回去做
			for(int i=0;i<aList.size();i++){
				Map<String,Object> map = aList.get(i);
				
				tempName = sName;
				sName = map.get("name") + "";
				
				if(i == aList.size()-1){
					tempMap.put(map.get("xy_seq") + "", map.get("area_x") + "," + map.get("area_y"));
					sum++;
				}
				
				if((!sName.equals(tempName) && i != 0) || i == aList.size()-1){
					path = new String[sum][];
					for(int s=0;s<sum;s++){ 
						path[s] = tempMap.get(s + "").split(",");
					}
					as = new AreaStationDTO();
					as.setName(tempName);
					as.setPath(path);
					asdList.add(as);
					tempMap.clear();
					sum = 0;
				}
				sum++;
				tempMap.put(map.get("xy_seq") + "", map.get("area_x") + "," + map.get("area_y"));
			}
			
			j.setSuccess(true);
			j.setObj(JSONArray.fromObject(asdList).toString().replace("\"", "'"));
		}catch (Exception e) {
			message = "获取其他站点失败！";
			j.setSuccess(false);
			logger.error(ExceptionUtil.getExceptionMessage(e));
		}
		j.setMsg(message);
		return j;		
	}
	
	/**
	 * 线路保存
	 */
	@RequestMapping(params="save")
	@ResponseBody
	public AjaxJson save(BusStopInfoEntity busStopInfo,HttpServletRequest request,HttpServletResponse respone){
		String message = null;
		AjaxJson j = new AjaxJson();
		
		//绘制区域中的各个站点
		String areaStations = request.getParameter("areaStations");
		
		busStopInfo.setDeleteFlag((short)0);
		busStopInfo.setCreateUserId(ResourceUtil.getSessionUserName().getId());
		String cityId = request.getParameter("city");
		if(StringUtil.isNotEmpty(cityId)){
			CitiesEntity listCity = systemService.findUniqueByProperty(CitiesEntity.class, "cityId", cityId);
			if (StringUtil.isNotEmpty(listCity)) {
				busStopInfo.setCityId(cityId);
				busStopInfo.setCityName(listCity.getCity());
			}
		}
		try{
			if(StringUtil.isNotEmpty(busStopInfo.getId())){
				BusStopInfoEntity l = busStopInfoService.getEntity(BusStopInfoEntity.class, busStopInfo.getId());
				
				String oldType = l.getStationType();
				
				MyBeanUtils.copyBeanNotNull2Bean(busStopInfo, l);
				busStopInfoService.saveOrUpdate(l);
				
				//添加新的区域站点前，先删除原有的站点集合
				if("3".equals(oldType)){
					List<AreaStationMiddleEntity> delList = 
							busStopInfoService.findByProperty(AreaStationMiddleEntity.class, "stationId", busStopInfo.getId());
					busStopInfoService.deleteAllEntitie(delList);
				}
				
				message ="站点修改成功！";
				//-----数据修改日志[类SVN]------------
				Gson gson = new Gson();
				systemService.addDataLog("busStopInfo", l.getId(), gson.toJson(l));
				//-----数据修改日志[类SVN]------------
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
				
			}else{
				if(busStopInfo.getStatus()==null){
					busStopInfo.setStatus("0");
				}
				busStopInfoService.save(busStopInfo);
				
				//lineInfoService.findByQueryString("sdsdd");	
				message ="站点添加成功！";
				//-----数据修改日志[类SVN]------------
				Gson gson = new Gson();
				systemService.addDataLog("busStopInfo", busStopInfo.getId(), gson.toJson(busStopInfo));
				//-----数据修改日志[类SVN]------------
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			}
			
			if(StringUtil.isNotEmpty(areaStations) && "3".equals(busStopInfo.getStationType())){
				
				String[] xy = areaStations.split(",");
				//经度
				String[] x = xy[0].split("&");
				//纬度
				String[] y = xy[1].split("&");
				for (int i = 0; i < x.length; i++) {
					systemService.executeSql(" insert into area_station_middle VALUES (?, ?, ?, ?, ?) ", 
							UUIDGenerator.generate(), busStopInfo.getId(), x[i], y[i], i + "");
				}
			}
			
		}catch (Exception e) {
			message = "站点添加失败！";
			j.setSuccess(false);
			logger.error(ExceptionUtil.getExceptionMessage(e));
			//e.printStackTrace();
		}
		j.setMsg(message);
		return j;		
	}
	
	/**
	 * 获取站点挂接站点序号
	 */
	private synchronized int getMaxSiteOrder(String lineInfoId){
		int siteOreder=0;
		StringBuffer st = new StringBuffer();
		st.append("select b.siteOrder from  line_busstop b where b.siteOrder!='99'");
		if(StringUtil.isNotEmpty(lineInfoId)){
			st.append(" and b.lineId='");
			st.append(lineInfoId+"'");
		}
		st.append(" ORDER BY b.siteOrder DESC");
		List<Object> bList =systemService.findListbySql(st.toString());
		if(bList.size()>0){
			 siteOreder = (int) bList.get(0);
		}
		
		return siteOreder;
	}
	
	
	/**
	 * 获取站点挂接站点序号
	 */
	private synchronized int getMaxSiteOrders(String lineInfoId){
		int siteOreder=0;
		StringBuffer st = new StringBuffer();
		st.append("select b.siteOrder from  line_busstop_history b where b.siteOrder!='99'");
		if(StringUtil.isNotEmpty(lineInfoId)){
			st.append(" and b.lineId='");
			st.append(lineInfoId+"'");
		}
		st.append(" ORDER BY b.siteOrder DESC");
		List<Object> bList =systemService.findListbySql(st.toString());
		if(bList.size()>0){
			 siteOreder = (int) bList.get(0);
		}
		
		return siteOreder;
	}
	
	 /**
     * 挂接站点保存
     * @param req request
     * @return 处理信息结果
     */
    @RequestMapping(params = "addSave")
    @ResponseBody
    public AjaxJson doAddUserToOrg(HttpServletRequest request) {
	    	String message = null;
	        AjaxJson j = new AjaxJson();
	        try{
		        String lineInfoId = request.getParameter("lineInfoId");
		        String history = request.getParameter("history");
		        if("1".equals(history)){
		        	
		        	 LineinfoHistoryEntity line = systemService.getEntity(LineinfoHistoryEntity.class, lineInfoId);
				        
			        String ids = oConvertUtils.getString(request.getParameter("ids"));//站点ID
			        List<String> idsList = extractIdListByComma(ids);
			        List<LineBusstopHistoryEntity> list = new ArrayList<LineBusstopHistoryEntity>();
			        StringBuffer sql = new StringBuffer();
			        for(int i=0;i<idsList.size();i++){
			        	LineBusstopHistoryEntity lin_busStop = new LineBusstopHistoryEntity();
			        	lin_busStop.setLineId(lineInfoId);
			        	lin_busStop.setBusStopsId(idsList.get(i));
			        	lin_busStop.setVersion(line.getVersion());
			        	int site = getMaxSiteOrders(lineInfoId);
			        	int pum = site+1+i;
			        	lin_busStop.setSiteOrder(pum);
			        	
			        	StartOrEndEntity st = new StartOrEndEntity();
			        	if("2".equals(line.getType()) || "4".equals(line.getType())){
			        		
							if (StringUtil.isNotEmpty(line.getStartLocation())) {
								st.setStartid(line.getStartLocation());
							}
							if (StringUtil.isNotEmpty(ids)) {
								st.setEndid(idsList.get(i));
							}
							st.setLinetype(line.getType());
							
			        	}else if("3".equals(line.getType()) || "5".equals(line.getType())){
			        		if (StringUtil.isNotEmpty(ids)) {
								st.setStartid(idsList.get(i));
							}
							if (StringUtil.isNotEmpty(line.getEndLocation())) {
								st.setEndid(line.getEndLocation());
							}
							st.setLinetype(line.getType());
		        		}
			        	st.setStationStatus("0");
			        	st.setLineId(line.getId());
			        	startOrEndService.save(st);
			        	
			        	if(StringUtil.isNotEmpty(idsList.get(i))){
			        		if(i==(idsList.size()-1)){
			        			sql.append("'"+idsList.get(i)+"'");
			        		}else{
			        			sql.append("'"+idsList.get(i)+"',");
			        		}
			        	}
			        	list.add(lin_busStop);
			        }
			        
			        systemService.saveAllEntitie(list);
		        }else{
				LineInfoEntity line = systemService.getEntity(LineInfoEntity.class, lineInfoId);

				String ids = oConvertUtils.getString(request.getParameter("ids"));// 站点ID
				List<String> idsList = extractIdListByComma(ids);
				List<Line_busStopEntity> list = new ArrayList<Line_busStopEntity>();
				StringBuffer sql = new StringBuffer();
					for (int i = 0; i < idsList.size(); i++) {
						Line_busStopEntity lin_busStop = new Line_busStopEntity();
						lin_busStop.setLineId(lineInfoId);
						lin_busStop.setBusStopsId(idsList.get(i));
						int site = getMaxSiteOrder(lineInfoId);
						int pum = site + 1 + i;
						lin_busStop.setSiteOrder(pum);
	
						StartOrEndEntity st = new StartOrEndEntity();
						if ("2".equals(line.getType()) || "4".equals(line.getType())) {
	
							if (StringUtil.isNotEmpty(line.getStartLocation())) {
								st.setStartid(line.getStartLocation());
							}
							if (StringUtil.isNotEmpty(ids)) {
								st.setEndid(idsList.get(i));
							}
	
							st.setLinetype(line.getType());
	
						} else if ("3".equals(line.getType()) || "5".equals(line.getType())) {
							if (StringUtil.isNotEmpty(ids)) {
								st.setStartid(idsList.get(i));
							}
							if (StringUtil.isNotEmpty(line.getEndLocation())) {
								st.setEndid(line.getEndLocation());
							}
							st.setLinetype(line.getType());
						}
						st.setStationStatus("0");
						st.setLineId(lineInfoId);
	
						startOrEndService.save(st);
	
						if (StringUtil.isNotEmpty(idsList.get(i))) {
							if (i == (idsList.size() - 1)) {
								sql.append("'" + idsList.get(i) + "'");
							} else {
								sql.append("'" + idsList.get(i) + "',");
							}
						}
						list.add(lin_busStop);
					}
	
					systemService.saveAllEntitie(list);
		        }
		        //公务车线路为1    接送机线路为2
		        //String lineType = request.getParameter("lineType");
		       
		        message="站点挂接成功";
		        
	        }catch (Exception e) {
	        	message = "站点挂接失败";
			}
	        j.setMsg(message);
        return j;
    }
    
    /**
	 * 检查用户是否被锁定
	 */
	@RequestMapping(params = "checkStation")
	@ResponseBody
	public JSONObject checkStation(HttpServletRequest request){
		JSONObject jsonObj = new JSONObject();
		
		//是否能对订单进行车辆的安排
		boolean b = false;
		
		String sId = request.getParameter("sId");
		
		long count = systemService.getCountForJdbc("select count(*) from line_busstop where busStopsId = '" + sId + "'");
		
		if(count == 0){
			b = true;
		}
		
		jsonObj.put("success", b);
		
		return jsonObj;
	}
	 
}

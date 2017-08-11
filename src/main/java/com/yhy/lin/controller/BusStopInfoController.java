package com.yhy.lin.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.tag.core.easyui.TagUtil;
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
import com.yhy.lin.entity.LineInfoEntity;
import com.yhy.lin.entity.Line_busStopEntity;
import com.yhy.lin.entity.OpenCityEntity;
import com.yhy.lin.entity.StartOrEndEntity;
import com.yhy.lin.service.BusStopInfoServiceI;
import com.yhy.lin.service.StartOrEndServiceI;

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
		/*CriteriaQuery cq = new CriteriaQuery(BusStopInfoEntity.class, dataGrid);
	    //查询出来所有逻辑为删除的 
		//cq.eq("deleteFlag", Globals.Delete_Normal);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, busStopInfo,request.getParameterMap());
		this.busStopInfoService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);*/
		String cityID = request.getParameter("cityID");
		JSONObject jObject = busStopInfoService.getDatagrid(busStopInfo, dataGrid,cityID);
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
			/*busStopInfo = systemService.getEntity(BusStopInfoEntity.class, busStopInfo.getId());
			if("1".equals(del)){
				busStopInfo.setDeleteFlag((short)1);
				message = "站点下架成功(逻辑删除)";
				busStopInfoService.saveOrUpdate(busStopInfo);
			}else{
				
			}*/
			message = "删除成功";
			busStopInfoService.delete(busStopInfo);
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
		}
//		List<OpenCityEntity> cities = systemService.findByProperty(OpenCityEntity.class, "status", "0");
		List<OpenCityEntity> cities = systemService.getList(OpenCityEntity.class);
		req.setAttribute("cities", cities);
		return new ModelAndView("yhy/busStop/busStopInfoAdd");
	}
	
	/**
	 * 线路保存
	 */
	@RequestMapping(params="save")
	@ResponseBody
	public AjaxJson save(BusStopInfoEntity busStopInfo,HttpServletRequest request,HttpServletResponse respone){
		String message = null;
		AjaxJson j = new AjaxJson();
		busStopInfo.setDeleteFlag((short)0);
		busStopInfo.setCreatePeople(ResourceUtil.getSessionUserName().getUserName());
		String cityId = request.getParameter("city");
		if(StringUtil.isNotEmpty(cityId)){
			CitiesEntity listCity = systemService.findUniqueByProperty(CitiesEntity.class, "cityId", cityId);
			if (StringUtil.isNotEmpty(listCity)) {
				busStopInfo.setCityId(cityId);
				busStopInfo.setCityName(listCity.getCity());
			}
		}
		if(StringUtil.isNotEmpty(busStopInfo.getId())){
			try{
				BusStopInfoEntity l = busStopInfoService.getEntity(BusStopInfoEntity.class, busStopInfo.getId());
				MyBeanUtils.copyBeanNotNull2Bean(busStopInfo, l);
				busStopInfoService.saveOrUpdate(l);
				message ="站点修改成功！";
				//-----数据修改日志[类SVN]------------
				Gson gson = new Gson();
				systemService.addDataLog("busStopInfo", l.getId(), gson.toJson(l));
				//-----数据修改日志[类SVN]------------
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			}catch (Exception e) {
				message = "站点添加失败！";
				j.setSuccess(false);
				logger.error(ExceptionUtil.getExceptionMessage(e));
				//e.printStackTrace();
			}
		}else{
			try{
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
			}catch (Exception e) {
				message = "站点添加失败！";
				j.setSuccess(false);
				logger.error(ExceptionUtil.getExceptionMessage(e));
				//e.printStackTrace();
			}
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
		        //公务车线路为1    接送机线路为2
		       // String lineType = request.getParameter("lineType");
		        LineInfoEntity line = this.systemService.getEntity(LineInfoEntity.class, lineInfoId);
		        
		        String ids = oConvertUtils.getString(request.getParameter("ids"));//站点ID
		        List<String> idsList = extractIdListByComma(ids);
		        List<Line_busStopEntity> list = new ArrayList<Line_busStopEntity>();
		        StringBuffer sql = new StringBuffer();
		        for(int i=0;i<idsList.size();i++){
		        	Line_busStopEntity lin_busStop = new Line_busStopEntity();
		        	lin_busStop.setLineId(lineInfoId);
		        	lin_busStop.setBusStopsId(idsList.get(i));
		        	int site = getMaxSiteOrder(lineInfoId);
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
		        //原设定是修改站点的挂接状态，占时不适用   (为啥不适用呢...)
		        /*System.out.println("update busstopinfo set status='" + lineType + "' where id in ("+sql.toString()+")");
		        StringBuffer updateSql = new StringBuffer();
		        if (StringUtil.isNotEmpty(line.getType())) {
		        	 updateSql.append("update busstopinfo set status=CONCAT(status,'" + line.getType() + "') where id in ("+sql.toString()+") ");
		        	 updateSql.append(" and station_type = '0' ");
		        	 systemService.updateBySqlString(updateSql.toString());
				        
				        systemService.saveAllEntitie(list);
				        message="站点挂接成功";
		        }*/
		        systemService.saveAllEntitie(list);
		        message="站点挂接成功";
		        //只有站点类型是普通站点的时候，才修改它的状态，让其只能在一条线路中使用
		        //如果是机场站点或者火车站点，则可以重复使用
		        
		        
	        }catch (Exception e) {
	        	message = "站点挂接失败";
			}
	        j.setMsg(message);
        return j;
    }
    
}

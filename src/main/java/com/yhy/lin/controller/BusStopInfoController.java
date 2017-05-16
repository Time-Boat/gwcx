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
import com.yhy.lin.entity.Line_busStopEntity;
import com.yhy.lin.service.BusStopInfoServiceI;

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
	
	@RequestMapping(params="busStopInfoList")
	public ModelAndView stopList(HttpServletRequest request){
		return new ModelAndView("yhy/busStop/busStopInfoList");
	}
	@RequestMapping(params="busStopInfoList2")
	public ModelAndView stopList2(HttpServletRequest request){
		return new ModelAndView("yhy/busStop/busStopInfoList2");
	}
	
	@RequestMapping(params="datagrid")
	public void datagrid(BusStopInfoEntity busStopInfo,HttpServletRequest request,HttpServletResponse response,DataGrid dataGrid){
		CriteriaQuery cq = new CriteriaQuery(BusStopInfoEntity.class, dataGrid);
	    //查询出来所有逻辑为删除的 
		//cq.eq("deleteFlag", Globals.Delete_Normal);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, busStopInfo,request.getParameterMap());
		this.busStopInfoService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
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
		        String lineType = request.getParameter("lineType");
		        
		        String ids = oConvertUtils.getString(request.getParameter("ids"));//站点ID
		        List<String> idsList = extractIdListByComma(ids);
		        List<Line_busStopEntity> list = new ArrayList<Line_busStopEntity>();
		        StringBuffer sql = new StringBuffer();
		        for(int i=0;i<idsList.size();i++){
		        	Line_busStopEntity lin_busStop = new Line_busStopEntity();
		        	lin_busStop.setLineId(lineInfoId);
		        	lin_busStop.setBusStopsId(idsList.get(i));
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
		        System.out.println("update busstopinfo set status='" + lineType + "' where id in ("+sql.toString()+")");
		        systemService.updateBySqlString("update busstopinfo set status='" + lineType + "' where id in ("+sql.toString()+")");
		        
		        systemService.saveAllEntitie(list);
		        message="站点挂接成功";
	        }catch (Exception e) {
	        	message = "站点挂接失败";
			}
	        j.setMsg(message);
        return j;
    }
    
}

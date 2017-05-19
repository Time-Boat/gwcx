package com.yhy.lin.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tools.ant.types.CommandlineJava.SysProperties;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yhy.lin.entity.CitiesEntity;
import com.yhy.lin.entity.LineInfoEntity;
import com.yhy.lin.entity.OpenCityEntity;
import com.yhy.lin.service.LineInfoServiceI;

import net.sf.json.JSONObject;

/**
 * 接送机线路管理模块
 * @author HSAEE
 *
 */
@Controller
@RequestMapping("lineInfoSpecializedController")
public class LineInfoSpecializedController extends BaseController{
	@Autowired
	private SystemService systemService;
	
	@Autowired
	private LineInfoServiceI lineInfoService;
	
	/**
	 *	 接送机线路管理跳转页面
	 */
	@RequestMapping(params="lineList")
	public ModelAndView lineList(HttpServletRequest request){
		return new ModelAndView("yhy/linesSpecial/lineList");
	}
	
	@RequestMapping(params="datagrid")
	public void datagrid(LineInfoEntity lineInfos,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid){
		String linetype = request.getParameter("linetype");//线路类型
		String beginTime = request.getParameter("createTime_begin");//线路创建开始查询时间
		String endTime = request.getParameter("createTime_end");//线路创建结束查询时间
		String lstartTime_begin = request.getParameter("lstartTime_begin");
		String lstartTime_end = request.getParameter("lstartTime_end");
		String lendTime_begin = request.getParameter("lendTime_begin");
		String lendTime_end = request.getParameter("lendTime_end");
		//因为调用的方法一样，所以在外层来处理...   忘记是啥意思了。。。
		linetype = " >='"+linetype+"' ";
		JSONObject jObject  = lineInfoService.getDatagrid3(lineInfos,beginTime,endTime,dataGrid,lstartTime_begin,lstartTime_end,lendTime_begin,lendTime_end,linetype);
        responseDatagrid(response, jObject);
	}
	
	/**
	 * 去线路添加页面
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(LineInfoEntity lineInfo, HttpServletRequest req) {
		//获取部门信息
		List<TSDepart> departList = systemService.getList(TSDepart.class);
		req.setAttribute("departList", departList);		
		if (StringUtil.isNotEmpty(lineInfo.getId())) {
			lineInfo = lineInfoService.getEntity(LineInfoEntity.class, lineInfo.getId());
			req.setAttribute("lineInfo", lineInfo);
		}
		List<OpenCityEntity> cities = systemService.findByProperty(OpenCityEntity.class, "status", "0");
		req.setAttribute("cities", cities);
		List<TSDepart> list = systemService.findByProperty(TSDepart.class, "orgType", "4");
		if(list.size()>0){
			req.setAttribute("list", list);
		}
		return new ModelAndView("yhy/linesSpecial/lineAdd");
	}
	
}

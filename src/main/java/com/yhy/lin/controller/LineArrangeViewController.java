package com.yhy.lin.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;

import javax.validation.Validator;

import java.math.BigDecimal;
import java.sql.Date;

import com.yhy.lin.entity.LineArrangeEntity;
import com.yhy.lin.entity.LineArrangeViewEntity;
import com.yhy.lin.service.LineArrangeViewServiceI;

import net.sf.json.JSONObject;

/**   
 * @Title: Controller
 * @Description: 线路排班模块
 * @author zhangdaihao
 * @date 2017-04-26 10:58:02
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/lineArrangeViewController")
public class LineArrangeViewController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(LineArrangeViewController.class);

	@Autowired
	private LineArrangeViewServiceI lineArrangeViewService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * 线路排班模块列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("/yhy/putCar/lineArrangeViewList");
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
	public void datagrid(LineArrangeViewEntity lineArrangeView,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
//		CriteriaQuery cq = new CriteriaQuery(LineArrangeViewEntity.class, dataGrid);
//		//查询条件组装器
//		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, lineArrangeView, request.getParameterMap());
//		this.lineArrangeViewService.getDataGridReturn(cq, true);
//		TagUtil.datagrid(response, dataGrid);
		
		JSONObject jObject = lineArrangeViewService.getDatagrid(dataGrid);

		responseDatagrid(response, jObject);
	}

	/**
	 * 删除线路排班模块
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(LineArrangeViewEntity lineArrangeView, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		lineArrangeView = systemService.getEntity(LineArrangeViewEntity.class, lineArrangeView.getId());
		message = "线路排班模块删除成功";
		lineArrangeViewService.delete(lineArrangeView);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加线路排班模块
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(LineArrangeEntity lineArrange, HttpServletRequest request) {
//		String periodStart = request.getParameter("periodStart");
//		String periodEnd = request.getParameter("periodEnd");
		
		String dd = request.getParameter("J_DepDate");
		String ed = request.getParameter("J_EndDate");
		String tp = request.getParameter("ticketPrice");
		String aId = request.getParameter("aId");
		
		String message = null;
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(aId)) {
			message = "线路排班模块更新成功";
			LineArrangeEntity t = lineArrangeViewService.get(LineArrangeEntity.class, aId);
			try {
				MyBeanUtils.copyBeanNotNull2Bean(lineArrange, t);
				
				t.setPeriodStart(Date.valueOf(dd));
				t.setPeriodEnd(Date.valueOf(ed));
				t.setTicketPrice(new BigDecimal(tp));
				
				lineArrangeViewService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = "线路排班模块更新失败";
			}
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 线路排班模块列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(LineArrangeViewEntity lineArrangeView, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(lineArrangeView.getId())) {
			lineArrangeView = lineArrangeViewService.getEntity(LineArrangeViewEntity.class, lineArrangeView.getId());
			req.setAttribute("lineArrangeViewPage", lineArrangeView);
		}
		return new ModelAndView("/yhy/putCar/lineArrangeView");
	}
	
	/**
	 * 线路排班日历
	 * 
	 * @return
	 */
	@RequestMapping(params = "putRecord")
	public ModelAndView putRecord(LineArrangeViewEntity lineArrangeView,HttpServletRequest req) {
		String id = req.getParameter("id");
		
		String lineId = req.getParameter("lineId");
		String tickets = lineArrangeViewService.getSaleTicketCount(lineId);
		
		if (StringUtil.isNotEmpty(id)) {
			lineArrangeView = lineArrangeViewService.getEntity(LineArrangeViewEntity.class, lineArrangeView.getId());
			req.setAttribute("lineArrangeViewPage", lineArrangeView);
			req.setAttribute("tickets", tickets);
		}
		return new ModelAndView("/yhy/putCar/putRecord");
	}
}

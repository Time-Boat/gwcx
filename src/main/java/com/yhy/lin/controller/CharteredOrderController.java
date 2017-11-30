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
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.service.SystemService;

import com.yhy.lin.entity.CharteredOrderEntity;
import com.yhy.lin.service.CharteredOrderServiceI;

import net.sf.json.JSONObject;

import javax.validation.Validator;

/**   
 * @Title: Controller
 * @Description: 包车订单
 * @author zhangdaihao
 * @date 2017-11-23 11:48:37
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/charteredOrderController")
public class CharteredOrderController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(CharteredOrderController.class);

	@Autowired
	private CharteredOrderServiceI charteredOrderService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;

	/**
	 * 包车订单列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "charteredOrderList")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("yhy/charteredOrder/charteredOrderList");
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
	public void datagrid(CharteredOrderEntity charteredOrder,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		String o_begin = request.getParameter("orderStartime_begin");
		String o_end = request.getParameter("orderStartime_end");
		String username = request.getParameter("username");
		JSONObject jObject = charteredOrderService.getDatagrid(charteredOrder,dataGrid,o_begin,o_end,username);
		responseDatagrid(response, jObject);
	}

	/**
	 * 删除包车订单
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(CharteredOrderEntity charteredOrder, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		charteredOrder = systemService.getEntity(CharteredOrderEntity.class, charteredOrder.getId());
		message = "包车订单删除成功";
		charteredOrderService.delete(charteredOrder);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}

	/**
	 * 包车订单列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(CharteredOrderEntity charteredOrder, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(charteredOrder.getId())) {
			charteredOrder = charteredOrderService.getEntity(CharteredOrderEntity.class, charteredOrder.getId());
			req.setAttribute("charteredOrderPage", charteredOrder);
		}
		return new ModelAndView("com/yhy/lin/charteredOrder");
	}
	
	
	/**
	 * 司机车辆安排跳转
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "appoint")
	public ModelAndView editCarAndDriver(HttpServletRequest request, HttpServletResponse response) {
		
		String id = request.getParameter("id");
		String slDate = request.getParameter("slDate");
		
		//接送机，接送火车，司机车辆安排三个模块都公用的这一个方法，高复用带来的高耦合，现在的解决办法就是通过参数判断
		request.setAttribute("slDate", slDate + "");
		
		return new ModelAndView("yhy/charteredOrder/charteredAppoint");
	}
	
	
	/**
	 * 保存接送订单的司机车辆安排
	 */
	@RequestMapping(params = "saveAppoint")
	@ResponseBody
	public AjaxJson saveCarAndDriver(HttpServletRequest request, HttpServletResponse response) {
		String message = null;
		boolean success = false;
		AjaxJson j = new AjaxJson();
		String ids = request.getParameter("id");// 勾选的订单id
		String startTime = request.getParameter("startDate");// 发车时间
		String licencePlateId = request.getParameter("licencePlateId");// 车牌id
		String driverId = request.getParameter("driverId");// 司机id
		String licencePlateName = request.getParameter("licencePlateName");// 车牌号
		
		List<String> orderIds = new ArrayList<String>();
		
		//boolean b = transferService.saveDriverAndDriver(orderIds, startTime, licencePlateId, driverId, licencePlateName, contents);
		
		/*if (b) {
			//同一个人下的两条订单被安排了同一个车上，那么会连续发送两条短信，这个会出发流程控制，一分钟之内不允许发送两条消息
			//可以将发送失败的短信存到数据库，使用quartz定时去循环发送
			//如果有rides缓存服务器，可以存到缓存中，到时候再定时发送
			List<String> stringlist = new ArrayList<String>();
			stringlist.add("name");
			stringlist.add("startStation");
			stringlist.add("terminusStation");
			stringlist.add("time");
			String[] keys = {};
			String util="";
			//String[] keys = new String[] { "name", "startStation", "terminusStation", "time","driverName","driverPhone" };
			for (int i = 0; i < contents.size(); i++) {
				String[] p = contents.get(i);
				
				if(p.length>5){
					stringlist.add("driverName");
					stringlist.add("driverPhone");
					util=SendMessageUtil.TEMPLATE_ARRANGE_CARS;
				}else{
					util=SendMessageUtil.TEMPLATE_ARRANGE_CAR;
				}
				int size = stringlist.size();  
				keys = (String[])stringlist.toArray(new String[size]);
				
				SendMessageUtil.sendMessage(p[p.length-1], keys, contents.get(i), util, SendMessageUtil.TEMPLATE_ARRANGE_CAR_SIGN_NAME);
			}
			success = true;
			message = "订单处理成功";
		} else {
			message = "服务器异常";
		}*/

		j.setSuccess(success);
		j.setMsg(message);
		return j;
	}
}

package com.yhy.lin.controller;

import java.math.BigDecimal;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yhy.lin.app.entity.CarCustomerEntity;
import com.yhy.lin.entity.TransferorderEntity;
import com.yhy.lin.service.TransferStatisticsServiceI;

import net.sf.json.JSONObject;

/**
 * 接送机汇总统计
 * 
 * @author
 *
 */
@Controller
@RequestMapping("/transferStatisticsController")
public class TransferStatisticsController extends BaseController {

	@Autowired
	private SystemService systemService;

	@Autowired
	private TransferStatisticsServiceI transferStatisticsServiceI;

	//用户汇总统计页面
	@RequestMapping(params = "userStatisticsList")
	public ModelAndView userStatisticsList(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("yhy/transferStatistics/userStatisticsList");
	}

	/*
	 * 订单收入统计页面
	 */
	@RequestMapping(params = "OrderStatisticsList")
	public ModelAndView OrderStatisticsList(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("yhy/transferStatistics/OrderStatisticsList");
	}

	/**
	 * 退款统计页面
	 */
	@RequestMapping(params = "refundStatisticsList")
	public ModelAndView refundStatisticsList(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("yhy/transferStatistics/refundStatisticsList");
	}

	/**
	 * 渠道商用户统计
	 */
	@RequestMapping(params = "channelUserStatisticsList")
	public ModelAndView channelUserStatisticsList(HttpServletRequest request, HttpServletResponse response) {
		
		request.setAttribute("accountList",getAccount());
		return new ModelAndView("yhy/transferStatistics/channelUserStatisticsList");
	}
	
	public String getAccount(){
		String sql ="select d.id,d.account from dealer_info d";
		List<Object> list = this.systemService.findListbySql(sql);
		StringBuffer json = new StringBuffer("{'data':[");
		if(list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				Object[] ob = (Object[]) list.get(i);
				String id = ob[0]+"";
				String account = ob[1]+"";
				
					json.append("{");
					json.append("'id':'" +id + "',");
					json.append("'account':'"+ account + "'");
					json.append("},");
			}
		}
		json.delete(json.length()-1, json.length());
		json.append("]}");
		return json.toString();
	}
	
	/**
	 * 渠道商订单统计
	 */
	@RequestMapping(params = "channelOrderStatisticsList")
	public ModelAndView channelOrderStatisticsList(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("accountList",getAccount());
		return new ModelAndView("yhy/transferStatistics/channelOrderStatisticsList");
	}
	
	@RequestMapping(params = "userdatagrid")
	public void userdatagrid(CarCustomerEntity carcustomer, HttpServletRequest request,
			HttpServletResponse response, DataGrid dataGrid) {
		String fc_begin = request.getParameter("createTime_begin");
		String fc_end = request.getParameter("createTime_end");

		JSONObject jObject = transferStatisticsServiceI.getUserDatagrid(carcustomer, dataGrid, fc_begin, fc_end);

		responseDatagrid(response, jObject);
	}

	@RequestMapping(params = "orderdatagrid")
	public void orderdatagrid(TransferorderEntity transferorder, HttpServletRequest request,
			HttpServletResponse response, DataGrid dataGrid) {

		String orderId = request.getParameter("orderId");
		String fc_begin = request.getParameter("applicationTime_begin");
		String fc_end = request.getParameter("applicationTime_end");
		String orderType = request.getParameter("ordertype");
		String orderStatus = request.getParameter("orderStatus");
		String lineName = request.getParameter("lineName");
		String driverName = request.getParameter("driverName");

		JSONObject jObject = transferStatisticsServiceI.getOrderDatagrid(transferorder, dataGrid,orderId,orderStatus, lineName, orderType,
				 driverName, fc_begin, fc_end);
		responseDatagrid(response, jObject);
	}

	@RequestMapping(params = "refunddatagrid")
	public void refunddatagrid(TransferorderEntity transferorder, HttpServletRequest request,
			HttpServletResponse response, DataGrid dataGrid) {
		String orderId = request.getParameter("orderId");
		String orderStartingstation = request.getParameter("refundCompletedTime_begin");
		String orderTerminusstation = request.getParameter("refundCompletedTime_end");
		String orderType = request.getParameter("ordertype");
		String lineName = request.getParameter("lineName");

		JSONObject jObject = transferStatisticsServiceI.getrefundDatagrid(transferorder, dataGrid, orderId,orderStartingstation,
				orderTerminusstation, orderType, lineName);
		responseDatagrid(response, jObject);

	}


	@RequestMapping(params = "channelUserdatagrid")
	public void channelUserdatagrid(CarCustomerEntity carcustomer, HttpServletRequest request,
			HttpServletResponse response, DataGrid dataGrid) {
		String id = request.getParameter("id");
		String account = request.getParameter("accountId");
		String fc_begin = request.getParameter("createTime_begin");
		String fc_end = request.getParameter("createTime_end");

		JSONObject jObject = transferStatisticsServiceI.getChannelUserDatagrid(carcustomer, dataGrid,account,fc_begin, fc_end);

		responseDatagrid(response, jObject);
	}
	
	@RequestMapping(params = "channelOrderdatagrid")
	public void channelOrderdatagrid(TransferorderEntity transferorder, HttpServletRequest request,
			HttpServletResponse response, DataGrid dataGrid) {

		String fc_begin = request.getParameter("orderCompletedTime_begin");
		String fc_end = request.getParameter("orderCompletedTime_end");
		String orderType = request.getParameter("ordertype");
		String lineName = request.getParameter("lineName");
		String account = request.getParameter("account");

		JSONObject jObject = transferStatisticsServiceI.getChannelOrderDatagrid(transferorder, dataGrid, lineName, orderType,
				account, fc_begin, fc_end);
		responseDatagrid(response, jObject);
	}
	
	/*
	 * 用戶汇总统计
	 */
	@RequestMapping(params = "getUserTotal")
	@ResponseBody
	public JSONObject getUserTotal(HttpServletRequest request, HttpServletResponse response) {
		JSONObject jsonObj = new JSONObject();

		return jsonObj;
	}

	/*
	 * 订单收入统计
	 */
	@RequestMapping(params = "getOrderTotal")
	@ResponseBody
	public JSONObject getOrderTotal(HttpServletRequest request, HttpServletResponse response) {
		JSONObject jsonObj = new JSONObject();
		String orderId = request.getParameter("orderId");
		String fc_begin = request.getParameter("applicationTime_begin");
		String fc_end = request.getParameter("applicationTime_end");
		String orderType = request.getParameter("ordertype");
		String orderStatus = request.getParameter("orderStatus");
		String lineName = request.getParameter("lineName");
		String driverName = request.getParameter("driverName");

		StringBuffer orsql = new StringBuffer();
		orsql.append(
				"select SUM(a.order_numbers) as sum_order,SUM(a.order_totalPrice) as sum_price from transferorder a LEFT"
						+ " JOIN order_linecardiver b on a.id = b.id left join car_info c on b.licencePlateId =c.id left join "
						+ "driversinfo d on b.driverId =d.id left join lineinfo l on l.id = a.line_id LEFT JOIN car_customer w on w.id=a.user_id ");
		if (!transferStatisticsServiceI.getWhere2(orderId,orderStatus,lineName, orderType, driverName, fc_begin, fc_end)
				.isEmpty()) {
			orsql.append(transferStatisticsServiceI.getWhere2(orderId,orderStatus,lineName, orderType, driverName, fc_begin,
					fc_end));
		}
		List<Object> mlist = systemService.findListbySql(orsql.toString());

		int sumorder;
		BigDecimal sumPrice=new BigDecimal("0");
		if (mlist.size() > 0) {
			Object[] ob = (Object[]) mlist.get(0);
			if (ob[0] != null) {
				String obnum = ob[0] + "";
				sumorder = Integer.parseInt(obnum.substring(0, obnum.indexOf(".")));
			} else {
				sumorder = 0;
			}

			BigDecimal bsum = (BigDecimal) ob[1];
			sumPrice=sumPrice.add(bsum);
		} else {
			sumorder = 0;
		}

		jsonObj.put("sumorder", sumorder);
		jsonObj.put("sumPrice", sumPrice);
		return jsonObj;
	}

	/*
	 * 退票统计
	 */
	@RequestMapping(params = "getRefundTotal")
	@ResponseBody
	public JSONObject getRefundTotal(HttpServletRequest request, HttpServletResponse response) {
		JSONObject jsonObj = new JSONObject();
		String orderId = request.getParameter("orderId");
		String orderStartingstation = request.getParameter("refundCompletedTime_begin");
		String orderTerminusstation = request.getParameter("refundCompletedTime_end");
		String orderType = request.getParameter("ordertype");
		String lineName = request.getParameter("lineName");

		StringBuffer resql = new StringBuffer();
		resql.append(
				"select  SUM(a.order_numbers),SUM(a.order_totalPrice ) from transferorder a LEFT JOIN order_linecardiver b "
						+ "on a.id=b.id left join car_info c on b.licencePlateId =c.id left join driversinfo d on b.driverId =d.id left "
						+ "join lineinfo l on l.id = a.line_id LEFT JOIN car_customer w on w.id=a.user_id");
		if (!transferStatisticsServiceI.getWhere1(orderId,orderStartingstation, orderTerminusstation, lineName, orderType)
				.isEmpty()) {
			resql.append(transferStatisticsServiceI.getWhere1(orderId,orderStartingstation, orderTerminusstation, lineName,
					orderType));
		}
		List<Object> mlist = systemService.findListbySql(resql.toString());
		int sumorder;
		BigDecimal sumPrice =new BigDecimal("0");
		if (mlist.size() > 0) {
			Object[] ob = (Object[]) mlist.get(0);
			if (ob[0] != null) {
				String obnum = ob[0] + "";
				sumorder = Integer.parseInt(obnum.substring(0, obnum.indexOf(".")));
			} else {
				sumorder = 0;
			}

			BigDecimal bsum = (BigDecimal) ob[1];
			sumPrice=sumPrice.add(bsum);
			
		} else {
			sumorder = 0;
		}

		jsonObj.put("sumorder", sumorder);
		jsonObj.put("sumPrice", sumPrice);

		return jsonObj;
	}

	/*
	 * 渠道商订单统计
	 */
	@RequestMapping(params = "getChannelTotal")
	@ResponseBody
	public JSONObject getChannelTotal(HttpServletRequest request, HttpServletResponse response) {
		JSONObject jsonObj = new JSONObject();
		
		String fc_begin = request.getParameter("orderCompletedTime_begin");
		String fc_end = request.getParameter("orderCompletedTime_end");
		String orderType = request.getParameter("ordertype");
		String lineName = request.getParameter("lineName");
		String account = request.getParameter("account");

		StringBuffer orsql = new StringBuffer();
		orsql.append(
				"select SUM(t.order_numbers),SUM(t.order_totalPrice ) from transferorder t LEFT JOIN lineinfo l on t.line_id = l.id "
				+ "LEFT JOIN car_customer w on w.id=t.user_id,dealer_customer d,dealer_info f where w.open_id = d.open_id and f.id="
				+ "d.dealer_id and t.order_status='0'");
		if (!transferStatisticsServiceI.getWhere4(lineName, orderType, account, fc_begin, fc_end)
				.isEmpty()) {
			orsql.append(transferStatisticsServiceI.getWhere4(lineName, orderType, account, fc_begin,
					fc_end));
		}
		List<Object> mlist = systemService.findListbySql(orsql.toString());

		int sumorder;
		BigDecimal sumPrice=new BigDecimal("0");
		if (mlist.size() > 0) {
			Object[] ob = (Object[]) mlist.get(0);
			if (ob[0] != null) {
				String obnum = ob[0] + "";
				sumorder = Integer.parseInt(obnum.substring(0, obnum.indexOf(".")));
			} else {
				sumorder = 0;
			}

			BigDecimal bsum = (BigDecimal) ob[1];
			if(bsum!=null){
				sumPrice=sumPrice.add(bsum);
			}else{
				sumPrice=sumPrice.add(new BigDecimal("0"));
			}
			
		} else {
			sumorder = 0;
		}

		jsonObj.put("sumorder", sumorder);
		jsonObj.put("sumPrice", sumPrice);
		return jsonObj;
	}

}

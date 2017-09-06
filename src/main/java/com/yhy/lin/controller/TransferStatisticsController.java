package com.yhy.lin.controller;

import java.math.BigDecimal;
import java.math.BigInteger;
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
				orderTerminusstation, lineName,orderType);
		responseDatagrid(response, jObject);

	}

	/*
	 * 用戶汇总统计
	 */
	@RequestMapping(params = "getUserTotal")
	@ResponseBody
	public JSONObject getUserTotal(HttpServletRequest request, HttpServletResponse response) {
		JSONObject jsonObj = new JSONObject();
		
		String fc_begin = request.getParameter("createTime_begin");
		String fc_end = request.getParameter("createTime_end");
		StringBuffer orsql = new StringBuffer();
		orsql.append("select COUNT(*) from car_customer s ");
		
		if (!transferStatisticsServiceI.getWhere(fc_begin, fc_end).isEmpty()) {
			orsql.append(transferStatisticsServiceI.getWhere(fc_begin,fc_end));
		}
		List<Object> mlist = systemService.findListbySql(orsql.toString());
		int sumUser = 0;
		if (mlist.size() > 0) {
			BigInteger ob = (BigInteger) mlist.get(0);
			sumUser = ob.intValue();
		} else {
			sumUser = 0;
		}

		jsonObj.put("sumorder", sumUser);
		
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
			if(bsum != null){
				sumPrice = sumPrice.add(bsum);
			}
			
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
				"select  SUM(a.order_numbers),SUM(a.refund_price ) from transferorder a LEFT JOIN order_linecardiver b "
						+ "on a.id=b.id left join car_info c on b.licencePlateId =c.id left join driversinfo d on b.driverId =d.id left "
						+ "join lineinfo l on l.id = a.line_id LEFT JOIN car_customer w on w.id=a.user_id");
		if (!transferStatisticsServiceI.getWhere1(orderId,orderStartingstation, orderTerminusstation, lineName, orderType)
				.isEmpty()) {
			resql.append(transferStatisticsServiceI.getWhere1(orderId,orderStartingstation, orderTerminusstation, lineName,
					orderType));
		}
		List<Object> mlist = systemService.findListbySql(resql.toString());
		int sumorder;
		BigDecimal sumPrice = new BigDecimal("0");
		if (mlist.size() > 0) {
			Object[] ob = (Object[]) mlist.get(0);
			if (ob[0] != null) {
				String obnum = ob[0] + "";
				sumorder = Integer.parseInt(obnum.substring(0, obnum.indexOf(".")));
				BigDecimal bsum = (BigDecimal) ob[1];
				sumPrice=sumPrice.add(bsum);
			} else {
				sumorder = 0;
			}
			
		} else {
			sumorder = 0;
		}

		jsonObj.put("sumorder", sumorder);
		jsonObj.put("sumPrice", sumPrice);

		return jsonObj;
	}

}

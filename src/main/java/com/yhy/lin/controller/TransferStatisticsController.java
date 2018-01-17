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
import com.yhy.lin.service.SharingInfoServiceI;
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
	
	@Autowired
	private SharingInfoServiceI sharingInfoServiceI;
	

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
		String userType = request.getParameter("userType");
		String cityid = request.getParameter("cityid");
		request.setAttribute("linelist", sharingInfoServiceI.getLine(userType,cityid));
		request.setAttribute("companylist",sharingInfoServiceI.getCompany());
		request.setAttribute("driverList",sharingInfoServiceI.getDriver());
		return new ModelAndView("yhy/transferStatistics/OrderStatisticsList");
	}
	
	/*
	 * 订单收入统计图表页面
	 */
	@RequestMapping(params = "OrderStatisticsChart")
	public ModelAndView OrderStatisticsChart(HttpServletRequest request, HttpServletResponse response) {
		
		request.setAttribute("accountList",sharingInfoServiceI.getDealerinfo());
		return new ModelAndView("yhy/transferStatistics/orderStatisticsChart");
	}
	
	/*
	 * 司机统计图表页面
	 */
	@RequestMapping(params = "driverOrderStatisticsChart")
	public ModelAndView DriverOrderStatisticsChart(HttpServletRequest request, HttpServletResponse response) {
		
		request.setAttribute("driverList",sharingInfoServiceI.getDriverInfo());
		return new ModelAndView("yhy/transferStatistics/driverOrderStatisticsChart");
	}
	/*
	 * 司机统计图表页面
	 */
	@RequestMapping(params = "lineOrderStatisticsChart")
	public ModelAndView LineOrderStatisticsChart(HttpServletRequest request, HttpServletResponse response) {
		String ordertype = request.getParameter("ordertype");
		String userType = request.getParameter("userType");
		request.setAttribute("linelist",sharingInfoServiceI.getLineinfo(ordertype, userType));
		return new ModelAndView("yhy/transferStatistics/lineOrderStatisticsChart");
	}

	/**
	 * 退款统计页面
	 */
	@RequestMapping(params = "refundStatisticsList")
	public ModelAndView refundStatisticsList(HttpServletRequest request, HttpServletResponse response) {
		String userType = request.getParameter("userType");
		String cityid = request.getParameter("cityid");
		request.setAttribute("linelist", sharingInfoServiceI.getLine(userType,cityid));
		request.setAttribute("companylist",sharingInfoServiceI.getCompany());
		
		return new ModelAndView("yhy/transferStatistics/refundStatisticsList");
	}

	@RequestMapping(params = "userdatagrid")
	public void userdatagrid(CarCustomerEntity carcustomer, HttpServletRequest request,
			HttpServletResponse response, DataGrid dataGrid) {
		String fc_begin = request.getParameter("createTime_begin");
		String fc_end = request.getParameter("createTime_end");
		String userType = request.getParameter("userType");
		String phone = request.getParameter("phone");
		JSONObject jObject = transferStatisticsServiceI.getUserDatagrid(carcustomer, dataGrid, fc_begin, fc_end,userType,phone);
		
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
		String lineName = request.getParameter("linesId");
		String driverId = request.getParameter("driverId");
		String departname = request.getParameter("departname");

		JSONObject jObject = transferStatisticsServiceI.getOrderDatagrid(transferorder, dataGrid,orderId,orderStatus, lineName, orderType,
				driverId, fc_begin, fc_end,departname);
		responseDatagrid(response, jObject);
	}

	@RequestMapping(params = "refunddatagrid")
	public void refunddatagrid(TransferorderEntity transferorder, HttpServletRequest request,
			HttpServletResponse response, DataGrid dataGrid) {
		String orderId = request.getParameter("orderId");
		String orderStartingstation = request.getParameter("refundCompletedTime_begin");
		String orderTerminusstation = request.getParameter("refundCompletedTime_end");
		String orderType = request.getParameter("ordertype");
		String lineName = request.getParameter("linesId");
		String departname = request.getParameter("departname");

		JSONObject jObject = transferStatisticsServiceI.getrefundDatagrid(transferorder, dataGrid, orderId,orderStartingstation,
				orderTerminusstation, lineName,orderType,departname);
		responseDatagrid(response, jObject);

	}

	/*
	 * 用戶汇总统计
	 */
	/*@RequestMapping(params = "getUserTotal")
	@ResponseBody
	public JSONObject getUserTotal(CarCustomerEntity carcustomer,HttpServletRequest request, HttpServletResponse response) {
		JSONObject jsonObj = new JSONObject();
		
		String phone =  request.getParameter("phone");
		String userType = request.getParameter("userType");
		String fc_begin = request.getParameter("createTime_begin");
		String fc_end = request.getParameter("createTime_end");
		StringBuffer orsql = new StringBuffer();
		orsql.append("select DISTINCT s.id from car_customer s LEFT JOIN dealer_customer d on s.open_id = d.open_id LEFT JOIN dealer_info f "
				+ "on f.id=d.dealer_id  LEFT JOIN transferorder a on a.user_id=s.id LEFT JOIN lineinfo l on a.line_id=l.id LEFT "
				+ "JOIN t_s_base_user u on l.createUserId =u.ID LEFT JOIN t_s_user_org o on u.ID=o.user_id LEFT JOIN t_s_depart t on o.org_id=t.ID ");
		
		if (!transferStatisticsServiceI.getWhere(carcustomer,fc_begin, fc_end,userType).isEmpty()) {
			orsql.append(transferStatisticsServiceI.getWhere(carcustomer,fc_begin,fc_end,userType));
		}
		List<Object> mlist = systemService.findListbySql(orsql.toString());
		int sumUser = 0;
		if (mlist.size() > 0) {
			 sumUser = mlist.size();
		} else {
			sumUser = 0;
		}

		jsonObj.put("sumorder", sumUser);
		
		return jsonObj;
	}*/

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
		String driverId = request.getParameter("driverId");
		String departname = request.getParameter("departname");

		StringBuffer orsql = new StringBuffer();
		orsql.append(
				"select SUM(a.order_numbers) as sum_order,SUM(a.order_totalPrice) as sum_price,SUM(a.refund_price) as refund_prices,"
				+ " (select SUM(a.order_numbers) from transferorder a where a.order_paystatus='2') as refund_order_numbers from transferorder a LEFT"
						+ " JOIN order_linecardiver b on a.id = b.id left join car_info c on b.licencePlateId =c.id left join "
						+ "driversinfo d on b.driverId =d.id left join lineinfo l on l.id = a.line_id LEFT JOIN car_customer w on w.id=a.user_id "
						+ "LEFT JOIN dealer_customer dc on w.open_id = dc.open_id LEFT JOIN dealer_info fi on fi.id=dc.dealer_id LEFT JOIN t_s_depart t "
						+ "on l.departId=t.ID,t_s_depart p where (case when LENGTH(t.org_code)<6 then t.org_code else substring(t.org_code,1,6) END)=p.org_code");
		if (!transferStatisticsServiceI.getWhere2(orderId,orderStatus,lineName, orderType, driverId, fc_begin, fc_end,departname)
				.isEmpty()) {
			orsql.append(transferStatisticsServiceI.getWhere2(orderId,orderStatus,lineName, orderType, driverId, fc_begin,
					fc_end,departname));
		}
		List<Object> mlist = systemService.findListbySql(orsql.toString());

		int sumorder;
		//int refundorder;
		BigDecimal sumPrice=new BigDecimal("0");
		BigDecimal sumrefundPrice=new BigDecimal("0");
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
			BigDecimal bsum1 = (BigDecimal) ob[2];
			if(bsum1 != null){
				sumrefundPrice = sumrefundPrice.add(bsum1);
			}
			/*if (ob[3] != null) {
				String obnum2 = ob[3] + "";
				refundorder = Integer.parseInt(obnum2.substring(0, obnum2.indexOf(".")));
			} else {
				refundorder = 0;
			}*/
			
		} else {
			
			sumorder = 0;
			//refundorder=0;
		}
		/*if(sumorder>0){
			jsonObj.put("sumorder", sumorder);
		}else{
			
		}*/
		jsonObj.put("sumorder", sumorder);
		if(sumPrice.intValue()>0){
			jsonObj.put("sumPrice", sumPrice.subtract(sumrefundPrice).doubleValue());
		}else{
			jsonObj.put("sumPrice", sumPrice);
		}
		
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
		String departname = request.getParameter("departname");
		StringBuffer resql = new StringBuffer();
		resql.append("select SUM(a.order_numbers),SUM(a.refund_price) from transferorder a LEFT JOIN order_linecardiver b "
						+ "on a.id=b.id left join car_info c on b.licencePlateId =c.id left join driversinfo d on b.driverId =d.id left "
						+ "join lineinfo l on l.id = a.line_id LEFT JOIN car_customer w on w.id=a.user_id LEFT JOIN t_s_depart t on l.departId=t.ID where a.order_paystatus='2'");
		if (!transferStatisticsServiceI.getWhere1(orderId,orderStartingstation, orderTerminusstation, lineName, orderType,departname).isEmpty()) {
			resql.append(transferStatisticsServiceI.getWhere1(orderId,orderStartingstation, orderTerminusstation, lineName,orderType,departname));
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
				sumPrice = sumPrice.add(bsum);
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
	
	/**
	 * 获取线路
	 */
	@RequestMapping(params = "getLineName")
	@ResponseBody
	public String getLineName(HttpServletRequest request){
		
		String ordertype = request.getParameter("ordertype");
		String userType = request.getParameter("userType");
		String linelist = sharingInfoServiceI.getLine(ordertype,userType);
		return linelist;
	}
	/**
	 * 获取统计图数据
	 */
	@RequestMapping(params = "getOrderStatisticsChart")
	@ResponseBody
	public String getOrderStatisticsChart(HttpServletRequest request){
		
		String userType = request.getParameter("userType");
		String dealer = request.getParameter("dealer");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String timestamp = request.getParameter("timestamp");
		
		String orderlist = sharingInfoServiceI.getOrderStatisticsChart(userType,dealer,startDate,endDate,timestamp);
		
		return orderlist;
	}
	
	/**
	 * 获取统计图数据
	 */
	@RequestMapping(params = "getDriverOrderStatisticsChart")
	@ResponseBody
	public String getDriverOrderStatisticsChart(HttpServletRequest request){
		
		String driver = request.getParameter("driver");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String timestamp = request.getParameter("timestamp");
		
		String driverlist = sharingInfoServiceI.getDriverOrderStatisticsChart(driver, startDate, endDate, timestamp);
		
		return driverlist;
	}
	
	/**
	 * 获取统计图数据
	 */
	@RequestMapping(params = "getLineOrderStatisticsChart")
	@ResponseBody
	public String getLineOrderStatisticsChart(HttpServletRequest request){
		
		String lineType = request.getParameter("lineType");
		String lineId = request.getParameter("lineId");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String timesTamp = request.getParameter("timestamp");
		
		String lineList = sharingInfoServiceI.getLineOrderStatisticsChart(lineType,lineId, startDate, endDate, timesTamp);
		
		return lineList;
	}
	
}

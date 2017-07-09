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
import com.yhy.lin.service.DealerStatisticsServiceI;
import com.yhy.lin.service.TransferStatisticsServiceI;

import net.sf.json.JSONObject;

/**
 * 接送机汇总统计
 * 
 * @author
 *
 */
@Controller
@RequestMapping("/dealerStatisticsController")
public class DealerStatisticsController extends BaseController {

	@Autowired
	private SystemService systemService;

	@Autowired
	private DealerStatisticsServiceI dsService;

	/**
	 * 渠道商用户统计
	 */
	@RequestMapping(params = "dealerUserStatisticsList")
	public ModelAndView dealerUserStatisticsList(HttpServletRequest request, HttpServletResponse response) {
		
		request.setAttribute("accountList",getAccount());
		return new ModelAndView("yhy/dealerStatistics/dealerUserStatisticsList");
	}
	
	/**
	 * 获取渠道商用户信息
	 */
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
	
	//干嘛用的
//	@RequestMapping(params = "userdatagrid")
//	public void userdatagrid(CarCustomerEntity carcustomer, HttpServletRequest request,
//			HttpServletResponse response, DataGrid dataGrid) {
//		String fc_begin = request.getParameter("createTime_begin");
//		String fc_end = request.getParameter("createTime_end");
//
//		JSONObject jObject = dsService.getUserDatagrid(carcustomer, dataGrid, fc_begin, fc_end);
//
//		responseDatagrid(response, jObject);
//	}

	//用户列表
	@RequestMapping(params = "dealerUserdatagrid")
	public void dealerUserdatagrid(CarCustomerEntity carcustomer, HttpServletRequest request,
			HttpServletResponse response, DataGrid dataGrid) {
//		String id = request.getParameter("id");
		String account = request.getParameter("accountId");
		String fc_begin = request.getParameter("createTime_begin");
		String fc_end = request.getParameter("createTime_end");

		JSONObject jObject = dsService.getDealerUserDatagrid(carcustomer, dataGrid,account,fc_begin, fc_end);

		responseDatagrid(response, jObject);
	}
	
	/**
	 * 渠道商订单统计
	 */
	@RequestMapping(params = "dealerOrderStatisticsList")
	public ModelAndView dealerOrderStatisticsList(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("accountList",getAccount());
		return new ModelAndView("yhy/dealerStatistics/dealerOrderStatisticsList");
	}
	
	//列表
	@RequestMapping(params = "dealerOrderdatagrid")
	public void dealerOrderdatagrid(TransferorderEntity transferorder, HttpServletRequest request,
			HttpServletResponse response, DataGrid dataGrid) {

		String fc_begin = request.getParameter("orderCompletedTime_begin");
		String fc_end = request.getParameter("orderCompletedTime_end");
		String orderType = request.getParameter("ordertype");
		String lineName = request.getParameter("lineName");
		String account = request.getParameter("account");

		JSONObject jObject = dsService.getDealerOrderDatagrid(transferorder, dataGrid, lineName, orderType,
				account, fc_begin, fc_end);
		responseDatagrid(response, jObject);
	}
	
	//
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

		JSONObject jObject = dsService.getOrderDatagrid(transferorder, dataGrid,orderId,orderStatus, lineName, orderType,
				 driverName, fc_begin, fc_end);
		responseDatagrid(response, jObject);
	}

	/**
	 * 渠道商订单统计
	 */
	@RequestMapping(params = "getDealerTotal")
	@ResponseBody
	public JSONObject getDealerTotal(HttpServletRequest request, HttpServletResponse response) {
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
		if (!dsService.getWhere4(lineName, orderType, account, fc_begin, fc_end)
				.isEmpty()) {
			orsql.append(dsService.getWhere4(lineName, orderType, account, fc_begin,
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

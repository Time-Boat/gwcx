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
import com.yhy.lin.app.util.AppGlobals;
import com.yhy.lin.entity.TransferorderEntity;
import com.yhy.lin.service.DealerStatisticsServiceI;

import net.sf.json.JSONObject;

/**
 * 渠道商统计
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
	 * 渠道商用户统计
	 * @param carcustomer
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "dealerUserdatagrid")
	public void dealerUserdatagrid(CarCustomerEntity carcustomer, HttpServletRequest request,
			HttpServletResponse response, DataGrid dataGrid) {
		String account = request.getParameter("accountId");
		String fc_begin = request.getParameter("createTime_begin");
		String fc_end = request.getParameter("createTime_end");
		
		boolean hasPermission = checkRole(AppGlobals.PLATFORM_DEALER_AUDIT);
		JSONObject jObject = dsService.getDealerUserDatagrid(carcustomer, dataGrid,account,fc_begin, fc_end, hasPermission);
		
		responseDatagrid(response, jObject);
	}
	
	/**
	 * 渠道商订单统计页面
	 */
	@RequestMapping(params = "dealerOrderStatisticsList")
	public ModelAndView dealerOrderStatisticsList(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("accountList",getAccount());
		return new ModelAndView("yhy/dealerStatistics/dealerOrderStatisticsList");
	}
	
	/**
	 * 渠道商订单统计
	 * @param transferorder
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "dealerOrderdatagrid")
	public void dealerOrderdatagrid(TransferorderEntity transferorder, HttpServletRequest request,
			HttpServletResponse response, DataGrid dataGrid) {

		String fc_begin = request.getParameter("orderCompletedTime_begin");
		String fc_end = request.getParameter("orderCompletedTime_end");
		String orderType = request.getParameter("ordertype");
		String lineName = request.getParameter("lineName");
		String account = request.getParameter("accountId");
		
		boolean hasPermission = checkRole(AppGlobals.PLATFORM_DEALER_AUDIT);
		JSONObject jObject = dsService.getDealerOrderDatagrid(transferorder, dataGrid, lineName, orderType,
				account, fc_begin, fc_end, hasPermission);
		responseDatagrid(response, jObject);
	}
	
	/**
	 * 渠道商订单统计
	 */
	@RequestMapping(params = "getDealerTotal")
	@ResponseBody
	public JSONObject getDealerTotal(HttpServletRequest request, HttpServletResponse response,TransferorderEntity transferorder) {
		JSONObject jsonObj = new JSONObject();
		
		String fc_begin = request.getParameter("orderCompletedTime_begin");
		String fc_end = request.getParameter("orderCompletedTime_end");
		String orderType = request.getParameter("ordertype");
		String lineName = request.getParameter("lineName");
		String account = request.getParameter("accountId");

		boolean hasPermission = checkRole(AppGlobals.PLATFORM_DEALER_AUDIT);
		
		StringBuffer orsql = new StringBuffer();
		orsql.append(
				" select SUM(t.order_numbers),SUM(t.order_totalPrice ) from transferorder t LEFT JOIN lineinfo l on t.line_id = l.id "
				+ " LEFT JOIN car_customer w on w.id=t.user_id,dealer_customer d,dealer_info f, t_s_depart td where w.open_id = d.open_id and f.id= "
				+ " d.dealer_id and t.order_status='0' and td.id=f.departId ");
		if (!dsService.getWhere4(transferorder,lineName, orderType, account, fc_begin, fc_end, hasPermission)
				.isEmpty()) {
			orsql.append(dsService.getWhere4(transferorder,lineName, orderType, account, fc_begin,
					fc_end, hasPermission));
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
	
	/*
	 * 用戶汇总统计
	 */
	@RequestMapping(params = "getUserTotal")
	@ResponseBody
	public JSONObject getUserTotal(HttpServletRequest request, HttpServletResponse response) {
		JSONObject jsonObj = new JSONObject();
		
		String account = request.getParameter("accountId");
		String fc_begin = request.getParameter("createTime_begin");
		String fc_end = request.getParameter("createTime_end");
		
		StringBuffer orsql = new StringBuffer();
		orsql.append(
				"select count(*) from car_customer s, dealer_customer d,dealer_info f,t_s_depart b  where s.open_id = d.open_id and f.id=d.dealer_id and b.id=f.departId ");
		
		boolean hasPermission = checkRole(AppGlobals.PLATFORM_DEALER_AUDIT);
		
		if (!dsService.getWhere(account, fc_begin, fc_end, hasPermission).isEmpty()) {
			orsql.append(dsService.getWhere(account,fc_begin,fc_end, hasPermission));
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


}

package com.yhy.lin.controller;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
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
import com.yhy.lin.service.TransferStatisticsServiceI;

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
	 * 获取渠道商用户信息
	 */
	public String getAccount(){
		StringBuffer sql = new StringBuffer();
		
		sql.append("select d.id,d.account from dealer_info d, t_s_depart td where status='0' ");
		
		TSDepart depart = ResourceUtil.getSessionUserName().getCurrentDepart();
		String orgCode = depart.getOrgCode();
		String orgType = depart.getOrgType();
		String userId = ResourceUtil.getSessionUserName().getId();
		
		//判断当前的机构类型，如果是"岗位"类型，就需要加个userId等于当前用户的条件，确保各个专员之间只能看到自己的数据
		if(AppGlobals.ORG_JOB_TYPE.equals(orgType)){
			sql.append(" and d.create_user_id = '" + userId + "' ");
		}
		sql.append(" and td.org_code like '" + orgCode + "%' and td.id=d.departId");
				
		List<Object> list = this.systemService.findListbySql(sql.toString());
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

		JSONObject jObject = dsService.getDealerUserDatagrid(carcustomer, dataGrid,account,fc_begin, fc_end);

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

		JSONObject jObject = dsService.getDealerOrderDatagrid(transferorder, dataGrid, lineName, orderType,
				account, fc_begin, fc_end);
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
		String account = request.getParameter("accountId");

		StringBuffer orsql = new StringBuffer();
		orsql.append(
				"select SUM(t.order_numbers),SUM(t.order_totalPrice ) from transferorder t LEFT JOIN lineinfo l on t.line_id = l.id "
				+ "LEFT JOIN car_customer w on w.id=t.user_id,dealer_customer d,dealer_info f, t_s_depart td where w.open_id = d.open_id and f.id="
				+ "d.dealer_id and t.order_status='0' and td.id=f.departId ");
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
				"select count(*) from car_customer s, dealer_customer d,dealer_info f,t_s_depart td  where s.open_id = d.open_id and f.id=d.dealer_id and td.id=f.departId ");
		
		if (!dsService.getWhere(account,fc_begin, fc_end).isEmpty()) {
			orsql.append(dsService.getWhere(account,fc_begin,fc_end));
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

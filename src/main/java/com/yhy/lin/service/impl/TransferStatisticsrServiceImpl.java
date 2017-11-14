package com.yhy.lin.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yhy.lin.app.entity.CarCustomerEntity;
import com.yhy.lin.app.quartz.BussAnnotation;
import com.yhy.lin.app.util.AppGlobals;
import com.yhy.lin.entity.TransferorderEntity;
import com.yhy.lin.service.TransferStatisticsServiceI;

import net.sf.json.JSONObject;

@Service("TransferStatisticsServiceI")
@Transactional
public class TransferStatisticsrServiceImpl extends CommonServiceImpl implements TransferStatisticsServiceI {

	@Autowired
	private SystemService systemService;
	
	@Override
	public JSONObject getUserDatagrid(CarCustomerEntity carcustomer, DataGrid dataGrid, String fc_begin,
			String fc_end) {
		String sqlWhere = ((TransferStatisticsServiceI) AopContext.currentProxy()).getWhere(fc_begin, fc_end);
		sqlWhere+=" ORDER BY s.create_time desc";
		StringBuffer sql = new StringBuffer();
		String sqlCnt = "select DISTINCT s.id from car_customer s LEFT JOIN dealer_customer d on s.open_id = d.open_id LEFT JOIN dealer_info f on f.id=d.dealer_id  LEFT JOIN transferorder a on a.user_id=s.id LEFT JOIN lineinfo l on a.line_id=l.id LEFT JOIN t_s_depart t on l.departId=t.ID  ";
		if (!sqlWhere.isEmpty()) {
			sqlCnt += sqlWhere;
		}
		List list = this.systemService.findListbySql(sqlCnt);
		
		Long iCount = (long) list.size();

		sql.append("select DISTINCT s.id,s.create_time,s.real_name,s.phone,s.card_number,s.address,s.common_addr,s.login_count,s.user_type,f.account"
				+ " from car_customer s LEFT JOIN dealer_customer d on s.open_id = d.open_id LEFT JOIN dealer_info f on"
				+ " f.id=d.dealer_id  LEFT JOIN transferorder a on a.user_id=s.id LEFT JOIN lineinfo l on a.line_id=l.id  "
				+ "LEFT JOIN t_s_depart t on l.departId=t.ID");
		if (!sqlWhere.isEmpty()) {
			sql.append(sqlWhere);
		}

		List<Map<String, Object>> mapList = findForJdbc(sql.toString(), dataGrid.getPage(), dataGrid.getRows());
		
		//合计登录次数
		/*int count = 0;
		if (mapList.size() > 0) {
			for (int i = 0; i < mapList.size(); i++) {
				int orderNumber = (int) mapList.get(i).get("login_count");
				count = orderNumber + count;
				if (orderNumber != null) {
					String order = orderNumber + "";
					int t = Integer.parseInt(order);
					orderNumbers = t + orderNumbers;
				}
				
			}
		}*/
		
		//dataGrid.setFooter("'loginCount':'" + count + "次','commonAddr':'合计:'");
		// 将结果集转换成页面上对应的数据集
		Db2Page[] db2Pages = { new Db2Page("id"), 
				new Db2Page("createTime", "create_time", null),
				new Db2Page("realName", "real_name", null), 
				new Db2Page("phone", "phone", null),
				new Db2Page("cardNumber", "card_number", null),
				new Db2Page("address", "address", null),
				new Db2Page("commonAddr", "common_addr", null),
				new Db2Page("userType", "user_type", null),
				new Db2Page("loginCount", "login_count", null),
				new Db2Page("account", "account", null)

		};
		JSONObject jObject = this.getJsonDatagridEasyUI(mapList, iCount.intValue(), db2Pages);
		return jObject;
	}

	@Override
	public JSONObject getOrderDatagrid(TransferorderEntity transferorder, DataGrid dataGrid,String orderId,String orderStatus, String lineName,
			String orderType, String driverName, String fc_begin, String fc_end,String departname) {

		String sqlWhere = ((TransferStatisticsServiceI) AopContext.currentProxy()).getWhere2(orderId,orderStatus,lineName, orderType, driverName, fc_begin, fc_end,departname);
		sqlWhere+=" order by a.applicationTime desc";
		StringBuffer sql = new StringBuffer();

		String sqlCnt = "select count(*) from transferorder a LEFT JOIN order_linecardiver b on a.id=b.id left join car_info c on "
				+ "b.licencePlateId =c.id left join driversinfo d on b.driverId =d.id left join lineinfo l on l.id = a.line_id LEFT JOIN t_s_depart t on l.departId=t.ID,t_s_depart p where (case when LENGTH(t.org_code)<6 then t.org_code else substring(t.org_code,1,6) END)=p.org_code";

		if (!sqlWhere.isEmpty()) {
			sqlCnt += sqlWhere;
		}
		Long iCount = getCountForJdbcParam(sqlCnt, null);

		sql.append(
				"select a.id,a.applicationTime,a.order_id,l.name as line_name,l.type,a.order_startime,w.real_name,w.user_type,a.order_contactsname,p.departname,"
						+ "a.order_contactsmobile,d.name as driver_name,c.licence_plate,a.order_status,a.order_numbers,a.order_totalPrice,fi.account from "
						+ "transferorder a LEFT JOIN order_linecardiver b on a.id = b.id left join car_info c on b.licencePlateId =c.id left join "
						+ "driversinfo d on b.driverId =d.id left join lineinfo l on l.id = a.line_id LEFT JOIN car_customer w on w.id=a.user_id "
						+ "LEFT JOIN dealer_customer dc on w.open_id = dc.open_id LEFT JOIN dealer_info fi on fi.id=dc.dealer_id LEFT JOIN t_s_depart t"
						+ " on l.departId=t.ID,t_s_depart p where (case when LENGTH(t.org_code)<6 then t.org_code else substring(t.org_code,1,6) END)=p.org_code");
		if (!sqlWhere.isEmpty()) {
			sql.append(sqlWhere);
		}

		List<Map<String, Object>> mapList = findForJdbc(sql.toString(), dataGrid.getPage(), dataGrid.getRows());

		int orderNumbers = 0;
		BigDecimal orderTotalPrice = new BigDecimal("0");
		if (mapList.size() > 0) {
			for (int i = 0; i < mapList.size(); i++) {
				Object orderNumber = mapList.get(i).get("order_numbers");
				Object total = mapList.get(i).get("order_totalPrice");
				if (orderNumber != null) {
					String order = orderNumber + "";
					int t = Integer.parseInt(order);
					orderNumbers = t + orderNumbers;
				}
				if (total != null) {
					BigDecimal ta = (BigDecimal) total;
					orderTotalPrice=orderTotalPrice.add(ta);
				}
			}
		}
		//dataGrid.setFooter("'commonAddr':'合计:'",TagUtil.getTotalValue("commonAddr", "合计"));
		dataGrid.setFooter("'orderNumbers':'" + orderNumbers + "张','orderTotalPrice':'" + orderTotalPrice
				+ "元','orderStatus':'合计:'");

		// 将结果集转换成页面上对应的数据集
		Db2Page[] db2Pages = { new Db2Page("id"), 
				new Db2Page("applicationTime", "applicationTime", null),
				new Db2Page("orderId", "order_id", null),
				new Db2Page("lineName", "line_name", null),
				new Db2Page("ordertype", "type", null), 
				new Db2Page("orderStartime", "order_startime", null),
				new Db2Page("realName", "real_name", null),
				new Db2Page("orderContactsname", "order_contactsname", null),
				new Db2Page("orderContactsmobile", "order_contactsmobile", null),
				new Db2Page("departname", "departname", null), 
				new Db2Page("userType", "user_type", null), 
				new Db2Page("driverName", "driver_name", null), 
				new Db2Page("licencePlate", "licence_plate", null),
				new Db2Page("orderStatus", "order_status", null),
				new Db2Page("orderNumbers", "order_numbers", null),
				new Db2Page("orderTotalPrice", "order_totalPrice", null),
				new Db2Page("account", "account", null)

		};
		JSONObject jObject = this.getJsonDatagridEasyUIs(mapList, iCount.intValue(), db2Pages, dataGrid);
		return jObject;
	}

	@Override
	public JSONObject getrefundDatagrid(TransferorderEntity transferorder, DataGrid dataGrid,String orderId,
			String orderStartingstation, String orderTerminusstation, String lineName, String orderType,String departname) {
		String sqlWhere = ((TransferStatisticsServiceI) AopContext.currentProxy()).getWhere1(orderId,orderStartingstation, orderTerminusstation, lineName, orderType,departname);

		sqlWhere+=" order by a.refund_completed_time desc";
		
		StringBuffer sql = new StringBuffer();

		String sqlCnt = "select count(*) from transferorder a LEFT JOIN order_linecardiver b on a.id=b.id left join car_info c on "
				+ "b.licencePlateId =c.id left join driversinfo d on b.driverId =d.id left join lineinfo l on l.id = a.line_id LEFT JOIN"
				+ " t_s_depart t on l.departId=t.ID,t_s_depart p where (case when LENGTH(t.org_code)<6 then t.org_code else substring(t.org_code,1,6) END)=p.org_code";

		if (!sqlWhere.isEmpty()) {
			sqlCnt += sqlWhere;
		}
		Long iCount = getCountForJdbcParam(sqlCnt, null);

		sql.append(
				"select a.refund_completed_time,a.id,a.order_id,l.name as line_name,l.type,a.refund_time,w.real_name,w.user_type,p.departname,"
						+ " a.order_contactsname,a.order_contactsmobile,d.name as driver_name,c.licence_plate,a.order_numbers,a.refund_price "
						+ " from transferorder a LEFT JOIN order_linecardiver b on a.id=b.id left join car_info c on b.licencePlateId "
						+ " =c.id left join driversinfo d on b.driverId =d.id left join lineinfo l on l.id = a.line_id LEFT JOIN car_customer w"
						+ " on w.id=a.user_id LEFT JOIN t_s_depart t on l.departId=t.ID,t_s_depart p where (case when LENGTH(t.org_code)<6 then"
						+ " t.org_code else substring(t.org_code,1,6) END)=p.org_code");
		if (!sqlWhere.isEmpty()) {
			sql.append(sqlWhere);
		}

		List<Map<String, Object>> mapList = findForJdbc(sql.toString(), dataGrid.getPage(), dataGrid.getRows());

		int orderNumbers = 0;
		BigDecimal orderTotalPrice = new BigDecimal("0");
		if (mapList.size() > 0) {
			for (int i = 0; i < mapList.size(); i++) {
				Object orderNumber = mapList.get(i).get("order_numbers");
				Object total = mapList.get(i).get("refund_price");
				if (orderNumber != null) {
					String order = orderNumber + "";
					int t = Integer.parseInt(order);
					orderNumbers = t + orderNumbers;
				}
				if (total != null) {
					BigDecimal ta = (BigDecimal) total;
					orderTotalPrice=orderTotalPrice.add(ta);
				}
			}
		}

		dataGrid.setFooter("'orderNumbers':'" + orderNumbers + "张','refundPrice':'" + orderTotalPrice
				+ "元','licencePlate':'合计:'");

		// 将结果集转换成页面上对应的数据集
		Db2Page[] db2Pages = { new Db2Page("id"), new Db2Page("refundCompletedTime", "refund_completed_time", null),
				new Db2Page("orderId", "order_id", null), new Db2Page("lineName", "line_name", null),
				new Db2Page("ordertype", "type", null), new Db2Page("refundTime", "refund_time", null),
				new Db2Page("realName", "real_name", null),
				new Db2Page("orderContactsname", "order_contactsname", null),
				new Db2Page("orderContactsmobile", "order_contactsmobile", null),
				new Db2Page("driverName", "driver_name", null),
				new Db2Page("userType", "user_type", null), 
				new Db2Page("departname", "departname", null),
				new Db2Page("licencePlate", "licence_plate", null),
				new Db2Page("orderNumbers", "order_numbers", null),
				new Db2Page("refundPrice", "refund_price", null)

		};
		JSONObject jObject = getJsonDatagridEasyUIs(mapList, iCount.intValue(), db2Pages, dataGrid);
		return jObject;
	}

	@BussAnnotation(orgType = { AppGlobals.ORG_JOB_TYPE}, 
			objTableUserId = " l.createUserId ", orgTable="t")
	public String getWhere2(String OrderId,String OrderStatus,String lineName, String orderType, String driverName, String fc_begin,
			String fc_end,String departname) {
		
		//String orgCode = ResourceUtil.getSessionUserName().getCurrentDepart().getOrgCode();
		
		StringBuffer sql = new StringBuffer(" and a.order_status not in('4','6') ");
		
		/*if(orgCode.length()>=6){
			String code = orgCode.substring(0,6);
			sql.append(" and t.org_code like '"+code+"%'");
		}*/
		
		//订单编号
		if (StringUtil.isNotEmpty(OrderId)) {
			sql.append(" and  a.order_id like '%" +OrderId + "%'");
		}

		// 发车时间
		if (StringUtil.isNotEmpty(fc_begin) && StringUtil.isNotEmpty(fc_end)) {
			sql.append(" and a.applicationTime between '" + fc_begin + "' and '" + fc_end + "'");
		}
		// 线路类型
		if (StringUtil.isNotEmpty(orderType)) {
			sql.append(" and  l.type ='" + orderType + "'");
		}
		// 线路名称
		if (StringUtil.isNotEmpty(lineName)) {
			sql.append(" and  l.name like '%" + lineName + "%'");
		}

		// 订单状态
		if (StringUtil.isNotEmpty(OrderStatus)) {
			sql.append(" and  a.order_status ='" + OrderStatus + "'");
		}
		// 司机
		if (StringUtil.isNotEmpty(driverName)) {
			sql.append(" and  d.name like '%" + driverName + "%'");
		}
		//所属公司
		if (StringUtil.isNotEmpty(departname)) {
			sql.append(" and  p.departname like '%" + departname + "%'");
		}
		
		return sql.toString();
	}

	@BussAnnotation(orgType = { AppGlobals.ORG_JOB_TYPE}, 
			objTableUserId = " l.createUserId ", orgTable="t")
	public String getWhere(String fc_begin, String fc_end) {
		StringBuffer sql = new StringBuffer();

		//sString orgCode = ResourceUtil.getSessionUserName().getCurrentDepart().getOrgCode();
		
		sql.append(" where 1=1 ");
		
		/*if(orgCode.length()>=6){
			String code = orgCode.substring(0,6);
			sql.append(" and t.org_code like '"+code+"%'");
			
		}*/
		
		// 发车时间
		if (StringUtil.isNotEmpty(fc_begin) && StringUtil.isNotEmpty(fc_end)) {
			sql.append(" and s.create_time between '" + fc_begin + "' and '" + fc_end + "'");
		}
//		sql.append(" ORDER BY s.create_time desc");
		return sql.toString();
	}

	@BussAnnotation(orgType = { AppGlobals.ORG_JOB_TYPE}, 
			objTableUserId = " l.createUserId ", orgTable="t")
	public String getWhere1(String orderId,String orderStartingstation, String orderTerminusstation, String lineName,
			String orderType,String departname) {
		
		//String orgCode = ResourceUtil.getSessionUserName().getCurrentDepart().getOrgCode();
		
		StringBuffer sql = new StringBuffer(" and a.order_status='4' ");
		/*
		if(orgCode.length()>=6){
			String code = orgCode.substring(0,6);
			sql.append(" and t.org_code like '"+code+"%'");
		}*/

		// 发车时间
		if (StringUtil.isNotEmpty(orderStartingstation) && StringUtil.isNotEmpty(orderTerminusstation)) {
			sql.append(
					" and a.refund_completed_time between '" + orderStartingstation + "' and '" + orderTerminusstation + "'");
		}
		//订单编号
		if (StringUtil.isNotEmpty(orderId)) {
			sql.append(" and  a.order_id like '%" + orderId + "%'");
		}		
		// 线路类型
		if (StringUtil.isNotEmpty(orderType)) {
			sql.append(" and  l.type ='" + orderType + "'");
		}
		// 线路名称
		if (StringUtil.isNotEmpty(lineName)) {
			sql.append(" and  l.name like '%" + lineName + "%'");
		}
		if (StringUtil.isNotEmpty(departname)) {
			sql.append(" and p.departname like '%" + departname + "%'");
		}
		
		return sql.toString();
	}

	/**
	 * 返回easyUI的DataGrid数据格式的JSONObject对象
	 * 
	 * @param mapList
	 *            : 从数据库直接取得的结果集列表
	 * @param iTotalCnt
	 *            : 从数据库直接取得的结果集总数据条数
	 * @param dataExchanger
	 *            : 页面表示数据与数据库字段的对应关系列表
	 * @return JSONObject
	 */
	public JSONObject getJsonDatagridEasyUIs(List<Map<String, Object>> mapList, int iTotalCnt, Db2Page[] dataExchanger,
			DataGrid dataGrid) {
		// easyUI的dataGrid方式 －－－－这部分可以提取成统一处理
		String jsonTemp = "{\'total\':" + iTotalCnt + ",\'footer\':[{" + dataGrid.getFooter() + "}],\'rows\':[";
		for (int j = 0; j < mapList.size(); j++) {
			Map<String, Object> m = mapList.get(j);
			if (j > 0) {
				jsonTemp += ",";
			}
			jsonTemp += "{";
			for (int i = 0; i < dataExchanger.length; i++) {
				if (i > 0) {
					jsonTemp += ",";
				}
				jsonTemp += "'" + dataExchanger[i].getKey() + "'" + ":";
				Object objValue = dataExchanger[i].getData(m);
				if (objValue == null) {
					jsonTemp += "null";
				} else {
					String val = (objValue + "").replace("\n", "\\s").replace("\r", "\\s");
					jsonTemp += "'" + val + "'";
				}
			}
			jsonTemp += "}";
		}
		jsonTemp += "]}";
		JSONObject jObject = JSONObject.fromObject(jsonTemp);
		return jObject;
	}
	
}

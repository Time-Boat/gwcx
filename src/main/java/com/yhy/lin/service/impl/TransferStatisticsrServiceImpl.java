package com.yhy.lin.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yhy.lin.entity.TransferorderEntity;
import com.yhy.lin.service.TransferStatisticsServiceI;

import net.sf.json.JSONObject;

@Service("TransferStatisticsServiceI")
@Transactional
public class TransferStatisticsrServiceImpl extends CommonServiceImpl implements TransferStatisticsServiceI {

	@Override
	public JSONObject getUserDatagrid(TransferorderEntity transferorder, DataGrid dataGrid, String fc_begin,
			String fc_end) {
		String sqlWhere = getWhere(fc_begin, fc_end);

		StringBuffer sql = new StringBuffer();
		String sqlCnt = "select COUNT(*) from car_customer s  ";
		if (!sqlWhere.isEmpty()) {
			sqlCnt += " where 1=1 ";
			sqlCnt += sqlWhere;
		}
		Long iCount = getCountForJdbcParam(sqlCnt, null);

		sql.append(
				"select s.id,s.create_time,s.real_name,s.phone,s.card_number,s.address,s.common_addr,SUM(t.order_numbers) as "
						+ "order_numbers,SUM(t.order_totalPrice) as order_totalPrice"
						+ " from car_customer s left join  transferorder t  on  s.id=t.user_id ");
		if (!sqlWhere.isEmpty()) {
			sql.append(sqlWhere);
		}
		sql.append(" GROUP BY s.id");

		List<Map<String, Object>> mapList = findForJdbc(sql.toString(), dataGrid.getPage(), dataGrid.getRows());
		int orderNumbers = 0;
		int orderTotalPrice = 0;
		if (mapList.size() > 0) {
			for (int i = 0; i < mapList.size(); i++) {
				Object orderNumber = mapList.get(i).get("order_numbers");
				Object total = mapList.get(i).get("order_totalPrice");
				if (orderNumber != null) {
					String order = orderNumber + "";
					int t = Integer.parseInt(order.substring(0, order.indexOf(".")));
					orderNumbers = t + orderNumbers;
				}
				if (total != null) {
					BigDecimal ta = (BigDecimal) total;
					int tatal = ta.intValue();
					orderTotalPrice = tatal + orderTotalPrice;
				}
			}
		}

		dataGrid.setFooter(
				"'orderNumbers':'" + orderNumbers + "','orderTotalPrice':'" + orderTotalPrice + "','commonAddr':'合计:'");
		// 将结果集转换成页面上对应的数据集
		Db2Page[] db2Pages = { new Db2Page("id"), new Db2Page("createTime", "create_time", null),
				new Db2Page("realName", "real_name", null), new Db2Page("phone", "phone", null),
				new Db2Page("cardNumber", "card_number", null), new Db2Page("address", "address", null),
				new Db2Page("commonAddr", "common_addr", null), new Db2Page("orderNumbers", "order_numbers", null),
				new Db2Page("orderTotalPrice", "order_totalPrice", null)

		};
		JSONObject jObject = this.getJsonDatagridEasyUIs(mapList, iCount.intValue(), db2Pages, dataGrid);
		return jObject;
	}

	@Override
	public JSONObject getOrderDatagrid(TransferorderEntity transferorder, DataGrid dataGrid,String orderId,String orderStatus, String lineName,
			String orderType, String driverName, String fc_begin, String fc_end) {

		String sqlWhere = getWhere2(orderId,orderStatus,lineName, orderType, driverName, fc_begin, fc_end);

		StringBuffer sql = new StringBuffer();

		String sqlCnt = "select count(*) from transferorder a LEFT JOIN order_linecardiver b on a.id=b.id left join car_info c on "
				+ "b.licencePlateId =c.id left join driversinfo d on b.driverId =d.id left join lineinfo l on l.id = a.line_id ";

		if (!sqlWhere.isEmpty()) {
			sqlCnt += sqlWhere;
		}
		Long iCount = getCountForJdbcParam(sqlCnt, null);

		sql.append(
				"select a.id,a.applicationTime,a.order_id,l.name as line_name,l.type,a.order_startime,w.real_name,a.order_contactsname,"
						+ "a.order_contactsmobile,d.name as driver_name,c.licence_plate,a.order_status,a.order_numbers,a.order_totalPrice from "
						+ "transferorder a LEFT JOIN order_linecardiver b on a.id = b.id left join car_info c on b.licencePlateId =c.id left join "
						+ "driversinfo d on b.driverId =d.id left join lineinfo l on l.id = a.line_id LEFT JOIN car_customer w on w.id=a.user_id");
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
		dataGrid.setFooter("'orderNumbers':'" + orderNumbers + "张','orderTotalPrice':'" + orderTotalPrice
				+ "元','orderStatus':'合计:'");

		// 将结果集转换成页面上对应的数据集
		Db2Page[] db2Pages = { new Db2Page("id"), new Db2Page("applicationTime", "applicationTime", null),
				new Db2Page("orderId", "order_id", null), new Db2Page("lineName", "line_name", null),
				new Db2Page("ordertype", "type", null), new Db2Page("orderStartime", "order_startime", null),
				new Db2Page("realName", "real_name", null),
				new Db2Page("orderContactsname", "order_contactsname", null),
				new Db2Page("orderContactsmobile", "order_contactsmobile", null),
				new Db2Page("driverName", "driver_name", null), new Db2Page("licencePlate", "licence_plate", null),
				new Db2Page("orderStatus", "order_status", null), new Db2Page("orderNumbers", "order_numbers", null),
				new Db2Page("orderTotalPrice", "order_totalPrice", null)

		};
		JSONObject jObject = this.getJsonDatagridEasyUIs(mapList, iCount.intValue(), db2Pages, dataGrid);
		return jObject;
	}

	@Override
	public JSONObject getrefundDatagrid(TransferorderEntity transferorder, DataGrid dataGrid,String orderId,
			String orderStartingstation, String orderTerminusstation, String lineName, String orderType) {
		String sqlWhere = getWhere1(orderId,orderStartingstation, orderTerminusstation, lineName, orderType);

		StringBuffer sql = new StringBuffer();

		String sqlCnt = "select count(*) from transferorder a LEFT JOIN order_linecardiver b on a.id=b.id left join car_info c on "
				+ "b.licencePlateId =c.id left join driversinfo d on b.driverId =d.id left join lineinfo l on l.id = a.line_id ";

		if (!sqlWhere.isEmpty()) {
			sqlCnt += sqlWhere;
		}
		Long iCount = getCountForJdbcParam(sqlCnt, null);

		sql.append(
				"select a.refund_completed_time,a.id,a.order_id,l.name as line_name,l.type,a.refund_time,w.real_name,"
						+ " a.order_contactsname,a.order_contactsmobile,d.name as driver_name,c.licence_plate,a.order_numbers,a.order_totalPrice "
						+ " from transferorder a LEFT JOIN order_linecardiver b on a.id=b.id left join car_info c on b.licencePlateId "
						+ " =c.id left join driversinfo d on b.driverId =d.id left join lineinfo l on l.id = a.line_id LEFT JOIN car_customer w on w.id=a.user_id");
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

		dataGrid.setFooter("'orderNumbers':'" + orderNumbers + "张','orderTotalPrice':'" + orderTotalPrice
				+ "元','licencePlate':'合计:'");

		// 将结果集转换成页面上对应的数据集
		Db2Page[] db2Pages = { new Db2Page("id"), new Db2Page("refundCompletedTime", "refund_completed_time", null),
				new Db2Page("orderId", "order_id", null), new Db2Page("lineName", "line_name", null),
				new Db2Page("ordertype", "type", null), new Db2Page("refundTime", "refund_time", null),
				new Db2Page("realName", "real_name", null),
				new Db2Page("orderContactsname", "order_contactsname", null),
				new Db2Page("orderContactsmobile", "order_contactsmobile", null),
				new Db2Page("driverName", "driver_name", null), new Db2Page("licencePlate", "licence_plate", null),
				new Db2Page("orderNumbers", "order_numbers", null),
				new Db2Page("orderTotalPrice", "order_totalPrice", null)

		};
		JSONObject jObject = getJsonDatagridEasyUIs(mapList, iCount.intValue(), db2Pages, dataGrid);
		return jObject;
	}

	public String getWhere2(String OrderId,String OrderStatus,String lineName, String orderType, String driverName, String fc_begin,
			String fc_end) {
		StringBuffer sql = new StringBuffer(" where a.order_status not in('4','6') ");
		
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

		return sql.toString();
	}

	public String getWhere(String fc_begin, String fc_end) {
		StringBuffer sql = new StringBuffer();

		// 发车时间
		if (StringUtil.isNotEmpty(fc_begin) && StringUtil.isNotEmpty(fc_end)) {
			sql.append(" and s.create_time between '" + fc_begin + "' and '" + fc_end + "'");
		}
		return sql.toString();
	}

	public String getWhere1(String orderId,String orderStartingstation, String orderTerminusstation, String lineName,
			String orderType) {
		StringBuffer sql = new StringBuffer(" where a.order_status='4' ");

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

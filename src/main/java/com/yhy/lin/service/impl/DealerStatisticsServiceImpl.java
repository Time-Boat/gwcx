package com.yhy.lin.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yhy.lin.app.entity.CarCustomerEntity;
import com.yhy.lin.entity.TransferorderEntity;
import com.yhy.lin.service.DealerStatisticsServiceI;
import com.yhy.lin.service.TransferStatisticsServiceI;

import net.sf.json.JSONObject;

@Service("DealerStatisticsServiceI")
@Transactional
public class DealerStatisticsServiceImpl extends CommonServiceImpl implements DealerStatisticsServiceI {

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

	@Override
	public JSONObject getDealerUserDatagrid(CarCustomerEntity carcustomer, DataGrid dataGrid, String account,String fc_begin,
			String fc_end) {
		String sqlWhere = getWhere(account,fc_begin, fc_end);

		StringBuffer sql = new StringBuffer();
		String sqlCnt = "select count(*) from car_customer s, dealer_customer d,dealer_info f where s.open_id = d.open_id and f.id=d.dealer_id";
		if (!sqlWhere.isEmpty()) {
			sqlCnt += sqlWhere;
		}
		Long iCount = getCountForJdbcParam(sqlCnt, null);

		sql.append(
				"select f.account,s.create_time,s.real_name,s.phone,s.card_number,s.address,s.login_count from car_customer s, dealer_customer d,dealer_info f where s.open_id = d.open_id and f.id=d.dealer_id  ");
		if (!sqlWhere.isEmpty()) {
			sql.append(sqlWhere);
		}

		List<Map<String, Object>> mapList = findForJdbc(sql.toString(), dataGrid.getPage(), dataGrid.getRows());
		
		//dataGrid.setFooter("'address':'合计:'");
		// 将结果集转换成页面上对应的数据集
		Db2Page[] db2Pages = { new Db2Page("id"), 
				new Db2Page("account", "account", null),
				new Db2Page("createTime", "create_time", null),
				new Db2Page("realName", "real_name", null),
				new Db2Page("phone", "phone", null),
				new Db2Page("cardNumber", "card_number", null),
				new Db2Page("address", "address", null),
				new Db2Page("loginCount", "login_count", null)

		};
		JSONObject jObject = this.getJsonDatagridEasyUI(mapList, iCount.intValue(), db2Pages);
		return jObject;
	}

	@Override
	public JSONObject getDealerOrderDatagrid(TransferorderEntity transferorder, DataGrid dataGrid, String lineName,
			String orderType, String account, String fc_begin, String fc_end) {
		String sqlWhere = getWhere4(lineName, orderType, account, fc_begin, fc_end);

		StringBuffer sql = new StringBuffer();

		String sqlCnt = "select count(*) from transferorder t LEFT JOIN lineinfo l on t.line_id = l.id LEFT JOIN car_customer w on w.id="
				+ "t.user_id,dealer_customer d,dealer_info f where w.open_id = d.open_id and f.id=d.dealer_id and t.order_status='0'";

		if (!sqlWhere.isEmpty()) {
			sqlCnt += sqlWhere;
		}
		Long iCount = getCountForJdbcParam(sqlCnt, null);

		sql.append("select f.account,t.order_completed_time,t.order_id,l.name as line_name,l.type as line_type,t.order_startime,w.real_name,t.order_contactsname,"
				+ "t.order_contactsmobile,t.order_status,t.order_numbers,t.order_totalPrice from transferorder t LEFT JOIN lineinfo l on "
				+ "t.line_id = l.id LEFT JOIN car_customer w on w.id=t.user_id,dealer_customer d,dealer_info f where w.open_id = d.open_id"
				+ " and f.id=d.dealer_id and t.order_status='0'");
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
				new Db2Page("account", "account", null),
				new Db2Page("orderCompletedTime", "order_completed_time", null),
				new Db2Page("orderId", "order_id", null),
				new Db2Page("lineName", "line_name", null),
				new Db2Page("ordertype", "line_type", null), 
				new Db2Page("orderStartime", "order_startime", null),
				new Db2Page("realName", "real_name", null),
				new Db2Page("orderContactsname", "order_contactsname", null),
				new Db2Page("orderContactsmobile", "order_contactsmobile", null),
				new Db2Page("orderStatus", "order_status", null),
				new Db2Page("orderNumbers", "order_numbers", null),
				new Db2Page("orderTotalPrice", "order_totalPrice", null)

		};
		JSONObject jObject = this.getJsonDatagridEasyUIs(mapList, iCount.intValue(), db2Pages, dataGrid);
		return jObject;
	}
	
	public String getWhere(String account,String fc_begin, String fc_end) {
		StringBuffer sql = new StringBuffer();
		//渠道商
		if (StringUtil.isNotEmpty(account)) {
			sql.append(" and  f.id = '" + account + "'");
		}
		//注册时间
		if (StringUtil.isNotEmpty(fc_begin) && StringUtil.isNotEmpty(fc_end)) {
			sql.append(" and s.create_time between '" + fc_begin + "' and '" + fc_end + "'");
		}
		sql.append(" ORDER BY s.create_time desc");
		return sql.toString();
	}
	
	public String getWhere4(String lineName, String orderType, String account, String fc_begin,
			String fc_end) {
		StringBuffer sql = new StringBuffer();
		
		//渠道商
		if (StringUtil.isNotEmpty(account)) {
			sql.append(" and  f.id = '" + account + "'");
		}
		// 发车时间
		if (StringUtil.isNotEmpty(fc_begin) && StringUtil.isNotEmpty(fc_end)) {
			sql.append(" and t.order_completed_time between '" + fc_begin + "' and '" + fc_end + "'");
		}
		// 线路类型
		if (StringUtil.isNotEmpty(orderType)) {
			sql.append(" and  l.type ='" + orderType + "'");
		}
		// 线路名称
		if (StringUtil.isNotEmpty(lineName)) {
			sql.append(" and  l.name like '%" + lineName + "%'");
		}
		sql.append(" order by t.order_completed_time desc");

		return sql.toString();
	}
}

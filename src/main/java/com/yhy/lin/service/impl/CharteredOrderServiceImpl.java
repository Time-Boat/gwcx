package com.yhy.lin.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.yhy.lin.entity.CharteredOrderEntity;
import com.yhy.lin.service.CharteredOrderServiceI;
import net.sf.json.JSONObject;

import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.StringUtil;

@Service("charteredOrderService")
@Transactional
public class CharteredOrderServiceImpl extends CommonServiceImpl implements CharteredOrderServiceI {

	@Override
	public JSONObject getDatagrid(CharteredOrderEntity charteredOrder, DataGrid dataGrid,String o_begin,String o_end,String username) {
		String sqlWhere = getSqlWhere(charteredOrder,o_begin,o_end,username);
		StringBuffer sql = new StringBuffer();
		// 取出总数据条数（为了分页处理, 如果不用分页，取iCount值的这个处理可以不要）
		String sqlCnt = "select COUNT(*) from chartered_order o LEFT JOIN car_customer c on o.user_id=c.id LEFT JOIN order_chartered_linecardiver l"
				+ " on o.id=l.id LEFT JOIN chartered_price p on o.chartered_price_id=p.id LEFT JOIN chartered_package cp on p.package_id=cp.id "
				+ "LEFT JOIN driversinfo d on d.id=l.driverId LEFT JOIN car_info ca on ca.id=l.licencePlateId LEFT JOIN t_s_base_user u on "
				+ "p.create_user_id=u.ID LEFT JOIN t_s_user_org org on u.ID=org.user_id LEFT JOIN t_s_depart t on t.ID=org.org_id,t_s_depart "
				+ "dep where o.delete_flag='0' and (case when  LENGTH(t.org_code)<6 then t.org_code else substring(t.org_code,1,6) END)=dep.org_code ";
		if (!sqlWhere.isEmpty()) {
			sqlCnt += sqlWhere;
		}
		Long iCount = getCountForJdbcParam(sqlCnt, null);
		// 取出当前页的数据 
		 sql.append("select o.order_id,o.order_startime,o.order_user_type,o.starting_station_name,o.terminus_station_name,o.order_type,cp.name"
		 		+ " as chart_package_name,o.order_actual_mileage,o.order_totalPrice,o.order_contactsname,o.order_contactsmobile,o.applicationTime,"
		 		+ "d.name as driver_name,d.phoneNumber,ca.seat,o.order_status,u.username,dep.departname from chartered_order o LEFT JOIN "
		 		+ "car_customer c on o.user_id=c.id LEFT JOIN order_chartered_linecardiver l on o.id=l.id LEFT JOIN chartered_price p on "
		 		+ "o.chartered_price_id=p.id LEFT JOIN chartered_package cp on p.package_id=cp.id LEFT JOIN driversinfo d on d.id=l.driverId "
		 		+ "LEFT JOIN car_info ca on ca.id=l.licencePlateId LEFT JOIN t_s_base_user u on p.create_user_id=u.ID LEFT JOIN t_s_user_org org "
		 		+ "on u.ID=org.user_id LEFT JOIN t_s_depart t on t.ID=org.org_id,t_s_depart dep where o.delete_flag='0' and (case when "
		 		+ "LENGTH(t.org_code)<6 then t.org_code else substring(t.org_code,1,6) END)=dep.org_code ");
		 
		if (!sqlWhere.isEmpty()) {
			sql.append(sqlWhere);
		}
		List<Map<String, Object>> mapList = findForJdbc(sql.toString(), dataGrid.getPage(), dataGrid.getRows());
		// 将结果集转换成页面上对应的数据集
		Db2Page[] db2Pages = {
				new Db2Page("id")
				,new Db2Page("orderId", "order_id", null)
				,new Db2Page("orderStartime", "order_startime", null)
				,new Db2Page("orderUserType", "order_user_type", null)
				,new Db2Page("startingStationName", "starting_station_name", null)
				,new Db2Page("terminusStationName", "terminus_station_name", null)
				,new Db2Page("orderType", "order_type", null)
				,new Db2Page("chartPackageName", "chart_package_name", null)
				,new Db2Page("orderActualMileage", "order_actual_mileage", null)
				,new Db2Page("orderTotalPrice", "order_totalPrice", null)
				,new Db2Page("orderContactsname", "order_contactsname", null)
				,new Db2Page("orderTotalPrice", "order_totalPrice", null)
				,new Db2Page("orderContactsmobile", "order_contactsmobile", null)
				,new Db2Page("applicationTime", "applicationTime", null)
				,new Db2Page("driverName", "driver_name", null)
				,new Db2Page("phoneNumber", "phoneNumber", null)
				,new Db2Page("seat", "seat", null)
				,new Db2Page("orderStatus", "order_status", null)
				,new Db2Page("username", "username", null)
				,new Db2Page("departname", "departname", null)
				
		};
		JSONObject jObject = getJsonDatagridEasyUI(mapList, iCount.intValue(), db2Pages);
		return jObject;
	}
	
	public String getSqlWhere(CharteredOrderEntity charteredOrder,String o_begin,String o_end,String username){
		StringBuffer sqlWhere = new StringBuffer();
		// 发车时间
		if (StringUtil.isNotEmpty(o_begin) && StringUtil.isNotEmpty(o_end)) {
			sqlWhere.append(" and o.order_startime between '" + o_begin + "' and '" + o_end + "'");
		}
		if (StringUtil.isNotEmpty(username)){
			sqlWhere.append(" and u.username like %"+username+"%");
		}
		
		if (StringUtil.isNotEmpty(charteredOrder.getOrderType())){
			sqlWhere.append(" and o.order_type = '"+charteredOrder.getOrderType()+"'");
		}
		if (StringUtil.isNotEmpty(charteredOrder.getOrderStatus())){
			sqlWhere.append(" and o.order_status = '"+charteredOrder.getOrderStatus()+"'");
		}
		
		return sqlWhere.toString();
	}
}
package com.yhy.lin.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.dao.jdbc.JdbcDao;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl.Db2Page;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.web.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yhy.lin.app.entity.AppMessageListEntity;
import com.yhy.lin.app.util.AppGlobals;
import com.yhy.lin.app.util.AppUtil;
import com.yhy.lin.app.util.SendMessageUtil;
import com.yhy.lin.entity.DriversInfoEntity;
import com.yhy.lin.entity.Order_LineCarDiverEntity;
import com.yhy.lin.entity.TransferorderEntity;
import com.yhy.lin.entity.TransferorderView;
import com.yhy.lin.service.TransferServiceI;

import net.sf.json.JSONObject;

@Service("TransferServiceI")
@Transactional
public class TransferServiceImpl extends CommonServiceImpl implements TransferServiceI {
	@Autowired
	private JdbcDao jdbcDao;
	
	@Autowired
	private SystemService systemService;
	
	@Autowired
	private UserService userService;

	// app客户消息信息
	private static final String USER_MESSAGE_INFO = "您的订单编号为 %1 %2-%3 的订单，确定发车时间为%4，司机手机号为%5，车牌号为%6，请合理安排行程。";

	@Override
	public JSONObject getDatagrid(TransferorderEntity transferorder, DataGrid dataGrid,String lineOrderCode,String orderStartingstation,String orderTerminusstation,String lineId,String driverId,String carId, String fc_begin, String fc_end,
			String ddTime_begin, String ddTime_end) {
		String sqlWhere = getWhere(transferorder,orderStartingstation,lineOrderCode,orderTerminusstation,lineId,driverId,carId,fc_begin, fc_end, ddTime_begin, ddTime_end);

		StringBuffer sql = new StringBuffer();
		StringBuffer sqlCnt = new StringBuffer();

		// 取出总数据条数（为了分页处理, 如果不用分页，取iCount值的这个处理可以不要）
		sqlCnt.append("select count(*) from transferorder a left join order_linecardiver b on a.id = b .id left join car_info c on b.licencePlateId =c.id left join "
				+ "driversinfo d on b.driverId =d.id left join lineinfo l on l.id = a.line_id left join t_s_depart t on t.id = l.departId LEFT JOIN t_s_base_user ur "
				+ "on l.createUserId=ur.ID LEFT JOIN line_busstop lb on lb.busStopsId=a.order_starting_station_id where 1=1");
		if (!sqlWhere.isEmpty()) {
			sqlCnt.append(sqlWhere);
		}
		Long iCount = getCountForJdbcParam(sqlCnt.toString(), null);
		sql.append(
				"select a.city_name,a.city_id,t.org_code,a.id,a.order_id,a.order_type,a.order_status,a.order_flightnumber,a.order_starting_station_name,a.order_terminus_station_name,");
		sql.append(
				"a.order_startime,a.order_expectedarrival,a.order_unitprice,a.order_numbers,a.order_paytype,a.order_contactsname,");
		sql.append(
				"a.order_contactsmobile,a.order_paystatus,a.order_trainnumber,a.order_totalPrice,d.name,d.phoneNumber,c.licence_plate,a.applicationTime,a.line_id,a.line_name,a.user_id,cu.phone");
		sql.append(
				" from transferorder a left join order_linecardiver b on a.id = b .id left join car_info c on b.licencePlateId =c.id left join driversinfo d on b.driverId =d.id"
						+ " left join lineinfo l on l.id = a.line_id left join t_s_depart t on t.id = l.departId LEFT JOIN t_s_base_user ur on l.createUserId=ur.ID left join car_customer cu on a.user_id=cu.id LEFT JOIN line_busstop lb on lb.busStopsId=a.order_starting_station_id where 1=1");
		if (!sqlWhere.isEmpty()) {
			sql.append(sqlWhere);
		}
		List<Map<String, Object>> mapList = findForJdbc(sql.toString(), dataGrid.getPage(), dataGrid.getRows());
		// 将结果集转换成页面上对应的数据集
		Db2Page[] db2Pages = { 
				new Db2Page("id"), 
				new Db2Page("orderId", "order_id", null),
				new Db2Page("orderType", "order_type", null), 
				new Db2Page("orderStatus", "order_status", null),
				new Db2Page("orderFlightnumber", "order_flightnumber", null),
				new Db2Page("orderStartingstation", "order_starting_station_name", null),
				new Db2Page("orderTerminusstation", "order_terminus_station_name", null),
				new Db2Page("orderStartime", "order_startime", null),
				new Db2Page("orderExpectedarrival", "order_expectedarrival", null),
				new Db2Page("orderUnitprice", "order_unitprice", null),
				new Db2Page("orderNumbers", "order_numbers", null), 
				new Db2Page("orderPaytype", "order_paytype", null),
				new Db2Page("orderContactsname", "order_contactsname", null),
				new Db2Page("orderContactsmobile", "order_contactsmobile", null),
				new Db2Page("orderPaystatus", "order_paystatus", null),
				new Db2Page("orderTrainnumber", "order_trainnumber", null),
				new Db2Page("orderTotalPrice", "order_totalPrice", null),
				new Db2Page("name", "name", null),
				new Db2Page("phoneNumber", "phoneNumber", null),
				new Db2Page("licencePlate", "licence_plate", null),
				new Db2Page("applicationTime", "applicationTime", null),
				new Db2Page("lineId", "line_id", null),
				new Db2Page("lineName", "line_name", null),
				new Db2Page("cityName", "city_name", null),
				new Db2Page("userId", "user_id", null),
				new Db2Page("custphone", "phone", null)
		};
		JSONObject jObject = getJsonDatagridEasyUI(mapList, iCount.intValue(), db2Pages);
		return jObject;
	}
	
	@Override
	public JSONObject getDatagrid1(TransferorderEntity transferorder, DataGrid dataGrid,String lineOrderCode,String orderTypes,String orderStartingstation,String orderTerminusstation,String lineId,String driverId,String carId, String fc_begin, String fc_end,
			String ddTime_begin, String ddTime_end) {
		
		//订单类型
		if (StringUtil.isNotEmpty(orderTypes)) {
			transferorder.setOrderType(Integer.parseInt(orderTypes));
		}
		String sqlWhere = getWhere(transferorder,lineOrderCode,orderStartingstation, orderTerminusstation,lineId,driverId,carId,fc_begin, fc_end, ddTime_begin, ddTime_end);

		StringBuffer sql = new StringBuffer();

		StringBuffer sqlCnt = new StringBuffer();
		// 取出总数据条数（为了分页处理, 如果不用分页，取iCount值的这个处理可以不要）
		sqlCnt.append("select count(*) from transferorder a left join order_linecardiver b on a.id = b .id left join car_info c on b.licencePlateId =c.id left join "
				+ "driversinfo d on b.driverId =d.id left join lineinfo l on l.id = a.line_id left join t_s_depart t on t.id = l.departId LEFT JOIN t_s_base_user ur "
				+ "on l.createUserId=ur.ID LEFT JOIN line_busstop lb on lb.busStopsId=a.order_starting_station_id where a.order_type in('2', '3') ");
		/*if (StringUtil.isNotEmpty(orderTypes)) {
			sqlCnt.append(" and  a.order_type= '" + orderTypes + "'");
		}*/
		
		if (!sqlWhere.isEmpty()) {
			sqlCnt.append(sqlWhere);
		}
		
		Long iCount = getCountForJdbcParam(sqlCnt.toString(), null);
		
		sql.append(
				"select a.city_name,a.city_id,t.org_code,a.id,a.order_id,a.order_type,a.order_status,a.order_flightnumber,a.order_starting_station_name,a.order_terminus_station_name,");
		sql.append(
				"a.order_startime,a.order_expectedarrival,a.order_unitprice,a.order_numbers,a.order_paytype,a.order_contactsname,");
		sql.append(
				"a.order_contactsmobile,a.order_paystatus,a.order_trainnumber,a.order_totalPrice,d.name,d.phoneNumber,c.licence_plate,a.applicationTime,a.line_id,a.line_name,a.user_id,cu.phone");
		sql.append(
				" from transferorder a left join order_linecardiver b on a.id = b .id left join car_info c on b.licencePlateId =c.id left join driversinfo d on b.driverId =d.id"
						+ " left join lineinfo l on l.id = a.line_id left join t_s_depart t on t.id = l.departId LEFT JOIN t_s_base_user ur on l.createUserId=ur.ID left join car_customer cu on "
						+ "a.user_id=cu.id LEFT JOIN line_busstop lb on lb.busStopsId=a.order_starting_station_id where a.order_type in('2', '3')");
		
		if (!sqlWhere.isEmpty()) {
			sql.append(sqlWhere);
		}
		List<Map<String, Object>> mapList = findForJdbc(sql.toString(), dataGrid.getPage(), dataGrid.getRows());
		// 将结果集转换成页面上对应的数据集
		Db2Page[] db2Pages = { 
				new Db2Page("id"), 
				new Db2Page("orderId", "order_id", null),
				new Db2Page("orderType", "order_type", null), 
				new Db2Page("orderStatus", "order_status", null),
				new Db2Page("orderFlightnumber", "order_flightnumber", null),
				new Db2Page("orderStartingstation", "order_starting_station_name", null),
				new Db2Page("orderTerminusstation", "order_terminus_station_name", null),
				new Db2Page("orderStartime", "order_startime", null),
				new Db2Page("orderExpectedarrival", "order_expectedarrival", null),
				new Db2Page("orderUnitprice", "order_unitprice", null),
				new Db2Page("orderNumbers", "order_numbers", null), 
				new Db2Page("orderPaytype", "order_paytype", null),
				new Db2Page("orderContactsname", "order_contactsname", null),
				new Db2Page("orderContactsmobile", "order_contactsmobile", null),
				new Db2Page("orderPaystatus", "order_paystatus", null),
				new Db2Page("orderTrainnumber", "order_trainnumber", null),
				new Db2Page("orderTotalPrice", "order_totalPrice", null),
				new Db2Page("name", "name", null),
				new Db2Page("phoneNumber", "phoneNumber", null),
				new Db2Page("licencePlate", "licence_plate", null),
				new Db2Page("applicationTime", "applicationTime", null),
				new Db2Page("lineId", "line_id", null),
				new Db2Page("lineName", "line_name", null),
				new Db2Page("cityName", "city_name", null),
				new Db2Page("userId", "user_id", null),
				new Db2Page("custphone", "phone", null)
		};
		JSONObject jObject = getJsonDatagridEasyUI(mapList, iCount.intValue(), db2Pages);
		return jObject;
	}
	
	@Override
	public JSONObject getDatagrid2(TransferorderEntity transferorder, DataGrid dataGrid,String lineOrderCode,String orderTypes,String orderStartingstation,String orderTerminusstation,String lineId,String driverId,String carId, String fc_begin, String fc_end,
			String ddTime_begin, String ddTime_end) {
		
		//订单类型
		if (StringUtil.isNotEmpty(orderTypes)) {
			transferorder.setOrderType(Integer.parseInt(orderTypes));
		}
		
		String sqlWhere = getWhere(transferorder,lineOrderCode,orderStartingstation ,orderTerminusstation,lineId,driverId,carId,fc_begin, fc_end, ddTime_begin, ddTime_end);

		StringBuffer sql = new StringBuffer();

		StringBuffer sqlCnt = new StringBuffer();
		// 取出总数据条数（为了分页处理, 如果不用分页，取iCount值的这个处理可以不要）
		sqlCnt.append("select count(*) from transferorder a left join order_linecardiver b on a.id = b .id left join car_info c on b.licencePlateId =c.id left join "
				+ "driversinfo d on b.driverId =d.id left join lineinfo l on l.id = a.line_id left join t_s_depart t on t.id = l.departId LEFT JOIN t_s_base_user ur "
				+ "on l.createUserId=ur.ID LEFT JOIN line_busstop lb on lb.busStopsId=a.order_starting_station_id where a.order_type in('4','5')");
		if (!sqlWhere.isEmpty()) {
			sqlCnt.append(sqlWhere);
		}
		Long iCount = getCountForJdbcParam(sqlCnt.toString(), null);
		sql.append("select a.city_name,a.city_id,t.org_code,a.id,a.order_id,a.order_type,a.order_status,a.order_flightnumber,a.order_starting_station_name,a.order_terminus_station_name,");
		sql.append("a.order_startime,a.order_expectedarrival,a.order_unitprice,a.order_numbers,a.order_paytype,a.order_contactsname,");
		sql.append("a.order_contactsmobile,a.order_paystatus,a.order_trainnumber,a.order_totalPrice,d.name,d.phoneNumber,c.licence_plate,a.applicationTime,a.line_id,a.line_name,a.user_id,cu.phone");
		sql.append(" from transferorder a left join order_linecardiver b on a.id = b .id left join car_info c on b.licencePlateId =c.id left join driversinfo d on b.driverId =d.id"
						+ " left join lineinfo l on l.id = a.line_id left join t_s_depart t on t.id = l.departId LEFT JOIN t_s_base_user ur on l.createUserId=ur.ID left join car_customer "
						+ " cu on a.user_id=cu.id LEFT JOIN line_busstop lb on lb.busStopsId=a.order_starting_station_id where a.order_type in('4','5')");
		if (!sqlWhere.isEmpty()) {
			sql.append(sqlWhere);
		}
		List<Map<String, Object>> mapList = findForJdbc(sql.toString(), dataGrid.getPage(), dataGrid.getRows());
		// 将结果集转换成页面上对应的数据集
		Db2Page[] db2Pages = { 
				new Db2Page("id"), 
				new Db2Page("orderId", "order_id", null),
				new Db2Page("orderType", "order_type", null), 
				new Db2Page("orderStatus", "order_status", null),
				new Db2Page("orderFlightnumber", "order_flightnumber", null),
				new Db2Page("orderStartingstation", "order_starting_station_name", null),
				new Db2Page("orderTerminusstation", "order_terminus_station_name", null),
				new Db2Page("orderStartime", "order_startime", null),
				new Db2Page("orderExpectedarrival", "order_expectedarrival", null),
				new Db2Page("orderUnitprice", "order_unitprice", null),
				new Db2Page("orderNumbers", "order_numbers", null), 
				new Db2Page("orderPaytype", "order_paytype", null),
				new Db2Page("orderContactsname", "order_contactsname", null),
				new Db2Page("orderContactsmobile", "order_contactsmobile", null),
				new Db2Page("orderPaystatus", "order_paystatus", null),
				new Db2Page("orderTrainnumber", "order_trainnumber", null),
				new Db2Page("orderTotalPrice", "order_totalPrice", null),
				new Db2Page("name", "name", null),
				new Db2Page("phoneNumber", "phoneNumber", null),
				new Db2Page("licencePlate", "licence_plate", null),
				new Db2Page("applicationTime", "applicationTime", null),
				new Db2Page("lineId", "line_id", null),
				new Db2Page("lineName", "line_name", null),
				new Db2Page("cityName", "city_name", null),
				new Db2Page("userId", "user_id", null),
				new Db2Page("custphone", "phone", null)
		};
		JSONObject jObject = getJsonDatagridEasyUI(mapList, iCount.intValue(), db2Pages);
		return jObject;
	}
	
	@Override
	public JSONObject getDatagrid3(TransferorderEntity transferorder, DataGrid dataGrid,String cityid,String lineId,String lineOrderCode,String lineType) {
		String sqlWhere = getWhere3(transferorder,cityid,lineId,lineOrderCode,lineType);

		StringBuffer sql = new StringBuffer();

		// 取出总数据条数（为了分页处理, 如果不用分页，取iCount值的这个处理可以不要）
		String sqlCnt = "select COUNT(*) from transferorder a left join order_linecardiver b on a.id = b .id left join car_info c on "
				+ "b.licencePlateId =c.id left join driversinfo d on b.driverId =d.id left join lineinfo l on l.id = a.line_id "
				+ "left join t_s_depart t on t.id = l.departId ";
		if (!sqlWhere.isEmpty()) {
			sqlCnt += sqlWhere;
		}
		
		List list = this.systemService.findListbySql(sqlCnt);
		Long iCount = (long) list.size();
		sql.append("select a.lineOrderCode,l.name as lineName,l.type,SUM(case when a.order_status='2' then a.order_numbers else 0 end) as alreadyarranged,SUM(case when a.order_status='1' then a.order_numbers else 0 end) as notarranged,a.city_name,"
				+ "a.city_id,SUM(a.order_numbers) as orderNumber from transferorder a left join order_linecardiver b on a.id = "
				+ "b.id left join car_info c on b.licencePlateId =c.id left join driversinfo d on b.driverId =d.id left join "
				+ "lineinfo l on l.id = a.line_id left join t_s_depart t on t.id = l.departId");
		if (!sqlWhere.isEmpty()) {
			sql.append(sqlWhere);
		}
		List<Map<String, Object>> mapList = findForJdbc(sql.toString(), dataGrid.getPage(), dataGrid.getRows());
		
		// 将结果集转换成页面上对应的数据集
		Db2Page[] db2Pages = { 
				new Db2Page("id"), 
				new Db2Page("lineOrderCode", "lineOrderCode", null),
				new Db2Page("lineName", "lineName", null), 
				new Db2Page("lineType", "type", null), 
				new Db2Page("alreadyarranged", "alreadyarranged", new MyDataExchangerOrderNumber()),
				new Db2Page("notarranged", "notarranged", new MyDataExchangerOrderNumber()),
				new Db2Page("cityName", "city_name", null),
				new Db2Page("cityId", "city_id", null),
				new Db2Page("orderNumber", "orderNumber", new MyDataExchangerOrderNumber() )
		};
		JSONObject jObject = getJsonDatagridEasyUI(mapList, iCount.intValue(), db2Pages);
		return jObject;
	}
	
	//在orderNumber为空的情况下操作下设为0，然后对类型进行转换
	private class MyDataExchangerOrderNumber implements IMyDataExchanger {
		@Override
		public Object exchange(Object value) {
			if (value == null) {
					return "0";
				} else {
					Double order=(Double) value;
					int a = order.intValue();
					return a;
				}
			}
		}
	
	public String getWhere(TransferorderEntity transferorder,String lineOrderCode,String orderStartingstation,String orderTerminusstation,String lineId,String driverId,String carId ,String fc_begin, String fc_end, String ddTime_begin,
			String ddTime_end) {
		StringBuffer sql = new StringBuffer();// 不需要显示退款状态的订单
		
		TSDepart depart = ResourceUtil.getSessionUserName().getCurrentDepart();
		String orgCode = depart.getOrgCode();
		String orgType = depart.getOrgType();
		String userId = ResourceUtil.getSessionUserName().getId();
		
		//判断当前的机构类型，如果是"岗位"类型，就需要加个userId等于当前用户的条件，确保各个专员之间只能看到自己的数据
		if(AppGlobals.ORG_JOB_TYPE.equals(orgType)){
			sql.append(" and l.createUserId = '" + userId + "' ");
			sql.append(" and a.order_history = '0' ");
		}
		
		sql.append(" and t.org_code like '" + orgCode + "%' ");
		
		// 发车时间
		if (StringUtil.isNotEmpty(fc_begin) && StringUtil.isNotEmpty(fc_end)) {
			sql.append(" and a.order_startime between '" + fc_begin + "' and '" + fc_end + "'");
		}
		// 预计到达时间
		if (StringUtil.isNotEmpty(ddTime_begin) && StringUtil.isNotEmpty(ddTime_end)) {
			sql.append(" and a.order_expectedarrival between '" + ddTime_begin + "' and '" + ddTime_end + "'");
		}
		// 订单编号
		if (StringUtil.isNotEmpty(transferorder.getOrderId())) {
			sql.append(" and  a.order_id like '%" + transferorder.getOrderId() + "%'");
		}
		// 线路名称
		if (StringUtil.isNotEmpty(lineId)) {
			sql.append(" and  a.line_id = '" + lineId + "'");
		}
		//司机
		if (StringUtil.isNotEmpty(driverId)) {
			sql.append(" and  d.name like '%" + driverId + "%'");
		}
				
		//车牌号
		if (StringUtil.isNotEmpty(carId)) {
			sql.append(" and  c.licence_plate = '" + carId + "'");
		}
		
		// 订单类型
		if (StringUtil.isNotEmpty(transferorder.getOrderType())) {
			sql.append(" and  a.order_type ='" + transferorder.getOrderType() + "'");
		}
		// 订单状态
		if (StringUtil.isNotEmpty(transferorder.getOrderStatus())) {
			sql.append(" and  a.order_status ='" + transferorder.getOrderStatus() + "'");
		}
		// 起点站id
		if (StringUtil.isNotEmpty(orderStartingstation)) {
			sql.append(" and  a.order_starting_station_name like '%" +orderStartingstation+ "%'");
		}
		// 终点站id
		if (StringUtil.isNotEmpty(orderTerminusstation)) {
			sql.append(" and  a.order_terminus_station_name like '%" + orderTerminusstation + "%'");
		}

		// 申请人
		if (StringUtil.isNotEmpty(transferorder.getOrderContactsname())) {
			sql.append(" and  a.order_contactsname like '%" + transferorder.getOrderContactsname() + "%'");
		}
		// 订单编号
		if (StringUtil.isNotEmpty(lineOrderCode)) {
			sql.append(" and  a.lineOrderCode= '" + lineOrderCode+ "'");
		}
		// 不需要显示退款状态的订单
		
		
		//如果是从接送司机安排页面跳转过来的则不显示未付款订单
		if(StringUtil.isNotEmpty(lineOrderCode)){
			sql.append(" and order_status in('1', '2')");
		}else{
			sql.append(" and order_status in('1', '2', '6', '0')");
		}
		
		sql.append(" and lb.lineId=l.id ");
		
		sql.append(" ORDER BY FIELD(order_status,1,2,3,4,5,6,7,0),lb.siteOrder,order_startime desc");
		
		return sql.toString();
	}
	
	public String getWhere3(TransferorderEntity transferorder,String cityid,String lineId,String lineOrderCode,String lineType) {

		// 添加了权限
		TSDepart depart = ResourceUtil.getSessionUserName().getCurrentDepart();
		String orgCode = depart.getOrgCode();
		String orgType = depart.getOrgType();
		String userId = ResourceUtil.getSessionUserName().getId();
		
		StringBuffer sql = new StringBuffer(" where t.org_code like '" + orgCode + "%' ");
		
		//判断当前的机构类型，如果是"岗位"类型，就需要加个userId等于当前用户的条件，确保各个专员之间只能看到自己的数据
		if(AppGlobals.ORG_JOB_TYPE.equals(orgType)){
			sql.append(" and l.createUserId = '" + userId + "' ");
			sql.append(" and a.order_history = '0' ");
		}
		
		//所属城市
		if (StringUtil.isNotEmpty(cityid)) {
			sql.append(" and  a.city_id = '" + cityid + "'");
		}
		//所属线路
		if (StringUtil.isNotEmpty(lineId)) {
			sql.append(" and  a.line_id = '" + lineId + "'");
		}
		//线路类型
		if (StringUtil.isNotEmpty(lineType)) {
			sql.append(" and  l.type = '" + lineType + "'");
		}
		//线路订单码
		if (StringUtil.isNotEmpty(lineOrderCode)) {
			sql.append(" and  a.lineOrderCode = '" + lineOrderCode + "'");
		}
		// 不需要显示退款状态的订单
		sql.append(" and a.lineOrderCode is not null and a.order_status in('1','2') ");

		sql.append(" GROUP BY a.lineOrderCode ORDER BY a.order_startime");

		return sql.toString();
	}

	// 根据id查询接送机的详细信息
	@Override
	public TransferorderView getDetail(String id) {
		StringBuffer sql = new StringBuffer();
		/*
		 * sql.append(
		 * "select a.id,a.order_id as orderId,a.order_type as orderType,a.order_status as orderStatus,a.order_flightnumber as orderFlightnumber,a.order_starting_station_id,a.order_terminus_station_id,"
		 * ); sql.append(
		 * "a.order_startime as orderStartime,a.order_expectedarrival as orderExpectedarrival,a.order_unitprice as orderUnitprice,a.order_numbers as orderNumbers,a.order_paytype as orderPaytype,a.order_contactsname as orderContactsname,"
		 * ); sql.append(
		 * "a.order_contactsmobile as orderContactsmobile,a.order_paystatus as orderPaystatus,a.order_trainnumber as orderTrainnumber,a.order_totalPrice as orderTotalPrice,d.name as driverName,d.phoneNumber as driverMobile,c.licence_plate as licencePlate,c.status as CarStatus,a.applicationTime "
		 * ); sql.append(
		 * " from transferorder a left join order_linecardiver b on a.id = b .id left join car_info c on b.licencePlateId =c.id left join driversinfo d on b.driverId =d.id "
		 * );
		 */
		sql.append("select a.id,a.order_id,a.order_type,a.order_status,a.order_flightnumber,a.order_starting_station_Name,a.order_terminus_station_Name,");
		sql.append(" a.order_startime,a.order_expectedarrival,a.order_unitprice,a.order_numbers,a.order_paytype,a.order_contactsname,");
		sql.append(" a.order_contactsmobile,a.order_paystatus,a.order_trainnumber,a.order_totalPrice,d.name,d.phoneNumber,c.licence_plate,c.status,a.applicationTime, ");
		sql.append(" a.city_id,a.city_name,cu.phone ");
		sql.append(" from transferorder a left join order_linecardiver b on a.id = b .id left join car_info c on b.licencePlateId =c.id "
				+ "left join driversinfo d on b.driverId =d.id LEFT JOIN car_customer cu on a.user_id=cu.id");

		sql.append(" where a.id='" + id + "'");
		List<Object[]> list = findListbySql(sql.toString());
		TransferorderView transferorderView = new TransferorderView();
		if (list.size() > 0) {
			Object[] obj = list.get(0);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				transferorderView.setId(String.valueOf(obj[0]));
				transferorderView.setOrderId(String.valueOf(obj[1]));
				transferorderView.setOrderType(String.valueOf(obj[2]));
				transferorderView.setOrderStatus(String.valueOf(obj[3]));
				if (obj[4] != null) {
					transferorderView.setOrderFlightnumber(String.valueOf(obj[4]));
				}
				
				transferorderView.setOrderStartingstationName(String.valueOf(obj[5]));
				transferorderView.setOrderTerminusstationName(String.valueOf(obj[6]));
				if (obj[7] != null) {
					transferorderView.setOrderStartime(sdf.parse(obj[7].toString()));
				}
				if (obj[8] != null) {
					transferorderView.setOrderExpectedarrival(sdf.parse(obj[8].toString()));
				}
				
				transferorderView.setOrderUnitprice(String.valueOf(obj[9]));
				transferorderView.setOrderNumbers(String.valueOf(obj[10]));
				transferorderView.setOrderPaytype(String.valueOf(obj[11]));
				transferorderView.setOrderContactsname(String.valueOf(obj[12]));
				transferorderView.setOrderContactsmobile(String.valueOf(obj[13]));
				transferorderView.setOrderPaystatus(String.valueOf(obj[14]));
				if (obj[15] != null) {
					transferorderView.setOrderTrainnumber(String.valueOf(obj[15]));
				}
				transferorderView.setOrderTotalPrice(String.valueOf(obj[16]));
				if (obj[17] != null) {
					transferorderView.setDriverName(String.valueOf(obj[17]));
				}
				if (obj[18] != null) {
					transferorderView.setDriverMobile(String.valueOf(obj[18]));
				}
				transferorderView.setLicencePlate(String.valueOf(obj[19]));
				transferorderView.setCarStatus(String.valueOf(obj[20]));
				if (obj[21] != null) {
					transferorderView.setApplicationTime(sdf.parse(obj[21].toString()));
				}
				transferorderView.setCityId(String.valueOf(obj[22]));
				transferorderView.setCityName(String.valueOf(obj[23]));
				transferorderView.setCustomerPhone(String.valueOf(obj[24]));
				
			} catch (Exception e) {
			}
		}
		return transferorderView;
	}

	/**
	 * 1.添加事务注解 使用propagation 指定事务的传播行为，即当前的事务方法被另外一个事务方法调用时如何使用事务。
	 * 默认取值为REQUIRED，即使用调用方法的事务 REQUIRES_NEW：使用自己的事务，调用的事务方法的事务被挂起。
	 * 
	 * 2.使用isolation 指定事务的隔离级别，最常用的取值为READ_COMMITTED 3.默认情况下 Spring
	 * 的声明式事务对所有的运行时异常进行回滚，也可以通过对应的属性进行设置。通常情况下，默认值即可。 4.使用readOnly 指定事务是否为只读。
	 * 表示这个事务只读取数据但不更新数据，这样可以帮助数据库引擎优化事务。若真的是一个只读取数据库值得方法，应设置readOnly=true
	 * 5.使用timeOut 指定强制回滚之前事务可以占用的时间。
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED
	// noRollbackFor={UserAccountException.class},
	// readOnly=true//, timeout=30 //timeout 允许在执行第一条sql之后保持连接30秒
	)
	@Override
	public boolean saveDriverAndDriver(List<String> orderIds, String startTime, String licencePlateId, String driverId,
			String licencePlateName, List<String[]> contents) {

		boolean b = false;

		List<Order_LineCarDiverEntity> list = new ArrayList<Order_LineCarDiverEntity>();
		List<TransferorderEntity> tList = new ArrayList<TransferorderEntity>();
		List<AppMessageListEntity> mList = new ArrayList<AppMessageListEntity>();
		
		startTime += ":00";
		
		StringBuffer userIds = new StringBuffer();
		try {
			for (int i = 0; i < orderIds.size(); i++) {
				Order_LineCarDiverEntity order_LineCarDiver = new Order_LineCarDiverEntity();
				order_LineCarDiver.setId(orderIds.get(i));
				order_LineCarDiver.setStartTime(startTime);
				order_LineCarDiver.setLicencePlateId(licencePlateId);
				order_LineCarDiver.setDriverId(driverId);
				list.add(order_LineCarDiver);

				// 修改订单状态
				TransferorderEntity t = getEntity(TransferorderEntity.class, orderIds.get(i));
				t.setOrderStatus(2);
				t.setOrderStartime(startTime);
				tList.add(t);
				
				// app端客户消息信息
				DriversInfoEntity d = get(DriversInfoEntity.class, driverId);
				// 只有已付款待审核状态的订单才能创建消息
				if ("0".equals(t.getOrderPaystatus())) {
					// "您的订单编号为%1%2-%3的订单，确定发车时间为%4，司机手机号为%5，车牌号为%6，请合理安排行程。";
					String msgInfo = USER_MESSAGE_INFO.replace("%1", t.getOrderId())
							.replace("%2", t.getOrderStartingStationName())
							.replace("%3", t.getOrderTerminusStationName()).replace("%4", t.getOrderStartime())
							.replace("%5", d.getPhoneNumber()).replace("%6", licencePlateName);

					String type = "0";
					// 这个订单是否已经被处理过
					long l = getCountForJdbcParam("select count(1) from order_linecardiver where id=? ",
							new Object[] { orderIds.get(i) });
					// 被处理过 就将消息类型改为修改
					if (l > 0) {
						type = "1";
					}
					
					mList.add(SendMessageUtil.buildAppMessage(t.getUserId(), msgInfo, "0", type, orderIds.get(i)));
					
					userIds.append("'" + t.getUserId() + "',");
					
					String strDate = "";

					SimpleDateFormat sformat = DateUtils.datetimeFormat;
					SimpleDateFormat sformat1 = new SimpleDateFormat("M月dd日 HH:mm");
					try {
						Date date = sformat.parse(t.getOrderStartime());
						strDate = sformat1.format(date);
					} catch (ParseException e) {
						e.printStackTrace();
					}

					contents.add(new String[] { t.getOrderContactsname(), t.getOrderStartingStationName() + " ",
							" " + t.getOrderTerminusStationName(), strDate, t.getOrderContactsmobile() });

				}
			}
			saveAllEntitie(list);
			saveAllEntitie(mList);
			// if(1==1) //测试spring事物
			// throw new Exception("a");
			saveAllEntitie(tList);
			
			//新增消息后，要把对应的用户状态改一下
			int l = userIds.length();
			if(l > 0){
				updateBySqlString("update car_customer set msg_status='0' where id in (" + userIds.deleteCharAt(l-1) + ") ");
			}
			
			b = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return b;
	}

	@Override
	public JSONObject getCarDatagrid(DataGrid dataGrid) {
		
		String orgCode = ResourceUtil.getSessionUserName().getCurrentDepart().getOrgCode();
		
		//过滤到公司这一层
		orgCode = orgCode.length() <= 6 ? orgCode : orgCode.substring(0, 6);
		
		String sqlWhere = " and c.business_type = '1' and c.car_status = '0' and t.org_code like '" + orgCode + "%'";
		
		String sqlCnt = " select count(1) from car_info c left join driversinfo d on c.driver_id = d.id LEFT JOIN t_s_depart t on c.departId=t.ID "
				+ " LEFT JOIN t_s_base_user u on d.create_user_id = u.id,t_s_depart p "
				+ " where c.delete_flag = '0' and (case when LENGTH(t.org_code) < 6 then t.org_code else substring(t.org_code,1,6) END) = p.org_code ";
		
		Long iCount = getCountForJdbcParam(sqlCnt + sqlWhere, null);
		
		// 取出当前页的数据 
		StringBuffer sql = new StringBuffer();
	    sql.append(" select c.*,d.name,d.driving_license,d.id as driverId,u.username from car_info c left join driversinfo d on c.driver_id = d.id ");
		sql.append(" LEFT JOIN t_s_depart t on c.departId=t.ID LEFT JOIN t_s_base_user u on c.create_user_id = u.id,t_s_depart p ");
		sql.append(" where c.delete_flag = '0' and (case when LENGTH(t.org_code) < 6 then t.org_code else substring(t.org_code,1,6) END) = p.org_code ");
		sql.append(sqlWhere);
		
		List<Map<String, Object>> mapList = findForJdbc(sql.toString(), dataGrid.getPage(), dataGrid.getRows());
		// 将结果集转换成页面上对应的数据集
					Db2Page[] db2Pages = {
							new Db2Page("id", "id")
							,new Db2Page("licencePlate", "licence_plate")
							,new Db2Page("carType", "car_type")
							,new Db2Page("stopPosition", "stop_position")
							,new Db2Page("name", "name")
							,new Db2Page("seat", "seat")
							,new Db2Page("status", "status")
							,new Db2Page("drivingLicense", "driving_license")
							,new Db2Page("businessType", "business_type")
							
							,new Db2Page("buyDate", "buy_date")
							,new Db2Page("carBrand", "car_brand")
							,new Db2Page("modelNumber", "model_number")
							
							,new Db2Page("driverId", "driver_id")
							
							,new Db2Page("remark", "remark")
					}; 
		JSONObject jObject = getJsonDatagridEasyUI(mapList, iCount.intValue(), db2Pages);
		return jObject;
	}

}

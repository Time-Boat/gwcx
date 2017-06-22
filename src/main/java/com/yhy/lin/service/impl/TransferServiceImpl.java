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
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yhy.lin.app.entity.AppMessageListEntity;
import com.yhy.lin.app.util.AppUtil;
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

	// app客户消息信息
	private static final String USER_MESSAGE_INFO = "您的订单编号为 %1 %2-%3 的订单，确定发车时间为%4，司机手机号为%5，车牌号为%6，请合理安排行程。";

	@Override
	public JSONObject getDatagrid(TransferorderEntity transferorder, DataGrid dataGrid,String orderId,String orderType,String orderStartingstation,String orderTerminusstation,String orderStatus, String fc_begin, String fc_end,
			String ddTime_begin, String ddTime_end) {
		String sqlWhere = getWhere(transferorder,orderId, orderType, orderStartingstation, orderTerminusstation, orderStatus, fc_begin, fc_end, ddTime_begin, ddTime_end);

		StringBuffer sql = new StringBuffer();

		// 取出总数据条数（为了分页处理, 如果不用分页，取iCount值的这个处理可以不要）
		String sqlCnt = "select count(*) from transferorder a left join order_linecardiver b on a.id = b .id left join car_info c on b.licencePlateId =c.id "
				+ " left join driversinfo d on b.driverId =d.id  "
				+ " left join lineinfo l on l.id = a.line_id left join t_s_depart t on t.id = l.departId ";
		if (!sqlWhere.isEmpty()) {
			sqlCnt += sqlWhere;
		}
		Long iCount = getCountForJdbcParam(sqlCnt, null);
		sql.append(
				"select a.city_name,a.city_id,t.org_code,a.id,a.order_id,a.order_type,a.order_status,a.order_flightnumber,a.order_starting_station_name,a.order_terminus_station_name,");
		sql.append(
				"a.order_startime,a.order_expectedarrival,a.order_unitprice,a.order_numbers,a.order_paytype,a.order_contactsname,");
		sql.append(
				"a.order_contactsmobile,a.order_paystatus,a.order_trainnumber,a.order_totalPrice,d.name,d.phoneNumber,c.licence_plate,a.applicationTime,a.line_id,a.line_name,a.user_id,cu.phone");
		sql.append(
				" from transferorder a left join order_linecardiver b on a.id = b .id left join car_info c on b.licencePlateId =c.id left join driversinfo d on b.driverId =d.id"
						+ " left join lineinfo l on l.id = a.line_id left join t_s_depart t on t.id = l.departId left join car_customer cu on a.user_id=cu.id");
		if (!sqlWhere.isEmpty()) {
			sql.append(sqlWhere);
		}

		System.out.println(sql.toString());
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

	public String getWhere(TransferorderEntity transferorder,String orderId,String orderType,String orderStartingstation,String orderTerminusstation,String orderStatus, String fc_begin, String fc_end, String ddTime_begin,
			String ddTime_end) {

		String orgCode = ResourceUtil.getSessionUserName().getCurrentDepart().getOrgCode();
		// 添加了权限
		StringBuffer sql = new StringBuffer(" where 1=1 ");

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
		if (StringUtil.isNotEmpty(orderId)) {
			sql.append(" and  a.order_id like '%" + orderId + "%'");
		}
		// 订单类型
		if (StringUtil.isNotEmpty(orderType)) {
			sql.append(" and  a.order_type ='" + orderType + "'");
		}
		// 订单状态
		if (StringUtil.isNotEmpty(orderStatus)) {
			sql.append(" and  a.order_status ='" + orderStatus + "'");
		}
		// 起点站id
		if (StringUtil.isNotEmpty(orderStartingstation)) {
			sql.append(" and  a.order_starting_station_name = '" +orderStartingstation+ "'");
		}
		// 终点站id
		if (StringUtil.isNotEmpty(orderTerminusstation)) {
			sql.append(" and  a.order_terminus_station_name = '" + orderTerminusstation + "'");
		}

		// 申请人
		if (StringUtil.isNotEmpty(transferorder.getOrderContactsname())) {
			sql.append(" and  a.order_contactsname like '%" + transferorder.getOrderContactsname() + "%'");
		}

		// 不需要显示退款状态的订单
		sql.append(" and order_status not in('3','4','5') ");

		sql.append(" ORDER BY FIELD(order_status,1,2,3,4,5,6,7,0)");

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
		sql.append(" a.city_id,a.city_name ");
		sql.append(" from transferorder a left join order_linecardiver b on a.id = b .id left join car_info c on b.licencePlateId =c.id "
				+ "left join driversinfo d on b.driverId =d.id ");

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
				transferorderView.setOrderFlightnumber(String.valueOf(obj[4]));
				transferorderView.setOrderStartingstationName(String.valueOf(obj[5]));
				transferorderView.setOrderTerminusstationName(String.valueOf(obj[6]));
				if (obj[7] != null) {
					transferorderView.setOrderStartime(sdf.parse(obj[7].toString()));
				}
				transferorderView.setOrderExpectedarrival(String.valueOf(obj[8]));
				transferorderView.setOrderUnitprice(String.valueOf(obj[9]));
				transferorderView.setOrderNumbers(String.valueOf(obj[10]));
				transferorderView.setOrderPaytype(String.valueOf(obj[11]));
				transferorderView.setOrderContactsname(String.valueOf(obj[12]));
				transferorderView.setOrderContactsmobile(String.valueOf(obj[13]));
				transferorderView.setOrderPaystatus(String.valueOf(obj[14]));
				transferorderView.setOrderTrainnumber(String.valueOf(obj[15]));
				transferorderView.setOrderTotalPrice(String.valueOf(obj[16]));
				transferorderView.setDriverName(String.valueOf(obj[17]));
				transferorderView.setDriverMobile(String.valueOf(obj[18]));
				transferorderView.setLicencePlate(String.valueOf(obj[19]));
				transferorderView.setCarStatus(String.valueOf(obj[20]));
				if (obj[21] != null) {
					transferorderView.setApplicationTime(sdf.parse(obj[21].toString()));
				}
				transferorderView.setCityId(String.valueOf(obj[22]));
				transferorderView.setCityName(String.valueOf(obj[23]));
				
				
				
				transferorderView.setId(String.valueOf(obj[0]));
				transferorderView.setOrderId(String.valueOf(obj[1]));
				transferorderView.setOrderType(String.valueOf(obj[2]));
				transferorderView.setOrderStatus(String.valueOf(obj[3]));
				transferorderView.setOrderFlightnumber(String.valueOf(obj[4]));
				transferorderView.setOrderStartingstationName(String.valueOf(obj[5]));
				transferorderView.setOrderTerminusstationName(String.valueOf(obj[6]));
				if (obj[7] != null) {
					transferorderView.setOrderStartime(sdf.parse(obj[7].toString()));
				}
				transferorderView.setOrderExpectedarrival(String.valueOf(obj[8]));
				transferorderView.setOrderUnitprice(String.valueOf(obj[9]));
				transferorderView.setOrderNumbers(String.valueOf(obj[10]));
				transferorderView.setOrderPaytype(String.valueOf(obj[11]));
				transferorderView.setOrderContactsname(String.valueOf(obj[12]));
				transferorderView.setOrderContactsmobile(String.valueOf(obj[13]));
				transferorderView.setOrderPaystatus(String.valueOf(obj[14]));
				transferorderView.setOrderTrainnumber(String.valueOf(obj[15]));
				transferorderView.setOrderTotalPrice(String.valueOf(obj[16]));
				transferorderView.setDriverName(AppUtil.Null2Blank(obj[17]+""));
				transferorderView.setDriverMobile(AppUtil.Null2Blank(String.valueOf(obj[18])+""));
				transferorderView.setLicencePlate(AppUtil.Null2Blank(String.valueOf(obj[19])+""));
				transferorderView.setCarStatus(AppUtil.Null2Blank(String.valueOf(obj[20])+""));
				if (obj[21] != null) {
					transferorderView.setApplicationTime(sdf.parse(obj[21].toString()));
				}
				transferorderView.setCityId(String.valueOf(obj[22]));
				transferorderView.setCityName(String.valueOf(obj[23]));
				
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

					AppMessageListEntity app = new AppMessageListEntity();
					app.setContent(msgInfo);
					app.setCreateTime(AppUtil.getCurTime());
					app.setStatus("0");
					app.setUserId(t.getUserId());
					// 这个订单是否已经被处理过
					long l = getCountForJdbcParam("select count(1) from order_linecardiver where id=? ",
							new Object[] { orderIds.get(i) });
					// 被处理过 就将消息类型改为修改
					if (l > 0) {
						app.setMsgType("1");
					} else {
						app.setMsgType("0");
					}
					app.setOrderId(orderIds.get(i));
					mList.add(app);

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
					// contents.add(new String[] {"某" , "吗", "吗"
					// , "吗", t.getOrderContactsmobile()});

				}
			}
			saveAllEntitie(list);
			saveAllEntitie(mList);
			// if(1==1) //测试spring事物
			// throw new Exception("a");
			saveAllEntitie(tList);
			b = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return b;
	}

}

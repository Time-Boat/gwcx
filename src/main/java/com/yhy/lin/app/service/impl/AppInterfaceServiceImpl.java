package com.yhy.lin.app.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.UUIDGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yhy.lin.app.entity.AppCheckTicket;
import com.yhy.lin.app.entity.AppLineStationInfoEntity;
import com.yhy.lin.app.entity.AppMessageListEntity;
import com.yhy.lin.app.entity.AppStationInfoEntity;
import com.yhy.lin.app.entity.AppUserOrderDetailEntity;
import com.yhy.lin.app.entity.AppUserOrderEntity;
import com.yhy.lin.app.entity.CustomerCommonAddrEntity;
import com.yhy.lin.app.util.AppUtil;
import com.yhy.lin.app.util.MakeOrderNum;
import com.yhy.lin.entity.TransferorderEntity;

import AppInterfaceController.AppInterfaceService;

/**
* Description : 
* @author Administrator
* @date 2017年6月1日 下午4:46:27
*/
@Service("AppInterfaceService")
@Transactional
public class AppInterfaceServiceImpl extends CommonServiceImpl implements AppInterfaceService {

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
	public void saveOrder(TransferorderEntity t, String orderPrefix, String commonAddrId) {
		
		// 生成订单号
		t.setOrderId(MakeOrderNum.makeOrderNum(orderPrefix));
		
		// 做了一个视图，确保查出的线路的唯一性
		Map<String, Object> map = findOneForJdbc("select * from order_line_view where busStopsId=? ", commonAddrId);
		if (map.size() > 0) {
			t.setLineName(map.get("name") + "");
			t.setLineId(map.get("id") + "");
		}
		
		t.setApplicationTime(AppUtil.getDate());
		// t.setOrderType(1);
		
		// 记录常用站点
		CustomerCommonAddrEntity c = null;
		List<CustomerCommonAddrEntity> addrs = findHql(
				" from CustomerCommonAddrEntity where user_id=? and station_id=? ", t.getUserId(),
				commonAddrId);
		if (addrs.size() > 0) {
			c = addrs.get(0);
			c.setCount(c.getCount() + 1);
		} else {
			c = new CustomerCommonAddrEntity();
			c.setCount(1);
			c.setStationId(commonAddrId);
			c.setUserId(t.getUserId());
			c.setId(UUIDGenerator.generate());
		}
		
		// 添加到消息中心
		AppMessageListEntity app = new AppMessageListEntity();
		app.setContent("您已购买 " + t.getOrderStartingStationName() + "-" + t.getOrderTerminusStationName()
				+ " 的车票，请等待管理员审核。");
		app.setCreateTime(AppUtil.getCurTime());
		app.setStatus("0"); // 消息状态 0：否 1：是
		app.setUserId(t.getUserId());
		app.setMsgType("0"); // 消息类型 0:新增 1:修改
		app.setOrderId(t.getId());
		
		save(t);
		save(app);
		save(c);
	}

	@Override
	public List<AppStationInfoEntity> getPTStation(String serveType, String cityId) {
		
		List<AppStationInfoEntity> lList = new ArrayList<>();
		
		// 查找指定类型的线路
		List<Map<String, Object>> lineList = findForJdbc(
				" select bi.id,bi.name,lb.lineId,bi.stopLocation,bi.x,bi.y "
						+ " from Line_busStop lb INNER JOIN lineinfo lf on lb.lineId = lf.id INNER JOIN busstopinfo bi on bi.id=lb.busStopsId "
						+ " where lf.cityId=? and lf.type=? and bi.station_type=? and lf.deleteFlag=0 and bi.deleteFlag=0 group by lb.busStopsId ",
				cityId, serveType, AppUtil.getStationType(serveType));

		for (Map<String, Object> a : lineList) {
			AppStationInfoEntity asi = new AppStationInfoEntity();
			asi.setId(a.get("id") + "");
			asi.setName(a.get("name") + "");
			asi.setLineId(a.get("lineId") + "");
			asi.setStopLocation(AppUtil.Null2Blank(a.get("stopLocation") + ""));
			asi.setX(AppUtil.Null2Blank(a.get("x") + ""));
			asi.setY(AppUtil.Null2Blank(a.get("y") + ""));

			lList.add(asi);
		}
		
		return lList;
		
	}

	@Override
	public void getLinebyStation(String serveType, String cityId, String stationId, String userId,
		List<AppLineStationInfoEntity> lList, List<AppStationInfoEntity> cList,
		List<AppStationInfoEntity> stationList) {
		
		// 根据起点id城市查找线路信息
		List<Map<String, Object>> lineList = findForJdbc(
				" select lf.id,lf.name,lf.price,lf.lineTimes "
						+ " from Line_busStop lb INNER JOIN lineinfo lf on lb.lineId = lf.id "
						+ " where busStopsId=? and lf.cityId=? and lf.type=? and lf.deleteFlag=0  ",
				stationId, cityId, serveType);

		StringBuffer sbf = new StringBuffer();
		for (Map<String, Object> a : lineList) {

			AppLineStationInfoEntity asi = new AppLineStationInfoEntity();

			asi.setId(a.get("id") + "");
			asi.setName(a.get("name") + "");
			asi.setPrice(AppUtil.Null2Blank(a.get("price") + ""));
			asi.setLineTimes(AppUtil.Null2Blank(a.get("lineTimes") + ""));
			// asi.setStationType(a.get("station_type") + "");
			lList.add(asi);

			sbf.append("'");
			sbf.append(asi.getId());
			sbf.append("',");
		}
		 
		if (sbf.length() > 0)
			sbf.deleteCharAt(sbf.length() - 1);

		// 查询指定id线路中的所有普通站点
		stationList = findHql(
				"from AppStationInfoEntity where lineId in (" + sbf.toString() + ") and station_type=? ", 0);

		// 常用站点列表
		// List<CustomerCommonAddrEntity> c =
		// systemService.findHql("from CustomerCommonAddrEntity where
		// user_id=? ", userId);
		List<Map<String, Object>> map = findForJdbc(
				"select b.id,b.name,b.x,b.y,b.stopLocation,b.station_type,b.lineId from app_station_info_view b "
						+ "inner join customer_common_addr c on c.station_id=b.id where c.user_id=? and b.lineId in ("
						+ sbf.toString() + ") and station_type=?",
				userId, 0);

		for (Map<String, Object> c : map) {
			AppStationInfoEntity addr = new AppStationInfoEntity();
			addr.setId(c.get("id") + "");
			addr.setLineId(c.get("lineId") + "");
			addr.setName(c.get("name") + "");
			addr.setStopLocation(c.get("stopLocation") + "");
			addr.setX(c.get("y") + "");
			addr.setY(c.get("y") + "");
			cList.add(addr);
		}
		
	}

	@Override
	public List<AppUserOrderEntity> getUserOrders(String userId, String orderStatus, String pageNo,
			String maxPageItem) {
		
		List<Map<String, Object>> list = null;
		List<AppUserOrderEntity> auoList = new ArrayList<>();
		
		StringBuffer sql = new StringBuffer();
		sql.append(
				"select a.id,a.order_status,order_id,a.order_starting_station_name,a.order_terminus_station_name,a.order_totalPrice,a.order_numbers,a.order_startime "
						+ " from transferorder a left join order_linecardiver b on a.id = b.id where user_id=? ");

		if (StringUtil.isNotEmpty(orderStatus)) {
			sql.append(" and order_paystatus = '" + orderStatus + "'");
		}

		list = findForJdbcParam(sql.toString(), Integer.parseInt(pageNo),
				Integer.parseInt(maxPageItem), userId);

		for (Map<String, Object> map : list) {
			// Date date = (Date) map.get("startTime");

			AppUserOrderEntity auo = new AppUserOrderEntity();
			auo.setId(map.get("id") + "");
			auo.setOrderId(map.get("order_id") + "");
			auo.setOrderNumbers(map.get("order_numbers") + "");
			auo.setOrderStartime(map.get("order_startime") + ""); // 使用用户的发车时间
			auo.setOrderStartingStationName(map.get("order_starting_station_name") + "");
			// 要做一下为空的判断，这个字段不会为空
			auo.setOrderStatus(map.get("order_status") + "");
			auo.setOrderTerminusStationName(map.get("order_terminus_station_name") + "");
			auo.setOrderTotalPrice(map.get("order_totalPrice") + "");
			auoList.add(auo);
		}
		
		return auoList;
	}

	@Override
	public AppUserOrderDetailEntity getOrderDetailById(String orderId) {
		
		List<Map<String, Object>> list = null;
		AppUserOrderDetailEntity aod = new AppUserOrderDetailEntity();
		
		//为了通用这个详情页，把几个订单详情页的属性都拿出来了
		list = findForJdbc(
				"select a.id,a.order_type,a.order_status,a.order_id,a.order_starting_station_name, "
						+ "	a.order_terminus_station_name,a.order_totalPrice,a.order_numbers,a.order_startime,a.order_contactsname,a.order_contactsmobile, "
						+ " a.applicationTime,c.licence_plate,c.car_type,d.name as driver_name,d.phoneNumber as driver_phone,l.lineTimes "
						+ " from transferorder a left join order_linecardiver b on a.id = b .id left join car_info c on b.licencePlateId =c.id "
						+ " left join driversinfo d on b.driverId = d.id left join lineInfo l on a.line_id = l.id where a.id=? ",
				orderId);

		for (Map<String, Object> map : list) {
			// 会出现空指针么...
			Date date = (Date) map.get("order_startime");
			Date date1 = (Date) map.get("applicationTime");

			aod.setId(map.get("id") + "");
			aod.setOrderType(map.get("order_type") + "");
			aod.setOrderStatus(map.get("order_status") + "");
			aod.setOrderId(map.get("order_id") + "");
			aod.setOrderStartingStationName(map.get("order_starting_station_name") + "");
			aod.setOrderTerminusStationName(map.get("order_terminus_station_name") + "");
			aod.setOrderTotalPrice(map.get("order_totalPrice") + "");
			aod.setOrderNumbers(map.get("order_numbers") + "");

			aod.setOrderStartime(DateUtils.date2Str(date, DateUtils.datetimeFormat));
			aod.setApplicationTime(DateUtils.date2Str(date1, DateUtils.datetimeFormat));

			aod.setOrderContactsname(map.get("order_contactsname") + "");
			aod.setOrderContactsmobile(map.get("order_contactsmobile") + "");
			aod.setLicencePlate(AppUtil.Null2Blank(map.get("licence_plate") + ""));
			aod.setCarType(AppUtil.Null2Blank(map.get("car_type") + ""));

			aod.setDriver(AppUtil.Null2Blank(map.get("driver_name") + ""));
			aod.setDriverPhone(AppUtil.Null2Blank(map.get("driver_phone") + ""));

			// 发车时间
			aod.setStationStartTime(DateUtils.date2Str(date, DateUtils.short_time_sdf));
			// 线路时长
			String lineTime = map.get("lineTimes") + "";

			// 在发车时间的基础上加上线路所用时长
			if (StringUtil.isNotEmpty(lineTime)) {
				double lt = Double.parseDouble(lineTime);

				long a = (long) (date.getTime() + (lt * 60 * 60 * 1000));

				aod.setStationEndTime(DateUtils.date2Str(new Date(a), DateUtils.short_time_sdf));
			} else {
				aod.setStationEndTime("");
			}
		}
		
		return aod;
	}

	@Override
	public List<AppCheckTicket> getTicketListById(String userId, String pageNo, String maxPageItem) {
		
		List<Map<String, Object>> list = null;
		List<AppCheckTicket> actlist = new ArrayList<>();
		
		StringBuffer sql = new StringBuffer();
		sql.append(
				" select a.id,a.order_status,a.order_starting_station_name,a.order_terminus_station_name,a.order_totalPrice,a.order_numbers,a.order_startime "
						+ " ,c.licence_plate,c.car_type,d.name as driver_name,d.phoneNumber "
						+ " from transferorder a left join order_linecardiver b on a.id = b .id left join car_info c on b.licencePlateId =c.id "
						+ " left join driversinfo d on b.driverId =d.id where a.user_id=? and a.order_status='2' and order_paystatus='0' ");

		list = findForJdbcParam(sql.toString(), Integer.parseInt(pageNo),
				Integer.parseInt(maxPageItem), userId);

		for (Map<String, Object> map : list) {

			AppCheckTicket auo = new AppCheckTicket();
			auo.setId(map.get("id") + "");
			auo.setOrderNumbers(map.get("order_numbers") + "");
			auo.setOrderStartime(map.get("order_startime") + "");
			auo.setOrderStartingStationName(map.get("order_starting_station_name") + "");
			auo.setOrderTerminusStationName(map.get("order_terminus_station_name") + "");
			// 要做一下为空的判断，这个字段不会为空
			auo.setOrderStatus(map.get("order_status") + "");
			auo.setOrderTotalPrice(map.get("order_totalPrice") + "");
			auo.setLicencePlate(map.get("licence_plate") + "");
			auo.setCarType(map.get("car_type") + "");
			auo.setDriver(map.get("driver_name") + "");
			auo.setDriverPhone(map.get("phoneNumber") + "");

			actlist.add(auo);
		}
		return actlist;
	}

	@Override
	public List<AppMessageListEntity> getMessageListById(String userId, String pageNo, String maxPageItem) {
		
		List<AppMessageListEntity> mList = new ArrayList<>();
		List<Map<String, Object>> lm = null;
		
		lm = findForJdbcParam("select * from customer_message where user_id = ?",
				Integer.parseInt(pageNo), Integer.parseInt(maxPageItem), userId);

		for (Map<String, Object> m : lm) {
			AppMessageListEntity am = new AppMessageListEntity();
			am.setContent(m.get("content") + "");
			am.setCreateTime(m.get("create_time") + "");
			am.setId(m.get("id") + "");
			am.setMsgType(m.get("msg_type") + "");
			am.setOrderId(m.get("order_id") + "");
			am.setStatus(m.get("status") + "");
			am.setUserId(m.get("user_id") + "");
			mList.add(am);
		}

		String sql = "update customer_message set status=1 where user_id=? ";
		executeSql(sql, userId);
		
		return null;
	}

	
	
	
}
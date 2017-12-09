package com.yhy.lin.app.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.UUIDGenerator;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.yhy.lin.app.service.AppInterfaceService;
import com.yhy.lin.app.util.AppUtil;
import com.yhy.lin.app.util.MakeOrderNum;
import com.yhy.lin.comparators.SortBySeq;
import com.yhy.lin.entity.AreaStationMiddleEntity;
import com.yhy.lin.entity.CarTSTypeLineEntity;
import com.yhy.lin.entity.DealerOrderUserStationEntity;
import com.yhy.lin.entity.LineInfoEntity;
import com.yhy.lin.entity.Order_LineCarDiverEntity;
import com.yhy.lin.entity.TransferorderEntity;

import sun.print.resources.serviceui;

/**
 * Description :
 * 
 * @author Administrator
 * @date 2017年6月1日 下午4:46:27
 */
@Service("AppInterfaceService")
@Transactional
public class AppInterfaceServiceImpl extends CommonServiceImpl implements AppInterfaceService {

	@Autowired
	private SystemService systemService;

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
	public String saveOrder(TransferorderEntity t, String orderPrefix, String commonAddrId, DealerOrderUserStationEntity station) {

		// 生成订单号
		t.setOrderId(MakeOrderNum.makeOrderNum(orderPrefix));

		// 订单批次编号
		String lineOrderCodel = "";

		LineInfoEntity line = this.systemService.getEntity(LineInfoEntity.class, t.getLineId());
		String str = line.getLineNumber();
		String orderstart = t.getOrderStartime();
		try {
			if (StringUtil.isNotEmpty(orderstart)) {
				Date w = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(orderstart);
				long nowLong = Long.parseLong(new SimpleDateFormat("yyyyMMddHHmm").format(w));
				String s = nowLong + "";
				String a = s.substring(10);
				String b = s.substring(2, 10);
				System.out.println(b);
				if (Integer.parseInt(a) < 30) {
					lineOrderCodel = str + b + "A";
				} else {
					lineOrderCodel = str + b + "B";
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		t.setLineOrderCode(lineOrderCodel);
		
//		// 如果是一个批次只需要安排一辆车，然后由调度员去处理，那安排车辆有什么意义呢。。。
//		List<Map<String, Object>> list = systemService.findForJdbc(
//				"select o.driverId,o.licencePlateId from transferorder t join order_linecardiver o on t.id = o.id where lineOrderCodel = ? ",lineOrderCodel);
//		// 只需要拿第一个数据就行了
//		if (list != null && list.size() > 0) {
//			Map<String, Object> map = list.get(0);
//			String driverId = map.get("driverId") + "";
//			String licencePlateId = map.get("licencePlateId") + "";
//			Order_LineCarDiverEntity olc = new Order_LineCarDiverEntity();
//		}

		t.setApplicationTime(AppUtil.getDate());
		// t.setOrderType(1);

		// 记录常用站点
		CustomerCommonAddrEntity c = null;
		List<CustomerCommonAddrEntity> addrs = findHql(
				" from CustomerCommonAddrEntity where user_id=? and station_id=? ", t.getUserId(), commonAddrId);
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
		
		save(t);
		
		if("1".equals(t.getOrderUserType())){
			station.setOrderId(t.getId());
			save(station);
		}
		
		save(c);

		return t.getId();
	}

	@Override
	public List<AppStationInfoEntity> getPTStation(String serveType, String cityId, String userType) {

		List<AppStationInfoEntity> lList = new ArrayList<>();
		
		StringBuffer sql = new StringBuffer();
		sql.append(" select bi.id,bi.name,lb.lineId,bi.stopLocation,bi.x,bi.y ");
		sql.append(" from Line_busStop lb INNER JOIN lineinfo lf on lb.lineId = lf.id INNER JOIN busstopinfo bi on bi.id=lb.busStopsId ");
		sql.append(" where lf.cityId=? and lf.type=? and bi.station_type=? and lf.deleteFlag=0 and bi.deleteFlag=0 and lf.status=0 ");
		if(userType.equals("1")){
			sql.append(" and lf.is_dealer_line=1 ");
		}
		sql.append(" group by lb.busStopsId ");
		
		// 查找指定类型的线路
		List<Map<String, Object>> lineList = findForJdbc(sql.toString(), cityId, serveType, AppUtil.getStationType(serveType));

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
	public void getLinebyStation(String serveType, String cityId, String stationId, String userId, String likeStation,
			List<AppLineStationInfoEntity> lList, List<AppStationInfoEntity> cList,
			List<AppStationInfoEntity> stationList, String userType) {

		StringBuffer sql = new StringBuffer();
		sql.append(" select lf.id,lf.name,lf.price,lf.lineTimes,lf.dispath ");
		sql.append(" from Line_busStop lb INNER JOIN lineinfo lf on lb.lineId = lf.id ");
		sql.append(" where busStopsId=? and lf.cityId=? and lf.type=? and lf.deleteFlag=0 and lf.status=0 ");
		
		if(userType.equals("1")){
			sql.append(" and lf.is_dealer_line like '%1%' ");
		}else if(userType.equals("0")){
			sql.append(" and lf.is_dealer_line like '%0%' ");
		}
		
		// 根据起点id城市查找线路信息
		List<Map<String, Object>> lineList = findForJdbc(sql.toString(), stationId, cityId, serveType);

		if (lineList.size() == 0) {
			return;
		}

		//线路id的集合
		StringBuffer sbf = new StringBuffer();
		for (Map<String, Object> a : lineList) {

			AppLineStationInfoEntity asi = new AppLineStationInfoEntity();

			asi.setId(a.get("id") + "");
			asi.setName(a.get("name") + "");
			asi.setPrice(AppUtil.Null2Blank(a.get("price") + ""));
			asi.setLineTimes(AppUtil.Null2Blank(a.get("lineTimes") + ""));
			asi.setDispath(a.get("dispath") + "");
			lList.add(asi);

			sbf.append("'");
			sbf.append(asi.getId());
			sbf.append("',");
		}

		if (sbf.length() > 0)
			sbf.deleteCharAt(sbf.length() - 1);

		String ssql = "from AppStationInfoEntity where lineId in (" + sbf.toString() + ") and station_type=? ";
		if(StringUtil.isNotEmpty(likeStation)){
			ssql += " and name like '%" + likeStation + "%' ";
		}
		
		// 普通用户查找普通站点，渠道商用户查找区域站点
		if("1".equals(userType)){
			List<AppStationInfoEntity> stationList1 = findHql(ssql, "3");
			for(AppStationInfoEntity aInfo : stationList1){
				
				AppStationInfoEntity asi = new AppStationInfoEntity();
				
				try {
					//直接修改查出来的值会报错，克隆一个实体出来，性能需要优化
					MyBeanUtils.copyBeanNotNull2Bean(aInfo, asi);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				List<Map<String,Object>> mList = findForJdbc(
						"select area_x,area_y,xy_seq from area_station_middle where station_id = ? order by xy_seq asc", asi.getId());
				
//				String[][] path = new String[mList.size()][];
//				for(int i=0;i<mList.size();i++){
//					path[i] = new String[]{mList.get(i).get("area_x") + "", mList.get(i).get("area_y") + ""};
//				}
				
				//查出来的结果是无序的，拍个序，不然会有问题
				Collections.sort(mList, new SortBySeq());
				
				StringBuffer sbfX = new StringBuffer();
				StringBuffer sbfY = new StringBuffer();
				for(Map<String,Object> map : mList){
					sbfX.append(map.get("area_x"));
					sbfX.append("&");
					sbfY.append(map.get("area_y"));
					sbfY.append("&");
				}
				
				if(sbfX.length() > 0){
					asi.setX(sbfX.deleteCharAt(sbfX.length()-1).toString());
					asi.setY(sbfY.deleteCharAt(sbfY.length()-1).toString());
				}
//				asi.setPath(path);
				stationList.add(asi);
			}
		}else{
			// 查询指定id线路中的所有普通站点
			List<AppStationInfoEntity> stationList1 = findHql(ssql, "0");
			stationList.addAll(stationList1);
		}
		
		// 常用站点列表
		// List<CustomerCommonAddrEntity> c =
		// systemService.findHql("from CustomerCommonAddrEntity where
		// user_id=? ", userId);

		// userId如果为空的话就不进行查找
		if (StringUtil.isNotEmpty(userId) && !StringUtil.isNotEmpty(likeStation)) {
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

	}

	@Override
	public List<AppUserOrderEntity> getUserOrders(String userId, String orderStatus, String pageNo,
			String maxPageItem) {

		List<Map<String, Object>> list = null;
		List<AppUserOrderEntity> auoList = new ArrayList<>();

		StringBuffer sql = new StringBuffer();
		sql.append(
				"select a.id,a.order_status,order_id,a.order_starting_station_name,a.order_terminus_station_name,a.order_totalPrice,a.order_numbers,a.order_startime "
						+ " from transferorder a left join order_linecardiver b on a.id = b.id where user_id=? "
						+ "and delete_flag='0' ORDER BY INSTR('2,1,3,4,6,5,0',order_status),a.order_startime asc ");

		// if (StringUtil.isNotEmpty(orderStatus)) {
		// sql.append(" and order_paystatus = '" + orderStatus + "'");
		// }

		list = findForJdbcParam(sql.toString(), Integer.parseInt(pageNo), Integer.parseInt(maxPageItem), userId);

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
	public AppUserOrderDetailEntity getOrderDetailById(String orderId, String userType) {

		List<Map<String, Object>> list = null;
		AppUserOrderDetailEntity aod = new AppUserOrderDetailEntity();

		// 为了通用这个详情页，把几个订单详情页的属性都拿出来了
		list = findForJdbc(
				"select a.id,a.order_type,a.order_status,a.order_id,a.order_starting_station_name,a.order_expectedarrival, "
						+ "	a.order_terminus_station_name,a.order_totalPrice,a.order_numbers,a.order_startime,a.order_contactsname,a.order_contactsmobile,a.remark, "
						+ " a.applicationTime,c.licence_plate,c.car_type,d.name as driver_name,d.phoneNumber as driver_phone,l.lineTimes,u.mobilePhone,u.officePhone "
						+ " from transferorder a left join order_linecardiver b on a.id = b .id left join car_info c on b.licencePlateId =c.id "
						+ " left join driversinfo d on b.driverId = d.id left join lineInfo l on a.line_id = l.id LEFT JOIN t_s_user u on l.createUserId=u.ID where a.id=? ",
				orderId);

		for (Map<String, Object> map : list) {
			// 会出现空指针么...
			String date = map.get("order_startime") + "";
			Date date1 = (Date) map.get("applicationTime");

			aod.setId(map.get("id") + "");
			aod.setOrderType(map.get("order_type") + "");
			aod.setOrderStatus(map.get("order_status") + "");
			aod.setOrderId(map.get("order_id") + "");
			aod.setOrderStartingStationName(map.get("order_starting_station_name") + "");
			aod.setOrderTerminusStationName(map.get("order_terminus_station_name") + "");
			aod.setOrderTotalPrice(map.get("order_totalPrice") + "");
			aod.setOrderNumbers(map.get("order_numbers") + "");

			aod.setOrderStartime(date);
			aod.setApplicationTime(DateUtils.date2Str(date1, DateUtils.datetimeFormat));

			aod.setOrderContactsname(map.get("order_contactsname") + "");
			aod.setOrderContactsmobile(map.get("order_contactsmobile") + "");
			aod.setLicencePlate(AppUtil.Null2Blank(map.get("licence_plate") + ""));
			aod.setCarType(AppUtil.Null2Blank(map.get("car_type") + ""));
			
			aod.setDriver(AppUtil.Null2Blank(map.get("driver_name") + ""));
			
			aod.setRemark(AppUtil.Null2Blank(map.get("remark") + ""));
			
			String officePhone = AppUtil.Null2Blank(map.get("officePhone") + "");
			
			if(StringUtil.isNotEmpty(userType) && userType.equals("1")){
				aod.setDriverPhone(AppUtil.Null2Blank(map.get("driver_phone") + ""));
			}else{
				if(StringUtil.isNotEmpty(officePhone)){
					aod.setDriverPhone(officePhone);
				}else{
					aod.setDriverPhone(AppUtil.Null2Blank(map.get("mobilePhone") + ""));
				}
			}
			
			// 发车时间
			aod.setStationStartTime(date.substring(date.indexOf(":")-2, date.lastIndexOf(":")));

			// // 线路时长
			// String lineTime = map.get("lineTimes") + "";
			// // 在发车时间的基础上加上线路所用时长
			// if (StringUtil.isNotEmpty(lineTime)) {
			// double lt = Double.parseDouble(lineTime);
			//
			// long a = (long) (date.getTime() + (lt * 60 * 1000));
			//
			// aod.setStationEndTime(DateUtils.date2Str(new Date(a),
			// DateUtils.short_time_sdf));
			// } else {
			// aod.setStationEndTime("");
			// }

			String lineTime = map.get("order_expectedarrival") + "";
			if (StringUtil.isNotEmpty(lineTime)) {
				aod.setStationEndTime(lineTime.substring(lineTime.indexOf(" "), lineTime.lastIndexOf(":")));
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

		list = findForJdbcParam(sql.toString(), Integer.parseInt(pageNo), Integer.parseInt(maxPageItem), userId);

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

		lm = findForJdbcParam("select * from customer_message where user_id = ? order by create_time desc  ",
				Integer.parseInt(pageNo), Integer.parseInt(maxPageItem), userId);

		for (Map<String, Object> m : lm) {
			AppMessageListEntity am = new AppMessageListEntity();
			am.setContent(m.get("content") + "");
			am.setTitle(m.get("title") + "");
			am.setCreateTime(m.get("create_time") + "");
			am.setId(m.get("id") + "");
			am.setMsgType(m.get("msg_type") + "");
			am.setOrderId(m.get("order_id") + "");
			am.setStatus(m.get("status") + "");
			am.setUserId(m.get("user_id") + "");
			mList.add(am);
		}

		// 进入消息中心后就将所有的消息改为已读 （改为点击消息的时候改为已读）
		// String sql = "update customer_message set status=1 where user_id=? ";
		// executeSql(sql, userId);

		return mList;
	}

	@Override
	public String getCarTypePrice(String sumPeople, String lineId, String phone, BigDecimal discount) {
		
		String tPrice = "";
		
		List<Map<String,Object>> lm = systemService.findForJdbc(" select c.car_type_price,t.typename "
				+ "from car_t_s_type_line c join t_s_type t on t.id = c.car_type_id where c.line_id = ? ", lineId);
		
		for(Map<String,Object> map : lm){
			String p = AppUtil.Null2Blank(map.get("typename") + "");
			
			if(!StringUtil.isNotEmpty(p))
				continue;
			
			//切字符串做比较
			int start = p.indexOf("-");
			int end = p.lastIndexOf("座");
			if(-1 == start){
				start = 0;
			}else{
				start += 1;
			}
			String maxNum = p.substring(start, end);
			
			int sp = Integer.parseInt(sumPeople);
			int mn = Integer.parseInt(maxNum) - 1;
			
			if(sp <= mn){
				tPrice = map.get("car_type_price") + "";
				double dis = discount.doubleValue();
				dis = dis/10;
				double tp = Double.parseDouble(tPrice);
				tPrice = String.valueOf(tp * dis);
//				tPrice = discount.divide(new BigDecimal("10"), 2, BigDecimal.ROUND_UP).multiply(new BigDecimal(tPrice)).toString();
				break;
			}
		}
		return tPrice;
	}

}

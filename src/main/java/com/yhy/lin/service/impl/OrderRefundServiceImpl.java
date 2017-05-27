package com.yhy.lin.service.impl;

import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.dao.jdbc.JdbcDao;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl.Db2Page;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yhy.lin.entity.TransferorderEntity;
import com.yhy.lin.service.OrderRefundServiceI;

import net.sf.json.JSONObject;

@Service("OrderRefundServiceI")
@Transactional
public class OrderRefundServiceImpl extends CommonServiceImpl implements OrderRefundServiceI {
	@Autowired
	private JdbcDao jdbcDao;

	@Override
	public JSONObject getDatagrid(TransferorderEntity transferorder, DataGrid dataGrid, String fc_begin, String fc_end,
			String ddTime_begin, String ddTime_end) {
		String sqlWhere = getWhere(transferorder, fc_begin, fc_end, ddTime_begin, ddTime_end);

		StringBuffer sql = new StringBuffer();

		String sqlCnt = "select count(*) from transferorder a left join order_linecardiver b on a.id = b .id left join car_info c on b.licencePlateId =c.id "
				+ " left join driversinfo d on b.driverId =d.id "
				+ " left join lineinfo l on l.id = a.line_id left join t_s_depart t on t.id = l.departId ";
		if (!sqlWhere.isEmpty()) {
			sqlCnt += sqlWhere;
		}
		// 取出总数据条数（为了分页处理, 如果不用分页，取iCount值的这个处理可以不要）
		Long iCount = getCountForJdbcParam(sqlCnt, null);
		sql.append("select a.city_name,t.org_code,a.id,a.order_id,a.order_status,a.order_starting_station_name,a.order_terminus_station_name,");
		sql.append("a.order_startime,a.order_numbers,a.order_paytype,a.order_contactsname,a.reject_reason,");
		sql.append("a.order_contactsmobile,a.order_paystatus,a.order_totalPrice,d.name,d.phoneNumber,a.applicationTime ");
		sql.append(" from transferorder a left join order_linecardiver b on a.id = b .id left join car_info c on b.licencePlateId =c.id "
				+ "	left join driversinfo d on b.driverId =d.id left join lineinfo l on l.id = a.line_id left join t_s_depart t on t.id = l.departId ");
		if (!sqlWhere.isEmpty()) {
			sql.append(sqlWhere);
		}

		System.out.println(sql.toString());
		List<Map<String, Object>> mapList = findForJdbc(sql.toString(), dataGrid.getPage(), dataGrid.getRows());
		// 将结果集转换成页面上对应的数据集
		Db2Page[] db2Pages = { new Db2Page("id"), new Db2Page("orderId", "order_id", null),
				new Db2Page("orderStatus", "order_status", null), new Db2Page("orderStartime", "order_startime", null),
				new Db2Page("orderNumbers", "order_numbers", null), new Db2Page("orderPaytype", "order_paytype", null),
				new Db2Page("orderContactsname", "order_contactsname", null),
				new Db2Page("orderContactsmobile", "order_contactsmobile", null),
				new Db2Page("orderPaystatus", "order_paystatus", null),
				new Db2Page("orderTotalPrice", "order_totalPrice", null), new Db2Page("name", "name", null),
				new Db2Page("phoneNumber", "phoneNumber", null),
				new Db2Page("applicationTime", "applicationTime", null),
				new Db2Page("orderStartingstation", "order_starting_station_name", null),
				new Db2Page("orderTerminusstation", "order_terminus_station_name", null),
				new Db2Page("refundTime", "refund_time", null),
				new Db2Page("rejectReason", "reject_reason", null),
				new Db2Page("cityName", "city_name", null)

		};
		JSONObject jObject = getJsonDatagridEasyUI(mapList, iCount.intValue(), db2Pages);
		return jObject;
	}

	public String getWhere(TransferorderEntity transferorder, String fc_begin, String fc_end, String ddTime_begin,
			String ddTime_end) {

		String orgCode = ResourceUtil.getSessionUserName().getCurrentDepart().getOrgCode();
		// 添加了权限
		StringBuffer sql = new StringBuffer(" where 1=1 and t.org_code like '" + orgCode + "%' ");

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
		// 订单类型
		if (StringUtil.isNotEmpty(transferorder.getOrderType())) {
			sql.append(" and  a.order_type ='" + transferorder.getOrderType() + "'");
		}
		// 订单状态
		if (StringUtil.isNotEmpty(transferorder.getOrderStatus())) {
			sql.append(" and  a.order_status ='" + transferorder.getOrderStatus() + "'");
		}
		// 起点站id
		if (StringUtil.isNotEmpty(transferorder.getOrderStartingStationId())) {
			sql.append(" and  a.order_starting_station_id = '" + transferorder.getOrderStartingStationId() + "'");
		}
		// 终点站id
		if (StringUtil.isNotEmpty(transferorder.getOrderTerminusStationId())) {
			sql.append(" and  a.order_terminus_station_id = '" + transferorder.getOrderTerminusStationId() + "'");
		}

		// 申请人
		if (StringUtil.isNotEmpty(transferorder.getOrderContactsname())) {
			sql.append(" and  a.order_contactsname like '%" + transferorder.getOrderContactsname() + "%'");
		}

		sql.append(" and order_status in('3','4','5') ");

		sql.append(" order by a.order_startime asc ");

		return sql.toString();
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED
	// noRollbackFor={UserAccountException.class},
	// readOnly=true//, timeout=30 //timeout 允许在执行第一条sql之后保持连接30秒
	)
	@Override
	public boolean agreeRefund(String id) {

		boolean success = false;

		try {
			TransferorderEntity t = getEntity(TransferorderEntity.class, id);
			t.setOrderStatus(4);
			t.setOrderPaystatus("2");
			updateEntitie(t);
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return success;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
	@Override
	public boolean rejectRefund(String id, String rejectReason) {
		boolean success = false;
		
		try {
			TransferorderEntity t = getEntity(TransferorderEntity.class, id);
			t.setOrderStatus(5);
			t.setOrderPaystatus("4");
			t.setRejectReason(rejectReason);
			updateEntitie(t);
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return success;
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
	// @Transactional(propagation=Propagation.REQUIRES_NEW,
	// isolation=Isolation.READ_COMMITTED,
	//// noRollbackFor={UserAccountException.class},
	// readOnly=true//, timeout=30 //timeout 允许在执行第一条sql之后保持连接30秒
	// )

}

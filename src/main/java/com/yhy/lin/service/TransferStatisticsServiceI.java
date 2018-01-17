package com.yhy.lin.service;

import java.util.List;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;

import com.yhy.lin.app.entity.CarCustomerEntity;
import com.yhy.lin.entity.TransferorderEntity;

import net.sf.json.JSONObject;

public interface TransferStatisticsServiceI extends CommonService {

	public JSONObject getUserDatagrid(CarCustomerEntity carcustomer, DataGrid dataGrid, String fc_begin,
			String fc_end,String userType,String phone);

	public JSONObject getOrderDatagrid(TransferorderEntity transferorder, DataGrid dataGrid, String orderId,String orderStatus,String lineName,
			String orderType,String driverId, String fc_begin, String fc_end,String departname);

	public JSONObject getrefundDatagrid(TransferorderEntity transferorder, DataGrid dataGrid,String orderId,
			String orderStartingstation, String orderTerminusstation, String lineName, String orderType,String departname);

	public String getWhere2(String OrderId,String OrderStatus,String lineName, String orderType, String driverName, String fc_begin,
			String fc_end,String departname);

	public String getWhere1(String orderId,String orderStartingstation, String orderTerminusstation, String lineName,
			String orderType,String departname);
	public String getWhere(CarCustomerEntity carcustomer,String fc_begin,String fc_end,String userType,String phone);
	
}

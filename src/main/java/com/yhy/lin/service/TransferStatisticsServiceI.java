package com.yhy.lin.service;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;

import com.yhy.lin.app.entity.CarCustomerEntity;
import com.yhy.lin.entity.TransferorderEntity;

import net.sf.json.JSONObject;

public interface TransferStatisticsServiceI extends CommonService {

	public JSONObject getUserDatagrid(CarCustomerEntity carcustomer, DataGrid dataGrid, String fc_begin,
			String fc_end);

	public JSONObject getOrderDatagrid(TransferorderEntity transferorder, DataGrid dataGrid, String orderId,String orderStatus,String lineName,
			String orderType,String driverName, String fc_begin, String fc_end);

	public JSONObject getrefundDatagrid(TransferorderEntity transferorder, DataGrid dataGrid,String orderId,
			String orderStartingstation, String orderTerminusstation, String lineName, String orderType);

	public String getWhere2(String OrderId,String OrderStatus,String lineName, String orderType, String driverName, String fc_begin,
			String fc_end);

	public String getWhere1(String orderId,String orderStartingstation, String orderTerminusstation, String lineName,
			String orderType);
	
}

package com.yhy.lin.service;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;

import com.yhy.lin.app.entity.CarCustomerEntity;
import com.yhy.lin.entity.TransferorderEntity;

import net.sf.json.JSONObject;

/**
* Description : 
* @author Administrator
* @date 2017年7月8日 下午6:09:39
*/
public interface DealerStatisticsServiceI extends CommonService {

	public JSONObject getDealerUserDatagrid(CarCustomerEntity carcustomer, DataGrid dataGrid,String account, String fc_begin,
			String fc_end);
	
	public JSONObject getDealerOrderDatagrid(TransferorderEntity transferorder, DataGrid dataGrid, String lineName,
			String orderType,String account, String fc_begin, String fc_end);

	public String getWhere4(String lineName, String orderType, String account, String fc_begin,String fc_end);
	
	public String getWhere(String account, String fc_begin,String fc_end);
	
}

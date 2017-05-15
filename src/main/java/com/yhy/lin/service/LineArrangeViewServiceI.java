package com.yhy.lin.service;


import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;

import net.sf.json.JSONObject;

public interface LineArrangeViewServiceI extends CommonService{

	JSONObject getDatagrid(DataGrid dataGrid);

	//根据线路id获取售票总和
	String getSaleTicketCount(String lineId);

}

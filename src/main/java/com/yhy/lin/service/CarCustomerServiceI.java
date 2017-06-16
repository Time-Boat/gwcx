package com.yhy.lin.service;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;

import net.sf.json.JSONObject;

public interface CarCustomerServiceI extends CommonService{

	public JSONObject getDatagrid(String id, DataGrid dataGrid);
}

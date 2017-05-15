package com.yhy.lin.service;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;

import net.sf.json.JSONObject;

public interface EnterCompanyInfoServiceI extends CommonService{

	public JSONObject getDatagrid(String departname, String startTime, String endTime, String orgCode, DataGrid dataGrid);
	
}

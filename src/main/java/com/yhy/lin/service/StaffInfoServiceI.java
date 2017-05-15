package com.yhy.lin.service;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;

import net.sf.json.JSONObject;

public interface StaffInfoServiceI extends CommonService{

	JSONObject getDatagrid(String name, String orgCode, String companyId, String phone, DataGrid dataGrid);


}

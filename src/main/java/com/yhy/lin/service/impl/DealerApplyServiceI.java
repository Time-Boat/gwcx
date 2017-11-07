package com.yhy.lin.service.impl;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;

import net.sf.json.JSONObject;

public interface DealerApplyServiceI extends CommonService{

	JSONObject getDatagrid(DataGrid dataGrid, String companyName, String phone, String applyPeople);

	JSONObject getAttacheDatagrid(DataGrid dataGrid, String role);

}

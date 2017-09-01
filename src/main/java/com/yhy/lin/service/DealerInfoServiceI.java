package com.yhy.lin.service;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;

import net.sf.json.JSONObject;

public interface DealerInfoServiceI extends CommonService{

	JSONObject getDatagrid(DataGrid dataGrid, String status, String auditStatus);

}

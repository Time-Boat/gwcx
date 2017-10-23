package com.yhy.lin.service;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;

import com.yhy.lin.entity.CharteredAppendServiceEntity;

import net.sf.json.JSONObject;

public interface CharteredAppendServiceServiceI extends CommonService{
	
	public JSONObject getDatagrid(DataGrid dataGrid,CharteredAppendServiceEntity CharteredAppendService);

}

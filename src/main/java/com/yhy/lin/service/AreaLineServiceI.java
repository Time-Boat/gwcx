package com.yhy.lin.service;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;

import com.yhy.lin.entity.AreaStationEntity;
import com.yhy.lin.entity.AreaStationLineEntity;

import net.sf.json.JSONObject;

public interface AreaLineServiceI extends CommonService{

	JSONObject getDatagrid(DataGrid dataGrid);

	JSONObject getAreaStationDatagrid(DataGrid dataGrid, String areaLineId);

}

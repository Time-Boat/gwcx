package com.yhy.lin.service;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;

import com.yhy.lin.entity.BusStopInfoEntity;

import net.sf.json.JSONObject;

public interface BusStopInfoServiceI extends CommonService{
	//根据线路lineInfoId，拿到对应线路的挂接站点信息
	public JSONObject getDatagrid3(String lineInfoId, DataGrid dataGrid);
	//根据线路lineInfoId，拿到对应线路的未挂接的站点信息
	public JSONObject getDatagrid3a(BusStopInfoEntity busStopInfo,String lineInfoId, DataGrid dataGrid, String lineType,String cityId);
	
	public JSONObject getDatagrid(BusStopInfoEntity busStopInfo, DataGrid dataGrid);
}

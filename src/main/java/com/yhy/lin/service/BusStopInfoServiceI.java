package com.yhy.lin.service;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;

import com.yhy.lin.entity.BusStopInfoEntity;
import com.yhy.lin.entity.LineInfoEntity;
import com.yhy.lin.entity.LineinfoHistoryEntity;

import net.sf.json.JSONObject;

public interface BusStopInfoServiceI extends CommonService{
	//根据线路lineInfoId，拿到对应线路的挂接站点信息
	public JSONObject getDatagrid3(String lineInfoId, DataGrid dataGrid);
	//根据线路lineInfoId，拿到对应线路的未挂接的站点信息
	public JSONObject getDatagrid3a(BusStopInfoEntity busStopInfo, DataGrid dataGrid,LineInfoEntity lineInfo);
	
	public JSONObject getDatagrid3b(BusStopInfoEntity busStopInfo, DataGrid dataGrid,LineinfoHistoryEntity lineInfo);
	//根据城市查找站点
	public JSONObject getLineDataGrid(String city,DataGrid dataGrid);
	
	public JSONObject getDatagrid(BusStopInfoEntity busStopInfo, DataGrid dataGrid,String cityID,String createTime_begin,String createTime_end);
	//根据线路lineInfoId，拿到对应线路的挂接站点信息
	public JSONObject getDatagrid4(String lineInfoId, DataGrid dataGrid);
}

package com.yhy.lin.service;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;

import com.yhy.lin.entity.LineInfoEntity;

import net.sf.json.JSONObject;

public interface LineInfoServiceI extends CommonService{
	public JSONObject getDatagrid(LineInfoEntity lineInfo,String startTime ,String endTime ,DataGrid dataGrid,String lstartTime_begin,String lstartTime_end,String lendTime_begin,String lendTime_end,String lineType);
	public JSONObject getDatagrid2(LineInfoEntity lineInfo,DataGrid dataGrid, String ywlx);
	
	public JSONObject getDatagrid3(LineInfoEntity lineInfo,String cityid,String startTime ,String endTime ,DataGrid dataGrid,String lstartTime_begin,String lstartTime_end,String lendTime_begin,String lendTime_end,String lineType,String username,String departname);
	
	public JSONObject getDatagrid4(LineInfoEntity lineInfo,DataGrid dataGrid);
}

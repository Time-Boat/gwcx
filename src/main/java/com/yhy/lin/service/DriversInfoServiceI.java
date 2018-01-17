package com.yhy.lin.service;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;

import com.yhy.lin.entity.DriversInfoEntity;

import net.sf.json.JSONObject;

public interface DriversInfoServiceI extends CommonService{

	JSONObject getDatagrid(DataGrid dataGrid, DriversInfoEntity driversInfo,String cityID,String driverId);
	
	JSONObject getDatagrid1(DataGrid dataGrid, String sex, String name, String phoneNumber,String status,String cityID, String fromPage);

}

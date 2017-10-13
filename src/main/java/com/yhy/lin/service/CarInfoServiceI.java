package com.yhy.lin.service;

import java.util.List;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;

import com.yhy.lin.entity.DriversInfoEntity;

import net.sf.json.JSONObject;

public interface CarInfoServiceI extends CommonService{

	JSONObject getDatagrid(DataGrid dataGrid, String userCar, String lpId, String licencePlate,
			String carType, String status, String businessType,String carAndDriver, String carStatus, String auditStatus);

	List<DriversInfoEntity> getDriverList(String driverId);

	String getWhere(String lpId, String userCar, String licencePlate, String carType, String status,
			String businessType, String carStatus, String auditStatus);

}

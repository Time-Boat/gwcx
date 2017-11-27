package com.yhy.lin.service;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;

import com.yhy.lin.entity.NotificationModelEntity;

import net.sf.json.JSONObject;

public interface NotificationModelServiceI extends CommonService{

	JSONObject getDatagrid(DataGrid dataGrid, NotificationModelEntity notificationModel);

}

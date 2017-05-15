package com.yhy.lin.service;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;

import com.yhy.lin.entity.TransferorderEntity;
import com.yhy.lin.entity.TransferorderView;

import net.sf.json.JSONObject;

public interface TransferServiceI extends CommonService{
	public JSONObject getDatagrid(TransferorderEntity transferorder,DataGrid dataGrid,String fc_begin,String fc_end,String ddTime_begin,String ddTime_end);
	
	//根据id查询接送机的详细信息
	public TransferorderView getDetail(String id);
}

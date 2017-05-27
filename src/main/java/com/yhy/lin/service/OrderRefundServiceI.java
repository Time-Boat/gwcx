package com.yhy.lin.service;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;

import com.yhy.lin.entity.TransferorderEntity;

import net.sf.json.JSONObject;

public interface OrderRefundServiceI extends CommonService{
	
	public JSONObject getDatagrid(TransferorderEntity transferorder,DataGrid dataGrid,String fc_begin,String fc_end,String ddTime_begin,String ddTime_end);

	public boolean agreeRefund(String id);

	public boolean rejectRefund(String id, String rejectReason);
	
}

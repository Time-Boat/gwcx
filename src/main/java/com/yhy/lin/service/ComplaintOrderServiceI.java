package com.yhy.lin.service;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;

import com.yhy.lin.entity.ComplaintOrderView;
import com.yhy.lin.entity.ComplaintOrderEntity;

import net.sf.json.JSONObject;

public interface ComplaintOrderServiceI extends CommonService{
	
	// 根据id查询接送机的详细信息
	public ComplaintOrderView getDetail(String id);
	
	public JSONObject getComplaintDatagrid(ComplaintOrderEntity complaintOrder, DataGrid dataGrid,String userType, String complaintTime_begin, String complaintTime_end,String orderContactsname, String orderContactsmobile, String orderStatus);

}

package com.yhy.lin.service;

import java.util.Map;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;

import com.yhy.lin.app.wechat.RequestHandler;
import com.yhy.lin.entity.TransferorderEntity;

import net.sf.json.JSONObject;

public interface OrderRefundServiceI extends CommonService{
	
	public JSONObject getDatagrid(TransferorderEntity transferorder,DataGrid dataGrid,String fc_begin,String fc_end,String rf_begin,String rf_end,String orderStartingstation,String orderTerminusstation);

	public boolean agreeRefund(String id);

	public boolean rejectRefund(String id, String rejectReason);

	public Map<String,String> firstAgreeAllRefund(String[] arrId, String fees, RequestHandler refundRequest, String path);

}

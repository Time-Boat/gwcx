package com.yhy.lin.service;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;

import com.yhy.lin.entity.DealerInfoEntity;

import net.sf.json.JSONObject;

public interface DealerInfoServiceI extends CommonService{

	JSONObject getDatagrid(DataGrid dataGrid, DealerInfoEntity dealerInfo,String username, String departname);

	String getWhere(DealerInfoEntity dealerInfo,String username, String departname);

	//同意审核之后的操作
	void agreeAudit(String id);
}

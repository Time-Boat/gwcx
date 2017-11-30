package com.yhy.lin.service;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;

import com.yhy.lin.entity.CharteredOrderEntity;

import net.sf.json.JSONObject;

public interface CharteredOrderServiceI extends CommonService{
	
	public JSONObject getDatagrid(CharteredOrderEntity charteredOrder,DataGrid dataGrid,String o_begin,String o_end,String username);

}

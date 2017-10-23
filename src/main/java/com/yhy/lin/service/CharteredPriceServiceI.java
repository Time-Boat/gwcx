package com.yhy.lin.service;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;

import com.yhy.lin.entity.CharteredPriceEntity;

import net.sf.json.JSONObject;

public interface CharteredPriceServiceI extends CommonService{
	
	public JSONObject getDatagrid(DataGrid dataGrid,CharteredPriceEntity charteredPrice,String cityID);

}

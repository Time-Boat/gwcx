package com.yhy.lin.service;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;

import com.yhy.lin.entity.ConductorEntity;

import net.sf.json.JSONObject;

public interface ConductorServiceI extends CommonService{
	
	JSONObject getDatagrid(DataGrid dataGrid,ConductorEntity conductors,String cr_bg,String cr_en,String lineId);

}

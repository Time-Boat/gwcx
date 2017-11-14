package com.yhy.lin.service;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;

import com.yhy.lin.entity.LineinfoHistoryEntity;

import net.sf.json.JSONObject;

public interface LineinfoHistoryServiceI extends CommonService{

	public JSONObject getDatagrid(LineinfoHistoryEntity lineInfo,String cityid,String startTime ,String endTime ,
			DataGrid dataGrid,String lstartTime_begin,String lstartTime_end,String lendTime_begin,String lendTime_end,
			String lineType,String username,String departname, String company);
	public String getSqlWhere(LineinfoHistoryEntity lineInfo,String cityid,String startTime,
			String endTime,String lstartTime_begin,String lstartTime_end,
			String lendTime_begin,String lendTime_end,String lineType,String username,String departname,String company);
}

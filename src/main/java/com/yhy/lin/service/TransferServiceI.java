package com.yhy.lin.service;

import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;

import com.yhy.lin.entity.TransferorderEntity;
import com.yhy.lin.entity.TransferorderView;

import net.sf.json.JSONObject;

public interface TransferServiceI extends CommonService {
	public JSONObject getDatagrid(TransferorderEntity transferorder, DataGrid dataGrid,String lineOrderCode,String orderStartingstation,String orderTerminusstation,String lineId, String driverId,String carId,String fc_begin,
			String fc_end,String ddTime_begin, String ddTime_end);
	public JSONObject getDatagrid1(TransferorderEntity transferorder, DataGrid dataGrid,String lineOrderCode,String orderStartingstation,String orderTerminusstation,String lineId, String driverId,String carId,String fc_begin,
			String fc_end,String ddTime_begin, String ddTime_end);
	public JSONObject getDatagrid2(TransferorderEntity transferorder, DataGrid dataGrid,String lineOrderCode,String orderStartingstation,String orderTerminusstation,String lineId, String driverId,String carId,String fc_begin,
			String fc_end,String ddTime_begin, String ddTime_end);
	public JSONObject getDatagrid3(TransferorderEntity transferorder, DataGrid dataGrid,String cityid,String lineId,String lineOrderCode,String lineType);

	// 根据id查询接送机的详细信息
	public TransferorderView getDetail(String id);

	public boolean saveDriverAndDriver(List<String> orderIds, String startTime, String licencePlateId, String driverId,
			String licencePlateName, List<String[]> contents);

}

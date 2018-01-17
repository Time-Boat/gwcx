package com.yhy.lin.service;

import java.util.List;

import org.jeecgframework.core.common.service.CommonService;

import com.yhy.lin.entity.DealerInfoEntity;
import com.yhy.lin.entity.DriversInfoEntity;
import com.yhy.lin.entity.LineInfoEntity;

import net.sf.json.JSONArray;

public interface SharingInfoServiceI extends CommonService {

	
	public String getOpencity();
	
	public String getCarPlate();
	
	public String getDriver();
	
	public List<DriversInfoEntity> getDriverInfo();
	
	public String getAccount();
	
	public List<DealerInfoEntity> getDealerinfo();
	
	public String getCompany();
	
	public String getStartStaion(String lineId,String lineType,String userType);
	
	public String getEndStaion(String lineId,String lineType,String userType);
	
	public String getCreatePeople();
	
	public String getLine(String ordertype,String userType);
	
	public List<LineInfoEntity> getLineinfo(String ordertype,String userType);
	
	public String getOrderStatisticsChart(String userType,String dealer,String startDate,String endDate,String timestamp);
	
	public String getDriverOrderStatisticsChart(String driver, String startDate, String endDate,String timestamp);

	public String getLineOrderStatisticsChart(String lineType, String lineId,String startDate, String endDate,
			String timestamp) ;

}

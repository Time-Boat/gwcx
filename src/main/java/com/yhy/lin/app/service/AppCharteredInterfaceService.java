package com.yhy.lin.app.service;

import java.util.List;

import org.jeecgframework.core.common.service.CommonService;

import com.yhy.lin.app.entity.AppCharteredPackageEntity;
import com.yhy.lin.app.entity.AppCharteredPriceEntity;


/**
* Description : 
* @author Administrator
* @date 2017年6月1日 下午4:37:35
*/
public interface AppCharteredInterfaceService extends CommonService{

	List<AppCharteredPriceEntity> getPackagePrice(String packageId, String carType, String charteredType,String actualMileage);
	
	List<AppCharteredPackageEntity> getChargesDetails(String packageId);
}

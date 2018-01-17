package com.yhy.lin.app.entity;

import java.util.List;

/**
* Description : 
* @author Administrator
* @date 2017年5月24日 下午5:20:44
*/
public class AppCharteredaCarTypeEntity {
	
	private List<AppCharteredPackagePriceEntity> packageDetails;

	private String charteredName;

	public List<AppCharteredPackagePriceEntity> getPackageDetails() {
		return packageDetails;
	}

	public void setPackageDetails(List<AppCharteredPackagePriceEntity> packageDetails) {
		this.packageDetails = packageDetails;
	}

	public String getCharteredName() {
		return charteredName;
	}

	public void setCharteredName(String charteredName) {
		this.charteredName = charteredName;
	}
	
	
}

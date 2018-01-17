package com.yhy.lin.app.entity;

import java.util.List;

/**
* Description : 
* @author Administrator
* @date 2017年5月24日 下午5:20:44
*/
public class AppCharteredPackageEntity {
	
	private List<AppCharteredaCarTypeEntity> charteredPackages;

	private String companyName;

	public List<AppCharteredaCarTypeEntity> getCharteredPackages() {
		return charteredPackages;
	}

	public void setCharteredPackages(List<AppCharteredaCarTypeEntity> charteredPackages) {
		this.charteredPackages = charteredPackages;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

}

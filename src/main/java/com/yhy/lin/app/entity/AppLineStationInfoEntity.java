package com.yhy.lin.app.entity;

import java.util.List;

/**
* Description : 
* @author Administrator
* @date 2017年5月17日 下午2:23:03
*/
public class AppLineStationInfoEntity {

	private String id;
	private String name;
	private String price;
	private String lineTimes;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getLineTimes() {
		return lineTimes;
	}

	public void setLineTimes(String lineTimes) {
		this.lineTimes = lineTimes;
	}

}

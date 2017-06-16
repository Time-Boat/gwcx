package com.yhy.lin.app.entity;

import java.io.Serializable;
import java.util.List;

/**
* Description : app站点信息
* @author Administrator
* @date 2017年5月17日 下午2:23:03
*/
public class AppLineStationInfoEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String name;
	private String price;
	private String lineTimes;
	private String dispath;

	public String getDispath() {
		return dispath;
	}

	public void setDispath(String dispath) {
		this.dispath = dispath;
	}

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

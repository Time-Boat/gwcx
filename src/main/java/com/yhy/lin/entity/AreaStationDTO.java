package com.yhy.lin.entity;

/**
* Description : 区域站点信息类
* @author Administrator
* @date 2017年12月7日 下午11:37:45
*/
public class AreaStationDTO {

	//站点名称
	private String name;
	//区域中每个点的xy
	private String[][] path;
	
	public String[][] getPath() {
		return path;
	}
	public void setPath(String[][] path) {
		this.path = path;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}

package com.yhy.lin.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 站点信息基础表
 * @author linj
 *
 */
@Entity
@Table(name="app_station_info_view")
public class AppStationInfoEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	/**主键id*/
	private String id;
	/**站点名称*/
	private String name;
	/**站点的经度（用于后面地图实现）*/
	private String x;
	/**站点的纬度（用于后面地图实现）*/
	private String y;
	/**站点地址*/
	private String stopLocation;
	
	/**线路id*/
	private String lineId;
	
	@Column(name ="LINEID",nullable=true,length=50)
	public String getLineId() {
		return lineId;
	}
	public void setLineId(String lineId) {
		this.lineId = lineId;
	}
	
	//主键生成策略：uuid 采用128位的uuid算法生成主键，uuid被编码为一个32位16进制数字的字符串。占用空间大（字符串类型）。   
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="id",nullable=false,length=32)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(name ="name",nullable=true,length=50)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(name="x",nullable=true,length=100)
	public String getX() {
		return x;
	}
	public void setX(String x) {
		this.x = x;
	}
	@Column(name="y",nullable=true,length=100)
	public String getY() {
		return y;
	}
	public void setY(String y) {
		this.y = y;
	}
	
	@Column(name="stopLocation",nullable=true,length=200)
	public String getStopLocation() {
		return stopLocation;
	}
	public void setStopLocation(String stopLocation) {
		this.stopLocation = stopLocation;
	}
	
}

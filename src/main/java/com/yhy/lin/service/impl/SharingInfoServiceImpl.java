package com.yhy.lin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import com.yhy.lin.app.util.AppGlobals;
import com.yhy.lin.entity.BusStopInfoEntity;
import com.yhy.lin.entity.DealerInfoEntity;
import com.yhy.lin.entity.DriversInfoEntity;
import com.yhy.lin.entity.LineInfoEntity;
import com.yhy.lin.service.SharingInfoServiceI;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.web.system.service.UserService;

@Service("sharingInfoService")
@Transactional
public class SharingInfoServiceImpl extends CommonServiceImpl implements SharingInfoServiceI {
	
	@Autowired
	private SystemService systemService;
	
	@Autowired
	private UserService userService;
	
	/**
	 * 获得开通城市
	 * @return
	 */
	public String getOpencity(){
		String sql = "select op.city_id,op.city_name from open_city op where op.status='0' ";
		List<Object> list = this.systemService.findListbySql(sql);
		StringBuffer json = new StringBuffer("{'data':[");
		if(list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				Object[] ob = (Object[]) list.get(i);
				String id = ob[0]+"";
				String cityName = ob[1]+"";
					json.append("{");
					json.append("'cityID':'" +id + "',");
					json.append("'cityName':'"+ cityName + "'");
					json.append("},");
				}
			}
		json.delete(json.length()-1, json.length());
		json.append("]}");
		return json.toString();
	}
	
	/**
	 * 获得车牌号
	 * @return
	 */
	public String getCarPlate(){
		StringBuffer sql = new StringBuffer();
		
		sql.append("select c.id,c.licence_plate from car_info c LEFT JOIN t_s_depart t on c.departId=t.ID where c.delete_flag='0' ");
		TSDepart depart = ResourceUtil.getSessionUserName().getCurrentDepart();
		String orgCode = depart.getOrgCode();
		TSUser user = ResourceUtil.getSessionUserName();
		String userId = user.getId();
		String role = userService.getUserRole(user);
		String code = "";
		
		//判断当前的角色，如果是技术专员，只能看自己建的车辆信息，超级管理员不用过滤，其他的只看本公司的车辆信息
		if(!role.equals(AppGlobals.XM_ADMIN)){
			if(role.contains(AppGlobals.TECHNICAL_SPECIALIST)){
				sql.append(" and c.create_user_id = '" + userId + "' ");
			}else{
				if(orgCode.length()>=6){
					code = orgCode.substring(0,6);
					sql.append(" and t.org_code like '" + code + "%'");
				}
			}
		}
		
		List<Object> list = this.systemService.findListbySql(sql.toString());
		StringBuffer json = new StringBuffer("{'data':[");
		if(list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				Object[] ob = (Object[]) list.get(i);
				String id = ob[0]+"";
				String licencePlate = ob[1]+"";
					json.append("{");
					json.append("'carId':'" +id + "',");
					json.append("'licencePlate':'"+ licencePlate + "'");
					json.append("},");
				}
			}else{
				json.append("{");
				json.append("'carId':'',");
				json.append("'licencePlate':''");
				json.append("},");
			}
		json.delete(json.length()-1, json.length());
		json.append("]}");
		return json.toString();
	}
	
	/**
	 * 获取司机
	 * @return
	 */
	public String getDriver(){
		
		StringBuffer sql = new StringBuffer();
		sql.append("select d.id,d.name from driversinfo d LEFT JOIN t_s_depart t on d.departId=t.ID where d.deleteFlag='0'");
		
		TSDepart depart = ResourceUtil.getSessionUserName().getCurrentDepart();
		String orgCode = depart.getOrgCode();
		TSUser user = ResourceUtil.getSessionUserName();
		String userId = user.getId();
		String role = userService.getUserRole(user);
		String code = "";
		
		//判断当前的角色，如果是技术专员，只能看自己建的车辆信息，超级管理员不用过滤，其他的只看本公司的车辆信息
		if(!role.equals(AppGlobals.XM_ADMIN)){
			if(role.contains(AppGlobals.TECHNICAL_SPECIALIST)){
				sql.append(" and d.create_user_id = '" + userId + "' ");
			}else{
				if(orgCode.length()>=6){
					code = orgCode.substring(0,6);
					sql.append(" and t.org_code like '" + code + "%'");
				}
			}
		}
		
		List<Object> list = this.systemService.findListbySql(sql.toString());
		StringBuffer json = new StringBuffer("{'data':[");
		if(list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				Object[] ob = (Object[]) list.get(i);
				String id = ob[0]+"";
				String name = ob[1]+"";
					json.append("{");
					json.append("'driverId':'" +id + "',");
					json.append("'driverName':'"+ name + "'");
					json.append("},");
				}
			}else{
				json.append("{");
				json.append("'driverId':'',");
				json.append("'driverName':''");
				json.append("},");
			}
		json.delete(json.length()-1, json.length());
		json.append("]}");
		return json.toString();
	}
	
	
	/**
	 * 获取司机
	 * @return
	 */
	public List<DriversInfoEntity> getDriverInfo(){
		
		StringBuffer sql = new StringBuffer();
		sql.append("select d.id,d.name from driversinfo d LEFT JOIN t_s_depart t on d.departId=t.ID where d.deleteFlag='0'");
		
		TSDepart depart = ResourceUtil.getSessionUserName().getCurrentDepart();
		String orgCode = depart.getOrgCode();
		TSUser user = ResourceUtil.getSessionUserName();
		String userId = user.getId();
		String role = userService.getUserRole(user);
		String code = "";
		
		//判断当前的角色，如果是技术专员，只能看自己建的车辆信息，超级管理员不用过滤，其他的只看本公司的车辆信息
		if(!role.equals(AppGlobals.XM_ADMIN)){
			if(role.contains(AppGlobals.TECHNICAL_SPECIALIST)){
				sql.append(" and d.create_user_id = '" + userId + "' ");
			}else{
				if(orgCode.length()>=6){
					code = orgCode.substring(0,6);
					sql.append(" and t.org_code like '" + code + "%'");
				}
			}
		}
		
		List<Object> list = this.systemService.findListbySql(sql.toString());
		List<DriversInfoEntity> driverlist = new ArrayList<>();
		if(list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				Object[] ob = (Object[]) list.get(i);
				String id = ob[0]+"";
				String name = ob[1]+"";
				DriversInfoEntity driver = new DriversInfoEntity();
				if (StringUtil.isNotEmpty(id)) {
					driver.setId(id);
				}
				if (StringUtil.isNotEmpty(name)) {
					driver.setName(name);
				}
					driverlist.add(driver);
				}
			}
		
		return driverlist;
	}
	
	/**
	 * 获取渠道商用户信息
	 */
	public String getAccount(){
		StringBuffer sql = new StringBuffer();
		
		sql.append("select d.id,d.account from dealer_info d, t_s_depart td where d.status='0' ");
		
		TSDepart depart = ResourceUtil.getSessionUserName().getCurrentDepart();
		String orgCode = depart.getOrgCode();
		String orgType = depart.getOrgType();
		String userId = ResourceUtil.getSessionUserName().getId();
		
		//判断当前的机构类型，如果是"岗位"类型，就需要加个userId等于当前用户的条件，确保各个专员之间只能看到自己的数据
		if(AppGlobals.ORG_JOB_TYPE.equals(orgType)){
			sql.append(" and d.create_user_id = '" + userId + "' ");
		}
		sql.append(" and td.org_code like '" + orgCode + "%' and td.id=d.departId");
				
		List<Object> list = this.systemService.findListbySql(sql.toString());
		StringBuffer json = new StringBuffer("{'data':[");
		if(list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				Object[] ob = (Object[]) list.get(i);
				String id = ob[0]+"";
				String account = ob[1]+"";
					json.append("{");
					json.append("'id':'" +id + "',");
					json.append("'account':'"+ account + "'");
					json.append("},");
			}
		}
		json.delete(json.length()-1, json.length());
		json.append("]}");
		return json.toString();
	}
	
	
	/**
	 * 获取渠道商用户信息
	 */
	public List<DealerInfoEntity> getDealerinfo(){
		StringBuffer sql = new StringBuffer();
		
		sql.append("select d.id,d.account from dealer_info d, t_s_depart td where d.status='0' ");
		
		TSDepart depart = ResourceUtil.getSessionUserName().getCurrentDepart();
		String orgCode = depart.getOrgCode();
		String orgType = depart.getOrgType();
		String userId = ResourceUtil.getSessionUserName().getId();
		
		//判断当前的机构类型，如果是"岗位"类型，就需要加个userId等于当前用户的条件，确保各个专员之间只能看到自己的数据
		if(AppGlobals.ORG_JOB_TYPE.equals(orgType)){
			sql.append(" and d.create_user_id = '" + userId + "' ");
		}
		sql.append(" and td.org_code like '" + orgCode + "%' and td.id=d.departId");
				
		List<Object> list = this.systemService.findListbySql(sql.toString());
		List<DealerInfoEntity> dealerList = new ArrayList<DealerInfoEntity>();
		if(list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				Object[] ob = (Object[]) list.get(i);
				String id = ob[0]+"";
				String account = ob[1]+"";
				
				DealerInfoEntity dear = new DealerInfoEntity();
				dear.setId(id);
				dear.setAccount(account);
				dealerList.add(dear);
			}
		}
		
		return dealerList;
	}
	
	/**
	 * 获取渠道商用户信息
	 */
	public String getCompany(){
		
		TSDepart depart = ResourceUtil.getSessionUserName().getCurrentDepart();
		String orgCode = depart.getOrgCode();
		String code = "";
		List<TSDepart> list = new ArrayList<TSDepart>();
		if (StringUtil.isNotEmpty(orgCode)) {
			if(orgCode.length()>=6){
				code = orgCode.substring(0,6);
				list = systemService.findByProperty(TSDepart.class, "orgCode", code);
			}else{
				list = systemService.findByProperty(TSDepart.class, "orgType", "2");
			}
		}
				
		StringBuffer json = new StringBuffer("{'data':[");
		if(list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				TSDepart ob =  list.get(i);
				String id = ob.getId();
				String departname = ob.getDepartname();
					json.append("{");
					json.append("'companyId':'" +id + "',");
					json.append("'departname':'"+ departname + "'");
					json.append("},");
			}
		}
		json.delete(json.length()-1, json.length());
		json.append("]}");
		return json.toString();
	}
	
	/**
	 * 获取起点
	 */
	public String getStartStaion(String lineId,String lineType,String userType){
		
		/*TSDepart depart = ResourceUtil.getSessionUserName().getCurrentDepart();
		String orgCode = depart.getOrgCode();
		String orgType = depart.getOrgType();
		String userId = ResourceUtil.getSessionUserName().getId();*/
		String busid = "";
		String busName = "";
		
		StringBuffer json = new StringBuffer("{'data':[");
		
		if (StringUtil.isNotEmpty(lineId)) {
			LineInfoEntity lineinfo = systemService.getEntity(LineInfoEntity.class, lineId);
			 busid = lineinfo.getStartLocation();
			if (StringUtil.isNotEmpty(busid)) {
				BusStopInfoEntity bus =systemService.getEntity(BusStopInfoEntity.class, busid);
				busName = bus.getName();
			}
			json.append("{");
			json.append("'busid':'" +busid + "',");
			json.append("'busName':'"+ busName + "'");
			json.append("},");
			
		}else{
			StringBuffer sql = new StringBuffer();
			sql.append("select b.id,b.name from busstopinfo b LEFT JOIN t_s_base_user u on b.create_user_id=u.ID "
					+ "LEFT JOIN t_s_user_org g on u.ID=g.user_id LEFT JOIN t_s_depart t on t.ID=g.org_id where b.deleteFlag='0'");
			//判断当前的机构类型，如果是"岗位"类型，就需要加个userId等于当前用户的条件，确保各个专员之间只能看到自己的数据
			/*if(AppGlobals.ORG_JOB_TYPE.equals(orgType)){
				sql.append(" and b.create_user_id ='" + userId + "' ");
			}
			sql.append(" and t.org_code like '" + orgCode + "%'");*/
			if (StringUtil.isNotEmpty(lineType)) {
				if(lineType.equals("2")){
					sql.append(" and b.station_type='2'");
				}
				if(lineType.equals("4")){
					sql.append(" and b.station_type='1'");
				}
				if(lineType.equals("3") || lineType.equals("5")){
					sql.append(" and b.station_type!='1' and b.station_type!='2'");
				}
			}
			if ("1".equals(userType)) {
				sql.append(" and b.station_type!='0'");
			}
			if ("0".equals(userType)) {
				sql.append(" and b.station_type!='3'");
			}
			
			
			List<Object> list = this.systemService.findListbySql(sql.toString());
			if(list.size()>0){
				for (int i = 0; i < list.size(); i++) {
					Object[] ob = (Object[]) list.get(i);
					busid = ob[0]+"";
					busName= ob[1]+"";
					json.append("{");
					json.append("'busid':'" +busid + "',");
					json.append("'busName':'"+ busName + "'");
					json.append("},");
				}
			}
		}
		
		json.delete(json.length()-1, json.length());
		json.append("]}");
		return json.toString();
	}
	
	/**
	 * 获取终点站
	 */
	public String getEndStaion(String lineId,String lineType,String userType){
		
		/*TSDepart depart = ResourceUtil.getSessionUserName().getCurrentDepart();
		String orgCode = depart.getOrgCode();
		String orgType = depart.getOrgType();
		String userId = ResourceUtil.getSessionUserName().getId();*/
		String busid = "";
		String busName = "";
		
		StringBuffer json = new StringBuffer("{'data':[");
		
		if (StringUtil.isNotEmpty(lineId)) {
			LineInfoEntity lineinfo = systemService.getEntity(LineInfoEntity.class, lineId);
			 busid = lineinfo.getEndLocation();
			if (StringUtil.isNotEmpty(busid)) {
				BusStopInfoEntity bus =systemService.getEntity(BusStopInfoEntity.class, busid);
				busName = bus.getName();
			}
			json.append("{");
			json.append("'busid':'" +busid + "',");
			json.append("'busName':'"+ busName + "'");
			json.append("},");
			
		}else{
			StringBuffer sql = new StringBuffer();
			sql.append("select b.id,b.name from busstopinfo b LEFT JOIN t_s_base_user u on b.create_user_id=u.ID "
					+ "LEFT JOIN t_s_user_org g on u.ID=g.user_id LEFT JOIN t_s_depart t on t.ID=g.org_id where b.deleteFlag='0'");
			//判断当前的机构类型，如果是"岗位"类型，就需要加个userId等于当前用户的条件，确保各个专员之间只能看到自己的数据
			/*if(AppGlobals.ORG_JOB_TYPE.equals(orgType)){
				sql.append(" and b.create_user_id ='" + userId + "' ");
			}
			sql.append(" and t.org_code like '" + orgCode + "%'");*/
			if (StringUtil.isNotEmpty(lineType)) {
				if(lineType.equals("3")){
					sql.append(" and b.station_type='2'");
				}
				if(lineType.equals("5")){
					sql.append(" and b.station_type='1'");
				}
				if(lineType.equals("2") || lineType.equals("4")){
					sql.append(" and b.station_type!='1' and b.station_type!='2'");
				}
			}
			if ("1".equals(userType)) {
				sql.append(" and b.station_type!='0'");
			}
			if ("0".equals(userType)) {
				sql.append(" and b.station_type!='3'");
			}
			
			List<Object> list = this.systemService.findListbySql(sql.toString());
			if(list.size()>0){
				for (int i = 0; i < list.size(); i++) {
					Object[] ob = (Object[]) list.get(i);
					busid = ob[0]+"";
					busName= ob[1]+"";
						json.append("{");
						json.append("'busid':'" +busid + "',");
						json.append("'busName':'"+ busName + "'");
						json.append("},");
				}
			}
			
		}
		
		json.delete(json.length()-1, json.length());
		json.append("]}");
		return json.toString();
	}
	
	/**
	 * 获取创建人
	 * @return
	 */
	public String getCreatePeople(){
		TSDepart depart = ResourceUtil.getSessionUserName().getCurrentDepart();
		String orgCode = depart.getOrgCode();
		StringBuffer sql = new StringBuffer();
		String orgType = depart.getOrgType();
		String id = ResourceUtil.getSessionUserName().getId();
		sql.append("select u.ID,u.realname from t_s_base_user u LEFT JOIN t_s_user_org g on u.ID=g.user_id LEFT JOIN "
				+ "t_s_depart t on t.ID=g.org_id LEFT JOIN t_s_role_user tu on u.ID=tu.userid LEFT JOIN t_s_role r "
				+ "on r.ID=tu.roleid where r.rolecode='operationS' ");
		if (StringUtil.isNotEmpty(orgCode)) {
			sql.append(" and t.org_code like '"+orgCode+"%'");
		}
		if(AppGlobals.ORG_JOB_TYPE.equals(orgType)){
			sql.append(" and u.id ='" + id + "' ");
		}
		List<Object> list = this.systemService.findListbySql(sql.toString());
		StringBuffer json = new StringBuffer("{'data':[");
		if(list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				Object[] ob = (Object[]) list.get(i);
				String userId = ob[0]+"";
				String userName = ob[1]+"";
					json.append("{");
					json.append("'userId':'" +userId + "',");
					json.append("'userName':'"+ userName + "'");
					json.append("},");
			}
		}
		json.delete(json.length()-1, json.length());
		json.append("]}");
		return json.toString();
	}
	
	/**
	 * 获取线路
	 */
	public String getLine(String ordertype,String userType){
		//String orgCode = ResourceUtil.getSessionUserName().getCurrentDepart().getOrgCode();
		// 添加了权限
		//String sql ="select l.id,l.name from lineinfo l,t_s_depart t where l.departId=t.ID and l.status='0' and t.org_code like '" + orgCode + "%'";
		TSUser user = ResourceUtil.getSessionUserName();
		TSDepart depart = user.getCurrentDepart();
		String orgCode = depart.getOrgCode();
		String orgType = depart.getOrgType();
		String userId = user.getId();
		
		StringBuffer sql = new StringBuffer();
		sql.append("select l.id,l.name from lineinfo l LEFT JOIN t_s_depart t on l.departId=t.ID LEFT JOIN t_s_base_user u on l.createUserId=u.ID where l.status='0' ");
		// 添加了权限
		//判断当前的机构类型，如果是"岗位"类型，就需要加个userId等于当前用户的条件，确保各个专员之间只能看到自己的数据
		if(AppGlobals.ORG_JOB_TYPE.equals(orgType)){
			sql.append(" and l.createUserId = '" + userId + "' ");
		}
		if (StringUtil.isNotEmpty(ordertype)) {
			sql.append(" and l.type = '" + ordertype + "' ");
		}
		if (StringUtil.isNotEmpty(userType)) {
			sql.append(" and l.is_dealer_line = '" + userType + "' ");
		}
		
		if (StringUtil.isNotEmpty(orgCode)) {
			sql.append(" and t.org_code like '" + orgCode + "%'");
		}
		
		List<Object> list = this.systemService.findListbySql(sql.toString());
		StringBuffer json = new StringBuffer("{'lineinfo':[");
		if(list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				Object[] ob = (Object[]) list.get(i);
				String id = ob[0]+"";
				String name = ob[1]+"";
					json.append("{");
					json.append("'lineId':'" +id + "',");
					json.append("'lineName':'"+ name + "'");
					json.append("},");
				}
			}else{
				json.append("{");
				json.append("'lineId':'',");
				json.append("'lineName':''");
				json.append("},");
			}
		json.delete(json.length()-1, json.length());
		json.append("]}");
		
		return json.toString();
	}
	
	
	@Override
	public List<LineInfoEntity> getLineinfo(String ordertype, String userType) {
		
		TSUser user = ResourceUtil.getSessionUserName();
		TSDepart depart = user.getCurrentDepart();
		String orgCode = depart.getOrgCode();
		String orgType = depart.getOrgType();
		String userId = user.getId();
		
		StringBuffer sql = new StringBuffer();
		sql.append("select l.id,l.name from lineinfo l LEFT JOIN t_s_depart t on l.departId=t.ID LEFT JOIN t_s_base_user u on l.createUserId=u.ID where l.status='0' ");
		// 添加了权限
		//判断当前的机构类型，如果是"岗位"类型，就需要加个userId等于当前用户的条件，确保各个专员之间只能看到自己的数据
		if(AppGlobals.ORG_JOB_TYPE.equals(orgType)){
			sql.append(" and l.createUserId = '" + userId + "' ");
		}
		if (StringUtil.isNotEmpty(ordertype)) {
			sql.append(" and l.type = '" + ordertype + "' ");
		}
		if (StringUtil.isNotEmpty(userType)) {
			sql.append(" and l.is_dealer_line = '" + userType + "' ");
		}
		
		if (StringUtil.isNotEmpty(orgCode)) {
			sql.append(" and t.org_code like '" + orgCode + "%'");
		}
		
		List<Object> list = this.systemService.findListbySql(sql.toString());
		
		List<LineInfoEntity> linelist = new ArrayList<>();
		
		if(list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				Object[] ob = (Object[]) list.get(i);
				String id = ob[0]+"";
				String name = ob[1]+"";
				LineInfoEntity lineinfo = new LineInfoEntity();
				lineinfo.setId(id);
				lineinfo.setName(name);
				linelist.add(lineinfo);
				}
			}
		
		return linelist;
	}

	/**
	 * 获取订单统计数据
	 */
	@Override
	public String getOrderStatisticsChart(String userType, String dealer, String startDate, String endDate,
			String timestamp) {
		// TODO Auto-generated method stub
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT DATE_FORMAT (DAY_SHORT_DESC, ");
		
		switch (timestamp) {
			case "0":
				sql.append("'%Y/%m/%d'");//默认30天
				break;
			case "1":
				sql.append("'%Y/%m/%d'");//按天查询
				break;
			case "2":
				sql.append("'%Y/%m/%d'");//按周查询
				break;
			case "3":
				sql.append("'%Y/%m'");//按月查询
				break;
			case "4":
				sql.append("'%Y年'");//按年查询
				break;
				default:sql.append("'%Y/%m/%d'");
				break;
			}
		
		sql.append(") ddate,SUM(m.order_totalPrice) as orderTotalPrice FROM (SELECT DAY_SHORT_DESC FROM dim_day d where ");
		
		if (StringUtil.isNotEmpty(startDate)) {
			sql.append("'"+startDate+"'");
		}else{
			sql.append(" DATE_SUB(CURDATE(), INTERVAL ");
			switch (timestamp) {
			case "0":
				sql.append(" 29 DAY)");//默认30天
				break;
			case "1":
				sql.append(" 29 DAY)");//查询最近30天的数据
				break;
			case "2":
				sql.append(" 3 MONTH)");//查询最近3个月的数据
				break;
			case "3":
				sql.append(" 1 year)");//按月查询
				break;
			case "4":
				sql.append(" 10 year)");//查询最近10年的数据
				break;
				default:sql.append(" 29 DAY)");//默认30天
				break;
			}
		}
		sql.append("<=d.DAY_SHORT_DESC and d.DAY_SHORT_DESC <= ");
		
		if (StringUtil.isNotEmpty(endDate)) {
			sql.append("'"+endDate+"'");
		}else{
			sql.append("CURDATE()");
		}
		sql.append(" GROUP BY ");
		
		switch (timestamp) {
		case "0":
			sql.append(" d.DAY_LONG_DESC)");//默认30天
			break;
		case "1":
			sql.append(" d.DAY_LONG_DESC)");//查询最近30天的数据
			break;
		case "2":
			sql.append(" d.WEEK_ID)");//查询最近3个月的数据,计算周
			break;
		case "3":
			sql.append(" d.MONTH_ID)");//按月查询
			break;
		case "4":
			sql.append(" d.YEAR_ID)");//查询最近10年的数据
			break;
			default:sql.append(" d.DAY_LONG_DESC)");//默认30天
			break;
		}
		
		sql.append(" a LEFT JOIN (SELECT t.* from transferorder t LEFT JOIN car_customer c on t.user_id = c.id LEFT JOIN dealer_info f on c.phone = f.phone where t.delete_flag='0' ");
		
		if (StringUtil.isNotEmpty(userType)) {
			sql.append(" and t.order_user_type='"+userType+"'");
		}
		if (StringUtil.isNotEmpty(dealer)) {
			sql.append(" and f.id='"+dealer+"'");
		}
		
		sql.append(") m on DATE_FORMAT(m.applicationTime,");
		
		switch (timestamp) {
		case "0":
			sql.append(" '%Y%m%d'");//默认30天
			break;
		case "1":
			sql.append(" '%Y%m%d'");//查询最近30天的数据
			break;
		case "2":
			sql.append(" '%Y%u'");//查询最近3个月的数据,计算周
			break;
		case "3":
			sql.append(" '%Y%m'");//按月查询
			break;
		case "4":
			sql.append(" '%Y'");//查询最近10年的数据
			break;
			default:sql.append(" '%Y%m%d'");//默认30天
			break;
		}
		
		sql.append(")=DATE_FORMAT (DAY_SHORT_DESC, ");
		
		switch (timestamp) {
		case "0":
			sql.append(" '%Y%m%d'");//默认30天
			break;
		case "1":
			sql.append(" '%Y%m%d'");//查询最近30天的数据
			break;
		case "2":
			sql.append(" '%Y%u'");//查询最近3个月的数据,计算周
			break;
		case "3":
			sql.append(" '%Y%m'");//按月查询
			break;
		case "4":
			sql.append(" '%Y'");//查询最近10年的数据
			break;
			default:sql.append(" '%Y%m%d'");//默认30天
			break;
		}
		
		sql.append(") GROUP BY ddate");
		
		List<Object> list = this.systemService.findListbySql(sql.toString());
		StringBuffer json = new StringBuffer("{'data':[");
		if(list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				Object[] ob = (Object[]) list.get(i);
				String orderTime = ob[0]+"";
				BigDecimal orderTotalPrice = new BigDecimal("0");
				if (StringUtil.isNotEmpty(ob[1])) {
					orderTotalPrice=(BigDecimal) ob[1];
				}
					json.append("{");
					json.append("'orderTime':'" +orderTime + "',");
					json.append("'orderTotalPrice':'"+ orderTotalPrice + "'");
					json.append("},");
				}
			}else{
				json.append("{");
				json.append("'orderTime':'',");
				json.append("'orderTotalPrice':''");
				json.append("},");
			}
		json.delete(json.length()-1, json.length());
		json.append("]}");
		
		return json.toString();
	}
	
	
	/**
	 * 获取订单统计数据
	 */
	@Override
	public String getDriverOrderStatisticsChart(String driver, String startDate, String endDate,
			String timestamp) {
		// TODO Auto-generated method stub
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT DATE_FORMAT (DAY_SHORT_DESC, ");
		
		switch (timestamp) {
			case "0":
				sql.append("'%Y/%m/%d'");//默认30天
				break;
			case "1":
				sql.append("'%Y/%m/%d'");//按天查询
				break;
			case "2":
				sql.append("'%Y/%m/%d'");//按周查询
				break;
			case "3":
				sql.append("'%Y/%m'");//按月查询
				break;
			case "4":
				sql.append("'%Y年'");//按年查询
				break;
				default:sql.append("'%Y/%m/%d'");
				break;
			}
		
		sql.append(") ddate,SUM(m.order_totalPrice) as orderTotalPrice,SUM(m.order_numbers) as orederNum FROM (SELECT DAY_SHORT_DESC FROM dim_day d where ");
		
		if (StringUtil.isNotEmpty(startDate)) {
			sql.append("'"+startDate+"'");
		}else{
			sql.append(" DATE_SUB(CURDATE(), INTERVAL ");
			switch (timestamp) {
			case "0":
				sql.append(" 29 DAY)");//默认30天
				break;
			case "1":
				sql.append(" 29 DAY)");//查询最近30天的数据
				break;
			case "2":
				sql.append(" 3 MONTH)");//查询最近3个月的数据
				break;
			case "3":
				sql.append(" 1 year)");//按月查询
				break;
			case "4":
				sql.append(" 10 year)");//查询最近10年的数据
				break;
				default:sql.append(" 29 DAY)");//默认30天
				break;
			}
		}
		sql.append("<=d.DAY_SHORT_DESC and d.DAY_SHORT_DESC <= ");
		
		if (StringUtil.isNotEmpty(endDate)) {
			sql.append("'"+endDate+"'");
		}else{
			sql.append("CURDATE()");
		}
		sql.append(" GROUP BY ");
		
		switch (timestamp) {
		case "0":
			sql.append(" d.DAY_LONG_DESC)");//默认30天
			break;
		case "1":
			sql.append(" d.DAY_LONG_DESC)");//查询最近30天的数据
			break;
		case "2":
			sql.append(" d.WEEK_ID)");//查询最近3个月的数据,计算周
			break;
		case "3":
			sql.append(" d.MONTH_ID)");//按月查询
			break;
		case "4":
			sql.append(" d.YEAR_ID)");//查询最近10年的数据
			break;
			default:sql.append(" d.DAY_LONG_DESC)");//默认30天
			break;
		}
		
		sql.append(" a LEFT JOIN (SELECT t.* from transferorder t left join order_linecardiver b on t.id = b .id left join car_info c on b.licencePlateId =c.id left join "
				+ "driversinfo f on c.driver_id = f.id where t.delete_flag='0' ");
		
		if (StringUtil.isNotEmpty(driver)) {
			sql.append(" and f.id='"+driver+"'");
		}
		
		sql.append(") m on DATE_FORMAT(m.applicationTime,");
		
		switch (timestamp) {
		case "0":
			sql.append(" '%Y%m%d'");//默认30天
			break;
		case "1":
			sql.append(" '%Y%m%d'");//查询最近30天的数据
			break;
		case "2":
			sql.append(" '%Y%u'");//查询最近3个月的数据,计算周
			break;
		case "3":
			sql.append(" '%Y%m'");//按月查询
			break;
		case "4":
			sql.append(" '%Y'");//查询最近10年的数据
			break;
			default:sql.append(" '%Y%m%d'");//默认30天
			break;
		}
		
		sql.append(")=DATE_FORMAT (DAY_SHORT_DESC, ");
		
		switch (timestamp) {
		case "0":
			sql.append(" '%Y%m%d'");//默认30天
			break;
		case "1":
			sql.append(" '%Y%m%d'");//查询最近30天的数据
			break;
		case "2":
			sql.append(" '%Y%u'");//查询最近3个月的数据,计算周
			break;
		case "3":
			sql.append(" '%Y%m'");//按月查询
			break;
		case "4":
			sql.append(" '%Y'");//查询最近10年的数据
			break;
			default:sql.append(" '%Y%m%d'");//默认30天
			break;
		}
		
		sql.append(") GROUP BY ddate");
		
		List<Object> list = this.systemService.findListbySql(sql.toString());
		StringBuffer json = new StringBuffer("{'data':[");
		if(list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				Object[] ob = (Object[]) list.get(i);
				String orderTime = ob[0]+"";
				int orderNum = 0;
				Double num = 0.0;
				BigDecimal orderTotalPrice = new BigDecimal("0");
				if (StringUtil.isNotEmpty(ob[1])) {
					orderTotalPrice=(BigDecimal) ob[1];
				}
				if (StringUtil.isNotEmpty(ob[2])) {
						num = (Double) ob[2];
					 orderNum = num.intValue();
				}
					json.append("{");
					json.append("'orderTime':'" +orderTime + "',");
					json.append("'orderTotalPrice':'"+ orderTotalPrice + "',");
					json.append("'orderNum':'" +orderNum + "'");
					json.append("},");
				}
			}else{
				json.append("{");
				json.append("'orderTime':'',");
				json.append("'orderTotalPrice':'',");
				json.append("'orderNum':''");
				json.append("},");
			}
		json.delete(json.length()-1, json.length());
		json.append("]}");
		
		return json.toString();
	}

	@Override
	public String getLineOrderStatisticsChart(String lineType, String lineId, String startDate, String endDate,
			String timestamp) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT DATE_FORMAT (DAY_SHORT_DESC, '%Y/%m/%d'");
		
		/*switch (timestamp) {
			case "0":
				sql.append("'%Y/%m/%d'");//默认30天
				break;
			case "1":
				sql.append("'%Y/%m/%d'");//按天查询
				break;
			case "2":
				sql.append("'%Y/%m/%d'");//按周查询
				break;
			case "3":
				sql.append("'%Y/%m'");//按月查询
				break;
			case "4":
				sql.append("'%Y年'");//按年查询
				break;
				default:sql.append("'%Y/%m/%d'");
				break;
			}*/
		
		sql.append(") ddate,SUM(linecount) as linecount FROM (SELECT DAY_SHORT_DESC FROM dim_day d where ");
		
		if (StringUtil.isNotEmpty(startDate)) {
			sql.append("'"+startDate+"'");
		}else{
			sql.append(" DATE_SUB(CURDATE(), INTERVAL ");
			switch (timestamp) {
			case "0":
				sql.append(" 29 DAY)");//默认30天
				break;
			case "1":
				sql.append(" 29 DAY)");//查询最近30天的数据
				break;
			case "2":
				sql.append(" 3 MONTH)");//查询最近3个月的数据
				break;
			case "3":
				sql.append(" 1 year)");//按月查询
				break;
			case "4":
				sql.append(" 10 year)");//查询最近10年的数据
				break;
				default:sql.append(" 29 DAY)");//默认30天
				break;
			}
		}
		sql.append("<=d.DAY_SHORT_DESC and d.DAY_SHORT_DESC <= ");
		
		if (StringUtil.isNotEmpty(endDate)) {
			sql.append("'"+endDate+"'");
		}else{
			sql.append("CURDATE()");
		}
		sql.append(" GROUP BY ");
		
		switch (timestamp) {
		case "0":
			sql.append(" d.DAY_LONG_DESC)");//默认30天
			break;
		case "1":
			sql.append(" d.DAY_LONG_DESC)");//查询最近30天的数据
			break;
		case "2":
			sql.append(" d.WEEK_ID)");//查询最近3个月的数据,计算周
			break;
		case "3":
			sql.append(" d.MONTH_ID)");//按月查询
			break;
		case "4":
			sql.append(" d.YEAR_ID)");//查询最近10年的数据
			break;
			default:sql.append(" d.DAY_LONG_DESC)");//默认30天
			break;
		}
		
		sql.append(" a LEFT JOIN (select date_format(t.applicationTime,'%Y%m%d'");
		
		sql.append(") as apptime,COUNT(l.id) as linecount from transferorder t LEFT JOIN lineinfo l on "
				+ "l.id=t.line_id where t.delete_flag='0' ");
		
		if (StringUtil.isNotEmpty(lineId)) {
			sql.append(" and l.id='"+lineId+"'");
		}
		if (StringUtil.isNotEmpty(lineType)) {
			sql.append(" and l.type='"+lineType+"'");
		}
		
		sql.append(" GROUP BY apptime) m on DATE_FORMAT(m.apptime,");
		
		switch (timestamp) {
		case "0":
			sql.append(" '%Y%m%d'");//默认30天
			break;
		case "1":
			sql.append(" '%Y%m%d'");//查询最近30天的数据
			break;
		case "2":
			sql.append(" '%Y%u'");//查询最近3个月的数据,计算周
			break;
		case "3":
			sql.append(" '%Y%m'");//按月查询
			break;
		case "4":
			sql.append(" '%Y'");//查询最近10年的数据
			break;
			default:sql.append(" '%Y%m%d'");//默认30天
			break;
		}
		
		sql.append(")=DATE_FORMAT (DAY_SHORT_DESC, ");
		
		switch (timestamp) {
		case "0":
			sql.append(" '%Y%m%d'");//默认30天
			break;
		case "1":
			sql.append(" '%Y%m%d'");//查询最近30天的数据
			break;
		case "2":
			sql.append(" '%Y%u'");//查询最近3个月的数据,计算周
			break;
		case "3":
			sql.append(" '%Y%m'");//按月查询
			break;
		case "4":
			sql.append(" '%Y'");//查询最近10年的数据
			break;
			default:sql.append(" '%Y%m%d'");//默认30天
			break;
		}
		
		sql.append(") GROUP BY ddate");
		
		List<Object> list = this.systemService.findListbySql(sql.toString());
		
		StringBuffer json = new StringBuffer("{'data':[");
		if(list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				Object[] ob = (Object[]) list.get(i);
				String orderTime = ob[0]+"";
				int sumcount = 0;
				int linecount = 0;
				BigDecimal count = new BigDecimal("0");
				if (StringUtil.isNotEmpty(ob[1])) {
					count = (BigDecimal) ob[1];
					sumcount = count.intValue();
				}
				JSONArray jsonArray = new JSONArray();
				JSONObject jsonObject = new JSONObject();
				
				if (StringUtil.isNotEmpty(orderTime) && sumcount>0) {
					
					StringBuffer sqls = new StringBuffer();
					sqls.append("select l.id,l.name as lineName,COUNT(l.id) as linecount from lineinfo l LEFT JOIN transferorder t on l.id =t.line_id where t.delete_flag='0' ");
					
					if (StringUtil.isNotEmpty(lineId)) {
						sqls.append(" and l.id='"+lineId+"'");
					}
					if (StringUtil.isNotEmpty(lineType)) {
						sqls.append(" and l.type='"+lineType+"'");
					}
					sqls.append(" and date_format(t.applicationTime, ");
					
					
					switch (timestamp) {
					case "0":
						sqls.append(" '%Y%m%d'");//默认30天
						break;
					case "1":
						sqls.append(" '%Y%m%d'");//查询最近30天的数据
						break;
					case "2":
						sqls.append(" '%Y%u'");//查询最近3个月的数据,计算周
						break;
					case "3":
						sqls.append(" '%Y%m'");//按月查询
						break;
					case "4":
						sqls.append(" '%Y'");//查询最近10年的数据
						break;
						default:sqls.append(" '%Y%m%d'");//默认30天
						break;
					}
					sqls.append(")=date_format('");
					sqls.append(orderTime);
					sqls.append("',");
					
					switch (timestamp) {
					case "0":
						sqls.append(" '%Y%m%d'");//默认30天
						break;
					case "1":
						sqls.append(" '%Y%m%d'");//查询最近30天的数据
						break;
					case "2":
						sqls.append(" '%Y%u'");//查询最近3个月的数据,计算周
						break;
					case "3":
						sqls.append(" '%Y%m'");//按月查询
						break;
					case "4":
						sqls.append(" '%Y'");//查询最近10年的数据
						break;
						default:sqls.append(" '%Y%m%d'");//默认30天
						break;
					}
					sqls.append(") GROUP BY l.id ORDER BY linecount DESC LIMIT 5");
					
					List<Object> lineList = this.systemService.findListbySql(sqls.toString());
					
					if(lineList.size()>0){
						for (int j = 0; j < lineList.size(); j++) {
							Object[] ob1 = (Object[]) lineList.get(j);
							//String lineIds = ob1[0]+"";
							String lineName = ob1[1]+"";
							String liecount = "";
							//BigInteger counts=new BigInteger("0");
							if (StringUtil.isNotEmpty(ob1[2])) {
								liecount = ob1[2]+"";
								//liecount = counts.intValue();
							}
							jsonObject.put("name", lineName);
							jsonObject.put("type", "line");
							jsonObject.put("data", liecount);
							jsonArray.add(jsonObject);
						}
					}else{
						
					}
				}
				
				String orderTimes = "";
				if("3".equals(timestamp)){
					orderTimes = orderTime.substring(0,7);
				}else if("4".equals(timestamp)){
					orderTimes = orderTime.substring(0,4);
				}else{
					orderTimes = orderTime;
				}
				
				json.append("{");
				json.append("'orderTime':'" +orderTimes + "',");
				json.append("'sumcount':'" +sumcount + "',");
				json.append("'seriesData':'"+ jsonArray + "',");
				json.append("},");
			}
		}else{
			json.append("{");
			json.append("'orderTime':'',");
			json.append("'sumcount':0,");
			json.append("'seriesData':'',");
			json.append("},");
		}
			json.delete(json.length()-1, json.length());
			json.append("]}");
	
		
		return json.toString();
	}
	

}
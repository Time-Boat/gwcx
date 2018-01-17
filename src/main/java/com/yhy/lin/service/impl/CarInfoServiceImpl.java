package com.yhy.lin.service.impl;

import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yhy.lin.app.quartz.BussAnnotation;
import com.yhy.lin.app.util.AppGlobals;
import com.yhy.lin.entity.CarInfoEntity;
import com.yhy.lin.entity.DriversInfoEntity;
import com.yhy.lin.service.CarInfoServiceI;

import net.sf.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.StringUtil;

@Service("carInfoService")
@Transactional
public class CarInfoServiceImpl extends CommonServiceImpl implements CarInfoServiceI {

	@Override
	public JSONObject getDatagrid(DataGrid dataGrid ,String userCar ,String lpId, String carId,
			String carType, String status, String businessType, String carStatus, String auditStatus) {
		
		String sqlWhere = ((CarInfoServiceI) AopContext.currentProxy()).getWhere(lpId, userCar, carId, carType, status, businessType, carStatus, auditStatus);
		
		String sqlCnt = " select count(1) from car_info c left join driversinfo d on c.driver_id = d.id LEFT JOIN t_s_depart t on c.departId=t.ID "
				+ " LEFT JOIN t_s_base_user u on d.create_user_id = u.id,t_s_depart p "
				+ " where c.delete_flag = '0' and (case when LENGTH(t.org_code)<6 then t.org_code else substring(t.org_code,1,6) END)=p.org_code ";
		
		if (!sqlWhere.isEmpty()) {
			sqlCnt += sqlWhere;
		}
		
		Long iCount = getCountForJdbcParam(sqlCnt, null);
		
		// 取出当前页的数据 
		StringBuffer sql = new StringBuffer();
	    sql.append(" select c.*,d.name,d.driving_license,d.id as driverId,u.username from car_info c left join driversinfo d on c.driver_id = d.id ");
		sql.append(" LEFT JOIN t_s_depart t on c.departId=t.ID LEFT JOIN t_s_base_user u on c.create_user_id = u.id,t_s_depart p ");
		sql.append(" where c.delete_flag = '0' and (case when LENGTH(t.org_code)<6 then t.org_code else substring(t.org_code,1,6) END)=p.org_code ");
		
	    if (!sqlWhere.isEmpty()) {
	    	sql.append(sqlWhere);
		}
	    
		List<Map<String, Object>> mapList = findForJdbc(sql.toString(), dataGrid.getPage(), dataGrid.getRows());
		// 将结果集转换成页面上对应的数据集
					Db2Page[] db2Pages = {
							new Db2Page("id", "id")
							,new Db2Page("licencePlate", "licence_plate")
							,new Db2Page("carType", "car_type")
							,new Db2Page("stopPosition", "stop_position")
							,new Db2Page("name", "name")
							,new Db2Page("seat", "seat")
							,new Db2Page("status", "status")
							,new Db2Page("drivingLicense", "driving_license")
							,new Db2Page("driverId", "driverId")
							,new Db2Page("username", "username")
							,new Db2Page("createTime", "create_time")
							,new Db2Page("businessType", "business_type")
							
							,new Db2Page("buyDate", "buy_date")
							,new Db2Page("carBrand", "car_brand")
							,new Db2Page("modelNumber", "model_number")
							
							,new Db2Page("auditStatus", "audit_status")
							,new Db2Page("carStatus", "car_status")
							,new Db2Page("applyContent", "apply_content")
							,new Db2Page("remark", "remark")
					}; 
		JSONObject jObject = getJsonDatagridEasyUI(mapList, iCount.intValue(), db2Pages);
		return jObject;
	}

	@Override
	public List<DriversInfoEntity> getDriverList(String driverId) {
		
		List<CarInfoEntity> list = findByQueryString("from CarInfoEntity where driverId != '" + driverId + "' group by driver_id ");
		StringBuffer sbf = new StringBuffer();
		for(int i=0;i<list.size();i++){
			sbf.append("'"+list.get(i).getDriverId()+"',");
		}
		
		List<DriversInfoEntity> dList = new ArrayList<DriversInfoEntity>();
		
		Connection con = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		try {
			con = ((SessionFactoryImplementor)getSession().getSessionFactory()).getConnectionProvider().getConnection();
			String sql = "select id,name,driving_license from driversinfo where "
					+ (sbf.length() == 0 ? "" : " id not in( " + sbf.deleteCharAt(sbf.length()-1)+" ) and ") + " deleteFlag='0' ";
			stat = con.prepareStatement(sql);
			rs = stat.executeQuery();
			while(rs.next()){
				DriversInfoEntity d = new DriversInfoEntity();
				d.setId(rs.getString("id"));
				d.setName(rs.getString("name"));
				d.setDrivingLicense(rs.getString("driving_license"));
				dList.add(d);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(con!=null)
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if(stat!=null)
				try {
					stat.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if(rs!=null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return dList;
	}
	
	@BussAnnotation(orgType = {AppGlobals.TECHNICAL_MANAGER , AppGlobals.ORG_JOB_TYPE}, 
			objTableUserId = " c.create_user_id ", orgTable="t", technicalSql = " and c.audit_status != '-1' ")
	public String getWhere(String lpId, String userCar, String carId, String carType, String status, String businessType, String carStatus, String auditStatus) {
		
		StringBuffer sql = new StringBuffer();
		
		if(StringUtil.isNotEmpty(userCar)){
			sql.append(" and c.status = '" + userCar + "' ");
			if(StringUtil.isNotEmpty(lpId)){
				sql.append(" or c.id = '" + lpId + "' ");
			}
		}
		
		if(StringUtil.isNotEmpty(carStatus)){
			sql.append(" and c.car_status = '"+carStatus+"' ");
		}
		
		if(StringUtil.isNotEmpty(auditStatus)){
			sql.append(" and c.audit_status = '"+auditStatus+"' ");
		}
		
		if(StringUtil.isNotEmpty(businessType)){
			sql.append(" and c.business_type = '"+businessType+"' ");
			sql.append(" and c.car_status = '0' ");
		}
		
		if(StringUtil.isNotEmpty(carId)){
			sql.append(" and c.id = '"+carId+"' ");
		}
		
		if(StringUtil.isNotEmpty(carType)){
			sql.append(" and car_type like '"+carType+"%' ");
		}
		
		if(StringUtil.isNotEmpty(status)){
			sql.append(" and c.status = '"+status+"' ");
		}
		
		return sql.toString();
	}
	
}
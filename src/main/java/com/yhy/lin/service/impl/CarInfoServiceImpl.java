package com.yhy.lin.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;

@Service("carInfoService")
@Transactional
public class CarInfoServiceImpl extends CommonServiceImpl implements CarInfoServiceI {

	@Override
	public JSONObject getDatagrid(DataGrid dataGrid ,String userCar ,String lpId, String licencePlate, String carType, String status, String businessType) {
		
		StringBuffer queryCondition = new StringBuffer(" where delete_flag = '0' ");
		
		TSDepart depart = ResourceUtil.getSessionUserName().getCurrentDepart();
		String orgCode = depart.getOrgCode();
		String orgType = depart.getOrgType();
		String userId = ResourceUtil.getSessionUserName().getId();
		
		
		//判断当前的机构类型，如果是"岗位"类型，就需要加个userId等于当前用户的条件，确保各个专员之间只能看到自己的数据
		if(AppGlobals.ORG_JOB_TYPE.equals(orgType)){
			queryCondition.append(" and c.create_user_id = '" + userId + "' ");
		}
		
		queryCondition.append(" and t.org_code like '" + orgCode + "%' ");
		
		
		if(StringUtil.isNotEmpty(userCar)){
			queryCondition.append(" and c.status = '" + userCar + "' ");
			if(StringUtil.isNotEmpty(lpId)){
				queryCondition.append(" or c.id = '" + lpId + "' ");
			}
		}
		
		if(StringUtil.isNotEmpty(businessType)){
			queryCondition.append(" and c.business_type = '"+businessType+"' ");
		}
		
		if(StringUtil.isNotEmpty(licencePlate)){
			queryCondition.append(" and c.licence_plate like '"+licencePlate+"%' ");
		}
		
		if(StringUtil.isNotEmpty(carType)){
			queryCondition.append(" and car_type like '"+carType+"%' ");
		}
		
		if(StringUtil.isNotEmpty(status)){
			queryCondition.append(" and c.status = '"+status+"' ");
		}
		
		String sqlCnt = "select count(1) from car_info c left join driversinfo d on c.driver_id = d.id LEFT JOIN t_s_depart t on c.departId=t.ID" + queryCondition.toString();
		Long iCount = getCountForJdbcParam(sqlCnt, null);
		
		// 取出当前页的数据 
		StringBuffer sql = new StringBuffer();
	    sql.append("select c.*,d.name,d.driving_license,d.id as driverId from car_info c left join driversinfo d on c.driver_id = d.id LEFT JOIN t_s_depart t on c.departId=t.ID" + queryCondition.toString());
		
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
							,new Db2Page("createUserId", "create_user_id")
							,new Db2Page("createTime", "create_time")
							,new Db2Page("businessType", "business_type")
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
	
	
	
}
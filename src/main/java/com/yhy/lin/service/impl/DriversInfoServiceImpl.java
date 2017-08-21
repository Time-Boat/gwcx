package com.yhy.lin.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yhy.lin.service.DriversInfoServiceI;

import net.sf.json.JSONObject;

import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;

@Service("driversInfoService")
@Transactional
public class DriversInfoServiceImpl extends CommonServiceImpl implements DriversInfoServiceI {

	@Override
	public JSONObject getDatagrid(DataGrid dataGrid, String sex, String name, String phoneNumber,String status) {
		
		String  orgCode = ResourceUtil.getSessionUserName().getCurrentDepart().getOrgCode();
		
		StringBuffer queryCondition = new StringBuffer(" where 1=1 ");
		
		 
		if(StringUtil.isNotEmpty(orgCode)){
			queryCondition.append(" and t.org_code like '"+orgCode+"%'");
		}
	    
		if(StringUtil.isNotEmpty(sex)){
			queryCondition.append(" and d.sex = '"+sex+"' ");
		}
		
		if(StringUtil.isNotEmpty(name)){
			queryCondition.append(" and d.name like '%"+name+"%' ");
		}
		
		if(StringUtil.isNotEmpty(phoneNumber)){
			queryCondition.append(" and d.phoneNumber like '" + phoneNumber +"%' ");
		}
		if(StringUtil.isNotEmpty(status)){
			queryCondition.append(" and d.status ='" + status +"' ");
		}
		
		// 取出总数据条数（为了分页处理, 如果不用分页，取iCount值的这个处理可以不要）
		String sqlCnt = "select count(*) from driversinfo d left join cities c on c.cityId=d.cityId LEFT JOIN t_s_depart t on d.departId=t.ID" + queryCondition.toString();
		Long iCount = getCountForJdbcParam(sqlCnt, null);
		
		// 取出当前页的数据 
		StringBuffer sql = new StringBuffer();
	    sql.append("select d.id,d.sex,d.phoneNumber,d.name,d.age,d.idCard,d.createDate,d.remark,d.driving_license,d.drivingLicenseImgUrl,d.cityId,c.city from driversinfo d left join cities c on c.cityId=d.cityId LEFT JOIN t_s_depart t on d.departId=t.ID" + queryCondition.toString());
		
		System.out.println(sql.toString());
		List<Map<String, Object>> mapList = findForJdbc(sql.toString(), dataGrid.getPage(), dataGrid.getRows());
		// 将结果集转换成页面上对应的数据集
					Db2Page[] db2Pages = {
							new Db2Page("id", "id")
							,new Db2Page("sex", "sex")
							,new Db2Page("phoneNumber", "phoneNumber")
							,new Db2Page("cityId", "cityId")
							,new Db2Page("cityName", "city")
							,new Db2Page("name", "name")
							,new Db2Page("age", "age")
							,new Db2Page("idCard", "idCard")
							,new Db2Page("createDate", "createDate")
							,new Db2Page("remark", "remark")
							,new Db2Page("drivingLicense", "driving_license")
							,new Db2Page("drivingLicenseImgUrl", "drivingLicenseImgUrl")
					};
		JSONObject jObject = getJsonDatagridEasyUI(mapList, iCount.intValue(), db2Pages);
		return jObject;
	}
	
	@Override
	public JSONObject getDatagrid1(DataGrid dataGrid, String sex, String name, String phoneNumber,String status) {
		StringBuffer queryCondition = new StringBuffer(" where d.id not in (select ci.driver_id from car_info ci ) ");
	    
		String  orgCode = ResourceUtil.getSessionUserName().getCurrentDepart().getOrgCode();
		
		if(StringUtil.isNotEmpty(orgCode)){
			queryCondition.append(" and t.org_code like '"+orgCode+"%'");
		}
		if(StringUtil.isNotEmpty(sex)){
			queryCondition.append(" and d.sex = '"+sex+"' ");
		}
		
		if(StringUtil.isNotEmpty(name)){
			queryCondition.append(" and d.name like '%"+name+"%' ");
		}
		
		if(StringUtil.isNotEmpty(phoneNumber)){
			queryCondition.append(" and d.phoneNumber like '" + phoneNumber +"%' ");
		}
		if(StringUtil.isNotEmpty(status)){
			queryCondition.append(" and d.status ='" + status +"' ");
		}
		
		// 取出总数据条数（为了分页处理, 如果不用分页，取iCount值的这个处理可以不要）
		String sqlCnt = "select count(*) from driversinfo d left join cities c on c.cityId=d.cityId LEFT JOIN t_s_depart t on d.departId=t.ID" + queryCondition.toString();
		Long iCount = getCountForJdbcParam(sqlCnt, null);
		
		// 取出当前页的数据 
		StringBuffer sql = new StringBuffer();
	    sql.append("select d.id,d.sex,d.phoneNumber,d.name,d.age,d.idCard,d.createDate,d.remark,d.driving_license,d.drivingLicenseImgUrl,d.cityId,c.city from driversinfo d left join cities c on c.cityId=d.cityId LEFT JOIN t_s_depart t on d.departId=t.ID" + queryCondition.toString());
		
		System.out.println(sql.toString());
		List<Map<String, Object>> mapList = findForJdbc(sql.toString(), dataGrid.getPage(), dataGrid.getRows());
		// 将结果集转换成页面上对应的数据集
					Db2Page[] db2Pages = {
							new Db2Page("id", "id")
							,new Db2Page("sex", "sex")
							,new Db2Page("phoneNumber", "phoneNumber")
							,new Db2Page("cityId", "cityId")
							,new Db2Page("cityName", "city")
							,new Db2Page("name", "name")
							,new Db2Page("age", "age")
							,new Db2Page("idCard", "idCard")
							,new Db2Page("createDate", "createDate")
							,new Db2Page("remark", "remark")
							,new Db2Page("drivingLicense", "driving_license")
							,new Db2Page("drivingLicenseImgUrl", "drivingLicenseImgUrl")
					};
		JSONObject jObject = getJsonDatagridEasyUI(mapList, iCount.intValue(), db2Pages);
		return jObject;
	}
	
	
}
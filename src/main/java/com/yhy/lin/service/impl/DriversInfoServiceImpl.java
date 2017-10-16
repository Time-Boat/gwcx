package com.yhy.lin.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yhy.lin.entity.DriversInfoEntity;
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
	public JSONObject getDatagrid(DataGrid dataGrid,DriversInfoEntity driversInfo,String cityID) {
		
		String  orgCode = ResourceUtil.getSessionUserName().getCurrentDepart().getOrgCode();
		
		String code = "";
		if(orgCode.length()>6){
			code= orgCode.substring(0,6);
		}else{
			code=orgCode;
		}
		
		StringBuffer queryCondition = new StringBuffer(" where d.deleteFlag='0' ");
		 
		if(StringUtil.isNotEmpty(code)){
			queryCondition.append(" and t.org_code like '"+code+"%'");
		}
		
		if(StringUtil.isNotEmpty(driversInfo.getSex())){
			queryCondition.append(" and d.sex = '"+driversInfo.getSex()+"' ");
		}
		
		if(StringUtil.isNotEmpty(driversInfo.getName())){
			queryCondition.append(" and d.name like '%"+driversInfo.getName()+"%' ");
		}
		
		if(StringUtil.isNotEmpty(driversInfo.getPhoneNumber())){
			queryCondition.append(" and d.phoneNumber like '" + driversInfo.getPhoneNumber() +"%' ");
		}
		
		if(StringUtil.isNotEmpty(driversInfo.getApplicationStatus())){
			queryCondition.append("and d.application_status = '"+driversInfo.getApplicationStatus()+"' ");
		}
		if(StringUtil.isNotEmpty(driversInfo.getStatus())){
			queryCondition.append("and d.status = '"+driversInfo.getStatus()+"' ");
		}
		
		if(StringUtil.isNotEmpty(cityID)){
			queryCondition.append(" and d.cityId = '" +cityID +"'");
		}
		
		// 取出总数据条数（为了分页处理, 如果不用分页，取iCount值的这个处理可以不要）
		String sqlCnt = "select count(*) from driversinfo d left join cities c on c.cityId=d.cityId LEFT JOIN t_s_depart t on d.departId=t.ID" + queryCondition.toString();
		Long iCount = getCountForJdbcParam(sqlCnt, null);
		
		// 取出当前页的数据 
		StringBuffer sql = new StringBuffer();
	    sql.append("select d.id,d.sex,d.phoneNumber,d.name,d.age,d.status,d.idCard,d.createDate,d.remark,d.driving_license,d.drivingLicenseImgUrl,d.idCardImgUrl,d.cityId,c.city,d.create_user_id,d.application_status,d.refusal_reason,d.application_time,d.application_user_id,d.apply_content,u.username,bu.username as applicationUserName,d.auditor,d.audit_time,tb.username as auditorUserName from driversinfo d left join cities c on c.cityId=d.cityId LEFT JOIN t_s_depart t on d.departId=t.ID LEFT JOIN t_s_base_user u on u.ID=d.create_user_id LEFT JOIN t_s_base_user bu on bu.ID=d.application_user_id LEFT JOIN t_s_base_user tb on tb.ID=d.auditor " + queryCondition.toString());
		
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
							,new Db2Page("status", "status")
							,new Db2Page("idCard", "idCard")
							,new Db2Page("createDate", "createDate")
							,new Db2Page("createUserId", "create_user_id")
							,new Db2Page("applicationStatus", "application_status")
							,new Db2Page("refusalReason", "refusal_reason")
							,new Db2Page("applicationTime", "application_time")
							,new Db2Page("applicationUserId", "application_user_id")
							,new Db2Page("applicationUserName", "applicationUserName")
							,new Db2Page("auditTime", "audit_time")
							,new Db2Page("auditor", "auditor")
							,new Db2Page("auditorUserName", "auditorUserName")
							,new Db2Page("applyContent", "apply_content")
							,new Db2Page("username", "username")
							,new Db2Page("remark", "remark")
							,new Db2Page("drivingLicense", "driving_license")
							,new Db2Page("drivingLicenseImgUrl", "drivingLicenseImgUrl")
							,new Db2Page("driverImgUrl", "idCardImgUrl")
					};
		JSONObject jObject = getJsonDatagridEasyUI(mapList, iCount.intValue(), db2Pages);
		return jObject;
	}
	
	@Override
	public JSONObject getDatagrid1(DataGrid dataGrid, String sex, String name, String phoneNumber,String status,String cityID, String fromPage) {
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
		if(StringUtil.isNotEmpty(cityID)){
			queryCondition.append(" and d.cityId= '"+cityID+"' ");
		}
		if(StringUtil.isNotEmpty(phoneNumber)){
			queryCondition.append(" and d.phoneNumber like '" + phoneNumber +"%' ");
		}
		
		//1是已启用
		if(StringUtil.isNotEmpty(fromPage)){
			queryCondition.append(" and d.status = '1' ");
		}else{
			if(StringUtil.isNotEmpty(status)){
				queryCondition.append(" and d.status ='" + status +"' ");
			}
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
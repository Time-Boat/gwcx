package com.yhy.lin.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yhy.lin.service.DriversInfoServiceI;

import net.sf.json.JSONObject;

import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.StringUtil;

@Service("driversInfoService")
@Transactional
public class DriversInfoServiceImpl extends CommonServiceImpl implements DriversInfoServiceI {

	@Override
	public JSONObject getDatagrid(DataGrid dataGrid, String sex, String name, String phoneNumber) {
		StringBuffer queryCondition = new StringBuffer(" where deleteFlag = 0 ");
	    
		if(StringUtil.isNotEmpty(sex)){
			queryCondition.append(" and sex = '"+sex+"' ");
		}
		
		if(StringUtil.isNotEmpty(name)){
			queryCondition.append(" and name like '%"+name+"%' ");
		}
		
		if(StringUtil.isNotEmpty(phoneNumber)){
			queryCondition.append(" and phoneNumber like '" + phoneNumber +"%' ");
		}
		
		// 取出总数据条数（为了分页处理, 如果不用分页，取iCount值的这个处理可以不要）
		String sqlCnt = "select count(*) from driversinfo  " + queryCondition.toString();
		Long iCount = getCountForJdbcParam(sqlCnt, null);
		
		// 取出当前页的数据 
		StringBuffer sql = new StringBuffer();
	    sql.append("select * from driversinfo  " + queryCondition.toString());
		
		System.out.println(sql.toString());
		List<Map<String, Object>> mapList = findForJdbc(sql.toString(), dataGrid.getPage(), dataGrid.getRows());
		// 将结果集转换成页面上对应的数据集
					Db2Page[] db2Pages = {
							new Db2Page("id", "id")
							,new Db2Page("sex", "sex")
							,new Db2Page("phoneNumber", "phoneNumber")
							,new Db2Page("name", "name")
							,new Db2Page("age", "age")
							,new Db2Page("idCard", "idCard")
							,new Db2Page("createDate", "createDate")
							,new Db2Page("deleteFlag", "deleteFlag")
							,new Db2Page("remark", "remark")
							,new Db2Page("drivingLicenseImgUrl", "drivingLicenseImgUrl")
					};
		JSONObject jObject = getJsonDatagridEasyUI(mapList, iCount.intValue(), db2Pages);
		return jObject;
	}
	
}
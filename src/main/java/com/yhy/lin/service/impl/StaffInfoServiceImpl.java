package com.yhy.lin.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yhy.lin.service.StaffInfoServiceI;

import net.sf.json.JSONObject;

import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.StringUtil;

@Service("staffInfoService")
@Transactional
public class StaffInfoServiceImpl extends CommonServiceImpl implements StaffInfoServiceI {

	@Override
	public JSONObject getDatagrid(String name, String orgCode, String companyId, String phone, DataGrid dataGrid) {
		
		StringBuffer queryCondition = new StringBuffer(" where 1=1 ");
	    
		if(StringUtil.isNotEmpty(name)){
			queryCondition.append(" and name = '"+name+"' ");
		}
		
		if(StringUtil.isNotEmpty(companyId)){
			queryCondition.append(" and company_id = '"+companyId+"' ");
		}else{
			queryCondition.append(" and company_id like '" + orgCode +"%' ");
		}
		
		if(StringUtil.isNotEmpty(phone)){
			queryCondition.append(" and phone like '"+phone+"%' ");
		}
//		
//		if(StringUtil.isNotEmpty(job)){
//			queryCondition.append(" and job = '"+job+"' ");
//		}
		
		// 取出总数据条数（为了分页处理, 如果不用分页，取iCount值的这个处理可以不要）
		String sqlCnt = "select count(*) from staff_info  " + queryCondition.toString();
		Long iCount = getCountForJdbcParam(sqlCnt, null);
		
		// 取出当前页的数据 
		StringBuffer sql = new StringBuffer();
	    sql.append("select * from staff_info  " + queryCondition.toString());
		
		System.out.println(sql.toString());
		List<Map<String, Object>> mapList = findForJdbc(sql.toString(), dataGrid.getPage(), dataGrid.getRows());
		// 将结果集转换成页面上对应的数据集
					Db2Page[] db2Pages = {
							new Db2Page("id", "id")
							,new Db2Page("name", "name")
							,new Db2Page("sex", "sex")
							,new Db2Page("age", "age")
							,new Db2Page("depart", "depart")
							,new Db2Page("phone", "phone")
							,new Db2Page("staffPosition", "staff_position")
							,new Db2Page("status", "status")
							,new Db2Page("remark", "remark")
					};
		JSONObject jObject = getJsonDatagridEasyUI(mapList, iCount.intValue(), db2Pages);
		return jObject;
	}
	
}
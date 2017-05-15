package com.yhy.lin.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yhy.lin.service.EnterCompanyInfoServiceI;

import net.sf.json.JSONObject;

import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.StringUtil;

@Service("enterCompanyInfoService")
@Transactional
public class EnterCompanyInfoServiceImpl extends CommonServiceImpl implements EnterCompanyInfoServiceI {

	
	@Override
	public JSONObject getDatagrid(String departname, String startTime, String endTime, String orgCode, DataGrid dataGrid) {
		
		StringBuffer queryCondition = new StringBuffer(" where 1=1 ");
			    
		if(StringUtil.isNotEmpty(startTime) && StringUtil.isNotEmpty(endTime)){
			queryCondition.append(" and create_date between '" + startTime + "' and '" + endTime + "' ");
		}
		
		if(StringUtil.isNotEmpty(departname)){
			queryCondition.append(" and departname like '%"+departname+"%' ");
		}
		
		queryCondition.append(" and org_code like '" + orgCode +"%' ");
		
		// 取出总数据条数（为了分页处理, 如果不用分页，取iCount值的这个处理可以不要）
		String sqlCnt = "select count(*) from company_depart_view  " + queryCondition.toString();
		Long iCount = getCountForJdbcParam(sqlCnt, null);
		
		// 取出当前页的数据 
		StringBuffer sql = new StringBuffer();
	    sql.append("select * from company_depart_view  " + queryCondition.toString());
		
		System.out.println(sql.toString());
		List<Map<String, Object>> mapList = findForJdbc(sql.toString(), dataGrid.getPage(), dataGrid.getRows());
		// 将结果集转换成页面上对应的数据集
					Db2Page[] db2Pages = {
							new Db2Page("id", "id")
							,new Db2Page("deptId", "deptId")
							,new Db2Page("departname", "departname")
							,new Db2Page("director", "director")
							,new Db2Page("directorPhone", "director_phone")
							,new Db2Page("createDate", "create_date")
							,new Db2Page("address", "address")
							,new Db2Page("status", "status")
							,new Db2Page("remark", "remark")
					};
		JSONObject jObject = getJsonDatagridEasyUI(mapList, iCount.intValue(), db2Pages);
		return jObject;
	}
	
	
	
	
}
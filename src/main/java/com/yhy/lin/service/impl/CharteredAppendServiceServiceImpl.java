package com.yhy.lin.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yhy.lin.entity.CharteredAppendServiceEntity;
import com.yhy.lin.service.CharteredAppendServiceServiceI;

import net.sf.json.JSONObject;

import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.StringUtil;

@Service("charteredAppendServiceService")
@Transactional
public class CharteredAppendServiceServiceImpl extends CommonServiceImpl implements CharteredAppendServiceServiceI {

	@Override
	public JSONObject getDatagrid(DataGrid dataGrid, CharteredAppendServiceEntity CharteredAppendService) {
		
		String sqlWhere =getSqlWhere(CharteredAppendService);
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		// 取出总数据条数（为了分页处理, 如果不用分页，取iCount值的这个处理可以不要）
		String sqlCnt = "select COUNT(*) from chartered_append_service c LEFT JOIN t_s_base_user u on c.create_user_id =u.ID ";
		if (!sqlWhere.isEmpty()) {
			sqlCnt += sqlWhere;
		}
		
		sql.append("select c.id,c.service_name,c.service_description,c.status,c.create_user_id,c.create_time,c.midify_time,c.remark,"
				+ "u.username from chartered_append_service c LEFT JOIN t_s_base_user u on c.create_user_id =u.ID");
		Long iCount = getCountForJdbcParam(sqlCnt, null);
		
		if (!sqlWhere.isEmpty()) {
			sql.append(sqlWhere);
		}
		List<Map<String, Object>> mapList = findForJdbc(sql.toString(), dataGrid.getPage(), dataGrid.getRows());
		// 将结果集转换成页面上对应的数据集
		Db2Page[] db2Pages = {
				new Db2Page("id")
				,new Db2Page("serviceName", "service_name", null)
				,new Db2Page("serviceDescription", "service_description", null)
				,new Db2Page("status", "status", null)
				,new Db2Page("createTime", "create_time", null)
				,new Db2Page("createUserId", "create_user_id", null)
				,new Db2Page("userName", "username", null)
				,new Db2Page("midifyTime", "midify_time", null)
				,new Db2Page("remark", "remark", null)
				
		};
		
		JSONObject jObject = getJsonDatagridEasyUI(mapList, iCount.intValue(), db2Pages);
		return jObject;
	}
	
	public String getSqlWhere(CharteredAppendServiceEntity CharteredAppendService){
		
		StringBuffer sqlWhere = new StringBuffer(" where 1=1 ");
		
		if(StringUtil.isNotEmpty(CharteredAppendService.getServiceName())){
			sqlWhere.append(" and c.service_name like '%"+CharteredAppendService.getServiceName()+"%'");
		}
		if(StringUtil.isNotEmpty(CharteredAppendService.getStatus())){
			sqlWhere.append(" and c.status = '"+CharteredAppendService.getStatus()+"'");
		}
		
		return sqlWhere.toString();
	}
	
}
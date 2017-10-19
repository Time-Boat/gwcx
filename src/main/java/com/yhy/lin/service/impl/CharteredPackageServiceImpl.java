package com.yhy.lin.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yhy.lin.service.CharteredPackageServiceI;

import net.sf.json.JSONObject;

import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl.Db2Page;

@Service("charteredPackageService")
@Transactional
public class CharteredPackageServiceImpl extends CommonServiceImpl implements CharteredPackageServiceI {

	@Override
	public JSONObject getDatagrid(DataGrid dataGrid) {
		
		String sqlWhere = " where cp.status = 0 ";
		
		// 取出总数据条数（为了分页处理, 如果不用分页，取iCount值的这个处理可以不要）
		String sqlCnt = "select count(*) from chartered_package cp left join cities c on c.cityId=cp.city_id LEFT JOIN t_s_base_user u on cp.create_user_id=u.ID ";
		
		Long iCount = getCountForJdbcParam(sqlCnt + sqlWhere, null);
		
		// 取出当前页的数据 
		StringBuffer sql = new StringBuffer();
	    sql.append(" select cp.id,cp.name,cp.description,cp.kilometre,cp.time_length,cp.create_time,c.city,cp.update_time,u.username,cp.remark,cp.status "
	    		+ " from chartered_package cp left join cities c on c.cityId=cp.city_id LEFT JOIN t_s_base_user u on u.ID= cp.create_user_id");
	    
		List<Map<String, Object>> mapList = findForJdbc(sql.toString() + sqlWhere, dataGrid.getPage(), dataGrid.getRows());
		// 将结果集转换成页面上对应的数据集
					Db2Page[] db2Pages = {
							new Db2Page("id", "id")
							,new Db2Page("name", "name")
							,new Db2Page("description", "description")
							,new Db2Page("kilometre", "kilometre")
							,new Db2Page("timeLength", "time_length")
							,new Db2Page("createTime", "create_time")
							,new Db2Page("cityName", "city")
							,new Db2Page("updateTime", "update_time")
							,new Db2Page("userName", "username")
							,new Db2Page("remark", "remark")
							,new Db2Page("status", "status")
					};
		JSONObject jObject = getJsonDatagridEasyUI(mapList, iCount.intValue(), db2Pages);
		return jObject;
	}
	
}
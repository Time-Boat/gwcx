package com.yhy.lin.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yhy.lin.entity.AreaStationEntity;
import com.yhy.lin.entity.AreaStationLineEntity;
import com.yhy.lin.service.AreaLineServiceI;

import net.sf.json.JSONObject;

import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl.Db2Page;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;

@Service("AreaLineServiceI")
@Transactional
public class AreaLineServiceImpl extends CommonServiceImpl implements AreaLineServiceI {

	@Override
	public JSONObject getDatagrid(DataGrid dataGrid) {
		
		StringBuffer sqlWhere = new StringBuffer();
		String orgCode = ResourceUtil.getSessionUserName().getCurrentDepart().getOrgCode();
		if(StringUtil.isNotEmpty(orgCode)){
			sqlWhere.append(" and org_code like '"+orgCode+"%'");
		}
		
		String p = sqlWhere.toString();
		
		StringBuffer sql = new StringBuffer();
		
		// 取出当前页的数据 
		sql.append(" select c.city as cityName,ar.area as district,a.id,a.name,a.station_id,a.line_type,a.status,a.remark,a.create_time,a.create_people, "
				+ " a.car_type,a.dispath,d.name as startname,d.name as station_name "
				+ " from area_line a left join t_s_depart b on a.depart_id =b.ID left join cities c on a.city_id = c.cityId "
				+ " left join areas as ar on a.district_id = ar.areaId left join busstopinfo d on d.id=a.station_id ");
		
		// 取出总数据条数（为了分页处理, 如果不用分页，取iCount值的这个处理可以不要）
		String sqlCnt = "select count(*) from area_line a inner join t_s_depart b on a.depart_id = b.ID ";
		if (StringUtil.isNotEmpty(p)) {
			sqlCnt += " where 1=1 " + sqlWhere;
			sql.append(sqlWhere);
		}
		
		Long iCount = getCountForJdbcParam(sqlCnt, null);
		
		List<Map<String, Object>> mapList = findForJdbc(sql.toString(), dataGrid.getPage(), dataGrid.getRows());
		// 将结果集转换成页面上对应的数据集
		Db2Page[] db2Pages = {
				new Db2Page("id")
				,new Db2Page("name", "name", null)
				,new Db2Page("stationId", "station_id", null)
				,new Db2Page("lineType", "line_type", null)
				,new Db2Page("createTime", "create_time", null)
				,new Db2Page("createPeople", "create_people", null)
				,new Db2Page("cityName")
				,new Db2Page("dispath", "dispath", null)
				,new Db2Page("carType", "car_type", null)
				,new Db2Page("district", "district", null)
				,new Db2Page("stationName", "station_name", null)
				,new Db2Page("status", "status", null)
				,new Db2Page("remark", "remark", null)
		};
		JSONObject jObject = getJsonDatagridEasyUI(mapList, iCount.intValue(), db2Pages);
		return jObject;
	}

	@Override
	public JSONObject getAreaStationDatagrid(DataGrid dataGrid, String areaLineId) {
		StringBuffer sqlWhere = new StringBuffer();
		
		if(StringUtil.isNotEmpty(areaLineId)){
			sqlWhere.append(" and al.id = '" + areaLineId + "' ");
		}
		
		String p = sqlWhere.toString();
		
		StringBuffer sql = new StringBuffer();
		
		// 取出当前页的数据 
		sql.append(" select ast.id as station_id,asl.id,ast.name,ast.location,ast.create_time  "
				+ " from area_line al join area_station_line asl on al.id = asl.area_line_id join area_station ast on ast.id = asl.area_station_id ");
		
		// 取出总数据条数（为了分页处理, 如果不用分页，取iCount值的这个处理可以不要）
		String sqlCnt = "select count(*) from area_line al join area_station_line asl on al.id=asl.area_line_id join area_station ast on ast.id = asl.area_station_id ";
		if (StringUtil.isNotEmpty(p)) {
			sqlCnt += " where 1=1 " + sqlWhere;
			sql.append(sqlWhere);
		}
		
		Long iCount = getCountForJdbcParam(sqlCnt, null);
		
		List<Map<String, Object>> mapList = findForJdbc(sql.toString(), dataGrid.getPage(), dataGrid.getRows());
		// 将结果集转换成页面上对应的数据集
		Db2Page[] db2Pages = {
				new Db2Page("id")
				,new Db2Page("stationId", "station_id", null)
				,new Db2Page("name", "name", null)
				,new Db2Page("location", "location", null)
				//,new Db2Page("createTime", "create_time", null)
		};
		JSONObject jObject = getJsonDatagridEasyUI(mapList, iCount.intValue(), db2Pages);
		return jObject;
	}

}
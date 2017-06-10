package com.yhy.lin.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.dao.jdbc.JdbcDao;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yhy.lin.entity.BusStopInfoEntity;
import com.yhy.lin.service.BusStopInfoServiceI;
import com.yhy.lin.entity.LineInfoEntity;

import net.sf.json.JSONObject;

@Service("BusStopInfoServiceI")
@Transactional
public class BusStopInfoServiceImpl extends CommonServiceImpl implements BusStopInfoServiceI{
	
	@Autowired
	private JdbcDao jdbcDao;
	
	@Override
	public JSONObject getDatagrid(BusStopInfoEntity busStopInfo, DataGrid dataGrid){
		String sqlWhere = getBusSqlWhere(busStopInfo);
		// 取出总数据条数（为了分页处理, 如果不用分页，取iCount值的这个处理可以不要）
		String sqlCnt = "select count(*) from busstopinfo a ";
		if (!sqlWhere.isEmpty()) {
			sqlCnt += " where" + sqlWhere;
		}
		Long iCount = getCountForJdbcParam(sqlCnt, null);
		// 取出当前页的数据 
		String sql = "select * from busstopinfo a ";
		if (!sqlWhere.isEmpty()) {
			sql += " where" + sqlWhere;
		}
		
		//排序
		if(StringUtil.isNotEmpty(dataGrid.getSort()) && StringUtil.isNotEmpty(dataGrid.getOrder()) ){
			sql += " order by " + dataGrid.getSort() + " " + dataGrid.getOrder();
		}
		
		List<Map<String, Object>> mapList = findForJdbc(sql, dataGrid.getPage(), dataGrid.getRows());
		// 将结果集转换成页面上对应的数据集
				Db2Page[] db2Pages = {
						new Db2Page("id","id")
						,new Db2Page("name", "name", null)
						,new Db2Page("remark", "remark", null)
						,new Db2Page("status", "status", null)	
						,new Db2Page("createTime", "createTime", null)
						,new Db2Page("createPeople", "createPeople", null)
						,new Db2Page("stopLocation", "stopLocation", null)
						,new Db2Page("stationType", "stationType", null)
						,new Db2Page("cityId", "cityId", null)
						,new Db2Page("cityName", "cityName", null)
				};
		JSONObject jObject = getJsonDatagridEasyUI(mapList, iCount.intValue(), db2Pages);
		return jObject;
	}

	public String getBusSqlWhere(BusStopInfoEntity busStopInfo) {
		StringBuffer sqlWhere = new StringBuffer(" a.deleteFlag='0' ");

		if (StringUtil.isNotEmpty(busStopInfo.getName())) {
			
			sqlWhere.append(" and  a.name like '%"+busStopInfo.getName()+"%'");
		}
		if (StringUtil.isNotEmpty(busStopInfo.getCityName())) {
			sqlWhere.append(" and  a.cityName like '%" + busStopInfo.getCityName() + "%' ");
		}
		return sqlWhere.toString();
	}
	
	//根据线路lineInfoId，拿到对应线路的站点信息  
	@Override
	public JSONObject getDatagrid3(String lineInfoId, DataGrid dataGrid){
		String sqlWhere = getSqlWhere(lineInfoId);
		
		// 取出总数据条数（为了分页处理, 如果不用分页，取iCount值的这个处理可以不要）
		String sqlCnt = "select count(*) from busstopinfo a inner join line_busstop b on a.id=b.busStopsId  inner join  lineinfo c on b.lineId = c.id ";
		if (!sqlWhere.isEmpty()) {
			sqlCnt += " where" + sqlWhere;
		}
		Long iCount = getCountForJdbcParam(sqlCnt, null);
		
		// 取出当前页的数据 
		String sql = "select a.id as id,b.id as line_busstopId  ,a.name,b.siteOrder,a.status,b.arrivalTime from busstopinfo a inner join line_busstop b on a.id=b.busStopsId  inner join  lineinfo c on b.lineId = c.id ";
		if (!sqlWhere.isEmpty()) {
			sql += " where" + sqlWhere;
		}
		sql +=" order by b.siteOrder is NULL,b.siteOrder ";
		
		List<Map<String, Object>> mapList = findForJdbc(sql, dataGrid.getPage(), dataGrid.getRows());

		// 将结果集转换成页面上对应的数据集
		Db2Page[] db2Pages = {
				new Db2Page("id","id")
				,new Db2Page("line_busstopId", "line_busstopId", null)
				,new Db2Page("name", "name", null)
				,new Db2Page("siteOrder", "siteOrder", null)
				,new Db2Page("status", "status", null)
				,new Db2Page("arrivalTime", "arrivalTime", null)
		};
		JSONObject jObject = getJsonDatagridEasyUI(mapList, iCount.intValue(), db2Pages);
		return jObject;
	}
	public String getSqlWhere(String lineInfoId){
		StringBuffer sqlWhere = new StringBuffer(" a.deleteFlag='0' ");
		
		if(StringUtil.isNotEmpty(lineInfoId)){
			sqlWhere.append(" and  b.lineId = '"+lineInfoId+"' ");
		}
		return sqlWhere.toString();
	}
	
	//根据线路lineInfoId，拿到对应线路的未挂接的站点信息   import com.yhy.lin.entity.LineInfoEntity;
	@Override
	public JSONObject getDatagrid3a(BusStopInfoEntity busStopInfo,String lineInfoId, DataGrid dataGrid, String lineType,LineInfoEntity lineInfo){
		String sqlWhere = getSqlWhere2(busStopInfo, lineInfoId, lineType, lineInfo);
		// 取出总数据条数（为了分页处理, 如果不用分页，取iCount值的这个处理可以不要）
		//where c.lineId is NULL 这个条件是为了不重复添加站点
		String sqlCnt = "select count(*) from  busstopinfo a LEFT JOIN (select lineId,busStopsId from line_busstop b where  b.lineId ='"+lineInfoId+"') as c on a.id = c.busStopsId  where c.lineId is NULL ";
		if (!sqlWhere.isEmpty()) {
			sqlCnt +=  sqlWhere;
		}
		Long iCount = getCountForJdbcParam(sqlCnt, null);
		
		// 取出当前页的数据 
		String sql = "select a.id ,a.name,a.stopLocation,a.createTime,a.createPeople,a.remark from  busstopinfo a LEFT JOIN (select lineId,busStopsId from line_busstop b where  b.lineId ='"+lineInfoId+"') as c on a.id = c.busStopsId  where c.lineId is NULL";
		if (!sqlWhere.isEmpty()) {
			sql +=  sqlWhere;
		}
		System.out.println(sql.toString());
		List<Map<String, Object>> mapList = findForJdbc(sql, dataGrid.getPage(), dataGrid.getRows());
		// 将结果集转换成页面上对应的数据集
		Db2Page[] db2Pages = {
				new Db2Page("id","id")
				,new Db2Page("name", "name", null)
				,new Db2Page("stopLocation", "stopLocation", null)
				,new Db2Page("createTime", "createTime", null)
				,new Db2Page("createPeople", "createPeople", null)
				,new Db2Page("remark", "remark", null)
		};
		JSONObject jObject = getJsonDatagridEasyUI(mapList, iCount.intValue(), db2Pages);
		return jObject;
	}
	
	public String getSqlWhere2(BusStopInfoEntity busStopInfo,String lineInfoId, String lineType,LineInfoEntity lineInfo){
		StringBuffer sqlWhere = new StringBuffer(" and a.deleteFlag='0' "); 
		if(StringUtil.isNotEmpty(lineInfo.getCityId())){
			sqlWhere.append(" and  a.cityId = '"+lineInfo.getCityId()+"' ");
		}
		
		/**是否已挂接站点，0：未挂接      1：挂接公务车站点     2：挂接接送机站点 */
		//因为站点是公务车和接送机业务都能使用，公务车中一条线路使用了这个站点之后，其他公务车的线路就不能再使用了，但是机场线路可以使用，所以这里做了一个区分，看站点是挂在公务车类型下还是挂在接送机类型下
		if(StringUtil.isNotEmpty(lineType)){
			sqlWhere.append(" and  a.status != '"+lineType+"' ");
		}
		
		if(StringUtil.isNotEmpty(busStopInfo.getName())){
			sqlWhere.append(" and  a.name like '%" + busStopInfo.getName() + "%'");
		}
		
		if(StringUtil.isNotEmpty(busStopInfo.getStopLocation())){
			sqlWhere.append(" and  a.stopLocation like '%" + busStopInfo.getStopLocation() + "%'");
		}
		
		//线路类型 0：班车 1：包车 2：接机 3：送机 4：接火车 5：送火车
		//站点类型 0：普通站点    1：火车站点    2：飞机站点、
		//接送机业务只能选择机场站点
		switch (lineInfo.getType()) {
		case "2":
		case "3":
			sqlWhere.append(" and  a.station_type != '1'");
			break;
		case "4":
		case "5":
			sqlWhere.append(" and  a.station_type != '2'");
			break;
		default:
			break;
		}
		
		return sqlWhere.toString();
	}
	
}

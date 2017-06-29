package com.yhy.lin.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yhy.lin.app.entity.CarCustomerEntity;
import com.yhy.lin.service.CarCustomerServiceI;

import net.sf.json.JSONObject;

import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl.Db2Page;
import org.jeecgframework.core.util.StringUtil;

@Service("carCustomerService")
@Transactional
public class CarCustomerServiceImpl extends CommonServiceImpl implements CarCustomerServiceI {

	@Override
	public JSONObject getDatagrid(String id, DataGrid dataGrid) {
		// TODO Auto-generated method stub
		String sqlWhere = getSqlWhere(id);
		
		// 取出总数据条数（为了分页处理, 如果不用分页，取iCount值的这个处理可以不要）
		String sqlCnt = "select count(*) from car_customer c inner join customer_common_addr a on c.id = a.user_id inner join busstopinfo b on b.id = a.station_id";
		if (!sqlWhere.isEmpty()) {
			sqlCnt += " where" + sqlWhere;
		}
		
		Long iCount = getCountForJdbcParam(sqlCnt, null);
		
		// 取出当前页的数据 
		String sql = "select c.id,a.count,b.name,b.stopLocation from car_customer c,customer_common_addr a,busstopinfo b where c.id = a.user_id and b.id = a.station_id";
		if (!sqlWhere.isEmpty()) {
			sql += " and " + sqlWhere;
		}
		sql +=" order by a.count desc ";
		
		System.out.println(sql);
		
		List<Map<String, Object>> mapList = findForJdbc(sql, dataGrid.getPage(), dataGrid.getRows());
		// 将结果集转换成页面上对应的数据集
		Db2Page[] db2Pages = {
				new Db2Page("id","id")
				,new Db2Page("count", "count", null)
				,new Db2Page("name", "name", null)
				,new Db2Page("stopLocation", "stopLocation", null)
		};
		JSONObject jObject = getJsonDatagridEasyUI(mapList, iCount.intValue(), db2Pages);
		return jObject;
		/*
		if(StringUtil.isNotEmpty(customerId)){
			String sql="select c.customer_id,a.count,b.name,b.stopLocation from car_customer c,customer_common_addr a,busstopinfo b where c.customer_id="+customerId+"and c.customer_id = a.user_id and b.id = a.station_id ORDER BY a.count desc";
			
		}*/
	}
	
	public String getSqlWhere(String id){
		StringBuffer sqlWhere = new StringBuffer();
		
		if(StringUtil.isNotEmpty(id)){
			sqlWhere.append("  c.id = '"+id+"' ");
		}
		return sqlWhere.toString();
	}

	@Override
	public JSONObject getDatagrid2(CarCustomerEntity carCustomer, DataGrid dataGrid, String realName, String phone) {
				
			String sqlWhere = getSqlWhere2(realName,phone);
				
				// 取出总数据条数（为了分页处理, 如果不用分页，取iCount值的这个处理可以不要）
				String sqlCnt = "select count(*) from car_customer s";
				if (!sqlWhere.isEmpty()) {
					sqlCnt += " where" + sqlWhere;
				}
				
				Long iCount = getCountForJdbcParam(sqlCnt, null);
				
				// 取出当前页的数据 
				String sql = "select s.id,s.real_name,s.sex,s.phone,s.card_number,s.address,s.create_time,s.remark from car_customer s";
				if (!sqlWhere.isEmpty()) {
					sql += " where " + sqlWhere;
				}
				
				
				List<Map<String, Object>> mapList = findForJdbc(sql, dataGrid.getPage(), dataGrid.getRows());
				// 将结果集转换成页面上对应的数据集
				Db2Page[] db2Pages = {
						new Db2Page("id","id")
						,new Db2Page("realName", "real_name", null)
						,new Db2Page("sex", "sex", null)
						,new Db2Page("phone", "phone", null)
						,new Db2Page("cardNumber", "card_number", null)
						,new Db2Page("address", "address", null)
						,new Db2Page("createTime", "create_time", null)
						,new Db2Page("remark", "remark", null)
				};
				JSONObject jObject = getJsonDatagridEasyUI(mapList, iCount.intValue(), db2Pages);
				return jObject;
	}
	
	public String getSqlWhere2(String realName,String phone){
		StringBuffer sqlWhere = new StringBuffer();
		
		if(StringUtil.isNotEmpty(realName)){
			sqlWhere.append("  s.real_name like '%"+realName+"%' ");
			if(StringUtil.isNotEmpty(phone)){
				sqlWhere.append(" and s.phone like '%"+phone+"%' ");
			}
		}else{
			if(StringUtil.isNotEmpty(phone)){
				sqlWhere.append(" s.phone like '%"+phone+"%' ");
			}
		}
		
		return sqlWhere.toString();
	}

	
	
}
package com.yhy.lin.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yhy.lin.service.OpenCityServiceI;

import net.sf.json.JSONObject;

import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;

@Service("openCityService")
@Transactional
public class OpenCityServiceImpl extends CommonServiceImpl implements OpenCityServiceI {

	@Override
	public JSONObject getDatagrid(DataGrid dataGrid) {
		StringBuffer queryCondition = new StringBuffer(" where 1=1 ");
	    
//		if(StringUtil.isNotEmpty(name)){
//			queryCondition.append(" and name = '"+name+"' ");
//		}
//		
//		if(StringUtil.isNotEmpty(companyId)){
//			queryCondition.append(" and company_id = '"+companyId+"' ");
//		}else{
//			queryCondition.append(" and company_id like '" + orgCode +"%' ");
//		}
		
		// 取出总数据条数（为了分页处理, 如果不用分页，取iCount值的这个处理可以不要）
		String sqlCnt = "select count(*) from open_city  " + queryCondition.toString();
		Long iCount = getCountForJdbcParam(sqlCnt, null);
		
		// 取出当前页的数据 
		StringBuffer sql = new StringBuffer();
	    sql.append("select * from open_city  " + queryCondition.toString());
		
		System.out.println(sql.toString());
		List<Map<String, Object>> mapList = findForJdbc(sql.toString(), dataGrid.getPage(), dataGrid.getRows());
		// 将结果集转换成页面上对应的数据集
					Db2Page[] db2Pages = {
							new Db2Page("id", "id")
							,new Db2Page("provinceName", "province_name")
							,new Db2Page("cityName", "city_name")
							,new Db2Page("cityBusiness", "city_business", new MyDataExchangerBusiness2Ch())
							,new Db2Page("state", "state")
							,new Db2Page("remark", "remark")
					};
		JSONObject jObject = getJsonDatagridEasyUI(mapList, iCount.intValue(), db2Pages);
		return jObject;
		
	}
	
	//在id为空的情况下操作下的按钮不显示，这里进行转换之后在编辑的时候需要判断一下
	private class MyDataExchangerBusiness2Ch implements IMyDataExchanger {
		@Override
		public Object exchange(Object value) {
			if ("0".equals(value)) {
				return "接送机业务";
			} else if ("1".equals(value)) {
				return "接送火车业务";
			}else if ("0,1".equals(value) || "1,0".equals(value)) {
				return "接送机业务,接送火车业务";
			}
			return "";
		}
	}
	
}
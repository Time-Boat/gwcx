package com.yhy.lin.service.impl;

import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl.Db2Page;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yhy.lin.entity.ConductorEntity;
import com.yhy.lin.service.ConductorServiceI;

import net.sf.json.JSONObject;

@Service("ConductorServiceI")
@Transactional
public class ConductorServiceImpl extends CommonServiceImpl implements ConductorServiceI {

	@Override
	public JSONObject getDatagrid(DataGrid dataGrid, ConductorEntity conductors, String cr_bg,String cr_en) {
		// TODO Auto-generated method stub
		
		String  orgCode = ResourceUtil.getSessionUserName().getCurrentDepart().getOrgCode();
		
		StringBuffer queryCondition = new StringBuffer(" where d.delete_flag = '0'");
	    
		if(StringUtil.isNotEmpty(orgCode)){
			queryCondition.append(" and t.org_code like '"+orgCode+"%'");
		}
		if(StringUtil.isNotEmpty(conductors.getSex())){
			queryCondition.append(" and d.sex = '"+conductors.getSex()+"' ");
		}
		
		if(StringUtil.isNotEmpty(conductors.getName())){
			queryCondition.append(" and d.name like '%"+conductors.getName()+"%' ");
		}
		
		if(StringUtil.isNotEmpty(conductors.getPhoneNumber())){
			queryCondition.append(" and d.phoneNumber like '" + conductors.getPhoneNumber() +"%' ");
		}
		
		if(StringUtil.isNotEmpty(conductors.getPhoneNumber())){
			queryCondition.append(" and d.jurisdiction like '%" + conductors.getJurisdiction() +"%' ");
		}
		if(StringUtil.isNotEmpty(conductors.getStatus())){
			queryCondition.append(" and d.status ='" + conductors.getStatus() +"' ");
		}
		
		// 取出总数据条数（为了分页处理, 如果不用分页，取iCount值的这个处理可以不要）
		String sqlCnt = "select count(*) from conductor d LEFT JOIN t_s_depart t on t.ID= d.departId " + queryCondition.toString();
		Long iCount = getCountForJdbcParam(sqlCnt, null);
		
		// 取出当前页的数据 
		StringBuffer sql = new StringBuffer();
	    sql.append("select d.id,d.name,d.phoneNumber,d.jurisdiction,d.age,d.sex,d.create_date,d.status,d.delete_flag,d.create_user_id,u.username from conductor d LEFT JOIN t_s_depart t on t.ID= d.departId LEFT JOIN t_s_base_user u on u.ID=d.create_user_id" + queryCondition.toString());
		
		List<Map<String, Object>> mapList = findForJdbc(sql.toString(), dataGrid.getPage(), dataGrid.getRows());
		// 将结果集转换成页面上对应的数据集
					Db2Page[] db2Pages = {
							new Db2Page("id", "id")
							,new Db2Page("sex", "sex")
							,new Db2Page("phoneNumber", "phoneNumber")
							,new Db2Page("name", "name")
							,new Db2Page("age", "age")
							,new Db2Page("createDate", "create_date")
							,new Db2Page("createUserId", "create_user_id")
							,new Db2Page("username", "username")
							,new Db2Page("deleteFlag", "delete_flag")
							,new Db2Page("jurisdiction", "jurisdiction")
							,new Db2Page("status", "status")
					};
		JSONObject jObject = getJsonDatagridEasyUI(mapList, iCount.intValue(), db2Pages);
		return jObject;
	}
	
}
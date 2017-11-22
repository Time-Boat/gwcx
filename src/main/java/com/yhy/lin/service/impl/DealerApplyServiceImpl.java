package com.yhy.lin.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yhy.lin.app.util.AppUtil;
import com.yhy.lin.entity.LineInfoEntity;

import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl.Db2Page;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSUser;

@Service("dealerApplyService")
@Transactional
public class DealerApplyServiceImpl extends CommonServiceImpl implements DealerApplyServiceI {

	@Override
	public JSONObject getDatagrid(DataGrid dataGrid, String companyName, String phone, String applyPeople,String username) {
		
		List<Object> list = new ArrayList<>();
		
		StringBuffer sqlWhere = getWhere(companyName, phone, applyPeople,username, list);
		
		// 取出总数据条数（为了分页处理, 如果不用分页，取iCount值的这个处理可以不要）
		String sqlCnt = "select count(*) from dealer_apply d left join t_s_base_user u on d.responsible_user_id = u.id  "
				+ " LEFT JOIN t_s_user_org o on o.user_id=u.ID LEFT JOIN  t_s_depart t on o.org_id=t.ID "
				+ " where 1=1 ";
		
		Long iCount = getCountForJdbcParam(sqlCnt + sqlWhere.toString(), list.toArray());
		// 取出当前页的数据  
		String sql = "select d.*,u.username from dealer_apply d left join t_s_base_user u on d.responsible_user_id = u.id  "
				+ " LEFT JOIN t_s_user_org o on o.user_id=u.ID LEFT JOIN  t_s_depart t on o.org_id=t.ID "
				+ " where 1=1 ";
		
		List<Map<String, Object>> mapList = findForJdbcParam(sql + sqlWhere.toString(), dataGrid.getPage(), dataGrid.getRows(), list.toArray());
		// 将结果集转换成页面上对应的数据集
		Db2Page[] db2Pages = {
				new Db2Page("id","id")
				,new Db2Page("createTime", "create_time", null)
				,new Db2Page("companyName", "company_name", null)
				,new Db2Page("address", "address", null)
				,new Db2Page("phone", "phone", null)
				,new Db2Page("applyPeople", "apply_people", null)
				,new Db2Page("username", "username", null)
		};
		JSONObject jObject = getJsonDatagridEasyUI(mapList, iCount.intValue(), db2Pages);
		return jObject;
	}

	private StringBuffer getWhere(String companyName, String phone, String applyPeople,String username, List<Object> list) {
		
		String orgCode = ResourceUtil.getSessionUserName().getCurrentDepart().getOrgCode();
				
		StringBuffer sqlWhere = new StringBuffer();
		
		sqlWhere.append(" and t.org_code like '" + orgCode + "%'");
		
		if(StringUtil.isNotEmpty(companyName)){
			sqlWhere.append(" and company_name like ? ");
			list.add("%" + companyName + "%");
		}
		
		if(StringUtil.isNotEmpty(phone)){
			sqlWhere.append(" and phone like ? ");
			list.add("%" + phone + "%");
		}
		
		if(StringUtil.isNotEmpty(applyPeople)){
			sqlWhere.append(" and apply_people like ? ");
			list.add("%" + applyPeople + "%");
		}
		if(StringUtil.isNotEmpty(username)){
			sqlWhere.append(" and u.username like ? ");
			list.add("%" + username + "%");
		}
		return sqlWhere;
	}
	
	@Override
	public JSONObject getAttacheDatagrid(DataGrid dataGrid, String role) {
		
		String  orgCode = ResourceUtil.getSessionUserName().getCurrentDepart().getOrgCode();
		StringBuffer sql = new StringBuffer();
		StringBuffer sqlCnt = new StringBuffer();
		
		sqlCnt.append(" select count(*) from t_s_role r LEFT JOIN t_s_role_user ru on ru.roleid= "
				+ " r.ID LEFT JOIN t_s_base_user u on ru.userid=u.ID LEFT JOIN t_s_user_org g on g.user_id=u.ID LEFT JOIN t_s_depart d "
				+ " on g.org_id=d.ID where r.rolecode='" + role + "' and u.delete_flag='0' and u.status='1' and d.org_code like '"+orgCode+"%' ");
		Long iCount = getCountForJdbcParam(sqlCnt.toString(), null);
		
		sql.append(" select u.id,u.username,u.realname,d.departname,r.rolename from t_s_role r LEFT JOIN t_s_role_user ru on ru.roleid= "
				+ " r.ID LEFT JOIN t_s_base_user u on ru.userid=u.ID LEFT JOIN t_s_user_org g on g.user_id=u.ID LEFT JOIN t_s_depart d "
				+ " on g.org_id=d.ID where r.rolecode='" + role + "' and u.delete_flag='0' and u.status='1'  and d.org_code like '"+orgCode+"%' ");
		
		List<Map<String, Object>> mapList = findForJdbc(sql.toString() , dataGrid.getPage(), dataGrid.getRows());
		
		// 将结果集转换成页面上对应的数据集
		Db2Page[] db2Pages = {
				new Db2Page("id")
				,new Db2Page("username", "username", null)
				,new Db2Page("realname", "realname", null)
				,new Db2Page("departname", "departname", null)
				,new Db2Page("rolename", "rolename", null)
		};
				
		JSONObject jObject = getJsonDatagridEasyUI(mapList, iCount.intValue(), db2Pages);
		return jObject;
	}

}
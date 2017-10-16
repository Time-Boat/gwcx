package com.yhy.lin.service.impl;

import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl.Db2Page;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yhy.lin.app.util.AppGlobals;
import com.yhy.lin.entity.ConductorEntity;
import com.yhy.lin.service.ConductorServiceI;

import net.sf.json.JSONObject;

@Service("ConductorServiceI")
@Transactional
public class ConductorServiceImpl extends CommonServiceImpl implements ConductorServiceI {

	@Autowired
	private UserService userService;
	
	@Override
	public JSONObject getDatagrid(DataGrid dataGrid, ConductorEntity conductors, String cr_bg,String cr_en,String lineId,String username) {
		// TODO Auto-generated method stub
		
		TSUser user = ResourceUtil.getSessionUserName();
		 String orgCode = user.getCurrentDepart().getOrgCode();
		 String code="";
		 if(orgCode.length()>6){
			  code = orgCode.substring(0,6); 
		 }else{
			  code = orgCode; 
		 }
		 
		 String roles = userService.getUserRole(user);
		
		StringBuffer queryCondition = new StringBuffer(" where d.delete_flag = '0'");
		
		 String a[] = roles.split(",");
		 for (int i = 0; i < a.length; i++) {
			if(AppGlobals.TECHNICAL_MANAGER.equals(a[i])){
				queryCondition.append(" and t.org_code like '"+code+"%'");
				queryCondition.append(" and d.application_status!='-1' ");
			}
			if(AppGlobals.TECHNICAL_SPECIALIST.equals(a[i])){
				queryCondition.append(" and d.create_user_id='"+user.getId()+"'");
			}
		}
		 
		if(StringUtil.isNotEmpty(username)){
			queryCondition.append(" and u.username like '%"+username+"%'");
		}
		if(StringUtil.isNotEmpty(cr_bg)&&StringUtil.isNotEmpty(cr_en)){
			queryCondition.append(" and d.create_date between '"+cr_bg+"' and '"+cr_en+"'");
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
		
		if(StringUtil.isNotEmpty(lineId)){
			queryCondition.append(" and d.line_ids like '%" + lineId+"%' ");
		}
		if(StringUtil.isNotEmpty(conductors.getStatus())){
			queryCondition.append(" and d.status ='" + conductors.getStatus() +"' ");
		}
		
		if(StringUtil.isNotEmpty(conductors.getApplicationStatus())){
			queryCondition.append(" and d.application_status ='" + conductors.getApplicationStatus() +"' ");
		}
		if(StringUtil.isNotEmpty(conductors.getConductStatus())){
			queryCondition.append(" and d.conduct_status ='" + conductors.getConductStatus() +"' ");
		}
		
		// 取出总数据条数（为了分页处理, 如果不用分页，取iCount值的这个处理可以不要）
		String sqlCnt = "select count(*) from conductor d LEFT JOIN t_s_depart t on t.ID= d.departId LEFT JOIN t_s_base_user u on u.ID=d.create_user_id" + queryCondition.toString();
		Long iCount = getCountForJdbcParam(sqlCnt, null);
		
		// 取出当前页的数据 
		StringBuffer sql = new StringBuffer();
	    sql.append("select d.id,d.name,d.phoneNumber,d.jurisdiction,d.age,d.sex,d.create_date,d.status,d.delete_flag,d.conduct_status,d.application_status,d.apply_content,u.username "
	    		+ "from conductor d LEFT JOIN t_s_depart t on t.ID= d.departId LEFT JOIN t_s_base_user u on u.ID=d.create_user_id" + queryCondition.toString());
		
		List<Map<String, Object>> mapList = findForJdbc(sql.toString(), dataGrid.getPage(), dataGrid.getRows());
		// 将结果集转换成页面上对应的数据集
					Db2Page[] db2Pages = {
							new Db2Page("id", "id")
							,new Db2Page("sex", "sex")
							,new Db2Page("phoneNumber", "phoneNumber")
							,new Db2Page("name", "name")
							,new Db2Page("age", "age")
							,new Db2Page("createDate", "create_date")
							,new Db2Page("conductStatus", "conduct_status")
							,new Db2Page("applicationStatus", "application_status")
							,new Db2Page("applyContent", "apply_content")
							,new Db2Page("username", "username")
							,new Db2Page("deleteFlag", "delete_flag")
							,new Db2Page("jurisdiction", "jurisdiction")
							,new Db2Page("status", "status")
					};
		JSONObject jObject = getJsonDatagridEasyUI(mapList, iCount.intValue(), db2Pages);
		return jObject;
	}
	
}
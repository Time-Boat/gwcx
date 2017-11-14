package com.yhy.lin.service.impl;

import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yhy.lin.app.quartz.BussAnnotation;
import com.yhy.lin.app.util.AppGlobals;
import com.yhy.lin.entity.LineInfoEntity;
import com.yhy.lin.entity.LineinfoHistoryEntity;
import com.yhy.lin.service.LineInfoServiceI;
import com.yhy.lin.service.LineinfoHistoryServiceI;

import net.sf.json.JSONObject;

import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl.Db2Page;
import org.jeecgframework.core.util.StringUtil;

@Service("lineinfoHistoryService")
@Transactional
public class LineinfoHistoryServiceImpl extends CommonServiceImpl implements LineinfoHistoryServiceI {
	
	public JSONObject getDatagrid(LineinfoHistoryEntity lineInfo,String cityid,String startTime ,String endTime ,
			DataGrid dataGrid,String lstartTime_begin,String lstartTime_end,String lendTime_begin,String lendTime_end,
			String lineType,String username,String departname, String company) {
		String sqlWhere = ((LineinfoHistoryServiceI) AopContext.currentProxy()).getSqlWhere(lineInfo,cityid,startTime,endTime,lstartTime_begin,lstartTime_end,lendTime_begin,lendTime_end,lineType,username,departname,company);
		StringBuffer sql = new StringBuffer();
		// 取出总数据条数（为了分页处理, 如果不用分页，取iCount值的这个处理可以不要）
		String sqlCnt = " select count(*) from lineinfo_history a inner join t_s_depart b on a.departId =b.ID left join cities c on a.cityId = c.cityId left join busstopinfo d on d.id= "
				+ " a.startLocation left join busstopinfo e on e.id= a.endLocation LEFT JOIN t_s_base_user u on a.createUserId=u.ID LEFT JOIN t_s_user_org o on o.user_id=u.ID "
				+ " LEFT JOIN  t_s_depart t on o.org_id=t.ID,t_s_depart p where 1=1 and (case when LENGTH(t.org_code)<6 then t.org_code else substring(t.org_code,1,6) END)=p.org_code ";
		if (!sqlWhere.isEmpty()) {
			sqlCnt += sqlWhere;
		}
		Long iCount = getCountForJdbcParam(sqlCnt, null);
		// 取出当前页的数据 
		 sql.append("select c.cityId,c.city,a.id,a.name,a.startLocation,a.endLocation,a.createUserId,u.username,a.application_edit_status,a.application_edit_time,"
		 		+ " a.imageurl,a.type,a.status,a.remark,a.deleteFlag,a.createTime,a.createPeople,a.price,a.apply_content,a.application_edit_user_id,"
		 		+ " a.lineNumber,a.departId,a.lstartTime,a.lendTime,a.lineTimes,a.settledCompanyId,a.settledCompanyName,a.dispath,d.name as startname,e.name as endname,a.application_status,a.is_dealer_line,a.application_time,p.departname ");
		 sql.append(" from lineinfo_history a inner join t_s_depart b on a.departId =b.ID left join cities c on a.cityId = c.cityId left join busstopinfo d on d.id=a.startLocation left join busstopinfo e on e.id= "
		 		+ " a.endLocation LEFT JOIN t_s_base_user u on a.createUserId=u.ID LEFT JOIN t_s_user_org o on o.user_id=u.ID LEFT JOIN  t_s_depart t on o.org_id=t.ID,t_s_depart p where 1=1 and (case when "
		 		+ " LENGTH(t.org_code)<6 then t.org_code else substring(t.org_code,1,6) END)=p.org_code ");
		 
		if (!sqlWhere.isEmpty()) {
			sql.append(sqlWhere);
		}
		List<Map<String, Object>> mapList = findForJdbc(sql.toString(), dataGrid.getPage(), dataGrid.getRows());
		// 将结果集转换成页面上对应的数据集
		Db2Page[] db2Pages = {
				new Db2Page("id")
				,new Db2Page("name", "name", null)
				,new Db2Page("startLocation", "startname", null)
				,new Db2Page("endLocation", "endname", null)
				,new Db2Page("imageurl", "imageurl", null)
				,new Db2Page("type", "type", null)
				,new Db2Page("status", "status", null)
				,new Db2Page("remark", "remark", null)
				,new Db2Page("deleteFlag", "deleteFlag", null)
				,new Db2Page("createTime", "createTime", null)
				,new Db2Page("createPeople", "createPeople", null)
				,new Db2Page("createUserId", "createUserId", null)
				,new Db2Page("username", "username", null)
				,new Db2Page("departname", "departname", null)
				,new Db2Page("price", "price", null)
				,new Db2Page("applyContent", "apply_content", null)
				,new Db2Page("lstartTime", "lstartTime", null)
				,new Db2Page("lendTime", "lendTime", null)
				,new Db2Page("lineTimes", "lineTimes", null)
				,new Db2Page("settledCompanyId", "settledCompanyId", null)
				,new Db2Page("settledCompanyName", "settledCompanyName", null)
				,new Db2Page("city", "city", null)
				,new Db2Page("cityId", "cityId", null)
				,new Db2Page("dispath", "dispath", null)
				,new Db2Page("applicationStatus", "application_status", null)
				,new Db2Page("applicationTime", "application_time", null)
				,new Db2Page("applicationEditStatus", "application_edit_status", null)
				,new Db2Page("applicationEditTime", "application_edit_time", null)
				,new Db2Page("applicationEditUserId", "application_edit_user_id", null)
				,new Db2Page("isDealerLine", "is_dealer_line", null)
				
				
		};
		JSONObject jObject = getJsonDatagridEasyUI(mapList, iCount.intValue(), db2Pages);
		return jObject;
	}
	
	@BussAnnotation(orgType = {AppGlobals.PLATFORM_LINE_AUDIT, AppGlobals.OPERATION_MANAGER , AppGlobals.ORG_JOB_TYPE}, 
			objTableUserId = " a.createUserId ", orgTable="b", operationSql=" and a.application_edit_status in('1','2','3','4','5') ",auditSql = " and a.application_edit_status in('2','3','5')" )
	public String getSqlWhere(LineinfoHistoryEntity lineInfo,String cityid,String startTime,
			String endTime,String lstartTime_begin,String lstartTime_end,
			String lendTime_begin,String lendTime_end,String lineType,String username,String departname,String company){

		StringBuffer sqlWhere = new StringBuffer();
		
		if(StringUtil.isNotEmpty(lineInfo.getCreateUserId())){
			sqlWhere.append(" and a.createUserId = '"+lineInfo.getCreateUserId()+"' ");
		}
		if(StringUtil.isNotEmpty(lineInfo.getIsDealerLine())){
			sqlWhere.append(" and  a.is_dealer_line = '"+lineInfo.getIsDealerLine()+"'");
		}
		
		if(StringUtil.isNotEmpty(cityid)){
			sqlWhere.append(" and a.cityId = '"+cityid+"'");
		}
		sqlWhere.append(" and a.deleteFlag='0'");
		if(StringUtil.isNotEmpty(lineInfo.getName())){
			sqlWhere.append(" and  a.name like '%"+lineInfo.getName()+"%'");
		}
		if(StringUtil.isNotEmpty(username)){
			sqlWhere.append(" and u.username like '%"+username+"%'");
		}
		if(StringUtil.isNotEmpty(lineInfo.getType())){
			sqlWhere.append(" and a.type= '"+lineInfo.getType()+"'");
		}
		
		if(StringUtil.isNotEmpty(lineInfo.getStatus())){
			sqlWhere.append(" and a.status = '"+lineInfo.getStatus()+"'");
		}
		
		if(StringUtil.isNotEmpty(lineInfo.getApplicationEditStatus())){
			sqlWhere.append(" and a.application_edit_status = '"+lineInfo.getApplicationEditStatus()+"'");
		}
		
		if(StringUtil.isNotEmpty(departname)){
			sqlWhere.append(" and  p.departname like '%"+departname+"%'");
		}
		
		if(StringUtil.isNotEmpty(lineInfo.getStartLocation())){
			sqlWhere.append(" and d.name like '%"+lineInfo.getStartLocation()+"%'");
		}
		if(StringUtil.isNotEmpty(lineInfo.getEndLocation())){
			sqlWhere.append(" and  e.name like '%"+lineInfo.getEndLocation()+"%'");
		}
		if(StringUtil.isNotEmpty(startTime)&&StringUtil.isNotEmpty(endTime)){
			sqlWhere.append(" and a.createTime between '"+startTime+"' and '"+endTime+"'");
		}		
		if(StringUtil.isNotEmpty(lstartTime_begin)&&StringUtil.isNotEmpty(lstartTime_end)){
			sqlWhere.append(" and a.lstartTime between '"+lstartTime_begin+"' and '"+lstartTime_end+"'");
		}
		if(StringUtil.isNotEmpty(lendTime_begin)&&StringUtil.isNotEmpty(lendTime_end)){
			sqlWhere.append(" and a.lendTime between '"+lendTime_begin+"' and '"+lendTime_end+"'");
		}
		
		if(StringUtil.isNotEmpty(lineInfo.getType())){
			sqlWhere.append(" and a.type ='"+lineInfo.getType()+"'");
		}
		if(StringUtil.isNotEmpty(lineType)){//接送机的线路判断
			sqlWhere.append(" and a.type "+lineType);
		}
		sqlWhere.append(" and a.deleteFlag='0' and p.status='0' and a.version=(select MAX(version) from lineinfo_history where a.lineNumber=lineNumber)");
		
		return sqlWhere.toString();
	}
	
}
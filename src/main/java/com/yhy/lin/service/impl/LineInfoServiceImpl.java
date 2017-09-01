package com.yhy.lin.service.impl;

import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.dao.jdbc.JdbcDao;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yhy.lin.app.util.AppGlobals;
import com.yhy.lin.entity.LineInfoEntity;
import com.yhy.lin.service.LineInfoServiceI;

import net.sf.json.JSONObject;

@Service("LineInfoServiceI")
@Transactional//声明所有方法都需要事务管理
public class LineInfoServiceImpl extends CommonServiceImpl implements LineInfoServiceI {
	@Autowired
	private JdbcDao JdbcDao;
	
	@Override
	public JSONObject getDatagrid3(LineInfoEntity lineInfo,String cityid,String startTime ,String endTime ,DataGrid dataGrid,String lstartTime_begin,String lstartTime_end,String lendTime_begin,String lendTime_end,String lineType){
		String sqlWhere = getSqlWhere(lineInfo,cityid,startTime,endTime,lstartTime_begin,lstartTime_end,lendTime_begin,lendTime_end,lineType);
		StringBuffer sql = new StringBuffer();
		// 取出总数据条数（为了分页处理, 如果不用分页，取iCount值的这个处理可以不要）
		String sqlCnt = "select count(*) from lineinfo a inner join t_s_depart b on a.departId =b.ID ";
		if (!sqlWhere.isEmpty()) {
			sqlCnt += " where 1=1 " + sqlWhere;
		}
		Long iCount = getCountForJdbcParam(sqlCnt, null);
		// 取出当前页的数据 
		 sql.append("select c.cityId,c.city,a.id,a.name,a.startLocation,a.endLocation,a.createUserId,u.username,"
		 		+ "a.imageurl,a.type,a.status,a.remark,a.deleteFlag,a.createTime,a.createPeople,a.price,a.apply_content,"
		 		+ " a.lineNumber,a.departId,a.lstartTime,a.lendTime,a.lineTimes,a.settledCompanyId,a.settledCompanyName,a.dispath,d.name as startname,e.name as endname,a.application_status");
		 sql.append(" from lineinfo a inner join t_s_depart b on a.departId =b.ID left join cities c on a.cityId = c.cityId left join busstopinfo d on d.id=a.startLocation left join busstopinfo e on e.id=a.endLocation LEFT JOIN t_s_base_user u on a.createUserId=u.ID where 1=1 ");
		 
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
				,new Db2Page("username", "username", null)
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
				
				
		};
		JSONObject jObject = getJsonDatagridEasyUI(mapList, iCount.intValue(), db2Pages);
		return jObject;
	}

	public String getSqlWhere(LineInfoEntity lineInfo,String cityid,String startTime,
			String endTime,String lstartTime_begin,String lstartTime_end,
			String lendTime_begin,String lendTime_end,String lineType){

		TSDepart depart = ResourceUtil.getSessionUserName().getCurrentDepart();
		String orgCode = depart.getOrgCode();
		String orgType = depart.getOrgType();
		String userId = ResourceUtil.getSessionUserName().getId();
		StringBuffer sqlWhere = new StringBuffer();
		
		//判断当前的机构类型，如果是"岗位"类型，就需要加个userId等于当前用户的条件，确保各个专员之间只能看到自己的数据
		if(AppGlobals.ORG_JOB_TYPE.equals(orgType)){
			sqlWhere.append(" and a.createUserId = '" + userId + "' ");
		}
		
		if(StringUtil.isNotEmpty(orgCode)){
			sqlWhere.append(" and org_code like '"+orgCode+"%'");
		}
		if(StringUtil.isNotEmpty(cityid)){
			sqlWhere.append(" and a.cityId = '"+cityid+"'");
		}
		sqlWhere.append(" and a.deleteFlag='0'");
		if(StringUtil.isNotEmpty(lineInfo.getName())){
			sqlWhere.append(" and  a.name like '%"+lineInfo.getName()+"%'");
		}
		if(StringUtil.isNotEmpty(lineInfo.getCreatePeople())){
			sqlWhere.append(" and a.createPeople like '%"+lineInfo.getCreatePeople()+"%'");
		}
		
		if(StringUtil.isNotEmpty(lineInfo.getStartLocation())){
			sqlWhere.append(" and  a.name like '%"+lineInfo.getStartLocation()+"%'");
		}
		if(StringUtil.isNotEmpty(lineInfo.getEndLocation())){
			sqlWhere.append(" and  a.name like '%"+lineInfo.getEndLocation()+"%'");
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
		
		return sqlWhere.toString();
	}
	
	@Override
	public JSONObject getDatagrid(LineInfoEntity lineInfo,String startTime ,String endTime ,DataGrid dataGrid,String lstartTime_begin,String lstartTime_end,String lendTime_begin,String lendTime_end,String lineType){
		String sqlWhere = getSqlWhere2(lineInfo,startTime,endTime,lstartTime_begin,lstartTime_end,lendTime_begin,lendTime_end,lineType);
		StringBuffer sql = new StringBuffer();
		// 取出总数据条数（为了分页处理, 如果不用分页，取iCount值的这个处理可以不要）
		String sqlCnt = "select count(*) from lineinfo a  ";
		if (!sqlWhere.isEmpty()) {
			sqlCnt += " where 1=1 " + sqlWhere;
		}
		Long iCount = getCountForJdbcParam(sqlCnt, null);
		// 取出当前页的数据 
		 sql.append("select a.id,a.name,a.startLocation,a.endLocation,"
		 		+ " a.imageurl,a.type,a.status,a.remark,a.deleteFlag,a.createTime,a.createPeople,a.price,"
		 		+ " a.lineNumber,a.departId,a.lineTimes,a.settledCompanyId,a.settledCompanyName ");
		 sql.append(" from lineinfo a  where 1=1 ");
		 
		if (!sqlWhere.isEmpty()) {
			sql.append(sqlWhere);
		}
		List<Map<String, Object>> mapList = findForJdbc(sql.toString() , dataGrid.getPage(), dataGrid.getRows());
		// 将结果集转换成页面上对应的数据集
		Db2Page[] db2Pages = {
				new Db2Page("id")
				,new Db2Page("name", "name", null)
				,new Db2Page("startLocation", "startLocation", null)
				,new Db2Page("endLocation", "endLocation", null)
				,new Db2Page("imageurl", "imageurl", null)
				,new Db2Page("type", "type", null)
				,new Db2Page("status", "status", null)
				,new Db2Page("remark", "remark", null)
				,new Db2Page("deleteFlag", "deleteFlag", null)
				,new Db2Page("createTime", "createTime", null)
				,new Db2Page("createPeople", "createPeople", null)
				,new Db2Page("price", "price", null)
				,new Db2Page("lineTimes", "lineTimes", null)
				,new Db2Page("settledCompanyId", "settledCompanyId", null)
				,new Db2Page("settledCompanyName", "settledCompanyName", null)
				
		};
		JSONObject jObject = getJsonDatagridEasyUI(mapList, iCount.intValue(), db2Pages);
		return jObject;
	}
	
	public String getSqlWhere2(LineInfoEntity lineInfo,String startTime,
			String endTime,String lstartTime_begin,String lstartTime_end,
			String lendTime_begin,String lendTime_end,String lineType){
		String  settledCompanyId = ResourceUtil.getSessionUserName().getCurrentDepart().getId();
		StringBuffer sqlWhere = new StringBuffer();
		if(StringUtil.isNotEmpty(settledCompanyId)){
			sqlWhere.append(" and a.settledCompanyId = '"+settledCompanyId+"'");
		}
		sqlWhere.append(" and a.deleteFlag='0'");
		if(StringUtil.isNotEmpty(lineInfo.getName())){
			sqlWhere.append(" and  a.name like '%"+lineInfo.getName()+"%'");
		}
		if(StringUtil.isNotEmpty(lineInfo.getStartLocation())){
			sqlWhere.append(" and  a.name like '%"+lineInfo.getStartLocation()+"%'");
		}
		if(StringUtil.isNotEmpty(lineInfo.getEndLocation())){
			sqlWhere.append(" and  a.name like '%"+lineInfo.getEndLocation()+"%'");
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
		if(StringUtil.isNotEmpty(lineType)){
			sqlWhere.append(" and a.type "+lineType);
		}
		
		return sqlWhere.toString();
	}
	
	@Override
	public JSONObject getDatagrid2(LineInfoEntity lineInfos, DataGrid dataGrid, String ywlx){
		
		StringBuffer sql = new StringBuffer();
		// 取出总数据条数（为了分页处理, 如果不用分页，取iCount值的这个处理可以不要）
		String sqlCnt = "select count(*) from lineinfo a where a.deleteFlag='0' and type " + ywlx + " ";
		if(StringUtil.isNotEmpty(lineInfos.getName())){
			sqlCnt +=" and a.name like '%"+lineInfos.getName()+"%'";
		}
		
		Long iCount = getCountForJdbcParam(sqlCnt, null);
		// 取出当前页的数据 
		sql.append("select a.id,a.name ");
		sql.append(" from lineinfo a  where type " + ywlx + " and a.deleteFlag='0' ");
		if(StringUtil.isNotEmpty(lineInfos.getName())){
			sql.append(" and a.name like '%"+lineInfos.getName()+"%'");
		}
		List<Map<String, Object>> mapList = findForJdbc(sql.toString());
		// 将结果集转换成页面上对应的数据集
		Db2Page[] db2Pages = {
			new Db2Page("id")
			,new Db2Page("name", "name", null)
		};
		JSONObject jObject = getJsonDatagridEasyUI(mapList, iCount.intValue(), db2Pages);
		return jObject;
	}

	@Override
	public JSONObject getDatagrid4(LineInfoEntity lineInfo, DataGrid dataGrid) {
		
		String  orgCode = ResourceUtil.getSessionUserName().getCurrentDepart().getOrgCode();
		StringBuffer sql = new StringBuffer();
		StringBuffer sqlCnt = new StringBuffer();
		sqlCnt.append("select count(*) from t_s_role r LEFT JOIN t_s_role_user ru on ru.roleid="
				+ "r.ID LEFT JOIN t_s_base_user u on ru.userid=u.ID LEFT JOIN t_s_user_org g on g.user_id=u.ID LEFT JOIN t_s_depart d "
				+ "on g.org_id=d.ID where r.rolecode='operationS' and u.delete_flag='0' and u.status='1' and d.org_code like '"+orgCode+"%'");
		Long iCount = getCountForJdbcParam(sqlCnt.toString(), null);
		
		sql.append("select u.id,u.username,u.realname,d.departname,r.rolename from t_s_role r LEFT JOIN t_s_role_user ru on ru.roleid="
				+ "r.ID LEFT JOIN t_s_base_user u on ru.userid=u.ID LEFT JOIN t_s_user_org g on g.user_id=u.ID LEFT JOIN t_s_depart d "
				+ "on g.org_id=d.ID where r.rolecode='operationS' and u.delete_flag='0' and u.status='1' and d.org_code like '"+orgCode+"%'");
		
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

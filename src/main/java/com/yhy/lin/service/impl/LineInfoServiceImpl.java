package com.yhy.lin.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.service.UserService;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yhy.lin.app.quartz.BussAnnotation;
import com.yhy.lin.app.util.AppGlobals;
import com.yhy.lin.entity.LineInfoEntity;
import com.yhy.lin.entity.LineInfoView;
import com.yhy.lin.service.LineInfoServiceI;

import net.sf.json.JSONObject;

@Service("LineInfoServiceI")
@Transactional//声明所有方法都需要事务管理
public class LineInfoServiceImpl extends CommonServiceImpl implements LineInfoServiceI {
	
	@Autowired
	private UserService userService;
	
	@Override
	public JSONObject getDatagrid3(LineInfoEntity lineInfo,String cityid,String startTime ,String endTime ,DataGrid dataGrid,String lstartTime_begin,
			String lstartTime_end,String lendTime_begin,String lendTime_end,String lineType,String username,String departname, String company){
		String sqlWhere = ((LineInfoServiceI) AopContext.currentProxy()).getSqlWhere(lineInfo,cityid,startTime,endTime,lstartTime_begin,lstartTime_end,lendTime_begin,lendTime_end,lineType,username,departname,company);
		StringBuffer sql = new StringBuffer();
		// 取出总数据条数（为了分页处理, 如果不用分页，取iCount值的这个处理可以不要）
		String sqlCnt = " select count(*) from lineinfo a inner join t_s_depart b on a.departId =b.ID left join cities c on a.cityId = c.cityId left join busstopinfo d on d.id= "
				+ " a.startLocation left join busstopinfo e on e.id= a.endLocation LEFT JOIN t_s_base_user u on a.createUserId=u.ID LEFT JOIN t_s_user_org o on o.user_id=u.ID "
				+ " LEFT JOIN  t_s_depart t on o.org_id=t.ID,t_s_depart p where 1=1 and (case when LENGTH(t.org_code)<6 then t.org_code else substring(t.org_code,1,6) END)=p.org_code ";
		if (!sqlWhere.isEmpty()) {
			sqlCnt += sqlWhere;
		}
		Long iCount = getCountForJdbcParam(sqlCnt, null);
		// 取出当前页的数据 
		 sql.append("select c.cityId,c.city,a.id,a.name,a.startLocation,a.endLocation,a.createUserId,u.username, "
		 		+ " a.imageurl,a.type,a.status,a.remark,a.deleteFlag,a.createTime,a.createPeople,a.price,a.apply_content, "
		 		+ " a.lineNumber,a.departId,a.lstartTime,a.lendTime,a.lineTimes,a.settledCompanyId,a.settledCompanyName,a.dispath,d.name as startname,e.name as endname,a.application_status,a.is_dealer_line,a.application_time,p.departname ");
		 sql.append(" from lineinfo a inner join t_s_depart b on a.departId =b.ID left join cities c on a.cityId = c.cityId left join busstopinfo d on d.id=a.startLocation left join busstopinfo e on e.id= "
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
				,new Db2Page("isDealerLine", "is_dealer_line", null)
				
				
		};
		JSONObject jObject = getJsonDatagridEasyUI(mapList, iCount.intValue(), db2Pages);
		return jObject;
	}

	@BussAnnotation(orgType = {AppGlobals.PLATFORM_LINE_AUDIT, AppGlobals.OPERATION_MANAGER , AppGlobals.ORG_JOB_TYPE}, objTableUserId = " a.createUserId ", orgTable="b"
			, auditSql = " and  CASE WHEN a.status ='1' then a.application_status in('2','3','4','6') ELSE a.application_status in('1','2','3','4','5','6') END "
			, operationSql = " and a.application_status in('1','2','3','4','5','6') ")
	public String getSqlWhere(LineInfoEntity lineInfo,String cityid,String startTime,
			String endTime,String lstartTime_begin,String lstartTime_end,
			String lendTime_begin,String lendTime_end,String lineType,String username,String departname,String company){

		StringBuffer sqlWhere = new StringBuffer();

//		TSUser user = ResourceUtil.getSessionUserName();
//		String roles = userService.getUserRole(user);
//		TSDepart depart = user.getCurrentDepart();
//		
//		String orgCode = depart.getOrgCode();
//		String orgType = depart.getOrgType();
//		String userId = user.getId();
//		
//		String oc = user.getOrgCompany();
//
//		//是否有平台线路审核员权限
//		boolean hasPLA = false;
//		//循环用户角色列表
//		String a[] = roles.split(",");
//		for (int i = 0; i < a.length; i++) {
//			String role = a[i];
//			if(AppGlobals.PLATFORM_LINE_AUDIT.equals(role)){
//				sqlWhere.append(" and a.application_status in('2','3','4','6') ");
//				hasPLA = true;
//			}
//			if(AppGlobals.OPERATION_MANAGER.equals(role)){
//				sqlWhere.append(" and a.application_status in('1','2','3','4','5','6') ");
//			}
//		}
//		
//		//如果是平台线路审核员权限，则根据其选择的子公司来过滤筛选
//		if(hasPLA){
//			if(StringUtil.isNotEmpty(company) && StringUtil.isNotEmpty(oc)){
//				sqlWhere.append(" and b.org_code like '" + company + "%' ");
//			}else{
//				sqlWhere.append("and ( 1=2 ");
//				
//				String[] ocArr = oc.split(",");
//				
//				for (int i = 0; i < ocArr.length; i++) {
//					sqlWhere.append(" or b.org_code like '"+ocArr[i]+"%' ");
//				}
//				sqlWhere.append(")");
//			}
//		} else {
//			sqlWhere.append(" and b.org_code like '"+orgCode+"%'");
//		}
//		
//		//判断当前的机构类型，如果是"岗位"类型，就需要加个userId等于当前用户的条件，确保各个专员之间只能看到自己的数据
//		if(AppGlobals.ORG_JOB_TYPE.equals(orgType)){
//			sqlWhere.append(" and a.createUserId = '" + userId + "' ");
//		}
		
		if(StringUtil.isNotEmpty(lineInfo.getCreateUserId())){
			sqlWhere.append(" and a.createUserId = '"+lineInfo.getCreateUserId()+"' ");
		}
		
		
		if(StringUtil.isNotEmpty(cityid)){
			sqlWhere.append(" and a.cityId = '"+cityid+"'");
		}
		sqlWhere.append(" and a.deleteFlag='0'");
		if(StringUtil.isNotEmpty(lineInfo.getName())){
			sqlWhere.append(" and  a.name like '%"+lineInfo.getName()+"%'");
		}
		
		if(StringUtil.isNotEmpty(lineInfo.getIsDealerLine())){
			sqlWhere.append(" and  a.is_dealer_line = '"+lineInfo.getIsDealerLine()+"'");
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
		
		if(StringUtil.isNotEmpty(lineInfo.getApplicationStatus())){
			sqlWhere.append(" and a.application_status = '"+lineInfo.getApplicationStatus()+"'");
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
		sqlWhere.append(" and a.deleteFlag='0' and p.status='0'");
		
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
		
		String  orgCode = ResourceUtil.getSessionUserName().getCurrentDepart().getOrgCode();
		String code="";
		if(orgCode.length()>6){
			code = orgCode.substring(0,6);
		}else{
			code=orgCode;
		}
		
		StringBuffer sql = new StringBuffer();
		// 取出总数据条数（为了分页处理, 如果不用分页，取iCount值的这个处理可以不要）
		String sqlCnt = "select count(*) from lineinfo a LEFT JOIN t_s_depart t on a.departId = t.ID where a.deleteFlag='0' and a.status='0' and t.org_code like '"+code+"%' and type " + ywlx + " ";
		if(StringUtil.isNotEmpty(lineInfos.getName())){
			sqlCnt +=" and a.name like '%"+lineInfos.getName()+"%'";
		}
		
		Long iCount = getCountForJdbcParam(sqlCnt, null);
		// 取出当前页的数据 
		sql.append("select a.id,a.name ");
		sql.append(" from lineinfo a LEFT JOIN t_s_depart t on a.departId = t.ID where type " + ywlx + " and a.deleteFlag='0' and a.status='0' and t.org_code like '"+code+"%'");
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
	
	
	@Override
	public LineInfoView getDetailHistory(String id) {
		
		StringBuffer sql = new StringBuffer();
		sql.append("select c.cityId,c.city,a.id,a.name,a.startLocation,a.endLocation,a.createUserId,u.username,a.imageurl,a.type,"
				+ "a.status,a.remark,a.deleteFlag,a.createTime,a.createPeople,a.price,a.apply_content,a.lineNumber,a.departId,"
				+ "a.lstartTime,a.lendTime,a.lineTimes,a.settledCompanyId,a.settledCompanyName,a.dispath,d.name as startname,e.name as "
				+ "endname,a.application_status,p.departname,a.application_time,a.trial_reason,a.review_reason,a.first_application_time,"
				+ "a.last_application_time,a.application_user_id,a.first_application_user,a.last_application_user from lineinfo_history a inner join t_s_depart b on a.departId =b.ID left join "
				+ "cities c on a.cityId = c.cityId left join busstopinfo d on d.id=a.startLocation left join busstopinfo e on e.id="
				+ "a.endLocation LEFT JOIN t_s_base_user u on a.createUserId=u.ID LEFT JOIN t_s_user_org o on o.user_id=u.ID LEFT JOIN "
				+ "t_s_depart t on o.org_id=t.ID,t_s_depart p where 1=1 and (case when LENGTH(t.org_code)<6 then t.org_code else "
				+ "substring(t.org_code,1,6) END)=p.org_code and a.id='"+id+"'");
		
		List<Object[]> list = findListbySql(sql.toString());
		LineInfoView lineInfoView = new LineInfoView();
		if (list.size() > 0) {
			Object[] obj = list.get(0);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				lineInfoView.setCityId(String.valueOf(obj[0]));
				lineInfoView.setCityName(String.valueOf(obj[1]));
				lineInfoView.setId(String.valueOf(obj[2]));
				lineInfoView.setName(String.valueOf(obj[3]));
				lineInfoView.setStartLocation(String.valueOf(obj[4]));
				lineInfoView.setEndLocation(String.valueOf(obj[5]));
				lineInfoView.setCreateUserId(String.valueOf(obj[6]));
				lineInfoView.setCreatePeople(String.valueOf(obj[7]));
				lineInfoView.setImageurl(String.valueOf(obj[8]));
				lineInfoView.setType(String.valueOf(obj[9]));
				lineInfoView.setStatus(String.valueOf(obj[10]));
				lineInfoView.setRemark(String.valueOf(obj[11]));
				lineInfoView.setDeleteFlag((short)Integer.parseInt(obj[12].toString()));
				if (obj[13] != null) {
					lineInfoView.setCreateTime(sdf.parse(obj[13].toString()));
				}
				lineInfoView.setCreatePeople(String.valueOf(obj[14]));
				lineInfoView.setPrice(new BigDecimal(obj[15].toString()));
				lineInfoView.setApplyContent(String.valueOf(obj[16]));
				lineInfoView.setLineNumber(String.valueOf(obj[17]));
				lineInfoView.setDepartId(String.valueOf(obj[18]));
				if (obj[19] != null) {
					lineInfoView.setLstartTime(sdf.parse(obj[19].toString()));
				}
				if (obj[20] != null) {
					lineInfoView.setLendTime(sdf.parse(obj[20].toString()));
				}
				lineInfoView.setLineTimes(String.valueOf(obj[21]));
				lineInfoView.setSettledCompanyId(String.valueOf(obj[22]));
				lineInfoView.setSettledCompanyName(String.valueOf(obj[23]));
				lineInfoView.setDispath(String.valueOf(obj[24]));
				lineInfoView.setStartName(String.valueOf(obj[25]));
				lineInfoView.setEndName(String.valueOf(obj[26]));
				lineInfoView.setApplicationStatus(String.valueOf(obj[27]));
				lineInfoView.setCompanyName(String.valueOf(obj[28]));
				if (obj[29] != null) {
					lineInfoView.setApplicationTime(sdf.parse(obj[29].toString()));
				}
				if (obj[30] != null) {
					lineInfoView.setTrialReason(String.valueOf(obj[30]));
				}
				if (obj[31] != null) {
					lineInfoView.setReviewReason(String.valueOf(obj[31]));
				}
				if (obj[32] != null) {
					lineInfoView.setFirstAuditDate(sdf.parse(obj[32].toString()));
				}
				if (obj[33] != null) {
					lineInfoView.setLastAuditDate(sdf.parse(obj[33].toString()));
				}
				if (obj[34] != null) {
					lineInfoView.setApplicationUserid(String.valueOf(obj[34]));
				}
				if (obj[35] != null) {
					lineInfoView.setFirstApplicationUser(String.valueOf(obj[35]));
				}
				if (obj[36] != null) {
					lineInfoView.setLastApplicationUser(String.valueOf(obj[36]));
				}
				
			} catch (Exception e) {
			}
			}
		return lineInfoView;
	}

	@Override
	public LineInfoView getDetail(String id) {
		
		StringBuffer sql = new StringBuffer();
		/*
		 * sql.append(
		 * "select a.id,a.order_id as orderId,a.order_type as orderType,a.order_status as orderStatus,a.order_flightnumber as orderFlightnumber,a.order_starting_station_id,a.order_terminus_station_id,"
		 * ); sql.append(
		 * "a.order_startime as orderStartime,a.order_expectedarrival as orderExpectedarrival,a.order_unitprice as orderUnitprice,a.order_numbers as orderNumbers,a.order_paytype as orderPaytype,a.order_contactsname as orderContactsname,"
		 * ); sql.append(
		 * "a.order_contactsmobile as orderContactsmobile,a.order_paystatus as orderPaystatus,a.order_trainnumber as orderTrainnumber,a.order_totalPrice as orderTotalPrice,d.name as driverName,d.phoneNumber as driverMobile,c.licence_plate as licencePlate,c.status as CarStatus,a.applicationTime "
		 * ); sql.append(
		 * " from transferorder a left join order_linecardiver b on a.id = b .id left join car_info c on b.licencePlateId =c.id left join driversinfo d on b.driverId =d.id "
		 * );
		 */
		sql.append("select c.cityId,c.city,a.id,a.name,a.startLocation,a.endLocation,a.createUserId,u.username,a.imageurl,a.type,"
				+ "a.status,a.remark,a.deleteFlag,a.createTime,a.createPeople,a.price,a.apply_content,a.lineNumber,a.departId,"
				+ "a.lstartTime,a.lendTime,a.lineTimes,a.settledCompanyId,a.settledCompanyName,a.dispath,d.name as startname,e.name as "
				+ "endname,a.application_status,p.departname,a.application_time,a.trial_reason,a.review_reason,a.first_application_time,"
				+ "a.last_application_time,a.application_user_id,a.first_application_user,a.last_application_user from lineinfo a inner join t_s_depart b on a.departId =b.ID left join "
				+ "cities c on a.cityId = c.cityId left join busstopinfo d on d.id=a.startLocation left join busstopinfo e on e.id="
				+ "a.endLocation LEFT JOIN t_s_base_user u on a.createUserId=u.ID LEFT JOIN t_s_user_org o on o.user_id=u.ID LEFT JOIN "
				+ "t_s_depart t on o.org_id=t.ID,t_s_depart p where 1=1 and (case when LENGTH(t.org_code)<6 then t.org_code else "
				+ "substring(t.org_code,1,6) END)=p.org_code and a.id='"+id+"'");
		
		List<Object[]> list = findListbySql(sql.toString());
		LineInfoView lineInfoView = new LineInfoView();
		if (list.size() > 0) {
			Object[] obj = list.get(0);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				lineInfoView.setCityId(String.valueOf(obj[0]));
				lineInfoView.setCityName(String.valueOf(obj[1]));
				lineInfoView.setId(String.valueOf(obj[2]));
				lineInfoView.setName(String.valueOf(obj[3]));
				lineInfoView.setStartLocation(String.valueOf(obj[4]));
				lineInfoView.setEndLocation(String.valueOf(obj[5]));
				lineInfoView.setCreateUserId(String.valueOf(obj[6]));
				lineInfoView.setCreatePeople(String.valueOf(obj[7]));
				lineInfoView.setImageurl(String.valueOf(obj[8]));
				lineInfoView.setType(String.valueOf(obj[9]));
				lineInfoView.setStatus(String.valueOf(obj[10]));
				lineInfoView.setRemark(String.valueOf(obj[11]));
				lineInfoView.setDeleteFlag((short)Integer.parseInt(obj[12].toString()));
				if (obj[13] != null) {
					lineInfoView.setCreateTime(sdf.parse(obj[13].toString()));
				}
				lineInfoView.setCreatePeople(String.valueOf(obj[14]));
				lineInfoView.setPrice(new BigDecimal(obj[15].toString()));
				lineInfoView.setApplyContent(String.valueOf(obj[16]));
				lineInfoView.setLineNumber(String.valueOf(obj[17]));
				lineInfoView.setDepartId(String.valueOf(obj[18]));
				if (obj[19] != null) {
					lineInfoView.setLstartTime(sdf.parse(obj[19].toString()));
				}
				if (obj[20] != null) {
					lineInfoView.setLendTime(sdf.parse(obj[20].toString()));
				}
				lineInfoView.setLineTimes(String.valueOf(obj[21]));
				lineInfoView.setSettledCompanyId(String.valueOf(obj[22]));
				lineInfoView.setSettledCompanyName(String.valueOf(obj[23]));
				lineInfoView.setDispath(String.valueOf(obj[24]));
				lineInfoView.setStartName(String.valueOf(obj[25]));
				lineInfoView.setEndName(String.valueOf(obj[26]));
				lineInfoView.setApplicationStatus(String.valueOf(obj[27]));
				lineInfoView.setCompanyName(String.valueOf(obj[28]));
				if (obj[29] != null) {
					lineInfoView.setApplicationTime(sdf.parse(obj[29].toString()));
				}
				if (obj[30] != null) {
					lineInfoView.setTrialReason(String.valueOf(obj[30]));
				}
				if (obj[31] != null) {
					lineInfoView.setReviewReason(String.valueOf(obj[31]));
				}
				if (obj[32] != null) {
					lineInfoView.setFirstAuditDate(sdf.parse(obj[32].toString()));
				}
				if (obj[33] != null) {
					lineInfoView.setLastAuditDate(sdf.parse(obj[33].toString()));
				}
				if (obj[34] != null) {
					lineInfoView.setApplicationUserid(String.valueOf(obj[34]));
				}
				if (obj[35] != null) {
					lineInfoView.setFirstApplicationUser(String.valueOf(obj[35]));
				}
				if (obj[36] != null) {
					lineInfoView.setLastApplicationUser(String.valueOf(obj[36]));
				}
				
			} catch (Exception e) {
			}
			}
		
		return lineInfoView;
	}
	
}

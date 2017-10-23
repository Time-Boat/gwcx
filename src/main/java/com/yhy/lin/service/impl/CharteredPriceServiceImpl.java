package com.yhy.lin.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yhy.lin.entity.CharteredPriceEntity;
import com.yhy.lin.service.CharteredPriceServiceI;

import net.sf.json.JSONObject;

import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.StringUtil;

@Service("charteredPriceService")
@Transactional
public class CharteredPriceServiceImpl extends CommonServiceImpl implements CharteredPriceServiceI {

	@Override
	public JSONObject getDatagrid(DataGrid dataGrid, CharteredPriceEntity charteredPrice, String cityID) {
		String sqlWhere =getSqlWhere(charteredPrice,cityID);
		
		StringBuffer sql = new StringBuffer();
		// 取出总数据条数（为了分页处理, 如果不用分页，取iCount值的这个处理可以不要）
		String sqlCnt = "select COUNT(*) from chartered_price c LEFT JOIN cities ci on c.city_id=ci.cityId LEFT JOIN t_s_base_user u on c.create_user_id=u.ID LEFT JOIN t_s_base_user b on c.modify_user=b.ID";
		if (!sqlWhere.isEmpty()) {
			sqlCnt += sqlWhere;
		}
		
		sql.append("select c.id,c.car_type,c.package_id,c.initiate_price,c.exceed_km_price,c.exceed_time_price,c.city_id,c.empty_return,"
				+ "c.create_user_id,c.create_time,c.midify_time,c.modify_user,c.delete_flag,c.status,c.remark,c.apply_user,c.apply_type,"
				+ "c.apply_date,c.audit_user,c.audit_status,c.audit_date,c.reject_reason,c.last_audit_user,c.last_audit_date,"
				+ "c.last_reject_reason,ci.city,u.username,b.username as modify_user_name,cp.name as packageName from chartered_price c LEFT JOIN cities ci on c.city_id="
				+ "ci.cityId LEFT JOIN t_s_base_user u on c.create_user_id=u.ID LEFT JOIN t_s_base_user b on c.modify_user=b.ID LEFT JOIN chartered_package cp on cp.id=c.package_id");
		Long iCount = getCountForJdbcParam(sqlCnt, null);
		
		if (!sqlWhere.isEmpty()) {
			sql.append(sqlWhere);
		}
		List<Map<String, Object>> mapList = findForJdbc(sql.toString(), dataGrid.getPage(), dataGrid.getRows());
		// 将结果集转换成页面上对应的数据集
		Db2Page[] db2Pages = {
				new Db2Page("id")
				,new Db2Page("carType", "car_type", null)
				,new Db2Page("packageId", "package_id", null)
				,new Db2Page("packageName", "packageName", null)
				,new Db2Page("initiatePrice", "initiate_price", null)
				,new Db2Page("exceedKmPrice", "exceed_km_price", null)
				,new Db2Page("exceedTimePrice", "exceed_time_price", null)
				,new Db2Page("status", "status", null)
				,new Db2Page("cityId", "city_id", null)
				,new Db2Page("emptyReturn", "empty_return", null)
				,new Db2Page("createTime", "create_time", null)
				,new Db2Page("createUserId", "create_user_id", null)
				,new Db2Page("username", "username", null)
				,new Db2Page("midifyTime", "midify_time", null)
				,new Db2Page("modifyUser", "modify_user", null)
				,new Db2Page("modifyUserName", "modify_user_name", null)
				,new Db2Page("deleteFlag", "delete_flag", null)
				,new Db2Page("remark", "remark", null)
				,new Db2Page("applyUser", "apply_user", null)
				,new Db2Page("applyType", "apply_type", null)
				,new Db2Page("applyDate", "apply_date", null)
				,new Db2Page("auditUser", "audit_user", null)
				,new Db2Page("auditStatus", "audit_status", null)
				,new Db2Page("auditDate", "audit_date", null)
				,new Db2Page("rejectReason", "reject_reason", null)
				,new Db2Page("lastAuditUser", "last_audit_user", null)
				,new Db2Page("lastAuditDate", "last_audit_date", null)
				,new Db2Page("lastRejectReason", "last_reject_reason", null)
				,new Db2Page("city", "city", null)
				
		};
		
		JSONObject jObject = getJsonDatagridEasyUI(mapList, iCount.intValue(), db2Pages);
		return jObject;
	}
	public String getSqlWhere(CharteredPriceEntity charteredPrice,String cityID){
		
		StringBuffer sqlWhere = new StringBuffer(" where c.delete_flag ='0'");
		if(StringUtil.isNotEmpty(cityID)){
			sqlWhere.append(" and c.city_id = '"+cityID+"'");
		}
		if(StringUtil.isNotEmpty(charteredPrice.getCarType())){
			sqlWhere.append(" and c.car_type = '"+charteredPrice.getCarType()+"'");
		}
		if(StringUtil.isNotEmpty(charteredPrice.getAuditStatus())){
			sqlWhere.append(" and c.audit_status = '"+charteredPrice.getAuditStatus()+"'");
		}
		if(StringUtil.isNotEmpty(charteredPrice.getStatus())){
			sqlWhere.append(" and c.status = '"+charteredPrice.getStatus()+"'");
		}
		
		return sqlWhere.toString();
	}
	
}
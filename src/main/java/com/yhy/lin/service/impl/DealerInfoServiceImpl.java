package com.yhy.lin.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yhy.lin.app.util.AppGlobals;
import com.yhy.lin.entity.DealerInfoEntity;
import com.yhy.lin.service.DealerInfoServiceI;

import net.sf.json.JSONObject;

import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;

@Service("dealerInfoService")
@Transactional
public class DealerInfoServiceImpl extends CommonServiceImpl implements DealerInfoServiceI {

	@Override
	public JSONObject getDatagrid(DataGrid dataGrid, DealerInfoEntity dealerInfo,String username) {
		String sqlWhere = getWhere(dealerInfo,username);
		// 取出总数据条数（为了分页处理, 如果不用分页，取iCount值的这个处理可以不要）
		String sqlCnt = "select count(*) from dealer_info d join t_s_base_user u on d.create_user_id = u.id join t_s_depart t on t.id = d.departId ";
		if (!sqlWhere.isEmpty()) {
			sqlCnt += sqlWhere;
		}
		Long iCount = getCountForJdbcParam(sqlCnt, null);
		// 取出当前页的数据 
		String sql = "select d.id,d.account,d.create_date,d.QR_code_url,d.scan_count,d.phone,d.manager,d.position,d.bank_account,d.status,"
				+ " d.audit_user,d.audit_date,d.audit_status,d.last_audit_user,d.last_audit_date,d.last_audit_status,d.commit_apply_date,d.apply_type,u.username "
				+ " from dealer_info d join t_s_base_user u on d.create_user_id = u.id join t_s_depart t on t.id = d.departId ";
		if (!sqlWhere.isEmpty()) {
			sql += sqlWhere;
		}
		
		//排序   大小写问题报错
		/*if(StringUtil.isNotEmpty(dataGrid.getSort()) && StringUtil.isNotEmpty(dataGrid.getOrder()) ){
			sql += " order by " + dataGrid.getSort() + " " + dataGrid.getOrder();
		}*/
		
		List<Map<String, Object>> mapList = findForJdbc(sql, dataGrid.getPage(), dataGrid.getRows());
		// 将结果集转换成页面上对应的数据集
		Db2Page[] db2Pages = {
				new Db2Page("id","id")
				,new Db2Page("account", "account", null)
				,new Db2Page("createDate", "create_date", null)
				,new Db2Page("scanCount", "scan_count", null)
				,new Db2Page("phone", "phone", null)
				,new Db2Page("qrCodeUrl", "QR_code_url", null)
				,new Db2Page("manager", "manager", null)
				,new Db2Page("bankAccount", "bank_account", null)
				,new Db2Page("username", "username", null)
				,new Db2Page("position", "position", null)
				,new Db2Page("auditUser", "audit_user", null)
				,new Db2Page("auditDate", "audit_date", null)
				,new Db2Page("auditStatus", "audit_status", null)
				,new Db2Page("lastAuditUser", "last_audit_user", null)
				,new Db2Page("lastAuditDate", "last_audit_date", null)
				,new Db2Page("lastAuditStatus", "last_audit_status", null)
				,new Db2Page("commitApplyDate", "commit_apply_date", null)
				,new Db2Page("applyType", "apply_type", null)
				,new Db2Page("status", "status", null)
		};
		JSONObject jObject = getJsonDatagridEasyUI(mapList, iCount.intValue(), db2Pages);
		return jObject;
	}
	
	public String getWhere(DealerInfoEntity dealerInfo,String username) {
		
		StringBuffer sql = new StringBuffer(" where 1=1 ");
		
		if(StringUtil.isNotEmpty(dealerInfo.getAccount())){
			sql.append(" and d.account like '%" + dealerInfo.getAccount() + "%' ");
		}
		if(StringUtil.isNotEmpty(username)){
			sql.append(" and u.username like '%" + username + "%' ");
		}
		
		if(StringUtil.isNotEmpty(dealerInfo.getStatus())){
			sql.append(" and d.status = '" + dealerInfo.getStatus() + "' ");
		}
		
		if(StringUtil.isNotEmpty(dealerInfo.getAuditStatus())){
			sql.append(" and d.audit_status = '" + dealerInfo.getAuditStatus() + "' ");
		}
		
		TSDepart depart = ResourceUtil.getSessionUserName().getCurrentDepart();
		String orgCode = depart.getOrgCode();
		String orgType = depart.getOrgType();
		String userId = ResourceUtil.getSessionUserName().getId();
		
		//判断当前的机构类型，如果是"岗位"类型，就需要加个userId等于当前用户的条件，确保各个专员之间只能看到自己的数据
		if(AppGlobals.ORG_JOB_TYPE.equals(orgType)){
			sql.append(" and d.create_user_id = '" + userId + "' ");
		}
		
		sql.append(" and t.org_code like '" + orgCode + "%' ");

		return sql.toString();
	}
	
}
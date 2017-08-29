package com.yhy.lin.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yhy.lin.entity.TransferorderEntity;
import com.yhy.lin.service.DealerInfoServiceI;

import net.sf.json.JSONObject;

import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl.Db2Page;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;

@Service("dealerInfoService")
@Transactional
public class DealerInfoServiceImpl extends CommonServiceImpl implements DealerInfoServiceI {

	@Override
	public JSONObject getDatagrid(DataGrid dataGrid, boolean hasPermission) {
		String sqlWhere = getWhere(hasPermission);
		// 取出总数据条数（为了分页处理, 如果不用分页，取iCount值的这个处理可以不要）
		String sqlCnt = "select count(*) from dealer_info d join t_s_base_user u on d.create_user_id = u.id join t_s_depart t on t.id = d.departId ";
		if (!sqlWhere.isEmpty()) {
			sqlCnt += sqlWhere;
		}
		Long iCount = getCountForJdbcParam(sqlCnt, null);
		// 取出当前页的数据 
		String sql = "select d.id,d.account,d.create_date,d.QR_code_url,d.scan_count,d.phone,d.manager,d.position,d.bank_account,d.status,u.username "
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
				,new Db2Page("status", "status", null)	
		};
		JSONObject jObject = getJsonDatagridEasyUI(mapList, iCount.intValue(), db2Pages);
		return jObject;
	}
	
	public String getWhere(boolean hasPermission) {

		String orgCode = ResourceUtil.getSessionUserName().getCurrentDepart().getOrgCode();
		String userId = ResourceUtil.getSessionUserName().getId();
		
		StringBuffer sql = new StringBuffer(" where 1=1 ");
		
		//看是否是商务经理
		if(hasPermission){
			//看到所有的渠道商
			//sql.append(" and a.first_audit_status = '1' ");
		}else{
			// 添加了权限
			sql.append(" and d.create_user_id = '" + userId + "' ");
		}
		sql.append(" and t.org_code like '" + orgCode + "%' ");

		return sql.toString();
	}
	
}
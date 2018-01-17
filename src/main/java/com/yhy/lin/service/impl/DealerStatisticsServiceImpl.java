package com.yhy.lin.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yhy.lin.app.entity.CarCustomerEntity;
import com.yhy.lin.app.quartz.BussAnnotation;
import com.yhy.lin.app.util.AppGlobals;
import com.yhy.lin.entity.TransferorderEntity;
import com.yhy.lin.service.DealerInfoServiceI;
import com.yhy.lin.service.DealerStatisticsServiceI;
import com.yhy.lin.service.TransferStatisticsServiceI;

import net.sf.json.JSONObject;

@Service("DealerStatisticsServiceI")
@Transactional
public class DealerStatisticsServiceImpl extends CommonServiceImpl implements DealerStatisticsServiceI {

	/**
	 * 返回easyUI的DataGrid数据格式的JSONObject对象
	 * 
	 * @param mapList
	 *            : 从数据库直接取得的结果集列表
	 * @param iTotalCnt
	 *            : 从数据库直接取得的结果集总数据条数
	 * @param dataExchanger
	 *            : 页面表示数据与数据库字段的对应关系列表
	 * @return JSONObject
	 */
	public JSONObject getJsonDatagridEasyUIs(List<Map<String, Object>> mapList, int iTotalCnt, Db2Page[] dataExchanger,
			DataGrid dataGrid) {
		// easyUI的dataGrid方式 －－－－这部分可以提取成统一处理
		String jsonTemp = "{\'total\':" + iTotalCnt + ",\'footer\':[{" + dataGrid.getFooter() + "}],\'rows\':[";
		for (int j = 0; j < mapList.size(); j++) {
			Map<String, Object> m = mapList.get(j);
			if (j > 0) {
				jsonTemp += ",";
			}
			jsonTemp += "{";
			for (int i = 0; i < dataExchanger.length; i++) {
				if (i > 0) {
					jsonTemp += ",";
				}
				jsonTemp += "'" + dataExchanger[i].getKey() + "'" + ":";
				Object objValue = dataExchanger[i].getData(m);
				if (objValue == null) {
					jsonTemp += "null";
				} else {
					String val = (objValue + "").replace("\n", "\\s").replace("\r", "\\s");
					jsonTemp += "'" + val + "'";
				}
			}
			jsonTemp += "}";
		}
		jsonTemp += "]}";
		JSONObject jObject = JSONObject.fromObject(jsonTemp);
		return jObject;
	}

	@Override
	public JSONObject getDealerUserDatagrid(CarCustomerEntity carcustomer, DataGrid dataGrid, String account,String fc_begin, String fc_end) {
		String sqlWhere = ((DealerStatisticsServiceI) AopContext.currentProxy()).getWhere(account,fc_begin, fc_end);

		sqlWhere += " ORDER BY s.create_time desc ";
		
		StringBuffer sql = new StringBuffer();
		String sqlCnt = "select count(*) from car_customer s, dealer_customer d,dealer_info f, t_s_depart b, t_s_base_user u "
				+ " LEFT JOIN t_s_user_org o on o.user_id=u.ID LEFT JOIN  t_s_depart t on o.org_id=t.ID,t_s_depart p "
				+ " where f.create_user_id = u.id and s.open_id = d.open_id and f.id=d.dealer_id and b.id=f.departId "
				+ " and (case when LENGTH(t.org_code)<6 then t.org_code else substring(t.org_code,1,6) END)=p.org_code ";
		if (!sqlWhere.isEmpty()) {
			sqlCnt += sqlWhere;
		}
		Long iCount = getCountForJdbcParam(sqlCnt, null);

		sql.append(
				"select f.account,s.create_time,s.real_name,s.phone,s.card_number,s.address,s.login_count "
				+ " from car_customer s, dealer_customer d,dealer_info f, t_s_depart b, t_s_base_user u "
				+ " LEFT JOIN t_s_user_org o on o.user_id=u.ID LEFT JOIN  t_s_depart t on o.org_id=t.ID,t_s_depart p "
				+ " where f.create_user_id = u.id and s.open_id = d.open_id and f.id=d.dealer_id and b.id=f.departId "
				+ " and (case when LENGTH(t.org_code)<6 then t.org_code else substring(t.org_code,1,6) END)=p.org_code ");
		if (!sqlWhere.isEmpty()) {
			sql.append(sqlWhere);
		}

		List<Map<String, Object>> mapList = findForJdbc(sql.toString(), dataGrid.getPage(), dataGrid.getRows());
		
		//dataGrid.setFooter("'address':'合计:'");
		// 将结果集转换成页面上对应的数据集
		Db2Page[] db2Pages = { new Db2Page("id"), 
				new Db2Page("account", "account", null),
				new Db2Page("createTime", "create_time", null),
				new Db2Page("realName", "real_name", null),
				new Db2Page("phone", "phone", null),
				new Db2Page("cardNumber", "card_number", null),
				new Db2Page("address", "address", null),
				new Db2Page("loginCount", "login_count", null)

		};
		JSONObject jObject = this.getJsonDatagridEasyUI(mapList, iCount.intValue(), db2Pages);
		return jObject;
	}

	@Override
	public JSONObject getDealerOrderDatagrid(TransferorderEntity transferorder, DataGrid dataGrid, String lineName,
			String orderType, String account, String fc_begin, String fc_end) {
		String sqlWheres = ((DealerStatisticsServiceI) AopContext.currentProxy()).getWhere4(transferorder,lineName, orderType, account, fc_begin, fc_end);
		
		StringBuffer sqlWhere = new StringBuffer();
		sqlWhere.append(sqlWheres);
		sqlWhere.append(" order by t.order_completed_time desc ");
		
		StringBuffer sql = new StringBuffer();

		StringBuffer sqlCnt = new StringBuffer("select count(*) from transferorder t LEFT JOIN lineinfo l on t.line_id = l.id "
				+ "LEFT JOIN car_customer w on w.id=t.user_id,dealer_customer d,dealer_info f, t_s_depart td,t_s_depart p where "
				+ "w.open_id = d.open_id and f.id=d.dealer_id and t.order_status='0' and t.order_user_type='0' and td.id=f.departId "
				+ "and (case when LENGTH(td.org_code)<6 then td.org_code else substring(td.org_code,1,6) END)=p.org_code ");

		if (!sqlWhere.toString().isEmpty()) {
			sqlCnt.append(sqlWhere);
		}
		Long iCount = getCountForJdbcParam(sqlCnt.toString(), null);

		sql.append(" select f.account,t.order_completed_time,t.order_id,l.name as line_name,l.type as line_type,t.order_startime,w.real_name,t.order_contactsname, "
				+ " t.order_contactsmobile,t.order_status,t.order_numbers,t.order_totalPrice,p.departname from transferorder t LEFT JOIN lineinfo l on "
				+ " t.line_id = l.id LEFT JOIN car_customer w on w.id=t.user_id,dealer_customer d,dealer_info f, t_s_depart td,t_s_depart p where w.open_id = d.open_id "
				+ " and f.id=d.dealer_id and t.order_status='0' and t.order_user_type='0' and td.id=f.departId "
				+ " and (case when LENGTH(td.org_code)<6 then td.org_code else substring(td.org_code,1,6) END)=p.org_code ");
		if (!sqlWhere.toString().isEmpty()) {
			sql.append(sqlWhere);
		}

		List<Map<String, Object>> mapList = findForJdbc(sql.toString(), dataGrid.getPage(), dataGrid.getRows());

		int orderNumbers = 0;
		BigDecimal orderTotalPrice = new BigDecimal("0");
		if (mapList.size() > 0) {
			for (int i = 0; i < mapList.size(); i++) {
				Object orderNumber = mapList.get(i).get("order_numbers");
				Object total = mapList.get(i).get("order_totalPrice");
				if (orderNumber != null) {
					String order = orderNumber + "";
					int t = Integer.parseInt(order);
					orderNumbers = t + orderNumbers;
				}
				if (total != null) {
					BigDecimal ta = (BigDecimal) total;
					orderTotalPrice=orderTotalPrice.add(ta);
				}
			}
		}
		//dataGrid.setFooter("'commonAddr':'合计:'",TagUtil.getTotalValue("commonAddr", "合计"));
		dataGrid.setFooter("'orderNumbers':'" + orderNumbers + "张','orderTotalPrice':'" + orderTotalPrice
				+ "元','orderStatus':'合计:'");

		// 将结果集转换成页面上对应的数据集
		Db2Page[] db2Pages = { new Db2Page("id"), 
				new Db2Page("account", "account", null),
				new Db2Page("orderCompletedTime", "order_completed_time", null),
				new Db2Page("orderId", "order_id", null),
				new Db2Page("lineName", "line_name", null),
				new Db2Page("ordertype", "line_type", null), 
				new Db2Page("orderStartime", "order_startime", null),
				new Db2Page("realName", "real_name", null),
				new Db2Page("departname", "departname", null),
				new Db2Page("orderContactsname", "order_contactsname", null),
				new Db2Page("orderContactsmobile", "order_contactsmobile", null),
				new Db2Page("orderStatus", "order_status", null),
				new Db2Page("orderNumbers", "order_numbers", null),
				new Db2Page("orderTotalPrice", "order_totalPrice", null)

		};
		JSONObject jObject = this.getJsonDatagridEasyUIs(mapList, iCount.intValue(), db2Pages, dataGrid);
		return jObject;
	}
	
	@BussAnnotation(orgType = {AppGlobals.PLATFORM_DEALER_AUDIT, AppGlobals.ORG_JOB_TYPE}, objTableUserId = " f.create_user_id ", orgTable="b" )
	public String getWhere(String account,String fc_begin, String fc_end) {
		StringBuffer sql = new StringBuffer();
		//渠道商
		if (StringUtil.isNotEmpty(account)) {
			sql.append(" and  f.id = '" + account + "'");
		}
		//注册时间
		if (StringUtil.isNotEmpty(fc_begin) && StringUtil.isNotEmpty(fc_end)) {
			sql.append(" and s.create_time between '" + fc_begin + "' and '" + fc_end + "'");
		}
		
//		TSUser user = ResourceUtil.getSessionUserName();
//		TSDepart depart = user.getCurrentDepart();
//		String orgCode = depart.getOrgCode();
//		String orgType = depart.getOrgType();
//		String userId = user.getId();
//		
//		//判断当前的机构类型，如果是"岗位"类型，就需要加个userId等于当前用户的条件，确保各个专员之间只能看到自己的数据
//		if(AppGlobals.ORG_JOB_TYPE.equals(orgType)){
//			sql.append(" and f.create_user_id = '" + userId + "' ");
//		}
//		
//		String oc = user.getOrgCompany();
//		
//		//如果是平台渠道商审核员权限，则根据其选择的子公司来过滤筛选
//		if(hasPermission && StringUtil.isNotEmpty(oc)){
//			sql.append("and ( 1=2 ");
//			
//			String[] ocArr = oc.split(",");
//			
//			for (int i = 0; i < ocArr.length; i++) {
//				sql.append(" or b.org_code like '"+ocArr[i]+"%' ");
//			}
//			sql.append(")");
//		} else {
//			sql.append(" and b.org_code like '"+orgCode+"%'");
//		}
				
		return sql.toString();
	}
	
	@BussAnnotation(orgType = {AppGlobals.PLATFORM_DEALER_AUDIT, AppGlobals.ORG_JOB_TYPE}, objTableUserId = " f.create_user_id ", orgTable="td" )
	public String getWhere4(TransferorderEntity transferorder,String lineName, String orderType, String account, String fc_begin, String fc_end) {
		StringBuffer sql = new StringBuffer();
		
		//订单编号
		if (StringUtil.isNotEmpty(transferorder.getOrderId())) {
			sql.append(" and t.order_id like '%" + transferorder.getOrderId() + "%'");
		}
		
		//渠道商
		if (StringUtil.isNotEmpty(account)) {
			sql.append(" and  f.id = '" + account + "'");
		}
		// 发车时间
		if (StringUtil.isNotEmpty(fc_begin) && StringUtil.isNotEmpty(fc_end)) {
			sql.append(" and t.order_completed_time between '" + fc_begin + "' and '" + fc_end + "'");
		}
		// 线路类型
		if (StringUtil.isNotEmpty(orderType)) {
			sql.append(" and  l.type ='" + orderType + "'");
		}
		// 线路名称
		if (StringUtil.isNotEmpty(lineName)) {
			sql.append(" and  l.id like '%" + lineName + "%'");
		}
		sql.append(" and t.applicationTime<f.audit_pass_time ");
		
		return sql.toString();
	}

}

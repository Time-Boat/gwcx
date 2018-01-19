package com.yhy.lin.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yhy.lin.app.util.AppGlobals;
import com.yhy.lin.app.util.AppUtil;
import com.yhy.lin.entity.ComplaintOrderView;
import com.yhy.lin.entity.BusStopInfoEntity;
import com.yhy.lin.entity.ComplaintOrderEntity;
import com.yhy.lin.entity.TransferorderView;
import com.yhy.lin.service.ComplaintOrderServiceI;

import net.sf.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl.Db2Page;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;

@Service("exceptionOrderService")
@Transactional
public class ComplaintOrderServiceImpl extends CommonServiceImpl implements ComplaintOrderServiceI {
	
	@Override
	public JSONObject getComplaintDatagrid(ComplaintOrderEntity complaintOrder, DataGrid dataGrid,String userType, String complaintTime_begin, String complaintTime_end,String orderContactsname, String orderContactsmobile, String orderStatus) {
		
		String sqlWhere = getAppealWhere(complaintOrder,userType,complaintTime_begin,complaintTime_end,orderContactsname,orderContactsmobile,orderStatus);

		StringBuffer sql = new StringBuffer();

		StringBuffer sqlCnt = new StringBuffer();
		// 取出总数据条数（为了分页处理, 如果不用分页，取iCount值的这个处理可以不要）
		sqlCnt.append("select COUNT(*) from complaint_order c LEFT JOIN transferorder a on c.transfer_id=a.id LEFT JOIN lineinfo l on a.line_id=l.id LEFT JOIN t_s_depart t on l.departId=t.ID where a.delete_flag='0' and a.is_exception='1'");
		
		if (!sqlWhere.isEmpty()) {
			sqlCnt.append(sqlWhere);
		}
		
		Long iCount = getCountForJdbcParam(sqlCnt.toString(), null);
		
		sql.append("select c.id,a.order_id,c.complaint_time,l.name as line_name,a.order_user_type,a.order_contactsname,a.order_contactsmobile,"
				+ "c.complaint_reason,a.order_totalPrice,c.handle_result,a.order_status from complaint_order c LEFT JOIN transferorder a on "
				+ "c.transfer_id=a.id LEFT JOIN lineinfo l on a.line_id=l.id LEFT JOIN t_s_depart t on l.departId=t.ID where a.delete_flag='0' and a.is_exception='1'");
		
		if (!sqlWhere.isEmpty()) {
			sql.append(sqlWhere);
		}
		List<Map<String, Object>> mapList = findForJdbc(sql.toString(), dataGrid.getPage(), dataGrid.getRows());
		// 将结果集转换成页面上对应的数据集
		Db2Page[] db2Pages = { 
				new Db2Page("id"), 
				new Db2Page("orderId", "order_id", null),
				new Db2Page("complaintTime", "complaint_time", null),
				new Db2Page("lineName", "line_name", null),
				new Db2Page("orderUserType", "order_user_type", null),
				new Db2Page("orderContactsname", "order_contactsname", null),
				new Db2Page("orderContactsmobile", "order_contactsmobile", null),
				new Db2Page("complaintReason", "complaint_reason", null),
				new Db2Page("orderTotalPrice", "order_totalPrice", null),
				new Db2Page("handleResult", "handle_result", null),
				new Db2Page("orderStatus", "order_status", null)
				
		};
		JSONObject jObject = getJsonDatagridEasyUI(mapList, iCount.intValue(), db2Pages);
		return jObject;
	}
	
	public String getAppealWhere(ComplaintOrderEntity complaintOrder,String userType,String complaintTime_begin, String complaintTime_end,String orderContactsname, String orderContactsmobile, String orderStatus) {
		StringBuffer sql = new StringBuffer();// 不需要显示退款状态的订单
		
		TSDepart depart = ResourceUtil.getSessionUserName().getCurrentDepart();
		String orgCode = depart.getOrgCode();
		String orgType = depart.getOrgType();
		String userId = ResourceUtil.getSessionUserName().getId();
		
		//判断当前的机构类型，如果是"岗位"类型，就需要加个userId等于当前用户的条件，确保各个专员之间只能看到自己的数据
		if(AppGlobals.ORG_JOB_TYPE.equals(orgType)){
			sql.append(" and l.createUserId = '" + userId + "' ");
		}
		
		sql.append(" and t.org_code like '" + orgCode + "%' ");
		// 用户类型
		if (StringUtil.isNotEmpty(userType)) {
			sql.append(" and  a.order_user_type= '" + userType+ "'");
		}
		// 申诉人
		if (StringUtil.isNotEmpty(orderContactsname)) {
			sql.append(" and  a.order_contactsname like '%" + orderContactsname + "%'");
		}
		//申诉人手机号
		if (StringUtil.isNotEmpty(orderContactsmobile)) {
			sql.append(" and  a.order_contactsmobile like '%" + orderContactsmobile + "%'");
		}
		//订单状态
		if (StringUtil.isNotEmpty(orderStatus)) {
			sql.append(" and a.order_status='" + orderStatus + "'");
		}
		//申诉时间
		if (StringUtil.isNotEmpty(complaintTime_begin) && StringUtil.isNotEmpty(complaintTime_end)) {
			sql.append(" and c.complaint_time between '" + complaintTime_begin + "' and '" + complaintTime_end + "'");
		}
		
		sql.append(" ORDER BY FIELD(order_status,8,1,2,3,4,5,6,7,0),c.complaint_time desc");
		
		return sql.toString();
	}
	
	@Override
	public ComplaintOrderView getDetail(String id) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		
		sql.append("select e.id as eid,a.id as transferId,a.order_id,a.order_status,l.name as lineName,l.startLocation,l.endLocation,a.applicationTime,a.order_startime,"
				+ "a.order_totalPrice,a.order_user_type,e.complaint_time,a.order_contactsname,a.order_contactsmobile,e.complaint_reason,e.complaint_content,e.handle_result,e.handle_content,e.handle_time from "
				+ "complaint_order e LEFT JOIN transferorder a on e.transfer_id=a.id LEFT JOIN lineinfo l on a.line_id=l.id LEFT JOIN t_s_depart t on l.departId=t.ID");

		sql.append(" where e.id='" + id + "'");
		List<Object[]> list = findListbySql(sql.toString());
		ComplaintOrderView complaintOrderView = new ComplaintOrderView();
		if (list.size() > 0) {
			Object[] obj = list.get(0);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				complaintOrderView.setId(String.valueOf(obj[0]));
				complaintOrderView.setTransferId(String.valueOf(obj[1]));
				complaintOrderView.setOrderId(String.valueOf(obj[2]));
				complaintOrderView.setOrderStatus(String.valueOf(obj[3]));
				complaintOrderView.setLineName(String.valueOf(obj[4]));
				
				if (obj[5] != null) {
					String startion = obj[5].toString();
					BusStopInfoEntity bus = this.getEntity(BusStopInfoEntity.class, startion);
					complaintOrderView.setStartLocation(bus.getName());
				}
				if (obj[6] != null) {
					String endtion = obj[6].toString();
					BusStopInfoEntity bus = this.getEntity(BusStopInfoEntity.class, endtion);
					complaintOrderView.setEndLocation(bus.getName());
				}
				
				if (obj[7] != null) {
					complaintOrderView.setApplicationTime(sdf.parse(obj[7].toString()));
				}
				if (obj[8] != null) {
					complaintOrderView.setOrderStartime(sdf.parse(obj[8].toString()));
				}
				complaintOrderView.setOrderTotalPrice(String.valueOf(obj[9]));
				complaintOrderView.setOrderUserType(String.valueOf(obj[10]));
				if (obj[11] != null) {
					complaintOrderView.setComplaintTime((sdf.parse(obj[11].toString())));
				}
				complaintOrderView.setOrderContactsname(String.valueOf(obj[12]));
				complaintOrderView.setOrderContactsmobile(String.valueOf(obj[13]));
				
				if (obj[14] != null) {
					complaintOrderView.setComplaintReason(String.valueOf(obj[14]));
				}
				if (obj[15] != null) {
					complaintOrderView.setComplaintContent((String.valueOf(obj[15])));
				}
				if (obj[16] != null) {
					complaintOrderView.setHandleResult(String.valueOf(obj[16]));
				}
				if (obj[17] != null) {
					complaintOrderView.setHandleContent(String.valueOf(obj[17]));
				}
				if (obj[18] != null) {
					complaintOrderView.setHandleTime((sdf.parse(obj[18].toString())));
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return complaintOrderView;
	}
}
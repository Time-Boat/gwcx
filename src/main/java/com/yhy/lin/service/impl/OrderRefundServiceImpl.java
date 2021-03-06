package com.yhy.lin.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.dao.jdbc.JdbcDao;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl.Db2Page;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yhy.lin.app.controller.AppInterfaceController;
import com.yhy.lin.app.entity.AppMessageListEntity;
import com.yhy.lin.app.entity.RefundReqData;
import com.yhy.lin.app.quartz.BussAnnotation;
import com.yhy.lin.app.util.AppGlobals;
import com.yhy.lin.app.util.AppUtil;
import com.yhy.lin.app.util.SendMailUtil;
import com.yhy.lin.app.util.SendMessageUtil;
import com.yhy.lin.app.wechat.MobiMessage;
import com.yhy.lin.app.wechat.RequestHandler;
import com.yhy.lin.entity.TransferorderEntity;
import com.yhy.lin.service.LineInfoServiceI;
import com.yhy.lin.service.OrderRefundServiceI;

import net.sf.json.JSONObject;

@Service("OrderRefundServiceI")
@Transactional
public class OrderRefundServiceImpl extends CommonServiceImpl implements OrderRefundServiceI {
	@Autowired
	private JdbcDao jdbcDao;

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(AppInterfaceController.class);
	
	@Override
	public JSONObject getDatagrid(TransferorderEntity transferorder, DataGrid dataGrid, String fc_begin, String fc_end,
			String rf_begin, String rf_end,  String departname,String lineId,String startLocation,String endLocation) {
		String sqlWhere = ((OrderRefundServiceI) AopContext.currentProxy()).getWhere(
				transferorder, fc_begin, fc_end,rf_begin,rf_end, departname,lineId,startLocation,endLocation);

		StringBuffer sql = new StringBuffer();

		String sqlCnt = "select count(*) from transferorder a left join order_linecardiver b on a.id = b .id left join car_info c on b.licencePlateId =c.id "
				+ " left join driversinfo d on b.driverId =d.id "
				+ " left join lineinfo l on l.id = a.line_id left join t_s_depart t on t.id = l.departId,t_s_depart p ";
		if (!sqlWhere.isEmpty()) {
			sqlCnt += sqlWhere;
		} 
		
		sqlCnt += " order by a.order_status,a.refund_time desc";
		
		// 取出总数据条数（为了分页处理, 如果不用分页，取iCount值的这个处理可以不要）
		Long iCount = getCountForJdbcParam(sqlCnt, null);
		sql.append(" select u.username as f_audit_user,a.first_audit_status,a.first_audit_date,us.username as l_audit_user,a.last_audit_status,a.order_user_type, ");
		sql.append(" a.last_audit_date,a.city_name,t.org_code,a.id,a.order_id,a.order_status,a.order_type,a.order_starting_station_name,a.order_terminus_station_name, ");
		sql.append(" a.order_startime,a.order_numbers,a.order_paytype,a.order_contactsname,a.refund_time,a.refund_price, ");
		sql.append(" a.order_contactsmobile,a.order_paystatus,a.order_totalPrice,d.name,d.phoneNumber,a.applicationTime, p.departname ");
		sql.append(" from transferorder a left join order_linecardiver b on a.id = b.id left join car_info c on b.licencePlateId = c.id "
				+ "	left join driversinfo d on b.driverId =d.id left join lineinfo l on l.id = a.line_id left join t_s_depart t on t.id = l.departId "
				+ " left join t_s_base_user u on u.id = a.first_audit_user left join t_s_base_user us on us.id = a.last_audit_user LEFT JOIN car_customer ca on a.user_id=ca.id"
				+ " ,t_s_depart p  ");
		
		if (!sqlWhere.isEmpty()) {
			sql.append(sqlWhere);
		}
		
		sql.append(" order by a.order_status,a.refund_time desc");
		
		System.out.println(sql.toString());
		List<Map<String, Object>> mapList = findForJdbc(sql.toString(), dataGrid.getPage(), dataGrid.getRows());
		// 将结果集转换成页面上对应的数据集
		Db2Page[] db2Pages = { new Db2Page("id"),
				new Db2Page("orderId", "order_id", null),
				new Db2Page("orderStatus", "order_status", null),
				new Db2Page("orderType", "order_type", null),
				new Db2Page("orderStartime", "order_startime", null),
				new Db2Page("orderNumbers", "order_numbers", null),
				new Db2Page("orderPaytype", "order_paytype", null),
				new Db2Page("orderContactsname", "order_contactsname", null),
				new Db2Page("orderContactsmobile", "order_contactsmobile", null),
				new Db2Page("orderPaystatus", "order_paystatus", null),
				new Db2Page("orderTotalPrice", "order_totalPrice", null),
				new Db2Page("name", "name", null),
				new Db2Page("phoneNumber", "phoneNumber", null),
				new Db2Page("orderUserType", "order_user_type", null),
				new Db2Page("applicationTime", "applicationTime", null),
				new Db2Page("orderStartingstation", "order_starting_station_name", null),
				new Db2Page("orderTerminusstation", "order_terminus_station_name", null),
				new Db2Page("refundTime", "refund_time", null),
				new Db2Page("cityName", "city_name", null),
				new Db2Page("refundPrice", "refund_price", null),
				new Db2Page("firstAuditUser", "f_audit_user", null),
				new Db2Page("firstAuditStatus", "first_audit_status", null),
				new Db2Page("firstAuditDate", "first_audit_date", null),
				new Db2Page("lastAuditUser", "l_audit_user", null),
				new Db2Page("lastAuditStatus", "last_audit_status", null),
				new Db2Page("lastAuditDate", "last_audit_date", null),
				new Db2Page("departname", "departname", null)
		};
		JSONObject jObject = getJsonDatagridEasyUI(mapList, iCount.intValue(), db2Pages);
		return jObject;
	}

	@BussAnnotation(orgType = {AppGlobals.PLATFORM_REFUND_AUDIT, AppGlobals.ORG_JOB_TYPE}, 
			objTableUserId = " l.createUserId ", orgTable="t", auditSql = " and a.first_audit_status = '1'" )
	public String getWhere(TransferorderEntity transferorder, String fc_begin, String fc_end, String rf_begin,String rf_end, String departname,String lineId,String startLocation,String endLocation) {

		StringBuffer sql = new StringBuffer(" where 1=1 ");
		
		sql.append(" and (case when LENGTH(t.org_code)<6 then t.org_code else substring(t.org_code,1,6) END)=p.org_code ");
		
		// 公司名称
		if (StringUtil.isNotEmpty(departname)) {
			sql.append(" and p.departname like '%" + departname + "%'");
		}
		//线路
		if (StringUtil.isNotEmpty(lineId)) {
			sql.append(" and l.id = '" + lineId + "'");
		}
		// 发车时间
		if (StringUtil.isNotEmpty(fc_begin) && StringUtil.isNotEmpty(fc_end)) {
			sql.append(" and a.order_startime between '" + fc_begin + "' and '" + fc_end + "'");
		}
		//申请退款时间
		if (StringUtil.isNotEmpty(rf_begin) && StringUtil.isNotEmpty(rf_end)) {
			sql.append(" and a.refund_time between '" + rf_begin + "' and '" + rf_end + "'");
		}
		// 订单编号
		if (StringUtil.isNotEmpty(transferorder.getOrderId())) {
			sql.append(" and  a.order_id like '%" + transferorder.getOrderId() + "%'");
		}
		// 订单类型
		if (StringUtil.isNotEmpty(transferorder.getOrderType())) {
			sql.append(" and  a.order_type ='" + transferorder.getOrderType() + "'");
		}
		// 订单类型
		if (StringUtil.isNotEmpty(transferorder.getOrderUserType())) {
			sql.append(" and  a.order_user_type ='" + transferorder.getOrderUserType() + "'");
		}
		// 订单状态
		if (StringUtil.isNotEmpty(transferorder.getOrderStatus())) {
			sql.append(" and  a.order_status ='" + transferorder.getOrderStatus() + "'");
		}
		// 起点站id
		if (StringUtil.isNotEmpty(startLocation)) {
			sql.append(" and  a.order_starting_station_id = '" + startLocation + "'");
		}
		// 终点站id
		if (StringUtil.isNotEmpty(endLocation)) {
			sql.append(" and  a.order_terminus_station_id = '" + endLocation + "'");
		}

		// 申请人
		if (StringUtil.isNotEmpty(transferorder.getOrderContactsname())) {
			sql.append(" and  a.order_contactsname like '%" + transferorder.getOrderContactsname() + "%'");
		}

//		TSUser user = ResourceUtil.getSessionUserName();
//		String orgCode = user.getCurrentDepart().getOrgCode();
//		String orgType = user.getCurrentDepart().getOrgType();
//		String userId = user.getId();
//		
//		//看是否是平台审核员
//		if(hasPermission){
//			//平台审核员只能看到初审通过的订单
//			sql.append(" and a.first_audit_status = '1' ");
//		}else{
//			//判断当前的机构类型，如果是"岗位"类型，就需要加个userId等于当前用户的条件，确保各个专员之间只能看到自己的数据
//			if(AppGlobals.ORG_JOB_TYPE.equals(orgType)){
//				sql.append(" and l.createUserId = '" + userId + "' ");
//			}
//		}
//		
//		String oc = user.getOrgCompany();
//		
//		//如果是平台退款审核员权限，则根据其选择的子公司来过滤筛选
//		if(hasPermission && StringUtil.isNotEmpty(oc)){
//			sql.append("and ( 1=2 ");
//			
//			String[] ocArr = oc.split(",");
//			
//			for (int i = 0; i < ocArr.length; i++) {
//				sql.append(" or t.org_code like '"+ocArr[i]+"%' ");
//			}
//			sql.append(")");
//		} else {
//			sql.append(" and t.org_code like '"+orgCode+"%'");
//		}
		
		sql.append(" and order_status in('3','4','5') ");

		return sql.toString();
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED
	// noRollbackFor={UserAccountException.class},
	// readOnly=true//, timeout=30 //timeout 允许在执行第一条sql之后保持连接30秒
	)
	@Override
	public boolean agreeRefund(String id) {
		
		boolean success = false;
		
		try {
			TransferorderEntity t = getEntity(TransferorderEntity.class, id);
			t.setOrderStatus(4);
			t.setOrderPaystatus("2");
			t.setRefundCompletedTime(AppUtil.getDate());
			updateEntitie(t);
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return success;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
	@Override
	public boolean rejectRefund(String id, String rejectReason) {
		boolean success = false;
		
		try {
			TransferorderEntity t = getEntity(TransferorderEntity.class, id);
			t.setOrderStatus(5);
			t.setOrderPaystatus("4");
			t.setRejectReason(rejectReason);
			updateEntitie(t);
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return success;
	}

	//@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
	//不要加事物，批量退款的时候，如果前两个人的款项已经退还，数据库就要做相应的修改       这名字取的像*一样....
	@Override
	public Map<String,String> firstAgreeAllRefund(String[] arrId, String fees, RequestHandler refundRequest, String path) {
		
		Map<String,String> map = new HashMap<>();
		
		String msg = "";
		String statusCode = "";
		String discription = "";
		boolean success = false;
		
		int sCount = 0;   //退款成功订单数
		int fCount = 0;   //退款失败订单数
		
		List<AppMessageListEntity> mList = new ArrayList<AppMessageListEntity>();
		
		RefundReqData refundReqData = new RefundReqData();
		String[] arrFee = fees.split(",");
		try {
			for(int i=0;i<arrId.length;i++){
				//确保在同一时间内的outRefundNo不一样，不然会出现退款失败的问题
				String outRefundNo = "NO" + System.currentTimeMillis() + StringUtil.numRandom(6);
				// 获得退款的传入参数
				//String transactionID = "";
				String outTradeNo = arrId[i];
				
				TransferorderEntity t = get(TransferorderEntity.class, outTradeNo);
				
				int totalFee = (int) ((Double.parseDouble(arrFee[i]) * 100));
				int refundFee = totalFee;
				
				//如果是异常订单，就全额退款      不是的话就走普通退款流程      0：不是     1：是
				if("0".equals(t.getIsException())){
					//计算订单的发车时间距离退款的时间在24小时之内，则只能退还50%的款项
					String startTime = t.getOrderStartime();
					String refundTime = t.getRefundTime();
					int m = AppUtil.compareDate(AppUtil.str2Date(startTime), AppUtil.str2Date(refundTime), 'h', "");
					
					//可以用策略模式    需要改进
					if (m < 10) {
						refundFee = totalFee/2;
					}
				}
				
				logger.info("总金额：" + totalFee);
				logger.info("退款金额：" + refundFee);
				
//				refundReqData.setParams(transactionID, outTradeNo, outRefundNo, totalFee, refundFee);
				System.out.println("交易单号：" + t.getOrderPayNumber());
//				refundReqData.setParams(transactionID, t.getOrderPayNumber(), outRefundNo, totalFee, refundFee);
				refundReqData.setParams(t.getOrderPayNumber(), "", outRefundNo, totalFee, refundFee);
				//refundReqData.setParams("4005282001201707252629347549", "", outRefundNo, totalFee, refundFee);
					
				refundRequest.init(AppGlobals.WECHAT_ID, AppGlobals.WECHAT_APP_SECRET, AppGlobals.WECHAT_KEY);
		
				refundReqData.setSign(refundRequest.createSign(refundReqData.getParameters()));
				
				String info = MobiMessage.RefundReqData2xml(refundReqData).replaceAll("__", "_");
				logger.info(info);
				// LogUtils.trace(info);
				
				String result = refundRequest.httpsRequest(AppGlobals.REFUND_URL, info, path);
				
				logger.info("result : " + result);
				
				Map<String, String> getMap = MobiMessage.parseXml(result);
				if ("SUCCESS".equals(getMap.get("result_code")) && "OK".equals(getMap.get("return_msg"))) {
					statusCode = AppGlobals.APP_SUCCESS;  //result_code=FAIL
					msg = AppGlobals.APP_SUCCESS_MSG;
					success = true;
					sCount += 1;
					
					//修改订单状态
					TransferorderEntity trans = getEntity(TransferorderEntity.class, arrId[i]);
					trans.setOrderStatus(4);
					trans.setOrderPaystatus("2");
					trans.setRefundCompletedTime(AppUtil.getDate());
					//记录审核数据
					trans.setLastAuditDate(AppUtil.getDate());
					trans.setLastAuditStatus("1");
					trans.setLastAuditUser(ResourceUtil.getSessionUserName().getId());
					logger.info("RefundCompletedTime：" + trans.getRefundCompletedTime());
					//退款金额
					double fee = (double)refundFee/100;
					logger.info("refundFee：" + refundFee);
					logger.info("fee：" + fee);
					trans.setRefundPrice(fee + "");
					saveOrUpdate(trans);
					
					//发送消息和短信
					SendMessageUtil.sendMessage(trans.getOrderContactsmobile(), new String[]{}, new String[]{}, 
							SendMessageUtil.TEMPLATE_ORDER_REFUND, SendMessageUtil.TEMPLATE_SMS_CODE_SIGN_NAME);
					
					mList.add(SendMessageUtil.buildAppMessage(
							t.getUserId(), "您的申请的退款已审核通过，退款金额最迟3-5日内会返回到原支付账户。", "0", "1", trans.getId()));
					
				} else {
					// 返回错误描述
					// return JSONObject.fromObject(getMap.get("err_code_des"));
					success = false;
					msg = getMap.get("err_code_des");
					if(!StringUtil.isNotEmpty(msg)){
						msg = getMap.get("return_msg");
					}
					statusCode = "777";
					fCount += 1;
				}
				logger.info("订单id：" + arrId[i] + "   订单信息：" + msg);
			}
			
			saveAllEntitie(mList);
			
		} catch (Exception e) {
			statusCode = AppGlobals.SYSTEM_ERROR;
			msg = AppGlobals.SYSTEM_ERROR_MSG;
			e.printStackTrace();
		}
		
		
		
		map.put("msg", msg);
		if(success){
			discription = "退款成功订单：" + sCount + "条<br/>退款失败订单：" + fCount + "条";
		}else{
			discription = "退款成功订单：" + sCount + "条<br/>退款失败订单：" + fCount + "条<br/>失败原因：" + msg;
		}
		map.put("description", discription);
		map.put("statusCode", statusCode);
		map.put("success", success+"");
		return map;
	}
	
	/**
	 * 1.添加事务注解 使用propagation 指定事务的传播行为，即当前的事务方法被另外一个事务方法调用时如何使用事务。
	 * 默认取值为REQUIRED，即使用调用方法的事务 REQUIRES_NEW：使用自己的事务，调用的事务方法的事务被挂起。
	 * 
	 * 2.使用isolation 指定事务的隔离级别，最常用的取值为READ_COMMITTED 3.默认情况下 Spring
	 * 的声明式事务对所有的运行时异常进行回滚，也可以通过对应的属性进行设置。通常情况下，默认值即可。 4.使用readOnly 指定事务是否为只读。
	 * 表示这个事务只读取数据但不更新数据，这样可以帮助数据库引擎优化事务。若真的是一个只读取数据库值得方法，应设置readOnly=true
	 * 5.使用timeOut 指定强制回滚之前事务可以占用的时间。
	 */
	// @Transactional(propagation=Propagation.REQUIRES_NEW,
	// isolation=Isolation.READ_COMMITTED,
	//// noRollbackFor={UserAccountException.class},
	// readOnly=true//, timeout=30 //timeout 允许在执行第一条sql之后保持连接30秒
	// )

}

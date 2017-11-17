package com.yhy.lin.service.impl;

import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yhy.lin.app.entity.CarCustomerEntity;
import com.yhy.lin.app.quartz.BussAnnotation;
import com.yhy.lin.app.util.AppGlobals;
import com.yhy.lin.app.util.AppUtil;
import com.yhy.lin.app.util.SendMessageUtil;
import com.yhy.lin.entity.DealerInfoEntity;
import com.yhy.lin.service.DealerInfoServiceI;

import net.sf.json.JSONObject;

import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.PasswordUtil;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;

@Service("DealerInfoService")
@Transactional
public class DealerInfoServiceImpl extends CommonServiceImpl implements DealerInfoServiceI {

	@Override
	public JSONObject getDatagrid(DataGrid dataGrid, DealerInfoEntity dealerInfo, String username, String departname) {
		//通过AOP代理调用getWhere方法即可走事务切面              必须得通过接口调用getWhere
		String sqlWhere = ((DealerInfoServiceI) AopContext.currentProxy()).getWhere(dealerInfo, username, departname);
		// 取出总数据条数（为了分页处理, 如果不用分页，取iCount值的这个处理可以不要）
		String sqlCnt = "select count(*) from dealer_info d left join t_s_base_user u on d.create_user_id = u.id join t_s_depart b on b.id = d.departId "
				+ " LEFT JOIN t_s_user_org o on o.user_id=u.ID LEFT JOIN  t_s_depart t on o.org_id=t.ID,t_s_depart p "
				+ " where 1=1 and (case when LENGTH(t.org_code)<6 then t.org_code else substring(t.org_code,1,6) END)=p.org_code ";
		if (!sqlWhere.isEmpty()) {
			sqlCnt += sqlWhere;
		}
		Long iCount = getCountForJdbcParam(sqlCnt, null);
		// 取出当前页的数据 
		String sql = "select d.dealer_discount,d.id,d.account,d.create_date,d.QR_code_url,d.scan_count,d.phone,d.manager,d.position,d.bank_account,d.status,p.departname,d.dealer_file_path,"
				+ " d.audit_user,d.audit_date,d.audit_status,d.last_audit_user,d.last_audit_date,d.last_audit_status,d.commit_apply_date,d.apply_type,u.username "
				+ " from dealer_info d left join t_s_base_user u on d.create_user_id = u.id join t_s_depart b on b.id = d.departId "
				+ " LEFT JOIN t_s_user_org o on o.user_id=u.ID LEFT JOIN  t_s_depart t on o.org_id=t.ID,t_s_depart p "
				+ " where 1=1 and (case when LENGTH(t.org_code)<6 then t.org_code else substring(t.org_code,1,6) END)=p.org_code ";
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
				,new Db2Page("dealerFilePath", "dealer_file_path", null)
				,new Db2Page("applyType", "apply_type", null)
				,new Db2Page("departname", "departname", null)
				,new Db2Page("dealerDiscount", "dealer_discount", null)
				,new Db2Page("status", "status", null)
		};
		JSONObject jObject = getJsonDatagridEasyUI(mapList, iCount.intValue(), db2Pages);
		return jObject;
	}
	
	@BussAnnotation(orgType = {AppGlobals.PLATFORM_DEALER_AUDIT, AppGlobals.COMMERCIAL_MANAGER , AppGlobals.ORG_JOB_TYPE}, 
			objTableUserId = " d.create_user_id ", orgTable="b", auditSql = " and d.audit_status = '1' ", commercialSql = " and (d.audit_status != '-1' or d.status = '2') ")
	public String getWhere(DealerInfoEntity dealerInfo,String username, String departname) {
		
		StringBuffer sql = new StringBuffer();
		
		if(StringUtil.isNotEmpty(departname)){
			sql.append(" and  p.departname like '%" + departname + "%'");
		}
		
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
		
		if(StringUtil.isNotEmpty(dealerInfo.getLastAuditStatus())){
			sql.append(" and d.last_audit_status = '" + dealerInfo.getLastAuditStatus() + "' ");
		}
		
//		TSUser user = ResourceUtil.getSessionUserName();
//		TSDepart depart = user.getCurrentDepart();
//		String orgCode = depart.getOrgCode();
//		String orgType = depart.getOrgType();
//		String userId = user.getId();
//		
//		//判断当前的机构类型，如果是"岗位"类型，就需要加个userId等于当前用户的条件，确保各个专员之间只能看到自己的数据
//		if(AppGlobals.ORG_JOB_TYPE.equals(orgType)){
//			sql.append(" and d.create_user_id = '" + userId + "' ");
//		}
//		
//		String oc = user.getOrgCompany();
//		
//		//如果是平台渠道商审核员权限，则根据其选择的子公司来过滤筛选
//		if(hasPermissionP){
//			sql.append("and ( 1=2 ");
//			
//			if(StringUtil.isNotEmpty(oc)){
//				String[] ocArr = oc.split(",");
//				for (int i = 0; i < ocArr.length; i++) {
//					sql.append(" or b.org_code like '"+ocArr[i]+"%' ");
//				}
//			}
//			sql.append(")");
//			sql.append(" and d.audit_status = '1' ");
//		} else {
//			sql.append(" and b.org_code like '"+orgCode+"%'");
//		}
//		
//		//商务经理的角色
//		if(hasPermissionC){
//			sql.append(" and d.audit_status != '-1' ");
//		}
		
		return sql.toString();
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
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED
	// noRollbackFor={UserAccountException.class},
	// readOnly=true//, timeout=30 //timeout 允许在执行第一条sql之后保持连接30秒
	)
	@Override
	public void agreeAudit(String id) {
		
		DealerInfoEntity dealerInfo = getEntity(DealerInfoEntity.class, id);
		String apply = dealerInfo.getApplyType();
		String status = dealerInfo.getAuditStatus();
		String recheck = dealerInfo.getLastAuditStatus();

		if ("0".equals(status)) { // 如果初审状态是待审核状态，则进行初审
			dealerInfo.setAuditDate(AppUtil.getDate());
			dealerInfo.setAuditStatus("1");
			dealerInfo.setAuditUser(ResourceUtil.getSessionUserName().getUserName());
			dealerInfo.setLastAuditStatus("0");
		} else if ("0".equals(recheck)) { // 如果复审状态是待审核状态，则进行复审
			dealerInfo.setLastAuditDate(AppUtil.getDate());
			dealerInfo.setLastAuditStatus("1");
			dealerInfo.setLastAuditUser(ResourceUtil.getSessionUserName().getUserName());
			
			//如果这个手机号已经是普通用户了，则将其直接转为渠道商用户，并删除未支付订单
			CarCustomerEntity customer = findUniqueByProperty(CarCustomerEntity.class, "phone", dealerInfo.getPhone());
			
			//如果是申请启用，则创建渠道商账号
			if ("0".equals(apply)) {
				//生成渠道商的登录密码
				String pwd = PasswordUtil.encrypt(dealerInfo.getPhone(), "123456", PasswordUtil.getStaticSalt());
				
				if(customer == null){
					customer = new CarCustomerEntity();
					customer.setPhone(dealerInfo.getPhone());
					customer.setCreateTime(AppUtil.getDate());
					customer.setLoginCount(0);
					customer.setRealName(dealerInfo.getAccount());
				}
				
				customer.setPassword(pwd);
				customer.setUserType("1");
				
				dealerInfo.setStatus("0");
				
				String mobiles=customer.getPhone();
				if (StringUtil.isNotEmpty(mobiles)) {
					SendMessageUtil.sendMessage(mobiles,new String[0],new String[0],
							SendMessageUtil.TEMPLATE_ARRANGE_DEALER , SendMessageUtil.TEMPLATE_SMS_CODE_SIGN_ORDER);
				}
				
			} else {//如果是申请停用，则将渠道商账号转为普通用户
				if(customer != null){
					customer.setPassword("");
					customer.setUserType("0");
				}
				dealerInfo.setStatus("2");
			}
			
			customer.setToken(AppUtil.generateToken(customer.getId(), customer.getPhone()));
			customer.setTokenUpdateTime(AppUtil.getDate());
			
			save(customer);
			//删除所有未付款订单
			executeSql(" delete from transferorder where user_id = ? and order_status = '6' and order_paystatus = '3' ", customer.getId());
		}
		
		saveOrUpdate(dealerInfo);
	}
	
}
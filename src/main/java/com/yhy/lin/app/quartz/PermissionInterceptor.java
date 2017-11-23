package com.yhy.lin.app.quartz;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yhy.lin.app.util.AppGlobals;

/**
 * Description : 权限过滤 AOP
 * 
 * @author Timer
 * @date 2017年9月21日 下午3:29:38
 */

@Aspect
@Component
public class PermissionInterceptor {

	//写个切面，统计每个接口被访问的次数
	
	@Autowired
	private UserService userService;
	
	// 定义切入点 @Pointcut("execution(public * com.jay..*.*(..))") -- 表示对com.jay
	// 包下的所有方法都可添加切入点
//	@Pointcut("execution(* com.yhy.lin.*.*.*(..)) && @annotation(com.yhy.lin.app.quartz.BussAnnotation )")
//	public void joinPointPermission() {
//	}

//	@Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping) ")
//	private void requestMapping() {
//	}

	/**
	 * 环绕通知 用于拦截指定内容，记录用户的操作
	 */
	@Around("execution(* com.yhy.lin.*.*.*(..)) && @annotation(org.springframework.web.bind.annotation.RequestMapping) ")
	public Object requestMapping(ProceedingJoinPoint pj) throws Throwable {
		Object object = pj.proceed();
		System.out.println(pj.getSignature().getName());
		return object;
	}

	/**
	 * 环绕通知 用于拦截指定内容，记录用户的操作
	 */
	@Around("execution(* com.yhy.lin.*.*.*(..)) && @annotation(buss)")
	public Object interceptorPermission(ProceedingJoinPoint pj, BussAnnotation buss) throws Throwable {
		
		// 获取需要验证权限的注解
//		BussAnnotation b = giveController(pj);

//		int statrt = buss.objTableUserId().indexOf('.');
//		String b = buss.objTableUserId().substring(statrt - 1, statrt);
		
		Object object = pj.proceed();
		StringBuffer sql = new StringBuffer((String) object);

//		System.out.println(pj.getSignature().getName());
//		System.out.println(buss.objTableUserId());
		
		//是否有审核员权限
		boolean isAudit = false;
		
		// 权限的处理
		TSUser user = ResourceUtil.getSessionUserName();
		TSDepart depart = user.getCurrentDepart();
		String orgCode = depart.getOrgCode();
		String orgType = depart.getOrgType();
		String userId = user.getId();

		// 审核员责任公司
		String oc = user.getOrgCompany();

		String[] ps = buss.orgType();
		for (String p : ps) {
			switch (p) {
				// 判断机构类型，如果是"岗位"类型，就需要加个userId等于当前用户的条件，确保各个专员之间只能看到自己的数据
				case AppGlobals.ORG_JOB_TYPE:
					if (AppGlobals.ORG_JOB_TYPE.equals(orgType)) {
						sql.append(" and " + buss.objTableUserId() + " = '" + userId + "' ");
					}
					break;
				case AppGlobals.PLATFORM_DEALER_AUDIT:
				case AppGlobals.PLATFORM_LINE_AUDIT:
				case AppGlobals.PLATFORM_REFUND_AUDIT:
					
					isAudit = true;
					
					// 如果是平台渠道商审核员权限，则根据其选择的子公司来过滤筛选
					if (checkRole(AppGlobals.PLATFORM_DEALER_AUDIT) || checkRole(AppGlobals.PLATFORM_LINE_AUDIT) || checkRole(AppGlobals.PLATFORM_REFUND_AUDIT)) {
						sql.append("and ( 1=2 ");
						
						if (StringUtil.isNotEmpty(oc)) {
							String[] ocArr = oc.split(",");
							for (int i = 0; i < ocArr.length; i++) {
								sql.append(" or " + buss.orgTable() + ".org_code like '" + ocArr[i] + "%' ");
							}
						}
						
						sql.append(")");
						sql.append(buss.auditSql());
					} else {
						sql.append(" and " + buss.orgTable() + ".org_code like '" + orgCode + "%'");
					}
					break;
				case AppGlobals.OPERATION_MANAGER:
					// 运营经理的角色
					if(checkRole(AppGlobals.OPERATION_MANAGER)){
						sql.append(buss.operationSql());
					}
					break;
				case AppGlobals.COMMERCIAL_MANAGER:
					// 商务经理的角色
					if (checkRole(AppGlobals.COMMERCIAL_MANAGER)) {
						sql.append(buss.commercialSql());
					}
					break;
				case AppGlobals.TECHNICAL_MANAGER:
					// 技术经理的角色
					if(checkRole(AppGlobals.TECHNICAL_MANAGER)){
						sql.append(buss.technicalSql());
					}
					break;
	//			case AppGlobals.XM_ADMIN:
	//				sql.append(" and " + buss.orgTable() + ".org_code like '" + orgCode + "%'");
	//				break;
				default:
					break;
			}
		}

		//如果没有审核员权限，则添加过滤条件
		if(!isAudit){
			sql.append(" and " + buss.orgTable() + ".org_code like '" + orgCode + "%'");
		}
		
		return sql.toString();
	}
	
	/**
	 * 检查用户是否有传入的参数权限权限
	 * 
	 * @param role
	 *            角色权限
	 */
	public boolean checkRole(String role) {
		TSUser user = ResourceUtil.getSessionUserName();
		return checkRole(user, role);
	}

	/**
	 * 检查用户是否有传入的参数权限权限
	 * 
	 * @param role
	 *            角色权限
	 */
	public boolean checkRole(TSUser user, String role) {

		// 是否拥有role这个角色权限
		boolean hasPermission = false;

		// 根据角色的不同来判断到底是初审还是复审，运营专员只能进行初审，平台审核员能进行复审，优先进行平台审核员的判断
		String roles = userService.getUserRole(user);
		String a[] = roles.split(",");

		for (int i = 0; i < a.length; i++) {
			if (a[i].equals(role)) {
				hasPermission = true;
				break;
			}
		}
		return hasPermission;
	}

	/**
	 * 获得注解
	 * 
	 * @param joinPoint
	 * @return
	 * @throws Exception
	 */
	private static BussAnnotation giveController(JoinPoint joinPoint) throws Exception {
		Signature signature = joinPoint.getSignature();
		MethodSignature methodSignature = (MethodSignature) signature;
		Method method = methodSignature.getMethod();

		if (method != null) {
			return method.getAnnotation(BussAnnotation.class);
		}
		return null;
	}

	/**
	 * 环绕通知 拦截指定的切点，这里拦截joinPointForAddOne切入点所指定的addOne()方法
	 * 
	 */
	// @Around("joinPointPermission()")
	// public Object interceptorAddOne(ProceedingJoinPoint joinPoint) throws
	// Throwable {
	// System.out.println("Aop start");
	// String methodRemark = getMthodRemark(joinPoint);
	// Object result = null;
	// try {
	// // 记录操作日志...谁..在什么时间..做了什么事情..
	// result = joinPoint.proceed();
	// } catch (Exception e) {
	// // 异常处理记录日志..log.error(e);
	// throw e;
	// }
	// System.out.println(methodRemark);
	// System.out.println("Aop end");
	// return result;
	// }

	// 获取方法的中文备注____用于记录用户的操作日志描述
	public static String getMthodRemark(ProceedingJoinPoint joinPoint) throws Exception {
		String targetName = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		System.out.println("====调用" + methodName + "方法-开始！");
		Object[] arguments = joinPoint.getArgs(); // 获得参数列表
		System.out.println("打印出方法调用时传入的参数，可以在这里通过添加参数的类型，进行一些简易逻辑处理和判断");
		if (arguments.length <= 0) {
			System.out.println("=== " + methodName + " 方法没有参数");
		} else {
			for (int i = 0; i < arguments.length; i++) {
				System.out.println("==== 参数   " + (i + 1) + " : " + arguments[i]);
			}
		}

		Class targetClass = Class.forName(targetName);
		Method[] method = targetClass.getMethods();
		String methode = "";
		for (Method m : method) {
			if (m.getName().equals(methodName)) {
				Class[] tmpCs = m.getParameterTypes();
				if (tmpCs.length == arguments.length) {
					BussAnnotation methodCache = m.getAnnotation(BussAnnotation.class);
					// methode = methodCache.orgType(); 数组
					break;
				}
			}
		}
		return methode;
	}

}

package com.yhy.lin.app.quartz;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
* Description : 权限过滤   AOP
* @author Timer
* @date 2017年9月21日 下午3:29:38
*/

@Aspect  
@Component
public class PermissionInterceptor {

	// 定义切入点  @Pointcut("execution(public * com.jay..*.*(..))")  -- 表示对com.jay 包下的所有方法都可添加切入点  
//    @Pointcut("execution(@com.yhy.lin.app.quartz.BussAnnotation * *(..))")
	@Pointcut("execution(* com.yhy.lin.*.*.*(..)) && @annotation(com.yhy.lin.app.quartz.BussAnnotation )")
//	@Pointcut("execution(* com.yhy.lin.service.impl.*.*(..))")
    public void joinPointPermission() {
    }

    //定义切入点  -- 拦截指定的方法  这里拦截 com.jay.demo3.aop1.impl.UserManagerApplogicImpl 的addOne()方法  
//    @Pointcut("execution(* * com.yhy.lin.*)")  
//    public void joinPointForAddOne(){  
//          
//    }
    
    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping) ") 
    private void requestMapping(){} 
    
    /** 
     * 环绕通知 用于拦截指定内容，记录用户的操作 
     */  
    @Around(value = "requestMapping()")
    public Object requestMapping(ProceedingJoinPoint pj) throws Throwable {  
        Object object = pj.proceed();
        System.out.println(pj.getSignature().getName());  
//        for(Object obj : pj.getArgs()){  
//            System.out.println(obj.toString());  
//        }  
        return object;  
    }  
    
    /** 
     * 环绕通知 用于拦截指定内容，记录用户的操作 
     */  
    @Around("joinPointPermission()")
    public Object interceptorPermission(ProceedingJoinPoint pj) throws Throwable {  
        Object object = pj.proceed();
        
        System.out.println(pj.getSignature().getName());  
//        for(Object obj : pj.getArgs()){  
//            System.out.println(obj.toString());  
//        }  
        return object;  
    }
    
    /** 
     * 环绕通知   拦截指定的切点，这里拦截joinPointForAddOne切入点所指定的addOne()方法 
     *  
     */  
//    @Around("joinPointPermission()")  
//    public Object interceptorAddOne(ProceedingJoinPoint joinPoint) throws Throwable {  
//        System.out.println("Aop start");  
//        String methodRemark = getMthodRemark(joinPoint);  
//        Object result = null;  
//        try {    
//            // 记录操作日志...谁..在什么时间..做了什么事情..    
//            result = joinPoint.proceed();    
//        } catch (Exception e) {    
//            // 异常处理记录日志..log.error(e);    
//            throw e;    
//        }    
//        System.out.println(methodRemark);  
//        System.out.println("Aop end");  
//        return result;  
//    }  
  
  
     // 获取方法的中文备注____用于记录用户的操作日志描述    
    public static String getMthodRemark(ProceedingJoinPoint joinPoint)    
            throws Exception {    
        String targetName = joinPoint.getTarget().getClass().getName();    
        String methodName = joinPoint.getSignature().getName();    
        System. out.println("====调用" +methodName+"方法-开始！");  
        Object[] arguments = joinPoint.getArgs();   //获得参数列表  
        System.out.println("打印出方法调用时传入的参数，可以在这里通过添加参数的类型，进行一些简易逻辑处理和判断");  
        if(arguments.length<=0){  
            System.out.println("=== "+methodName+" 方法没有参数");  
        }else{  
        for(int i=0;i<arguments.length;i++){  
            System.out.println("==== 参数   "+(i+1)+" : "+arguments[i]);  
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
                    methode = methodCache.orgType();    
                    break;    
                }    
            }    
        }    
        return methode;    
    }
	
}

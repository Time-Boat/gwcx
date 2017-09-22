package com.yhy.lin.app.quartz;

import java.lang.reflect.Modifier;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;  
import org.aspectj.lang.annotation.After;  
import org.aspectj.lang.annotation.Around;  
import org.aspectj.lang.annotation.Aspect;  
import org.aspectj.lang.annotation.Before;  
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;  
  
//切面
@Aspect   
@Component("check")
public class CheckUser {  
  
    @Pointcut("execution(* com.yhy.lin.app.quartz.*.find*(..))")  
    public void checkUser(){  
        System.out.println("**************The System is Searching Information For You****************");  
    }  
    
    //切入点   指定在什么情况下才进行切入
    @Pointcut("execution(* com.yhy.lin.app.quartz.*.add*(..))")  
    public void checkAdd(){  
        System.out.println("**************<< Add User >> Checking.....***************");  
    }  
      
    @Before("checkUser()")  
    public void beforeCheck(){  
        System.out.println(">>>>>>>> 准备搜查用户..........");  
    }  
      
    @After("checkUser()")  
    public void afterCheck(JoinPoint joinPoint){  
        System.out.println(
        		">>>>>>>>　搜查用户完毕..........");
        
        System.out.println(
        		"目标方法名为:" + joinPoint.getSignature().getName());
        
        System.out.println(
        		"目标方法所属类的简单类名:" + joinPoint.getSignature().getDeclaringType().getSimpleName());
        
        System.out.println(
        		"目标方法所属类的类名:" + joinPoint.getSignature().getDeclaringTypeName());
        
        System.out.println(
        		"目标方法声明类型:" + Modifier.toString(joinPoint.getSignature().getModifiers()));
        
        //获取传入目标方法的参数
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            System.out.println("第" + (i+1) + "个参数为:" + args[i]);
        }
        System.out.println("被代理的对象:" + joinPoint.getTarget());
        System.out.println("代理对象自己:" + joinPoint.getThis());
    }  
  
    @Before("checkAdd()")  
    public void beforeAdd(){  
        System.out.println(">>>>>>>>　增加用户--检查ing..........");  
    }  
    
    @After("checkAdd()")  
    public void afterAdd(){  
        System.out.println(">>>>>>>>　增加用户--检查完毕！未发现异常!..........");  
    }  
      
     //声明环绕通知    
    @Around("checkUser()")    
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {    
        System.out.println("进入方法---环绕通知");    
        Object o = pjp.proceed();    
        System.out.println("退出方法---环绕通知");    
        return o;    
    }    
}

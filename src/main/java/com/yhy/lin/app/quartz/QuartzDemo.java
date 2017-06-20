package com.yhy.lin.app.quartz;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

/**
* Description : 
* @author Administrator
* @date 2017年6月19日 下午6:04:30
*/
public class QuartzDemo {

	 public static void main(String[] args) throws Exception{
	        //读取properties配置文件
	        Properties prop = new Properties();
	        prop.load(QuartzDemo.class.getResourceAsStream("/set.properties"));
	        String toAccountId=prop.getProperty("toAccountId","fd60e46db0dc119cfea740c3375fd7c4");
	        String cron=prop.getProperty("cron","0/10 * 8-18 * * ?");
	        //创建Quartz的计划任务
	        Scheduler sched = new StdSchedulerFactory().getScheduler();
	        
	        //配置计划任务的工作类名，这个类需要实现Job接口，在execute方法中实现所需要做的工作
	        JobDetail job = new Job(QuartzTest.class).build();
	        //向工作类传递参数
	        job.getJobDataMap().put("toAccountId",toAccountId);           
	        
	        //配置计划任务的定时器
	        Trigger trigger = new Trigger().withSchedule(cronSchedule(cron)).build();
	        
	        Date date=sched.scheduleJob(job, trigger);//获得首次将要执行计划任务的时间，待会儿println出来
	        sched.start();//开始执行
	        
	        System.out.println("first time run at:"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
	        
	        //无限循环下去
	        boolean flag=true;
	        while(flag){
	            Thread.sleep(24*3600*1000);
	        }
	        sched.shutdown();
	    }
	 
}

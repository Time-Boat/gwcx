package com.yhy.lin.app.quartz;

import java.util.Date;

import org.jeecgframework.core.timer.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

/**
* Description : 
* @author Administrator
* @date 2017年6月19日 下午5:46:17
*/
public class QuartzTest extends Job{

	 @Override
	    public void execute(JobExecutionContext arg) throws JobExecutionException {
	        String[] time=getTimeStr();
	        String toAccountId=arg.getJobDetail().getJobDataMap().getString("toAccountId");    
	        String access_token=arg.getJobDetail().getJobDataMap().getString("access_token");//修改Excel模板
	        try {
	            new OpenExcelDemo().doOpenExcel(...);            
	        } catch (Exception e1) {
	            e1.printStackTrace();
	        }
	        
	        //将Excel绘制成PNG
	        String exportPNGPath=dirPath + time[1] + ".png";
	        DrawFromExcel dfe=new DrawFromExcel();
	        dfe.setExportPNGPath(exportPNGPath);
	        dfe.setFileName(fileName);
	        dfe.setSheetName(sheetName);
	        dfe.setFromIndex(fromIndex);
	        dfe.setToIndex(toIndex);
	        try {
	            dfe.draw();
	        } catch (Exception e1) {
	            e1.printStackTrace();
	        }
	        
	        String pngFileName=time[1];
	        try {
	            SendPostToYixin.doPost(toAccountId,access_token,pngFileName,urlPath);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    
	    public String[] getTimeStr() {
	        String[] time = new String[2];
	        Date date=new Date();
	        time[0]=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	        time[1]=new SimpleDateFormat("yyyyMMddHHmmss").format(date);
	        return time;
	    }
}

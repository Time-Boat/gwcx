package org.jeecgframework.web.demo.service.impl.test;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.yhy.lin.app.controller.AppInterfaceController;
import com.yhy.lin.entity.TransferorderEntity;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.web.demo.service.test.TaskDemoServiceI;
@Service("taskDemoService")
public class TaskDemoServiceImpl extends CommonServiceImpl implements TaskDemoServiceI {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(AppInterfaceController.class);
	
	@Override
	public void work() {
		
//		List<Map<String,Object>> t = findForJdbc("select * from transferorder where to_days(order_startime) = to_days(now())");
		
		//定时修改状态
		List<TransferorderEntity> tList = findByQueryString("from TransferorderEntity where to_days(order_startime) = to_days(now())");
		
		for(TransferorderEntity t : tList){
			t.setOrderStatus(0);
			logger.info("定时完成订单          时间：" + new Date().getTime() + "，订单id：" + t.getId());
			saveOrUpdate(t);
		}
		
//		org.jeecgframework.core.util.LogUtil.info("---------任务测试-------" + t.get(0).getOrderStartime());
		
	}

}

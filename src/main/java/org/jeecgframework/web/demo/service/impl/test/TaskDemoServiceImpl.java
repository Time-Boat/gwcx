package org.jeecgframework.web.demo.service.impl.test;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yhy.lin.app.controller.AppInterfaceController;
import com.yhy.lin.app.util.AppUtil;
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
		List<TransferorderEntity> tList = findByQueryString("from TransferorderEntity where to_days(order_startime) = to_days(now())"
				+ " and order_status='2' and order_paystatus='0' ");
		
		for(TransferorderEntity t : tList){
			t.setOrderStatus(0);
			logger.info("定时完成订单          时间：" + AppUtil.getDate() + "，订单id：" + t.getId());
			saveOrUpdate(t);
		}
		
		if(tList.size() <= 0){
			logger.info("时间：" + AppUtil.getDate() + "，没有未完成的订单。");
		}
		
//		org.jeecgframework.core.util.LogUtil.info("---------任务测试-------" + t.get(0).getOrderStartime());
		
	}

}

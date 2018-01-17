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

		String curTime = AppUtil.getCurTime();
		// List<Map<String,Object>> t = findForJdbc("select * from transferorder
		// where to_days(order_startime) = to_days(now())");

		// 定时修改状态
		List<TransferorderEntity> tList = findByQueryString(
				"from TransferorderEntity where order_status in ('1','2') and order_paystatus='0' and UNIX_TIMESTAMP(order_startime) <= UNIX_TIMESTAMP(SYSDATE()) ");

		for (TransferorderEntity t : tList) {

			if ("1".equals(t.getOrderStatus())) {
				t.setIsException("1");
			} else {
				t.setIsException("0");
			}

			t.setOrderStatus(0);
			t.setOrderCompletedTime(AppUtil.getDate());
			logger.info("定时完成订单          时间：" + AppUtil.getDate() + "，订单id：" + t.getId());
		}

		saveAllEntitie(tList);

		if (tList.size() <= 0) {
			logger.info("时间：" + curTime + "，没有未完成的订单。");
		}

		// 可以添加一个token过期的定时器，每天定时清空过期的token 不要清空token时间，用token更新时间来判断是不是新注册用户

		// 定时删除未支付订单
		
//		String delOrder = "delete from transferorder where to_days(order_startime) = to_days(now()) and order_status = ? ";
//		int delSum = executeSql(delOrder, 6);
//		logger.info("时间：" + curTime + "，已删除" + delSum + "条未支付的订单");

		// 定时清空未支付的订单

	}

}

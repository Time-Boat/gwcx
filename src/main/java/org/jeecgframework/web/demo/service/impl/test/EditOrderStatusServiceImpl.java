package org.jeecgframework.web.demo.service.impl.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.web.demo.service.test.OrderTimeServiceI;
import org.jeecgframework.web.system.service.SystemService;

@Service("editOrderStatusService")
public class EditOrderStatusServiceImpl extends CommonServiceImpl implements OrderTimeServiceI {

	@Autowired
	private SystemService systemService;
	
	@Override
	public void work() {
		
		//将订单状态改为已审核
		String sql = "update transferorder set order_status = '7' where applicationTime <= date_sub(NOW(),interval 30 minute) and order_status='1'";
		systemService.updateBySqlString(sql);
		
	}
}

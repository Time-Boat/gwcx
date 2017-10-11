package com.yhy.lin.app.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yhy.lin.app.controller.AppInterfaceController;
import com.yhy.lin.app.service.WeixinPayService;
import com.yhy.lin.entity.DealerInfoEntity;

/**
* Description : 
* @author Administrator
* @date 2017年7月4日 下午6:41:03
*/
@Service("WeixinPayService")
@Transactional
public class WeixinPayServiceImpl extends CommonServiceImpl implements WeixinPayService {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(AppInterfaceController.class);
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
	@Override
	public void updateBandingInfo(String openId, String phone) {
		
		DealerInfoEntity dc = null;
		
		String sql = "select dc.dealer_id from car_customer cc join dealer_customer dc on cc.open_id = dc.open_id "
				+ " where cc.phone = ?";
		
		//获取openId绑定到数据库      	先绑定，在获取
		int s = executeSql("update car_customer set open_id = ?, dealer_banding_time=now() where phone = ? ", openId, phone);
		logger.info("saveOpenId：" + s);
		
		List<Map<String,Object>> list = findForJdbc(sql, phone);
		if(list.size() > 0){
			String dealerId = list.get(0).get("dealer_id") + "";
			logger.info(dealerId);
			dc = get(DealerInfoEntity.class, dealerId);
			logger.info("WeixinPayServiceImpl   :" + dc.getScanCount());
			dc.setScanCount(dc.getScanCount() + 1);   //添加一次记录
			logger.info("WeixinPayServiceImpl   :" + dc.getScanCount());
			saveOrUpdate(dc);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

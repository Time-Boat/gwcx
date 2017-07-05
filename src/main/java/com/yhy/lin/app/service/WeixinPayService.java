package com.yhy.lin.app.service;

import org.jeecgframework.core.common.service.CommonService;

/**
* Description : 
* @author Administrator
* @date 2017年7月4日 下午6:37:56
*/
public interface WeixinPayService  extends CommonService{

	public void updateBandingInfo(String openId, String phone);
	
}

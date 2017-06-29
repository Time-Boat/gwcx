package com.yhy.lin.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yhy.lin.service.DealerInfoServiceI;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;

@Service("dealerInfoService")
@Transactional
public class DealerInfoServiceImpl extends CommonServiceImpl implements DealerInfoServiceI {
	
}
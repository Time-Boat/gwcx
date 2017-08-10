package com.yhy.lin.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yhy.lin.service.StartOrEndServiceI;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;

@Service("startOrEndService")
@Transactional
public class StartOrEndServiceImpl extends CommonServiceImpl implements StartOrEndServiceI {
	
}
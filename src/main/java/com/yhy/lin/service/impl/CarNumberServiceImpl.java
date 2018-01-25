package com.yhy.lin.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yhy.lin.service.CarNumberServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;

@Service("carNumberService")
@Transactional
public class CarNumberServiceImpl extends CommonServiceImpl implements CarNumberServiceI {
	
}
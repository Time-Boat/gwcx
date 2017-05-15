package com.yhy.lin.service.impl;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yhy.lin.service.ConductorServiceI;

@Service("ConductorServiceI")
@Transactional
public class ConductorServiceImpl extends CommonServiceImpl implements ConductorServiceI {
	
}
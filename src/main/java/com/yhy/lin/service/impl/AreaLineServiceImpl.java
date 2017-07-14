package com.yhy.lin.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yhy.lin.service.AreaLineServiceI;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;

@Service("areaLineService")
@Transactional
public class AreaLineServiceImpl extends CommonServiceImpl implements AreaLineServiceI {
	
}
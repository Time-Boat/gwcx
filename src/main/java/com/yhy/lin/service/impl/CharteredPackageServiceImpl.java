package com.yhy.lin.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yhy.lin.service.CharteredPackageServiceI;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;

@Service("charteredPackageService")
@Transactional
public class CharteredPackageServiceImpl extends CommonServiceImpl implements CharteredPackageServiceI {
	
}
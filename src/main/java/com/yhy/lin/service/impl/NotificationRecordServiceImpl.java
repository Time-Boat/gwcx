package com.yhy.lin.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yhy.lin.service.NotificationRecordServiceI;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;

@Service("notificationRecordService")
@Transactional
public class NotificationRecordServiceImpl extends CommonServiceImpl implements NotificationRecordServiceI {
	
}
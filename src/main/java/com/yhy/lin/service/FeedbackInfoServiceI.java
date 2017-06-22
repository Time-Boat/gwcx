package com.yhy.lin.service;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;

import com.yhy.lin.entity.FeedbackInfoEntity;

import net.sf.json.JSONObject;

public interface FeedbackInfoServiceI extends CommonService{

	public JSONObject getDatagrid(FeedbackInfoEntity feedbackInfo, DataGrid dataGrid,String realName,String phone);
}

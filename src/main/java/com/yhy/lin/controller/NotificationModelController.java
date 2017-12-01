package com.yhy.lin.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;

import com.yhy.lin.app.util.AppGlobals;
import com.yhy.lin.app.util.AppUtil;
import com.yhy.lin.app.util.SystemMessage;
import com.yhy.lin.entity.NotificationModelEntity;
import com.yhy.lin.service.NotificationModelServiceI;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @Title: Controller
 * @Description: 系统消息通知模板
 * @author zhangdaihao
 * @date 2017-11-24 11:00:14
 * @version V1.0
 *
 */
@Controller
@RequestMapping("/notificationModelController")
public class NotificationModelController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(NotificationModelController.class);

	@Autowired
	private NotificationModelServiceI notificationModelService;
	@Autowired
	private SystemService systemService;

	/**
	 * 系统消息通知模板列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		//SystemMessage.getInstance().saveMessage(systemService, "测试", "测试邮件内容", new String[]{AppGlobals.OPERATION_SPECIALIST}, new String[]{"1","2"});
		return new ModelAndView("yhy/notificationModel/notificationModelList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */
	@RequestMapping(params = "datagrid")
	public void datagrid(NotificationModelEntity notificationModel,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		JSONObject jObject = notificationModelService.getDatagrid(dataGrid, notificationModel);
		responseDatagrid(response, jObject);
	}

	/**
	 * 删除系统消息通知模板
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(NotificationModelEntity notificationModel, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		notificationModel = systemService.getEntity(NotificationModelEntity.class, notificationModel.getId());
		message = "系统消息通知模板删除成功";
		notificationModelService.delete(notificationModel);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加系统消息通知模板
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(NotificationModelEntity notificationModel, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		
		Date curDate = AppUtil.getDate();
		notificationModel.setMidifyTime(curDate);
		
		if (StringUtil.isNotEmpty(notificationModel.getId())) {
			message = "系统消息通知模板更新成功";
			NotificationModelEntity t = notificationModelService.get(NotificationModelEntity.class, notificationModel.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(notificationModel, t);
				notificationModelService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = "系统消息通知模板更新失败";
			}
		} else {
			message = "系统消息通知模板添加成功";
			notificationModel.setStatus("0");
			notificationModel.setCreateTime(curDate);
			String userId = ResourceUtil.getSessionUserName().getId();
			notificationModel.setCreateUserId(userId);
			
			notificationModelService.save(notificationModel);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		j.setMsg(message);
		return j;
	}
		
	/**
	 * 系统消息通知模板列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(NotificationModelEntity notificationModel, HttpServletRequest req) {
		
		List<Map<String,String>> roles = systemService.findListbySql(
				"select rolecode,rolename from t_s_role where rolecode not in('" + AppGlobals.XM_ADMIN + "','" + AppGlobals.D_ADMIN + "') ");
		String json = JSONArray.fromObject(roles).toString();
		
		req.setAttribute("roles", json);
		
		if (StringUtil.isNotEmpty(notificationModel.getId())) {
			notificationModel = notificationModelService.getEntity(NotificationModelEntity.class, notificationModel.getId());
			//爆炸了。。。前端通过对象取不到值，只能单独的拿出来set一下
			req.setAttribute("nType", notificationModel.getNType());
			req.setAttribute("notificationModelPage", notificationModel);
		}
		return new ModelAndView("yhy/notificationModel/notificationModel");
	}
	
}

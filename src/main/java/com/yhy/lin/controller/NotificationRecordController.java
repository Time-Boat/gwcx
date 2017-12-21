package com.yhy.lin.controller;

import java.util.ArrayList;
import java.util.List;

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
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;

import com.yhy.lin.entity.NotificationRecordEntity;
import com.yhy.lin.service.NotificationRecordServiceI;

import net.sf.json.JSONObject;

/**   
 * @Title: Controller
 * @Description: 系统消息发送记录
 * @author zhangdaihao
 * @date 2017-11-28 10:39:33
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/notificationRecordController")
public class NotificationRecordController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(NotificationRecordController.class);

	@Autowired
	private NotificationRecordServiceI notificationRecordService;
	@Autowired
	private SystemService systemService;

	/**
	 * 系统消息发送记录列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("yhy/notificationRecord/notificationRecordList");
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
	public void datagrid(NotificationRecordEntity notificationRecord, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		JSONObject jObject = notificationRecordService.getDatagrid(dataGrid, notificationRecord);
		responseDatagrid(response, jObject);
	}

	/**
	 * 删除系统消息发送记录
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(NotificationRecordEntity notificationRecord, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		notificationRecord = systemService.getEntity(NotificationRecordEntity.class, notificationRecord.getId());
		message = "系统消息发送记录删除成功";
		notificationRecordService.delete(notificationRecord);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);

		j.setMsg(message);
		return j;
	}

	/**
	 * 添加系统消息发送记录
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(NotificationRecordEntity notificationRecord, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(notificationRecord.getId())) {
			message = "系统消息发送记录更新成功";
			NotificationRecordEntity t = notificationRecordService.get(NotificationRecordEntity.class, notificationRecord.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(notificationRecord, t);
				notificationRecordService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = "系统消息发送记录更新失败";
			}
		} else {
			message = "系统消息发送记录添加成功";
			notificationRecordService.save(notificationRecord);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 系统消息发送记录列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(NotificationRecordEntity notificationRecord, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(notificationRecord.getId())) {
			try {
				String userId = ResourceUtil.getSessionUserName().getId();
				notificationRecord = notificationRecordService.getEntity(NotificationRecordEntity.class, notificationRecord.getId());
				
				//修改状态，设置为已读
				systemService.updateHql("update NotificationUserMiddleEntity set status='1' where recordId =? and userId =? ", notificationRecord.getId(),userId);
				logger.info("设为已读");
				
				String ntype=notificationRecord.getNType();
				String notification = getNtype(ntype);//获取通知方式
				
				req.setAttribute("notificationRecordPage", notificationRecord);
				req.setAttribute("notification", notification);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return new ModelAndView("yhy/notificationRecord/notificationRecord");
	}
	
	/**
	 * 获取通知方式
	 * @param ntype
	 * @return
	 */
	public String getNtype(String ntype){
		
		StringBuffer string = new StringBuffer();
		if (StringUtil.isNotEmpty(ntype)) {
			String sql ="SELECT t.typename from t_s_type t LEFT JOIN t_s_typegroup g on t.typegroupid=g.ID where g.typegroupcode='notify_type' and t.typecode in("+ntype+");";
			List<String> list = systemService.findListbySql(sql);
			if(list.size()>0){
				for (int i = 0; i < list.size(); i++) {
					String ntypes = list.get(i);
					if(i>0){
						string.append(",");
					}
					string.append(ntypes);
				}
			}
		}
		
		return string.toString();
	}


	/**
	 * 批量删除系统通知
	 * 
	 * @return
	 * @author Administrator
	 * @date 2017-04-17
	 */
	@RequestMapping(params = "doDeleteALLSelect")
	@ResponseBody
	public AjaxJson doDeleteALLSelect(NotificationRecordEntity notificationRecord, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		try {
			String ids = request.getParameter("ids");
			String[] entitys = ids.split(",");
			List<NotificationRecordEntity> list = new ArrayList<NotificationRecordEntity>();
			for (int i = 0; i < entitys.length; i++) {
				notificationRecord = systemService.getEntity(NotificationRecordEntity.class, entitys[i]);
				list.add(notificationRecord);
			}
			message = "批量删除成功！";
			// 批量逻辑删除
			notificationRecordService.deleteAllEntitie(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 全部设为已读
	 * @param notificationRecord
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "makeMessageRead")
	@ResponseBody
	public AjaxJson makeMessageRead(HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();

		try {
			String userId = ResourceUtil.getSessionUserName().getId();
			//全部设为已读
			systemService.updateHql("update NotificationUserMiddleEntity set status='1' where userId =? ",userId);
			
			message = "全部设置为已读成功！";
		} catch (Exception e) {
			e.printStackTrace();
		}
		j.setMsg(message);
		return j;
	}

}

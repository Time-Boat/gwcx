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

import com.yhy.lin.app.util.AppGlobals;
import com.yhy.lin.entity.DealerApplyEntity;
import com.yhy.lin.service.impl.DealerApplyServiceI;

import net.sf.json.JSONObject;

/**   
 * @Title: Controller
 * @Description: 渠道商申请模块
 * @author zhangdaihao
 * @date 2017-10-30 17:11:55
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/dealerApplyController")
public class DealerApplyController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DealerApplyController.class);

	@Autowired
	private DealerApplyServiceI dealerApplyService;
	@Autowired
	private SystemService systemService;
	
	/**
	 * 渠道商申请模块列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		boolean isAdmin  = checkRole(AppGlobals.XM_ADMIN);
		if(isAdmin){
			request.setAttribute("del", "1");
		}
		return new ModelAndView("yhy/dealerApply/dealerApplyList");
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
	public void datagrid(DealerApplyEntity dealerApply,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		
		String companyName = request.getParameter("companyName");
		String phone = request.getParameter("phone");
		String applyPeople = request.getParameter("applyPeople");
		JSONObject jObject = dealerApplyService.getDatagrid(dataGrid, companyName, phone, applyPeople);
		
		responseDatagrid(response, jObject);
		
	}

	/**
	 * 删除渠道商申请模块
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(DealerApplyEntity dealerApply, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		dealerApply = systemService.getEntity(DealerApplyEntity.class, dealerApply.getId());
		message = "渠道商申请模块删除成功";
		dealerApplyService.delete(dealerApply);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}

	/**
	 * 添加渠道商申请模块
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(DealerApplyEntity dealerApply, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(dealerApply.getId())) {
			message = "渠道商申请模块更新成功";
			DealerApplyEntity t = dealerApplyService.get(DealerApplyEntity.class, dealerApply.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(dealerApply, t);
				dealerApplyService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = "渠道商申请模块更新失败";
			}
		} else {
			message = "渠道商申请模块添加成功";
			dealerApplyService.save(dealerApply);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 渠道商申请模块列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(DealerApplyEntity dealerApply, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(dealerApply.getId())) {
			dealerApply = dealerApplyService.getEntity(DealerApplyEntity.class, dealerApply.getId());
			req.setAttribute("dealerApplyPage", dealerApply);
		}
		return new ModelAndView("yhy/dealerApply/dealerApply");
	}
	
	/**
	 * 专员列表
	 * 
	 * @return
	 */
	@RequestMapping(params = "getAttacheList")
	public ModelAndView getAttacheList(HttpServletRequest req) {
		String ids = req.getParameter("ids");
		req.setAttribute("ids", ids);
		return new ModelAndView("yhy/dealerApply/applyDealerAttacheList");
	}
	
	/**
	 * 获取专员列表
	 * 
	 * @return
	 */
	@RequestMapping(params = "getAttacheDatagrid")
	public void getAttacheDatagrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		
		boolean isAdmin  = checkRole(AppGlobals.XM_ADMIN);
		
		String role = "";
		if(isAdmin){
			role = AppGlobals.COMMERCIAL_MANAGER;
		}else{
			role = AppGlobals.COMMERCIAL_SPECIALIST;
		}
		JSONObject jObject = dealerApplyService.getAttacheDatagrid(dataGrid, role);
		
		responseDatagrid(response, jObject);
	}
	
	/**
	 * 分配专员
	 * 
	 * @return
	 */
	@RequestMapping(params = "allotAttache")
	@ResponseBody
	public AjaxJson allotAttache(HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		String message = null;

		String userId = req.getParameter("userId");
		String ids = req.getParameter("ids");

		List<DealerApplyEntity> list = new ArrayList<>();

		try {
			String[] idArr = ids.split(",");
			for (int i = 0; i < idArr.length; i++) {

				DealerApplyEntity dealerInfo = dealerApplyService.getEntity(DealerApplyEntity.class, idArr[i]);
				dealerInfo.setResponsibleUserId(userId);
				list.add(dealerInfo);
			}
			
			dealerApplyService.saveAllEntitie(list);
		} catch (Exception e) {
			message = "服务器异常";
			e.printStackTrace();
		}
		message = "分配成功";
		j.setMsg(message);
		return j;
	}
	
}

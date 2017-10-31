package com.yhy.lin.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;

import com.yhy.lin.entity.DealerApplyEntity;
import com.yhy.lin.service.impl.DealerApplyServiceI;

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
		CriteriaQuery cq = new CriteriaQuery(DealerApplyEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, dealerApply, request.getParameterMap());
		this.dealerApplyService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
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
	
}

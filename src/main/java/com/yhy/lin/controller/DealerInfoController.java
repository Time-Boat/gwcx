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

import javax.validation.Validator;

import com.yhy.lin.app.util.AppGlobals;
import com.yhy.lin.app.util.AppUtil;
import com.yhy.lin.app.wechat.WeixinPayUtil;
import com.yhy.lin.entity.DealerInfoEntity;
import com.yhy.lin.service.DealerInfoServiceI;

/**   
 * @Title: Controller
 * @Description: 渠道商信息
 * @author zhangdaihao
 * @date 2017-06-29 17:51:18
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/dealerInfoController")
public class DealerInfoController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DealerInfoController.class);

	@Autowired
	private DealerInfoServiceI dealerInfoService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * 渠道商信息列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("yhy/dealer/dealerInfoList");
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
	public void datagrid(DealerInfoEntity dealerInfo,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(DealerInfoEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, dealerInfo, request.getParameterMap());
		this.dealerInfoService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除渠道商信息
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(DealerInfoEntity dealerInfo, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		dealerInfo = systemService.getEntity(DealerInfoEntity.class, dealerInfo.getId());
		message = "渠道商信息删除成功";
		dealerInfoService.delete(dealerInfo);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加渠道商信息
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(DealerInfoEntity dealerInfo, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(dealerInfo.getId())) {
			message = "渠道商信息更新成功";
			DealerInfoEntity t = dealerInfoService.get(DealerInfoEntity.class, dealerInfo.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(dealerInfo, t);
				dealerInfoService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = "渠道商信息更新失败";
			}
		} else {
			message = "渠道商信息添加成功";
			dealerInfo.setCreateDate(AppUtil.getDate());
			dealerInfo.setScanCount("0");
			
			dealerInfoService.save(dealerInfo);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 生成二维码
	 * 
	 * @return
	 */
	@RequestMapping(params = "generateQRCode")
	@ResponseBody
	public AjaxJson generateQRCode(DealerInfoEntity dealerInfo, HttpServletRequest req) {
		String message = null;
		AjaxJson j = new AjaxJson();
		
		dealerInfo = dealerInfoService.getEntity(DealerInfoEntity.class, dealerInfo.getId());
		try {
			//文件存储路径
			String path = AppGlobals.QR_CODE_FILE_PATH + req.getParameter("id") + ".jpg";
			WeixinPayUtil.getQRCode(req.getParameter("id"), AppGlobals.IMAGE_BASE_FILE_PATH + path);
			
			dealerInfo.setQrCodeUrl(path);
			dealerInfoService.saveOrUpdate(dealerInfo);
		} catch (Exception e) {
			message = "二维码生成失败";
			e.printStackTrace();
		}
		message = "二维码生成成功";
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 渠道商信息列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(DealerInfoEntity dealerInfo, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(dealerInfo.getId())) {
			dealerInfo = dealerInfoService.getEntity(DealerInfoEntity.class, dealerInfo.getId());
			req.setAttribute("dealerInfoPage", dealerInfo);
		}
		return new ModelAndView("yhy/dealer/dealerInfo");
	}
	
}

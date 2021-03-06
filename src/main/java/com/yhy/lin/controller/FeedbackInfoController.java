package com.yhy.lin.controller;
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
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;

import com.yhy.lin.app.entity.CarCustomerEntity;
import com.yhy.lin.entity.FeedbackInfoEntity;
import com.yhy.lin.service.CarCustomerServiceI;
import com.yhy.lin.service.FeedbackInfoServiceI;

import net.sf.json.JSONObject;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.jeecgframework.core.beanvalidator.BeanValidators;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.net.URI;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;

/**   
 * @Title: Controller
 * @Description: 乘客反馈信息
 * @author zhangdaihao
 * @date 2017-06-21 11:51:44
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/feedbackInfoController")
public class FeedbackInfoController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(FeedbackInfoController.class);

	@Autowired
	private FeedbackInfoServiceI feedbackInfoService;
	@Autowired
	private CarCustomerServiceI carCustomerServiceI;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * 乘客反馈信息列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("yhy/feedback/feedbackInfoList");
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
	public void datagrid(FeedbackInfoEntity feedbackInfo,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		/*CriteriaQuery cq = new CriteriaQuery(FeedbackInfoEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, feedbackInfo, request.getParameterMap());
		this.feedbackInfoService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);*/
		String realName = request.getParameter("realName");
		String phone = request.getParameter("phone");
		JSONObject jObject = feedbackInfoService.getDatagrid(feedbackInfo, dataGrid,realName,phone);

		responseDatagrid(response, jObject);
	}
	

	/**
	 * 删除乘客反馈信息
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(FeedbackInfoEntity feedbackInfo, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		feedbackInfo = systemService.getEntity(FeedbackInfoEntity.class, feedbackInfo.getId());
		message = "乘客反馈信息删除成功";
		feedbackInfoService.delete(feedbackInfo);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加乘客反馈信息
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(FeedbackInfoEntity feedbackInfo, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(feedbackInfo.getId())) {
			message = "乘客反馈信息更新成功";
			FeedbackInfoEntity t = feedbackInfoService.get(FeedbackInfoEntity.class, feedbackInfo.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(feedbackInfo, t);
				feedbackInfoService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = "乘客反馈信息更新失败";
			}
		} else {
			message = "乘客反馈信息添加成功";
			feedbackInfoService.save(feedbackInfo);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 乘客反馈信息列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(FeedbackInfoEntity feedbackInfo, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(feedbackInfo.getId())) {
			feedbackInfo = feedbackInfoService.getEntity(FeedbackInfoEntity.class, feedbackInfo.getId());
			req.setAttribute("feedbackInfoPage", feedbackInfo);
		}
		
		return new ModelAndView("yhy/feedback/feedbackInfo");
	}
	
}

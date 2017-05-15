package com.yhy.lin.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.jeecgframework.core.beanvalidator.BeanValidators;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;

import com.yhy.lin.entity.EnterCompanyInfoEntity;
import com.yhy.lin.service.EnterCompanyInfoServiceI;

import net.sf.json.JSONObject;

/**
 * @Title: Controller
 * @Description: 入驻公司信息
 * @author zhangdaihao
 * @date 2017-04-18 17:26:36
 * @version V1.0
 *
 */
@Controller
@RequestMapping("/enterCompanyInfoController")
public class EnterCompanyInfoController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(EnterCompanyInfoController.class);

	@Autowired
	private EnterCompanyInfoServiceI enterCompanyInfoService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;

	/**
	 * 入驻公司信息列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("/yhy/cCompany/enterCompanyInfoList");
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
	public void datagrid(EnterCompanyInfoEntity enterCompanyInfo, HttpServletRequest request,
			HttpServletResponse response, DataGrid dataGrid) {

		String startTime = request.getParameter("createDate_begin");
		String endTime = request.getParameter("createDate_end");

		String departname = request.getParameter("departname");

		String orgCode = ResourceUtil.getSessionUserName().getCurrentDepart().getOrgCode();

		JSONObject jObject = enterCompanyInfoService.getDatagrid(departname, startTime, endTime, orgCode, dataGrid);

		responseDatagrid(response, jObject);
	}

	/**
	 * 删除入驻公司信息
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(EnterCompanyInfoEntity enterCompanyInfo, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		enterCompanyInfo = systemService.getEntity(EnterCompanyInfoEntity.class, enterCompanyInfo.getId());
		message = "入驻公司信息删除成功";
		enterCompanyInfoService.delete(enterCompanyInfo);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);

		j.setMsg(message);
		return j;
	}

	/**
	 * 添加入驻公司信息
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(EnterCompanyInfoEntity enterCompanyInfo, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();

		String deptId = request.getParameter("deptId");

		if (StringUtil.isNotEmpty(enterCompanyInfo.getId())) {
			message = "入驻公司信息更新成功";
			EnterCompanyInfoEntity t = enterCompanyInfoService.get(EnterCompanyInfoEntity.class,
					enterCompanyInfo.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(enterCompanyInfo, t);
				enterCompanyInfoService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = "入驻公司信息更新失败";
			}
		} else {
			message = "入驻公司信息添加成功";

			// 默认设置为0 未删除状态
			enterCompanyInfo.setDeleteFlag(0);

			enterCompanyInfo.setDepartid(deptId);

			enterCompanyInfoService.save(enterCompanyInfo);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 入驻公司信息列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(EnterCompanyInfoEntity enterCompanyInfo, HttpServletRequest req) {
		String deptId = req.getParameter("deptId");
		String departName = req.getParameter("departName");
		String id = req.getParameter("id");

		try {
			if (StringUtil.isNotEmpty(departName)) {
				departName = new String(departName.getBytes("iso-8859-1"), "utf-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		req.setAttribute("deptId", deptId);
		req.setAttribute("departName", departName);

		if (StringUtil.isNotEmpty(id) || StringUtil.isNotEmpty(enterCompanyInfo.getId())) {
			enterCompanyInfo = enterCompanyInfoService.getEntity(EnterCompanyInfoEntity.class,
					enterCompanyInfo.getId());
			req.setAttribute("enterCompanyInfoPage", enterCompanyInfo);
		}
		return new ModelAndView("/yhy/cCompany/enterCompanyInfo");
	}

}

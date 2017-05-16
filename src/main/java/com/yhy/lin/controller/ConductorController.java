package com.yhy.lin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.jeecgframework.core.annotation.config.AutoMenu;
import org.jeecgframework.core.annotation.config.AutoMenuOperation;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.demo.service.test.JeecgDemoServiceI;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.yhy.lin.entity.ConductorEntity;
import com.yhy.lin.entity.LineInfoEntity;
import com.yhy.lin.service.ConductorServiceI;
import com.yhy.lin.service.LineInfoServiceI;

import net.sf.json.JSONObject;

/**
 * @Title: Controller
 * @Description: 验票员信息管理
 * @author linj
 * @date 2013-01-23 17:12:40
 * @version V1.0
 *
 */
// @Scope("prototype")
@Controller
@RequestMapping("/conductorController")
public class ConductorController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ConductorController.class);

	@Autowired
	private LineInfoServiceI lineInfoService;
	@Autowired
	private ConductorServiceI conductorService;

	@Autowired
	private SystemService systemService;

	/**
	 * 验票员信息管理跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "conductorList")
	public ModelAndView conductorList(HttpServletRequest request) {
		return new ModelAndView("yhy/conductor/conductorList");
	}

	/**
	 * 验票员选择线路页面
	 * 
	 * @return
	 */
	@RequestMapping(params = "conductorTolinesList")
	public ModelAndView conductorTolinesList(HttpServletRequest request) {
		String ywlx = request.getParameter("ywlx");
		request.setAttribute("ywlx", ywlx);
		return new ModelAndView("yhy/conductor/conductorTolinesList");
	}

	@RequestMapping(params = "datagridLines")
	public void datagridLines(LineInfoEntity lineInfos, HttpServletRequest request, HttpServletResponse response,
			DataGrid dataGrid) {
		String ywlx = request.getParameter("ywlx");
		
		if(StringUtil.isNotEmpty(ywlx)){
			if(ywlx.equals("0")){
				ywlx = " < '2' ";
			}else{
				ywlx = " >= '2' ";
			}
		}
		
		JSONObject jObject = lineInfoService.getDatagrid2(lineInfos, dataGrid, ywlx);
		responseDatagrid(response, jObject);
	}

	/**
	 * easyuiAJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param
	 */
	@RequestMapping(params = "datagrid")
	public void datagrid(ConductorEntity conductors, HttpServletRequest request, HttpServletResponse response,
			DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(ConductorEntity.class, dataGrid);
		// 查询出来所有逻辑为删除的
		cq.eq("deleteFlag", Globals.Delete_Normal);
		// 查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, conductors, request.getParameterMap());
		this.conductorService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(ConductorEntity conductor, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		String del = request.getParameter("deleteFlag");
		try {
			// String id = conductor.getId().split("\\*")[0];
			/*
			 * 通过deleteFlag来区别逻辑删除和物理删除 1：逻辑删除 2 ：物理删除
			 */
			conductor = systemService.getEntity(ConductorEntity.class, conductor.getId());
			if ("1".equals(del)) {
				conductor.setDeleteFlag((short) 1);
				message = "逻辑删除成功";
				conductorService.saveOrUpdate(conductor);
				// systemService.updateBySqlString("update conductor set
				// delete_flag =1 where id='"+id+"'");
			} else {
				message = "删除成功";
				conductorService.delete(conductor);
			}
		} catch (Exception e) {
		}
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		j.setMsg(message);
		return j;
	}

	/**
	 * 保存检票员信息
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	@AutoMenuOperation(name = "添加", code = "add")
	public AjaxJson save(ConductorEntity conductor, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		conductor.setDeleteFlag((short) 0);// 默认都是为做逻辑删除的数据
		String lineInfos = request.getParameter("lineInfos");
		System.out.println(lineInfos);
		if (StringUtil.isNotEmpty(conductor.getId())) {
			message = "验票员: " + conductor.getName() + "被更新成功";
			ConductorEntity t = conductorService.get(ConductorEntity.class, conductor.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(conductor, t);
				conductorService.saveOrUpdate(t);
				// -----数据修改日志[类SVN]------------
				Gson gson = new Gson();
				systemService.addDataLog("conductor", t.getId(), gson.toJson(t));
				// -----数据修改日志[类SVN]------------
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			message = "验票员: " + conductor.getName() + "被添加成功";
			conductor.setStatus("0");
			try {
				conductorService.save(conductor);
				// -----数据修改日志[类SVN]------------
				Gson gson = new Gson();
				systemService.addDataLog("conductor", conductor.getId(), gson.toJson(conductor));
				// -----数据修改日志[类SVN]------------
				systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				j.setMsg("线路添加失败！");
				logger.error(ExceptionUtil.getExceptionMessage(e));
				// e.printStackTrace();
			}
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 去验票人员添加页面
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(ConductorEntity conductor, HttpServletRequest req) {
		// 获取部门信息
		List<TSDepart> departList = systemService.getList(TSDepart.class);
		req.setAttribute("departList", departList);

		Map sexMap = new HashMap();
		sexMap.put(0, "男");
		sexMap.put(1, "女");
		req.setAttribute("sexMap", sexMap);

		if (StringUtil.isNotEmpty(conductor.getId())) {
			conductor = conductorService.getEntity(ConductorEntity.class, conductor.getId());
			req.setAttribute("conductor", conductor);
		}

		List<LineInfoEntity> listLine = systemService.findByProperty(LineInfoEntity.class, "deleteFlag", (short) 0);
		if (listLine.size() > 0) {
			req.setAttribute("listLine", listLine);
		}

		return new ModelAndView("yhy/conductor/conductorAdd");
	}

	/**
	 * 批量逻辑删除选中的所有验票员
	 * 
	 * @return
	 * @author linj
	 * @date 2017-04-11 14:53:00
	 */
	@RequestMapping(params = "doDeleteALLSelect")
	@ResponseBody
	public AjaxJson doDeleteALLSelect(ConductorEntity conductor, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		try {
			String ids = request.getParameter("ids");
			String[] entitys = ids.split(",");
			List<ConductorEntity> list = new ArrayList<ConductorEntity>();
			for (int i = 0; i < entitys.length; i++) {
				conductor = systemService.getEntity(ConductorEntity.class, entitys[i]);
				conductor.setDeleteFlag((short) 1);
				list.add(conductor);
			}
			message = "逻辑删除成功";
			// 批量逻辑删除
			conductorService.updateAllEntitie(list);
		} catch (Exception e) {
		}
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		j.setMsg(message);
		return j;
	}

}

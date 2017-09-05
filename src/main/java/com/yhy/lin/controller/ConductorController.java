package com.yhy.lin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.jeecgframework.core.annotation.config.AutoMenuOperation;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSUser;
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
		
		String cr_bg = request.getParameter("createDate_begin");//创建时间
		String cr_en = request.getParameter("createDate_end");
		
		JSONObject jObject = conductorService.getDatagrid(dataGrid,conductors,cr_bg,cr_en);
		
		responseDatagrid(response, jObject);
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
		TSUser user = ResourceUtil.getSessionUserName();
		conductor.setDepartId(user.getCurrentDepart().getId());
		conductor.setDeleteFlag((short) 0);// 默认都是为做逻辑删除的数据
		//验票员线路没有添加对应的线路id
		//System.out.println(lineInfos);
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
				conductor.setCreateUserId(user.getId());
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

		Map<Integer,String> sexMap = new HashMap<>();
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
	
	/**
	 * 检查手机号是否在数据库中已存在
	 */
	@RequestMapping(params = "checkPhone")
	@ResponseBody
	public AjaxJson checkPhone(HttpServletRequest request) {
		String message = "";
		boolean success = false;
		AjaxJson j = new AjaxJson();
		try {
			String phone = request.getParameter("phone");
			String id = request.getParameter("id");
			
			long l = conductorService.getCountForJdbcParam("select count(*) from conductor where phoneNumber=? and id <> ? ", new Object[]{phone,id});
			
			if(l > 0){
				message = "手机号已存在";
				success = false;
			}else{
				success = true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setSuccess(success);
		j.setMsg(message);
		return j;
	}

}

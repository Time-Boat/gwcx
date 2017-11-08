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
import org.jeecgframework.web.system.pojo.base.TSUserOrg;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.yhy.lin.app.util.AppUtil;
import com.yhy.lin.entity.ConductorEntity;
import com.yhy.lin.entity.ConductorLineInfoEntity;
import com.yhy.lin.entity.LineInfoEntity;
import com.yhy.lin.service.ConductorServiceI;
import com.yhy.lin.service.LineInfoServiceI;

import net.sf.json.JSONArray;
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
		request.setAttribute("lineNameList",getJurisdiction());
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

		String username = request.getParameter("username");
		String cr_bg = request.getParameter("createDate_begin");//创建时间
		String cr_en = request.getParameter("createDate_end");
		String lineId = request.getParameter("lineId");
		JSONObject jObject = conductorService.getDatagrid(dataGrid,conductors,cr_bg,cr_en,lineId,username);

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
		conductor.setDeleteFlag((short) 0);// 默认都是为做逻辑删除的数据、
		conductor.setCreateUserId(user.getId());
		conductor.setCreateDate(AppUtil.getDate());
		
		//验票员线路没有添加对应的线路id
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
			try {
				conductor.setApplicationStatus("-1");
				conductor.setConductStatus("0");
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
		
		String cid = conductor.getId();
		
		systemService.executeSql("delete from conductor_line_info where conductor_id = ? ", cid);
		
		String lineIds = request.getParameter("lineIds");
		String[] lineArr = lineIds.split(",");
		
		List<ConductorLineInfoEntity> clList = new ArrayList<>();
		
		for(String lineId : lineArr){
			ConductorLineInfoEntity cl = new ConductorLineInfoEntity();
			cl.setConductorId(cid);
			cl.setLineId(lineId);
			clList.add(cl);
		}
		conductorService.saveAllEntitie(clList);
		
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

		if (StringUtil.isNotEmpty(conductor.getId())) {
			conductor = conductorService.getEntity(ConductorEntity.class, conductor.getId());
			req.setAttribute("conductor", conductor);
			
			//获取验票员管理线路信息
			List<Map<String, Object>> listLine = systemService.findForJdbc(
					" select l.id,l.name from conductor d LEFT JOIN conductor_line_info cl on d.id=cl.conductor_id "
					+ " LEFT JOIN lineInfo l on l.id = cl.line_id where cl.conductor_id = ? ", conductor.getId());
			if (listLine.size() > 0) {
				StringBuffer ids = new StringBuffer();
				StringBuffer names = new StringBuffer();
				
				for(Map<String, Object> map : listLine){
					ids.append(map.get("id"));
					ids.append(",");
					names.append(map.get("name"));
					names.append(",");
				}
				
				
				req.setAttribute("lineIds", ids.deleteCharAt(ids.length()-1).toString());
				req.setAttribute("lineNames", names.deleteCharAt(names.length()-1).toString());
			}
		}

		return new ModelAndView("yhy/conductor/conductorAdd");
	}

	/**
	 * 查看验票员详情
	 * 
	 * @return
	 */
	@RequestMapping(params = "conductordetail")
	public ModelAndView conductordetail(ConductorEntity conductor,HttpServletRequest req) {
		if (StringUtil.isNotEmpty(conductor.getId())) {
			conductor = systemService.getEntity(ConductorEntity.class, conductor.getId());
			req.setAttribute("conductor", conductor);
			if(StringUtil.isNotEmpty(conductor.getAuditor())) {
				TSUser auditor = this.systemService.getEntity(TSUser.class, conductor.getAuditor());
				req.setAttribute("auditor", auditor);
			}
			if(StringUtil.isNotEmpty(conductor.getApplicationUserId())) {
				TSUser user = this.systemService.getEntity(TSUser.class, conductor.getApplicationUserId());
				req.setAttribute("user", user);
			}

		}

		return new ModelAndView("yhy/conductor/conductorDetial");
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

	/**
	 * 获取线路
	 */
	public String getJurisdiction(){
		String orgCode = ResourceUtil.getSessionUserName().getCurrentDepart().getOrgCode();
		String code="";
		if(orgCode.length()>6){
			code = orgCode.substring(0,6);
		}else{
			code=orgCode;
		}

		// 添加了权限
		String sql ="select l.id,l.name from lineinfo l,t_s_depart t where l.departId=t.ID and l.status='0' and t.org_code like '" + code + "%' ";
		List<Object> list = this.systemService.findListbySql(sql);
		StringBuffer json = new StringBuffer("{'data':[");
		if(list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				Object[] ob = (Object[]) list.get(i);
				String id = ob[0]+"";
				String name = ob[1]+"";
				json.append("{");
				json.append("'lineId':'" +id + "',");
				json.append("'lineName':'"+ name + "'");
				json.append("},");
			}
		}else{
			json.append("{");
			json.append("'lineId':'',");
			json.append("'lineName':''");
			json.append("},");
		}
		json.delete(json.length()-1, json.length());
		json.append("]}");

		return json.toString();
	}

	/**
	 * 申请启动
	 */
	@RequestMapping(params = "applyEnable")
	@ResponseBody
	public AjaxJson applyEnable(HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		String id = request.getParameter("id");

		if(StringUtil.isNotEmpty(id)){
			ConductorEntity  conductor = this.systemService.getEntity(ConductorEntity.class, id);
			if(StringUtil.isNotEmpty(conductor)){
				conductor.setApplicationStatus("0");//待审核
				if("1".equals(conductor.getConductStatus())){
					conductor.setApplyContent("1");//申请内容
				}else{
					conductor.setApplyContent("0");//申请内容
				}
				conductor.setApplicationTime(AppUtil.getDate());
				conductor.setApplicationUserId(ResourceUtil.getSessionUserName().getId());
			}
			try {
				message = "申请成功！";
				this.systemService.saveOrUpdate(conductor);
			} catch (Exception e) {
				// TODO: handle exception
				message = "服务器异常！";
			}
		}

		j.setMsg(message);
		return j;
	}

	/**
	 * 审核同意
	 */
	@RequestMapping(params = "agree")
	@ResponseBody
	public AjaxJson agree(HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		String id = request.getParameter("id");
		if(StringUtil.isNotEmpty(id)){
			ConductorEntity  conductor = this.systemService.getEntity(ConductorEntity.class, id);
			if(StringUtil.isNotEmpty(conductor)){
				conductor.setApplicationStatus("1");
				/*if("0".equals(conductor.getConductStatus())){
					conductor.setConductStatus("1");;//验票员状态
				}else{
					conductor.setConductStatus("0");;//验票员状态
				}*/
				if("0".equals(conductor.getApplyContent())){
					conductor.setConductStatus("1");
				}else{
					conductor.setConductStatus("2");
				}

				conductor.setAuditor(ResourceUtil.getSessionUserName().getId());
				conductor.setAuditTime(AppUtil.getDate());
			}

			try {
				message = "申请成功！";
				this.systemService.saveOrUpdate(conductor);
			} catch (Exception e) {
				// TODO: handle exception
				message = "服务器异常！";
			}
		}

		j.setMsg(message);
		return j;
	}

	/**
	 * 申请拒绝
	 */
	@RequestMapping(params = "refuse")
	@ResponseBody
	public AjaxJson refuse(HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		String id = request.getParameter("id");
		String rejectReason = request.getParameter("rejectReason");

		if (StringUtil.isNotEmpty(id)) {
			ConductorEntity conductor = this.systemService.getEntity(ConductorEntity.class, id);

			if (StringUtil.isNotEmpty(conductor)) {
				conductor.setRefusalReason(rejectReason);
				conductor.setApplicationStatus("2");
				conductor.setAuditor(ResourceUtil.getSessionUserName().getId());
				conductor.setAuditTime(AppUtil.getDate());
			}

			try {
				message = "拒绝成功！";
				this.systemService.saveOrUpdate(conductor);
			} catch (Exception e) {
				// TODO: handle exception
				message = "服务器异常！";
			}
		}

		j.setMsg(message);
		return j;
	}

	/**
	 * 获取拒绝原因
	 */
	@RequestMapping(params = "getReason")
	@ResponseBody
	public AjaxJson getReason(HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		String id = request.getParameter("id");
		if (StringUtil.isNotEmpty(id)) {
			ConductorEntity conductor = this.systemService.getEntity(ConductorEntity.class, id);

			if (StringUtil.isNotEmpty(conductor)) {
				message=conductor.getRefusalReason();
			}
		}

		j.setMsg(message);
		return j;
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
		return new ModelAndView("yhy/conductor/conductorAttacheList");
	}
	
	/**
	 * 分配专员
	 * 
	 * @return
	 */
	@RequestMapping(params = "conductorAllotAttache")
	@ResponseBody
	public AjaxJson conductorAllotAttache(HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		String message = null;

		String userId = req.getParameter("userId");
		String ids = req.getParameter("ids");

		List<ConductorEntity> conductorList = new ArrayList<>();

		try {
			String[] idArr = ids.split(",");
			for (int i = 0; i < idArr.length; i++) {

				ConductorEntity conductor = systemService.getEntity(ConductorEntity.class, idArr[i]);
				TSUserOrg t = systemService.findUniqueByProperty(TSUserOrg.class, "tsUser.id", userId);
				conductor.setCreateUserId(userId);
				conductor.setDepartId(t.getTsDepart().getId());
				
				conductorList.add(conductor);
			}
			
			systemService.saveAllEntitie(conductorList);
		} catch (Exception e) {
			message = "服务器异常";
			e.printStackTrace();
		}
		message = "分配成功";
		j.setMsg(message);
		return j;
	}
	

}

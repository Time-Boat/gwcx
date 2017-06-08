package com.yhy.lin.controller;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.common.UploadFile;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;

import javax.validation.Validator;

import com.yhy.lin.entity.DriversInfoEntity;
import com.yhy.lin.service.DriversInfoServiceI;

import net.sf.json.JSONObject;

/**   
 * @Title: Controller
 * @Description: 司机信息表
 * @author zhangdaihao
 * @date 2017-04-22 01:24:32
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/driversInfoController")
public class DriversInfoController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DriversInfoController.class);

	@Autowired
	private DriversInfoServiceI driversInfoService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;

	/**
	 * 司机信息表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("yhy/drivers/driversInfoList");
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
	public void datagrid(DriversInfoEntity driversInfo,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		
		String sex = request.getParameter("sex");
		String name = request.getParameter("name");
		String phoneNumber = request.getParameter("phoneNumber");
		
		JSONObject jObject = driversInfoService.getDatagrid(dataGrid ,sex ,name ,phoneNumber);
		
		responseDatagrid(response, jObject);
	}

	/**
	 * 删除司机信息表
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(DriversInfoEntity driversInfo, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		driversInfo = systemService.getEntity(DriversInfoEntity.class, driversInfo.getId());
		driversInfo.setDeleteFlag(1);
		message = "司机信息表删除成功";
//		driversInfoService.delete(driversInfo);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加司机信息表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(DriversInfoEntity driversInfo, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(driversInfo.getId())) {
			message = "司机信息表更新成功";
			DriversInfoEntity t = driversInfoService.get(DriversInfoEntity.class, driversInfo.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(driversInfo, t);
				driversInfoService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = "司机信息表更新失败";
			}
		} else {
			message = "司机信息表添加成功";
			driversInfo.setCreateDate(new Date(System.currentTimeMillis()));
			driversInfo.setDeleteFlag(0);
			driversInfoService.save(driversInfo);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 添加司机信息,保存图片
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "saveOrUpdate", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson saveOrUpdate(DriversInfoEntity driversInfo,HttpServletRequest request) throws Exception {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "司机信息表添加成功";
		driversInfo.setCreateDate(new Date(System.currentTimeMillis()));
		driversInfo.setDeleteFlag(0);
		UploadFile uploadFile = new UploadFile(request, driversInfo);
		//uploadFile.setCusPath("plug-in/accordion/images");
		uploadFile.setCusPath("image");    
		uploadFile.setRealPath("drivingLicenseImgUrl");  
		uploadFile.setObject(driversInfo);  
		uploadFile.setRename(true);
		systemService.uploadFile(uploadFile);	
		j.setMsg(message);
		
		return j;
	}

	/**
	 * 司机信息表列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(DriversInfoEntity driversInfo, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(driversInfo.getId())) {
			driversInfo = driversInfoService.getEntity(DriversInfoEntity.class, driversInfo.getId());
			req.setAttribute("driversInfoPage", driversInfo);
		}
		if(StringUtil.isNotEmpty(req.getParameter("type"))){
			String type = req.getParameter("type");
			req.setAttribute("type", type);
		}
		return new ModelAndView("yhy/drivers/driversInfo");
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
			
			long l = driversInfoService.getCountForJdbcParam("select count(*) from driversinfo where phoneNumber=? and id <> ? ", new Object[]{phone,id});
			
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

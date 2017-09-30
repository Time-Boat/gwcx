package com.yhy.lin.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.common.UploadFile;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;

import com.yhy.lin.app.util.AppGlobals;
import com.yhy.lin.entity.DriversInfoEntity;
import com.yhy.lin.entity.OpenCityEntity;
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

	/**
	 * 司机信息表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "driversInfoList")
	public ModelAndView list(HttpServletRequest request) {
		request.setAttribute("cityList",getOpencity());
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
		String status = request.getParameter("status");
		String cityID= request.getParameter("cityID");
		
		JSONObject jObject = driversInfoService.getDatagrid(dataGrid ,driversInfo,cityID);
		
		responseDatagrid(response, jObject);
	}
	
	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */
	@RequestMapping(params = "driverdatagrid")
	public void driverdatagrid(DriversInfoEntity driversInfo,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		
		String sex = request.getParameter("sex");
		String name = request.getParameter("name");
		String phoneNumber = request.getParameter("phoneNumber");
		String status =  request.getParameter("status");
		String cityID= request.getParameter("cityID");
		
		JSONObject jObject = driversInfoService.getDatagrid1(dataGrid ,sex ,name ,phoneNumber,status,cityID);
		
		responseDatagrid(response, jObject);
	}

	/**
	 * 删除司机信息表
	 * 
	 * @return
	 */
	@RequestMapping(params = "checkBinding")
	@ResponseBody
	public AjaxJson checkBinding(HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		j.setSuccess(true);
		
		String id = request.getParameter("id");
		long l = systemService.getCountForJdbc("select count(*) from car_info where driver_id = '" + id + "'");
		if(l > 0){
			message = "司机已绑定车辆，请先解除绑定！";
			j.setSuccess(false);
		}
		
		j.setMsg(message);
		return j;
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
		message = "司机信息表删除成功";
		driversInfoService.delete(driversInfo);
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
		String cityId = request.getParameter("city");
		TSUser user = ResourceUtil.getSessionUserName();
		driversInfo.setDepartId(user.getCurrentDepart().getId());
		driversInfo.setCreateUserId(user.getId());
		if (StringUtil.isNotEmpty(driversInfo.getId())) {
			driversInfo.setCityId(cityId);
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
			driversInfo.setCityId(cityId);
			driversInfo.setIdCardImgUrl("");
			driversInfo.setCreateDate(new Date(System.currentTimeMillis()));
			driversInfo.setDeleteFlag(0);
			driversInfo.setStatus("0");
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
	@RequestMapping(params = "saveImgUrl", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson saveImgUrl(DriversInfoEntity driversInfo,HttpServletRequest request) throws Exception {
		String message = null;
		AjaxJson j = new AjaxJson();
		driversInfo = systemService.get(DriversInfoEntity.class, driversInfo.getId());
		message = "司机信息表添加成功";
		driversInfo.setCreateDate(new Date(System.currentTimeMillis()));
		UploadFile uploadFile = new UploadFile(request, driversInfo);
		//uploadFile.setCusPath("plug-in/accordion/images");
		uploadFile.setDiskBasePath(AppGlobals.IMAGE_BASE_FILE_PATH);
		uploadFile.setCusPath("image");  
		uploadFile.setBasePath("driver");
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
		List<OpenCityEntity> cities = systemService.findByProperty(OpenCityEntity.class, "status", "0");
		req.setAttribute("cities", cities);
		return new ModelAndView("yhy/drivers/driversInfo");
	}
	
	/**
	 * 保存文件
	 * 
	 * @return
	 */
	@RequestMapping(params = "saveFile")
	@ResponseBody
	public AjaxJson saveFile(HttpServletRequest request, HttpServletResponse response) {
		
		AjaxJson j = new AjaxJson();
		InputStream input = null;
		FileOutputStream fos = null;
		MultipartHttpServletRequest mRequest = null;
		MultipartFile file = null;
		j.setSuccess(false);
		
		String driverId = request.getParameter("driverId");
		if(driverId!=null){
		
		try {
			
			mRequest = (MultipartHttpServletRequest) request;// request强制转换注意
			file = mRequest.getFile("file");
			
			input = file.getInputStream();
			
			String filePath = AppGlobals.IMAGE_BASE_FILE_PATH + AppGlobals.DRIVER_FILE_PATH;
			
			// 文件夹是否存在
			boolean mkDir = false;
			File f = new File(filePath);
			if (!f.isDirectory()) {
				mkDir = f.mkdirs();
			} else {
				mkDir = true;
			}
			
			// String suffix =
			// file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
			
			String DBUrl = AppGlobals.DRIVER_FILE_PATH + System.currentTimeMillis() + "&&"
					+ file.getOriginalFilename();
			// 一个渠道商只保存一份文件
			// String DBUrl = AppGlobals.DEALER_FILE_PATH + id + suffix;
			
			if (mkDir) {
				fos = new FileOutputStream(AppGlobals.IMAGE_BASE_FILE_PATH + DBUrl);
				int size = 0;
				byte[] buffer = new byte[1024];
				while ((size = input.read(buffer, 0, 1024)) != -1) {
					fos.write(buffer, 0, size);
				}
				
				if(StringUtil.isNotEmpty(driverId)) {
					DriversInfoEntity driverinfo = systemService.get(DriversInfoEntity.class, driverId);
					if(StringUtil.isNotEmpty(driverinfo)){
						File df = new File(AppGlobals.IMAGE_BASE_FILE_PATH + driverinfo.getIdCardImgUrl());
						if (df.exists()) {
							df.delete();
						}
						driverinfo.setIdCardImgUrl(DBUrl);
						systemService.saveOrUpdate(driverinfo);
						j.setSuccess(true);
						j.setMsg("上传文件成功");
					}
				}else{
					j.setMsg("请选择司机！");
				}
			}

		} catch (Exception e) {
			j.setMsg("服务器异常");
			e.printStackTrace();
		} finally {
			try {
				if (fos != null)
					fos.close();
				if (input != null)
					input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		}
		return j;
	}
	
	/**
	 * 司机图片上传
	 * 
	 * @return
	 */
	@RequestMapping(params = "driverUploadFile")
	public ModelAndView driverUploadFile(HttpServletRequest request) {
		String id = request.getParameter("id");
		request.setAttribute("driverId", id);
		return new ModelAndView("yhy/drivers/driverUploadFile");
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

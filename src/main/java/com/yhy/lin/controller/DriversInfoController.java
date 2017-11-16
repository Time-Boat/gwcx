package com.yhy.lin.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.ArrayList;
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
import org.jeecgframework.web.system.pojo.base.TSUserOrg;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;

import com.yhy.lin.app.util.AppGlobals;
import com.yhy.lin.app.util.AppUtil;
import com.yhy.lin.entity.CarInfoEntity;
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
	 * 这个是从车辆页面请求过来的数据，应该将这个方法放在carController中的
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
		String cityID = request.getParameter("cityID");
		
		//如果是车辆模块传过来的参数，则限制只显示已经被审核通过的人员
		String fromPage = request.getParameter("fromPage");
		
		JSONObject jObject = driversInfoService.getDatagrid1(dataGrid, sex, name, phoneNumber, status, cityID, fromPage);
		
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
			driversInfo.setUseStatus("0");
			driversInfo.setApplicationStatus("-1");
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
	 * 查看线路详情
	 * 
	 * @return
	 */
	@RequestMapping(params = "driverdetail")
	public ModelAndView driverdetail(DriversInfoEntity driversInfo,HttpServletRequest req) {
		if (StringUtil.isNotEmpty(driversInfo.getId())) {
			driversInfo = driversInfoService.getEntity(DriversInfoEntity.class, driversInfo.getId());
			req.setAttribute("driversInfoPage", driversInfo);
			if(StringUtil.isNotEmpty(driversInfo.getAuditor())) {
				TSUser auditor = this.systemService.getEntity(TSUser.class, driversInfo.getAuditor());
				req.setAttribute("auditor", auditor);
			}
			if(StringUtil.isNotEmpty(driversInfo.getApplicationUserId())) {
				TSUser user = this.systemService.getEntity(TSUser.class, driversInfo.getApplicationUserId());
				req.setAttribute("user", user);
			}
			
		}
		if(StringUtil.isNotEmpty(req.getParameter("type"))){
			String type = req.getParameter("type");
			req.setAttribute("type", type);
		}
		List<OpenCityEntity> cities = systemService.findByProperty(OpenCityEntity.class, "status", "0");
		req.setAttribute("cities", cities);
		
		return new ModelAndView("yhy/drivers/driverDetial");
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
	
	/**
	 * 检查身份证是否在已存在
	 */
	@RequestMapping(params = "checkCard")
	@ResponseBody
	public AjaxJson checkCard(HttpServletRequest request) {
		String message = "";
		boolean success = false;
		AjaxJson j = new AjaxJson();
		try {
			String card = request.getParameter("card");
			String id = request.getParameter("id");
			
			long l = driversInfoService.getCountForJdbcParam("select count(*) from driversinfo where idCard=? and id <> ? ", new Object[]{card,id});
			
			if(l > 0){
				message = "身份证号已存在";
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
	 * 申请启动
	 */
	@RequestMapping(params = "applyEnable")
	@ResponseBody
	public AjaxJson applyEnable(HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		String id = request.getParameter("id");
		
		if(StringUtil.isNotEmpty(id)){
			DriversInfoEntity  driver = this.systemService.getEntity(DriversInfoEntity.class, id);
			if(StringUtil.isNotEmpty(driver)){
			
				if("1".equals(driver.getStatus())){
					driver.setApplyContent("1");//申请内容
					j = checkStatus(id);
					if(j.isSuccess()==false){
						return j;
					}
				}else{
					driver.setApplyContent("0");//申请内容
				}
				driver.setApplicationStatus("0");//待审核
				driver.setApplicationTime(AppUtil.getDate());
				driver.setApplicationUserId(ResourceUtil.getSessionUserName().getId());
			}
			try {
				message = "申请成功！";
				this.systemService.saveOrUpdate(driver);
			} catch (Exception e) {
				message = "服务器异常！";
			}
		}
		
		j.setMsg(message);
		return j;
	}
	
	public AjaxJson checkStatus(String id){
		String message = null;
		AjaxJson j = new AjaxJson();
		boolean success = false;
		if(StringUtil.isNotEmpty(id)){
			List<CarInfoEntity> carelist = systemService.findHql(
					"from CarInfoEntity where driverId=? and carStatus=? ", id, "0");
			if(carelist.size()>0){
				message = "司机已挂接已上架车辆，不能申请停用!";
				success = false;
			}else{
				success = true;
			}
		}
		j.setSuccess(success);
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
			DriversInfoEntity driver = this.systemService.getEntity(DriversInfoEntity.class, id);
			if(StringUtil.isNotEmpty(driver)){
				try {
					
					/*if("0".equals(driver.getStatus())){
						driver.setStatus("1");;//司机状态
					}else{
						driver.setStatus("0");;//司机状态
					}*/
					if("0".equals(driver.getApplyContent())){
						driver.setStatus("1");
					}else{
						j = checkStatus(id);
						if(j.isSuccess()==false){
							return j;
						}
						driver.setStatus("2");
					}
					driver.setApplicationStatus("1");
					driver.setAuditor(ResourceUtil.getSessionUserName().getId());
					driver.setAuditTime(AppUtil.getDate());
					message = "申请成功！";
					systemService.saveOrUpdate(driver);
				} catch (Exception e) {
					message = "服务器异常！";
				}
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
			DriversInfoEntity driver = this.systemService.getEntity(DriversInfoEntity.class, id);

			if (StringUtil.isNotEmpty(driver)) {
				driver.setRefusalReason(rejectReason);
				driver.setApplicationStatus("2");
				driver.setAuditor(ResourceUtil.getSessionUserName().getId());
				driver.setAuditTime(AppUtil.getDate());
			}

			try {
				message = "拒绝成功！";
				this.systemService.saveOrUpdate(driver);
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
			DriversInfoEntity driver = this.systemService.getEntity(DriversInfoEntity.class, id);

			if (StringUtil.isNotEmpty(driver)) {
				message=driver.getRefusalReason();
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
		return new ModelAndView("yhy/drivers/driverAttacheList");
	}
	
	/**
	 * 分配专员
	 * 
	 * @return
	 */
	@RequestMapping(params = "driverAllotAttache")
	@ResponseBody
	public AjaxJson driverAllotAttache(HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		String message = null;

		String userId = req.getParameter("userId");
		String ids = req.getParameter("ids");

		List<DriversInfoEntity> driverList = new ArrayList<>();
		
		try {
			String[] idArr = ids.split(",");
			for (int i = 0; i < idArr.length; i++) {

				DriversInfoEntity driverInfo = systemService.getEntity(DriversInfoEntity.class, idArr[i]);
				TSUserOrg t = systemService.findUniqueByProperty(TSUserOrg.class, "tsUser.id", userId);
				driverInfo.setCreateUserId(userId);
				driverInfo.setDepartId(t.getTsDepart().getId());
				
				driverList.add(driverInfo);
				
				String sql = " update car_info set create_user_id = ?, departId = ? where driver_id = ? ";
				
				long l = systemService.executeSql(sql, userId, t.getTsDepart().getId(), idArr[i]);
			}
			
			systemService.saveAllEntitie(driverList);
		} catch (Exception e) {
			message = "服务器异常";
			e.printStackTrace();
		}
		message = "分配成功";
		j.setMsg(message);
		return j;
	}
}

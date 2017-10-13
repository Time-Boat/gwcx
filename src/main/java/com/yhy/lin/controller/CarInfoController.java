package com.yhy.lin.controller;

import java.util.ArrayList;
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
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.pojo.base.TSType;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.pojo.base.TSUserOrg;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;

import javax.validation.Validator;

import com.yhy.lin.app.util.AppGlobals;
import com.yhy.lin.app.util.AppUtil;
import com.yhy.lin.entity.CarInfoEntity;
import com.yhy.lin.entity.DealerInfoEntity;
import com.yhy.lin.entity.DriversInfoEntity;
import com.yhy.lin.service.CarInfoServiceI;

import net.sf.json.JSONObject;

/**   
 * @Title: Controller
 * @Description: 车辆信息
 * @author zhangdaihao
 * @date 2017-04-22 13:37:21
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/carInfoController")
public class CarInfoController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(CarInfoController.class);

	@Autowired
	private CarInfoServiceI carInfoService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;


	/**
	 * 车辆信息列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "carInfoList")
	public ModelAndView list(HttpServletRequest request) {
		
		//如果是项目管理员，则不限制分配的条件
		if(checkRole(AppGlobals.XM_ADMIN)){
			request.setAttribute("ap", "1");
		}
				
		return new ModelAndView("yhy/car/carInfoList");
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
	public void datagrid(CarInfoEntity carInfo,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		
		//没写注释，忘记是干嘛用的了。。
		String userCar = request.getParameter("userCar");
		String lpId = request.getParameter("lpId");
		String operation = request.getParameter("operation");
		
		String licencePlate = request.getParameter("licencePlate");
		String carType = request.getParameter("carType");
		String status = request.getParameter("status");
		String businessType = request.getParameter("businessType");
		String carAndDriver = request.getParameter("carAndDriver");
		
		String carStatus = request.getParameter("carStatus");
		String auditStatus = request.getParameter("auditStatus");
		
		if(StringUtil.isNotEmpty(operation)){
			businessType = operation;
		}
		
		JSONObject jObject = carInfoService.getDatagrid(dataGrid, userCar, lpId, licencePlate
				, carType, status, businessType, carAndDriver, carStatus, auditStatus);
		
		responseDatagrid(response, jObject);
		
	}

	/**
	 * 删除车辆信息
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(CarInfoEntity carInfo, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		carInfo = systemService.getEntity(CarInfoEntity.class, carInfo.getId());
		
		if(StringUtil.isNotEmpty(carInfo.getDriverId())){
			DriversInfoEntity driver = this.systemService.getEntity(DriversInfoEntity.class, carInfo.getDriverId());
			driver.setStatus("0");//修改司机状态--被使用
		}
		
		message = "车辆信息删除成功";
		carInfoService.delete(carInfo);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}

	/**
	 * 添加车辆信息
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(CarInfoEntity carInfo, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();

		String beforeDriverId = "";
		TSUser user = ResourceUtil.getSessionUserName();
		carInfo.setDepartId(user.getCurrentDepart().getId());
		carInfo.setCreateUserId(user.getId());
		carInfo.setCreateTime(AppUtil.getDate());
		if (StringUtil.isNotEmpty(carInfo.getId())) {
			message = "车辆信息更新成功";
			CarInfoEntity t = carInfoService.get(CarInfoEntity.class, carInfo.getId());
			beforeDriverId = t.getDriverId();
			try {
				MyBeanUtils.copyBeanNotNull2Bean(carInfo, t);
				carInfoService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = "车辆信息更新失败";
			}
		} else {
			message = "车辆信息添加成功";
			
			carInfo.setDeleteFlag(0);
			carInfo.setCarStatus("1");
			carInfo.setAuditStatus("-1");
			carInfoService.save(carInfo);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		
		//修改司机状态--被使用
		if(StringUtil.isNotEmpty(carInfo.getDriverId())){
			DriversInfoEntity driver = this.systemService.getEntity(DriversInfoEntity.class, carInfo.getDriverId());
			driver.setStatus("1");
			systemService.save(driver);
		}
		
		//被替换的司机要把状态改为0   beforeDriverId
		if(StringUtil.isNotEmpty(beforeDriverId) && !beforeDriverId.equals(carInfo.getDriverId())){
			DriversInfoEntity driver = this.systemService.getEntity(DriversInfoEntity.class, beforeDriverId);
			driver.setStatus("0");
			systemService.save(driver);
		}
		
		j.setMsg(message);
		return j;
	}

	/**
	 * 车辆信息列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(CarInfoEntity carInfo, HttpServletRequest req) {
		
		List<DriversInfoEntity> driversList = null;
		
		if (StringUtil.isNotEmpty(carInfo.getId())) {
			carInfo = carInfoService.getEntity(CarInfoEntity.class, carInfo.getId());
			req.setAttribute("carInfoPage", carInfo);
		}
		
		if (StringUtil.isNotEmpty(carInfo.getDriverId())) {
			DriversInfoEntity driver = this.systemService.getEntity(DriversInfoEntity.class, carInfo.getDriverId());
			req.setAttribute("driverPage", driver);
		}
		
		driversList = carInfoService.getDriverList(carInfo.getDriverId());
		//进行和数据字典的转换
		//查找数据字典的表，4028820d5b9eda40015b9f2164c80018 是驾照类型对应的数据
		List<TSType> type = systemService.findByQueryString("from TSType where typegroupid='4028820d5b9eda40015b9f2164c80018'");//(TSType.class, "typegroupid", "4028820d5b9eda40015b9f2164c80018");
		for(DriversInfoEntity d : driversList){
			for (int i = 0; i < type.size(); i++) {
				if(d.getDrivingLicense().equals(type.get(i).getTypecode())){
					d.setDrivingLicense(type.get(i).getTypename());
					break;
				}
			}
		}
		
		req.setAttribute("driversList", driversList);
		return new ModelAndView("yhy/car/carInfo");
	}
	
	/**
	 * 线路排班列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addDriver")
	public ModelAndView addCar(HttpServletRequest req) {
		String lpId = req.getParameter("lpId");
		req.setAttribute("lpId", lpId);
		req.setAttribute("fromPage", req.getParameter("fromPage"));
		req.setAttribute("cityList",getOpencity());
		return new ModelAndView("/yhy/car/driverAndCity");
	}
	
	/**
	 * 申请停用
	 * 
	 * @return
	 */
	@RequestMapping(params = "carDisable")
	@ResponseBody
	public AjaxJson dealerDisable(HttpServletRequest req) {
		String message = null;
		AjaxJson j = new AjaxJson();

		String id = req.getParameter("id");

		CarInfoEntity carInfo = systemService.getEntity(CarInfoEntity.class, id);
		try {

			carInfo.setApplyTime(AppUtil.getDate());
			carInfo.setAuditStatus("0");
			carInfo.setApplyContent("1");
			carInfo.setApplyUserId(ResourceUtil.getSessionUserName().getId());

			// 清空审核状态
			carInfo.setAuditTime(null);
			carInfo.setAuditUserId("");
			carInfo.setRejectReason("");
			
			systemService.saveOrUpdate(carInfo);
		} catch (Exception e) {
			message = "服务器异常";
			e.printStackTrace();
		}
		message = "申请成功";
		j.setMsg(message);
		return j;
	}

	/**
	 * 提交申请
	 * 
	 * @return
	 */
	@RequestMapping(params = "carApply")
	@ResponseBody
	public AjaxJson dealerApply(HttpServletRequest req) {
		String message = null;
		AjaxJson j = new AjaxJson();

		String id = req.getParameter("id");

		CarInfoEntity carInfo = systemService.getEntity(CarInfoEntity.class, id);
		try {
			carInfo.setApplyTime(AppUtil.getDate());
			carInfo.setAuditStatus("0");
			carInfo.setApplyContent("0");
			carInfo.setApplyUserId(ResourceUtil.getSessionUserName().getId());

			// 清空审核状态
			carInfo.setAuditTime(null);
			carInfo.setAuditUserId("");
			carInfo.setRejectReason("");

			systemService.saveOrUpdate(carInfo);
		} catch (Exception e) {
			message = "服务器失败";
			e.printStackTrace();
		}
		message = "申请成功";
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 渠道商信息列表详情页跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "carDetail")
	public ModelAndView dealerDetail(CarInfoEntity carInfo, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(carInfo.getId())) {
			carInfo = systemService.getEntity(CarInfoEntity.class, carInfo.getId());
			String applyUserId = carInfo.getApplyUserId();
			if(StringUtil.isNotEmpty(applyUserId)){
				TSUser user = systemService.getEntity(TSUser.class, applyUserId);
				req.setAttribute("applyUser", user.getUserName());
			}
			String auditUserId = carInfo.getApplyUserId();
			if(StringUtil.isNotEmpty(auditUserId)){
				TSUser user = systemService.getEntity(TSUser.class, auditUserId);
				req.setAttribute("auditUser", user.getUserName());
			}
			
			if (StringUtil.isNotEmpty(carInfo.getDriverId())) {
				DriversInfoEntity driver = this.systemService.getEntity(DriversInfoEntity.class, carInfo.getDriverId());
				req.setAttribute("driverName", driver.getName());
			}
			
			req.setAttribute("carInfoPage", carInfo);
		}
		return new ModelAndView("yhy/car/carDetail");
	}
	
	/**
	 * 分配专员
	 * 
	 * @return
	 */
	@RequestMapping(params = "carAllotAttache")
	@ResponseBody
	public AjaxJson allotAttache(HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		String message = null;

		String userId = req.getParameter("userId");
		String ids = req.getParameter("ids");

		List<CarInfoEntity> carList = new ArrayList<>();
		List<DriversInfoEntity> driverList = new ArrayList<>();

		try {
			String[] idArr = ids.split(",");
			for (int i = 0; i < idArr.length; i++) {

				CarInfoEntity carInfo = systemService.getEntity(CarInfoEntity.class, idArr[i]);
				TSUserOrg t = systemService.findUniqueByProperty(TSUserOrg.class, "tsUser.id", userId);
				carInfo.setCreateUserId(userId);
				carInfo.setDepartId(t.getTsDepart().getId());
				
				DriversInfoEntity driverInfo = systemService.getEntity(DriversInfoEntity.class, carInfo.getDriverId());
				driverInfo.setCreateUserId(userId);
				driverInfo.setDepartId(t.getTsDepart().getId());
				
				driverList.add(driverInfo);
				carList.add(carInfo);
			}
			
			systemService.saveAllEntitie(carList);
			systemService.saveAllEntitie(driverList);
		} catch (Exception e) {
			message = "服务器异常";
			e.printStackTrace();
		}
		message = "分配成功";
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
		return new ModelAndView("yhy/car/carAttacheList");
	}
	
	/**
	 * 同意审核
	 * 
	 * @return
	 */
	@RequestMapping(params = "carAgree")
	@ResponseBody
	public AjaxJson dealerAgree(HttpServletRequest req) {
		String message = null;
		AjaxJson j = new AjaxJson();

		String id = req.getParameter("id");

		CarInfoEntity carInfo = systemService.getEntity(CarInfoEntity.class, id);

		try {
			String apply = carInfo.getApplyContent();

			carInfo.setAuditTime(AppUtil.getDate());
			carInfo.setAuditStatus("1");
			carInfo.setAuditUserId(ResourceUtil.getSessionUserName().getUserName());

			if ("0".equals(apply)) {
				carInfo.setCarStatus("0");
			} else {
				carInfo.setCarStatus("1");
			}

			systemService.saveOrUpdate(carInfo);
		} catch (Exception e) {
			message = "服务器异常";
			e.printStackTrace();
		}
		message = "审核成功";
		j.setMsg(message);
		return j;
	}

	/**
	 * 拒绝审核
	 * 
	 * @return
	 */
	@RequestMapping(params = "carReject")
	@ResponseBody
	public AjaxJson dealerReject(HttpServletRequest req) {
		String message = null;
		AjaxJson j = new AjaxJson();

		String id = req.getParameter("id");
		String rejectReason = req.getParameter("rejectReason");

		CarInfoEntity dealerInfo = systemService.getEntity(CarInfoEntity.class, id);
		try {

			dealerInfo.setAuditTime(AppUtil.getDate());
			dealerInfo.setAuditStatus("2");
			dealerInfo.setAuditUserId(ResourceUtil.getSessionUserName().getUserName());
			dealerInfo.setRejectReason(rejectReason);

			systemService.saveOrUpdate(dealerInfo);
		} catch (Exception e) {
			message = "服务器异常";
			e.printStackTrace();
		}
		message = "审核成功";
		j.setMsg(message);
		return j;
	}

	// 获取拒绝原因
	@RequestMapping(params = "getReason")
	@ResponseBody
	public AjaxJson getReason(HttpServletRequest request) {
		
		AjaxJson j = new AjaxJson();
		
		String id = request.getParameter("id");// id
		
		CarInfoEntity t = systemService.getEntity(CarInfoEntity.class, id);
		String reasont = "";
		reasont = t.getRejectReason();
		
		j.setSuccess(true);
		j.setMsg(reasont);
		return j;
	}
	
}

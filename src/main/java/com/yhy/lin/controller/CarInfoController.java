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
import org.jeecgframework.web.system.pojo.base.TSType;
import org.jeecgframework.web.system.pojo.base.TSTypegroup;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;

import javax.validation.Validator;

import com.yhy.lin.entity.CarInfoEntity;
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
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
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
		
		String userCar = request.getParameter("userCar");
		String lpId = request.getParameter("lpId");
		String operation = request.getParameter("operation");
		
		String licencePlate = request.getParameter("licencePlate");
		String carType = request.getParameter("carType");
		String status = request.getParameter("status");
		String businessType = request.getParameter("businessType");
		
		if(StringUtil.isNotEmpty(operation)){
			businessType = operation;
		}
		
		JSONObject jObject = carInfoService.getDatagrid(dataGrid, userCar, lpId, licencePlate, carType, status, businessType);
		
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
		if (StringUtil.isNotEmpty(carInfo.getId())) {
			message = "车辆信息更新成功";
			CarInfoEntity t = carInfoService.get(CarInfoEntity.class, carInfo.getId());
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
			
			carInfoService.save(carInfo);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
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
	
}

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
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;
import javax.validation.Validator;
import java.sql.Date;

import com.yhy.lin.entity.CarInfoEntity;
import com.yhy.lin.entity.DriversInfoEntity;
import com.yhy.lin.entity.LineArrangeEntity;
import com.yhy.lin.service.LineArrangeServiceI;

import net.sf.json.JSONObject;

/**   
 * @Title: Controller
 * @Description: 线路排班
 * @author zhangdaihao
 * @date 2017-04-21 18:00:03
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/lineArrangeController")
public class LineArrangeController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(LineArrangeController.class);

	private static final String ID_NULL = "id123";
	
	@Autowired
	private LineArrangeServiceI lineArrangeService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	
	/**
	 * 线路排班列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
//		List<TSDepart> t = getCompanys();
//		
//		StringBuffer json = new StringBuffer("{'data':[");
//		for (int i = 0; i < t.size(); i++) {
//			json.append("{");
//			json.append("'id':'" + t.get(i).getOrgCode() + "',");
//			json.append("'departname':'" + t.get(i).getDepartname() + "'");
//			json.append("},");
//		}
//		json.delete(json.length()-1, json.length());
//		json.append("]}");
//		request.setAttribute("companyList",json.toString());
		return new ModelAndView("/yhy/putCar/lineArrangeList");
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
	public void datagrid(LineArrangeEntity lineArrange,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		
//		String job = request.getParameter("staffPosition");
//		String depart = request.getParameter("depart");
//		String name = request.getParameter("name");
//		String companyId = request.getParameter("companyId");
//		String orgCode = ResourceUtil.getSessionUserName().getCurrentDepart().getOrgCode();
		
		JSONObject jObject = lineArrangeService.getDatagrid(dataGrid);
		
		responseDatagrid(response, jObject);

	}

	/**
	 * 删除线路排班
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(LineArrangeEntity lineArrange, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		lineArrange = systemService.getEntity(LineArrangeEntity.class, lineArrange.getId());
		message = "线路排班删除成功";
		lineArrangeService.delete(lineArrange);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}

	/**
	 * 添加线路排班
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(LineArrangeEntity lineArrange, HttpServletRequest request) {
		
		String lineId = request.getParameter("lineId");
		lineArrange.setLineId(lineId);
		String message = null;
		AjaxJson j = new AjaxJson();
		
		String dd = request.getParameter("J_DepDate");
		String ed = request.getParameter("J_EndDate");
		
		lineArrange.setStartDate(Date.valueOf(dd));
		lineArrange.setEndDate(Date.valueOf(ed));
		
		String prevLicencePlateId = request.getParameter("prevLicencePlateId");
		String curLicencePlateId = request.getParameter("licencePlateId");
		if(StringUtil.isNotEmpty(curLicencePlateId)){
			//如果选择前的车辆id和选择后的车辆id不同
			if(!curLicencePlateId.equals(prevLicencePlateId)){
				
				if(StringUtil.isNotEmpty(prevLicencePlateId)){
					CarInfoEntity carInfo = systemService.getEntity(CarInfoEntity.class, prevLicencePlateId);
					carInfo.setStatus("0");
					systemService.updateEntitie(carInfo);
				}
				
				CarInfoEntity carInfo1 = systemService.getEntity(CarInfoEntity.class, curLicencePlateId);
				carInfo1.setStatus("1");
				systemService.updateEntitie(carInfo1);
			}
		}
		
		if (StringUtil.isNotEmpty(lineArrange.getId())) {
			message = "线路排班更新成功";
			LineArrangeEntity t = lineArrangeService.get(LineArrangeEntity.class, lineArrange.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(lineArrange, t);
				lineArrangeService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = "线路排班更新失败";
			}
		} else {
			message = "线路排班添加成功";
			lineArrangeService.save(lineArrange);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 线路排班列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(LineArrangeEntity lineArrange, HttpServletRequest req) {
		
		String lineId = req.getParameter("lineId");
		String id = req.getParameter("id");
		
		if(ID_NULL.equals(id)){
			id = "";
		}
		
		List<CarInfoEntity> carList = systemService.getList(CarInfoEntity.class);
		List<DriversInfoEntity> driversList = systemService.getList(DriversInfoEntity.class);
		req.setAttribute("carList", carList);
		req.setAttribute("driversList", driversList);
		req.setAttribute("lineId", lineId);
		if (StringUtil.isNotEmpty(id) && StringUtil.isNotEmpty(lineId)) {
			lineArrange = lineArrangeService.getEntity(LineArrangeEntity.class, lineArrange.getId());
			req.setAttribute("lineArrangePage", lineArrange);
			req.setAttribute("dDate", lineArrange.getDepartDate());
		}
		return new ModelAndView("/yhy/putCar/lineArrange");
	}
	
	/**
	 * 司机页跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addDriver")
	public ModelAndView addDriver(HttpServletRequest req) {
		String id = req.getParameter("id");
		req.setAttribute("dId", id);
		return new ModelAndView("/yhy/putCar/arrangeDriver");
	}
	
	/**
	 * 司机列表页跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addCar")
	public ModelAndView addCar(HttpServletRequest req) {
		String lpId = req.getParameter("lpId");
		req.setAttribute("lpId", lpId);
		return new ModelAndView("/yhy/putCar/arrangeCar");
	}
	
}

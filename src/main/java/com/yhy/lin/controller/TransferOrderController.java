package com.yhy.lin.controller;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yhy.lin.app.util.SendMessageUtil;
import com.yhy.lin.entity.CarInfoEntity;
import com.yhy.lin.entity.DriversInfoEntity;
import com.yhy.lin.entity.TransferorderEntity;
import com.yhy.lin.entity.TransferorderView;
import com.yhy.lin.service.TransferServiceI;

import net.sf.json.JSONObject;

/**
 * 接送机订单处理
 * 
 * @author HSAEE
 *
 */
@Controller
@RequestMapping("/transferOrderController")
public class TransferOrderController extends BaseController {

	@Autowired
	private SystemService systemService;

	@Autowired
	private TransferServiceI transferService;

	// 接送机订单处理
	@RequestMapping(params = "transferOrderList")
	public ModelAndView transferOrderList(HttpServletRequest request, HttpServletResponse response) {
		
		request.setAttribute("carplateList",getCarPlate());	
		request.setAttribute("driverList",getDriver());	
		request.setAttribute("lineNameList",getLine());
		return new ModelAndView("yhy/transferOrder/transferOrderList");
	}
	
	/**
	 * 获得车牌号
	 * @return
	 */
	public String getCarPlate(){
		String sql = "select c.id,c.licence_plate from car_info c ";
		List<Object> list = this.systemService.findListbySql(sql);
		StringBuffer json = new StringBuffer("{'data':[");
		if(list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				Object[] ob = (Object[]) list.get(i);
				String id = ob[0]+"";
				String licencePlate = ob[1]+"";
					json.append("{");
					json.append("'carId':'" +id + "',");
					json.append("'licencePlate':'"+ licencePlate + "'");
					json.append("},");
				}
			}
		json.delete(json.length()-1, json.length());
		json.append("]}");
		return json.toString();
	}
	
	/**
	 * 获取司机
	 * @return
	 */
	public String getDriver(){
		String sql = "select d.id,d.name from driversinfo d ";
		List<Object> list = this.systemService.findListbySql(sql);
		StringBuffer json = new StringBuffer("{'data':[");
		if(list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				Object[] ob = (Object[]) list.get(i);
				String id = ob[0]+"";
				String name = ob[1]+"";
					json.append("{");
					json.append("'driverId':'" +id + "',");
					json.append("'driverName':'"+ name + "'");
					json.append("},");
				}
			}
		json.delete(json.length()-1, json.length());
		json.append("]}");
		return json.toString();
	}
	
	
	/**
	 * 获取线路
	 */
	public String getLine(){
		String orgCode = ResourceUtil.getSessionUserName().getCurrentDepart().getOrgCode();
		// 添加了权限
		String sql ="select l.id,l.name from lineinfo l,t_s_depart t where l.departId=t.ID and l.status='1' and t.org_code like '" + orgCode + "%' ";
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
			}
		json.delete(json.length()-1, json.length());
		json.append("]}");
		
		return json.toString();
	}
	

	// 接送机订单查询
	@RequestMapping(params = "transferOrderSearchList")
	public ModelAndView transferOrderSearchList(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("yhy/transferOrder/transferOrderSearchList");
	}

	@RequestMapping(params = "datagrid")
	public void dataGrid(TransferorderEntity transferorder, HttpServletRequest request, HttpServletResponse response,
			DataGrid dataGrid) {
		
		String orderStartingstation = request.getParameter("orderStartingstation");
		String orderTerminusstation = request.getParameter("orderTerminusstation");
		String lineId = request.getParameter("lineId");
		String driverId = request.getParameter("driverId");
		String driverName ="";
		if (StringUtil.isNotEmpty(driverId)) {
			DriversInfoEntity dr = this.systemService.getEntity(DriversInfoEntity.class, driverId);
			 driverName = dr.getName();
		}
		String plate ="";
		String carId = request.getParameter("carId");
		if (StringUtil.isNotEmpty(carId)) {
			CarInfoEntity dr = this.systemService.getEntity(CarInfoEntity.class, carId);
			 plate = dr.getLicencePlate();
		}
		
		String fc_begin = request.getParameter("orderStartime_begin");
		String fc_end = request.getParameter("orderStartime_end");
		String ddTime_begin = request.getParameter("orderExpectedarrival_begin");
		String ddTime_end = request.getParameter("orderExpectedarrival_end");
		JSONObject jObject = transferService.getDatagrid(transferorder, dataGrid,orderStartingstation, orderTerminusstation
				,lineId,driverName,plate,fc_begin, fc_end, ddTime_begin,ddTime_end);

		responseDatagrid(response, jObject);
	}
	
	/**
	 * 司机车辆安排跳转
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "editCarAndDriver")
	public ModelAndView editCarAndDriver(HttpServletRequest request, HttpServletResponse response) {
		String ids = request.getParameter("ids");

		String slDate = request.getParameter("slDate");

		// System.out.println(ids);
		// 1、根据id查询到对应订单的所有线路，后台进行判断，如果是同一条线路，允许多条订单同时操作，否则给信息提示
		if (StringUtil.isNotEmpty(ids)) {
			request.setAttribute("ids", ids);
			request.setAttribute("slDate", slDate);
		}
		return new ModelAndView("yhy/transferOrder/transferOrderAdd");
	}

	/**
	 * 保存接送订单的司机车辆安排
	 */
	@RequestMapping(params = "saveCarAndDriver")
	@ResponseBody
	public AjaxJson saveCarAndDriver(HttpServletRequest request, HttpServletResponse response) {
		String message = null;
		boolean success = false;
		AjaxJson j = new AjaxJson();
		String ids = request.getParameter("id");// 勾选的订单id
		String startTime = request.getParameter("startDate");// 发车时间
		String licencePlateId = request.getParameter("licencePlateId");// 车牌id
		String driverId = request.getParameter("driverId");// 司机id
		String licencePlateName = request.getParameter("licencePlateName");// 车牌号
		
		List<String> orderIds = extractIdListByComma(ids);
		
		List<String[]> contents = new ArrayList<>();
		
		boolean b = transferService.saveDriverAndDriver(orderIds, startTime, licencePlateId, driverId, licencePlateName,
				contents);
		
		if (b) {
			String[] keys = new String[] { "name", "startStation", "terminusStation", "time" };
			for (int i = 0; i < contents.size(); i++) {
				String[] p = contents.get(i);
				SendMessageUtil.sendMessage(p[p.length - 1], keys, contents.get(i), SendMessageUtil.TEMPLATE_ARRANGE_CAR, SendMessageUtil.TEMPLATE_ARRANGE_CAR_SIGN_NAME);
			}
			success = true;
			message = "订单处理成功";
		} else {
			message = "服务器异常";
		}

		j.setSuccess(success);
		j.setMsg(message);
		return j;
	}

	/**
	 * 线路排班列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addCar")
	public ModelAndView addCar(HttpServletRequest req) {
		String lpId = req.getParameter("lpId");
		req.setAttribute("lpId", lpId);
		return new ModelAndView("/yhy/transferOrder/carAndDrivers");
	}

	/**
	 * 接送机订单查询详情查看页面
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(HttpServletRequest request) {
		// 获取部门信息
		String id = request.getParameter("id");
		if (StringUtil.isNotEmpty(id)) {
			TransferorderView tView = transferService.getDetail(id);
			if (tView != null) {
				request.setAttribute("tView", tView);
			}
		}
		return new ModelAndView("yhy/transferOrder/transferOrderDetial");
	}

	/**
	 * 定时获取订单
	 */
	@RequestMapping(params = "getOrder")
	@ResponseBody
	public JSONObject getOrder(){
		JSONObject jsonObj = new JSONObject();
		String sql = "select count(*) from transferorder a left join order_linecardiver b on a.id = b .id left join car_info c on "
				+ "b.licencePlateId =c.id left join driversinfo d on b.driverId =d.id left join lineinfo l on l.id = a.line_id "
				+ "left join t_s_depart t on t.id = l.departId where a.applicationTime > DATE_SUB(NOW(), INTERVAL 30 MINUTE);";
		List<Object> list = this.systemService.findListbySql(sql);
		int num = 0;
		if (list.size() > 0) {
			BigInteger ob = (BigInteger) list.get(0);
			num = ob.intValue();
		}

		jsonObj.put("num", num);
		return jsonObj;
		
	}
	
}

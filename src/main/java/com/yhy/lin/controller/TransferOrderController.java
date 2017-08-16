package com.yhy.lin.controller;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.h2.value.Transfer;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.p3.core.common.utils.DateUtil;
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

import net.sf.json.JSONArray;
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
	// 接送车司机安排
	@RequestMapping(params = "transferDriverList")
	public ModelAndView transferDriverList(HttpServletRequest request, HttpServletResponse response) {
		
		return new ModelAndView("yhy/transferOrder/transferDriverList");
	}
	
	// 接送车司机安排
	@RequestMapping(params = "getOrderdetail")
	public ModelAndView getOrderdetail(HttpServletRequest request, HttpServletResponse response) {
		String orderType = request.getParameter("lineType");
		String lineOrderCode = request.getParameter("lineOrderCode");
		if("2".equals(orderType) || "3".equals(orderType)){
			return transferOrderAirList(request, response,lineOrderCode);
		}
		if("4".equals(orderType) || "5".equals(orderType)){
			return transferOrderList(request, response,lineOrderCode);
		}
			
		return new ModelAndView("yhy/transferOrder/transferDriverList");
	}

	// 接送火车订单处理
	@RequestMapping(params = "transferOrderList")
	public ModelAndView transferOrderList(HttpServletRequest request, HttpServletResponse response,String lineOrderCode) {
		
		request.setAttribute("lineOrderCode",lineOrderCode);	
		request.setAttribute("carplateList",getCarPlate());	
		request.setAttribute("driverList",getDriver());	
		request.setAttribute("lineNameList",getLine1());
		return new ModelAndView("yhy/transferOrder/transferOrderList");
	}
	
	// 接送机订单处理
	@RequestMapping(params = "transferOrderAirList")
	public ModelAndView transferOrderAirList(HttpServletRequest request, HttpServletResponse response,String lineOrderCode) {
			
		request.setAttribute("lineOrderCode",lineOrderCode);
		request.setAttribute("carplateList",getCarPlate());	
		request.setAttribute("driverList",getDriver());	
		request.setAttribute("lineNameList",getLine());
		return new ModelAndView("yhy/transferOrder/transferOrderAirList");
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
			}else{
				json.append("{");
				json.append("'carId':'',");
				json.append("'licencePlate':''");
				json.append("},");
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
			}else{
				json.append("{");
				json.append("'driverId':'',");
				json.append("'driverName':''");
				json.append("},");
			}
		json.delete(json.length()-1, json.length());
		json.append("]}");
		return json.toString();
	}
	
	
	/**
	 * 获取线路(接送机)
	 */
	public String getLine(){
		String orgCode = ResourceUtil.getSessionUserName().getCurrentDepart().getOrgCode();
		// 添加了权限
		String sql ="select l.id,l.name from lineinfo l,t_s_depart t where l.departId=t.ID and l.status='0' and t.org_code like '" + orgCode + "%'  and l.type in('2','3');";
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
	
	@RequestMapping(params = "driverdatagrid")
	public void driverdatagrid(TransferorderEntity transferorder, HttpServletRequest request, HttpServletResponse response,
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
		String plate = "";
		String carId = request.getParameter("carId");
		if (StringUtil.isNotEmpty(carId)) {
			CarInfoEntity dr = this.systemService.getEntity(CarInfoEntity.class, carId);
			 plate = dr.getLicencePlate();
		}
		
		String fc_begin = request.getParameter("orderStartime_begin");
		String fc_end = request.getParameter("orderStartime_end");
		String ddTime_begin = request.getParameter("orderExpectedarrival_begin");
		String ddTime_end = request.getParameter("orderExpectedarrival_end");
		JSONObject jObject = transferService.getDatagrid3(transferorder, dataGrid,orderStartingstation, orderTerminusstation
				,lineId,driverName,plate,fc_begin, fc_end, ddTime_begin,ddTime_end);
		
		responseDatagrid(response, jObject);
	}
	
	@RequestMapping(params = "datagrid")
	public void dataGrid(TransferorderEntity transferorder, HttpServletRequest request, HttpServletResponse response,
			DataGrid dataGrid) {
		
		String orderStartingstation = request.getParameter("orderStartingstation");
		String orderTerminusstation = request.getParameter("orderTerminusstation");
		String lineId = request.getParameter("lineId");
		String driverId = request.getParameter("driverId");
		String lineOrderCode= request.getParameter("lineOrderCode");
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
		JSONObject jObject = transferService.getDatagrid(transferorder, dataGrid,orderStartingstation,lineOrderCode, orderTerminusstation
				,lineId,driverName,plate,fc_begin, fc_end, ddTime_begin,ddTime_end);
		
		responseDatagrid(response, jObject);
	}
	
	/**
	 * 获取线路(接送火车)
	 */
	public String getLine1(){
		String orgCode = ResourceUtil.getSessionUserName().getCurrentDepart().getOrgCode();
		// 添加了权限
		String sql ="select l.id,l.name from lineinfo l,t_s_depart t where l.departId=t.ID and l.status='0' and t.org_code like '" + orgCode + "%'  and l.type  in('4','5');";
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
	

	// 接送机订单查询
	@RequestMapping(params = "transferOrderSearchList")
	public ModelAndView transferOrderSearchList(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("yhy/transferOrder/transferOrderSearchList");
	}

	@RequestMapping(params = "airdatagrid")
	public void airdatagrid(TransferorderEntity transferorder, HttpServletRequest request, HttpServletResponse response,
			DataGrid dataGrid) {
		
		String lineOrderCode = request.getParameter("lineOrderCode");
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
		JSONObject jObject = transferService.getDatagrid1(transferorder, dataGrid,lineOrderCode,orderStartingstation, orderTerminusstation
				,lineId,driverName,plate,fc_begin, fc_end, ddTime_begin,ddTime_end);

		responseDatagrid(response, jObject);
	}
	
	@RequestMapping(params = "traindatagrid")
	public void traindatagrid(TransferorderEntity transferorder, HttpServletRequest request, HttpServletResponse response,
			DataGrid dataGrid) {
		
		String lineOrderCode = request.getParameter("lineOrderCode");
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
		JSONObject jObject = transferService.getDatagrid2(transferorder, dataGrid,lineOrderCode,orderStartingstation, orderTerminusstation
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
		String sdate = request.getParameter("sdate");
		String lineOrderCode = request.getParameter("lineOrderCode");
		
		if (StringUtil.isNotEmpty(lineOrderCode)) {
			request.setAttribute("lineOrderCode", lineOrderCode);
			request.setAttribute("sdate", sdate + "");
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
			//同一个人下的两条订单被安排了同一个车上，那么会连续发送两条短信，这个会出发流程控制，一分钟之内不允许发送两条消息
			//可以将发送失败的短信存到数据库，使用quartz定时去循环发送
			//如果有rides缓存服务器，可以存到缓存中，到时候再定时发送
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
	 * 提醒未处理的订单
	 */
	@RequestMapping(params = "getOrderStartime")
	@ResponseBody
	public JSONObject getOrderStartime(){
		JSONObject jsonObj = new JSONObject();
		String  orgCode = ResourceUtil.getSessionUserName().getCurrentDepart().getOrgCode();
		StringBuffer sql = new StringBuffer();
		sql.append("select COUNT(ts.org_code),t.order_type from transferorder t LEFT JOIN lineinfo l on t.line_id=l.id "
				+ "LEFT JOIN  t_s_depart ts on ts.ID=l.departId where t.order_status='1' and t.order_type in('2','3','4','5') "
				+ "and t.order_startime >= NOW() and t.order_startime<=date_add(NOW(),INTERVAL 11 HOUR)");
		if (StringUtil.isNotEmpty(orgCode)) {
			sql.append(" and ts.org_code = '" + orgCode + "'");
		}
		sql.append(" GROUP BY t.order_type");
		List<Object> list = this.systemService.findListbySql(sql.toString());
		int ordair = 0;
		int ordtr = 0;
		
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Object[] ob = (Object[]) list.get(i);
				BigInteger num = (BigInteger) ob[0];
				int oderType = (int) ob[1];
				 if(oderType==2 || oderType==3){
					 ordair+=num.intValue();
				 }
				 if(oderType==4 || oderType==5){
					 ordtr+=num.intValue();
				 }
				 
				 jsonObj.put("ordair", ordair);
				 jsonObj.put("ordtr", ordtr);
			}
			
		}
		
		return jsonObj;
	}
	
	
	
	/**
	 * 提醒未处理的订单
	 */
	@RequestMapping(params = "getOrder")
	@ResponseBody
	public JSONObject getOrder(){
		JSONObject jsonObj = new JSONObject();
		JSONObject jsonObj1 = new JSONObject();
		JSONObject jsonObj2 = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		JSONArray jsonArray1 = new JSONArray();
		String userid = ResourceUtil.getSessionUserName().getId();
		
		String sql = "select * from (select COUNT(l.createUserId),ts.mobilePhone,a.order_type from transferorder a left join order_linecardiver b on a.id = b .id left join "
				+ "car_info c on b.licencePlateId =c.id left join driversinfo d on b.driverId =d.id left join lineinfo l on l.id = a.line_id left join t_s_depart t on t.id = "
				+ "l.departId LEFT JOIN t_s_base_user u on u.ID=l.createUserId LEFT JOIN t_s_user ts on ts.id=u.ID LEFT JOIN t_s_role_user ru on ru.userid=ts.id LEFT JOIN "
				+ "t_s_role tr on tr.ID=ru.roleid where a.order_status='1' and tr.rolecode='adminkf' and a.order_type in('2','3') and l.createUserId ='"+userid+"' GROUP BY l.createUserId UNION select "
				+ "COUNT(l.createUserId),ts.mobilePhone,a.order_type from transferorder a left join order_linecardiver b on a.id = b.id left join car_info c on b.licencePlateId "
				+ "=c.id left join driversinfo d on b.driverId =d.id left join lineinfo l on l.id = a.line_id left join t_s_depart t on t.id = l.departId LEFT JOIN "
				+ "t_s_base_user u on u.ID=l.createUserId LEFT JOIN t_s_user ts on ts.id=u.ID LEFT JOIN t_s_role_user ru on ru.userid=ts.id LEFT JOIN t_s_role tr "
				+ "on tr.ID=ru.roleid where a.order_status='1' and tr.rolecode='adminkf' and a.order_type in('4','5') and l.createUserId ='"+userid+"' GROUP BY l.createUserId) gh ORDER BY gh.mobilePhone";
		
		List<Object> list = this.systemService.findListbySql(sql);
		
		BigInteger num;
		
		String mobile="";
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Object[] ob = (Object[]) list.get(i);
				 num = (BigInteger) ob[0];
				 mobile = ob[1]+"";
				 int oderType = (int) ob[2];
				 jsonObj.put("num", num.toString());
				 jsonObj.put("mobile", mobile);
				 jsonObj.put("oderType", oderType+"");
				 jsonArray.add(jsonObj);
			}
			
		}
		int ordair = 0;
		int ordtr = 0;
		String m="";
		for (int i = 0; i < jsonArray.size(); i++) {
			m= jsonArray.getJSONObject(i).getString("mobile");
			if(i>=1){
				String m2= jsonArray.getJSONObject(i-1).getString("mobile");
				if(!m.equals(m2)){
					ordair=0;
					ordtr = 0;
				}
			}
			
			String mnum= jsonArray.getJSONObject(i).getString("num");
			String moderType= jsonArray.getJSONObject(i).getString("oderType");
			int mt = Integer.parseInt(moderType);
			
			if(mt==4 || mt==5){
				ordair=Integer.parseInt(mnum);
			}else if(mt==2 || mt==3){
				ordtr=Integer.parseInt(mnum);
			}
			
			jsonObj1.put("mobile", m);
			jsonObj1.put("ordair", ordair+"");
			jsonObj1.put("ordtr", ordtr+"");
			jsonArray1.add(jsonObj1.toString());
		}
		
		for (int i = 1; i < jsonArray1.size(); i++) {
			String mo = jsonArray1.getJSONObject(i).getString("mobile");
			String mo1 = jsonArray1.getJSONObject(i-1).getString("mobile");
			if(mo1.equals(mo)){
				jsonArray1.remove(i-1);
				i--;
			}
		}
		for (int i = 0; i < jsonArray1.size(); i++) {
			String ordairs = jsonArray1.getJSONObject(i).getString("ordair");
			String ordtrs = jsonArray1.getJSONObject(i).getString("ordtr");
			jsonObj2.put("ordairs", ordairs);
			jsonObj2.put("ordtrs", ordtrs);
		}
		return jsonObj2;
	}
	
}

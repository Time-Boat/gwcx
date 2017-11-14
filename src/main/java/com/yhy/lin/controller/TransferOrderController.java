package com.yhy.lin.controller;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.web.system.pojo.base.TSBaseUser;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yhy.lin.app.util.AppGlobals;
import com.yhy.lin.app.util.SendMessageUtil;
import com.yhy.lin.entity.CarInfoEntity;
import com.yhy.lin.entity.DriversInfoEntity;
import com.yhy.lin.entity.ExportTransferorderEntity;
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

	private static final Logger logger = Logger.getLogger(TransferOrderController.class);
	
	@Autowired
	private SystemService systemService;

	@Autowired
	private TransferServiceI transferService;
	// 接送车司机安排
	@RequestMapping(params = "transferDriverList")
	public ModelAndView transferDriverList(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("cityList",getOpencity());
		request.setAttribute("lineNameList",getLine());
		return new ModelAndView("yhy/transferOrder/transferDriverList");
	}
	
	//订单详细
	@RequestMapping(params = "getOrderdetail")
	public ModelAndView getOrderdetail(HttpServletRequest request, HttpServletResponse response) {
		String orderType = request.getParameter("lineType");
		String lineOrderCode = request.getParameter("lineOrderCode");
		
		boolean b = false;
		//权限判断，只有运营专员和运营经理能看到安排司机车辆按钮
		if(checkRole(AppGlobals.OPERATION_SPECIALIST) || checkRole(AppGlobals.OPERATION_MANAGER) || checkRole(AppGlobals.XM_ADMIN)){
			request.setAttribute("permission", "1");
			b = true;
		}
		
		if("2".equals(orderType) || "3".equals(orderType)){
			return transferOrderAirList(request, response,lineOrderCode,orderType,b);
		}
		if("4".equals(orderType) || "5".equals(orderType)){
			return transferOrderList(request, response,lineOrderCode,orderType,b);
		}
		
		return new ModelAndView("yhy/transferOrder/transferDriverList");
	}

	// 接送火车订单处理
	@RequestMapping(params = "transferOrderList")
	public ModelAndView transferOrderList(HttpServletRequest request, HttpServletResponse response,String lineOrderCode,String orderType, boolean b) {
		
		if(!b){
			if(checkRole(AppGlobals.OPERATION_SPECIALIST) || checkRole(AppGlobals.OPERATION_MANAGER) || checkRole(AppGlobals.XM_ADMIN)){
				request.setAttribute("permission", "1");
			}
		}
		
		request.setAttribute("orderType",orderType);
		request.setAttribute("lineOrderCode",lineOrderCode);	
		request.setAttribute("carplateList",getCarPlate());	
		request.setAttribute("driverList",getDriver());	
		request.setAttribute("lineNameList",getLine1());
		return new ModelAndView("yhy/transferOrder/transferOrderList");
	}
	
	// 接送机订单处理
	@RequestMapping(params = "transferOrderAirList")
	public ModelAndView transferOrderAirList(HttpServletRequest request, HttpServletResponse response,String lineOrderCode,String orderType,boolean b) {
		
		if(!b){
			if(checkRole(AppGlobals.OPERATION_SPECIALIST) || checkRole(AppGlobals.OPERATION_MANAGER) || checkRole(AppGlobals.XM_ADMIN)){
				request.setAttribute("permission", "1");
			}
		}
		
		request.setAttribute("orderType",orderType);
		request.setAttribute("lineOrderCode",lineOrderCode);
		request.setAttribute("carplateList",getCarPlate());	
		request.setAttribute("driverList",getDriver());	
		request.setAttribute("lineNameList",getLine2());
		return new ModelAndView("yhy/transferOrder/transferOrderAirList");
	}
	
	@RequestMapping(params = "driverdatagrid")
	public void driverdatagrid(TransferorderEntity transferorder, HttpServletRequest request, HttpServletResponse response,
			DataGrid dataGrid) {
		
		String lineOrderCode = request.getParameter("lineOrderCode");
		String lineId = request.getParameter("lineId");
		String lineType = request.getParameter("lineType");
		String cityid = request.getParameter("cityID");
		
		JSONObject jObject = transferService.getDatagrid3(transferorder, dataGrid,cityid,lineId,lineOrderCode,lineType);
		
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
		TSUser user = ResourceUtil.getSessionUserName();
		TSDepart depart = user.getCurrentDepart();
		String orgCode = depart.getOrgCode();
		String orgType = depart.getOrgType();
		String userId = user.getId();
		
		StringBuffer sql = new StringBuffer();
		sql.append("select l.id,l.name from lineinfo l LEFT JOIN t_s_depart t on l.departId=t.ID LEFT JOIN t_s_base_user u on l.createUserId=u.ID where l.status='0' and l.type in('4','5')");
		
		// 添加了权限
		//判断当前的机构类型，如果是"岗位"类型，就需要加个userId等于当前用户的条件，确保各个专员之间只能看到自己的数据
		if(AppGlobals.ORG_JOB_TYPE.equals(orgType)){
			sql.append(" and l.createUserId = '" + userId + "' ");
		}
		if (StringUtil.isNotEmpty(orgCode)) {
			sql.append(" and t.org_code like '" + orgCode + "%'");
		}
		
		List<Object> list = this.systemService.findListbySql(sql.toString());
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
	 * 获取线路
	 */
	public String getLine2(){
		TSUser user = ResourceUtil.getSessionUserName();
		TSDepart depart = user.getCurrentDepart();
		String orgCode = depart.getOrgCode();
		String orgType = depart.getOrgType();
		String userId = user.getId();
		
		StringBuffer sql = new StringBuffer();
		sql.append("select l.id,l.name from lineinfo l LEFT JOIN t_s_depart t on l.departId=t.ID LEFT JOIN t_s_base_user u on l.createUserId=u.ID where l.status='0' and l.type in('2','3')");
		
		// 添加了权限
		//判断当前的机构类型，如果是"岗位"类型，就需要加个userId等于当前用户的条件，确保各个专员之间只能看到自己的数据
		if(AppGlobals.ORG_JOB_TYPE.equals(orgType)){
			sql.append(" and l.createUserId = '" + userId + "' ");
		}
		if (StringUtil.isNotEmpty(orgCode)) {
			sql.append(" and t.org_code like '" + orgCode + "%'");
		}
		
		List<Object> list = this.systemService.findListbySql(sql.toString());
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

	//获取接送机数据
	@RequestMapping(params = "airdatagrid")
	public void airdatagrid(TransferorderEntity transferorder, HttpServletRequest request, HttpServletResponse response,
			DataGrid dataGrid) {
		
		String orderTypes = request.getParameter("orderTypes");
		String lineOrderCode = request.getParameter("lineOrderCode");
		String orderStartingstation = request.getParameter("orderStartingstation");
		String orderTerminusstation = request.getParameter("orderTerminusstation");
		String lineId = request.getParameter("lineId");
		String driverName = request.getParameter("aDriverName");
		String plate = request.getParameter("aPlate");
		
		String fc_begin = request.getParameter("orderStartime_begin");
		String fc_end = request.getParameter("orderStartime_end");
		String ddTime_begin = request.getParameter("orderExpectedarrival_begin");
		String ddTime_end = request.getParameter("orderExpectedarrival_end");
		JSONObject jObject = transferService.getDatagrid1(transferorder, dataGrid,lineOrderCode,orderTypes,orderStartingstation, orderTerminusstation
				,lineId,driverName,plate,fc_begin, fc_end, ddTime_begin,ddTime_end);

		responseDatagrid(response, jObject);
	}
	
	@RequestMapping(params = "traindatagrid")
	public void traindatagrid(TransferorderEntity transferorder, HttpServletRequest request, HttpServletResponse response,
			DataGrid dataGrid) {
		//long startTime=System.currentTimeMillis();   //获取开始时间
		String orderTypes = request.getParameter("orderTypes");
		String lineOrderCode = request.getParameter("lineOrderCode");
		String orderStartingstation = request.getParameter("orderStartingstation");
		String orderTerminusstation = request.getParameter("orderTerminusstation");
		String lineId = request.getParameter("lineId");
		String driverName = request.getParameter("driverName");
		String plate = request.getParameter("plate");
		
		String fc_begin = request.getParameter("orderStartime_begin");
		String fc_end = request.getParameter("orderStartime_end");
		String ddTime_begin = request.getParameter("orderExpectedarrival_begin");
		String ddTime_end = request.getParameter("orderExpectedarrival_end");
		JSONObject jObject = transferService.getDatagrid2(transferorder, dataGrid,lineOrderCode,orderTypes,orderStartingstation, orderTerminusstation
				,lineId,driverName,plate,fc_begin, fc_end, ddTime_begin,ddTime_end);
		//long endTime=System.currentTimeMillis(); //获取结束时间
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
		String lineOrderCode = request.getParameter("lineOrderCode");
		
		/*logger.info("ids: " + ids);
		logger.info("slDate: " + slDate);
		logger.info("lineOrderCode: " + lineOrderCode);*/
		
		//接送机，接送火车，司机车辆安排三个模块都公用的这一个方法，高复用带来的高耦合，现在的解决办法就是通过参数判断
		if(StringUtil.isNotEmpty(lineOrderCode)){
			List<Map<String,Object>> tList = systemService.findForJdbc(
					"select order_startime from transferorder where lineOrderCode=? order by order_startime", new Object[]{lineOrderCode});
			
			String orderStartime = tList.get(0).get("order_startime") + "";
			
			slDate = (DateUtils.str2Date(orderStartime,DateUtils.datetimeFormat).getTime() / 1000) + "";
		}
		
		// System.out.println(ids);
		// 1、根据id查询到对应订单的所有线路，后台进行判断，如果是同一条线路，允许多条订单同时操作，否则给信息提示
		if (StringUtil.isNotEmpty(lineOrderCode)) {
			request.setAttribute("lineOrderCode", lineOrderCode);
		}else{
			request.setAttribute("ids", ids);
		}
		
		request.setAttribute("slDate", slDate + "");
		
		return new ModelAndView("yhy/transferOrder/transferOrderAdd");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */
	@RequestMapping(params = "carDatagrid")
	public void carDatagrid(CarInfoEntity carInfo,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		
		JSONObject jObject = transferService.getCarDatagrid(dataGrid);
		
		responseDatagrid(response, jObject);
		
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
		String ids = request.getParameter("ids");// 勾选的订单id
		String startTime = request.getParameter("startDate");// 发车时间
		String licencePlateId = request.getParameter("licencePlateId");// 车牌id
		String driverId = request.getParameter("driverId");// 司机id
		String licencePlateName = request.getParameter("licencePlateName");// 车牌号
		
		List<String> orderIds = new ArrayList<>();
		
		String lineOrderCode = request.getParameter("lineOrderCode");
		
		//通过订单批次的编码来查询所有的订单id
		if(StringUtil.isNotEmpty(lineOrderCode)){
			List<Map<String, Object>> list = systemService.findForJdbc("select id from transferorder where lineOrderCode=? and order_status in('1','2') ", lineOrderCode);
			for(Map<String, Object> map : list){
				//logger.info("tids: " + map.get("id"));
				orderIds.add(map.get("id") + "");
			}
		}else{
			orderIds = extractIdListByComma(ids);
		}
		
		List<String[]> contents = new ArrayList<>();
		
		boolean b = transferService.saveDriverAndDriver(orderIds, startTime, licencePlateId, driverId, licencePlateName, contents);
		
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
				+ "t_s_role tr on tr.ID=ru.roleid where a.order_status='1' and tr.rolecode='" + AppGlobals.OPERATION_MANAGER + "' and a.order_type in('2','3') and l.createUserId ='"+userid+"' GROUP BY l.createUserId UNION select "
				+ "COUNT(l.createUserId),ts.mobilePhone,a.order_type from transferorder a left join order_linecardiver b on a.id = b.id left join car_info c on b.licencePlateId "
				+ "=c.id left join driversinfo d on b.driverId =d.id left join lineinfo l on l.id = a.line_id left join t_s_depart t on t.id = l.departId LEFT JOIN "
				+ "t_s_base_user u on u.ID=l.createUserId LEFT JOIN t_s_user ts on ts.id=u.ID LEFT JOIN t_s_role_user ru on ru.userid=ts.id LEFT JOIN t_s_role tr "
				+ "on tr.ID=ru.roleid where a.order_status='1' and tr.rolecode='" + AppGlobals.OPERATION_MANAGER + "' and a.order_type in('4','5') and l.createUserId ='"+userid+"' GROUP BY l.createUserId) gh ORDER BY gh.mobilePhone";
		
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
	
	/**
	 * 检查用户是否被锁定
	 */
	@RequestMapping(params = "checkUser")
	@ResponseBody
	public JSONObject checkUser(HttpServletRequest request){
		JSONObject jsonObj = new JSONObject();
		
		//是否能对订单进行车辆的安排
		boolean b = true;
		
		String userIds = request.getParameter("userIds");
		
		String[] ids = userIds.split(",");
		
		//条件只对运营经理生效
		if(checkRole(AppGlobals.OPERATION_MANAGER)){
			for(String id : ids){
				TSBaseUser user = systemService.get(TSBaseUser.class, id);
				if(user == null || 1 == user.getStatus()){
					b = false;
					break;
				}
			}
		}
		
		jsonObj.put("success", b);
		
		return jsonObj;
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TransferorderEntity tranfer,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		
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
		
		//接送机模块导出和接送火车模块导出的区分字段
		String taOrderType = request.getParameter("taOrderType");
		
		List<ExportTransferorderEntity> tranfers = transferService.getListforExcel(tranfer, dataGrid,orderStartingstation,lineOrderCode, orderTerminusstation
				,lineId,driverName,plate,fc_begin, fc_end, ddTime_begin,ddTime_end,taOrderType);
		
		
		modelMap.put(NormalExcelConstants.FILE_NAME, taOrderType.contains("2") ? "接送机订单列表" : "接送火车订单列表");
		modelMap.put(NormalExcelConstants.CLASS,ExportTransferorderEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("订单详情列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(), "订单列表"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tranfers);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	
}

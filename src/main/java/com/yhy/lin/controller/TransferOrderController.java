package com.yhy.lin.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yhy.lin.entity.Order_LineCarDiverEntity;
import com.yhy.lin.entity.TransferorderEntity;
import com.yhy.lin.entity.TransferorderView;
import com.yhy.lin.service.TransferServiceI;

import net.sf.json.JSONObject;
/**
 * 接送机订单处理
 * @author HSAEE
 *
 */
@Controller
@RequestMapping("/transferOrderController")
public class TransferOrderController extends BaseController{
	@Autowired
	private SystemService systemService;
	
	@Autowired
	private TransferServiceI transferService;
	//接送机订单处理
	@RequestMapping(params="transferOrderList")
	public ModelAndView transferOrderList(HttpServletRequest request,HttpServletResponse response){
		return new ModelAndView("yhy/transferOrder/transferOrderList");
	}
	//接送机订单查询
	@RequestMapping(params="transferOrderSearchList")
	public ModelAndView transferOrderSearchList(HttpServletRequest request,HttpServletResponse response){
		return new ModelAndView("yhy/transferOrder/transferOrderSearchList");
	}
	@RequestMapping(params="datagrid")
	public void  dataGrid(TransferorderEntity transferorder,HttpServletRequest request,HttpServletResponse response,DataGrid dataGrid){
		String fc_begin = request.getParameter("orderStartime_begin");
		String fc_end = request.getParameter("orderStartime_end"); 
		String ddTime_begin = request.getParameter("orderExpectedarrival_begin"); 
		String ddTime_end = request.getParameter("orderExpectedarrival_end");  
		JSONObject jObject  = transferService.getDatagrid(transferorder,dataGrid,fc_begin,fc_end,ddTime_begin,ddTime_end);
        
		responseDatagrid(response, jObject);
	}
	/**
	 * 司机车辆安排跳转
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params="editCarAndDriver")
	public ModelAndView editCarAndDriver(HttpServletRequest request,HttpServletResponse response){
		String ids = request.getParameter("ids");
		
		String slDate = request.getParameter("slDate");
		
//		System.out.println(ids);
		//1、根据id查询到对应订单的所有线路，后台进行判断，如果是同一条线路，允许多条订单同时操作，否则给信息提示
		if(StringUtil.isNotEmpty(ids)){
			request.setAttribute("ids", ids);
			request.setAttribute("slDate", slDate);
		}
		return new ModelAndView("yhy/transferOrder/transferOrderAdd");
	}
	
	/**
	 * 保存接送订单的司机车辆安排
	 */
	@RequestMapping(params="saveCarAndDriver")
	@ResponseBody
	public AjaxJson saveCarAndDriver(HttpServletRequest request,HttpServletResponse response){
		String message = null;
        AjaxJson j = new AjaxJson();
		String ids =request.getParameter("id");//勾选的订单id
		String startTime = request.getParameter("startDate");//发车时间
		String licencePlateId = request.getParameter("licencePlateId");//车牌id
		String driverId = request.getParameter("driverId");//司机id
		//线路id暂时保留
		if(StringUtil.isNotEmpty(ids)){
			List<String> orderIds = extractIdListByComma(ids);
			List<Order_LineCarDiverEntity> list = new ArrayList<Order_LineCarDiverEntity>();
		        for(int i=0;i<orderIds.size();i++){
		        	Order_LineCarDiverEntity order_LineCarDiver = new Order_LineCarDiverEntity();
		        	order_LineCarDiver.setId(orderIds.get(i));
		        	order_LineCarDiver.setStartTime(startTime);
		        	order_LineCarDiver.setLicencePlateId(licencePlateId);
		        	order_LineCarDiver.setDriverId(driverId);
		        	list.add(order_LineCarDiver);
		        }
		        systemService.saveAllEntitie(list);
		        message="站点挂接成功";
		        j.setSuccess(true);
		}
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
		//获取部门信息
		String id= request.getParameter("id");
		if(StringUtil.isNotEmpty(id)){
			TransferorderView tView = transferService.getDetail(id);
			if(tView!=null){
				request.setAttribute("tView", tView);
			}
		}
		return new ModelAndView("yhy/transferOrder/transferOrderDetial");
	}
	
}

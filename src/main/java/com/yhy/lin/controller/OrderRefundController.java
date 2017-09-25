package com.yhy.lin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.web.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yhy.lin.app.util.AppGlobals;
import com.yhy.lin.app.util.AppUtil;
import com.yhy.lin.app.wechat.RequestHandler;
import com.yhy.lin.entity.TransferorderEntity;
import com.yhy.lin.service.OrderRefundServiceI;

import net.sf.json.JSONObject;

/**
 * 接送机订单退款处理
 * 
 * @author HSAEE
 *
 */
@Controller
@RequestMapping("/orderRefundController")
public class OrderRefundController extends BaseController {

	@Autowired
	private SystemService systemService;

	@Autowired
	private OrderRefundServiceI orderRefundService;
	
	@Autowired
	private UserService userService;
	
	//退款管理
	@RequestMapping(params = "orderRefundList")
	public ModelAndView orderRefundOrderList(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("yhy/transferOrder/orderRefundList");
	}

	@RequestMapping(params = "datagrid")
	public void dataGrid(TransferorderEntity transferorder, HttpServletRequest request, HttpServletResponse response,
			DataGrid dataGrid) {
		String fc_begin = request.getParameter("orderStartime_begin");
		String fc_end = request.getParameter("orderStartime_end");
		String rf_begin = request.getParameter("refundTime_begin");
		String rf_end = request.getParameter("refundTime_end");
		
		String orderStartingstation = request.getParameter("orderStartingstation");
		String orderTerminusstation = request.getParameter("orderTerminusstation");
		
		String departname = request.getParameter("departname");
		
		//有没有平台退款的权限
//		boolean hasPermission = checkRole(AppGlobals.PLATFORM_REFUND_AUDIT);
		
		JSONObject jObject = orderRefundService.getDatagrid(transferorder, dataGrid, fc_begin, fc_end,rf_begin,rf_end,orderStartingstation,
				orderTerminusstation, departname);
		
		responseDatagrid(response, jObject);
	}

	// 同意退款
	@RequestMapping(params = "agreeRefund")
	@ResponseBody
	public AjaxJson agreeRefund(HttpServletRequest request, HttpServletResponse response) {

		String message = null;
		AjaxJson j = new AjaxJson();
		String id = request.getParameter("id");// id

		boolean b = orderRefundService.agreeRefund(id);

		if (b) {
			message = "订单处理成功";
		} else {
			message = "服务器异常";
		}

		j.setSuccess(true);
		j.setMsg(message);
		return j;
	}

	// 拒绝退款
	@RequestMapping(params = "rejectRefund")
	@ResponseBody
	public AjaxJson rejectRefund(HttpServletRequest request, HttpServletResponse response) {

		String message = null;
		AjaxJson j = new AjaxJson();

		String id = request.getParameter("id");// id
		String rejectReason = request.getParameter("rejectReason");

		boolean b = orderRefundService.rejectRefund(id, rejectReason);

		if (b) {
			message = "订单处理成功";
		} else {
			message = "服务器异常";
		}

		j.setSuccess(true);
		j.setMsg(message);
		return j;
	}

	// 获取拒绝退款原因
	@RequestMapping(params = "getReason")
	@ResponseBody
	public AjaxJson getReason(HttpServletRequest request, HttpServletResponse response) {

		AjaxJson j = new AjaxJson();

		String id = request.getParameter("id");// id

		TransferorderEntity t = orderRefundService.getEntity(TransferorderEntity.class, id);
		String reasont = t.getRejectReason();
		
		j.setSuccess(true);
		j.setMsg(reasont);
		return j;
	}

	/**
	 * 批量同意退款
	 * 
	 * @return
	 * @author hxz
	 * @date 2017-04-17
	 */
	@RequestMapping(params = "doAgreeALLSelect")
	@ResponseBody
	public JSONObject doAgreeALLSelect(TransferorderEntity trans, HttpServletRequest request, HttpServletResponse response) {
		
		//根据角色的不同来判断到底是初审还是复审，运营专员只能进行初审，平台审核员能进行复审，优先进行平台审核员的判断
		String userId = ResourceUtil.getSessionUserName().getId();
		
		//是不是平台审核员
		boolean hasPermission = checkRole(AppGlobals.PLATFORM_REFUND_AUDIT);
		
		String message = null;
		Map<String,String> map = null;
		try {
			String ids = request.getParameter("ids");
			String fees = request.getParameter("fees");
			
			// 获得当前目录
			String path = request.getSession().getServletContext().getRealPath("/");
			
			RequestHandler refundRequest = new RequestHandler(request, response);
			
			String[] arrId = ids.split(",");
			
			if(hasPermission){
				map = orderRefundService.firstAgreeAllRefund(arrId, fees, refundRequest, path);
			}else{
				map = new HashMap<>();
				String date = AppUtil.getCurTime();
				
				int len = arrId.length;
				
				StringBuffer sbfIds = new StringBuffer();
				
				for(int i = 0;i < len;i++){
					sbfIds.append("'" + arrId[i] + "',");
				}
				if(len > 0){
					ids = sbfIds.deleteCharAt(sbfIds.length()-1).toString();
				}
				String sql = "update transferorder set first_audit_status='1',first_audit_user=?,first_audit_date=? where id in(" + ids + ")";
				int l = orderRefundService.executeSql(sql, userId, date);
				map.put("description", "共有" + l + "条数据被处理");
				map.put("statusCode", AppGlobals.APP_SUCCESS);
				map.put("success", "true");
			}
			
//			String[] entitys = ids.split(",");
//			if(entitys.length>0){
//				for (int i = 0; i < entitys.length; i++) {
//					trans = systemService.getEntity(TransferorderEntity.class, entitys[i]);
//					trans.setOrderStatus(4);
//					trans.setOrderPaystatus("2");
//					list.add(trans);
//				}
//			}
			
			// 批量逻辑删除
			//orderRefundService.updateAllEntitie(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		
		return JSONObject.fromObject(map);
	}
	
	/**
	 * 批量拒绝退款
	 * 
	 * @return
	 * @author hxz
	 * @date 2017-04-17
	 */
	@RequestMapping(params = "doRefuseALLSelect")
	@ResponseBody
	public AjaxJson doRefuseALLSelect(TransferorderEntity trans, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		try {
			String ids = request.getParameter("ids");
			String rejectReason = request.getParameter("rejectReason");
			String[] entitys = ids.split(",");
			List<TransferorderEntity> list = new ArrayList<TransferorderEntity>();
			if(entitys.length>0){
				for (int i = 0; i < entitys.length; i++) {
					trans = systemService.getEntity(TransferorderEntity.class, entitys[i]);
					//trans.setOrderStatus("5");
					trans.setOrderStatus(Integer.parseInt(trans.getBeforeStatus()));
//					trans.setOrderPaystatus("4");
					trans.setOrderPaystatus("0");
					trans.setRejectReason(rejectReason);
					list.add(trans);
				}
			}
			
			message = "批量拒绝成功！";
			// 批量拒绝
			orderRefundService.updateAllEntitie(list);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			j.setMsg(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return j;
	}
	
	
}

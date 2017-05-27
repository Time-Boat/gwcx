package com.yhy.lin.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yhy.lin.entity.TransferorderEntity;
import com.yhy.lin.service.OrderRefundServiceI;

import net.sf.json.JSONObject;

/**
 * 接送机订单处理
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

	// 接送机订单处理
	@RequestMapping(params = "orderRefundList")
	public ModelAndView orderRefundOrderList(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("yhy/transferOrder/orderRefundList");
	}

	@RequestMapping(params = "datagrid")
	public void dataGrid(TransferorderEntity transferorder, HttpServletRequest request, HttpServletResponse response,
			DataGrid dataGrid) {
		String fc_begin = request.getParameter("orderStartime_begin");
		String fc_end = request.getParameter("orderStartime_end");
		String ddTime_begin = request.getParameter("orderExpectedarrival_begin");
		String ddTime_end = request.getParameter("orderExpectedarrival_end");
		JSONObject jObject = orderRefundService.getDatagrid(transferorder, dataGrid, fc_begin, fc_end, ddTime_begin,
				ddTime_end);

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

}

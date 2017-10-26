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
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;

import com.yhy.lin.app.util.AppUtil;
import com.yhy.lin.entity.CharteredAppendServiceEntity;
import com.yhy.lin.entity.CharteredPackageEntity;
import com.yhy.lin.entity.CharteredPriceAppendServiceEntity;
import com.yhy.lin.entity.CharteredPriceEntity;
import com.yhy.lin.entity.OpenCityEntity;
import com.yhy.lin.service.CharteredPriceAppendServiceEntityServiceI;
import com.yhy.lin.service.CharteredPriceServiceI;

import net.sf.json.JSONObject;

/**
 * 包车价格设置
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/charteredPriceController")
public class CharteredPriceController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(CharteredPriceController.class);

	@Autowired
	private CharteredPriceServiceI charteredPriceService;
	@Autowired
	private CharteredPriceAppendServiceEntityServiceI charteredPriceAppendService;
	@Autowired
	private SystemService systemService;

	/**
	 * 包车价格设置列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "charteredPriceList")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("yhy/charteredPrice/charteredPriceList");
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
	public void datagrid(CharteredPriceEntity charteredPrice,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		
		String cityID = request.getParameter("cityID");// 城市
		JSONObject jObject = charteredPriceService.getDatagrid(dataGrid,charteredPrice,cityID);
		responseDatagrid(response, jObject);
	}

	/**
	 * 删除包车定价设置
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(CharteredPriceEntity charteredPrice, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		charteredPrice = systemService.getEntity(CharteredPriceEntity.class, charteredPrice.getId());
		message = "包车定价设置删除成功";
		charteredPriceService.delete(charteredPrice);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加包车定价设置
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(CharteredPriceEntity charteredPrice, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		String cityid = request.getParameter("city");
		String packageId = request.getParameter("packageId");
		String charteredAppendId[] = request.getParameterValues("charteredAppendId");
		String userId = ResourceUtil.getSessionUserName().getId();
		
		if (StringUtil.isNotEmpty(cityid)) {//添加所在城市
			charteredPrice.setCityId(cityid);
		}
		if (StringUtil.isNotEmpty(packageId)) {//添加套餐
			charteredPrice.setPackageId(packageId);
		}
		
		if (StringUtil.isNotEmpty(charteredPrice.getId())) {
			message = "包车定价设置更新成功";
			CharteredPriceEntity t = charteredPriceService.get(CharteredPriceEntity.class, charteredPrice.getId());
			try {
				charteredPrice.setModifyUser(userId);
				charteredPrice.setMidifyTime(AppUtil.getDate());
				MyBeanUtils.copyBeanNotNull2Bean(charteredPrice, t);
				charteredPriceService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = "包车定价设置更新失败";
			}
		} else {
			message = "包车定价设置添加成功";
			charteredPrice.setStatus("0");
			charteredPrice.setAuditStatus("0");
			charteredPrice.setCreateUserId(userId);
			charteredPrice.setCreateTime(AppUtil.getDate());
			charteredPriceService.save(charteredPrice);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		if (StringUtil.isNotEmpty(charteredAppendId)) {//添加附加服务
			for (int i = 0; i < charteredAppendId.length; i++) {
				//中间表添加数据
				CharteredPriceAppendServiceEntity appendService = new CharteredPriceAppendServiceEntity();
				String AppendId = charteredAppendId[i];
				appendService.setAppendServiceId(AppendId);
				if (StringUtil.isNotEmpty(charteredPrice.getId())) {
					appendService.setCharteredPriceId(charteredPrice.getId());
				}
				charteredPriceAppendService.save(appendService);
			}
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 包车定价设置列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(CharteredPriceEntity charteredPrice, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(charteredPrice.getId())) {
			charteredPrice = charteredPriceService.getEntity(CharteredPriceEntity.class, charteredPrice.getId());
			req.setAttribute("charteredPrice", charteredPrice);
			//获取附加服务
			List<CharteredPriceAppendServiceEntity> CharteredPriceAppendService = systemService.findByProperty(CharteredPriceAppendServiceEntity.class,"charteredPriceId",charteredPrice.getId());
			List<String> list = new ArrayList<String>();
			for (int i = 0; i < CharteredPriceAppendService.size(); i++) {
				String CharteredPriceAppendServiceId= CharteredPriceAppendService.get(i).getAppendServiceId();
				list.add(CharteredPriceAppendServiceId);
			}
			System.out.println(list.toString());
			req.setAttribute("CharteredPriceAppendService", list);
		}
		List<OpenCityEntity> cities = systemService.findByProperty(OpenCityEntity.class, "status", "0");
		req.setAttribute("cities", cities);
		//获取已上架的套餐
		List<CharteredPackageEntity> CharteredPackage = systemService.findByProperty(CharteredPackageEntity.class, "status","0");
		req.setAttribute("CharteredPackage", CharteredPackage);
		//获取已启动的包车附加服务
		List<CharteredAppendServiceEntity> charteredAppendService = systemService.findByProperty(CharteredAppendServiceEntity.class, "status","1");
		req.setAttribute("charteredAppendService", charteredAppendService);
		
		return new ModelAndView("yhy/charteredPrice/charteredPrice");
	}
	
	/**
	 * 申请上架、申请下架
	 */
	@RequestMapping(params = "applyShelves")
	@ResponseBody
	public AjaxJson applyShelves(HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		String id = request.getParameter("id");
		
		CharteredPriceEntity  charteredPrice = this.systemService.getEntity(CharteredPriceEntity.class, id);
		if(StringUtil.isNotEmpty(charteredPrice)){
			charteredPrice.setAuditStatus("1");//待审核
			if("0".equals(charteredPrice.getStatus())){
				charteredPrice.setApplyType("0");//申请启用
			}else{
				charteredPrice.setApplyType("1");//申请停用
			}
			charteredPrice.setAuditDate(AppUtil.getDate());
			charteredPrice.setApplyUser(ResourceUtil.getSessionUserName().getId());
		}
		try {
			message = "申请成功！";
			this.systemService.saveOrUpdate(charteredPrice);
		} catch (Exception e) {
			// TODO: handle exception
			message = "服务器异常！";
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
		CharteredPriceEntity chartered= this.systemService.getEntity(CharteredPriceEntity.class, id);

		if (StringUtil.isNotEmpty(chartered)) {
			if (StringUtil.isNotEmpty(chartered.getLastRejectReason())) {
				message = chartered.getLastRejectReason();// 复审拒绝原因
			} else {
				message = chartered.getRejectReason();// 初审拒绝原因
			}
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 申请同意
	 */
	@RequestMapping(params = "agree")
	@ResponseBody
	public AjaxJson agree(HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		String id = request.getParameter("id");
		CharteredPriceEntity  chartered = this.systemService.getEntity(CharteredPriceEntity.class, id);
		
		if(StringUtil.isNotEmpty(chartered)){
			if("1".equals(chartered.getAuditStatus())){
				chartered.setAuditStatus("2");//初审
				chartered.setApplyUser(ResourceUtil.getSessionUserName().getId());
				chartered.setAuditDate(AppUtil.getDate());
			}else if("2".equals(chartered.getAuditStatus())){
				if("1".equals(chartered.getStatus())){
					chartered.setAuditStatus("4");//复审
					chartered.setStatus("2");//已上架
				}else if("0".equals(chartered.getStatus())){
					chartered.setAuditStatus("3");//复审
					chartered.setStatus("1");//已下架
				}
				chartered.setLastAuditDate(AppUtil.getDate());
				chartered.setLastAuditUser((ResourceUtil.getSessionUserName().getId()));
			}
		}
		try {
			message = "申请成功！";
			this.systemService.saveOrUpdate(chartered);
		} catch (Exception e) {
			// TODO: handle exception
			message = "服务器异常！";
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
		CharteredPriceEntity chartered = this.systemService.getEntity(CharteredPriceEntity.class, id);

		if (StringUtil.isNotEmpty(chartered)) {

			if ("1".equals(chartered.getAuditStatus())) {
				chartered.setRejectReason(rejectReason);
			}
			if ("2".equals(chartered.getAuditStatus())) {
				chartered.setLastRejectReason(rejectReason);
			}
			if ("1".equals(chartered.getAuditStatus())) {
				chartered.setAuditStatus("5");// 初审拒绝
				chartered.setAuditUser(ResourceUtil.getSessionUserName().getId());
				chartered.setAuditDate(AppUtil.getDate());
			} else if ("2".equals(chartered.getAuditStatus())) {
				chartered.setAuditStatus("6");// 复审拒绝
				chartered.setLastAuditUser(ResourceUtil.getSessionUserName().getId());
				chartered.setLastAuditDate(AppUtil.getDate());
			}
		}

		try {
			message = "拒绝成功！";
			this.systemService.saveOrUpdate(chartered);
		} catch (Exception e) {
			// TODO: handle exception
			message = "服务器异常！";
		}

		j.setMsg(message);
		j.setMsg(message);
		return j;
	}
	
}

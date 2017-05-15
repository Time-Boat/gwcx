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
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.jeecgframework.core.beanvalidator.BeanValidators;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.net.URI;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;

import com.yhy.lin.entity.CompanyDepartViewEntity;
import com.yhy.lin.entity.DriversInfoEntity;
import com.yhy.lin.entity.StaffInfoEntity;
import com.yhy.lin.service.StaffInfoServiceI;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @Title: Controller
 * @Description: 公司员工信息
 * @author zhangdaihao
 * @date 2017-04-17 17:30:59
 * @version V1.0
 *
 */
@Controller
@RequestMapping("/staffInfoController")
public class StaffInfoController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(StaffInfoController.class);

	@Autowired
	private StaffInfoServiceI staffInfoService;
	@Autowired
	private SystemService systemService;

	/**
	 * 公司员工信息列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		List<TSDepart> t = getCompanys();
		
		StringBuffer json = new StringBuffer("{'data':[");
		for (int i = 0; i < t.size(); i++) {
			json.append("{");
			json.append("'id':'" + t.get(i).getOrgCode() + "',");
			json.append("'departname':'" + t.get(i).getDepartname() + "'");
			json.append("},");
		}
		json.delete(json.length()-1, json.length());
		json.append("]}");
		request.setAttribute("companyList",json.toString());
		return new ModelAndView("/yhy/cCompany/staffInfoList");
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
	public void datagrid(StaffInfoEntity staffInfo, HttpServletRequest request, HttpServletResponse response,
			DataGrid dataGrid) {
		
//		String job = request.getParameter("staffPosition");
//		String depart = request.getParameter("depart");
		String name = request.getParameter("name");
		String companyId = request.getParameter("companyId");
		String phone = request.getParameter("phone");
//		String sex = request.getParameter("sex");
		
		String orgCode = ResourceUtil.getSessionUserName().getCurrentDepart().getOrgCode();

		JSONObject jObject = staffInfoService.getDatagrid(name, orgCode, companyId, phone, dataGrid);

		responseDatagrid(response, jObject);
		
	}

	/**
	 * 删除公司员工信息
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(StaffInfoEntity staffInfo, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		staffInfo = systemService.getEntity(StaffInfoEntity.class, staffInfo.getId());
		message = "公司员工信息删除成功";
		staffInfoService.delete(staffInfo);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);

		j.setMsg(message);
		return j;
	}

	/**
	 * 添加公司员工信息
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(StaffInfoEntity staffInfo, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();

		if (StringUtil.isNotEmpty(staffInfo.getId())) {
			message = "公司员工信息更新成功";
			StaffInfoEntity t = staffInfoService.get(StaffInfoEntity.class, staffInfo.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(staffInfo, t);
				staffInfoService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = "公司员工信息更新失败";
			}
		} else {
			message = "公司员工信息添加成功";

			// 默认设置为0 未删除状态
			staffInfo.setDeleteFlag((short) 0);

			staffInfoService.save(staffInfo);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 公司员工信息列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(StaffInfoEntity staffInfo, HttpServletRequest req) {

		req.setAttribute("companyList", getCompanys());

		// 还是要做判断，判断当前用户的角色来控制如何查询
//		String userId = ResourceUtil.getSessionUserName().getId();
		// 通过userId去查询对应角色
//		String roleSql = "select rolecode from user_role_rolecode_view where userId ='" + userId + "'";
//		List<String> rolecode = systemService.findListbySql(roleSql);

		if (StringUtil.isNotEmpty(staffInfo.getId())) {
			staffInfo = staffInfoService.getEntity(StaffInfoEntity.class, staffInfo.getId());
			req.setAttribute("staffInfoPage", staffInfo);
		}
		return new ModelAndView("/yhy/cCompany/staffInfo");
	}
	
	public List<TSDepart> getCompanys(){
		// 用户所属组织机构的orgCode
		String orgCode = ResourceUtil.getSessionUserName().getCurrentDepart().getOrgCode();

		// 获取公司信息
		List<TSDepart> companyList = systemService
				.findByQueryString("from TSDepart where org_code like '" + orgCode + "%' and org_type='4' ");
		
		return companyList;
	}

}

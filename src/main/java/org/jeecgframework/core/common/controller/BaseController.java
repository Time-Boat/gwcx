package org.jeecgframework.core.common.controller;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.core.interceptors.DateConvertEditor;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 基础控制器，其他控制器需集成此控制器获得initBinder自动转换的功能
 * @author  张代浩
 * 
 */
@Controller
@RequestMapping("/baseController")
public class BaseController {
	
	@Autowired
	private SystemService systemService;

	/**
	 * 将前台传递过来的日期格式的字符串，自动转化为Date类型
	 * 
	 * @param binder
	 */
	@InitBinder
	public void initBinder(ServletRequestDataBinder binder) {
//		SimpleDateFormat dateFormat = new SimpleDateFormat(
//				"yyyy-MM-dd hh:mm:ss");
//		binder.registerCustomEditor(Date.class, new CustomDateEditor(
//				dateFormat, true));
		binder.registerCustomEditor(Date.class, new DateConvertEditor());
	}

	/**
	 * 分页公共方法(非easyui)
	 * 
	 * @author Alexander
	 * @date 20131022
	 */
	public List<?> pageBaseMethod(HttpServletRequest request,
			DetachedCriteria dc, CommonService commonService, int pageRow) {
		// 当前页
		// 总条数
		// 总页数

		int currentPage = 1;

		int totalRow = 0;
		int totalPage = 0;
		// 获取当前页
		String str_currentPage = request.getParameter("str_currentPage");
		currentPage = str_currentPage == null || "".equals(str_currentPage) ? 1
				: Integer.parseInt(str_currentPage);
		// 获取每页的条数
		String str_pageRow = request.getParameter("str_pageRow");
		pageRow = str_pageRow == null || "".equals(str_pageRow) ? pageRow
				: Integer.parseInt(str_pageRow);

		// 统计的总行数
		dc.setProjection(Projections.rowCount());

		totalRow = Integer.parseInt(commonService.findByDetached(dc).get(0)
				.toString());
		totalPage = (totalRow + pageRow - 1) / pageRow;

		currentPage = currentPage < 1 ? 1 : currentPage;
		currentPage = currentPage > totalPage ? totalPage : currentPage;
		// 清空统计函数
		dc.setProjection(null);
		// dc.setResultTransformer(dc.DISTINCT_ROOT_ENTITY);
		List<?> list = commonService.pageList(dc, (currentPage - 1) * pageRow,
				pageRow);

		request.setAttribute("currentPage", currentPage);
		request.setAttribute("pageRow", pageRow);
		request.setAttribute("totalRow", totalRow);
		request.setAttribute("totalPage", totalPage);
		return list;
	}

    /**
     * 抽取由逗号分隔的主键列表
     *
     * @param ids
     *            由逗号分隔的多个主键值
     * @return 主键类表
     * @author 张国明 2014-8-21 21:57:16
     */
    protected List<String> extractIdListByComma(String ids) {
        List<String> result = new ArrayList<String>();
        if (StringUtils.hasText(ids)) {
            for (String id : ids.split(",")) {
                if (StringUtils.hasLength(id)) {
                    result.add(id.trim());
                }
            }
        }

        return result;
    }
    
    // -----------------------------------------------------------------------------------
  	public void responseDatagrid(HttpServletResponse response, JSONObject jObject) {
  		response.setContentType("application/json");
  		response.setHeader("Cache-Control", "no-store");
  		try {
  			PrintWriter pw=response.getWriter();
  			pw.write(jObject.toString());
  			pw.flush();
  		} catch (IOException e) {
  			e.printStackTrace();
  		}
  	}	
  	
  	/**
	 * 获得开通城市
	 * @return
	 */
	public String getOpencity(){
		String sql = "select op.city_id,op.city_name from open_city op where op.status='0' ";
		List<Object> list = this.systemService.findListbySql(sql);
		StringBuffer json = new StringBuffer("{'data':[");
		if(list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				Object[] ob = (Object[]) list.get(i);
				String id = ob[0]+"";
				String cityName = ob[1]+"";
					json.append("{");
					json.append("'cityID':'" +id + "',");
					json.append("'cityName':'"+ cityName + "'");
					json.append("},");
				}
			}
		json.delete(json.length()-1, json.length());
		json.append("]}");
		return json.toString();
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
}

package org.jeecgframework.core.common.controller;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.core.interceptors.DateConvertEditor;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.web.system.service.UserService;
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

	@Autowired
	private UserService userService;
	
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
	 * 检查用户是否有传入的参数权限权限
	 * @param role 角色权限
	 */
  	public boolean checkRole(String role){
		TSUser user = ResourceUtil.getSessionUserName();
		return checkRole(user, role);
  	}
  	
  	/**
	 * 检查用户是否有传入的参数权限权限
	 * @param role 角色权限
	 */
  	public boolean checkRole(TSUser user, String role){
  		
  		//是否拥有role这个角色权限
  		boolean hasPermission = false;
  		
  		//根据角色的不同来判断到底是初审还是复审，运营专员只能进行初审，平台审核员能进行复审，优先进行平台审核员的判断
		String roles = userService.getUserRole(user);
		String a[] = roles.split(",");
		
		for(int i=0;i<a.length;i++){
			if(a[i].equals(role)){
				hasPermission = true;
				break;
			}
		}
		return hasPermission;
  	}
  	
  	/**
	 * 根据给定的专员id，查找其上级的经理
	 * @param userId 
	 */
  	public String[] getUsers(String userId){
  		
  		//根据创建线路的专员向上找一级，找到经理的id
  		List<String> userList = systemService.findListbySql(
  				" select u.id from t_s_base_user u left join t_s_user_org o on o.user_id = u.id left join t_s_depart t on t.id = o.org_id "
  				+ " where t.org_code = SUBSTRING((select t.org_code from t_s_base_user u left join t_s_user_org o on o.user_id = u.id "
  				+ " left join t_s_depart t on o.org_id = t.id where u.id = '" + userId + "') ,1,9)");
  		
  		String[] users = new String[userList.size()];
  		
  		for (int i = 0; i < userList.size(); i++) {
  			users[i] = userList.get(i);
		}
  		
		return users;
  	}
  	
  	/**
	 * 根据给定的运营专员id，查找其上级的公司，然后找到管理这家公司的平台审核员
	 * @param userId 用户id
	 * @param role   角色编码
	 */
  	public String[] getAudits(String userId, String role){
  		
  		//根据数据的创建人所在的公司，找到管理其公司的审核员
  		List<String> list = systemService.findListbySql("select tsu.id from t_s_role r left join t_s_role_user ru on r.id = ru.roleid "
				+ " left join t_s_user tsu on tsu.id = ru.userid where r.rolecode in ('" + role + "') and tsu.org_company like "
				+ " CONCAT('%',(select SUBSTRING(t.org_code,1,6) from t_s_user_org o left join t_s_depart t on o.org_id = t.id "
				+ "  where o.user_Id = '" + userId + "'),'%')");
  		
  		String[] users = new String[list.size()];
  		
  		for (int i = 0; i < list.size(); i++) {
  			users[i] = list.get(i);
		}
  		
		return users;
  	}
  	
}

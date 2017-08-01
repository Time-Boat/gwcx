package com.yhy.lin.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tools.ant.types.CommandlineJava.SysProperties;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yhy.lin.entity.BusStopInfoEntity;
import com.yhy.lin.entity.CitiesEntity;
import com.yhy.lin.entity.LineInfoEntity;
import com.yhy.lin.entity.OpenCityEntity;
import com.yhy.lin.service.LineInfoServiceI;

import net.sf.json.JSONObject;

/**
 * 接送机线路管理模块
 * @author HSAEE
 *
 */
@Controller
@RequestMapping("lineInfoSpecializedController")
public class LineInfoSpecializedController extends BaseController{
	@Autowired
	private SystemService systemService;
	
	@Autowired
	private LineInfoServiceI lineInfoService;
	
	/**
	 *	 接送机线路管理跳转页面
	 */
	@RequestMapping(params="lineList")
	public ModelAndView lineList(HttpServletRequest request){
		request.setAttribute("cityList",getOpencity());	
		return new ModelAndView("yhy/linesSpecial/lineList");
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
	
	@RequestMapping(params="datagrid")
	public void datagrid(LineInfoEntity lineInfos,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid){
		String cityid = request.getParameter("cityID");
		String linetype = request.getParameter("linetype");//线路类型
		String beginTime = request.getParameter("createTime_begin");//线路创建开始查询时间
		String endTime = request.getParameter("createTime_end");//线路创建结束查询时间
		String lstartTime_begin = request.getParameter("lstartTime_begin");
		String lstartTime_end = request.getParameter("lstartTime_end");
		String lendTime_begin = request.getParameter("lendTime_begin");
		String lendTime_end = request.getParameter("lendTime_end");
		//因为调用的方法一样，所以在外层来处理...   忘记是啥意思了。。。
		linetype = " >='"+linetype+"' ";
		JSONObject jObject  = lineInfoService.getDatagrid3(lineInfos,cityid,beginTime,endTime,dataGrid,lstartTime_begin,lstartTime_end,lendTime_begin,lendTime_end,linetype);
        responseDatagrid(response, jObject);
	}
	
	/**
	 * 去线路添加页面
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(LineInfoEntity lineInfo, HttpServletRequest req) {
		//获取部门信息
		List<TSDepart> departList = systemService.getList(TSDepart.class);
		req.setAttribute("departList", departList);	
		
		//修改线路
		if (StringUtil.isNotEmpty(lineInfo.getId())) {
			lineInfo = lineInfoService.getEntity(LineInfoEntity.class, lineInfo.getId());
			req.setAttribute("lineInfo", lineInfo);
			req.setAttribute("startList",getStartLocation(lineInfo.getCityId(),lineInfo.getType()));
			req.setAttribute("endList",getEndLocation(lineInfo.getCityId(),lineInfo.getType(),lineInfo.getStartLocation()));
		}
		List<OpenCityEntity> cities = systemService.findByProperty(OpenCityEntity.class, "status", "0");
		req.setAttribute("cities", cities);
		List<TSDepart> list = systemService.findByProperty(TSDepart.class, "orgType", "4");
		if(list.size()>0){
			req.setAttribute("list", list);
		}
			
		return new ModelAndView("yhy/linesSpecial/lineAdd");
	}
	
	/**
	 * 获取起点站点
	 * @param cityid
	 * @param type
	 * @return
	 */
	public List<BusStopInfoEntity> getStartLocation(String cityid,String type){
		
		StringBuffer st = new StringBuffer();
	        
	    if(StringUtil.isNotEmpty(type)){
	        if("2".equals(type) || "4".equals(type)){
	        	st.append("select DISTINCT b.id,b.name from busstopinfo b LEFT JOIN line_busstop a on b.id=a.busStopsId where  1=1 ");
	                if(StringUtil.isNotEmpty(cityid)){
	                	st.append(" and b.cityId='"+cityid+"'");
	                }
	                if("2".equals(type)){
	                	st.append("and b.station_type = '2'");
	                }
	                if("4".equals(type)){
	                	st.append("and b.station_type = '1'");
	                }
	        	}
	        	
	        	if("3".equals(type) || "5".equals(type)){
	        		st.append("select DISTINCT b.id,b.name from busstopinfo b LEFT JOIN line_busstop a on b.id=a.busStopsId where 1=1");
	                if(StringUtil.isNotEmpty(cityid)){
	                	st.append(" and b.cityId='"+cityid+"'");
	                }
	                st.append("and b.station_type = '0'");
	        	}
	        }else{
	        	st.append("select DISTINCT b.id,b.name from busstopinfo b LEFT JOIN line_busstop a on b.id=a.busStopsId where  1=1");
	        }
		
		List<Object> list = this.systemService.findListbySql(st.toString());
		List<BusStopInfoEntity> buslist = new ArrayList<BusStopInfoEntity>();
		if(list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				Object[] ob = (Object[]) list.get(i);
				String id = ob[0]+"";
				String name = ob[1]+"";
				BusStopInfoEntity bus = new BusStopInfoEntity();
				bus.setId(id);
				bus.setName(name);
				buslist.add(bus);
				}
			}
		return buslist;
	}
	
	/**
	 * 获取终点站点
	 * @param cityid
	 * @param type
	 * @param busid
	 * @return
	 */
	public List<BusStopInfoEntity> getEndLocation(String cityid,String type,String busid){
		
		StringBuffer st = new StringBuffer();
        
        if(StringUtil.isNotEmpty(type)){
        	if("2".equals(type) || "4".equals(type)){
        		st.append("select DISTINCT b.id,b.name from busstopinfo b  where 1=1 ");
        		
        		if(StringUtil.isNotEmpty(cityid)){
                	st.append(" and b.cityId='"+cityid+"'");
                }
        		st.append("and b.station_type = '0'");
        		
        		if(StringUtil.isNotEmpty(busid)){
        			st.append(" and b.id not in (select g.id from line_busstop f LEFT JOIN busstopinfo g on g.id=f.busStopsId where f.siteId='");
        			st.append(busid+"'");
        		}
        		st.append(" and g.status like '%"+type+"%')");
        		
        	}
        	
        	if("3".equals(type) || "5".equals(type)){
        		st.append("select DISTINCT b.id,b.name from busstopinfo b  where 1=1 ");
                if(StringUtil.isNotEmpty(cityid)){
                	st.append(" and b.cityId='"+cityid+"'");
                }
                if("3".equals(type)){
                	st.append("and b.station_type = '2'");
                }
                if("5".equals(type)){
                	st.append("and b.station_type = '1'");
                }
                
                if(StringUtil.isNotEmpty(busid)){
                	st.append(" and b.id not in (select lb.siteId from line_busstop lb LEFT JOIN busstopinfo a on a.id=lb.busStopsId where lb.siteId is not null");
                	st.append(" and lb.busStopsId='"+busid+"'");
                	st.append(" and a.status like '%"+type+"%')");
        		}
        	}
        }
        List<Object> list = this.systemService.findListbySql(st.toString());
        List<BusStopInfoEntity> buslist = new ArrayList<BusStopInfoEntity>();
		if(list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				Object[] ob = (Object[]) list.get(i);
				String id = ob[0]+"";
				String name = ob[1]+"";
				BusStopInfoEntity bus = new BusStopInfoEntity();
				bus.setId(id);
				bus.setName(name);
				buslist.add(bus);
				}
			}
		return buslist;
	}
}

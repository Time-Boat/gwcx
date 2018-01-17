package com.yhy.lin.app.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yhy.lin.app.entity.AppCharteredPackageEntity;
import com.yhy.lin.app.entity.AppCharteredPriceEntity;
import com.yhy.lin.app.exception.ParameterException;
import com.yhy.lin.app.service.AppCharteredInterfaceService;
import com.yhy.lin.app.util.AppGlobals;
import com.yhy.lin.app.util.AppUtil;
import com.yhy.lin.entity.CharteredPackageEntity;

import net.sf.json.JSONObject;

/**
 * Description : app包车接口
 * @author Timer
 * @date 2017年10月19日 下午4:08:28\
 */
@Controller
@RequestMapping(value = "/charteredApp")
public class AppCharteredInterfaceContoller  extends AppBaseController {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(AppInterfaceController.class);

	@Autowired
	private AppCharteredInterfaceService appCharteredService;
	        
	@Autowired
	private SystemService systemService;

	// 获取包车线路信息
	@RequestMapping(params = "getCharteredInfo")
	public void getCharteredInfo(HttpServletRequest request, HttpServletResponse response) {
		AppUtil.responseUTF8(response);
		JSONObject returnJsonObj = new JSONObject();
		String msg = "";
		String statusCode = "";

		JSONObject data = new JSONObject();

		try {
			// 2： 接机 3：送机 4：接火车 5：送火车
			String serveType = request.getParameter("serveType");
			// 所属城市
			String cityId = request.getParameter("cityId"); 

			// 验证参数
			checkParam(new String[] { "serveType", "cityId" }, serveType, cityId);

			//				List<AppStationInfoEntity> lList = appCharteredService.getPTStation(serveType, cityId);

			//				data.put("PTStation", lList);

			statusCode = AppGlobals.APP_SUCCESS;
			msg = AppGlobals.APP_SUCCESS_MSG;

		} catch (ParameterException e) {
			statusCode = e.getCode();
			msg = e.getErrorMessage();
			logger.error(e.getErrorMessage());
		} catch (Exception e) {
			statusCode = AppGlobals.SYSTEM_ERROR;
			msg = AppGlobals.SYSTEM_ERROR_MSG;
			e.printStackTrace();
		}

		returnJsonObj.put("msg", msg);
		returnJsonObj.put("code", statusCode);
		returnJsonObj.put("data", data.toString());

		responseOutWrite(response, returnJsonObj);
	}

	/**
	 * 获取包车套餐信息
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "getCharteredPackage")
	public void getCharteredPackage(HttpServletRequest request, HttpServletResponse response) {
		AppUtil.responseUTF8(response);
		JSONObject returnJsonObj = new JSONObject();
		String msg = "";
		String statusCode = "";

		try {
			String param = AppUtil.inputToStr(request);
			System.out.println("tokenLogin    前端传递参数：" + param);

			//验证参数
			JSONObject jsondata = checkParam(param);
			String token = jsondata.getString("token");
			String cityId = jsondata.getString("cityId"); 
			checkToken(token);//验证token

			List<CharteredPackageEntity> charterelist = this.systemService.findHql("from CharteredPackageEntity where cityId=? and status=? and deleteFlag=? ", cityId, "0","0");

			if (charterelist != null && charterelist.size() > 0) {
				returnJsonObj.put("data", charterelist);
			} else {
				returnJsonObj.put("data", "");
			}

			statusCode = AppGlobals.APP_SUCCESS;
			msg = AppGlobals.APP_SUCCESS_MSG;

		} catch (ParameterException e) {
			statusCode = e.getCode();
			msg = e.getErrorMessage();
			logger.error(e.getErrorMessage());
		} catch (Exception e) {
			statusCode = AppGlobals.SYSTEM_ERROR;
			msg = AppGlobals.SYSTEM_ERROR_MSG;
			e.printStackTrace();
		}

		returnJsonObj.put("msg", msg);
		returnJsonObj.put("code", statusCode);

		responseOutWrite(response, returnJsonObj);
	}


	/**
	 * 获取包车车辆类型
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "getCharteredCarType")
	public void getCharteredCarType(HttpServletRequest request, HttpServletResponse response) {
		AppUtil.responseUTF8(response);
		JSONObject returnJsonObj = new JSONObject();
		String msg = "";
		String statusCode = "";

		try {
			String param = AppUtil.inputToStr(request);
			System.out.println("tokenLogin    前端传递参数：" + param);
			//验证参数
			JSONObject jsondata = checkParam(param);
			String token = jsondata.getString("token");
			String packageId = jsondata.getString("packageId"); //包车套餐
			String charteredType = jsondata.getString("charteredType"); //包车类型，0：单程，1：往返
			String actualMileage = jsondata.getString("actualMileage"); //实际公里数
			boolean flag = jsondata.has("carType");//判断是否存在车辆类型
			String carType= "";
			if(flag==true){
				 carType = jsondata.getString("carType"); //车辆类型
			}else{
				carType="0";//没有车辆类型默认最低档的车辆
			}
			
			checkToken(token);//验证token
			//获取参数
			List<AppCharteredPriceEntity> appCharteredPricelist =appCharteredService.getPackagePrice(packageId, carType, charteredType, actualMileage);
			
			if (appCharteredPricelist != null && appCharteredPricelist.size() > 0) {
				returnJsonObj.put("data", appCharteredPricelist);
			} else {
				returnJsonObj.put("data", "");
			}
			
			statusCode = AppGlobals.APP_SUCCESS;
			msg = AppGlobals.APP_SUCCESS_MSG;

		} catch (ParameterException e) {
			statusCode = e.getCode();
			msg = e.getErrorMessage();
			logger.error(e.getErrorMessage());
		} catch (Exception e) {
			statusCode = AppGlobals.SYSTEM_ERROR;
			msg = AppGlobals.SYSTEM_ERROR_MSG;
			e.printStackTrace();
		}

		returnJsonObj.put("msg", msg);
		returnJsonObj.put("code", statusCode);

		responseOutWrite(response, returnJsonObj);
	}
	
	/**
	 * 获取包车收费详情
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "getChargesDetails")
	public void getChargesDetails(HttpServletRequest request, HttpServletResponse response) {
		AppUtil.responseUTF8(response);
		JSONObject returnJsonObj = new JSONObject();
		String msg = "";
		String statusCode = "";

		try {
			String param = AppUtil.inputToStr(request);
			System.out.println("tokenLogin    前端传递参数：" + param);
			//验证参数
			JSONObject jsondata = checkParam(param);
			String token = jsondata.getString("token");
			String departCode = jsondata.getString("departCode"); //包车价格id
			checkToken(token);//验证token
			
			List<AppCharteredPackageEntity> appCharteredPricelist =appCharteredService.getChargesDetails(departCode);
			
			//获取参数
			if (appCharteredPricelist != null &  appCharteredPricelist.size() > 0) {
				returnJsonObj.put("data", appCharteredPricelist);
			} else {
				returnJsonObj.put("data", "");
			}
			
			statusCode = AppGlobals.APP_SUCCESS;
			msg = AppGlobals.APP_SUCCESS_MSG;

		} catch (ParameterException e) {
			statusCode = e.getCode();
			msg = e.getErrorMessage();
			logger.error(e.getErrorMessage());
		} catch (Exception e) {
			statusCode = AppGlobals.SYSTEM_ERROR;
			msg = AppGlobals.SYSTEM_ERROR_MSG;
			e.printStackTrace();
		}

		returnJsonObj.put("msg", msg);
		returnJsonObj.put("code", statusCode);

		responseOutWrite(response, returnJsonObj);
	}
}

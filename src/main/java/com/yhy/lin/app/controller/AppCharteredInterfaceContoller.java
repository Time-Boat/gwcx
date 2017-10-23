package com.yhy.lin.app.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yhy.lin.app.entity.AppStationInfoEntity;
import com.yhy.lin.app.exception.ParameterException;
import com.yhy.lin.app.service.AppCharteredInterfaceService;
import com.yhy.lin.app.util.AppGlobals;
import com.yhy.lin.app.util.AppUtil;

import net.sf.json.JSONObject;

/**
* Description : app包车接口
* @author Timer
* @date 2017年10月19日 下午4:08:28
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
	
	
}

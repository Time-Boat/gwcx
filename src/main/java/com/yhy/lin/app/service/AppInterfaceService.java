package com.yhy.lin.app.service;

import java.math.BigDecimal;
import java.util.List;

import org.jeecgframework.core.common.service.CommonService;

import com.yhy.lin.app.entity.AppCheckTicket;
import com.yhy.lin.app.entity.AppLineStationInfoEntity;
import com.yhy.lin.app.entity.AppMessageListEntity;
import com.yhy.lin.app.entity.AppStationInfoEntity;
import com.yhy.lin.app.entity.AppUserOrderDetailEntity;
import com.yhy.lin.app.entity.AppUserOrderEntity;
import com.yhy.lin.entity.CarTSTypeLineEntity;
import com.yhy.lin.entity.DealerOrderUserStationEntity;
import com.yhy.lin.entity.TransferorderEntity;

/**
* Description : 
* @author Administrator
* @date 2017年6月1日 下午4:37:35
*/
public interface AppInterfaceService extends CommonService{

	/** 保存订单信息，记录常用站点，添加到消息中心 
	 * @param station */
	String saveOrder(TransferorderEntity t, String orderPrefix, String commonAddrId, DealerOrderUserStationEntity station);

	/** 获取机场站点或者火车站站点信息 */
	List<AppStationInfoEntity> getPTStation(String serveType, String cityId, String userType);

	/** 获取线路站点信息 
	 * @param userType */
	void getLinebyStation(String serveType, String cityId, String stationId, String userId, String likeStation,
			List<AppLineStationInfoEntity> lList, List<AppStationInfoEntity> cList,
			List<AppStationInfoEntity> stationList, String userType);

	/** 获取用户订单列表 */
	List<AppUserOrderEntity> getUserOrders(String userId, String orderStatus, String pageNo, String maxPageItem);

	/** 获取用户订单详情页 */
	AppUserOrderDetailEntity getOrderDetailById(String orderId, String userType);

	/** 获取验票列表页数据 */
	List<AppCheckTicket> getTicketListById(String userId, String pageNo, String maxPageItem);

	/** 获取消息通知列表 */
	List<AppMessageListEntity> getMessageListById(String userId, String pageNo, String maxPageItem);

	/** 根据人数计算总价 
	 * @param discount */
	String getCarTypePrice(String sumPeople, String lineId, String phone, BigDecimal discount);

	
	
	
}

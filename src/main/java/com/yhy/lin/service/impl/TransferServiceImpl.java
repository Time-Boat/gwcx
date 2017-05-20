package com.yhy.lin.service.impl;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.dao.jdbc.JdbcDao;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yhy.lin.entity.TransferorderEntity;
import com.yhy.lin.entity.TransferorderView;
import com.yhy.lin.service.TransferServiceI;

import net.sf.json.JSONObject;

@Service("TransferServiceI")
@Transactional
public class TransferServiceImpl extends CommonServiceImpl implements TransferServiceI{
	@Autowired
	private JdbcDao jdbcDao;
	
	@Override
	public JSONObject getDatagrid(TransferorderEntity transferorder,DataGrid dataGrid,
			String fc_begin,String fc_end,String ddTime_begin,String ddTime_end){
		String sqlWhere = getWhere(transferorder,fc_begin,fc_end,ddTime_begin,ddTime_end);
		StringBuffer sql = new StringBuffer();
		// 取出总数据条数（为了分页处理, 如果不用分页，取iCount值的这个处理可以不要）
		String sqlCnt = "select count(*) from transferorder a left join order_linecardiver b on a.id = b .id left join car_info c on b.licencePlateId =c.id "
				+ " left join driversinfo d on b.driverId =d.id  "
				+ " left join lineinfo l on l.id = a.line_id left join t_s_depart t on t.id = l.departId " ;
		if (!sqlWhere.isEmpty()) {
			sqlCnt += sqlWhere;
		}
		Long iCount = getCountForJdbcParam(sqlCnt, null);
		sql.append("select t.org_code,a.id,a.order_id,a.order_type,a.order_status,a.order_flightnumber,a.order_starting_station_id,a.order_terminus_station_id,");
		sql.append("a.order_startime,a.order_expectedarrival,a.order_unitprice,a.order_numbers,a.order_paytype,a.order_contactsname,");
		sql.append("a.order_contactsmobile,a.order_paystatus,a.order_trainnumber,a.order_numberPeople,a.order_totalPrice,d.name,d.phoneNumber,c.licence_plate,a.applicationTime,a.line_id,a.line_name ");
		sql.append(" from transferorder a left join order_linecardiver b on a.id = b .id left join car_info c on b.licencePlateId =c.id left join driversinfo d on b.driverId =d.id"
				+ " left join lineinfo l on l.id = a.line_id left join t_s_depart t on t.id = l.departId ");
		if (!sqlWhere.isEmpty()){
			sql.append(sqlWhere);
		}
		
		System.out.println(sql.toString());
		List<Map<String, Object>> mapList = findForJdbc(sql.toString(), dataGrid.getPage(), dataGrid.getRows());
		// 将结果集转换成页面上对应的数据集
		Db2Page[] db2Pages = {
				 new Db2Page("id")
				,new Db2Page("orderId", "order_id", null)
				,new Db2Page("orderType", "order_type", null)
				,new Db2Page("orderStatus", "order_status", null)
				,new Db2Page("orderFlightnumber", "order_flightnumber", null)
				,new Db2Page("orderStartingstation", "order_starting_station_id", null)
				,new Db2Page("orderTerminusstation", "order_terminus_station_id", null)
				,new Db2Page("orderStartime", "order_startime", null)
				,new Db2Page("orderExpectedarrival", "order_expectedarrival", null)
				,new Db2Page("orderUnitprice", "order_unitprice", null)
				,new Db2Page("orderNumbers", "order_numbers", null)
				,new Db2Page("orderPaytype", "order_paytype", null)
				,new Db2Page("orderContactsname", "order_contactsname", null)
				,new Db2Page("orderContactsmobile", "order_contactsmobile", null)
				,new Db2Page("orderPaystatus", "order_paystatus", null)
				,new Db2Page("orderTrainnumber", "order_trainnumber", null)
				,new Db2Page("orderNumberPeople", "order_numberPeople", null)	
				,new Db2Page("orderTotalPrice", "order_totalPrice", null)
				,new Db2Page("name", "name", null)
				,new Db2Page("phoneNumber", "phoneNumber", null)
				,new Db2Page("licencePlate", "licence_plate", null)
				,new Db2Page("applicationTime", "applicationTime", null)
				,new Db2Page("lineId", "line_id", null)
				,new Db2Page("lineName", "line_name", null)

				
		};
		JSONObject jObject = getJsonDatagridEasyUI(mapList, iCount.intValue(), db2Pages);
		return jObject;
	}
	
	public String  getWhere(TransferorderEntity transferorder,String fc_begin,String fc_end,String ddTime_begin,String ddTime_end){
		
		String orgCode = ResourceUtil.getSessionUserName().getCurrentDepart().getOrgCode();
		//添加了权限
		StringBuffer sql = new StringBuffer(" where 1=1 and t.org_code like '" + orgCode + "%' ");
		
		//发车时间
		if(StringUtil.isNotEmpty(fc_begin)&&StringUtil.isNotEmpty(fc_end)){
			sql.append(" and a.order_startime between '"+fc_begin+"' and '"+fc_end+"'");
		}
		//预计到达时间
		if(StringUtil.isNotEmpty(ddTime_begin)&&StringUtil.isNotEmpty(ddTime_end)){
			sql.append(" and a.order_expectedarrival between '"+ddTime_begin+"' and '"+ddTime_end+"'");
		}
		//订单编号
		if(StringUtil.isNotEmpty(transferorder.getOrderId())){
			sql.append(" and  a.order_id like '%"+transferorder.getOrderId()+"%'");
		}
		//订单类型
		if(StringUtil.isNotEmpty(transferorder.getOrderType())){
			sql.append(" and  a.order_type ='"+transferorder.getOrderType()+"'");
		}
		//订单状态
		if(StringUtil.isNotEmpty(transferorder.getOrderStatus())){
			sql.append(" and  a.order_status ='"+transferorder.getOrderStatus()+"'");
		}
		//起点站id
		if(StringUtil.isNotEmpty(transferorder.getOrderStartingStationId())){
			sql.append(" and  a.order_starting_station_id = '"+transferorder.getOrderStartingStationId()+"'");
		}
		//终点站id
		if(StringUtil.isNotEmpty(transferorder.getOrderTerminusStationId())){
			sql.append(" and  a.order_terminus_station_id = '"+transferorder.getOrderTerminusStationId()+"'");
		}
		
		//申请人
		if(StringUtil.isNotEmpty(transferorder.getOrderContactsname())){
			sql.append(" and  a.order_contactsname like '%"+transferorder.getOrderContactsname()+"%'");
		}
		sql.append(" order by a.order_startime asc ");
		
		return sql.toString();
	}
	
	//根据id查询接送机的详细信息
	@Override
	public TransferorderView getDetail(String id){
		StringBuffer sql = new  StringBuffer();
		/*sql.append("select a.id,a.order_id as orderId,a.order_type as orderType,a.order_status as orderStatus,a.order_flightnumber as orderFlightnumber,a.order_starting_station_id,a.order_terminus_station_id,");
		sql.append("a.order_startime as orderStartime,a.order_expectedarrival as orderExpectedarrival,a.order_unitprice as orderUnitprice,a.order_numbers as orderNumbers,a.order_paytype as orderPaytype,a.order_contactsname as orderContactsname,");
		sql.append("a.order_contactsmobile as orderContactsmobile,a.order_paystatus as orderPaystatus,a.order_trainnumber as orderTrainnumber,a.order_numberPeople as orderNumberPeople,a.order_totalPrice as orderTotalPrice,d.name as driverName,d.phoneNumber as driverMobile,c.licence_plate as licencePlate,c.status as CarStatus,a.applicationTime ");
		sql.append(" from transferorder a left join order_linecardiver b on a.id = b .id left join car_info c on b.licencePlateId =c.id left join driversinfo d on b.driverId =d.id ");
		*/
		sql.append("select a.id,a.order_id,a.order_type,a.order_status,a.order_flightnumber,a.order_starting_station_id,a.order_terminus_station_id,");
		sql.append("a.order_startime,a.order_expectedarrival,a.order_unitprice,a.order_numbers,a.order_paytype,a.order_contactsname,");
		sql.append("a.order_contactsmobile,a.order_paystatus,a.order_trainnumber,a.order_numberPeople,a.order_totalPrice,d.name,d.phoneNumber,c.licence_plate,c.status,a.applicationTime ");
		sql.append(" from transferorder a left join order_linecardiver b on a.id = b .id left join car_info c on b.licencePlateId =c.id left join driversinfo d on b.driverId =d.id ");

		sql.append(" where a.id='"+id+"'");
		List<Object[]> list = findListbySql(sql.toString());
		TransferorderView  transferorderView = new TransferorderView();
		if(list.size()>0){
			Object [] obj = list.get(0);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try{
				transferorderView.setId(String.valueOf(obj[0]));
				transferorderView.setOrderId(String.valueOf(obj[1]));
				transferorderView.setOrderType(String.valueOf(obj[2]));
				transferorderView.setOrderStatus(String.valueOf(obj[3]));
				transferorderView.setOrderFlightnumber(String.valueOf(obj[4]));
				transferorderView.setOrderStartingstation(String.valueOf(obj[5]));
				transferorderView.setOrderTerminusstation(String.valueOf(obj[6]));
				if(obj[7]!=null){
					transferorderView.setOrderStartime(sdf.parse(obj[7].toString()));
				}
				transferorderView.setOrderExpectedarrival(String.valueOf(obj[8]));
				transferorderView.setOrderUnitprice(String.valueOf(obj[9]));
				transferorderView.setOrderNumbers(String.valueOf(obj[10]));
				transferorderView.setOrderPaytype(String.valueOf(obj[11]));
				transferorderView.setOrderContactsname(String.valueOf(obj[12]));
				transferorderView.setOrderContactsmobile(String.valueOf(obj[13]));
				transferorderView.setOrderPaystatus(String.valueOf(obj[14]));
				transferorderView.setOrderTrainnumber(String.valueOf(obj[15]));
				transferorderView.setOrderNumberPeople(String.valueOf(obj[16]));
				transferorderView.setOrderTotalPrice(String.valueOf(obj[17]));
				transferorderView.setDriverName(String.valueOf(obj[18]));
				transferorderView.setDriverMobile(String.valueOf(obj[19]));
				transferorderView.setLicencePlate(String.valueOf(obj[20]));
				transferorderView.setCarStatus(String.valueOf(obj[21]));
				if(obj[22]!=null){
					transferorderView.setApplicationTime(sdf.parse(obj[22].toString()));
				}
			}catch (Exception e) {	
			}
		}
		return transferorderView;
	}
	
}

package org.jeecgframework.web.demo.service.impl.test;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yhy.lin.app.util.AppGlobals;
import com.yhy.lin.app.util.SendMessageUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.demo.service.test.OrderTimeServiceI;
import org.jeecgframework.web.system.service.SystemService;

@Service("orderTimeService")
public class OrderTimeServiceImpl extends CommonServiceImpl implements OrderTimeServiceI {

	@Autowired
	private SystemService systemService;
	
	@Override
	public void work() {
		
		JSONObject jsonObj = new JSONObject();
		JSONObject jsonObj1 = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		JSONArray jsonArray1 = new JSONArray();
		
		String sql = "select * from (select COUNT(l.createUserId),ts.mobilePhone,a.order_type from transferorder a left join order_linecardiver b on a.id = b .id left join "
				+ "car_info c on b.licencePlateId =c.id left join driversinfo d on b.driverId =d.id left join lineinfo l on l.id = a.line_id left join t_s_depart t on t.id = "
				+ "l.departId LEFT JOIN t_s_base_user u on u.ID=l.createUserId LEFT JOIN t_s_user ts on ts.id=u.ID LEFT JOIN t_s_role_user ru on ru.userid=ts.id LEFT JOIN "
				+ "t_s_role tr on tr.ID=ru.roleid where a.order_status='1' and tr.rolecode='" + AppGlobals.OPERATION_MANAGER + "' and a.order_type in('2','3') GROUP BY l.createUserId UNION select "
				+ "COUNT(l.createUserId),ts.mobilePhone,a.order_type from transferorder a left join order_linecardiver b on a.id = b.id left join car_info c on b.licencePlateId "
				+ "=c.id left join driversinfo d on b.driverId =d.id left join lineinfo l on l.id = a.line_id left join t_s_depart t on t.id = l.departId LEFT JOIN "
				+ "t_s_base_user u on u.ID=l.createUserId LEFT JOIN t_s_user ts on ts.id=u.ID LEFT JOIN t_s_role_user ru on ru.userid=ts.id LEFT JOIN t_s_role tr "
				+ "on tr.ID=ru.roleid where a.order_status='1' and tr.rolecode='" + AppGlobals.OPERATION_MANAGER + "' and a.order_type in('4','5') GROUP BY l.createUserId) gh ORDER BY gh.mobilePhone";
		
		List<Object> list = this.systemService.findListbySql(sql);
		
		BigInteger num;
		
		String mobile="";
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Object[] ob = (Object[]) list.get(i);
				 num = (BigInteger) ob[0];
				 mobile = ob[1]+"";
				 int oderType = (int) ob[2];
				 jsonObj.put("num", num.toString());
				 jsonObj.put("mobile", mobile);
				 jsonObj.put("oderType", oderType+"");
				 jsonArray.add(jsonObj);
			}
			
		}
		int ordair = 0;
		int ordtr = 0;
		String m="";
		for (int i = 0; i < jsonArray.size(); i++) {
			m= jsonArray.getJSONObject(i).getString("mobile");
			if(i>=1){
				String m2= jsonArray.getJSONObject(i-1).getString("mobile");
				if(!m.equals(m2)){
					ordair=0;
					ordtr = 0;
				}
			}
			
			String mnum= jsonArray.getJSONObject(i).getString("num");
			String moderType= jsonArray.getJSONObject(i).getString("oderType");
			int mt = Integer.parseInt(moderType);
			
			if(mt==4 || mt==5){
				ordair=Integer.parseInt(mnum);
			}else if(mt==2 || mt==3){
				ordtr=Integer.parseInt(mnum);
			}
			
			jsonObj1.put("mobile", m);
			jsonObj1.put("ordair", ordair+"");
			jsonObj1.put("ordtr", ordtr+"");
			jsonArray1.add(jsonObj1.toString());
		}
		
		for (int i = 1; i < jsonArray1.size(); i++) {
			String mo = jsonArray1.getJSONObject(i).getString("mobile");
			String mo1 = jsonArray1.getJSONObject(i-1).getString("mobile");
			if(mo1.equals(mo)){
				jsonArray1.remove(i-1);
				i--;
			}
		}
		for (int i = 0; i < jsonArray1.size(); i++) {
			String mobiles = jsonArray1.getJSONObject(i).getString("mobile");
			String ordairs = jsonArray1.getJSONObject(i).getString("ordair");
			String ordtrs = jsonArray1.getJSONObject(i).getString("ordtr");
			if (StringUtil.isNotEmpty(ordairs) && StringUtil.isNotEmpty(ordtrs) && !ordairs.equals("0") && !ordtrs.equals("0")) {
				SendMessageUtil.sendMessage(mobiles, new String[] { "ordair","ordtr" }, new String[] {ordairs,ordtrs},
						SendMessageUtil.TEMPLATE_ARRANGE_ORDER , SendMessageUtil.TEMPLATE_SMS_CODE_SIGN_ORDER);
			}else if(StringUtil.isNotEmpty(ordairs) && ordtrs.equals("0")){
				SendMessageUtil.sendMessage(mobiles, new String[] { "ordair" }, new String[] {ordairs},
						SendMessageUtil.TEMPLATE_ARRANGE_AIRORDER , SendMessageUtil.TEMPLATE_SMS_CODE_SIGN_ORDER);
			}else if(StringUtil.isNotEmpty(ordtrs) && ordairs.equals("0")){
				SendMessageUtil.sendMessage(mobiles, new String[] { "ordtr" }, new String[] {ordtrs},
						SendMessageUtil.TEMPLATE_ARRANGE_TRORDER , SendMessageUtil.TEMPLATE_SMS_CODE_SIGN_ORDER);
			}
		}
	}
}

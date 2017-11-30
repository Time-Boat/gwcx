package com.yhy.lin.app.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.core.util.ResourceUtil;

import com.yhy.lin.entity.NotificationRecordEntity;
import com.yhy.lin.entity.NotificationUserMiddleEntity;

/**
* Description : 保存系统消息记录
* @author Administrator
* @date 2017年11月28日 上午10:56:26
*/
public class SystemMessage {
	
	private static SystemMessage systemMassage = null;
	
	private SystemMessage(){}
	
	public static SystemMessage getInstance(){
		synchronized(APIHttpClient.class){
			if(systemMassage == null){
				systemMassage = new SystemMessage();
			}
		}
		return systemMassage;
	}
	
	//因为这边持有了对对象systemService的引用，会不会导致controller那边的systemService不会被释放掉，而占用内存
	//整个过程结束时，工具类对象本身并不会持有传入对象的引用
	//就是说不用全局变量去引用这个形参指定的变量应该不会有问题
	//为了防止异步操作，用户名被改变，加个锁
	public synchronized void saveMessage(CommonService commonService, String title, String content, String[] target, String[] nType){
		
		String userId = ResourceUtil.getSessionUserName().getId();
		
		Date d = AppUtil.getDate(); 
		
		StringBuilder t = new StringBuilder();
		StringBuilder tSql = new StringBuilder();
		for(String temp : target){
			t.append(temp);
			t.append(",");
			
			tSql.append("'");
			tSql.append(temp);
			tSql.append("',");
		}
		
		StringBuilder nt = new StringBuilder();
		for(String temp1 : nType){
			nt.append(temp1);
			nt.append(",");
		}
		
		NotificationRecordEntity n = new NotificationRecordEntity();
		n.setTitle(title);
		n.setContent(content);
		n.setCreateUserId(userId);
		n.setCreateTime(d);
		n.setSendTime(d);
		n.setTarget(t.length() > 0 ? t.deleteCharAt(t.length() - 1).toString() : "");
		n.setNType(nt.length() > 0 ? nt.deleteCharAt(nt.length() - 1).toString() : "");
		
		commonService.save(n);
		
		String roles = tSql.length() > 0 ? tSql.deleteCharAt(tSql.length() - 1).toString() : "";
		
		List<String> list = commonService.findListbySql(
				" select u.id from t_s_role r left join t_s_role_user ru on r.id = ru.roleid left join t_s_base_user u on ru.userid = u.id "
				+ " where r.rolecode in (" + roles + ") ");
		
		List<NotificationUserMiddleEntity> nList = new ArrayList<>();
		for(String uId : list){
			NotificationUserMiddleEntity num = new NotificationUserMiddleEntity();
			num.setUserId(uId);
			num.setRecordId(n.getId());
			nList.add(num);
		}
		commonService.saveAllEntitie(nList);
		
	}
	
	
	
	public static void main(String[] args) {
	}
	
	
}

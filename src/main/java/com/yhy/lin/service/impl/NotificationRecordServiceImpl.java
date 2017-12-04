package com.yhy.lin.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yhy.lin.entity.NotificationRecordEntity;
import com.yhy.lin.service.NotificationRecordServiceI;

import net.sf.json.JSONObject;

import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl.Db2Page;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl.IMyDataExchanger;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;

@Service("notificationRecordService")
@Transactional
public class NotificationRecordServiceImpl extends CommonServiceImpl implements NotificationRecordServiceI {

	@Override
	public JSONObject getDatagrid(DataGrid dataGrid, NotificationRecordEntity notificationRecord) {
		
		String userId = ResourceUtil.getSessionUserName().getId();
		
		// 取出总数据条数（为了分页处理, 如果不用分页，取iCount值的这个处理可以不要）
		String sqlCnt = " select count(*) from notification_record nr left join notification_user_middle nu on nr.id = nu.record_id "
				+ " where nu.user_id = ? ";
//		if (!sqlWhere.isEmpty()) {
//			sqlCnt += sqlWhere;
//		}
		Long iCount = getCountForJdbcParam(sqlCnt, new Object[]{userId}); //userId
		// 取出当前页的数据 
		String sql = " select n.id,n.send_time,n.title,n.content,n.n_type,n.create_time,n.status,n.remark,u.username "   //,n.target
				+ " from notification_record n left join notification_user_middle nu on n.id = nu.record_id left join t_s_base_user u on n.create_user_id = u.id "
				+ " where nu.user_id = ? ";
//				if (!sqlWhere.isEmpty()) {
//					sql += sqlWhere;
//				}
		
		//排序   大小写问题报错
		/*if(StringUtil.isNotEmpty(dataGrid.getSort()) && StringUtil.isNotEmpty(dataGrid.getOrder()) ){
			sql += " order by " + dataGrid.getSort() + " " + dataGrid.getOrder();
		}*/
		List<Map<String, Object>> mapList = findForJdbcParam(sql, dataGrid.getPage(), dataGrid.getRows(), userId);
		// 将结果集转换成页面上对应的数据集
		Db2Page[] db2Pages = {
				new Db2Page("id","id")
				,new Db2Page("title", "title", null)
				,new Db2Page("content", "content", null)
				//,new Db2Page("target", "target", null)
				,new Db2Page("nType", "n_type", new MyDataExchangerNType2Ch())
				,new Db2Page("userName", "username", null)
				,new Db2Page("createTime", "create_time", null)
				,new Db2Page("sendTime", "send_time", null)
				,new Db2Page("remark", "remark", null)
		};
		JSONObject jObject = getJsonDatagridEasyUI(mapList, iCount.intValue(), db2Pages);
		return jObject;
	}
	
	private class MyDataExchangerNType2Ch implements IMyDataExchanger {
		@Override
		public Object exchange(Object value) {
			
			StringBuffer bName = new StringBuffer();
			
			if(!StringUtil.isNotEmpty(value)){
				return "";
			}
			String v = value + "";
			if (v.contains("1")) {
				bName.append("邮件,");
			}
			if (v.contains("2")) {
				bName.append("站内信,");
			}
			if(bName.length() > 0){
				return bName.deleteCharAt(bName.length() -1 ).toString();
			}else{
				return "";
			}
		}
	}
	
}
package com.yhy.lin.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yhy.lin.app.util.SystemMessage;
import com.yhy.lin.entity.NotificationModelEntity;
import com.yhy.lin.service.NotificationModelServiceI;

import net.sf.json.JSONObject;

import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.StringUtil;

@Service("notificationModelService")
@Transactional
public class NotificationModelServiceImpl extends CommonServiceImpl implements NotificationModelServiceI {

	@Override
	public JSONObject getDatagrid(DataGrid dataGrid, NotificationModelEntity notificationModel) {
		//通过AOP代理调用getWhere方法即可走事务切面              必须得通过接口调用getWhere
		//String sqlWhere = getWhere(dealerInfo, username, departname);
		// 取出总数据条数（为了分页处理, 如果不用分页，取iCount值的这个处理可以不要）
		String sqlCnt = "select count(*) from notification_model n left join t_s_base_user u on n.create_user_id = u.id ";
//		if (!sqlWhere.isEmpty()) {
//			sqlCnt += sqlWhere;
//		}
		Long iCount = getCountForJdbcParam(sqlCnt, null);
		// 取出当前页的数据 
		String sql = "select n.id,n.title,n.content,n.n_type,n.create_time,n.midify_time,n.status,n.remark,u.username "   //,n.target
				+ " from notification_model n left join t_s_base_user u on n.create_user_id = u.id ";
//		if (!sqlWhere.isEmpty()) {
//			sql += sqlWhere;
//		}
		
		//排序   大小写问题报错
		/*if(StringUtil.isNotEmpty(dataGrid.getSort()) && StringUtil.isNotEmpty(dataGrid.getOrder()) ){
			sql += " order by " + dataGrid.getSort() + " " + dataGrid.getOrder();
		}*/
		List<Map<String, Object>> mapList = findForJdbc(sql, dataGrid.getPage(), dataGrid.getRows());
		// 将结果集转换成页面上对应的数据集
		Db2Page[] db2Pages = {
				new Db2Page("id","id")
				,new Db2Page("title", "title", null)
				,new Db2Page("content", "content", null)
				//,new Db2Page("target", "target", null)
				,new Db2Page("nType", "n_type", new MyDataExchangerNType2Ch())
				,new Db2Page("userName", "username", null)
				,new Db2Page("createTime", "create_time", null)
				,new Db2Page("midifyTime", "midify_time", null)
				,new Db2Page("status", "status", null)
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
				bName.append("短信,");
			}
			if (v.contains("3")) {
				bName.append("站内信,");
			} 
			if (v.contains("4")) {
				bName.append("消息中心,");
			}
			if(bName.length() > 0){
				return bName.deleteCharAt(bName.length() -1 ).toString();
			}else{
				return "";
			}
		}
	}
	
}
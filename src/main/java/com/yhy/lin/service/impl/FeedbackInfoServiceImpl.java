package com.yhy.lin.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yhy.lin.entity.FeedbackInfoEntity;
import com.yhy.lin.service.FeedbackInfoServiceI;

import net.sf.json.JSONObject;

import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.StringUtil;

@Service("feedbackInfoService")
@Transactional
public class FeedbackInfoServiceImpl extends CommonServiceImpl implements FeedbackInfoServiceI {
	
	public JSONObject getDatagrid(FeedbackInfoEntity feedbackInfo, DataGrid dataGrid,String realName,String phone){
		
		String sqlWhere = getWhere(feedbackInfo,realName,phone);
		
		// 取出总数据条数（为了分页处理, 如果不用分页，取iCount值的这个处理可以不要）
		String sqlCnt = "select COUNT(*) from feedback f left join car_customer c on f.user_id=c.id";
		
		if (!sqlWhere.isEmpty()) {
			sqlCnt += " where " + sqlWhere;
		}
		
		Long iCount = getCountForJdbcParam(sqlCnt, null);
		
		String sql= "select f.id,f.user_id,f.content,f.create_time,f.status,f.remark,c.real_name,c.phone from feedback f LEFT JOIN car_customer c on f.user_id=c.id ";
		if (!sqlWhere.isEmpty()) {
			sql += " where" + sqlWhere;
		}
		List<Map<String, Object>> mapList = findForJdbc(sql.toString(), dataGrid.getPage(), dataGrid.getRows());
		// 将结果集转换成页面上对应的数据集
				Db2Page[] db2Pages = { 
						new Db2Page("id"), 
						new Db2Page("userId", "user_id", null),
						new Db2Page("content", "content", null), 
						new Db2Page("createTime", "create_time", null),
						new Db2Page("status", "status", null),
						new Db2Page("remark", "remark", null),
						new Db2Page("realName", "real_name", null),
						new Db2Page("phone", "phone", null)

				};
		JSONObject jObject = getJsonDatagridEasyUI(mapList, iCount.intValue(), db2Pages);
		return jObject; 
	}
	
	public String getWhere(FeedbackInfoEntity feedbackInfo,String realName,String phone) {
		StringBuffer sql = new StringBuffer();
		
		if (StringUtil.isNotEmpty(realName)) {
			
			sql.append(" c.real_name like '%"+realName+"%'");
			if (StringUtil.isNotEmpty(phone)) {
				sql.append(" and  c.phone like '%" +phone + "%' ");
			}
		}else{
			if (StringUtil.isNotEmpty(phone)) {
				sql.append("  c.phone like '%" +phone + "%' ");
			}
		}
		
		
		return sql.toString();
	}
}
package com.yhy.lin.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yhy.lin.service.LineArrangeViewServiceI;

import net.sf.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl.IMyDataExchanger;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;

@Service("lineArrangeViewService")
@Transactional
public class LineArrangeViewServiceImpl extends CommonServiceImpl implements LineArrangeViewServiceI {

	
	@Override
	public JSONObject getDatagrid(DataGrid dataGrid) {
		StringBuffer queryCondition = new StringBuffer(" where 1=1 ");
	    
//		if(StringUtil.isNotEmpty(name)){
//			queryCondition.append(" and name = '"+name+"' ");
//		}
//		
//		if(StringUtil.isNotEmpty(companyId)){
//			queryCondition.append(" and company_id = '"+companyId+"' ");
//		}else{
//			queryCondition.append(" and company_id like '" + orgCode +"%' ");
//		}
//		
//		if(StringUtil.isNotEmpty(depart)){
//			queryCondition.append(" and depart = '"+depart+"' ");
//		}
//		
//		if(StringUtil.isNotEmpty(job)){
//			queryCondition.append(" and job = '"+job+"' ");
//		}
		
		String  orgCode = ResourceUtil.getSessionUserName().getCurrentDepart().getOrgCode();
		if(StringUtil.isNotEmpty(orgCode)){
			queryCondition.append(" and org_code like '"+orgCode+"%'");
		}
		
		// 取出总数据条数（为了分页处理, 如果不用分页，取iCount值的这个处理可以不要）
		String sqlCnt = "select count(*) from lineinfo_line_arrange_view  " + queryCondition.toString();
		Long iCount = getCountForJdbcParam(sqlCnt, null);
		
		// 取出当前页的数据 
		StringBuffer sql = new StringBuffer();
	    sql.append("select * from lineinfo_line_arrange_view  " + queryCondition.toString()  );
		
		System.out.println(sql.toString());
		List<Map<String, Object>> mapList = findForJdbc(sql.toString() + " order by period_start desc ", dataGrid.getPage(), dataGrid.getRows());
		// 将结果集转换成页面上对应的数据集
					Db2Page[] db2Pages = {
							new Db2Page("id", "id", new LvMyDataExchangerId2Blank())
							,new Db2Page("lineId", "lineId")
							,new Db2Page("departDate", "depart_date")
							,new Db2Page("name", "name")
							,new Db2Page("director", "director")
							,new Db2Page("remark", "remark")
							,new Db2Page("periodStart", "period_start")
							,new Db2Page("periodEnd", "period_end")
							,new Db2Page("ticketPrice", "ticket_price")
					};
		JSONObject jObject = getJsonDatagridEasyUI(mapList, iCount.intValue(), db2Pages);
		return jObject;
	}

	@Override
	public String getSaleTicketCount(String lineId) {
		
		//List<SaleTicketEntity> list = lineArrangeViewService.findByQueryString("from SaleTicketEntity where line_id = '" + id + "' and status = '0' group by ticket_date");
		StringBuffer sbf = new StringBuffer("[");
		
		Connection con = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		try {
			con = ((SessionFactoryImplementor)getSession().getSessionFactory()).getConnectionProvider().getConnection();
			String sql = "select ticket_date as tDate,count(ticket_date) as count from sale_ticket where line_id = ? and status = '0' group by ticket_date";
			stat = con.prepareStatement(sql);
			stat.setString(1, lineId);
			rs = stat.executeQuery();
			while(rs.next()){
				sbf.append("{");
				sbf.append("'tDate':'" + rs.getString("tDate") + "',");
				sbf.append("'count':'" + rs.getString("count") + "'");
				sbf.append("},");
			}
			if(sbf.length()>1){
				sbf.deleteCharAt(sbf.length()-1);
			}
			sbf.append("]");
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(con!=null)
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if(stat!=null)
				try {
					stat.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if(rs!=null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return sbf.toString();
		
	}
	
	//在id为空的情况下操作下的按钮不显示，这里进行转换之后在编辑的时候需要判断一下
	private class LvMyDataExchangerId2Blank implements IMyDataExchanger {
		@Override
		public Object exchange(Object value) {
			if (value == null) {
				return "lvId";
			} else {
				return value;
			}
		}
	}
}
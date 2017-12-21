package org.jeecgframework.core.common.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.jeecgframework.core.common.dao.ICommonDao;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.hibernate.qbc.HqlQuery;
import org.jeecgframework.core.common.hibernate.qbc.PageList;
import org.jeecgframework.core.common.model.common.DBTable;
import org.jeecgframework.core.common.model.common.UploadFile;
import org.jeecgframework.core.common.model.json.ComboTree;
import org.jeecgframework.core.common.model.json.DataGridReturn;
import org.jeecgframework.core.common.model.json.ImportFile;
import org.jeecgframework.core.common.model.json.TreeGrid;
import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.vo.datatable.DataTableReturn;
import org.jeecgframework.tag.vo.easyui.Autocomplete;
import org.jeecgframework.tag.vo.easyui.ComboTreeModel;
import org.jeecgframework.tag.vo.easyui.TreeGridModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.sf.json.JSONObject;


@Service("commonService")
@Transactional
public class CommonServiceImpl implements CommonService {
	public ICommonDao commonDao = null;
	
	/**
	 * 获取所有数据库表
	 * 
	 * @return
	 */
	@Override
	public List<DBTable> getAllDbTableName() {
		return commonDao.getAllDbTableName();
	}

	@Override
	public Integer getAllDbTableSize() {
		return commonDao.getAllDbTableSize();
	}

	@Resource
	public void setCommonDao(ICommonDao commonDao) {
		this.commonDao = commonDao;
	}

	
	@Override
	public <T> Serializable save(T entity) {
		return commonDao.save(entity);
	}

	
	@Override
	public <T> void saveOrUpdate(T entity) {
		commonDao.saveOrUpdate(entity);

	}

	
	@Override
	public <T> void delete(T entity) {
		commonDao.delete(entity);

	}

	/**
	 * 删除实体集合
	 * 
	 * @param <T>
	 * @param entities
	 */
	@Override
	public <T> void deleteAllEntitie(Collection<T> entities) {
		commonDao.deleteAllEntitie(entities);
	}
	
	/**
	 * 更新实体集合，用于批量逻辑删除 create linj
	 * @param <T>
	 * @param entities
	 */
	@Override
	public <T> void updateAllEntitie(Collection<T> entities){
		commonDao.updateAllEntitie(entities);
	}
	/**
	 * 保存实体集合，用于批量保存 create linj
	 * @param <T>
	 * @param entities
	 */
	@Override
	public <T> void saveAllEntitie(Collection<T> entities){
		commonDao.saveAllEntitie(entities);
	}
	
	

	/**
	 * 根据实体名获取对象
	 */
	@Override
	public <T> T get(Class<T> class1, Serializable id) {
		return commonDao.get(class1, id);
	}

	/**
	 * 根据实体名返回全部对象
	 * 
	 * @param <T>
	 * @param hql
	 * @param size
	 * @return
	 */
	@Override
	public <T> List<T> getList(Class clas) {
		return commonDao.loadAll(clas);
	}

	/**
	 * 根据实体名获取对象
	 */
	@Override
	public <T> T getEntity(Class entityName, Serializable id) {
		return commonDao.getEntity(entityName, id);
	}

	/**
	 * 根据实体名称和字段名称和字段值获取唯一记录
	 * 
	 * @param <T>
	 * @param entityClass
	 * @param propertyName
	 * @param value
	 * @return
	 */
	@Override
	public <T> T findUniqueByProperty(Class<T> entityClass,
			String propertyName, Object value) {
		return commonDao.findUniqueByProperty(entityClass, propertyName, value);
	}

	/**
	 * 按属性查找对象列表.
	 */
	@Override
	public <T> List<T> findByProperty(Class<T> entityClass,
			String propertyName, Object value) {

		return commonDao.findByProperty(entityClass, propertyName, value);
	}

	/**
	 * 加载全部实体
	 * 
	 * @param <T>
	 * @param entityClass
	 * @return
	 */
	@Override
	public <T> List<T> loadAll(final Class<T> entityClass) {
		return commonDao.loadAll(entityClass);
	}

	@Override
	public <T> T singleResult(String hql) {
		return commonDao.singleResult(hql);
	}

	/**
	 * 删除实体主键ID删除对象
	 * 
	 * @param <T>
	 * @param entities
	 */
	@Override
	public <T> void deleteEntityById(Class entityName, Serializable id) {
		commonDao.deleteEntityById(entityName, id);
	}

	/**
	 * 更新指定的实体
	 * 
	 * @param <T>
	 * @param pojo
	 */
	@Override
	public <T> void updateEntitie(T pojo) {
		commonDao.updateEntitie(pojo);

	}

	/**
	 * 通过hql 查询语句查找对象
	 * 
	 * @param <T>
	 * @param query
	 * @return
	 */
	@Override
	public <T> List<T> findByQueryString(String hql) {
		return commonDao.findByQueryString(hql);
	}

	/**
	 * 根据sql更新
	 * 
	 * @param query
	 * @return
	 */
	@Override
	public int updateBySqlString(String sql) {
		return commonDao.updateBySqlString(sql);
	}
	
	/**
	 * 根据sql查找List
	 * 
	 * @param <T>
	 * @param query
	 * @return
	 */
	@Override
	public <T> List<T> findListbySql(String query) {
		return commonDao.findListbySql(query);
	}

	/**
	 * 通过属性称获取实体带排序
	 * 
	 * @param <T>
	 * @param clas
	 * @return
	 */
	@Override
	public <T> List<T> findByPropertyisOrder(Class<T> entityClass,
			String propertyName, Object value, boolean isAsc) {
		return commonDao.findByPropertyisOrder(entityClass, propertyName,
				value, isAsc);
	}

	/**
	 * 
	 * cq方式分页
	 * 
	 * @param cq
	 * @param isOffset
	 * @return
	 */
	@Override
	public PageList getPageList(final CriteriaQuery cq, final boolean isOffset) {
		return commonDao.getPageList(cq, isOffset);
	}

	/**
	 * 返回DataTableReturn模型
	 * 
	 * @param cq
	 * @param isOffset
	 * @return
	 */
	@Override
	public DataTableReturn getDataTableReturn(final CriteriaQuery cq,
			final boolean isOffset) {
		return commonDao.getDataTableReturn(cq, isOffset);
	}

	/**
	 * 返回easyui datagrid模型
	 * 
	 * @param cq
	 * @param isOffset
	 * @return
	 */
	@Override
	public DataGridReturn getDataGridReturn(final CriteriaQuery cq,
			final boolean isOffset) {
		return commonDao.getDataGridReturn(cq, isOffset);
	}

	/**
	 * 
	 * hqlQuery方式分页
	 * 
	 * @param cq
	 * @param isOffset
	 * @return
	 */
	@Override
	public PageList getPageList(final HqlQuery hqlQuery,
			final boolean needParameter) {
		return commonDao.getPageList(hqlQuery, needParameter);
	}

	/**
	 * 
	 * sqlQuery方式分页
	 * 
	 * @param cq
	 * @param isOffset
	 * @return
	 */
	@Override
	public PageList getPageListBySql(final HqlQuery hqlQuery,
			final boolean isToEntity) {
		return commonDao.getPageListBySql(hqlQuery, isToEntity);
	}

	@Override
	public Session getSession()

	{
		return commonDao.getSession();
	}

	@Override
	public List findByExample(final String entityName,
			final Object exampleEntity) {
		return commonDao.findByExample(entityName, exampleEntity);
	}

	/**
	 * 通过cq获取全部实体
	 * 
	 * @param <T>
	 * @param cq
	 * @return
	 */
	@Override
	public <T> List<T> getListByCriteriaQuery(final CriteriaQuery cq,
			Boolean ispage) {
		return commonDao.getListByCriteriaQuery(cq, ispage);
	}

	/**
	 * 文件上传
	 * 
	 * @param request
	 */
	@Override
	public <T> T uploadFile(UploadFile uploadFile) {
		return commonDao.uploadFile(uploadFile);
	}

	@Override
	public HttpServletResponse viewOrDownloadFile(UploadFile uploadFile)

	{
		return commonDao.viewOrDownloadFile(uploadFile);
	}

	/**
	 * 生成XML文件
	 * 
	 * @param fileName
	 *            XML全路径
	 * @return
	 */
	@Override
	public HttpServletResponse createXml(ImportFile importFile) {
		return commonDao.createXml(importFile);
	}

	/**
	 * 解析XML文件
	 * 
	 * @param fileName
	 *            XML全路径
	 */
	@Override
	public void parserXml(String fileName) {
		commonDao.parserXml(fileName);
	}

	@Override
	public List<ComboTree> comTree(List<TSDepart> all, ComboTree comboTree) {
		return commonDao.comTree(all, comboTree);
	}

	@Override
	public List<ComboTree> ComboTree(List all, ComboTreeModel comboTreeModel, List in, boolean recursive) {
        return commonDao.ComboTree(all, comboTreeModel, in, recursive);
	}

	/**
	 * 构建树形数据表
	 */
	@Override
	public List<TreeGrid> treegrid(List all, TreeGridModel treeGridModel) {
		return commonDao.treegrid(all, treeGridModel);
	}

	/**
	 * 获取自动完成列表
	 * 
	 * @param <T>
	 * @return
	 */
	@Override
	public <T> List<T> getAutoList(Autocomplete autocomplete) {
		StringBuffer sb = new StringBuffer("");
		for (String searchField : autocomplete.getSearchField().split(",")) {
			sb.append("  or " + searchField + " like '%"
					+ autocomplete.getTrem() + "%' ");
		}
		String hql = "from " + autocomplete.getEntityName() + " where 1!=1 "
				+ sb.toString();
		return commonDao.getSession().createQuery(hql)
				.setFirstResult(autocomplete.getCurPage() - 1)
				.setMaxResults(autocomplete.getMaxRows()).list();
	}

	
	@Override
	public Integer executeSql(String sql, List<Object> param) {
		return commonDao.executeSql(sql, param);
	}

	
	@Override
	public Integer executeSql(String sql, Object... param) {
		return commonDao.executeSql(sql, param);
	}

	
	@Override
	public Integer executeSql(String sql, Map<String, Object> param) {
		return commonDao.executeSql(sql, param);
	}
	
	@Override
	public Object executeSqlReturnKey(String sql, Map<String, Object> param) {
		return commonDao.executeSqlReturnKey(sql, param);
	}
	
	@Override
	public List<Map<String, Object>> findForJdbc(String sql, int page, int rows) {
		return commonDao.findForJdbc(sql, page, rows);
	}

	
	@Override
	public List<Map<String, Object>> findForJdbc(String sql, Object... objs) {
		return commonDao.findForJdbc(sql, objs);
	}

	
	@Override
	public List<Map<String, Object>> findForJdbcParam(String sql, int page,
			int rows, Object... objs) {
		return commonDao.findForJdbcParam(sql, page, rows, objs);
	}

	
	@Override
	public <T> List<T> findObjForJdbc(String sql, int page, int rows,
			Class<T> clazz) {
		return commonDao.findObjForJdbc(sql, page, rows, clazz);
	}

	
	@Override
	public Map<String, Object> findOneForJdbc(String sql, Object... objs) {
		return commonDao.findOneForJdbc(sql, objs);
	}

	
	@Override
	public Long getCountForJdbc(String sql) {
		return commonDao.getCountForJdbc(sql);
	}

	@Override
	public Long getCountForJdbcParam(String sql, Object[] objs) {
		return commonDao.getCountForJdbcParam(sql,objs);
	}


	
	@Override
	public <T> void batchSave(List<T> entitys) {
		this.commonDao.batchSave(entitys);
	}

	/**
	 * 通过hql 查询语句查找对象
	 * 
	 * @param <T>
	 * @param query
	 * @return
	 */
	@Override
	public <T> List<T> findHql(String hql, Object... param) {
		return this.commonDao.findHql(hql, param);
	}
	
	/**
	 * 通过hql 修改对象
	 * 
	 * @param <T>
	 * @param query
	 * @return
	 */
	@Override
	public Integer updateHql(String hql, Object... params) {
		return this.commonDao.updateHql(hql, params);
	}

	@Override
	public <T> List<T> pageList(DetachedCriteria dc, int firstResult,
			int maxResult) {
		return this.commonDao.pageList(dc, firstResult, maxResult);
	}

	@Override
	public <T> List<T> findByDetached(DetachedCriteria dc) {
		return this.commonDao.findByDetached(dc);
	}

	/**
	 * 调用存储过程
	 */
	@Override
	public <T> List<T> executeProcedure(String procedureSql,Object... params) {
		return this.commonDao.executeProcedure(procedureSql, params);
	}

	// 数据变换的统一接口
		public interface IMyDataExchanger {
			public Object exchange(Object value);
		}
		
		// 页面表示数据与数据库字段的对应关系
		public class Db2Page {
			String fieldPage;		// 页面的fieldID
			String columnDB;		// 数据库的字段名
			IMyDataExchanger dataExchanger;		// 数据变换
			
			// 构造函数1：当页面的fieldID与数据库字段一致时（数据也不用变换）
			public Db2Page(String fieldPage) {
				this.fieldPage = fieldPage;
				this.columnDB = fieldPage;
				this.dataExchanger = null;
			}
			// 构造函数2：当页面的fieldID与数据库字段不一致时（数据不用变换）
			public Db2Page(String fieldPage, String columnDB) {
				this.fieldPage = fieldPage;
				if (columnDB == null) {// 与fieldPage相同
					this.columnDB = fieldPage;
				} else {
					this.columnDB = columnDB;
				}
				this.dataExchanger = null;
			}
			// 构造函数3：当页面的fieldID与数据库字段不一致，且数据要进行变换（当然都用这个构造函数也行）
			public Db2Page(String fieldPage, String columnDB, IMyDataExchanger dataExchanger) {
				this.fieldPage = fieldPage;
				if (columnDB == null) {// 与fieldPage相同
					this.columnDB = fieldPage;
				} else {
					this.columnDB = columnDB;
				}
				this.dataExchanger = dataExchanger;
			}
			
			/**
			 * 取页面表示绑定的fieldID
			 */
			public String getKey() {
				return fieldPage;
			}
			
			/**
			 * 取页面表示对应的值
			 * @param mapDB : 从数据库直接取得的结果集(一条数据的MAP)
			 * @return Object : 页面表示对应的值
			 */
			public Object getData(Map mapDB) {
				Object objValue = mapDB.get(columnDB);
				
				//新增的
				if (objValue == null && dataExchanger != null){
					return dataExchanger.exchange(objValue);
				}
				
				if (objValue == null) {
					return null;
				} else {
					if (dataExchanger != null) {
						return dataExchanger.exchange(objValue);
					} else {
						return objValue;
					}
				}
			}
		}
		
		// 性别的数据变换实体
		public class MyDataExchangerSex implements IMyDataExchanger {
			@Override
			public Object exchange(Object value) {
				if (value == null) {
					return "";
				} else if ("0".equals(value.toString())) {
					return "男";
				} else {
					return "女";
				}
			}
		}
		/**
		 * 返回easyUI的DataGrid数据格式的JSONObject对象
		 * @param mapList : 从数据库直接取得的结果集列表
		 * @param iTotalCnt : 从数据库直接取得的结果集总数据条数
		 * @param dataExchanger : 页面表示数据与数据库字段的对应关系列表
		 * @return JSONObject
		 */
		public JSONObject getJsonDatagridEasyUI(List<Map<String, Object>> mapList, int iTotalCnt, Db2Page[] dataExchanger) {
			//easyUI的dataGrid方式  －－－－这部分可以提取成统一处理
			String jsonTemp = "{\'total\':" + iTotalCnt + ",\'rows\':[";
			for (int j = 0; j < mapList.size(); j++) {
				Map<String, Object> m = mapList.get(j);
				if (j > 0) {
					jsonTemp += ",";
				}
				jsonTemp += "{";
				for (int i = 0; i < dataExchanger.length; i++) {
					if (i > 0) {
						jsonTemp += ",";
					}
					jsonTemp += "'" + dataExchanger[i].getKey() + "'" + ":";
					Object objValue = dataExchanger[i].getData(m);
					if (objValue == null) {
						jsonTemp += "null";
					} else {
						String val = (objValue + "" ).replace("\n","\\s").replace("\r","\\s");
						jsonTemp += "'" + val + "'";
					}
				}
				jsonTemp += "}";
			}
			jsonTemp += "]}";
			JSONObject jObject = JSONObject.fromObject(jsonTemp);
			return jObject;
		}
}

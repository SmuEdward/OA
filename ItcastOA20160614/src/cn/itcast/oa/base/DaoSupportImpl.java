package cn.itcast.oa.base;

import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.oa.domain.PageBean;
import cn.itcast.oa.util.QueryHelper;

@Transactional
@SuppressWarnings("unchecked")
public abstract class DaoSupportImpl<T> implements DaoSupport<T> {

	@Resource
	private SessionFactory sessionFactory;
	private Class<T> clazz;

	public DaoSupportImpl() {
		// 使用反射技术得到T的真实类型
		ParameterizedType pt = (ParameterizedType) this.getClass()
				.getGenericSuperclass(); // 获取当前new的对象的 泛型的父类 类型
		this.clazz = (Class<T>) pt.getActualTypeArguments()[0]; // 获取第一个类型参数的真实类型
		System.out.println("clazz ---> " + clazz);
	}

	/**
	 * 获取当前可用的Session
	 * 
	 * @return
	 */
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public void save(T entity) {
		getSession().save(entity);
	}

	public void update(T entity) {
		getSession().update(entity);
	}

	public void delete(Long id) {
		Object obj = getById(id);
		if (obj != null) {
			getSession().delete(obj);
		}
	}

	public T getById(Long id) {
		if (id == null) {
			return null;
		} else {
			return (T) getSession().get(clazz, id);
		}
	}

	public List<T> getByIds(Long[] ids) {
		if (ids == null || ids.length == 0) {
			return Collections.EMPTY_LIST;
		} else {
			return getSession().createQuery(//
					"FROM " + clazz.getSimpleName() + " WHERE id IN (:ids)")//
					.setParameterList("ids", ids)//
					.list();
		}
	}

	public List<T> findAll() {
		return getSession().createQuery(//
				"FROM " + clazz.getSimpleName())//
				.list();
	}

	public PageBean getPageBean(int currentPage, int pageSize, String hql,
			List parameters) {
		/*
		 * 获取每页显示的信息
		 */
		Query listQuery = getSession().createQuery(hql);
		for (int i = 0; i < parameters.size(); i++) {
			listQuery.setParameter(i, parameters.get(i));
		}
		listQuery.setFirstResult((currentPage - 1) * pageSize); // 注意这是记录的索引（从0开始）
		listQuery.setMaxResults(pageSize);//
		List recordList = listQuery.list();

		/*
		 * List recordList = getSession().createQuery(
		 * "FROM Topic t where t.forum = ? ORDER BY (CASE t.type WHEN 2 THEN 2 ELSE 0 END) DESC,t.lastUpdateTime DESC"
		 * )// .setParameter(0, forum)//
		 * .setFirstResult((currentPage-1)*pageSize) //注意这是记录的索引（从0开始）
		 * .setMaxResults(pageSize)// .list();
		 */

		/*
		 * 获取记录数
		 */
		Query countQuery = getSession().createQuery("SELECT count(*)" + hql);
		for (int i = 0; i < parameters.size(); i++) {
			countQuery.setParameter(i, parameters.get(i));
		}
		Long recordCount = (Long) countQuery.uniqueResult();

		/*
		 * Long recordCount = (Long)getSession().createQuery(
		 * "SELECT count(*) FROM Topic t where t.forum = ?") .setParameter(0,
		 * forum) .uniqueResult();
		 */

		return new PageBean(pageSize, currentPage, recordList,
				recordCount.intValue());
	}
	
	// 公共的查询分页信息的方法（最终版）
		public PageBean getPageBean(int currentPage, int pageSize, QueryHelper queryHelper) {
			System.out.println("-------> DaoSupportImpl.getPageBean( int pageNum, int pageSize, QueryHelper queryHelper )");

			// 参数列表
			List<Object> parameters = queryHelper.getParameters();

			// 查询本页的数据列表
			Query listQuery = getSession().createQuery(queryHelper.getListQueryHql()); // 创建查询对象
			if (parameters != null) { // 设置参数
				for (int i = 0; i < parameters.size(); i++) {
					listQuery.setParameter(i, parameters.get(i));
				}
			}
			listQuery.setFirstResult((currentPage - 1) * pageSize);
			listQuery.setMaxResults(pageSize);
			List list = listQuery.list(); // 执行查询

			// 查询总记录数量
			Query countQuery = getSession().createQuery(queryHelper.getCountQueryHql());
			if (parameters != null) { // 设置参数
				for (int i = 0; i < parameters.size(); i++) {
					countQuery.setParameter(i, parameters.get(i));
				}
			}
			Long count = (Long) countQuery.uniqueResult(); // 执行查询

			return new PageBean(currentPage, pageSize, list, count.intValue());
		}

}

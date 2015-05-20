package module.orm;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;

public class QueryDao<T, PK extends Serializable> extends CriteriaDao<T, Serializable> {

	@SuppressWarnings("unchecked")
	public <X> X get(final String hql, final Object... values) {
		return (X) createQuery(hql, values).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<T> find(final String hql, final Object... values) {
		return createQuery(hql, values).list();
	}

	/**
	 * 执行HQL进行批量修改/删除操作.
	 */
	public int batchExecute(final String hql, final Object... values) {
		return createQuery(hql, values).executeUpdate();
	}

	/**
	 * 根据查询HQL与参数列表创建Query对象.
	 */
	protected Query createQuery(final String queryString, final Object... values) {
		Query query = getSession().createQuery(queryString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}

	/**
	 * 根据查询HQL与参数列表创建Query对象.Map
	 */
	protected Query createQuery(final String queryString, final Map<String, Object> values) {
		Query query = getSession().createQuery(queryString);
		if (values != null) {
			// 格式 from Account where name=:name
			query.setProperties(values);
		}
		return query;
	}

}
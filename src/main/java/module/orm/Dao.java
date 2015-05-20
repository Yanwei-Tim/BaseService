package module.orm;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.beans.factory.annotation.Autowired;

public class Dao<T, PK extends Serializable> {

	protected Class<T> entityClass;

	protected SessionFactory sessionFactory;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Dao() {
		Class c = getClass();
		Type t = c.getGenericSuperclass();
		if (t instanceof ParameterizedType) {
			Type[] p = ((ParameterizedType) t).getActualTypeArguments();
			this.entityClass = (Class<T>) p[0];
		}
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Autowired
	public void setSessionFactory(final SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void save(final T entity) {
		getSession().saveOrUpdate(entity);
	}

	public void delete(final T entity) {
		getSession().delete(entity);
	}

	public void delete(final PK id) {
		delete(get(id));
	}

	@SuppressWarnings("unchecked")
	public T get(final PK id) {
		// load
		return (T) getSession().get(entityClass, id);
	}

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * 取得对象的主键名.
	 */
	protected String getIdName() {
		ClassMetadata meta = getSessionFactory().getClassMetadata(entityClass);
		return meta.getIdentifierPropertyName();
	}
}
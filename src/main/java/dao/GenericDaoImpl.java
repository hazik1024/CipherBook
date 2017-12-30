package dao;

import network.db.HibernateOperator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Map;

public abstract class GenericDaoImpl<T> implements GenericDao<T> {
    protected Logger logger = LogManager.getLogger(this.getClass());

    private Class<T> clazz;
    private String fromEntity;

    public GenericDaoImpl(final Class<T> clazz) {
        this.clazz = clazz;
        this.fromEntity = "FROM " + this.clazz.getSimpleName() + " ";
    }

    private Session getSession() {
        return HibernateOperator.getInstance().getSession();
    }

    public void save(T entity) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        session.save(entity);
        transaction.commit();
        session.close();
    }

    public void delete(T entity) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        session.delete(entity);
        transaction.commit();
        session.close();
    }

    public void update(T entity) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        session.update(entity);
        transaction.commit();
        session.close();
    }

    public T query(final String condition) {
        return this.query(condition, null);
    }

    public T query(final String condition, final Map<String, Object> params) {
        T entity = null;
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        try {
            String queryString = getQueryStringDefault();
            if (condition != null && condition.length() > 0) {
                queryString += condition;
            }
            Query<T> query = session.createQuery(queryString, clazz);
            if (params != null && params.size() > 0) {
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    query.setParameter(entry.getKey(), entry.getValue());
                }
            }
            entity = query.getSingleResult();
            return entity;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            transaction.commit();
            session.close();
        }
        return entity;
    }
    public List<T> queryList() {
        return this.queryList(null);
    }
    public List<T> queryList(final String condition) {
        return this.queryList(condition, null);
    }
    public List<T> queryList(final String condition, final Map<String, Object> params) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        String queryString = getQueryStringDefault();
        if (condition != null && condition.length() > 0) {
            queryString += condition;
        }
        Query<T> query = session.createQuery(queryString, clazz);
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }
        List<T> list = query.getResultList();
        transaction.commit();
        session.close();
        return list;
    }

    private String getQueryStringDefault() {
        return fromEntity;
    }
}

package dao;

import db.HibernateOperator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenericDaoImpl<T> implements GenericDao<T> {
    protected Logger logger = LogManager.getLogger(this.getClass());

    private Class<T> clazz;

    public GenericDaoImpl(final Class<T> clazz) {
        this.clazz = clazz;
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

    public T query(final String queryString) {
        return this.query(queryString, null);
    }

    public T query(String queryString, Map<String, Object> params) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        Query<T> query = session.createQuery(queryString, clazz);
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }
        T entity = query.getSingleResult();
        transaction.commit();
        session.close();
        return entity;
    }

    public List<T> queryList(final String queryString) {
        return this.queryList(queryString, null);
    }
    public List<T> queryList(final String queryString, final Map<String, Object> params) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
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
}

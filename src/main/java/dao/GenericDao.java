package dao;

import db.HibernateOperator;
import entity.BaseEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.Serializable;
import java.util.List;

public abstract class GenericDao<T extends BaseEntity, K extends Serializable> {
    protected Logger logger = LogManager.getLogger(this.getClass());

    protected Session getSession() {
        return HibernateOperator.getInstance().getSession();
    }

    private void closeSession(Session session) {
        session.close();
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

    protected T query(String queryString) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        T entity = (T)session.createQuery(queryString).getSingleResult();
        transaction.commit();
        session.close();
        return entity;
    }

    protected List<T> queryList(String queryString) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        List<T> list = (List<T>) session.createQuery(queryString).getResultList();
        transaction.commit();
        session.close();
        return list;
    }

    protected Integer queryCount(String queryString) {
        return this.queryList(queryString).size();
    }
}

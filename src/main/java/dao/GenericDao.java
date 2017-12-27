package dao;

import db.HibernateOperator;
import org.hibernate.Session;

import java.io.Serializable;

public abstract class GenericDao<T, K extends Serializable> {

    public Session getSession() {
        return HibernateOperator.getInstance().getSession();
    }

    public void closeSession(Session session) {
        session.close();
    }

    public void save(T entity) {
        Session session = getSession();
        session.save(entity);
        session.close();
    }

    public void delete(T entity) {
        Session session = getSession();
        session.delete(entity);
        session.close();
    }

    public void update(T entity) {
        Session session = getSession();
        session.update(entity);
        session.close();
    }
}

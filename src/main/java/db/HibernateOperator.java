package db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateOperator {
    private static HibernateOperator instance = new HibernateOperator();
    public static HibernateOperator getInstance() {
        return instance;
    }

    private HibernateOperator() {
    }

    public void initHibernate() {
        StandardServiceRegistry standardServiceRegistry = new StandardServiceRegistryBuilder().configure().build();
        this.sessionFactory = new MetadataSources(standardServiceRegistry).buildMetadata().buildSessionFactory();
    }

    private SessionFactory sessionFactory;

    public Session getSession() {
        return sessionFactory.openSession();
    }

    public void closeFactory() {
        this.sessionFactory.close();
    }
}

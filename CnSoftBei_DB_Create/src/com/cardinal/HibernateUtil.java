package com.cardinal;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

	private static final ThreadLocal<Session> threadLocal = new ThreadLocal<>();
    private static SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
        	Configuration cfg = new Configuration().configure();
            return cfg.buildSessionFactory(
            		new StandardServiceRegistryBuilder().applySettings(cfg.getProperties()).build());
        	//return new Configuration().configure().buildSessionFactory();
        }
        catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("创建会话工厂失败." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    public static void closeSession()throws HibernateException {
    	Session session = (Session) threadLocal.get();
    	threadLocal.set(null);
    	if(session !=null){
    		session.close();
    	}
    }

    public static Session getSession() throws HibernateException{
    	Session session = (Session) threadLocal.get();
    	if(session == null|| !session.isOpen()){
    		if(sessionFactory == null){
    			sessionFactory = buildSessionFactory();
    		}
    		session = (sessionFactory !=null)?sessionFactory.openSession():null;
    		threadLocal.set(session);
    	}
    	return session;
    }
}
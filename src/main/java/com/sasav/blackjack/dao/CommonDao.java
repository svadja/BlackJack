/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sasav.blackjack.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Vadim
 */
@Repository
public class CommonDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void saveOrUpdate(Object object) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.saveOrUpdate(object);
            session.flush();
            session.clear();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }

    }

    public void savaOrUpdateAll(Collection coll) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            for (Iterator it = coll.iterator(); it.hasNext();) {
                session.saveOrUpdate(it.next());
            }
            session.flush();
            session.clear();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }

    }

    public Object getById(String clazzString, final Serializable id) {
        Session session = sessionFactory.openSession();
        Object result = session.get(clazzString, id);
        session.close();
        return result;
    }

    public <T> List<T> getAll(Class<T> clazz) {
        Session session = sessionFactory.openSession();
        List result = session.createCriteria(clazz).list();
        session.close();
        return result;
    }

    public void delete(Object object) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(object);
            session.flush();
            session.clear();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    public void deleteAll(Collection coll) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            for (Iterator it = coll.iterator(); it.hasNext();) {
                session.delete(it.next());
            }
            session.flush();
            session.clear();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    public void runQuery(String queryS) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createSQLQuery(queryS);
            query.executeUpdate();
            session.flush();
            session.clear();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }
}

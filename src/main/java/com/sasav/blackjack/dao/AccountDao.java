/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sasav.blackjack.dao;

import com.sasav.blackjack.model.account.Account;
import com.sasav.blackjack.model.account.AccountTransaction;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Vadim
 */
public class AccountDao {

    private static final Logger LOG = Logger.getLogger(AccountDao.class.getName());

    @Autowired
    private SessionFactory sessionFactory;

    public Account getAccountByUsername(String username) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Account.class);
        criteria.add(Restrictions.eq("user.username", username));
        Account account = (Account) criteria.uniqueResult();
        session.flush();
        session.close();
        return account;
    }

    public boolean runAccountTransaction(Account account, AccountTransaction transaction) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.saveOrUpdate(transaction);
            session.saveOrUpdate(account);
            session.flush();
            session.clear();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            LOG.error("Saving AccountTransaction with error ", e);
            return false;
        }
        return true;
    }
    
     public boolean runAccountTransaction(Account account, Account account2, AccountTransaction transaction) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.saveOrUpdate(transaction);
            session.saveOrUpdate(account);
            session.saveOrUpdate(account2);
            session.flush();
            session.clear();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            LOG.error("Saving AccountTransaction with error ", e);
            return false;
        }
        return true;
    }
}

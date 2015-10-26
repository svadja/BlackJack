/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sasav.blackjack.dao;

import com.sasav.blackjack.model.security.LoginDetails;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Vadim
 */
public class UserDao {
    @Autowired
    private SessionFactory sessionFactory;
    
    public LoginDetails getLoginDetailsByUsername(String username){
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(LoginDetails.class);
        criteria.add(Restrictions.eq("username", username));
        LoginDetails loginDetails = (LoginDetails) criteria.uniqueResult();
        session.flush();
        session.close();
        return loginDetails;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sasav.blackjack.model.account;

import com.sasav.blackjack.model.security.LoginDetails;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Vadim
 */
@Entity
@Table(name = "accounts")
public class Account {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    @Column(name = "a_number")
    private String number;
    
    @OneToOne
    @JoinColumn(name="username", referencedColumnName="username")
    private LoginDetails user;
    
    //it is needed BigDecimal
    @Column(name = "amount")
    private BigDecimal amount;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account", cascade = CascadeType.ALL)
    private List<AccountTransaction> transactions;

    public Account() {
    }

    public Account(String number, LoginDetails user, BigDecimal amount) {
        this.number = number;
        this.user = user;
        this.amount = amount;
    }

    public Account(int id, String number, LoginDetails user, BigDecimal amount) {
        this.id = id;
        this.number = number;
        this.user = user;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LoginDetails getUser() {
        return user;
    }

    public void setUser(LoginDetails user) {
        this.user = user;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public List<AccountTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<AccountTransaction> transactions) {
        this.transactions = transactions;
    }
    
    
    
}

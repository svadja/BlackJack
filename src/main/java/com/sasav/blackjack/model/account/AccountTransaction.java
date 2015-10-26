/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sasav.blackjack.model.account;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Vadim
 */
@Entity
@Table(name = "account_transactions")
public class AccountTransaction {
        
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    @ManyToOne
    @JoinColumn(name="account_id", referencedColumnName="id")
    private Account account;
    
    @Column(name = "amount")
    private int amount;
    
    @Enumerated(EnumType.STRING)
     @Column(name = "tr_type")
    private TransactionType type;

    public AccountTransaction() {
    }

    public AccountTransaction(Account account, int amount, TransactionType type) {
        this.account = account;
        this.amount = amount;
        this.type = type;
    }

    public AccountTransaction(int id, Account account, int amount, TransactionType type) {
        this.id = id;
        this.account = account;
        this.amount = amount;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }
    
}

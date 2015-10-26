/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sasav.blackjack.service;

import com.sasav.blackjack.dao.AccountDao;
import com.sasav.blackjack.dao.CommonDao;
import com.sasav.blackjack.dao.UserDao;
import com.sasav.blackjack.model.account.Account;
import com.sasav.blackjack.model.account.AccountTransaction;
import com.sasav.blackjack.model.account.TransactionType;
import com.sasav.blackjack.model.security.LoginDetails;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Vadim
 */
public class AccountMaster {

    @Autowired
    CommonDao commonDao;

    @Autowired
    AccountDao accountDao;

    @Autowired
    UserDao userDao;

    public Account getAccountByUsername(String username) {
        return accountDao.getAccountByUsername(username);
    }

    public boolean depositUserAccount(String username, int amount) {
        Account account = accountDao.getAccountByUsername(username);
        if (account != null) {
            AccountTransaction transaction = new AccountTransaction(account, amount, TransactionType.DEPOSIT);
            account.setAmount(account.getAmount() + amount);
            return accountDao.runAccountTransaction(account, transaction);
        } else {
            return false;
        }
    }

    public boolean withDrawBet(String username, int bet) {
        Account account = accountDao.getAccountByUsername(username);
        if (account != null) {
            AccountTransaction transaction = new AccountTransaction(account, bet, TransactionType.BET);
            //@TODO: Неправильно - тут можуть бути уже неакуальні дані і можем зайти в мінус. Потрібно переносити все це в одну транзакцію. 
            account.setAmount(account.getAmount() - bet);
            return accountDao.runAccountTransaction(account, transaction);
        } else {
            return false;
        }
    }

    public AccountTransaction createTransactionForUser(String username) {
        Account account = accountDao.getAccountByUsername(username);
        if (account != null) {
            return new AccountTransaction(account, 0, TransactionType.BLANK);
        } else {
            return null;
        }

    }

}

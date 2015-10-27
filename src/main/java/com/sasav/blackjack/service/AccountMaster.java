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
import java.math.BigDecimal;
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

    public boolean depositUserAccount(String username, BigDecimal amount) {
        Account account = accountDao.getAccountByUsername(username);
        if (account != null) {
            AccountTransaction transaction = new AccountTransaction(account, amount, TransactionType.DEPOSIT);
            account.setAmount(account.getAmount().add(amount));
            return accountDao.runAccountTransaction(account, transaction);
        } else {
            return false;
        }
    }

    public boolean withDrawBet(String username, BigDecimal bet) {
        Account account = accountDao.getAccountByUsername(username);
        if (account != null) {
            AccountTransaction transaction = new AccountTransaction(account, bet, TransactionType.BET);
            account.setAmount(account.getAmount().subtract(bet));
            return accountDao.runAccountTransaction(account, transaction);
        } else {
            return false;
        }
    }

    public boolean winBet(String username, String dealerName, BigDecimal amount) {
        Account accountPlayer = accountDao.getAccountByUsername(username);
        Account accountDealer = accountDao.getAccountByUsername(dealerName);
        if ((accountPlayer != null)&&(accountDealer != null)) {
            AccountTransaction transaction = new AccountTransaction(accountPlayer, amount, TransactionType.GAME_WIN);
            accountPlayer.setAmount(accountPlayer.getAmount().add(amount));
            accountDealer.setAmount(accountDealer.getAmount().subtract(amount));
            return accountDao.runAccountTransaction(accountPlayer,accountDealer, transaction);
        } else {
            return false;
        }
    }

    public AccountTransaction createTransactionForUser(String username) {
        Account account = accountDao.getAccountByUsername(username);
        if (account != null) {
            return new AccountTransaction(account, BigDecimal.ZERO, TransactionType.BLANK);
        } else {
            return null;
        }

    }

}

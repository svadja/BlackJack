/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sasav.blackjack.controller;

import com.sasav.blackjack.model.account.AccountTransaction;
import com.sasav.blackjack.model.account.TransactionType;
import com.sasav.blackjack.service.AccountMaster;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Vadim
 */
@Controller
public class AccountController {

    private static final Logger LOG = Logger.getLogger(AccountController.class.getName());

    @Autowired
    AccountMaster accountMaster;

    @RequestMapping(value = "/deposit", method = RequestMethod.GET)
    public ModelAndView depositPage() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String login = auth.getName();
        AccountTransaction deposit = accountMaster.createTransactionForUser(login);
        ModelAndView mv = new ModelAndView("deposit");
        mv.addObject("depositAcc", deposit);
        return mv;
    }

    @RequestMapping(value = "/deposit", method = RequestMethod.POST)
    public ModelAndView deposit(AccountTransaction deposit) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String login = auth.getName();
        accountMaster.depositUserAccount(login, deposit.getAmount());
        ModelAndView mv = new ModelAndView("home");
        return mv;
    }
}

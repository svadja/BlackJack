/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sasav.blackjack.controller;

import com.sasav.blackjack.dao.CommonDao;
import com.sasav.blackjack.model.account.Account;
import com.sasav.blackjack.model.game.Game;
import com.sasav.blackjack.model.game.GameActor;
import com.sasav.blackjack.model.game.GameStatus;
import com.sasav.blackjack.service.AccountMaster;
import com.sasav.blackjack.service.GameCore;
import java.math.BigDecimal;
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
public class GameController {

    private static final Logger LOG = Logger.getLogger(GameController.class.getName());

    @Autowired
    CommonDao commonDao;

    @Autowired
    GameCore gameCore;

    @Autowired
    AccountMaster accountMaster;

    @RequestMapping(value = {"/game", "/"}, method = RequestMethod.GET)
    public ModelAndView gamePage() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String login = auth.getName();
        ModelAndView mv = new ModelAndView("game");
        Game userGame = gameCore.getUserGame(login);
        if (userGame == null) {
                return new ModelAndView("error");
        }
        Account account = accountMaster.getAccountByUsername(login);
        if (account != null) {
            mv.addObject("accountAmount", account.getAmount());
        }
        mv.addObject("userGame", userGame);

        return mv;
    }

    @RequestMapping(value = "/start_game", method = RequestMethod.GET)
    public ModelAndView startGameRequest(@RequestParam(value = "bet", required = true) BigDecimal bet) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String login = auth.getName();
        ModelAndView mv = new ModelAndView("game");
        Game userGame = gameCore.startNewGame(login, bet);
        if (userGame == null) {
            return new ModelAndView("error");
        }
        Account account = accountMaster.getAccountByUsername(login);
        if (account != null) {
            mv.addObject("accountAmount", account.getAmount());
        }
        mv.addObject("userGame", userGame);
        return mv;
    }

    @RequestMapping(value = "/hit", method = RequestMethod.GET)
    public ModelAndView hit() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String login = auth.getName();
        ModelAndView mv = new ModelAndView("game");
        
        Game userGame = gameCore.hit(login);
         Account account = accountMaster.getAccountByUsername(login);
        if (account != null) {
            mv.addObject("accountAmount", account.getAmount());
        }
        mv.addObject("userGame",userGame);
        return mv;
    }

    @RequestMapping(value = "/stand", method = RequestMethod.GET)
    public ModelAndView stand() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String login = auth.getName();
        ModelAndView mv = new ModelAndView("game");
        Game userGame = gameCore.stand(login);
         Account account = accountMaster.getAccountByUsername(login);
        if (account != null) {
            mv.addObject("accountAmount", account.getAmount());
        }
        mv.addObject("userGame",userGame);
        return mv;
    }

}

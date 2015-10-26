/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sasav.blackjack.controller;

import com.sasav.blackjack.dao.CommonDao;
import com.sasav.blackjack.model.account.Account;
import com.sasav.blackjack.model.game.Game;
import com.sasav.blackjack.model.game.GameStatus;
import com.sasav.blackjack.service.AccountMaster;
import com.sasav.blackjack.service.GameCore;
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

    @RequestMapping(value = "/new_game_page", method = RequestMethod.GET)
    public ModelAndView newGamePage() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	String login = auth.getName();
        ModelAndView mv = new ModelAndView("new-game");
        Game game = gameCore.getUserGame(login);
        String message="WELLCOME";
        if((game!=null)&&(game.getStatus()!=null)){
           if(game.getStatus().equals(GameStatus.PLAYER_LOST)) {
               message = "YOU LOST";
           }else if(game.getStatus().equals(GameStatus.PLAYER_WIN)) {
               message = "YOU WIN";
           } else if(game.getStatus().equals(GameStatus.PROCESS)) {
               return new ModelAndView("game");
           } 
        }
        int accountAmount = 0;
        Account account = accountMaster.getAccountByUsername(login);
        if (account!=null){
            accountAmount = account.getAmount();
        }
        mv.addObject("message", message);
        mv.addObject("accountAmount", accountAmount);
        return mv;
    }

    @RequestMapping(value = "/new_game", method = { RequestMethod.GET, RequestMethod.POST } )
    public String newGameRequest(@RequestParam(value = "bet", required = true) long bet) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	String login = auth.getName();
        gameCore.createNewGame("login", bet);
        return "game";
    }
}

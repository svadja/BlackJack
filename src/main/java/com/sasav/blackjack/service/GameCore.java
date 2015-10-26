/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sasav.blackjack.service;

import com.sasav.blackjack.dao.CommonDao;
import com.sasav.blackjack.dao.GameDao;
import com.sasav.blackjack.dao.UserDao;
import com.sasav.blackjack.model.card.Card;
import com.sasav.blackjack.model.card.CardSuit;
import com.sasav.blackjack.model.game.Game;
import com.sasav.blackjack.model.game.GameActor;
import com.sasav.blackjack.model.game.GamePoints;
import com.sasav.blackjack.model.game.GameStatus;
import com.sasav.blackjack.model.security.LoginDetails;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Vadim
 */
public class GameCore {

    public static final ArrayList<Card> DEFAULT_CARD_DECK = new ArrayList<Card>() {
        {
            add(new Card(1, CardSuit.CLUBS));
            add(new Card(2, CardSuit.CLUBS));
            add(new Card(3, CardSuit.CLUBS));
            add(new Card(4, CardSuit.CLUBS));
            add(new Card(5, CardSuit.CLUBS));
            add(new Card(6, CardSuit.CLUBS));
            add(new Card(7, CardSuit.CLUBS));
            add(new Card(8, CardSuit.CLUBS));
            add(new Card(9, CardSuit.CLUBS));
            add(new Card(10, CardSuit.CLUBS));
            add(new Card(11, CardSuit.CLUBS));
            add(new Card(12, CardSuit.CLUBS));
            add(new Card(13, CardSuit.CLUBS));

            add(new Card(1, CardSuit.DIAMONDS));
            add(new Card(2, CardSuit.DIAMONDS));
            add(new Card(3, CardSuit.DIAMONDS));
            add(new Card(4, CardSuit.DIAMONDS));
            add(new Card(5, CardSuit.DIAMONDS));
            add(new Card(6, CardSuit.DIAMONDS));
            add(new Card(7, CardSuit.DIAMONDS));
            add(new Card(8, CardSuit.DIAMONDS));
            add(new Card(9, CardSuit.DIAMONDS));
            add(new Card(10, CardSuit.DIAMONDS));
            add(new Card(11, CardSuit.DIAMONDS));
            add(new Card(12, CardSuit.DIAMONDS));
            add(new Card(13, CardSuit.DIAMONDS));

            add(new Card(1, CardSuit.HEARTS));
            add(new Card(2, CardSuit.HEARTS));
            add(new Card(3, CardSuit.HEARTS));
            add(new Card(4, CardSuit.HEARTS));
            add(new Card(5, CardSuit.HEARTS));
            add(new Card(6, CardSuit.HEARTS));
            add(new Card(7, CardSuit.HEARTS));
            add(new Card(8, CardSuit.HEARTS));
            add(new Card(9, CardSuit.HEARTS));
            add(new Card(10, CardSuit.HEARTS));
            add(new Card(11, CardSuit.HEARTS));
            add(new Card(12, CardSuit.HEARTS));
            add(new Card(13, CardSuit.HEARTS));

            add(new Card(1, CardSuit.SPADES));
            add(new Card(2, CardSuit.SPADES));
            add(new Card(3, CardSuit.SPADES));
            add(new Card(4, CardSuit.SPADES));
            add(new Card(5, CardSuit.SPADES));
            add(new Card(6, CardSuit.SPADES));
            add(new Card(7, CardSuit.SPADES));
            add(new Card(8, CardSuit.SPADES));
            add(new Card(9, CardSuit.SPADES));
            add(new Card(10, CardSuit.SPADES));
            add(new Card(11, CardSuit.SPADES));
            add(new Card(12, CardSuit.SPADES));
            add(new Card(13, CardSuit.SPADES));
        }
    };

    @Autowired
    CommonDao commonDao;

    @Autowired
    GameDao gameDao;

    @Autowired
    UserDao userDao;

    public void pullRandomCardFromDeck(Game game, GameActor actor) {
        ArrayList<Card> cardDeck = game.getCardDeck();
        int index = (int) (Math.random() * cardDeck.size());
        Card card = cardDeck.get(index);
        if (actor.equals(GameActor.PLAYER)) {
            game.getPlayerSet().add(card);
        } else {
            game.getDealerSet().add(card);
        }
        cardDeck.remove(index);
        commonDao.saveOrUpdate(game);
    }

    public GameStatus createNewGame(LoginDetails loginDetails, long bet) {
        Game game = gameDao.getGameByUsername(loginDetails.getUsername());
        if (game == null) {
            game = new Game(loginDetails, null, null, (ArrayList<Card>) DEFAULT_CARD_DECK.clone(), bet, GameStatus.NEW);
            commonDao.saveOrUpdate(game);
            return GameStatus.NEW;
        } else if (game.getStatus().equals(GameStatus.PROCESS)) {
            return GameStatus.PROCESS;
        } else {
            game.setStatus(GameStatus.NEW);
            game.setBet(bet);
            game.setPlayerSet(null);
            game.setDealerSet(null);
            game.setCardDeck((ArrayList<Card>) DEFAULT_CARD_DECK.clone());
            commonDao.saveOrUpdate(game);
            return GameStatus.NEW;
        }

    }

    public GameStatus createNewGame(String username, long bet) {

        LoginDetails loginDetails = userDao.getLoginDetailsByUsername(username);
        if (loginDetails != null) {
            return createNewGame(loginDetails, bet);
        } else {
            return GameStatus.ERROR;
        }
    }

    public Game getUserGame(String username) {
        return gameDao.getGameByUsername(username);
    }

    public GamePoints countPoints(ArrayList<Card> cardSet) {
        int countAce = 0;
        int countFigure = 0;
        int minPoint = 0;
        boolean bj = false;
        for (Card card : cardSet) {
            int rank = card.getRank();
            if (rank > 10) {
                minPoint = minPoint + 10;
                countFigure++;
            } else if (rank == 1) {
                minPoint = minPoint + 1;
                countAce++;
            } else {
                minPoint = minPoint + rank;
            }
        }
        int maxPoint = minPoint;
        if ((countAce > 0) && (maxPoint < 11)) {
            maxPoint = maxPoint + 10;
            if ((maxPoint == 21) && (countFigure > 0) && (cardSet.size() == 2)) {
                bj=true;
            }
        }
        return new GamePoints(minPoint, maxPoint, bj);
    }

}

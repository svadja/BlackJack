/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sasav.blackjack.service;

import com.sasav.blackjack.Const;
import com.sasav.blackjack.controller.BasicController;
import com.sasav.blackjack.dao.AccountDao;
import com.sasav.blackjack.dao.CommonDao;
import com.sasav.blackjack.dao.GameDao;
import com.sasav.blackjack.dao.UserDao;
import com.sasav.blackjack.model.account.Account;
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
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import sun.security.x509.CRLDistributionPointsExtension;

/**
 *
 * @author Vadim
 */
public class GameCore {

    private static final Logger LOG = Logger.getLogger(GameCore.class.getName());
    
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
    AccountDao accountDao;

    @Autowired
    UserDao userDao;

    @Autowired
    AccountMaster accountMaster;

    public Game pullRandomCardFromDeck(Game game, GameActor actor) {
        ArrayList<Card> cardDeck = game.getCardDeck();
        int index = (int) (Math.random() * cardDeck.size());
        Card card = cardDeck.get(index);
        if (actor.equals(GameActor.PLAYER)) {
            game.getPlayerSet().add(card);
        } else {
            game.getDealerSet().add(card);
        }
        return game;
    }

    public Game createNewGame(LoginDetails loginDetails) {
        Game game = getUserGame(loginDetails);
        boolean noError = false;
        if (game == null) {
            game = new Game(loginDetails, null, null, (ArrayList<Card>) DEFAULT_CARD_DECK.clone(), BigDecimal.ZERO, GameStatus.NEW);
            noError = commonDao.saveOrUpdate(game);

        }
        return noError ? game : null;
    }

    public Game createNewGame(String username) {
        LoginDetails loginDetails = userDao.getLoginDetailsByUsername(username);
        return (loginDetails != null) ? createNewGame(loginDetails) : null;
    }

    public Game startNewGame(String username, BigDecimal bet) {
        LoginDetails loginDetails = userDao.getLoginDetailsByUsername(username);
        if (loginDetails == null) {
            return null;
        }
        Game game = getUserGame(loginDetails);
        if (game == null) {
            game = new Game(loginDetails, null, null, (ArrayList<Card>) DEFAULT_CARD_DECK.clone(), bet, GameStatus.NEW);
            if (!commonDao.saveOrUpdate(game)) {
                return null;
            }
        } else if (game.getStatus().equals(GameStatus.PROCESS)) {
            return game;
        }
        Account account = accountDao.getAccountByUsername(username);
        if ((account != null) && (account.getAmount().compareTo(bet) != -1) && (accountMaster.withDrawBet(username, bet))) {
            game.setStatus(GameStatus.PROCESS);
            game.setBet(bet);
            game.setPlayerSet(new ArrayList<Card>());
            game.setDealerSet(new ArrayList<Card>());
            game.setCardDeck((ArrayList<Card>) DEFAULT_CARD_DECK.clone());
            pullRandomCardFromDeck(game, GameActor.PLAYER);
            pullRandomCardFromDeck(game, GameActor.PLAYER);
            pullRandomCardFromDeck(game, GameActor.DEALER);
            if (commonDao.saveOrUpdate(game)) {
                return game;
            } else {
                return getUserGame(username);
            }
        } else {
            return game;
        }
    }

    public Game hit(String username) {
        Game game = getUserGame(username);
        if (game.getStatus().equals(GameStatus.PROCESS)) {
            game = pullRandomCardFromDeck(game, GameActor.PLAYER);
            GamePoints points = countPoints(game.getPlayerSet());
            //Bust
            if (points.getMin() > 21) {
                game.setStatus(GameStatus.PLAYER_LOST);
            }
            boolean accSaveStatus = false;
            if (commonDao.saveOrUpdate(game)) {
               accSaveStatus = accountMaster.depositUserAccount(Const.DEALER_NAME, game.getBet());
            } else {
                game = getUserGame(username);
            }
        }
        return game;
    }

    public Game stand(String username) {
        Game game = getUserGame(username);
        if (game.getStatus().equals(GameStatus.PROCESS)) {
            GamePoints playerPoints = countPoints(game.getPlayerSet());

            int pPoints = playerPoints.getMax();
            if (pPoints > 21) {
                pPoints = playerPoints.getMin();
            }

            game = pullRandomCardFromDeck(game, GameActor.DEALER);
            GamePoints dealerPoints = countPoints(game.getDealerSet());
            int dPoints = dealerPoints.getMax();

            while (dPoints < 17) {
                game = pullRandomCardFromDeck(game, GameActor.DEALER);
                dealerPoints = countPoints(game.getDealerSet());
                dPoints = dealerPoints.getMax();
                if (dPoints > 21) {
                    dPoints = dealerPoints.getMin();
                }
            }

            game = getGameResult(game);
            boolean accSaveStatus = false;
            if (commonDao.saveOrUpdate(game)) {
                if (game.getStatus().equals(GameStatus.PUSH)) {
                    accSaveStatus = accountMaster.depositUserAccount(username, game.getBet());
                } else if (game.getStatus().equals(GameStatus.PLAYER_LOST)) {
                    accSaveStatus = accountMaster.depositUserAccount(Const.DEALER_NAME, game.getBet());
                } else if (game.getStatus().equals(GameStatus.PLAYER_BJ)) {
                    accSaveStatus = accountMaster.winBet(username, Const.DEALER_NAME, game.getBet().multiply(new BigDecimal(2.5)));
                } else if (game.getStatus().equals(GameStatus.PLAYER_WIN)) {
                    accSaveStatus = accountMaster.winBet(username, Const.DEALER_NAME, game.getBet().multiply(new BigDecimal(2)));
                }
            }else{
                game = getUserGame(username);
            }

        }
        return game;
    }

    public Game getUserGame(String username) {
        return gameDao.getGameByUsername(username);
    }

    public Game getUserGame(LoginDetails user) {
        return gameDao.getGameByUsername(user.getUsername());
    }

    private GamePoints countPoints(ArrayList<Card> cardSet) {
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
                bj = true;
            }
        }
        return new GamePoints(minPoint, maxPoint, bj);
    }

    private Game getGameResult(Game game) {
        GamePoints playerPoints = countPoints(game.getPlayerSet());
        int pPoints = playerPoints.getMax();
        if (pPoints > 21) {
            pPoints = playerPoints.getMin();
        }

        GamePoints dealerPoints = countPoints(game.getDealerSet());
        int dPoints = dealerPoints.getMax();
        if (dPoints > 21) {
            dPoints = dealerPoints.getMin();
        }
        
        
        LOG.info("PLAYER: " + pPoints + " BJ: " + playerPoints.isBj() + " DEALER: " + dPoints + " BJ: " + dealerPoints.isBj());

        if (playerPoints.isBj()) {
            if (dealerPoints.isBj()) {
                //push
                game.setStatus(GameStatus.PUSH);

            } else {
                //black jack player
                game.setStatus(GameStatus.PLAYER_BJ);

            }
        } else if ((dPoints > 21) || (pPoints > dPoints)) {
            //win
            game.setStatus(GameStatus.PLAYER_WIN);

        } else if (pPoints < dPoints) {
            //lost
            game.setStatus(GameStatus.PLAYER_LOST);
        } else {
            //push
            game.setStatus(GameStatus.PUSH);
        }
        return game;
    }

}

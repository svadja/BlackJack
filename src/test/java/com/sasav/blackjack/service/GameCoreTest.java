package com.sasav.blackjack.service;

import com.sasav.blackjack.model.card.Card;
import com.sasav.blackjack.model.card.CardSuit;
import com.sasav.blackjack.model.game.Game;
import com.sasav.blackjack.model.game.GamePoints;
import com.sasav.blackjack.model.game.GameStatus;
import com.sasav.blackjack.model.security.LoginDetails;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author sasav
 */
public class GameCoreTest {

    public GameCoreTest() {
    }

    public static GameCore gameCore;

    @BeforeClass
    public static void beforeClass() {
        gameCore = new GameCore();
    }

    @Test
    public void testCountPoints() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method method = GameCore.class.getDeclaredMethod("countPoints", ArrayList.class);
        method.setAccessible(true);
        ArrayList<Card> cardSet;
        GamePoints points;

        // Set Black Jack
        cardSet = new ArrayList<Card>();
        points = new GamePoints();

        cardSet.add(new Card(1, CardSuit.CLUBS));
        cardSet.add(new Card(12, CardSuit.CLUBS));
        points = (GamePoints) method.invoke(gameCore, cardSet);
        assertTrue("set of Queen and Ace is Black Jack", points.isBj());
        assertEquals("max points of Queen and Ace = 21", 21, points.getMax());
        assertEquals("min points of Queen and Ace = 21", 11, points.getMin());

        // Set Ace + 10
        cardSet = new ArrayList<Card>();
        points = new GamePoints();

        points = (GamePoints) method.invoke(gameCore, cardSet);
        cardSet.add(new Card(1, CardSuit.CLUBS));
        cardSet.add(new Card(10, CardSuit.CLUBS));
        assertFalse("set of 10 and Ace is NOT Black Jack", points.isBj());

        // Set Ace + Ace + 8
        cardSet = new ArrayList<Card>();
        points = new GamePoints();

        cardSet.add(new Card(1, CardSuit.CLUBS));
        cardSet.add(new Card(1, CardSuit.CLUBS));
        cardSet.add(new Card(8, CardSuit.CLUBS));
        points = (GamePoints) method.invoke(gameCore, cardSet);
        assertEquals("max points of Ace + Ace + 8 = 20", 20, points.getMax());
        assertEquals("min points of Ace + Ace + 8 = 10", 10, points.getMin());

        //Set Queen + Queen + 5
        cardSet = new ArrayList<Card>();
        points = new GamePoints();

        points = (GamePoints) method.invoke(gameCore, cardSet);
        cardSet.add(new Card(12, CardSuit.CLUBS));
        cardSet.add(new Card(12, CardSuit.CLUBS));
        cardSet.add(new Card(5, CardSuit.CLUBS));
        points = (GamePoints) method.invoke(gameCore, cardSet);
        assertEquals("max points of Queen + Queen + 5 = 25", 25, points.getMax());
        assertEquals("min points of Queen + Queen + 5 = 25", 25, points.getMin());

    }

    @Test
    public void testGetGameResult() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Method method = GameCore.class.getDeclaredMethod("getGameResult", Game.class);
        method.setAccessible(true);
        Game game;
        Game gameResult;
        ArrayList<Card> playerSet;
        ArrayList<Card> dealerSet;
        
        /*
         player: Black Jack 
         dealer: Ace+10
         expected status GameStatus.PLAYER_BJ
         */
        playerSet = new ArrayList<Card>();
        playerSet.add(new Card(1, CardSuit.CLUBS));
        playerSet.add(new Card(12, CardSuit.CLUBS));
        dealerSet = new ArrayList<Card>();
        dealerSet.add(new Card(1, CardSuit.CLUBS));
        dealerSet.add(new Card(10, CardSuit.CLUBS));
        game = new Game(null, playerSet, dealerSet, null, null, GameStatus.NEW);
        gameResult = (Game) method.invoke(gameCore, game);
        assertEquals("GameStatus of game with player set Queen and Ace is PLAYER_BJ", GameStatus.PLAYER_BJ ,gameResult.getStatus());
       
        /*
         player: Ace+10 
         dealer: Black Jack
         expected status GameStatus.PLAYER_LOST
         */
        playerSet = new ArrayList<Card>();
        playerSet.add(new Card(1, CardSuit.CLUBS));
        playerSet.add(new Card(10, CardSuit.CLUBS));
        dealerSet = new ArrayList<Card>();
        dealerSet.add(new Card(1, CardSuit.CLUBS));
        dealerSet.add(new Card(12, CardSuit.CLUBS));
        game = new Game(null, playerSet, dealerSet, null, null, GameStatus.NEW);
        gameResult = (Game) method.invoke(gameCore, game);
        assertEquals("GameStatus of game with dealer set Queen and Ace is PLAYER_LOST", GameStatus.PLAYER_LOST ,gameResult.getStatus());
        
        
         /*
         player: Black Jack 
         dealer: Black Jack 
         expected status GameStatus.PUSH
         */
        playerSet = new ArrayList<Card>();
        playerSet.add(new Card(1, CardSuit.CLUBS));
        playerSet.add(new Card(12, CardSuit.CLUBS));
        dealerSet = new ArrayList<Card>();
        dealerSet.add(new Card(1, CardSuit.CLUBS));
        dealerSet.add(new Card(12, CardSuit.CLUBS));
        game = new Game(null, playerSet, dealerSet, null, null, GameStatus.NEW);
        gameResult = (Game) method.invoke(gameCore, game);
        assertEquals("GameStatus of game with player and dealer set Queen and Ace is PUSH", GameStatus.PUSH ,gameResult.getStatus());
        
        /*
         player: 10+10+5
         dealer: 10+2
         expected status GameStatus.PLAYER_LOST
         */
        playerSet = new ArrayList<Card>();
        playerSet.add(new Card(5, CardSuit.CLUBS));
        playerSet.add(new Card(10, CardSuit.CLUBS));
        playerSet.add(new Card(10, CardSuit.CLUBS));
        dealerSet = new ArrayList<Card>();
        dealerSet.add(new Card(10, CardSuit.CLUBS));
        dealerSet.add(new Card(2, CardSuit.CLUBS));
        game = new Game(null, playerSet, dealerSet, null, null, GameStatus.NEW);
        gameResult = (Game) method.invoke(gameCore, game);
        assertEquals("GameStatus of game with player: 10+10+5  dealer: 10+2 is PLAYER_LOST", GameStatus.PLAYER_LOST ,gameResult.getStatus());
        
        
        /*
         player: 10+2 
         dealer: 10+2 
         expected status GameStatus.PUSH
         */
        playerSet = new ArrayList<Card>();
        playerSet.add(new Card(10, CardSuit.CLUBS));
        playerSet.add(new Card(2, CardSuit.CLUBS));
        dealerSet = new ArrayList<Card>();
        dealerSet.add(new Card(10, CardSuit.CLUBS));
        dealerSet.add(new Card(2, CardSuit.CLUBS));
        game = new Game(null, playerSet, dealerSet, null, null, GameStatus.NEW);
        gameResult = (Game) method.invoke(gameCore, game);
        assertEquals("GameStatus of game with  player: 10+2 dealer: 10+2 is PUSH", GameStatus.PUSH ,gameResult.getStatus());
        
        /*
         player: Ace+2 
         dealer: 10+2 
         expected status GameStatus.PLAYER_WIN
         */
        playerSet = new ArrayList<Card>();
        playerSet.add(new Card(1, CardSuit.CLUBS));
        playerSet.add(new Card(2, CardSuit.CLUBS));
        dealerSet = new ArrayList<Card>();
        dealerSet.add(new Card(10, CardSuit.CLUBS));
        dealerSet.add(new Card(2, CardSuit.CLUBS));
        game = new Game(null, playerSet, dealerSet, null, null, GameStatus.NEW);
        gameResult = (Game) method.invoke(gameCore, game);
        assertEquals("GameStatus of game with  player: Ace+2 dealer: 10+2 is PLAYER_WIN", GameStatus.PLAYER_WIN ,gameResult.getStatus());
    }

}

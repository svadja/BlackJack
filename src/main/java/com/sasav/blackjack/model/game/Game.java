/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sasav.blackjack.model.game;

import com.sasav.blackjack.model.card.Card;
import com.sasav.blackjack.model.security.LoginDetails;
import java.math.BigDecimal;
import java.util.ArrayList;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;


/**
 *
 * @author Vadim
 */
@Entity
@Table(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    
    @OneToOne
    @JoinColumn(name="username", referencedColumnName="username")
    private LoginDetails user;
    
    @Lob
    @Column(name = "playerSet")
    private ArrayList<Card> playerSet;
    
    @Lob
    @Column(name = "dealerSet")
    private ArrayList<Card> dealerSet;
    
    @Lob
    @Column(name = "cardDeck")
    private ArrayList<Card> cardDeck;    
    
    @Column(name = "bet")
    private int bet;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private GameStatus status;

    public Game() {
    }

    public Game(LoginDetails user, ArrayList<Card> playerSet, ArrayList<Card> dealerSet, ArrayList<Card> cardDeck, int bet, GameStatus status) {
        this.user = user;
        this.playerSet = playerSet;
        this.dealerSet = dealerSet;
        this.cardDeck = cardDeck;
        this.bet = bet;
        this.status = status;
    }

    public Game(int id, LoginDetails user, ArrayList<Card> playerSet, ArrayList<Card> dealerSet, ArrayList<Card> cardDeck, int bet, GameStatus status) {
        this.id = id;
        this.user = user;
        this.playerSet = playerSet;
        this.dealerSet = dealerSet;
        this.cardDeck = cardDeck;
        this.bet = bet;
        this.status = status;
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LoginDetails getUser() {
        return user;
    }

    public void setUser(LoginDetails user) {
        this.user = user;
    }

    public ArrayList<Card> getPlayerSet() {
        return playerSet;
    }

    public void setPlayerSet(ArrayList<Card> playerSet) {
        this.playerSet = playerSet;
    }

    public ArrayList<Card> getDealerSet() {
        return dealerSet;
    }

    public void setDealerSet(ArrayList<Card> dealerSet) {
        this.dealerSet = dealerSet;
    }

    public ArrayList<Card> getCardDeck() {
        return cardDeck;
    }

    public void setCardDeck(ArrayList<Card> cardDeck) {
        this.cardDeck = cardDeck;
    }

    public int getBet() {
        return bet;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }
    
    

}

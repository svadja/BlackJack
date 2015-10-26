/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sasav.blackjack.model.card;

import java.io.Serializable;

/**
 *
 * @author Vadim
 * Rank :
 *      ACE     - 1
 *      2       - 2
 *      ...
 *      10      - 10
 *      JACK    - 11
 *      QUEEN   - 12
 *      KING    - 13
 * 
 * 
 */

public class Card implements Serializable{
    private int rank;
    private CardSuit suit;

    public Card() {
    }
    
    public Card(int rank, CardSuit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public CardSuit getSuit() {
        return suit;
    }

    public void setSuit(CardSuit suit) {
        this.suit = suit;
    }
}

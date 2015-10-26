/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sasav.blackjack.dao;

import com.sasav.blackjack.model.card.Card;
import com.sasav.blackjack.model.game.Game;
import com.sasav.blackjack.model.game.GameActor;
import java.util.ArrayList;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Vadim
 */
@Repository
public class GameDao {

    @Autowired
    private SessionFactory sessionFactory;

    public Card pullRandomCardFromDeck(Game game, GameActor actor) {
        ArrayList<Card> cardDeck = game.getCardDeck();
        int index = (int)(Math.random()*cardDeck.size());
        Card card = cardDeck.get(index);
        cardDeck.remove(index);
        Session session = sessionFactory.openSession();
        session.flush();
        session.close();
        return null;
    }

    
    public Game getGameByUsername(String username){
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Game.class);
        criteria.add(Restrictions.eq("user.username", username));
        Game game = (Game) criteria.uniqueResult();
        session.flush();
        session.close();
        return game;
    }
}

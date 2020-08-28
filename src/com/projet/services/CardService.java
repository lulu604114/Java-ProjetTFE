package com.projet.services;

import com.projet.entities.Card;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CardService extends Service<Card> implements Serializable {
    private static final Logger log = Logger.getLogger(CardService.class);
    private static final long serialVersionUID = 1L;
    Map<String, Object> params = new HashMap<String, Object>();

    public CardService(Class<?> ec) {
        super(ec);
    }

    public List<Card> findAllCards() {
        return finder.findByNamedQuery("Card.findAll", null);
    }

    @Override
    public Card save(Card card) {
        return null;
    }
}

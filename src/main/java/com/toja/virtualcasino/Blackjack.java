package com.toja.virtualcasino;

import java.util.ArrayList;
import java.util.Random;

public class Blackjack {

    private static ArrayList<Card> deck;
    private int playerBet;
    private ArrayList<ArrayList<Card>> playerHands;
    private ArrayList<Card> dealerCards;

    public Blackjack(ArrayList<Card> deck, int playerBet, ArrayList<ArrayList<Card>> playerHands, ArrayList<Card> dealerCards) {
        Blackjack.deck = Card.create52Deck();
        this.playerBet = playerBet;
        this.playerHands = playerHands;
        this.dealerCards = dealerCards;
    }

    public int makeBet(int amount) {
        setPlayerBet(amount);
        return amount;
    }

    public static Card drawCard(ArrayList<Card> toDraw) {
        Random r = new Random();
        int index = r.nextInt(0, toDraw.size());
        Card temp = toDraw.get(index);
        toDraw.remove(index);

        return temp;
    }


    public int getPlayerBet() {
        return playerBet;
    }

    public void setPlayerBet(int playerBet) {
        this.playerBet = playerBet;
    }

    public ArrayList<ArrayList<Card>> getPlayerHands() {
        return playerHands;
    }

    public void setPlayerHands(ArrayList<ArrayList<Card>> playerHands) {
        this.playerHands = playerHands;
    }

    public ArrayList<Card> getDealerCards() {
        return dealerCards;
    }

    public void setDealerCards(ArrayList<Card> dealerCards) {
        this.dealerCards = dealerCards;
    }
}

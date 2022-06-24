package com.toja.virtualcasino;

import java.util.ArrayList;
import java.util.Random;

public class Blackjack {

    private static ArrayList<Card> deck;
    private ArrayList<Integer> playerBet;
    private ArrayList<ArrayList<Card>> playerHands;
    private ArrayList<Card> dealerCards;

    public Blackjack(ArrayList<Card> deck, ArrayList<Integer> playerBet, ArrayList<ArrayList<Card>> playerHands, ArrayList<Card> dealerCards) {
        Blackjack.deck = Card.create52Deck();
        this.playerBet = playerBet;
        this.playerHands = playerHands;
        this.dealerCards = dealerCards;
    }


    public static Card drawCard(ArrayList<Card> toDraw) {
        Random r = new Random();
        int index = r.nextInt(0, toDraw.size());
        Card temp = toDraw.get(index);
        toDraw.remove(index);

        return temp;
    }

    public ArrayList<Integer> getPlayerBet() {
        return playerBet;
    }

    public void setPlayerBet(int newBet, int hand) {
        this.playerBet.set(hand, newBet);
    }

    public ArrayList<ArrayList<Card>> getPlayerHands() {
        return playerHands;
    }

    public ArrayList<Card> getDealerCards() {
        return dealerCards;
    }
}
